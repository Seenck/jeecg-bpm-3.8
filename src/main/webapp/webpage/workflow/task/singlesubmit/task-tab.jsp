<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<link id="easyuiTheme" rel="stylesheet" href="plug-in/easyui/themes/metrole/main.css" type="text/css"></link>
<t:tabs  id="tt" iframe="false" tabPosition="top">
	<t:tab  href="taskController.do?goTaskOperate&taskId=${taskId }" icon="fa fa-user" title="common.task.operate" id="taskOperate"></t:tab>
	<t:tab  href="activitiController.do?viewProcessInstanceHistory&processInstanceId=${processInstanceId }" icon="fa fa-sitemap" title="common.process.chart" id="processPicture"></t:tab>
</t:tabs>
