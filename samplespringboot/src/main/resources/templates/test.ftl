<html>
<head>
    <title>freemarker测试</title>
    <link rel="stylesheet" type="text/css" href="/static/base.css">
</head>
<body>
<#--<h1>${name},${age},${level},${sign}</h1>-->
<div class="navigation_area">
    <ul>
        <li><a href="/search">搜索</a></li>
        <li><a href="/login">登录</a></li>
        <li>|</li>
        <li><a href="/register">注册</a></li>
    </ul>

</div>
<div class="article_title">

</div>
<div class="article_area">
    <#list map?keys as k>
        <h1>${k}:${map[k]}</h1>
    </#list>
</div>
</body>
</html>