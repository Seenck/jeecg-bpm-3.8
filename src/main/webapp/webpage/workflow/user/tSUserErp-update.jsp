<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>第三方系统用户</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tSUserErpController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${tSUserErpPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tSUserErpPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tSUserErpPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tSUserErpPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tSUserErpPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tSUserErpPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSUserErpPage.updateDate }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${tSUserErpPage.sysOrgCode }">
					<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${tSUserErpPage.sysCompanyCode }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								系统用户账号:
							</label>
						</td>
						<td class="value">
						     	 <input id="userId" name="userId" type="text" style="width: 150px" class="inputxt"  value='${tSUserErpPage.userId}' datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">系统用户账号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								第三方系统账号:
							</label>
						</td>
						<td class="value">
						     	 <input id="erpNo" name="erpNo" type="text" style="width: 150px" class="inputxt"  value='${tSUserErpPage.erpNo}' datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">第三方系统账号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								第三方系统类型:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="sysCode" type="list"
										typeGroupCode="syscode" defaultVal="${tSUserErpPage.sysCode}" hasLabel="false"  title="第三方系统类型" datatype="*" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">第三方系统类型</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								用户名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="userName" name="userName" type="text" style="width: 150px" class="inputxt"  value='${tSUserErpPage.userName}' datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户名称</label>
						</td>
					</tr>
					
			</table>
		</t:formvalid>
 </body>
</html>