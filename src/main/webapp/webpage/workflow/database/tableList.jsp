<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:datagrid name="${type.typecode}List" title="data.table.list" actionUrl="dbController.do?tableGrid&typeid=${type.id }" idField="id">
	<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="table.name" field="tableName" width="15"></t:dgCol>
	<t:dgCol title="entity.key" field="entityKey" width="15"></t:dgCol>
	<t:dgCol title="entity.name" field="entityName" width="50"></t:dgCol>
	<t:dgCol title="common.description" field="tableTitle"></t:dgCol>
	<t:dgToolBar title="common.edit" icon="icon-edit" url="dbController.do?aouTable" funname="update"></t:dgToolBar>
	<t:dgToolBar title="common.load" icon="icon-edit" url="dbController.do?reloadData" funname="doSubmit"></t:dgToolBar>
</t:datagrid>
