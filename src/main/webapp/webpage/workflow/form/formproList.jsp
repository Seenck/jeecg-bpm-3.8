<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="formproList" title="form.manage" actionUrl="processController.do?datagridFPro&formid=${form.id }" idField="id">
			<t:dgCol title="property.id" field="id" width="50" hidden="true"></t:dgCol>
			<t:dgCol title="property.name" field="formproname" width="70"></t:dgCol>
			<t:dgCol title="property.value" field="formproval" width="70"></t:dgCol>
			<t:dgCol title="property.code" field="formprocode" width="70"></t:dgCol>
			<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt url="processController.do?delFPro&id={id}" title="common.delete"></t:dgDelOpt>
			<t:dgToolBar title="add.form.control" icon="icon-add" url="processController.do?addorupdateFPro&formid=${form.id }" funname="add"></t:dgToolBar>
			<t:dgToolBar title="edit.form.control" icon="icon-edit" url="processController.do?addorupdateFPro&formid=${form.id }" funname="update"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
