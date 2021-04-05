const addButton = document.getElementById("add");
addButton.addEventListener("click", weiterleitenZuTermin)
const endButton = document.getElementById("endPlanningtime");

async function onLoad() {
    await kursNeuladen();

    const roles = await getRoles();
    showButtons(roles);
    if (!roles.includes(2)) {
        loadTable(roles);
    }
}

async function getRoles() {
    const url = "http://localhost:8080/process_role";
    const res = await fetch(url);
    const json = await res.json();
    return json;
}

function showButtons(roles) {
    if (roles.includes(3)) {
        endButton.style.display = "inline-block";
        addButton.style.display = "inline-block";
    }
}

async function kursNeuladen() {
    var calendarEl = document.getElementById('calendar');
    let url;
    let kurs = document.getElementById("kurs1");
    let kursValue = kurs.options[kurs.selectedIndex].value;

    if (kursValue == 0) {
        alert("Bitte wählen Sie einen Kurs aus.")
    } else {
        url = "http://localhost:8080/vorlesungsplaner/process_kalender?kurs=" + kursValue;
    }
    const res = await fetch(url);
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
    endButton.style.display = "none";
    addButton.style.display = "none";
}

async function loadTable(roles) {
    let course = document.getElementById("kurs1");
    let courseValue = course.options[course.selectedIndex].value;
    const url = "http://localhost:8080/vorlesungsplaner/process_kalendertable?kurs=" + courseValue;
    const table = document.getElementById("tabelle");
    let bool;

    const res = await fetch(url);
    const json = await res.json();

    roles.forEach(x => {
        if (x === 3) {
            bool = true;
        }
    })

    let i = 0;
    while (json[i] !== undefined) {
        let row = table.insertRow(i + 1);
        let cell1 = row.insertCell(0);
        let cell2 = row.insertCell(1);
        let cell3 = row.insertCell(2);
        let cell4 = row.insertCell(3);

        if (bool) {
            let cell5 = row.insertCell(4);
            let cell6 = row.insertCell(5);
            let cell7 = row.insertCell(6);

            const div1 = document.createElement("div");
            const bt1 = document.createElement("i");
            bt1.className = "fas fa-pencil-alt";
            div1.appendChild(bt1);
            const div2 = document.createElement("div");
            const bt2 = document.createElement("i");
            bt2.className = "fas fa-trash-alt";
            div2.appendChild(bt2);

            cell5.appendChild(div1);
            cell6.appendChild(div2);
            cell7.innerHTML = json[i].termin.ter_id;
            cell7.style.visibility = "hidden";
            roles.forEach(x => {
                if (x === 2) {
                    cell6.style.visibility = "hidden";
                    cell5.style.visibility = "hiden";
                }
            })
            cell5.onclick = function () {
                modifyRow(this);
            }
            cell6.onclick = function () {
                deleteRow(this);
            }
        }

        cell1.innerHTML = json[i].name;
        cell2.innerHTML = json[i].termin.ter_datum;
        cell3.innerHTML = json[i].termin.ter_start;
        cell4.innerHTML = json[i].termin.ter_ende;
        i++;
    }
    table.style.visibility = "visible";
}

async function modifyRow(cell) {
    const termin = cell.parentElement.cells[6].innerHTML;
    const url = "http://localhost:8080/vorlesungsplaner/termin_modify?terminId=" + termin;
    location.href = url;
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
