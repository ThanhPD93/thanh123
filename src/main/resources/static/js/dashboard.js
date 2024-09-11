 // Ajax
 function fetchEmployee(filename) {
     fetch(`/employee/getAjax?filename=${filename}`)
         .then(response => response.text())
         .then(data => {
             document.getElementById('ajax-content').innerHTML = data;
             findAllEmployeeWithPagination(0,10);
         })
         .catch(error => console.error('Error fetching document:', error));
 }

 function fetchUpdateEmployee(filename, employeeId) {
     fetch(`/employee/getAjax?filename=${filename}`)
         .then(response => response.text())
         .then(data => {
             document.getElementById('ajax-content').innerHTML = data;
             updateEmployeeDetail(employeeId);
         })
         .catch(error => console.error('Error fetching document:', error));
 }


 function fetchInjectionResult(filename) {
     fetch(`/injection-result/getAjax?filename=${filename}`)
         .then(response => response.text())
         .then(data => {
             document.getElementById('ajax-content').innerHTML = data;

             // Initialize data loading functions after updating content
             findAllInjectionResultsWithPagination(0, 10);
             loadCustomers();
             loadVaccineTypeName();
             loadInjectionPlace();

             // Add an event listener to the vaccine type dropdown after it's loaded
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
         })
         .catch(error => console.error('Error fetching document:', error));
 }
