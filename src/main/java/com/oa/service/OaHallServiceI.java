package com.oa.service;
import com.oa.entity.OaHallEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface OaHallServiceI extends CommonService{
	
 	public void delete(OaHallEntity entity) throws Exception;
 	
 	public Serializable save(OaHallEntity entity) throws Exception;
 	
 	public void saveOrUpdate(OaHallEntity entity) throws Exception;
 	
}
