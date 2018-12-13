<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:datagrid name="taskFinishedList" title="已结束流程列表" actionUrl="activitiController.do?taskFinishedGrid" idField="id">
 <t:dgCol title="流程ID" field="id"></t:dgCol>
 <t:dgCol title="流程定义ID" field="processDefinitionId"></t:dgCol>
 <t:dgCol title="业务ID" field="businessKey"></t:dgCol>
 <t:dgCol title="流程启动时间" field="startTime"></t:dgCol>
 <t:dgCol title="流程结束时间" field="endTime"></t:dgCol>
 <t:dgCol title="流程结束原因" field="deleteReason"></t:dgCol>
</t:datagrid>

