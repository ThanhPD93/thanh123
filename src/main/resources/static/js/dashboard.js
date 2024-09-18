let UpdateBtnPressed = false;
let selectedEmployeeId = null;
let vaccineTypeUpdateBtn = false;

document.addEventListener("DOMContentLoaded", function () {
	$.ajax({
		url: "/api/customer/getCurrentUsername",
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

function pressEmployeeUpdateButton() {
	UpdateBtnPressed = true;
}

function dropdownPageSize(pageSize) {
	document.getElementById("dropdownMenuButton").innerHTML = pageSize;
}

function backToHomePage() {
	$("#ajax-title")[0].innerHTML = "WELCOME TO VACCINE MANAGEMENT SYSTEM";
	$("#ajax-content")[0].innerHTML = "";
}
