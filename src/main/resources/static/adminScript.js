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
    const course = document.getElementById("course1");
    const coursValue = course.options[course.selectedIndex].value;

    const url = baseURL + "/admin/process_setperiod";
    const obj = {
        kurs: coursValue,
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
    const text = await res.text();
    if (res.status === 200) {
        alert("Zeitraum wurde für Kurs " + coursValue + " gesetzt.");
    } else alert(text);
}