const refreshButton = document.getElementById("refresh");
refreshButton.addEventListener("click", kursNeuladen);

async function onLoad() {
    var calendarEl = document.getElementById('calendar');

    const url = "http://localhost:8080/vorlesungsplaner/kalender/process_kalender";
    const res = await fetch(url);
    const json = await res.json();

    var calendar = new FullCalendar.Calendar(calendarEl, json);

    calendar.render();
}
async function kursNeuladen() {
    var calendarEl = document.getElementById('calendar');

    let kurs = document.getElementById("kurs");
    let kursValue = kurs.options[kurs.selectedIndex].value;
    const url = "http://localhost:8080/vorlesungsplaner/kalender/process_kalender?kurs="+kursValue;
    const res = await fetch(url);
    console.log(url);
    const json = await res.json();

    var calendar = new FullCalendar.Calendar(calendarEl, json);

    calendar.render();
}