<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:datagrid name="bustripList" title="出差审批列表" actionUrl="activitiController.do?taskList&busCode=businesstrip" idField="id">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="申请人" field="TSUser_userName"></t:dgCol>
 <t:dgCol title="出差地点" field="bustriplocale"></t:dgCol>
 <t:dgCol title="开始时间" field="begintime" width="90"></t:dgCol>
 <t:dgCol title="结束时间" field="endtime"></t:dgCol>
 <t:dgCol title="当前环节" field="Process_task_name" width="60"></t:dgCol>
 <t:dgCol title="办理人" field="Process_task_assignee" width="30"></t:dgCol>
 <t:dgCol hidden="true" title="TASK ID(该字段隐藏)" field="Process_task_id"></t:dgCol>
 <t:dgCol hidden="true" title="key" field="Process_task_taskDefinitionKey"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
 <t:dgConfOpt exp="Process_task_assignee#empty#true" url="activitiController.do?claim&taskId={Process_task_id}" message="确定签收?" title="签收"></t:dgConfOpt>
 <t:dgFunOpt exp="Process_task_assignee#empty#false" funname="myopenhandle(Process_task_id,Process_task_name)" title="办理"></t:dgFunOpt>
 <t:dgFunOpt funname="processimg(Process_task_id,Process_task_name)" title="<font style=color:red>流程进度图</font>"></t:dgFunOpt>
</t:datagrid>

<script type="text/javascript">
<!--
/**
 * 打开流程办理页面
 */
function myopenhandle(taskId, title, width, height) {
	if (typeof (width) == 'undefined') {
		width = "auto";
	}
	if (typeof (height) == 'undefined') {
		height = "auto";
	}
	if (typeof (title) == 'undefined') {
		title = "";
	}
	var url = 'activitiController.do?openProcessHandle&taskId=' + taskId;
	$.dialog({
		content: 'url:'+url,
		title : '流程办理--当前环节:' + title,
		lock : true,
		opacity : 0.3,
		button : [ {
			name : '综合评价录入',
			callback : function() {
				iframe = this.iframe.contentWindow;
				var inputvar = $("[vartype]", iframe.document);
				setvar(true, inputvar, iframe);
				saveObj();
				return false;

			},
			focus : true
		}, {
			name : '流程下一步',
			callback : function() {
				iframe = this.iframe.contentWindow;
				var inputvar = $("[vartype]", iframe.document);
				setvar(false, inputvar, iframe);
				saveObj();
				return false;
			}
		} ]
	});
}
//-->
</script>
