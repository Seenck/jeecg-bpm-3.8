<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<script type="text/javascript">
	$(function() {
		$(document).ready(function() {
			var chart;
			$.ajax({
				type : "POST",
				url : "logController.do?getBroswerBar&reportType=${reportType}",
				success : function(jsondata) {
					data = eval(jsondata);
					//update-begin--author:zhangjiaqiang Date:20170213 for:修订X坐标轴信息
					var xAxisCategories = new Array();
					for(var i = 0; i < data[0].data.length; i++){
						xAxisCategories[i] = data[0].data[i].name;
					}
					//update-end--author:zhangjiaqiang Date:20170213 for:修订X坐标轴信息
					chart = new Highcharts.Chart({
						chart : {
							renderTo : 'containerCol',
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : "<t:mutiLang langKey="user.browser.analysis"/>"
						},
						xAxis : {
							categories : xAxisCategories
						},
						tooltip : {
							 percentageDecimals : 1,
							 formatter: function() {
            					return  '<b>'+this.point.name + '</b>:' +  this.y;
         					}

						},
						exporting:{  
			                filename:'column',  
			                url:'${ctxPath}/logController.do?export'//
			            },
						plotOptions : {
							column : {
								allowPointSelect : true,
								cursor : 'pointer',
								showInLegend : true,
								dataLabels : {
									enabled : true,
									color : '#000000',
									connectorColor : '#000000',
									formatter : function() {
										return '<b>' + this.point.name + '</b>: ' + this.y;
									}
								}
							}
						},
						series : data
					});
				}
			});
		});
	});
</script>
<div id="containerCol" style="width: 80%; height: 80%"></div>


