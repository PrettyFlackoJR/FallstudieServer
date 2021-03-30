const addMore = document.getElementById("moreFields");
const list = document.querySelector(".item-list");
addMore.addEventListener("click", moreFields)
list.addEventListener("click", trashErledigt);

var array= [];
let counter = 0;
async function moreFields() {
    let kursValue = document.getElementById("kursListe").value;
    let vorlesungValue = document.getElementById("vorlesungsListe").value;
    let stundenValue = document.getElementById("stunden").value;
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
        if(a.vorlesungsId == vorlesungValue) {
            alert("Vorlesungen dürfen nicht doppelt vorkommen.");
            return false;
        }
    }

    obj = {
        kursId: kursValue,
        vorlesungsId: vorlesungValue,
        stunden: stundenValue
    }
    array.push(obj);
    document.getElementById("kursListe").value = "";
    document.getElementById("vorlesungsListe").value = "";
    document.getElementById("stunden").value = "";

    const body = JSON.stringify(array);
 /*   const res = await fetch("/register_process/" +title, {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: body
    });
    if(res.status == 400){
        alert("Bitte Eintrag machen.");
    }*/

    loadItems();


}
async function loadItems() {

    clearChildren(list);
    for (const a of array) {
        const itemsDiv = document.createElement("div");
        itemsDiv.classList.add("items")
        //Item erstellen
        const newItem = document.createElement("li");
        newItem.innerText = "KursId: "+a.kursId;
        newItem.classList.add("item");
        itemsDiv.appendChild(newItem);
        const newItem2 = document.createElement("li");
        newItem2.innerText = "VorlesungsID: "+a.vorlesungsId;
        newItem2.classList.add("item");
        itemsDiv.appendChild(newItem2);
        const newItem3 = document.createElement("li");
        newItem3.innerText = "Stunden: "+a.stunden;
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
function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
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
async function eingabeUeberpruefen() {
    let kurs = document.getElementById("kursListe");
    let kursValue = kurs.options[kurs.selectedIndex].value;
    let vorlesung = document.getElementById("vorlesungsListe");
    let vorlesungValue = kurs.options[vorlesung.selectedIndex].value;
    if (kursValue == 0 || vorlesungValue == 0) {
        alert("Bitte wählen Sie einen Kurs und eine Vorlesung aus.")
    }
}

