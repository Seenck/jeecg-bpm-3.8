<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="departUserList" title="common.operation"
            actionUrl="organzationController.do?userDatagrid&departid=${departid}" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.username" sortable="false" width="300" field="userName" query="true"></t:dgCol>
	<t:dgCol title="common.real.name" field="realName" width="300" query="true"></t:dgCol>
	<t:dgCol title="common.status" sortable="true" width="300" field="status" replace="common.active_1,common.inactive_0,super.admin_-1"></t:dgCol>
	<t:dgCol title="common.operation" field="opt"></t:dgCol>
	<!-- 	//update-begin--Author:zhangjq  Date:20160904 for：1332 【系统图标统一调整】讲{系统管理模块}{在线开发}的链接按钮，改成ace风格 -->
	<t:dgDelOpt title="common.delete" url="organzationController.do?delUserOrg&userid={id}&departid=${departid }" urlclass="ace_button"  urlfont="fa-trash-o"/>
	<!-- 	//update-end--Author:zhangjq  Date:20160904 for：1332 【系统图标统一调整】讲{系统管理模块}{在线开发}的链接按钮，改成ace风格 -->
	<t:dgToolBar title="common.add.param" langArg="common.user" icon="icon-add" url="userController.do?addorupdate&departid=${departid}" funname="add"></t:dgToolBar>
	<t:dgToolBar title="common.edit.param" langArg="common.user" icon="icon-edit" url="userController.do?addorupdate&departid=${departid}" funname="update"></t:dgToolBar>
    <%--update-start--Author:zhangguoming  Date:20140826 for：添加有客户--%>
	<t:dgToolBar title="common.add.exist.user" icon="icon-add" url="organzationController.do?goAddUserToOrg&orgId=${departid}" funname="add" width="650"></t:dgToolBar>
    <%--update-end--Author:zhangguoming  Date:20140826 for：添加有客户--%>
</t:datagrid>
