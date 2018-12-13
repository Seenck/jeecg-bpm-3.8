<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><t:mutiLang langKey="choose.appointer"></t:mutiLang></title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" layout="table" action="processInstanceController.do?reassign">
		<input id="taskId" name="taskId" type="hidden" value="${taskId }" />
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						<t:mutiLang langKey="appointer"></t:mutiLang>
					</label></td>
				<td class="value" nowrap>
						<input name="userName" name="userName" type="hidden" value="" id="userName"> 
						<input name="realName" class="inputxt" value="" id="realName" readonly="readonly" datatype="*" /> 
						<t:choose hiddenName="userName" hiddenid="userName" url="processInstanceController.do?reassignUsers" name="roleList" width="600px" height="400px" icon="icon-search" title="appointer.list" textname="realName" isclear="true"></t:choose> 
						<span class="Validform_checktip">
						<t:mutiLang langKey="user.cannot.be.multiselect"></t:mutiLang>
					</span></td>
			</tr>
		</table>
	</t:formvalid>
</body>