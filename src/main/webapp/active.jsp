<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>验证激活</title>
</head>
<script>
function GetQueryString(name) { 
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
    var r = window.location.search.substr(1).match(reg);  //获取url中"?"符后的字符串并正则匹配
    var context = ""; 
    if (r != null) 
         context = r[2]; 
    reg = null; 
    r = null; 
    return context == null || context == "" || context == "undefined" ? "" : context; 
}

var msg = '<%=request.getAttribute("msg")%>';
	alert(msg);
 

</script>
<body>
		
<form action="${pageContext.request.contextPath }/UserServlet?method=active"
		method="post">
		<%session.setAttribute("code", request.getParameter("code")); %>
<label for="code1">请输入激活码</label>
			<div>
				<input type="text" name="code1" placeholder="请输入激活码">
			</div>
			<input type="submit" value="激活">
</form>
</body>
</html>
