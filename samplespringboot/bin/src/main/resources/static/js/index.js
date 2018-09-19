var http = new XMLHttpRequest();
var checkback = function () {
    if (http.readyState == 4) {
        var json = eval('(' + http.responseText + ')');
        if (json.data != null) {
            var user = json.data[0];
            document.getElementById("checklogin").innerText = user.name;
        }
    }

};
window.onload = function () {
    var ticket = getCookie("ticket");
    if (ticket == null)
        return;
    var url = '/login/checkstate';
    http.onreadystatechange = checkback;
    http.open("POST", url);
    http.setRequestHeader("Content-Type", "application/json");
    http.send(ticket)
};