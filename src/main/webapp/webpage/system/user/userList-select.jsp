<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
	<t:datagrid name="userList" title="common.user.select" actionUrl="userController.do?datagrid" fitColumns="true" idField="id" queryMode="group"  checkbox="true"  sortName="createDate" sortOrder="desc" onLoadSuccess="initCheck">
		<t:dgCol title="common.code" field="id" hidden="true"></t:dgCol>
		<t:dgCol title="common.username" field="userName" query="true" width="100"></t:dgCol>
	</t:datagrid>
	</div>
</div>
<script type="text/javascript">
  	function getUserId(){
  		var rowsData = $("#userList").datagrid("getChecked");
  		var id = "";
  		if(rowsData.length==0){
  			tip('<t:mutiLang langKey="please.select.user"/>');
  			return "";
  		}
  		return rowsData[0].id;
  	}
  	<%-- update-begin--Author:dangzhenghui  --date-- 20170322 for:TASK #1801 【公告改进】公告的创建维护角色，人员，直接在一个页面即可 --%>
    function initCheck(data){
        var ids = "${ids}";
        var idArr = ids.split(",");
        for(var i=0;i<idArr.length;i++){
            if(idArr[i]!=""){
                $("#userList").datagrid("getChecked",idArr[i]);
            }
        }
    }
    <%-- update-end--Author:dangzhenghui  --date-- 20170322 for:TASK #1801 【公告改进】公告的创建维护角色，人员，直接在一个页面即可 --%>

</script>
