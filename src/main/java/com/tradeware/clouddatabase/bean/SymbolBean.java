package com.tradeware.clouddatabase.bean;

import java.util.Date;

public class SymbolBean extends AbstractBean {

	private static final long serialVersionUID = 7488609656827667132L;
	private String symbolId;
	private Integer lotSize;
	private Boolean fnoInd;
	private Boolean equityInd;
	private Boolean validInd;
	private Boolean indexInd;
	private Long instrumentToken;
	private Integer categoryFilter;
	private Double week52High;
	private Double week52Low;
	private String sector;
	private Double optionTickerSize;
	private Date dateTimeStamp;
	
	public String getSymbolId() {
		return symbolId;
	}
	public void setSymbolId(String symbolId) {
		this.symbolId = symbolId;
	}
	public Integer getLotSize() {
		return lotSize;
	}
	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}
	public Boolean getFnoInd() {
		return fnoInd;
	}
	public void setFnoInd(Boolean fnoInd) {
		this.fnoInd = fnoInd;
	}
	public Boolean getEquityInd() {
		return equityInd;
	}
	public void setEquityInd(Boolean equityInd) {
		this.equityInd = equityInd;
	}
	public Boolean getValidInd() {
		return validInd;
	}
	public void setValidInd(Boolean validInd) {
		this.validInd = validInd;
	}
	public Boolean getIndexInd() {
		return indexInd;
	}
	public void setIndexInd(Boolean indexInd) {
		this.indexInd = indexInd;
	}
	public Long getInstrumentToken() {
		return instrumentToken;
	}
	public void setInstrumentToken(Long instrumentToken) {
		this.instrumentToken = instrumentToken;
	}
	public Integer getCategoryFilter() {
		return categoryFilter;
	}
	public void setCategoryFilter(Integer categoryFilter) {
		this.categoryFilter = categoryFilter;
	}
	public Double getWeek52High() {
		return week52High;
	}
	public void setWeek52High(Double week52High) {
		this.week52High = week52High;
	}
	public Double getWeek52Low() {
		return week52Low;
	}
	public void setWeek52Low(Double week52Low) {
		this.week52Low = week52Low;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public Double getOptionTickerSize() {
		return optionTickerSize;
	}
	public void setOptionTickerSize(Double optionTickerSize) {
		this.optionTickerSize = optionTickerSize;
	}
	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}
	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}
	
	
	//
	private Double lastPrice;
	private Double yrHiLoNearPer;
	private Boolean yrHiLoNearInd;
	private String yrHiLoNearTrend;
	
	private Double close9Sma;
	private Double close21Sma;
	private Double close50Sma;
	private Double close100Sma;
	private Double close200Sma;
	private Double close200SmaTradableRatio;
	private Boolean isClose200SmaTradable;
	
	public Double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}
	public Double getYrHiLoNearPer() {
		return yrHiLoNearPer;
	}
	public void setYrHiLoNearPer(Double yrHiLoNearPer) {
		this.yrHiLoNearPer = yrHiLoNearPer;
	}
	public Boolean getYrHiLoNearInd() {
		return yrHiLoNearInd;
	}
	public void setYrHiLoNearInd(Boolean yrHiLoNearInd) {
		this.yrHiLoNearInd = yrHiLoNearInd;
	}
	public String getYrHiLoNearTrend() {
		return yrHiLoNearTrend;
	}
	public void setYrHiLoNearTrend(String yrHiLoNearTrend) {
		this.yrHiLoNearTrend = yrHiLoNearTrend;
	}
	public Double getClose9Sma() {
		return close9Sma;
	}
	public void setClose9Sma(Double close9Sma) {
		this.close9Sma = close9Sma;
	}
	public Double getClose21Sma() {
		return close21Sma;
	}
	public void setClose21Sma(Double close21Sma) {
		this.close21Sma = close21Sma;
	}
	public Double getClose50Sma() {
		return close50Sma;
	}
	public void setClose50Sma(Double close50Sma) {
		this.close50Sma = close50Sma;
	}
	public Double getClose100Sma() {
		return close100Sma;
	}
	public void setClose100Sma(Double close100Sma) {
		this.close100Sma = close100Sma;
	}
	public Double getClose200Sma() {
		return close200Sma;
	}
	public void setClose200Sma(Double close200Sma) {
		this.close200Sma = close200Sma;
	}
	public Double getClose200SmaTradableRatio() {
		return close200SmaTradableRatio;
	}
	public void setClose200SmaTradableRatio(Double close200SmaTradableRatio) {
		this.close200SmaTradableRatio = close200SmaTradableRatio;
	}
	public Boolean getIsClose200SmaTradable() {
		return isClose200SmaTradable;
	}
	public void setIsClose200SmaTradable(Boolean isClose200SmaTradable) {
		this.isClose200SmaTradable = isClose200SmaTradable;
	}
}