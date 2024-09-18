function vaccineTypeUpdate() {
	vaccineTypeUpdateBtn = true;
}

function searchVaccineType(page) {
    const query = document.getElementById('searchInput').value;
    const currentPageSize = parseInt(document.getElementById("dropdownMenuButton").innerHTML, 10);
//    console.log(currentPageSize);

    fetch(`/api/vaccine-type/findAll?searchInput=${encodeURIComponent(query)}&page=${page}&size=${currentPageSize}`)
        .then(response => response.json())
        .then(vaccineTypes => {
            const tableBody = document.getElementById('vaccine-type-list');
            tableBody.innerHTML = '';
            vaccineTypes.content.forEach(vaccineType => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="text-center check-boxes"><input type="checkbox"></td>
                    <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0"
                    onclick="vaccineTypeUpdate(); fetchVaccineType('vaccine-type-create.html');">${vaccineType.vaccineTypeId}</a></td>
                    <td>${vaccineType.vaccineTypeName}</td>
                    <td>${vaccineType.vaccineTypeDescription}</td>
                    <td>${vaccineType.status}</td>
                `;
                tableBody.appendChild(row);
            });
            updateVaccineTypePaginationControls(vaccineTypes.number, vaccineTypes.totalPages, currentPageSize, vaccineTypes.totalElements);
        })
        .catch(error => console.error('Error fetching vaccineType data:', error));
}

function findAllVaccineType(page, pageSize) {
    const query = $("#searchInput")[0].value;

    $.ajax({
    	url: "/api/vaccine-type/findAll",
    	data: {
    		searchInput: query,
    		page: page,
    		size: pageSize
    	},
    	success: function(vaccineTypes) {
    		const tableBody = document.getElementById('vaccine-type-list');
            tableBody.innerHTML = '';
            vaccineTypes.content.forEach(vaccineType => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="text-center check-boxes"><input type="checkbox" ></td>
                    <td>
                    <a href="#" class="link-offset-2 link-underline link-underline-opacity-0"
                    onclick="fetchUpdateVaccineType('vaccine-type-create.html', '${vaccineType.vaccineTypeId}')" >${vaccineType.vaccineTypeId}</a>
                    </td>
                    <td>${vaccineType.vaccineTypeName}</td>
                    <td>${vaccineType.vaccineTypeDescription}</td>
                    <td>${vaccineType.vaccineTypeStatus}</td>
                `;
                tableBody.appendChild(row);
            });
            updateVaccineTypePaginationControls(vaccineTypes.number, vaccineTypes.totalPages, pageSize, vaccineTypes.totalElements);
    	},
    	error: function() {
    		alert("fail to fetch /api/vaccine-type/findAll");
    	}
    });
}

function updateVaccineTypePaginationControls(currentPage, totalPages, pageSize, totalElements) {
    document.getElementById("start-entry").innerHTML = currentPage === 0 ? 1 : currentPage * pageSize + 1;
    document.getElementById("end-entry").innerHTML = currentPage === totalPages - 1 ? totalElements : (currentPage + 1) * pageSize;
    document.getElementById("total-entries").innerHTML = totalElements;

    const paginationContainer = document.getElementById("page-buttons");
    let pageButtons = '';

    // Left button
    if (currentPage > 0) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllVaccineType(${currentPage - 1}, ${pageSize})">&laquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&laquo;</span></li>`;
    }

    // Show all pages if totalPages < 10
    if (totalPages <= 10) {
        for (let i = 0; i < totalPages; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineType(${i}, ${pageSize})">${i + 1}</a></li>`;
        }
    } else {
        // Always show page 1 and 2
        if (totalPages > 1) {
            pageButtons += `<li class="page-item ${currentPage === 0 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineType(0, ${pageSize})">1</a></li>`;
            if (totalPages > 2) {
                pageButtons += `<li class="page-item ${currentPage === 1 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineType(1, ${pageSize})">2</a></li>`;
            }
        }

        // Show page numbers around the current page with ellipses
        if (currentPage > 2) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        let startPage = Math.max(2, currentPage - 1);
        let endPage = Math.min(totalPages - 3, currentPage + 1);

        for (let i = startPage; i <= endPage; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineType(${i}, ${pageSize})">${i + 1}</a></li>`;
        }

        if (currentPage < totalPages - 4) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        // Always show the last two pages
        if (totalPages > 2) {
            if (totalPages > 3) {
                pageButtons += `<li class="page-item ${currentPage === totalPages - 2 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineType(${totalPages - 2}, ${pageSize})">${totalPages - 1}</a></li>`;
            }
            pageButtons += `<li class="page-item ${currentPage === totalPages - 1 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineType(${totalPages - 1}, ${pageSize})">${totalPages}</a></li>`;
        }
    }

    // Right button
    if (currentPage < totalPages - 1) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllVaccineType(${currentPage + 1}, ${pageSize})">&raquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&raquo;</span></li>`;
    }

    paginationContainer.innerHTML = `<ul class="pagination">${pageButtons}</ul>`;
    document.getElementById("dropdownMenuButton").innerHTML = pageSize;
}

// Call Ajax to load vaccine type list
function fetchVaccineType(filename) {
	$.ajax({
		url: "/api/vaccine-type/getAjax",
		data: {filename: filename},
		success: function(ajaxData) {
			document.getElementById('ajax-content').innerHTML = ajaxData;
                if(filename === "vaccine-type-list.html") {
                    findAllVaccineType(0,1);
                    document.getElementById("ajax-title").innerHTML="VACCINE TYPE LIST";
                }
                else {
                	document.getElementById("ajax-title").innerHTML="CREATE VACCINE TYPE";
                	if(vaccineTypeUpdateBtn == true) {
                		updateVaccineTypeDetail(vaccineTypeId);
                	}
                }
                vaccineTypeUpdateBtn = false;
		},
		error: function() {
			alert("error fetching ajax for vaccineType list");
		}
	});
}
// Add new vaccine type
function addVaccineType() {
    // const form = document.getElementById('add-vaccine-type');
    //
    // const formData = new FormData(form);

    const activeInput = document.querySelector('input[name="status"]:checked');

    const vaccineType = {

        vaccineTypeId : document.getElementById('vaccineTypeId').value,
        vaccineTypeName: document.getElementById('vaccineTypeName').value,
        vaccineTypeDescription: document.getElementById('vaccineTypeDescription').value,
        vaccineTypeStatus: activeInput ? "ACTIVE" : "INACTIVE",
        vaccineTypeImage : null
    };

    const imageFile = document.getElementById('image').files[0];

    if (imageFile) {
        const reader = new FileReader()
        reader.onloadend = function () {
            vaccineType.vaccineTypeImage = reader.result.split(',')[1].replace(/\s/g, '');
            sendVaccineTypeData(vaccineType);
        };
        reader.readAsDataURL(imageFile);
    } else {
        sendVaccineTypeData(vaccineType);
    }
    document.getElementById('add-vaccine-type').reset();
}

function sendVaccineTypeData(vaccineType) {
    const vaccineTypeId = document.getElementById('vaccineTypeId');
    $.ajax({
    	url: "/api/vaccine-type/add",
    	method: "POST",
    	contentType: "application/json",
    	data: JSON.stringify(vaccineType),
    	success: function(stringData) {
    		alert(stringData);
    	},
    	error: function(xhr) {
    		console.error("error at /api/vaccine-type/add: error code: " + xhr.status);
    	}
    });
}
// Make the vaccine type in-active in order to
function makeInactive() {
    const checkboxes = document.querySelectorAll('.check-boxes input[type="checkbox"]:checked');
    console.log(checkboxes.length)
    if(checkboxes.length === 0) {
        alert("No data to make inactive!");
        return;
    } else {
        let ids = [];
        const makeInactiveBody = {
            vaccineTypeListIds : ids
        };
        console.log(makeInactiveBody.vaccineTypeListIds);
        checkboxes.forEach(checkbox => {
            ids.push(checkbox.closest('tr').querySelector('td:nth-child(2) a').textContent);
        });
        console.log("AFTER PUSH: " + makeInactiveBody.vaccineTypeListIds);
        fetch('/api/vaccine-type/make-inactive', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(makeInactiveBody)
        })
            .then(response => response.text())
            .then(message => {
                findAllVaccineType(0,10);
                alert(message);
            })
            .catch(error => {
                console.error('JS: Error making inactive:', error);
                alert('JS: Failed to make inactive. Exception was thrown.');
            });
    }
}

function fetchUpdateVaccineType(filename, vaccineTypeId) {

    fetch(`/api/vaccine-type/getAjax?filename=${filename}`)
        .then(response => response.text())
        .then(data => {
            document.getElementById('ajax-content').innerHTML = data;
            updateVaccineTypeDetail(vaccineTypeId);
        })
        .catch(error => console.error('Error fetching document:', error));
}

function updateVaccineTypeDetail(vaccineTypeId) {
	$.ajax({
		url: "/api/vaccine-type/detail/" + vaccineTypeId,
		success: function(vaccineType) {
			$("#vaccineTypeId")[0].value = vaccineType.vaccineTypeId;
            $("#vaccineTypeId")[0].disabled = true;
            $("#vaccineTypeName")[0].value = vaccineType.vaccineTypeName;
            $("#vaccineTypeDescription")[0].value = vaccineType.vaccineTypeDescription;
            $("#vaccineTypeStatus")[0].disabled = false;
            const statusCheckbox = $("#vaccineTypeStatus")[0];
            // status = "ACTIVE" ? checked = true : checked = false
            vaccineType.vaccineTypeStatus === "ACTIVE" ? statusCheckbox.checked = true : statusCheckbox.checked = false ;
            // Image preview (optional)
            if (vaccineType.vaccineTypeImage) {
                $("#image-preview")[0].src = "/api/vaccine-type/image/" + vaccineType.vaccineTypeId;
                $("#image-preview")[0].style.display = 'block';
            } else {
                $('image-preview')[0].style.display = 'none';
            }
		},
		error: function(xhr) {
			console.error("error at /api/vaccine-type/detail/{id}: error code: " + xhr.status);
		}
	});

    fetch(`/api/vaccine-type/detail/` + vaccineTypeId)
        .then(response => response.json())
        .then(vaccineType => {
            document.getElementById('vaccineTypeId').value = vaccineType.data.vaccineTypeId;
            document.getElementById('vaccineTypeId').disabled = true;

            document.getElementById('vaccineTypeName').value = vaccineType.vaccineTypeName;

            document.getElementById('vaccineTypeDescription').value = vaccineType.vaccineTypeDescription;

            document.getElementById('status').disabled = false;

            console.log(vaccineType.vaccineTypeId);

            console.log(vaccineType.status);

            const statusCheckbox= document.getElementById('status')
            // status = "ACTIVE" ? checked = true : checked = false
            statusCheckbox.checked = vaccineType.status === "ACTIVE";
            // Image preview (optional)
            if (vaccineType.vaccineTypeImage) {
                document.getElementById('image-preview').src = `/api/vaccine-type/image/${vaccineType.vaccineTypeId}`;
                document.getElementById('image-preview').style.display = 'block';
            } else {
                document.getElementById('image-preview').style.display = 'none';
            }
        }).catch(error => {
        console.error('Error fetching employee data', error);
    });
}

function resetVaccineTypeInput() {

    const vaccineTypeId = document.getElementById('vaccineTypeId');
    const vaccineTypeName = document.getElementById('vaccineTypeName');
    const vaccineTypeDescription = document.getElementById('vaccineTypeDescription');

    if(!vaccineTypeId.disabled) vaccineTypeId.value ='';
    if(!vaccineTypeName.disabled) vaccineTypeName.value = '';
    if(!vaccineTypeDescription.disabled) vaccineTypeDescription.value = '';

    document.getElementById('image-preview').style.display = 'none';
    document.getElementById('image-preview').src = '/images/icons/image.png';
}

