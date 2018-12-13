package org.jeecgframework.workflow.bus.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtil;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.bus.entity.TPKeyConfigEntity;
import org.jeecgframework.workflow.bus.service.TPKeyConfigServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Title: Controller  
 * @Description: 第三方密钥管理
 * @author zhoujf
 * @date 2017-03-30 10:32:20
 * @version V1.0   
 *
 */
@Controller("tPKeyConfigController")
@RequestMapping("/tPKeyConfigController")
public class TPKeyConfigController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPKeyConfigController.class);

	@Autowired
	private TPKeyConfigServiceI tPKeyConfigService;
	@Autowired
	private SystemService systemService;
	
	/**
	 * 第三方密钥管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("workflow/bus/tPKeyConfigList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TPKeyConfigEntity tPKeyConfig,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPKeyConfigEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPKeyConfig, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPKeyConfigService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除第三方密钥管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPKeyConfigEntity tPKeyConfig, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tPKeyConfig = systemService.getEntity(TPKeyConfigEntity.class, tPKeyConfig.getId());
		message = "第三方密钥管理删除成功";
		try{
			tPKeyConfigService.delete(tPKeyConfig);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "第三方密钥管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除第三方密钥管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "第三方密钥管理删除成功";
		try{
			for(String id:ids.split(",")){
				TPKeyConfigEntity tPKeyConfig = systemService.getEntity(TPKeyConfigEntity.class,id);
				tPKeyConfigService.delete(tPKeyConfig);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "第三方密钥管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加第三方密钥管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPKeyConfigEntity tPKeyConfig, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "第三方密钥管理添加成功";
		try{
			tPKeyConfigService.save(tPKeyConfig);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "第三方密钥管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新第三方密钥管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPKeyConfigEntity tPKeyConfig, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "第三方密钥管理更新成功";
		TPKeyConfigEntity t = tPKeyConfigService.get(TPKeyConfigEntity.class, tPKeyConfig.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPKeyConfig, t);
			tPKeyConfigService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "第三方密钥管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 第三方密钥管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TPKeyConfigEntity tPKeyConfig, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPKeyConfig.getId())) {
			tPKeyConfig = tPKeyConfigService.getEntity(TPKeyConfigEntity.class, tPKeyConfig.getId());
			req.setAttribute("tPKeyConfigPage", tPKeyConfig);
		}
		return new ModelAndView("workflow/bus/tPKeyConfig-add");
	}
	/**
	 * 第三方密钥管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPKeyConfigEntity tPKeyConfig, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPKeyConfig.getId())) {
			tPKeyConfig = tPKeyConfigService.getEntity(TPKeyConfigEntity.class, tPKeyConfig.getId());
			req.setAttribute("tPKeyConfigPage", tPKeyConfig);
		}
		return new ModelAndView("workflow/bus/tPKeyConfig-update");
	}
	
}
