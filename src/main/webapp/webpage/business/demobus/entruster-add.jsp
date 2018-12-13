<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>委托人</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="activitiController.do?doEntrust" tiptype="4">
		<input id="taskId" name="taskId" type="hidden" value="${taskId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 委托人: </label></td>
				<td class="value" nowrap>
					<input name="last" id="last" readonly="readonly" datatype="*" type="text"/>  
					<t:choose hiddenName="id" hiddenid="id" url="activitiController.do?goEntrustSingle" name="entrusterList" height="450px" width="600px" icon="icon-search" title="用户列表" textname="last" isclear="true"></t:choose>
			     	<input id="id" name="id" readonly="readonly" type="hidden" style="width: 150px" class="inputxt">
					<span class="Validform_checktip"></span>
				</td>
			</tr>
		</table>
	</t:formvalid>
 </body>