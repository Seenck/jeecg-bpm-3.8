package org.jeecgframework.workflow.pojo.base;

import java.io.Serializable;
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
import org.jeecgframework.web.system.pojo.base.TSType;


/**
 * 环节处理表单表
 */
@Entity
@Table(name = "t_p_form")
public class TPForm extends IdEntity implements Serializable {

	private TSType TSType;// 表单类型
	private String formname;// 表单名称
	private String formaction;// 表单提交地址
	private String formurl;// 外置表单地址
	private String formkey;// 表单唯一键
	private String formcode;// 表单编码
	private String formnote;// 备注
	private List<TPFormpro> TPFormpros = new ArrayList<TPFormpro>();
	private List<TPProcessnode> TProcessnodes = new ArrayList<TPProcessnode>(0);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeid")
	public TSType getTSType() {
		return this.TSType;
	}

	public void setTSType(TSType TSType) {
		this.TSType = TSType;
	}

	@Column(name = "formname", length = 50)
	public String getFormname() {
		return this.formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	@Column(name = "formaction", length = 100)
	public String getFormaction() {
		return this.formaction;
	}

	public void setFormaction(String formaction) {
		this.formaction = formaction;
	}

	@Column(name = "formkey", length = 30)
	public String getFormkey() {
		return this.formkey;
	}

	public void setFormkey(String formkey) {
		this.formkey = formkey;
	}

	@Column(name = "formcode", length = 30)
	public String getFormcode() {
		return this.formcode;
	}

	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	@Column(name = "formnote", length = 50)
	public String getFormnote() {
		return this.formnote;
	}

	public void setFormnote(String formnote) {
		this.formnote = formnote;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPForm")
	public List<TPFormpro> getTPFormpros() {
		return this.TPFormpros;
	}

	public void setTPFormpros(List<TPFormpro> TPFormpros) {
		this.TPFormpros = TPFormpros;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPForm")
	public List<TPProcessnode> getTProcessnodes() {
		return this.TProcessnodes;
	}

	public void setTProcessnodes(List<TPProcessnode> TProcessnodes) {
		this.TProcessnodes = TProcessnodes;
	}

	@Column(name = "formurl", length = 100)
	public String getFormurl() {
		return formurl;
	}

	public void setFormurl(String formurl) {
		this.formurl = formurl;
	}

}