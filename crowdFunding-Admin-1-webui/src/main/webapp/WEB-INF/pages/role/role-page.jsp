<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="UTF-8">
<title>角色维护</title>
<%@ include file="/WEB-INF/pages/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/role.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
    $(function () {
        // 调用分页参数初始化方法
        initGlobalVariable();
        showPage();
        //关键词查询
        $("#queryBtn").click(function () {
            // $.trim去掉字符串起始和结尾的空格
            var queryCondition = $.trim($("#QueryCondition").val());
            if (queryCondition == "" || queryCondition == null){
                layer.msg("查询条件不能为空！请输入内容");
                return;
            }
            window.keyword = queryCondition;
            showPage();
        });

        // 全选/全不选功能
        $("#summaryBox").click(function () {
            $(".itemBox").prop("checked",this.checked);
        });

        // 批量删除功能
        $("#batchRemoveBtn").click(function () {
            var length = $(".itemBox:checked").length;
            if (length == 0){
                layer.msg("请勾选要删除的内容！");
                return;
            }
            // 在全局作用域内创建id数组
            window.roleIdArray = new Array();
            // // 获取所有勾选的itemBox的roleId值,存放进数组中
            $(".itemBox:checked").each(function () {
                var roleId = $(this).attr("roleid");
                window.roleIdArray.push(roleId);
            });

            // 调用函数打开删除确认模态框
            showRemoveConfirmModal();
        });

        // 确认删除
        $("#executeRemoveBtn").click(function () {
            $.ajax({
                "url":"role/batch/remove.json",
                "type":"post",
                "data":JSON.stringify(window.roleIdArray),
                "contentType":"application/json;charset=UTF-8",
                "dataType":"json",
                "success":function (response) {
                    var result = response.result;
                    if (result == "SUCCESS"){
                        layer.msg("操作成功！");
                        // 删除成功，重新调用分页方法
                        showPage();
                    }
                    if (result == "FAILED"){
                        layer.msg(response.message);
                    }
                    // 不管是否删除成功，都需关闭模态框
                    $("#confirmModal").modal("hide");
                },
                "error":function (response) {
                    layer.msg(response.message);
                }
            });
        });


        // $("动态元素所依附的静态元素").on("事件类型","具体要绑定事件的动态元素选择器",事件响应函数);
        $("#roleTableBody").on("click",".removeBtn",function () {
            var roleId = $(this).attr("roleid");
            // 初始化roleIdArray数组
            window.roleIdArray = new Array();
            // 获取当前点击的元素的roleid值,存入到全局变量中
            window.roleIdArray.push(roleId);
            // 打开模态框,进行删除确认即可
            showRemoveConfirmModal();
        });

        // 新增操作
        $("#addBtn").click(function () {
            $("#addModal").modal("show");
        });

        // 保存按钮函数
        $("#confirmAddRoleBtn").click(function () {
            // 获取文本框内容
            var roleName = $.trim($("#roleNameInput").val());
            if (roleName == null || roleName == ""){
                layer.msg("请输入有效角色名称！");
                return;
            }
            // 发送请求
            $.ajax({
                "url":"role/save/role.json",
                "type":"post",
                "data":{
                  "roleName":roleName
                },
                "dataType":"json",
                "success":function (response) {
                    var result = response.result;
                    if (result == "SUCCESS"){
                        layer.msg("操作成功！");
                        // 操作成功，直接前往最后一页
                        window.pageNum = 5E5;
                        showPage();
                    }
                    if (result == "FAILED"){
                        layer.msg(response.message);
                    }
                    // 不管是否成功，都要关闭模态框
                    $("#addModal").modal("hide");
                    // 清理本次文本框填写的数据
                    $("#roleNameInput").val("");
                },
                "error":function (response) {
                    layer.msg(response.message);
                }
            });
        });

        // 打开更新模态框
        $("#roleTableBody").on("click",".updateBtn",function () {
            window.roleID = $(this).attr("roleid");
            var roleName = $(this).parents("tr").children("td:eq(2)").text();
            showUpdateConfirmModal(roleName);
        });

        // 更新操作
        $("#confirmUpdateRoleBtn").click(function () {
            var roleName = $.trim($("#updateRoleNameInput").val());
            var roleId = $("#updateRoleNameInput").attr("roleid");
            if (roleName == null || roleName == ""){
                layer.msg("请输入有效角色名称！");
                return;
            }
            $.ajax({
                url:"role/update/role.json",
                type:"post",
                data:{
                    "id":roleId,
                    "name":roleName
                },
                dataType:"json",
                success:function (response) {
                    var result = response.result;
                    if (result == "SUCCESS"){
                        layer.msg("操作成功！");
                        showPage();
                    }
                    if (result == "FAILED"){
                        layer.msg(response.message);
                    }
                    $("#updateModal").modal("hide");
                },
                error:function (response) {
                    layer.msg(response.message);
                }
            });
        });

        // 打开分配权限模态框
        $("#roleTableBody").on("click",".checkBtn",function () {
            // 将角色id存入全局变量
            window.roleID = $(this).attr("roleId");
            $("#roleAssignAuthModal").modal("show");

            // 1. 设置树形结构属性
            var setting = {
                "data":{
                    "simpleData":{
                        "enable":true,
                        "pIdKey":"categoryId"
                    },
                    "key":{
                        "name":"title"
                    }
                },
                "check":{
                    "enable":true
                }
            };
            // 2. 获取json数据
            var ajaxResult = $.ajax({
                url:"/assign/get/all/auth.json",
                type:"post",
                dataType:"json",
                async:false
            });
            if (ajaxResult.responseJSON.result == "FAILED"){
                layer.msg(ajaxResult.responseJSON.message);
                return;
            }
            var zNodes = ajaxResult.responseJSON.data;
            // 3.初始化树形结构
            $.fn.zTree.init($("#treeDemo"),setting,zNodes);
            // 4. 将树形结构展开
            $.fn.zTree.getZTreeObj("treeDemo").expandAll(true);

            // 5. 查询已分配过的authId
            var ajaxAuthResult = $.ajax({
                "url": "assign/get/assigned/auth/id/list.json",
                "type": "post",
                "data": {
                    "roleId": $(this).attr("roleId"),
                    "random": Math.random() // 生成一个随机数，避免浏览器使用缓存数据
                },
                "dataType": "json",
                "async": false
            });
            if (ajaxAuthResult.responseJSON.result == "FAILED"){
                layer.msg(ajaxAuthResult.responseJSON.message);
                return;
            }
            var authIdList = ajaxAuthResult.responseJSON.data;
            // 6. 使用authIdList勾选对应的树形节点
            for (var i = 0;i<authIdList.length;i++){
                // 1. 获取authId
                var authId = authIdList[i];
                // 2. 根据authId查询到具体的树形节点
                var key = "id";
                var treeNode = $.fn.zTree.getZTreeObj("treeDemo").getNodeByParam(key,authId);
                // 3. 勾选找到的节点
                // treeNode:表示当前要勾选的节点
                // true:表示设置为勾选状态
                // false:表示不联动
                $.fn.zTree.getZTreeObj("treeDemo").checkNode(treeNode,true,false);
                /**
                 * 联动效果：
                 *  在联动模式下，子菜单A勾选会导致父菜单勾选
                 *  父菜单勾选会根据联动效果，把子菜单B、子菜单C也勾选，实际上B、C不应该被勾选
                 */
            }
        });

        // 将模态框中的分配按钮绑定单击事件
        $("#roleAssignAuthBtn").click(function () {
            var authIdArray = new Array();
            // 调用zTree对象的方法获取当前被勾选的节点
            var checkedNodes = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes();
            // 遍历checkedNodes
            for (var i = 0;i<checkedNodes.length;i++){
                // 获取具体的一个节点
                var node = checkedNodes[i];
                // 获取当前节点的id属性
                var authId = node.id;
                // 将authId存入数组
                authIdArray.push(authId);
            }

            // 封装要发送给handler的JSON数据
            // 此处的role需使用[]封装成list形式，以方便后端接收处理
            var requestBody = {"roleIdList":[window.roleID],"authIdList":authIdArray};
            // 发送请求
            var ajaxResult = $.ajax({
                "url": "assign/do/assign.json",
                "type": "post",
                "data": JSON.stringify(requestBody),
                "contentType": "application/json;charset=UTF-8",
                "dataType": "json",
                "async": false
            });
            var result = ajaxResult.responseJSON.result;
            if (result == "SUCCESS"){
                layer.msg("分配成功！");
            }
            if (result == "FAILED"){
                layer.msg(ajaxResult.responseJSON.message);
            }

            $("#roleAssignAuthModal").modal("hide");
        });

    });
</script>
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
                        <form class="form-inline" role="form" style="float:left;">
                            <div class="form-group has-feedback">
                                <div class="input-group">
                                    <div class="input-group-addon">查询条件</div>
                                    <input class="form-control has-success" type="text" placeholder="请输入查询条件" id="QueryCondition">
                                </div>
                            </div>
                            <button type="button" id="queryBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                        </form>
                        <button type="button" id="batchRemoveBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 批量删除</button>
                        <button type="button" id="addBtn" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <br>
                        <hr style="clear:both;">
                        <div class="table-responsive">
                            <table class="table  table-bordered">
                                <thead>
                                <tr >
                                    <th width="30">#</th>
                                    <th width="30"><input id="summaryBox" type="checkbox"></th>
                                    <th>名称</th>
                                    <th width="100">操作</th>
                                </tr>
                                </thead>
                                <tbody id="roleTableBody"></tbody>
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
    <%@ include file="/WEB-INF/pages/role/include-modal-role-confirm.jsp"%>
    <%@ include file="/WEB-INF/pages/role/include-modal-role-add.jsp"%>
    <%@ include file="/WEB-INF/pages/role/include-modal-role-update.jsp"%>
    <%@ include file="/WEB-INF/pages/assign/include-modal-assign-auth.jsp" %>
</body>
</html>
