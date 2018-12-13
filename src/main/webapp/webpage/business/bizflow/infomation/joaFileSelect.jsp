<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/ztree/ext/custom.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/hplus/js/plugins/layer/layer.min.js"></script>
<div class="easyui-layout" fit="true">
  <div data-options="region:'north'" style="width:200px;overflow:hidden;">
		<div style="height:30px;line-height:30px;background:#f9f9f9;vretical-align:middle">
			<span><i class="cipan"></i>我的文件库</span>
		</div>
  </div>
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid fit="true" name="joaFileInfoList" checkbox="true" pagination="true" fitColumns="true" title="" sortOrder="asc" sortName="ext" actionUrl="joaFileInfoController.do?datagrid&dirId=${dirId}" idField="id" onDblClick="datagridDbclick" queryMode="group">
   <t:dgCol title="主键"  field="id" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="流程状态"  field="bpmStatus"  hidden="true"  queryMode="single"  dictionary="bpm_status"  width="120"></t:dgCol>
   <t:dgCol title="文件名称"  field="name" extendParams="editor:'text'" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="扩展名"  field="ext" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="文件格式"  field="type"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="文件大小/byte"  field="size"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="业务类型"  field="business"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="路径"  field="path" hidden="true" ></t:dgCol>
   <t:dgCol title="操作" field="opt" ></t:dgCol>
   <t:dgFunOpt exp="ext#ne#0" funname="goviewinfo(id,ext)" title="预览" urlclass="ace_button"  urlfont="fa-eye"></t:dgFunOpt>
   <t:dgFunOpt exp="ext#ne#0" funname="godownload(id)" title="下载" urlclass="ace_button"  urlfont="fa-download"></t:dgFunOpt>
   <t:dgToolBar title="上传" icon="icon-putout" funname="goUpload"></t:dgToolBar>
   <t:dgToolBar title="保存修改" icon="icon-save" funname="saveData"></t:dgToolBar>
  </t:datagrid>
  <input type="hidden" id = "currentDirCode" value = "${dirId}"/>
  </div>
 </div>
 <script type="text/javascript">
 function goUpload(){
	var currentDirCode = $("#currentDirCode").val();
    top.layer.open({
      title: '文件上传',
      type: 2,
	  shadeClose: true,
	  closeBtn:1,
	  shade: 0.5,
	  area: ['700px', '360px'],
	  content: '${webRoot}/joaFileInfoController.do?goPlupload&dirId='+currentDirCode,
      cancel: function(index) {
      	layer.close(index);
      },
      btn: ['确认'],
      yes: function(index,layero) {
    	  //var frameId=layero[0].getElementsByTagName("iframe")[0].id;
    	  var flag = layero[0].getElementsByTagName("iframe")[0].contentWindow.saveFileInfo();
    	  if(!!flag){
    		  $('#joaFileInfoList').datagrid('reload');
    		  top.layer.close(index);
    	  }
      },
    });
 }
 function godownload(id){
	 location.href = "${webRoot}/joaFileInfoController.do?downFile&id="+id;
 }
 function goviewinfo(id,ext){
	 if(ext == '0'){
		 tip('文件夹不支持预览');
		 return false;
	 }
	 var url = "joaFileInfoController.do?openViewFile&id="+id;
	 //openwindow('预览',url,'joaFileInfoList',700,500);
	 top.layer.open({
	      title: '预览',
	      type: 2,
		  shadeClose: true,
		  closeBtn:1,
		  shade: 0.5,
		  area: ['700px', '500px'],
		  content: url,
	      cancel: function(index) {
	      	layer.close(index);
	      }
	    });
 }
 //行编辑修改文件名称---------------------------------------------
 var updateFileName = function(id,name){this.id = id;this.name = name;}
 function datagridDbclick(index,row){
	  if(row.ext == '0'){
		  var curDirId = row.id;
		  $("#joaFileInfoList").datagrid("load",{curDirId :curDirId});
		  $("#currentDirCode").val(curDirId);
	  }else{
		  $("#joaFileInfoList").datagrid('beginEdit', index);
	  }
 }
 function saveData(){
	var addurl = "joaFileInfoController.do?updateFileName", gname = "joaFileInfoList";
	var  editIndex = $('#'+gname).datagrid('getRows').length-1;
	for(var i=0;i<=editIndex;i++){
		$('#'+gname).datagrid('endEdit', i);
	}
	var rows=$('#'+gname).datagrid("getChanges","updated");
	if(rows.length<=0){
		tip("没有需要保存的数据！")
		return false;
	}
	var result=[];
	for(var i=0;i<rows.length;i++){
		var temp = new updateFileName(rows[i].id,rows[i].name);
		result.push(temp);
	}
	$.ajax({
		url:addurl,
		type:"post",
		data:{"updatenames":JSON.stringify(result)},
		dataType:"json",
		success:function(data){
			tip(data.msg);
			if(data.success){
				$('#'+gname).datagrid('reload');    
			}
		}
	})
 }
 //行编辑修改文件名称---------------------------------------------
 function getSelectFiles(){
	 var arr = [];
	 var rows = $("#joaFileInfoList").datagrid('getSelections');
	 if (rows.length > 0) {
		 for ( var i = 0; i < rows.length; i++) {
			var ext = rows[i].ext;
			if(ext !=0){
				arr.push({id:rows[i].id,name:rows[i].name,type:rows[i].type,ext:ext});
			}
		 }
	 }
	 return arr;
 }
 </script>