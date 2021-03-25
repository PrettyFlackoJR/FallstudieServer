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