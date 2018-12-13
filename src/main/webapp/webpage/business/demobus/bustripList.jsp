<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
 <!-- update-begin-author:taoYan date:20170803 for:时间格式化 -->
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="bustrip" title="出差列表" actionUrl="busController.do?bustripGrid" idField="id" queryMode="group">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="申请人" field="TSUser_userName"></t:dgCol>
 <t:dgCol title="出差地点" field="bustriplocale"></t:dgCol>
 <t:dgCol title="开始时间" field="begintime" formatter="yyyy-MM-dd" query="true"></t:dgCol>
 <t:dgCol title="结束时间" field="endtime" formatter="yyyy-MM-dd" query="true"></t:dgCol>
 <!-- update-end-author:taoYan date:20170803 for:时间格式化 -->
 <t:dgCol title="出差事由" field="bustripreson" query="true"></t:dgCol>
 <t:dgCol title="状态" field="TSPrjstatus_description"></t:dgCol>
 <t:dgCol hidden="true" title="状态ID(该字段隐藏)" field="TSPrjstatus_code"></t:dgCol>
 <t:dgCol title="业务配置ID" field="TSBusConfig_id" hidden="true"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
 <t:dgConfOpt exp="TSPrjstatus_code#eq#new" url="activitiController.do?startBusProcess&businessKey={id}&busconfigKey={TSBusConfig_id}&bpm_biz_title={bustripreson}" 
 message="确认完毕,提交申请?" title="提交申请" urlclass="ace_button" urlfont="fa fa-download"></t:dgConfOpt>
 <t:dgFunOpt exp="TSPrjstatus_code#eq#doing" funname="progress(id,TSUser_userName)" title="项目进度" urlclass="ace_button"  urlStyle="background-color:	#FF6347" urlfont="fa fa-history"></t:dgFunOpt>
 <t:dgDelOpt exp="TSPrjstatus_code#eq#new" url="busController.do?delBustrip&id={id}" title="删除" urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-trash-o"></t:dgDelOpt>
 <t:dgToolBar operationCode="add" title="出差申请" icon="icon-add" url="busController.do?aorubustrip" funname="add"></t:dgToolBar>
 <t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" url="busController.do?aorubustrip" funname="update"></t:dgToolBar>
 <t:dgToolBar operationCode="detail" title="查看" icon="icon-search" url="busController.do?aorubustrip" funname="detail"></t:dgToolBar>
</t:datagrid>
<script type="text/javascript">
$(document).ready(function(){
		$("input[name='begintime']").focus(function(){
		  WdatePicker({isShowClear:false,readOnly:true});
		 });
		//update-begin-author:taoYan date:20170803 for:时间查询条件格式化----
		$("input[name='endtime']").focus(function(){
			  WdatePicker({isShowClear:false,readOnly:false});
		 });
		//update-end-author:taoYan date:20170803 for:时间查询条件格式化----
	});
</script>