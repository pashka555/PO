var LETTERS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';

var messages = [
    {nickname:"Commenter1", content:"LOREM IPSUM DOLOR SIT AMET"},
    {nickname:"Commenter2", content:"LOREM IPSUM DOLOR SIT AMET"},
    {nickname:"HAX0R", content:"SOOBT IMPBM ALSOO PPT XJLT"}
];

//function refreshMessageList() {
//   var target = document.getElementById("messagelist");
//    target.innerHTML = "";
//    var xhttp = new XMLHttpRequest();
//    xhttp.onreadystatechange = function() {
//        if (this.readyState == 4) {
//        messages = this.response;
        //var parsedJSON = JSON.parse(this.response);
        //console.log(parsedJSON[0])
        //messages = JSON.parse(this.responseText);
//        messages.forEach(element => {
//            addMessage(target,element.content);
//        });
//        }
//    };
//    xhttp.open("GET", "http://localhost:8080/load", true);
//    xhttp.send();/
//}


function refreshMessageList() {
    var target = document.getElementById("messagelist");
    messages.forEach(element => {
        addMessage(target,element.content);
    });
     
}

function addMessage(target, content) {
        var child = document.createElement('div');
        child.className = "message";

        var textField = document.createElement("p");
        textField.className = "text-field";
        textField.innerHTML = content;
        child.appendChild(textField);

        var decryptSpan = document.createElement("span");
        var decrButton = document.createElement("button");
        decrButton.type = "button";
        decrButton.innerHTML = "DECRYPT";
        decrButton.onclick = function() {
            decryptMessage(child);
         };
        decryptSpan.appendChild(decrButton);

        var keyInput = document.createElement("input");
        keyInput.type = "text";
        keyInput.className = "key-input";
        decryptSpan.appendChild(keyInput);

        child.appendChild(decryptSpan);

        target.appendChild(child);
        
        //alert(decryptSpan.children[0] == decrButton);

}

function vigenerEncrypt() {
    var text = document.getElementById("message-input").value;
    var key = document.getElementById("key-input").value
    //var key = "HAXX";
    key = key.toUpperCase();
    var translatedtext = "";
    var keyIndex = 0;
    for(var  i = 0; i < text.length; i++) {
        num = LETTERS.indexOf(text.charAt(i).toUpperCase());
        if (num != -1) {
            num += LETTERS.indexOf(key.charAt(keyIndex));
            num %= LETTERS.length;

        translatedtext = translatedtext + LETTERS.charAt(num);

        keyIndex += 1
        if (keyIndex == key.length) {
            keyIndex = 0;
        } 

        } else translatedtext = translatedtext + text.charAt(i);
    } 

    messages.push({nick:"DAY", content:translatedtext});

    var target = document.getElementById("messagelist");

    addMessage(target,translatedtext);
}

function decryptMessage(child) {

    var text = child.children[0].innerHTML;
    var key = child.children[1].children[1].value;
    //var key = "HAXX";
    key = key.toUpperCase();
    var translatedtext = "";
    var keyIndex = 0;
    for(var  i = 0; i < text.length; i++) {
        num = LETTERS.indexOf(text.charAt(i).toUpperCase());
        if (num != -1) {
            num -= LETTERS.indexOf(key.charAt(keyIndex));
            if (num < 0) {
                num = LETTERS.length + num;
            }
            num %= LETTERS.length;

        translatedtext = translatedtext + LETTERS.charAt(num);

        keyIndex += 1
        if (keyIndex == key.length) {
            keyIndex = 0;
        } 

        } else translatedtext = translatedtext + text.charAt(i);
    } 
    
    //messages.push({nick:"DAY", content:translatedtext});

    var translatedTextContainer = document.createElement("p");
    translatedTextContainer.innerHTML = translatedtext;
    
    child.appendChild(translatedTextContainer);
    child.children[0].style.visibility = "hidden";
    child.children[1].style.visibility = "hidden";


    var destrButton = document.createElement("button");
        destrButton.type = "button";
        destrButton.innerHTML = "CANCEL";
        destrButton.onclick = function() {
            child.children[0].style.visibility = "visible";
            child.children[1].style.visibility = "visible";

            child.removeChild(child.children[3]);
            child.removeChild(child.children[2]);
         };
        child.appendChild(destrButton);

    

    addMessage(target,translatedtext);
}
