package com.tradeware.clouddatabase.bean;

import java.util.Date;

public class TradeStateLookupBean extends AbstractBean {

	private static final long serialVersionUID = 1889521666655219311L;
	private String tradeStateId;
	private String tradeStateDescription;
	private String tradeStateCategory;
	private Date dateTimeStamp;
	
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
}