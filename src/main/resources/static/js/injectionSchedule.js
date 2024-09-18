// from dashboard
function fetchInjectionSchedule(filename){
    $.ajax({
        url: "/api/injection-schedule/getAjax",
        data: {filename: filename},
        success: function(data) {
            document.getElementById('ajax-content').innerHTML = data;
            if(filename === "injection-schedule-list.html") {
            	$("#ajax-title")[0].innerHTML = "INJECTION SCHEDULE LIST";
            	findAllInjectionSchedule(0);
            } else {
            	$("#ajax-title")[0].innerHTML = "CREATE INJECTION SCHEDULE";
            }
        },
        error: function() {
            alert("error add fetchInjectionSchedule function");
        }
    });
}

function findAllInjectionSchedule(page){
    const query = $("#searchInput")[0].value;
    const pageSize = parseInt($("#dropdownMenuButton")[0].innerHTML, 10);
    $.ajax({
        url: "/api/injection-schedule/findAll",
        data: {
        	searchInput: query,
        	page: page,
        	size: pageSize
        },
        success: function(injectionSchedules){
            const tableBody = $("#injection-schedule-list")[0];
            tableBody.innerHTML = "";
            injectionSchedules.content.forEach(injectionScheduleDto => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td><a href="#" class="link-offset-2 link-underline link-underline-opacity-0" onclick="">${injectionScheduleDto.vaccineFromInjectionSchedule.vaccineName}</a></td>
                    <td>From <strong>${injectionScheduleDto.startDate}</strong> to <strong>${injectionScheduleDto.endDate}</strong></td>
                    <td>${injectionScheduleDto.place}</td>
                    <td>${injectionScheduleDto.status}</td>
                    <td>${injectionScheduleDto.injectionScheduleDescription}</td>
                `;
                tableBody.appendChild(row);
            });
            updatePaginationControls(injectionSchedules.number, injectionSchedules.totalPages, pageSize, injectionSchedules.totalElements);
        },
        error: function(){
            alert("error findAll");
        }
    });
}

function resetInjectionSchedule(){
    $("#injection-schedule-create-form")[0].reset();
}

function addInjectionSchedule(){

    const injectionSchedule = {
        injectionScheduleId: "abcde",
        injectionScheduleDescription: $("#note")[0].value,
        endDate: $("#to")[0].value,
        place: $("#place")[0].value,
        startDate: $("#from")[0].value,
        vaccineName: $("#vaccine")[0].value
    };

    $.ajax({
        url: "/api/injection-schedule/add",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(injectionSchedule),
        dataType: "text",
        success: function(text) {
            alert(text);
            if(text === "add success") {
                resetInjectionSchedule();
            }
        },
        error: function(){
            alert("error adding injectionSchedule from back-end");
        }
    });
}
