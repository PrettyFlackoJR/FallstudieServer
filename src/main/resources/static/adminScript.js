const baseURL = "http://localhost:8080/vorlesungsplaner";

async function startPlanning() {
    let order;
    if (document.getElementById("planAscending1").checked) {
        order = "A";
    } else order = "D";

    let url = baseURL + "/admin/process_checkinitial"
    let res = await fetch(url);

    if (res.status == 200) {
        url = baseURL + "/process_endplanning?order=" + order;
        res = await fetch(url);

        if (res.status == 200) {
            alert("Die Planung wurde gestartet.");
        } else {
            alert("Bitte registrieren Sie die Dozenten bevor Sie die Planung starten.");
        }
    } else {
        alert("Die Planung läuft bereits.");
    }
}

async function setPeriod() {
    const start = document.getElementById("start").value;
    const end = document.getElementById("end").value;
    const kurs = document.getElementById("course1");
    const kursValue = kurs.options[kurs.selectedIndex].value;

    const url = baseURL + "/admin/process_setperiod";
    const obj = {
        kurs: kursValue,
        start: start,
        end: end
    }

    const body = JSON.stringify(obj);
    const res = await fetch(url, {
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        body: body
    })
    const text = await res.text();
    if (res.status === 200) {
        alert("Der angegebene Zeitraum wurde für Kurs " + kursValue + " gesetzt.");
    } else {
        alert(text);
    }
}