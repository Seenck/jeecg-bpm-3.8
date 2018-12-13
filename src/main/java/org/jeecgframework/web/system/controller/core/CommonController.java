package org.jeecgframework.web.system.controller.core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.TSDictTableConfigServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


/**
 * 通用业务处理
 * 
 * @author 张代浩
 * 
 */
//@Scope("prototype")
@Controller
@RequestMapping("/commonController")
public class CommonController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CommonController.class);
	private SystemService systemService;
	@Autowired
	private TSDictTableConfigServiceI tSDictTableConfigService;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * 通用列表页面跳转
	 */
	@RequestMapping(params = "listTurn")
	public ModelAndView listTurn(HttpServletRequest request) {
		String turn = request.getParameter("turn");// 跳转的目标页面
		logger.info("--通用页面跳转--listTurn-------"+turn);
		return new ModelAndView(turn);
	}

	/**
	 * 附件预览页面打开链接
	 * 
	 * @return
	 */
	@RequestMapping(params = "openViewFile")
	public ModelAndView openViewFile(HttpServletRequest request) {
		String fileid = request.getParameter("fileid");
		String subclassname = oConvertUtils.getString(request.getParameter("subclassname"), "org.jeecgframework.web.system.pojo.base.TSAttachment");
		String contentfield = oConvertUtils.getString(request.getParameter("contentfield"));
		Class<?> fileClass = MyClassLoader.getClassByScn(subclassname);// 附件的实际类
		Object fileobj = systemService.getEntity(fileClass, fileid);
		ReflectHelper reflectHelper = new ReflectHelper(fileobj);
		String extend = oConvertUtils.getString(reflectHelper.getMethodValue("extend"));
		if ("dwg".equals(extend)) {
			String realpath = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
			request.setAttribute("realpath", realpath);
			return new ModelAndView("common/upload/dwgView");
		} else if (FileUtils.isPicture(extend)) {
			String realpath = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
			request.setAttribute("realpath", realpath);
			request.setAttribute("fileid", fileid);
			request.setAttribute("subclassname", subclassname);
			request.setAttribute("contentfield", contentfield);
			return new ModelAndView("common/upload/imageView");
		} else {
			String swfpath = oConvertUtils.getString(reflectHelper.getMethodValue("swfpath"));
			//update-begin--Author:dangzhenghui  Date:20170524 for：TASK #1901 【demo】文档管理demo
			swfpath=swfpath.replace("\\","/");
			//update-end--Author:dangzhenghui  Date:20170524 for：TASK #1901 【demo】文档管理demo
			request.setAttribute("swfpath", swfpath);
			return new ModelAndView("common/upload/swfView");
		}

	}

	/**
	 * 附件预览读取/下载文件
	 * 
	 * @return
	 */
	@RequestMapping(params = "viewFile")
	public void viewFile(HttpServletRequest request, HttpServletResponse response) {
		String fileid =oConvertUtils.getString(request.getParameter("fileid"));
		//update-start--Author:scott  Date:20170608 for：判断是否默认上传实体，是则原生态写法不用反射，提高效率----
		String subclassname = request.getParameter("subclassname");
		if(oConvertUtils.isEmpty(subclassname)){
			TSAttachment tsAttachment = systemService.getEntity(TSAttachment.class, fileid);
			UploadFile uploadFile = new UploadFile(request, response);
			//byte[] content = tsAttachment.getAttachmentcontent();
			String path = tsAttachment.getRealpath();;
			String extend = tsAttachment.getExtend();
			String attachmenttitle = tsAttachment.getAttachmenttitle();
			uploadFile.setExtend(extend);
			uploadFile.setTitleField(attachmenttitle);
			uploadFile.setRealPath(path);
			//uploadFile.setContent(content);
			//uploadFile.setView(true);
			systemService.viewOrDownloadFile(uploadFile);
			logger.info("--附件预览----TSAttachment---viewFile-----path--"+path);
		}else{
			subclassname = oConvertUtils.getString(subclassname);
			Class<?> fileClass = MyClassLoader.getClassByScn(subclassname);// 自定义附件实体类
			Object fileobj = systemService.getEntity(fileClass, fileid);
			ReflectHelper reflectHelper = new ReflectHelper(fileobj);
			UploadFile uploadFile = new UploadFile(request, response);
			String contentfield = oConvertUtils.getString(request.getParameter("contentfield"), uploadFile.getByteField());
			byte[] content = (byte[]) reflectHelper.getMethodValue(contentfield);
			String path = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
			String extend = oConvertUtils.getString(reflectHelper.getMethodValue("extend"));
			String attachmenttitle = oConvertUtils.getString(reflectHelper.getMethodValue("attachmenttitle"));
			uploadFile.setExtend(extend);
			uploadFile.setTitleField(attachmenttitle);
			uploadFile.setRealPath(path);
			uploadFile.setContent(content);
			//uploadFile.setView(true);
			systemService.viewOrDownloadFile(uploadFile);
			logger.info("--附件预览---自定义实体类："+subclassname+"--viewFile-----path--"+path);
		}
		//update-end--Author:scott  Date:20170608 for：判断是否默认上传实体，是则原生态写法不用反射，提高效率----
	}

	@RequestMapping(params = "importdata")
	public ModelAndView importdata() {
		return new ModelAndView("system/upload");
	}

	/**
	 * 生成XML文件
	 * 
	 * @return
	 */
	@RequestMapping(params = "createxml")
	public void createxml(HttpServletRequest request, HttpServletResponse response) {
		String field = request.getParameter("field");
		String entityname = request.getParameter("entityname");
		ImportFile importFile = new ImportFile(request, response);
		importFile.setField(field);
		importFile.setEntityName(entityname);
		importFile.setFileName(entityname + ".bak");
		importFile.setEntityClass(MyClassLoader.getClassByScn(entityname));
		systemService.createXml(importFile);
	}

	/**
	 * 生成XML文件parserXml
	 * 
	 * @return
	 */
	@RequestMapping(params = "parserXml")
	@ResponseBody
	public AjaxJson parserXml(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson json = new AjaxJson();
		String fileName = null;
		UploadFile uploadFile = new UploadFile(request);
		String ctxPath = request.getSession().getServletContext().getRealPath("");
		File file = new File(ctxPath);
		if (!file.exists()) {
			file.mkdir();// 创建文件根目录
		}
		MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();// 获取上传文件对象
			fileName = mf.getOriginalFilename();// 获取文件名
			String savePath = file.getPath() + "/" + fileName;
			File savefile = new File(savePath);
			try {
				FileCopyUtils.copy(mf.getBytes(), savefile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		systemService.parserXml(ctxPath + "/" + fileName);
		json.setSuccess(true);
		return json;
	}

	/**
	 * 自动完成请求返回数据
	 * 
	 * @param request
	 * @param responss
	 */
	@RequestMapping(params = "getAutoList")
	public void getAutoList(HttpServletRequest request, HttpServletResponse response, Autocomplete autocomplete) {
		String jsonp = request.getParameter("jsonpcallback");
		String trem = StringUtil.getEncodePra(request.getParameter("trem"));// 重新解析参数
		autocomplete.setTrem(trem);
		List autoList = systemService.getAutoList(autocomplete);
		String labelFields = autocomplete.getLabelField();
		String[] fieldArr = labelFields.split(",");
		String valueField = autocomplete.getValueField();
		String[] allFieldArr = null;
		if (StringUtil.isNotEmpty(valueField)) {
			allFieldArr = new String[fieldArr.length+1];
			for (int i=0; i<fieldArr.length; i++) {
				allFieldArr[i] = fieldArr[i];
			}
			allFieldArr[fieldArr.length] = valueField;
		}
		
		try {
			String str = TagUtil.getAutoList(autocomplete, autoList);
			str = "(" + str + ")";
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(JSONHelper.listtojson(allFieldArr,allFieldArr.length,autoList));
            response.getWriter().flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			try {
				response.getWriter().close();
			} catch (IOException e) {
			}
		}

	}

	/**
	 * 删除继承于TSAttachment附件的公共方法
	 * 	
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "delObjFile")
	@ResponseBody
	public AjaxJson delObjFile(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
		TSAttachment attachment = systemService.getEntity(TSAttachment.class,fileKey);
		String subclassname = attachment.getSubclassname(); // 子类类名
		Object objfile = systemService.getEntity(MyClassLoader.getClassByScn(subclassname), attachment.getId());// 子类对象
		message = "" + attachment.getAttachmenttitle() + "删除成功";
		systemService.delete(objfile);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		logger.info("--删除附件---delObjFile----"+message);
		
		j.setMsg(message);
		return j;
	}

	/**
	 * 继承于TSAttachment附件公共列表跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "objfileList")
	public ModelAndView objfileList(HttpServletRequest request) {
		Object object = null;// 业务实体对象
		String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
		TSAttachment attachment = systemService.getEntity(TSAttachment.class,fileKey);
		String businessKey = oConvertUtils.getString(request.getParameter("businessKey"));// 业务主键
		String busentityName = oConvertUtils.getString(request.getParameter("busentityName"));// 业务主键
		String typename = oConvertUtils.getString(request.getParameter("typename"));// 类型
		String typecode = oConvertUtils.getString(request.getParameter("typecode"));// 类型typecode
		if (StringUtil.isNotEmpty(busentityName) && StringUtil.isNotEmpty(businessKey)) {
			object = systemService.get(MyClassLoader.getClassByScn(busentityName), businessKey);
			request.setAttribute("object", object);
			request.setAttribute("businessKey", businessKey);
		}
		if(attachment!=null)
		{
			request.setAttribute("subclassname",attachment.getSubclassname());
		}
		request.setAttribute("fileKey", fileKey);
		request.setAttribute("typecode", typecode);
		request.setAttribute("typename", typename);
		request.setAttribute("typecode", typecode);
		return new ModelAndView("common/objfile/objfileList");
	}

	/**
	 * 继承于TSAttachment附件公共列表数据
	 */
	@RequestMapping(params = "objfileGrid")
	public void objfileGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String businessKey = oConvertUtils.getString(request.getParameter("businessKey"));
		String subclassname = oConvertUtils.getString(request.getParameter("subclassname"));// 子类类名
		String type = oConvertUtils.getString(request.getParameter("typename"));
		String code = oConvertUtils.getString(request.getParameter("typecode"));
		String filekey = oConvertUtils.getString(request.getParameter("filekey"));
		CriteriaQuery cq = new CriteriaQuery(MyClassLoader.getClassByScn(subclassname), dataGrid);
		cq.eq("businessKey", businessKey);
		if (StringUtil.isNotEmpty(type)) {
			cq.createAlias("TBInfotype", "TBInfotype");
			cq.eq("TBInfotype.typename", type);
		}
		if (StringUtil.isNotEmpty(filekey)) {
			cq.eq("id", filekey);
		}
		if (StringUtil.isNotEmpty(code)) {
			cq.createAlias("TBInfotype", "TBInfotype");
			cq.eq("TBInfotype.typecode", code);
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	//update-begin-author:taoyan date:20180508 for:判断是否配置高级查询
	@RequestMapping(params = "superQueryExist")
	@ResponseBody
	public String superQueryExist(HttpServletRequest request,String superQueryCode) {
		if(oConvertUtils.isEmpty(superQueryCode)){
			return "no";
		}
		//update-begin-author:taoyan date:20180528 for:TASK #2745 【Sql 注入问题修改】c
		String sql = "select count(1) from super_query_main where query_code = ?";
		long count = this.systemService.getCountForJdbcParam(sql, superQueryCode);
		//update-end-author:taoyan date:20180528 for:TASK #2745 【Sql 注入问题修改】c
		if(count>0){
			return "yes";
		}else{
			return "no";
		}
	}
	//update-end-author:taoyan date:20180508 for:判断是否配置高级查询
	
	//update-begin-Author:zhoujf  Date:20180712 for:TASK #2809 【bug】网友反馈问题，字典性能问题
	/**
	 * @param dictionary 自定义字典(格式：表名,编码,显示文本)
	 * @param dictCondition 自定义字典表的显示文本-字典查询条件
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getDictInfo")
	@ResponseBody
	public AjaxJson getDictInfo(String dictionary,String dictCondition,Boolean popup,String value, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//update-begin-Author:zhoujf  Date:20180724 for:TASK #2996 【友好提示】ajax模式的列表加载提示信息
		Object text = "--";
		if(popup){
			//TODO popup处理  (暂不做处理)
			text="-- popup暂不支持ajax --";
		}else{
			boolean flag = tSDictTableConfigService.checkDictAuth(dictionary, dictCondition);
			if(flag){
				text = tSDictTableConfigService.getDictText(dictionary, dictCondition, value);
			}else{
				text="-- 字典配置需要授权 --";
			}
		}
		//update-end-Author:zhoujf  Date:20180724 for:TASK #2996 【友好提示】ajax模式的列表加载提示信息
		j.setObj(text);
		j.setMsg(message);
		return j;
	}
	//update-end-Author:zhoujf  Date:20180712 for:TASK #2809 【bug】网友反馈问题，字典性能问题

}
