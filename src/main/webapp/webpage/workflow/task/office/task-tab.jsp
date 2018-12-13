<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:tabs  id="tt" iframe="false" tabPosition="top">
	<t:tab  href="taskController.do?goTaskForm&taskId=${taskId }" icon="fa fa-building" title="审批页面" id="formPage"></t:tab>
	<t:tab  href="taskController.do?goTaskOperate&taskId=${taskId }" icon="fa fa-eye-slash" title="流转明细" id="taskOperate"></t:tab>
	<t:tab  href="activitiController.do?viewProcessInstanceHistory&processInstanceId=${processInstanceId }" icon="fa fa-sitemap" title="流程图示" id="processPicture"></t:tab>
</t:tabs>
<script type="text/javascript">
		function callbackTable(msg){
			//alert("callbackTable form:"+msg);
			W.tip(msg);
			W.reloadTable();
			windowapi.close();
		}
</script>