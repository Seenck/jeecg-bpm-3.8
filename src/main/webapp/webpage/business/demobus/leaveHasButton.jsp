<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>请假单</title>
  <t:base type="jquery,tools,DatePicker"></t:base>
    <link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
    <script type="text/javascript">
        function saveNoteInfo() {
            $('#btn_sub').click();
            alert('业务单据提交成功');
        }
    </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="div" dialog="true" action="busController.do?updateLeave">
  <input type="hidden" name="id" id="id" value="${leave.id}">
   <input name="code" id="code" type="hidden" value="${leave.TSPrjstatus.code}" />
   <input name="taskId" id="taskId" type="hidden" value="${taskId}" />
   <fieldset class="step">
    <div class="form">
     <label class="Validform_label">
     	 请假类型：
     </label>
     <select id="leavetype" name="TSType.id">
     <c:forEach items="${typeList}" var="type">
      <option value="${type.id}" <c:if test="${leave.begintime==type.id}">selected="selected"</c:if>>${type.typename}</option>
     </c:forEach>
     </select>
     <span class="Validform_checktip"></span>
    </div>
    <div class="form">
    <label class="Validform_label">
      	开始时间：
     </label>
      <input name="begintime" id="begintime"  class="Wdate" value="${leave.begintime}" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endtime\')||\'2020-10-01\'}'})"/> 
     <span class="Validform_checktip"></span>
    </div>
    <div class="form">
     <label class="Validform_label">
      	结束时间：
     </label>
      <input name="endtime" id="endtime"  value="${leave.endtime}" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'begintime\')}',maxDate:'2020-10-01'})"/>
    </div>
    <div class="form">
    <label class="Validform_label">
      	请假原因：
     </label>
     <textarea name="reason" datatype="*" style="resize: none;" rows="4" cols="30">${leave.reason}</textarea>
     <span class="Validform_checktip"></span>
    </div>
    <div class="form">
        <div class="ui_buttons" style="text-align:center">
            <input type="button" value="业务单据提交" onclick="saveNoteInfo();" class="ui_state_highlight">
        </div>
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>