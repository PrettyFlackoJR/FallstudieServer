const addMore = document.getElementById("moreFields");
const list = document.querySelector(".item-list");
const abschicken = document.getElementById("abschicken");
addMore.addEventListener("click", moreFields)
list.addEventListener("click", trashErledigt);
abschicken.addEventListener("click", register);
let array= [];
let counter = 0;
let jsonObject;
let kvs;
async function moreFields() {
    let kursValue = document.getElementById("kursListe").value;
    let vorlesungValue = document.getElementById("vorlesungsListe").value;
    let stundenValue = document.getElementById("stunden").value;
    let kursName = document.getElementById("kursListe").innerHTML;
    let vorlesungsName = document.getElementById("vorlesungsListe").innerText;
    console.log(array);
    if(document.getElementById("kursListe").value.length == 0 || document.getElementById("vorlesungsListe").value.length == 0 || document.getElementById("stunden").value.length == 0) {
        alert("Sachen eingeben");
        return false;
    }
    if (stundenValue < 1) {
        alert("Stunden dürfen nicht negativ oder 0 sein.");
        return false;
    }
    for(const a of array) {
        if(a.vvn_vor_id == vorlesungValue) {
            alert("Vorlesungen dürfen nicht doppelt vorkommen.");
            return false;
        }
    }
    function is_valid_datalist_value(idDataList, inputValue) {

        var option = document.querySelector("#" + idDataList + " option[value='" + inputValue + "']");
        if (option != null) {
            return option.value.length > 0;
        }
        return false;
    }

    if (is_valid_datalist_value('kurs', document.getElementById('kursListe').value) && is_valid_datalist_value('vorlesungen', document.getElementById('vorlesungsListe').value)) {
    } else {
        alert("Invalid");
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
    console.log(helpObj);

    document.getElementById("kursListe").value = "";
    document.getElementById("vorlesungsListe").value = "";
    document.getElementById("stunden").value = "";

    loadItems();


}
async function register(e) {
    if ( nut_vorname: document.getElementById("vorname").value == "" ||
        nut_nachname: document.getElementById("nachname").value == "" ||
        nut_email: document.getElementById("email").value == "" ||
        nut_anrede: document.getElementById("anrede").value == "" ||
        nut_passwort: document.getElementById("passwort").value == "") {
        return false;
    }
    e.preventDefault();
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
    console.log(jsonObject);
       // const url = "http://localhost:8080/vorlesungsplaner/admin/process_registerdozent";
        const res = await fetch("/vorlesungsplaner/admin/process_registerdozent", {
       method: "POST",
       headers: {
           "Content-Type": "application/json"
       },
       body: jsonObject
   });
   if(res.status == 200){
       location.href = "http://localhost:8080/register_success";
   }


}

async function loadItems() {

    clearChildren(list);
    for (const a of array) {
        const itemsDiv = document.createElement("div");
        itemsDiv.classList.add("items")
        //Item erstellen
        const newItem = document.createElement("li");
        newItem.innerText = "KursId: "+ a.kurs_id;
        newItem.classList.add("item");
        itemsDiv.appendChild(newItem);
        const newItem2 = document.createElement("li");
        newItem2.innerText = "VorlesungsID: "+ a.vvn_vor_id;
        newItem2.classList.add("item");
        itemsDiv.appendChild(newItem2);
        const newItem3 = document.createElement("li");
        newItem3.innerText = "Stunden: "+a.stnd;
        newItem3.classList.add("item");
        itemsDiv.appendChild(newItem3);
        //Loeschen Button
        const trash = document.createElement("button");
        trash.innerHTML = '<i class="fas fa-trash"></i>';
        trash.classList.add("trash-btn");
        itemsDiv.appendChild(trash);
        //AN UL anknüpfen
        list.appendChild(itemsDiv);

        helpObj = {
        }
    }
}
function clearChildren(element) {
    while (element.firstElementChild != null) {
        element.removeChild(element.firstElementChild);
    }
}
async function trashErledigt(e) {
    const item = e.target;
    //ITEM loeschen
    if (item.classList[0] == "trash-btn" || item.classList.value == "fas fa-trash") {
        let eintrag = item.parentElement;
        if (item.classList.value == "fas fa-trash") {
            eintrag = eintrag.parentElement;
        }
        eintrag.classList.add("fall");

// get index of object with id:37
        var removeIndex = array.map(function(item) { return item.vorlesungsId; }).indexOf(eintrag.childNodes[1].value);

// remove object
        array.splice(removeIndex, 1);
        eintrag.addEventListener("transitionend", async function () {
            eintrag.remove();
        });
        loadItems();
    }
}

