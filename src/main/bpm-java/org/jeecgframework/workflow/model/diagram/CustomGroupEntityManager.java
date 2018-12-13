package org.jeecgframework.workflow.model.diagram;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

public class CustomGroupEntityManager extends GroupEntityManager{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Group> findGroupsByUser(String userId) {
		// TODO Auto-generated method stub
		System.out.println("liujinghua...... findGroupsByUser  ");
		
		String sql = "select t_s_user_depart.groupid from t_s_base_user,t_s_user_depart where t_s_base_user.username=t_s_user_depart.username and t_s_base_user.username='"+userId+"'";
		
		final List<Group> groups = new ArrayList<Group>();
		jdbcTemplate.query(sql, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				groups.add(new GroupEntity(rs.getString("groupid")));
				System.out.println(rs.getString("groupid")+"-----------------------------------------");
			}
		});
		
		return groups;
	}

}
