package com.tradeware.clouddatabase.bean;

import java.util.Date;

public class IntradayInfoBean extends AbstractBean {

	private static final long serialVersionUID = 8302728551920698471L;
	private Integer intradayTradeId;
	private Double previousDayLow;
	private Double previousDayHigh;
	private Double previousDayClose;
	private Double averageMinimumVal;
	private Double averageHiLoClsVal;
	private Double intradayUptrendValue;
	private Double intradayDowntrendValue;
	private String postionalSignal;
	private String intradaySignal;
	private Double oiPreviousDay;
	private Double oiToday;
	private Double change;
	private Double monthLow;
	private Double monthHigh;
	private Date intraBuySignalActiveAt;
	private Date intraSellSignalActiveAt;
	private Date positionalBuySignalActiveAt;
	private Date positionalSellSignalActiveAt;
	private Date dateTimeStamp;
	private Date dateStamp;
	public Integer getIntradayTradeId() {
		return intradayTradeId;
	}
	public void setIntradayTradeId(Integer intradayTradeId) {
		this.intradayTradeId = intradayTradeId;
	}
	public Double getPreviousDayLow() {
		return previousDayLow;
	}
	public void setPreviousDayLow(Double previousDayLow) {
		this.previousDayLow = previousDayLow;
	}
	public Double getPreviousDayHigh() {
		return previousDayHigh;
	}
	public void setPreviousDayHigh(Double previousDayHigh) {
		this.previousDayHigh = previousDayHigh;
	}
	public Double getPreviousDayClose() {
		return previousDayClose;
	}
	public void setPreviousDayClose(Double previousDayClose) {
		this.previousDayClose = previousDayClose;
	}
	public Double getAverageMinimumVal() {
		return averageMinimumVal;
	}
	public void setAverageMinimumVal(Double averageMinimumVal) {
		this.averageMinimumVal = averageMinimumVal;
	}
	public Double getAverageHiLoClsVal() {
		return averageHiLoClsVal;
	}
	public void setAverageHiLoClsVal(Double averageHiLoClsVal) {
		this.averageHiLoClsVal = averageHiLoClsVal;
	}
	public Double getIntradayUptrendValue() {
		return intradayUptrendValue;
	}
	public void setIntradayUptrendValue(Double intradayUptrendValue) {
		this.intradayUptrendValue = intradayUptrendValue;
	}
	public Double getIntradayDowntrendValue() {
		return intradayDowntrendValue;
	}
	public void setIntradayDowntrendValue(Double intradayDowntrendValue) {
		this.intradayDowntrendValue = intradayDowntrendValue;
	}
	public String getPostionalSignal() {
		return postionalSignal;
	}
	public void setPostionalSignal(String postionalSignal) {
		this.postionalSignal = postionalSignal;
	}
	public String getIntradaySignal() {
		return intradaySignal;
	}
	public void setIntradaySignal(String intradaySignal) {
		this.intradaySignal = intradaySignal;
	}
	public Double getOiPreviousDay() {
		return oiPreviousDay;
	}
	public void setOiPreviousDay(Double oiPreviousDay) {
		this.oiPreviousDay = oiPreviousDay;
	}
	public Double getOiToday() {
		return oiToday;
	}
	public void setOiToday(Double oiToday) {
		this.oiToday = oiToday;
	}
	public Double getChange() {
		return change;
	}
	public void setChange(Double change) {
		this.change = change;
	}
	public Double getMonthLow() {
		return monthLow;
	}
	public void setMonthLow(Double monthLow) {
		this.monthLow = monthLow;
	}
	public Double getMonthHigh() {
		return monthHigh;
	}
	public void setMonthHigh(Double monthHigh) {
		this.monthHigh = monthHigh;
	}
	public Date getIntraBuySignalActiveAt() {
		return intraBuySignalActiveAt;
	}
	public void setIntraBuySignalActiveAt(Date intraBuySignalActiveAt) {
		this.intraBuySignalActiveAt = intraBuySignalActiveAt;
	}
	public Date getIntraSellSignalActiveAt() {
		return intraSellSignalActiveAt;
	}
	public void setIntraSellSignalActiveAt(Date intraSellSignalActiveAt) {
		this.intraSellSignalActiveAt = intraSellSignalActiveAt;
	}
	public Date getPositionalBuySignalActiveAt() {
		return positionalBuySignalActiveAt;
	}
	public void setPositionalBuySignalActiveAt(Date positionalBuySignalActiveAt) {
		this.positionalBuySignalActiveAt = positionalBuySignalActiveAt;
	}
	public Date getPositionalSellSignalActiveAt() {
		return positionalSellSignalActiveAt;
	}
	public void setPositionalSellSignalActiveAt(Date positionalSellSignalActiveAt) {
		this.positionalSellSignalActiveAt = positionalSellSignalActiveAt;
	}
	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}
	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}
	public Date getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
	
	private String symbolId;
	public String getSymbolId() {
		return symbolId;
	}
	public void setSymbolId(String symbolId) {
		this.symbolId = symbolId;
	}
}