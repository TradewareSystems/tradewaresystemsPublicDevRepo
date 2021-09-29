package com.tradeware.clouddatabase.bean;

import java.io.Serializable;
import java.util.Date;

public class Narrow7Bean implements Serializable{

	private static final long serialVersionUID = -8504798336990775612L;
	private Integer itemId;
	private String symbol;
	private Integer lotSize;
	private Double buyAtVal;
	private Double sellAtVal;
	private String tradableState;
	private String tradedState;
	private Double tradedAtVal;
	private Double exitedAtVal;
	private Date tradedAtDt;
	private Date exitedAtDt;
	private Double profitLossAmt; 
	private Double buySellDiff; 
	private Double positiveMoveValue;
	private Double negativeMoveValue;
	private Long volumes;
	private String tradableStrength;
	private String strengthTradableState;
	
	private Double candle1SizeAmt;
	private Double candle2SizeAmt;
	private Double candle3SizeAmt;
	private Double candle4SizeAmt;
	private Double candle1HighMinusClose;
	private Double candle1CloseMinusLow;
	private Double candle2HighMinusClose;
	private Double candle2CloseMinusLow;
	private Double candle3HighMinusClose;
	private Double candle3CloseMinusLow;
	private Double candle4HighMinusClose;
	private Double candle4CloseMinusLow;
	private String candle1Type;
	private String candle2Type;
	private String candle3Type;
	private String candle4Type;
	private String ohlcState;
	private Boolean tempCustomTradingRuleInd;
	private Date tradedDateStamp;
	private String oiInfo;
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
	public Double getBuyAtVal() {
		return buyAtVal;
	}
	public void setBuyAtVal(Double buyAtVal) {
		this.buyAtVal = buyAtVal;
	}
	public Double getSellAtVal() {
		return sellAtVal;
	}
	public void setSellAtVal(Double sellAtVal) {
		this.sellAtVal = sellAtVal;
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
	public Double getTradedAtVal() {
		return tradedAtVal;
	}
	public void setTradedAtVal(Double tradedAtVal) {
		this.tradedAtVal = tradedAtVal;
	}
	public Double getExitedAtVal() {
		return exitedAtVal;
	}
	public void setExitedAtVal(Double exitedAtVal) {
		this.exitedAtVal = exitedAtVal;
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
	public Long getVolumes() {
		return volumes;
	}
	public void setVolumes(Long volumes) {
		this.volumes = volumes;
	}
	public String getTradableStrength() {
		return tradableStrength;
	}
	public void setTradableStrength(String tradableStrength) {
		this.tradableStrength = tradableStrength;
	}
	public String getStrengthTradableState() {
		return strengthTradableState;
	}
	public void setStrengthTradableState(String strengthTradableState) {
		this.strengthTradableState = strengthTradableState;
	}
	public Double getCandle1SizeAmt() {
		return candle1SizeAmt;
	}
	public void setCandle1SizeAmt(Double candle1SizeAmt) {
		this.candle1SizeAmt = candle1SizeAmt;
	}
	public Double getCandle2SizeAmt() {
		return candle2SizeAmt;
	}
	public void setCandle2SizeAmt(Double candle2SizeAmt) {
		this.candle2SizeAmt = candle2SizeAmt;
	}
	public Double getCandle3SizeAmt() {
		return candle3SizeAmt;
	}
	public void setCandle3SizeAmt(Double candle3SizeAmt) {
		this.candle3SizeAmt = candle3SizeAmt;
	}
	public Double getCandle4SizeAmt() {
		return candle4SizeAmt;
	}
	public void setCandle4SizeAmt(Double candle4SizeAmt) {
		this.candle4SizeAmt = candle4SizeAmt;
	}
	public Double getCandle1HighMinusClose() {
		return candle1HighMinusClose;
	}
	public void setCandle1HighMinusClose(Double candle1HighMinusClose) {
		this.candle1HighMinusClose = candle1HighMinusClose;
	}
	public Double getCandle1CloseMinusLow() {
		return candle1CloseMinusLow;
	}
	public void setCandle1CloseMinusLow(Double candle1CloseMinusLow) {
		this.candle1CloseMinusLow = candle1CloseMinusLow;
	}
	public Double getCandle2HighMinusClose() {
		return candle2HighMinusClose;
	}
	public void setCandle2HighMinusClose(Double candle2HighMinusClose) {
		this.candle2HighMinusClose = candle2HighMinusClose;
	}
	public Double getCandle2CloseMinusLow() {
		return candle2CloseMinusLow;
	}
	public void setCandle2CloseMinusLow(Double candle2CloseMinusLow) {
		this.candle2CloseMinusLow = candle2CloseMinusLow;
	}
	public Double getCandle3HighMinusClose() {
		return candle3HighMinusClose;
	}
	public void setCandle3HighMinusClose(Double candle3HighMinusClose) {
		this.candle3HighMinusClose = candle3HighMinusClose;
	}
	public Double getCandle3CloseMinusLow() {
		return candle3CloseMinusLow;
	}
	public void setCandle3CloseMinusLow(Double candle3CloseMinusLow) {
		this.candle3CloseMinusLow = candle3CloseMinusLow;
	}
	public Double getCandle4HighMinusClose() {
		return candle4HighMinusClose;
	}
	public void setCandle4HighMinusClose(Double candle4HighMinusClose) {
		this.candle4HighMinusClose = candle4HighMinusClose;
	}
	public Double getCandle4CloseMinusLow() {
		return candle4CloseMinusLow;
	}
	public void setCandle4CloseMinusLow(Double candle4CloseMinusLow) {
		this.candle4CloseMinusLow = candle4CloseMinusLow;
	}
	public String getCandle1Type() {
		return candle1Type;
	}
	public void setCandle1Type(String candle1Type) {
		this.candle1Type = candle1Type;
	}
	public String getCandle2Type() {
		return candle2Type;
	}
	public void setCandle2Type(String candle2Type) {
		this.candle2Type = candle2Type;
	}
	public String getCandle3Type() {
		return candle3Type;
	}
	public void setCandle3Type(String candle3Type) {
		this.candle3Type = candle3Type;
	}
	public String getCandle4Type() {
		return candle4Type;
	}
	public void setCandle4Type(String candle4Type) {
		this.candle4Type = candle4Type;
	}
	public String getOhlcState() {
		return ohlcState;
	}
	public void setOhlcState(String ohlcState) {
		this.ohlcState = ohlcState;
	}
	public Boolean getTempCustomTradingRuleInd() {
		return tempCustomTradingRuleInd;
	}
	public void setTempCustomTradingRuleInd(Boolean tempCustomTradingRuleInd) {
		this.tempCustomTradingRuleInd = tempCustomTradingRuleInd;
	}
	public Date getTradedDateStamp() {
		return tradedDateStamp;
	}
	public void setTradedDateStamp(Date tradedDateStamp) {
		this.tradedDateStamp = tradedDateStamp;
	}
	public String getOiInfo() {
		return oiInfo;
	}
	public void setOiInfo(String oiInfo) {
		this.oiInfo = oiInfo;
	}
}