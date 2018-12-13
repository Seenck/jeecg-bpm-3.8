<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" layout="div" dialog="true" action="dbController.do?saveTable">
		<input name="id" type="hidden" value="${table.id }">
		<input name="TSType.id" type="hidden" value="${table.TSType.id}">
		<input name="entityKey" type="hidden" value="${table.entityKey}">
		<fieldset class="step">
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="db.name"></t:mutiLang>
				</label>
				<input name="tableName" class="inputxt" value="${table.tableName }" datatype="s2-50">
				<span class="Validform_checktip">
					<t:mutiLang langKey="dbname.rang2to15"></t:mutiLang>
				</span>
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="common.entity"></t:mutiLang>
				</label>
				<input name="entityName" class="inputxt" value="${table.entityName}" datatype="s2-200">
				<span class="Validform_checktip">
					<t:mutiLang langKey="entityname.rang2-15"></t:mutiLang>
				</span>
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="common.description"></t:mutiLang>
				</label>
				<input name="tableTitle" class="inputxt" value="${table.tableTitle}">
			</div>
		</fieldset>
	</t:formvalid>
</body>
</html>
