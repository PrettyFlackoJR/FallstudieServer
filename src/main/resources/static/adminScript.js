async function startPlanning() {
    const url = "http://localhost:8080/vorlesungsplaner/process_endplanning";
    const res = await fetch(url);
    alert("Die Planung wurde gestartet.");
    document.getElementById("startPlanning").onclick = function() { alert("Die Plannung wurde bereits gestartet."); }
}