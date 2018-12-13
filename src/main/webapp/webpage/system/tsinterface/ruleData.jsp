<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>操作信息</title>
<t:base type="jquery,easyui,tools"></t:base>
 <script type="text/javascript">
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="interfaceController.do?saverule">
	<input name="id" type="hidden" value="${operation.id}">
	<fieldset class="step">
        <div class="form">
            <label class="Validform_label"> 接口权限规则名称: </label>
            <input name="ruleName" class="inputxt" value="${operation.ruleName}" datatype="s2-20" style="width: 280px;"/>
            <span class="Validform_checktip"> <t:mutiLang langKey="operatename.rang2to20"/></span>
        </div>
      <div class="form" <c:if test="${operation.ruleConditions=='USE_SQL_RULES'}">style="display:none;" </c:if>>
            <label class="Validform_label"> 接口权限规则字段: </label>
            <input name="ruleColumn" class="inputxt" value="${operation.ruleColumn}" style="width: 280px;" />
        </div>
        <div class="form">
            <label class="Validform_label"> 接口权限条件规则: </label>
            <t:dictSelect id="ruleConditions" field="ruleConditions" typeGroupCode="rulecon"  datatype="*"  hasLabel="false" defaultVal="${operation.ruleConditions}"></t:dictSelect>
        </div>
           <input name="tSInterface.id" value="${interfaceId}" type="hidden"  >
         <div class="form">
            <label class="Validform_label"> 接口权限规则值: </label>
            <input name="ruleValue" class="inputxt" value="${operation.ruleValue}" style="width: 390px;" datatype="*"/>
        </div>
	</fieldset>
</t:formvalid>
<script>
$("#ruleConditions").change(function(){
	if("USE_SQL_RULES" == $(this).val()){
		var inp = $("input[name='ruleColumn']");
		inp.parent("div").fadeOut();
		inp.val("");
	}else{
		$("input[name='ruleColumn']").parent("div").show();
	}
});
</script>
</body>
</html>
