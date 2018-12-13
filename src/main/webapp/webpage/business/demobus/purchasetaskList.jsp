<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:datagrid name="purchaseList" title="采购审批列表" actionUrl="activitiController.do?taskList&busCode=purchase" idField="id">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="制单人" field="TSUser_userName"></t:dgCol>
 <t:dgCol title="状态" field="TSPrjstatus_description"></t:dgCol>
 <t:dgCol title="当前环节" field="Process_task_name"></t:dgCol>
 <t:dgCol title="办理人" field="Process_task_assignee"></t:dgCol>
 <t:dgCol hidden="true" title="TASK ID(该字段隐藏)" field="Process_task_id"></t:dgCol>
 <t:dgCol hidden="true" title="key" field="Process_task_taskDefinitionKey"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
 <t:dgConfOpt exp="Process_task_assignee#empty#true" url="activitiController.do?claim&taskId={Process_task_id}" message="确定签收?" title="签收"></t:dgConfOpt>
 <t:dgFunOpt exp="Process_task_assignee#empty#false" funname="openhandle(Process_task_id,TSUser_userName)" title="办理"></t:dgFunOpt>
 <t:dgFunOpt funname="processimg(Process_task_id,TSUser_userName)" title="<font style=color:red>流程图</font>"></t:dgFunOpt>
</t:datagrid>
