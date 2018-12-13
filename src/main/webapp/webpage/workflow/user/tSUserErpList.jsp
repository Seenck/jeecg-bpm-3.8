<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tSUserErpList" checkbox="false" pagination="true" fitColumns="true" title="第三方系统用户" actionUrl="tSUserErpController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="第三方系统类型"  field="sysCode"    queryMode="single" dictionary="syscode" width="120"></t:dgCol>
    <t:dgCol title="第三方用户账号"  field="erpNo"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="用户账号"  field="userId"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="真实姓名"  field="userName"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="手机"  field="mobilePhone"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="办公电话"  field="officePhone"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="邮箱"  field="email"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <%-- <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tSUserErpController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
   <t:dgToolBar title="录入" icon="icon-add" url="tSUserErpController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tSUserErpController.do?goUpdate" funname="update"></t:dgToolBar>
   <%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="tSUserErpController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tSUserErpController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入第三方系统用户" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导入模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
//导入
 function ImportXls() {
 	openuploadwin('Excel导入', 'tSUserErpController.do?upload', "tSUserErpList");
 }

 //模板下载
 function ExportXlsByT() {
 	JeecgExcelExport("tSUserErpController.do?exportXlsByT","tSUserErpList");
 }
 </script>