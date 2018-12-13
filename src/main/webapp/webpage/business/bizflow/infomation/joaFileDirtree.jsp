<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>我的文件库</title>
<t:base type="jquery,easyui,tools"></t:base>
<link rel="stylesheet" href="plug-in/ztree/css/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="plug-in/layer/layer.js"></script>
<link rel="stylesheet" href="plug-in/ztree/ext/custom.css" type="text/css"></link>
</head>
<body>
<div id="ko" class="easyui-layout" fit="true" scroll="no">
	<div data-options="region:'west',title:'我的文件',split:true" style="width:200px;overflow: auto;">
		<div id="fileDir" class="ztree"></div>
	</div>

	<div data-options="region:'center',title:'',iconCls:'icon-save',tools:'#tt'" style="overflow-y:hidden">
		<!-- <div style="height:30px;line-height:30px;background:#f9f9f9;vretical-align:middle">
			<span><i class="cipan"></i>我的文件库</span>
		</div> -->
		<iframe id="fileList" scrolling="no" frameborder="0" src="joaFileInfoController.do?fileList&select=${select}" width="100%" height="100%"></iframe>
	</div>
</div>
<div style="display:none">
	<div id="fileTreeMenu" class="easyui-menu" style="width:80px;">
		<div data-options="iconCls:'icon-add'" onclick="addFileDir()">新建</div>
		<div data-options="iconCls:'icon-edit'" onclick="renameFileDir()">重命名</div>
		<div data-options="iconCls:'icon-remove'" onclick="deleteFileDir()">删除</div>
		<!-- <div class="menu-sep"></div> -->
	</div>
	
	<div id="tt">
		<a href="#" class="icon-add" onclick="javascript:alert('add')"></a>
		<a href="#" class="icon-edit" onclick="javascript:alert('edit')"></a>
	</div>
</div>


<script type="text/javascript">
var currFileDirId;//当前选中的文件目录id
$(function(){
	//document.getElementById('fileList').style.height = $(window).height()-35+"px";
	layer.config({extend: 'extend/layer.ext.js'});
	//加载树
	var zTreeObj ;
	var url = "joaFileInfoController.do?getFileDirTree";
	var select = "${select}";
	var zTreeOnClick = function(event, treeId, treeNode) {
		var dirId = treeNode.id;
		var iframeSrc = "joaFileInfoController.do?fileList&dirId="+dirId;
		if(!!select){
			iframeSrc +="&select=1";
		}
		document.getElementById('fileList').src=iframeSrc;
	};
	var zTreeOnRightClick = function(e, treeId, treeNode){
		currFileDirId = treeNode.id;
		var h = $(window).height();
		var w = $(window).width();
		var menuWidth = 120;
		var menuHeight = 75;
		var x = e.pageX, y = e.pageY;
		if (e.pageY + menuHeight > h) {
			y = e.pageY - menuHeight;
		}
		if (e.pageX + menuWidth > w) {
			x = e.pageX - menuWidth;
		}
		$('#fileTreeMenu').menu('show', {
			left : x,
			top : y
		});
	}
	var setting = {
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "parent_id",
				}
			},
			async: {
				enable: true,
				url: url,//异步加载时的请求地址
				autoParam: ["id"],//提交参数
				type: 'get',
			    dataType: 'json'
			},
			view:{
				nameIsHTML: true,
				selectedMulti: false,
				showIconFont:true,
			    showIcon: true,
			    showLine:true
			},
			check: {
				enable: false,
			},
			callback:{
				beforeClick: null,
				onClick: zTreeOnClick,
				onRightClick: zTreeOnRightClick,
				beforeDrop: null,
				onDrop: null,
				onAsyncSuccess: null
			}
		};
	zTreeObj = $.fn.zTree.init($("#fileDir"), setting);
});
function addFileDir(){
	layer.prompt({title: '请输入新建目录的名称...并确认', formType:3}, function(content, index){
		ajaxFileDirOperat(0,index,content);
	});
}
function renameFileDir(){
	layer.prompt({title: '请输入新的名称...并确认', formType:3}, function(content, index){
		ajaxFileDirOperat(1,index,content);
	});
}
function deleteFileDir(){
	layer.confirm('确认删除该目录及其以下文件吗？', {
	  btn: ['确认','取消']
	}, function(index){
		ajaxFileDirOperat(2,index);
	}, function(index){
		layer.close(index);
	});
}
/**
 * opt 操作类型0-add;1-rename;2-del
 * id 目录id
 */
function ajaxFileDirOperat(opt,index,content){
	var url = "joaFileInfoController.do?fileDirOperator";
	$.ajax({
		url:url,
		type:"POST",
		data:{id:currFileDirId,opt:opt,content:content},
		dataType:"JSON",
		success:function(){
			refreshTree(opt);
			layer.close(index);
		},
		error:function(e){
			console.log("reeor:"+e);
		}
	})
}
 
 function refreshTree(opt){
	 var treety = $.fn.zTree.getZTreeObj("fileDir");
	 var node = treety.getNodeByParam("id",currFileDirId, null);
	 if(node==null){
		 return ;
	 }
	 if(opt==0){
		 var isParent = node.isParent;
		 if(!isParent){
			 node.isParent = true;
			 treety.updateNode(node);
		 }
		 treety.reAsyncChildNodes(node, "refresh");
	 }else if(opt==1||opt==2){
		 var currParentTId = node.parentTId;//
		 var parentNode = treety.getNodeByTId(currParentTId);
		 treety.reAsyncChildNodes(parentNode, "refresh");
		 if(opt==2){
			$("#"+currParentTId).children("a[treenode_a]").click();
		 }
	 }
 }
 
 function getSelectFiles(){
	 return document.getElementById('fileList').contentWindow.getSelectFiles();
 }
</script>
</body>
</html>
