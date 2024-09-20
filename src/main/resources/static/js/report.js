
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
            } else if(filename === "report-customer.html") {
                $("#ajax-title").html("REPORT CUSTOMER");
            } else {
                $("#ajax-title").html("REPORT VACCINE");

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

function switchDisplayTypeCustmer(type) {
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
            labels: months, // Sử dụng tháng
            datasets: [{
                label: 'Injection Results',
                data: results, // Dữ liệu kết quả
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


function fetchChartDataCustomer() {
    const year = $('#year').val();

    if (!year) {
        console.error("No year selected.");
        return;
    }

    $.ajax({
        url: '/api/report/customer/chart',
        method: 'GET',
        data: { year: year },
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
                label: 'Vaccinated Customers',
                data: results,
                backgroundColor: 'rgba(153, 102, 255, 0.2)',
                borderColor: 'rgba(153, 102, 255, 1)',
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
$('#year').on('change', fetchChartData);
$('#year').on('change', fetchChartDataCustomer);
