<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>催办</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="taskController.do?taskNotification" tiptype="4">
		<input id="processInstanceId" name="processInstanceId" type="hidden" value="${processInstanceId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 催办说明: </label></td>
				<td class="value" nowrap>
					<textarea id="remarks" name="remarks" rows="5" cols="50" datatype="*"></textarea>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
		</table>
	</t:formvalid>
 </body>