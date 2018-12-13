<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%--<t:datagrid name="userList" title="user.manage" actionUrl="roleController.do?roleUserDatagrid&roleId=${roleId}" fit="true" fitColumns="true" idField="id">--%>
<%--	<t:dgCol title="common.id" field="id" hidden="true" ></t:dgCol>--%>
<%--	<t:dgCol title="common.username" sortable="false" field="userName" width="5"></t:dgCol>--%>
<%--	<t:dgCol title="common.real.name" field="realName" width="5"></t:dgCol>--%>
<%--</t:datagrid>--%>

<t:datagrid name="roleUserList" title="common.operation"
            actionUrl="roleController.do?roleUserDatagrid&roleId=${roleId}" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.username" sortable="false" field="userName" width="200" query="true"></t:dgCol>
	<t:dgCol title="common.real.name" field="realName" width="200" query="true"></t:dgCol>
	<t:dgCol title="common.status" sortable="true" width="100" field="status" replace="common.active_1,common.inactive_0,super.admin_-1"></t:dgCol>
	<t:dgCol title="common.operation" field="opt" width="200" ></t:dgCol>
<!-- 	//update-begin--Author:zhangjq  Date:20160904 for：1332 【系统图标统一调整】讲{系统管理模块}{在线开发}的链接按钮，改成ace风格 -->
	<t:dgDelOpt title="common.delete" url="roleController.do?delUserRole&userid={id}&roleid=${roleId }" urlclass="ace_button"  urlfont="fa-trash-o"/>
<!-- 	//update-end--Author:zhangjq  Date:20160904 for：1332 【系统图标统一调整】讲{系统管理模块}{在线开发}的链接按钮，改成ace风格 -->
	<t:dgToolBar title="common.add.param" langArg="common.user" icon="icon-add" url="userController.do?addorupdate&roleId=${roleId}" funname="add"></t:dgToolBar>
	<t:dgToolBar title="common.edit.param" langArg="common.user" icon="icon-edit" url="userController.do?addorupdate&roleId=${roleId}" funname="update"></t:dgToolBar>
	<t:dgToolBar title="common.add.exist.user" icon="icon-add" url="roleController.do?goAddUserToRole&roleId=${roleId}" funname="add" width="600"></t:dgToolBar>
</t:datagrid>

