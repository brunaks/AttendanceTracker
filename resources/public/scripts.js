$(function () {
    $('.datetimepicker').datetimepicker({
        format: 'LT'
    });
});

function load_menu() {
    $("#menu").load("menu.html");
};

function add_class() {

    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.status === 200 && request.readyState === 4) {
            alert("The class was added successfully");
        } else if (request.readyState === 4 && request.status != 200) {
            alert("Sorry, an error occurred...");
        }
    };

    request.open("POST", "addClass", true);
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    var paddedStartTime = document.getElementById("startTime").value;
    if (paddedStartTime.length < 8) {
        paddedStartTime = '0' + paddedStartTime;
    }

    var paddedEndTime = document.getElementById("endTime").value;
    if (paddedEndTime.length < 8) {
        paddedEndTime = '0' + paddedEndTime;
    }

    var selectedDay = document.getElementById('day');

    var formData = JSON.stringify(
        {
            className: document.getElementById('className').value,
            professorName: document.getElementById('professorName').value,
            startTime: paddedStartTime,
            endTime: paddedEndTime,
            day: selectedDay.options[selectedDay.selectedIndex].value
        }
    );

    request.send(formData);
}

function buildListOfClasses() {

    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.status === 200 && request.readyState === 4) {

            var classes = JSON.parse(request.responseText);
            buildHeader(classes);
            buildData(classes);

        } else if (request.readyState === 4 && request.status != 200) {
            alert("Sorry, an error occurred while loading classes");
        }
    };
    request.open("GET", "listClasses", true);
    request.send();
}

function buildHeader(classes) {
    var row = document.createElement("tr");
    var firstClass = classes[0];
    for (var key in firstClass) {
        var headerData = document.createElement("th");
        var headerText = document.createTextNode(key);
        headerData.appendChild(headerText);
        row.appendChild(headerData);
    }

    var table = document.getElementById("classes");
    var header = document.createElement("thead");
    header.appendChild(row);
    table.appendChild(header);
}

function buildData(classes) {

    var table = document.getElementById("classes");
    var body = document.createElement("tbody");
    table.appendChild(body);
    for (index = 0; index < classes.length; index++) {
        var oneClass = classes[index];
        var row = document.createElement("tr");
        for (var key in oneClass) {
            if (key === "schedules") {
                var schedules = oneClass[key];
                for (var arrayKey in schedules) {
                    var scheduleData = schedules[arrayKey];
                    var day = scheduleData.day;
                    var startTimeHours = scheduleData.startTimeHours.toString();
                    var startTimeMinutes = scheduleData.startTimeMinutes.toString();
                    var startTimePeriod = scheduleData.startTimePeriod.toString();
                    var endTimeHours = scheduleData.endTimeHours.toString();
                    var endTimeMinutes = scheduleData.endTimeMinutes.toString();
                    var endTimePeriod = scheduleData.endTimePeriod.toString();

                    startTimeHours = padTime(startTimeHours);
                    startTimeMinutes = padTime(startTimeMinutes);
                    endTimeHours = padTime(endTimeHours);
                    endTimeMinutes = padTime(endTimeMinutes);

                    var startTime = startTimeHours + ":" + startTimeMinutes + " " + startTimePeriod;
                    var endTime = endTimeHours + ":" + endTimeMinutes + " " + endTimePeriod;
                    var fullTime = startTime + " - " + endTime + " - " + day;

                    var data = document.createElement("td");
                    var text = document.createTextNode(fullTime);
                    data.appendChild(text);
                    row.appendChild(data);
                }
            } else {
                var data = document.createElement("td");
                var text = document.createTextNode(oneClass[key]);
                data.appendChild(text);
                row.appendChild(data);
            }
        }
        body.appendChild(row);
    }
}

function padTime(time) {
    if (time.length < 2) {
        return '0' + time;
    } else {
        return time;
    }
}
