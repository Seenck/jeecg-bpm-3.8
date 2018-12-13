<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>服务处理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBServicemanageController.do?save">
			<input id="id" name="id" type="hidden" value="${tBServicemanagePage.id }">
   			<input name="code" id="code" type="hidden" value="${tBServicemanagePage.TSPrjstatus.code}" />
   			<input name="taskId" id="taskId" type="hidden" value="${taskId}" />
   			<input name="keys" id="keys" type="hidden" />
   			<input name="values" id="values" type="hidden" />
   			<input name="types" id="types" type="hidden" />
			
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否审批:
						</label>
					</td>
					<td class="value">
						<select name="iconType" id="iconType">
					      <option value="true">
					       审批
					      </option>
					      <option value="false">
					       不审批
					      </option>
					    </select>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否复核:
						</label>
					</td>
					<td class="value">
						<select name="review" id="review">
					      <option value="true" >
					       复核
					      </option>
					      <option value="false">
					       不复核
					      </option>
					    </select>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				
			</table>
		</t:formvalid>
 </body>