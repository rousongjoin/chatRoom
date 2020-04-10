<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>ChatRoom | 关于</title>
    <jsp:include page="commonfile.jsp"/>
</head>
<body>
<jsp:include page="head.jsp"/>
<div class="am-cf admin-main">
    <jsp:include page="sideLeft.jsp"/>

    <!-- content start -->
    <div class="admin-content">
        <div class="am-cf am-padding">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">关于</strong> / <small>about</small></div>
        </div>
        <div class="am-tabs am-margin" data-am-tabs>
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">所用技术</a></li>
                <li><a href="#tab2">获取源码</a></li>
                <li><a href="#tab3">关于作者</a></li>
            </ul>
            <div class="am-tabs-bd">
                <div class="am-tab-panel am-fade am-in am-active" id="tab1">
                    <hr>
                    <blockquote>
                        <p>ChatRoom主要使用Jsp、Servlet</p>
                        <p>通讯使用的是Session</p>
                        <p>数据库使用的是Mybatis</p>
                        <p>前端框架采用的是Amaze UI,弹窗控件和分页控件采用的是Layer和Laypage以及其他部分我觉得好看的样式</p>
                    </blockquote>
                </div>

                <div class="am-tab-panel am-fade am-in" id="tab2">
                    <hr>
                    <blockquote>
                        <p><a href="https://github.com/rousongjoin/chatRoom" target="_blank">github.com/rousongjoin/chatRoom</a></p>
                    </blockquote>
                </div>

                <div class="am-tab-panel am-fade am-in" id="tab3">
                    <hr>
                    <blockquote>
                        <p>欢迎访问我的github, <a href="https://github.com/rousongjoin" target="_blank">github.com/rousongjoin</a></p>
                    </blockquote>
                </div>
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