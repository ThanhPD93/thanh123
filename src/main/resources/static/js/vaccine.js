//from dashboard
function fetchVaccine(filename) {
	$.ajax({
		url: "/api/vaccine/getAjax",
		data: {filename: filename},
		success: function(ajaxData) {
			$('#ajax-content')[0].innerHTML = ajaxData;
                if (filename === "vaccine-list.html"){
                    $("#ajax-title")[0].innerHTML = "VACCINE LIST";
                    findAllVaccine(0);
                } else if(filename === "import-vaccine.html") {
                    $("#ajax-content")[0].innerHTML = data;
                    $("#ajax-title")[0].innerHTML = "IMPORT VACCINE";
                } else {
                    $("#ajax-title")[0].innerHTML = "CREATE VACCINE";
                    if(vaccineIdParam !== undefined) {
                        updateVaccineDetail(vaccineIdParam);
                        vaccineIdParam = undefined;
                    } else {
                        loadVaccineTypeName();
                    }
                }
		},
		error: function(xhr) {
			alert("error at /.../getAjax\nerror: " + xhr.status + "\nmessage: " + xhr.responseText);
		}
	});
}


//show list with pagination
function findAllVaccine(page){
    const searchInputElement = document.getElementById('searchInput');
	const pageSize = parseInt($("#dropdownMenuButton").text().trim(), 10);
    const query = searchInputElement ? searchInputElement.value : '';
//    console.log('Search input element:', searchInputElement);
    $.ajax({
        url: "/api/vaccine/search",
        data: {
          searchInput: query,
          page: page,
          size: pageSize
        },
        success: function(vaccines){
            const tableBody = document.getElementById('vaccine-list-content');
            tableBody.innerHTML = '';
            vaccines.content.forEach(vaccine =>{
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="text-center check-boxes"><input type="checkbox"></td>
                    <td><a class="link-offset-2 link-underline link-underline-opacity-0 text-uppercase" onclick="updateSelectedVaccine('${vaccine.vaccineId}')">${vaccine.vaccineId}</a></td>
                    <td class="text-capitalize text-start">${vaccine.vaccineName}</td>
                    <td class="text-start">${vaccine.vaccineType.vaccineTypeName}</td>
                    <td class="text-start">${vaccine.numberOfInjection}</td>
                    <td class="text-start">${vaccine.vaccineOrigin}</td>
                    <td class="text-start">${vaccine.vaccineStatus}</td>
                `;
                tableBody.appendChild(row);
            });
            updatePageVaccine(vaccines.number, vaccines.totalPages, pageSize, vaccines.totalElements);
        },
        error: function(xhr) {
            alert("error at /.../search\nerror code: " + xhr.status + "\nerror status: " + xhr.statusText + "\nerror message: " + xhr.responseText);
        }
    });
}
//---------------------
//update navigation button and customize pagination
function updatePageVaccine(currentPage, totalPages, pageSize, totalElements) {
    document.getElementById("start-entry").innerHTML = currentPage === 0 ? 1 : currentPage * pageSize + 1;
    document.getElementById("end-entry").innerHTML = currentPage === totalPages - 1 ? totalElements : (currentPage + 1) * pageSize;
    document.getElementById("total-entries").innerHTML = totalElements;

    const paginationContainer = document.getElementById("page-buttons");
    let pageButtons = '';

    // Left button
    if (currentPage > 0) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllVaccine(${currentPage - 1}, ${pageSize})">&laquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&laquo;</span></li>`;
    }

    // Show all pages if totalPages < 10
    if (totalPages <= 10) {
        for (let i = 0; i < totalPages; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllVaccine(${i}, ${pageSize})">${i + 1}</a></li>`;
        }
    } else {
        // Always show page 1 and 2
        if (totalPages > 1) {
            pageButtons += `<li class="page-item ${currentPage === 0 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccine(0, ${pageSize})">1</a></li>`;
            if (totalPages > 2) {
                pageButtons += `<li class="page-item ${currentPage === 1 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccine(1, ${pageSize})">2</a></li>`;
            }
        }

        // Show page numbers around the current page with ellipses
        if (currentPage > 2) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        let startPage = Math.max(2, currentPage - 1);
        let endPage = Math.min(totalPages - 3, currentPage + 1);

        for (let i = startPage; i <= endPage; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllVaccine(${i}, ${pageSize})">${i + 1}</a></li>`;
        }

        if (currentPage < totalPages - 4) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        // Always show the last two pages
        if (totalPages > 2) {
            if (totalPages > 3) {
                pageButtons += `<li class="page-item ${currentPage === totalPages - 2 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccine(${totalPages - 2}, ${pageSize})">${totalPages - 1}</a></li>`;
            }
            pageButtons += `<li class="page-item ${currentPage === totalPages - 1 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccine(${totalPages - 1}, ${pageSize})">${totalPages}</a></li>`;
        }
    }

    // Right button
    if (currentPage < totalPages - 1) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllVaccine(${currentPage + 1}, ${pageSize})">&raquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&raquo;</span></li>`;
    }

    paginationContainer.innerHTML = `<ul class="pagination">${pageButtons}</ul>`;
    document.getElementById("dropdownMenuButton").innerHTML = pageSize;
}
//---------------------
//create new vaccine
function loadVaccineTypeName(vaccineTypeNameParam) {
	$.ajax({
		url: "/api/vaccine-type/vt-for-add-ir",
		success: function(vaccineTypes) {
			$("#vaccineTypeName")[0].innerHTML = "";
			let row = "";
			if(vaccineTypeNameParam !== undefined) {
				vaccineTypes.forEach(vaccineType => {
                    if(vaccineType.vaccineTypeName === vaccineTypeNameParam) {
                        $("#vaccineTypeName")[0].innerHTML += `<option value="${vaccineType.vaccineTypeId}" selected>${vaccineType.vaccineTypeName}</option>`;
                    } else {
                        $("#vaccineTypeName")[0].innerHTML += `<option value="${vaccineType.vaccineTypeId}">${vaccineType.vaccineTypeName}</option>`;
                    }
				});
			} else {
                $("#vaccineTypeName")[0].innerHTML = '<option value="" disabled selected>--Select Vaccine Type--</option>';
                vaccineTypes.forEach(vaccineType => {
                    row += `<option value="${vaccineType.vaccineTypeId}">${vaccineType.vaccineTypeName}</option>`;
                });
                $("#vaccineTypeName").append(row);
			}
		},
		error: function(xhr) {
			const error = JSON.parse(xhr.responseText);
			alert("error at /api/vaccine-type/vt-for-add-vaccine, error message:" + error.message);
		}
	});
}

function addVaccine(event) {
    event.preventDefault();

    const activeInput = document.querySelector('input[name = "vaccineStatus"]:checked');
    const vaccine = {
        vaccineId: document.getElementById('vaccineId').value,
        contraindication: document.getElementById('vaccineContraindication').value,
        indication: document.getElementById('vaccineIndication').value,
        numberOfInjection: document.getElementById('numberOfInjection').value,
        vaccineOrigin: document.getElementById('vaccineOrigin').value,
        timeBeginNextInjection: document.getElementById('beginning-time').value,
        timeEndNextInjection: document.getElementById('ending-time').value,
        vaccineUsage: document.getElementById('vaccineUsage').value,
        vaccineName: document.getElementById('vaccineName').value,
        vaccineStatus: activeInput ? "ACTIVE" : "INACTIVE",
        vaccineType: document.getElementById('vaccineTypeName').value
    };

    $.ajax({
    	url: "/api/vaccine/add",
    	method: "POST",
    	contentType: "application/json",
    	data: JSON.stringify(vaccine),
    	success: function(stringData) {
    		alert(stringData);
    		document.getElementById('add-vaccine-form').reset();
    	},
    	error: function(xhr) {
    		console.error("error at /api/vaccine/add, error code: " + xhr.status);
    	}
    });
}
//---------------------
//update vaccine
function updateSelectedVaccine(vaccineId){
	if(vaccineId !== undefined) {
		vaccineIdParam = vaccineId;
		fetchVaccine("create-vaccine.html");
	} else {
        const checkbox = $('#vaccine-list-content input[type="checkbox"]:checked').toArray();
        if(checkbox.length !== 1){
            alert("Please select only one vaccine");
            return;
        }
        const vaccineIdCheckbox = checkbox[0].closest('tr').querySelector('td:nth-child(2)').textContent;
        vaccineIdParam = vaccineIdCheckbox;
        fetchVaccine('create-vaccine.html');
	}
}

function updateVaccineDetail(vaccineId){
	$.ajax({
		url: "/api/vaccine/detail/" + vaccineId,
		success: function (vaccine) {
			$('#vaccineId')[0].value = vaccine.vaccineId;
            $('#vaccineId')[0].disabled = true;
            $('#vaccineName')[0].value = vaccine.vaccineName;
            $('#numberOfInjection')[0].value = vaccine.numberOfInjection;
            $('#vaccineUsage')[0].value = vaccine.vaccineUsage;
            $('#vaccineIndication')[0].value = vaccine.indication;
            $('#vaccineContraindication')[0].value = vaccine.contraindication;
            $('#beginning-time')[0].value = vaccine.timeBeginNextInjection;
            $('#ending-time')[0].value = vaccine.timeEndNextInjection;
            $('#vaccineOrigin')[0].value = vaccine.vaccineOrigin;
            $('#vaccineStatus')[0].value = vaccine.vaccineStatus;
            loadVaccineTypeName(vaccine.vaccineType.vaccineTypeName);
		},
		error: function (xhr) {
			alert("error: " + xhr.status + "\nmessage: " + xhr.responseText);
		}
	});
}
//change vaccine status
function changeStatusSelectedVaccines(){
    const checkboxes = $('#vaccine-list-content input[type="checkbox"]:checked').toArray();
    if (checkboxes.length === 0) {
        alert("Please select at least one vaccine to make inactive");
        return;
    }
    const vaccineIds = Array.from(checkboxes).map(checkbox => {
        return checkbox.closest('tr').querySelector('td:nth-child(2)').textContent.trim();
    });

	$.ajax({
		url: "/api/vaccine/change-status",
		method: "PUT",
		contentType: "application/json",
		data: JSON.stringify(vaccineIds),
		success: function(stringData) {
			alert(stringData);
			findAllVaccine(0,10);
		},
		error: function(xhr) {
			alert("error: " + xhr.status + "\nmessage: " + xhr.responseText);
			console.error(xhr.responseText);
		}
	});
}
//reset input of create/update page
function resetVaccineInput(){
	if($("#vaccineId")[0].disabled === true) {
		const vaccineId = $("#vaccineId")[0].value;
		$("#add-vaccine-form")[0].reset();
		$("#vaccineId")[0].value = vaccineId;
	} else {
    	$("#add-vaccine-form")[0].reset();
	}
}

function resetImportVaccine() {
	$("#importForm")[0].reset();
}

//import file excel
function importExcelFile(event){
    event.preventDefault();

    const fileInput = document.getElementById('importVaccine');
    if (fileInput.files.length === 0) {
        alert("Please select a file to upload.");
        return;
    }

    const formData = new FormData();
    formData.append('file', fileInput.files[0]);

    fetch('/api/vaccine/import/excel', {
        method: 'POST',
        body: formData
    })
    .then(response => response.text())
    .then(data => {
        alert(data);
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to upload and import file.');
    });
}

//export file excel
function exportExcelFile(event){
    event.preventDefault();
    const apiUrl = 'http://localhost:8080/api/vaccine/export/excel';
    window.location.href = apiUrl;
}
