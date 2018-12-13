<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
<t:datagrid name="fList" title="文件下载" actionUrl="jeecgFormDemoController.do?documentList&typecode=files" idField="id" fit="true" sortName="createdate" sortOrder="desc">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="标题" field="documentTitle" width="20" query="true"></t:dgCol>
	<!-- update-begin--Author:LiShaoQing  Date:20170804 for：添加日期格式化 -->
	<t:dgCol title="创建时间" field="createdate" formatter="yyyy-MM-dd hh:mm:ss"  width="20"></t:dgCol>
	<!-- update-end--Author:LiShaoQing  Date:20170804 for：添加日期格式化 -->
	<t:dgCol title="类名" field="subclassname" hidden="true"></t:dgCol>
	<t:dgCol title="操作" field="opt"  width="40"></t:dgCol>
	<%--//update-begin--Author:dangzhenghui  Date:20170721 for：TASK #2221 【UI例子】exp显示表达式demo----------------------%>
	<t:dgDefOpt url="commonController.do?viewFile&fileid={id}&subclassname={subclassname}" urlclass="ace_button"  urlfont="fa-trash-o" title="下载"></t:dgDefOpt>
	<!-- update-begin--Author:LiShaoQing  Date:20170804 for：[TASK#2232]添加文档预览功能 -->
	<t:dgOpenOpt width="700" height="600" url="commonController.do?openViewFile&fileid={id}&subclassname={subclassname}" urlclass="ace_button"  urlfont="fa-trash-o" title="预览"></t:dgOpenOpt>
	<!-- update-end--Author:LiShaoQing  Date:20170804 for：[TASK#2232]添加文档预览功能 -->
	<%--//update-begin--Author:dangzhenghui  Date:20170721 for：TASK #2221 【UI例子】exp显示表达式demo----------------------%>
	<t:dgDelOpt url="jeecgFormDemoController.do?delDocument&id={id}" title="删除" urlclass="ace_button"  urlfont="fa-trash-o"></t:dgDelOpt>
	<t:dgToolBar title="文件录入" icon="icon-add" funname="add" url="jeecgFormDemoController.do?addFiles"></t:dgToolBar>
	<t:dgToolBar title="通用文件录入" icon="icon-add" funname="commmonUpload"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" funname="update" url="jeecgFormDemoController.do?editFiles"></t:dgToolBar>
</t:datagrid>
<script type="text/javascript">
function commmonUpload(){
	createwindow("通用上传", "jeecgFormDemoController.do?commonUpload","700px",'350px');
}
</script>
</div>
</div>
