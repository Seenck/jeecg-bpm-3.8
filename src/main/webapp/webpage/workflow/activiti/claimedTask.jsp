<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center"  style="padding:0px;border:0px">
		<table id="claimedTask" style="width: 700px; height: 300px">
			<thead>
				<tr>
					<th field="taskId" hidden="true">
						<t:mutiLang langKey="common.id"></t:mutiLang>
					</th>
					<th field="name" width="50">
						<t:mutiLang langKey="process.task.name"></t:mutiLang>
					</th>
					<th field="processDefinitionId" width="50">
						<t:mutiLang langKey="activity.flow.definition"></t:mutiLang>
					</th>
					<th field="processInstanceId" width="50">
						<t:mutiLang langKey="process.instance"></t:mutiLang>
					</th>
					<th field="opt" width="50">
						<t:mutiLang langKey="common.operation"></t:mutiLang>
					</th>
				</tr>
			</thead>
		</table>

		<script type="text/javascript">
			// 编辑初始化数据
			function getData(data) {
				var rows = [];
				var total = data.total;
				for (var i = 0; i < data.rows.length; i++) {
					rows
							.push({
								taskId : data.rows[i].taskId,
								name : data.rows[i].name,
								processDefinitionId : data.rows[i].processDefinitionId,
								processInstanceId : data.rows[i].processInstanceId,
								opt : "[<a href=\"#\" onclick=\"add('<t:mutiLang langKey='process.handle'></t:mutiLang>','leaveController.do?taskCompletePageSelect&jspPage="
										+ data.rows[i].description
										+ "&processInstanceId="
										+ data.rows[i].processInstanceId
										+ "&taskId="
										+ data.rows[i].taskId
										+ "','claimedTask')\"><t:mutiLang langKey='process.handle'></t:mutiLang></a>]"
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
				$('#claimedTask').datagrid('reload');
			}

			// 设置datagrid属性
			$('#claimedTask').datagrid({
				title : '<t:mutiLang langKey='common.home.task'></t:mutiLang>',
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
				url : 'activitiController.do?claimedTaskDataGrid',
				loadFilter : function(data) {
					return getData(data);
				}

			});
			//设置分页控件  
			$('#claimedTask').datagrid('getPager').pagination({
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