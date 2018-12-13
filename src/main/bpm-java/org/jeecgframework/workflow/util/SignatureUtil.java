package org.jeecgframework.workflow.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jeecgframework.core.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加签、验签工具.
 *
 * @author zhoujf
 */
public abstract class SignatureUtil {
    private static final Logger LOG = LoggerFactory.getLogger(SignatureUtil.class);
    
    /**
     * 加签,MD5.
     * @param paramMap 参数Map,不包含商户秘钥且顺序确定
     * @param key  商户秘钥
     * @return  签名串
     */
    public static String sign(Map<String, String> paramMap, String key) {
        if(key == null){
            throw new BusinessException("key不能为空");
        }
        String sign = createSign(paramMap,key);
        return sign;
    }
    
    /**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	private static String createSign(Map<String, String> paramMap, String key) {
		StringBuffer sb = new StringBuffer();
		SortedMap<String,String> sort=new TreeMap<String,String>(paramMap);  
		Set<Entry<String, String>> es = sort.entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v)&& !"null".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		LOG.info("HMAC source:{}", new Object[] { sb.toString() } );
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		LOG.info("HMAC:{}", new Object[] { sign } );
		return sign;
	}

    /**
     * 验签, 仅支持MD5.
     * @param paramMap 参数Map,不包含商户秘钥且顺序确定
     * @param key  商户秘钥
     * @param sign     签名串
     * @return         验签结果
     */
    public static boolean checkSign(Map<String, String> paramMap, String key, String sign) {
        if(key == null){
            throw new BusinessException("key不能为空");
        }
        if(sign == null){
            throw new BusinessException("需要验签的字符为空");
        }

        return sign.equals(sign(paramMap,key));
    }
    
    public static void main(String[] args) {
    	String id = "4";
    	Map<String,String> paramMap = new HashMap<String, String>();
    	paramMap.put("sysCode", "jeecgbpm");
    	paramMap.put("dataId", id);
    	paramMap.put("applySysCode", "qyweixin");
		paramMap.put("applyUserId", "test");
		paramMap.put("bizTitile", "第三方测试订单【"+id+"】");
		paramMap.put("formUrl", "http://www.baidu.com");
		paramMap.put("mobileFormUrl", "");
		paramMap.put("data", "{id:'"+id+"',name:'zhangsan'}");
		paramMap.put("processKey", "process1489455729094");
		paramMap.put("callBackUrl", "http://www.baidu.com");
		String sign = sign(paramMap,"0372E839C3FCAED61912B6C47B1912B53FC47BF72780372E839C56CAED6F7278");
		System.out.println(sign);
		paramMap.put("sign", sign);
		String url = "http://127.0.0.1:8888/jeecg-bpm/flowApi/startProcess.do";
		String result = HttpUtils.post(url, paramMap);
		System.out.println("--------------"+result);
		
	}
}
