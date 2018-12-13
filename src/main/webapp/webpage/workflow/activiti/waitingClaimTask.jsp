<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;border:0px">
		<table id="waitingClaimTask" style="width: 700px; height: 300px">
			<thead>
				<tr>
					<th field="taskId" hidden="true">
						<t:mutiLang langKey="common.id"></t:mutiLang>
					</th>
					<th field="name" width="50">
						<t:mutiLang langKey="process.task.name"></t:mutiLang>
					</th>
					<th field="processDefinitionId" width="50">
						<t:mutiLang langKey="process.definition"></t:mutiLang>
					</th>
					<th field="opt" width="50">
						<t:mutiLang langKey="common.operation"></t:mutiLang>
					</th>
				</tr>
			</thead>
		</table>
		<script type="text/javascript">
			//查看流程历史
			function claimTask(taskId) {
				confirm('activitiController.do?claimTask&taskId=' + taskId, '<t:mutiLang langKey='common.claim.sure'></t:mutiLang>', 'waitingClaimTask');
			}
			// 编辑初始化数据
			function getData(data) {
				var rows = [];
				var total = data.total;
				for (var i = 0; i < data.rows.length; i++) {
					rows.push({
						taskId : data.rows[i].taskId,
						name : data.rows[i].name,
						processDefinitionId : data.rows[i].processDefinitionId,
						opt : "[<a href=\"#\" onclick=\"claimTask('" + data.rows[i].taskId + "')\"><t:mutiLang langKey='common.claim'></t:mutiLang></a>]"
					});
				}
				var newData = {
					"total" : total,
					"rows" : rows
				};
				return newData;
			}

			// 刷新
			function reloadTable() {
				$('#waitingClaimTask').datagrid('reload');
			}

			// 设置datagrid属性
			$('#waitingClaimTask').datagrid({
				title : '<t:mutiLang langKey='unclaimed.task.list'></t:mutiLang>',
				idField : 'id',
				fit : true,
				loadMsg : '<t:mutiLang langKey='common.data.loading'></t:mutiLang>',
				pageSize : 10,
				pagination : true,
				sortOrder : 'asc',
				rownumbers : true,
				singleSelect : true,
				fitColumns : true,
				showFooter : true,
				url : 'activitiController.do?waitingClaimTaskDataGrid',
				loadFilter : function(data) {
					return getData(data);
				}

			});
			//设置分页控件  
			$('#waitingClaimTask').datagrid('getPager').pagination({
				pageSize : 10,
				pageList : [ 10, 20, 30 ],
				beforePageText : '',
				afterPageText : '/{pages}',
				displayMsg : '{from}-{to}<t:mutiLang langKey='common.total'></t:mutiLang>{total}<t:mutiLang langKey='common.item'></t:mutiLang>',
				showPageList : true,
				showRefresh : true,
				onBeforeRefresh : function(pageNumber, pageSize) {
					$(this).pagination('loading');
					$(this).pagination('loaded');
				}
			});
		</script>
	</div>
</div>