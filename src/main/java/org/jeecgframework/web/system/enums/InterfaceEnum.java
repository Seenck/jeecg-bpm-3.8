package org.jeecgframework.web.system.enums;



public enum InterfaceEnum {
	blacklist_list("blacklist_list", "黑名单列表查询", "/rest/tsBlackListController", "GET", 1),
	blacklist_get("blacklist_get", "黑名单单条数据查询", "/rest/tsBlackListController/{id}", "GET", 2),
	blacklist_add("blacklist_add", "黑名单添加", "/rest/tsBlackListController", "POST", 3),
	blacklist_edit("blacklist_edit", "黑名单编辑", "/rest/tsBlackListController", "PUT", 4),
	blacklist_delete("blacklist_delete", "黑名单删除", "/rest/tsBlackListController/{id}", "DELETE", 5)	,
  
	//update-begin-author:taoYan date:20180123 for:TASK#2507:【online开发】online对外接口改造-
	onlineform_get("onlineform_get", "根据tableName和记录ID获取online表单详细信息", "/rest/cgFormDataController/get/{tableName}/{id}", "GET", 1),
	onlineform_add("onlineform_add", "online表单增加一条记录", "/rest/cgFormDataController/add", "POST", 2),
	onlineform_update("onlineform_update", "online表单修改一条记录", "/rest/cgFormDataController/update", "POST", 3),
	onlineform_delete("onlineform_delete", "online表单删除一条记录", "/rest/cgFormDataController/delete/{tableName}/{id}", "DELETE", 4),
	//update-end-author:taoYan date:20180123 for:TASK#2507:【online开发】online对外接口改造-
	
	jeecgdemo_list("jeecgdemo_list", "jeecgDemo列表查询", "/rest/jeecgListDemoController/list", "GET", 1),
	jeecgdemo_get("jeecgdemo_get", "jeecgDemo单条数据查询", "/rest/jeecgListDemoController/{id}", "GET", 2),
	jeecgdemo_add("jeecgdemo_add", "jeecgDemo添加", "/rest/jeecgListDemoController", "POST", 3),
	jeecgdemo_edit("jeecgdemo_edit", "jeecgDemo编辑", "/rest/jeecgListDemoController", "PUT", 4),
	jeecgdemo_delete("jeecgdemo_delete", "jeecgDemo删除", "/rest/jeecgListDemoController/{id}", "DELETE", 5)
	;
	/**
     * 接口编码
     */
    private String code;
    /**
     * 接口名称
     */
    private String name;
    /**
     * 接口url
     */
    private String url;
    /**
     * 接口请求方式
     */
    private String method;
    /**
     * 接口排序
     */
    private Integer sort;


    private InterfaceEnum(String code, String name, String url, String method, Integer sort) {
       
        this.code = code;
        this.name = name;
        this.url = url;
        this.method = method;
        this.sort = sort;
    }
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public static InterfaceEnum toEnum(String code) {

		for(InterfaceEnum item : InterfaceEnum.values()) {
			if(item.getCode().equals(code)) {
				return item;
			}
		}
		//默认风格
		return null;
	}

    public String toString() {
        return "接口名称: " + name + ", 接口编码: " + code +" ";
    }
}
