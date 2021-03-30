const addButton = document.getElementById("add");
addButton.addEventListener("click", weiterleitenZuTermin)
const endButton = document.getElementById("endPlanningtime");

async function onLoad() {
    let json = await kursNeuladen();
    console.log(json.events.length)
    showButtons();
    loadTable(json);
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
    return json;
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
async function loadTable(json) {
    const events = json.events;
    const table = document.getElementById("tabelle");

    let i = 1;
    while (events[i] !== undefined) {
        let row = table.insertRow(i);
        let cell1 = row.insertCell(0);
        let cell2 = row.insertCell(1);
        let cell3 = row.insertCell(2);
        let cell4 = row.insertCell(3);
        let cell5 = row.insertCell(4);
        let cell6 = row.insertCell(5);
        let cell7 = row.insertCell(6);

        const datum = events[i].start.split("T");
        const ende = events[i].end.split("T")[1];

        const div1 = document.createElement("div");
        const bt1 = document.createElement("i");
        bt1.className="fas fa-pencil-alt";
        div1.appendChild(bt1);
        const div2 = document.createElement("div");
        const bt2 = document.createElement("i");
        bt2.className="fas fa-trash-alt";
        div2.appendChild(bt2);

        cell1.innerHTML = events[i].title;
        cell2.innerHTML = datum[0];
        cell3.innerHTML = datum[1];
        cell4.innerHTML = ende;
        cell5.appendChild(div1);
        cell6.appendChild(div2);
        cell7.innerHTML = events[i].ter_id;
        cell7.style.visibility="hidden";
        cell5.onclick = function () { modifyRow(this);}
        cell6.onclick = function() { deleteRow(this);}
        i++;
    }
    table.style.visibility="visible";
}
async function modifyRow(cell) {
    const termin = cell.parentElement.cells[6].innerHTML;
    const url = "http://localhost:8080/vorlesungsplaner/termin_modify";
}
async function deleteRow(cell) {
    const termin = cell.parentElement.cells[6].innerHTML;
    const url = "http://localhost:8080/vorlesungsplaner/process_deleteTermin/" + termin;
    const res = await fetch(url, {
        method: 'delete'
    });
    if (res.status === 200) {
        alert("Der Termin wurde erfolgreich gelöscht.");
        location.reload();
    }
}
