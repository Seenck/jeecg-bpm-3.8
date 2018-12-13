<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>出差申请单</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="table" dialog="true" action="busController.do?saveBustrip">
   <input type="hidden" name="id" id="id" value="${bustrip.id}">
   <input name="code" id="code" type="hidden" value="${bustrip.TSPrjstatus.code}" />
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
     <td align="right" height="40" width="15%">
       <label class="Validform_label">出差地点：</label>
     </td>
     <td class="value" width="85%">
      <input name="bustriplocale" value="${bustrip.bustriplocale}" datatype="s2-10" class="inputxt" >
      <span class="Validform_checktip"></span>
     </td>
    </tr>
    <tr>
     <td align="right" height="40">
       <label class="Validform_label"> 开始时间：</label>
     </td>
     <td class="value">
      <input name="begintime" id="begintime" style="width: 150px" class="Wdate" value="${bustrip.begintime}" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endtime\')||\'2020-10-01\'}'})"/> 
     </td>
    </tr>
    <tr>
     <td align="right" height="40">
      <label class="Validform_label"> 结束时间：</label>
     </td>
     <td class="value">
     <input name="endtime" id="endtime" style="width: 150px" value="${bustrip.endtime}" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'begintime\')}',maxDate:'2020-10-01'})"/>
     </td>
    </tr>
    <tr>
     <td align="right" height="40">
       <label class="Validform_label"> 出行方式：</label>
     </td>
     <td class="value">
      <select name="goouttype">
       <option value="1">
       	 火车
       </option>
       <!-- update-begin-author:taoYan date:20170803 for:默认未选中 -->
       <option value="2" <c:if test="${bustrip.goouttype=='2'}">selected="selected"</c:if>>
      	  汽车
       </option>
       <option value="3" <c:if test="${bustrip.goouttype=='3'}">selected="selected"</c:if>>
     	   飞机
       </option>
       <!-- update-end-author:taoYan date:20170803 for:默认未选中 -->
      </select>
      <span class="Validform_checktip"></span>
     </td>
    </tr>
     <tr>
     <td align="right" height="40">
       <label class="Validform_label"> 出差事由：</label>
     </td>
     <td class="value">
      <input name="bustripreson" value="${bustrip.bustripreson}" rows="4" cols="30" datatype="*" class="inputxt" >
      <span class="Validform_checktip"></span>
     </td>
    </tr>
    <tr>
     <td align="right" height="40">
      <label class="Validform_label"> 出差费用：</label>
     </td>
     <td class="value">
      <input name="bustripmoney" value="${bustrip.bustripmoney}" datatype="d" class="inputxt">
      <span class="Validform_checktip"></span>
     </td>
    </tr>
   </table>
  </t:formvalid>
 </body>
</html>
