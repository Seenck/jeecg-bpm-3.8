package jeecg.workflow.entity.bus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.jeecgframework.core.annotation.JeecgEntityTitle;
import org.jeecgframework.workflow.pojo.base.TSBaseBus;



/**
 * 借款表
 */
@Entity
@Table(name = "t_b_bormoney")
@PrimaryKeyJoinColumn(name = "id")
@JeecgEntityTitle(name="借款表")
public class TBBormoney extends TSBaseBus implements java.io.Serializable {
	private Double bormoney;// 借款金额
	private String bormoneyuse;// 借款用途

	@Column(name = "bormoney", precision = 12)
	public Double getBormoney() {
		return this.bormoney;
	}

	public void setBormoney(Double bormoney) {
		this.bormoney = bormoney;
	}

	@Column(name = "bormoneyuse", length = 500)
	public String getBormoneyuse() {
		return this.bormoneyuse;
	}

	public void setBormoneyuse(String bormoneyuse) {
		this.bormoneyuse = bormoneyuse;
	}

}