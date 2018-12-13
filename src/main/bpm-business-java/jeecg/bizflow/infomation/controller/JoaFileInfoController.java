package jeecg.bizflow.infomation.controller;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jeecg.bizflow.infomation.entity.JoaFileInfoEntity;
import jeecg.bizflow.infomation.service.JoaFileInfoServiceI;
import jeecg.bizflow.infomation.util.InformationUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**   
 * @Title: Controller  
 * @Description: 文件目录表
 * @author onlineGenerator
 * @date 2018-07-31 17:41:59
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/joaFileInfoController")
public class JoaFileInfoController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(JoaFileInfoController.class);

	@Autowired
	private SystemService systemService;
	@Autowired
	private JoaFileInfoServiceI JoaFileInfoService;
	
	/**
	 * 文件目录表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("business/bizflow/infomation/joaFileDirtree");
	}
	
	@RequestMapping(params = "listForSelect")
	public ModelAndView listForSelect(HttpServletRequest request) {
		request.setAttribute("select",1);
		return new ModelAndView("business/bizflow/infomation/joaFileDirtree");
	}
	
	/**
	 * 加载目录树
	 */
	@RequestMapping(params = "getFileDirTree")
	@ResponseBody
	public Object getFileDirTree(HttpServletRequest request){
		String id = request.getParameter("id");
		if(oConvertUtils.isEmpty(id)){
			//一级目录type为0 parent_id为0
			String sql = "select icon as iconSkin,'true' as isParent,id,parent_id,name,path from joa_file_info where parent_id = '0' and ext = '0'";
			List<Map<String,Object>> list = this.systemService.findForJdbc(sql);
			return list; 
		}else{
			String user = ResourceUtil.getSessionUser().getUserName();
			String sql = "select icon as iconSkin,id,parent_id,name,path,(CASE when leaf=1 then 'false' else 'true' end) as isParent from joa_file_info where create_by = ? and parent_id = ? and ext = '0'";
			List<Map<String,Object>> list = this.systemService.findForJdbc(sql,user,id);
			return list;
		}
	}
	
	/**
	 * 文件夹的增删改操作
	 */
	@RequestMapping(params = "fileDirOperator")
	@ResponseBody
	public AjaxJson fileDirOperator(String id,String content,String opt,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String msg = null;
		String user = ResourceUtil.getSessionUser().getUserName();
		try{
			if("0".equals(opt)){
				//新增
				JoaFileInfoEntity dir = new JoaFileInfoEntity();
				dir.setName(content);
				dir.setOwner(user);
				dir.setParentId(id);
				dir.setLeaf(1);
				dir.setIcon("treefolder");
				dir.setExt("0");
				dir.setType("文件夹");//文件夹类型
				this.JoaFileInfoService.saveDir(dir);
				msg = "新增成功！";
			}else if("1".equals(opt)){
				//编辑
				JoaFileInfoEntity dir = this.systemService.get(JoaFileInfoEntity.class, id);
				dir.setName(content);
				this.systemService.updateEntitie(dir);
				msg = "编辑成功！";
			}else if("2".equals(opt)){
				//删除
				JoaFileInfoEntity dir = this.systemService.get(JoaFileInfoEntity.class, id);
				this.JoaFileInfoService.deleteDir(dir);
				msg = "删除成功！";
			}
		}catch(Exception e){
			e.printStackTrace();
			msg = "操作失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(msg);
		return j;
	}
	
	/**
	 * 加载文件列表
	 */
	@RequestMapping(params = "fileList")
	public ModelAndView fileList(HttpServletRequest request) {
		String dirId = request.getParameter("dirId");
		if(oConvertUtils.isEmpty(dirId)){
			dirId = "001";
		}
		request.setAttribute("dirId", dirId);
		String select = request.getParameter("select");
		if(oConvertUtils.isNotEmpty(select) && "1".equals(select)){
			return new ModelAndView("business/bizflow/infomation/joaFileSelect");
		}else{
			return new ModelAndView("business/bizflow/infomation/joaFileInfoList");
		}
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(JoaFileInfoEntity JoaFileInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(JoaFileInfoEntity.class, dataGrid);
		String name = JoaFileInfo.getName();
		if(oConvertUtils.isNotEmpty(name)){
			JoaFileInfo.setName(name+"*");//自动模糊查询
		}
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, JoaFileInfo, request.getParameterMap());
		String dirId = request.getParameter("curDirId");
		if(oConvertUtils.isEmpty(dirId)){
			dirId = request.getParameter("dirId");
		}
		if(oConvertUtils.isNotEmpty(dirId)){
			try{
				//自定义追加查询条件
				cq.eq("parentId", dirId);
				String user = ResourceUtil.getSessionUser().getUserName();
				cq.eq("owner", user);
			}catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
			cq.add();
			this.systemService.getDataGridReturn(cq, true);
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 上传页面跳转
	 */
	@RequestMapping(params = "goPlupload")
	public ModelAndView goPlupload(HttpServletRequest request) {
		String dirId = request.getParameter("dirId");
		request.setAttribute("dirId", dirId);
		return new ModelAndView("business/bizflow/infomation/joaFileUpload");
	}
	
	/**
	 * 保存文件信息
	 */
	@RequestMapping(params = "saveFileInfo")
	@ResponseBody
	public AjaxJson saveFileInfo(HttpServletRequest request) {
		String message = "文件信息保存成功";
		AjaxJson j = new AjaxJson();
		String files = request.getParameter("files");
		String dirId = request.getParameter("dirId");
		try{
			if(oConvertUtils.isNotEmpty(files)){
				this.JoaFileInfoService.saveFileInfo(files, dirId);
			}
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "文件信息保存失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 行编辑修改文件名称 
	 * @author taoYan
	 * @since 2018年8月9日
	 */
	@RequestMapping(params = "updateFileName")
	@ResponseBody
	public AjaxJson updateFileName(HttpServletRequest request) {
		String message = "文件名称修改成功";
		AjaxJson j = new AjaxJson();
		String updatenames = request.getParameter("updatenames");
		try{
			JSONArray array = JSONArray.fromObject(updatenames);
			for (Object object : array) {
				JSONObject json = (JSONObject) object;
				String name = json.getString("name");
				String id = json.getString("id");
				this.JoaFileInfoService.updateFileName(id, name);
				systemService.addLog("文件:"+id+"名称"+name+"修改成功", Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "文件名称修改失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 下载单个文件
	 * @author taoYan
	 * @since 2018年8月9日
	 */
	@RequestMapping(params = "downFile")
	public void downFile(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("id");
		JoaFileInfoEntity JoaFileInfo = systemService.getEntity(JoaFileInfoEntity.class,id);
		String path = JoaFileInfo.getPath();
		String fileName=JoaFileInfo.getName()+"."+JoaFileInfo.getExt();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			//去掉路径结尾逗号
			String userAgent = request.getHeader("user-agent").toLowerCase();	
			if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}else {  
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");  
			} 	
			String localPath = ResourceUtil.getConfigByName("webUploadpath");
			String imgurl = localPath + File.separator + path;
			inputStream = new BufferedInputStream(new FileInputStream(imgurl));
			response.setContentType("application/x-msdownload;charset=utf-8");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			outputStream = response.getOutputStream();
			byte[] buf = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				outputStream.write(buf, 0, len);
			}
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("--通过流的方式获取文件异常--" + e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 下载zip
	 * @author taoYan
	 * @since 2018年8月9日
	 */
	@RequestMapping(params = "downFilesOnZip")
	public void downFilesOnZip(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String ids = request.getParameter("ids");
		if(oConvertUtils.isNotEmpty(ids)){
			if(ids.endsWith(",")){
				ids = ids.substring(0, ids.length()-1);
			}
			InputStream ins = null;
			BufferedInputStream bins = null;
			OutputStream outs = null;
			BufferedOutputStream bouts = null;
			File file = null;
			try {
				CriteriaQuery cq = new CriteriaQuery(JoaFileInfoEntity.class);
				cq.in("id",ids.split(","));
				cq.add();
				List<JoaFileInfoEntity> list = this.systemService.getListByCriteriaQuery(cq, false);
				
				if(list!=null && list.size()>=0){
					String fileName=DateUtils.formatDate(new Date(),"yyyy-MM-dd")+".zip";
					String user = ResourceUtil.getSessionUser().getUserName();
					String path = request.getServletContext().getRealPath("/")+user+fileName;//zip临时目录
					InformationUtil.zipMultiFile(list, path);
					file = new File(path);
					if(file.exists()){
						ins = new FileInputStream(path);
						bins = new BufferedInputStream(ins);
						//去掉路径结尾逗号
						response.setContentType("application/x-msdownload;charset=utf-8");
						response.setHeader("Content-disposition", "attachment; filename=" + fileName);
						outs = response.getOutputStream();
						bouts = new BufferedOutputStream(outs);
						int bytesRead = 0;
						byte[] buffer = new byte[8192];
						//开始向网络传输流
						while((bytesRead = bins.read(buffer,0,buffer.length)) != -1){
							bouts.write(buffer, 0, bytesRead);
						}
						bouts.flush();
					}
				}
			} catch (Exception e) {
				logger.info("全部下载出问题了");
			}finally{
				if(ins!=null){
					ins.close();
				}
				if(bins!=null){
					bins.close();
				}
				if(outs!=null){
					outs.close();
				}
				if(bouts!=null){
					bouts.close();
				}
				if(file!=null){
					file.delete();//删除临时zip文件
				}
			}
			
		}
	}
	
	/**
	 * 预览文件
	 * @author taoYan
	 * @since 2018年8月9日
	 */
	@RequestMapping(params = "openViewFile")
	public ModelAndView openViewFile(HttpServletRequest request) {
		String id = request.getParameter("id");
		JoaFileInfoEntity JoaFileInfo = systemService.getEntity(JoaFileInfoEntity.class,id);
		String inputFile = JoaFileInfo.getPath();
		String extend=JoaFileInfo.getExt();
		if (FileUtils.isPicture(extend)) {
			request.setAttribute("realpath", "img/server/"+inputFile);
			return new ModelAndView("common/upload/imageView");
		}else{
			String swfPath = FileUtils.getSwfPath(inputFile);
			request.setAttribute("swfpath", "img/server/"+swfPath+"?down=true");
			return new ModelAndView("common/upload/swfView");
		}
	}	
	/**
	 * 删除文件
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(JoaFileInfoEntity JoaFileInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		JoaFileInfo = systemService.getEntity(JoaFileInfoEntity.class, JoaFileInfo.getId());
		message = "文件删除成功";
		try{
			JoaFileInfoService.delete(JoaFileInfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "文件删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除文件
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "文件删除成功";
		try{
			for(String id:ids.split(",")){
				JoaFileInfoEntity JoaFileInfo = systemService.getEntity(JoaFileInfoEntity.class,id); 
				systemService.delete(JoaFileInfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "文件删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 根据多个id获取文件信息
	 * @author taoYan
	 * @since 2018年8月9日
	 */
	@RequestMapping(params = "getHistoryFiles")
	@ResponseBody
	public AjaxJson getHistoryFiles(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		if(oConvertUtils.isEmpty(ids)){
			message = "文件不存在!";
			j.setSuccess(false);
		}else{
			try{
				if(ids.endsWith(",")){
					ids = ids.substring(0,ids.length()-1);
				}
				CriteriaQuery cq = new CriteriaQuery(JoaFileInfoEntity.class);
				cq.in("id",ids.split(","));
				cq.add();
				List<JoaFileInfoEntity> list = this.systemService.getListByCriteriaQuery(cq, false);
				j.setObj(list);
			}catch(Exception e){
				e.printStackTrace();
				message = "获取文件失败";
				j.setSuccess(false);
				throw new BusinessException(e.getMessage());
			}
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 跳转选择用户界面
	 * @return
	 */
	@RequestMapping(params = "goUserSelect")
	public ModelAndView addUserSelect(HttpServletRequest request) {
		return new ModelAndView("business/bizflow/infomation/joaUserSelect");
	}
	
	@RequestMapping(params = "getUserDataGrid", method = RequestMethod.POST)
	@ResponseBody
	public void getUserDataGrid(TSUser user,HttpServletRequest request,HttpServletResponse response,DataGrid dataGrid){
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
		Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN, Globals.User_Forbidden };
		cq.in("status", userstate);
		cq.eq("deleteFlag", Globals.Delete_Normal);
        cq.eq("userType",Globals.USER_TYPE_SYSTEM);//系统用户
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
}
