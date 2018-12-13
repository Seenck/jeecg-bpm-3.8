<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hrQianbaoList" checkbox="true" fitColumns="false" title="华融公文签报" actionUrl="hrQianbaoController.do?datagrid" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
   <t:dgCol title="ID"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <%-- <t:dgCol title="公文类别"  field="documentCategory"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="机要年份"  field="confidentialYear"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="机要号"  field="confidentialNum"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="编码"  field="qianbaoCodeYear"   formatterjs="getCode"  width="120"></t:dgCol>
   <t:dgCol title="号"  field="qianbaoCodeNum"  hidden="true"   width="120"></t:dgCol>
   <t:dgCol title="签报单位编码"  field="qianbaoDepartCode"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="签报单位名称"  field="qianbaoDepartName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="签报日期"  field="qianbaoDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="是否需要会签（0不需要会签1需要会签）"  field="qianbaoFlag"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="是否为制度文件（0否1是）"  field="policyFlag"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="流程状态"  field="bpmStatus"    queryMode="group"  width="120"></t:dgCol>
   <%-- <t:dgCol title="创建人名称"  field="createName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="所属公司"  field="sysCompanyCode"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
   <t:dgDelOpt title="删除" url="hrQianbaoController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o" />
   <t:dgFunOpt exp="bpmStatus#eq#1" title="提交申请" funname="startUserDefinedProcess(id)" urlclass="ace_button"  urlfont="fa fa-download"/>
    <t:dgFunOpt exp="bpmStatus#eq#2" funname="progress(id)" title="项目进度"  urlclass="ace_button"  urlStyle="background-color:	#FF6347" urlfont="fa fa-history"></t:dgFunOpt>
   <t:dgToolBar title="录入" icon="icon-add" url="hrQianbaoController.do?goAdd" funname="add" height="100%" width="100%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="hrQianbaoController.do?goUpdate" funname="update" height="100%" width="100%"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="hrQianbaoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="hrQianbaoController.do?goUpdate" funname="detail" height="100%" width="100%"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/jeecg/qianbao/hrQianbaoList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#hrQianbaoListtb").find("input[name='qianbaoDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#hrQianbaoListtb").find("input[name='qianbaoDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#hrQianbaoListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#hrQianbaoListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#hrQianbaoListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#hrQianbaoListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'hrQianbaoController.do?upload', "hrQianbaoList");
}

//导出
function ExportXls() {
	JeecgExcelExport("hrQianbaoController.do?exportXls","hrQianbaoList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("hrQianbaoController.do?exportXlsByT","hrQianbaoList");
}

function startUserDefinedProcess(id){
		//业务表名
		var tableName = "hr_qianbao";
		//pc端默认表单URL
		var formUrl = "hrQianbaoController.do?goUpdate";
		//移动端默认表单URL
		var formUrlMobile = "hrQianbaoController.do?goUpdate&load=detail";
		var url = "activitiController.do?startUserDefinedProcess&id="+id
				  +"&tableName="+tableName
				  +"&formUrl="+formUrl
				  +"&formUrlMobile="+formUrlMobile;
		confirm(url,"确定提交审请吗？","hrQianbaoList");
	
}

function getCode(value,row,index){
	var rtn = "";
	var year = row.qianbaoCodeYear;
	var num = row.qianbaoCodeNum;
	rtn = "办公室["+year+"]"+num+"号"
	return rtn;
}
 </script>