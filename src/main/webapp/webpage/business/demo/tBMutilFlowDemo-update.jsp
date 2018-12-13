<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>出差借款多流程审批</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBMutilFlowDemoController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${tBMutilFlowDemoPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								姓名:
							</label>
						</td>
						<td class="value">
						    <input id="name" name="name" type="text" maxlength="32" style="width: 150px" class="inputxt"  datatype="*" ignore="checked"  value='${tBMutilFlowDemoPage.name}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">姓名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								出差开始时间:
							</label>
						</td>
						<td class="value">
									  <input id="beginDate" name="beginDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"  ignore="ignore" value='<fmt:formatDate value='${tBMutilFlowDemoPage.beginDate}' type="date" pattern="yyyy-MM-dd"/>'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">出差开始时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								出差结束时间:
							</label>
						</td>
						<td class="value">
									  <input id="endDate" name="endDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"  ignore="ignore" value='<fmt:formatDate value='${tBMutilFlowDemoPage.endDate}' type="date" pattern="yyyy-MM-dd"/>'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">出差结束时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								出差事由:
							</label>
						</td>
						<td class="value">
						    <input id="remarks" name="remarks" type="text" maxlength="200" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tBMutilFlowDemoPage.remarks}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">出差事由</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								借款金额:
							</label>
						</td>
						<td class="value">
						    <input id="borrowBalance" name="borrowBalance" type="text" maxlength="32" style="width: 150px" class="inputxt"  datatype="*" ignore="checked"  value='${tBMutilFlowDemoPage.borrowBalance}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">借款金额</label>
						</td>
					</tr>
				
			</table>
		</t:formvalid>
 </body>
