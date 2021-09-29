package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tradeware.clouddatabase.bean.PositionalInfoBean;

@Entity
@Table(name = "T_POSITIONAL_TRADE")
public class PositionalInfoEntity extends AbstractEntity {

	private static final long serialVersionUID = 1536735791651805780L;
	
	@Id
	@Column(name = "POSITIONAL_TRADE_ID")
	private Integer positionalTradeId;
	
	@Column(name = "SYMBOL_ID")
	private String symbolId;

	@Column(name = "LOT_SIZE")
	private Double lotsize;
	
	@Column(name = "OPT_TICK_SIZE")
	private Double tickerSize;
	
	@Column(name = "YEAR_LOW")
	private Double yearLow;
	
	@Column(name = "YEAR_HIGH")
	private Double yearHigh;
	
	@Column(name = "MONTH_LOW")
	private Double monthLow;
	
	@Column(name = "MONTH_HIGH")
	private Double monthHigh;
	
	@Column(name = "FIRST_DAY_CLOSE")
	private Double firstDayClose;
	
	@Column(name = "AVG_HLC")
	private Integer avgHLC;
	
	@Column(name = "AVG_CONTRACTS")
	private Double averageContracts;
	
	@Column(name = "AVG_TURNOVER_IN_RUPPES")
	private Double averageTurnoverActual;//In ruppes
	
	@Column(name = "AVG_TURNOVER_IN_LAKHS")
	private Double averageTurnover;// Turnover (in lacs);
	
	@Column(name = "ATP_VALUE")
	private Double atpValue;
	
	@Column(name = "DIFFERENCE_VALUE")
	private Double differenceValue;//first close - ATP
	
	@Column(name = "NUM_OF_CONTRACTS")
	private Integer numberOfContracts;
	
	@Column(name = "UP_TREND_VALUE")
	private Double positionalUptrendValue;
	
	@Column(name = "DOWN_TREND_VALUE")
	private Double positionalDowntrendValue;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_TM_STAMP")
	private Date dateTimeStamp;
	
	@Column(name = "DT_STAMP")
	@Temporal(TemporalType.DATE)
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
	
	public void populateEntity(PositionalInfoBean bean) {
		this.positionalTradeId = bean.getPositionalTradeId();
		this.symbolId = bean.getSymbolId();
		this.lotsize = bean.getLotsize();
		this.tickerSize = bean.getTickerSize();
		this.yearLow = bean.getYearLow();
		this.yearHigh = bean.getYearHigh();
		this.monthLow = bean.getMonthLow();
		this.monthHigh = bean.getMonthHigh();
		this.firstDayClose = bean.getFirstDayClose();
		this.avgHLC = bean.getAvgHLC();
		this.averageContracts = bean.getAverageContracts();
		this.averageTurnoverActual = bean.getAverageTurnoverActual();
		this.averageTurnover = bean.getAverageTurnover();
		this.atpValue = bean.getAtpValue();
		this.differenceValue = bean.getDifferenceValue();
		this.numberOfContracts = bean.getNumberOfContracts();
		this.positionalUptrendValue = bean.getPositionalUptrendValue();
		this.positionalDowntrendValue = bean.getPositionalDowntrendValue();
		this.dateTimeStamp = bean.getDateTimeStamp() ;
		this.dateStamp = bean.getDateStamp();
	}

	public PositionalInfoBean populateBean() {
		PositionalInfoBean bean = new PositionalInfoBean();
		bean.setPositionalTradeId(this.positionalTradeId);
		bean.setSymbolId(this.symbolId);
		bean.setLotsize(this.lotsize);
		bean.setTickerSize(this.tickerSize);
		bean.setYearLow(this.yearLow);
		bean.setYearHigh(this.yearHigh);
		bean.setMonthLow(this.monthLow);
		bean.setMonthHigh(this.monthHigh);
		bean.setFirstDayClose(this.firstDayClose);
		bean.setAvgHLC(this.avgHLC);
		bean.setAverageContracts(this.averageContracts);
		bean.setAverageTurnoverActual(this.averageTurnoverActual);
		bean.setAverageTurnover(this.averageTurnover);
		bean.setAtpValue(this.atpValue);
		bean.setDifferenceValue(this.differenceValue);
		bean.setNumberOfContracts(this.numberOfContracts);
		bean.setPositionalUptrendValue(this.positionalUptrendValue);
		bean.setPositionalDowntrendValue(this.positionalDowntrendValue);
		bean.setDateTimeStamp(this.dateTimeStamp);
		bean.setDateStamp(this.dateStamp);
		return bean;
	}
}
