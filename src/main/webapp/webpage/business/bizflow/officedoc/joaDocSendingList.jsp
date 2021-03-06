<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="joaDocSendingList" checkbox="true" pagination="true" fitColumns="false" title="公文发文" actionUrl="joaDocSendingController.do?datagrid" idField="id" sortName="createDate" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" ></t:dgCol>
   <t:dgCol title="流程状态"  field="bpmStatus" dictionary="bpm_status" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="发文字号"  field="docCode"  queryMode="group"  width="150"></t:dgCol>
   <t:dgCol title="登记人"  field="booker"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="登记时间"  field="bookDate"  formatter="yyyy-MM-dd"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="公文标题"  field="title"  queryMode="group"  width="150"></t:dgCol>
   <t:dgCol title="发文目标"  field="sendTarget"  queryMode="group"  dictionary="of_fwmb"  width="120"></t:dgCol>
   <t:dgCol title="主送机关"  field="sendDepart"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="公文分类"  field="docType"  queryMode="group"  dictionary="of_gwfl"  width="120"></t:dgCol>
   <t:dgCol title="文种"  field="classification"  queryMode="group"  dictionary="of_wenz"  width="120"></t:dgCol>
   <t:dgCol title="机密度"  field="confidentiality"  queryMode="group"  dictionary="of_jimi"  width="120"></t:dgCol>
   <t:dgCol title="缓急程度"  field="urgency"  queryMode="group"  dictionary="of_hjcd"  width="120"></t:dgCol>
   <t:dgCol title="成文日期"  field="writtenDate"  formatter="yyyy-MM-dd"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审阅时间"  field="reviewDate"  formatter="yyyy-MM-dd"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="主题词"  field="theme" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="相关文件"  field="extFile" hidden="true"  width="120"></t:dgCol>
   <t:dgDelOpt exp="bpmStatus#eq#1" title="删除" url="joaDocSendingController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgFunOpt exp="bpmStatus#eq#1" title="提交流程" funname="startProcess(id)" urlclass="ace_button"  urlfont="fa fa-download"></t:dgFunOpt>
   <t:dgFunOpt exp="bpmStatus#ne#1" title="流程进度" funname="openProcess(id)" urlclass="ace_button" urlStyle="background-color: red"  urlfont="fa fa-history"></t:dgFunOpt>
    <%--   <t:dgConfOpt exp="bpmStatus#eq#1" url="activitiController.do?startBusProcess&businessKey={id}&busconfigKey={TSBusConfig_id}&bpm_biz_title={TSUser_userName}" message="确认提交申请?" title="提交流程"
  urlclass="ace_button"  urlfont="fa fa-download"></t:dgConfOpt> --%>
  
   <t:dgToolBar title="录入" icon="icon-add" url="joaDocSendingController.do?goAdd" funname="add" width="100%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="joaDocSendingController.do?goUpdate" funname="update" width="100%"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="joaDocSendingController.do?goUpdate" funname="detail" width="100%"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'joaDocSendingController.do?upload', "joaDocSendingList");
}

//导出
function ExportXls() {
	JeecgExcelExport("joaDocSendingController.do?exportXls","joaDocSendingList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("joaDocSendingController.do?exportXlsByT","joaDocSendingList");
}

function startProcess(id){
	 //业务表名
	var tableName = "joa_doc_sending"; 
	var formUrl = "joaDocSendingController.do?goUpdate&load=detail";
	var formUrlMobile = "joaDocSendingController.do?goUpdate&load=detail";
	
	var url = "activitiController.do?startUserDefinedProcess&id="+id
			   +"&tableName="+tableName
			   +"&formUrl="+formUrl
			   +"&formUrlMobile="+formUrlMobile;
	 confirm(url,'确定提交流程吗？','joaDocSendingList',1);
}
//弹出提交流程框
function openProcess(id) {
	var url = "activitiController.do?openProcessPic&tag=project&businessKey="+id;
	$.dialog({
		title : '流程进度查看',
	    content: 'url:'+url,
		zIndex: getzIndex(),
		height : '600px',
		width : '900px',
	    cache:false,
	    button: [
	        {
	            name: "关闭",
	            callback: function(){
	            	window.opener=null;
	            	window.open('','_self');
	            	window.close();
	            }
	        }
	    ]
	});
}
 </script>