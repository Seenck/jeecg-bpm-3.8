package org.jeecgframework.workflow.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.oConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class ThirdLoginUtil {
	private static final Logger logger = LoggerFactory.getLogger(ThirdLoginUtil.class);
	private static String httpurl="https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
	private static String tokenurl="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORP_ID&corpsecret=CORP_SECRET";
	/**
	 * @param accessToken
	 * @param code
	 * @return
	 * 企业授权成员{"UserId":"USERID","DeviceId":"DEVICEID"}<br>
	 * 非企业授权成员{"OpenId":"OPENID","DeviceId":"DEVICEID"}<br>
	 * 出错时 {"errcode": "40029","errmsg": "invalid code"}
	 */
	public static JSONObject getCorpUser(String accessToken,String code){
		JSONObject result=null;
		try {
			logger.info("------accessToken:"+accessToken+"--------code:"+code);
			//验证请求参数
			if(oConvertUtils.isEmpty(accessToken)){
				throw new BusinessException("accessToken为空");
			}
			if(oConvertUtils.isEmpty(code)){
				throw new BusinessException("code为空");
			}
			String url=httpurl.replace("ACCESS_TOKEN",accessToken).replace("CODE", code);
			logger.info("-------请求地址-------"+url);
			result=httpRequest(url, "GET", null);
		    logger.info("-------返回json串-------"+result.toString());
		}catch (BusinessException e) {
			logger.info(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @param corpid
	 * @param secrect
	 * @return
	 * 正确返回{"access_token": "accesstoken000001xx","expires_in": 7200}<br>
	 * 错误返回{"errcode": 43003,"errmsg": "require https"}
	 */
	public static JSONObject getAccessToken(String corpid,String secrect){
		JSONObject result=null;
		logger.info("请求获取企业号AccessToken");
		try {
			logger.info("------corpid:"+corpid+"------secrect:"+secrect);
			//验证请求参数
			if(oConvertUtils.isEmpty(corpid)){
				throw new BusinessException("corpid为空");
			}
			if(oConvertUtils.isEmpty(secrect)){
				throw new BusinessException("secrect为空");
			}
			String url=tokenurl.replace("CORP_ID",corpid).replace("CORP_SECRET", secrect);
			logger.info("-------请求地址-------"+url);
			result=httpRequest(url, "GET", null);
		    logger.info("-------返回json串-------"+result.toString());
		}catch (BusinessException e) {
			logger.info(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	 private static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
	        JSONObject jsonObject = null;
	        StringBuffer buffer = new StringBuffer();
	        HttpsURLConnection httpUrlConn =null;
	        OutputStream outputStream =null;
	        InputStream inputStream =null;
	        InputStreamReader inputStreamReader =null;
	        BufferedReader bufferedReader =null;
	        try {
	                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
	                TrustManager[] tm = { new MyX509TrustManager() };
	                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	                sslContext.init(null, tm, new java.security.SecureRandom());
	                // 从上述SSLContext对象中得到SSLSocketFactory对象
	                SSLSocketFactory ssf = sslContext.getSocketFactory();

	                URL url = new URL(requestUrl);
	                httpUrlConn = (HttpsURLConnection) url.openConnection();
	                httpUrlConn.setSSLSocketFactory(ssf);

	                httpUrlConn.setDoOutput(true);
	                httpUrlConn.setDoInput(true);
	                httpUrlConn.setUseCaches(false);
				    //设置网络超时
	                httpUrlConn.setConnectTimeout(6000);  
	                httpUrlConn.setReadTimeout(6000);
	                // 设置请求方式（GET/POST）
	                httpUrlConn.setRequestMethod(requestMethod);
	                if ("GET".equalsIgnoreCase(requestMethod))
	                        httpUrlConn.connect();
	                // 当有数据需要提交时
	                if (null != outputStr) {
	                        outputStream = httpUrlConn.getOutputStream();
	                        // 注意编码格式，防止中文乱码
	                        outputStream.write(outputStr.getBytes("UTF-8"));
	                        outputStream.close();
	                }
	                // 将返回的输入流转换成字符串
	                inputStream = httpUrlConn.getInputStream();
	                inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	                bufferedReader = new BufferedReader(inputStreamReader);

	                String str = null;
	                while ((str = bufferedReader.readLine()) != null) {
	                        buffer.append(str);
	                }
	                bufferedReader.close();
	                inputStreamReader.close();
	                // 释放资源
	                inputStream.close();
	                inputStream = null;
	                httpUrlConn.disconnect();
	                jsonObject = JSONObject.fromObject(buffer.toString());
	        } catch (ConnectException ce) {
	        	ce.printStackTrace();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }finally{
	        	try {
					if(inputStreamReader!=null){
						inputStreamReader.close();
					}
					if(inputStream!=null){
						inputStream.close();
					}
					if(bufferedReader!=null){
						bufferedReader.close();
					}
					if(outputStream!=null){
						outputStream.close();
					}
					if(httpUrlConn!=null){
						httpUrlConn.disconnect();
					}
				} catch (Exception e) {
				}
	        }
	        return jsonObject;
	    }
}
