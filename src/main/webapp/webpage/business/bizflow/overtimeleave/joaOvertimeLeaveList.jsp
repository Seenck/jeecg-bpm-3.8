<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="joaOvertimeLeaveList" checkbox="true" pagination="true" fitColumns="true" title="调休申请单" actionUrl="joaOvertimeLeaveController.do?datagrid" idField="id" sortName="createDate" fit="true" queryMode="group">
   <t:dgCol title="ID"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="申请人"  field="applyUser"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="部门"  field="department"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="申请时间"  field="applyTime"  formatter="yyyy-MM-dd"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="调休时长"  field="leaveTime"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="调休开始时间"  field="beginTime"  formatter="yyyy-MM-dd"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="调休结束时间"  field="endTime"  formatter="yyyy-MM-dd"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="调休期间联系方式"  field="contactWay"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="工作代理人"  field="workAgent" hidden="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="工作安排"  field="workPlan" hidden="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="直接领导审批"  field="leaderRemark" hidden="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="部门领导审批"  field="deptLeaderRemark" hidden="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人id"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人id"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="流程状态"  field="bpmStatus" queryMode="group"  dictionary="bpm_status"  width="120"></t:dgCol>
   <t:dgCol title="逻辑删除标识0未删除1删除"  field="delFlag"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgDelOpt exp="bpmStatus#eq#1" title="删除" url="joaOvertimeLeaveController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgFunOpt exp="bpmStatus#eq#1" funname="startProcess(id)"  title="提交流程" urlclass="ace_button"  urlfont="fa fa-download"></t:dgFunOpt>
   <t:dgFunOpt title="流程进度" exp="bpmStatus#ne#1" funname="openProcess(id)" urlclass="ace_button" urlStyle="background-color: red"  urlfont="fa fa-history" />
   <t:dgToolBar title="录入" icon="icon-add" url="joaOvertimeLeaveController.do?goAdd" funname="add"  width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="joaOvertimeLeaveController.do?goUpdate" funname="update"  width="100%" height="100%"></t:dgToolBar>
   <%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="joaOvertimeLeaveController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
    --%>
    <t:dgToolBar title="查看" icon="icon-search" url="joaOvertimeLeaveController.do?goDetail" funname="detail"  width="100%" height="100%"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
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
	openuploadwin('Excel导入', 'joaOvertimeLeaveController.do?upload', "joaOvertimeLeaveList");
}

//导出
function ExportXls() {
	JeecgExcelExport("joaOvertimeLeaveController.do?exportXls","joaOvertimeLeaveList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("joaOvertimeLeaveController.do?exportXlsByT","joaOvertimeLeaveList");
}

//bootstrap列表图片格式化
function btListImgFormatter(value,row,index){
	return listFileImgFormat(value,"image");
}
//bootstrap列表文件格式化
function btListFileFormatter(value,row,index){
	return listFileImgFormat(value);
}

//列表文件图片 列格式化方法
function listFileImgFormat(value,type){
	var href='';
	if(value==null || value.length==0){
		return href;
	}
	var value1 = "img/server/"+value;
	if("image"==type){
 		href+="<img src='"+value1+"' width=30 height=30  onmouseover='tipImg(this)' onmouseout='moveTipImg()' style='vertical-align:middle'/>";
	}else{
 		if(value.indexOf(".jpg")>-1 || value.indexOf(".gif")>-1 || value.indexOf(".png")>-1){
 			href+="<img src='"+value1+"' onmouseover='tipImg(this)' onmouseout='moveTipImg()' width=30 height=30 style='vertical-align:middle'/>";
 		}else{
 			var value2 = "img/server/"+value+"?down=true";
 			href+="<a href='"+value2+"' class='ace_button' style='text-decoration:none;' target=_blank><u><i class='fa fa-download'></i>点击下载</u></a>";
 		}
	}
	return href;
}

function startProcess(id){
	 //业务表名
	var tableName = "joa_overtime_leave"; 
	var formUrl = "joaOvertimeLeaveController.do?goDetail&load=detail";
	var formUrlMobile = "joaOvertimeLeaveController.do?goDetail&load=detail";
	
	var url = "activitiController.do?startUserDefinedProcess&id="+id
			   +"&tableName="+tableName
			   +"&formUrl="+formUrl
			   +"&formUrlMobile="+formUrlMobile;
	 confirm(url,'确定提交流程吗？','joaOvertimeLeaveList',1);
}

//弹出提交流程框
function openProcess(id) {
	var url = "activitiController.do?openProcessPic&tag=project&businessKey="+id;
	$.dialog({
		title : '流程管理',
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
