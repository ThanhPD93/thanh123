function search() {
            console.log(document.getElementById('searchInput').value);
            const query = document.getElementById('searchInput').value;

            fetch('/employee/getEmployeesBySearch?searchInput=' + encodeURIComponent(query))
                .then(response => response.json())
                .then(data => {
                    const tableBody = document.getElementById('employee-list-content');
                    tableBody.innerHTML = '';

                    data.forEach(employee => {
                        const row = document.createElement('tr');

                        row.innerHTML = `
                            <td class="text-center"><input type="checkbox"></td>
                            <td>${employee.employeeId}</td>
                            <td>${employee.employeeName}</td>
                            <td>${employee.dateOfBirth}</td>
                            <td>${employee.gender}</td>
                            <td>${employee.phone}</td>
                            <td>${employee.address}</td>
                            <td class="text-center"><img src="${employee.image}" alt="image" style="height: 30px; width: 45px"></td>
                        `;
                        tableBody.appendChild(row);
                    });
                })
                .catch(error => console.error('Error fetching employee data:', error));
        }

// show list employee
function findAllEmployee() {
    fetch('/employee/list')
        .then(response => response.json())
        .then(response => {
            if (response.code === 1) { // Check if the response is successful
                const employees = response.data; // Access the data field
                document.getElementById('employee-list-content').innerHTML = "";
                employees.forEach(employee => {
                    document.getElementById("employee-list-content").innerHTML += `
                        <tr>
                            <td class="text-center"><input type="checkbox"></td>
                            <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0">${employee.employeeId}</a></td>
                            <td>${employee.employeeName}</td>
                            <td>${employee.dateOfBirth}</td>
                            <td>${employee.gender}</td>
                            <td>${employee.phone}</td>
                            <td>${employee.address}</td>
                            <td class="text-center">
                                <img src="${employee.image}" alt="image" style="height: 30px; width: 45px">
                            </td>
                        </tr>
                    `;
                });
            } else {
                console.error('Failed to fetch employees: ', response.description);
            }
        })
        .catch(error => console.error('Error fetching list of employees', error));
}

//add
function addEmployee() {
    // Gather employee data from form inputs
    const employeeData = {
        employeeId: document.getElementById('employeeId').value,
        employeeName: document.getElementById('employeeName').value,
        dateOfBirth: document.getElementById('dateOfBirth').value,
        gender: document.querySelector('input[name="gender"]:checked').value,
        phone: document.getElementById('phone').value,
        address: document.getElementById('address').value,
        email: document.getElementById('email').value,
        workingPlace: document.getElementById('workingPlace').value,
        position: document.getElementById('position').value,
        // image: document.getElementById('image').files[0] ? document.getElementById('image').files[0].name : ''
    };

    // Send POST request to add the employee
    fetch('/employee/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(employeeData)
    })
        .then(response => response.json())
        .then(response => {
            if (response.code === 1) { // Check if the employee was added successfully
                alert('Employee added successfully!');
                // Optionally, clear the form or redirect to another page
                document.getElementById('add-employee-form').reset();
            } else {
                alert('Failed to add employee: ' + response.description);
            }
        })
        .catch(error => console.error('Error adding employee', error));
}
