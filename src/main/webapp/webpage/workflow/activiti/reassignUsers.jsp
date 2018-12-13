<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title><t:mutiLang langKey="role.collection"></t:mutiLang></title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:datagrid name="roleList" title="please.choose.appointer" actionUrl="processInstanceController.do?datagridUsers" idField="id" checkbox="true" queryMode="group" showRefresh="false">
		<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
		<t:dgCol title="user.account" field="userName" width="50" query="true"></t:dgCol>
		<t:dgCol title="common.username" field="realName" width="50" query="true"></t:dgCol>
	</t:datagrid>
</body>
</html>
