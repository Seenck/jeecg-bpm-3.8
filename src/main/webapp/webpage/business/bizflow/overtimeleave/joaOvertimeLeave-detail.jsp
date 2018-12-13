<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>调休申请单</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<t:base type="jquery,aceform,easyui,DatePicker,validform"></t:base>
<link rel="stylesheet" href="plug-in/ueditor/ext/ueditor-ext.css" type="text/css"></link>
</head>
<style>
	.panel-body {
	     padding: 0px; 
	}
</style>
<body>
	<t:formvalid layout="table" dialog="true" formid="formobj" usePlugin="password" action="joaOvertimeLeaveController.do?doUpdate" tiptype="1">
		<input type="hidden" id="btn_sub" class="btn_sub"/>
		<input type="hidden" id="id" name="id" value="${joaOvertimeLeave.id}"/>
		<div class="tableform-shadow" style="margin: 0 auto;margin-top:10px;padding: 20px; border-width: 0px; width: 794px;">
			<div class="ueditor-box">
				<p style="text-align: center;">
					<br>
				</p>
				<h1 style="text-align: center;margin-bottom:10px;">
					<span style="text-decoration: none;font-size: 24pt;font-family: 宋体;">调休申请单</span><br />
				</h1>
			</div>
			<table align="center">
				<tbody>
					<tr class="firstRow">
						<td class="bg-bai" width="141.66666666666666" valign="middle" align="center" style="padding: 4px; border-width: 1px; border-style: solid; word-break: break-all;">
							申请人
						</td>
						<td width="226" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid; word-break: break-all;" align="center">
							<input type="text" size="1" name="applyUser" class="x-form-field tableform-field x-form-text" readonly="readonly" style="width: 100%;" value="${joaOvertimeLeave.applyUser}" />
						</td>
						<td width="154.66666666666666" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid; word-break: break-all;" align="center">
							部门
						</td>
						<td width="233" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid;" align="center">
							<input id="department" size="1" type="text" name="department" class="x-form-field tableform-field x-form-text" readonly="readonly" style="width: 100%;" value="${joaOvertimeLeave.department}" />
						</td>
					</tr>
					<tr>
						<td width="140.66666666666666" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid;" align="center">
							申请时间
						</td>
						<td width="226" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid;" align="center" >
							<input name="applyTime" type="text" style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent; width:100%;" 
								class="tableform-field" readonly="readonly" value="<fmt:formatDate value='${joaOvertimeLeave.applyTime}' type='date' pattern='yyyy-MM-dd'/>"/> 
						</td>
						<td width="153.66666666666666" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid; word-break: break-all;" align="center">
							<span style="text-align: -webkit-center; color: rgb(255, 0, 0);">*</span><span style="text-align: -webkit-center;">调休时长</span>
						</td>
						<td width="233" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid; background-color: rgb(228, 255, 219);" align="center" height="" data-fill="0" id="ext-gen3309">
							<input id="leaveTime" name="leaveTime" type="text" class="x-form-field tableform-field x-form-text" readonly="readonly"  value="${joaOvertimeLeave.leaveTime}"/>(天/小时)
						</td>
					</tr>
					<tr>
						<td width="153.66666666666666" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid; word-break: break-all;" align="center">
							<span style="text-align: -webkit-center; color: rgb(255, 0, 0);">*</span><span style="text-align: -webkit-center;">开始时间</span>
						</td>
						<td width="226" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid; word-break: break-all; background-color: rgb(228, 255, 219);" align="center">
							<input name="beginTime" type="text" style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent; width:100%;" 
								class="tableform-field" readonly="readonly" value="<fmt:formatDate value='${joaOvertimeLeave.beginTime}' type='date' pattern='yyyy-MM-dd HH:mm:ss'/>"/> 
						</td>
						<td width="153.66666666666666" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid; word-break: break-all;" align="center">
							<span style="text-align: -webkit-center; color: rgb(255, 0, 0);">*</span><span style="text-align: -webkit-center;">结束时间</span>
						</td>
						<td width="233" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid; word-break: break-all; background-color: rgb(228, 255, 219);" align="center">
							<input name="endTime" type="text" style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent; width:100%;" 
								class="tableform-field" readonly="readonly" value="<fmt:formatDate value='${joaOvertimeLeave.endTime}' type='date' pattern='yyyy-MM-dd HH:mm:ss'/>"/> 
						</td>
					</tr>
					<tr>
						<td width="140.66666666666666" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid;" align="center">
							注意
						</td>
						<td valign="middle" align="left" data-fill="0" colspan="3" rowspan="1" style="padding: 4px; border-width: 1px; border-style: solid; word-break: break-all;">
							<p>调休<span style="background-color: rgb(255, 255, 255);">半天可以写0.5不能写0.1,0.2等小数(切记)2.全天调休以00:00:00开始以23:59:59结束,下午调休以12:00:00开始</span>
							<span style="background-color: rgb(255, 255, 255);">最小调休时间为0.5天,情各领导审批时注意.</span></p>
						</td>
					</tr>
					<tr>
						<td width="140.66666666666666" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid;" align="center">
							调休期间联系方式
						</td>
						<td valign="middle" style="padding: 4px; border-width: 1px; border-style: solid; background-color: rgb(228, 255, 219);" align="left" rowspan="1" colspan="3">
							<input id="contactWay" type="text" size="1" readonly="readonly" name="contactWay" class="x-form-field tableform-field x-form-text"  style="width: 100%;" value="${joaOvertimeLeave.contactWay}">
						</td>
					</tr>
					<tr>
						<td width="140.66666666666666" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid;" align="center" height="54">
							工作交接安排
						</td>
						<td valign="middle" style="padding: 4px; border-width: 1px; border-style: solid; background-color: rgb(228, 255, 219);" align="left" rowspan="1" colspan="3" >
							<textarea id="workPlan" name="workPlan" readonly="readonly" rows="4" cols="3" class="x-form-field tableform-field x-form-text x-form-textarea tabIndent" 
								 style="width: 100%; height: 80px;">${joaOvertimeLeave.workPlan}</textarea>
						</td>
					</tr>
					<tr>
						<td width="140.66666666666666" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid;" align="center">
							工作代理人
						</td>
						<td valign="middle" style="padding: 4px; border-width: 1px; border-style: solid; background-color: rgb(228, 255, 219);" align="left" rowspan="1" colspan="3">
							<input id="workAgent" type="text" size="1" readonly="readonly" name="workAgent" class="x-form-field tableform-field x-form-text"  style="width: 100%;" value="${joaOvertimeLeave.workAgent}">
						</td>
					</tr>
					<tr style="height:300px;">
						<td valign="middle" colspan="4" rowspan="1" style="padding: 0px; border-width: 1px; border-style: solid;height:100px;">
							<t:datagrid  actionUrl="joaOvertimeController.do?overtimeDatagrid&leaveId=${joaOvertimeLeave.id}" name="overtimeList" title="加班工时" fit="true" fitColumns="true" idField="id" checkbox="false">
								<t:dgCol title="id" hidden="true" field="id"></t:dgCol>
								<t:dgCol title="加班开始时间" field="beginTime" rowspan="2" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
								<t:dgCol title="加班结束时间" field="endTime" rowspan="2" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
								<t:dgCol title="总加班时间" field="total" rowspan="2"></t:dgCol>
								<t:dgCol title="未经补偿时间" field="makeupTime" rowspan="2"></t:dgCol>
								<t:dgCol title="<b>调休申请</b>" rowspan="1" colspan="2" newColumn="true" ></t:dgCol>
								<t:dgCol title="天" field="applyDay" align="center" colspan="1"></t:dgCol>
								<t:dgCol title="小时" field="applyHour" align="center" colspan="1" ></t:dgCol>
							</t:datagrid>
						</td>
					</tr>
					<tr>
						<td width="140.66666666666666" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid;" align="center" height="49">
							直接领导审批
						</td>
						<td width="226" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid;" align="center" height="49">
							<input id="leaderRemark" readonly="readonly" type="text" size="1" name="leaderRemark" class="x-form-field tableform-field x-form-text" style="width: 100%;" value="${joaOvertimeLeave.leaderRemark}">
						</td>
						<td width="153.66666666666666" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid;" align="center" height="49">
							部门领导审批
						</td>
						<td width="233" valign="middle" style="padding: 4px; border-width: 1px; border-style: solid;" align="center" height="49" >
							<input id="deptLeaderRemark" readonly="readonly" type="text" size="1" name="deptLeaderRemark" class="x-form-field tableform-field x-form-text" style="width: 100%;" value="${joaOvertimeLeave.deptLeaderRemark}">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</t:formvalid>
<script type="text/javascript">
$(function(){	
    //查看模式情况下,删除和上传附件功能禁止使用
	if(location.href.indexOf("load=detail")!=-1){
		$(".jeecgDetail").hide();
	}
	
	if(location.href.indexOf("mode=read")!=-1){
		//查看模式控件禁用
		$("#formobj").find(":input").attr("disabled","disabled");
	}
	if(location.href.indexOf("mode=onbutton")!=-1){
		//其他模式显示提交按钮
		$("#sub_tr").show();
	}
   });

  var neibuClickFlag = false;
  function neibuClick() {
	  neibuClickFlag = true; 
	  $('#btn_sub').trigger('click');
  }
</script>
</body>
</html>