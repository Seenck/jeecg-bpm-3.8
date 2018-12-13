<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,tools,easyui,DatePicker"></t:base>
<t:tabs id="tt" iframe="true" tabPosition="bottom">
	<t:tab href="taskController.do?goMyTaskList" icon="fa fa-user"  title="common.task.my" id="default"></t:tab>
	<t:tab href="taskController.do?goGroupTaskList" icon="fa fa-group" title="common.task.group" id="autocom"></t:tab>
	<t:tab href="taskController.do?goHistoryTaskList" icon="fa fa-history" title="common.task.history" id="autoSelect"></t:tab>
</t:tabs>