const addButton = document.getElementById("add");
addButton.addEventListener("click", weiterleitenZuTermin)

async function onLoad() {
    var calendarEl = document.getElementById('calendar');
    const url = "http://localhost:8080/vorlesungsplaner/process_kalender";
    const res = await fetch(url);
    const json = await res.json();

    var calendar = new FullCalendar.Calendar(calendarEl, json);

    calendar.render();
    kursNeuladen();
}
async function kursNeuladen() {
    var calendarEl = document.getElementById('calendar');
    let url;
    let kurs = document.getElementById("kurs1");
    let kursValue = kurs.options[kurs.selectedIndex].value;
    console.log(kursValue);
    if (kursValue == 0) {
        alert("Bitte wählen Sie einen Kurs aus.")
    } else {
         url = "http://localhost:8080/vorlesungsplaner/process_kalender?kurs=" + kursValue;
    }
    const res = await fetch(url);
    console.log(url);
    const json = await res.json();

    var calendar = new FullCalendar.Calendar(calendarEl, json);

    calendar.render();
}
async function weiterleitenZuTermin() {
    let kurs = document.getElementById("kurs1");
    let kursValue = kurs.options[kurs.selectedIndex].value;
    if (kursValue == 0) {
        alert("Bitte wählen Sie einen Kurs aus.")
    } else {
        const url = "http://localhost:8080/vorlesungsplaner/termin_add?kurs=" + kursValue;
        location.href = url;
    }
}
