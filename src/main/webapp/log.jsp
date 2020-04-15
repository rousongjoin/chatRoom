<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>ChatRoom | 系统日志</title>
    <jsp:include page="commonfile.jsp"/>
</head>
<link rel="stylesheet" type="text/css" href="static/plugins/laypage/skin/laypage.css"/>
<script src="static/plugins/laypage/laypage.js"></script>
<script  type="text/javascript">
    $(function(){
        laypage({
            cont: 'page',
            pages: ${count},
            curr: function(){
                var page = location.search.match(/page=(\d+)/);
                return page ? page[1] : 1;
            }(),
            skin: '#AF0000',
            groups: 5, //连续显示分页数
            jump: function(e, first){ //触发分页后的回调
                if(!first){ //一定要加此判断，否则初始时会无限刷新
                    location.href = '?method=logUI&name=${user.name}?page='+e.curr;
                }
            }
        });
    });
</script>
<body>
<jsp:include page="head.jsp"/>
<div class="am-cf admin-main">
    <jsp:include page="sideLeft.jsp"/>

    <!-- content start -->
    <div class="admin-content">
        <div class="am-cf am-padding">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">系统日志</strong> / <small>log</small></div>
        </div>
        <div class="am-tabs am-margin">
            <table class="am-table am-table-striped">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>用户名</th>
                        <th>时间</th>
                        <th>类型</th>
                        <th>详情</th>
                        <th>IP</th>
                    </tr>
                </thead>
                <tbody>
                	<c:set var="startIndex" value="${fn:length(logs)-1}"></c:set>  
                    <c:forEach items="${logs}" var="log" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${logs[startIndex - status.index].name}</td>
                            <td>${logs[startIndex - status.index].time}</td>
                            <td>${logs[startIndex - status.index].logtype}</td>
                            <td>${logs[startIndex - status.index].detail}</td>
                            <td>${logs[startIndex - status.index].ip}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div id="page" style="float: right"></div>
        </div>
    </div>
    <!-- content end -->
</div>
<a href="#" class="am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}">
    <span class="am-icon-btn am-icon-th-list"></span>
</a>
<jsp:include page="footer.jsp"/>


</body>
</html>