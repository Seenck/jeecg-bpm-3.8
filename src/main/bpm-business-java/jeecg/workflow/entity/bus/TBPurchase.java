package jeecg.workflow.entity.bus;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.jeecgframework.core.annotation.JeecgEntityTitle;
import org.jeecgframework.workflow.pojo.base.TSBaseBus;


/**
 * 采购单表
 */
@Entity
@Table(name = "t_b_purchase")
@PrimaryKeyJoinColumn(name = "id")
@JeecgEntityTitle(name="采购流程")
public class TBPurchase  extends TSBaseBus implements java.io.Serializable {

	private Double totalprice;
	private Time purcdate;
	private String purcnumber;
	private List<TBPurchaseDetail> TBPurchaseDetail = new ArrayList<TBPurchaseDetail>();//流程环节集合
	

	@Column(name = "totalprice", precision = 12)
	public Double getTotalprice() {
		return this.totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}

	@Column(name = "purcdate", length = 21)
	public Time getPurcdate() {
		return this.purcdate;
	}

	public void setPurcdate(Time purcdate) {
		this.purcdate = purcdate;
	}

	@Column(name = "purcnumber", length = 100)
	public String getPurcnumber() {
		return this.purcnumber;
	}

	public void setPurcnumber(String purcnumber) {
		this.purcnumber = purcnumber;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TBPurchase")
	public List<TBPurchaseDetail> getTBPurchaseDetail() {
		return TBPurchaseDetail;
	}

	
	public void setTBPurchaseDetail(List<TBPurchaseDetail> tBPurchaseDetail) {
		TBPurchaseDetail = tBPurchaseDetail;
	}

}