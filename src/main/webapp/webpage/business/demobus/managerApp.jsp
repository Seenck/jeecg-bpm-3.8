<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
 <head>
  <title>总经理审批</title>
  <t:base type="jquery,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" action="activitiController.do?processComplete" layout="div" dialog="true">
   <input name="taskId" id="taskId" type="hidden" value="${taskId}" />
   <input name="bormoney" id="bormoney" type="hidden" vartype="B" value="${bormoney}">
   <input name="keys" id="keys" type="hidden" />
   <input name="values" id="values" type="hidden" />
   <input name="types" id="types" type="hidden" />
   <fieldset class="step">
    <div class="form">
     <label class="form">
      	出差申请人:
     </label>
     ${tbBusinesstrip.TSUser.realName}
    </div>
    <div class="form">
     <label class="form">
      	出差地点:
     </label>
     ${tbBusinesstrip.bustriplocale}
    </div>
    <div class="form">
     <label class="form">
      	开始时间:
     </label>
     ${tbBusinesstrip.begintime}
    </div>
    <div class="form">
     <label class="form">
      	结束时间:
     </label>
     ${tbBusinesstrip.begintime}
    </div>
    <div class="form">
     <label class="form">
     	 审批意见:
     </label>
     <textarea rows="3" cols="20" vartype="S" name="reason" id="reason" /></textarea>
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>
