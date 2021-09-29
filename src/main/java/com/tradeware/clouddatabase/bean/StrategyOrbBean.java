package com.tradeware.clouddatabase.bean;

import java.util.Date;

public class StrategyOrbBean extends AbstractBean {

	private static final long serialVersionUID = -5000098717955897179L;
	private Integer itemId;
	private String symbolId;
	private String symbolName;
	private Integer lotSize;
	private Double buyAtVal;
	private Double sellAtVal;
	
	private Double tradedAtVal;
	private Double exitedAtVal;
	private Date tradedAtDtTm;
	private Date exitedAtDtTm;
	
	private String tradableStateId;
	private String tradableStateDescription;
	private String tradedStateId;
	private String tradedStateDescription;
	private Double buyerSellerDiffVal; 
	private String tradeType;
	private Double buyerQuantityVal; 
	private Double sellerQuantityVal;
	private Double profitLossAmtVal; 
	private Double positiveMoveVal;
	private Double negativeMoveVal;
	private Double positiveMoveLtp;
	private Double negativeMoveLtp;
	private Long volumes;
	
	private String ohlcStateId;
	private String ohlcStateDescription;
	private String strengthableTradeStateId;
	private String strengthableTradeStateDescription;
	
	private String strengthStyleClass;
	private Integer candleNumber;
	private Boolean dayLevelTradeInd;
	private Double gapUpDownMoveVal;
	private Boolean tempCustomTradingRuleInd;
	private String oiInfo;
	private Date tradedDateStamp;
	private String heikinAshiTrendId;
	private String heikinAshiTrendDescription;
	private String optionChainTrend;
	private String optionChainPriceTrend;
	private Integer optionChainId;
	private Double buySellQuantityRatio;
	private Double percentageChange;
	private String strategyRule;
	private String candleTimeFrame;
	private Integer tradeCountPerDay;
	private Double profitLossAmtValFinal;
	private Boolean reverseMotherCandleInd;
	
	private Double targetPrice;
	private Double stopLoss;
	
	// 04-21-2021 start - afterSomeSuccess [04-27-2021]
	private Boolean manualTradeExitInd;
	private Double manualExitedAtVal;
	private Date manualExitedAtDtTm;
	private Double manualBookProfitLossAmtVal;
	private String manualTradeExitStateId;
	private String manualTradeExitStateDescription;
	
	private Integer waitForEngulfingCount;
	private String trendTradableStateId;
	// 04-21-2021 end
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getSymbolId() {
		return symbolId;
	}
	public void setSymbolId(String symbolId) {
		this.symbolId = symbolId;
	}
	public String getSymbolName() {
		return symbolName;
	}
	public void setSymbolName(String symbolName) {
		this.symbolName = symbolName;
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
	public String getTradableStateId() {
		return tradableStateId;
	}
	public void setTradableStateId(String tradableStateId) {
		this.tradableStateId = tradableStateId;
	}
	public String getTradableStateDescription() {
		return tradableStateDescription;
	}
	public void setTradableStateDescription(String tradableStateDescription) {
		this.tradableStateDescription = tradableStateDescription;
	}
	public String getTradedStateId() {
		return tradedStateId;
	}
	public void setTradedStateId(String tradedStateId) {
		this.tradedStateId = tradedStateId;
	}
	public String getTradedStateDescription() {
		return tradedStateDescription;
	}
	public void setTradedStateDescription(String tradedStateDescription) {
		this.tradedStateDescription = tradedStateDescription;
	}
	public Double getBuyerSellerDiffVal() {
		return buyerSellerDiffVal;
	}
	public void setBuyerSellerDiffVal(Double buyerSellerDiffVal) {
		this.buyerSellerDiffVal = buyerSellerDiffVal;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public Double getBuyerQuantityVal() {
		return buyerQuantityVal;
	}
	public void setBuyerQuantityVal(Double buyerQuantityVal) {
		this.buyerQuantityVal = buyerQuantityVal;
	}
	public Double getSellerQuantityVal() {
		return sellerQuantityVal;
	}
	public void setSellerQuantityVal(Double sellerQuantityVal) {
		this.sellerQuantityVal = sellerQuantityVal;
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
	public Double getPositiveMoveLtp() {
		return positiveMoveLtp;
	}
	public void setPositiveMoveLtp(Double positiveMoveLtp) {
		this.positiveMoveLtp = positiveMoveLtp;
	}
	public Double getNegativeMoveLtp() {
		return negativeMoveLtp;
	}
	public void setNegativeMoveLtp(Double negativeMoveLtp) {
		this.negativeMoveLtp = negativeMoveLtp;
	}
	public Long getVolumes() {
		return volumes;
	}
	public void setVolumes(Long volumes) {
		this.volumes = volumes;
	}
	public String getOhlcStateId() {
		return ohlcStateId;
	}
	public void setOhlcStateId(String ohlcStateId) {
		this.ohlcStateId = ohlcStateId;
	}
	public String getOhlcStateDescription() {
		return ohlcStateDescription;
	}
	public void setOhlcStateDescription(String ohlcStateDescription) {
		this.ohlcStateDescription = ohlcStateDescription;
	}
	public String getStrengthableTradeStateId() {
		return strengthableTradeStateId;
	}
	public void setStrengthableTradeStateId(String strengthableTradeStateId) {
		this.strengthableTradeStateId = strengthableTradeStateId;
	}
	public String getStrengthableTradeStateDescription() {
		return strengthableTradeStateDescription;
	}
	public void setStrengthableTradeStateDescription(String strengthableTradeStateDescription) {
		this.strengthableTradeStateDescription = strengthableTradeStateDescription;
	}
	public String getStrengthStyleClass() {
		return strengthStyleClass;
	}
	public void setStrengthStyleClass(String strengthStyleClass) {
		this.strengthStyleClass = strengthStyleClass;
	}
	public Integer getCandleNumber() {
		return candleNumber;
	}
	public void setCandleNumber(Integer candleNumber) {
		this.candleNumber = candleNumber;
	}
	public Boolean getDayLevelTradeInd() {
		return dayLevelTradeInd;
	}
	public void setDayLevelTradeInd(Boolean dayLevelTradeInd) {
		this.dayLevelTradeInd = dayLevelTradeInd;
	}
	public Double getGapUpDownMoveVal() {
		return gapUpDownMoveVal;
	}
	public void setGapUpDownMoveVal(Double gapUpDownMoveVal) {
		this.gapUpDownMoveVal = gapUpDownMoveVal;
	}
	public Boolean getTempCustomTradingRuleInd() {
		return tempCustomTradingRuleInd;
	}
	public void setTempCustomTradingRuleInd(Boolean tempCustomTradingRuleInd) {
		this.tempCustomTradingRuleInd = tempCustomTradingRuleInd;
	}
	public String getOiInfo() {
		return oiInfo;
	}
	public void setOiInfo(String oiInfo) {
		this.oiInfo = oiInfo;
	}
	public Date getTradedDateStamp() {
		return tradedDateStamp;
	}
	public void setTradedDateStamp(Date tradedDateStamp) {
		this.tradedDateStamp = tradedDateStamp;
	}
	public String getHeikinAshiTrendId() {
		return heikinAshiTrendId;
	}
	public void setHeikinAshiTrendId(String heikinAshiTrendId) {
		this.heikinAshiTrendId = heikinAshiTrendId;
	}
	public String getHeikinAshiTrendDescription() {
		return heikinAshiTrendDescription;
	}
	public void setHeikinAshiTrendDescription(String heikinAshiTrendDescription) {
		this.heikinAshiTrendDescription = heikinAshiTrendDescription;
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
	public Integer getOptionChainId() {
		return optionChainId;
	}
	public void setOptionChainId(Integer optionChainId) {
		this.optionChainId = optionChainId;
	}
	public Double getBuySellQuantityRatio() {
		return buySellQuantityRatio;
	}
	public void setBuySellQuantityRatio(Double buySellQuantityRatio) {
		this.buySellQuantityRatio = buySellQuantityRatio;
	}
	public Double getPercentageChange() {
		return percentageChange;
	}
	public void setPercentageChange(Double percentageChange) {
		this.percentageChange = percentageChange;
	}
	public String getStrategyRule() {
		return strategyRule;
	}
	public void setStrategyRule(String strategyRule) {
		this.strategyRule = strategyRule;
	}
	public String getCandleTimeFrame() {
		return candleTimeFrame;
	}
	public void setCandleTimeFrame(String candleTimeFrame) {
		this.candleTimeFrame = candleTimeFrame;
	}
	public Integer getTradeCountPerDay() {
		return tradeCountPerDay;
	}
	public void setTradeCountPerDay(Integer tradeCountPerDay) {
		this.tradeCountPerDay = tradeCountPerDay;
	}
	public Double getProfitLossAmtValFinal() {
		return profitLossAmtValFinal;
	}
	public void setProfitLossAmtValFinal(Double profitLossAmtValFinal) {
		this.profitLossAmtValFinal = profitLossAmtValFinal;
	}
	public Boolean getReverseMotherCandleInd() {
		return reverseMotherCandleInd;
	}
	public void setReverseMotherCandleInd(Boolean reverseMotherCandleInd) {
		this.reverseMotherCandleInd = reverseMotherCandleInd;
	}
	public Double getTargetPrice() {
		return targetPrice;
	}
	public void setTargetPrice(Double targetPrice) {
		this.targetPrice = targetPrice;
	}
	public Double getStopLoss() {
		return stopLoss;
	}
	public void setStopLoss(Double stopLoss) {
		this.stopLoss = stopLoss;
	}

	// 04-21-2021 start - afterSomeSuccess
	public Boolean getManualTradeExitInd() {
		return manualTradeExitInd;
	}
	public void setManualTradeExitInd(Boolean manualTradeExitInd) {
		this.manualTradeExitInd = manualTradeExitInd;
	}
	public Double getManualExitedAtVal() {
		return manualExitedAtVal;
	}
	public void setManualExitedAtVal(Double manualExitedAtVal) {
		this.manualExitedAtVal = manualExitedAtVal;
	}
	public Date getManualExitedAtDtTm() {
		return manualExitedAtDtTm;
	}
	public void setManualExitedAtDtTm(Date manualExitedAtDtTm) {
		this.manualExitedAtDtTm = manualExitedAtDtTm;
	}
	public Double getManualBookProfitLossAmtVal() {
		return manualBookProfitLossAmtVal;
	}
	public void setManualBookProfitLossAmtVal(Double manualBookProfitLossAmtVal) {
		this.manualBookProfitLossAmtVal = manualBookProfitLossAmtVal;
	}
	public String getManualTradeExitStateId() {
		return manualTradeExitStateId;
	}
	public void setManualTradeExitStateId(String manualTradeExitStateId) {
		this.manualTradeExitStateId = manualTradeExitStateId;
	}
	public String getManualTradeExitStateDescription() {
		return manualTradeExitStateDescription;
	}
	public void setManualTradeExitStateDescription(String manualTradeExitStateDescription) {
		this.manualTradeExitStateDescription = manualTradeExitStateDescription;
	}
	public Integer getWaitForEngulfingCount() {
		return waitForEngulfingCount;
	}
	public void setWaitForEngulfingCount(Integer waitForEngulfingCount) {
		this.waitForEngulfingCount = waitForEngulfingCount;
	}
	public String getTrendTradableStateId() {
		return trendTradableStateId;
	}
	public void setTrendTradableStateId(String trendTradableStateId) {
		this.trendTradableStateId = trendTradableStateId;
	}
	// 04-21-2021 end
	
	//Previous day start
	private Integer prevDayHistId;
	private Double prevDayHrBuyAtVal;
	private Double prevDayHrSellAtVal;
	private Boolean prevDayHrCrossInd;
	private Double prevDayHigh;
	private Double prevDayLow;
	private Boolean prevDayLevelCrossInd;
	private Double prevDayPercentageChange;
	private String candleTypeTrendId;
	private String candleTypeTrendDescription;

	public Integer getPrevDayHistId() {
		return prevDayHistId;
	}
	public void setPrevDayHistId(Integer prevDayHistId) {
		this.prevDayHistId = prevDayHistId;
	}
	public Double getPrevDayHrBuyAtVal() {
		return prevDayHrBuyAtVal;
	}
	public void setPrevDayHrBuyAtVal(Double prevDayHrBuyAtVal) {
		this.prevDayHrBuyAtVal = prevDayHrBuyAtVal;
	}
	public Double getPrevDayHrSellAtVal() {
		return prevDayHrSellAtVal;
	}
	public void setPrevDayHrSellAtVal(Double prevDayHrSellAtVal) {
		this.prevDayHrSellAtVal = prevDayHrSellAtVal;
	}
	public Boolean getPrevDayHrCrossInd() {
		return prevDayHrCrossInd;
	}
	public void setPrevDayHrCrossInd(Boolean prevDayHrCrossInd) {
		this.prevDayHrCrossInd = prevDayHrCrossInd;
	}
	public Double getPrevDayHigh() {
		return prevDayHigh;
	}
	public void setPrevDayHigh(Double prevDayHigh) {
		this.prevDayHigh = prevDayHigh;
	}
	public Double getPrevDayLow() {
		return prevDayLow;
	}
	public void setPrevDayLow(Double prevDayLow) {
		this.prevDayLow = prevDayLow;
	}
	public Boolean getPrevDayLevelCrossInd() {
		return prevDayLevelCrossInd;
	}
	public void setPrevDayLevelCrossInd(Boolean prevDayLevelCrossInd) {
		this.prevDayLevelCrossInd = prevDayLevelCrossInd;
	}
	public Double getPrevDayPercentageChange() {
		return prevDayPercentageChange;
	}
	public void setPrevDayPercentageChange(Double prevDayPercentageChange) {
		this.prevDayPercentageChange = prevDayPercentageChange;
	}
	//Previous day end
	
	public String getCandleTypeTrendId() {
		return candleTypeTrendId;
	}
	public void setCandleTypeTrendId(String candleTypeTrendId) {
		this.candleTypeTrendId = candleTypeTrendId;
	}
	public String getCandleTypeTrendDescription() {
		return candleTypeTrendDescription;
	}
	public void setCandleTypeTrendDescription(String candleTypeTrendDescription) {
		this.candleTypeTrendDescription = candleTypeTrendDescription;
	}




	//Multi level trade start
	private Integer multiLevelTradeId;
	private Integer tradedLotCount;
	private Double tradedAtAvgVal;
	private Double targetAmtVal;
	private Double tradedAtVal2;
	private Date tradedAtDtTm2;
	private Double targetAmtVal2;
	private Double buyerSellerDiffVal2;
	private Double tradedAtVal3;
	private Date tradedAtDtTm3;
	private Double targetAmtVal3;
	private Double buyerSellerDiffVal3;
	public Integer getMultiLevelTradeId() {
		return multiLevelTradeId;
	}
	public void setMultiLevelTradeId(Integer multiLevelTradeId) {
		this.multiLevelTradeId = multiLevelTradeId;
	}
	public Integer getTradedLotCount() {
		return tradedLotCount;
	}
	public void setTradedLotCount(Integer tradedLotCount) {
		this.tradedLotCount = tradedLotCount;
	}
	public Double getTradedAtAvgVal() {
		return tradedAtAvgVal;
	}
	public void setTradedAtAvgVal(Double tradedAtAvgVal) {
		this.tradedAtAvgVal = tradedAtAvgVal;
	}
	public Double getTargetAmtVal() {
		return targetAmtVal;
	}
	public void setTargetAmtVal(Double targetAmtVal) {
		this.targetAmtVal = targetAmtVal;
	}
	public Double getTradedAtVal2() {
		return tradedAtVal2;
	}
	public void setTradedAtVal2(Double tradedAtVal2) {
		this.tradedAtVal2 = tradedAtVal2;
	}
	public Date getTradedAtDtTm2() {
		return tradedAtDtTm2;
	}
	public void setTradedAtDtTm2(Date tradedAtDtTm2) {
		this.tradedAtDtTm2 = tradedAtDtTm2;
	}
	public Double getTargetAmtVal2() {
		return targetAmtVal2;
	}
	public void setTargetAmtVal2(Double targetAmtVal2) {
		this.targetAmtVal2 = targetAmtVal2;
	}
	public Double getBuyerSellerDiffVal2() {
		return buyerSellerDiffVal2;
	}
	public void setBuyerSellerDiffVal2(Double buyerSellerDiffVal2) {
		this.buyerSellerDiffVal2 = buyerSellerDiffVal2;
	}
	public Double getTradedAtVal3() {
		return tradedAtVal3;
	}
	public void setTradedAtVal3(Double tradedAtVal3) {
		this.tradedAtVal3 = tradedAtVal3;
	}
	public Date getTradedAtDtTm3() {
		return tradedAtDtTm3;
	}
	public void setTradedAtDtTm3(Date tradedAtDtTm3) {
		this.tradedAtDtTm3 = tradedAtDtTm3;
	}
	public Double getTargetAmtVal3() {
		return targetAmtVal3;
	}
	public void setTargetAmtVal3(Double targetAmtVal3) {
		this.targetAmtVal3 = targetAmtVal3;
	}
	public Double getBuyerSellerDiffVal3() {
		return buyerSellerDiffVal3;
	}
	public void setBuyerSellerDiffVal3(Double buyerSellerDiffVal3) {
		this.buyerSellerDiffVal3 = buyerSellerDiffVal3;
	}
	//Multi level trade end
	
	
	//Previous candle details start
	private Integer previousCandleDeatilId;
	private Double candle1SizeAmt;
	private Double candle2SizeAmt;
	private Double candle3SizeAmt;
	private Double candle4SizeAmt;
	private Double candleHighsDiff;
	private Double candleLowsDiff;
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
	public Integer getPreviousCandleDeatilId() {
		return previousCandleDeatilId;
	}
	public void setPreviousCandleDeatilId(Integer previousCandleDeatilId) {
		this.previousCandleDeatilId = previousCandleDeatilId;
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
	public Double getCandleHighsDiff() {
		return candleHighsDiff;
	}
	public void setCandleHighsDiff(Double candleHighsDiff) {
		this.candleHighsDiff = candleHighsDiff;
	}
	public Double getCandleLowsDiff() {
		return candleLowsDiff;
	}
	public void setCandleLowsDiff(Double candleLowsDiff) {
		this.candleLowsDiff = candleLowsDiff;
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
	//Previous candle details end
	
	//Previous candle history start
	private Integer previousCandleAvgHistId;
	private Double currentCandleOpen;//In other entity also
	private Double avgHigh1min;
	private Double avgLow1min;
	private Double avgClose1min;
	private Double avgHighMinusClose1min;
	private Double avgCloseMinusLow1min;
	private Double avgHigh5min;
	private Double avgLow5min;
	private Double avgClose5min;
	private Double avgHighMinusClose5min;
	private Double avgCloseMinusLow5min;
	private String min5HeikinAshiTrendId;
	private String min5HeikinAshiTrendIdDescription;
	public Integer getPreviousCandleAvgHistId() {
		return previousCandleAvgHistId;
	}
	public void setPreviousCandleAvgHistId(Integer previousCandleAvgHistId) {
		this.previousCandleAvgHistId = previousCandleAvgHistId;
	}
	public Double getCurrentCandleOpen() {
		return currentCandleOpen;
	}
	public void setCurrentCandleOpen(Double currentCandleOpen) {
		this.currentCandleOpen = currentCandleOpen;
	}
	public Double getAvgHigh1min() {
		return avgHigh1min;
	}
	public void setAvgHigh1min(Double avgHigh1min) {
		this.avgHigh1min = avgHigh1min;
	}
	public Double getAvgLow1min() {
		return avgLow1min;
	}
	public void setAvgLow1min(Double avgLow1min) {
		this.avgLow1min = avgLow1min;
	}
	public Double getAvgClose1min() {
		return avgClose1min;
	}
	public void setAvgClose1min(Double avgClose1min) {
		this.avgClose1min = avgClose1min;
	}
	public Double getAvgHighMinusClose1min() {
		return avgHighMinusClose1min;
	}
	public void setAvgHighMinusClose1min(Double avgHighMinusClose1min) {
		this.avgHighMinusClose1min = avgHighMinusClose1min;
	}
	public Double getAvgCloseMinusLow1min() {
		return avgCloseMinusLow1min;
	}
	public void setAvgCloseMinusLow1min(Double avgCloseMinusLow1min) {
		this.avgCloseMinusLow1min = avgCloseMinusLow1min;
	}
	public Double getAvgHigh5min() {
		return avgHigh5min;
	}
	public void setAvgHigh5min(Double avgHigh5min) {
		this.avgHigh5min = avgHigh5min;
	}
	public Double getAvgLow5min() {
		return avgLow5min;
	}
	public void setAvgLow5min(Double avgLow5min) {
		this.avgLow5min = avgLow5min;
	}
	public Double getAvgClose5min() {
		return avgClose5min;
	}
	public void setAvgClose5min(Double avgClose5min) {
		this.avgClose5min = avgClose5min;
	}
	public Double getAvgHighMinusClose5min() {
		return avgHighMinusClose5min;
	}
	public void setAvgHighMinusClose5min(Double avgHighMinusClose5min) {
		this.avgHighMinusClose5min = avgHighMinusClose5min;
	}
	public Double getAvgCloseMinusLow5min() {
		return avgCloseMinusLow5min;
	}
	public void setAvgCloseMinusLow5min(Double avgCloseMinusLow5min) {
		this.avgCloseMinusLow5min = avgCloseMinusLow5min;
	}
	public String getMin5HeikinAshiTrendId() {
		return min5HeikinAshiTrendId;
	}
	public void setMin5HeikinAshiTrendId(String min5HeikinAshiTrendId) {
		this.min5HeikinAshiTrendId = min5HeikinAshiTrendId;
	}
	public String getMin5HeikinAshiTrendIdDescription() {
		return min5HeikinAshiTrendIdDescription;
	}
	public void setMin5HeikinAshiTrendIdDescription(String min5HeikinAshiTrendIdDescription) {
		this.min5HeikinAshiTrendIdDescription = min5HeikinAshiTrendIdDescription;
	}
	//Previous candle history end
	
	//Previous candle OHLC start
	private Integer previousCandleOhlcId;
	private Double openCandle1;
	private Double highCandle1;
	private Double lowCandle1;
	private Double closeCandle1;
	private Double openCandle2;
	private Double highCandle2;
	private Double lowCandle2;
	private Double closeCandle2;
	private Double openCandle3;
	private Double highCandle3;
	private Double lowCandle3;
	private Double closeCandle3;
	private Double openCandle4;
	private Double highCandle4;
	private Double lowCandle4;
	private Double closeCandle4;
	
	public Integer getPreviousCandleOhlcId() {
		return previousCandleOhlcId;
	}
	public void setPreviousCandleOhlcId(Integer previousCandleOhlcId) {
		this.previousCandleOhlcId = previousCandleOhlcId;
	}
	public Double getOpenCandle1() {
		return openCandle1;
	}
	public void setOpenCandle1(Double openCandle1) {
		this.openCandle1 = openCandle1;
	}
	public Double getHighCandle1() {
		return highCandle1;
	}
	public void setHighCandle1(Double highCandle1) {
		this.highCandle1 = highCandle1;
	}
	public Double getLowCandle1() {
		return lowCandle1;
	}
	public void setLowCandle1(Double lowCandle1) {
		this.lowCandle1 = lowCandle1;
	}
	public Double getCloseCandle1() {
		return closeCandle1;
	}
	public void setCloseCandle1(Double closeCandle1) {
		this.closeCandle1 = closeCandle1;
	}
	public Double getOpenCandle2() {
		return openCandle2;
	}
	public void setOpenCandle2(Double openCandle2) {
		this.openCandle2 = openCandle2;
	}
	public Double getHighCandle2() {
		return highCandle2;
	}
	public void setHighCandle2(Double highCandle2) {
		this.highCandle2 = highCandle2;
	}
	public Double getLowCandle2() {
		return lowCandle2;
	}
	public void setLowCandle2(Double lowCandle2) {
		this.lowCandle2 = lowCandle2;
	}
	public Double getCloseCandle2() {
		return closeCandle2;
	}
	public void setCloseCandle2(Double closeCandle2) {
		this.closeCandle2 = closeCandle2;
	}
	public Double getOpenCandle3() {
		return openCandle3;
	}
	public void setOpenCandle3(Double openCandle3) {
		this.openCandle3 = openCandle3;
	}
	public Double getHighCandle3() {
		return highCandle3;
	}
	public void setHighCandle3(Double highCandle3) {
		this.highCandle3 = highCandle3;
	}
	public Double getLowCandle3() {
		return lowCandle3;
	}
	public void setLowCandle3(Double lowCandle3) {
		this.lowCandle3 = lowCandle3;
	}
	public Double getCloseCandle3() {
		return closeCandle3;
	}
	public void setCloseCandle3(Double closeCandle3) {
		this.closeCandle3 = closeCandle3;
	}
	public Double getOpenCandle4() {
		return openCandle4;
	}
	public void setOpenCandle4(Double openCandle4) {
		this.openCandle4 = openCandle4;
	}
	public Double getHighCandle4() {
		return highCandle4;
	}
	public void setHighCandle4(Double highCandle4) {
		this.highCandle4 = highCandle4;
	}
	public Double getLowCandle4() {
		return lowCandle4;
	}
	public void setLowCandle4(Double lowCandle4) {
		this.lowCandle4 = lowCandle4;
	}
	public Double getCloseCandle4() {
		return closeCandle4;
	}
	public void setCloseCandle4(Double closeCandle4) {
		this.closeCandle4 = closeCandle4;
	}
	//Previous candle OHLC END
	
	//VWAP and VOLUME Start
	private Integer vwapVolumeDetailId;
	private Double vwapValue;//In other entity also
	private String volumeTradeStateId;////In other entity also//@oneToOne
	private String volumeTradeStateDescription; // refer with LKP
	private String vwapTradeStateId;
	private String vwapTradeStateDescription;
	private String volStrengthStyleClass;
	
	private String vwapTradeVolInfo;    //, separated volume string not required in entity
	private String vwapTradeVolRatioInfo;//, separated volume ration string not required in entity
	private String strengthableTradeInfo;
	
	private Long volume4;
	private Long volume3;
	private Long volume2;
	private Long volume1;
	private Long smallVolume;
	private Double vol4Ratio;
	private Double vol3Ratio;
	private Double vol2Ratio;
	private Double vol1Ratio;
	//VWAP and VOLUME END

	public Integer getVwapVolumeDetailId() {
		return vwapVolumeDetailId;
	}
	public void setVwapVolumeDetailId(Integer vwapVolumeDetailId) {
		this.vwapVolumeDetailId = vwapVolumeDetailId;
	}
	public Double getVwapValue() {
		return vwapValue;
	}
	public void setVwapValue(Double vwapValue) {
		this.vwapValue = vwapValue;
	}
	public String getVolumeTradeStateId() {
		return volumeTradeStateId;
	}
	public void setVolumeTradeStateId(String volumeTradeStateId) {
		this.volumeTradeStateId = volumeTradeStateId;
	}
	public String getVolumeTradeStateDescription() {
		return volumeTradeStateDescription;
	}
	public void setVolumeTradeStateDescription(String volumeTradeStateDescription) {
		this.volumeTradeStateDescription = volumeTradeStateDescription;
	}
	public String getVwapTradeStateId() {
		return vwapTradeStateId;
	}
	public void setVwapTradeStateId(String vwapTradeStateId) {
		this.vwapTradeStateId = vwapTradeStateId;
	}
	public String getVwapTradeStateDescription() {
		return vwapTradeStateDescription;
	}
	public void setVwapTradeStateDescription(String vwapTradeStateDescription) {
		this.vwapTradeStateDescription = vwapTradeStateDescription;
	}
	public String getVolStrengthStyleClass() {
		return volStrengthStyleClass;
	}
	public void setVolStrengthStyleClass(String volStrengthStyleClass) {
		this.volStrengthStyleClass = volStrengthStyleClass;
	}
	public String getVwapTradeVolInfo() {
		return vwapTradeVolInfo;
	}
	public void setVwapTradeVolInfo(String vwapTradeVolInfo) {
		this.vwapTradeVolInfo = vwapTradeVolInfo;
	}
	public String getVwapTradeVolRatioInfo() {
		return vwapTradeVolRatioInfo;
	}
	public void setVwapTradeVolRatioInfo(String vwapTradeVolRatioInfo) {
		this.vwapTradeVolRatioInfo = vwapTradeVolRatioInfo;
	}
	public String getStrengthableTradeInfo() {
		return strengthableTradeInfo;
	}
	public void setStrengthableTradeInfo(String strengthableTradeInfo) {
		this.strengthableTradeInfo = strengthableTradeInfo;
	}
	public Long getVolume4() {
		return volume4;
	}
	public void setVolume4(Long volume4) {
		this.volume4 = volume4;
	}
	public Long getVolume3() {
		return volume3;
	}
	public void setVolume3(Long volume3) {
		this.volume3 = volume3;
	}
	public Long getVolume2() {
		return volume2;
	}
	public void setVolume2(Long volume2) {
		this.volume2 = volume2;
	}
	public Long getVolume1() {
		return volume1;
	}
	public void setVolume1(Long volume1) {
		this.volume1 = volume1;
	}
	public Long getSmallVolume() {
		return smallVolume;
	}
	public void setSmallVolume(Long smallVolume) {
		this.smallVolume = smallVolume;
	}
	public Double getVol4Ratio() {
		return vol4Ratio;
	}
	public void setVol4Ratio(Double vol4Ratio) {
		this.vol4Ratio = vol4Ratio;
	}
	public Double getVol3Ratio() {
		return vol3Ratio;
	}
	public void setVol3Ratio(Double vol3Ratio) {
		this.vol3Ratio = vol3Ratio;
	}
	public Double getVol2Ratio() {
		return vol2Ratio;
	}
	public void setVol2Ratio(Double vol2Ratio) {
		this.vol2Ratio = vol2Ratio;
	}
	public Double getVol1Ratio() {
		return vol1Ratio;
	}
	public void setVol1Ratio(Double vol1Ratio) {
		this.vol1Ratio = vol1Ratio;
	}
	
	//StrategyOrbTradingRuleEntity start
	private Boolean tradePlacedRuleInd;
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd;
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd;
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd;
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd;
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd;
	private Boolean tradingRuleOHLCAnd3TimesStrengthInd;
	private Boolean tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd;
	private Boolean tradingRuleVwapStrongInd;
	private Boolean tradingRuleVolumeStrengthInd;
	private Boolean tradingRule7TimesStrengthInd;
	private Boolean tradingRuleOpenBtwnAvgHiLoClsAndVwapInd;
	private Boolean tradingRuleCandleTypeTrendAndPrevHrCrossInd;
	private Boolean tradingRuleVolumeTradeStateInd;
	private Boolean tradingRuleProfitableCustomeStrongVolumeVwapInd;
	private Boolean tradingRuleForwardEngulfingInd;
	private Boolean tradingRuleForwardEngulfingLvl2Ind;
	private Boolean tradingRuleForwardEngulfingLvl3Ind;
	private Boolean tradingRuleReverseEngulfingInd;
	private String tradePlaceRule;
	
	public Boolean getTradePlacedRuleInd() {
		return tradePlacedRuleInd;
	}
	public void setTradePlacedRuleInd(Boolean tradePlacedRuleInd) {
		this.tradePlacedRuleInd = tradePlacedRuleInd;
	}
	public Boolean getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd() {
		return tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd;
	}
	public void setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd(Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd) {
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd = tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd;
	}
	public Boolean getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd() {
		return tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd;
	}
	public void setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd(Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd) {
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd = tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd;
	}
	public Boolean getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd() {
		return tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd;
	}
	public void setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd(
			Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd) {
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd = tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd;
	}
	public Boolean getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd() {
		return tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd;
	}
	public void setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd(
			Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd) {
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd = tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd;
	}
	public Boolean getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd() {
		return tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd;
	}
	public void setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd(
			Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd) {
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd = tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd;
	}
	public Boolean getTradingRuleOHLCAnd3TimesStrengthInd() {
		return tradingRuleOHLCAnd3TimesStrengthInd;
	}
	public void setTradingRuleOHLCAnd3TimesStrengthInd(Boolean tradingRuleOHLCAnd3TimesStrengthInd) {
		this.tradingRuleOHLCAnd3TimesStrengthInd = tradingRuleOHLCAnd3TimesStrengthInd;
	}
	public Boolean getTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd() {
		return tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd;
	}
	public void setTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd(
			Boolean tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd) {
		this.tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd = tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd;
	}
	public Boolean getTradingRuleVwapStrongInd() {
		return tradingRuleVwapStrongInd;
	}
	public void setTradingRuleVwapStrongInd(Boolean tradingRuleVwapStrongInd) {
		this.tradingRuleVwapStrongInd = tradingRuleVwapStrongInd;
	}
	public Boolean getTradingRuleVolumeStrengthInd() {
		return tradingRuleVolumeStrengthInd;
	}
	public void setTradingRuleVolumeStrengthInd(Boolean tradingRuleVolumeStrengthInd) {
		this.tradingRuleVolumeStrengthInd = tradingRuleVolumeStrengthInd;
	}
	public Boolean getTradingRule7TimesStrengthInd() {
		return tradingRule7TimesStrengthInd;
	}
	public void setTradingRule7TimesStrengthInd(Boolean tradingRule7TimesStrengthInd) {
		this.tradingRule7TimesStrengthInd = tradingRule7TimesStrengthInd;
	}
	public Boolean getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd() {
		return tradingRuleOpenBtwnAvgHiLoClsAndVwapInd;
	}
	public void setTradingRuleOpenBtwnAvgHiLoClsAndVwapInd(Boolean tradingRuleOpenBtwnAvgHiLoClsAndVwapInd) {
		this.tradingRuleOpenBtwnAvgHiLoClsAndVwapInd = tradingRuleOpenBtwnAvgHiLoClsAndVwapInd;
	}
	public Boolean getTradingRuleCandleTypeTrendAndPrevHrCrossInd() {
		return tradingRuleCandleTypeTrendAndPrevHrCrossInd;
	}
	public void setTradingRuleCandleTypeTrendAndPrevHrCrossInd(Boolean tradingRuleCandleTypeTrendAndPrevHrCrossInd) {
		this.tradingRuleCandleTypeTrendAndPrevHrCrossInd = tradingRuleCandleTypeTrendAndPrevHrCrossInd;
	}public String getTradePlaceRule() {
		return tradePlaceRule;
	}
	public void setTradePlaceRule(String tradePlaceRule) {
		this.tradePlaceRule = tradePlaceRule;
	}
	public Boolean getTradingRuleVolumeTradeStateInd() {
		return tradingRuleVolumeTradeStateInd;
	}
	public void setTradingRuleVolumeTradeStateInd(Boolean tradingRuleVolumeTradeStateInd) {
		this.tradingRuleVolumeTradeStateInd = tradingRuleVolumeTradeStateInd;
	}
	public Boolean getTradingRuleProfitableCustomeStrongVolumeVwapInd() {
		return tradingRuleProfitableCustomeStrongVolumeVwapInd;
	}
	public void setTradingRuleProfitableCustomeStrongVolumeVwapInd(
			Boolean tradingRuleProfitableCustomeStrongVolumeVwapInd) {
		this.tradingRuleProfitableCustomeStrongVolumeVwapInd = tradingRuleProfitableCustomeStrongVolumeVwapInd;
	}
	public Boolean getTradingRuleForwardEngulfingInd() {
		return tradingRuleForwardEngulfingInd;
	}
	public void setTradingRuleForwardEngulfingInd(Boolean tradingRuleForwardEngulfingInd) {
		this.tradingRuleForwardEngulfingInd = tradingRuleForwardEngulfingInd;
	}
	public Boolean getTradingRuleForwardEngulfingLvl2Ind() {
		return tradingRuleForwardEngulfingLvl2Ind;
	}
	public void setTradingRuleForwardEngulfingLvl2Ind(Boolean tradingRuleForwardEngulfingLvl2Ind) {
		this.tradingRuleForwardEngulfingLvl2Ind = tradingRuleForwardEngulfingLvl2Ind;
	}
	public Boolean getTradingRuleForwardEngulfingLvl3Ind() {
		return tradingRuleForwardEngulfingLvl3Ind;
	}
	public void setTradingRuleForwardEngulfingLvl3Ind(Boolean tradingRuleForwardEngulfingLvl3Ind) {
		this.tradingRuleForwardEngulfingLvl3Ind = tradingRuleForwardEngulfingLvl3Ind;
	}
	public Boolean getTradingRuleReverseEngulfingInd() {
		return tradingRuleReverseEngulfingInd;
	}
	public void setTradingRuleReverseEngulfingInd(Boolean tradingRuleReverseEngulfingInd) {
		this.tradingRuleReverseEngulfingInd = tradingRuleReverseEngulfingInd;
	}
	//StrategyOrbTradingRuleEntity end

	// StrategyOrbTechnicalIndicatorEntity start
	// CPR values
	private Double cprLowerBound;
	private Double cprPivotalPoint;
	private Double cprUpperBound;
	private Double valueCPR;

	public Double getCprLowerBound() {
		return cprLowerBound;
	}
	public void setCprLowerBound(Double cprLowerBound) {
		this.cprLowerBound = cprLowerBound;
	}
	public Double getCprPivotalPoint() {
		return cprPivotalPoint;
	}
	public void setCprPivotalPoint(Double cprPivotalPoint) {
		this.cprPivotalPoint = cprPivotalPoint;
	}
	public Double getCprUpperBound() {
		return cprUpperBound;
	}
	public void setCprUpperBound(Double cprUpperBound) {
		this.cprUpperBound = cprUpperBound;
	}
	public Double getValueCPR() {
		return valueCPR;
	}
	public void setValueCPR(Double valueCPR) {
		this.valueCPR = valueCPR;
	}
	// StrategyOrbTechnicalIndicatorEntity end
	
	
	// StrategyOrbStochasticDataBean

	private Double min60StochasticValK1;
	private Double min60StochasticValD3;
	private Double min60StochasticValK2;
	private Double min60StochasticValD4;
	private Double min60StochasticValK3;
	private Double min60StochasticValD5;
	private String min60StochasticTrend;

	private Double min15StochasticValK1;
	private Double min15StochasticValD3;
	private Double min15StochasticValK2;
	private Double min15StochasticValD4;
	private Double min15StochasticValK3;
	private Double min15StochasticValD5;
	private String min15StochasticTrend;

	private Double min5StochasticValK1;
	private Double min5StochasticValD3;
	private Double min5StochasticValK2;
	private Double min5StochasticValD4;
	private Double min5StochasticValK3;
	private Double min5StochasticValD5;
	private String min5StochasticTrend;
	private Boolean stochasticBasicRule1Ind;
	
	public Double getMin60StochasticValK1() {
		return min60StochasticValK1;
	}
	public void setMin60StochasticValK1(Double min60StochasticValK1) {
		this.min60StochasticValK1 = min60StochasticValK1;
	}
	public Double getMin60StochasticValD3() {
		return min60StochasticValD3;
	}
	public void setMin60StochasticValD3(Double min60StochasticValD3) {
		this.min60StochasticValD3 = min60StochasticValD3;
	}
	public Double getMin60StochasticValK2() {
		return min60StochasticValK2;
	}
	public void setMin60StochasticValK2(Double min60StochasticValK2) {
		this.min60StochasticValK2 = min60StochasticValK2;
	}
	public Double getMin60StochasticValD4() {
		return min60StochasticValD4;
	}
	public void setMin60StochasticValD4(Double min60StochasticValD4) {
		this.min60StochasticValD4 = min60StochasticValD4;
	}
	public Double getMin60StochasticValK3() {
		return min60StochasticValK3;
	}
	public void setMin60StochasticValK3(Double min60StochasticValK3) {
		this.min60StochasticValK3 = min60StochasticValK3;
	}
	public Double getMin60StochasticValD5() {
		return min60StochasticValD5;
	}
	public void setMin60StochasticValD5(Double min60StochasticValD5) {
		this.min60StochasticValD5 = min60StochasticValD5;
	}
	public String getMin60StochasticTrend() {
		return min60StochasticTrend;
	}
	public void setMin60StochasticTrend(String min60StochasticTrend) {
		this.min60StochasticTrend = min60StochasticTrend;
	}
	public Double getMin15StochasticValK1() {
		return min15StochasticValK1;
	}
	public void setMin15StochasticValK1(Double min15StochasticValK1) {
		this.min15StochasticValK1 = min15StochasticValK1;
	}
	public Double getMin15StochasticValD3() {
		return min15StochasticValD3;
	}
	public void setMin15StochasticValD3(Double min15StochasticValD3) {
		this.min15StochasticValD3 = min15StochasticValD3;
	}
	public Double getMin15StochasticValK2() {
		return min15StochasticValK2;
	}
	public void setMin15StochasticValK2(Double min15StochasticValK2) {
		this.min15StochasticValK2 = min15StochasticValK2;
	}
	public Double getMin15StochasticValD4() {
		return min15StochasticValD4;
	}
	public void setMin15StochasticValD4(Double min15StochasticValD4) {
		this.min15StochasticValD4 = min15StochasticValD4;
	}
	public Double getMin15StochasticValK3() {
		return min15StochasticValK3;
	}
	public void setMin15StochasticValK3(Double min15StochasticValK3) {
		this.min15StochasticValK3 = min15StochasticValK3;
	}
	public Double getMin15StochasticValD5() {
		return min15StochasticValD5;
	}
	public void setMin15StochasticValD5(Double min15StochasticValD5) {
		this.min15StochasticValD5 = min15StochasticValD5;
	}
	public String getMin15StochasticTrend() {
		return min15StochasticTrend;
	}
	public void setMin15StochasticTrend(String min15StochasticTrend) {
		this.min15StochasticTrend = min15StochasticTrend;
	}
	public Double getMin5StochasticValK1() {
		return min5StochasticValK1;
	}
	public void setMin5StochasticValK1(Double min5StochasticValK1) {
		this.min5StochasticValK1 = min5StochasticValK1;
	}
	public Double getMin5StochasticValD3() {
		return min5StochasticValD3;
	}
	public void setMin5StochasticValD3(Double min5StochasticValD3) {
		this.min5StochasticValD3 = min5StochasticValD3;
	}
	public Double getMin5StochasticValK2() {
		return min5StochasticValK2;
	}
	public void setMin5StochasticValK2(Double min5StochasticValK2) {
		this.min5StochasticValK2 = min5StochasticValK2;
	}
	public Double getMin5StochasticValD4() {
		return min5StochasticValD4;
	}
	public void setMin5StochasticValD4(Double min5StochasticValD4) {
		this.min5StochasticValD4 = min5StochasticValD4;
	}
	public Double getMin5StochasticValK3() {
		return min5StochasticValK3;
	}
	public void setMin5StochasticValK3(Double min5StochasticValK3) {
		this.min5StochasticValK3 = min5StochasticValK3;
	}
	public Double getMin5StochasticValD5() {
		return min5StochasticValD5;
	}
	public void setMin5StochasticValD5(Double min5StochasticValD5) {
		this.min5StochasticValD5 = min5StochasticValD5;
	}
	public String getMin5StochasticTrend() {
		return min5StochasticTrend;
	}
	public void setMin5StochasticTrend(String min5StochasticTrend) {
		this.min5StochasticTrend = min5StochasticTrend;
	}
	public Boolean getStochasticBasicRule1Ind() {
		return stochasticBasicRule1Ind;
	}
	public void setStochasticBasicRule1Ind(Boolean stochasticBasicRule1Ind) {
		this.stochasticBasicRule1Ind = stochasticBasicRule1Ind;
	}
	//StrategyOrbStochasticDataBean 
	
	
	private String heikinAshiTrendIdMin5;
	private String heikinAshiTrendIdMin15;
	private String heikinAshiTrendIdMin60;
	
	private Double heikenAshiOpen3Min5;  
	private Double heikenAshiClose3Min5; 
	private Double heikenAshiHigh3Min5;  
	private Double heikenAshiLow3Min5;  
	private Double heikenAshiOpen2Min5;  
	private Double heikenAshiClose2Min5; 
	private Double heikenAshiHigh2Min5;  
	private Double heikenAshiLow2Min5;  
	private Double heikenAshiOpen1Min5;  
	private Double heikenAshiClose1Min5; 
	private Double heikenAshiHigh1Min5;  
	private Double heikenAshiLow1Min5; 
	private Double heikenAshiOpen3Min15;  
	private Double heikenAshiClose3Min15; 
	private Double heikenAshiHigh3Min15;  
	private Double heikenAshiLow3Min15;  
	private Double heikenAshiOpen2Min15;  
	private Double heikenAshiClose2Min15; 
	private Double heikenAshiHigh2Min15;  
	private Double heikenAshiLow2Min15;  
	private Double heikenAshiOpen1Min15;  
	private Double heikenAshiClose1Min15; 
	private Double heikenAshiHigh1Min15;  
	private Double heikenAshiLow1Min15;
	private Double heikenAshiOpen3Min60;  
	private Double heikenAshiClose3Min60; 
	private Double heikenAshiHigh3Min60;  
	private Double heikenAshiLow3Min60;  
	private Double heikenAshiOpen2Min60;  
	private Double heikenAshiClose2Min60; 
	private Double heikenAshiHigh2Min60;  
	private Double heikenAshiLow2Min60;  
	private Double heikenAshiOpen1Min60;  
	private Double heikenAshiClose1Min60; 
	private Double heikenAshiHigh1Min60;  
	private Double heikenAshiLow1Min60;
	public String getHeikinAshiTrendIdMin5() {
		return heikinAshiTrendIdMin5;
	}
	public void setHeikinAshiTrendIdMin5(String heikinAshiTrendIdMin5) {
		this.heikinAshiTrendIdMin5 = heikinAshiTrendIdMin5;
	}
	public String getHeikinAshiTrendIdMin15() {
		return heikinAshiTrendIdMin15;
	}
	public void setHeikinAshiTrendIdMin15(String heikinAshiTrendIdMin15) {
		this.heikinAshiTrendIdMin15 = heikinAshiTrendIdMin15;
	}
	public String getHeikinAshiTrendIdMin60() {
		return heikinAshiTrendIdMin60;
	}
	public void setHeikinAshiTrendIdMin60(String heikinAshiTrendIdMin60) {
		this.heikinAshiTrendIdMin60 = heikinAshiTrendIdMin60;
	}
	public Double getHeikenAshiOpen3Min5() {
		return heikenAshiOpen3Min5;
	}
	public void setHeikenAshiOpen3Min5(Double heikenAshiOpen3Min5) {
		this.heikenAshiOpen3Min5 = heikenAshiOpen3Min5;
	}
	public Double getHeikenAshiClose3Min5() {
		return heikenAshiClose3Min5;
	}
	public void setHeikenAshiClose3Min5(Double heikenAshiClose3Min5) {
		this.heikenAshiClose3Min5 = heikenAshiClose3Min5;
	}
	public Double getHeikenAshiHigh3Min5() {
		return heikenAshiHigh3Min5;
	}
	public void setHeikenAshiHigh3Min5(Double heikenAshiHigh3Min5) {
		this.heikenAshiHigh3Min5 = heikenAshiHigh3Min5;
	}
	public Double getHeikenAshiLow3Min5() {
		return heikenAshiLow3Min5;
	}
	public void setHeikenAshiLow3Min5(Double heikenAshiLow3Min5) {
		this.heikenAshiLow3Min5 = heikenAshiLow3Min5;
	}
	public Double getHeikenAshiOpen2Min5() {
		return heikenAshiOpen2Min5;
	}
	public void setHeikenAshiOpen2Min5(Double heikenAshiOpen2Min5) {
		this.heikenAshiOpen2Min5 = heikenAshiOpen2Min5;
	}
	public Double getHeikenAshiClose2Min5() {
		return heikenAshiClose2Min5;
	}
	public void setHeikenAshiClose2Min5(Double heikenAshiClose2Min5) {
		this.heikenAshiClose2Min5 = heikenAshiClose2Min5;
	}
	public Double getHeikenAshiHigh2Min5() {
		return heikenAshiHigh2Min5;
	}
	public void setHeikenAshiHigh2Min5(Double heikenAshiHigh2Min5) {
		this.heikenAshiHigh2Min5 = heikenAshiHigh2Min5;
	}
	public Double getHeikenAshiLow2Min5() {
		return heikenAshiLow2Min5;
	}
	public void setHeikenAshiLow2Min5(Double heikenAshiLow2Min5) {
		this.heikenAshiLow2Min5 = heikenAshiLow2Min5;
	}
	public Double getHeikenAshiOpen1Min5() {
		return heikenAshiOpen1Min5;
	}
	public void setHeikenAshiOpen1Min5(Double heikenAshiOpen1Min5) {
		this.heikenAshiOpen1Min5 = heikenAshiOpen1Min5;
	}
	public Double getHeikenAshiClose1Min5() {
		return heikenAshiClose1Min5;
	}
	public void setHeikenAshiClose1Min5(Double heikenAshiClose1Min5) {
		this.heikenAshiClose1Min5 = heikenAshiClose1Min5;
	}
	public Double getHeikenAshiHigh1Min5() {
		return heikenAshiHigh1Min5;
	}
	public void setHeikenAshiHigh1Min5(Double heikenAshiHigh1Min5) {
		this.heikenAshiHigh1Min5 = heikenAshiHigh1Min5;
	}
	public Double getHeikenAshiLow1Min5() {
		return heikenAshiLow1Min5;
	}
	public void setHeikenAshiLow1Min5(Double heikenAshiLow1Min5) {
		this.heikenAshiLow1Min5 = heikenAshiLow1Min5;
	}
	public Double getHeikenAshiOpen3Min15() {
		return heikenAshiOpen3Min15;
	}
	public void setHeikenAshiOpen3Min15(Double heikenAshiOpen3Min15) {
		this.heikenAshiOpen3Min15 = heikenAshiOpen3Min15;
	}
	public Double getHeikenAshiClose3Min15() {
		return heikenAshiClose3Min15;
	}
	public void setHeikenAshiClose3Min15(Double heikenAshiClose3Min15) {
		this.heikenAshiClose3Min15 = heikenAshiClose3Min15;
	}
	public Double getHeikenAshiHigh3Min15() {
		return heikenAshiHigh3Min15;
	}
	public void setHeikenAshiHigh3Min15(Double heikenAshiHigh3Min15) {
		this.heikenAshiHigh3Min15 = heikenAshiHigh3Min15;
	}
	public Double getHeikenAshiLow3Min15() {
		return heikenAshiLow3Min15;
	}
	public void setHeikenAshiLow3Min15(Double heikenAshiLow3Min15) {
		this.heikenAshiLow3Min15 = heikenAshiLow3Min15;
	}
	public Double getHeikenAshiOpen2Min15() {
		return heikenAshiOpen2Min15;
	}
	public void setHeikenAshiOpen2Min15(Double heikenAshiOpen2Min15) {
		this.heikenAshiOpen2Min15 = heikenAshiOpen2Min15;
	}
	public Double getHeikenAshiClose2Min15() {
		return heikenAshiClose2Min15;
	}
	public void setHeikenAshiClose2Min15(Double heikenAshiClose2Min15) {
		this.heikenAshiClose2Min15 = heikenAshiClose2Min15;
	}
	public Double getHeikenAshiHigh2Min15() {
		return heikenAshiHigh2Min15;
	}
	public void setHeikenAshiHigh2Min15(Double heikenAshiHigh2Min15) {
		this.heikenAshiHigh2Min15 = heikenAshiHigh2Min15;
	}
	public Double getHeikenAshiLow2Min15() {
		return heikenAshiLow2Min15;
	}
	public void setHeikenAshiLow2Min15(Double heikenAshiLow2Min15) {
		this.heikenAshiLow2Min15 = heikenAshiLow2Min15;
	}
	public Double getHeikenAshiOpen1Min15() {
		return heikenAshiOpen1Min15;
	}
	public void setHeikenAshiOpen1Min15(Double heikenAshiOpen1Min15) {
		this.heikenAshiOpen1Min15 = heikenAshiOpen1Min15;
	}
	public Double getHeikenAshiClose1Min15() {
		return heikenAshiClose1Min15;
	}
	public void setHeikenAshiClose1Min15(Double heikenAshiClose1Min15) {
		this.heikenAshiClose1Min15 = heikenAshiClose1Min15;
	}
	public Double getHeikenAshiHigh1Min15() {
		return heikenAshiHigh1Min15;
	}
	public void setHeikenAshiHigh1Min15(Double heikenAshiHigh1Min15) {
		this.heikenAshiHigh1Min15 = heikenAshiHigh1Min15;
	}
	public Double getHeikenAshiLow1Min15() {
		return heikenAshiLow1Min15;
	}
	public void setHeikenAshiLow1Min15(Double heikenAshiLow1Min15) {
		this.heikenAshiLow1Min15 = heikenAshiLow1Min15;
	}
	public Double getHeikenAshiOpen3Min60() {
		return heikenAshiOpen3Min60;
	}
	public void setHeikenAshiOpen3Min60(Double heikenAshiOpen3Min60) {
		this.heikenAshiOpen3Min60 = heikenAshiOpen3Min60;
	}
	public Double getHeikenAshiClose3Min60() {
		return heikenAshiClose3Min60;
	}
	public void setHeikenAshiClose3Min60(Double heikenAshiClose3Min60) {
		this.heikenAshiClose3Min60 = heikenAshiClose3Min60;
	}
	public Double getHeikenAshiHigh3Min60() {
		return heikenAshiHigh3Min60;
	}
	public void setHeikenAshiHigh3Min60(Double heikenAshiHigh3Min60) {
		this.heikenAshiHigh3Min60 = heikenAshiHigh3Min60;
	}
	public Double getHeikenAshiLow3Min60() {
		return heikenAshiLow3Min60;
	}
	public void setHeikenAshiLow3Min60(Double heikenAshiLow3Min60) {
		this.heikenAshiLow3Min60 = heikenAshiLow3Min60;
	}
	public Double getHeikenAshiOpen2Min60() {
		return heikenAshiOpen2Min60;
	}
	public void setHeikenAshiOpen2Min60(Double heikenAshiOpen2Min60) {
		this.heikenAshiOpen2Min60 = heikenAshiOpen2Min60;
	}
	public Double getHeikenAshiClose2Min60() {
		return heikenAshiClose2Min60;
	}
	public void setHeikenAshiClose2Min60(Double heikenAshiClose2Min60) {
		this.heikenAshiClose2Min60 = heikenAshiClose2Min60;
	}
	public Double getHeikenAshiHigh2Min60() {
		return heikenAshiHigh2Min60;
	}
	public void setHeikenAshiHigh2Min60(Double heikenAshiHigh2Min60) {
		this.heikenAshiHigh2Min60 = heikenAshiHigh2Min60;
	}
	public Double getHeikenAshiLow2Min60() {
		return heikenAshiLow2Min60;
	}
	public void setHeikenAshiLow2Min60(Double heikenAshiLow2Min60) {
		this.heikenAshiLow2Min60 = heikenAshiLow2Min60;
	}
	public Double getHeikenAshiOpen1Min60() {
		return heikenAshiOpen1Min60;
	}
	public void setHeikenAshiOpen1Min60(Double heikenAshiOpen1Min60) {
		this.heikenAshiOpen1Min60 = heikenAshiOpen1Min60;
	}
	public Double getHeikenAshiClose1Min60() {
		return heikenAshiClose1Min60;
	}
	public void setHeikenAshiClose1Min60(Double heikenAshiClose1Min60) {
		this.heikenAshiClose1Min60 = heikenAshiClose1Min60;
	}
	public Double getHeikenAshiHigh1Min60() {
		return heikenAshiHigh1Min60;
	}
	public void setHeikenAshiHigh1Min60(Double heikenAshiHigh1Min60) {
		this.heikenAshiHigh1Min60 = heikenAshiHigh1Min60;
	}
	public Double getHeikenAshiLow1Min60() {
		return heikenAshiLow1Min60;
	}
	public void setHeikenAshiLow1Min60(Double heikenAshiLow1Min60) {
		this.heikenAshiLow1Min60 = heikenAshiLow1Min60;
	}
}
