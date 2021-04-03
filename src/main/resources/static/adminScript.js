const baseURL = "http://localhost:8080/vorlesungsplaner";

async function startPlanning() {
    const url = baseURL + "/process_endplanning";
    const res = await fetch(url);
    alert("Die Planung wurde gestartet.");
    document.getElementById("startPlanning").onclick = function() { alert("Die Planung wurde bereits gestartet."); }

}

async function setPeriod() {
    const start = document.getElementById("start").value;
    const end = document.getElementById("end").value;
    const course = document.getElementById("kurs1");
    const kursValue = course.options[course.selectedIndex].value;
    const url = baseURL + "/admin/process_setperiod";
    const obj = {
        kurs: kursValue,
        start: start,
        end: end
    }

    const body = JSON.stringify(obj);
    const res = await fetch(url,{
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        body: body
    })
    if (res.status === 200) {
        alert("Zeitraum wurde für Kurs " + kursValue + " gesetzt.");
    } else alert("Es ist ein Fehler aufgetreten.");
}