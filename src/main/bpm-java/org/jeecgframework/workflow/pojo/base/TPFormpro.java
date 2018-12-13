package org.jeecgframework.workflow.pojo.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;


/**
 * 内置表单设计表
 */
@Entity
@Table(name = "t_p_formpro")
public class TPFormpro extends IdEntity implements java.io.Serializable {
	private TPForm TPForm=new TPForm();//所属表单对象
	private String formproname;//控件名称
	private String formproval;//控件值
	private String formprofun;//控件方法名
	private String formproscript;//控件方法体
	private String formprokey;//表单控件唯一标示
	private String formprocode;//流程变量编码
	private boolean formprostate;//控件状态
	private String formprotype;//表单控件类型
	private String formprocss;//表单样式
	private String processopt;//是否操作按钮
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "formid")
	public TPForm getTPForm() {
		return this.TPForm;
	}

	public void setTPForm(TPForm TPForm) {
		this.TPForm = TPForm;
	}

	@Column(name = "formproname", length = 50)
	public String getFormproname() {
		return this.formproname;
	}

	public void setFormproname(String formproname) {
		this.formproname = formproname;
	}

	@Column(name = "formproval", length = 50)
	public String getFormproval() {
		return this.formproval;
	}

	public void setFormproval(String formproval) {
		this.formproval = formproval;
	}

	@Column(name = "formprofun", length = 50)
	public String getFormprofun() {
		return this.formprofun;
	}

	public void setFormprofun(String formprofun) {
		this.formprofun = formprofun;
	}

	@Column(name = "formproscript", length = 1000)
	public String getFormproscript() {
		return this.formproscript;
	}

	public void setFormproscript(String formproscript) {
		this.formproscript = formproscript;
	}

	@Column(name = "formprokey", length = 50)
	public String getFormprokey() {
		return this.formprokey;
	}

	public void setFormprokey(String formprokey) {
		this.formprokey = formprokey;
	}

	@Column(name = "formprocode", length = 20)
	public String getFormprocode() {
		return this.formprocode;
	}

	public void setFormprocode(String formprocode) {
		this.formprocode = formprocode;
	}

	@Column(name = "formprostate")
	public boolean getFormprostate() {
		return this.formprostate;
	}

	public void setFormprostate(boolean formprostate) {
		this.formprostate = formprostate;
	}

	@Column(name = "formprotype", length = 50)
	public String getFormprotype() {
		return this.formprotype;
	}

	public void setFormprotype(String formprotype) {
		this.formprotype = formprotype;
	}
	@Column(name = "processopt", length = 50)
	public String getProcessopt() {
		return processopt;
	}

	public void setProcessopt(String processopt) {
		this.processopt = processopt;
	}

	@Column(name = "formprocss", length = 200)
	public String getFormprocss() {
		return formprocss;
	}

	public void setFormprocss(String formprocss) {
		this.formprocss = formprocss;
	}

}