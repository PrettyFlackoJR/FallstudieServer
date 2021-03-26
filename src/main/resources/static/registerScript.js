const addMore = document.getElementById("moreFields");

addMore.addEventListener("click", moreFields);


let counter = 0;
async function moreFields() {
    counter++;
    var newFields = document.getElementById('readroot').cloneNode(true);
    newFields.id = '';
    newFields.style.display = 'block';
    var newField = newFields.childNodes;
    for (var i=0;i<newField.length;i++) {
        var theName = newField[i].name
        if (theName)
            newField[i].name = theName + counter;
    }
    var insertHere = document.getElementById('abschicken');
    insertHere.parentNode.insertBefore(newFields,abschicken);
}
function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}
async function eingabeUeberpruefen() {
    let kurs = document.getElementById("kurs");
    let kursValue = kurs.options[kurs.selectedIndex].value;
    let vorlesung = document.getElementById("vorlesung");
    let vorlesungValue = kurs.options[vorlesung.selectedIndex].value;
    if (kursValue == 0 || vorlesungValue == 0) {
        alert("Bitte wählen Sie einen Kurs und eine Vorlesung aus.")
    }
}
async function objektUebergeben() {
    document.getElementsByName("f").act
}
