package org.jeecgframework.workflow.pojo.activiti;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ActRuEventSubscr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "act_ru_event_subscr")
public class ActRuEventSubscr implements java.io.Serializable {

	// Fields

	private String id;
	private ActRuExecution actRuExecution;
	private Integer rev;
	private String eventType;
	private String eventName;
	private String procInstId;
	private String activityId;
	private String configuration;
	private Timestamp created;

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	@Column(name = "id_", unique = true, nullable = false, length = 64)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "execution_id_")
	public ActRuExecution getActRuExecution() {
		return this.actRuExecution;
	}

	public void setActRuExecution(ActRuExecution actRuExecution) {
		this.actRuExecution = actRuExecution;
	}

	@Column(name = "rev_")
	public Integer getRev() {
		return this.rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	@Column(name = "event_type_", nullable = false)
	public String getEventType() {
		return this.eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@Column(name = "event_name_")
	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	@Column(name = "proc_inst_id_", length = 64)
	public String getProcInstId() {
		return this.procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	@Column(name = "activity_id_", length = 64)
	public String getActivityId() {
		return this.activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@Column(name = "configuration_")
	public String getConfiguration() {
		return this.configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	@Column(name = "created_", nullable = false, length = 29)
	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

}