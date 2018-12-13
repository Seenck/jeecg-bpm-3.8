package jeecg.workflow.entity.bus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jeecgframework.core.annotation.JeecgEntityTitle;
import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 采购单详细表
 */
@Entity
@Table(name = "t_b_purchasedetail")
@JeecgEntityTitle(name="采购单详细表")
public class TBPurchaseDetail extends IdEntity implements java.io.Serializable {


	private String purcname;
	private Short purcnum;
	private Double purcprice;
	private Double purctotalprice;
	private TBPurchase TBPurchase;

	@Column(name = "purcname", length = 100)
	public String getPurcname() {
		return this.purcname;
	}

	public void setPurcname(String purcname) {
		this.purcname = purcname;
	}

	@Column(name = "purcnum")
	public Short getPurcnum() {
		return this.purcnum;
	}

	public void setPurcnum(Short purcnum) {
		this.purcnum = purcnum;
	}

	@Column(name = "purcprice", precision = 12)
	public Double getPurcprice() {
		return this.purcprice;
	}

	public void setPurcprice(Double purcprice) {
		this.purcprice = purcprice;
	}

	@Column(name = "purctotalprice", precision = 12)
	public Double getPurctotalprice() {
		return this.purctotalprice;
	}

	public void setPurctotalprice(Double purctotalprice) {
		this.purctotalprice = purctotalprice;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchaseid")
	public TBPurchase getTBPurchase() {
		return TBPurchase;
	}
	public void setTBPurchase(TBPurchase tBPurchase) {
		this.TBPurchase = tBPurchase;
	}


}