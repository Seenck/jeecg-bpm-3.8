package org.jeecgframework.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

/**
 * @author  张代浩
 * @desc 通过反射来动态调用get 和 set 方法
 */
public class ReflectHelper {
	private static final Logger logger = Logger.getLogger(ReflectHelper.class);
	
	@SuppressWarnings("rawtypes")
	private Class cls;
	/**
	 * 传过来的对象
	 */
	private Object obj;

	/**
	 * 存放get方法
	 */
	private Hashtable<String, Method> getMethods = null;
	/**
	 * 存放set方法
	 */
	private Hashtable<String, Method> setMethods = null;

	/**
	 * 定义构造方法 -- 一般来说是个pojo
	 * 
	 * @param o
	 *            目标对象
	 */
	public ReflectHelper(Object o) {
		obj = o;
		initMethods();
	}

	/**
	 * 
	 * @desc 初始化
	 */
	public void initMethods() {
		getMethods = new Hashtable<String, Method>();
		setMethods = new Hashtable<String, Method>();
		cls = obj.getClass();
		Method[] methods = cls.getMethods();
		// 定义正则表达式，从方法中过滤出getter / setter 函数.
		String gs = "get(\\w+)";
		Pattern getM = Pattern.compile(gs);
		String ss = "set(\\w+)";
		Pattern setM = Pattern.compile(ss);
		// 把方法中的"set" 或者 "get" 去掉
		String rapl = "$1";
		String param;
		for (int i = 0; i < methods.length; ++i) {
			Method m = methods[i];
			String methodName = m.getName();
			if (Pattern.matches(gs, methodName)) {
				param = getM.matcher(methodName).replaceAll(rapl).toLowerCase();
				getMethods.put(param, m);
			} else if (Pattern.matches(ss, methodName)) {
				param = setM.matcher(methodName).replaceAll(rapl).toLowerCase();
				setMethods.put(param, m);
			} else {
				// logger.info(methodName + " 不是getter,setter方法！");
			}
		}
	}

	/**
	 * 
	 * @desc 调用set方法
	 */
	public boolean setMethodValue(String property,Object object) {
		Method m = setMethods.get(property.toLowerCase());
		if (m != null) {
			try {
				// 调用目标类的setter函数
				m.invoke(obj, object);
				return true;
			} catch (Exception ex) {
				logger.info("invoke getter on " + property + " error: " + ex.toString());
				return false;
			}
		}
		return false;
	}

	/**
	 * 
	 * @desc 调用set方法
	 */
	public Object getMethodValue(String property) {
		Object value=null;
		Method m = getMethods.get(property.toLowerCase());
		if (m != null) {
			try {
				/**
				 * 调用obj类的setter函数
				 */
				value=m.invoke(obj, new Object[] {});
				
			} catch (Exception ex) {
				logger.info("invoke getter on " + property + " error: " + ex.toString());
			}
		}
		return value;
	}
	
	//add-start--Author:yugwu  Date:20170808 for:TASK #1827 【改造】多数据源，支持minidao语法sql----
	/**
	 * 把map中的内容全部注入到obj中
	 * @param data
	 * @return
	 */
	public Object setAll(Map<String, Object> data){
		if(data == null || data.keySet().size() <= 0){
			return null;
		}
		for(Entry<String, Object> entry : data.entrySet()){
			this.setMethodValue(entry.getKey(), entry.getValue());
        }
		return obj;
	}
	/**
	 * 把map中的内容全部注入到obj中
	 * @param o
	 * @param data
	 * @return
	 */
	public static Object setAll(Object o, Map<String, Object> data){
		ReflectHelper reflectHelper = new ReflectHelper(o);
		reflectHelper.setAll(data);
		return o;
	}
	/**
	 * 把map中的内容全部注入到新实例中
	 * @param clazz
	 * @param data
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T setAll(Class<T> clazz, Map<String, Object> data){
		T o = null;
		try {
			o = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			o = null;
			return o;
		}
		return (T) setAll(o, data);
	}
	//add-end--Author:yugwu  Date:20170808 for:TASK #1827 【改造】多数据源，支持minidao语法sql----
	//add-begin--Author:yugwu  Date:20170810 for:根据clazz转换list----
	/**
	 * 根据传入的class将mapList转换为实体类list
	 * @param mapist
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> transList2Entrys(List<Map<String,Object>> mapist, Class<T> clazz){
		List<T> list = new ArrayList<T>();
		if(mapist != null && mapist.size() > 0){
			for(Map<String,Object> data : mapist){
				list.add(ReflectHelper.setAll(clazz, data));
			}
		}
		return list;
	}
	//add-begin--Author:yugwu  Date:20170810 for:根据clazz转换list----
	
	//update-begin--Author:XueLin  Date:20171030 for：[#2391] 【功能demo】一对多的效果demo，主子表效果---新增反射获取字段函数
	/** 
	 * 根据属性名获取属性值 
	 * */  
   public static Object getFieldValueByName(String fieldName, Object o) {  
       try {    
           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
           String getter = "get" + firstLetter + fieldName.substring(1);    
           Method method = o.getClass().getMethod(getter, new Class[] {});    
           Object value = method.invoke(o, new Object[] {});    
           return value;    
       } catch (Exception e) {    
           e.printStackTrace();  
           return null;    
       }    
   }   
	     
   /** 
    * 获取属性名数组 
    * */  
   public static String[] getFiledName(Object o){  
	    Field[] fields=o.getClass().getDeclaredFields();  
	    String[] fieldNames=new String[fields.length];  
	    for(int i=0;i<fields.length;i++){  
	        //System.out.println(fields[i].getType());  
	        fieldNames[i]=fields[i].getName();  
	    }  
	    return fieldNames;  
   }  
     
   /** 
    * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list 
    * */  
   public static List<Map> getFiledsInfo(Object o){  
		Field[] fields=o.getClass().getDeclaredFields();  
	    String[] fieldNames=new String[fields.length];  
	    List<Map> list = new ArrayList<Map>();  
	    Map<String, Object> infoMap=null;  
	    for(int i=0;i<fields.length;i++){  
	        infoMap = new HashMap<String, Object>();  
	        infoMap.put("type", fields[i].getType().toString());  
	        infoMap.put("name", fields[i].getName());  
	        infoMap.put("value", getFieldValueByName(fields[i].getName(), o));  
	        list.add(infoMap);  
	    }  
	    return list;  
   }  
     
   /** 
    * 获取对象的所有属性值，返回一个对象数组 
    * */  
   public static Object[] getFiledValues(Object o){  
	    String[] fieldNames=getFiledName(o);  
	    Object[] value=new Object[fieldNames.length];  
	    for(int i=0;i<fieldNames.length;i++){  
	        value[i]=getFieldValueByName(fieldNames[i], o);  
	    }  
	    return value;  
   }   
   //update-end--Author:XueLin  Date:20171030 for：[#2391] 【功能demo】一对多的效果demo，主子表效果---新增反射获取字段函数
   
  //update-begin-author:taoyan date：20180726 for:TASK #3014 【bug】online 导入bug
   /**
    * 判断该字段get方法是否被@Transient注解了
    * @author taoYan
    * @since 2018年7月26日
    * @return 如果没有get*返回true，有get且无@Transient注解返回false
    */
	public boolean isIgore(String property) {
		Method m = getMethods.get(property.toLowerCase());
		if (m != null) {
			Object o = m.getAnnotation(Transient.class);
			if(o==null){
				return false;
			}
		}
		return true;
	}
	//update-end-author:taoyan date：20180726 for:TASK #3014 【bug】online 导入bug
}