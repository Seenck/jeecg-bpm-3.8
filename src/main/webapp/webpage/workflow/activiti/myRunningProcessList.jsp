<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;border:0px">
		<t:datagrid name="myRunningProcessList" title="processes.i.created" autoLoadData="true" actionUrl="processInstanceController.do?myRunningProcessListDataGrid" sortName="userName" fitColumns="true" idField="id" fit="true" queryMode="group">
			<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="标题" field="bpmBizTitle" width="150"></t:dgCol>
			<t:dgCol title="common.process.name" sortable="false" field="prcocessDefinitionName" width="100" query="false"></t:dgCol>
			<t:dgCol title="common.process.id" sortable="false" field="processDefinitionId" query="true" width="100" extend="{style:'width:200px'}"></t:dgCol>
			<t:dgCol title="process.instance.id" field="processInstanceId" width="100"></t:dgCol>
			<t:dgCol title="common.process.create" field="startUserId" width="100"></t:dgCol>
			<t:dgCol title="common.begindate" field="starttime" formatter="yyyy-MM-dd hh:mm:ss" width="100"></t:dgCol>
			<t:dgCol title="common.endtime" field="endtime" formatter="yyyy-MM-dd hh:mm:ss" width="100"></t:dgCol>
			<t:dgCol title="time.elapsed" field="spendTimes" width="100"></t:dgCol>
			<t:dgCol title="common.operation" field="opt"></t:dgCol>
			<t:dgFunOpt exp="endtime#eq#" funname="goTaskNotification(processInstanceId)" title="催办" urlclass="ace_button"  urlfont="fa-clock-o"></t:dgFunOpt>
			<t:dgFunOpt exp="endtime#eq#" funname="invalidProcess(processInstanceId,isSuspended)" title="invalid.process" urlclass="ace_button" urlStyle="background-color:#ec4758;" urlfont="fa-trash-o"></t:dgFunOpt>
			<t:dgFunOpt exp="endtime#eq#" funname="callBackProcess(processInstanceId,isSuspended)" title="return.process" urlclass="ace_button" urlStyle="background-color:#FF6347" urlfont="fa-exchange"></t:dgFunOpt>
			<%-- <t:dgFunOpt funname="viewHistory(processInstanceId)" title="common.history"></t:dgFunOpt>--%>
			<t:dgFunOpt funname="goProcessHisTab(processInstanceId,undefined)" title="common.history" urlclass="ace_button"  urlfont="fa fa-history"></t:dgFunOpt>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	//作废流程
	function invalidProcess(processInstanceId, isSuspended) {
		confirm('processInstanceController.do?invalidProcess&processInstanceId=' + processInstanceId, '<t:mutiLang langKey='sure.invalid.process'></t:mutiLang>', 'myRunningProcessList');
	}

	//流程追回
	function callBackProcess(processInstanceId, isSuspended) {
		confirm('processInstanceController.do?callBackProcess&processInstanceId=' + processInstanceId, '<t:mutiLang langKey='sure.recover.process'></t:mutiLang>', 'myRunningProcessList');
	}

	//查看流程历史
	function viewHistory(processInstanceId) {
		var url = "";
		var title = "<t:mutiLang langKey='process.history'></t:mutiLang>";
		url = "activitiController.do?viewProcessInstanceHistory&processInstanceId=" + processInstanceId + "&isIframe";
		addOneTab(title, url);
	}
	
	//催办
	function goTaskNotification(processInstanceId) {
		createwindow('<t:mutiLang langKey='催办'></t:mutiLang>', 'taskController.do?goTaskNotification&processInstanceId=' + processInstanceId,700,150);
	}
</script>