package com.tradeware.clouddatabase.bean;

import java.util.Date;
import java.util.List;

public class OptionLiveTradeMainBean extends AbstractBean {
	private static final long serialVersionUID = 7105742037311382915L;
	private Integer tradeId;
	private String tradeName;
	private String symbolId;
	private Integer lotSize;
	private Double optionTickerSize;
	private String expiryDate;
	private String tradeStrategy;
	private String tradeSubStrategy;
	private Date tradedAtDtTm;
	private Date exitedAtDtTm;
	private Double profitLossAmtVal; 
	private Double positiveMoveVal;
	private Double negativeMoveVal;
	private Date tradedDateStamp;
	private String tradePosition; //OPEN/CLOSE
	private Long instrumentToken;
	private String kiteOrderId;
	private String kiteOrderType;//CO/BO/Regular
	private String comments;
	private Double breakEvenLower1;
	private Double breakEvenHigher1;
	private List<OptionLiveTradeChildBean> tradeChildBeanList;
	
	// properties specific to Butter Fly start
	private Double atmStrikePrice;
	private Double stopLossVal;
	private Double targetVal;
	private Boolean intradayTradeInd;
	// properties specific to Butter Fly start
	
	public Integer getTradeId() {
		return tradeId;
	}
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
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
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getTradeStrategy() {
		return tradeStrategy;
	}
	public void setTradeStrategy(String tradeStrategy) {
		this.tradeStrategy = tradeStrategy;
	}
	public String getTradeSubStrategy() {
		return tradeSubStrategy;
	}
	public void setTradeSubStrategy(String tradeSubStrategy) {
		this.tradeSubStrategy = tradeSubStrategy;
	}
	public Date getTradedAtDtTm() {
		return tradedAtDtTm;
	}
	public void setTradedAtDtTm(Date tradedAtDtTm) {
		this.tradedAtDtTm = tradedAtDtTm;
	}
	public Date getExitedAtDtTm() {
		return exitedAtDtTm;
	}
	public void setExitedAtDtTm(Date exitedAtDtTm) {
		this.exitedAtDtTm = exitedAtDtTm;
	}
	public Double getProfitLossAmtVal() {
		return profitLossAmtVal;
	}
	public void setProfitLossAmtVal(Double profitLossAmtVal) {
		this.profitLossAmtVal = profitLossAmtVal;
	}
	public Double getPositiveMoveVal() {
		return positiveMoveVal;
	}
	public void setPositiveMoveVal(Double positiveMoveVal) {
		this.positiveMoveVal = positiveMoveVal;
	}
	public Double getNegativeMoveVal() {
		return negativeMoveVal;
	}
	public void setNegativeMoveVal(Double negativeMoveVal) {
		this.negativeMoveVal = negativeMoveVal;
	}
	public Date getTradedDateStamp() {
		return tradedDateStamp;
	}
	public void setTradedDateStamp(Date tradedDateStamp) {
		this.tradedDateStamp = tradedDateStamp;
	}
	public String getTradePosition() {
		return tradePosition;
	}
	public void setTradePosition(String tradePosition) {
		this.tradePosition = tradePosition;
	}
	public Double getOptionTickerSize() {
		return optionTickerSize;
	}
	public void setOptionTickerSize(Double optionTickerSize) {
		this.optionTickerSize = optionTickerSize;
	}
	public Long getInstrumentToken() {
		return instrumentToken;
	}
	public void setInstrumentToken(Long instrumentToken) {
		this.instrumentToken = instrumentToken;
	}
	public String getKiteOrderId() {
		return kiteOrderId;
	}
	public void setKiteOrderId(String kiteOrderId) {
		this.kiteOrderId = kiteOrderId;
	}
	public String getKiteOrderType() {
		return kiteOrderType;
	}
	public void setKiteOrderType(String kiteOrderType) {
		this.kiteOrderType = kiteOrderType;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List<OptionLiveTradeChildBean> getTradeChildBeanList() {
		return tradeChildBeanList;
	}
	public void setTradeChildBeanList(List<OptionLiveTradeChildBean> tradeChildBeanList) {
		this.tradeChildBeanList = tradeChildBeanList;
	}
	public Double getBreakEvenLower1() {
		return breakEvenLower1;
	}
	public void setBreakEvenLower1(Double breakEvenLower1) {
		this.breakEvenLower1 = breakEvenLower1;
	}
	public Double getBreakEvenHigher1() {
		return breakEvenHigher1;
	}
	public void setBreakEvenHigher1(Double breakEvenHigher1) {
		this.breakEvenHigher1 = breakEvenHigher1;
	}
	public Double getAtmStrikePrice() {
		return atmStrikePrice;
	}
	public void setAtmStrikePrice(Double atmStrikePrice) {
		this.atmStrikePrice = atmStrikePrice;
	}
	public Double getStopLossVal() {
		return stopLossVal;
	}
	public void setStopLossVal(Double stopLossVal) {
		this.stopLossVal = stopLossVal;
	}
	public Double getTargetVal() {
		return targetVal;
	}
	public void setTargetVal(Double targetVal) {
		this.targetVal = targetVal;
	}
	public Boolean getIntradayTradeInd() {
		return intradayTradeInd;
	}
	public void setIntradayTradeInd(Boolean intradayTradeInd) {
		this.intradayTradeInd = intradayTradeInd;
	}
	
}
