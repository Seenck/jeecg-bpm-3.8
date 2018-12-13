package org.jeecgframework.web.rest.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.web.system.pojo.base.TSJeecgServerEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

/**
 * 对外接口
 *
 */
@Controller
@RequestMapping("/licController")
public class LicController {
	@Autowired
	private SystemService systemService;
	/**
	 * getLicenseKey
	 * @return
	 * @throws  
	 */
	@RequestMapping(params = "getLicKey")
	public void getLicenseKey(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("licenseKey", "20150402");
		//-------------------------------------------------------------------------------
		
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		try {
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
			PrintWriter pw = response.getWriter();
			pw.write(JSONObject.toJSONString(map));
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * getLicenseKey
	 * @return
	 * @throws  
	 */
	@RequestMapping(params = "saveJeecgServer")
	public void saveJeecgServer(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		//IP
		String ip = request.getParameter("ip");
		//MAC
		String mac = request.getParameter("mac");
		//osname
		String osname = request.getParameter("osname");
		
		
		TSJeecgServerEntity po = new TSJeecgServerEntity();
		po.setStartTime(new Date());
		po.setOsName(osname);
		po.setIp(ip);
		po.setMac(mac);
		systemService.save(po);
		
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		try {
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
			PrintWriter pw = response.getWriter();
			pw.write("success");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
