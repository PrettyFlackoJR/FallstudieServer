const baseURL = "http://localhost:8080/vorlesungsplaner";

async function startPlanning() {
    const url = baseURL + "/process_endplanning";
    const res = await fetch(url);
    alert("Die Planung wurde gestartet.");
    document.getElementById("startPlanning").onclick = function() { alert("Die Plannung wurde bereits gestartet."); }
}

async function setPeriod() {
    const start = document.getElementById("start").value;
    const end = document.getElementById("end").value;
    const course = document.getElementById("kurs1");
    const kursValue = course.options[course.selectedIndex].value;
    const url = baseURL + "/process_setperiod";

    const res = await fetch(url, {
        method: 'post',
        body: {
            "kurs": kursValue,
            "start": start,
            "end": end
        }
    })

}