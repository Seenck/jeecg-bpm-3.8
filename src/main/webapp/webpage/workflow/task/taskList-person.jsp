<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script>
    $(function() {
        var datagrid = $("#myTaskListtb");
		datagrid.find("div[name='searchColums']").find("form#myTaskListForm").append($("#realNameSearchColums div[name='searchColumsRealName']").html());
		$("#realNameSearchColums").html('');
	});
</script>
<div id="realNameSearchColums" style="display: none;">
	<div name="searchColumsRealName">
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="任务发起人"/>任务发起人：</span>
		<input  type="hidden" id="userName" name="userName">
		<input readonly="true" type="text" id="realName" name="realName" style="width: 200px" onclick="openUserSelect()"> </span>
		<script type="text/javascript">
		function openUserSelect() {   
			var height = document.body.clientHeight;
			$.dialog({content: 'url:userController.do?userSelect', 
					zIndex: 9999, 
					title: '用户列表', 
					lock: true, 
					width: '1000px', 
					height: height, 
					opacity: 0.4, 
					button: [       
					        {name: '确定', callback: callbackUserSelect, focus: true},       
					        {name: '取消', callback: function (){}}   ]
					});
			}
			function callbackUserSelect() {
				var iframe = this.iframe.contentWindow;
				var rowsData = iframe.$('#userList1').datagrid('getSelections');
				if (!rowsData || rowsData.length==0) {
					tip('<t:mutiLang langKey="common.please.select.edit.item"/>');
					return;
					}
				var userName=rowsData[0].userName;
				$('#userName').val(userName);
				var names=rowsData[0].realName;
				$('#realName').val(names);
				$('#realName').blur();
			}
		</script>
	</div>
</div>

<t:datagrid fitColumns="true" name="myTaskList" queryMode="group" title="common.task.my" actionUrl="taskController.do?taskAllList" idField="id">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="common.node.timeout.remind" field="timeoutRemaid" hidden="true"></t:dgCol>
 <t:dgCol title="common.node.timeout.remind" field="taskNotificationRemarks" hidden="true"></t:dgCol>
 <t:dgCol title="common.node.timeout.remind" field="taskNotification" hidden="true"></t:dgCol>
 <t:dgCol title="标题" field="bpmBizTitle" formatterjs="timeoutRemaid" width="150"></t:dgCol>
 <t:dgCol title="common.process.id" field="Process_processDefinition_id" width="180" query="true" extend="{style:'width:200px'}"></t:dgCol>
 <t:dgCol title="common.process.name" field="Process_processDefinition_name" width="150" query="false"></t:dgCol>
 <t:dgCol title="process.instance" field="Process_task_processInstanceId" width="100"></t:dgCol>
 <t:dgCol title="common.process.user" field="userRealName" width="100" query="false"></t:dgCol>
 <t:dgCol title="common.process.assignee" field="assigneeName" width="100" hidden="true"></t:dgCol>
 <t:dgCol title="common.begintime" field="Process_task_createTime" width="130" formatterjs="getFormatDate"></t:dgCol>
 <t:dgCol title="common.endtime" field="Process_task_dueTime" width="130" hidden="true" formatterjs="getFormatDate"></t:dgCol>
 <t:dgCol title="common.status" field="TSPrjstatus_description" width="80"></t:dgCol>
 <t:dgCol title="common.process.current" field="Process_task_name" width="100"></t:dgCol>
 <t:dgCol hidden="true" title="TASK ID(该字段隐藏)" field="Process_task_id"></t:dgCol>
 <t:dgCol hidden="true" title="key" field="Process_task_taskDefinitionKey"></t:dgCol>
 <t:dgCol title="common.operation" field="opt" width="200"></t:dgCol>
 <t:dgConfOpt exp="assigneeName#empty#true" url="activitiController.do?claim&taskId={Process_task_id}" message="common.claim.sure" title="common.claim" urlclass="ace_button"  urlfont="fa-cog"></t:dgConfOpt>
  <%--<t:dgFunOpt exp="Process_task_assignee#empty#false" funname="openhandleMixTab(Process_task_id,Process_task_name)" title="办理"></t:dgFunOpt> --%>
 <t:dgFunOpt exp="assigneeName#empty#false" funname="openhandleMix(Process_task_id,Process_task_name)" title="process.handle" urlclass="ace_button"  urlfont="fa-plus-circle"></t:dgFunOpt>
 <t:dgFunOpt exp="assigneeName#empty#false" funname="selectEntruster(Process_task_id,Process_task_name)" title="common.entruster" urlStyle="background-color:#FF7F00;" urlclass="ace_button"  urlfont="fa-hand-o-right"></t:dgFunOpt>
</t:datagrid>

<SCRIPT type="text/javascript">
function timeoutRemaid(value,row,index){
	var rtn = "";
	if(row.taskNotification=='true'){
		rtn += "<img id='"+row.id+"' onmouseleave=\"cuibantipleave()\" onmouseenter=\"cuibantip('"+row.id+"','"+row.taskNotificationRemarks+"')\" title=\"点击查看催办信息\" style=\"width:12px;height:12px\" src=\"plug-in/easyui/themes/icons/bpm-cjtixin-red.png\" >";
	}
	if(row.timeoutRemaid=='true'){
		rtn += "<img title=\"超时\" style=\"width:12px;height:12px\" src=\"plug-in/easyui/themes/icons/bpm-fttixin-orange.png\" >"; 
	}
	return rtn+"<span title=\""+value+"\">"+value+"</span>";
}

function cuibantipleave(){
	layer.closeAll('tips');
}

function cuibantip(id,remarks){
	/* layer.tips(remarks, '#'+id, {
		  tips: [2, 'noto']
		}); */
	 layer.tips("<span style='color:#000'>"+remarks+"</span>", '#'+id, {
         tips: [1, '#FEFEFE']
        });
}

function getFormatDate(value,row,index){
	   return getTaskTime(value);
}
	
//输出格式：yyyy-MM-dd HH:mm:ss  
function getTaskTime(value) {   
    if(null==value || ""==value){  
        return "";  
    }  
	var format = "yyyy-MM-dd hh:mm:ss";
	if(value==''||value==null){
		return '';
	}
	var strdata=value.replace(/-/g,"/");
	var index=strdata.indexOf(".");
	if(index>0)
	{
		strdata=strdata.substr(0,index);
	}
	var date= new Date(Date.parse(strdata));
	var o = {
		"M+" : date.getMonth() + 1, // month
		"d+" : date.getDate(), // day
		"h+" : date.getHours(), // hour
		"m+" : date.getMinutes(), // minute
		"s+" : date.getSeconds(), // second
		"q+" : Math.floor((date.getMonth() + 3) / 3), // quarter
		"S" : date.getMilliseconds()
		// millisecond
	};
	
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, strdata.substr(4-RegExp.$1.length,RegExp.$1.length));
	}
	
	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}
</SCRIPT>