<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:forEach items="${operationList}" var="operation">
	<c:if test="${fn:contains(operationcodes, operation.id)}">
		<%-- <span class="icon ${operation.TSIcon.iconClas}">&nbsp;</span> --%>
		<input style="width: 20px;" type="checkbox" name="operationCheckbox" value="${operation.id}" checked="checked" />${operation.operationname}
	 </c:if>
	<c:if test="${!fn:contains(operationcodes, operation.id)}">
		<!-- <span class="icon group_add">&nbsp;</span> -->
		<input style="width: 20px;" type="checkbox" name="operationCheckbox" value="${operation.id}" />${operation.operationname}
	 </c:if>
	<br>
</c:forEach>
<script type="text/javascript">
function submitNodeOperation() {
	var functionId = "${functionId}";
	var nodeId = $("#nodeId").val();
	var operationcodes = "";
	$("input[name='operationCheckbox']").each(function(i){
		   if(this.checked){
			   operationcodes+=this.value+",";
		   }
	 });
	operationcodes=escape(operationcodes); 
	doSubmit("processController.do?updateNodeOperation&functionId=" + functionId + "&nodeId=" + nodeId+"&operationcodes="+operationcodes);
}
</script>
