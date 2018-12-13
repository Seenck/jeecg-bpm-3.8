<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:tabs  id="tt" iframe="false" tabPosition="top">
	<t:tab  href="taskController.do?goProcessHisForm&load=detail&processInstanceId=${processInstanceId }" icon="fa fa-building" title="common.process.page" id="formPage"></t:tab>
	<t:tab  href="taskController.do?goProcessHisOperate&processInstanceId=${processInstanceId }" icon="fa fa-user" title="common.process.history" id="taskOperate" heigth="100"></t:tab>
	<t:tab  href="activitiController.do?viewProcessInstanceHistory&processInstanceId=${processInstanceId }" icon="fa fa-sitemap" title="common.process.chart" id="processPicture"></t:tab>
</t:tabs>
