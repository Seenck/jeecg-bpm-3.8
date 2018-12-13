<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="departList" title="common.department.list" fitColumns="true" actionUrl="organzationController.do?departgrid" treegrid="true" idField="departid" pagination="false">
            <t:dgCol title="common.id" field="id" treefield="id" hidden="true"></t:dgCol>
            <t:dgCol title="common.department.name" field="departname" treefield="text" width="120"></t:dgCol>
            <t:dgCol title="position.desc" field="description" treefield="src" width="70"></t:dgCol>
            <t:dgCol title="common.org.code" field="orgCode" treefield="fieldMap.orgCode" width="50"></t:dgCol>
            <t:dgCol title="common.org.type" field="orgType" dictionary="orgtype" treefield="fieldMap.orgType" width="60"></t:dgCol>
            <t:dgCol title="common.mobile" field="mobile" treefield="fieldMap.mobile" width="60"></t:dgCol>
            <t:dgCol title="common.fax" field="fax" treefield="fieldMap.fax" width="60"></t:dgCol>
            <t:dgCol title="common.address" field="address" treefield="fieldMap.address" width="100"></t:dgCol>
            <t:dgCol title="common.operation" field="opt" width="200"></t:dgCol>
           <!-- 	//update-begin--Author:zhangjq  Date:20160904 for：1332 【系统图标统一调整】讲{系统管理模块}{在线开发}的链接按钮，改成ace风格 -->
            <t:dgDelOpt url="organzationController.do?del&id={id}" title="common.delete" urlclass="ace_button"  urlfont="fa-trash-o" urlStyle="background-color:#ec4758;"></t:dgDelOpt>
            <t:dgFunOpt funname="queryUsersByDepart(id)" title="view.member" urlclass="ace_button"  urlfont="fa-user"></t:dgFunOpt>
            <t:dgFunOpt funname="setRoleByDepart(id,text)" title="role.set" urlclass="ace_button"  urlfont="fa-cog" urlStyle="background-color:#1a7bb9;"></t:dgFunOpt>
       	<!-- 	//update-end--Author:zhangjq  Date:20160904 for：1332 【系统图标统一调整】讲{系统管理模块}{在线开发}的链接按钮，改成ace风格 -->
        </t:datagrid>
        <div id="departListtb" style="padding: 3px; height: 25px">
            <div style="float: left;">
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="addOrg()"><t:mutiLang langKey="common.add.param" langArg="common.department"/></a>
                <!-- //update--begin--author:zhangjiaqiang Date:20170112 for:增加排序功能 -->
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('<t:mutiLang langKey="common.edit.param" langArg="common.department"/>','organzationController.do?update','departList','680px','450px')"><t:mutiLang langKey="common.edit.param" langArg="common.department"/></a>
                <!-- //update--end--author:zhangjiaqiang Date:20170112 for:增加排序功能 -->
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-put" onclick="ImportXls()"><t:mutiLang langKey="excelImport" langArg="common.department"/></a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-putout" onclick="ExportXls()"><t:mutiLang langKey="excelOutput" langArg="common.department"/></a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-putout" onclick="ExportXlsByT()"><t:mutiLang langKey="templateDownload" langArg="common.department"/></a>
            </div>
        </div>
    </div>
</div>
<div data-options="region:'east',
	title:'<t:mutiLang langKey="member.list"/>',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 400px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="userListpanel"></div>
</div>

<script type="text/javascript">
<!--
//update-start--Author:zhangguoming  Date:20140821 for：为组织机构设置角色
    $(function() {
        var li_east = 0;
    });
    function addOrg() {
        var id = "";
        var rowsData = $('#departList').datagrid('getSelections');
        if (rowsData.length == 1) {
            id = rowsData[0].id;
        }
        var url = "organzationController.do?add&id=" + id;
        //update--begin--author:zhangjiaqiang Date:20170112 for:增加排序功能
        //update--begin--author:LiShaoQing  Date:20180802 for:新增页面给定高宽值
        add('<t:mutiLang langKey="common.add.param" langArg="common.department"/>', url, "departList","660px","480px");
        //update--end--author:LiShaoQing Date:20170802 for:新增页面给定高宽值
      //update--end--author:zhangjiaqiang Date:20170112 for:增加排序功能
    }

    function queryUsersByDepart(departid){
        var title = '<t:mutiLang langKey="member.list"/>';
        if(li_east == 0 || $('#main_depart_list').layout('panel','east').panel('options').title != title){
            $('#main_depart_list').layout('expand','east');
        }
        <%--$('#eastPanel').panel('setTitle','<t:mutiLang langKey="member.list"/>');--%>
        $('#main_depart_list').layout('panel','east').panel('setTitle', title);
        $('#main_depart_list').layout('panel','east').panel('resize', {width: 500});
        $('#userListpanel').panel("refresh", "organzationController.do?userList&departid=" + departid);
    }
    /**
     * 为 组织机构 设置 角色
     * @param departid 组织机构主键
     * @param departname 组织机构名称
     */
    function setRoleByDepart(departid, departname){
        var currentTitle = $('#main_depart_list').layout('panel', 'east').panel('options').title;
        if(li_east == 0 || currentTitle.indexOf("<t:mutiLang langKey="current.org"/>") < 0){
            $('#main_depart_list').layout('expand','east');
        }
        var title = departname + ':<t:mutiLang langKey="current.org"/>';
        $('#main_depart_list').layout('panel','east').panel('setTitle', title);
        $('#main_depart_list').layout('panel','east').panel('resize', {width: 200});
        var url = {
            <%--title :"test",--%>
            href:"roleController.do?roleTree&orgId=" + departid
        }
        $('#userListpanel').panel(url);
        $('#userListpanel').panel("refresh");
    }
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'organzationController.do?upload', "departList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("organzationController.do?exportXls","departList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("organzationController.do?exportXlsByT","departList");
    }
//update-end--Author:zhangguoming  Date:20140821 for：为组织机构设置角色
//-->
</script>