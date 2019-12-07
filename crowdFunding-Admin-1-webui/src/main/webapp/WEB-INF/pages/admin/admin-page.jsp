<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
<title>用户维护</title>
<%@ include file="/WEB-INF/pages/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/admin.js"></script>
<script type="text/javascript">
    $(function(){
        // 初始化全局变量
        window.totalRecord = ${requestScope['PAGE-INFO'].total};
        window.pageSize = ${requestScope['PAGE-INFO'].pageSize};
        window.pageNum =${requestScope['PAGE-INFO'].pageNum};
        //每一次页面最初显示时都会把keyword设置为最新值
        window.keyword = "${param.keyword}";

        // 对分页导航条显示进行初始化
        initPagination();

        // 全选/全不选功能
        $("#summaryBox").click(function () {
            // this代表当前多选框对象,checked属性为true表示被勾选，false表示未勾选
            // 获取所有的选项框对象，将其checked属性状态设置跟摘要框一致
            $(".itemBox").prop("checked",this.checked);
        });

        // 批量删除功能
        $("#batchRemoveBtn").click(function () {
            // 创建数组对象：存储adminID
            var adminIDArray = new Array();
            // 创建数组对象：存储账号信息
            var loginAcctArray = new Array();

            // 通过jQuery选择器定位到所有被选中的itemBox，然后遍历
            $(".itemBox:checked").each(function () {
                // this.adminID拿不到值，原因是：this作为DOM对象无法读取HTML标签本身没有的属性
                // 将this转换为jQuery对象调用attr()函数就能拿到值
                var adminID = $(this).attr("adminID");
                adminIDArray.push(adminID);
                <%-- <td><input type="checkbox"></td><td>loginAcct</td> --%>
                var loginAcct = $(this)  // 当前checkbox对象
                    .parent("td")        // 包含checkbox的td
                    .next()              // 当前td的下一个兄弟元素，就是下一个td，即包含loginAcct信息的这个td
                    .text();             // 获取当前标签内部的文本
                loginAcctArray.push(loginAcct);
            });

            // 检查adminIDArray是否包含有效数据
            if (adminIDArray.length == 0){
                alert("请勾选你要删除的记录！");
                return; // 函数直接返回，后续停止执行
            }

            // 给出删除确认提示
            var confirmResult = confirm("确认要删除\n"+loginAcctArray+"这"+loginAcctArray.length+"条信息吗？\n注意！该操作不可逆！");
            if (!confirmResult){
                return;
            }
            // 调用批量删除函数
            doBatchRemove(adminIDArray);
        });

        // 给单条删除按钮绑定单击事件
        $(".uniqueRemoveBtn").click(function () {
            // 获取当前adminID值
            var adminID = $(this).attr("adminID");
            // 获取当前记录的loginAcct
            var loginAcct = $(this).parents("tr").children("td:eq(2)").text();
            var confirmResult = confirm("你确定要删除 ["+loginAcct+"] 这条记录吗？");
            if (!confirmResult){
                return;
            }
            var adminIDArray = new Array();
            adminIDArray.push(adminID);
            doBatchRemove(adminIDArray);
        });
    });

</script>
</head>
<body>

    <%@ include file="/WEB-INF/pages/include-nav.jsp"%>
    <div class="container-fluid">
        <div class="row">
            <%@ include file="/WEB-INF/pages/include-sidebar.jsp"%>
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                    </div>
                    <div class="panel-body">
                        <form action="admin/query/for/search.html" class="form-inline" role="form" style="float:left;" method="post">
                            <div class="form-group has-feedback">
                                <div class="input-group">
                                    <div class="input-group-addon">查询条件</div>
                                    <input name="keyword" class="form-control has-success" type="text" placeholder="请输入查询条件" id="QueryCondition">
                                </div>
                            </div>
                            <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                        </form>
                        <button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 批量删除</button>
                        <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='admin/to/add/page.html'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <br>
                        <hr style="clear:both;">
                        <div class="table-responsive">
                            <table class="table  table-bordered">
                                <thead>
                                <tr >
                                    <th width="30">#</th>
                                    <th width="30"><input id="summaryBox" type="checkbox"></th>
                                    <th>账号</th>
                                    <th>名称</th>
                                    <th>邮箱地址</th>
                                    <th width="100">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${empty requestScope['PAGE-INFO'].list}">
                                        <tr>
                                            <td style="text-align: center;" colspan="6">抱歉！没有符合你要求的查询结果！</td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${!empty requestScope['PAGE-INFO'].list}">
                                        <c:forEach items="${requestScope['PAGE-INFO'].list}" var="admin" varStatus="myStatus">
                                            <tr>
                                                <td>${myStatus.count}</td>
                                                <td><input adminID="${admin.id}" class="itemBox" type="checkbox"></td>
                                                <td>${admin.loginAcct}</td>
                                                <td>${admin.userName}</td>
                                                <td>${admin.email}</td>
                                                <td>
                                                    <a href="assign/to/assign/role/page.html?adminId=${admin.id}&pageNum=${requestScope['PAGE-INFO'].pageNum}" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></a>
                                                    <a href="admin/to/edit/page.html?adminID=${admin.id}&pageNum=${requestScope['PAGE-INFO'].pageNum}" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></a>
                                                    <button adminID="${admin.id}" type="button" class="btn btn-danger btn-xs uniqueRemoveBtn"><i class=" glyphicon glyphicon-remove"></i></button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                </tbody>
                                <tfoot>
                                <tr >
                                    <td colspan="6" align="center">
                                        <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
