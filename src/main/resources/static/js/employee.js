function checkAllBoxes() {
	 const selectAllCheckbox = document.getElementById('mother-checkbox');
    const checkboxes = document.querySelectorAll('.check-boxes input[type="checkbox"]');
    checkboxes.forEach(function(checkbox) {
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
                    <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0">${employee.employeeId}</a></td>
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
                    <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0">${employee.employeeId}</a></td>
                    <td>${employee.employeeName}</td>
                    <td>${employee.dateOfBirth}</td>
                    <td>${employee.gender}</td>
                    <td>${employee.phone}</td>
                    <td>${employee.address}</td>
                    <td class="text-center"><img src="/employee/image/${employee.employeeId}" alt="image" style="height: 30px; width: 45px"></td>
                `;
                tableBody.appendChild(row);
            });
            updatePaginationControls(employees.number, employees.totalPages, pageSize, employees.totalElements);
        })
        .catch(error => console.error('Error fetching list of employees', error));
}

let selectedEmployeeId = null;

function handleCheckboxChange() {
    const checkboxes = document.querySelectorAll('.check-select-box');
    const updateButton = document.querySelector('.update-button');

    const checkedBox = Array.from(checkboxes).find(checkbox => checkbox.checked);

    if (checkedBox) {
        selectedEmployeeId = checkedBox.closest('tr').querySelector('td:nth-child(2) a').textContent;
    }
}

function updateSelectedEmployee() {
    const checkbox = document.querySelectorAll('#employee-list-content input[type="checkbox"]:checked')
    if(checkbox.length !== 1) {
        alert("Please select only one employee");
        return;
    }
    const employeeId = checkbox[0].closest('tr').querySelector('td:nth-child(2)').textContent;
    console.log(employeeId);
    // Fetch employee data using selectedEmployeeId
    fetchUpdateEmployee('employee-create.html',employeeId);
}

function updateEmployeeDetail(employeeId) {
    fetch(`/employee/detail/` + employeeId)
        .then(response => response.json())
        .then(employee => {
            // Now that the form is loaded, populate the form with employee data
            document.getElementById('employeeId').value = employee.employeeId;
            document.getElementById('employeeId').readOnly = true;
            document.getElementById('employeeName').value = employee.employeeName;
            document.getElementById('dateOfBirth').value = employee.dateOfBirth;
            document.getElementById('phone').value = employee.phone;
            document.getElementById('address').value = employee.address;
            document.getElementById('email').value = employee.email;
            document.getElementById('workingPlace').value = employee.workingPlace;
            document.getElementById('position').value = employee.position;
            document.getElementById('username').value = employee.username;
            document.getElementById('username').readOnly = true;
            document.getElementById('password').value = employee.password;
            document.getElementById('password').readOnly = true;

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

function updatePaginationControls(currentPage, totalPages, pageSize, totalElements) {
    document.getElementById("start-entry").innerHTML = currentPage === 0 ? 1 : currentPage * pageSize + 1;
    document.getElementById("end-entry").innerHTML = currentPage === totalPages - 1 ? totalElements : (currentPage + 1) * pageSize;
    document.getElementById("total-entries").innerHTML = totalElements;

    const paginationContainer = document.getElementById("page-buttons");
    let pageButtons = '';

	// left button
    if (currentPage > 0) {
        pageButtons += `<button onclick="findAllEmployeeWithPagination(${currentPage - 1}, ${pageSize})">&lt;&lt;</button>`;
    } else {
        pageButtons += `<button disabled style="color: grey">&lt;&lt;</button>`;
    }

	// buttons with number page
    for (let i = 0; i < totalPages; i++) {
        if (i === currentPage) {
            pageButtons += `<button disabled style="background-color: blue; color: white">${i + 1}</button>`;
        } else {
            pageButtons += `<button onclick="findAllEmployeeWithPagination(${i}, ${pageSize})">${i + 1}</button>`;
        }
    }

	// right button
    if (currentPage < totalPages - 1) {
        pageButtons += `<button onclick="findAllEmployeeWithPagination(${currentPage + 1}, ${pageSize})">&gt;&gt;</button>`;
    } else {
        pageButtons += `<button disabled style="color: grey">&gt;&gt;</button>`;
    }

    paginationContainer.innerHTML = pageButtons;
    document.getElementById("dropdownMenuButton").innerHTML = pageSize;
}

//preview image
function previewImage() {
    const imagePreview = document.getElementById('image-preview');
    const fileInput = document.getElementById('image');
    const file = fileInput.files[0];

    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            imagePreview.src = e.target.result;
            imagePreview.style.display = 'block'; // Show the preview
        };
        reader.readAsDataURL(file);
    } else {
        imagePreview.src = '';
        imagePreview.style.display = 'none'; // Hide the preview if no file is selected
    }
}

//button reset: reset and hide preview image
function resetInputEmployee(){
        document.getElementById('add-employee-form').reset();
        document.getElementById('image-preview').style.display = 'none';
        document.getElementById('image-preview').src = '#';
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
        reader.onloadend = function() {
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



