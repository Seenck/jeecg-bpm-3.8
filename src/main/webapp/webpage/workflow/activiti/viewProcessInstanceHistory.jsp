
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools"></t:base> --%>
<style type="text/css">
.tooltips {
	display: none;
	font-size: 12px;
	border: 1px solid #8BAABF;
	background: #FBFCFC;
	padding: 5px;
	max-width: 400px;
	min-width: 180px;
	color: #000;
	background-image: url($_basePath/bpm/main/images/page_16.png);
	background-repeat: no-repeat;
	background-position: top right;
	background-origin: content-box
}
</style>
<div class="easyui-layout" fit="true">
	<script>
		$("span[spans='spans']").each(function() {
			$(this).hide();
		});
	</script>
	<div region="center" style="padding: 1px;">
		<script type="text/javascript">
			var xvalue = 0;
			var yvalue = 0;
			var getCoordInDocumentExample = function() {
				var coords = document.getElementById("coords");
				coords.onmousemove = function(e) {
					var pointer = getCoordInDocument(e);
					xvalue = pointer.x + 20;
					yvalue = pointer.y + 40;
					changeValues();
				};
			};
			var getCoordInDocument = function(e) {
				e = e || window.event;
				var x = e.pageX || (e.clientX + (document.documentElement.scrollLeft || document.body.scrollLeft));
				var y = e.pageY || (e.clientY + (document.documentElement.scrollTop || document.body.scrollTop));
				return {
					'x' : x,
					'y' : y
				};
			};
			getCoordInDocumentExample();
			function changeValues() {
				$("input[selprexi='imageSpanHidden']").each(function(i) {
					var startwidth = parseInt($(this).attr("leftPrefix"));
					var endwidth = parseInt($(this).attr("leftprefix"));
					var topWidthPrefix = parseInt($(this).attr("topWidthPrefix"));
					var topPrefix = parseInt($(this).attr("topPrefix"));
					if ((endwidth - 30) <= parseInt(xvalue) && (parseInt(xvalue)) <= (endwidth + 50)) {
						if ($($(this).val()) != null) {
							if (((topPrefix) <= yvalue) && (yvalue <= (topWidthPrefix + topPrefix))) {
								$($(this).val()).css("left", xvalue + "px");
								$($(this).val()).css("top", yvalue + "px");
								$($(this).val()).show();
							} else {
								$($(this).val()).hide();
							}

						} else {
							$($(this).val()).hide();
						}
					}
				});
			}
		</script>
		<c:forEach items="${historicTasks}" varStatus="stuts" var="memo">
			<span id="imgs${stuts.count+1}" class="tooltips" spans="spans" style="border: 1px solid green; position: absolute; height: 100px;">
				<t:mutiLang langKey="process.task"></t:mutiLang>
				${memo.name}
				<br />
				<t:mutiLang langKey="process.execute"></t:mutiLang>${memo.name}
				<br />
				<t:mutiLang langKey="common.time"></t:mutiLang>
				<fmt:formatDate value="${memo.endTime}" pattern="yyyy-MM-dd hh:mm:ss" />
				<br />
				<t:mutiLang langKey="common.task.operator"></t:mutiLang>${memo.assignee}
				<br />
				<t:mutiLang langKey="order.remark"></t:mutiLang>${memo.deleteReason}
			</span>
		</c:forEach>
		<c:forEach items="${listIs }" varStatus="stuts" var="item">
			<input type="hidden" name="${item.x }" id="${item.x }" vsle="" selprexi="imageSpanHidden" leftPrefix="${item.x}" leftWidthPrefix="${item.width }" topWidthPrefix="${item.height}" topPrefix="${item.y }" value="#imgs${stuts.count+1}" />
			<script type="text/javascript">
				$("#imgs${stuts.count+1}").css("left", "${item.x}px").css("top", "${item.y}px");
			</script>
		</c:forEach>
		<div id="coords" style="background: #F2F1D7; border: 0px solid #0066cc;">
			<img id="taskProcessId" style="border:2px;cursor:hand;" src="activitiController.do?traceImage&processInstanceId=${processInstanceId}&isIframe">
		</div>
		<br />
		<div id="divLocation"></div>
	</div>
	<div data-options="region:'south',split:true" style="height: 250px;">
		<table id="taskHistoryList">
			<thead>
				<tr>
					<th field="name">
						<t:mutiLang langKey="common.name"></t:mutiLang>
					</th>
					<th field="processInstanceId" width="50">
						<t:mutiLang langKey="process.instance.id"></t:mutiLang>
					</th>
					<th field="startTime" width="50">
						<t:mutiLang langKey="common.begintime"></t:mutiLang>
					</th>
					<th field="endTime" width="50">
						<t:mutiLang langKey="common.endtime"></t:mutiLang>
					</th>
					<th field="assignee" width="25">
						<t:mutiLang langKey="person.in.charge"></t:mutiLang>
					</th>
					<th field="deleteReason" width="150">
						<t:mutiLang langKey="process.handle.result"></t:mutiLang>
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
					rows.push({
						name : data.rows[i].name,
						processInstanceId : data.rows[i].processInstanceId,
						startTime : data.rows[i].startTime,
						endTime : data.rows[i].endTime,
						assignee : data.rows[i].assignee,
						deleteReason : data.rows[i].deleteReason
					});
				}
				var newData = {
					"total" : total,
					"rows" : rows
				};
				return newData;
			}

			// 设置datagrid属性
			$('#taskHistoryList').datagrid({
				title : '<t:mutiLang langKey='track.process.history'></t:mutiLang>',
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
				url : 'activitiController.do?taskHistoryList&processInstanceId=${processInstanceId}',
				loadFilter : function(data) {
					return getData(data);
				}

			});
			//设置分页控件  
			$('#taskHistoryList').datagrid('getPager').pagination({
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