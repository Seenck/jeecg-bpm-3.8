try {
$.fn.panel.defaults.onBeforeDestroy = function() {/* tab关闭时回收内存 */
	var frame = $('iframe', this);
		if (frame.length > 0) {
			frame[0].contentWindow.document.write('');
			frame[0].contentWindow.close();
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		} else {
			$(this).find('.combo-f').each(function() {
				var panel = $(this).data().combo.panel;
				panel.panel('destroy');
			});
		}
};

$.parser.onComplete = function() {/* 页面所有easyui组件渲染成功后，隐藏等待信息 */
	if ($.browser.msie && $.browser.version < 7) {/* 解决IE6的PNG背景不透明BUG */
	}
	window.setTimeout(function() {
		try {
			window.top.$.messager.progress('close');
		} catch (e) {
		}
	}, 200);
};
/**
 * 部署流程图
 * 
 * @param deploymentId
 * @param resourceName
 */
function deploymentimg(deploymentId, resourceName,title) {
	if (typeof (title) == 'undefined') {
		title = "";
	}
	openwindow('流程部署详细图--'+title, 'activitiController.do?openProcessPic&tag=deployment&deploymentId=' + deploymentId + '&resourceName=' + resourceName);
}
/**
 * 下载流程xml
 * 
 * @param deploymentId
 * @param resourceName
 */
function downloadXml(deploymentId, resourceName,title) {
	if (typeof (title) == 'undefined') {
		title = "";
	}
	window.location.href='activitiController.do?processBpmXmlByDeploy&tag=deployment&deploymentId=' + deploymentId + '&resourceName=' + resourceName;
}
/**
 * 办理过程流程图
 * 
 * @param taskId
 */
function processimg(taskId, title) {
	if (typeof (title) == 'undefined') {
		title = "";
	}
	openwindow('流程进度--当前节点:' + title, 'activitiController.do?openProcessPic&tag=task&taskId=' + taskId);
}
//update-begin--author: zhoujf  Date: 20180727 for： 多流程流程进度查看----
/**
 * 项目列表流程图查看
 * @param flowCode 多流程流程编码
 * @param dataId 表单数据id
 * @param title 标题
 */
function  mutilFlowProgress(flowCode,dataId, title, width, height) {
	if (typeof (title) == 'undefined') {
		title = "流程进度";
	}
	openwindow(title, 'activitiController.do?openProcessPic&tag=mutilFlow&flowCode='+flowCode+'&dataId='+ dataId,'', width, height);
}
//update-end--author: zhoujf  Date: 20180727 for： 多流程流程进度查看----

/**
 * 项目列表流程图查看
 * @param taskId
 * @param title
 * @param width
 * @param height
 */
function progress(busKey, title) {
	if (typeof (title) == 'undefined') {
		title = "";
	}
	openwindow('流程进度--当前节点:' + title, 'activitiController.do?openProcessPic&tag=project&businessKey='+ busKey);
}
/**
 * 打开流程办理页面
 */
function openhandle(taskId, title, width, height) {
	if (typeof (width) == 'undefined') {
		width = "auto";
	}
	if (typeof (height) == 'undefined') {
		height = "auto";
	}
	if (typeof (title) == 'undefined') {
		title = "";
	}
	var url = 'activitiController.do?openProcessHandle&taskId=' + taskId;
	$.dialog({
		content: 'url:'+url,
		title : '流程办理--当前环节:' + title,
		lock : true,
		opacity : 0.3,
		button : [ {
			name : '同意',
			callback : function() {
				iframe = this.iframe.contentWindow;
				var inputvar = $("[vartype]", iframe.document);
				setvar(true, inputvar, iframe);
				saveObj();
				return false;

			},
			focus : true
		}, {
			name : '驳回',
			callback : function() {
				iframe = this.iframe.contentWindow;
				var inputvar = $("[vartype]", iframe.document);
				setvar(false, inputvar, iframe);
				saveObj();
				return false;
			}
		} ]
	});
}
/**
 * 设置流程变量
 * 
 * @param flag
 *          操作标示
 * @param varobj//作为流程变量的对象集合
 * @param iframe//页面对象
 */
function setvar(flag, varobj, iframe) {
	var keys = "opt,", values = flag + ",", types = "B,", tag = "";
	if (!flag) {
		// tag="Back";//驳回操作时变量添加后缀
	}
	varobj.each(function() {
		var value = this.value;
		if (value == "") {
			if (flag) {
				value = "同意";
			} else {
				value = "不同意";
			}
		}
		keys += this.id + tag + ",";
		values += value + ",";
		types += $("#" + this.id, iframe.document).attr("vartype") + ",";
	});
	$("#keys", iframe.document).val(keys);
	$("#values", iframe.document).val(values);
	$("#types", iframe.document).val(types);
}
/**
 * 打开流程办理页面 - New (新的流程办理页面包括"表单信息","任务处理","流程跟踪")
 */
function openhandleMix(taskId, title, width, height) {
	//update-begin--author: scott  Date: 20170307 for： 我的任务，历史任务弹出页面大小----
	if (typeof (width) == 'undefined' || width < 500) {
		width = window.top.document.body.offsetWidth - 300;
	}
	if (typeof (height) == 'undefined') {
		height = window.top.document.body.offsetHeight-100;
	}
	//update-end--author: scott  Date: 20170307 for： 我的任务，历史任务弹出页面大小----
	if (typeof (title) == 'undefined') {
		title = "";
	}
	var url = 'taskController.do?goTaskTab&taskId=' + taskId;
	$.dialog({
		content: 'url:'+url,
		title : '流程办理--当前环节 : ' + title,
		lock : true,
		zIndex: getzIndex(),
		opacity : 0.3,
		width: width,
		height: height,
		button : [{
			name : '关闭',
			callback : function() {
				iframe = this.iframe.contentWindow;
				return true;
			}
		} ]
	});
}

/**
 * 打开流程办理页面 - New (新的流程办理页面包括"表单信息","任务处理","流程跟踪")
 */
function goProcessHisTab(processInstanceId, title, width, height) {
	//update-begin--author: scott  Date: 20170307 for： 我的任务，历史任务弹出页面大小----
	if (width == 'undefined' || typeof (width) == 'undefined' || width < 500) {
		width = window.top.document.body.offsetWidth - 300;
	}
	if (height == 'undefined' || typeof (height) == 'undefined') {
		height = window.top.document.body.offsetHeight-100;
	}
	//update-end--author: scott  Date: 20170307 for： 我的任务，历史任务弹出页面大小----
	if (title == 'undefined' || typeof (title) == 'undefined') {
		title = "";
	}else{
		title ="--当前环节  : "+title
	}
	var url = 'taskController.do?goProcessHisTab&processInstanceId=' + processInstanceId;
	$.dialog({
		content: 'url:'+url,
		title : '流程历史' + title,
		lock : true,
		zIndex: getzIndex(),
		opacity : 0.3,
		width: width,
		height: height,
		button : [{
			name : '关闭',
			callback : function() {
				iframe = this.iframe.contentWindow;
				return true;
			}
		} ]
	});
}
/**
 * 打开流程办理页面 - New (新的流程办理页面包括"表单信息","任务处理","流程跟踪")
 */
function openhandleMixTab(taskId, title, width, height) {
	if (typeof (width) == 'undefined') {
		width = "auto";
	}
	if (typeof (height) == 'undefined') {
		height = "auto";
	}
	if (typeof (title) == 'undefined') {
		title = "";
	}
	var url = 'taskController.do?goTaskTab&taskId=' + taskId;
	addOneTab('任务办理-:'+title,url);
}



/**
 * 选择委托人
 * 
 * @param taskId
 */
function selectEntruster(taskId, title) {
	if (typeof (title) == 'undefined') {
		title = "";
	}
	createwindow('选择委托人' + title, 'activitiController.do?goEntrustAdd&tag=task&taskId=' + taskId,700,150);
}


} catch (e) {
}
