$.ajaxSetup({
    contentType: "application/x-www-form-urlencoded; charset=utf-8"
});

function save() {
    var json = {};
    var title = $("#_title").val();
    if (title == "") {
        alert("文章标题不能为空！");
        return;
    }
    var content = $("#content").html();
    if (content == "") {
        alert("文章内容不能为空！");
        return;
    }
    json["title"] = title;
    json["keywords"] = $("#_keywords").val();
    json["content"] = content;
    json["articleId"] = $("#article_id").val();
    $.post('/addArticle', json, function (data) {
            var d = JSON.parse(data);
            alert(d.msg);
            if (d.data != null) {
                if ($("#article_id")[0]) {
                } else {
                    var input = document.createElement("input");
                    input.type = "hidden";
                    input.id = "article_id";
                    input.value = d.data[0].id;
                    $("#function_line").append(input);
                }
            }
        }
    );
}