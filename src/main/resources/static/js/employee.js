function checkAllBoxes() {
    const selectAllCheckbox = document.getElementById('mother-checkbox');
    const checkboxes = document.querySelectorAll('.check-boxes input[type="checkbox"]');
    checkboxes.forEach(function (checkbox) {
        checkbox.checked = selectAllCheckbox.checked;
    });
}

function search(page) {
    const query = document.getElementById('searchInput').value;
    const currentPageSize = parseInt(document.getElementById("dropdownMenuButton").innerHTML, 10);
//    console.log(currentPageSize);

    fetch(`/employee/findAllWithPagination?searchInput=${encodeURIComponent(query)}&page=${page}&size=${currentPageSize}`)
        .then(response => response.json())
        .then(employees => {
            const tableBody = document.getElementById('employee-list-content');
            tableBody.innerHTML = '';

            employees.content.forEach(employee => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="text-center check-boxes"><input type="checkbox"></td>
                    <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0" onclick="showEmployeeDetails('${employee.employeeId}')">${employee.employeeId}</a></td>
                    <td>${employee.employeeName}</td>
                    <td>${employee.dateOfBirth}</td>
                    <td>${employee.gender}</td>
                    <td>${employee.phone}</td>
                    <td>${employee.address}</td>
                    <td class="text-center"><img src="/employee/image/${employee.employeeId}" alt="image" style="height: 30px; width: 45px"></td>
                `;
                tableBody.appendChild(row);
            });
            updatePaginationControls(employees.number, employees.totalPages, currentPageSize, employees.totalElements);
        })
        .catch(error => console.error('Error fetching employee data:', error));
}

//---------------------------
function findAllEmployeeWithPagination(page, pageSize) {
    const searchInputElement = document.getElementById('searchInput');

    // Check if the element exists before accessing its value
    const query = searchInputElement ? searchInputElement.value : '';
    console.log('Search input element:', searchInputElement);
    fetch(`/employee/findAllWithPagination?searchInput=${encodeURIComponent(query)}&page=${page}&size=${pageSize}`)
        .then(response => response.json())
        .then(employees => {
            const tableBody = document.getElementById('employee-list-content');
            tableBody.innerHTML = '';

            // List<Employee> .forEach()
            // Page<Employee> .content() (trả về List<Employee>, .totalPages, .number, .totalElements
            employees.content.forEach(employee => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="text-center check-boxes"><input type="checkbox" onchange="handleCheckboxChange()" class="check-select-box"></td>
                    <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0 text-uppercase" onclick="showEmployeeDetails('${employee.employeeId}')">${employee.employeeId}</a></td>
                    <td class="text-capitalize text-start">${employee.employeeName}</td>
                    <td class="text-start">${employee.dateOfBirth}</td>
                    <td class="text-start">${employee.gender}</td>
                    <td class="text-start">${employee.phone}</td>
                    <td class="text-capitalize text-start">${employee.address}</td>
                    <td class="text-center"><img src="/employee/image/${employee.employeeId}" alt="image" style="height: 30px; width: 45px"></td>
                `;
                tableBody.appendChild(row);
            });
            updatePaginationControls(employees.number, employees.totalPages, pageSize, employees.totalElements);
        })
        .catch(error => console.error('Error fetching list of employees', error));
}
//---------------------------------

let selectedEmployeeId = null;
function handleCheckboxChange() {
    const checkboxes = document.querySelectorAll('.check-select-box');
    const updateButton = document.querySelector('.update-button');

    const checkedBox = Array.from(checkboxes).find(checkbox => checkbox.checked);

    if (checkedBox) {
        selectedEmployeeId = checkedBox.closest('tr').querySelector('td:nth-child(2) a').textContent;
    }
}

//--update
function updateSelectedEmployee() {
    const checkbox = document.querySelectorAll('#employee-list-content input[type="checkbox"]:checked')
    if (checkbox.length !== 1) {
        alert("Please select only one employee");
        return;
    }
    const employeeId = checkbox[0].closest('tr').querySelector('td:nth-child(2)').textContent;
    console.log(employeeId);
    // Fetch employee data using selectedEmployeeId
    fetchUpdateEmployee('employee-create.html', employeeId);
}

//------delete
// async function deleteSelectedEmployee() {
//     const checkboxes = document.querySelectorAll('#employee-list-content input[type="checkbox"]:checked')
//     if(checkboxes.length == 0) {
//         alert("Please select at least one employee to delete");
//         return;
//     }
//     const employeeIds = Array.from(checkboxes).map(checkbox => {
//         return checkbox.closest('tr').querySelector('td:nth-child(2)').textContent.trim();
//     });
//     console.log(employeeIds);
//     const confirmed = confirm("Are you sure you want to delete the selected employees?");
//     if (!confirmed) {
//         return;
//     }
//     try {
//         // Gửi request DELETE đến backend
//         const response = await fetch('/employee/delete', {
//             method: 'DELETE', // Hoặc 'DELETE' nếu backend yêu cầu
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify(employeeIds)
//         });
//
//         const result = await response.json();
//
//         if (response.ok) {
//             // Xử lý kết quả nếu thành công
//             alert("Employees deleted successfully!");
//             // Cập nhật danh sách nhân viên trên giao diện người dùng nếu cần
//             location.reload(); // Tải lại trang để cập nhật
//         } else {
//             // Xử lý lỗi từ server
//             alert("Error deleting employees: " + result.message);
//         }
//     } catch (error) {
//         // Xử lý lỗi khi gửi request
//         alert("An error occurred: " + error.message);
//     }
// }

async function deleteSelectedEmployee() {
    const checkboxes = document.querySelectorAll('#employee-list-content input[type="checkbox"]:checked');
    if (checkboxes.length === 0) {
        alert("Please select at least one employee to delete");
        return;
    }

    const employeeIds = Array.from(checkboxes).map(checkbox => {
        return checkbox.closest('tr').querySelector('td:nth-child(2)').textContent.trim();
    });
    console.log(employeeIds);

    const confirmed = confirm("Are you sure you want to delete the selected employees?");
    if (!confirmed) {
        return;
    }

    try {
        const response = await fetch('/employee/delete', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(employeeIds)
        });

        const result = await response.json();

        if (response.ok) {
            alert(result.description);
            location.reload();
        } else {
            alert("Error deleting employees: " + result.description);
        }
    } catch (error) {
        alert("An error occurred: " + error.message);
    }
}


//---update
function updateEmployeeDetail(employeeId) {
    fetch(`/employee/detail/` + employeeId)
        .then(response => response.json())
        .then(employee => {
            // Now that the form is loaded, populate the form with employee data
            document.getElementById('employeeId').value = employee.employeeId;
            document.getElementById('employeeId').disabled = true;
            // document.getElementById('employeeId').readOnly = true;
            document.getElementById('employeeName').value = employee.employeeName;
            document.getElementById('dateOfBirth').value = employee.dateOfBirth;
            document.getElementById('phone').value = employee.phone;
            document.getElementById('address').value = employee.address;
            document.getElementById('email').value = employee.email;
            document.getElementById('workingPlace').value = employee.workingPlace;
            document.getElementById('position').value = employee.position;
            document.getElementById('username').value = employee.username;
            document.getElementById('username').disabled = true;
            // document.getElementById('username').readOnly = true;
            document.getElementById('password').value = employee.password;
            document.getElementById('password').disabled = true;
            // document.getElementById('password').readOnly = true;
            document.getElementById('toggle-button').hidden = true; //hide toggle button
            // Set gender
            if (employee.gender === 'MALE') {
                document.getElementById('gender-male').checked = true;
            } else {
                document.getElementById('gender-female').checked = true;
            }

            // Image preview (optional)
            if (employee.image) {
                document.getElementById('image-preview').src = `/employee/image/${employee.employeeId}`;
                document.getElementById('image-preview').style.display = 'block';
            } else {
                document.getElementById('image-preview').style.display = 'none';
            }
        }).catch(error => {
        console.error('Error fetching employee data', error);
    });
}

//------show employee details on modal
function showEmployeeDetails(employeeId) {
    // Fetch employee details by employeeId
    fetch(`/employee/detail/` + employeeId)
        .then(response => response.json())  // Assuming the response is in JSON format
        .then(employee => {
            // Populate the modal fields with employee data
            document.getElementById('modalEmployeeId').value = employee.employeeId;
            document.getElementById('modalEmployeeName').value = employee.employeeName;
            document.getElementById('modalEmployeeDob').value = employee.dateOfBirth;
            document.getElementById('modalEmployeeGender').value = employee.gender;
            document.getElementById('modalEmployeePhone').value = employee.phone;
            document.getElementById('modalEmployeeAddress').value = employee.address;
            document.getElementById('modalEmployeeEmail').value = employee.email;
            document.getElementById('modalEmployeePosition').value = employee.position;
            document.getElementById('modalWorkingPlace').value = employee.workingPlace;
            document.getElementById('modalEmployeeImage').src = `/employee/image/${employee.employeeId}`;

            // Show the modal
            var modal = new bootstrap.Modal(document.getElementById('employeeModal'));
            modal.show();
        })
        .catch(error => {
            console.error('Error fetching employee details:', error);
            alert('Failed to load employee details. Please try again.');
        });
}

//------
// function updatePaginationControls(currentPage, totalPages, pageSize, totalElements) {
//     document.getElementById("start-entry").innerHTML = currentPage === 0 ? 1 : currentPage * pageSize + 1;
//     document.getElementById("end-entry").innerHTML = currentPage === totalPages - 1 ? totalElements : (currentPage + 1) * pageSize;
//     document.getElementById("total-entries").innerHTML = totalElements;
//
//     const paginationContainer = document.getElementById("page-buttons");
//     let pageButtons = '';
//
//     // left button
//     if (currentPage > 0) {
//         pageButtons += `<button onclick="findAllEmployeeWithPagination(${currentPage - 1}, ${pageSize})" class="text-info">&laquo;</button>`;
//     } else {
//         pageButtons += `<button disabled class="text-info">&laquo;</button>`;
//     }
//
//     // buttons with number page
//     for (let i = 0; i < totalPages; i++) {
//         if (i === currentPage) {
//             pageButtons += `<button disabled class="bg-info fw-medium">${i + 1}</button>`;
//         } else {
//             pageButtons += `<button onclick="findAllEmployeeWithPagination(${i}, ${pageSize})">${i + 1}</button>`;
//         }
//     }
//
//     // right button
//     if (currentPage < totalPages - 1) {
//         pageButtons += `<button onclick="findAllEmployeeWithPagination(${currentPage + 1}, ${pageSize})" class="text-info">&raquo;</button>`;
//     } else {
//         pageButtons += `<button disabled class="text-info">&raquo;</button>`;
//     }
//
//     paginationContainer.innerHTML = pageButtons;
//     document.getElementById("dropdownMenuButton").innerHTML = pageSize;
// }
function updatePaginationControls(currentPage, totalPages, pageSize, totalElements) {
    document.getElementById("start-entry").innerHTML = currentPage === 0 ? 1 : currentPage * pageSize + 1;
    document.getElementById("end-entry").innerHTML = currentPage === totalPages - 1 ? totalElements : (currentPage + 1) * pageSize;
    document.getElementById("total-entries").innerHTML = totalElements;

    const paginationContainer = document.getElementById("page-buttons");
    let pageButtons = '';

    // Left button
    if (currentPage > 0) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllEmployeeWithPagination(${currentPage - 1}, ${pageSize})">&laquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&laquo;</span></li>`;
    }

    // Show all pages if totalPages < 10
    if (totalPages <= 10) {
        for (let i = 0; i < totalPages; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllEmployeeWithPagination(${i}, ${pageSize})">${i + 1}</a></li>`;
        }
    } else {
        // Always show page 1 and 2
        if (totalPages > 1) {
            pageButtons += `<li class="page-item ${currentPage === 0 ? 'active' : ''}"><a class="page-link" onclick="findAllEmployeeWithPagination(0, ${pageSize})">1</a></li>`;
            if (totalPages > 2) {
                pageButtons += `<li class="page-item ${currentPage === 1 ? 'active' : ''}"><a class="page-link" onclick="findAllEmployeeWithPagination(1, ${pageSize})">2</a></li>`;
            }
        }

        // Show page numbers around the current page with ellipses
        if (currentPage > 2) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        let startPage = Math.max(2, currentPage - 1);
        let endPage = Math.min(totalPages - 3, currentPage + 1);

        for (let i = startPage; i <= endPage; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" onclick="findAllEmployeeWithPagination(${i}, ${pageSize})">${i + 1}</a></li>`;
        }

        if (currentPage < totalPages - 4) {
            pageButtons += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }

        // Always show the last two pages
        if (totalPages > 2) {
            if (totalPages > 3) {
                pageButtons += `<li class="page-item ${currentPage === totalPages - 2 ? 'active' : ''}"><a class="page-link" onclick="findAllEmployeeWithPagination(${totalPages - 2}, ${pageSize})">${totalPages - 1}</a></li>`;
            }
            pageButtons += `<li class="page-item ${currentPage === totalPages - 1 ? 'active' : ''}"><a class="page-link" onclick="findAllEmployeeWithPagination(${totalPages - 1}, ${pageSize})">${totalPages}</a></li>`;
        }
    }

    // Right button
    if (currentPage < totalPages - 1) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="findAllEmployeeWithPagination(${currentPage + 1}, ${pageSize})">&raquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&raquo;</span></li>`;
    }

    paginationContainer.innerHTML = `<ul class="pagination">${pageButtons}</ul>`;
    document.getElementById("dropdownMenuButton").innerHTML = pageSize;
}

//------------------
//preview image
function previewImage() {
    const imagePreview = document.getElementById('image-preview');
    const fileInput = document.getElementById('image');
    const file = fileInput.files[0];

    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            imagePreview.src = e.target.result;
            imagePreview.style.display = 'block'; // Show the preview
        };
        reader.readAsDataURL(file);
    } else {
        imagePreview.src = '';
        imagePreview.style.display = 'none'; // Hide the preview if no file is selected
    }
}


// add employee
function addEmployee() {
    const form = document.getElementById('add-employee-form');
    const formData = new FormData(form);

    const genderInput = document.querySelector('input[name="gender"]:checked');
    const genderValue = genderInput ? genderInput.value : null;

    const employee = {
        employeeId: document.getElementById('employeeId').value,
        employeeName: document.getElementById('employeeName').value,
        gender: genderValue,
        dateOfBirth: document.getElementById('dateOfBirth').value,
        phone: document.getElementById('phone').value,
        address: document.getElementById('address').value,
        email: document.getElementById('email').value,
        workingPlace: document.getElementById('workingPlace').value,
        position: document.getElementById('position').value,
        username: document.getElementById('username').value,
        password: document.getElementById('password').value,
        image: null
    };

    console.log(employee.email);

    const imageFile = document.getElementById('image').files[0];
    if (imageFile) {
        const reader = new FileReader();
        reader.onloadend = function () {
            employee.image = reader.result.split(',')[1].replace(/\s/g, '');
            sendEmployeeData(employee);
        };
        reader.readAsDataURL(imageFile);
    } else {
        sendEmployeeData(employee);
    }
}


function sendEmployeeData(employee) {
    fetch('/employee/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(employee)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to add employee');
            }
            return response.json();
        })
        .then(data => {
            alert('Employee added successfully!');
            document.getElementById('add-employee-form').reset();
            document.getElementById('image-preview').style.display = 'none';
        })
        .catch(error => {
            console.error('Error adding employee:', error);
            alert('Error adding employee.');
        });
}

// show/hide password when input data
function togglePassword() {
    const passwordField = document.getElementById("password");
    const toggleIcon = document.getElementById("toggle-icon");
    if (passwordField.type === "password") {
        passwordField.type = "text";
        toggleIcon.src = "/images/icons/hidden.png"; // Change to hide icon
    } else {
        passwordField.type = "password";
        toggleIcon.src = "/images/icons/eye.png"; // Change to show icon
    }
}

// button reset: reset and hide preview image, can't reset id, username, password
function resetInput() {
    // Get all the fields
    const employeeId = document.getElementById('employeeId');
    const employeeName = document.getElementById('employeeName');
    const dateOfBirth = document.getElementById('dateOfBirth');
    const phone = document.getElementById('phone');
    const address = document.getElementById('address');
    const email = document.getElementById('email');
    const workingPlace = document.getElementById('workingPlace');
    const position = document.getElementById('position');
    const username = document.getElementById('username');
    const password = document.getElementById('password');

    // Reset only fields that are not disabled
    if (!employeeId.disabled) employeeId.value = '';
    if (!employeeName.disabled) employeeName.value = '';
    if (!dateOfBirth.disabled) dateOfBirth.value = '';
    if (!phone.disabled) phone.value = '';
    if (!address.disabled) address.value = '';
    if (!email.disabled) email.value = '';
    if (!workingPlace.disabled) workingPlace.value = '';
    if (!position.disabled) position.value = '';
    if (!username.disabled) username.value = '';
    if (!password.disabled) password.value = '';

    // Reset the image preview
    document.getElementById('image-preview').style.display = 'none';
    document.getElementById('image-preview').src = '/images/icons/image.png'; // Set to default image icon
}
