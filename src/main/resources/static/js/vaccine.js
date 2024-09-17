//show list with pagination
function findAllVaccineWithPagination(page, pageSize){
    const searchInputElement = document.getElementById('searchInput');

    const query = searchInputElement ? searchInputElement.value : '';
//    console.log('Search input element:', searchInputElement);
    $.ajax({
        url: "/vaccine/search",
        data: {
          searchInput: query,
          page: page,
          size: pageSize
        },
        success: function(vaccines){
            const tableBody = document.getElementById('vaccine-list-content');
            console.log(tableBody);
            tableBody.innerHTML = '';

            vaccines.content.forEach(vaccine =>{
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="text-center check-boxes"><input type="checkbox" onchange="handleCheckboxChange()" class="check-select-box"></td>
                    <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0 text-uppercase">${vaccine.vaccineId}</a></td>
                    <td class="text-capitalize text-start">${vaccine.vaccineName}</td>
                    <td class="text-start">${vaccine.vaccineType.vaccineTypeName}</td>
                    <td class="text-start">${vaccine.numberOfInjection}</td>
                    <td class="text-start">${vaccine.vaccineOrigin}</td>
                    <td class="text-start">${vaccine.vaccineStatus}</td>
                `;
                tableBody.appendChild(row);
            });
            updatePaginationControls(vaccines.number, vaccines.totalPages, pageSize, vaccines.totalElements);
        },
        error: function(error) {
            console.error('Error fetching list of vaccines', error)
        }
    });
}
//---------------------
//search list with pagination
function search(page) {
    const query = document.getElementById('searchInput').value;
    const currentPageSize = parseInt(document.getElementById("dropdownMenuButton").innerHTML, 10);

    $.ajax({
        url: "/vaccine/search",
        data: {
          searchInput: query,
          page: page,
          size: currentPageSize
        },
        success: function(vaccines){
            const tableBody = document.getElementById('vaccine-list-content');
            tableBody.innerHTML = '';

            vaccines.content.forEach(vaccine =>{
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="text-center check-boxes"><input type="checkbox" onchange="handleCheckboxChange()" class="check-select-box"></td>
                    <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0 text-uppercase">${vaccine.vaccineId}</a></td>
                    <td class="text-capitalize text-start">${vaccine.vaccineName}</td>
                    <td class="text-start">${vaccine.vaccineType.vaccineTypeName}</td>
                    <td class="text-start">${vaccine.numberOfInjection}</td>
                    <td class="text-start">${vaccine.vaccineOrigin}</td>
                    <td class="text-start">${vaccine.vaccineStatus}</td>
                `;
                tableBody.appendChild(row);
            });
            updatePaginationControls(vaccines.number, vaccines.totalPages, currentPageSize, vaccines.totalElements);
        },
        error: function(error) {
            console.error('Error fetching list of vaccines', error)
        }
    });
}
//---------------------
//update navigation button and customize pagination
function updatePaginationControls(currentPage, totalPages, pageSize, totalElements) {
    document.getElementById("start-entry").innerHTML = currentPage === 0 ? 1 : currentPage * pageSize + 1;
    document.getElementById("end-entry").innerHTML = currentPage === totalPages - 1 ? totalElements : (currentPage + 1) * pageSize;
    document.getElementById("total-entries").innerHTML = totalElements;

    const paginationContainer = document.getElementById("page-buttons");
    let pageButtons = '';

    // Left button
    if (currentPage > 0) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllVaccineWithPagination(${currentPage - 1}, ${pageSize})">&laquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&laquo;</span></li>`;
    }

    // Show all pages if totalPages < 10
    if (totalPages <= 10) {
        for (let i = 0; i < totalPages; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineWithPagination(${i}, ${pageSize})">${i + 1}</a></li>`;
        }
    } else {
        // Always show page 1 and 2
        if (totalPages > 1) {
            pageButtons += `<li class="page-item ${currentPage === 0 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineWithPagination(0, ${pageSize})">1</a></li>`;
            if (totalPages > 2) {
                pageButtons += `<li class="page-item ${currentPage === 1 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineWithPagination(1, ${pageSize})">2</a></li>`;
            }
        }

        // Show page numbers around the current page with ellipses
        if (currentPage > 2) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        let startPage = Math.max(2, currentPage - 1);
        let endPage = Math.min(totalPages - 3, currentPage + 1);

        for (let i = startPage; i <= endPage; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineWithPagination(${i}, ${pageSize})">${i + 1}</a></li>`;
        }

        if (currentPage < totalPages - 4) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        // Always show the last two pages
        if (totalPages > 2) {
            if (totalPages > 3) {
                pageButtons += `<li class="page-item ${currentPage === totalPages - 2 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineWithPagination(${totalPages - 2}, ${pageSize})">${totalPages - 1}</a></li>`;
            }
            pageButtons += `<li class="page-item ${currentPage === totalPages - 1 ? 'active' : ''}"><a class="page-link" onclick="findAllVaccineWithPagination(${totalPages - 1}, ${pageSize})">${totalPages}</a></li>`;
        }
    }

    // Right button
    if (currentPage < totalPages - 1) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllVaccineWithPagination(${currentPage + 1}, ${pageSize})">&raquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&raquo;</span></li>`;
    }

    paginationContainer.innerHTML = `<ul class="pagination">${pageButtons}</ul>`;
    document.getElementById("dropdownMenuButton").innerHTML = pageSize;
}
//---------------------
//create new vaccine
function loadVaccineTypeName() {
    fetch('/vaccine-type/vt-for-add-vaccine')
        .then(response => response.json())
        .then(vaccineTypes => {
            const vaccineTypeSelect = document.getElementById('vaccineTypeName');
            console.log(vaccineTypeSelect);
            vaccineTypeSelect.innerHTML = '<option value="" disabled selected>--Select Vaccine Type--</option>';

            vaccineTypes.forEach(vaccineType => {
                const option = document.createElement('option');

                option.value = JSON.stringify({
                    vaccineTypeId: vaccineType.id,
                    vaccineTypeName: vaccineType.name,
                    vaccineTypeDescription: vaccineType.description || ''
                });
                option.text = vaccineType.name;
                vaccineTypeSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error loading vaccine types:', error));
}

//document.addEventListener('DOMContentLoaded', function() {
//    loadVaccineTypeName();
//});

function addVaccine(event) {
    event.preventDefault();

    const vaccineType = JSON.parse(document.getElementById('vaccineTypeName').value);
    const activeInput = document.querySelector('input[name = "vaccineStatus"]:checked');
    const data = {
        vaccineId: document.getElementById('vaccineId').value,
        vaccineName: document.getElementById('vaccineName').value,
        vaccineType: vaccineType,
        numberOfInjection: document.getElementById('numberOfInjection').value,
        vaccineUsage: document.getElementById('vaccineUsage').value,
        indication: document.getElementById('vaccineIndication').value,
        contraindication: document.getElementById('vaccineContraindication').value,
        timeBeginNextInjection: document.getElementById('beginning-time').value,
        timeEndNextInjection: document.getElementById('ending-time').value,
        vaccineOrigin: document.getElementById('vaccineOrigin').value,
        vaccineStatus: activeInput ? "ACTIVE" : "INACTIVE"
    };
    fetch('/vaccine/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            return response.text().then(text => {
                throw new Error(text || 'Failed to add vaccine.');
            });
        }
    })
    .then(result => {
        alert('Vaccine added successfully!');
        document.getElementById('add-vaccine-form').reset();
    })
    .catch(error => {
        console.error('Error adding vaccine:', error);
        alert('Failed to add vaccine. Please try again.');
    });
}
//---------------------
//handle checkbox for vaccineId
let selectedVaccineId = null;
function handleCheckboxChange() {
    const checkboxes = document.querySelectorAll('.check-select-box');
    const updateButton = document.querySelector('.update-button');

    const checkedBox = Array.from(checkboxes).find(checkbox => checkbox.checked);

    if(checkedBox){
        selectedVaccineId = checkedBox.closest('tr').querySelector('td:nth-child(2) a').textContent;
    }
}
//update vaccine
function updateSelectedVaccine(){
    const checkbox = document.querySelectorAll('#vaccine-list-content input[type="checkbox"]:checked');
    if(checkbox.length !== 1){
        alert("Please select only one vaccine");
        return;
    }
    const vaccineId = checkbox[0].closest('tr').querySelector('td:nth-child(2)').textContent;
    console.log(vaccineId);

    fetchUpdateVaccine('create-vaccine.html', vaccineId);
}
function updateVaccineDetail(vaccineId){
    fetch(`/vaccine/detail/` + vaccineId)
        .then(response => response.json())
        .then(vaccine => {
            document.getElementById('vaccineId').value = vaccine.vaccineId;
            document.getElementById('vaccineId').disabled = true;
            document.getElementById('vaccineName').value = vaccine.vaccineName;
            document.getElementById('vaccineTypeName').value = vaccine.vaccineTypeName;
            document.getElementById('numberOfInjection').value = vaccine.numberOfInjection;
            document.getElementById('vaccineUsage').value = vaccine.vaccineUsage;
            document.getElementById('vaccineIndication').value = vaccine.indication;
            document.getElementById('vaccineContraindication').value = vaccine.contraindication;
            document.getElementById('beginning-time').value = vaccine.timeBeginNextInjection;
            document.getElementById('ending-time').value = vaccine.timeEndNextInjection;
            document.getElementById('vaccineOrigin').value = vaccine.vaccineOrigin;
            document.getElementById('vaccineStatus').value = vaccine.vaccineStatus;
            loadVaccineTypeName();
        }).catch(error =>{
        console.error('Error fetching vaccine data', error);
        });
}
//change vaccine status
async function changeStatusSelectedVaccines(){
    const checkboxes = document.querySelectorAll('#vaccine-list-content input[type="checkbox"]:checked');
    if (checkboxes.length === 0) {
        alert("Please select at least one vaccine to make inactive");
        return;
    }

    const vaccineIds = Array.from(checkboxes).map(checkbox => {
        return checkbox.closest('tr').querySelector('td:nth-child(2)').textContent.trim();
    });
    console.log(vaccineIds);

    try{
        const response = await fetch('/vaccine/change-status', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(vaccineIds)
        });

        const result = await response.json();

        if(response.ok){
            alert(result.description);
            findAllVaccineWithPagination(0,10);
        } else {
            alert("Error change vaccine status: " + result.description)
        }
    } catch (error) {
        alert("An error occurred: " + error.message);
   }
}
//reset input of create/update page
function resetInput(){
    const vaccineId = document.getElementById('vaccineId');
    const vaccineName = document.getElementById('vaccineName');
    const vaccineTypeName = document.getElementById('vaccineTypeName');
    const numberOfInjection = document.getElementById('numberOfInjection');
    const vaccineUsage = document.getElementById('vaccineUsage');
    const vaccineIndication = document.getElementById('vaccineIndication');
    const vaccineContraindication = document.getElementById('vaccineContraindication');
    const timeBeginNextInjection = document.getElementById('beginning-time');
    const timeEndNextInjection = document.getElementById('ending-time');
    const vaccineOrigin = document.getElementById('vaccineOrigin');

    if(!vaccineId.disable) vaccineId.value = '';
    if(!vaccineName.disable) vaccineName.value = '';
    if(!vaccineTypeName.disable) vaccineTypeName.value = '';
    if(!numberOfInjection.disable) numberOfInjection.value = '';
    if(!vaccineIndication.disable) vaccineIndication.value = '';
    if(!vaccineContraindication.disable) vaccineContraindication.value = '';
    if(!timeBeginNextInjection.disable) timeBeginNextInjection.value = '';
    if(!timeEndNextInjection.disable) timeEndNextInjection.value = '';
    if(!vaccineOrigin.disable) vaccineOrigin.value = '';
    if(!vaccineUsage.disable) vaccineUsage.value = '';
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

    fetch('/vaccine/import/excel', {
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
