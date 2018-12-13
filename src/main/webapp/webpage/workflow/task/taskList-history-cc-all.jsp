<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:datagrid fitColumns="true" name="historyCcTaskList" queryMode="group" title="抄送给我的任务" actionUrl="taskController.do?taskAllCcHistoryList" idField="id">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="标题" field="bpmBizTitle" width="150"></t:dgCol>
 <t:dgCol title="common.process.id" field="procDefId" width="80" query="true"></t:dgCol>
 <t:dgCol title="common.process.name" field="prodef_name" width="150" query="true"></t:dgCol>
 <t:dgCol title="process.instance" field="proInsl_procInstId" width="100"></t:dgCol>
 <t:dgCol title="process.task.name" field="name" width="100"></t:dgCol>
 <t:dgCol title="common.process.user" field="proInsl_startUserId" width="100"></t:dgCol>
 <t:dgCol title="common.process.assignee" field="assignee" width="100"></t:dgCol>
 <t:dgCol title="common.begintime" field="startTime" width="130" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
 <t:dgCol title="common.endtime" field="endTime" width="130" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
 <t:dgCol title="common.duration" field="durationStr"  width="100"></t:dgCol>
 <t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
 <%-- <t:dgFunOpt funname="viewHistory(proInsl_procInstId)" title="<font style=color:red>查看</font>"></t:dgFunOpt>--%>
 <t:dgFunOpt funname="goProcessHisTab(proInsl_procInstId,name)" title="查看审批" urlclass="ace_button"  urlStyle="background-color:#FF6347" urlfont="fa fa-history"></t:dgFunOpt>
 </t:datagrid>
<SCRIPT type="text/javascript">
//查看流程历史
function viewHistory(processInstanceId){
	var url = "";
	var title = "流程历史";
	url = "activitiController.do?viewProcessInstanceHistory&processInstanceId="+processInstanceId;
	addOneTab(title, url);
}
</SCRIPT>