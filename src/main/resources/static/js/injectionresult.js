//show list with pagination
function findAllInjectionResultsWithPagination(page, pageSize) {
    const searchInputElement = document.getElementById('searchInput');
    const query = searchInputElement ? searchInputElement.value : '';

    fetch(`/injection-result/findAllWithPagination?searchInput=${encodeURIComponent(query)}&page=${page}&size=${pageSize}`)
        .then(response => response.json())
        .then(injectionResults => {
            const tableBody = document.getElementById('injection-result-list-content');
            tableBody.innerHTML = '';

            injectionResults.content.forEach(result => {
                const row = document.createElement('tr');
                row.innerHTML = `
                        <td class="text-center check-boxes"><input type="checkbox"></td>
                        <td class="text-start"><a class="link-offset-2 link-underline link-underline-opacity-0" href="#">${result.customerInfo}</a></td>
                        <td class="text-start">${result.vaccineName}</td>
                        <td class="text-start">${result.vaccineTypeName}</td>
                        <td>${result.numberOfInjection}</td>
                        <td>${result.injectionDate}</td>
                        <td>${result.nextInjectionDate}</td>
                    `;
                tableBody.appendChild(row);
            });
            updatePaginationControlsIR(injectionResults.number, injectionResults.totalPages, pageSize, injectionResults.totalElements);
        })
        .catch(error => console.error('Error fetching injection results', error));
}

//-handle paging
function updatePaginationControlsIR(currentPage, totalPages, pageSize, totalElements) {
    document.getElementById("start-entry").innerHTML = currentPage === 0 ? 1 : currentPage * pageSize + 1;
    document.getElementById("end-entry").innerHTML = currentPage === totalPages - 1 ? totalElements : (currentPage + 1) * pageSize;
    document.getElementById("total-entries").innerHTML = totalElements;

    const paginationContainer = document.getElementById("page-buttons");
    let pageButtons = '';

    // Left button
    if (currentPage > 0) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllInjectionResultsWithPagination(${currentPage - 1}, ${pageSize})">&laquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&laquo;</span></li>`;
    }

    // Show all pages if totalPages < 10
    if (totalPages <= 10) {
        for (let i = 0; i < totalPages; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllInjectionResultsWithPagination(${i}, ${pageSize})">${i + 1}</a></li>`;
        }
    } else {
        // Always show page 1 and 2
        if (totalPages > 1) {
            pageButtons += `<li class="page-item ${currentPage === 0 ? 'active' : ''}"><a class="page-link" onclick="findAllInjectionResultsWithPagination(0, ${pageSize})">1</a></li>`;
            if (totalPages > 2) {
                pageButtons += `<li class="page-item ${currentPage === 1 ? 'active' : ''}"><a class="page-link" onclick="findAllInjectionResultsWithPagination(1, ${pageSize})">2</a></li>`;
            }
        }

        // Show page numbers around the current page with ellipses
        if (currentPage > 2) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        let startPage = Math.max(2, currentPage - 1);
        let endPage = Math.min(totalPages - 3, currentPage + 1);

        for (let i = startPage; i <= endPage; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllInjectionResultsWithPagination(${i}, ${pageSize})">${i + 1}</a></li>`;
        }

        if (currentPage < totalPages - 4) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        // Always show the last two pages
        if (totalPages > 2) {
            if (totalPages > 3) {
                pageButtons += `<li class="page-item ${currentPage === totalPages - 2 ? 'active' : ''}"><a class="page-link" onclick="findAllInjectionResultsWithPagination(${totalPages - 2}, ${pageSize})">${totalPages - 1}</a></li>`;
            }
            pageButtons += `<li class="page-item ${currentPage === totalPages - 1 ? 'active' : ''}"><a class="page-link" onclick="findAllInjectionResultsWithPagination(${totalPages - 1}, ${pageSize})">${totalPages}</a></li>`;
        }
    }

    // Right button
    if (currentPage < totalPages - 1) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllInjectionResultsWithPagination(${currentPage + 1}, ${pageSize})">&raquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&raquo;</span></li>`;
    }

    paginationContainer.innerHTML = `<ul class="pagination">${pageButtons}</ul>`;
    document.getElementById("dropdownMenuButton").innerHTML = pageSize;
}

//--search

function searchInjectionResults(page) {
    const query = document.getElementById('searchInput').value;
    const currentPageSize = parseInt(document.getElementById("dropdownMenuButton").innerHTML, 10);

    fetch(`/injection-result/findAllWithPagination?searchInput=${encodeURIComponent(query)}&page=${page}&size=${currentPageSize}`)
        .then(response => response.json())
        .then(injectionResults => {
            const tableBody = document.getElementById('injection-result-list-content');
            tableBody.innerHTML = '';

            injectionResults.content.forEach(result => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="text-center check-boxes "><input type="checkbox"></td>
                    <td class="text-start"><a class="link-offset-2 link-underline link-underline-opacity-0" href="#">${result.customerInfo}</a></td>
                    <td class="text-start">${result.vaccineName}</td>
                    <td class="text-start">${result.prevention}</td>
                    <td>${result.numberOfInjection}</td>
                    <td>${result.injectionDate}</td>
                    <td>${result.nextInjectionDate}</td>
                `;
                tableBody.appendChild(row);
            });
            updatePaginationControlsIR(injectionResults.number, injectionResults.totalPages, currentPageSize, injectionResults.totalElements);
        })
        .catch(error => console.error('Error fetching injection results data:', error));
}

//check all
// function checkAllInjectionResultBoxes() {
//     const selectAllCheckbox = document.getElementById('mother-checkbox');
//     const checkboxes = document.querySelectorAll('.check-boxes input[type="checkbox"]');
//     checkboxes.forEach(function (checkbox) {
//         checkbox.checked = selectAllCheckbox.checked;
//     });
// }


// Function to load customers
function loadCustomers() {
    fetch('/customer/c-for-add-ir')
        .then(response => response.json())
        .then(customers => {
            const customerSelect = document.getElementById('customer');
            customerSelect.innerHTML = '<option selected>--Select Customer--</option>';

            customers.forEach(customer => {
                const option = document.createElement('option');
                option.value = customer.id;
                option.text = `${customer.id} - ${customer.name} - ${customer.dateOfBirth}`;
                customerSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error loading customers:', error));
}

// Function to load vaccine types
function loadVaccines() {
    fetch('/vaccine/v-for-add-ir')
        .then(response => response.json())
        .then(vaccines => {
            const vaccineSelect = document.getElementById('vaccinename');
            vaccineSelect.innerHTML = '<option selected>--Select Vaccine--</option>';

            vaccines.forEach(vaccine => {
                const option = document.createElement('option');
                option.value = vaccine.id;
                option.text = vaccine.name;
                vaccineSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error loading vaccines:', error));
}
//load prevention from file
function loadVaccineTypeName() {
    fetch('/vaccine-type/vt-for-add-ir')
        .then(response => response.json())
        .then(vaccineType => {
            const vaccineTypeSelect = document.getElementById('vaccinetypename');
            vaccineTypeSelect.innerHTML = '<option selected>--Select Vaccine Type Name--</option>';

            vaccineType.forEach(vaccineType => {
                const option = document.createElement('option');
                option.value = vaccineType.id;
                option.text = vaccineType.name;
                vaccineTypeSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error loading vaccines:', error));
}
//load injection place from file
function loadInjectionPlace() {
    fetch('/injection-result/places')
        .then(response => response.json())
        .then(places => {
            const placeSelect = document.getElementById('injectionplace');
            placeSelect.innerHTML = '<option value="">--Select Place--</option>'; // Clear previous options
            places.forEach(place => {
                const option = document.createElement('option');
                option.value = place;
                option.textContent = place;
                placeSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error loading places:', error));
}

// Call all the functions to load data when the page loads
document.addEventListener('DOMContentLoaded', function() {
    loadCustomers();
    loadVaccineTypeName()
    loadVaccines();
    loadInjectionPlace();
});


//add
function addInjectionResult(event) {
    event.preventDefault(); // Prevent the default form submission

    // Get form values
    const customerId = document.getElementById('customer').value;
    const vaccineTypeName = document.getElementById('vaccinetypename').value;
    const vaccineName = document.getElementById('vaccinename').value;
    const injection = document.getElementById('injection').value;
    const injectionDate = document.getElementById('injectiondate').value;
    const nextInjectionDate = document.getElementById('nextinjectiondate').value;
    const injectionPlace = document.getElementById('injectionplace').value;

    // Create the data object
    const data = {
        customerId: customerId,
        vaccineTypeName: vaccineTypeName,
        vaccineName: vaccineName,
        injection: injection,
        injectionDate: injectionDate,
        nextInjectionDate: nextInjectionDate,
        injectionPlace: injectionPlace
    };

    // Send data to the server
    fetch('/injection-result/add', {
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
                    throw new Error(text || 'Failed to add injection result.');
                });
            }
        })
        .then(result => {
            // Handle success (e.g., show a success message or redirect)
            alert('Injection result added successfully!');
            document.getElementById('add-injection-result-form').reset();
            // Optionally redirect or update the page
        })
        .catch(error => {
            // Handle error
            console.error('Error adding injection result:', error);
            alert('Failed to add injection result. Please try again.');
        });
}
