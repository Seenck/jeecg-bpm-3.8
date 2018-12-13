
/**
 * 流程提交
 * @param title  按钮标题
 * @param taskId 当前任务ID
 * @param nextNodeCount 下一步节点数
 * @param nextNodeId 下一步节点ID
 */
function procPass(title,taskId,nextNodeCount,nextNodeId){
			//alert('d.success');
			//$("#option").val(title);
			//$("#nextnode").val(nextTaskId);
	        //alert(title+";"+taskId+";"+nextNodeCount+";"+nextNodeId);
			var formData = {};
			
			formData["nextnode"]=nextNodeId;
			formData["nextCodeCount"]=nextNodeCount;
			formData["taskId"]=taskId;
			formData["model"]="1";
			
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
						var msg = d.msg;
						window.parent.callbackTable(msg);
					}else{
						var msg = d.msg;
						window.parent.tip(msg);
						//alert(msg);
					}
				}
			});
		}

/**
 * 驳回
 * @param title 按钮标题
 * @param taskId 当前任务ID
 * @param rejectNodeId 驳回节点ID
 */
function reject(title,taskId,rejectNodeId){
	//alert('d.success');
	//$("#option").val(yes);
	//$("#nextnode").val(nextnode);
	var formData = {};
	
	formData["rejectModelNode"]=rejectNodeId;
	formData["nextCodeCount"]=1;
	formData["taskId"]=taskId;
	formData["model"]="3";
	
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
				var msg = d.msg;
				window.parent.callbackTable(msg);
			}else{
				var msg = d.msg;
				//W.tip(msg);
				alert(msg);
			}
		}
	});
}


/**
 * 委派
 * @param title 标题
 * @param taskId 当前任务ID
 * @param userId 委派的人
 */
function selectEntrusterProcess(title,taskId,userId) {
	if (typeof (title) == 'undefined') {
		title = "";
	}
	alert(title+";"+userId);
	var formData = {};
	
	formData["taskId"]=taskId;
	formData["id"]=userId;
	
	//ajax方式提交iframe内的表单
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		data : formData,
		url : 'activitiController.do?doEntrust',// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('提交申请失败');
		},
		success : function(data) {
			var d = $.parseJSON(data);
			//alert('d.success'+d.success);
			if (d.success) {
				var msg = d.msg;
				window.parent.callbackTable(msg);
			}else{
				var msg = d.msg;
				//W.tip(msg);
				alert(msg);
			}
		}
	});

}
