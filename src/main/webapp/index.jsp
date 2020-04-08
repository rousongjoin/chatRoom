<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head lang="en">
<style type="text/css">
body
{
	margin:0;
	padding: 0;
	font-family: sans-serif;
	background: url(img/timg.gif);
	background-size: cover;
}
.box
{
	position: absolute;
	top:50%;
	left: 50%;
	transform: translate(-50%,-50%);
	width:400px;
	padding: 40px;
	background: rgba(0,0,0,.8)
	box-sizing : border-box;
	box-shadow: 0 15px 25px rgba(0,0,0,.5);
	border-radius: 10px;
}

.box h2
{

	color: #fff;
	text-shadow: 0 0 10px;
	letter-spacing: 1px;
	text-align: center;
}
.nav {
	width: 300px;
	height: 80px;
	margin: 100px auto;
	border: 0px solid #000;
}

ul {
	list-style: none;
}

ul>li {
	float: left;
	width: 120px;
	height: 40px;
	line-height: 40px;
	text-align: center;
	list-style:none;
}

.nav a {
	display: block;
	width: 120px;
	height: 40px;
	text-decoration: none;
	color: blue;
	background-color:#6698cb;
}

.nav a:hover {
	background-color: orangered;
}
	
</style>

<meta charset="UTF-8">
<title>主页</title>
</head>
<body>
	<div class ="box">
		<h2>欢迎来到聊天室</h2>
			<div class="nav">
				<ul>
					<li><a
						href="${pageContext.request.contextPath }/UserServlet?method=loginUI">登录</a></li>
					<li><a
						href="${pageContext.request.contextPath }/UserServlet?method=registUI">注册</a></li>
				</ul>
			</div>
	</div>
</body>
</html>