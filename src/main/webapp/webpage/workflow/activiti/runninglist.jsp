<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;border:0px">
		<t:datagrid name="runningList" title="process.instance.manage" autoLoadData="true" actionUrl="processInstanceController.do?runningProcessDataGrid"  sortName="userName" fitColumns="true" treegrid="true" idField="id" fit="true" queryMode="group">
			<t:dgCol title="common.id" field="id" treefield="id" hidden="true"></t:dgCol>
			<t:dgCol title="common.process.name" sortable="false" field="prcocessDefinitionName" treefield="text" width="150"></t:dgCol>
			<t:dgCol title="process.definition.id" sortable="false" field="processDefinitionId" treefield="processDefinitionId" width="85"></t:dgCol>
			<t:dgCol title="process.instance.id" field="processInstanceId" query="true" treefield="processInstanceId" width="80"></t:dgCol>
			<t:dgCol title="common.process.create" field="startUserId" treefield="startUserId" query="true" width="80"></t:dgCol>
			<t:dgCol title="current.task.name" field="activityName" treefield="activityName" width="80"></t:dgCol>
			<t:dgCol title="current.task.handler" field="activityUser" treefield="activityUser" width="80"></t:dgCol>
			<t:dgCol title="common.begintime" field="starttime" treefield="starttime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="150"></t:dgCol>
			<t:dgCol title="common.endtime" field="endtime" treefield="endtime" formatter="yyyy-MM-dd hh:mm:ss" hidden="true" width="180"></t:dgCol>
			<t:dgCol title="time.elapsed" field="spendTimes" treefield="spendTimes" width="120"></t:dgCol>
			<t:dgCol title="common.taskid" field="taskId" treefield="taskId" hidden="true" width="80"></t:dgCol>
			<t:dgCol title="common.status" sortable="false" field="isSuspended" treefield="isSuspended" width="50" replace="process.finished_finished,process.start_false,process.pause_true" style="background:red;_true"></t:dgCol>
			<t:dgCol title="common.operation" field="opt" width="310"></t:dgCol>
			<t:dgFunOpt exp="isSuspended#ne#&&isSuspended#ne#true&&isSuspended#ne#finished" funname="suspendProcessInstance(processInstanceId,isSuspended)" title="process.pause" urlclass="ace_button" urlStyle="background-color:#FFA500;" urlfont="fa-pause"></t:dgFunOpt>
			<t:dgFunOpt exp="isSuspended#ne#&&isSuspended#ne#false&&isSuspended#ne#finished" funname="startProcessInstance(processInstanceId,isSuspended)" title="common.start" urlclass="ace_button" urlfont="fa-play"></t:dgFunOpt>
			<t:dgFunOpt exp="isSuspended#ne#&&isSuspended#ne#finished" funname="closeProcessInstance(processInstanceId,isSuspended)" title="common.close" urlclass="ace_button" urlStyle="background-color:#ec4758;" urlfont="fa-times-circle-o"></t:dgFunOpt>
			<t:dgFunOpt exp="isSuspended#ne#&&isSuspended#ne#true&&isSuspended#ne#finished" funname="reassign(taskId,isSuspended)" title="process.appoint" urlclass="ace_button" urlfont="fa-share"></t:dgFunOpt>
			<%-- <t:dgFunOpt funname="viewHistory(processInstanceId)" title="历史"></t:dgFunOpt>--%>
			<t:dgFunOpt exp="isSuspended#ne#" funname="goProcessHisTab(processInstanceId,activityName)" title="common.history" urlclass="ace_button" urlStyle="background-color:#FF6347" urlfont="fa-history"></t:dgFunOpt>
			<t:dgFunOpt exp="isSuspended#ne#" funname="skipNode(taskId,isSuspended)" title="process.jump" urlclass="ace_button" urlfont="fa-twitter"></t:dgFunOpt>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
		//给时间控件加上样式
			$("#runningListtb").find("input[name='starttime_begin']").attr("style","width:125px;background:#fff url(plug-in/My97DatePicker/skin/datePicker.gif) no-repeat right;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
			$("#runningListtb").find("input[name='starttime_end']").attr("style","width:125px;background:#fff url(plug-in/My97DatePicker/skin/datePicker.gif) no-repeat right;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
			$("#runningListtb").find("input[name='endtime_begin']").attr("style","width:125px;background:#fff url(plug-in/My97DatePicker/skin/datePicker.gif) no-repeat right;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
			$("#runningListtb").find("input[name='endtime_end']").attr("style","width:125px;background:#fff url(plug-in/My97DatePicker/skin/datePicker.gif) no-repeat right;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
			$("#runningListtb").find("input[name='startUserId']").attr("readonly","readonly").click(function(){openUserSelect();});
});


	function openUserSelect() {    
		$.dialog.setting.zIndex = 9999;     
		$.dialog({content: 'url:userController.do?userSelect', 
			zIndex: 2100, 
			title: '用户列表', 
			lock: true, 
			width: '1000px', 
			height: '600px', 
			opacity: 0.4, 
			button: [       
			        {name: '确定', callback: callbackUserSelect, focus: true},       
			        {name: '取消', callback: function (){}}   ]
			}).zindex();
	}
	function callbackUserSelect() {
		var iframe = this.iframe.contentWindow;
		var rowsData = iframe.$('#userList1').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('<t:mutiLang langKey="common.please.select.edit.item"/>');
			return;
			}
		var userName=rowsData[0].userName;
		$("#runningListtb").find("input[name='startUserId']").val(userName);
		//$("#runningListtb").find("input[name='startUserId']").blur();
	}

	//暂停
	function suspendProcessInstance(processInstanceId, isSuspended) {
		confirm('processInstanceController.do?suspend&processInstanceId=' + processInstanceId, '<t:mutiLang langKey='sure.pause'></t:mutiLang>', 'runningList');
	}

	//启动
	function startProcessInstance(processInstanceId, isSuspended) {
		confirm('processInstanceController.do?restart&processInstanceId=' + processInstanceId, '<t:mutiLang langKey='sure.start'></t:mutiLang>', 'runningList');
	}

	//关闭
	function closeProcessInstance(processInstanceId, isSuspended) {
		confirm('processInstanceController.do?close&processInstanceId=' + processInstanceId, '<t:mutiLang langKey='sure.cancel'></t:mutiLang>', 'runningList');
	}

	//委派(重新分配处理人)
	function reassign(taskId, isSuspended) {
		createwindow('<t:mutiLang langKey='process.appoint'></t:mutiLang>', 'processInstanceController.do?reassignInit&taskId=' + taskId, 800, 100);
	}

	//查看流程历史
	function viewHistory(processInstanceId) {
		var url = "";
		var title = "<t:mutiLang langKey='process.history'></t:mutiLang>";
		url = "activitiController.do?viewProcessInstanceHistory&processInstanceId=" + processInstanceId + "&isIframe"
		addOneTab(title, url);
	}

	//流程跳转（选择节点，跳转哪个节点）
	function skipNode(taskId, isSuspended) {
		createwindow('<t:mutiLang langKey='process.jump'></t:mutiLang>', 'processInstanceController.do?skipNodeInit&taskId=' + taskId, 700, 100);
	}

	$(document).ready(function() {
		$("input[name='starttime_begin']").attr("class", "easyui-datebox");
		$("input[name='starttime_end']").attr("class", "easyui-datebox");
		$("input[name='endtime_begin']").attr("class", "easyui-datebox");
		$("input[name='endtime_end']").attr("class", "easyui-datebox");
	});
</script>