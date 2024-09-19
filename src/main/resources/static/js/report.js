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

function switchDisplayType(type) {
    if (type === 'REPORT') {
        $('#report-section').show();
        $('#chart-section').hide();
    } else if (type === 'CHART') {
        $('#report-section').hide();
        $('#chart-section').show();
    }
}

    // Load years into the dropdown for the chart
    function loadYears() {
        // Example AJAX call to fetch available years for the chart
        $.ajax({
            url: '/api/report/getYears',
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

    // Fetch report data based on filters
function fetchReportData() {
        // Collect filter values
        const fromDate = $('input[type="date"][name="from"]').val();
        const toDate = $('input[type="date"][name="to"]').val();
        const vaccineTypeName = $('#rp-vaccinetypename').val();
        const vaccineName = $('#rp-vaccinename').val();

        // Example AJAX call to fetch report data
        $.ajax({
            url: '/api/report',
            method: 'GET',
            data: {
                fromDate: fromDate,
                toDate: toDate,
                vaccineTypeName: vaccineTypeName,
                vaccineName: vaccineName
            },
            success: function (reportData) {
                // Populate report table
                const tableContent = $('#report-injection-list-content');
                tableContent.empty();
                reportData.forEach(function (item, index) {
                    tableContent.append(`
                        <tr>
                            <td>${index + 1}</td>
                            <td>${item.vaccineName}</td>
                            <td>${item.vaccineTypeName}</td>
                            <td>${item.customerName}</td>
                            <td>${item.dateOfInjection}</td>
                            <td>${item.numberOfInjection}</td>
                        </tr>
                    `);
                });
            },
            error: function (error) {
                console.error('Error fetching report data:', error);
            }
        });
}

    // Fetch chart data based on the selected year
    function fetchChartData() {
        const year = $('#year').val();

        // Example AJAX call to fetch chart data
        $.ajax({
            url: '/api/chart',
            method: 'GET',
            data: { year: year },
            success: function (chartData) {
                // Use chartData to render the chart (e.g., using Chart.js)
                renderChart(chartData);
            },
            error: function (error) {
                console.error('Error fetching chart data:', error);
            }
        });
    }

    // Example function to render a chart using Chart.js
    function renderChart(chartData) {
        const ctx = document.getElementById('injectionChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: chartData.months,
                datasets: [{
                    label: 'Injection Results',
                    data: chartData.results,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    // Load initial data
    loadYears();

    // Bind filter button to fetch report data
    $('button[type="submit"]').on('click', function (e) {
        e.preventDefault(); // Prevent form submission
        fetchReportData();
    });

    // Bind change event to fetch chart data when year is selected
    $('#year').on('change', fetchChartData);
