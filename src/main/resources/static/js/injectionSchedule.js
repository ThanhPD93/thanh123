function findAllInjectionSchedule(){
    $.ajax({
        url: "/injection-schedule/findAll",
        method: "GET",
        dataType: "json",
        success: function(injectionScheduleDtos){
            const tableBody = document.getElementById('injection-schedule-list');
            tableBody.innerHTML = '';
            injectionScheduleDtos.forEach(injectionScheduleDto => {
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
        url: "/injection-schedule/add",
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
