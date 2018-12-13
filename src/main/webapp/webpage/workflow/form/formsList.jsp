<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="formList" title="form.manage" actionUrl="processController.do?datagridForm" idField="id">
			<t:dgCol title="form.id" hidden="true" field="id" width="30"></t:dgCol>
			<t:dgCol title="form.name" field="formname" width="70"></t:dgCol>
			<t:dgCol title="submit.url" field="formaction" width="70"></t:dgCol>
			<t:dgCol title="form.code" field="formcode" width="70"></t:dgCol>
			<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
			<t:dgFunOpt funname="teplateparam(id)" title="form.design"></t:dgFunOpt>
			<t:dgDelOpt url="processController.do?delForm&id={id}" title="common.delete"></t:dgDelOpt>
			<t:dgToolBar title="add.form" icon="icon-add" url="processController.do?addorupdateForm" funname="add"></t:dgToolBar>
			<t:dgToolBar title="edit.form" icon="icon-edit" url="processController.do?addorupdateForm" funname="update"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	function teplateparam(id) {
		addOneTab('<t:mutiLang langKey='form.design'></t:mutiLang>', 'processController.do?formpro&formid=' + id);
	}
</script>
