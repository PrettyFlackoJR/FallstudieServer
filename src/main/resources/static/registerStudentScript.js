async function registerStudent() {
    if (document.getElementById("vorname").value == "" ||
        document.getElementById("nachname").value == "" ||
        document.getElementById("email").value == "" ||
        document.getElementById("anrede").value == "" ||
        document.getElementById("passwort").value == "") {
        return false;
    }
    if(document.getElementById("kursListe").value.length == 0) {
        alert("Kurs eingeben");
        return false;
    }
    function is_valid_datalist_value(idDataList, inputValue) {

        var option = document.querySelector("#" + idDataList + " option[value='" + inputValue + "']");
        if (option != null) {
            return option.value.length > 0;
        }
        return false;
    }

    if (is_valid_datalist_value('kurs', document.getElementById('kursListe').value)) {
    } else {
        alert("Invalid");
        return false;
    }
    jsonObject = {
        nut_vorname: document.getElementById("vorname").value,
        nut_nachname: document.getElementById("nachname").value,
        nut_email: document.getElementById("email").value,
        nut_anrede: document.getElementById("anrede").value,
        nut_titel: null,
        nut_passwort: document.getElementById("passwort").value,
    }
    jsonObject = JSON.stringify(jsonObject);
    let kursId = document.getElementById("kursListe").value;
    const res = await fetch("/vorlesungsplaner/admin/process_registerstudent?kursId="+kursId, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: jsonObject
    });
    const text = await res.text();
    if (res.status === 200) {
        location.href = "http://localhost:8080/register_success";
    } else {
        document.getElementById("error").style.display="block";
        document.getElementById("error").innerHTML=text;
    }
}