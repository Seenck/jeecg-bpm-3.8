<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:datagrid fitColumns="false" name="groupTaskList" queryMode="group" title="common.task.group" actionUrl="taskController.do?taskGroupList" idField="id">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="标题" field="bpmBizTitle" width="150"></t:dgCol>
 <t:dgCol title="common.process.id" field="Process_processDefinition_id" width="80" query="true"></t:dgCol>
 <t:dgCol title="common.process.name" field="Process_processDefinition_name" width="150" query="true"></t:dgCol>
<t:dgCol title="process.instance" field="Process_task_processInstanceId" width="100"></t:dgCol>
 <t:dgCol title="common.process.user" field="TSUser_userName" width="100"></t:dgCol>
 <t:dgCol title="common.process.assignee" field="Process_task_assignee" width="100"></t:dgCol>
 <t:dgCol title="common.begintime" field="Process_task_createTime" width="130" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
 <t:dgCol title="common.endtime" field="Process_task_dueTime" width="130" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
 <t:dgCol title="common.status" field="TSPrjstatus_description" width="80"></t:dgCol>
 <t:dgCol title="common.process.current" field="Process_task_name" width="100"></t:dgCol>
 <t:dgCol hidden="true" title="TASK ID(该字段隐藏)" field="Process_task_id"></t:dgCol>
 <t:dgCol hidden="true" title="key" field="Process_task_taskDefinitionKey"></t:dgCol>
 <t:dgCol title="common.operation" field="opt" width="200"></t:dgCol>
 <t:dgConfOpt exp="Process_task_assignee#empty#true" url="activitiController.do?claim&taskId={Process_task_id}" message="common.claim.sure" title="common.claim"  urlclass="ace_button"  urlfont="fa-cog"></t:dgConfOpt>
 <t:dgFunOpt exp="Process_task_assignee#empty#false" funname="openhandle(Process_task_id,Process_task_name)" title="process.handle" urlclass="ace_button"  urlfont="fa-book"></t:dgFunOpt>
</t:datagrid>

