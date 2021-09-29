package com.tradeware.clouddatabase.bean;

import java.util.Date;

public class StrategyHighLowBean extends AbstractBean {
	private static final long serialVersionUID = -3670248035388353026L;
	private Integer itemId;
	private String symbol;
	private Integer lotSize;
	private Double buyValue;
	private Double sellValue;
	private String tradableState;
	private String tradedState;
	private Double tradedAt;
	private Double exitedAt;
	private Date tradedAtDt;
	private Date exitedAtDt;
	private Double profitLossAmt; 
	private Double buySellDiff; 
	private Double positiveMoveValue;
	private Double negativeMoveValue;
	private String additionalInfo1;
	private Long volumes;
	private Boolean closeRule;
	private Boolean closeHighRule1;
	private Boolean closeHighRule2;
	private Boolean openHighLowRule;
	private Boolean openHighLowHARule;
	private Date tradedDate;
	
	private Boolean smallCandle;
	private Boolean oppositeHighLowRule;
	private Boolean nr4Rule;
	private Boolean dayHighLowRule;
	
	private String volumeTrend;
	private String lastCandle;
	private String ohlcState;
	private Double strengthFactor;
	
	private Double day1VolStrengthFactor;
	private Double day2VolStrengthFactor;
	private Long day1CandleVolumes;
	private Long currentCandleVolumesOnTradeEntry;
	private Boolean currentCandleOHLRuleInd;
	
	private Double vwapVal;
	private Double rsiVal;
	private Double candleMovement;
	private Double day1Movement;
	private Double day2Movement;
	private Double minute5CPR;
	private Double minute15CPR;
	private String additionalInfoNifty50;
	
	private String strenthTradableState;
	private String strenthTradableStateNifty;
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Integer getLotSize() {
		return lotSize;
	}
	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}
	public Double getBuyValue() {
		return buyValue;
	}
	public void setBuyValue(Double buyValue) {
		this.buyValue = buyValue;
	}
	public Double getSellValue() {
		return sellValue;
	}
	public void setSellValue(Double sellValue) {
		this.sellValue = sellValue;
	}
	public String getTradableState() {
		return tradableState;
	}
	public void setTradableState(String tradableState) {
		this.tradableState = tradableState;
	}
	public String getTradedState() {
		return tradedState;
	}
	public void setTradedState(String tradedState) {
		this.tradedState = tradedState;
	}
	public Double getTradedAt() {
		return tradedAt;
	}
	public void setTradedAt(Double tradedAt) {
		this.tradedAt = tradedAt;
	}
	public Double getExitedAt() {
		return exitedAt;
	}
	public void setExitedAt(Double exitedAt) {
		this.exitedAt = exitedAt;
	}
	public Date getTradedAtDt() {
		return tradedAtDt;
	}
	public void setTradedAtDt(Date tradedAtDt) {
		this.tradedAtDt = tradedAtDt;
	}
	public Date getExitedAtDt() {
		return exitedAtDt;
	}
	public void setExitedAtDt(Date exitedAtDt) {
		this.exitedAtDt = exitedAtDt;
	}
	public Double getProfitLossAmt() {
		return profitLossAmt;
	}
	public void setProfitLossAmt(Double profitLossAmt) {
		this.profitLossAmt = profitLossAmt;
	}
	public Double getBuySellDiff() {
		return buySellDiff;
	}
	public void setBuySellDiff(Double buySellDiff) {
		this.buySellDiff = buySellDiff;
	}
	public Double getPositiveMoveValue() {
		return positiveMoveValue;
	}
	public void setPositiveMoveValue(Double positiveMoveValue) {
		this.positiveMoveValue = positiveMoveValue;
	}
	public Double getNegativeMoveValue() {
		return negativeMoveValue;
	}
	public void setNegativeMoveValue(Double negativeMoveValue) {
		this.negativeMoveValue = negativeMoveValue;
	}
	public String getAdditionalInfo1() {
		return additionalInfo1;
	}
	public void setAdditionalInfo1(String additionalInfo1) {
		this.additionalInfo1 = additionalInfo1;
	}
	public Long getVolumes() {
		return volumes;
	}
	public void setVolumes(Long volumes) {
		this.volumes = volumes;
	}
	public Boolean getCloseRule() {
		return closeRule;
	}
	public void setCloseRule(Boolean closeRule) {
		this.closeRule = closeRule;
	}
	public Boolean getCloseHighRule1() {
		return closeHighRule1;
	}
	public void setCloseHighRule1(Boolean closeHighRule1) {
		this.closeHighRule1 = closeHighRule1;
	}
	public Boolean getCloseHighRule2() {
		return closeHighRule2;
	}
	public void setCloseHighRule2(Boolean closeHighRule2) {
		this.closeHighRule2 = closeHighRule2;
	}
	public Boolean getOpenHighLowRule() {
		return openHighLowRule;
	}
	public void setOpenHighLowRule(Boolean openHighLowRule) {
		this.openHighLowRule = openHighLowRule;
	}
	public Boolean getOpenHighLowHARule() {
		return openHighLowHARule;
	}
	public void setOpenHighLowHARule(Boolean openHighLowHARule) {
		this.openHighLowHARule = openHighLowHARule;
	}
	public Date getTradedDate() {
		return tradedDate;
	}
	public void setTradedDate(Date tradedDate) {
		this.tradedDate = tradedDate;
	}
	public Boolean getSmallCandle() {
		return smallCandle;
	}
	public void setSmallCandle(Boolean smallCandle) {
		this.smallCandle = smallCandle;
	}
	public Boolean getOppositeHighLowRule() {
		return oppositeHighLowRule;
	}
	public void setOppositeHighLowRule(Boolean oppositeHighLowRule) {
		this.oppositeHighLowRule = oppositeHighLowRule;
	}
	public Boolean getNr4Rule() {
		return nr4Rule;
	}
	public void setNr4Rule(Boolean nr4Rule) {
		this.nr4Rule = nr4Rule;
	}
	public Boolean getDayHighLowRule() {
		return dayHighLowRule;
	}
	public void setDayHighLowRule(Boolean dayHighLowRule) {
		this.dayHighLowRule = dayHighLowRule;
	}
	public String getVolumeTrend() {
		return volumeTrend;
	}
	public void setVolumeTrend(String volumeTrend) {
		this.volumeTrend = volumeTrend;
	}
	public String getLastCandle() {
		return lastCandle;
	}
	public void setLastCandle(String lastCandle) {
		this.lastCandle = lastCandle;
	}
	public String getOhlcState() {
		return ohlcState;
	}
	public void setOhlcState(String ohlcState) {
		this.ohlcState = ohlcState;
	}
	public Double getStrengthFactor() {
		return strengthFactor;
	}
	public void setStrengthFactor(Double strengthFactor) {
		this.strengthFactor = strengthFactor;
	}
	public Double getDay1VolStrengthFactor() {
		return day1VolStrengthFactor;
	}
	public void setDay1VolStrengthFactor(Double day1VolStrengthFactor) {
		this.day1VolStrengthFactor = day1VolStrengthFactor;
	}
	public Double getDay2VolStrengthFactor() {
		return day2VolStrengthFactor;
	}
	public void setDay2VolStrengthFactor(Double day2VolStrengthFactor) {
		this.day2VolStrengthFactor = day2VolStrengthFactor;
	}
	public Long getDay1CandleVolumes() {
		return day1CandleVolumes;
	}
	public void setDay1CandleVolumes(Long day1CandleVolumes) {
		this.day1CandleVolumes = day1CandleVolumes;
	}
	public Long getCurrentCandleVolumesOnTradeEntry() {
		return currentCandleVolumesOnTradeEntry;
	}
	public void setCurrentCandleVolumesOnTradeEntry(Long currentCandleVolumesOnTradeEntry) {
		this.currentCandleVolumesOnTradeEntry = currentCandleVolumesOnTradeEntry;
	}
	public Boolean getCurrentCandleOHLRuleInd() {
		return currentCandleOHLRuleInd;
	}
	public void setCurrentCandleOHLRuleInd(Boolean currentCandleOHLRuleInd) {
		this.currentCandleOHLRuleInd = currentCandleOHLRuleInd;
	}

	public Double getVwapVal() {
		return vwapVal;
	}
	public void setVwapVal(Double vwapVal) {
		this.vwapVal = vwapVal;
	}
	public Double getRsiVal() {
		return rsiVal;
	}
	public void setRsiVal(Double rsiVal) {
		this.rsiVal = rsiVal;
	}
	public Double getCandleMovement() {
		return candleMovement;
	}
	public void setCandleMovement(Double candleMovement) {
		this.candleMovement = candleMovement;
	}
	public Double getDay1Movement() {
		return day1Movement;
	}
	public void setDay1Movement(Double day1Movement) {
		this.day1Movement = day1Movement;
	}
	public Double getDay2Movement() {
		return day2Movement;
	}
	public void setDay2Movement(Double day2Movement) {
		this.day2Movement = day2Movement;
	}
	public Double getMinute5CPR() {
		return minute5CPR;
	}
	public void setMinute5CPR(Double minute5cpr) {
		minute5CPR = minute5cpr;
	}
	public Double getMinute15CPR() {
		return minute15CPR;
	}
	public void setMinute15CPR(Double minute15cpr) {
		minute15CPR = minute15cpr;
	}
	public String getAdditionalInfoNifty50() {
		return additionalInfoNifty50;
	}
	public void setAdditionalInfoNifty50(String additionalInfoNifty50) {
		this.additionalInfoNifty50 = additionalInfoNifty50;
	}
	public String getStrenthTradableState() {
		return strenthTradableState;
	}
	public void setStrenthTradableState(String strenthTradableState) {
		this.strenthTradableState = strenthTradableState;
	}
	public String getStrenthTradableStateNifty() {
		return strenthTradableStateNifty;
	}
	public void setStrenthTradableStateNifty(String strenthTradableStateNifty) {
		this.strenthTradableStateNifty = strenthTradableStateNifty;
	}
}