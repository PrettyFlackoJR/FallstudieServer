const refreshButton = document.getElementById("refresh");
const addButton = document.getElementById("add");
refreshButton.addEventListener("click", kursNeuladen);
addButton.addEventListener("click", weiterleitenZuTermin)

async function onLoad() {
    var calendarEl = document.getElementById('calendar');

    const url = "http://localhost:8080/vorlesungsplaner/process_kalender?kurs=A";
    const res = await fetch(url);
    const json = await res.json();

    var calendar = new FullCalendar.Calendar(calendarEl, json);

    calendar.render();
}
async function kursNeuladen() {
    var calendarEl = document.getElementById('calendar');

    let kurs = document.getElementById("kurs1");
    let kursValue = kurs.options[kurs.selectedIndex].value;
    console.log(kursValue);
    const url = "http://localhost:8080/vorlesungsplaner/process_kalender?kurs="+kursValue;
    const res = await fetch(url);
    console.log(url);
    const json = await res.json();

    var calendar = new FullCalendar.Calendar(calendarEl, json);

    calendar.render();
}
async function weiterleitenZuTermin() {
    let kurs = document.getElementById("kurs1");
    let kursValue = kurs.options[kurs.selectedIndex].value;
    const url = "http://localhost:8080/vorlesungsplaner/termin_add?kurs="+kursValue;
    location.href = url;
}
