
function fetchInjectionResult(filename) {
$.ajax({
    url: "/api/injection-result/getAjax",
    data: {filename: filename},
    success: function(ajaxData) {
        $('#ajax-content')[0].innerHTML = ajaxData;
         if(filename === "injection-result-list.html") {
             $("#ajax-title")[0].innerHTML = "INJECTION RESULT LIST";
             findAllInjectionResults(0, 10);
         }
         else {
            $("#ajax-title")[0].innerHTML = "CREATE INJECTION RESULT";
            setupDropdown();
//            $('#vaccineTypeName')[0].addEventListener("change", filterVaccineName);
         }
    },
    error: function(xhr) {
        alert("error at /api/injection-result/getAjax, error code: " + xhr.status);
        console.log("response code: " + xhr.status);
        console.log("response body: " + JSON.parse(xhr.responseText).message);
    }
});
}

function setupDropdown() {
	$.ajax({
		url: "/api/injection-result/displayDropdown",
		success:function (data) {
			vaccineTypeDisplayObject = data.vaccineTypes;
			$("#customer")[0].innerHTML = '<option value="" disabled selected>--Select Customer--</option>';
			$("#vaccineTypeName")[0].innerHTML = '<option value="" disabled selected>--Select Vaccine Type Name--</option>';
			$("#vaccineName")[0].innerHTML = '<option value="" disabled selected>--Select Vaccine Type--</option>';
			data.customers.forEach(customer => {
				const row = `<option value="${customer.customerId}">${customer.customerId}-${customer.fullName}-${customer.dateOfBirth}</option>`;
				$("#customer").append(row);
			});
			data.vaccineTypes.forEach(vaccineType => {
				const row = `<option value="${vaccineType.vaccineTypeId}">--${vaccineType.vaccineTypeName}--</option>`;
				$("#vaccineTypeName").append(row);
			});
		},
		error: function(xhr) {
			alert("error at /api/injection-result/displayDropdown, error code: " + xhr.status);
		}
	});
}

function dropdownVaccineName() {
	$("#vaccineName")[0].innerHTML = '<option value="" disabled selected>--Select Vaccine Type--</option>';
	const vaccineTypeValue = $("#vaccineTypeName")[0].value;
	vaccineTypeDisplayObject.forEach(vaccineType => {
		if (vaccineType.vaccineTypeId === vaccineTypeValue) {
			vaccineType.vaccines.forEach(vaccine => {
				const row = `<option value ="${vaccine.vaccineId}">--${vaccine.vaccineName}--</option>`;
				$("#vaccineName").append(row);
			});
		}
	});
}

function fetchUpdateInjectionResult(filename, injectionResultId) {
fetch(`/api/injection-result/getAjax?filename=${filename}`)
.then(response => response.text())
    .then(data => {
        document.getElementById('ajax-content').innerHTML = data;
        updateInjectionResultDetail(injectionResultId);
        loadCustomers();
        loadVaccineTypeName();
        loadInjectionPlace();
        document.getElementById('vaccineTypeName').addEventListener('change', function() {
            const selectedVaccineTypeId = this.value;
            const vaccineSelect = document.getElementById('vaccineName');
            // Clear previous vaccine names and hide dropdown if no vaccine type is selected
            vaccineSelect.innerHTML = '<option selected>--Select Vaccine--</option>';
            vaccineSelect.style.display = 'none';

            if (selectedVaccineTypeId) {
                loadVaccines(selectedVaccineTypeId);
            }
      });

})
  .catch(error => console.error('Error fetching document:', error));
}

function findAllInjectionResults(page) {
    const searchInputElement = document.getElementById('searchInput');
    const query = searchInputElement ? searchInputElement.value : '';
    const pageSize = parseInt($("#dropdownMenuButton")[0].innerHTML, 10);
	$.ajax({
		url: "/api/injection-result/findBySearch",
		data: {
			searchInput: query,
			page: page,
			size: pageSize
		},
		success: function(injectionResults) {
			const tableBody = document.getElementById('injection-result-list-content');
            tableBody.innerHTML = '';
            injectionResults.content.forEach(result => {
                const row = document.createElement('tr');
                row.innerHTML = `
                        <td class="text-center check-boxes"><input type="checkbox"></td>
                        <td hidden>${result.injectionResultId}</td>
                        <td class="text-start"><a class="link-offset-2 link-underline link-underline-opacity-0" onclick="showInjectionResultDetails('${result.injectionResultId}')">${result.customer.fullName}</a></td>
                        <td class="text-start">${result.vaccineFromInjectionResult.vaccineName}</td>
                        <td class="text-start">${result.vaccineFromInjectionResult.vaccineType.vaccineTypeName}</td>
                        <td>${result.numberOfInjection}</td>
                        <td>${result.injectionDate}</td>
                        <td>${result.nextInjectionDate}</td>
                    `;
                tableBody.appendChild(row);
            });
            updatePageInjectionResult(injectionResults.number, injectionResults.totalPages, pageSize, injectionResults.totalElements);
		},
		error: function(xhr) {
			alert("error at /api/injection-result/findBySearch, error code: " + xhr.status + ", " + xhr.statusText);
		}
	});
}

function updatePageInjectionResult(currentPage, totalPages, pageSize, totalElements) {
    document.getElementById("start-entry").innerHTML = currentPage === 0 ? 1 : currentPage * pageSize + 1;
    document.getElementById("end-entry").innerHTML = currentPage === totalPages - 1 ? totalElements : (currentPage + 1) * pageSize;
    document.getElementById("total-entries").innerHTML = totalElements;

    const paginationContainer = document.getElementById("page-buttons");
    let pageButtons = '';

    // Left button
    if (currentPage > 0) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllInjectionResults(${currentPage - 1}, ${pageSize})">&laquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&laquo;</span></li>`;
    }

    // Show all pages if totalPages < 10
    if (totalPages <= 10) {
        for (let i = 0; i < totalPages; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllInjectionResults(${i}, ${pageSize})">${i + 1}</a></li>`;
        }
    } else {
        // Always show page 1 and 2
        if (totalPages > 1) {
            pageButtons += `<li class="page-item ${currentPage === 0 ? 'active' : ''}"><a class="page-link" onclick="findAllInjectionResults(0, ${pageSize})">1</a></li>`;
            if (totalPages > 2) {
                pageButtons += `<li class="page-item ${currentPage === 1 ? 'active' : ''}"><a class="page-link" onclick="findAllInjectionResults(1, ${pageSize})">2</a></li>`;
            }
        }

        // Show page numbers around the current page with ellipses
        if (currentPage > 2) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        let startPage = Math.max(2, currentPage - 1);
        let endPage = Math.min(totalPages - 3, currentPage + 1);

        for (let i = startPage; i <= endPage; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllInjectionResults(${i}, ${pageSize})">${i + 1}</a></li>`;
        }

        if (currentPage < totalPages - 4) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        // Always show the last two pages
        if (totalPages > 2) {
            if (totalPages > 3) {
                pageButtons += `<li class="page-item ${currentPage === totalPages - 2 ? 'active' : ''}"><a class="page-link" onclick="findAllInjectionResults(${totalPages - 2}, ${pageSize})">${totalPages - 1}</a></li>`;
            }
            pageButtons += `<li class="page-item ${currentPage === totalPages - 1 ? 'active' : ''}"><a class="page-link" onclick="findAllInjectionResults(${totalPages - 1}, ${pageSize})">${totalPages}</a></li>`;
        }
    }

    // Right button
    if (currentPage < totalPages - 1) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllInjectionResults(${currentPage + 1}, ${pageSize})">&raquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&raquo;</span></li>`;
    }

    paginationContainer.innerHTML = `<ul class="pagination">${pageButtons}</ul>`;
    document.getElementById("dropdownMenuButton").innerHTML = pageSize;
}

function searchInjectionResults(page) {
    const query = document.getElementById('searchInput').value;
    const currentPageSize = parseInt(document.getElementById("dropdownMenuButton").innerHTML, 10);

    fetch(`/api/injection-result/findAll?searchInput=${encodeURIComponent(query)}&page=${page}&size=${currentPageSize}`)
        .then(response => response.json())
        .then(injectionResults => {
            const tableBody = document.getElementById('injection-result-list-content');
            tableBody.innerHTML = '';

            injectionResults.content.forEach(result => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="text-center check-boxes "><input type="checkbox"></td>
                    <td hidden>${result.injectionResultId}</td>
                    <td class="text-start"><a class="link-offset-2 link-underline link-underline-opacity-0" href="#">${result.customerInfo}</a></td>
                    <td class="text-start">${result.vaccineName}</td>
                    <td class="text-start">${result.vaccineTypeName}</td>
                    <td>${result.numberOfInjection}</td>
                    <td>${result.injectionDate}</td>
                    <td>${result.nextInjectionDate}</td>
                `;
                tableBody.appendChild(row);
            });
            updatePageInjectionResult(injectionResults.number, injectionResults.totalPages, currentPageSize, injectionResults.totalElements);
        })
        .catch(error => console.error('Error fetching injection results data:', error));
}

function loadInjectionPlace() {
    $.ajax({
        url: "/api/injection-result/places",
        success: function(places) {
            const placeSelect = document.getElementById('injectionplace');
            placeSelect.innerHTML = '<option value="">--Select Place--</option>'; // Clear previous options
            places.forEach(place => {
                const option = document.createElement('option');
                option.value = place;
                option.textContent = place;
                placeSelect.appendChild(option);
            });
        },
        error: function(xhr) {
            console.error("Error at /api/injection-result/places, error code: " + xhr.status);
        }
    });
}

function addInjectionResult() {
    const injectionResult = {
        customerId: $("#customer")[0].value,
        vaccineTypeId: $("#vaccineTypeName")[0].value,
        vaccineId: $("#vaccineName")[0].value,
        injection: $("#injection")[0].value,
        injectionDate: $("#injectiondate")[0].value,
        nextInjectionDate: $("#nextinjectiondate")[0].value,
        injectionPlace: $("#injectionplace")[0].value,
    };

	$.ajax({
		url: "/api/injection-result/add",
		method: "POST",
		contentType: "application/json",
		data: JSON.stringify(injectionResult),
		success: function(stringData) {
			alert(stringData);
			$('#add-injection-result-form')[0].reset();
		},
		error: function(xhr) {
			alert("error at /api/injection/result/add, error code: " + xhr.status);
		}
	});
}

function updateSelectedInjectionResult() {
    const checkbox = document.querySelectorAll('#injection-result-list-content input[type="checkbox"]:checked');

    if (checkbox.length !== 1) {
        alert("Please select only one injection result");
        return;
    }
    // Check if the selected row exists and retrieve the hidden ID
    const selectedRow = checkbox[0].closest('tr');
    const injectionResultId = selectedRow.querySelector('td[hidden]').textContent.trim(); // Hidden ID

    if (!injectionResultId) {
        console.error('Injection Result ID not found.');
        return;
    }
    console.log('Selected injectionResultId:', injectionResultId);
    fetchUpdateInjectionResult('injection-result-create.html', injectionResultId);  // Pass ID to the update handler
}

function updateInjectionResultDetail(injectionResultId) {
    fetch(`/api/injection-result/detail/` + injectionResultId)
        .then(response => response.json())
        .then(injectionResult => {
            // Populate form fields with the injection result data
            document.getElementById('injectionresultid').value = injectionResult.injectionResultId;
            document.getElementById('injection').value = injectionResult.injection;
            document.getElementById('injectiondate').value = injectionResult.injectionDate;
            document.getElementById('nextinjectiondate').value = injectionResult.nextInjectionDate;
            document.getElementById('injectionplace').value = injectionResult.injectionPlace;
        })
        .catch(error => console.error('Error fetching injection result data', error));
}

function showInjectionResultDetails(injectionResultId) {
    // Fetch injection result details by ID
    fetch(`/api/injection-result/detail/` + injectionResultId)
        .then(response => response.json())
        .then(injectionResult => {
            // Populate the modal fields with the injection result data
            document.getElementById('modalInjection').value = injectionResult.injection;
            document.getElementById('modalInjectionDate').value = injectionResult.injectionDate;
            document.getElementById('modalNextInjectionDate').value = injectionResult.nextInjectionDate;
            document.getElementById('modalInjectionPlace').value = injectionResult.injectionPlace;

            // Fetch customer details
            fetch(`/customer/detail/` + injectionResult.customerInfoId)
                .then(response => response.json())
                .then(customer => {
                    document.getElementById('modalCustomerInfo').value = `${customer.customerInfoId}-${customer.customerInfoFullName}-${customer.customerInfoDateOfBirth}`; // Adjust based on your customer fields
                })
                .catch(error => console.error('Error fetching customer details:', error));

            // Fetch vaccine details
            fetch(`/vaccine/detail/` + injectionResult.vaccineInfoId)
                .then(response => response.json())
                .then(vaccine => {
                    document.getElementById('modalVaccineName').value = vaccine.vaccineName;
                })
                .catch(error => console.error('Error fetching vaccine details:', error));

            // Fetch vaccine type details
            fetch(`/api/vaccine-type/detail/` + injectionResult.vaccineTypeInfoId)
                .then(response => response.json())
                .then(vaccineType => {
                    document.getElementById('modalVaccineTypeName').value = vaccineType.vaccineTypeName;
                })
                .catch(error => console.error('Error fetching vaccine type details:', error));

            // Show the modal
            var modal = new bootstrap.Modal(document.getElementById('injectionResultModal'));
            modal.show();
        })
        .catch(error => {
            console.error('Error fetching injection result details:', error);
            alert('Failed to load injection result details. Please try again.');
        });
}

function deleteSelectedInjectionResults() {
    // Get all checked checkboxes
    const checkboxes = document.querySelectorAll('#injection-result-list-content input[type="checkbox"]:checked');

    if (checkboxes.length === 0) {
        alert("Please select at least one injection result");
        return;
    }

    // Gather IDs of selected injection results
    const idsToDelete = Array.from(checkboxes).map(checkbox => {
        const row = checkbox.closest('tr');
        return row.querySelector('td[hidden]').textContent.trim(); // Hidden ID
    });

    if (idsToDelete.length === 0) {
        console.error('No IDs found for deletion.');
        return;
    }

    // Confirm deletion
    if (!confirm(`Are you sure you want to delete ${idsToDelete.length} injection result(s)?`)) {
        return;
    }

    // Send delete request
    fetch('/api/injection-result/delete', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(idsToDelete)
    })
        .then(response => response.json())
        .then(result => {
            if (result.code === 200) { // Assuming 200 indicates success
                alert('Deletion successful');
                fetchInjectionResult('injection-result-list.html'); // Reload the list
            } else {
                alert('Deletion failed: ' + result.description);
            }
        })
        .catch(error => console.error('Error deleting injection results:', error));
}

function resetInputInjectionresult() {
    // Get all the fields
    const injectionResultId = document.getElementById('injectionresultid');
    const injection = document.getElementById('injection');
    const injectionDate = document.getElementById('injectiondate');

    // Get all dropdown lists
    const customerDropdown = document.getElementById('customer');
    const vaccineTypeDropdown = document.getElementById('vaccineTypeName');
    const vaccineNameDropdown = document.getElementById('vaccineName');
    const placeOfInjectionDropdown = document.getElementById('injectionplace');

    // Reset only fields that are not disabled
    if (!injectionResultId.disabled) injectionResultId.value = '';
    if (!injection.disabled) injection.value = '';
    if (!injectionDate.disabled) injectionDate.value = '';

    // Reset dropdown lists to their default value (usually the first option or an empty option)
    if (!customerDropdown.disabled) customerDropdown.selectedIndex = 0; // Set to the first option
    if (!vaccineTypeDropdown.disabled) vaccineTypeDropdown.selectedIndex = 0; // Set to the first option
    if (!vaccineNameDropdown.disabled) vaccineNameDropdown.selectedIndex = 0; // Set to the first option
    if (!placeOfInjectionDropdown.disabled) placeOfInjectionDropdown.selectedIndex = 0; // Set to the first option
}









