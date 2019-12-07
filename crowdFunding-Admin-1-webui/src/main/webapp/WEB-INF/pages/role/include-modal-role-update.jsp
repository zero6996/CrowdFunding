<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<div id="updateModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">X</span></button>
                    <h4 class="modal-title">尚筹网更新角色弹窗</h4>
                </div>
                <div class="modal-body">
                    <p>更新用户信息</p>
                    <table class="table  table-bordered">
                        <thead>
                            <tr >
                                <th width="30">ID</th>
                                <th>名称</th>
                            </tr>
                        </thead>
                        <tbody id="confirmUpdateModalTableBody"></tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" id="confirmUpdateRoleBtn" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i>更新</button>
                    <button type="reset" class="btn btn-primary"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                </div>
            </form>
        </div>
    </div>
</div>