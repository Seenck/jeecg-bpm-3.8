<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>借款单</title>
  <t:base type="jquery,tools"></t:base>
  <link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
    <script type="text/javascript">
        function saveNoteInfo() {
            $('#btn_sub').click();
            alert('保存并提交流程成功');
        }
    </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="table" dialog="true" action="busController.do?saveBormoney">
   <input type="hidden" name="id" id="id" value="${bormoney.id}">
   <input name="taskId" id="taskId" type="hidden" value="${taskId}" />
   <table style="width:550px;" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
     <td align="right" height="40" width="15%">
      <label class="Validform_label"> 借款金额：</label>
     </td>
     <td class="value"  width="85%">
      <input name="bormoney" class="inputxt"  value="${bormoney.bormoney}" datatype="n" >
      <span class="Validform_checktip"></span>
     </td>
    </tr>
    <tr>
     <td align="right" height="40">
     <label class="Validform_label"> 借款事由：</label>
     </td>
     <td class="value">
     <textarea name="bustripreson"  rows="4" cols="30" datatype="*">${bormoney.bormoneyuse}</textarea>
      <span class="Validform_checktip"></span>
     </td>
    </tr>
   </table>
   <div class="form">
        <div class="ui_buttons" style="text-align:left">
            <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="保存并提交流程" onclick="saveNoteInfo();" class="ui_state_highlight">
        </div>
    </div>
  </t:formvalid>
 </body>
</html>
