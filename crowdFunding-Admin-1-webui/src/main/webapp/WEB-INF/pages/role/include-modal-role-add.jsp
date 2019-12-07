<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<div id="addModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">X</span></button>
                    <h4 class="modal-title">尚筹网新增角色弹窗</h4>
                </div>
                <div class="modal-body">
                    <input type="text" id="roleNameInput" class="form-control" placeholder="请输入角色名称">
                </div>
                <div class="modal-footer">
                    <button type="button" id="confirmAddRoleBtn" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i>保存</button>
                    <button type="reset" class="btn btn-primary"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                </div>
            </form>
        </div>
    </div>
</div>