	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title><t:mutiLang langKey="common.task.operate"/></title>
<%--   <t:base type="jquery,tools,easyui,DatePicker"></t:base> --%>
 </head>
 <body>
   <style type="text/css">
   	#t_table td label {font-size:15px;}
   	.blueButton{
	  display: inline-block;
	  *display: inline;
	  padding: 4px 12px;
	  margin-bottom: 0;
	  *margin-left: .3em;
	  font-size: 14px;
	  line-height: 20px;
	  text-align: center;
	  vertical-align: middle;
	  cursor: pointer;
	  border: 1px solid #cccccc;
	  *border: 0;
	  border-bottom-color: #b3b3b3;
	  -webkit-border-radius: 4px;
	     -moz-border-radius: 4px;
	          border-radius: 4px;
	  *zoom: 1;
	  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
	     -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
	          box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
	  color: #ffffff;
	  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	  background-color: #006dcc;
	  *background-color: #0044cc;
	  background-image: -moz-linear-gradient(top, #0088cc, #0044cc);
	  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#0088cc), to(#0044cc));
	  background-image: -webkit-linear-gradient(top, #0088cc, #0044cc);
	  background-image: -o-linear-gradient(top, #0088cc, #0044cc);
	  background-image: linear-gradient(to bottom, #0088cc, #0044cc);
	  background-repeat: repeat-x;
	  border-color: #0044cc #0044cc #002a80;
	  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff0088cc', endColorstr='#ff0044cc', GradientType=0);
	  filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
}

.blueButton:hover,
.blueButton:focus,
.blueButton:active,
.blueButton.active,
.blueButton.disabled,
.blueButton[disabled] {
  color: #ffffff;
  background-color: #0044cc;
  *background-color: #003bb3;
}
	
	.disabledButton{ 
		display: inline-block;
	  *display: inline;
	  padding: 4px 12px;
	  margin-bottom: 0;
	  *margin-left: .3em;
	  font-size: 14px;
	  line-height: 20px;
	  text-align: center;
	  vertical-align: middle;
	  cursor: pointer;
	  border: 1px solid #cccccc;
	  *border: 0;
	  border-bottom-color: #b3b3b3;
	  -webkit-border-radius: 4px;
	     -moz-border-radius: 4px;
	          border-radius: 4px;
	  *zoom: 1;
	  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
	     -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
	          box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
	  color: #ffffff;
	  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	  background-color: #BDBEC0;
	  *background-color: #BDBEC0;
	  background-image: -moz-linear-gradient(top, #BDBEC0, #BDBEC0);
	  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#BDBEC0), to(#0044cc));
	  background-image: -webkit-linear-gradient(top, #BDBEC0, #BDBEC0);
	  background-image: -o-linear-gradient(top, #BDBEC0, #BDBEC0);
	  background-image: linear-gradient(to bottom, #BDBEC0, #BDBEC0);
	  background-repeat: repeat-x;
	  border-color: #BDBEC0 #BDBEC0 #BDBEC0;
	  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#BDBEC0', endColorstr='#BDBEC0', GradientType=0);
	  filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
	}
  </style>
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
		    	 <c:if test="${taskName != null }">
		    	 	<div class='progress progress_cancel'></div>
		    	 	<div class="progress progress3">
			    	 	<div class="detial">
			                <span><b>${taskName}</b></span><br>
			                [<span style="color:red;"><t:mutiLang langKey="common.begintime"/>:
							 ${taskNameStartTime }]</span><br/>
							<!-- [<span><t:mutiLang langKey="common.task.assignee"/>：</span>]<br> -->
							<!-- update-begin--Author:zhoujf  Date:20171226 for：TASK #2455 【bug】我的任务，流程进度条，当前节点处理人显示的账号，应该改成名称 -->
					       [<span><t:mutiLang langKey="common.task.operator"/>：${taskAssigneeName }]</span>
					        <!-- update-end--Author:zhoujf  Date:20171226 for：TASK #2455 【bug】我的任务，流程进度条，当前节点处理人显示的账号，应该改成名称 -->
			          	</div>
		          	</div>
		    	 </c:if>
		    	 <div class='progress progress_unstart'></div>
				 <div class='progress progress_unstart'></div>
				 <div class='progress progress_unstart'></div>	
	    	 </div>
    	 </div>
    	 <table id="t_table" cellpadding="0" cellspacing="1" class="formtable" style="width: 100%;">
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
		    	<br/>
		     </c:forEach>
		     <br>
		     <tr height="35" >
		     	<td class="value" style="padding: 0px 5px;">
		     		 <label class="Validform_label" style="font-size:14px;">
				      	<t:mutiLang langKey="common.handel.suggestion"/><p></p>
				     </label>
				     <textarea name="reason"  vartype="S" style="resize: none;" rows="3" cols="105"></textarea>
		     		<span class="Validform_checktip"></span>
		     	</td>
		     </tr>
		    <tr> 
			  <td class="value" style="padding: 0px 5px;">
			  	<div class="form jeecgDetail" style="padding: 3px;">
			    	<input type="hidden" id="bpmlogId" name="bpmlogId" />
					<br/><t:upload name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;" buttonText="common.file.add" formData="bpmlogId" uploader="activitiController.do?saveBpmFiles">
					</t:upload>
				</div>
				<div class="form" id="filediv" style="height: 50px"></div>
				</td>
			</tr> 
			<tr> 
			  <td class="value">
				 <input type="radio" name="model" value="1" onclick="changeModel(1);" checked/><t:mutiLang langKey="common.model.one"/>
				 <input type="radio" name="model" value="2" onclick="changeModel(2);"/><t:mutiLang langKey="common.model.more"/>
				 <span id="manyModel" style="display:none">
				 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red"><t:mutiLang langKey="common.model.more.all"/>：</span>
					<c:forEach items="${transitionList}" var="trans">
						<input type="checkbox" name="transition" value="${trans.nextnode}" checked disabled>${trans.Transition }
					</c:forEach>
		  		</span>
		  		<c:if test="${histListSize > 0 }">
			  		<input type="radio" name="model" value="3" onclick="changeModel(3);"/><t:mutiLang langKey="common.reject"/>
			  		<span id="rejectModel" style="display:none">
			  			<select name="rejectModelNode">
			  				<!-- update-begin--Author:zhoujf  Date:20170801 for：TASK #2225 去掉退回按钮，增加驳回上一步操作 -->
			  				<c:if test="${histListSize > 0 }">
				  			<c:if test="${turnbackTaskId!='' }">
				  				<option value="${turnbackTaskId}" selected>上一步</option>
				  			</c:if>
				  			</c:if> 
			  				<c:forEach items="${histListNode}" var="histNode">
			  					<option value="${histNode.task_def_key_}" <%-- <c:if test="${histNode.task_def_key_==turnbackTaskId }">selected</c:if>  --%>>${histNode.name_ }</option>
			  				</c:forEach>
			  				<!-- update-end--Author:zhoujf  Date:20170801 for：TASK #2225 去掉退回按钮，增加驳回上一步操作 -->
			  			</select>
			  		</span>
		  		</c:if>
			  </td>
		 	</tr>
		 	<tr> 
			  <td class="value">
				 <input type="checkbox" name="next_username_checkbox" id="next_username_checkbox" onclick="changeUsernameCheckbox();" />
			                 指定下一步处理人（指定下一步会签人员）
				 <input type="checkbox" name="ccusername_checkbox" id="ccusername_checkbox" onclick="changeCCUsernameCheckbox();" />
				  是否抄送
			  </td>
		 	</tr>
		 	<tr id="next_username_checkbox_tr" style="display:none"> 
			  <td class="value">
				 指定下一步处理人/指定下一步会签人员：
				 <input name="last" id="last" readonly="readonly" type="text"> 
				 <input name="id" type="hidden" value="" id="id">  
				 <t:choose width="700" height="400" hiddenName="id" hiddenid="id" url="activitiController.do?goEntrust" name="entrusterList" icon="icon-search" title="common.user.list" textname="last" isclear="true" fun="hqCallback"></t:choose><t:mutiLang langKey="common.noselect.default"/>
			  </td>
		 	</tr>
		 	<tr> 
		 	<tr id="ccusername_checkbox_tr" style="display:none"> 
			  <td class="value">
				    抄送给：
				 <input name="ccUserRealNames" id="ccUserRealNames" readonly="readonly" type="text"> 
				 <input name="ccUserNames" type="hidden" value="" id="ccUserNames">  
				 <t:choose width="700" height="400" hiddenName="ccUserNames" hiddenid="id" url="activitiController.do?goEntrust" name="entrusterList" icon="icon-search" title="common.user.list" inputTextname="ccUserRealNames" textname="last" isclear="true" fun="ccCallback"></t:choose>
			  </td>
		 	</tr>
			<tr align="center"> 
			  <td class="value"  align="center">
			  		<div id="singleModel" style="display:black">
						<input type="hidden" name="option" id="option" />
			  			<input type="hidden" name="nextnode" id="nextnode" />
						<c:forEach items="${transitionList}" var="trans">
							<input type="button" buttongroup="buttongroup" class="blueButton" onclick="disabledButton();procPass('${trans.Transition }','${trans.nextnode}')" value="${trans.Transition }">
						</c:forEach>
						<!-- update-begin--Author:zhoujf  Date:20170801 for：TASK #2225 去掉退回按钮，增加驳回上一步操作 -->
						<%-- <c:if test="${histListSize > 0 }">
						<c:if test="${turnbackTaskId!='' }">
			  			<input type="button" buttongroup="buttongroup" class="blueButton" onclick="disabledButton();rejectProcessButton('${turnbackTaskId}');" value='退回'>
		  				</c:if>
			  			</c:if> --%>
			  			<!-- update-end--Author:zhoujf  Date:20170801 for：TASK #2225 去掉退回按钮，增加驳回上一步操作 -->
			  		</div>
			  		<div id="manyModelButton" style="display:none">
			  			<input type="button" buttongroup="buttongroup" class="blueButton" onclick="disabledButton();manyModelSubmit();" value='<t:mutiLang langKey="common.submit"/>'>
			  			<input type="hidden" name="transStr" id="transStr">
			  			<!-- update-begin--Author:zhoujf  Date:20170801 for：TASK #2225 去掉退回按钮，增加驳回上一步操作 -->
			  			<%-- <c:if test="${histListSize > 0 }">
			  			<c:if test="${turnbackTaskId!='' }">
				  		<input type="button" id="rejectModelButton" buttongroup="buttongroup" class="blueButton" onclick="disabledButton();rejectProcessButton('${turnbackTaskId}');" value='退回'>
			  			</c:if>
			  			</c:if> --%>
			  			<!-- update-end--Author:zhoujf  Date:20170801 for：TASK #2225 去掉退回按钮，增加驳回上一步操作 -->
			  		</div>
				</td>
			</tr>
		 </table>
  	</t:formvalid>
    <script type="text/javascript">
    //-- update-begin--Author:zhoujf  Date:20180104 for：TASK #1215 【改造】会签支持配置固定的会签人员，不用再下一步节点的时候，手工选择 -->
					function hqCallback() {
						var last=iframe.getValuesById('userName');
						if ($('#last').length >= 1) {
							$('#last').val(last);
							$('#last').blur();
						}
						if ($("input[name='last']").length >= 1) {
							$("input[name='last']").val(last);
							$("input[name='last']").blur();
						}
						var id=iframe.getValuesById('id');
						if (id !== undefined && id != "") {
							$('#id').val(id);
						}
					}
					
					function ccCallback() {
						var last=iframe.getValuesById('userName');
						if ($('#ccUserRealNames').length >= 1) {
							$('#ccUserRealNames').val(last);
							$('#ccUserRealNames').blur();
						}
						if ($("input[name='ccUserRealNames']").length >= 1) {
							$("input[name='ccUserRealNames']").val(last);
							$("input[name='ccUserRealNames']").blur();
						}
						var id=iframe.getValuesById('id');
						if (id !== undefined && id != "") {
							$('#ccUserNames').val(id);
						}
					}
	//-- update-begin--Author:zhoujf  Date:20180104 for：TASK #1215 【改造】会签支持配置固定的会签人员，不用再下一步节点的时候，手工选择 -->
					function disabledButton() {
						$('input[buttongroup="buttongroup"]').attr("disabled",
								"true");
						$('input[buttongroup="buttongroup"]').attr("class",
								"disabledButton");
					}

					//update-begin--Author:zhoujf  Date:20170801 for：TASK #2225 置灰按钮恢复
					function showButton() {
						$('input[buttongroup="buttongroup"]').removeAttr(
								"disabled");
						$('input[buttongroup="buttongroup"]').attr("class",
								"blueButton");
					}
					//update-end--Author:zhoujf  Date:20170801 for：TASK #2225 置灰按钮恢复

					function procPass(yes, nextnode) {
						//alert('d.success');
						$("#option").val(yes);
						$("#nextnode").val(nextnode);
						var formData = {};
						$(formobj)
								.find("input,textarea,select")
								.each(
										function() {
											if ($(this).attr("name") == 'model') {
												formData[$(this).attr("name")] = $(
														'input[name="model"]:checked')
														.val();
											} else {
												formData[$(this).attr("name")] = $(
														this).val();
											}
										});
						//ajax方式提交iframe内的表单
						$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							data : formData,
							url : 'activitiController.do?processComplete',// 请求的action路径
							error : function() {// 请求失败处理函数
								alert('提交申请失败');
							},
							success : function(data) {
								var d = $.parseJSON(data);
								//alert('d.success'+d.success);
								if (d.success) {
									$("#bpmlogId").val(d.obj.id);
									if ($(".uploadify-queue-item").length > 0) {
										upload();
									} else {
										var msg = d.msg;
										W.tip(msg);
										W.reloadTable();
										windowapi.close();
									}
								} else {
									var msg = d.msg;
									//W.tip(msg);
									alert(msg);
								}
								//update-begin--Author:zhoujf  Date:20170801 for：TASK #2225 置灰按钮恢复
								setTimeout("showButton()", 500);
								//update-end--Author:zhoujf  Date:20170801 for：TASK #2225 置灰按钮恢复
							}
						});
					}

					function rejectProcessButton(turnbackTaskId) {
						var formData = {};
						$(formobj)
								.find("input,textarea,select")
								.each(
										function() {
											if ($(this).attr("name") == 'model') {
												formData[$(this).attr("name")] = "3";
											} else if ($(this).attr("name") == 'rejectModelNode') {
												formData[$(this).attr("name")] = turnbackTaskId;
											} else {
												formData[$(this).attr("name")] = $(
														this).val();
											}
										});
						$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							data : formData,
							url : 'activitiController.do?processComplete',// 请求的action路径
							error : function() {// 请求失败处理函数
								alert('退回失败');
							},
							success : function(data) {
								var d = $.parseJSON(data);
								//alert('d.success'+d.success);
								if (d.success) {
									$("#bpmlogId").val(d.obj.id);
									if ($(".uploadify-queue-item").length > 0) {
										upload();
									} else {
										var msg = d.msg;
										W.tip(msg);
										W.reloadTable();
										windowapi.close();
									}
								} else {
									var msg = d.msg;
									//W.tip(msg);
									alert(msg);
								}
								//update-begin--Author:zhoujf  Date:20170801 for：TASK #2225 置灰按钮恢复
								setTimeout("showButton()", 500);
								//update-end--Author:zhoujf  Date:20170801 for：TASK #2225 置灰按钮恢复
							}
						});
					}

					/**
					 * 单分支模式/多分支模式切换
					 */
					function changeModel(value) {
						if (value == 1) {
							//单分支模式
							$("#singleModel").show();
							$("#manyModel").hide();
							$("#manyModelButton").hide();
							$("#rejectModel").hide();
						} else if (value == 2) {
							//多分支模式
							$("#singleModel").hide();
							$("#rejectModel").hide();
							$("#manyModel").show();
							$("#manyModelButton").show();
							$("#rejectModelButton").show();
						} else {
							$("#singleModel").hide();
							$("#manyModel").hide();
							$("#rejectModel").show();
							$("#manyModelButton").show();
							$("#rejectModelButton").hide();
						}

					}

					/**
					 *  指定下一步处理人/指定下一步会签人员切换
					 */
					function changeUsernameCheckbox() {
						if ($("#next_username_checkbox").attr("checked") == "checked"
								|| $("#next_username_checkbox").attr("checked") == "true") {
							//是
							$("#next_username_checkbox_tr").show();
						} else {
							//否
							$("#next_username_checkbox_tr").hide();
							$("#id").val("");
							$("#last").val("");
						}
					}

					/**
					 *  是否抄送
					 */
					function changeCCUsernameCheckbox() {
						if ($("#ccusername_checkbox").attr("checked") == "checked"
								|| $("#ccusername_checkbox").attr("checked") == "true") {
							//是
							$("#ccusername_checkbox_tr").show();
						} else {
							//否
							$("#ccusername_checkbox_tr").hide();
							$("#ccUserNames").val("");
							$("#ccUserRealNames").val("");
						}
					}

					/**
					 * 多分支模式 提交
					 */
					function manyModelSubmit() {
						// alert('d.success');

						/**	//checkbox 选中
							var transStr = "";
							var trans = $("input[name='transition']");
						   	for(i=0;i<trans.length;i++){
						           if(trans[i].checked==true){
						           	transStr += (trans[i].value+',');
						           }
						       }
						   	$("#transStr").val(transStr);
						   	if(transStr == ""){
						   		alert("多分支模式必须选择下一步分支");
						       	return;
						   	}
						 */
						var formData = {};
						$(formobj)
								.find("input,textarea,select")
								.each(
										function() {
											if ($(this).attr("name") == 'model') {
												formData[$(this).attr("name")] = $(
														'input[name="model"]:checked')
														.val();
											} else {
												formData[$(this).attr("name")] = $(
														this).val();
											}
										});
						$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							data : formData,
							url : 'activitiController.do?processComplete',// 请求的action路径
							error : function() {// 请求失败处理函数
								alert('提交申请失败');
							},
							success : function(data) {
								var d = $.parseJSON(data);
								//alert('d.success'+d.success);
								if (d.success) {
									$("#bpmlogId").val(d.obj.id);
									if ($(".uploadify-queue-item").length > 0) {
										upload();
									} else {
										var msg = d.msg;
										W.tip(msg);
										W.reloadTable();
										windowapi.close();
									}
								} else {
									var msg = d.msg;
									//W.tip(msg);
									alert(msg);
								}
								//update-begin--Author:zhoujf  Date:20170801 for：TASK #2225 置灰按钮恢复
								setTimeout("showButton()", 500);
								//update-end--Author:zhoujf  Date:20170801 for：TASK #2225 置灰按钮恢复
							}
						});
					}
				</script>
 </body>
</html>
