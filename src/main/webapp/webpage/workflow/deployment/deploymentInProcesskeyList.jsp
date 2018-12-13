<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="deploymentInProcesskeyList" title="common.process.published" actionUrl="activitiController.do?processDeploymentInProcesskeyGrid&processkey=${processkey}" idField="id">
			<t:dgCol title="ProcessDefinitionId" field="id" width="100" hidden="true"></t:dgCol>
			<t:dgCol title="DeploymentId" field="deploymentId" width="70" hidden="true"></t:dgCol>
			<t:dgCol title="common.name" field="name" width="70"></t:dgCol>
			<t:dgCol title="key" field="key" width="70"></t:dgCol>
			<t:dgCol title="common.version" field="version"></t:dgCol>
			<t:dgCol title="common.status" field="suspensionState" replace="已激活_1,已挂起_2"></t:dgCol>
			<t:dgCol title="picture.name" field="diagramResourceName" hidden="true"></t:dgCol>
			<t:dgCol title="resource.name" field="resourceName" hidden="true"></t:dgCol>
			<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
			<t:dgFunOpt funname="deploymentimg(deploymentId,diagramResourceName,name)" title="common.process.chart" urlclass="ace_button" urlfont="fa-image"></t:dgFunOpt>
			<t:dgConfOpt exp="suspensionState#eq#1" url="activitiController.do?setProcessState&state=suspend&processDefinitionId={id}" title="pending" message="is.pending" urlclass="ace_button" urlfont="fa-pause"></t:dgConfOpt>
			<t:dgConfOpt exp="suspensionState#eq#2" url="activitiController.do?setProcessState&state=active&processDefinitionId={id}" title="common.active" message="preferences.isActive" urlclass="ace_button" urlStyle="background-color:#FF6347" urlfont="fa-play"></t:dgConfOpt>
			<t:dgDelOpt url="activitiController.do?deleteProcess&key={key}&deploymentId={deploymentId}" title="common.delete" urlclass="ace_button" urlStyle="background-color:#ec4758;" urlfont="fa-trash-o"></t:dgDelOpt>
			<t:dgFunOpt funname="downloadXml(deploymentId,resourceName,name)" title="download.process" urlclass="ace_button" urlfont="fa-download"></t:dgFunOpt>
			<t:dgFunOpt funname="deploymentNodeList(deploymentId)" title="common.process.deplayment.node" urlclass="ace_button" urlfont="fa-sitemap"></t:dgFunOpt>
			<t:dgToolBar title="deploy.process" icon="icon-edit" url="activitiController.do?openDeployProcess" funname="openuploadwin"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>

<script type="text/javascript">
	
	function deploymentNodeList(deploymentId) {
		addOneTab('<t:mutiLang langKey="common.process.deplayment.node.list"/>', 'activitiController.do?deploymentNodeList&deploymentId=' + deploymentId);
	}
</script>