package com.tradeware.clouddatabase.bean;

import java.util.Date;

public class StrategyOrbMonthlyReportBean extends AbstractBean {
	private static final long serialVersionUID = 8751072941539085757L;
	private Date tradedDateStamp;
	private Integer tradeCountPerDay;
	private Double profitLossAmtVal;
	private Double profitLossAmtValFinal;
	private Double profitLossAmtValManalFinal;
	public Date getTradedDateStamp() {
		return tradedDateStamp;
	}
	public void setTradedDateStamp(Date tradedDateStamp) {
		this.tradedDateStamp = tradedDateStamp;
	}
	public Integer getTradeCountPerDay() {
		return tradeCountPerDay;
	}
	public void setTradeCountPerDay(Integer tradeCountPerDay) {
		this.tradeCountPerDay = tradeCountPerDay;
	}
	public Double getProfitLossAmtVal() {
		return profitLossAmtVal;
	}
	public void setProfitLossAmtVal(Double profitLossAmtVal) {
		this.profitLossAmtVal = profitLossAmtVal;
	}
	public Double getProfitLossAmtValFinal() {
		return profitLossAmtValFinal;
	}
	public void setProfitLossAmtValFinal(Double profitLossAmtValFinal) {
		this.profitLossAmtValFinal = profitLossAmtValFinal;
	}
	public Double getProfitLossAmtValManalFinal() {
		return profitLossAmtValManalFinal;
	}
	public void setProfitLossAmtValManalFinal(Double profitLossAmtValManalFinal) {
		this.profitLossAmtValManalFinal = profitLossAmtValManalFinal;
	}
}
