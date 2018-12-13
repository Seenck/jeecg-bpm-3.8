<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;border:0px">
		<table id="processList" style="width: 700px; height: 300px">
			<thead>
				<tr>
					<th field="id" hidden="hidden">
						<t:mutiLang langKey="common.id"></t:mutiLang>
					</th>
					<th field="processDefinitionId" width="50">
						<t:mutiLang langKey="process.definition.id"></t:mutiLang>
					</th>
					<th field="deploymentId" width="50">
						<t:mutiLang langKey="process.deployment.id"></t:mutiLang>
					</th>
					<th field="name" width="50">
						<t:mutiLang langKey="common.name"></t:mutiLang>
					</th>
					<th field="key" width="50">
						<t:mutiLang langKey="key"></t:mutiLang>
					</th>
					<th field="version" width="20">
						<t:mutiLang langKey="common.version"></t:mutiLang>
					</th>
					<th field="xml" width="50">
						<t:mutiLang langKey="process.files"></t:mutiLang>
					</th>
					<th field="image" width="50">
						<t:mutiLang langKey="process.pictures"></t:mutiLang>
					</th>
					<th field="isSuspended" width="50">
						<t:mutiLang langKey="is.pending"></t:mutiLang>
					</th>
					<th field="opt" width="50">
						<t:mutiLang langKey="common.operation"></t:mutiLang>
					</th>
				</tr>
			</thead>
		</table>
		<div id="tb2" style="height: 30px;" class="datagrid-toolbar">
			<span style="float: left;">
				<a href="#" id='add' class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('<t:mutiLang langKey='deploy.process'></t:mutiLang>','activitiController.do?developmentInit','processList',400,150)" id="">
					<t:mutiLang langKey='deploy.process'></t:mutiLang>
				</a>
			</span>
		</div>
		<script type="text/javascript">
			//查看流程xml或流程图片
			function readProcessResouce(processDefinitionId, resourceType) {
				var url = "";
				var title = "";
				if (resourceType == "xml") {
					title = "<t:mutiLang langKey='see.the.process.files'></t:mutiLang>";
					url = "activitiController.do?resourceRead&processDefinitionId=" + processDefinitionId + "&resourceType=xml&isIframe"
					//url = "activitiController.do?resourceRead&processDefinitionId=vacation:1:10&resourceType=image&isIframe"
				}

				if (resourceType == "image") {
					title = "<t:mutiLang langKey='see.the.process.pictures'></t:mutiLang>";
					url = "activitiController.do?resourceRead&processDefinitionId=" + processDefinitionId + "&resourceType=image&isIframe"
				}
				addOneTab(title, url);
			}

			// 编辑初始化数据
			function getData(data) {
				var rows = [];
				var total = data.total;
				for (var i = 0; i < data.rows.length; i++) {
					rows.push({
						id : data.rows[i].id,
						processDefinitionId : data.rows[i].processDefinitionId,
						deploymentId : data.rows[i].deploymentId,
						name : data.rows[i].name,
						key : data.rows[i].key,
						version : data.rows[i].version,
						xml : "[<a href=\"#\" onclick=\"readProcessResouce('" + data.rows[i].processDefinitionId + "','xml')\"><t:mutiLang langKey='see.the.process.xml'></t:mutiLang></a>]",
						image : "[<a href=\"#\" onclick=\"readProcessResouce('" + data.rows[i].processDefinitionId + "','image')\"><t:mutiLang langKey='see.the.process.pictures'></t:mutiLang></a>]",
						isSuspended : data.rows[i].isSuspended,
						opt : "[<a href=\"#\" onclick=\"delObj('activitiController.do?del&deploymentId=" + data.rows[i].deploymentId + "','processList')\"><t:mutiLang langKey='delete.process'></t:mutiLang></a>]"
					});
				}
				var newData = {
					"total" : total,
					"rows" : rows
				};
				return newData;
			}
			// 筛选
			function jeecgEasyUIListsearchbox(value, name) {
				var queryParams = $('#processList').datagrid('options').queryParams;
				queryParams[name] = value;
				queryParams.searchfield = name;
				$('#processList').datagrid('load');
			}
			// 刷新
			function reloadTable() {
				$('#processList').datagrid('reload');
			}

			// 设置datagrid属性
			$('#processList').datagrid({
				title : '<t:mutiLang langKey='process.definition.and.deployment.manage'></t:mutiLang>',
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
				url : 'activitiController.do?datagrid',
				toolbar : '#tb2',
				loadFilter : function(data) {
					return getData(data);
				}

			});
			//设置分页控件  
			$('#processList').datagrid('getPager').pagination({
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
			// 设置筛选
			$('#jeecgEasyUIListsearchbox').searchbox({
				searcher : function(value, name) {
					jeecgEasyUIListsearchbox(value, name);
				},
				menu : '#jeecgEasyUIListmm',
				prompt : '<t:mutiLang langKey='common.please.input.query.keyword'></t:mutiLang>'
			});
		</script>
	</div>
</div>