package org.jeecgframework.workflow.common;

import org.apache.commons.lang.CommonRandomUtil;



/**  
* 项目名称：jeecg
* 类名称：Globals   
* 类描述：  全局变量定义
* 创建人：zhoujf      
* @version    
*
 */
public final class WorkFlowGlobals {
	
	/**
	 * 人员类型
	 */
	public static Short User_Normal=1;//正常
	public static Short User_Forbidden=0;//禁用
	public static Short User_ADMIN=-1;//超级管理员
	
	/**
	 *日志级别定义
	 */
	public static Short Log_Leavel_INFO=1;
	public static Short Log_Leavel_WARRING=2;
	public static Short Log_Leavel_ERROR=3;
	 /**
	  * 日志类型
	  */
	 public static Short Log_Type_LOGIN=1; //登陆
	 public static Short Log_Type_EXIT=2;  //退出
	 public static Short Log_Type_INSERT=3; //插入
	 public static Short Log_Type_DEL=4; //删除
	 public static Short Log_Type_UPDATE=5; //更新
	 public static Short Log_Type_UPLOAD=6; //上传
	 public static Short Log_Type_OTHER=7; //其他

	 /**
	  * 流程发布状态
	  */
	public static Short Process_Deploy_NO=0;//未发布
	public static Short Process_Deploy_YES=1;//已发布
	 
	 /**
	  * 流程节点监听映射表状态
	  */
	public static Short Process_Listener_NO=0;//禁用
	public static Short Process_Listener_YES=1;//启用
	 
	 /**
	  * 监听状态值
	  */
	public static Short Listener_No=0;//禁用
	public static Short Listener_Yes=1;//启用
	 
	 /**
	  * 监听器类型
	  */
	public static Short Listener_Type_Task=2;//任务监听器
	public static Short Listener_Type_Excution=1;//执行监听器
	
	 /**
	  * 数据库表名字前缀
	  */
	public static String DataBase_Activiti="act";//引擎表
	public static String DataBase_Base="t_s";//系统基础表
	public static String DataBase_Bus="t_b";//业务表
	public static String DataBase_Process="t_p";//自定义引擎表
	public static String DataBase_Other="other";//其他表
	
	
	/**
	 * BPM_BUS_STATUS[流程业务单据状态位]
	 * 待提交:1/处理中:2/处理完毕:3
	 */
	public static String BPM_BUS_STATUS_1 = "1";//待提交
	public static String BPM_BUS_STATUS_2 = "2";//处理中
	public static String BPM_BUS_STATUS_3 = "3";//处理完毕
	 
	 /**
	  * 用户是否同步到引擎
	  */
	public static Short Activiti_Deploy_NO=0;//不同步
	public static Short Activiti_Deploy_YES=1;//同步
	
	/**
	  * 词典分组定义
	  */
	public static String TypeGroup_Database="database";//数据表分类
	
	public static String ProcNode_Start= "nodeStart";//流程起始节点名称
	
	
	/**
	  * 用户指定节点
	  */
	public static String USER_SELECT_TASK_NODE = "USER_SELECT_TASK_NODE";
	/**
	 * BPM 节点对应的表单URL(全局)
	 */
	public static String BPM_FORM_CONTENT_URL = "BPM_FORM_CONTENT_URL";
	/**
	 * BPM 节点对应的表单URL(全局) - 移动端
	 */
	public static String BPM_FORM_CONTENT_URL_MOBILE = "BPM_FORM_CONTENT_URL_MOBILE";
	
	/**
	 * BPM 节点对应的表单URL(全局)
	 */
	public static String BPM_STATUS = "bpm_status";
	/**
	 * BPM 业务标题表达式(全局)
	 */
	public static String BPM_BIZ_TITLE = "bpm_biz_title";
	/**
	 * BPM 流程办理风格(全局)
	 */
	public static String BPM_PROC_DEAL_STYLE = "bpm_proc_deal_style";
	//--update-begin---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
	/**
	 * BPM 业务表单类型 表单类型：online:Online表单,mutlflowform:多流程表单,autoform:自定义表单,entity:自定义实体
	 */
	public static String BPM_FORM_TYPE = "BPM_FORM_TYPE";
	/**
	 * BPM 流程对应的流程业务KEY
	 */
	public static String BPM_FORM_BUSINESSKEY = "BPM_FORM_BUSINESSKEY";
	//--update-end---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
	
	//--update-begin---author:zhoujf-----date:20180727---------------for:常量抽取
	public static String BPM_FORM_TYPE_MUTIL_FLOW = "mutlflowform";
	//--update-end---author:zhoujf-----date:20180727---------------for:常量抽取
	/**
	 * BPM 流程对应的表单KEY
	 */
	public static String BPM_FORM_KEY = "BPM_FORM_KEY";
	
	/**
	 * BPM 流程对应的流程实例ID KEY[当前流程]
	 */
	public static String JG_LOCAL_PROCESS_ID = "JG_LOCAL_PROCESS_ID";
	/**
	 * BPM 流程对应的流程实例ID KEY[主流程]
	 */
	public static String JG_SUB_MAIN_PROCESS_ID = "JG_SUB_MAIN_PROCESS_ID";
	
	/**
	 * 业务数据对应ID
	 */
	public static String BPM_DATA_ID = "BPM_DATA_ID";
	/**
	 * form表单参数id的签名
	 */
	public static String BPM_DATA_ID_SIGN = "BPM_DATA_ID_SIGN";
	/**
	 * 第三方表单流程回调地址
	 */
	public static String BPM_API_CALLBACK_URL = "BPM_API_CALLBACK_URL";
	/**
	 * 自定义表单数据列表数据对应ID
	 */
	public static String BPM_AUTO_FORM_DATA_ID = "BPM_AUTO_FORM_DATA_ID";
	
	/**
	 * 会签【用户组常量】
	 */
	public static String ASSIGNEE_USER_ID_LIST = "assigneeUserIdList";
	
	//--update--begin------author:zhoujf-----date:20180125-----for:会签节点选择会签人员校验控制-----------------
	/**
	 * 会签【会签节点指定下一步会签人员校验】
	 */
	public static String NEXT_USER_CHECK_FLAG = "NEXT_USER_CHECK_FLAG";
	//--update--end------author:zhoujf-----date:20180125-----for:会签节点选择会签人员校验控制-----------------
	
	/**
	 * 定位异常
	 */
	public static int INDEX_NOT_FOUND = CommonRandomUtil.INDEX_NOT_FOUND;
	
	/**
	 * BPM 节点对应的表单URL类型--pc电脑端 mobile移动端
	 */
	public static String SUFFIX_BPM_FORM_URL = "pc";
	public static String SUFFIX_BPM_FORM_URL_MOBILE = "mobile";
	//--update-begin---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
	/**
	 * 流程追回状态
	 */
	public static String PROCESS_CALLBACKPROCESS_STATUS = "callBackProcess";
	/**
	 * 流程作废状态
	 */
	public static String PROCESS_INVALIDPROCESS_STATUS = "invalidProcess";
	/**
	 * 流程驳回状态
	 */
	public static String PROCESS_REJECTPROCESS_STATUS = "rejectProcess";
	//--update-end---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
}
