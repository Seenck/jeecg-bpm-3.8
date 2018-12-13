package jeecg.workflow.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * 
 * 类名：QbUtil
 *
 */
public class QbUtil {

	private static final Logger logger = Logger.getLogger(QbUtil.class);
	
	/**
	 * 华融签报流程--获取会签人员列表
	 * 注：表达是使用方法 ${QbUtil.getQbList(id)}  
	 * @param userId 流程发起人
	 * @param id 项目id
	 * @return
	 */
	public List<String> getQbList(String id){
		logger.info("[华融签报流程] getQbList param id:"+id);
		List<String> list = new ArrayList<String>();
		list.add("zhoujf");
		list.add("cw");
		logger.info("[华融签报流程] getQbList id:"+id+",result:"+list.toString());
		return list;
	}
	
}
