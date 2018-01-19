var url = "/login";
var http = new XMLHttpRequest;

function callback() {
    if (http.readyState == 4) {
        var data = eval('(' + http.responseText + ')');
        if (data.data != null) {
            setCookie("ticket",data.data[0].password);
        }
        document.getElementById("loginstate").innerText = http.responseText
    }
}

function login() {
    var name = document.getElementsByName("username")[0].value;
    var passwd = document.getElementsByName("passwd")[0].value;
    var json = '{"username": "' + name + '", "passwd":"' + passwd + '"}';
    http.onreadystatechange = callback;
    http.open("POST", url);
    http.setRequestHeader("Content-Type", "application/json");
    http.send(json)
}
