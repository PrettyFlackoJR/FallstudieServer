function getDate(){
    var today = new Date();

    document.getElementById("date").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);


}

async function ladeStunden() {
    let kurs = document.getElementById("kurs");
    let kursValue = kurs.options[kurs.selectedIndex].value;
    const url = "http://localhost:8080/vorlesungsplaner/getStunden?vorlesung="+kursValue;
    const res = await fetch(url);
    fetch("http://localhost:8080/vorlesungsplaner/getStunden?vorlesung="+kursValue)
        .then(response => response.json())
        .then(data => document.getElementById("stunden").innerHTML = data)
    console.log(res);
}