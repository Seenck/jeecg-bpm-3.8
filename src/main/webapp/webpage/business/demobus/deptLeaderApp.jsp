<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
 <head>
  <title>流程办理</title>
  <t:base type="jquery,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" action="activitiController.do?processComplete" layout="div" dialog="true">
   <input name="taskId" id="taskId" type="hidden" value="${taskId}" />
   <input name="keys" id="keys" type="hidden" />
   <input name="values" id="values" type="hidden" />
   <input name="types" id="types" type="hidden" />
   <fieldset class="step">
    <div class="form">
     <label class="Validform_label">
     	审批意见:
     </label>
     <textarea rows="3" cols="20" vartype="S" name="reason" id="reason" /></textarea>
    </div>
     <div class="form">
    <label class="Validform_label">
     	借款金额:
     </label>
    ${tbBusinesstrip.bustripmoney}
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>
