package jeecg.bizflow.infomation.service.impl;

import java.io.File;
import java.io.Serializable;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jeecg.bizflow.infomation.entity.JoaFileInfoEntity;
import jeecg.bizflow.infomation.enums.FileTypeEnum;
import jeecg.bizflow.infomation.service.JoaFileInfoServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("JoaFileInfoService")
@Transactional
public class JoaFileInfoServiceImpl extends CommonServiceImpl implements JoaFileInfoServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void delete(JoaFileInfoEntity entity) throws Exception {
		super.delete(entity);
		String path = entity.getPath();
		// 删除文件
		String ctxPath=ResourceUtil.getConfigByName("webUploadpath");
    	String delpath = ctxPath + File.separator + path;
    	File fileDelete = new File(delpath);
		if (fileDelete.exists() && fileDelete.isFile()){
			fileDelete.delete();
		}
	}

	public Serializable save(JoaFileInfoEntity entity) throws Exception {
		Serializable t = super.save(entity);
		return t;
	}

	public void saveOrUpdate(JoaFileInfoEntity entity) throws Exception {
		super.saveOrUpdate(entity);
	}

	@Override
	public void saveFileInfo(String files, String dirId) {
		JSONArray array = JSONArray.fromObject(files);
		String user = ResourceUtil.getSessionUser().getUserName();
		for (Object object : array) {
			JSONObject json = (JSONObject) object;
			String nameWithSuffix = json.getString("name");
			String path = json.getString("path");
			double size = json.getDouble("size");
			String ext = nameWithSuffix.substring(nameWithSuffix.lastIndexOf(".") + 1);
			String type = FileTypeEnum.getTypeByext(ext);
			JoaFileInfoEntity info = new JoaFileInfoEntity();
			info.setExt(ext);
			info.setType(type);
			info.setPath(path);
			info.setSize(size);
			info.setParentId(dirId);
			info.setName(nameWithSuffix.substring(0, nameWithSuffix.lastIndexOf(".")));

			info.setBusiness("");
			info.setFromUser("");
			info.setOwner(user);
			super.save(info);
		}

	}

	@Override
	public void saveDir(JoaFileInfoEntity entity) {
		super.save(entity);
		String pid = entity.getParentId();
		String sql = "update joa_file_info set leaf = 0 where id = ?";
		this.executeSql(sql,pid);
	}

	@Override
	public void deleteDir(JoaFileInfoEntity entity) {
		String delSql = "delete from joa_file_info where id = ?";
		String son = "select count(*) from joa_file_info where type='0' and PARENT_ID = ?";
		this.executeSql(delSql, entity.getId());
		String pid = entity.getParentId();
		//查询被删除的文件夹父级是否还有其他儿子
		Long count = this.getCountForJdbcParam(son,pid);
		if(count<=0){
			//没有儿子设置叶子
			String sql = "update joa_file_info set leaf = 1 where id = ?";
			this.executeSql(sql, pid);
		}
		
	}

	@Override
	public void updateFileName(String id, String name) {
		String  sql = "update joa_file_info set name = ? where id = ?";
		this.executeSql(sql, name, id);
	}

}