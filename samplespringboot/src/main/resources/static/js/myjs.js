var cookieMap = {};

function map2Str(map) {
    var temp = "";
    for (var key in map) {
        temp += key + "=" + map[key] + ";";
    }
    return temp;
}


var initCookie = function () {
    if (document.cookie == null) return;
    var temps = document.cookie.split(";");
    var spl;
    for (var s in temps) {
        spl = temps[s].split("=");
        if (spl != null && spl.length == 2)
            cookieMap[spl[0]] = spl[1];
    }
};

function setCookie(name, value) {
    cookieMap[name] = value;
    document.cookie = map2Str(cookieMap);
}

function getCookie(name) {
    initCookie();
    return cookieMap[name];
}