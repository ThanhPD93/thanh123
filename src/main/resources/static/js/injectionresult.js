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
                    <td class="text-start">${result.vaccineTypeName}</td>
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

// Load vaccine types and initially hide the vaccine names dropdown
function loadVaccineTypeName() {
    fetch('/vaccine-type/vt-for-add-ir')
        .then(response => response.json())
        .then(vaccineTypes => {
            const vaccineTypeSelect = document.getElementById('vaccinetypename');
            vaccineTypeSelect.innerHTML = '<option selected>--Select Vaccine Type Name--</option>';

            vaccineTypes.forEach(vaccineType => {
                const option = document.createElement('option');
                option.value = vaccineType.id;
                option.text = vaccineType.name;
                vaccineTypeSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error loading vaccine types:', error));
}

// Load vaccines based on selected vaccine type
function loadVaccines(vaccineTypeId) {
    fetch(`/vaccine/v-for-add-ir?vaccineTypeId=${vaccineTypeId}`)
        .then(response => response.json())
        .then(vaccines => {
            const vaccineSelect = document.getElementById('vaccinename');
            vaccineSelect.innerHTML = '<option selected>--Select Vaccine--</option>';
            vaccineSelect.style.display = 'block'; // Show the vaccine name dropdown

            vaccines.forEach(vaccine => {
                const option = document.createElement('option');
                option.value = vaccine.id;
                option.text = vaccine.name;
                vaccineSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error loading vaccines:', error));
}

// Event listener for vaccine type dropdown change
document.getElementById('vaccinetypename').addEventListener('change', function() {
    const selectedVaccineTypeId = this.value;
    const vaccineSelect = document.getElementById('vaccinename');

    // Clear previous vaccine names and hide dropdown if no vaccine type is selected
    vaccineSelect.innerHTML = '<option selected>--Select Vaccine--</option>';
    vaccineSelect.style.display = 'none';

    if (selectedVaccineTypeId) {
        loadVaccines(selectedVaccineTypeId);
    }
});

// Initial load of vaccine types on page load
document.addEventListener('DOMContentLoaded', function() {
    loadVaccineTypeName();
});

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

// // Call all the functions to load data when the page loads
document.addEventListener('DOMContentLoaded', function() {
    loadCustomers();
    loadVaccineTypeName()
    loadVaccines();
    loadInjectionPlace();
});


//add
function addInjectionResult(event) {
    event.preventDefault(); // Prevent default form submission

    // Get form values
    const customerId = document.getElementById('customer').value.split(' - ')[0];  // Extract customerId only
    const vaccineTypeName = document.getElementById('vaccinetypename').value;
    const vaccineName = document.getElementById('vaccinename').value;
    const injection = document.getElementById('injection').value;
    const injectionDate = document.getElementById('injectiondate').value;
    const nextInjectionDate = document.getElementById('nextinjectiondate').value;
    const injectionPlace = document.getElementById('injectionplace').value;

    // Create the data object
    const data = {
        customerId: customerId, // Send only the customerId to the server
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
            alert('Injection result added successfully!');
            document.getElementById('add-injection-result-form').reset();
        })
        .catch(error => {
            console.error('Error adding injection result:', error);
            alert('Failed to add injection result. Please try again.');
        });
}

//---------------------------------

// function populateFormWithInjectionResult(data) {
//     document.getElementById('customer').value = data.customerId;
//     document.getElementById('vaccinetypename').value = data.vaccineTypeName;
//     document.getElementById('vaccinename').value = data.vaccineName;
//     document.getElementById('injection').value = data.injection;
//     document.getElementById('injectiondate').value = data.injectionDate;
//     document.getElementById('nextinjectiondate').value = data.nextInjectionDate;
//     document.getElementById('injectionplace').value = data.injectionPlace;
// }
//
// // Modify this function to handle both adding and updating
// function addInjectionResult(event) {
//     event.preventDefault(); // Prevent default form submission
//
//     const form = document.getElementById('add-injection-result-form');
//     const data = {
//         customerId: form.customer.value,
//         vaccineTypeName: form.vaccinetypename.value,
//         vaccineName: form.vaccinename.value,
//         injection: form.injection.value,
//         injectionDate: form.injectiondate.value,
//         nextInjectionDate: form.nextinjectiondate.value,
//         injectionPlace: form.injectionplace.value
//     };
//
//     // Determine whether we are adding or updating
//     if (form.dataset.updateId) {
//         // Update logic
//         fetch(`/injection-result/update/${form.dataset.updateId}`, {
//             method: 'PUT',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify(data)
//         })
//             .then(response => response.json())
//             .then(updatedResult => {
//                 alert('Injection Result updated successfully');
//                 // Refresh the list or clear the form
//                 resetForm();
//             })
//             .catch(error => console.error('Error updating InjectionResult:', error));
//     } else {
//         // Add logic
//         fetch('/injection-result/add', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify(data)
//         })
//             .then(response => response.json())
//             .then(result => {
//                 alert('Injection Result added successfully');
//                 // Refresh the list or clear the form
//                 resetForm();
//             })
//             .catch(error => console.error('Error adding InjectionResult:', error));
//     }
// }
//
// function resetForm() {
//     document.getElementById('add-injection-result-form').reset();
//     document.getElementById('add-injection-result-form').removeAttribute('data-update-id'); // Clear the update ID
//     // Hide the form or reset other states if necessary
// }
//
// //-----------
// function populateFormWithInjectionResult(data) {
//     document.getElementById('customer').value = data.customerId;
//     document.getElementById('vaccinetypename').value = data.vaccineTypeName;
//
//     // Trigger change event to load vaccines based on selected vaccine type
//     document.getElementById('vaccinetypename').dispatchEvent(new Event('change'));
//
//     // Wait for vaccines to be loaded, then set vaccine name
//     setTimeout(() => {
//         document.getElementById('vaccinename').value = data.vaccineName;
//     }, 500); // Adjust timeout as necessary for data loading
//     document.getElementById('injection').value = data.injection;
//     document.getElementById('injectiondate').value = data.injectionDate;
//     document.getElementById('nextinjectiondate').value = data.nextInjectionDate;
//     document.getElementById('injectionplace').value = data.injectionPlace;
//
//     // Set form mode to update
//     const form = document.getElementById('add-injection-result-form');
//     form.dataset.updateId = data.id; // Store the ID for the update
// }
//
// function updateSelectedInjectionResult() {
//     const checkboxes = document.querySelectorAll('#injection-result-list input[type="checkbox"]:checked');
//
//     if (checkboxes.length !== 1) {
//         alert("Please select exactly one Injection Result.");
//         return;
//     }
//
//     const selectedId = checkboxes[0].closest('tr').dataset.id; // Assumes each row has a data-id attribute
//
//     fetch(`/injection-result/detail/${selectedId}`)
//         .then(response => response.json())
//         .then(data => {
//             populateFormWithInjectionResult(data);
//         })
//         .catch(error => console.error('Error fetching InjectionResult details:', error));
// }
//
//
// function saveInjectionResult(event) {
//     event.preventDefault(); // Prevent default form submission
//
//     const form = document.getElementById('add-injection-result-form');
//     const data = {
//         customerId: form.customer.value,
//         vaccineTypeName: form.vaccinetypename.value,
//         vaccineName: form.vaccinename.value,
//         injection: form.injection.value,
//         injectionDate: form.injectiondate.value,
//         nextInjectionDate: form.nextinjectiondate.value,
//         injectionPlace: form.injectionplace.value
//     };
//
//     const updateId = form.dataset.updateId;
//
//     fetch(`/injection-result/update/${updateId}`, {
//         method: 'PUT',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(data)
//     })
//         .then(response => response.json())
//         .then(updatedResult => {
//             alert('Injection Result updated successfully');
//             // Optionally refresh the list or clear the form
//             resetForm();
//         })
//         .catch(error => console.error('Error updating InjectionResult:', error));
// }
//
// function resetForm() {
//     document.getElementById('add-injection-result-form').reset();
//     document.getElementById('add-injection-result-form').removeAttribute('data-update-id'); // Clear the update ID
//     // Hide the form or reset other states if necessary
// }


