<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="safe.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="commonfile.jsp"/>
<script type="text/javascript"
	src="${ pageContext.request.contextPath }/js/jquery-1.8.3.js">
	
</script>
<script type="text/javascript">
	//使用Jquery：js框架
	var title = "<span style='font-size:14px; line-height:30px;'>欢迎光临聊天室，请遵守聊天室规则，不要使用不文明用语。</span><br><span style='line-height:22px;'>";
	window.setInterval("showContent();", 1000);
	window.setInterval("showOnLine();", 10000);
	window.setInterval("check();", 1000);
	$(function() {
		showOnLine();
		showContent();
		check();
	});

	//检验用户在线情况
	function check() {
		$.post("${pageContext.request.contextPath}/UserServlet?method=check",
				function(data) {
					if (data == 1) {
						// 提示用户下线了
						alert("用户已经被踢下线了!");
						// 回到登录页面!
						window.location = "index.jsp";
					}
				});
	}

	//修改信息显示样式
	function checkScrollScreen() {
		if (!$("#scrollScreen").attr("checked")) {
			$("#content").css("overflow", "scroll");
		} else {
			$("#content").css("overflow", "hidden");
			//当聊天信息超过一屏时，设置最先发送的聊天信息不显示
			//alert($("#content").height());
			$("#content").scrollTop($("#content").height() * 2);
		}
		setTimeout('checkScrollScreen()', 500);
	}

	//退出
	function exit() {
		alert("欢迎您下次光临！");
		window.location.href = "${pageContext.request.contextPath}/UserServlet?method=logout";
	}

	//发送消息
	function send() {
		if (form1.to.value == "") {
			alert("请选择聊天对象！");
			return false;
		}
		if (form1.content.value == "") {
			alert("发送信息不可以为空！");
			form1.content.focus();
			return false;
		}
		// $("#form1").serialize():让表单中所有的元素都提交.
		$.post("${pageContext.request.contextPath}/UserServlet?"
				+ new Date().getTime(), $("#form1").serialize(),
				function(data) {
					$("#content").html(title + data + "</span>");
				});
	}

	// 显示聊天的内容
	function showContent() {
		$.post("${pageContext.request.contextPath}/UserServlet?"
				+ new Date().getTime(), {
			'method' : 'getMessage'
		}, function(data) {
			$("#content").html(title + data);
		});
	}

	//用来规范选择对象
	function set(selectPerson) { //自动添加聊天对象
		if (selectPerson != "${user.nickname}") {
			form1.to.value = selectPerson;
		} else {
			alert("不要和自己聊天，换个人选吧！");
		}
	}

	// 显示在线人员列表
	function showOnLine() {
		// 异步发送请求 获取在线人员列表
		// Jquery发送异步请求
		$.post("${pageContext.request.contextPath}/online.jsp?"
				+ new Date().getTime(), function(data) {
			// $("#online") == document.getElementById("online");
			$("#online").html(data);
		});
	}
</script>
<meta charset="UTF-8">
<title>聊天室</title>
</head>
<body>
	<jsp:include page="head.jsp"/>
	<div class="am-cf admin-main">
	<jsp:include page="sideLeft.jsp"/>
	<table width="778" height="276" border="0" align="center"
		cellpadding="0" cellspacing="0">
		<tr>
			<td width="165" valign="top" bgcolor="#f6fded" id="online"
				style="padding: 5px">在线人员列表</td>
			<td width="613" height="200px" valign="top" bgcolor="#FFFFFF"
				style="padding: 5px;">
				<div style="height: 290px; overflow: hidden" id="content">聊天内容</div>
			</td>
		</tr>
	</table>
	<table width="778" style="background-color: red" height="95" border="0"
		align="center" cellpadding="0" cellspacing="0" bordercolor="#D6D3CE">
		<form action="" id="form1" name="form1" method="post">
			<input type="hidden" name="method" value="sendMessage" />
			<tr>
				<td height="30" align="left">&nbsp;</td>
				<td height="37" align="left">
				<input name="from" type="hidden" value="${user.nickname}">
				[${user.nickname} ]对 
				<input name="to" type="text" value="" size="35" readonly="readonly"> 
				说：
				</td>
				<td width="189" align="left">&nbsp;&nbsp;字体颜色： <select
					name="color" size="1" id="select">
						<option selected>默认颜色</option>
						<option style="color: #FF0000" value="FF0000">红色热情</option>
						<option style="color: #0000FF" value="0000ff">蓝色开朗</option>
						<option style="color: #ff00ff" value="ff00ff">桃色浪漫</option>
						<option style="color: #009900" value="009900">绿色青春</option>
						<option style="color: #009999" value="009999">青色清爽</option>
						<option style="color: #990099" value="990099">紫色拘谨</option>
						<option style="color: #990000" value="990000">暗夜兴奋</option>
						<option style="color: #000099" value="000099">深蓝忧郁</option>
						<option style="color: #999900" value="999900">卡其制服</option>
						<option style="color: #ff9900" value="ff9900">镏金岁月</option>
						<option style="color: #0099ff" value="0099ff">湖波荡漾</option>
						<option style="color: #9900ff" value="9900ff">发亮蓝紫</option>
						<option style="color: #ff0099" value="ff0099">爱的暗示</option>
						<option style="color: #006600" value="006600">墨绿深沉</option>
						<option style="color: #999999" value="999999">烟雨蒙蒙</option>
				</select>
				</td>
				<td width="19" align="left"><input name="scrollScreen"
					type="checkbox" id="scrollScreen" onClick="checkScrollScreen()"
					value="1" checked></td>
			</tr>
			<tr>
				<td width="21" height="30" align="left">&nbsp;</td>
				<td width="549" align="left"><input name="content" type="text"
					size="70"
					onKeyDown="if(event.keyCode==13 && event.ctrlKey){send();}">
					<input name="Submit2" type="button" value="发送" onClick="send()">
				</td>
				<td align="right"><input name="button_exit" type="button"
					value="退出聊天室" onClick="exit()"></td>
				<td align="center">&nbsp;</td>
			</tr>
		</form>
	</table>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>