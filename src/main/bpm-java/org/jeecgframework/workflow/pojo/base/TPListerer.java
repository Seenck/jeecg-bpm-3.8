package org.jeecgframework.workflow.pojo.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 监听器对象
 */
@Entity
@Table(name = "t_p_listener")
public class TPListerer extends IdEntity implements java.io.Serializable {
	private String listenereven;
	private String listenertype;
	private String listenervalue;
	private String listenername;
	private Short listenerstate;
	private Short typeid;
	private List<TPProcessListener> TProcessListeners = new ArrayList<TPProcessListener>(
			0);

	@Column(name = "listenereven", length = 20)
	public String getListenereven() {
		return this.listenereven;
	}

	public void setListenereven(String listenereven) {
		this.listenereven = listenereven;
	}

	@Column(name = "listenertype", length = 20)
	public String getListenertype() {
		return this.listenertype;
	}

	public void setListenertype(String listenertype) {
		this.listenertype = listenertype;
	}

	@Column(name = "listenervalue", length = 100)
	public String getListenervalue() {
		return this.listenervalue;
	}

	public void setListenervalue(String listenervalue) {
		this.listenervalue = listenervalue;
	}
	@Column(name = "listenername", length = 50)
	public String getListenername() {
		return this.listenername;
	}

	public void setListenername(String listenername) {
		this.listenername = listenername;
	}

	@Column(name = "listenerstate")
	public Short getListenerstate() {
		return this.listenerstate;
	}

	public void setListenerstate(Short listenerstate) {
		this.listenerstate = listenerstate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPListerer")
	public List<TPProcessListener> getTProcessListeners() {
		return this.TProcessListeners;
	}

	public void setTProcessListeners(List<TPProcessListener> TProcessListeners) {
		this.TProcessListeners = TProcessListeners;
	}
	@Column(name = "typeid")
	public Short getTypeid() {
		return typeid;
	}

	public void setTypeid(Short typeid) {
		this.typeid = typeid;
	}

}