<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
<title>修改管理员信息</title>
<%@ include file="/WEB-INF/pages/include-head.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/pages/include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/pages/include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/main/page.html">首页</a></li>
                <li><a href="admin/query/for/search.html">数据列表</a></li>
                <li class="active">修改</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">用户信息<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"></div></div>
                <div class="panel-body">
                    <%--使用SpringMVC的<form:form标签> 标签使用详见:https://www.cnblogs.com/lixiang1993/articles/7417492.html--%>
                    <form:form action="admin/update.html" method="post" modelAttribute="admin" id="updateForm">
                        <%--模型对象中包含的属性就可以使用form:hidden--%>
                        <form:hidden path="id"/>
                        <%--模型对象没有包含的属性不能使用form:hidden--%>
                        <input type="hidden" name="pageNum" value="${param.pageNum}">
                        <%-- todo:前端使用js做表单验证，防止提交空数据--%>
                        <div class="form-group">
                            <label for="Account">登录账号</label>
                            <form:input path="loginAcct" cssClass="form-control" id="Account" name="Account"/>
                        </div>
                        <div class="form-group">
                            <label for="uPwd">登录密码</label>
                            <form:password path="userPwd" cssClass="form-control" id="uPwd" name="uPwd"/>
                        </div>
                        <div class="form-group">
                            <label for="uName">用户名称</label>
                            <form:input path="userName" cssClass="form-control" id="uName" name="uName"/>
                        </div>
                        <div class="form-group">
                            <label for="email">邮箱地址</label>
                            <form:input path="email" cssClass="form-control" id="email" name="email"/>
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-edit"></i> 修改</button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
