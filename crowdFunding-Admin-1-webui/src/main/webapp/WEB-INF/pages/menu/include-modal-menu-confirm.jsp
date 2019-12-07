<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="menuConfirmModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">X</span>
				</button>
				<h4 class="modal-title">尚筹网警示弹窗</h4>
			</div>
			<div class="modal-body">
				<p>你确认要删除下面显示的内容吗?</p>
				<table class="table  table-bordered">
					<thead>
						<tr>
							<td>节点名称：</td>
							<td name="name"></td>
						</tr>
						<tr>
							<td>URL地址：</td>
							<td name="url"></td>
						</tr>
					</thead>
				</table>
			</div>
			<div class="modal-footer">
				<button id="menuConfirmBtn" onclick="removeMenuById(this)" type="button" class="btn btn-danger"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
			</div>
		</div>
	</div>
</div>