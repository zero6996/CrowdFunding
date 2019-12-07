<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
<title>新增管理员</title>
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
                    <li class="active">新增</li>
                </ol>
                <div class="panel panel-default">
                    <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                    <div class="panel-body">
                        <form role="form" action="admin/save.html" method="post">
                            <div class="form-group">
                                <label for="Account">登录账号</label>
                                <input type="text" class="form-control" id="Account" name="loginAcct" placeholder="请输入登录账号"/>
                            </div>
                            <div class="form-group">
                                <label for="uPwd">登录密码</label>
                                <input type="text" class="form-control" id="uPwd" name="userPwd" placeholder="请输入密码"/>
                            </div>
                            <div class="form-group">
                                <label for="uName">用户名称</label>
                                <input type="text" class="form-control" id="uName" name="userName" placeholder="请输入用户名称"/>
                            </div>
                            <div class="form-group">
                                <label for="uEmail">邮箱地址</label>
                                <input type="email" class="form-control" id="uEmail" name="email" placeholder="请输入邮箱地址"/>
                                <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                            </div>
                            <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                            <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
