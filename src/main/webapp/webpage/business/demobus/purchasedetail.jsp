<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>采购申请</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="table" dialog="true" action="busController.do?savePurchase">
   <input type="hidden" name="id" id="id" value="${purchase.id}">
    <input type="hidden" name="TBPurchase.id"  value="${purchaseid}">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
     <td align="right" height="40" width="15%">
       <label class="Validform_label">采购物品：</label>
     </td>
     <td class="value" width="85%">
      <input name="purcname" value="${purchase.purcname}" datatype="s2-50" class="inputxt" >
      <span class="Validform_checktip"></span>
     </td>
    </tr>
    <tr>
     <td align="right" height="40">
       <label class="Validform_label">采购数量：</label>
     </td>
     <td class="value">
      <input name="purcnum" value="${purchase.purcnum}" datatype="num" class="inputxt" >
     </td>
    </tr>
    <tr>
     <td align="right" height="40">
      <label class="Validform_label"> 物品单价：</label>
     </td>
     <td class="value">
    <input name="purcprice" value="${purchase.purcprice}" datatype="num" class="inputxt" >
     </td>
    </tr>
   </table>
  </t:formvalid>
 </body>
</html>
