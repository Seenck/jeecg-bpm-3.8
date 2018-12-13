package org.jeecgframework.workflow.pojo.base;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.jeecgframework.web.system.pojo.base.TSAttachment;

@Entity
@Table(name = "t_p_bpm_file", schema = "")
@PrimaryKeyJoinColumn(name = "id")
@SuppressWarnings("serial")
public class TPBpmFile  extends TSAttachment implements java.io.Serializable{
	
	private TPBpmLog bpmlog;//流程备注ID
	private String path;//路径
	private String file_name;//文件名称
	private Timestamp createdate;//创建时间

	@Column(name = "path")
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Column(name = "file_name")
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String fileName) {
		file_name = fileName;
	}
	@Column(name = "create_date",length=35)
	public Timestamp getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JoinColumn(name = "bpm_log_id")
	public TPBpmLog getBpmlog() {
		return bpmlog;
	}
	public void setBpmlog(TPBpmLog bpmlog) {
		this.bpmlog = bpmlog;
	}
	
}
