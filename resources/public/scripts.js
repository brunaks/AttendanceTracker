$(function () {
    $('.datetimepicker').datetimepicker({
        format: 'LT'
    });
});

function add_class() {

    var request = new XMLHttpRequest();
    request.onreadystatechange = function() {
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

    var formData = JSON.stringify(
        {
            className: document.getElementById('className').value,
            professorName: document.getElementById('professorName').value,
            startTime: paddedStartTime,
            endTime: paddedEndTime
        }
    );

    request.send(JSON.stringify(formData));
}