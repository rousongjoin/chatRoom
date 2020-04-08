<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}
/* 登录页面 */

.index_bg {

	background: url(img/loginBg.jpg) 0 0 repeat-x;
}

.indexBox {
	width: 1000px;
	margin: 0 auto;
	background: url(img/login_bg.jpg) 0 0 no-repeat;
}

.indexHeader {
	padding-top: 102px;
	text-align: center;
	padding-bottom: 30px;
}

.indexHeader h1 {
	color: #fff;
	text-shadow: 2px 2px #000;
}

.indexCont {
	height: 284px;
	/*border: 1px solid red ;*/
	margin: 0 auto;
}

/* 登录界面 */
html {
	width: 100%;
	height: 100%;
	overflow: hidden;
	font-style: sans-serif;
}

.login_bg {
	width: 100%;
	height: 100%;
	font-family: 'Open Sans', sans-serif;
	margin: 0;
	background-color: #4A374A;
}

#login {
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -150px 0 0 -150px;
	width: 300px;
	height: 300px;
}

#login h1 {
	color: #fff;
	text-shadow: 0 0 10px;
	letter-spacing: 1px;
	text-align: center;
}

h1 {
	font-size: 2em;
	margin: 0.67em 0;
}

input {
	width: 278px;
	height: 18px;
	margin-bottom: 10px;
	outline: none;
	padding: 10px;
	font-size: 13px;
	color: #fff;
	text-shadow: 1px 1px 1px;
	border-top: 1px solid #312E3D;
	border-left: 1px solid #312E3D;
	border-right: 1px solid #312E3D;
	border-bottom: 1px solid #56536A;
	border-radius: 4px;
	background-color: #2D2D3F;
}

.but {
	width: 300px;
	min-height: 20px;
	display: block;
	background-color: #4a77d4;
	border: 1px solid #3762bc;
	color: #fff;
	padding: 9px 14px;
	font-size: 15px;
	line-height: normal;
	border-radius: 5px;
	margin: 0;
}
</style>

<title>登录界面</title>
</head>
<body class="login_bg">
	<div id="login">
		<h1>Login</h1>
		<form
			action="${pageContext.request.contextPath }/UserServlet?method=login"
			method="post">
			<div>
				<label for="name">用户名</label>
				<div>
					<input type="text" name="name" placeholder="请输入用户名">
				</div>
			</div>
			<div>
				<label for="password">密码</label>
				<div>
					<input type="password" name="password" placeholder="请输入密码">
				</div>
				<!--显示下面代码域对象里的信息,"用户名或密码错误"-->
				<%
				String msg = (String) request.getAttribute("inform");
			%>
				<span style="color: red; font-size: 20px"> <%=msg == null ? "" : msg%>
				</span>
			</div>
			<div>
				<label>验证码</label>
				<div>
					<input type="text" name="number" /> <img src="checkcode"
						border="1" onclick="this.src='checkcode?'+Math.random()">
					<%
					String msg2 = (String) request.getAttribute("number_error");
				%>
					<span style="color: red; font-size: 20px"> <%=msg2 == null ? "" : msg2%>
					</span>
				</div>
			</div>
			<button class="but" type="submit">登录</button>
		</form>
	</div>
</body>
</html>