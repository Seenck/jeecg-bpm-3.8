<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>请假单</title>
  <t:base type="jquery,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="div" dialog="true" action="busController.do?saveLeave">
   <input type="hidden" name="id" id="id" value="${leave.id}">
   <input name="code" id="code" type="hidden" value="${leave.TSPrjstatus.code}" />
   <input name="taskId" id="taskId" type="hidden" value="${taskId}" />
   <input name="keys" id="keys" type="hidden" />
   <input name="values" id="values" type="hidden" />
   <input name="types" id="types" type="hidden" />
   <fieldset class="step">
    <div class="form">
     <label class="Validform_label">
     	 请假类型：
     </label>
     <select id="leavetype" name="TSType.id">
     <c:forEach items="${typeList}" var="type">
      <option value="${type.id}">${type.typename}</option>
     </c:forEach>
     </select>
     <span class="Validform_checktip"></span>
    </div>
    <div class="form">
    <label class="Validform_label">
      	开始时间：
     </label>
      <input name="begintime" id="begintime"  class="Wdate" value="<fmt:formatDate value='${leave.begintime}' type="date" pattern="yyyy-MM-dd"/>" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endtime\')||\'2020-10-01\'}'})"/> 
     <span class="Validform_checktip"></span>
    </div>
    <div class="form">
     <label class="Validform_label">
      	结束时间：
     </label>
      <input name="endtime" id="endtime" value="<fmt:formatDate value='${leave.endtime}' type="date" pattern="yyyy-MM-dd"/>" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'begintime\')}',maxDate:'2020-10-01'})"/>
    </div>
    <div class="form">
    <label class="Validform_label">
      	请假原因：
     </label>
     <textarea name="reason" datatype="*" style="resize: none;" rows="4" cols="30">${leave.reason}</textarea>
     <span class="Validform_checktip"></span>
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>
