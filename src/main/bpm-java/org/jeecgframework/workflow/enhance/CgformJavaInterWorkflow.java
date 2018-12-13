package org.jeecgframework.workflow.enhance;

import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.constant.DataBaseConstant;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.jeecgframework.workflow.pojo.base.TSBusConfig;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.stereotype.Service;


/**
 * 描述：流程提交
 * @author：zhoujf
 * @since：2017-3-17 下午04:47:19
 * @version:1.0
 */
@Service("cgformJavaInterWorkflow")
public class CgformJavaInterWorkflow implements CgformEnhanceJavaInter {
    @Override
    public void execute(String tableName,Map map) throws BusinessException {
    	LogUtil.info("============调用[java增强]提交流程 开始!========tableName:"+tableName+"===map==="+map);
    	try {
			ActivitiService activitiService = ApplicationContextUtil.getContext().getBean(ActivitiService.class);
			SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
			DataBaseService dataBaseService = ApplicationContextUtil.getContext().getBean(DataBaseService.class);
			ActivitiDao activitiDao = ApplicationContextUtil.getContext().getBean(ActivitiDao.class);
			String configId = tableName;
			String id = (String)map.get("id");
			if(id==null){
				id = (String)map.get("ID");
			}
			TSBusConfig tsBusbase =  systemService.findUniqueByProperty(TSBusConfig.class, "onlineId",configId);
			//获取业务数据，并且加载到流程变量中
			Map<String,Object> dataForm = dataBaseService.findOneForJdbc(configId, id);
			dataForm.put(WorkFlowGlobals.BPM_DATA_ID, id);
			//获取发起流程配置的表单地址
			String BPM_FORM_CONTENT_URL  = activitiDao.getProcessStartNodeView(tsBusbase.getTPProcess().getId());
			if(oConvertUtils.isEmpty(BPM_FORM_CONTENT_URL)){
				//update-begin--Author:zhoujf  Date:20170401 for：online表单访问地址更换
//				BPM_FORM_CONTENT_URL = "cgFormBuildController.do?ftlForm&tableName="+configId+"&mode=read&load=detail&id="+id;
				BPM_FORM_CONTENT_URL = "cgFormBuildController/ftlForm/"+configId+"/goDetail.do?id="+id;
				//update-end--Author:zhoujf  Date:20170401 for：online表单访问地址更换
			}
			dataForm.put(WorkFlowGlobals.BPM_FORM_CONTENT_URL, BPM_FORM_CONTENT_URL);
			//获取发起流程配置的表单地址
			String BPM_FORM_CONTENT_URL_MOBILE  = activitiDao.getProcessStartNodeViewMobile(tsBusbase.getTPProcess().getId());
			if(oConvertUtils.isEmpty(BPM_FORM_CONTENT_URL_MOBILE)){
				BPM_FORM_CONTENT_URL_MOBILE = "cgFormBuildController.do?mobileForm&tableName="+configId+"&mode=read&load=detail&id="+id;
			}
			dataForm.put(WorkFlowGlobals.BPM_FORM_CONTENT_URL_MOBILE, BPM_FORM_CONTENT_URL_MOBILE);
			dataForm.put(WorkFlowGlobals.BPM_FORM_KEY, configId);
			String create_by = dataForm.get(DataBaseConstant.CREATE_BY_DB).toString();//业务数据创建人
			String data_id = id;//online数据ID
			activitiService.startOnlineProcess(create_by, data_id, dataForm, tsBusbase);
		} catch (ActivitiException e) {
			e.printStackTrace();
			if (e.getMessage().indexOf("no processes deployed with key") != -1) {
				String message = "没有部署流程!,请在流程配置中部署流程!";
				LogUtil.info("============调用[java增强]提交流程 异常!========"+message);
				throw new BusinessException(message);
			} else if (e.getMessage().indexOf("Error while evaluating expression") != -1) {
				String message = "启动流程失败,流程表达式异常!";
				try {
					message += e.getCause().getCause().getMessage();
				} catch (Exception e1) {
				}
				LogUtil.info("============调用[java增强]提交流程 异常!========"+message);
				throw new BusinessException(message);
			} else {
				String message = "启动流程失败!请确认流程和表单是否关联!";
				LogUtil.info("============调用[java增强]提交流程 异常!========"+message);
				throw new BusinessException(message);
			}
		}  catch (BusinessException e) {
			e.printStackTrace();
			String message = "启动流程失败:"+e.getMessage();
			LogUtil.info("============调用[java增强]提交流程 异常!========"+message);
			throw new BusinessException(message);
		} catch (Exception e) {
			e.printStackTrace();
			String message = "启动流程失败!,请确认流程和表单是否关联!";
			LogUtil.info("============调用[java增强]提交流程 异常!========"+message);
			throw new BusinessException(message);
		}finally{
			LogUtil.info("============调用[java增强]提交流程 结束!========tableName:"+tableName+"===map==="+map);
		}
    }
}
