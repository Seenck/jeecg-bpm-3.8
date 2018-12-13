	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title><t:mutiLang langKey="common.task.operate"/></title>
<%--   <t:base type="jquery,tools,easyui,DatePicker"></t:base> --%>
   <style type="text/css">
   	#t_table td label {font-size:15px;}
  </style>
 </head>
 <body>
   <t:formvalid formid="formobj" layout="table" dialog="true" usePlugin="password">
	   <input name="taskId" id="taskId" type="hidden" value="${taskId}" />
	   <input name="bormoney" id="bormoney" type="hidden" vartype="B" value="${bormoney}">
	   <input name="keys" id="keys" type="hidden" />
	   <input name="values" id="values" type="hidden" />
	   <input name="types" id="types" type="hidden" />
	   <input name="nextCodeCount" id="nextCodeCount" type="hidden" value="${nextCodeCount}"/>
    	<div  class="ui-widget-content ui-corner-all" style="padding: 10px; margin: 10px;">
	    	<div style="margin: 15px auto; height: 50px; width: 900px;" id="tabs-project">
		    	<c:if test="${bpmLogListCount-3 > 0}">
		    		<div class="progress"></div>
		    		<div class="progress"></div>
		    	</c:if>
		    	 <c:forEach items="${bpmLogList}" var="bpmLg" varStatus="name" >
		    	 	<c:if test="${name.index < bpmLogNewListCount}">
			    	 		<div class="progress progress"></div>
			    	 		<div class="progress progress1">
				    	 		<div class="detial">
							       <b>${bpmLg.task_node }</b><br/>
							        [<span style="color:green;"><t:mutiLang langKey="common.task.time"/>:
							       	<fmt:formatDate value="${bpmLg.op_time }" pattern="MM-dd HH:mm:ss"/></span>]<br/>
							       [<span><t:mutiLang langKey="common.task.operator"/>：${bpmLg.op_name }]</span>
							    </div>
						    </div>
		    	 	</c:if>
		    	 </c:forEach>
		    	 <c:if test="${currTaskName != null }">
		    	 	<div class='progress progress_cancel'></div>
		    	 	<div class="progress progress3">
			    	 	<div class="detial">
			                <span><b>${currTaskName}</b></span><br>
			                [<span style="color:red;"><t:mutiLang langKey="common.begintime"/>:
							 ${currTaskNameStartTime }]</span><br/>
							<!-- [<span><t:mutiLang langKey="common.task.assignee"/>：</span>]<br> -->
					       [<span><t:mutiLang langKey="common.task.operator"/>：${currTaskNameAssignee }]</span>
			          	</div>
		          	</div>
		          	<div class='progress progress_unstart'></div>
				 	<div class='progress progress_unstart'></div>
					<div class='progress progress_unstart'></div>
		    	 </c:if>
		    	  <c:if test="${currTaskName == null }">
		    	 	<div class="progress">
			    	 	<div class="detial">
			                <span><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<t:mutiLang langKey="common.task.finished"/></b></span><br>
			          	</div>
		          	</div>
		    	 </c:if>	
	    	 </div>
    	 </div>
    	 <table id="t_table" cellpadding="0" cellspacing="1" class="formtable" >
	    	 <tr height="35">
	    	 	<td class="value" style="padding: 0px 5px;">
	    	 		<label class="Validform_label"><t:mutiLang langKey="common.task.suggestion"/></label>
	    	 	</td>
	    	 </tr>
		     <c:forEach items="${bpmLogList}" var="bpmLog">
		     	<tr height="35">
		     		<td class="value" style="padding: 0px 5px;border-top:1px dashed #00CCCC; font-size:13px;">
		     			<fmt:formatDate value="${bpmLog.op_time }" pattern="yyyy-MM-dd HH:mm:ss"/>[${bpmLog.op_name }]
		     		</td>
		     	</tr>
		     	<tr height="35">
		     		<td class="value" style="padding: 0px 5px;font-size:13px;">
		     			[<span style="color:blue">${bpmLog.task_node }</span>]${bpmLog.memo }
		     		</td>
		     	</tr>
		     	<c:forEach items="${bpmLog.bpmFiles}" var="bpmFile">
			     	<tr height="35">
			     		<td class="value" style="padding: 0px 5px;">
			     			[<span style="color:blue"><t:mutiLang langKey="common.attachment"/></span>] ${bpmFile.attachmenttitle}
							<a href="commonController.do?viewFile&fileid=${bpmFile.id}&subclassname=org.jeecgframework.workflow.pojo.base.TPBpmFile" title="common.document.download"><t:mutiLang langKey="common.document.download"/></a>
							<a href="javascript:void(0);"
								onclick="openwindow('<t:mutiLang langKey="common.preview"/>','commonController.do?openViewFile&fileid=${bpmFile.id}&subclassname=org.jeecgframework.workflow.pojo.base.TPBpmFile','fList','800','700')"><t:mutiLang langKey="common.preview"/></a>
<!--							<a href="javascript:void(0)" class="jeecgDetail" onclick="del('tFinanceController.do?delFile&id=${bpmFile.id}',this)">删除</a>-->
			     		</td>
			     	</tr>
		    	</c:forEach>	
		    	<!-- <br/> -->
		     </c:forEach>
		     <br>
		     
		 </table>
  	</t:formvalid>

 </body>
</html>
