<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>服务上报</title>
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
							上报来源:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="trapSource" name="trapSource" ignore="ignore"
							   value="${tBServicemanagePage.trapSource}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							服务团体:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="serviceTeam" name="serviceTeam" ignore="ignore"
							   value="${tBServicemanagePage.serviceTeam}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							服务目录:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="serviceItem" name="serviceItem" ignore="ignore"
							   value="${tBServicemanagePage.serviceItem}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							主题:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="subject" name="subject" ignore="ignore"
							   value="${tBServicemanagePage.subject}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							描述:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="content" name="content" ignore="ignore"
							   value="${tBServicemanagePage.content}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							紧急程度:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="urgentLevel" name="urgentLevel" ignore="ignore"
							   value="${tBServicemanagePage.urgentLevel}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							影响程度:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="effectLevel" name="effectLevel" ignore="ignore"
							   value="${tBServicemanagePage.effectLevel}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							客户端ip:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="clientIp" name="clientIp" ignore="ignore"
							   value="${tBServicemanagePage.clientIp}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							提交时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker()"  style="width: 150px" id="submitDate" name="submitDate" ignore="ignore"
							   value="<fmt:formatDate value='${tBServicemanagePage.submitDate}' type="date" pattern="yyyy-MM-dd"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							提交用户ID:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="submitBy" name="submitBy" ignore="ignore"
							   value="${tBServicemanagePage.submitBy}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							提交用户部门id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="departId" name="departId" ignore="ignore"
							   value="${tBServicemanagePage.departId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							提交用户部门名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="departName" name="departName" ignore="ignore"
							   value="${tBServicemanagePage.departName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>