package org.jeecgframework.core.common.model.json;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
 
public class TreeGrid implements java.io.Serializable {
	private String id;
	private String text;
 	private String parentId;
 	private String parentText;
 	private String code;
 	private String src;
 	private String note;
	private Map<String,String> attributes;// 其他参数
 	private String  operations;// 其他参数
 	private String state = "open";// 是否展开(open,closed)
 	private String order;//排序
    private Map<String, Object> fieldMap; // 存储实体字段信息容器： key-字段名称，value-字段值
  //update-begin--Author:anchao  Date:20140822 for：[bugfree号]字段级权限（表单，列表）--------------------
    private String  functionType;// 其他参数
    //        update-begin--Author:chenj  Date:20160722 for：添加菜单图标样式
    private String iconStyle;//菜单图表样式
    //        update-end--Author:chenj  Date:20160722 for：添加菜单图标样式
    
	public String getFunctionType() {
		return functionType;
	}
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}
	//update-end--Author:anchao  Date:20140822 for：[bugfree号]字段级权限（表单，列表）--------------------
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getOperations() {
		return operations;
	}
	public void setOperations(String operations) {
		this.operations = operations;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public String getParentText() {
		return parentText;
	}
	public void setParentText(String parentText) {
		this.parentText = parentText;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	 
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}	 
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public String toJson() {
        return "{" +
                "'id':'" + id + '\'' +
                ", 'text':'" + text + '\'' +
                ", 'parentId':'" + parentId + '\'' +
                ", 'parentText':'" + parentText + '\'' +
                ", 'code':'" + code + '\'' +
                ", 'src':'" + src + '\'' +
                ", 'note':'" + note + '\'' +
                ", 'attributes':" + attributes +
                ", 'operations':'" + operations + '\'' +
                ", 'state':'" + state + '\'' +
                ", 'order':'" + order + '\'' +
                    //        update-begin--Author:chenj  Date:20160722 for：添加菜单图标样式
                ", 'iconStyle':'" + iconStyle + '\'' +
                    //        update-end--Author:chenj  Date:20160722 for：添加菜单图标样式
                assembleFieldsJson() +
                '}';
    }

    private String assembleFieldsJson() {
        String fieldsJson = ", 'fieldMap':" + JSON.toJSON(fieldMap);
        if (fieldMap != null && fieldMap.size() > 0) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
                resultMap.put("fieldMap." + entry.getKey(), entry.getValue());
            }
            fieldsJson += ", " + JSON.toJSON(resultMap).toString().replace("{", "").replace("}", "");
        }
        return fieldsJson;
    }
    //        update-begin--Author:chenj  Date:20160722 for：添加菜单图标样式
	public String getIconStyle() {
		return iconStyle;
	}
	public void setIconStyle(String iconStyle) {
		this.iconStyle = iconStyle;
	}
    //        update-end--Author:chenj  Date:20160722 for：添加菜单图标样式
 
}
