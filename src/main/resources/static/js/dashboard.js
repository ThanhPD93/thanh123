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
	$("#ajax-title")[0].innerHTML = "WELCOME TO VACCINE MANAGEMENT SYSTEM";
	$("#ajax-content")[0].innerHTML = "";
}

document.addEventListener("DOMContentLoaded", function () {
	$.ajax({
		url: "/api/user/getCurrentUsername",
		method: "GET",
		dataType: "text",
		success: function(currentUsername) {
			$("#current-username")[0].innerHTML = currentUsername.charAt(0).toUpperCase() + currentUsername.slice(1);
			if(currentUsername === "admin") {
				$("#userEmail")[0].innerHTML = "admin@fsoft.com.vn";
			}
		},
		error: function(error) {
			alert("error fetching current username!");
			console.error("Error fetching current username!", error);
		}
	});
});
