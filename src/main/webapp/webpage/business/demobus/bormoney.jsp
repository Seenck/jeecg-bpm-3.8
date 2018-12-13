<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>借款单</title>
  <t:base type="jquery,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="table" dialog="true" action="busController.do?saveBormoney">
   <input type="hidden" name="id" id="id" value="${tbBormoney.id}">
   <input name="taskId" id="taskId" type="hidden" value="${taskId}" />
   <input name="keys" id="keys" type="hidden" />
   <input name="values" id="values" type="hidden" />
   <input name="types" id="types" type="hidden" />
   <table style="width:550px;" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
     <td align="right" height="40" width="15%">
      <label class="Validform_label"> 借款金额：</label>
     </td>
     <td class="value"  width="85%">
     <!-- update-begin-author:taoYan date:20170803 for:借款金额数字验证修改 -->
      <input name="bormoney" class="inputxt"  value="${tbBormoney.bormoney}" datatype="d" >
      <!-- update-end-author:taoYan date:20170803 for:借款金额数字验证修改 -->
      <span class="Validform_checktip"></span>
     </td>
    </tr>
    <tr>
     <td align="right" height="40">
     <label class="Validform_label"> 借款事由：</label>
     </td>
     <td class="value">
     <textarea name="bormoneyuse"  rows="4" cols="30" datatype="*">${tbBormoney.bormoneyuse}</textarea>
      <span class="Validform_checktip"></span>
     </td>
    </tr>
    
   </table>
  </t:formvalid>
 </body>
</html>
