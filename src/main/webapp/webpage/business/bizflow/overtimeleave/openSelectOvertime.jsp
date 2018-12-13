<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<body style="overflow-x: hidden;">
<div class="easyui-layout" style="width:970px;height:470px;">
    <div data-options="region:'center'">
        <t:datagrid actionUrl="joaOvertimeController.do?selectDatagrid&leaveId=${leaveId}" name="overtimeList" title="加班工时" fit="true" fitColumns="true" idField="id" checkbox="true">
			<t:dgCol title="id" hidden="true" field="id"></t:dgCol>
			<t:dgCol title="申请人" field="applyUser"></t:dgCol>
			<t:dgCol title="部门" field="department"></t:dgCol>
			<t:dgCol title="申请时间" field="applyTime" formatter="yyyy-MM-dd"></t:dgCol>
			<t:dgCol title="加班补偿" field="compensation" ></t:dgCol>
			<t:dgCol title="加班开始时间" field="beginTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
			<t:dgCol title="加班结束时间" field="endTime"  formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
			<t:dgCol title="总加班时间" field="total"></t:dgCol>
			<t:dgCol title="已补偿时间" field="compensatedTime"></t:dgCol>
			<t:dgCol title="未经补偿时间" field="makeupTime"></t:dgCol>
		</t:datagrid>
    </div>
</div>
</body>
<script type="text/javascript">
function checkSelect() {
	var rows = $("#overtimeList").datagrid("getSelections");
	var overtimeIds = "";
	if(rows.length>=1) {
		for(var i =0; i<rows.length;i++) {
    		var rowsId = rows[i]['id'];
    		overtimeIds += rowsId + ",";
		}
	}
	return overtimeIds;
}

function getSelected() {
	return checkSelect();
}
</script>
