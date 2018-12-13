<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="deploymentList" title="deploy.process" actionUrl="activitiController.do?processDeploymentGrid" idField="id">
			<t:dgCol title="ProcessDefinitionId" field="id" width="100" hidden="true"></t:dgCol>
			<t:dgCol title="DeploymentId" field="deploymentId" width="70" hidden="true"></t:dgCol>
			<t:dgCol title="common.name" field="name" width="70"></t:dgCol>
			<t:dgCol title="common.process.key" field="key" width="70"></t:dgCol>
			<t:dgCol title="common.version" field="version"></t:dgCol>
			<t:dgCol title="common.status" field="suspensionState" replace="已激活_1,已挂起_2"></t:dgCol>
			<t:dgCol title="picture.name" field="diagramResourceName" hidden="true"></t:dgCol>
			<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
			<t:dgFunOpt funname="deploymentimg(deploymentId,diagramResourceName,name)" title="common.process.chart"></t:dgFunOpt>
			<t:dgConfOpt exp="suspensionState#eq#1" url="activitiController.do?setProcessState&state=suspend&processDefinitionId={id}" title="pending" message="is.pending"></t:dgConfOpt>
			<t:dgConfOpt exp="suspensionState#eq#2" url="activitiController.do?setProcessState&state=active&processDefinitionId={id}" title="common.active" message="preferences.isActive"></t:dgConfOpt>
			<t:dgDelOpt url="activitiController.do?deleteProcess&key={key}&deploymentId={deploymentId}" title="common.delete"></t:dgDelOpt>
			<t:dgToolBar title="deploy.process" icon="icon-edit" url="activitiController.do?openDeployProcess" funname="openuploadwin"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>