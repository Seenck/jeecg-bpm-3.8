package org.jeecgframework.web.system.controller.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.MutiLangEntity;
import org.jeecgframework.web.system.service.CacheServiceI;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Title: Controller
 * @Description: 多语言
 * @author Rocky
 * @date 2014-06-28 00:09:31
 * @version V1.0
 * 
 */
//@Scope("prototype")
@Controller
@RequestMapping("/mutiLangController")
public class MutiLangController extends BaseController {
	private static final Logger logger = Logger.getLogger(MutiLangController.class);

	@Autowired
	private MutiLangServiceI mutiLangService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CacheServiceI cacheService;

	/**
	 * 多语言列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "mutiLang")
	public ModelAndView mutiLang(HttpServletRequest request) {
		return new ModelAndView("system/mutilang/mutiLangList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(MutiLangEntity mutiLang, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(MutiLangEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mutiLang, request.getParameterMap());
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除多语言
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(MutiLangEntity mutiLang, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		mutiLang = systemService.getEntity(MutiLangEntity.class, mutiLang.getId());
		message = MutiLangUtil.paramDelSuccess("common.language");
		systemService.delete(mutiLang);
		mutiLangService.initAllMutiLang();
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加多语言
	 * 
	 * @param mutiLang
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(MutiLangEntity mutiLang, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(mutiLang.getId())) {
			message = MutiLangUtil.paramUpdSuccess("common.language");
			MutiLangEntity t = systemService.get(MutiLangEntity.class, mutiLang.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(mutiLang, t);
				systemService.saveOrUpdate(t);
				mutiLangService.initAllMutiLang();
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = MutiLangUtil.paramUpdFail("common.language");
			}
		} else {
			//---update--begin----author:scott----date:20160224---for:国际化配置不允许重复添加（通过语言和key）----------
			if(MutiLangUtil.existLangKey( mutiLang.getLangKey(),mutiLang.getLangCode()))
			{
				message = mutiLangService.getLang("common.langkey.exist");
			}
			//---update--end----author:scott----date:20160224---for:国际化配置不允许重复添加（通过语言和key）----------
			if(StringUtil.isEmpty(message))
			{
				systemService.save(mutiLang);
				message = MutiLangUtil.paramAddSuccess("common.language");
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}
		
		mutiLangService.putMutiLang(mutiLang);
		j.setMsg(message);
		return j;
	}

	/**
	 * 多语言列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(MutiLangEntity mutiLang,
			HttpServletRequest req) {
		if (StringUtil.isNotEmpty(mutiLang.getId())) {
			mutiLang = systemService.getEntity(MutiLangEntity.class, mutiLang.getId());
			req.setAttribute("mutiLangPage", mutiLang);
			mutiLangService.putMutiLang(mutiLang);
		}
		return new ModelAndView("system/mutilang/mutiLang");
	}
	
	
	/**
	 * 刷新前端缓存
	 * @param request
	 */
	@RequestMapping(params = "refreshCach")
	@ResponseBody
	public AjaxJson refreshCach(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		try {
			mutiLangService.refleshMutiLangCach();
			cacheService.clean();
			message = mutiLangService.getLang("common.refresh.success");
		} catch (Exception e) {
			message = mutiLangService.getLang("common.refresh.fail");
		}
		j.setMsg(message);
		return j;
	}
}
