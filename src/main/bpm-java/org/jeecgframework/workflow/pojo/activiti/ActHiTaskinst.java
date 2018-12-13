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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 任务历史表
 */
@Entity
@Table(name = "act_hi_taskinst")
public class ActHiTaskinst implements java.io.Serializable {

	// Fields

	private String id;// 主键ID
	private String procDefId;// 流程定义ID
	private String taskDefKey;// 任务ID
	private String procInstId;// 流程实例ID
	private String executionId;// 流程运行表活动实例ID
	private String name;// 任务定义名称
	private String parentTaskId;// 父任务ID
	private String description;// 描述
	private String owner;// 创建人
	private String assignee;// 执行人
	private Timestamp startTime;// 开始时间
	private Timestamp endTime;// 结束时间
	private Long duration;// 持续时间
	private String deleteReason;// 删除原因
	private Integer priority;// 优先级
	private Timestamp dueDate;// 任务完成期限

	private ActReProcdef prodef;//流程定义实体
	private ActHiProcinst proInsl;//流程实例实体
	//update-begin--Author:zhoujf  Date:20151208 for：增加业务标题
	private String bpmBizTitle;//业务标题
	//update-end--Author:zhoujf  Date:20151208 for：增加业务标题
	@Id
	@Column(name="id_")
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "proc_def_id_", length = 64)
	public String getProcDefId() {
		return this.procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	@Column(name = "task_def_key_")
	public String getTaskDefKey() {
		return this.taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	@Column(name = "proc_inst_id_", length = 64)
	public String getProcInstId() {
		return this.procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	@Column(name = "execution_id_", length = 64)
	public String getExecutionId() {
		return this.executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	@Column(name = "name_")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "parent_task_id_", length = 64)
	public String getParentTaskId() {
		return this.parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	@Column(name = "description_", length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "owner_")
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name = "assignee_")
	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	@Column(name = "start_time_", nullable = false, length = 29)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time_", length = 29)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "duration_")
	public Long getDuration() {
		return this.duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Column(name = "delete_reason_", length = 4000)
	public String getDeleteReason() {
		return this.deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	@Column(name = "priority_")
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "due_date_", length = 29)
	public Timestamp getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="proc_def_id_",insertable=false,updatable=false)
	public ActReProcdef getProdef() {
		return prodef;
	}

	public void setProdef(ActReProcdef prodef) {
		this.prodef = prodef;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="proc_inst_id_",insertable=false,updatable=false)
	public ActHiProcinst getProInsl() {
		return proInsl;
	}

	public void setProInsl(ActHiProcinst proInsl) {
		this.proInsl = proInsl;
	}
	/**
	* 时间的处理
	* @param time
	* @return
	*/
	public  String dealTimeFromNum(long time) {
		StringBuilder result = new StringBuilder();
		long interval = time / 1000;
		final long day = 24 * 60 * 60;
		final long hour = 60 * 60;
		final long minute = 60;
		int detailDay = 0;
		int detailHour = 0;
		int detailMinute = 0;
		int detailSecond = 0;
		if (interval >= day) {
			detailDay = (int) (interval / day);
			interval = interval - detailDay * day;
		}
		if (interval >= hour) {
			detailHour = (int) (interval / hour);
			interval = interval - hour * detailHour;
		}
		if (interval >= minute) {
			detailMinute = (int) (interval / minute);
			interval = interval - detailMinute * minute;
		}
		if (interval > 0) {
			detailSecond = (int) interval;
		}
		result.setLength(0);
		if (detailDay > 0) {
			result.append(detailDay);
			result.append("天");
		}
		if (detailHour > 0) {
			result.append(detailHour);
			result.append("小时");
		}
		if (detailMinute > 0) {
			result.append(detailMinute);
			result.append("分");
		}
		if (detailSecond > 0) {
			result.append(detailSecond);
			result.append("秒");
		}
		return result.toString();
	}
	@Transient
	public String getDurationStr(){
		return dealTimeFromNum(this.duration);
	}
	
	@Transient
	public String getBpmBizTitle() {
		return bpmBizTitle;
	}

	public void setBpmBizTitle(String bpmBizTitle) {
		this.bpmBizTitle = bpmBizTitle;
	}
}