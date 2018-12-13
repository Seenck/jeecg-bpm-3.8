<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><t:mutiLang langKey="jump.process.node"></t:mutiLang></title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" layout="table" action="processInstanceController.do?skipNode">
		<input id="taskId" name="taskId" type="hidden" value="${taskId }" />
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<t:mutiLang langKey="choose.node.to.jump"></t:mutiLang>
					</label>
				</td>
				<td class="value" nowrap>
					<select name="skipTaskNode">
						<c:forEach items="${taskList}" var="taskList">
							<option value="${taskList.taskKey}">${taskList.name }${taskList.taskKey}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>