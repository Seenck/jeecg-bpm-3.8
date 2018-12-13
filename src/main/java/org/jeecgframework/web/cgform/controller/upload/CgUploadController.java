package org.jeecgframework.web.cgform.controller.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.service.upload.CgUploadServiceI;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.service.SystemService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.swftools.SwfToolsUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.PinyinUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 
 * @Title:CgUploadController
 * @description:智能表单文件上传控制器
 * @author 赵俊夫
 * @date Jul 24, 2013 9:10:44 PM
 * @version V1.0
 */
//@Scope("prototype")
@Controller
@RequestMapping("/cgUploadController")
public class CgUploadController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CgUploadController.class);
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private CgUploadServiceI cgUploadService;

	/**
	 * 保存文件
	 * @param request
	 * @param response
	 * @param cgUploadEntity 智能表单文件上传实体
	 * @return
	 */
	@RequestMapping(params = "saveFiles", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveFiles(HttpServletRequest request, HttpServletResponse response, CgUploadEntity cgUploadEntity) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
		String id = oConvertUtils.getString(request.getParameter("cgFormId"));//动态表主键ID
		String tableName = oConvertUtils.getString(request.getParameter("cgFormName"));//动态表名
		String cgField = oConvertUtils.getString(request.getParameter("cgFormField"));//动态表上传控件字段
		logger.info("--cgUploadController--saveFiles--上传文件-----"+"{id:"+id+"}  {tableName："+tableName+"}  {cgField:"+cgField+"}");
		if(!StringUtil.isEmpty(id)){
			cgUploadEntity.setCgformId(id);
			cgUploadEntity.setCgformName(tableName);
			cgUploadEntity.setCgformField(cgField);
		}
		if (StringUtil.isNotEmpty(fileKey)) {
			cgUploadEntity.setId(fileKey);
			cgUploadEntity = systemService.getEntity(CgUploadEntity.class, fileKey);
		}
		UploadFile uploadFile = new UploadFile(request, cgUploadEntity);
		uploadFile.setCusPath("files");
		uploadFile.setSwfpath("swfpath");
		uploadFile.setByteField(null);//不存二进制内容
		cgUploadEntity = systemService.uploadFile(uploadFile);
		logger.info("--cgUploadController--saveFiles--上传文件----数据库保存转换成功-----");
		//update--begin--author:zhangjiaqiang date:20170531 for:修订文件保存数据库的路径
		String realPath = cgUploadEntity.getRealpath();
		realPath = realPath.replace(File.separator, "/");
		cgUploadService.writeBack(id, tableName, cgField, fileKey, realPath);
		logger.info("--cgUploadController--saveFiles--上传文件----回写业务数据表字段文件路径-----");
		//update--end--author:zhangjiaqiang date:20170531 for:修订文件保存数据库的路径
		attributes.put("url", realPath);
		attributes.put("name", cgUploadEntity.getAttachmenttitle());
		attributes.put("fileKey", cgUploadEntity.getId());
		attributes.put("viewhref", "commonController.do?objfileList&fileKey=" + cgUploadEntity.getId());
		attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + cgUploadEntity.getId());
		j.setMsg("操作成功");
		j.setAttributes(attributes);
		logger.info("--cgUploadController--saveFiles--上传文件----操作成功-----");
		return j;
	}
	
	/**
	 * 删除文件
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delFile")
	@ResponseBody
	public AjaxJson delFile( HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id  = request.getParameter("id");
		CgUploadEntity file = systemService.getEntity(CgUploadEntity.class, id);
		//update--begin--author:zhangjiaqiang date:20170608 for:删除资源目录当中的文件信息的时候，同时删除数据信息
		String sql  = "select " + file.getCgformField() + " from " + file.getCgformName() + " where id = '" + file.getCgformId() + "'";
		List<Object> cgformFieldResult = systemService.findListbySql(sql);
		if(cgformFieldResult != null && !cgformFieldResult.isEmpty() && cgformFieldResult.get(0) != null){
			String path = cgformFieldResult.get(0).toString();
			String realPath = file.getRealpath();
			realPath = realPath.replace(File.separator, "/");
			boolean updateFlag = false;
			if(path.equals(realPath)){
				//获取这个关联的其他文件信息
				String hql = "from CgUploadEntity where cgformId = ?  and cgformField = ?  and cgformName = ?";
				List<CgUploadEntity> uploadList = systemService.findHql(hql,file.getCgformId(),file.getCgformField(),file.getCgformName());
				if(uploadList != null && !uploadList.isEmpty() && uploadList.size() > 1){
					for (CgUploadEntity cgUploadEntity : uploadList) {
						if(!file.getId().equals(cgUploadEntity.getId())){
							realPath = cgUploadEntity.getRealpath();
							realPath = realPath.replace(File.separator, "/");
							cgUploadService.writeBack(file.getCgformId(), file.getCgformName(), file.getCgformField(), file.getId(), realPath);
							updateFlag = true;
							break;
						}
					}
				}
			}
			if(!updateFlag){
				cgUploadService.writeBack(file.getCgformId(), file.getCgformName(), file.getCgformField(), file.getId(), "");
			}
		}
		//update--end--author:zhangjiaqiang date:20170608 for:删除资源目录当中的文件信息的时候，同时删除数据信息
		message = "" + file.getAttachmenttitle() + "被删除成功";
		cgUploadService.deleteFile(file);
		systemService.addLog(message, Globals.Log_Type_DEL,Globals.Log_Leavel_INFO);
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}
	
	//update--begin--author:zhangjiaqiang date:20170606 for:添加一种增加文件上传的方式，采用attachment的保存处理
	/**
	 * 自动上传保存附件资源的方式
	 * @return
	 */
	@RequestMapping(params = "ajaxSaveFile")
	@ResponseBody
	public AjaxJson ajaxSaveFile(MultipartHttpServletRequest request) {
		AjaxJson ajaxJson = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		try {
			Map<String, MultipartFile> fileMap = request.getFileMap();
			String uploadbasepath = ResourceUtil.getConfigByName("uploadpath");
			// 文件数据库保存路径
			String path = uploadbasepath + "/";// 文件保存在硬盘的相对路径
			String realPath = request.getSession().getServletContext().getRealPath("/") + "/" + path;// 文件的硬盘真实路径
			realPath += DateUtils.getDataString(DateUtils.yyyyMMdd) + "/";
			path += DateUtils.getDataString(DateUtils.yyyyMMdd) + "/";
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdirs();// 创建文件时间子目录
			}
			if(fileMap != null && !fileMap.isEmpty()){
				for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
					MultipartFile mf = entity.getValue();// 获取上传文件对象
					String fileName = mf.getOriginalFilename();// 获取文件名
					String swfName = PinyinUtil.getPinYinHeadChar(oConvertUtils.replaceBlank(FileUtils.getFilePrefix(fileName)));// 取文件名首字母作为SWF文件名
					String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
					String noextfilename=DateUtils.getDataString(DateUtils.yyyymmddhhmmss)+StringUtil.random(8);//自定义文件名称
					String myfilename=noextfilename+"."+extend;//自定义文件名称
					String savePath = realPath + myfilename;// 文件保存全路径
					write2Disk(mf, extend, savePath);
					TSAttachment attachment = new TSAttachment();
					attachment.setId(UUID.randomUUID().toString().replace("-", ""));
					attachment.setAttachmenttitle(fileName);
					attachment.setCreatedate(new Timestamp(new Date().getTime()));
					attachment.setExtend(extend);
					attachment.setRealpath(path + myfilename);
					//update--begin--author:zhangjiaqiang date:@0170703 for:增加转换swf功能
					attachment.setSwfpath( path + FileUtils.getFilePrefix(myfilename) + ".swf");
					SwfToolsUtil.convert2SWF(savePath);
					//update--end--author:zhangjiaqiang date:@0170703 for:增加转换swf功能
					systemService.save(attachment);
					attributes.put("url", path + myfilename);
					attributes.put("name", fileName);
					attributes.put("swfpath", attachment.getSwfpath());
				}
			}
			ajaxJson.setAttributes(attributes);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg(e.getMessage());
		}
		return ajaxJson;
	}
	
	/**
	 * 保存文件的具体操作
	 * @param mf
	 * @param extend
	 * @param savePath
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private void write2Disk(MultipartFile mf, String extend, String savePath)
			throws IOException, UnsupportedEncodingException, FileNotFoundException {
		File savefile = new File(savePath);
		if("txt".equals(extend)){
			//利用utf-8字符集的固定首行隐藏编码原理
			//Unicode:FF FE   UTF-8:EF BB   
			byte[] allbytes = mf.getBytes();
			try{
				String head1 = toHexString(allbytes[0]);
				String head2 = toHexString(allbytes[1]);
				if("ef".equals(head1) && "bb".equals(head2)){
					//UTF-8
					String contents = new String(mf.getBytes(),"UTF-8");
					if(StringUtils.isNotBlank(contents)){
						OutputStream out = new FileOutputStream(savePath);
						out.write(contents.getBytes());
						out.close();
					}
				}  else {
					//GBK
					String contents = new String(mf.getBytes(),"GBK");
					OutputStream out = new FileOutputStream(savePath);
					out.write(contents.getBytes());
					out.close();
				}
			  } catch(Exception e){
				  String contents = new String(mf.getBytes(),"UTF-8");
					if(StringUtils.isNotBlank(contents)){
						OutputStream out = new FileOutputStream(savePath);
						out.write(contents.getBytes());
						out.close();
					}
			}
		} else {
			FileCopyUtils.copy(mf.getBytes(), savefile);
		}
	}
	
	private String toHexString(int index){
        String hexString = Integer.toHexString(index);   
        // 1个byte变成16进制的，只需要2位就可以表示了，取后面两位，去掉前面的符号填充   
        hexString = hexString.substring(hexString.length() -2);  
        return hexString;
	}
	
	//update--end--author:zhangjiaqiang date:20170606 for:添加一种增加文件上传的方式，采用attachment的保存处理
	
}
