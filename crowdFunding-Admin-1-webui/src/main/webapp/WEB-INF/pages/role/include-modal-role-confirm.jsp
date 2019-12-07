<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<div id="confirmModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">X</span></button>
                <h4 class="modal-title">尚筹网警示弹窗</h4>
            </div>
            <div class="modal-body">
                <p>你确认要删除下面显示的内容吗?</p>
                <table class="table  table-bordered">
                    <thead>
                        <tr >
                            <th width="30">ID</th>
                            <th>名称</th>
                        </tr>
                    </thead>
                    <tbody id="confirmModalTableBody"></tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" id="executeRemoveBtn" class="btn btn-default">确认</button>
            </div>
        </div>
    </div>
</div>