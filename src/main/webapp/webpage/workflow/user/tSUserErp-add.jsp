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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tSUserErpController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${tSUserErpPage.id }"/>
		<input id="createName" name="createName" type="hidden" value="${tSUserErpPage.createName }"/>
		<input id="createBy" name="createBy" type="hidden" value="${tSUserErpPage.createBy }"/>
		<input id="createDate" name="createDate" type="hidden" value="${tSUserErpPage.createDate }"/>
		<input id="updateName" name="updateName" type="hidden" value="${tSUserErpPage.updateName }"/>
		<input id="updateBy" name="updateBy" type="hidden" value="${tSUserErpPage.updateBy }"/>
		<input id="updateDate" name="updateDate" type="hidden" value="${tSUserErpPage.updateDate }"/>
		<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${tSUserErpPage.sysOrgCode }"/>
		<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${tSUserErpPage.sysCompanyCode }"/>
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<!-- <tr>
					<td align="right">
						<label class="Validform_label">
							用户账号:
						</label>
					</td>
					<td class="value">
					     	 <input id="userId" name="userId" type="text" style="width: 150px" class="inputxt" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户账号</label>
						</td>
				</tr> -->
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
							第三方用户账号:
						</label>
					</td>
					<td class="value">
					     	 <input id="erpNo" name="erpNo" type="text" style="width: 150px" class="inputxt" datatype="*"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">第三方系统账号</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否绑定系统用户:
						</label>
					</td>
					<td class="value">
					     	<input name="bindUserFlag" type="radio" onclick="bindUser('0')" checked="true" value="0">
						     	不绑定
						    <input name="bindUserFlag" type="radio" onclick="bindUser('1')" value="1">
						     	 绑定
							<span class="Validform_checktip">不绑定则会生成一个对应的系统用户</span>
							<label class="Validform_label" style="display: none;">是否绑定系统用户</label>
						</td>
				</tr>
				<tr id="userId_tr" style="display:none">
					<td align="right">
						<label class="Validform_label">
							系统用户账号:
						</label>
					</td>
					<td class="value">
					     	 <input id="userId" name="userId" type="text" readonly="readonly" onclick="openUserSelect()" style="width: 150px" class="inputxt"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">系统用户账号</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							真实姓名:
						</label>
					</td>
					<td class="value">
					     	 <input id="userName" name="userName" type="text" style="width: 150px" class="inputxt" datatype="*"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户名称</label>
						</td>
				</tr>
				<tr>
					<td align="right" nowrap><label class="Validform_label">  <t:mutiLang langKey="common.phone"/>: </label></td>
					<td class="value">
		                <input class="inputxt" name="mobilePhone" value="" datatype="m" errormsg="手机号码不正确" ignore="ignore">
		                <span class="Validform_checktip"></span>
		            </td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.tel"/>: </label></td>
					<td class="value">
		                <input class="inputxt" name="officePhone" value="" datatype="n" errormsg="办公室电话不正确" ignore="ignore">
		                <span class="Validform_checktip"></span>
		            </td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.common.mail"/>: </label></td>
					<td class="value">
		                <input class="inputxt" name="email" value="" datatype="e" errormsg="邮箱格式不正确!" ignore="ignore">
		                <span class="Validform_checktip"></span>
		            </td>
				</tr>
				
			</table>
		</t:formvalid>
 </body>
 <script type="text/javascript">
 function bindUser(flag){
	 if(flag=='1'){
		 $("#userId_tr").css("display","");
		 $("#userId").val("");
	 }else{
		 $("#userId_tr").css("display","none");
		 $("#userId").val("");
	 }
 }
 
 function openUserSelect() {    
		$.dialog({content: 'url:userController.do?userSelect', 
			zIndex: 9999, 
			title: '用户列表', 
			lock: false, 
			width: '1000px', 
			height: '600px', 
			opacity: 0.4, 
			button: [       
			        {name: '确定', callback: callbackUserSelect, focus: true},       
			        {name: '取消', callback: function (){}}   ]
			});
	}
	function callbackUserSelect() {
		var iframe = this.iframe.contentWindow;
		var rowsData = iframe.$('#userList1').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('<t:mutiLang langKey="common.please.select.edit.item"/>');
			return;
			}
		var userName=rowsData[0].userName;
		$("#userId").val(userName);
		//$("#runningListtb").find("input[name='startUserId']").blur();
	}
 </script>
</html>