package org.jeecgframework.core.common.dao.impl;


import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.Query;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.dao.IGenericBaseCommonDao;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.extend.swftools.SwfToolsUtil;
import org.jeecgframework.core.extend.template.DataSourceMap;
import org.jeecgframework.core.extend.template.Template;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

/**
 * 公共扩展方法
 * @author  张代浩
 *
 */
@Repository
public class CommonDao extends GenericBaseCommonDao implements ICommonDao, IGenericBaseCommonDao {

	/**
	 * 检查用户是否存在
	 * */
	public TSUser getUserByUserIdAndUserNameExits(TSUser user) {
		String password = PasswordUtil.encrypt(user.getUserName(), user.getPassword(), PasswordUtil.getStaticSalt());
		String query = "from TSUser u where u.userName = :username and u.password=:passowrd";
		Query queryObject = getSession().createQuery(query);
		queryObject.setParameter("username", user.getUserName());
		queryObject.setParameter("passowrd", password);
		List<TSUser> users = queryObject.list();
		//update-start-Author:jg_renjie  Date:20151220 for：配合TASK #804 【基础权限】切换用户，session刷新后，重新调用该方法时，password值为加密后的值,故不需要加密
		if (users != null && users.size() > 0) {
			return users.get(0);
		} else {
			queryObject = getSession().createQuery(query);
			queryObject.setParameter("username", user.getUserName());
			queryObject.setParameter("passowrd", user.getPassword());
			users = queryObject.list();
			if(users != null && users.size() > 0){
				return users.get(0);
			}
		}
		//update-end-Author:jg_renjie  Date:20151220 for：配合TASK #804 【基础权限】切换用户，session刷新后，重新调用该方法时，password值为加密后的值，故不需要加密
		return null;
	}
	
	/**
	 * 检查用户是否存在
	 * */
	public TSUser findUserByAccountAndPassword(String username,String inpassword) {
		String password = PasswordUtil.encrypt(username, inpassword, PasswordUtil.getStaticSalt());
		String query = "from TSUser u where u.userName = :username and u.password=:passowrd";
		Query queryObject = getSession().createQuery(query);
		queryObject.setParameter("username", username);
		queryObject.setParameter("passowrd", password);
		@SuppressWarnings("unchecked")
		List<TSUser> users = queryObject.list();
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}
	
	/**
	 * admin账户初始化
	 */
	public void pwdInit(TSUser user,String newPwd){
		String query ="from TSUser u where u.userName = :username ";
		Query queryObject = getSession().createQuery(query);
		queryObject.setParameter("username", user.getUserName());
		List<TSUser> users =  queryObject.list();
		if(null != users && users.size() > 0){
			user = users.get(0);
			String pwd = PasswordUtil.encrypt(user.getUserName(), newPwd, PasswordUtil.getStaticSalt());
			user.setPassword(pwd);
			save(user);
		}
		
	}
	

	public String getUserRole(TSUser user) {
		String userRole = "";
		List<TSRoleUser> sRoleUser = findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		for (TSRoleUser tsRoleUser : sRoleUser) {
			userRole += tsRoleUser.getTSRole().getRoleCode() + ",";
		}
		return userRole;
	}


	/**
	 * 文件上传
	 * 
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object uploadFile(UploadFile uploadFile) {
		Object object = uploadFile.getObject();
		if(uploadFile.getFileKey()!=null)
		{
			updateEntitie(object);
		}
		else {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			ReflectHelper reflectHelper = new ReflectHelper(uploadFile.getObject());
			String uploadbasepath = uploadFile.getBasePath();// 文件上传根目录
			if (uploadbasepath == null) {
				uploadbasepath = ResourceUtil.getConfigByName("uploadpath");
			}
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径
			String path = uploadbasepath + "/";// 文件保存在硬盘的相对路径
			String realPath = uploadFile.getMultipartRequest().getSession().getServletContext().getRealPath("/") + "/" + path;// 文件的硬盘真实路径
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdirs();// 创建根目录
			}
			if (uploadFile.getCusPath() != null) {
				realPath += uploadFile.getCusPath() + "/";
				path += uploadFile.getCusPath() + "/";
				file = new File(realPath);
				if (!file.exists()) {
					file.mkdirs();// 创建文件自定义子目录
				}
			}
			else {
				realPath += DateUtils.getDataString(DateUtils.yyyyMMdd) + "/";
				path += DateUtils.getDataString(DateUtils.yyyyMMdd) + "/";
				file = new File(realPath);
				if (!file.exists()) {
					file.mkdir();// 创建文件时间子目录
				}
			}
			String entityName = uploadFile.getObject().getClass().getSimpleName();
			// 设置文件上传路径
			if (entityName.equals("TSTemplate")) {
				realPath = uploadFile.getMultipartRequest().getSession().getServletContext().getRealPath("/") + ResourceUtil.getConfigByName("templatepath") + "/";
				path = ResourceUtil.getConfigByName("templatepath") + "/";
			} else if (entityName.equals("TSIcon")) {
				realPath = uploadFile.getMultipartRequest().getSession().getServletContext().getRealPath("/") + uploadFile.getCusPath() + "/";
				path = uploadFile.getCusPath() + "/";
			}
			String fileName = "";
			String swfName = "";
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();// 获取上传文件对象
				fileName = mf.getOriginalFilename();// 获取文件名
				swfName = PinyinUtil.getPinYinHeadChar(oConvertUtils.replaceBlank(FileUtils.getFilePrefix(fileName)));// 取文件名首字母作为SWF文件名
				String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
				String myfilename="";
				String noextfilename="";//不带扩展名
				if(uploadFile.isRename())
				{
				   
				   noextfilename=DateUtils.getDataString(DateUtils.yyyymmddhhmmss)+StringUtil.random(8);//自定义文件名称
				   myfilename=noextfilename+"."+extend;//自定义文件名称
				}
				else {
				  myfilename=fileName;
				}
				
				String savePath = realPath + myfilename;// 文件保存全路径
				String fileprefixName = FileUtils.getFilePrefix(fileName);
				if (uploadFile.getTitleField() != null) {
					reflectHelper.setMethodValue(uploadFile.getTitleField(), fileprefixName);// 动态调用set方法给文件对象标题赋值
				}
				if (uploadFile.getExtend() != null) {
					// 动态调用 set方法给文件对象内容赋值
					reflectHelper.setMethodValue(uploadFile.getExtend(), extend);
				}
				if (uploadFile.getByteField() != null) {
					// 二进制文件保存在数据库中
					//---update-begin-----author:scott--date:20160503----for:文件流不做存储----------------
//					reflectHelper.setMethodValue(uploadFile.getByteField(), StreamUtils.InputStreamTOByte(mf.getInputStream()));
					//---update-end-----author:scott--date:20160503----for:文件流不做存储----------------
				}
				File savefile = new File(savePath);
				if (uploadFile.getRealPath() != null) {
					// 设置文件数据库的物理路径
					reflectHelper.setMethodValue(uploadFile.getRealPath(), path + myfilename);
				}
				saveOrUpdate(object);
				// 文件拷贝到指定硬盘目录
		//update-begin--Author:jg_renjie  Date:20150607 for：上传下载功能上传UTF-8字符集TXT文件预览出现乱码的错误
					if("txt".equals(extend)){
						//利用utf-8字符集的固定首行隐藏编码原理
						//Unicode:FF FE   UTF-8:EF BB   
						byte[] allbytes = mf.getBytes();
						try{
							String head1 = toHexString(allbytes[0]);
							//System.out.println(head1);
							String head2 = toHexString(allbytes[1]);
							//System.out.println(head2);
							if("ef".equals(head1) && "bb".equals(head2)){
								//UTF-8
								String contents = new String(mf.getBytes(),"UTF-8");
								if(StringUtils.isNotBlank(contents)){
									OutputStream out = new FileOutputStream(savePath);
									out.write(contents.getBytes());
									out.close();
								}
							}  else {
								//update-begin--Author:zhoujf  Date:20150610 for：TXT文件预览出现乱码的错误
								//GBK
								String contents = new String(mf.getBytes(),"GBK");
								OutputStream out = new FileOutputStream(savePath);
								out.write(contents.getBytes());
								out.close();
								//update-end--Author:zhoujf  Date:20150610 for：TXT文件预览出现乱码的错误
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
		  //update-begin--Author:jg_renjie  Date:20150607 for：上传下载功能上传UTF-8字符集TXT文件预览出现乱码的错误
				
//				if (uploadFile.getSwfpath() != null) {
//					// 转SWF
//					reflectHelper.setMethodValue(uploadFile.getSwfpath(), path + swfName + ".swf");
//					SwfToolsUtil.convert2SWF(savePath);
//				}
//				FileCopyUtils.copy(mf.getBytes(), savefile);
				
				//update-begin--Author:scott  Date:20180726 for：增加全局开关，是否默认开启swf在线文档预览转换功能--------------
				//默认上传文件是否转换为swf，实现在线预览功能开关
				String globalSwfTransformFlag = ResourceUtil.getConfigByName("swf.transform.flag");
				if ( "true".equals(globalSwfTransformFlag) && uploadFile.getSwfpath() != null) {
					// 转SWF
					reflectHelper.setMethodValue(uploadFile.getSwfpath(), path + FileUtils.getFilePrefix(myfilename) + ".swf");
					SwfToolsUtil.convert2SWF(savePath);
				}
				 //update-end--Author:scott  Date:20180726 for：增加全局开关，是否默认开启swf在线文档预览转换功能--------------

			}
		} catch (Exception e1) {
		}
		}
		return object;
	}
    
	private String toHexString(int index){
        String hexString = Integer.toHexString(index);   
        // 1个byte变成16进制的，只需要2位就可以表示了，取后面两位，去掉前面的符号填充   
        hexString = hexString.substring(hexString.length() -2);  
        return hexString;
	}
	
	
	/**
	 * 文件下载或预览
	 * 
	 * @param request
	 * @throws Exception
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile) {
		uploadFile.getResponse().setContentType("UTF-8");
		uploadFile.getResponse().setCharacterEncoding("UTF-8");
		InputStream bis = null;
		BufferedOutputStream bos = null;
		HttpServletResponse response = uploadFile.getResponse();
		HttpServletRequest request = uploadFile.getRequest();
		String ctxPath = request.getSession().getServletContext().getRealPath("/");
		String downLoadPath = "";
		long fileLength = 0;
		try {
			if (uploadFile.getRealPath() != null&&uploadFile.getContent() == null) {
				downLoadPath = ctxPath + uploadFile.getRealPath();
				fileLength = new File(downLoadPath).length();
				try {
					bis = new BufferedInputStream(new FileInputStream(downLoadPath));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				if (uploadFile.getContent() != null)
					bis = new ByteArrayInputStream(uploadFile.getContent());
					fileLength = uploadFile.getContent().length;
			}
		
			if (!uploadFile.isView() && uploadFile.getExtend() != null) {
				if (uploadFile.getExtend().equals("text")) {
					response.setContentType("text/plain;");
				} else if (uploadFile.getExtend().equals("doc")) {
					response.setContentType("application/msword;");
				} else if (uploadFile.getExtend().equals("xls")) {
					response.setContentType("application/ms-excel;");
				} else if (uploadFile.getExtend().equals("pdf")) {
					response.setContentType("application/pdf;");
				} else if (uploadFile.getExtend().equals("jpg") || uploadFile.getExtend().equals("jpeg")) {
					response.setContentType("image/jpeg;");
				} else {
					response.setContentType("application/x-msdownload;");
				}
				response.setHeader("Content-disposition", "attachment; filename=" + new String((uploadFile.getTitleField() + "." + uploadFile.getExtend()).getBytes("GBK"), "ISO8859-1"));
				response.setHeader("Content-Length", String.valueOf(fileLength));
			}
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	public Map<Object, Object> getDataSourceMap(Template template) {
		return DataSourceMap.getDataSourceMap();
	}

	/**
	 * 生成XML importFile 导出xml工具类
	 */
	public HttpServletResponse createXml(ImportFile importFile) {
		HttpServletResponse response = importFile.getResponse();
		HttpServletRequest request = importFile.getRequest();
		try {
			// 创建document对象
			Document document = DocumentHelper.createDocument();
			document.setXMLEncoding("UTF-8");
			// 创建根节点
			String rootname = importFile.getEntityName() + "s";
			Element rElement = document.addElement(rootname);
			Class entityClass = importFile.getEntityClass();
			String[] fields = importFile.getField().split(",");
			// 得到导出对象的集合
			List objList = loadAll(entityClass);
			Class classType = entityClass.getClass();
			for (Object t : objList) {
				Element childElement = rElement.addElement(importFile.getEntityName());
				for (int i = 0; i < fields.length; i++) {
					String fieldName = fields[i];
					// 第一为实体的主键
					if (i == 0) {
						childElement.addAttribute(fieldName, String.valueOf(TagUtil.fieldNametoValues(fieldName, t)));
					} else {
						Element name = childElement.addElement(fieldName);
						name.setText(String.valueOf(TagUtil.fieldNametoValues(fieldName, t)));
					}
				}

			}
			String ctxPath = request.getSession().getServletContext().getRealPath("");
			File fileWriter = new File(ctxPath + "/" + importFile.getFileName());
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(fileWriter));

			xmlWriter.write(document);
			xmlWriter.close();
			// 下载生成的XML文件
			UploadFile uploadFile = new UploadFile(request, response);
			uploadFile.setRealPath(importFile.getFileName());
			uploadFile.setTitleField(importFile.getFileName());
			uploadFile.setExtend("bak");
			viewOrDownloadFile(uploadFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 解析XML文件将数据导入数据库中
	 */
	@SuppressWarnings("unchecked")
	public void parserXml(String fileName) {
		try {
			File inputXml = new File(fileName);
			Class entityClass;
			// 读取文件
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(inputXml);
			Element employees = document.getRootElement();
			// 遍历根节点下的子节点
			for (Iterator i = employees.elementIterator(); i.hasNext();) {
				Element employee = (Element) i.next();
				// 有实体名反射得到实体类
				entityClass = GenericsUtils.getEntityClass(employee.getName());
				// 得到实体属性
				Field[] fields = TagUtil.getFiled(entityClass);
				// 得到实体的ID
				String id = employee.attributeValue(fields[0].getName());
				// 判断实体是否已存在
				Object obj1 = getEntity(entityClass, id);
				// 实体不存在new个实体
				if (obj1 == null) {
					obj1 = entityClass.newInstance();
				}
				// 根据反射给实体属性赋值
				for (Iterator j = employee.elementIterator(); j.hasNext();) {
					Element node = (Element) j.next();
					for (int k = 0; k < fields.length; k++) {
						if (node.getName().equals(fields[k].getName())) {
							String fieldName = fields[k].getName();
							String stringLetter = fieldName.substring(0, 1).toUpperCase();
							String setName = "set" + stringLetter + fieldName.substring(1);
							Method setMethod = entityClass.getMethod(setName, new Class[] { fields[k].getType() });
							String type = TagUtil.getColumnType(fieldName, fields);
							if (type.equals("int")) {
								setMethod.invoke(obj1, new Integer(node.getText()));
							} else if (type.equals("string")) {
								setMethod.invoke(obj1, node.getText().toString());
							} else if (type.equals("short")) {
								setMethod.invoke(obj1, new Short(node.getText()));
							} else if (type.equals("double")) {
								setMethod.invoke(obj1, new Double(node.getText()));
							} else if (type.equals("Timestamp")) {
								setMethod.invoke(obj1, new Timestamp(DateUtils.str2Date(node.getText(), DateUtils.datetimeFormat).getTime()));
							}
						}
					}
				}
				if (obj1 != null) {
					saveOrUpdate(obj1);
				} else {
					save(obj1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据模型生成JSON
	 * 
	 * @param all
	 *            全部对象
	 * @param in
	 *            已拥有的对象
	 * @param comboBox
	 *            模型
	 * @return
	 */
	public List<ComboTree> comTree(List<TSDepart> all, ComboTree comboTree) {
		List<ComboTree> trees = new ArrayList<ComboTree>();
		for (TSDepart depart : all) {
			trees.add(tree(depart, true));
		}
		return trees;

	}

	@SuppressWarnings("unchecked")
	public ComboTree tree(TSDepart depart, boolean recursive) {
		ComboTree tree = new ComboTree();
		tree.setId(oConvertUtils.getString(depart.getId()));
		tree.setText(depart.getDepartname());
		List<TSDepart> departsList = findByProperty(TSDepart.class, "TSPDepart.id", depart.getId());
		if (departsList != null && departsList.size() > 0) {
			tree.setState("closed");
			tree.setChecked(false);
			if (recursive) {// 递归查询子节点
				List<TSDepart> departList = new ArrayList<TSDepart>(departsList);
				//Collections.sort(departList, new SetListSort());// 排序
				List<ComboTree> children = new ArrayList<ComboTree>();
				for (TSDepart d : departList) {
					ComboTree t = tree(d, true);
					children.add(t);
				}
				tree.setChildren(children);
			}
		}
		return tree;
	}

	public List<ComboTree> ComboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive) {
		List<ComboTree> trees = new ArrayList<ComboTree>();
		for (Object obj : all) {
			trees.add(comboTree(obj, comboTreeModel, in, recursive));
		}
		//update-begin--Author:scott  Date:20160530 for：清空降低缓存占用
		all.clear();
		//update-end--Author:scott  Date:20160530 for：清空降低缓存占用
		return trees;

	}

    /**
     * 构建ComboTree
     * @param obj
     * @param comboTreeModel
     * @param in
     * @param recursive 是否递归子节点
     * @return
     */
	private ComboTree comboTree(Object obj, ComboTreeModel comboTreeModel, List in, boolean recursive) {
		ComboTree tree = new ComboTree();
		Map<String, Object> attributes = new HashMap<String, Object>();
		ReflectHelper reflectHelper = new ReflectHelper(obj);
		String id = oConvertUtils.getString(reflectHelper.getMethodValue(comboTreeModel.getIdField()));
		tree.setId(id);
		tree.setText(oConvertUtils.getString(reflectHelper.getMethodValue(comboTreeModel.getTextField())));
		if (comboTreeModel.getSrcField() != null) {
			attributes.put("href", oConvertUtils.getString(reflectHelper.getMethodValue(comboTreeModel.getSrcField())));
			tree.setAttributes(attributes);
		}
		if (in == null) {
		} else {
			if (in.size() > 0) {
				for (Object inobj : in) {
					ReflectHelper reflectHelper2 = new ReflectHelper(inobj);
					String inId = oConvertUtils.getString(reflectHelper2.getMethodValue(comboTreeModel.getIdField()));
					//update-begin--Author:JueYue  Date:20140514 for：==不起作用--------------------
                    if (inId.equals(id)) {
						tree.setChecked(true);
					}
                    //update-end--Author:JueYue  Date:20140514 for：==不起作用--------------------
				}
			}
		}

//            update-begin--Author:zhangguoming  Date:20140819 for：递归子节点属性
		List curChildList = (List) reflectHelper.getMethodValue(comboTreeModel.getChildField());
		if (curChildList != null && curChildList.size() > 0) {
			tree.setState("closed");
			tree.setChecked(false);

            if (recursive) { // 递归查询子节点
                List<ComboTree> children = new ArrayList<ComboTree>();
                List nextChildList = new ArrayList(curChildList);
                for (Object childObj : nextChildList) {
                    ComboTree t = comboTree(childObj, comboTreeModel, in, recursive);
                    children.add(t);
                }
                tree.setChildren(children);
            }
        }
//            update-end--Author:zhangguoming  Date:20140819 for：递归子节点属性
		
		//update-begin--Author:scott  Date:20160530 for：清空降低缓存占用
		if(curChildList!=null){
			curChildList.clear();
		}
		//update-end--Author:scott  Date:20140819 for：清空降低缓存占用
		return tree;
	}
	/**
	 * 构建树形数据表
	 */
	public List<TreeGrid> treegrid(List<?> all, TreeGridModel treeGridModel) {
		List<TreeGrid> treegrid = new ArrayList<TreeGrid>();
		for (Object obj : all) {
			ReflectHelper reflectHelper = new ReflectHelper(obj);
			TreeGrid tg = new TreeGrid();
			String id = oConvertUtils.getString(reflectHelper.getMethodValue(treeGridModel.getIdField()));
			String src = oConvertUtils.getString(reflectHelper.getMethodValue(treeGridModel.getSrc()));
			String text = oConvertUtils.getString(reflectHelper.getMethodValue(treeGridModel.getTextField()));
			if(StringUtils.isNotEmpty(treeGridModel.getOrder())){
				String order = oConvertUtils.getString(reflectHelper.getMethodValue(treeGridModel.getOrder()));
				tg.setOrder(order);
			}
			tg.setId(id);
			if (treeGridModel.getIcon() != null) {
				String iconpath = TagUtil.fieldNametoValues(treeGridModel.getIcon(), obj).toString();
				if (iconpath != null) {
					tg.setCode(iconpath);
				} else {
					tg.setCode("");
				}
			}
			tg.setSrc(src);
			tg.setText(text);
			if (treeGridModel.getParentId() != null) {
				Object pid = TagUtil.fieldNametoValues(treeGridModel.getParentId(), obj);
				if (pid != null) {
					tg.setParentId(pid.toString());
				} else {
					tg.setParentId("");
				}
			}
			if (treeGridModel.getParentText() != null) {
				Object ptext = TagUtil.fieldNametoValues(treeGridModel.getTextField(), obj);
				if (ptext != null) {
					tg.setParentText(ptext.toString());
				} else {
					tg.setParentText("");
				}

			}
			List childList = (List) reflectHelper.getMethodValue(treeGridModel.getChildList());

			if (childList != null && childList.size() > 0) {
				tg.setState("closed");
			}
			if (treeGridModel.getRoleid() != null) {
				String[] opStrings = {};
				List<TSRoleFunction> roleFunctions = findByProperty(TSRoleFunction.class, "TSFunction.id", id);

				if (roleFunctions.size() > 0) {
					for (TSRoleFunction tRoleFunction : roleFunctions) {
						TSRoleFunction roleFunction = tRoleFunction;
						if (roleFunction.getTSRole().getId().toString().equals(treeGridModel.getRoleid())) {
							String bbString = roleFunction.getOperation();
							if (bbString != null) {
								opStrings = bbString.split(",");
								break;
							}
						}
					}
				}
				List<TSOperation> operateions = findByProperty(TSOperation.class, "TSFunction.id", id);
				StringBuffer attributes = new StringBuffer();
				if (operateions.size() > 0) {
					for (TSOperation tOperation : operateions) {
						if (opStrings.length < 1) {
							attributes.append("<input type=checkbox name=operatons value=" + tOperation.getId() + "_" + id + ">" + tOperation.getOperationname());
						} else {
							StringBuffer sb = new StringBuffer();
							sb.append("<input type=checkbox name=operatons");
							for (int i = 0; i < opStrings.length; i++) {
								if (opStrings[i].equals(tOperation.getId().toString())) {
									sb.append(" checked=checked");
								}
							}
							sb.append(" value=" + tOperation.getId() + "_" + id + ">" + tOperation.getOperationname());
							attributes.append(sb.toString());
						}
					}
				}
				tg.setOperations(attributes.toString());
			}
            if (treeGridModel.getFieldMap() != null) {
                tg.setFieldMap(new HashMap<String, Object>());
                for (Map.Entry<String, Object> entry : treeGridModel.getFieldMap().entrySet()) {
                    Object fieldValue = reflectHelper.getMethodValue(entry.getValue().toString());
                    tg.getFieldMap().put(entry.getKey(), fieldValue);
                }
            }
            //update-begin--Author:anchao  Date:20140822 for：[bugfree号]字段级权限（表单，列表）--------------------
            if (treeGridModel.getFunctionType() != null) {
            	String functionType = oConvertUtils.getString(reflectHelper.getMethodValue(treeGridModel.getFunctionType()));
            	tg.setFunctionType(functionType);
            }
          //update-end--Author:anchao  Date:20140822 for：[bugfree号]字段级权限（表单，列表）--------------------
            
    	    //        update-begin--Author:chenj  Date:20160722 for：添加菜单图标样式
            if(treeGridModel.getIconStyle() != null){
            	String iconStyle = oConvertUtils.getString(reflectHelper.getMethodValue(treeGridModel.getIconStyle()));
            	tg.setIconStyle(iconStyle);
            }
    	    //        update-end--Author:chenj  Date:20160722 for：添加菜单图标样式
			treegrid.add(tg);
		}
		return treegrid;
	}
}
