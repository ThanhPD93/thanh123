function listCustomers (currentPage) {
	const query = $("#searchInput").val();
    const currentPageSize = parseInt($("#dropdownMenuButton").html(), 10);
//    console.log(currentPageSize);

	$.ajax({
		url: "/customer/findAllCustomers",
		data: {
			searchInput: query,
			page: currentPage,
			size: currentPageSize
		},
		method: "GET",
		dataType: "json",
		success: function(apiResponse) {
			const customers = apiResponse.data;
			if(apiResponse.code === 1) {
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
                updateCustomerPaginationControls(customers.number,
                                                customers.totalPages,
                                                currentPageSize,
                                                customers.totalElements);
			} else {
                alert(apiResponse.description);
            }
		},
		error: function(jqXHR, errorThrown) {
            console.error('Error fetching customer data:', jqXHR.statusText, errorThrown);
          }
	});
}

function updateCustomerPaginationControls(currentPage, totalPages, pageSize, totalElements) {
    $("#start-entry").text(currentPage === 0 ? 1 : (currentPage * pageSize) + 1);
    $("#end-entry").text(currentPage === totalPages - 1 ? totalElements : (currentPage + 1) * pageSize);
    $("#total-entries").text(totalElements);

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
        url: "/customer/delete",
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

function randomizeCapcha() {
    const capchaCode = Math.floor(10000 + Math.random() * 90000);
    $("#customerCapchaCode").val(capchaCode);
}

function randomizeCapchaWithReset() {
	$("#add-customer-form")[0].reset();
	randomizeCapcha();
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

function checkCapcha() {
	if(checkPassword() === 0) {
		return;
	}
	if($("#customerCapcha").val() === $("#customerCapchaCode").val()) {
		addCustomer();
		fetchCustomer("customer-create.html");
	} else {
		alert("Capcha does not match!, please try again");
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
    console.log(customer);

    $.ajax({
    	url: "/customer/add",
    	method: "POST",
    	contentType: "application/json",
    	data: JSON.stringify(customer),
    	dataType: "text",
    	success: function(data) {alert(data)},
    	error: function(error) {
    		alert("error fetching data for /customer/add");
    		alert(error);
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
	$.getScript("/js/dashboard.js");
	fetchCustomer("customer-create.html");
	$.ajax({
        url: "/customer/findById",
        data: {id: customerId},
        dataType: "json",
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
            $("#customerPassword")[0].value = customer.password;
            $("#customerEmail")[0].value = customer.email;
            $("#customerPhone")[0].value = customer.phone;
        },
        error: function() {alert("error at /customer/findById")}
    });

}