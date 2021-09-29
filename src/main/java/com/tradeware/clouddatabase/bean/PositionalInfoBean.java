package com.tradeware.clouddatabase.bean;

import java.util.Date;

public class PositionalInfoBean extends AbstractBean {

	private static final long serialVersionUID = 93934640164971173L;
	private Integer positionalTradeId;
	private String symbolId;
	private Double lotsize;
	private Double tickerSize;
	private Double yearLow;
	private Double yearHigh;
	private Double monthLow;
	private Double monthHigh;
	private Double firstDayClose;
	private Integer avgHLC;
	private Double averageContracts;
	private Double averageTurnoverActual;//In ruppes
	private Double averageTurnover;// Turnover (in lacs);
	private Double atpValue;
	private Double differenceValue;//first close - ATP
	private Integer numberOfContracts;
	private Double positionalUptrendValue;
	private Double positionalDowntrendValue;
	private Date dateTimeStamp;
	private Date dateStamp;
	public Integer getPositionalTradeId() {
		return positionalTradeId;
	}
	public void setPositionalTradeId(Integer positionalTradeId) {
		this.positionalTradeId = positionalTradeId;
	}
	public String getSymbolId() {
		return symbolId;
	}
	public void setSymbolId(String symbolId) {
		this.symbolId = symbolId;
	}
	public Double getLotsize() {
		return lotsize;
	}
	public void setLotsize(Double lotsize) {
		this.lotsize = lotsize;
	}
	public Double getTickerSize() {
		return tickerSize;
	}
	public void setTickerSize(Double tickerSize) {
		this.tickerSize = tickerSize;
	}
	public Double getYearLow() {
		return yearLow;
	}
	public void setYearLow(Double yearLow) {
		this.yearLow = yearLow;
	}
	public Double getYearHigh() {
		return yearHigh;
	}
	public void setYearHigh(Double yearHigh) {
		this.yearHigh = yearHigh;
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
	public Double getFirstDayClose() {
		return firstDayClose;
	}
	public void setFirstDayClose(Double firstDayClose) {
		this.firstDayClose = firstDayClose;
	}
	public Integer getAvgHLC() {
		return avgHLC;
	}
	public void setAvgHLC(Integer avgHLC) {
		this.avgHLC = avgHLC;
	}
	public Double getAverageContracts() {
		return averageContracts;
	}
	public void setAverageContracts(Double averageContracts) {
		this.averageContracts = averageContracts;
	}
	public Double getAverageTurnoverActual() {
		return averageTurnoverActual;
	}
	public void setAverageTurnoverActual(Double averageTurnoverActual) {
		this.averageTurnoverActual = averageTurnoverActual;
	}
	public Double getAverageTurnover() {
		return averageTurnover;
	}
	public void setAverageTurnover(Double averageTurnover) {
		this.averageTurnover = averageTurnover;
	}
	public Double getAtpValue() {
		return atpValue;
	}
	public void setAtpValue(Double atpValue) {
		this.atpValue = atpValue;
	}
	public Double getDifferenceValue() {
		return differenceValue;
	}
	public void setDifferenceValue(Double differenceValue) {
		this.differenceValue = differenceValue;
	}
	public Integer getNumberOfContracts() {
		return numberOfContracts;
	}
	public void setNumberOfContracts(Integer numberOfContracts) {
		this.numberOfContracts = numberOfContracts;
	}
	public Double getPositionalUptrendValue() {
		return positionalUptrendValue;
	}
	public void setPositionalUptrendValue(Double positionalUptrendValue) {
		this.positionalUptrendValue = positionalUptrendValue;
	}
	public Double getPositionalDowntrendValue() {
		return positionalDowntrendValue;
	}
	public void setPositionalDowntrendValue(Double positionalDowntrendValue) {
		this.positionalDowntrendValue = positionalDowntrendValue;
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
}