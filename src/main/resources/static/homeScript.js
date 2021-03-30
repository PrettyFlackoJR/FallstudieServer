const addButton = document.getElementById("add");
addButton.addEventListener("click", weiterleitenZuTermin)
const endButton = document.getElementById("endPlanningtime");

async function onLoad() {
    kursNeuladen();
    showButtons();
    loadTable();
}
async function showButtons() {
    const url = "http://localhost:8080/process_role";
    const res = await fetch(url);
    const json = await res.json();

    json.forEach(x => {
        if (x === 3) {
            endButton.style.display = "inline-block";
            addButton.style.display = "inline-block";
        }
    })
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
async function endPlanning() {
    const url = "http://localhost:8080/vorlesungsplaner/process_endplanning";
    const res = await fetch(url);
    alert("Sie haben Ihre Planung beendet.");
    endButton.style.display="none";
    addButton.style.display="none";
}
async function loadTable() {
    const table = document.getElementById("tabelle");
    const kurs = document.getElementById("kurs1");
    const kursValue = kurs.options[kurs.selectedIndex].value;
    const url = "http://localhost:8080/vorlesungsplaner/process_kalendertable?kurs=" + kursValue;

    const res = await fetch(url);
    const json = await res.json();

    for (let i = 1; i < json.length+1; i++) {
        let row = table.insertRow(i);
        let cell1 = row.insertCell(0);
        let cell2 = row.insertCell(1);
        let cell3 = row.insertCell(2);
        let cell4 = row.insertCell(3);

        cell1.innerHTML = json[i].name;
        cell2.innerHTML = json[i].termin.ter_datum;
        cell3.innerHTML = json[i].termin.ter_start;
        cell4.innerHTML = json[i].termin.ter_ende;
    }
}
