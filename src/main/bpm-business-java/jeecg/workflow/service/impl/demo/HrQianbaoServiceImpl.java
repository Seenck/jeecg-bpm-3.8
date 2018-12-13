package jeecg.workflow.service.impl.demo;
import java.io.Serializable;
import java.util.UUID;

import jeecg.workflow.entity.demo.HrQianbaoEntity;
import jeecg.workflow.service.demo.HrQianbaoServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("hrQianbaoService")
@Transactional
public class HrQianbaoServiceImpl extends CommonServiceImpl implements HrQianbaoServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((HrQianbaoEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((HrQianbaoEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((HrQianbaoEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(HrQianbaoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(HrQianbaoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(HrQianbaoEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,HrQianbaoEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{document_category}",String.valueOf(t.getDocumentCategory()));
 		sql  = sql.replace("#{bpm_status}",String.valueOf(t.getBpmStatus()));
 		sql  = sql.replace("#{confidential_year}",String.valueOf(t.getConfidentialYear()));
 		sql  = sql.replace("#{confidential_num}",String.valueOf(t.getConfidentialNum()));
 		sql  = sql.replace("#{qianbao_depart_code}",String.valueOf(t.getQianbaoDepartCode()));
 		sql  = sql.replace("#{qianbao_depart_name}",String.valueOf(t.getQianbaoDepartName()));
 		sql  = sql.replace("#{qianbao_date}",String.valueOf(t.getQianbaoDate()));
 		sql  = sql.replace("#{qianbao_flag}",String.valueOf(t.getQianbaoFlag()));
 		sql  = sql.replace("#{policy_flag}",String.valueOf(t.getPolicyFlag()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{sys_company_code}",String.valueOf(t.getSysCompanyCode()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}