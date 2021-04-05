const addButton = document.getElementById("add");
addButton.addEventListener("click", weiterleitenZuTermin)
const endButton = document.getElementById("endPlanningtime");

async function onLoad() {
    const roles = await getRoles();
    await kursNeuladen(roles);
    showButtons(roles);
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

async function kursNeuladen(roles) {
    let url;
    let kurs = document.getElementById("kurs1");
    let kursValue = kurs.options[kurs.selectedIndex].value;
    url = "http://localhost:8080/vorlesungsplaner/process_kalender?kurs=" + kursValue;

    const res = await fetch(url);
    const json = await res.json();

    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        timeZone: 'CET',
        themeSystem: 'standard',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,listMonth'
        },
        weekNumbers: true,
        dayMaxEvents: true, // allow "more" link when too many events
        events: json,
        eventClick: function (info) {
            if (roles.includes(3)) {
                let eventObj = info.event;
                if (eventObj.backgroundColor === "red") {
                    modifyTermin(eventObj.extendedProps.ter_id);
                }
            }
        }
    });

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
    endButton.style.display = "none";
    addButton.style.display = "none";
}

async function modifyTermin(termin) {
    const url = "http://localhost:8080/vorlesungsplaner/termin_modify?terminId=" + termin;
    location.href = url;
}
