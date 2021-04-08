const addMore = document.getElementById("moreFields");
const list = document.querySelector(".item-list");
addMore.addEventListener("click", moreFields)
list.addEventListener("click", trashAndDone);
let array = [];
let counter = 0;
let jsonObject;
let kvs;

async function moreFields() {
    let kursValue = document.getElementById("kursListe").value;
    let vorlesungValue = document.getElementById("vorlesungsListe").value;
    let stundenValue = document.getElementById("stunden").value;
    let kursName = document.getElementById("kursListe").text;
    let vorlesungsName = document.getElementById("vorlesungsListe").innerHTML;

    if (document.getElementById("kursListe").value.length == 0 ||
        document.getElementById("vorlesungsListe").value.length == 0 ||
        document.getElementById("stunden").value.length == 0) {
        alert("Bitte geben Sie Kurs, Vorlesung und auch die Stunden an.");
        return false;
    }

    if (stundenValue < 1) {
        alert("Die Anzahl an Vorlesungsstunden darf nicht negativ oder 0 sein.");
        return false;
    }

    for (const a of array) {
        if (a.vvn_vor_id == vorlesungValue) {
            alert("Vorlesungen dürfen nicht doppelt vorkommen.");
            return false;
        }
    }

    function is_valid_datalist_value(idDataList, inputValue) {

        let option = document.querySelector("#" + idDataList + " option[value='" + inputValue + "']");
        if (option != null) {
            return option.value.length > 0;
        }
        return false;
    }

    if (is_valid_datalist_value('kurs', document.getElementById('kursListe').value) && is_valid_datalist_value('vorlesungen', document.getElementById('vorlesungsListe').value)) {
    } else {
        alert("Der angegebene Kurs/Vorlesung existiert nicht.");
        return false;
    }

    obj = {
        kurs_id: kursValue,
        vvn_vor_id: vorlesungValue,
        stnd: stundenValue
    }

    array.push(obj);
    helpObj = {
        kurs_name: kursName,
        vor_name: vorlesungsName,
        stnd: stundenValue
    }
    document.getElementById("kursListe").value = "";
    document.getElementById("vorlesungsListe").value = "";
    document.getElementById("stunden").value = "";

    loadItems();
}

async function register() {
    if (document.getElementById("vorname").value == "" ||
        document.getElementById("nachname").value == "" ||
        document.getElementById("email").value == "" ||
        document.getElementById("anrede").value == "" ||
        document.getElementById("passwort").value == "" ||
        array.length < 1) {
        alert("Bitte alle Felder ausfüllen!");
        return false;
    }
    kvs = JSON.stringify(array);
    kvs = array;
    jsonObject = {
        nut_vorname: document.getElementById("vorname").value,
        nut_nachname: document.getElementById("nachname").value,
        nut_email: document.getElementById("email").value,
        nut_anrede: document.getElementById("anrede").value,
        nut_titel: document.getElementById("titel").value,
        nut_passwort: document.getElementById("passwort").value,
        kvs: kvs
    }
    jsonObject = JSON.stringify(jsonObject);
    const res = await fetch("/vorlesungsplaner/admin/process_registerlecturer", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: jsonObject
    });
    const text = await res.text();
    if (res.status === 200) {
        location.href = "http://localhost:8080/register_success";
    } else alert(text);
}

async function loadItems() {
    const urlk = "http://localhost:8080/vorlesungsplaner/kursnamen";
    const resk = await fetch(urlk);
    const jsonk = await resk.json();

    const urlv = "http://localhost:8080/vorlesungsplaner/vorlesungsnamen";
    const resv = await fetch(urlv);
    const jsonv = await resv.json();

    clearChildren(list);
    for (const a of array) {
        const itemsDiv = document.createElement("div");
        itemsDiv.classList.add("items")
        //Item erstellen
        const newItem = document.createElement("li");
        for (const b of jsonk) {
            if (b.kurs_id == a.kurs_id) {
                newItem.innerText = "Kurs: " + b.kurs_name;

            }
        }
        newItem.classList.add("item");
        itemsDiv.appendChild(newItem);
        const newItem2 = document.createElement("li");
        for (const c of jsonv) {
            if (c.vor_id == a.vvn_vor_id) {
                newItem2.innerText = "Vorlesung: " + c.vor_name;
            }
        }

        newItem2.classList.add("item");
        itemsDiv.appendChild(newItem2);
        const newItem3 = document.createElement("li");
        newItem3.innerText = "Stunden: " + a.stnd;
        newItem3.classList.add("item");
        itemsDiv.appendChild(newItem3);
        //Loeschen Button
        const trash = document.createElement("button");
        trash.innerHTML = '<i class="fas fa-trash"></i>';
        trash.classList.add("trash-btn");
        itemsDiv.appendChild(trash);
        //AN UL anknüpfen
        list.appendChild(itemsDiv);

    }
}

function clearChildren(element) {
    while (element.firstElementChild != null) {
        element.removeChild(element.firstElementChild);
    }
}

async function trashAndDone(e) {
    const item = e.target;
    if (item.classList[0] == "trash-btn" || item.classList.value == "fas fa-trash") {
        let eintrag = item.parentElement;
        if (item.classList.value == "fas fa-trash") {
            eintrag = eintrag.parentElement;
        }
        eintrag.classList.add("fall");

        let removeIndex = array.map(function (item) {
            return item.vorlesungsId;
        }).indexOf(eintrag.childNodes[1].value);

        array.splice(removeIndex, 1);
        eintrag.addEventListener("transitionend", async function () {
            eintrag.remove();
        });
        loadItems();
    }
}

