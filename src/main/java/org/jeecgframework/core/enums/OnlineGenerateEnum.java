package org.jeecgframework.core.enums;

import org.jeecgframework.core.util.StringUtil;


/**
 * 代码生成模板风格配置
 *
 * @author zhoujf
 */
public enum OnlineGenerateEnum {
	

	
	/**
	 * ext ： 标识自定义代码生成器模板（新模式）
	 * code： 标识自定义代码生成器目录 src/main/resources/jeecg/ext-template/{code:替换点为斜杠}
	 * 例如： code = default.single 对应自定义代码生成器目录 -> src/main/resources/jeecg/ext-template/default/single
	 */
//	ONLINE_COMMON_TABLE_SINGLE("table.single","可自定义TABLE风格模板","single","ext-common"),
//	ONLINE_COMMON_TABLE_ONETOMANY("table.onetomany","可自定义TABLE风格模板","onetomany","ext-common"),

	//***********{新一代代码生成器模板}**********************************************
	ONLINE_COMMON_BOOTSTRAP_SINGLE("bootstrap.single","Bootstrap表单+EasyUI标签列表","single","ext-common",false),
	ONLINE_COMMON_BOOTSTRAP_ONETOMANY("bootstrap.onetomany","Bootstrap表单+EasyUI标签列表","onetomany","ext-common",false),

	ONLINE_COMMON_NATUREBT_SINGLE("naturebt.single","Bootstrap表单+EasyUI原生列表","single","ext-common",false),
	ONLINE_COMMON_NATUREBT_ONETOMANY("naturebt.onetomany","ElementUI表单+EasyUI原生列表","onetomany","ext-common",false),
	
	
	//update-begin-author：jiaqiankun date:20180703 for:TASK #2864 【代码生成器】一套新的模板，bootstapTable UI标签列表 + boostrap表单
	ONLINE_COMMON_BOOTSTRAPTABLEUI_SINGLE("bootstraptableUI.single","Boostrap表单+BootstapTable标签列表","single","ext-common",false),
	ONLINE_COMMON_BOOTSTRAPTABLEUI_ONETOMANY("bootstraptableUI.onetomany","Boostrap表单+BootstapTable标签列表","onetomany","ext-common",false),
	//update-end-author：jiaqiankun date:20180703 for:TASK #2864 【代码生成器】一套新的模板，bootstapTable UI标签列表 + boostrap表单
	
	//update-begin-author：jiaqiankun date:20180629 for:TASK #2863 【代码生成器】一套新的模板，bootstapTable原生列表 + boostrap表单
	ONLINE_COMMON_BOOTSTRAPTABLE_SINGLE("bootstraptable.single","Bootstrap表单+BootstrapTable原生列表","single","ext-common",false),
	ONLINE_COMMON_BOOTSTRAPTABLE_ONETOMANY("bootstraptable.onetomany","Bootstrap表单+BootstrapTable原生列表","onetomany","ext-common",false),
	//update-end-author：jiaqiankun date:20180629 for:TASK #2863 【代码生成器】一套新的模板，bootstapTable原生列表 + boostrap表单
	
	
	//船舶订制版本
	ONLINE_MULTI_TABLE_ONETOMANY("multitable.onetomany","Table风格表单+EasyUI标签列表(ERP上下布局风格)","onetomany","ext-common",false),
	
	//ElementUI风格(VUE)
	ONLINE_COMMON_ELEMENT_SINGLE("element.single","VUE+ElementUI风格","single","ext-common",false),
	ONLINE_COMMON_ELEMENT_ONETOMANY("element.onetomany","VUE+ElementUI风格","onetomany","ext-common",false),
	
	
	//***********{老版本代码生成器模板}**********************************************
	ONLINE_TABLE_SINGLE("table.single","TABLE风格表单","single","ext",true),
	ONLINE_TABLE_ONETOMANY("table.onetomany","TABLE风格表单","onetomany","ext",false),
	ONLINE_ACE_SINGLE("ace.single","ACE风格表单","single","ext",true),
	ONLINE_ACE_ONETOMANY("ace.onetomany","ACE风格表单","onetomany","ext",false),
	
	
	ONLINE_DIV_SINGLE("div.single","DIV风格表单","single","ext",true),
	ONLINE_NOPOP_SINGLE("nopop.single","NOPOP风格表单","single","ext",false),
	ONLINE_ROW_SINGLE("rowedit.single","行编辑风格表单","single","ext",false),
	ONLINE_TAB_ONETOMANY("tab.onetomany","TAB风格表单","onetomany","ext",false);
	
	//TODO 暂时不推荐这个版本代码生成器
//	ONLINE_VUEBT_SINGLE("vuebt.single","Bootstrap表单+VUE-Bootstrap-Table列表风格","single","ext");
	
//	ONLINE_DEFAULT_SINGLE("default.single","用户扩展风格示例","single","ext"),
//	ONLINE_DEFAULT_ONETOMANY("default.onetomany","用户扩展风格示例","onetomany","ext");

	
	/**
	 * system: 老的代码生成器模板方式
	 */
//	ONLINE_03("03","TABLE风格(form)","onetomany", "system"),
//	ONLINE_01("01","TABLE风格(form)","single", "system"),
//	ONLINE_02("02","DIV风格(form)","single", "system"),
//	ONLINE_05("05","BootStrap风格(form)","single", "system"),
//	ONLINE_06("06","BootStrap风格(form)","onetomany","system");
//	ONLINE_04("04","自定义模板(form)","single", "system"),
	/*ONLINE_07("07","nopopform风格","single","system"),*/
	

    /**
     * 风格
     */
    private String code;
    /**
     * 描述
     */
    private String desc;
    
    /**
     * 表单类型  single 单表 ，onetomany 一对多
     */
    private String formType;
    /**
     * 类型： system 系统, ext 用户扩展（自定义代码生成器模板）
     */
    private String version;
    
    /**
     * 表单是否支持树形列表
     */
    private boolean supportTreegrid;

    private OnlineGenerateEnum(String code, String desc, String formType, String version,boolean supportTreegrid) {
        this.code = code;
        this.desc = desc;
        this.formType = formType;
        this.version = version;
        this.supportTreegrid = supportTreegrid;
    }

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public boolean isSupportTreegrid() {
		return supportTreegrid;
	}
	public void setSupportTreegrid(boolean supportTreegrid) {
		this.supportTreegrid = supportTreegrid;
	}

	public static OnlineGenerateEnum toEnum(String code) {
		if(StringUtil.isEmpty(code)){
			return null;
		}
		for(OnlineGenerateEnum item : OnlineGenerateEnum.values()) {
			if(item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}

	public static OnlineGenerateEnum toEnum(String code, String version) {
		if(StringUtil.isEmpty(code)){
			return null;
		}
		if(StringUtil.isEmpty(version)){
			return null;
		}
		for(OnlineGenerateEnum item : OnlineGenerateEnum.values()) {
			if(item.getCode().equals(code)&&item.getVersion().equals(version)) {
				return item;
			}
		}
		return null;
	}
	
}
