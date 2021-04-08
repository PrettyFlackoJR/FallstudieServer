function getDate(){
    var today = new Date();
    document.getElementById("date").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);
}

async function loadHours(option) {
    await fetch("http://localhost:8080/vorlesungsplaner/gethours?vorlesung=" + option.value)
        .then(response => response.json())
        .then(data => document.getElementById("stunden").innerHTML = data)
}

async function loadHoursModify() {
    const hours = document.getElementById("hours");
    const lecture = document.getElementById("lecture");

    await fetch("http://localhost:8080/vorlesungsplaner/gethours?vorlesung=" + lecture.value)
        .then(response => response.json())
        .then(data => hours.innerHTML = data);
}

function validateForm() {
    let start = document.getElementById("start").value;
    let end = document.getElementById("end").value;
    start =  start.split(':');
    end =  end.split(':');

    totalSeconds1 = parseInt(start[0] * 3600 + start[1]);
    totalSeconds2 = parseInt(end[0] * 3600 + end[1]);
    if (totalSeconds2 - totalSeconds1 < 0) {
        alert("Die Endzeit muss nach der Startzeit liegen.");
        return false;
    }
}

async function deleteTermin() {
    const termin = document.getElementById("ter_id").value;
    const url = "http://localhost:8080/vorlesungsplaner/process_deletetermin/" + termin;
    const res = await fetch(url, {
        method: 'delete'
    });

    if (res.status === 200) {
        location.href="http://localhost:8080/vorlesungsplaner";
    }
}