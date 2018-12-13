<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	$(function(){
		$('#operationListpanel').panel("refresh", "processController.do?nodeOperation&nodeId=${nodeId}");
	});
	
</script>
<div class="easyui-layout" fit="true">
	<div region="center" split="true">
		<div class="easyui-layout" fit="true">
			<div region="center" split="true">
				<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="operationListpanel"></div>
			</div>
		</div>
	</div>
	
</div>
