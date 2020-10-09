<!doctype html>
<html lang="en">
<#include "../common/head.ftl">
<body>
<div id="app" style="margin: 20px 20%">
	<form action="/freemarker/user/info" method="post">
		姓名：<input type="text" name="name" placeholder="姓名"/>
		年龄：<input type="text" name="age" placeholder="年龄"/>
		<input type="submit" value="登录">
	</form>
</div>
</body>
</html>