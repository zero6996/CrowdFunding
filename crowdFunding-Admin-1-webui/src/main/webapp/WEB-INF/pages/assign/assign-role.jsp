<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="UTF-8">
<title>角色分配页面</title>
<%@ include file="/WEB-INF/pages/include-head.jsp"%>
<script type="text/javascript">
    $(function () {
        // 将左边选择框中选中的数据追加到右边选择框
        $("#rightBtn").click(function () {
            $("#leftSelect>option:selected").appendTo("#rightSelect");
        });
        $("#leftBtn").click(function () {
            $("#rightSelect>option:selected").appendTo("#leftSelect");
        });
        $("#submitBtn").click(function () {
            $("#rightSelect>option").prop("selected","selected");
        });


    });
</script>
<body>
<%@ include file="/WEB-INF/pages/include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/pages/include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <!-- 各个页面具体内容 -->
            <ol class="breadcrumb">
                <li><a href="/admin/to/main/page.html">首页</a></li>
                <li><a href="/admin/query/for/search.html">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form action="assign/do/assign/role.html" method="post" role="form" class="form-inline">
                        <input type="hidden" name="adminId" value="${param.adminId }" />
                        <input type="hidden" name="pageNum" value="${param.pageNum }" />
                        <div class="form-group">
                            <label>未分配角色列表</label><br>
                            <select
                                    id="leftSelect" class="form-control" multiple size="10"
                                    style="width: 100px; overflow-y: auto;">

                                <c:forEach items="${requestScope.unAssignedRoleList }" var="role">
                                    <option value="${role.id }">${role.name }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="rightBtn"
                                    class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="leftBtn"
                                    class="btn btn-default glyphicon glyphicon-chevron-left"
                                    style="margin-top: 20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left: 40px;">
                            <label>已分配角色列表</label><br>
                            <select
                                    id="rightSelect" name="roleIdList" class="form-control"
                                    multiple size="10" style="width: 100px; overflow-y: auto;">

                                <c:forEach items="${requestScope.assignedRoleList }" var="role">
                                    <option value="${role.id }">${role.name }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button id="submitBtn" type="submit" style="width: 200px;"
                                class="btn btn-success btn-lg btn-block">分配</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>