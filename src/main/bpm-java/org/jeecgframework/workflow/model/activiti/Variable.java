package org.jeecgframework.workflow.model.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jodd.util.StringUtil;
import org.apache.commons.beanutils.ConvertUtils;
import org.jeecgframework.workflow.pojo.base.TPProcesspro;


public class Variable {

	private String keys;
	private String values;
	private String types;
	List<TPProcesspro> myvarialbe;//流程变量定义集合

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	/**
	 * 流程配置：任务节点，只能有一个流转类型的节点
	 * 流程页面：opt的变量，对应着任务节点的流转类型变量
	 * @param myvarialbe
	 * @return
	 */
	public Map<String, Object> getVariableMap(List<TPProcesspro> myvarialbe) {
		this.myvarialbe = myvarialbe;
		Map<String, Object> vars = new HashMap<String, Object>();
		ConvertUtils.register(new DateConverter(), java.util.Date.class);

		if (StringUtil.isBlank(keys)) {
			return vars;
		}

		String[] arrayKey = keys.split(",");
		String[] arrayValue = values.split(",");
		String[] arrayType = types.split(",");
		for (int i = 0; i < arrayKey.length; i++) {
			String key = getKey(arrayKey[i]);
			String value =arrayValue[i];
			String type = arrayType[i];

			Class<?> targetType = Enum.valueOf(PropertyType.class, type).getValue();
			Object objectValue = ConvertUtils.convert(value, targetType);
			vars.put(key, objectValue);
		}
		return vars;
	}
	/**
	 * 获取流程运转中真实KEY
	 * @param key
	 * @return
	 */
	public String getKey(String key) {
		if (myvarialbe.size() > 0) {
			for (TPProcesspro processpro : myvarialbe) {
				//判断key=opt的变量，在流程控制中对应的KEY值
				if (key.equals(processpro.getProcessprotype())) {
					key = processpro.getProcessprokey();
				}
			}
		}
		return key;
	}

}
