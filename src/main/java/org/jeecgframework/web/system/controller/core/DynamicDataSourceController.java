package org.jeecgframework.web.system.controller.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.enums.SysDatabaseEnum;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.DynamicDataSourceEntity;
import org.jeecgframework.web.system.service.DynamicDataSourceServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Title: Controller
 * @Description: 数据源配置
 * @author zhangdaihao
 * @date 2014-09-05 13:22:10
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/dynamicDataSourceController")
public class DynamicDataSourceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DynamicDataSourceController.class);

	@Autowired
	private DynamicDataSourceServiceI dynamicDataSourceService;
	@Autowired
	private SystemService systemService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 数据源配置列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "dbSource")
	public ModelAndView dbSource(HttpServletRequest request) {
		return new ModelAndView("system/dbsource/dbSourceList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(DynamicDataSourceEntity dbSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DynamicDataSourceEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, dbSource, request.getParameterMap());
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);

	}

	/**
	 * 删除数据源配置
	 *
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DynamicDataSourceEntity dbSource, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		dbSource = systemService.getEntity(DynamicDataSourceEntity.class, dbSource.getId());

		message = MutiLangUtil.paramDelSuccess("common.datasource.manage");

		systemService.delete(dbSource);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}


	/**
	 * 添加数据源配置
	 *
	 * @param dbSource
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DynamicDataSourceEntity dbSource, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(dbSource.getId())) {
			message = MutiLangUtil.paramUpdSuccess("common.datasource.manage");
			DynamicDataSourceEntity t = systemService.get(DynamicDataSourceEntity.class, dbSource.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(dbSource, t);
//			      update-start--Author:chenjin  Date:20160711 for：多数据源目前数据库密码是明文，采用加密方式存储
				t.setDbPassword(PasswordUtil.encrypt(t.getDbPassword(), t.getDbUser(), PasswordUtil.getStaticSalt()));
//			      update-end--Author:chenjin  Date:20160711 for：多数据源目前数据库密码是明文，采用加密方式存储
				systemService.saveOrUpdate(t);
				dynamicDataSourceService.refleshCache();
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = MutiLangUtil.paramUpdFail("common.datasource.manage");
			}
		} else {
			message = MutiLangUtil.paramAddSuccess("common.datasource.manage");
			
//		      update-start--Author:chenjin  Date:20160711 for：多数据源目前数据库密码是明文，采用加密方式存储
			try {
				dbSource.setDbPassword(PasswordUtil.encrypt(dbSource.getDbPassword(), dbSource.getDbUser(), PasswordUtil.getStaticSalt()));
			} catch (Exception e) {
				e.printStackTrace();
			}
//		      update-end--Author:chenjin  Date:20160711 for：多数据源目前数据库密码是明文，采用加密方式存储
			systemService.save(dbSource);
			dynamicDataSourceService.refleshCache();
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 数据源配置列表页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DynamicDataSourceEntity dbSource, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(dbSource.getId())) {
			dbSource = systemService.getEntity(DynamicDataSourceEntity.class, dbSource.getId());
//		      update-start--Author:chenjin  Date:20160711 for：多数据源目前数据库密码是明文，采用加密方式存储
			try {
				//String result = PasswordUtil.decrypt(d.getDbPassword(), d.getDbUser(), PasswordUtil.getStaticSalt());
				//System.out.println("==result"+result);
				
				//update-begin--Author:xuelin  Date:20170329 for：[#1821]【bug】多数据源管理，密码采用加密方式存储，加密解密总报错--------------------
				//直接dbSource.setDbPassword hibernate会自动保存修改，数据库值随之改变，因此采用临时变量方式传递到页面
				String showDbPassword = PasswordUtil.decrypt(dbSource.getDbPassword(), dbSource.getDbUser(), PasswordUtil.getStaticSalt());//解密dbPassword
				req.setAttribute("showDbPassword", showDbPassword);
				//update-end--Author:xuelin  Date:20170329 for：[#1821]【bug】多数据源管理，密码采用加密方式存储，加密解密总报错----------------------
				
			} catch (Exception e) {
				e.printStackTrace();
			}
//		      update-end--Author:chenjin  Date:20160711 for：多数据源目前数据库密码是明文，采用加密方式存储
			req.setAttribute("dbSourcePage", dbSource);
		}
		return new ModelAndView("system/dbsource/dbSource");
	}
    //update-begin--Author:张忠亮  Date:20150608 for：获取数据源列表

    /**
     * 获取数据源列表
     * @return
     */
    @RequestMapping(params = "getAll")
    @ResponseBody
    public List<ComboBox> getAll(){
        List<DynamicDataSourceEntity> list= systemService.getList(DynamicDataSourceEntity.class);
        List<ComboBox> comboBoxes=new ArrayList<ComboBox>();
        if(list!=null&&list.size()>0){
            for(DynamicDataSourceEntity entity:list){
                ComboBox comboBox=new ComboBox();
                comboBox.setId(entity.getId());
                comboBox.setText(entity.getDbKey());
                comboBoxes.add(comboBox);
            }
        }
        return  comboBoxes;
    }
    //update-end--Author:张忠亮  Date:20150608 for：获取数据源列表


    //add-begin--Author:	jg_huangxg Date: 20150625 for：[bugfree号]根据字典表数据库类型获取数据源对象
    //update-begin--Author:	jg_huangxg Date: 20150628 for：获取动态数据源的默认值
    @RequestMapping(params = "getDynamicDataSourceParameter")
	@ResponseBody
    public AjaxJson getDynamicDataSourceParameter(@RequestParam String dbType){
    	AjaxJson j = new AjaxJson();
    	SysDatabaseEnum sysDatabaseEnum = SysDatabaseEnum.toEnum(dbType);

    	if (sysDatabaseEnum != null) {
    		Map<String, String> map = new HashMap<String, String>();
        	map.put("driverClass", sysDatabaseEnum.getDriverClass());
        	map.put("url", sysDatabaseEnum.getUrl());
        	map.put("dbtype", sysDatabaseEnum.getDbtype());
        	j.setObj(map);
		}else {
			j.setObj("");
		}

    	return j;
    }
    //update-begin--Author:	jg_huangxg Date: 20150628 for：获取动态数据源的默认值
	//add-end--Author: jg_huangxg Date: 20150625 for：[bugfree号]根据字典表数据库类型获取数据源对象
    
    
  //add-begin--Author:chenj Date: 20160801 for：TASK #1246 【改进】多数据源增加测试有效功能
    @RequestMapping(params = "testConnection")
	@ResponseBody
    public AjaxJson testConnection(DynamicDataSourceEntity dbSource, HttpServletRequest request){
    	AjaxJson j = new AjaxJson();
    	Connection con = null;
    	Map map = new HashMap();
    	try {
			Class.forName(dbSource.getDriverClass());//加载及注册JDBC驱动程序
			//建立连接对象
			con = DriverManager.getConnection(dbSource.getUrl(), dbSource.getDbUser(), dbSource.getDbPassword());
			if(con!=null){
				map.put("msg", "数据库连接成功!!");
			}
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			logger.error(e.toString());
			map.put("msg", "数据库连接失败!!");
		} catch (SQLException e) {
			//e.printStackTrace();
			logger.error(e.toString());
			map.put("msg", "数据库连接失败!!");
		}finally{
			try {
				if(con!=null&&!con.isClosed()){
					con.close();
				}
			} catch (SQLException e) {
				//e.printStackTrace();
				logger.error(e.toString());
			}
		}
    	j.setObj(map);
    	return j;
    }
  //add-end--Author:chenj Date: 20160801 for：TASK #1246 【改进】多数据源增加测试有效功能
    
}
