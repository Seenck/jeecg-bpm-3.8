<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;border:0px">
		<t:datagrid name="categoryList" title="menu.sort.management"
			actionUrl="categoryController.do?datagrid" idField="id"
			treegrid="true" pagination="false">
			<t:dgCol title="common.type.name" field="name" treefield="text" width="300"></t:dgCol>
			<t:dgCol title="common.icon" field="TSIcon_iconPath" treefield="code"
				image="true" hidden="true"></t:dgCol>
			<t:dgCol title="common.type.code" field="code" treefield="id"></t:dgCol>
			<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
			<!-- 	//update-begin--Author:zhangjq  Date:20160904 for：1332 【系统图标统一调整】讲{系统管理模块}{在线开发}的链接按钮，改成ace风格 -->
			<t:dgDelOpt title="common.delete"
				url="categoryController.do?del&id={src}" urlclass="ace_button"  urlfont="fa-trash-o" />
			<!-- 	//update-end--Author:zhangjq  Date:20160904 for：1332 【系统图标统一调整】讲{系统管理模块}{在线开发}的链接按钮，改成ace风格 -->
			<t:dgToolBar icon="icon-add" title="common.add"
				url="categoryController.do?addorupdate" funname="addCategory"></t:dgToolBar>
			<t:dgToolBar icon="icon-edit" title="common.edit"
				url="categoryController.do?addorupdate" funname="updateCategory"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	function addCategory(title, url, id) {
		var rowData = $('#' + id).datagrid('getSelected');
		if (rowData) {
			url += '&parent.code=' + rowData.id;
		}
		//update-begin--Author:LiShaoQing  Date:20170816 for：[TASK #2284]界面大小调整
		add(title, url, 'categoryList', 500, 340);
		//update-end--Author:LiShaoQing  Date:20170816 for：[TASK #2284]界面大小调整
	}
	
	function updateCategory(title, url, id) {
		var rowData = $('#' + id).datagrid('getSelected');
		if (rowData) {
			url += '&code=' + rowData.id;
		}
		//update-begin--Author:LiShaoQing  Date:20170816 for：[TASK #2284]界面大小调整
		update(title, url, 'categoryList', 500, 340);
		//update-end--Author:LiShaoQing  Date:20170816 for：[TASK #2284]界面大小调整
	}
</script>