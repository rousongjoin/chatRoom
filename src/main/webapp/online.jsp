<%@page import="service.UserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td height="32" align="center" class="word_orange ">欢迎来到心之语聊天室！</td>
		</tr>
		<tr>
			<td height="23" align="center">
			<a href="#" onclick="set('所有人')">所有人</a>
			</td>
		</tr>
		<c:forEach var="entry" items="${ usermap }">
			<tr>
				<td height="23" align="center">
				<a href="#" onclick="set('${ entry.key.nickname }')">${ entry.key.nickname }</a>
				<c:if test="${ user.type == 1 and entry.key.type == 0}">
				<a href="${pageContext.request.contextPath }/UserServlet?method=kick&id=${ entry.key.id }">踢下线</a>
				</c:if>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td height="30" align="center">当前在线[<font color="#FF6600">${ fn:length(usermap) }</font>]人
			</td>
		</tr>
	</table>
</body>
</html>