<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><t:mutiLang langKey="common.process.params" /></title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" layout="div" action="processController.do?saveNode">
	<input name="id" type="hidden" value="${processnode.id}">
	<input name="TPProcess.id" type="hidden" value="${processid}">
	<fieldset class="step">
	<div class="form"><label class="Validform_label"> <t:mutiLang langKey="common.node.name" />: </label> <input name="processnodename" value="${processnode.processnodename }" class="inputxt" datatype="s1-50"> <span class="Validform_checktip">节点名称范围在1~50位字符<%-- <t:mutiLang langKey="nodename.rang3to50" /> --%></span></div>
	<div class="form"><label class="Validform_label"> <t:mutiLang langKey="common.node.code" />: </label> <input name="processnodecode" value="${processnode.processnodecode}" class="inputxt"></div>
	<div class="form"><label class="Validform_label"> <t:mutiLang langKey="common.node.form_pc" />: </label> <input name="modelandview" value="${processnode.modelandview}" class="inputxt" style="width: 400px"></div>
	<div class="form"><label class="Validform_label"> <t:mutiLang langKey="common.node.form_mobile" />: </label> <input name="modelandviewMobile" value="${processnode.modelandviewMobile}" class="inputxt" style="width: 400px"></div>
	<div class="form" style="display:table;">
	    <label class="Validform_label"> <t:mutiLang langKey="common.node.timeout" />: </label> 
	    <input name="nodeTimeout" value="${processnode.nodeTimeout}" datatype="n1-11" ignore="ignore" class="inputxt" style="float:left;width: 357px"/>
	    <span id="groupspan" class="groupspan"><t:mutiLang langKey="common.node.timeout.unit.hours" /></span>
	</div>
	<div class="form"><label class="Validform_label"> <t:mutiLang langKey="common.node.bpmstatus" />: </label> 
		<t:dictSelect id="nodeBpmStatus" field="nodeBpmStatus" typeGroupCode="bpm_status" hasLabel="false" defaultVal="${processnode.nodeBpmStatus}" ></t:dictSelect>  &nbsp; 默认为空，流程任务节点执行后，流程状态变更值
	</div>
	
	<%--  表单不再使用，全部采用链接的方式配置
    <div class="form">
     <label class="Validform_label">
      内部表单:
     </label>
     <select name="fromid" style="width:140px">
      <option value="0">-请选择内部表单-</option>
      <c:forEach items="${formList }" var="form">
       <option value="${form.id }" <c:if test="${form.id==processnode.TPForm.id}">selected="selected"</c:if>>
        ${form.formname }
       </option>
      </c:forEach>
     </select>
    </div>
     --%></fieldset>
</t:formvalid>
<script type="text/javascript">
var indexStyle = getCookie("JEECGINDEXSTYLE");
if(indexStyle=='shortcut'||indexStyle=='default'||indexStyle=='sliding'){
	$("#groupspan").css({"padding":"3px 10px","height":"14px","line-height":"14px","border":"1px solid #a5aeb6"});
}
</script>
</body>
</html>
