<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
  <t:datagrid name="tBServicemanageList" title="服务上报" actionUrl="tBServicemanageController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="上报来源" field="trapSource" ></t:dgCol>
   <t:dgCol title="服务团体" field="serviceTeam" ></t:dgCol>
   <t:dgCol title="服务目录" field="serviceItem" ></t:dgCol>
   <t:dgCol title="主题" field="subject" ></t:dgCol>
   <t:dgCol title="描述" field="content" ></t:dgCol>
   <t:dgCol title="紧急程度" field="urgentLevel" ></t:dgCol>
   <t:dgCol title="影响程度" field="effectLevel" ></t:dgCol>
   <t:dgCol title="客户端ip" field="clientIp" ></t:dgCol>
   <t:dgCol title="业务配置ID" field="TSBusConfig_id" hidden="true"></t:dgCol>
   <t:dgCol title="状态" field="TSPrjstatus_description"></t:dgCol>
   <t:dgCol hidden="true" title="状态ID(该字段隐藏)" field="TSPrjstatus_code"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgConfOpt exp="TSPrjstatus_code#eq#new" url="activitiController.do?startBusProcess&businessKey={id}&busconfigKey={TSBusConfig_id}" message="确认完毕,提交申请?" title="提交"></t:dgConfOpt>
   <t:dgFunOpt exp="TSPrjstatus_code#eq#doing" funname="progress(id,TSUser_userName)" title="项目进度"></t:dgFunOpt>
   <t:dgDelOpt exp="TSPrjstatus_code#eq#new" url="tBServicemanageController.do?del&id={id}" title="删除"></t:dgDelOpt>
  </t:datagrid>
	<div id="servicemanagetb" style="padding:3px; height: 25px">
	 <div style="float: left;">
	  <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('服务上报','tBServicemanageController.do?addorupdate','tBServicemanageList');">服务上报</a>
	  <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('编辑','tBServicemanageController.do?addorupdate','tBServicemanageList')">编辑</a>
	 </div>
	 <div align="right">
	 </div>
	</div>