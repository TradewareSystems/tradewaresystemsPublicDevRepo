package com.tradeware.stockmarket.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tradeware.clouddatabase.bean.AbstractBean;

public class ProfitLossSummaryDataBean extends AbstractBean{

	private static final long serialVersionUID = 5157925153198083358L;
	private Integer profitLossSummaryId;
	private String strategyRule;
	private Double profitLossBookedValueMinus5k;
	private Date dateTimeStampMinus5k;
	private Double profitLossBookedValue5k;
	private Date dateTimeStamp5k;
	private Double profitLossBookedValue10k;
	private Date dateTimeStamp10k;
	private Double profitLossBookedValue15k;
	private Date dateTimeStamp15k;
	private Double profitLossBookedValue20k;
	private Date dateTimeStamp20k;
	private Double profitLossBookedValue25k;
	private Date dateTimeStamp25k;
	private Double profitLossBookedValue30k;
	private Date dateTimeStamp30k;
	private Double profitLossBookedValue35k;
	private Date dateTimeStamp35k;
	private Double profitLossBookedValue40k;
	private Date dateTimeStamp40k;
	private Double profitLossBookedValue45k;
	private Date dateTimeStamp45k;
	private Double profitLossBookedValue50k;
	private Date dateTimeStamp50k;
	private Double maxProfitMoveValue;
	private Double maxLossMoveValue;
	private Double dayProfitLossValue;
	private Date dateTimeStampDayProfitLoss;
	private Date dateStamp;
	
	private Boolean scannerUpdatable = Boolean.TRUE;
	private Double trailingStopLossValue;
	
	public ProfitLossSummaryDataBean() {
		this.maxProfitMoveValue = 0d;
		this.maxLossMoveValue = 0d;
	}
	
	public Integer getProfitLossSummaryId() {
		return profitLossSummaryId;
	}
	public void setProfitLossSummaryId(Integer profitLossSummaryId) {
		this.profitLossSummaryId = profitLossSummaryId;
	}
	public String getStrategyRule() {
		return strategyRule;
	}
	public void setStrategyRule(String strategyRule) {
		this.strategyRule = strategyRule;
	}
	public Double getProfitLossBookedValueMinus5k() {
		return profitLossBookedValueMinus5k;
	}
	public void setProfitLossBookedValueMinus5k(Double profitLossBookedValueMinus5k) {
		this.profitLossBookedValueMinus5k = profitLossBookedValueMinus5k;
	}
	public Date getDateTimeStampMinus5k() {
		return dateTimeStampMinus5k;
	}
	public void setDateTimeStampMinus5k(Date dateTimeStampMinus5k) {
		this.dateTimeStampMinus5k = dateTimeStampMinus5k;
	}

	public Double getProfitLossBookedValue5k() {
		return profitLossBookedValue5k;
	}
	public void setProfitLossBookedValue5k(Double profitLossBookedValue5k) {
		this.profitLossBookedValue5k = profitLossBookedValue5k;
	}
	public Date getDateTimeStamp5k() {
		return dateTimeStamp5k;
	}
	public void setDateTimeStamp5k(Date dateTimeStamp5k) {
		this.dateTimeStamp5k = dateTimeStamp5k;
	}
	public Double getProfitLossBookedValue10k() {
		return profitLossBookedValue10k;
	}
	public void setProfitLossBookedValue10k(Double profitLossBookedValue10k) {
		this.profitLossBookedValue10k = profitLossBookedValue10k;
	}
	public Date getDateTimeStamp10k() {
		return dateTimeStamp10k;
	}
	public void setDateTimeStamp10k(Date dateTimeStamp10k) {
		this.dateTimeStamp10k = dateTimeStamp10k;
	}
	public Double getProfitLossBookedValue15k() {
		return profitLossBookedValue15k;
	}
	public void setProfitLossBookedValue15k(Double profitLossBookedValue15k) {
		this.profitLossBookedValue15k = profitLossBookedValue15k;
	}
	public Date getDateTimeStamp15k() {
		return dateTimeStamp15k;
	}
	public void setDateTimeStamp15k(Date dateTimeStamp15k) {
		this.dateTimeStamp15k = dateTimeStamp15k;
	}
	public Double getProfitLossBookedValue20k() {
		return profitLossBookedValue20k;
	}
	public void setProfitLossBookedValue20k(Double profitLossBookedValue20k) {
		this.profitLossBookedValue20k = profitLossBookedValue20k;
	}
	public Date getDateTimeStamp20k() {
		return dateTimeStamp20k;
	}
	public void setDateTimeStamp20k(Date dateTimeStamp20k) {
		this.dateTimeStamp20k = dateTimeStamp20k;
	}
	public Double getProfitLossBookedValue25k() {
		return profitLossBookedValue25k;
	}
	public void setProfitLossBookedValue25k(Double profitLossBookedValue25k) {
		this.profitLossBookedValue25k = profitLossBookedValue25k;
	}
	public Date getDateTimeStamp25k() {
		return dateTimeStamp25k;
	}
	public void setDateTimeStamp25k(Date dateTimeStamp25k) {
		this.dateTimeStamp25k = dateTimeStamp25k;
	}
	public Double getProfitLossBookedValue30k() {
		return profitLossBookedValue30k;
	}
	public void setProfitLossBookedValue30k(Double profitLossBookedValue30k) {
		this.profitLossBookedValue30k = profitLossBookedValue30k;
	}
	public Date getDateTimeStamp30k() {
		return dateTimeStamp30k;
	}
	public void setDateTimeStamp30k(Date dateTimeStamp30k) {
		this.dateTimeStamp30k = dateTimeStamp30k;
	}
	public Double getProfitLossBookedValue35k() {
		return profitLossBookedValue35k;
	}
	public void setProfitLossBookedValue35k(Double profitLossBookedValue35k) {
		this.profitLossBookedValue35k = profitLossBookedValue35k;
	}
	public Date getDateTimeStamp35k() {
		return dateTimeStamp35k;
	}
	public void setDateTimeStamp35k(Date dateTimeStamp35k) {
		this.dateTimeStamp35k = dateTimeStamp35k;
	}
	public Double getProfitLossBookedValue40k() {
		return profitLossBookedValue40k;
	}
	public void setProfitLossBookedValue40k(Double profitLossBookedValue40k) {
		this.profitLossBookedValue40k = profitLossBookedValue40k;
	}
	public Date getDateTimeStamp40k() {
		return dateTimeStamp40k;
	}
	public void setDateTimeStamp40k(Date dateTimeStamp40k) {
		this.dateTimeStamp40k = dateTimeStamp40k;
	}
	public Double getProfitLossBookedValue45k() {
		return profitLossBookedValue45k;
	}
	public void setProfitLossBookedValue45k(Double profitLossBookedValue45k) {
		this.profitLossBookedValue45k = profitLossBookedValue45k;
	}
	public Date getDateTimeStamp45k() {
		return dateTimeStamp45k;
	}
	public void setDateTimeStamp45k(Date dateTimeStamp45k) {
		this.dateTimeStamp45k = dateTimeStamp45k;
	}
	public Double getProfitLossBookedValue50k() {
		return profitLossBookedValue50k;
	}
	public void setProfitLossBookedValue50k(Double profitLossBookedValue50k) {
		this.profitLossBookedValue50k = profitLossBookedValue50k;
	}
	public Date getDateTimeStamp50k() {
		return dateTimeStamp50k;
	}
	public void setDateTimeStamp50k(Date dateTimeStamp50k) {
		this.dateTimeStamp50k = dateTimeStamp50k;
	}
	public Double getMaxProfitMoveValue() {
		return maxProfitMoveValue;
	}
	public void setMaxProfitMoveValue(Double maxProfitMoveValue) {
		this.maxProfitMoveValue = maxProfitMoveValue;
	}
	public Double getMaxLossMoveValue() {
		return maxLossMoveValue;
	}
	public void setMaxLossMoveValue(Double maxLossMoveValue) {
		this.maxLossMoveValue = maxLossMoveValue;
	}
	public Double getDayProfitLossValue() {
		return dayProfitLossValue;
	}
	public void setDayProfitLossValue(Double dayProfitLossValue) {
		this.dayProfitLossValue = dayProfitLossValue;
	}
	public Date getDateTimeStampDayProfitLoss() {
		return dateTimeStampDayProfitLoss;
	}
	public void setDateTimeStampDayProfitLoss(Date dateTimeStampDayProfitLoss) {
		this.dateTimeStampDayProfitLoss = dateTimeStampDayProfitLoss;
	}
	public Date getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
	public Boolean getScannerUpdatable() {
		return scannerUpdatable;
	}
	public void setScannerUpdatable(Boolean scannerUpdatable) {
		this.scannerUpdatable = scannerUpdatable;
	}
	public Double getTrailingStopLossValue() {
		return trailingStopLossValue;
	}
	public void setTrailingStopLossValue(Double trailingStopLossValue) {
		this.trailingStopLossValue = trailingStopLossValue;
	}
	
	public String getDateTimeStamp5kStr() {
		return getCurrentTime(this.dateTimeStamp5k);
	}

	public String getDateTimeStamp10kStr() {
		return getCurrentTime(this.dateTimeStamp10k);
	}

	public String getDateTimeStamp15kStr() {
		return getCurrentTime(this.dateTimeStamp15k);
	}

	public String getDateTimeStamp20kStr() {
		return getCurrentTime(this.dateTimeStamp20k);
	}

	public String getDateTimeStamp25kStr() {
		return getCurrentTime(this.dateTimeStamp25k);
	}

	public String getDateTimeStamp30kStr() {
		return getCurrentTime(this.dateTimeStamp30k);
	}

	public String getDateTimeStamp35kStr() {
		return getCurrentTime(this.dateTimeStamp35k);
	}

	public String getDateTimeStamp40kStr() {
		return getCurrentTime(this.dateTimeStamp40k);
	}

	public String getDateTimeStamp45kStr() {
		return getCurrentTime(this.dateTimeStamp45k);
	}

	public String getDateTimeStamp50kStr() {
		return getCurrentTime(this.dateTimeStamp50k);
	}

	public String getDateTimeStampDayProfitLossStr() {
		return getCurrentTime(this.dateTimeStampDayProfitLoss);
	}
	
	String EMPTY_STRING = "";
	String TIME_ZONE = "Asia/Kolkata";
	String DATE_PATTERN_WITH_AM_PM_3 = "hh:mm:ss a yyyy-MM-dd";
	public String getCurrentTime(Date date) {
		if (null != date) {
			SimpleDateFormat dtf = new SimpleDateFormat(DATE_PATTERN_WITH_AM_PM_3);
			return dtf.format(date);
		}
		return EMPTY_STRING;
	}
}
