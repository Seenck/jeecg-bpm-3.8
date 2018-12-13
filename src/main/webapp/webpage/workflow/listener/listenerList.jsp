<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;border:0px">
		<t:datagrid name="listenerList" actionUrl="processController.do?listenerGrid" idField="id">
			<t:dgCol title="id" hidden="true" field="id"></t:dgCol>
			<t:dgCol title="common.name" field="listenername" width="20"></t:dgCol>
			<t:dgCol title="common.listen.typeid" field="typeid" width="20" dictionary="listen_typeid"></t:dgCol>
			<t:dgCol title="common.event" field="listenereven" width="20"></t:dgCol>
			<t:dgCol title="execution.type" field="listenertype" width="20" dictionary="listenertype"></t:dgCol>
			<t:dgCol title="execution.content" field="listenervalue" width="40"></t:dgCol>
			<t:dgCol title="common.status" field="listenerstate" replace="已禁用_0,已启用_1"></t:dgCol>
			<t:dgCol title="common.operation" field="opt" width="20"></t:dgCol>
			<t:dgConfOpt exp="listenerstate#eq#0" url="processController.do?setListeren&id={id}&status=1" message="is.enable" title="common.enable" urlclass="ace_button" urlfont="fa-toggle-on"/>
			<t:dgConfOpt exp="listenerstate#eq#1" url="processController.do?setListeren&id={id}&status=0" message="is.disable" title="operationType.disabled" urlclass="ace_button" urlfont="fa-trash-o"/>
			<t:dgDelOpt exp="listenerstate#eq#0" url="processController.do?delListeren&id={id}" title="common.delete" urlclass="ace_button" urlStyle="background-color:#ec4758;" urlfont="fa-trash-o"></t:dgDelOpt>
			<t:dgToolBar title="add.listener" icon="icon-add" url="processController.do?aouListener" funname="add"></t:dgToolBar>
			<t:dgToolBar title="edit.listener" icon="icon-edit" url="processController.do?aouListener" funname="update"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
