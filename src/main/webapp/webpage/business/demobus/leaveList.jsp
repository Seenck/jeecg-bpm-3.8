<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
 <!-- update-begin-author:taoYan date:20170803 for:时间格式化 -->
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="leave" title="请假列表" actionUrl="busController.do?leaveGrid" idField="id" queryMode="group" sortOrder="desc" sortName="id">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="请假原因" field="reason"></t:dgCol>
 <t:dgCol title="申请人" field="TSUser_userName"></t:dgCol>
 <t:dgCol title="开始时间" field="begintime" formatter="yyyy-MM-dd" query="true"></t:dgCol>
 <t:dgCol title="结束时间" field="endtime" formatter="yyyy-MM-dd" query="true"></t:dgCol>
  <!-- update-end-author:taoYan date:20170803 for:时间格式化 -->
 <t:dgCol title="业务配置ID" field="TSBusConfig_id" hidden="true"></t:dgCol>
 <t:dgCol title="状态" field="TSPrjstatus_description"></t:dgCol>
 <t:dgCol hidden="true" title="状态ID(该字段隐藏)" field="TSPrjstatus_code"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
 <t:dgConfOpt exp="TSPrjstatus_code#eq#new" url="activitiController.do?startBusProcess&businessKey={id}&busconfigKey={TSBusConfig_id}&bpm_biz_title={TSUser_userName}" message="确认提交申请?" title="提交流程"
  urlclass="ace_button"  urlfont="fa fa-download"></t:dgConfOpt>
 <t:dgFunOpt exp="TSPrjstatus_code#eq#doing" funname="progress(id,TSUser_userName)" title="项目进度"  urlclass="ace_button"  urlStyle="background-color:	#FF6347" urlfont="fa fa-history"></t:dgFunOpt>
 <t:dgDelOpt exp="TSPrjstatus_code#eq#new" url="busController.do?delLeave&id={id}" title="删除"  urlclass="ace_button"   urlStyle="background-color:#ec4758;" urlfont="fa-trash-o"></t:dgDelOpt>
 <t:dgToolBar operationCode="add" title="请假申请" icon="icon-add" url="busController.do?aoruleave" funname="add"></t:dgToolBar>
 <t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" url="busController.do?aoruleave" funname="beforeUpdate"></t:dgToolBar>
 <t:dgToolBar operationCode="detail" title="查看" icon="icon-search" url="busController.do?aoruleave" funname="detail"></t:dgToolBar>
</t:datagrid>
<script type="text/javascript">
$(document).ready(function(){
		$("input[name='begintime']").focus(function(){
		  WdatePicker({isShowClear:false,readOnly:true});
		 });
		$("input[name='endtime']").focus(function(){
			  WdatePicker({isShowClear:false,readOnly:true});
			 });
	});


function beforeUpdate(title,url, id,width,height){
		gridname=id;
		var rowsData = $('#'+id).datagrid('getSelections');
		var rowStatus = rowsData[0].TSPrjstatus_description;
		if(rowStatus == '结束'){
			tip('当前记录已结束不可编辑');
			return;
		}
		if (!rowsData || rowsData.length==0) {
			tip('请选择编辑项目');
			return;
		}
		if (rowsData.length>1) {
			tip('请选择一条记录再编辑');
			return;
		}
		
		url += '&id='+rowsData[0].id;
		createwindow(title,url,width,height);
}
</script>