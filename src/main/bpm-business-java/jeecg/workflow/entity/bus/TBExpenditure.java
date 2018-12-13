package jeecg.workflow.entity.bus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.jeecgframework.core.annotation.JeecgEntityTitle;
import org.jeecgframework.workflow.pojo.base.TSBaseBus;



/**
 * 费用支出表
 */
@Entity
@Table(name = "t_b_expenditure")
@PrimaryKeyJoinColumn(name = "id")
@JeecgEntityTitle(name="费用支出表")
public class TBExpenditure extends TSBaseBus implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Double expenmoney;// 支出金额
	private Short expentype;// 支出类型
	private String expenreson; // 支出事由

	@Column(name = "expenmoney", precision = 12)
	public Double getExpenmoney() {
		return this.expenmoney;
	}

	public void setExpenmoney(Double expenmoney) {
		this.expenmoney = expenmoney;
	}

	@Column(name = "expentype")
	public Short getExpentype() {
		return this.expentype;
	}

	public void setExpentype(Short expentype) {
		this.expentype = expentype;
	}

	@Column(name = "expenreson", length = 2000)
	public String getExpenreson() {
		return this.expenreson;
	}

	public void setExpenreson(String expenreson) {
		this.expenreson = expenreson;
	}

}