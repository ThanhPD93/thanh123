function getAllVaccineTypes() {
    fetch('/vaccine-type')
        .then(response => response.json())
        .then(data => {
            let vaccineTypeList = document.getElementById("vaccine-type-list");
            vaccineTypeList.innerHTML = "";
            data.forEach(vaccine => {
                vaccineTypeList.innerHTML += `<tr>
                    <td class="text-center"><input type="checkbox" ></td>
                    <td><a class="nav-link" href="#">${vaccine.vaccineTypeId}</a></td>
                    <td>${vaccine.vaccineTypeName}</td>
                    <td>${vaccine.vaccineTypeDescription}</td>
                    <td>Active</td>
                     </tr>`
            });
        })
        .catch(error => console.error('Error:', error));
}

function fetchVaccineType(filename) {
    fetch(`/vaccine-type/getAjax?filename=${filename}`)
        .then(response => response.text())
        .then(data => {
            document.getElementById('ajax-content').innerHTML = data;
            getAllVaccineTypes();
        })
        .catch(error => console.error('Error fetching document:', error));
}