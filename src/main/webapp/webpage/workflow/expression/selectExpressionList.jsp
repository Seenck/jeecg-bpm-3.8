<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>流程表达式</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:datagrid name="selectExpressionList" title="选择表达式" actionUrl="tPExpressionController.do?datagrid" idField="id"  queryMode="group" checkbox="false" showRefresh="true">
	<t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="表达式名称"  field="name"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="表达式"  field="expression"  queryMode="group"  width="120"></t:dgCol>
</t:datagrid>
</body>
</html>

