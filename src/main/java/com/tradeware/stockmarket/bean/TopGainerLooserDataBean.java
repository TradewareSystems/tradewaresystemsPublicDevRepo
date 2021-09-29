package com.tradeware.stockmarket.bean;

import java.math.BigDecimal;
import java.util.Date;

public class TopGainerLooserDataBean extends AbstractDataBean {

	private static final long serialVersionUID = 7919339635307903059L;
	private String indexSymbol;
	private String symbol;
	private Double open;
	private Double high;
	private Double low;
	private Double previousClose;
	private Double lastPrice;
	private Double change;
	private Double percentageChange;
	private Double yearHigh;
	private Double yearLow;
	private Long volumes;//Shares
	private BigDecimal valueInLakhs;
	
	private int sortOrder;
	private int topTenGainLooseSortOrder;
	private Date dateTimeStamp;
	private Date dateStamp;
	
	public TopGainerLooserDataBean(String symbol) {
		super();
		this.symbol = symbol;
	}
	
	public TopGainerLooserDataBean clone() {
		TopGainerLooserDataBean bean = new TopGainerLooserDataBean(this.symbol);
		bean.setIndexSymbol(indexSymbol);
		bean.setSymbol(symbol);
		bean.setOpen(open);
		bean.setHigh(high);
		bean.setLow(low);;
		bean.setPreviousClose(previousClose);
		bean.setLastPrice(lastPrice);
		bean.setChange(change);
		bean.setPercentageChange(percentageChange);
		bean.setYearHigh(yearHigh);
		bean.setYearLow(yearLow);
		bean.setVolumes(volumes);//Shares
		bean.setValueInLakhs(valueInLakhs);
		
		bean.setSortOrder(sortOrder);
		bean.setTopTenGainLooseSortOrder(topTenGainLooseSortOrder);
		bean.setDateTimeStamp(dateTimeStamp);
		bean.setDateStamp(dateStamp);
		return bean;
	}

	public String getIndexSymbol() {
		return indexSymbol;
	}

	public void setIndexSymbol(String indexSymbol) {
		this.indexSymbol = indexSymbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getPreviousClose() {
		return previousClose;
	}

	public void setPreviousClose(Double previousClose) {
		this.previousClose = previousClose;
	}

	public Double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Double getChange() {
		return change;
	}

	public void setChange(Double change) {
		this.change = change;
	}

	public Double getPercentageChange() {
		return percentageChange;
	}

	public void setPercentageChange(Double percentageChange) {
		this.percentageChange = percentageChange;
	}

	public Double getYearHigh() {
		return yearHigh;
	}

	public void setYearHigh(Double yearHigh) {
		this.yearHigh = yearHigh;
	}

	public Double getYearLow() {
		return yearLow;
	}

	public void setYearLow(Double yearLow) {
		this.yearLow = yearLow;
	}

	public Long getVolumes() {
		return volumes;
	}

	public void setVolumes(Long volumes) {
		this.volumes = volumes;
	}

	public BigDecimal getValueInLakhs() {
		return valueInLakhs;
	}

	public void setValueInLakhs(BigDecimal valueInLakhs) {
		this.valueInLakhs = valueInLakhs;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getTopTenGainLooseSortOrder() {
		return topTenGainLooseSortOrder;
	}

	public void setTopTenGainLooseSortOrder(int topTenGainLooseSortOrder) {
		this.topTenGainLooseSortOrder = topTenGainLooseSortOrder;
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
