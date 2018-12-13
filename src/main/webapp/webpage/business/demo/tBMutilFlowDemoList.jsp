<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBMutilFlowDemoList" checkbox="false" pagination="true" fitColumns="true" title="出差借款多流程审批" actionUrl="tBMutilFlowDemoController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="name"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="出差开始时间"  field="beginDate"  formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="出差结束时间"  field="endDate"  formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="出差事由"  field="remarks"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="借款金额"  field="borrowBalance"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="出差审批状态"  field="bpmStatus"  queryMode="single"  dictionary="bpm_status"  width="120"></t:dgCol>
   <t:dgCol title="借款审批状态"  field="borrowBpmStatus"  queryMode="single"  dictionary="bpm_status"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="240"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBMutilFlowDemoController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgFunOpt title="出差申请提交" exp="bpmStatus#eq#1"  funname="startProcess(id)" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgFunOpt exp="bpmStatus#ne#1" funname="businessTripProgress(id)" title="出差流程进度"  urlclass="ace_button"  urlStyle="background-color:	#FF6347" urlfont="fa fa-history"></t:dgFunOpt>
   <t:dgFunOpt title="借款申请提交" exp="borrowBpmStatus#eq#1"  funname="startProcessBorrow(id)" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgFunOpt exp="borrowBpmStatus#ne#1" funname="borrowProgress(id)" title="借款流程进度"  urlclass="ace_button"  urlStyle="background-color:	#FF6347" urlfont="fa fa-history"></t:dgFunOpt>
   <t:dgToolBar title="录入" icon="icon-add" url="tBMutilFlowDemoController.do?goAdd" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="tBMutilFlowDemoController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBMutilFlowDemoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBMutilFlowDemoController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 //出差流程进度
 function  businessTripProgress(id){
	 mutilFlowProgress('MFF_01_CHUCHAI',id,'出差流程进度',600,500);
 }
 
 //借款流程进度
 function  borrowProgress(id){
	 mutilFlowProgress('MFF_02_JIEKUAN',id,'借款流程进度');
 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBMutilFlowDemoController.do?upload', "tBMutilFlowDemoList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBMutilFlowDemoController.do?exportXls","tBMutilFlowDemoList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBMutilFlowDemoController.do?exportXlsByT","tBMutilFlowDemoList");
}

//出差流程提交
function startProcess(id){
	 //多流程表单编码
	var flowCode = "MFF_01_CHUCHAI"; 
	var formUrl = "tBMutilFlowDemoController.do?goUpdate&load=detail";
	var formUrlMobile = "tBMutilFlowDemoController.do?goUpdate&load=detail";
	
	var url = "activitiController.do?startMutilProcess&id="+id
			   +"&flowCode="+flowCode
			   +"&formUrl="+formUrl
			   +"&formUrlMobile="+formUrlMobile;
	 confirm(url,'确定提交出差流程吗？','tBMutilFlowDemoList');
	 
 }

//借款流程提交
function startProcessBorrow(id){
	//多流程表单编码
	var flowCode = "MFF_02_JIEKUAN"; 
	var formUrl = "tBMutilFlowDemoController.do?goUpdate&load=detail";
	var formUrlMobile = "tBMutilFlowDemoController.do?goUpdate&load=detail";
	
	var url = "activitiController.do?startMutilProcess&id="+id
			   +"&flowCode="+flowCode
			   +"&formUrl="+formUrl
			   +"&formUrlMobile="+formUrlMobile;
	 confirm(url,'确定提交借款流程吗？','tBMutilFlowDemoList');
	 
}
 </script>