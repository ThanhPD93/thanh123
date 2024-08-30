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

function findAllEmployee() {
	fetch('/employee/findAll')
		.then(response => response.json())
		.then(employees => {
			document.getElementById('employee-list-content').innerHTML = "";
			employees.forEach(employee => {
				document.getElementById("employee-list-content").innerHTML +=`
					<tr>
                        <td class="text-center"><input type="checkbox"></td>
                        <td>${employee.employeeId}</td>
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
		})
		.catch(error => console.error('Error fetching list of employees', error));
}