
// Ajax
function fetchEmployee(filename) {
 fetch(`/employee/getAjax?filename=${filename}`)
     .then(response => response.text())
     .then(data => {
         document.getElementById('ajax-content').innerHTML = data;
         if (filename === "employee-list.html") {
         	document.getElementById("ajax-title").innerHTML = "EMPLOYEE LIST";
         	findAllEmployeeWithPagination(0,10);
         } else {
         	document.getElementById("ajax-title").innerHTML = "CREATE EMPLOYEE";
         }
     })
     .catch(error => console.error('Error fetching document:', error));
}

function fetchUpdateEmployee(filename, employeeId) {
 fetch(`/employee/getAjax?filename=${filename}`)
     .then(response => response.text())
     .then(data => {
         $("#ajax-content")[0].innerHTML = data;
         document.getElementById("ajax-title").innerHTML = "CREATE EMPLOYEE";
         updateEmployeeDetail(employeeId);
     })
     .catch(error => console.error('Error fetching document:', error));
}

//--------INJECTION RESULT LIST
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

//------------
 function fetchUpdateInjectionResult(filename, injectionResultId) {
     fetch(`/injection-result/getAjax?filename=${filename}`)
         .then(response => response.text())
         .then(data => {
             document.getElementById('ajax-content').innerHTML = data;
             updateInjectionResultDetail(injectionResultId);
             loadCustomers();
             loadVaccineTypeName();
             loadInjectionPlace();
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




function fetchCustomer(filename) {
    $.ajax({
        url: "customer/getAjax",
        data: { filename: filename },
        dataType: "text",
        success: function(data) {
            // Inject the response content into the DOM
            $("#ajax-content")[0].innerHTML = data;
            if (filename === "customer-list.html") {
                $("#ajax-title").html("INJECTION CUSTOMER LIST");
                $.getScript("/js/customer.js");
                listCustomers(0);
            } else {
                $("#ajax-title").html("REGISTER CUSTOMER INFORMATION");
                $.getScript("/js/customer.js");
                randomizeCaptcha();
            }
        },
        error: function(jqxhr, textStatus, errorThrown) {
            console.error("AJAX error:", textStatus, errorThrown);
        }
    });
}

document.addEventListener("DOMContentLoaded", function () {
	fetch(`/customer/getCurrentUsername`)
	.then(response => response.text())
	.then(currentUsername => {
		document.getElementById("current-username").innerHTML = currentUsername;
	})
	.catch(error => console.error("Error fetching current username!", error));
});

function dropdownPageSize(pageSize) {
	document.getElementById("dropdownMenuButton").innerHTML = pageSize;
}