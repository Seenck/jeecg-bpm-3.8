<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
 <head>
  <title><t:mutiLang langKey="common.process.handle"/></title>
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
     	<t:mutiLang langKey="common.approval.opinion"/>:
     </label>
     <textarea rows="3" cols="20"  vartype="S" name="reason" id="reason" style="margin: 0px; width: 334px; height: 85px;"/></textarea>
    </div>
    <!-- 下面两个是测试服务上报流程用得  请假流程等流程不用理会 -->
    <div class="form" style="display:none">
     <label class="Validform_label">
    	 <t:mutiLang langKey="common.process.check"/>:
     </label>
     <input name="review" id="review" vartype="B" type="text" value="true" />
    </div>
    <div class="form" style="display:none">
   <label class="Validform_label">
     	<t:mutiLang langKey="common.process.approval"/>:
     </label>
     <input name="approval" id="approval" vartype="B" type="text" value="true" />
    </div>
    
   </fieldset>
  </t:formvalid>
 </body>
</html>