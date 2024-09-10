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
             findAllInjectionResultsWithPagination(0, 10);
             loadCustomers();
             loadVaccineTypes();
             loadPreventions();
             loadInjectionPlaces();
         })
         .catch(error => console.error('Error fetching document:', error));
 }

