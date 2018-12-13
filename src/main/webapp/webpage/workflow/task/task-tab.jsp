<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:tabs  id="tt" iframe="false" tabPosition="top">
	<t:tab  href="taskController.do?goTaskForm&taskId=${taskId }" icon="fa fa-building" title="common.task.form" id="formPage"></t:tab>
	<t:tab  href="taskController.do?goTaskOperate&taskId=${taskId }" icon="fa fa-user" title="common.task.operate" id="taskOperate"></t:tab>
	<t:tab  href="activitiController.do?viewProcessInstanceHistory&processInstanceId=${processInstanceId }" icon="fa fa-sitemap" title="common.process.chart" id="processPicture"></t:tab>
</t:tabs>
