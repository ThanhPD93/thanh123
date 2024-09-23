function customerUpdatePressed () {
	customerUpdateBtn = true;
}

function customerUpdateNotPressed() {
	customerUpdateBtn = false;
}

function fetchCustomer(filename) {
    $.ajax({
        url: "/api/customer/getAjax",
        data: { filename: filename },
        dataType: "text",
        success: function(data) {
            $("#ajax-content")[0].innerHTML = data;
            if (filename === "customer-list.html") {
                $("#ajax-title").html("INJECTION CUSTOMER LIST");
                listCustomers(0);
            } else {
                $("#ajax-title").html("REGISTER CUSTOMER INFORMATION");
                randomizeCaptcha();
            }
        },
        error: function(jqxhr, textStatus, errorThrown) {
            console.error("AJAX error:", textStatus, errorThrown);
        }
    });
}

function listCustomers(currentPage) {
	const query = $("#searchInput")[0].value;
    const currentPageSize = parseInt($("#dropdownMenuButton").html(), 10);
	$.ajax({
		url: "/api/customer/findAllCustomers",
		data: {
			searchInput: query,
			page: currentPage,
			size: currentPageSize
		},
		method: "GET",
		dataType: "json",
		success: function(customers) {
                $("#customer-list-content").html("");
                customers.content.forEach(customer => {
                    const row = `
                        <tr>
                            <td class="customer-id-hidden">${customer.customerId}</td>
                            <td class="text-center check-boxes"><input type="checkbox"></td>
                            <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0"
                                onclick="showCustomerDetails('${customer.customerId}')">
                                    ${customer.fullName}
                                </a>
                            </td>
                            <td>${customer.dateOfBirth}</td>
                            <td>${customer.gender}</td>
                            <td>${customer.address}</td>
                            <td>${customer.identityCard}</td>
                            <td>${customer.phone}</td>
                        </tr>
                    `;
                    $("#customer-list-content").append(row);
                });
                updatePageCustomer(customers.number, customers.totalPages, currentPageSize, customers.totalElements);
		},
		error: function(jqXHR, errorThrown) {
            console.error('Error fetching customer data:', jqXHR.statusText, errorThrown);
          }
	});
}

function updatePageCustomer(currentPage, totalPages, pageSize, totalElements) {
	if(totalElements === 0) {
		$("#start-entry")[0].textContent = 0;
		$("#end-entry")[0].textContent = 0;
		$("#total-entries")[0].textContent = 0;
	} else {
        $("#start-entry").text(currentPage === 0 ? 1 : (currentPage * pageSize) + 1);
        $("#end-entry").text(currentPage === totalPages - 1 ? totalElements : (currentPage + 1) * pageSize);
        $("#total-entries").text(totalElements);
	}

    const paginationContainer = $("#page-buttons");
    let pageButtons = '';

    // Left button
    if (currentPage > 0) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="listCustomers(${currentPage - 1})">&laquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&laquo;</span></li>`;
    }

	// direct page buttons
    for (let i = 0; i < totalPages; i++) {
        pageButtons += `<li class="page-item ${i === currentPage ? 'disabled active' : ''}"><a class="page-link" onclick="listCustomers(${i})">${i + 1}</a></li>`;
    }

    // Right button
    if (currentPage < totalPages - 1) {
        pageButtons += `<li class="page-item"><a class="page-link" onclick="listCustomers(${currentPage + 1})">&raquo;</a></li>`;
    } else {
        pageButtons += `<li class="page-item disabled"><span class="page-link">&raquo;</span></li>`;
    }
    paginationContainer.html(`<ul class="pagination">${pageButtons}</ul>`);
}

function deleteSelectedCustomer() {
    const checkboxes = $("#customer-list-content input[type='checkbox']:checked");
    if (checkboxes.length === 0) {
        alert("Please select at least one customer to delete");
        return;
    }
    const customerIds = Array.from(checkboxes).map(checkbox => {
        return checkbox.closest('tr').querySelector('td:nth-child(1)').textContent.trim();
    });

    const confirmed = confirm("Are you sure you want to delete the selected customers?");
    if (!confirmed) {
        return;
    }
    $.ajax({
        url: "/api/customer/delete",
        method: "DELETE",
        contentType: "application/json",
        data: JSON.stringify(customerIds),
        dataType: "json",
        success: function(response) {
            if (response.code === 200) {
                alert(response.description);
                listCustomers(0);
            } else {
                alert("Error deleting customers: " + response.description);
            }
        },
        error: function(error) {
            alert("An error occurred while deleting customers.");
        }
    });
}

function randomizeCaptcha() {
    const captchaCode = Math.floor(10000 + Math.random() * 90000);
    $("#customerCaptchaCode").val(captchaCode);
}

function randomizeCaptchaWithReset() {
	if(customerUpdateBtn === true) {
		const customerId = $("#customerId")[0].value;
		$("#add-customer-form")[0].reset();
		$("#customerId")[0].value = customerId;
	}
	else {
		$("#add-customer-form")[0].reset();
	}
	randomizeCaptcha();
}

function checkPassword() {
	if($("#customerPassword").val() != $("#customerPasswordConfirm").val()) {
		alert("password and password confirm do not match!");
		return 0;
	}
	else {
		return 1;
	}
}

function checkCaptcha() {
	if(checkPassword() === 0) {
		return;
	}
	if($("#customerCaptcha").val() === $("#customerCaptchaCode").val()) {
		addCustomer();
		randomizeCaptchaWithReset();
	} else {
		alert("Captcha does not match!, please try again");
	}
}

function addCustomer() {
	const genderInput = $('input[name="gender"]:checked');
    const genderValue = genderInput.length ? genderInput.val() : null;
    const customer = {
            customerId: $('#customerId').val(),
            fullName: $('#customerFullName').val(),
            gender: genderValue,
            dateOfBirth: $('#customerDateOfBirth').val(),
            phone: $('#customerPhone').val(),
            address: $('#customerAddress').val(),
            email: $('#customerEmail').val(),
            identityCard: $("#customerIdentityCard").val(),
            username: $('#customerUsername').val(),
            password: $('#customerPassword').val(),
        };
    $.ajax({
    	url: "/api/customer/add",
    	method: "POST",
    	contentType: "application/json",
    	data: JSON.stringify(customer),
    	success: function(stringData) {
    		alert(stringData);
    		if (customerUpdateBtn === true) {
                customerUpdateBtn = false;
                fetchCustomer("customer-list.html");
    		} else {
    			randomizeCaptchaWithReset();
    		}
    	},
    	error: function(xhr) {
    		alert("error fetching data for /api/customer/add");
    		alert(xhr.status);
    		alert(xhr.statusText);
    	}
    });
}

function updateSelectedCustomer() {
	const checkbox = $(".check-boxes input[type='checkbox']:checked");
	if (checkbox.length != 1) {
		alert("please select only 1 customer to update information!");
		return;
	}
	const customerId = checkbox[0].closest('tr').querySelector('td:nth-child(1)').textContent;
	fetchCustomer("customer-create.html");
	$.ajax({
        url: "/api/customer/findById",
        data: {customerId: customerId},
        success: function(customer) {
            $("#customerFullName")[0].value = customer.fullName;
            $("#customerDateOfBirth")[0].value = customer.dateOfBirth;
            if (customer.gender === "MALE") {
                $('#gender-male')[0].checked = true;
            } else {
            	$('#gender-female')[0].checked = true;
            }
            $("#customerIdentityCard")[0].value = customer.identityCard;
            $("#customerAddress")[0].value = customer.address;
            $("#customerId")[0].value = customer.customerId;
            $("#customerId")[0].disabled = true;
            $("#customerUsername")[0].value = customer.username;
//            $("#customerPassword")[0].value = customer.password;
            $("#customerEmail")[0].value = customer.email;
            $("#customerPhone")[0].value = customer.phone;
        },
        error: function() {alert("error at /api/customer/findById")}
    });
}

function showCustomerDetails(id) {
	$.ajax({
		url: "/api/customer/findById",
		data: {customerId: id},
		success: function(customer) {
			$("#modalCustomerId")[0].value = customer.customerId;
			$("#modalCustomerFullName")[0].value = customer.fullName;
			$("#modalCustomerDob")[0].value = customer.dateOfBirth;
			$("#modalCustomerGender")[0].value = customer.gender;
			$("#modalCustomerIdentityCard")[0].value = customer.identityCard;
			$("#modalCustomerAddress")[0].value = customer.address;
			$("#modalCustomerEmail")[0].value = customer.email;
			$("#modalCustomerPhone")[0].value = customer.phone;
			// Show the modal
            var modal = new bootstrap.Modal(document.getElementById('customerModal'));
            modal.show();
		},
		error: function(xhr) {
			console.log("error at /api/customer/findById/{id}: ", xhr.responseText);
			alert("error at /api/customer/findById/{id}");
		}
	});
}