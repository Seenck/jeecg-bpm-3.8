<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="testZhangList" title="工作流表单" actionUrl="testZhangController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="名字" field="title" ></t:dgCol>
   <t:dgCol title="年龄" field="age" ></t:dgCol>
   <t:dgCol title="业务配置ID" field="TSBusConfig_id" hidden="true"></t:dgCol>
   <t:dgCol title="状态" field="TSPrjstatus_description"></t:dgCol>
   <t:dgCol hidden="true" title="状态ID(该字段隐藏)" field="TSPrjstatus_code"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt exp="TSPrjstatus_code#eq#new" title="删除" url="testZhangController.do?del&id={id}" />
   <t:dgToolBar title="入职申请" icon="icon-add" url="testZhangController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="testZhangController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgConfOpt exp="TSPrjstatus_code#eq#new" url="activitiController.do?startBusProcess&businessKey={id}&busconfigKey={TSBusConfig_id}" message="确认完毕,提交申请?" title="提交申请"></t:dgConfOpt>
   <t:dgFunOpt exp="TSPrjstatus_code#eq#doing" funname="progress(id)" title="项目进度"></t:dgFunOpt>
  </t:datagrid>