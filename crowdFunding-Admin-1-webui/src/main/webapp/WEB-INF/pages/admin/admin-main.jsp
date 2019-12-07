<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <title>控制面板</title>
    <%@ include file="/WEB-INF/pages/include-head.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/pages/include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/pages/include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">控制面板</h1>
            <div class="row placeholders">
                <%--有总经理角色才可以看到该标签--%>
                <security:authorize access="hasRole('总经理')">
                    <div class="col-xs-6 col-sm-3 placeholder">
                        <img src="/img/services-box1.jpg" class="img-responsive" alt="Generic placeholder thumbnail">
                        <a href="admin/query/for/search.html"><h4>用户维护</h4></a>
                        <span class="text-muted">管理用户信息</span>
                    </div>
                </security:authorize>
                <%--有查询角色权限才可以看到该标签--%>
                <security:authorize access="hasAuthority('role:get')">
                    <div class="col-xs-6 col-sm-3 placeholder">
                        <img src="/img/services-box2.jpg" class="img-responsive" alt="Generic placeholder thumbnail">
                        <a href="role/to/page.html"><h4>角色维护</h4></a>
                        <span class="text-muted">管理角色信息</span>
                    </div>
                </security:authorize>
            </div>
        </div>
    </div>
</div>
</body>
</html>
