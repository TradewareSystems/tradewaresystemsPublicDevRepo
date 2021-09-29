package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tradeware.clouddatabase.bean.SymbolBean;

@Entity
@Table(name = "T_SYMBOL")
public class SymbolEntity extends AbstractEntity {

	private static final long serialVersionUID = 862369734122906695L;

	@Id
	@Column(name = "SYMBOL_ID")
	private String symbolId;

	@Column(name = "LOT_SIZE")
	private Integer lotSize;
	
	@Column(name = "OPT_TICK_SIZE")
	private Double optionTickerSize;

	@Column(name = "FNO_IND")
	private Boolean fnoInd;

	@Column(name = "EQUITY_IND")
	private Boolean equityInd;

	@Column(name = "VALID_IND")
	private Boolean validInd;
	
	@Column(name = "INDEX_IND")
	private Boolean indexInd;

	@Column(name = "INSTRUMENT_TOKEN")
	private Long instrumentToken;
	
	@Column(name = "CATEGORY_FILTER")
	private Integer categoryFilter;

	@Column(name = "WEEK52_HIGH")
	private Double week52High;

	@Column(name = "WEEK52_LOW")
	private Double week52Low;

	@Column(name = "SECTOR")
	private String sector;
	
	@Column(name = "DT_TM_STAMP", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
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

	public Double getOptionTickerSize() {
		return optionTickerSize;
	}

	public void setOptionTickerSize(Double optionTickerSize) {
		this.optionTickerSize = optionTickerSize;
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

	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}

	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}

	public void populateEntity(SymbolBean bean) {
		this.symbolId = bean.getSymbolId();
		this.lotSize = bean.getLotSize();
		this.optionTickerSize = bean.getOptionTickerSize();
		this.fnoInd = bean.getFnoInd();
		this.equityInd = bean.getEquityInd();
		this.validInd = bean.getValidInd();
		this.indexInd = bean.getIndexInd();
		this.instrumentToken = bean.getInstrumentToken();
		this.categoryFilter = bean.getCategoryFilter();
		this.week52High = bean.getWeek52High();
		this.week52Low = bean.getWeek52Low();
		this.sector = bean.getSector();
		this.dateTimeStamp = bean.getDateTimeStamp();
		
		this.lastPrice = bean.getLastPrice();
		this.yrHiLoNearInd = bean.getYrHiLoNearInd();
		this.yrHiLoNearPer = bean.getYrHiLoNearPer();
		this.yrHiLoNearTrend = bean.getYrHiLoNearTrend();
		
		this.close9Sma = bean.getClose9Sma();
		this.close21Sma = bean.getClose21Sma();
		this.close50Sma = bean.getClose50Sma();
		this.close100Sma = bean.getClose100Sma();
		this.close200Sma = bean.getClose200Sma();
		this.close200SmaTradableRatio = bean.getClose200SmaTradableRatio();
		this.isClose200SmaTradable = bean.getIsClose200SmaTradable();
	}

	public SymbolBean populateBean() {
		SymbolBean bean = new SymbolBean();
		bean.setSymbolId(this.symbolId);
		bean.setLotSize(this.lotSize);
		bean.setOptionTickerSize(this.optionTickerSize);
		bean.setFnoInd(this.fnoInd);
		bean.setEquityInd(this.equityInd);
		bean.setValidInd(this.validInd);
		bean.setIndexInd(this.indexInd);
		bean.setInstrumentToken(this.instrumentToken);
		bean.setCategoryFilter(this.categoryFilter);
		bean.setWeek52High(this.week52High);
		bean.setWeek52Low(this.week52Low);
		bean.setSector(this.sector);
		bean.setDateTimeStamp(this.dateTimeStamp);
		bean.setLastPrice(this.lastPrice);
		bean.setYrHiLoNearInd(this.yrHiLoNearInd);
		bean.setYrHiLoNearPer(this.yrHiLoNearPer);
		bean.setYrHiLoNearTrend(this.yrHiLoNearTrend);

		bean.setClose9Sma(this.close9Sma);
		bean.setClose21Sma(this.close21Sma);
		bean.setClose50Sma(this.close50Sma);
		bean.setClose100Sma(this.close100Sma);
		bean.setClose200Sma(this.close200Sma);
		bean.setClose200SmaTradableRatio(this.close200SmaTradableRatio);
		bean.setIsClose200SmaTradable(this.isClose200SmaTradable);

		return bean;
	}
	
	@Column(name = "LAST_PRICE")
	private Double lastPrice;
	
	@Column(name = "YR_HI_LO_NEAR_PER")
	private Double yrHiLoNearPer;
	
	@Column(name = "YR_HI_LO_NEAR_IND")
	private Boolean yrHiLoNearInd;
	
	@Column(name = "YR_HI_LO_NEAR_TREND")
	private String yrHiLoNearTrend;
	
	@Column(name = "SMA_9_CLS")
	private Double close9Sma;
	
	@Column(name = "SMA_21_CLS")
	private Double close21Sma;
	
	@Column(name = "SMA_50_CLS")
	private Double close50Sma;
	
	@Column(name = "SMA_100_CLS")
	private Double close100Sma;
	
	@Column(name = "SMA_200_CLS")
	private Double close200Sma;
	
	@Column(name = "SMA_200_CLS_TRADE_RATIO")
	private Double close200SmaTradableRatio;
	
	@Column(name = "SMA_200_CLS_TRADE_IND")
	private Boolean isClose200SmaTradable;
}