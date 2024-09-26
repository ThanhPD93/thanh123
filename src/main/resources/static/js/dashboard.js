let employeeUpdateBtn = false;
let selectedEmployeeId = null;
let vaccineTypeUpdateBtn = false;
let customerUpdateBtn = false;
let vaccineTypeDisplayObject;
let vaccineIdParam;
let injectionScheduleUpdateBtn = false;
let injectionScheduleIdParam;
let vaccineOfIsIdParam;
let injectionScheduleLink = false;
let InjectionResultUpdateBtn = false;
let injectionResultTempId;
let vaccineNameForDropdown;

function checkAllBoxes() {
    const selectAllCheckbox = $("#mother-checkbox")[0];
    const checkboxes = $(".check-boxes input[type='checkbox']").toArray();
    checkboxes.forEach(checkbox => checkbox.checked = selectAllCheckbox.checked);
}

function dropdownPageSize(pageSize) {
	document.getElementById("dropdownMenuButton").innerHTML = pageSize;
}

function backToHomePage() {
	ajaxNews("show-news.html");
}

document.addEventListener("DOMContentLoaded", function () {
	// get news at home page
	ajaxNews("show-news.html");

	// get current username
	$.ajax({
		url: "/api/user/getCurrentUsernameAndEmail",
		method: "GET",
		success: function(user) {
			$("#current-username")[0].innerHTML = user.username.charAt(0).toUpperCase() + user.username.slice(1);
			if(user.username === "admin") {
				$("#userEmail")[0].innerHTML = "admin@fsoft.com.vn";
				$("#employeeManagementLocation")[0].innerHTML = `
					<a class="nav-link text-black d-flex justify-content-between" data-bs-toggle="collapse" data-bs-target="#employeeManagement"
                    aria-expanded="false" aria-controls="employeeManagement" id="employeeManagementTitle">
                     <div class="fw-medium">
                         Employee Management
                     </div>
                     <div>
                         <i class="bi bi-plus"></i>
                     </div>
                 </a>
                 <div class="sub-menu collapse " id="employeeManagement">
                     <a class="nav-link fw-medium" onclick="unPressEmployeeUpdateButton(); fetchEmployee('employee-list.html');">
                         Employee List
                     </a>
                     <a class="nav-link fw-medium" onclick="unPressEmployeeUpdateButton(); fetchEmployee('employee-create.html');">
                         Create Employee
                     </a>
                 </div>`;
			} else {
				$("#userEmail")[0].innerHTML = user.email;
			}
		},
		error: function(xhr) {
			alert("error fetching current username!");
			console.error("Error fetching current username!", xhr.status);
		}
	});

	// get current user role
//	$.ajax({
//    		url: "/api/user/getRole",
//    		method: "GET",
//    		success: function(role) {
//                if(role === "employee") {
//                    $("#employeeManagementTitle").addClass("d-none");
//                }
//    		},
//    		error: function(xhr) {
//    			alert("error fetching current user role!");
//    			console.error("Error fetching current role!", xhr.status);
//    		}
//    	});

});