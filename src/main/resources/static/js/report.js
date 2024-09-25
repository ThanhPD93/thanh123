function fetchReport(filename) {
    $.ajax({
        url: "/api/report/getAjax",
        data: { filename: filename },
        dataType: "text",
        success: function(data) {
            // Inject the response content into the DOM
            $("#ajax-content")[0].innerHTML = data;
            if (filename === "report-injection-result.html") {
                $("#ajax-title").html("REPORT INJECTION RESULT");
                setupDropdownForInjectionResultReport();
            } else if(filename === "report-customer.html") {
                $("#ajax-title").html("REPORT CUSTOMER");
            } else {
                $("#ajax-title").html("REPORT VACCINE");
                loadVaccineTypeNameForReport();
            }
        },
        error: function(jqxhr, textStatus, errorThrown) {
            console.error("AJAX error:", textStatus, errorThrown);
        }
    });
}

function switchDisplayTypeInjection(type) {
    if (type === 'REPORT') {
        $('#report-section').show();
        $('#chart-section').hide();
    } else if (type === 'CHART') {
        $('#report-section').hide();
        $('#chart-section').show();
        loadYears();
        $('#year').on('change', fetchChartDataInjection);
    }
}

function switchDisplayTypeCustomer(type) {
    if (type === 'REPORT') {
        $('#report-section').show();
        $('#chart-section').hide();
    } else if (type === 'CHART') {
        $('#report-section').hide();
        $('#chart-section').show();
        loadYears();
        $('#year').on('change', fetchChartDataCustomer);
    }
}

function switchDisplayTypeVaccine(type) {
    if (type === 'REPORT') {
        $('#report-section').show();
        $('#chart-section').hide();
    } else if (type === 'CHART') {
        $('#report-section').hide();
        $('#chart-section').show();
        loadYears();
        $('#year').on('change', fetchChartDataVaccine);
    }
}

    // Load years into the dropdown for the chart
function loadYears() {
        $.ajax({
            url: '/api/report/injection/getYears',
            method: 'GET',
            success: function (years) {
                $('#year').empty().append('<option value="" disabled selected>Select Year</option>');
                years.forEach(function (year) {
                    $('#year').append('<option value="' + year + '">' + year + '</option>');
                });
            },
            error: function (error) {
                console.error('Error fetching years:', error);
            }
        });
    }
//--injection
function fetchChartDataInjection() {
    const year = $('#year').val();

    if (!year) {
        console.error("No year selected.");
        return;
    }

    $.ajax({
        url: '/api/report/injection/chart',
        method: 'GET',
        data: { year: year },
        success: function (chartData) {
            renderChart(chartData);
        },
        error: function (error) {
            console.error('Error fetching chart data:', error);
        }
    });
}


// Render
let currentChart = null;
function renderChart(chartData) {
    const ctx = document.getElementById('injectionChart').getContext('2d');

    if (currentChart) {
        currentChart.destroy();
    }

    const months = chartData.months;
    const results = chartData.results;

    currentChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: months,
            datasets: [{
                label: 'Injection Results',
                data: results,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1
                    }
                }
            }
        }
    });
}

//--customer
function fetchChartDataCustomer() {
    const year = $('#year').val();

    if (!year) {
        console.error("No year selected.");
        return;
    }
        $.ajax({
            url: '/api/report/customer/chart',
            method: 'GET',
            data: {year: year},
            success: function (chartData) {
                renderVaccinatedCustomerChart(chartData);
            },
            error: function (error) {
                console.error('Error fetching chart data:', error);
            }
        });
    }


    let currentCustomerChart = null;

    function renderVaccinatedCustomerChart(chartData) {
        const ctx = document.getElementById('vaccinatedCustomerChart').getContext('2d');

        if (currentCustomerChart) {
            currentCustomerChart.destroy();
        }

        const months = chartData.months;
        const results = chartData.results;

        currentCustomerChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: months,
                datasets: [{
                    label: 'Customers',
                    data: results,
                    backgroundColor: 'rgba(117,236,201,0.2)',
                    borderColor: 'rgb(9,145,36)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        }
                    }
                }
            }
        });
    }

//--vaccine
    function fetchChartDataVaccine() {
        const year = $('#year').val();

        if (!year) {
            console.error("No year selected.");
            return;
        }

        $.ajax({
            url: '/api/report/vaccine/chart',
            method: 'GET',
            data: {year: year},
            success: function (chartData) {
                renderVaccineChart(chartData);
            },
            error: function (error) {
                console.error('Error fetching chart data:', error);
            }
        });
    }


    let currentVaccineChart = null;

    function renderVaccineChart(chartData) {
        const ctx = document.getElementById('vaccineChart').getContext('2d');

        if (currentVaccineChart) {
            currentVaccineChart.destroy();
        }

        const months = chartData.months;
        const results = chartData.results;

        currentVaccineChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: months,
                datasets: [{
                    label: 'Vaccine',
                    data: results,
                    backgroundColor: 'rgba(241,215,130,0.2)',
                    borderColor: 'rgb(228,184,27)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        }
                    }
                }
            }
        });
    }

// Event listener for when the user selects a year
    $('#year').on('change', fetchChartDataInjection);
    $('#year').on('change', fetchChartDataCustomer);
    $('#year').on('change', fetchChartDataVaccine);

function CustomerReportList(currentPage, pageSize) {
    // Get the search inputs from the HTML fields
    const fullName = $("#fullName").val();
    const fromDate = $("#fromDate").val();
    const toDate = $("#toDate").val();
    const address = $("#address").val();

    // Make the AJAX call to the backend
    $.ajax({
        url: "/api/report/customer/list",
        data: {
            fullName: fullName,
            fromDate: fromDate,
            toDate: toDate,
            address: address,
            page: currentPage,
            size: pageSize
        },
        success: function (reports) {
            const tableBody = document.getElementById('report-customer-list');
            tableBody.innerHTML = '';  // Clear existing table content

            // Populate the table with the new filtered data
            reports.content.forEach((report, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td class="text-center">${(currentPage * pageSize) + index + 1}</td>  <!-- Auto-generated number -->
                    <td>${report.fullName}</td>
                    <td>${report.dateOfBirth}</td>
                    <td>${report.address}</td>
                    <td>${report.identityCard}</td>
                    <td>${report.totalNumberOfInjection}</td>
                `;
                tableBody.appendChild(row);  // Add the row to the table
            });

            // Update pagination controls after fetching data
            updateCustomerReportPaginationControls(reports.number, reports.totalPages, pageSize, reports.totalElements);
        },
        error: function () {
            alert("Failed to fetch /api/report/customer/list");
        }
    });
}

    function updateCustomerReportPaginationControls(currentPage, totalPages, pageSize, totalElements) {
        $("#start-entry").text(currentPage === 0 ? 1 : (currentPage * pageSize) + 1);
        $("#end-entry").text(currentPage === totalPages - 1 ? totalElements : (currentPage + 1) * pageSize);
        $("#total-entries").text(totalElements);

        const paginationContainer = $("#page-buttons");
        let pageButtons = '';

        // Left button
        if (currentPage > 0) {
            pageButtons += `<li class="page-item"><a class="page-link" onclick="CustomerReportList(${currentPage - 1})">&laquo;</a></li>`;
        } else {
            pageButtons += `<li class="page-item disabled"><span class="page-link">&laquo;</span></li>`;
        }

        // direct page buttons
        for (let i = 0; i < totalPages; i++) {
            pageButtons += `<li class="page-item ${i === currentPage ? 'disabled active' : ''}"><a class="page-link" onclick="CustomerReportList(${i})">${i + 1}</a></li>`;
        }

        // Right button
        if (currentPage < totalPages - 1) {
            pageButtons += `<li class="page-item"><a class="page-link" onclick="CustomerReportList(${currentPage + 1})">&raquo;</a></li>`;
        } else {
            pageButtons += `<li class="page-item disabled"><span class="page-link">&raquo;</span></li>`;
        }
        paginationContainer.html(`<ul class="pagination">${pageButtons}</ul>`);
    }
//show list of vaccine with filter

function loadVaccineTypeNameForReport() {
    $.ajax({
        url: "/api/vaccine-type/vt-for-add-ir",
        success: function(vaccineTypes) {
            const vaccineTypeSelect = document.getElementById('vaccineTypeName');
            console.log(vaccineTypeSelect);
            vaccineTypeSelect.innerHTML = '<option value="" disabled selected>Select Vaccine Type Name</option>';

            vaccineTypes.forEach(vaccineType => {
                vaccineTypeSelect.innerHTML += `<option value="${vaccineType.vaccineTypeName}">${vaccineType.vaccineTypeName}</option>`;
            });
        },
        error: function(xhr) {
            console.error("Error at /api/vaccine-type/vt-for-add-ir \nerrorcode: " + xhr.status + "\n message: " + xhr.responseText);
        }
    });
}


function getListVaccineWithFilter(page, size){
     console.log($("#beginDate")[0].value);
     console.log($("#endDate")[0].value);
     console.log($("#vaccineTypeName")[0].value);
     console.log($("#origin")[0].value);
     console.log(page);
     console.log(size);

    $.ajax({
        url: "/api/report/vaccine/filter",
        data:{
            beginDate: $("#beginDate")[0].value,
            endDate: $("#endDate")[0].value,
            vaccineTypeName: $("#vaccineTypeName")[0].value,
            origin: $("#origin")[0].value,
            page: page,
            size: size
        },
        dataType: "json",
        success: function(vaccines){
            const tableBody = document.getElementById('report-vaccine-list-content');
            tableBody.innerHTML = "";
            console.log(vaccines.content);
            let autoIncrement = (page * size) + 1;
            vaccines.content.forEach(vaccine =>{
                const row = document.createElement('tr');
                row.innerHTML =`
                    <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0 text-uppercase">${autoIncrement}</a></td>
                    <td class="text-capitalize text-start">${vaccine.vaccineName}</td>
                    <td class="text-start">${vaccine.vaccineType.vaccineTypeName}</td>
                    <td class="text-start">${vaccine.numberOfInjection}</td>
                    <td class="text-start">${vaccine.timeBeginNextInjection}</td>
                    <td class="text-start">${vaccine.timeEndNextInjection}</td>
                    <td class="text-start">${vaccine.vaccineOrigin}</td>
                `;
                tableBody.appendChild(row);
                autoIncrement++;
            });
        },
        error: function(error) {
            console.error('Error fetching list of vaccines', error);
        }
    })
}

//show list injection result with filter

function getListInjectionResultWithFilter(page, size) {
    $.ajax({
        url: "/api/report/injection/filter",
        data: {
            startDate: $("#startDate")[0].value,
            endDate: $("#endDate")[0].value,
            vaccineTypeName: $("#rp-injection-vaccinetypename")[0].value,
            vaccineName: $("#rp-injection-vaccinename")[0].value,
            page: page,
            size: size
        },
        dataType: "json",
        success: function(injectionResults) {
            const tableBody = document.getElementById('report-injection-list-content');
            tableBody.innerHTML = "";

            // Define the auto-increment variable based on the current page and size
            let autoIncrement = (page * size) + 1;

            injectionResults.content.forEach(injectionResult => {
                const row = document.createElement('tr');

                // Use the auto-increment variable and increment it with each row
                row.innerHTML = `
                    <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0 text-uppercase">${autoIncrement}</a></td>
                    <td class="text-capitalize text-start">${injectionResult.vaccineFromInjectionResult.vaccineName}</td>
                    <td class="text-start">${injectionResult.vaccineFromInjectionResult.vaccineType.vaccineTypeName}</td>
                    <td class="text-start">${injectionResult.customer.fullName}</td>
                    <td class="text-start">${injectionResult.injectionDate}</td>
                    <td class="text-start">${injectionResult.numberOfInjection}</td>
                `;

                tableBody.appendChild(row);

                // Increment the auto-increment variable
                autoIncrement++;
            });
        },
        error: function(error) {
            console.error('Error fetching list of injection result', error);
        }
    });
}

function setupDropdownForInjectionResultReport() {
    console.log("checkpoint 4: injectionResultTempId = " + injectionResultTempId);
    $.ajax({
        url: "/api/injection-result/displayDropdown",
        success: function (data) {
            vaccineTypeDisplayObject = data.vaccineTypes;
            $("#rp-injection-vaccinetypename")[0].innerHTML = '';
            $("#rp-injection-vaccinename")[0].innerHTML = '';

            // Populate the Vaccine Type dropdown
            data.vaccineTypes.forEach(vaccineType => {
                    $("#rp-injection-vaccinetypename")[0].innerHTML += `<option value="${vaccineType.vaccineTypeName}">--${vaccineType.vaccineTypeName}--</option>`;
            });

            // Reset the temporary variable
            injectionResultTempId = undefined;

            // Populate the Vaccine Name dropdown based on the selected Vaccine Type
            dropdownVaccineNameForInjectionResultReport();
        },
        error: function (xhr) {
            alert("Error at /api/injection-result/displayDropdown, error code: " + xhr.status);
        }
    });
}

function dropdownVaccineNameForInjectionResultReport() {
    $("#rp-injection-vaccinename")[0].innerHTML = '';
    const vaccineTypeValue = $("#rp-injection-vaccinetypename")[0].value;

    // Populate Vaccine Name dropdown based on selected Vaccine Type
    vaccineTypeDisplayObject.forEach(vaccineType => {
        if (vaccineType.vaccineTypeName === vaccineTypeValue) {
            vaccineType.vaccines.forEach(vaccine => {
                $("#rp-injection-vaccinename")[0].innerHTML += `<option value="${vaccine.vaccineName}">--${vaccine.vaccineName}--</option>`;
            });
        }
    });
}