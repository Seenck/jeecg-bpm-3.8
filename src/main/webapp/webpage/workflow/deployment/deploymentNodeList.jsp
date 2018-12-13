<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="deploymentNodeList" title="common.process.deplayment.node.list" actionUrl="activitiController.do?processDeploymentNodeGrid&deploymentId=${deploymentId}" idField="id">
			<t:dgCol title="id" field="id" width="30" hidden="true"></t:dgCol>
		    <t:dgCol title="common.node.name" field="processnodename" width="70"></t:dgCol>
		    <t:dgCol title="common.node.code" field="processnodecode" width="70"></t:dgCol>
		    <t:dgCol title="common.node.form_pc" field="modelandview" width="70"></t:dgCol>
		    <t:dgCol title="common.node.form_mobile" field="modelandviewMobile" width="70"></t:dgCol>
		    <t:dgCol title="common.node.timeout" field="nodeTimeout" width="70"></t:dgCol>
		    <t:dgCol title="common.node.bpmstatus" field="nodeBpmStatus" dictCondition="bpm_status" width="70"></t:dgCol>
		</t:datagrid>
	</div>
</div>
