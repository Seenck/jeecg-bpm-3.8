<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>第三方系统对接密钥管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPKeyConfigController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${tPKeyConfigPage.id }">
					<input id="createBy" name="createBy" type="hidden" value="${tPKeyConfigPage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tPKeyConfigPage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${tPKeyConfigPage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPKeyConfigPage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tPKeyConfigPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPKeyConfigPage.updateDate }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${tPKeyConfigPage.sysOrgCode }">
					<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${tPKeyConfigPage.sysCompanyCode }">
		<table style="width: 90%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								系统标识:
							</label>
						</td>
						<td class="value">
						     	 <input id="sysCode" name="sysCode" type="text" style="width: 150px" class="inputxt" datatype="*" value='${tPKeyConfigPage.sysCode}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">系统标识</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								加签验签密钥:
							</label>
						</td>
						<td class="value">
						     	 <input id="signKey" name="signKey" type="text" style="width: 500px" class="inputxt" datatype="*" value='${tPKeyConfigPage.signKey}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">加签验签密钥</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								加解密密钥:
							</label>
						</td>
						<td class="value">
						     	 <input id="encrypKey" name="encrypKey" type="text" style="width: 500px" class="inputxt"  value='${tPKeyConfigPage.encrypKey}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">加解密密钥</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								是否启用:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="isUsed" type="radio"
										typeGroupCode="isused" defaultVal="${tPKeyConfigPage.isUsed}" hasLabel="false"  title="是否启用" datatype="*"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否启用</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
