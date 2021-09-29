package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tradeware.clouddatabase.bean.TradeStateLookupBean;

@Entity
@Table(name = "T_TRADE_STATE_LOOKUP")
public class TradeStateLookupEntity extends AbstractEntity {

	private static final long serialVersionUID = 7196790167642416816L;

	@Id
	@Column(name = "TRADE_STATE_ID", length = 12)
	private String tradeStateId;

	@Column(name = "TRADE_STATE_DESC", length = 64)
	private String tradeStateDescription;

	@Column(name = "TRADE_STATE_CATEGORY", length = 64)
	private String tradeStateCategory;

	@Column(name = "DT_TM_STAMP", columnDefinition= "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp;

	public TradeStateLookupEntity() {
		super();
	}

	public TradeStateLookupEntity(String tradeStateId) {
		super();
		this.tradeStateId = tradeStateId;
	}

	public String getTradeStateId() {
		return tradeStateId;
	}

	public void setTradeStateId(String tradeStateId) {
		this.tradeStateId = tradeStateId;
	}

	public String getTradeStateDescription() {
		return tradeStateDescription;
	}

	public void setTradeStateDescription(String tradeStateDescription) {
		this.tradeStateDescription = tradeStateDescription;
	}

	public String getTradeStateCategory() {
		return tradeStateCategory;
	}

	public void setTradeStateCategory(String tradeStateCategory) {
		this.tradeStateCategory = tradeStateCategory;
	}

	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}

	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}

	public void populateEntity(TradeStateLookupBean bean) {
		this.tradeStateId = bean.getTradeStateId();
		this.tradeStateDescription = bean.getTradeStateDescription();
		this.tradeStateCategory = bean.getTradeStateCategory();
		this.dateTimeStamp = bean.getDateTimeStamp();
	}

	public TradeStateLookupBean populateBean() {
		TradeStateLookupBean bean = new TradeStateLookupBean();
		bean.setTradeStateId(this.tradeStateId);
		bean.setTradeStateDescription(this.tradeStateDescription);
		bean.setTradeStateCategory(this.tradeStateCategory);
		bean.setDateTimeStamp(this.dateTimeStamp);
		return bean;
	}

	@Override
	public String toString() {
		return "TradeStateLookupEntity [tradeStateId=" + tradeStateId + ", tradeStateDescription="
				+ tradeStateDescription + ", tradeStateCategory=" + tradeStateCategory + ", dateTimeStamp="
				+ dateTimeStamp + "]";
	}
}
