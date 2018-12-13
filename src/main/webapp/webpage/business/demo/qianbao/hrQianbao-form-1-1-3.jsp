<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>签报</title>
    <link rel="stylesheet" type="text/css" href="plug-in/qianbao/css/css-reset.css" />
    <link href="plug-in/qianbao/bootstrap/css/bootstrap-theme1.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="plug-in/qianbao/css/style.css" />
    <link rel="stylesheet" href="plug-in/Validform/css/metrole/style.css" type="text/css"/>
    <script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
    <script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
    <script type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script>
</head>
<body>
<form role="form" id="formobj" action="hrQianbaoController.do?doUpdate" name="formobj" method="post">
<input 	type="hidden" id="btn_sub" class="btn_sub"/>
<input 	type="hidden" value="${hrQianbaoPage.id }" id="id" name="id"/>
<input  type="hidden" value="${hrQianbaoPage.documentCategory }" id="documentCategory" name="documentCategory" />
<input  type="hidden" value="${hrQianbaoPage.qianbaoDepartCode }" id="qianbaoDepartCode" name="qianbaoDepartCode" />
<input  type="hidden" value="${hrQianbaoPage.qianbaoDepartName }" id="qianbaoDepartName" name="qianbaoDepartName" />
<input  type="hidden" value="${hrQianbaoPage.qianbaoDate }" id="qianbaoDate" name="qianbaoDate" />
<div class="wrapper">
    <div class="content">
        <div class="topbar">
            <div class="topbar-text">公文类别：股份有限公司签报</div>
            <div class="topbar-text">当前状态：拟稿</div>
            <!-- <div class="topbar-link">
                <li class="bluetext-link"><a href="#" ondragstart="return false">流转明细</a></li>
                <li class="bluetext-link"><a href="#" ondragstart="return false">流程图示</a></li>
            </div> -->
        </div>
        <div class="number">
            <li class="text-red">编号：</li>
            <li>办公室[</li>
            <li><input class="form-control" type="text" value="2015" id="qianbaoCodeYear" name="qianbaoCodeYear" value="${hrQianbaoPage.qianbaoCodeYear }"/></li>
            <li>]</li>
            <li><input class="form-control" type="text" value="11" id="qianbaoCodeNum" name="qianbaoCodeNum" value="${hrQianbaoPage.qianbaoCodeNum }"/></li>
            <li>号</li>
        </div>
        <div class="sign">
            <li>签</li>
            <li class="signtext">报</li>
        </div>
        <div class="hr-office">
            <div>
                <li class="text-red">中国华融资产管理股份有限公司（</li>
                <li>办公室</li>
                <li class="text-red">）</li>
            </div>
            <div class="date">
                <li>2015 </li>
                <li class="text-red">年</li> 
                <li>4 </li>
                <li class="text-red">月</li> 
                <li>23 </li>
                <li class="text-red">日</li>
            </div>
        </div>
        <div class="sign-content">
            <div class="instructions">
                <div class="instructions-title">
                    <div class="text-red">公司领导批示</div>
                    
                </div>
                <div class="instructionsdiv">
                    <li class="topic text-red">题目：</li>
                    <div class="topic-content text-blue">
                        <li>题目内容1</li>
                    </div>
                    <li class="btn-group instructions-btn"><button class="btn btn-default" type="button">正文</button></li>
                </div>
                <div class="instructionsdiv">
                    <li class="topic text-red">附件：</li>
                    <div class="topic-content text-blue">
                        <li>附件1</li>
                        <li>附件2</li>
                        <li>附件3</li>
                    </div>
                    <li class="btn-group instructions-btn"><button class="btn btn-default" type="button">附件</button></li>
                </div>
                <div class="instructionsdiv">
                    <li class="topic text-red">相关材料：</li>
                    <div class="topic-content text-blue">
                        <li>相关材料1</li>
                        <li>相关材料2</li>
                        <li>相关材料3</li>
                    </div>
                    <li class="btn-group instructions-btn"><button class="btn btn-default" type="button">选择材料</button></li>
                </div>
                <div class="instructionsdiv">
                    <li class="text-red">是否为制度文件：</li>
                    <label class="checkbox-inline">
                      <input type="radio" name="policyFlag" 
                         value="0" <c:if test="${hrQianbaoPage.policyFlag eq 0}">checked="true"</c:if>> 否
                    </label>
                    <label class="checkbox-inline">
                      <input type="radio" name="policyFlag" 
                         value="1" <c:if test="${hrQianbaoPage.policyFlag eq 1}">checked="true"</c:if>> 是
                    </label>
                </div>
            </div>
            <div class="opinion">
                
                <div class="opinion-title text-red">会签单位意见</div>
                <div class="selection-unit">
                    <div class="opiniondiv">
	                	<li>是否需会签：</li>
	                	<label class="checkbox-inline">
	                      <input type="radio" name="qianbaoFlag" 
	                         value="1" <c:if test="${hrQianbaoPage.qianbaoFlag eq 1}">checked="true"</c:if>> 需要会签
	                    </label>
	                    <label class="checkbox-inline">
	                      <input type="radio" name="qianbaoFlag" 
	                         value="0" <c:if test="${hrQianbaoPage.qianbaoFlag eq 0}">checked="true"</c:if>> 不需要会签
	                    </label>
	                    
	                </div>
	                <div class="opiniondiv">
	                    <li><label for="name">会签单位：</label></li>
	                    <li>
	                          <div class="form-group">
	                            <textarea class="form-control" rows="3">股权管理部</textarea>
	                          </div>
	                        
	                    </li>
	                    <li class="isobtn btn-group"><button class="btn btn-default" type="button">选择单位</button></li>
	                </div>
                </div>
                
                <div class="isotext">
                    <div class="text-red">ISO9000 审核： </div>
                    
                </div>
            </div>
        </div>
        <div class="bottom-info">
            <div class="charge">
                <li class="text-red">部室负责人：</li>
                <li><input class="form-control" type="text" /></li>
                <li class="btn-group"><button class="btn btn-default" type="button">选择</button></li>
            </div>
            <div class="contact">
                <li class="text-red">联系人：	</li>
                <li>李青</li>
                <li><input class="form-control" type="text" value="8340" /></li>
            </div>
        </div>
        <div class="bottom-btn">
            <li><button class="btn btn-default" type="button">下一步</button></li>
            <li><button class="btn btn-default" type="button">打印</button></li>
        </div>
    </div>
</div>
<script src = "plug-in/tools/workflow.js"></script>
		<script type="text/javascript">
			$(function() {
				$("#formobj")
						.Validform(
								{
									tiptype : 1,
									btnSubmit : "#btn_sub",
									btnReset : "#btn_reset",
									ajaxPost : true,
									usePlugin : {
										passwordstrength : {
											minLen : 6,
											maxLen : 18,
											trigger : function(obj, error) {
												if (error) {
													obj
															.parent()
															.next()
															.find(
																	".Validform_checktip")
															.show();
													obj
															.find(
																	".passwordStrength")
															.hide();
												} else {
													$(".passwordStrength")
															.show();
													obj
															.parent()
															.next()
															.find(
																	".Validform_checktip")
															.hide();
												}
											}
										}
									},
									callback : function(data) {
										if (data.success == true) {
											var win = frameElement.api.opener;
											win.reloadTable();
											win.tip(data.msg);
											frameElement.api.close();
										} else {
											if (data.responseText == ''
													|| data.responseText == undefined) {
												$.messager
														.alert('错误', data.msg);
												$.Hidemsg();
											} else {
												try {
													var emsg = data.responseText
															.substring(
																	data.responseText
																			.indexOf('错误描述'),
																	data.responseText
																			.indexOf('错误信息'));
													$.messager
															.alert('错误', emsg);
													$.Hidemsg();
												} catch (ex) {
													$.messager.alert('错误',
															data.responseText
																	+ '');
												}
											}
											return false;
										}
									}
								});
			});
		</script>

	</form>
</body>
</html>
