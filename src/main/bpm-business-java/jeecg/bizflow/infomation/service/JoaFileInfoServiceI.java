package jeecg.bizflow.infomation.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import jeecg.bizflow.infomation.entity.JoaFileInfoEntity;

public interface JoaFileInfoServiceI extends CommonService{
	
 	public void delete(JoaFileInfoEntity entity) throws Exception;
 	
 	public Serializable save(JoaFileInfoEntity entity) throws Exception;
 	
 	public void saveOrUpdate(JoaFileInfoEntity entity) throws Exception;
 	
 	public void saveFileInfo(String files,String dirId);
 	
 	public void updateFileName(String id,String name);
 	
 	public void saveDir(JoaFileInfoEntity entity);
 	
 	public void deleteDir(JoaFileInfoEntity entity);
 	
}
