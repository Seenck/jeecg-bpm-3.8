<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:datagrid name="bormoney" title="借款列表" actionUrl="busController.do?bormoneyGrid" idField="id">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="借款人" field="TSUser_userName"></t:dgCol>
 <t:dgCol title="借款金额" field="bormoney"></t:dgCol>
 <t:dgCol title="申请时间" field="createtime" formatter="yyyy-MM-dd" query="true"></t:dgCol>
 <t:dgCol title="业务配置ID" field="TSBusConfig_id" hidden="true"></t:dgCol>
 <t:dgCol title="状态" field="TSPrjstatus_description"></t:dgCol>
 <t:dgCol hidden="true" title="状态ID(该字段隐藏)" field="TSPrjstatus_code"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
 <t:dgConfOpt exp="TSPrjstatus_code#eq#new" url="activitiController.do?startBusProcess&businessKey={id}&busconfigKey={TSBusConfig_id}&bpm_biz_title={TSUser_userName}" 
 message="确认完毕,提交申请?" title="提交申请" urlclass="ace_button" urlfont="fa fa-download"></t:dgConfOpt>
 <t:dgFunOpt exp="TSPrjstatus_code#eq#doing" funname="progress(id)" title="项目进度" urlclass="ace_button"  urlStyle="background-color:	#FF6347" urlfont="fa fa-history"></t:dgFunOpt>
 <t:dgDelOpt exp="TSPrjstatus_code#eq#new" url="busController.do?delBormoney&id={id}" title="删除" urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-trash-o"></t:dgDelOpt>
 <t:dgToolBar operationCode="add" title="录入" icon="icon-add" url="busController.do?aorubormoney" funname="add"></t:dgToolBar>
 <t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" url="busController.do?aorubormoney" funname="update"></t:dgToolBar>
</t:datagrid>