function searchVaccineType(page) {
    const query = document.getElementById('searchInput').value;
    const currentPageSize = parseInt(document.getElementById("dropdownMenuButton").innerHTML, 10);
//    console.log(currentPageSize);

    fetch(`/vaccine-type/findAllWithPagination?searchInput=${encodeURIComponent(query)}&page=${page}&size=${currentPageSize}`)
        .then(response => response.json())
        .then(vaccineTypes => {
            const tableBody = document.getElementById('vaccine-type-list');
            tableBody.innerHTML = '';

            vaccineTypes.content.forEach(vaccineType => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="text-center check-boxes"><input type="checkbox"></td>
                    <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0" onclick="updateSelectedVaccineType('${vaccineType.vaccineTypeId}')">${vaccineType.vaccineTypeId}</a></td>
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

function findAllVaccineTypeWithPagination(page, pageSize) {
    const searchInputElement = document.getElementById('searchInput');

    // Check if the element exists before accessing its value
    const query = searchInputElement ? searchInputElement.value : '';
    fetch(`/vaccine-type/findAllWithPagination?searchInput=${encodeURIComponent(query)}&page=${page}&size=${pageSize}`)
        .then(response => response.json())
        .then(vaccineTypes => {
            const tableBody = document.getElementById('vaccine-type-list');
            tableBody.innerHTML = '';
            // List<vaccineType> .forEach()
            // Page<vaccineType> .content() (trả về List<vaccineType>, .totalPages, .number, .totalElements
            vaccineTypes.content.forEach(vaccineType => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="text-center check-boxes"><input type="checkbox" ></td>
                    <td>
                    <a href="#" class="link-offset-2 link-underline link-underline-opacity-0" onclick="updateSelectedVaccineType('${vaccineType.vaccineTypeId}')" >${vaccineType.vaccineTypeId}</a>
                    </td>
                    <td>${vaccineType.vaccineTypeName}</td>
                    <td>${vaccineType.vaccineTypeDescription}</td>
                    <td>${vaccineType.status}</td>
                `;
                tableBody.appendChild(row);
            });
            updateVaccineTypePaginationControls(vaccineTypes.number, vaccineTypes.totalPages, pageSize, vaccineTypes.totalElements);
        })
        .catch(error => console.error('Error fetching list of vaccineTypes', error));
}

function updateVaccineTypePaginationControls(currentPage, totalPages, pageSize, totalElements) {
    document.getElementById("start-entry").innerHTML = currentPage === 0 ? 1 : currentPage * pageSize + 1;
    document.getElementById("end-entry").innerHTML = currentPage === totalPages - 1 ? totalElements : (currentPage + 1) * pageSize;
    document.getElementById("total-entries").innerHTML = totalElements;

    const paginationContainer = document.getElementById("page-buttons");
    let pageButtons = '';

    // Left button
    if (currentPage > 0) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllVaccineTypeWithPagination(${currentPage - 1}, ${pageSize})">&laquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&laquo;</span></li>`;
    }

    // Show all pages if totalPages < 10
    if (totalPages <= 10) {
        for (let i = 0; i < totalPages; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineTypeWithPagination(${i}, ${pageSize})">${i + 1}</a></li>`;
        }
    } else {
        // Always show page 1 and 2
        if (totalPages > 1) {
            pageButtons += `<li class="page-item ${currentPage === 0 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineTypeWithPagination(0, ${pageSize})">1</a></li>`;
            if (totalPages > 2) {
                pageButtons += `<li class="page-item ${currentPage === 1 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineTypeWithPagination(1, ${pageSize})">2</a></li>`;
            }
        }

        // Show page numbers around the current page with ellipses
        if (currentPage > 2) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        let startPage = Math.max(2, currentPage - 1);
        let endPage = Math.min(totalPages - 3, currentPage + 1);

        for (let i = startPage; i <= endPage; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineTypeWithPagination(${i}, ${pageSize})">${i + 1}</a></li>`;
        }

        if (currentPage < totalPages - 4) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        // Always show the last two pages
        if (totalPages > 2) {
            if (totalPages > 3) {
                pageButtons += `<li class="page-item ${currentPage === totalPages - 2 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineTypeWithPagination(${totalPages - 2}, ${pageSize})">${totalPages - 1}</a></li>`;
            }
            pageButtons += `<li class="page-item ${currentPage === totalPages - 1 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineTypeWithPagination(${totalPages - 1}, ${pageSize})">${totalPages}</a></li>`;
        }
    }

    // Right button
    if (currentPage < totalPages - 1) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllVaccineTypeWithPagination(${currentPage + 1}, ${pageSize})">&raquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&raquo;</span></li>`;
    }

    paginationContainer.innerHTML = `<ul class="pagination">${pageButtons}</ul>`;
    document.getElementById("dropdownMenuButton").innerHTML = pageSize;
}

// Call Ajax to load vaccine type list
function fetchVaccineType(filename) {
    fetch(`/vaccine-type/getAjax?filename=${filename}`)
        .then(response => response.text())
        .then(data => {
            document.getElementById('ajax-content').innerHTML = data;
            if(filename === "vaccine-type-list.html") {
                findAllVaccineTypeWithPagination(0,10);
                document.getElementById("title").innerHTML="VACCINE TYPE LIST";
            }else document.getElementById("title").innerHTML="CREATE VACCINE TYPE";
        })
        .catch(error => console.error('Error fetching document:', error));
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
        status: activeInput ? "ACTIVE" : "INACTIVE",
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
}

function sendVaccineTypeData(vaccineType) {
    const vaccineTypeId = document.getElementById('vaccineTypeId');
    fetch('/vaccine-type/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(vaccineType)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to add vaccine type');
            }
            return response.json();
        })
        .then(data => {
            if(!vaccineTypeId.disabled) {
                alert('Vaccine type added successfully!');
            } else {

                alert('Vaccine type update successfully!');
            }
            // document.getElementById('add-vaccine-type').reset();
            // document.getElementById('image-preview').style.display = 'none';
        })
        .catch(error => {
            console.error('Error adding vaccine type', error);
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
        fetch('/vaccine-type/make-inactive', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(makeInactiveBody)
        })
            .then(response => response.text())
            .then(message => {
                findAllVaccineTypeWithPagination(0,10);
                alert(message);
            })
            .catch(error => {
                console.error('JS: Error making inactive:', error);
                alert('JS: Failed to make inactive. Exception was thrown.');
            });
    }
}

function updateSelectedVaccineType(vaccineTypeId) {
    console.log(vaccineTypeId);
    // Fetch employee data using selectedEmployeeId
    fetchUpdateVaccineType('vaccine-type-create.html', vaccineTypeId);
}

function fetchUpdateVaccineType(filename, vaccineTypeId) {

    fetch(`/vaccine-type/getAjax?filename=${filename}`)
        .then(response => response.text())
        .then(data => {
            document.getElementById('ajax-content').innerHTML = data;
            updateVaccineTypeDetail(vaccineTypeId);
        })
        .catch(error => console.error('Error fetching document:', error));
}

function updateVaccineTypeDetail(vaccineTypeId) {
    fetch(`/vaccine-type/detail/` + vaccineTypeId)
        .then(response => response.json())
        .then(vaccineType => {
            // Now that the form is loaded, populate the form with employee data
            document.getElementById('vaccineTypeId').value = vaccineType.vaccineTypeId;
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
                document.getElementById('image-preview').src = `/vaccine-type/image/${vaccineType.vaccineTypeId}`;
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

