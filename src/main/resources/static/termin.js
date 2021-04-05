function getDate(){
    var today = new Date();
    document.getElementById("date").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);
}

async function ladeStunden(option) {
    await fetch("http://localhost:8080/vorlesungsplaner/getStunden?vorlesung=" + option.value)
        .then(response => response.json())
        .then(data => document.getElementById("stunden").innerHTML = data)
}

async function loadHoursModify() {
    const hours = document.getElementById("hours");
    const lecture = document.getElementById("lecture");

    await fetch("http://localhost:8080/vorlesungsplaner/getStunden?vorlesung=" + lecture.value)
        .then(response => response.json())
        .then(data => hours.innerHTML = data);
}

function validateForm() {
    var start = document.getElementById("start").value;
   // var startValue = start.options[start.selectedIndex].value;
    var end = document.getElementById("end").value;
   // var endValue = end.options[start.selectedIndex].value;
    start =  start.split(':');
    end =  end.split(':');

    totalSeconds1 = parseInt(start[0] * 3600 + start[1]);
    totalSeconds2 = parseInt(end[0] * 3600 + end[1]);
    console.log("start:" +start);
  //  console.log("startValue:"+startValue);
    if (totalSeconds2 - totalSeconds1 < 0) {
        alert("Die Endzeit muss vor der Startzeit sein.-");
        return false;
    }
}