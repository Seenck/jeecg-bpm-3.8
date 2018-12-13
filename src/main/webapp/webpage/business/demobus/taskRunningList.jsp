<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:datagrid name="taskRunningList" title="运行中流程列表" actionUrl="activitiController.do?taskRunningGrid" idField="id">
  <t:dgCol title="执行ID" field="id"></t:dgCol>
 <t:dgCol title="流程实例ID" field="processInstanceId"></t:dgCol>
 <t:dgCol title="业务ID" field="businessKey"></t:dgCol>
 <t:dgCol title="流程定义ID" field="processDefinitionId"></t:dgCol>
 <t:dgCol title="是否挂起" field="suspended"></t:dgCol>
</t:datagrid>

