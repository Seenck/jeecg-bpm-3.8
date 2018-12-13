﻿﻿﻿﻿// update--begin--author:zhangjiaqiang date:20170621 for:如何避免console.log引起javascript的兼容问题 
if(!window.console){
    window.console = {};
}
if(!window.console.log){
    window.console.log = function(msg){};
}
// update--end--author:zhangjiaqiang date:20170621 for:如何避免console.log引起javascript的兼容问题
//update-begin--Author:scott  --- Date:20170401 --- for：inputclick ajax请求追加根路径，防止深路径访问有问题----
﻿var basePath;
try{
	var local = window.location;  
	var contextPath = local.pathname.split("/")[1];  
	basePath = local.protocol+"//"+local.host+"/"+contextPath;
	//alert(basePath);
}catch(e){}
//update-end--Author:scott  --- Date:20170401 --- for：inputclick ajax请求追加根路径，防止深路径访问有问题----

//﻿var jq = jQuery.noConflict();
/**
 * 增删改工具栏
 */
/*window.onerror = function() {
	return true;
};*/
var iframe;// iframe操作对象
var win;//窗口对象
var gridname="";//操作datagrid对象名称
//scott 20160426 JS异常报错
var windowapi;
var W;
try {
	windowapi = frameElement.api, W = windowapi.opener;//内容页中调用窗口实例对象接口
} catch (e) {
}


/**
 * 设置 window的 zIndex
 * @param flag true: 不增量(因为 tip提示经常使用 zIndex, 所以如果是 tip的话 ,则不增量)
 * @returns
 */
function getzIndex(flag){
	var zindexNumber = getCookie("ZINDEXNUMBER");
	if(zindexNumber == null){
		zindexNumber = 1990;
	}
	
	if(!flag){
		zindexNumber = parseInt(zindexNumber) + parseInt(10);
		setCookie("ZINDEXNUMBER",zindexNumber);
	}
	return zindexNumber;
}  

function upload(curform) {
	upload();
}
/**
 * 添加事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 */
function add(title,addurl,gname,width,height) {
	gridname=gname;
	createwindow(title, addurl,width,height);
}
/**
 * 树列表添加事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 */
function addTreeNode(title,addurl,gname) {
	if (rowid != '') {
		addurl += '&id='+rowid;
	}
	gridname=gname;
	createwindow(title, addurl);
}
/**
 * 更新事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
//update-begin--Author:luobaoli  Date:20150705 for：增加了一个判断是否需要转换为restful风格URL的参数，如果该参数为true，那么转换为restful风格
function update(title,url, id,width,height,isRestful) {
	gridname=id;
	var rowsData = $('#'+id).bootstrapTable('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择编辑项目');
		return;
	}
	if (rowsData.length>1) {
		tip('请选择一条记录再编辑');
		return;
	}
	if(isRestful!='undefined'&&isRestful){
		url += '/'+rowsData[0].id;
	}else{
		url += '&id='+rowsData[0].id;
	}
	createwindow(title,url,width,height);
}
//update-end--Author:luobaoli  Date:20150705 for：增加了一个判断是否需要转换为restful风格URL的参数，如果该参数为true，那么转换为restful风格

//update-begin--Author:gj_shaojc  Date:20180330 for：TASK #2580 【论坛问题 - 问题确认】树状的数据列表添加checkbox后全选选不中 
function updatetree(title,url, id,width,height,isRestful) {
	gridname=id;
	var rowsData = $('#'+id).treegrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择编辑项目');
		return;
	}
	if (rowsData.length>1) {
		tip('请选择一条记录再编辑');
		return;
	}
	if(isRestful!='undefined'&&isRestful){
		url += '/'+rowsData[0].id;
	}else{
		url += '&id='+rowsData[0].id;
	}
	createwindow(title,url,width,height);
}

function detailtree(title,url, id,width,height) {
	var rowsData = $('#'+id).treegrid('getSelections');
	if (!rowsData || rowsData.length == 0) {
		tip('请选择查看项目');
		return;
	}
	if (rowsData.length > 1) {
		tip('请选择一条记录再查看');
		return;
	}
    url += '&load=detail&id='+rowsData[0].id;
	createdetailwindow(title,url,width,height);
}
/**
 * 多记录刪除請求
 * @param title
 * @param url
 * @param gname
 * @return
 */
function deleteALLSelecttree(title,url,gname) {
	gridname=gname;
    var ids = [];
    var rows = $("#"+gname).treegrid('getSelections');
    if (rows.length > 0) {
    	$.dialog.setting.zIndex = getzIndex(true);
    	$.dialog.confirm('你确定永久删除该数据吗?', function(r) {
		   if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).bootstrapTable('uncheckAll');
							ids='';
						}
					}
				});
			}
		});
	} else {
		tip("请选择需要删除的数据");
	}
}
//update-end--Author:gj_shaojc  Date:20180330 for：TASK #2580 【论坛问题 - 问题确认】树状的数据列表添加checkbox后全选选不中 
/**
 * 如果页面是详细查看页面，无效化所有表单元素，只能进行查看
 */
$(function(){
	if(location.href.indexOf("load=detail")!=-1){
		$(":input").attr("disabled","true");
		//$(":input").attr("style","border:0;border-bottom:1 solid black;background:white;");
	}
});

/**
 * 查看详细事件打开窗口
 * @param title 查看框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function detail(title,url, id,width,height) {
	var rowsData = $('#'+id).bootstrapTable('getSelections');
//	if (rowData.id == '') {
//		tip('请选择查看项目');
//		return;
//	}
	
	if (!rowsData || rowsData.length == 0) {
		tip('请选择查看项目');
		return;
	}
	if (rowsData.length > 1) {
		tip('请选择一条记录再查看');
		return;
	}
    url += '&load=detail&id='+rowsData[0].id;
	createdetailwindow(title,url,width,height);
}

/**
 * 多记录刪除請求
 * @param title
 * @param url
 * @param gname
 * @return
 */
function deleteALLSelect(title,url,gname) {
	gridname=gname;
    var ids = [];
    var rows = $("#"+gname).bootstrapTable('getSelections');
    if (rows.length > 0) {
    	$.dialog.setting.zIndex = getzIndex(true);
    	$.dialog.confirm('你确定永久删除该数据吗?', function(r) {
		   if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).bootstrapTable('uncheckAll');
							ids='';
						}
					}
				});
			}
		});
	} else {
		tip("请选择需要删除的数据");
	}
}

/**
 * 查看时的弹出窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function createdetailwindow(title, addurl,width,height) {
	width = width?width:700;
	height = height?height:400;
	if(width=="100%" || height=="100%"){
		width = window.top.document.body.offsetWidth;
		height =window.top.document.body.offsetHeight-100;
	}
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+addurl,
			zIndex: getzIndex(),
			lock : true,
			width:width,
			height: height,
			title:title,
			opacity : 0.3,
			cache:false, 
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		});
	}else{
		//update-begin--Author:xuelin  Date:20170421 for：#1804 【体验】需要选中行数据，点击阅读才弹出页面--------------------
		W.$.dialog({
			content: 'url:'+addurl,
			zIndex: getzIndex(),
			lock : true,
			width:width,
			height: height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false, 
		    cancelVal: '关闭',
		    cancel: function(){
		    	windowapi.zindex();
		    }
			//cancel:true /*为true等价于function(){}*/
		});
		//update-end--Author:xuelin  Date:20170421 for：#1804 【体验】需要选中行数据，点击阅读才弹出页面----------------------
	}
	
}
/**
 * 全屏编辑
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function editfs(title,url) {
	var name=gridname;
	 if (rowid == '') {
		tip('请选择编辑项目');
		return;
	}
	url += '&id='+rowid;
	//update-begin--Author:scott  --- Date:20170406 --- for：弹出窗口最大化问题处理----
	width = window.top.document.body.offsetWidth;
	height =window.top.document.body.offsetHeight-100;
	openwindow(title,url,name,width,height);
	//update-end--Author:scott  --- Date:20170406 --- for：弹出窗口最大化问题处理----
}
// 删除调用函数
function delObj(url,name) {
	gridname=name;
	createdialog('删除确认 ', '确定删除该记录吗 ?', url,name);
}
// 删除调用函数
function confuploadify(url, id) {
		$.dialog.setting.zIndex = getzIndex(true);
	$.dialog.confirm('确定删除吗', function(){
		deluploadify(url, id);
	}, function(){
	});
}
/**
 * 执行删除附件
 * 
 * @param url
 * @param index
 */
function deluploadify(url, id) {
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
				$("#" + id).remove();// 移除SPAN
				m.remove(id);// 移除MAP对象内字符串
			}

		}
	});
}
// 普通询问操作调用函数
//update-begin-author:taoyan date:20171130 for：增加是否显示遮罩--
function confirm(url, content,name,noShade) {
	createdialog('提示信息 ', content, url,name,noShade);
}
//update-end-author:taoyan date:20171130 for：增加是否显示遮罩--
/**
 * 提示信息
 */
function tip_old(msg) {
	$.dialog.setting.zIndex = getzIndex(true);
	$.dialog.tips(msg, 1);
}
/**
 * 提示信息
 */
function tip(msg) {
	try{
		$.dialog.setting.zIndex = getzIndex(true);
		//update--begin--author:zhangjiaqiang Date:20170227 for:更新提示框
		var navigatorName = "Microsoft Internet Explorer";
		//update--begin--author:taoYan Date:20170628 for:default/shortcut提示用原风格---
		if(navigator.appName == navigatorName||"default,shortcut".indexOf(getCookie("JEECGINDEXSTYLE"))>=0){
		//update--end--author:taoYan Date:20170628 for:default/shortcut提示用原风格---
			$.messager.show({
				title : '提示信息',
				msg : msg,
				timeout : 1000 * 6
			});
		}else{
			var icon = 7;
			if(msg.indexOf("成功") > -1){
				icon = 1;
			}else if(msg.indexOf("失败") > -1){
				icon = 2;
			}
			layer.open({
				title:'提示信息',
				offset:'rb',
				content:msg,
				time:3000,
				btn:false,
				shade:false,
				icon:icon,
				shift:2
			});
		}
		//update--begin--author:zhangjiaqiang Date:20170227 for:更新提示框
	}catch(e){
		alertTipTop(msg,'10%');
	}
}

/**
 * Layer风格alert提示
 */
function alerLayerTip(msg) {
	if(msg==null || msg==''){
		msg = "系统异常，请看系统日志!";
	}
	try{
		var navigatorName = "Microsoft Internet Explorer"; 
		//update--begin--author:zhangjiaqiang date:20170711 for:TASK 2218 shortcut风格 使用jqueryui对话框
		if( navigator.appName == navigatorName ||"default,shortcut".indexOf(getCookie("JEECGINDEXSTYLE"))>=0){
		//update--end--author:zhangjiaqiang date:20170711 for:TASK 2218 shortcut风格 使用jqueryui对话框
			$.messager.alert('提示信息',msg);
		}else{
			layer.open({
				title:'提示信息',
				content:msg,
				time:6000,
				btn:false,
				shade:false,
				icon:2
			});
		}
	}catch(e){
		alertTipTop(msg,'10%');
	}
}

function alertTipTop(msg,top,title) {
	$.dialog.setting.zIndex = getzIndex(true);
	title = title?title:"提示信息";
	$.dialog({
			title:title,
			zIndex: getzIndex(),
			icon:'tips.gif',
			top:top,
			content: msg
		});
}

/**
 * 提示信息像alert一样
 */
function alertTip(msg,title) {
	$.dialog.setting.zIndex = getzIndex(true);
	title = title?title:"提示信息";
	$.dialog({
			title:title,
			zIndex: getzIndex(),
			icon:'tips.gif',
			content: msg
		});
}
//--author：zhoujf---------date：20180718---------for：弹出窗口大小控制问题
function isRealNum(val){
    // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
    if(val === "" || val ==null){
        return false;
    }
    if(!isNaN(val)){
        return true;
    }else{
        return false;
    }
}  
//--author：zhoujf---------date：20180718---------for：弹出窗口大小控制问题
/**
 * 创建添加或编辑窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function createwindow(title, addurl,width,height) {
	//--author：zhoujf---------date：20180718---------for：弹出窗口大小控制问题
	if(width=="100%" || height=="100%"){
		width = window.top.document.body.offsetWidth;
		height =window.top.document.body.offsetHeight-100;
	}else{
		width = isRealNum(width)?width:700;
		height = isRealNum(height)?height:400;
		width=parseInt(width);
		height=parseInt(height);
	}
	//--author：zhoujf---------date：20180718---------for：弹出窗口大小控制问题
    //--author：JueYue---------date：20140427---------for：弹出bug修改,设置了zindex()函数
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+addurl,
			lock : true,
			zIndex: getzIndex(),
			width:width,
			height:height,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		});
	}else{
		//update-begin--Author:xuelin  Date:20170705 for：TASK #2196 【bug】云桌面问题--------------------
		/*W.*/$.dialog({//使用W，即为使用顶级页面作为openner，造成打开的次级窗口获取不到关联的主窗口
			content: 'url:'+addurl,
			lock : true,
			width:width,
			zIndex:getzIndex(),
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		});
		//update-end--Author:xuelin  Date:20170705 for：TASK #2196 【bug】云桌面问题----------------------
	}
    //--author：JueYue---------date：20140427---------for：弹出bug修改,设置了zindex()函数
	
}
/**
 * 创建上传页面窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function openuploadwin(title, url,name,width, height) {
	gridname=name;
	$.dialog({
	    content: 'url:'+url,
		zIndex: getzIndex(),
	    cache:false,
	    button: [
	        {
	            name: "开始上传",
	            callback: function(){
	            	iframe = this.iframe.contentWindow;
					iframe.upload();
					return false;
	            },
	            focus: true
	        },
	        {
	            name: "取消上传",
	            callback: function(){
	            	iframe = this.iframe.contentWindow;
					iframe.cancel();
	            }
	        }
	    ]
	});
}
/**
 * 创建查询页面窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function opensearchdwin(title, url, width, height) {
	$.dialog({
		content: 'url:'+url,
		zIndex: getzIndex(),
		title : title,
		lock : true,
		height : height,
		cache:false,
		width : width,
		opacity : 0.3,
		button : [ {
			name : '查询',
			callback : function() {
				iframe = this.iframe.contentWindow;
				iframe.searchs();
			},
			focus : true
		}, {
			name : '取消',
			callback : function() {

			}
		} ]
	});
}
/**
 * 创建不带按钮的窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function openwindow(title, url,name, width, height) {
	//update-begin--Author:scott  --- Date:20170406 --- for：弹出窗口最大化问题处理----
	if(width=="100%" || height=="100%"){
		width = window.top.document.body.offsetWidth;
		height =window.top.document.body.offsetHeight-100;
	}
	//update-end--Author:scott  --- Date:20170406 --- for：弹出窗口最大化问题处理----
	gridname=name;
	if (typeof (width) == 'undefined'&&typeof (height) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				zIndex: getzIndex(),
				title : title,
				cache:false,
				lock : true,
				width: 'auto',
			    height: height
			});
		}else{
			$.dialog({
				content: 'url:'+url,
				zIndex: getzIndex(),
				title : title,
				cache:false,
				parent:windowapi,
				lock : true,
				width: 'auto',
			    height: height
			});
		}
	}
	if (typeof (height) == 'undefined'&&typeof (width) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				zIndex: getzIndex(),
				lock : true,
				width: width,
				cache:false,
			    height: 'auto'
			});
		}else{
			$.dialog({
				content: 'url:'+url,
				zIndex: getzIndex(),
				title : title,
				lock : true,
				parent:windowapi,
				width: width,
				cache:false,
			    height: 'auto'
			});
		}
	}
	if (typeof (width) == 'undefined'&&typeof (height) == 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				zIndex: getzIndex(),
				title : title,
				lock : true,
				width: 'auto',
				cache:false,
			    height: 'auto'
			});
		}else{
			$.dialog({
				content: 'url:'+url,
				zIndex: getzIndex(),
				title : title,
				lock : true,
				parent:windowapi,
				width: 'auto',
				cache:false,
			    height: 'auto'
			});
		}
	}
	
	if (typeof (width) != 'undefined'&&typeof (height) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				width: width,
			    height:height,
				content: 'url:'+url,
				zIndex: getzIndex(),
				title : title,
				cache:false,
				lock : true
			});
		}else{
			$.dialog({
				width: width,
			    height:height,
				content: 'url:'+url,
				zIndex: getzIndex(),
				parent:windowapi,
				title : title,
				cache:false,
				lock : true
			});
		}
	}
}

/**
 * 创建询问窗口
 * 
 * @param title
 * @param content
 * @param url
 * @param noShade 不赋值则有遮罩
 */
 //update-begin-author:taoyan date:20171129 for:不显示遮罩---
function createdialog(title, content, url,name,noShade) {
	$.dialog.setting.zIndex = getzIndex(true);
//	$.dialog.confirm(content, function(){
//		doSubmit(url,name);
//		rowid = '';
//	}, function(){
//	});
	//update--begin---author:zhangjiaqiang date:20170301 for:修订提示框
	var navigatorName = "Microsoft Internet Explorer"; 
	//update--begin--author:zhangjiaqiang date:20170711 for:TASK 2218 shortcut风格 使用jqueryui对话框
	if( navigator.appName == navigatorName ||"default,shortcut".indexOf(getCookie("JEECGINDEXSTYLE"))>=0){ 
	//update--begin--author:zhangjiaqiang date:20170711 for:TASK 2218 shortcut风格 使用jqueryui对话框
		$.dialog.confirm(content, function(){
			doSubmit(url,name);
			rowid = '';
		}, function(){
		});
	}else{
		layer.open({
			title:title,
			content:content,
			icon:7,
			shade: !noShade?0.3:0,
			yes:function(index){
				doSubmit(url,name);
				rowid = '';
			},
			btn:['确定','取消'],
			btn2:function(index){
				layer.close(index);
			}
		});
	}
	//update-end-author:taoyan date:20171129 for:不显示遮罩---
	//update--end---author:zhangjiaqiang date:20170301 for:修订提示框
}
/**
 * 执行保存
 * 
 * @param url
 * @param gridname
 */
function saveObj() {
	$('#btn_sub', iframe.document).click();
}

/**
 * 执行AJAX提交FORM
 * 
 * @param url
 * @param gridname
 */
function ajaxSubForm(url) {
	$('#myform', iframe.document).form('submit', {
		url : url,
		onSubmit : function() {
			iframe.editor.sync();
		},
		success : function(r) {
			tip('操作成功');
			reloadTable();
		}
	});
}
/**
 * 执行查询
 * 
 * @param url
 * @param gridname
 */
function search() {

	$('#btn_sub', iframe.document).click();
	iframe.search();
}

/**
 * 执行操作
 * 
 * @param url
 * @param index
 */
function doSubmit(url,name,data) {
	gridname=name;
	//--author：JueYue ---------date：20140227---------for：把URL转换成POST参数防止URL参数超出范围的问题
	var paramsData = data;
	if(!paramsData){
		paramsData = new Object();
		if (url.indexOf("&") != -1) {
			var str = url.substr(url.indexOf("&")+1);
			url = url.substr(0,url.indexOf("&"));
			var strs = str.split("&");
			for(var i = 0; i < strs.length; i ++) {
				paramsData[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
			}
		}      
	}
	//--author：JueYue ---------date：20140227---------for：把URL转换成POST参数防止URL参数超出范围的问题
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		data : paramsData,
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				var msg = d.msg;
				tip(msg);
				reloadTable();
			} else {
				tip(d.msg);
			}
		}
	});
	
	
}
/**
 * 退出确认框
 * 
 * @param url
 * @param content
 * @param index
 */
function exit(url, content) {
	$.dialog.setting.zIndex = getzIndex(true);
	$.dialog.confirm(content, function(){
		window.location = url;
	}, function(){
	});
}
/**
 * 模板页面ajax提交
 * 
 * @param url
 * @param gridname
 */
function ajaxdoSub(url, formname) {
	$('#' + formname).form('submit', {
		url : url,
		onSubmit : function() {
			editor.sync();
		},
		success : function(r) {
			tip('操作成功');
		}
	});
}
/**
 * ajax提交FORM
 * 
 * @param url
 * @param gridname
 */
function ajaxdoForm(url, formname) {
	$('#' + formname).form('submit', {
		url : url,
		onSubmit : function() {
		},
		success : function(r) {
			tip('操作成功');
		}
	});
}

function opensubwin(title, url, saveurl, okbutton, closebutton) {
	$.dialog({
		content: 'url:'+url,
		zIndex: getzIndex(),
		title : title,
		lock : true,
		opacity : 0.3,
		button : [ {
			name : okbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = frameElement.api.opener;// 来源页面
				$('#btn_sub', iframe.document).click();
				return false;
			}
		}, {
			name : closebutton,
			callback : function() {
			}
		} ]

	});
}

function openauditwin(title, url, saveurl, okbutton, backbutton, closebutton) {
	$.dialog({
		content: 'url:'+url,
		zIndex: getzIndex(),
		title : title,
		lock : true,
		opacity : 0.3,
		button : [ {
			name : okbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = $.dialog.open.origin;// 来源页面
				$('#btn_sub', iframe.document).click();
				return false;
			}
		}, {
			name : backbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = frameElement.api.opener;// 来源页面
				$('#formobj', iframe.document).form('submit', {
					url : saveurl + "&code=exit",
					onSubmit : function() {
						$('#code').val('exit');
					},
					success : function(r) {
						$.dialog.tips('操作成功', 2);
						win.location.reload();
					}
				});

			}
		}, {
			name : closebutton,
			callback : function() {
			}
		} ]

	});
}

/*获取Cookie值*/
function getCookie(name){
	 var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)"); 
	 return (arr=document.cookie.match(reg))?unescape(arr[2]):null;
}
/* 设置 cookie  */
function setCookie(name, value){
	var Days = 30; 
    var exp = new Date(); 
    exp.setTime(exp.getTime() + Days*24*60*60*1000); 
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path=/";
}

function createTabId(str){
　　　　var val="";
　　　　for(var i = 0; i < str.length; i++){
　　　　　　　　val += str.charCodeAt(i).toString(16);
　　　　}
　　　　return val;
　　}
// 添加标签
function addOneTab(subtitle, url, icon) {
	var indexStyle = getCookie("JEECGINDEXSTYLE");
	if(indexStyle=='sliding'||indexStyle=='bootstrap'){
		//shortcut和bootstrap风格的tab跳转改为直接跳转
		window.location.href=url;
	}else if(indexStyle=='acele'||indexStyle=='ace'){
		var id = "";
		//if(url.indexOf("=")!=-1){
		//	id = url.substring(url.indexOf("=")+1);
		//}else{
			id = createTabId(subtitle);
		//}
		window.top.addTabs({id:id,title:subtitle,close: true,url: url});
	}else if(indexStyle=='hplus'){
		var id = "";
		id = createTabId(subtitle);
		window.top.addTabs({id:id,title:subtitle,close: true,url: url});
		//update-begin-author:taoyan date:20171121 for:fineui-addTab--
	}else if(indexStyle=='fineui'){
		var id = "";
		id = createTabId(subtitle);
		window.top.addFineuiTab({id:id,title:subtitle,close: true,url: url});
		//update-end-author:taoyan date:20171121 for:fineui-addTab--
	}else{
		if (icon == '') {
			icon = 'icon folder';
		}
		window.top.$.messager.progress({
			text : '页面加载中....',
			interval : 300
		});
		window.top.$('#maintabs').tabs({
			onClose : function(subtitle, index) {
				window.top.$.messager.progress('close');
			}
		});
		if (window.top.$('#maintabs').tabs('exists', subtitle)) {
			window.top.$('#maintabs').tabs('select', subtitle);
			if (url.indexOf('isHref') != -1) {
				window.top.$('#maintabs').tabs('update', {
					tab : window.top.$('#maintabs').tabs('getSelected'),
					options : {
						title : subtitle,
						href:url,
						//content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
						closable : true,
						icon : icon
					}
				});
			}else {
				window.top.$('#maintabs').tabs('update', {
					tab : window.top.$('#maintabs').tabs('getSelected'),
					options : {
						title : subtitle,
						content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
						//content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
						closable : true,
						icon : icon
					}
				});
			}
		} else {
			if (url.indexOf('isHref') != -1) {
				window.top.$('#maintabs').tabs('add', {
					title : subtitle,
					href:url,
					closable : true,
					icon : icon
				});
			}else {
				window.top.$('#maintabs').tabs('add', {
					title : subtitle,
					content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					closable : true,
					icon : icon
				});
			}
		}
	}
}
// 关闭自身TAB刷新父TABgrid
function closetab(title) {
	//暂时先不刷新
	//window.top.document.getElementById('tabiframe').contentWindow.reloadTable();
	//window.top.document.getElementById('maintabs').contentWindow.reloadTable();
	window.top.$('#maintabs').tabs('close', title);
	//tip("添加成功");
}

//popup  
//object: this  name:需要选择的列表的字段  code:动态报表的code
function inputClick(obj,name,code) {
	 if(name==""||code==""){
		 alert("popup参数配置不全");
		 return;
	 }
	 //update-begin--Author:scott  --- Date:20170401 --- for：inputclick ajax请求追加根路径，防止深路径访问有问题----
	 var inputClickUrl = basePath + "/cgReportController.do?popup&id="+code;
	 //update-end--Author:scott  --- Date:20170401 --- for：inputclick ajax请求追加根路径，防止深路径访问有问题----
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+inputClickUrl,
				zIndex: getzIndex(),
				lock : true,
				title:"选择",
				width:800,
				height: 400,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
					    var str = "";
				    	$.each( selected, function(i, n){
					    	if (i==0)
					    	str+= n[name];
					    	else
				    		str+= ","+n[name];
				    	});
				    	$(obj).val("");
				    	//$('#myText').searchbox('setValue', str);
					    $(obj).val(str);
				    	return true;
				    }
					
			    },
			    cancelVal: '关闭',
			    cancel: true //为true等价于function(){}
			});
		}else{
			$.dialog({
				content: "url:"+inputClickUrl,
				zIndex: getzIndex(),
				lock : true,
				title:"选择",
				width:800,
				height: 400,
				parent:windowapi,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
					    var str = "";
				    	$.each( selected, function(i, n){
					    	if (i==0)
					    	str+= n[name];
					    	else
				    		str+= ","+n[name];
				    	});
				    	$(obj).val("");
				    	//$('#myText').searchbox('setValue', str);
					    $(obj).val(str);
				    	return true;
				    }
					
			    },
			    cancelVal: '关闭',
			    cancel: true //为true等价于function(){}
			});
		}
}

/*
	自定义url的弹出
	obj:要填充的控件,可以为多个，以逗号分隔
	name:列表中对应的字段,可以为多个，以逗号分隔（与obj要对应）
	url：弹出页面的Url
*/
function popClick(obj,name,url) {
	 $.dialog.setting.zIndex = getzIndex(true);
	var names = name.split(",");
	var objs = obj.split(",");
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				zIndex: getzIndex(),
				lock : true,
				title:"选择",
				width:700,
				height: 400,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
				    	for(var i1=0;i1<names.length;i1++){
						    var str = "";
					    	$.each( selected, function(i, n){
						    	if (i==0)
						    	str+= n[names[i1]];
						    	else{
									str+= ",";
									str+=n[names[i1]];
								}
					    	});
							if($("#"+objs[i1]).length>=1){
								$("#"+objs[i1]).val("");
								$("#"+objs[i1]).val(str);
							}else{
								$("input[name='"+objs[i1]+"']").val("");
								$("input[name='"+objs[i1]+"']").val(str);
							}
						 }
				    	return true;
				    }
					 
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			});
		}else{
			$.dialog({
				content: "url:"+url,
				zIndex: getzIndex(),
				lock : true,
				title:"选择",
				width:700,
				height: 400,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
				    	for(var i1=0;i1<names.length;i1++){
						    var str = "";
					    	$.each( selected, function(i, n){
						    	if (i==0)
						    	str+= n[names[i1]];
						    	else{
									str+= ",";
									str+=n[names[i1]];
								}
					    	});
					    	if($("#"+objs[i1]).length>=1){
								$("#"+objs[i1]).val("");
								$("#"+objs[i1]).val(str);
							}else{
								$("[name='"+objs[i1]+"']").val("");
								$("[name='"+objs[i1]+"']").val(str);
							}
						 }
				    	return true;
				    }
					
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			});
		}
}
/**
 * Jeecg Excel 导出
 * 代入查询条件
 */
function JeecgExcelExport(url,datagridId){
	var queryParams = $('#'+datagridId).bootstrapTable('getOptions').queryParams;
	$('#'+datagridId+'tb').find('*').each(function() {
	    queryParams[$(this).attr('name')] = $(this).val();
	});
	var params = '&';
	$.each(queryParams, function(key, val){
		params+='&'+key+'='+val;
	}); 
	var fields = '&field=';
	$.each($('#'+ datagridId).bootstrapTable('getOptions').columns[0], function(i, val){
		if(val.field != 'opt'){
			fields+=val.field+',';
		}
	});
    //update-begin--Author:dangzhenghui  Date:20170429 for：TASK #1869 【demo】jeecg excel导出 可以导出 指定行的数据
    var id='&id=';
    $.each($('#'+ datagridId).bootstrapTable('getSelections'), function(i, val){
        id+=val.id+",";
    });
	window.location.href = url+ encodeURI(fields+params+id);
    //update-end--Author:dangzhenghui  Date:20170429 for：TASK #1869 【demo】jeecg excel导出 可以导出 指定行的数据
}
/**
 * 自动完成的解析函数
 * @param data
 * @returns {Array}
 */
function jeecgAutoParse(data){
	var parsed = [];
    	$.each(data.rows,function(index,row){
    		parsed.push({data:row,result:row,value:row.id});
    	});
			return parsed;
}
//add--start--Author:xugj date:20160531 for: TASK #1089 【demo】针对jeecgdemo，实现一个新的页面方式
/**
 * 更新跳转新页面
 * @param title 编辑框标题 未实现标题改变
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function updateNotCreateWin(title,url, id,isRestful) {
	var rowsData = $('#'+id).bootstrapTable('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择编辑项目');
		return;
	}
	if (rowsData.length>1) {
		tip('请选择一条记录再编辑');
		return;
	}
	if(isRestful!='undefined'&&isRestful){
		url += '/'+rowsData[0].id;
	}else{
		url += '&id='+rowsData[0].id;
	}
	window.location.href=url
}
/**
 * 查看详情跳转新页面
 * @param title 编辑框标题 未实现标题改变
 * @param id//主键字段
 */
function viewNotCreateWin(title,url, id,isRestful)
{
	var rowsData = $('#'+id).bootstrapTable('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择查看项目');
		return;
	}
	if (rowsData.length>1) {
		tip('请选择一条记录再查看');
		return;
	}
	if(isRestful!='undefined'&&isRestful){
		url += '/'+rowsData[0].id;
	}else{
		url += '&id='+rowsData[0].id;
	}
	window.location.href=url
}
//add--end--Author:xugj date:20160531 for: TASK #1089 【demo】针对jeecgdemo，实现一个新的页面方式

//add--start--Author:gengjiajia date:20160802 for: TASK #1175 批量添加数据的时popup多值的传递
//popup  
//object: pobj当前操作的文本框. tablefield:对应字典TEXT,要从popup报表中获取的字段.inputnames:对应字典CODE,当前需要回填数据的文本框名称. pcode:动态报表的code
/**
 *   object: pobj当前操作的文本框.
 *   tablefield:对应字典TEXT,要从popup报表中获取的字段.
 *   inputnames:对应字典CODE,当前需要回填数据的文本框名称.
 *   pcode:动态报表的code
 */
function popupClick(pobj,tablefield,inputnames,pcode) {
	 if(inputnames==""||pcode==""){
		 alert("popup参数配置不全");
		 return;
	 }
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:cgReportController.do?popup&id="+pcode,
				zIndex: getzIndex(),
				lock : true,
				title:"选择",
				width:800,
				height: 400,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
				    	//对应数据库字段不为空的情况下,根据表单中字典TEXT的值来取popup的值
				    	if(tablefield != "" && tablefield != null){
					    	var fields = tablefield.split(",");
					    	var inputfield = inputnames.split(",");
					    	for(var i1=0;i1<fields.length;i1++){
							   var str = "";
						    	$.each( selected, function(i, n){ 
						    		if (i==0)
								    	str+= n[fields[i1]];
							    	else{
										str+= ",";
										str+=n[fields[i1]];
									}
								 });
						    	var inputname = $(pobj).attr("name"); 
						    	var inputs = inputname.split(".");
						    	//判断传入的this格式是否为 "AA[#index#].aa"的形式
						    	if(str.indexOf("undefined")==-1){
						    		if(inputs.length>1){
						    		﻿	//update--begin--author:scott date:20171031 for:TASK #2385 online和代码生成器 一对多行popup多字段赋值问题解决-----------
						    			var inpu = inputs[0]+"."+inputfield[i1];
						    			//update--end--author:scott date:20171031 for:TASK #2385 online和代码生成器 一对多行popup多字段赋值问题解决-----------
						    			$("input[name='"+inpu+"']").val(str);
						    		}else{
						    			$("input[name='"+inputfield[i1]+"']").val(str);
						    		}
						    	}else{
						    		if(inputs.length>1){
						    			//update--begin--author:scott date:20171031 for:TASK #2385 online和代码生成器 一对多行popup多字段赋值问题解决-----------
						    			var inpu = inputs[0]+"."+inputfield[i1];
						    			//update--end--author:scott date:20171031 for:TASK #2385 online和代码生成器 一对多行popup多字段赋值问题解决-----------
						    			$("input[name='"+inpu+"']").val("");
						    		}else{
						    			$("input[name='"+inputfield[i1]+"']").val("");
						    		}
						    	}
					    	}
				    	}else{
				    		//对应数据库字段为空的情况下并且字典CODE传入多个值时，根据表单中字典CODE的值从popup中来取值
				    		var inputfield = inputnames.split(",");
				    		if(inputfield.length>1){
				    			for(var i1=0;i1<inputfield.length;i1++){
									   var str = "";
								    	$.each( selected, function(i, n){ 
							    			if (i==0)
							    				str+= n[inputfield[i1]];
							    			else{
							    				str+= ",";
							    				str+=n[inputfield[i1]];
							    			}
										 });
								    	var inputname = $(pobj).attr("name"); 
								    	var inputs = inputname.split(".");
								    	if(str.indexOf("undefined")==-1){
								    		if(inputs.length>1){
								    			var inpu = inputs[i1]+"."+inputfield[i1];
								    			$("input[name='"+inpu+"']").val(str);
								    		}else{
								    			$("input[name='"+inputfield[i1]+"']").val(str);
								    		}
								    	}else{
								    		if(inputs.length>1){
								    			var inpu = inputs[i1]+"."+inputfield[i1];
								    			$("input[name='"+inpu+"']").val("");
								    		}else{
								    			$("input[name='"+inputfield[i1]+"']").val("");
								    		}
								    	}
							    	}
				    		}else{
				    			//对应数据库字段为空的情况下并且字典CODE传入一个值时，根据表单中字典TEXT的值从popup中来取值
				    			 var str = "";
						    	$.each( selected, function(i, n){
							    	if (i==0)
							    	str+= n[inputfield];
							    	else
						    		str+= ","+n[inputfield];
						    	});
						    	var inputname = $(pobj).attr("name"); 
						    	var inputs = inputname.split(".");
						    	if(str.indexOf("undefined")==-1){
						    		if(inputs.length>1){
						    			//update--begin--author:scott date:20171031 for:TASK #2385 online和代码生成器 一对多行popup多字段赋值问题解决-----------
						    			var inpu = inputs[0]+"."+inputfield[i1];
						    			//update--end--author:scott date:20171031 for:TASK #2385 online和代码生成器 一对多行popup多字段赋值问题解决-----------
						    			$("input[name='"+inpu+"']").val(str);
						    		}else{
						    			$("input[name='"+inputfield+"']").val(str);
						    		}
						    	}else{
						    		if(inputs.length>1){
						    			//update--begin--author:scott date:20171031 for:TASK #2385 online和代码生成器 一对多行popup多字段赋值问题解决-----------
						    			var inpu = inputs[0]+"."+inputfield[i1];
						    			//update--end--author:scott date:20171031 for:TASK #2385 online和代码生成器 一对多行popup多字段赋值问题解决-----------
						    			$("input[name='"+inpu+"']").val("");
						    		}else{
						    			$("input[name='"+inputfield+"']").val("");
						    		}
						    	}
				    		}
				    	}
				    	return true;
				    }
					
			    },
			    cancelVal: '关闭',
			    cancel: true // 为true等价于function(){}
			});
		}else{
			$.dialog({
				content: "url:cgReportController.do?popup&id="+pcode,
				zIndex: getzIndex(),
				lock : true,
				title:"选择",
				width:800,
				height: 400,
				parent:windowapi,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
				    	//对应数据库字段不为空的情况下,根据表单中字典TEXT的值来取popup的值
				    	if(tablefield != "" && tablefield != null){
					    	var fields = tablefield.split(",");
					    	var inputfield = inputnames.split(",");
					    	for(var i1=0;i1<fields.length;i1++){
							   var str = "";
						    	$.each( selected, function(i, n){ 
						    		if (i==0)
								    	str+= n[fields[i1]];
							    	else{
										str+= ",";
										str+=n[fields[i1]];
									}
								 });
						    	var inputname = $(pobj).attr("name"); 
						    	var inputs = inputname.split(".");
						    	//判断传入的this格式是否为 "AA[#index#].aa"的形式
						    	if(str.indexOf("undefined")==-1){
						    		if(inputs.length>1){
						    			var inpu = inputs[0]+"."+inputfield[i1];
						    			$("input[name='"+inpu+"']").val(str);
						    		}else{
						    			$("input[name='"+inputfield[i1]+"']").val(str);
						    		}
						    	}else{
						    		if(inputs.length>1){
						    			var inpu = inputs[0]+"."+inputfield[i1];
						    			$("input[name='"+inpu+"']").val("");
						    		}else{
						    			$("input[name='"+inputfield[i1]+"']").val("");
						    		}
						    	}
					    	}
				    	}else{
				    		//对应数据库字段为空的情况下并且字典CODE传入多个值时，根据表单中字典CODE的值从popup中来取值
				    		var inputfield = inputnames.split(",");
				    		if(inputfield.length>1){
				    			for(var i1=0;i1<inputfield.length;i1++){
									   var str = "";
								    	$.each( selected, function(i, n){ 
							    			if (i==0)
							    				str+= n[inputfield[i1]];
							    			else{
							    				str+= ",";
							    				str+=n[inputfield[i1]];
							    			}
										 });
								    	var inputname = $(pobj).attr("name"); 
								    	var inputs = inputname.split(".");
								    	if(str.indexOf("undefined")==-1){
								    		if(inputs.length>1){
								    			var inpu = inputs[i1]+"."+inputfield[i1];
								    			$("input[name='"+inpu+"']").val(str);
								    		}else{
								    			$("input[name='"+inputfield[i1]+"']").val(str);
								    		}
								    	}else{
								    		if(inputs.length>1){
								    			var inpu = inputs[i1]+"."+inputfield[i1];
								    			$("input[name='"+inpu+"']").val("");
								    		}else{
								    			$("input[name='"+inputfield[i1]+"']").val("");
								    		}
								    	}
							    	}
				    		}else{
				    			//对应数据库字段为空的情况下并且字典CODE传入一个值时，根据表单中字典TEXT的值从popup中来取值
				    			 var str = "";
						    	$.each( selected, function(i, n){
							    	if (i==0)
							    	str+= n[inputfield];
							    	else
						    		str+= ","+n[inputfield];
						    	});
						    	var inputname = $(pobj).attr("name"); 
						    	var inputs = inputname.split(".");
						    	if(str.indexOf("undefined")==-1){
						    		if(inputs.length>1){
						    			var inpu = inputs[i1]+"."+inputfield[i1];
						    			$("input[name='"+inpu+"']").val(str);
						    		}else{
						    			$("input[name='"+inputfield+"']").val(str);
						    		}
						    	}else{
						    		if(inputs.length>1){
						    			var inpu = inputs[i1]+"."+inputfield[i1];
						    			$("input[name='"+inpu+"']").val("");
						    		}else{
						    			$("input[name='"+inputfield+"']").val("");
						    		}
						    	}
				    		}
				    	}
				    	return true;
				    }
					
			    },
			    cancelVal: '关闭',
			    cancel: true // 为true等价于function(){}
			});
		}
	}
//add--end--Author:gengjiajia date:20160802 for: TASK #1175 批量添加数据的时popup多值的传递

//update--begin--author:zhangjiaqiang date:20170527 for:增加鼠标放在图片上方，显示图片大图
/*
 * 鼠标放在图片上方，显示大图
 */
var bigImgIndex = null;
function tipImg(obj){
	try{
		var navigatorName = "Microsoft Internet Explorer"; 
		if( navigator.appName != navigatorName ){ 
			if(obj.nodeName == 'IMG'){
				var e = window.event;
				var x = e.clientX+document.body.scrollLeft + document.documentElement.scrollLeft
				var y = e.clientY+document.body.scrollTop + document.documentElement.scrollTop 
				var src = obj.src;
				var width = obj.naturalWidth;
				var height = obj.naturalHeight;
				bigImgIndex = layer.open({
					content:[src,'no'],
					type:2,
					offset:[y+"px",x+"px"],
					title:false,
					area:[width+"px",height+"px"],
					shade:0,
					closeBtn:0
				});
			}
		}
	}catch(e){
	}
	
}

function moveTipImg(){
	try{
		if(bigImgIndex != null){
			layer.close(bigImgIndex);
		}
	}catch(e){
		
	}
}
//update--end--author:zhangjiaqiang date:20170527 for:增加鼠标放在图片上方，显示图片大图
//<!-- update-begin-author:zhangjiaqiang date:20170815 for:TASK #2274 【online】Online 表单支持树控件 -->
function treeFormater(value,row,index){
	return getTreeResult(value);
}

function getTreeResult(value){
	if(value != null && value != ''){
		if(value.indexOf(",") > 0){
			var valueArray = value.split(",");
			var resultValue = "";
			for(var i = 0; i < valueArray.length; i++){
				var tempValue = getResult(valueArray[i]);
				if(resultValue != ""){
					resultValue += ","+ tempValue;
				}else{
					resultValue = tempValue;
				}
			}
			return resultValue;
		}else{
			return getResult(value);
		}
		
	}else{
		return value;
	}
}

/**
 * 获取类型编码对应的值
 * @param selfCode
 * @returns
 */
function getResult(selfCode){
	var result = $.ajax({
		url:'categoryController.do?tree',
		type:'POST',
		dataType:'JSON',
		data:{
			selfCode:selfCode
		},
		async:false
	});
	var responseText = result.responseText;
	if(typeof responseText == 'string'){
		responseText = JSON.parse(responseText);
	}
	if(responseText.length != undefined && responseText.length > 0 && responseText[0].text != undefined){
		return responseText[0].text;
	}
	else{
		return selfCode;
	}
}

//<!-- update-begin-author:zhangjiaqiang date:20170815 for:TASK #2274 【online】Online 表单支持树控件 -->

//update--begin--author:jiaqiankun---------- date:20180703 ----------- for: 代码生成器公共方法抽取-----------------------------------
//bootstrap列表图片格式化
function btListImgFormatter(value, row, index) {
	return listFileImgFormat(value, "image");
}
//bootstrap列表文件格式化
function btListFileFormatter(value, row, index) {
	return listFileImgFormat(value);
}
//列表文件图片 列格式化方法
function listFileImgFormat(value,type){
	var href='';
	if(value==null || value.length==0){
		return href;
	}
	var value1 = "img/server/"+value;
	if("image"==type){
		href+="<img src='"+value1+"' width=30 height=30  onmouseover='tipImg(this)' onmouseout='moveTipImg()' style='vertical-align:middle'/>";
	}else{
		if(value.indexOf(".jpg")>-1 || value.indexOf(".gif")>-1 || value.indexOf(".png")>-1){
			href+="<img src='"+value1+"' onmouseover='tipImg(this)' onmouseout='moveTipImg()' width=30 height=30 style='vertical-align:middle'/>";
		}else{
			var value2 = "img/server/"+value+"?down=true";
			href+="<a href='"+value2+"' class='ace_button' style='text-decoration:none;' target=_blank><u><i class='fa fa-download'></i>点击下载</u></a>";
		}
	}
	return href;
}
//update--end--author:jiaqiankun---------- date:20180703 ----------- for: 代码生成器公共方法抽取-----------------------------------

//update-begin-author：zhoujf date:20180713 for:TASK #2809 【bug】网友反馈问题，字典性能问题
function loadAjaxDict(){
	$("body").find("span[name='ajaxDict']").each(function(i){
		var $this = $(this);
		var dictionary = $this.attr('dictionary');
		var dictCondition = $this.attr('dictCondition');
		var popup = $this.attr('popup');
		var value = $this.attr('value');
		$.ajax({
			url : "commonController.do?getDictInfo",
			type : 'post',
			data : {
				dictionary:dictionary,
				dictCondition:dictCondition,
				popup:popup,
				value:value
			},
			dataType:"json",
			async : true,
			cache : false,
			success : function(data) {
				$this.html(data.obj);
			}
		});
		
	});
}
//update-end-author：zhoujf date:20180713 for:TASK #2809 【bug】网友反馈问题，字典性能问题