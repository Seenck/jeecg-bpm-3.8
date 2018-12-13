<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>调整申请</title>
  <t:base type="jquery,easyui,tools"></t:base>
   <link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
    <script type="text/javascript">
        function saveNoteInfo() {
            $('#btn_sub').click();
            alert('业务单据提交成功');
        }
    </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="table" dialog="true" action="busController.do?updateBusinesstrip">
   <input type="hidden" name="id" id="id" value="${tbBusinesstrip.id}">
   <input name="code" id="code" type="hidden" value="${tbBusinesstrip.TSPrjstatus.code}" />
   <input name="taskId" id="taskId" type="hidden" value="${taskId}" />
   <input name="keys" id="keys" type="hidden" />
   <input name="values" id="values" type="hidden" />
   <input name="types" id="types" type="hidden" />
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
     <td align="right" height="40" width="15%">
      <span class="filedzt">出差地点：</span>
     </td>
     <td class="value" width="85%">
      <input name="bustriplocale" value="${tbBusinesstrip.bustriplocale}" datatype="s2-10" errormsg="4~10位字符之间 !" checktip="4~10位字符,且不为空" style="width: 150px">
      <span class="Validform_checktip"></span>
     </td>
    </tr>
    <tr>
     <td align="right" height="40">
      <span class="filedzt"> 开始时间：</span>
     </td>
     <td class="value">
      <input class="easyui-datetimebox" value="${tbBusinesstrip.begintime}" formatter="yyyy-MM-dd ss:mm" style="width: 150px" name="begintime">
     </td>
    </tr>
    <tr>
     <td align="right" height="40">
      <span class="filedzt"> 结束时间：</span>
     </td>
     <td class="value">
      <input class="easyui-datetimebox" value="${tbBusinesstrip.endtime}" formatter="yyyy-MM-dd ss:mm" style="width: 150px" name="endtime">
     </td>
    </tr>
    <tr>
     <td align="right" height="40">
      <span class="filedzt"> 出行方式：</span>
     </td>
     <td class="value">
      <select name="goouttype" style="width: 150px">
       <option value="1" <c:if test="${tbBusinesstrip.goouttype=='1'}">selected="selected"</c:if> >
        	火车
       </option>
       <option value="2" <c:if test="${tbBusinesstrip.goouttype=='2'}">selected="selected"</c:if> >
       	         汽车
       </option>
       <option value="3" <c:if test="${tbBusinesstrip.goouttype=='3'}">selected="selected"</c:if> >
        	飞机
       </option>
      </select>
      <span class="Validform_checktip"></span>
     </td>
    </tr>
     <tr>
     <td align="right" height="40">
      <span class="filedzt"> 出差事由：</span>
     </td>
     <td class="value">
      <input name="bustripreson" value="${tbBusinesstrip.bustripreson}" datatype="*" errormsg="请填写出差事由" checktip="请填写出差事由" style="width: 150px">
      <span class="Validform_checktip"></span>
     </td>
    </tr>
    <tr>
     <td align="right" height="40">
      <span class="filedzt"> 出差费用：</span>
     </td>
     <td class="value">
      <input name="bustripmoney" value="${tbBusinesstrip.bustripmoney}" datatype="d" errormsg="必须数字" checktip="必须为数字" style="width: 150px">
      <span class="Validform_checktip"></span>
     </td>
    </tr>
   </table>
   <div class="form">
        <div class="ui_buttons" style="text-align:center">
            <input type="button" value="业务单据提交" onclick="saveNoteInfo();" class="ui_state_highlight">
        </div>
    </div>
  </t:formvalid>
 </body>
</html>
