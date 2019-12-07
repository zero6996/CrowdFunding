// 初始化全局变量
function initGlobalVariable() {
    window.pageSize = 5;
    window.pageNum = 1;
    window.keyword = "";
}

// 给服务器发送请求获取分页数据(pageInfo),并在页面上显示分页效果(主体、页码导航条)
function showPage() {
    // 给服务器发送请求获取分页数据：PageInfo
    var pageInfo = getPageInfo();
    if (pageInfo == null){
        // 如果没有获取到pageInfo消息，则停止后续操作
        return;
    }
    // 在页面上的表格中tbody标签内显示分页的主体数据
    generateTableBody(pageInfo);
    // 在页面上的表格中tfoot标签内显示分页的页码导航条
    initPagination(pageInfo);
}

// 获取分页数据
function getPageInfo() {
    // 以同步请求方式调用$.ajax()函数并获取返回值
    var ajaxResult = $.ajax({
        "url":"/role/search/by/keyword.json",
        "type":"post",
        "data":{
            "pageNum":(window.pageNum == undefined)?1:window.pageNum,
            "pageSize":(window.pageSize == undefined)?5:window.pageSize,
            "keyword":(window.keyword == undefined)?"":window.keyword
        },
        "dataType":"json",
        "async":false, // 为了保证getPageInfo()函数在ajax请求拿到响应后获取PageInfo，需设置为同步操作
        "success":function (response) {
            pageInfo = response.data;
        }
    });
    var resultEntity = ajaxResult.responseJSON;
    var result = resultEntity.result;
    if (result == "SUCCESS"){
        return resultEntity.data;
    }
    if (result == "FAILED"){
        layer.msg(resultEntity.message);
    }
    return null;
}


// 使用PageInfo数据在tbody标签内显示分页数据
function generateTableBody(pageInfo) {
    // 执行所有操作前先清空
    $("#roleTableBody").empty();
    // 获取数据集合
    var list = pageInfo.list;
    // 判断list是否有效
    if (list == null || list.length == 0){
        $("#roleTableBody").append("<tr><td colspan='4' style='text-align:center;'>没有查询到数据！</td></tr>");
        return;
    }




    for (var i = 0;i < list.length;i++){
        var role = list[i];
        var checkBtn = "<button type=\"button\" roleId='"+role.id+"' class=\"checkBtn btn btn-success btn-xs\"><i class=\" glyphicon glyphicon-check\"></i></button>";
        var removeBtn = "<button type=\"button\" roleId='"+role.id+"' class=\"removeBtn btn btn-danger btn-xs\"><i class=\" glyphicon glyphicon-remove\"></i></button>";
        var pencilBtn = " <button type=\"button\" roleId='"+role.id+"' class=\"updateBtn btn btn-primary btn-xs\"><i class=\" glyphicon glyphicon-pencil\"></i></button>";
        var numberTd = "<td>"+(i+1)+"</td>";
        var checkBoxTd = "<td><input roleid='"+role.id+"' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>"+role.name+"</td>";
        var btnTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
        var tr = "<tr>"+numberTd+checkBoxTd+roleNameTd+btnTd+"</tr>";
        // 将前面拼接好的HTML代码追加到#roleTableBody中
        $("#roleTableBody").append(tr);
    }
}

// 声明函数封装导航条初始化操作
function initPagination() {
    // 声明变量存储分页导航条显示时的属性设置
    var paginationProperties = {
        num_edge_entries: 3,            //边缘页数
        num_display_entries: 5,         //主体页数
        callback: pageselectCallback,   // 回调函数
        items_per_page:window.pageSize, //每页显示数据量,就是pageSize
        current_page:(window.pageNum - 1), // 当前页码
        prev_text:"上一页",
        next_text:"下一页"
    };
    // 显示分页导航条
    $("#Pagination").pagination(pageInfo.total,paginationProperties);3333
};

// 在每一次点击"上一页"、"下一页"、"页码" 时执行这个函数进行跳转操作
function pageselectCallback(pageIndex, jq){
    // pageIndex从0开始，pageNum从1开始
    window.pageNum = pageIndex + 1;
    // 调用分页函数重新执行
    showPage();
    return false;
};

// 根据id数组获取数据
function getRoleListByRoleIdArray(roleIdArray) {
    var requestBody = JSON.stringify(roleIdArray);
    var ajaxResult = $.ajax({
        "url": "role/get/list/by/id/list.json",
        "type": "post",
        "data":requestBody,
        "contentType":"application/json;charset=UTF-8",
        "dataType":"json",
        "async":false
    });

    // 获取JSON对象类型的响应体
    var resultEntity = ajaxResult.responseJSON;
    var result = resultEntity.result;
    if (result == "SUCCESS"){
        return resultEntity.data;
    }
    if (result == "FAILED"){
        layer.msg(resultEntity.message);
        return null;
    }
    return null;
}

// 打开确认删除模态框
function showRemoveConfirmModal() {
    // 1. 将模态框显示出来
    $("#confirmModal").modal("show");
    // 2. 根据roleIdList获取roleList
    var roleList = getRoleListByRoleIdArray(window.roleIdArray);
    // 3. 清空#confirmModalTableBody
    $("#confirmModalTableBody").empty();
    // 4. 将数据填充进#confirmModalTableBody
    for (var i = 0;i < roleList.length; i++){
        // 5. 获取角色相关数据
        var role = roleList[i];
        var trHTML = "<tr><td>"+role.id+"</td><td>"+role.name+"</td></tr>";
        // 6. 填充数据
        $("#confirmModalTableBody").append(trHTML);
    }
}

// 打开更新确认模态框
function showUpdateConfirmModal(roleName) {
    $("#updateModal").modal("show");
    $("#confirmUpdateModalTableBody").empty();
    var trHTML = "<tr><td>"+window.roleID+"</td><td><input type='text' roleid='"+window.roleID+"' id='updateRoleNameInput' class='form-control' value='"+roleName+"'></td>";
    $("#confirmUpdateModalTableBody").append(trHTML);
}

