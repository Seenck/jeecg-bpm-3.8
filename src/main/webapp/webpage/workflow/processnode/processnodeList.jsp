<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
 <head>
  <t:base type="jquery,easyui,tools"></t:base>
  <title><t:mutiLang langKey="common.node.list"/></title>
 </head>
 <body>
  <t:datagrid name="processnodeList" title="common.node.list" actionUrl="processController.do?datagridNode&processid=${processid }" idField="processnodeid">
   <t:dgCol title="id" field="id" width="30" hidden="true"></t:dgCol>
   <t:dgCol title="common.node.name" field="processnodename" width="70"></t:dgCol>
   <t:dgCol title="common.node.code" field="processnodecode" width="70"></t:dgCol>
   <t:dgCol title="common.node.form_pc" field="modelandview" width="70"></t:dgCol>
   <t:dgCol title="common.node.form_mobile" field="modelandviewMobile" width="70"></t:dgCol>
   <t:dgCol title="common.node.timeout" field="nodeTimeout" width="40"></t:dgCol>
   <t:dgCol title="common.node.bpmstatus" field="nodeBpmStatus" dictCondition="bpm_status" width="40"></t:dgCol>
   <t:dgCol title="common.node.process" field="TPProcess_processname" hidden="true" width="70"></t:dgCol>
   <t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt url="processController.do?delNode&id={id}" title="common.delete" urlclass="ace_button" urlStyle="background-color:#ec4758;" urlfont="fa-trash-o"></t:dgDelOpt>
   <t:dgFunOpt funname="conifgRole(id)" title="common.auth" urlclass="ace_button" urlfont="fa-user"></t:dgFunOpt>
   <t:dgFunOpt funname="conifgNodeAuth(id)" title="common.node.auth" urlclass="ace_button" urlfont="fa-user"></t:dgFunOpt>
  </t:datagrid>
  <div id="processnodeListtb" style="padding:3px; height:25px">
   <div style="float: left;">
    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('<t:mutiLang langKey="common.node.add"/>','processController.do?addorupdateNode&processid=${processid }','processnodeList')"><t:mutiLang langKey="common.node.add"/></a>
    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('<t:mutiLang langKey="common.node.update"/>','processController.do?addorupdateNode&processid=${processid }','processnodeList')"><t:mutiLang langKey="common.node.update"/></a>
   </div>
  </div>
  <script type="text/javascript">
	function conifgRole(id){
		createdetailwindow('<t:mutiLang langKey="common.auth"/>','processController.do?nodeFun&nodeId='+id);
	}
	
	function conifgNodeAuth(id){
		createdetailwindow('<t:mutiLang langKey="common.node.auth"/>','processController.do?nodeAuth&nodeId='+id,800,400);
	}
</script>
</body>