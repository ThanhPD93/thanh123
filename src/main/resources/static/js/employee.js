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
    const query = document.getElementById('searchInput').value;
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
            updatePaginationControls(employees.number, employees.totalPages, pageSize, employees.totalElements);
        })
        .catch(error => console.error('Error fetching list of employees', error));
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

function findAllEmployee() {
    fetch('/employee/list')
        .then(response => response.json())
        .then(response => {
            if (response.code === 1) {
                const employees = response.data;
                const tableBody = document.getElementById('employee-list-content');
                tableBody.innerHTML = '';

                employees.forEach(employee => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td class="text-center check-boxes"><input type="checkbox"></td>
                        <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0">${employee.employeeId}</a></td>
                        <td>${employee.employeeName}</td>
                        <td>${employee.dateOfBirth}</td>
                        <td>${employee.gender}</td>
                        <td>${employee.phone}</td>
                        <td>${employee.address}</td>
                        <td class="text-center"><img src="${employee.image}" alt="image" style="height: 30px; width: 45px"></td>
                    `;
                    tableBody.appendChild(row);
                });
            } else {
                console.error('Failed to fetch employees:', response.description);
            }
        })
        .catch(error => console.error('Error fetching list of employees', error));
}

//function addEmployee() {
//    const employeeData = {
//        employeeId: document.getElementById('employeeId').value,
//        employeeName: document.getElementById('employeeName').value,
//        dateOfBirth: document.getElementById('dateOfBirth').value,
//        gender: document.querySelector('input[name="gender"]:checked').value,
//        phone: document.getElementById('phone').value,
//        address: document.getElementById('address').value,
//        email: document.getElementById('email').value,
//        workingPlace: document.getElementById('workingPlace').value,
//        position: document.getElementById('position').value
//    };
//
//    fetch('/employee/add', {
//        method: 'POST',
//        headers: {
//            'Content-Type': 'application/json'
//        },
//        body: JSON.stringify(employeeData)
//    })
//        .then(response => response.json())
//        .then(response => {
//            if (response.code === 1) {
//                alert('Employee added successfully!');
//                document.getElementById('add-employee-form').reset();
//            } else {
//                alert('Failed to add employee: ' + response.description);
//            }
//        })
//        .catch(error => console.error('Error adding employee', error));
//}

function addEmployee() {
    const form = document.getElementById('add-employee-form');
    const formData = new FormData(form);

    const employee = {
        employeeId: document.getElementById('employeeId').value,
        employeeName: document.getElementById('employeeName').value,
        gender: document.querySelector('input[name="gender"]:checked').value,
        dateOfBirth: document.getElementById('dateOfBirth').value,
        phone: document.getElementById('phone').value,
        address: document.getElementById('address').value,
        email: document.getElementById('email').value,
        workingPlace: document.getElementById('workingPlace').value,
        position: document.getElementById('position').value,
        image: null
    };

    const imageFile = document.getElementById('image').files[0];
    if (imageFile) {
        const reader = new FileReader();
        reader.onloadend = function() {
            // Debugging: Log the Base64 data to ensure it's correct
            console.log('Base64 Image Data:', reader.result);

            // Extract the Base64 part after the comma
			employee.image = reader.result.split(',')[1].replace(/\s/g, '');

            // Debugging: Log the extracted Base64 image string
            console.log('Extracted Base64 Image String:', employee.image);

            sendEmployeeData(employee);
        };
        reader.readAsDataURL(imageFile);
    } else {
        sendEmployeeData(employee); // Send without image if not provided
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
        console.log('Employee added successfully:', data);
    })
    .catch(error => {
        console.error('Error adding employee:', error);
    });
}

function previewImage(event) {
    const imagePreview = document.getElementById('image-preview');
    // Remove the inline style that hides the image
    imagePreview.style.display = 'block';
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            imagePreview.src = e.target.result;
            imagePreview.style.display = 'block';
        };
        reader.readAsDataURL(file);
    } else {
        imagePreview.src = '';
        imagePreview.style.display = 'none';
    }
}




