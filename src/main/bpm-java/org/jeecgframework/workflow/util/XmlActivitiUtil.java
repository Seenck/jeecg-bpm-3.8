package org.jeecgframework.workflow.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlActivitiUtil {

	/**
	 * 通过解析xml文件，判断该节点是否是会签节点
	 * 
	 * @param resourceAsStream
	 * @param taskkey
	 * @throws Exception
	 */
	public static boolean parseXml(String doc, String taskkey) {
		Element root = null;
		try {
			Document document = null;
			try {
				SAXReader reader = new SAXReader();
				//解决报错：Dom4j中文异常处理:Invalid byte 2 of 2-byte UTF-8 sequence
				reader.setEncoding("GBK");
				InputStream   inputStream   =  new  ByteArrayInputStream(doc.toString().getBytes());
				document = reader.read(inputStream);  
			} catch (Exception e) {
				SAXReader reader = new SAXReader();
				InputStream   inputStream   =  new  ByteArrayInputStream(doc.toString().getBytes());
				document = reader.read(inputStream);  
			}
			
			root = document.getRootElement();
			  for ( Iterator iter = root.elementIterator(); iter.hasNext(); ) {
				  Element element = (Element) iter.next();
	              for (Iterator iterInner = element.elementIterator(); iterInner.hasNext(); ) {
		              Element elementInner = (Element) iterInner.next();
		              if(elementInner.getName().equals("userTask")){
			               //获取节点的id属性的值
			               Attribute leaderAttr=elementInner.attribute("id");
			               if(leaderAttr!=null){
			            	   if(leaderAttr.getValue().equals(taskkey)){
				            	   Element e_adds = elementInner.element("multiInstanceLoopCharacteristics");
				            	   if (e_adds != null ) {
				            		   return true;
				            	   }
			            	   }
					      }
		              }
	             }
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
