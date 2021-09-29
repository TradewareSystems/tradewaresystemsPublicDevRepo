package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tradeware.clouddatabase.bean.IntradayInfoBean;

@Entity
@Table(name = "T_INTRADAY_TRADE")
public class IntradayInfoEntity extends AbstractEntity {

	private static final long serialVersionUID = 7260828224409269159L;
	
	@Id
	@Column(name = "INTRADAY_TRADE_ID")
	private Integer intradayTradeId;
	
	@Column(name = "PREV_DAY_LOW")
	private Double previousDayLow;
	
	@Column(name = "PREV_DAY_HIGH")
	private Double previousDayHigh;
	
	@Column(name = "PREV_DAY_CLOSE")
	private Double previousDayClose;
	
	@Column(name = "AVG_MIN_VAL")
	private Double averageMinimumVal;
	
	@Column(name = "AVG_HLC")
	private Double averageHiLoClsVal;
	
	@Column(name = "UP_TREND_VALUE")
	private Double intradayUptrendValue;
	
	@Column(name = "DOWN_TREND_VALUE")
	private Double intradayDowntrendValue;
	
	@Column(name = "POS_TRADE_SIGNAL")
	private String postionalSignal;
	
	@Column(name = "INTRA_TRADE_SIGNAL")
	private String intradaySignal;
	
	@Column(name = "OI_PREV_DAY")
	private Double oiPreviousDay;
	
	@Column(name = "OI_TODAY")
	private Double oiToday;
	
	@Column(name = "OI_CHANGE")
	private Double change;
	
	@Column(name = "MONTH_LOW")
	private Double monthLow;
	
	@Column(name = "MONTH_HIGH")
	private Double monthHigh;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INTRA_BUY_SIGNAL_ACTIVE_AT")
	private Date intraBuySignalActiveAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INTRA_SELL_SIGNAL_ACTIVE_AT")
	private Date intraSellSignalActiveAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "POS_BUY_SIGNAL_ACTIVE_AT")
	private Date positionalBuySignalActiveAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "POS_SELL_SIGNAL_ACTIVE_AT")
	private Date positionalSellSignalActiveAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_TM_STAMP")
	private Date dateTimeStamp;
	
	@Column(name = "DT_STAMP")
	@Temporal(TemporalType.DATE)
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
	
	public void populateEntity(IntradayInfoBean bean) {
		this.intradayTradeId = bean.getIntradayTradeId();
		this.previousDayLow = bean.getPreviousDayLow();
		this.previousDayHigh = bean.getPreviousDayHigh();
		this.previousDayClose = bean.getPreviousDayClose();
		this.averageMinimumVal = bean.getAverageMinimumVal();
		this.averageHiLoClsVal = bean.getAverageHiLoClsVal();
		this.intradayUptrendValue = bean.getIntradayUptrendValue();
		this.intradayDowntrendValue = bean.getIntradayDowntrendValue();
		this.postionalSignal = bean.getPostionalSignal();
		this.intradaySignal = bean.getIntradaySignal();
		this.oiPreviousDay = bean.getOiPreviousDay();
		this.oiToday = bean.getOiToday();
		this.monthLow = bean.getMonthLow();
		this.monthHigh = bean.getMonthHigh();
		this.intraBuySignalActiveAt = bean.getIntraBuySignalActiveAt();
		this.intraSellSignalActiveAt = bean.getIntraSellSignalActiveAt();
		this.positionalBuySignalActiveAt = bean.getPositionalBuySignalActiveAt();
		this.positionalSellSignalActiveAt = bean.getPositionalSellSignalActiveAt();
		this.change = bean.getChange();
		this.symbolId = bean.getSymbolId();
		this.dateTimeStamp = bean.getDateTimeStamp();
	}

	public IntradayInfoBean populateBean() {
		IntradayInfoBean bean = new IntradayInfoBean();
		bean.setIntradayTradeId(this.intradayTradeId);
		bean.setPreviousDayLow(this.previousDayLow);
		bean.setPreviousDayHigh(this.previousDayHigh);
		bean.setPreviousDayClose(this.previousDayClose);
		bean.setAverageMinimumVal(this.averageMinimumVal);
		bean.setAverageHiLoClsVal(this.averageHiLoClsVal);
		bean.setIntradayUptrendValue(this.intradayUptrendValue);
		bean.setIntradayDowntrendValue(this.intradayDowntrendValue);
		bean.setPostionalSignal(this.postionalSignal);
		bean.setIntradaySignal(this.intradaySignal);
		bean.setOiPreviousDay(this.oiPreviousDay);
		bean.setOiToday(this.oiToday);
		bean.setMonthLow(this.monthLow);
		bean.setMonthHigh(this.monthHigh);
		bean.setIntraBuySignalActiveAt(this.intraBuySignalActiveAt);
		bean.setIntraSellSignalActiveAt(this.intraSellSignalActiveAt);
		bean.setPositionalBuySignalActiveAt(this.positionalBuySignalActiveAt);
		bean.setPositionalSellSignalActiveAt(this.positionalSellSignalActiveAt);
		bean.setChange(this.change);
		bean.setSymbolId(this.symbolId);
		bean.setDateTimeStamp(this.dateTimeStamp);
		return bean;
	}
	
	@Column(name = "SYMBOL_ID")
	private String symbolId;

	public String getSymbolId() {
		return symbolId;
	}

	public void setSymbolId(String symbolId) {
		this.symbolId = symbolId;
	}

}
