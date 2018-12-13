<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<t:datagrid name="userList" title="员工列表" actionUrl="userController.do?datagrid" fit="true" fitColumns="true" idField="id" queryMode="group" sortName="createDate,userName" sortOrder="asc,desc">
	<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="员工账号" sortable="false" field="userName" query="true" width="100"></t:dgCol>
	<t:dgCol title="员工名称" field="realName" query="true" width="100"></t:dgCol>
	<%--<t:dgCol title="common.user.type" field="userType" dictionary="user_type" width="80"></t:dgCol>
	<t:dgCol title="common.department" field="TSDepart_id" query="true" replace="${departsReplace}"></t:dgCol>--%>
	<t:dgCol title="所属部门" sortable="false" field="userOrgList.tsDepart.departname" query="false" width="100"></t:dgCol>
	<t:dgCol title="common.role" field="userKey" width="100"></t:dgCol>
	<t:dgCol title="common.createby" field="createBy" hidden="true" width="100"></t:dgCol>
	<t:dgCol title="common.createtime" field="createDate" formatter="yyyy-MM-dd"  width="50" hidden="false"></t:dgCol>
	<t:dgCol title="common.updateby" field="updateBy" hidden="true"></t:dgCol>
	<t:dgCol title="common.updatetime" field="updateDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
	<t:dgCol title="common.status" sortable="true" field="status" width="50" replace="common.active_1,common.inactive_0,super.admin_-1" ></t:dgCol>
	
	<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
	<t:dgFunOpt funname="deleteDialog(id)" title="common.delete" urlclass="ace_button"  urlfont="fa-trash-o"></t:dgFunOpt>
	<%-- update-begin--Author:zhoujf  Date:20170306 for：TASK #1099 【工作流完善】代理功能(代理的有效时间)必须是同步到工作流的人
	<t:dgFunOpt funname="setAgentUser(userName)" title="代理人配置" urlclass="ace_button"  urlfont="fa-cog"></t:dgFunOpt>--%>
	<!-- update-end--Author:zhoujf  Date:20170306 for：TASK #1099 【工作流完善】代理功能(代理的有效时间)必须是同步到工作流的人-->
	<t:dgToolBar title="员工录入" langArg="common.user" icon="icon-add" url="userController.do?addorupdate" funname="add" height="420"></t:dgToolBar>
	<t:dgToolBar title="员工编辑" langArg="common.user" icon="icon-edit" url="userController.do?addorupdate" funname="update"></t:dgToolBar>
	<t:dgToolBar title="common.password.reset" icon="icon-edit" url="userController.do?changepasswordforuser" funname="update"></t:dgToolBar>
	<t:dgToolBar title="员工锁定" icon="icon-edit" url="userController.do?lock&lockvalue=0" funname="lockObj"></t:dgToolBar>
	<t:dgToolBar title="员工激活" icon="icon-edit" url="userController.do?lock&lockvalue=1" funname="unlockObj"></t:dgToolBar>
	<%--update-begin--Author:zhoujf  Date:20180111 for：TASK #2483 【新功能】批量流程同步用户和角色功能 
	<t:dgToolBar title="common.sync.user" icon="icon-reload" url="userController.do?doSyncUser" funname="syncUser"></t:dgToolBar>--%>
	<%--update-end--Author:zhoujf  Date:20180111 for：TASK #2483 【新功能】批量流程同步用户和角色功能 --%>
	<t:dgToolBar title="excelImport" icon="icon-put" funname="ImportXls"></t:dgToolBar>
	<t:dgToolBar title="excelOutput" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
	<%--<t:dgToolBar title="templateDownload" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
</t:datagrid>
<script>
    <%--$(function() {
        var datagrid = $("#userListtb");
		datagrid.find("div[name='searchColums']").find("form#userListForm").append($("#realNameSearchColums div[name='searchColumsRealName']").html());
		$("#realNameSearchColums").html('');
        datagrid.find("div[name='searchColums']").find("form#userListForm").append($("#tempSearchColums div[name='searchColums']").html());
        $("#tempSearchColums").html('');
	});--%>
</script>
<%--<div id="realNameSearchColums" style="display: none;">
	<div name="searchColumsRealName">
		<t:userSelect hasLabel="true" selectedNamesInputId="realName" windowWidth="1000px" windowHeight="600px" title="用户名称"></t:userSelect>
	</div>
</div>
<div id="tempSearchColums" style="display: none;">
    <div name="searchColums">
       <t:departSelect hasLabel="true" selectedNamesInputId="orgNames"></t:departSelect>
    </div>
</div>--%>
<script type="text/javascript">
<%--update-begin--Author:zhoujf  Date:20180111 for：TASK #2483 【新功能】批量流程同步用户和角色功能 --%>
function syncUser(title,url, id) {
	$.dialog.confirm('<t:mutiLang langKey="common.sync.user.tips"/>', function(){
		doSyncUser(url);
	}, function(){
	});
}
function doSyncUser(url) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
			var msg = d.msg;
				tip(msg);
			}
		}
	});
}
<%--update-end--Author:zhoujf  Date:20180111 for：TASK #2483 【新功能】批量流程同步用户和角色功能 --%>

<%--update-begin--Author:zhoujf  Date:20170306 for：TASK #1099 【工作流完善】代理功能(代理的有效时间)必须是同步到工作流的人 --%>
function setAgentUser(userName){
	var url = "tSUserAgentController.do?goAgentUser&userName=" + userName
	createwindow("用户代理人设置", url, 600, 300);
}
<%--update-begin--Author:zhoujf  Date:20170306 for：TASK #1099 【工作流完善】代理功能(代理的有效时间)必须是同步到工作流的人 --%>

function deleteDialog(id){
	var url = "userController.do?deleteDialog&id=" + id
	createwindow("删除模式", url, 200, 100);
}
function lockObj(title,url, id) {

	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('<t:mutiLang langKey="common.please.select.edit.item"/>');
		return;
	}
		url += '&id='+rowsData[0].id;

	$.dialog.confirm('<t:mutiLang langKey="common.lock.user.tips"/>', function(){
		lockuploadify(url, '&id');
	}, function(){
	});
}
function unlockObj(title,url, id) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('<t:mutiLang langKey="common.please.select.edit.item"/>');
		return;
	}
    //update-begin--Author:caoez  Date:20180202 for：TASK #2514 已经激活的用户不让激活
    if(rowsData[0].status == 1){
        tip('<t:mutiLang langKey="common.please.select.user.status.inactive"/>');
        return;
    }
    //update-end--Author:caoez  Date:20180202 for：TASK #2514 已经激活的用户不让激活
		url += '&id='+rowsData[0].id;

	$.dialog.confirm('<t:mutiLang langKey="common.unlock.user.tips"/>', function(){
		lockuploadify(url, '&id');
	}, function(){
	});
}


function lockuploadify(url, id) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
			var msg = d.msg;
				tip(msg);
				reloadTable();
			}
		}
	});
}
</script>

<script type="text/javascript">
	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'userController.do?upload', "userList");
	}

	//导出
	function ExportXls() {
		JeecgExcelExport("userController.do?exportXls", "userList");
	}

	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("userController.do?exportXlsByT", "userList");
	}
</script>