
/**
 * 流程提交
 * @param title  按钮标题
 * @param taskId 当前任务ID
 * @param nextNodeCount 下一步任务分支数
 * @param nextNodeId 下一步任务ID
 */
function procPass(title,taskId,nextNodeCount,nextNodeId){
			//alert('d.success');
			//$("#option").val(title);
			//$("#nextnode").val(nextTaskId);
	        alert(title+";"+taskId+";"+nextNodeCount+";"+nextNodeId);
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
						//W.tip(msg);
						alert(msg);
					}
				}
			});
		}

/**
 * 驳回
 * @param title
 * @param taskId
 * @param rejectNodeId
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

//通用弹出式文件上传
function commonUpload(callback){
    $.dialog({
           content: "url:systemController.do?commonUpload",
           lock : true,
           title:"文件上传",
           zIndex:2100,
           width:700,
           height: 200,
           parent:windowapi,
           cache:false,
       ok: function(){
               var iframe = this.iframe.contentWindow;
               iframe.uploadCallback(callback);
                   return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
}
function browseImages(inputId, Img) {// 图片管理器，可多个上传共用
		var finder = new CKFinder();
		finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
			$("#" + Img).attr("src", fileUrl);
			$("#" + inputId).attr("value", fileUrl);
		};
		finder.resourceType = 'Images';// 指定ckfinder只为图片进行管理
		finder.selectActionData = inputId; //接收地址的input ID
		finder.removePlugins = 'help';// 移除帮助(只有英文)
		finder.defaultLanguage = 'zh-cn';
		finder.popup();
	}
function browseFiles(inputId, file) {// 文件管理器，可多个上传共用
	var finder = new CKFinder();
	finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
		$("#" + file).attr("href", fileUrl);
		$("#" + inputId).attr("value", fileUrl);
		decode(fileUrl, file);
	};
	finder.resourceType = 'Files';// 指定ckfinder只为文件进行管理
	finder.selectActionData = inputId; //接收地址的input ID
	finder.removePlugins = 'help';// 移除帮助(只有英文)
	finder.defaultLanguage = 'zh-cn';
	finder.popup();
}
function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}