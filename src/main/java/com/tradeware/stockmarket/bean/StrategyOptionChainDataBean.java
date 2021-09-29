package com.tradeware.stockmarket.bean;

import java.util.Date;

public class StrategyOptionChainDataBean extends AbstractTradeDataBean {
	private static final long serialVersionUID = -9104483639722108864L;
	
	private Integer optionChainBreakOutId;
	private Integer optionChainId;
	private String tradableState;
	private String tradedState;
	private Double positiveMoveVal;
	private Double negativeMoveVal;
	private Double candle1SizeAmt;
	private Double candle2SizeAmt;
	private Double candle3SizeAmt;
	private Double candle1Open;
	private Double candle1High;
	private Double candle1Low;
	private Double candle1Close;
	private Double candle2Open;
	private Double candle2High;
	private Double candle2Low;
	private Double candle2Close;
	private Double candle3Open;
	private Double candle3High;
	private Double candle3Low;
	private Double candle3Close;
	private Double candle1HighMinusClose;
	private Double candle1CloseMinusLow;
	private Double candle2HighMinusClose;
	private Double candle2CloseMinusLow;
	private Double candle3HighMinusClose;
	private Double candle3CloseMinusLow;
	private String candle1Type;
	private String candle2Type;
	private String candle3Type;
	private String ohlcState;
	private Integer candleNumber;
	private Double gapUpDownMoveVal;
	private Date tradedDateStamp;
	private String heikinAshiTrend;
	private String optionChainTrend;
	private String optionChainPriceTrend;
	private Integer tradeCount;
	private Double profitLossAmtValFinal;
	private String strikePrices;
	private String liveTradingMapKey;
	
	public StrategyOptionChainDataBean(Integer lotSize, String symbolId) {
		super(lotSize, symbolId);
	}
	
	public StrategyOptionChainDataBean clone() {
		StrategyOptionChainDataBean bean = new StrategyOptionChainDataBean(super.getLotSize(), super.getSymbolId());
		super.clone(bean);

		bean.setOptionChainBreakOutId(this.optionChainBreakOutId);
		bean.setOptionChainId(this.optionChainId);
		bean.setTradableState(this.tradableState);
		bean.setTradedState(this.tradedState);
		bean.setPositiveMoveVal(this.positiveMoveVal);
		bean.setNegativeMoveVal(this.negativeMoveVal);
		bean.setCandle1SizeAmt(this.candle1SizeAmt);
		bean.setCandle2SizeAmt(this.candle2SizeAmt);
		bean.setCandle3SizeAmt(this.candle3SizeAmt);
		bean.setCandle1Open(this.candle1Open);
		bean.setCandle1High(this.candle1High);
		bean.setCandle1Low(this.candle1Low);
		bean.setCandle1Close(this.candle1Close);
		bean.setCandle2Open(this.candle2Open);
		bean.setCandle2High(this.candle2High);
		bean.setCandle2Low(this.candle2Low);
		bean.setCandle2Close(this.candle2Close);
		bean.setCandle3Open(this.candle3Open);
		bean.setCandle3High(this.candle3High);
		bean.setCandle3Low(this.candle3Low);
		bean.setCandle3Close(this.candle3Close);
		bean.setCandle1HighMinusClose(this.candle1HighMinusClose);
		bean.setCandle1CloseMinusLow(this.candle1CloseMinusLow);
		bean.setCandle2HighMinusClose(this.candle2HighMinusClose);
		bean.setCandle2CloseMinusLow(this.candle2CloseMinusLow);
		bean.setCandle3HighMinusClose(this.candle3HighMinusClose);
		bean.setCandle3CloseMinusLow(this.candle3CloseMinusLow);
		bean.setCandle1Type(this.candle1Type);
		bean.setCandle2Type(this.candle2Type);
		bean.setCandle3Type(this.candle3Type);
		bean.setOhlcState(this.ohlcState);
		bean.setCandleNumber(this.candleNumber);
		bean.setGapUpDownMoveVal(this.gapUpDownMoveVal);
		bean.setTradedDateStamp(this.tradedDateStamp);
		bean.setHeikinAshiTrend(this.heikinAshiTrend);
		bean.setOptionChainTrend(this.optionChainTrend);
		bean.setOptionChainPriceTrend(this.optionChainPriceTrend);
		bean.setTradeCount(this.tradeCount);
		bean.setProfitLossAmtValFinal(this.profitLossAmtValFinal);
		bean.setStrikePrices(this.strikePrices);
		bean.setLiveTradingMapKey(this.liveTradingMapKey);
		return bean;
	}
	public Integer getOptionChainBreakOutId() {
		return optionChainBreakOutId;
	}
	public void setOptionChainBreakOutId(Integer optionChainBreakOutId) {
		this.optionChainBreakOutId = optionChainBreakOutId;
	}
	public Integer getOptionChainId() {
		return optionChainId;
	}
	public void setOptionChainId(Integer optionChainId) {
		this.optionChainId = optionChainId;
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
	public Double getCandle1Open() {
		return candle1Open;
	}
	public void setCandle1Open(Double candle1Open) {
		this.candle1Open = candle1Open;
	}
	public Double getCandle1High() {
		return candle1High;
	}
	public void setCandle1High(Double candle1High) {
		this.candle1High = candle1High;
	}
	public Double getCandle1Low() {
		return candle1Low;
	}
	public void setCandle1Low(Double candle1Low) {
		this.candle1Low = candle1Low;
	}
	public Double getCandle1Close() {
		return candle1Close;
	}
	public void setCandle1Close(Double candle1Close) {
		this.candle1Close = candle1Close;
	}
	public Double getCandle2Open() {
		return candle2Open;
	}
	public void setCandle2Open(Double candle2Open) {
		this.candle2Open = candle2Open;
	}
	public Double getCandle2High() {
		return candle2High;
	}
	public void setCandle2High(Double candle2High) {
		this.candle2High = candle2High;
	}
	public Double getCandle2Low() {
		return candle2Low;
	}
	public void setCandle2Low(Double candle2Low) {
		this.candle2Low = candle2Low;
	}
	public Double getCandle2Close() {
		return candle2Close;
	}
	public void setCandle2Close(Double candle2Close) {
		this.candle2Close = candle2Close;
	}
	public Double getCandle3Open() {
		return candle3Open;
	}
	public void setCandle3Open(Double candle3Open) {
		this.candle3Open = candle3Open;
	}
	public Double getCandle3High() {
		return candle3High;
	}
	public void setCandle3High(Double candle3High) {
		this.candle3High = candle3High;
	}
	public Double getCandle3Low() {
		return candle3Low;
	}
	public void setCandle3Low(Double candle3Low) {
		this.candle3Low = candle3Low;
	}
	public Double getCandle3Close() {
		return candle3Close;
	}
	public void setCandle3Close(Double candle3Close) {
		this.candle3Close = candle3Close;
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
	public String getOhlcState() {
		return ohlcState;
	}
	public void setOhlcState(String ohlcState) {
		this.ohlcState = ohlcState;
	}
	public Integer getCandleNumber() {
		return candleNumber;
	}
	public void setCandleNumber(Integer candleNumber) {
		this.candleNumber = candleNumber;
	}
	public Double getGapUpDownMoveVal() {
		return gapUpDownMoveVal;
	}
	public void setGapUpDownMoveVal(Double gapUpDownMoveVal) {
		this.gapUpDownMoveVal = gapUpDownMoveVal;
	}
	public Date getTradedDateStamp() {
		return tradedDateStamp;
	}
	public void setTradedDateStamp(Date tradedDateStamp) {
		this.tradedDateStamp = tradedDateStamp;
	}
	public String getHeikinAshiTrend() {
		return heikinAshiTrend;
	}
	public void setHeikinAshiTrend(String heikinAshiTrend) {
		this.heikinAshiTrend = heikinAshiTrend;
	}
	public String getOptionChainTrend() {
		return optionChainTrend;
	}
	public void setOptionChainTrend(String optionChainTrend) {
		this.optionChainTrend = optionChainTrend;
	}
	public String getOptionChainPriceTrend() {
		return optionChainPriceTrend;
	}
	public void setOptionChainPriceTrend(String optionChainPriceTrend) {
		this.optionChainPriceTrend = optionChainPriceTrend;
	}
	public Integer getTradeCount() {
		return tradeCount;
	}
	public void setTradeCount(Integer tradeCount) {
		this.tradeCount = tradeCount;
	}
	public Double getProfitLossAmtValFinal() {
		return profitLossAmtValFinal;
	}
	public void setProfitLossAmtValFinal(Double profitLossAmtValFinal) {
		this.profitLossAmtValFinal = profitLossAmtValFinal;
	}
	public String getStrikePrices() {
		return strikePrices;
	}
	public void setStrikePrices(String strikePrices) {
		this.strikePrices = strikePrices;
	}
	public String getLiveTradingMapKey() {
		return liveTradingMapKey;
	}
	public void setLiveTradingMapKey(String liveTradingMapKey) {
		this.liveTradingMapKey = liveTradingMapKey;
	}
}
