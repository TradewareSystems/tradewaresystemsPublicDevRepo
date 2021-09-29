package com.tradeware.stockmarket.bean;

import java.util.Date;

public abstract class AbstractTradeDataBean extends AbstractTradeAdditionalDataBean {

	private static final long serialVersionUID = -5698349351843527622L;
	private String symbolId;
	private String symbolName;
	private Integer lotSize;
	private Double ltp;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private String kiteFutureKey;
	private Long instrumentToken;
	private String kiteOrderId;
	private String kiteOrderType;//CO/BO/Regular
	
	private Double stopLoss;
	private Double baseStopLoss;
	private Double targetPrice;
	private Double trailingStopLoss;
	private Double tradedAtVal;
	private Double exitedAtVal;
	private Double buyAtVal;
	private Double sellAtVal;
	private Double buyerAt;
	private Double sellerAt;
	private Date tradedAtDtTm;
	private Date exitedAtDtTm;
	private Long volumes;
	//private Double buyQuantity;
	//private Double sellQuantity;
	private Double buySellQuantityRatio;
	private Double percentageChange;
	private Double topHigh;
	private Double topLow;
	
	private Double profitLossAmtVal;
	private Double buyerSellerDiffVal;
	private Double negativeDirectionLtp;
	private Double negativeDirectionLoss;
	private Double positiveDirectionLtp;
	private Double positiveDirectionProfit;
	private Double positiveDirectionProfitPrevious;
	private Double profitChaseVal;
	private Date tradedDateStamp;
	
	private String tradableStateId;
	private String tradedStateId;
	private String tradableStateDescription;
	private String tradedStateDescription;
	private String ohlcStateId;
	private String ohlcStateDescription;
	private Integer tradedLotCount;
	
	private String signalTriggeredInAt;
	private String signalTriggeredOutAt;
	
	// 04-21-2021 start - afterSomeSuccess [04-27-2021]
	private Boolean manualTradeExitInd;
	private Double manualExitedAtVal;
	private Date manualExitedAtDtTm;
	private Double manualBookProfitLossAmtVal;
	private String manualTradeExitStateId;
	private String manualTradeExitStateDescription;
	// 04-21-2021 end
	
	//private String candleTypeTrendId;
	//private String candleTypeTrendDescription;
	private Double prevDayHrBuyAtVal;
	private Double prevDayHrSellAtVal;
	private Boolean prevDayHrCrossInd;
	private Double prevDayHigh;
	private Double prevDayLow;
	private Double prevDayPercentageChange;
	
	//Additional Start
	private Double buyerA2t;
	private Double seller2At;
	private Double buyer3At;
	private Double seller3At;
	private Double buyer4At;
	private Double seller4At;
	private Double buyer5At;
	private Double seller5At;
	private Double buyerSellerDiffVal2;
	private Double buyerSellerDiffVal3;
	private Double buyerSellerDiffVal4;
	private Double buyerSellerDiffVal5;
	
	private Double buyerQuantityVal; 
	private Double sellerQuantityVal;
	
	private Double tradedAtVal2;
	private Date tradedAtDtTm2;
	private Double tradedAtVal3;
	private Date tradedAtDtTm3;
	private Double tradedAtVal4;
	private Date tradedAtDtTm4;
	private Double tradedAtVal5;
	private Date tradedAtDtTm5;
	
	private Double targetAmtVal;
	private Double targetAmtVal2;
	private Double targetAmtVal3;
	private Double tradedAtAvgVal;
	
	private Double vwapValue;
	private String tradeType;
	//private Double positiveMoveVal;
	//private Double negativeMoveVal;
	
	private Integer tradeCountPerDay;
	private Double profitLossAmtValFinal;
	

	private String symbolType; 
	private String expiryDate;
	//Addditional End
	
	public AbstractTradeDataBean(Integer lotSize, String symbolId) {
		super();
		this.lotSize = lotSize;
		this.symbolId = symbolId;
		this.tradableStateId = STATUS_NA;
		this.tradedStateId = STATUS_NA;
		this.tradedLotCount = DEFAULT_TRADE_LOT;
		this.tradeType = TRADE_TYPE_FORWARD;
		
		this.profitLossAmtVal = 0d;
		this.negativeDirectionLoss = 0d;
		this.positiveDirectionProfit = 0d;
		//this.negativeDirectionLtp = 0d;
		//this.positiveDirectionLtp = 0d;
		this.positiveDirectionProfitPrevious = 0d;
		
		//04-21-2021  start - afterSomeSuccess	
		/*this.manualTradeExitInd;
		this.manualExitedAtVal;
		this.manualExitedAtDtTm;
		this.manualBookProfitLossAmtVal;*/
		this.manualTradeExitStateId = STATUS_NA;
		//04-21-2021  end
	}
	
	public AbstractTradeDataBean clone(AbstractTradeDataBean bean) {
		super.clone(bean);
		//AbstractTradeDataBean bean = new AbstractTradeDataBean(this.lotSize, this.symbolName);
		//AbstractTradeDataBean
		bean.setSymbolId(this.symbolId);
		bean.setSymbolName(this.symbolName);
		bean.setLotSize(this.lotSize);
		bean.setLtp(this.ltp);
		bean.setOpen(this.open);
		bean.setHigh(this.high);
		bean.setLow(this.low);
		bean.setClose(this.close);
		bean.setKiteFutureKey(this.kiteFutureKey);
		bean.setInstrumentToken(this.instrumentToken);
		bean.setKiteOrderId(this.kiteOrderId);
		bean.setKiteOrderType(this.kiteOrderType);
		bean.setStopLoss(this.baseStopLoss);
		bean.setBaseStopLoss(this.baseStopLoss);
		bean.setTargetPrice(this.targetPrice);
		bean.setTrailingStopLoss(this.trailingStopLoss);
		bean.setTradedAtVal(this.tradedAtVal);
		bean.setExitedAtVal(this.exitedAtVal);
		bean.setBuyAtVal(this.buyAtVal);
		bean.setSellAtVal(this.prevDayHrSellAtVal);
		bean.setBuyerAt(this.buyerAt);
		bean.setSellerAt(this.sellerAt);
		bean.setTradedAtDtTm(this.tradedAtDtTm);
		bean.setExitedAtDtTm(this.exitedAtDtTm);
		bean.setVolumes(this.volumes);
		bean.setBuySellQuantityRatio(this.buySellQuantityRatio);
		bean.setPercentageChange(this.percentageChange);
		bean.setTopHigh(this.topHigh);
		bean.setTopLow(this.topLow);
		bean.setProfitLossAmtVal(this.manualBookProfitLossAmtVal);
		bean.setBuyerSellerDiffVal(this.buyerSellerDiffVal);
		bean.setNegativeDirectionLtp(this.negativeDirectionLtp);
		bean.setNegativeDirectionLoss(this.negativeDirectionLoss);
		bean.setPositiveDirectionLtp(this.positiveDirectionLtp);
		bean.setPositiveDirectionProfit(this.positiveDirectionProfit);
		bean.setPositiveDirectionProfitPrevious(this.positiveDirectionProfitPrevious);
		bean.setProfitChaseVal(this.profitChaseVal);
		bean.setTradedDateStamp(this.tradedDateStamp);
		bean.setTradableStateId(this.tradableStateId);
		bean.setTradedStateId(this.tradedStateId);
		bean.setTradableStateDescription(this.tradableStateDescription);
		bean.setTradedStateDescription(this.tradedStateDescription);
		bean.setOhlcStateId(this.ohlcStateId);
		bean.setOhlcStateDescription(this.ohlcStateDescription);
		bean.setTradedLotCount(this.tradedLotCount);
		bean.setSignalTriggeredInAt(this.signalTriggeredInAt);
		bean.setSignalTriggeredOutAt(this.signalTriggeredOutAt);
		bean.setManualTradeExitInd(this.manualTradeExitInd);
		bean.setManualExitedAtVal(this.manualExitedAtVal);
		bean.setManualExitedAtDtTm(this.manualExitedAtDtTm);
		bean.setManualBookProfitLossAmtVal(this.manualBookProfitLossAmtVal);
		bean.setManualTradeExitStateId(this.manualTradeExitStateId);
		bean.setManualTradeExitStateDescription(this.manualTradeExitStateDescription);
		bean.setPrevDayHrBuyAtVal(this.prevDayHrBuyAtVal);
		bean.setPrevDayHrSellAtVal(this.prevDayHrSellAtVal);
		bean.setPrevDayHrCrossInd(this.prevDayHrCrossInd);
		bean.setPrevDayHigh(this.prevDayHigh);
		bean.setPrevDayLow(this.prevDayLow);
		bean.setPrevDayPercentageChange(this.prevDayPercentageChange);
			
		//Additional Start
		bean.setBuyerA2t(this.buyerA2t);
		bean.setSeller2At(this.seller2At);
		bean.setBuyer3At(this.buyer3At);
		bean.setSeller3At(this.seller3At);
		bean.setBuyer4At(this.buyer4At);
		bean.setSeller4At(this.seller4At);
		bean.setBuyer5At(this.buyer5At);
		bean.setSeller5At(this.seller5At);
		bean.setBuyerSellerDiffVal2(this.buyerSellerDiffVal2);
		bean.setBuyerSellerDiffVal3(this.buyerSellerDiffVal3);
		bean.setBuyerSellerDiffVal4(this.buyerSellerDiffVal4);
		bean.setBuyerSellerDiffVal5(this.buyerSellerDiffVal5);
		bean.setBuyerQuantityVal(this.buyerQuantityVal); 
		bean.setSellerQuantityVal(this.sellerQuantityVal);
		bean.setTradedAtVal2(this.tradedAtVal2);
		bean.setTradedAtDtTm2(this.tradedAtDtTm2);
		bean.setTradedAtVal3(this.tradedAtVal3);
		bean.setTradedAtDtTm3(this.tradedAtDtTm3);
		bean.setTradedAtVal4(this.tradedAtVal4);
		bean.setTradedAtDtTm4(this.tradedAtDtTm4);
		bean.setTradedAtVal5(this.tradedAtVal5);
		bean.setTradedAtDtTm5(this.tradedAtDtTm5);
		bean.setTargetAmtVal(this.targetAmtVal);
		bean.setTargetAmtVal2(this.targetAmtVal2);
		bean.setTargetAmtVal3(this.targetAmtVal3);
		bean.setTradedAtAvgVal(this.tradedAtAvgVal);
		bean.setVwapValue(this.vwapValue);
		bean.setTradeType(this.tradeType);	
		bean.setTradeCountPerDay(this.tradeCountPerDay);
		bean.setProfitLossAmtValFinal(this.profitLossAmtValFinal);
		bean.setSymbolType(this.symbolType);
		bean.setTrendTradableStateId(trendTradableStateId);
		
		
		bean.setExpiryDate(this.expiryDate);
		return bean;
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

	public Double getLtp() {
		return ltp;
	}

	public void setLtp(Double ltp) {
		this.ltp = ltp;
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

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public String getKiteFutureKey() {
		return kiteFutureKey;
	}

	public void setKiteFutureKey(String kiteFutureKey) {
		this.kiteFutureKey = kiteFutureKey;
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

	public Double getStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(Double stopLoss) {
		this.stopLoss = stopLoss;
	}

	public Double getBaseStopLoss() {
		return baseStopLoss;
	}

	public void setBaseStopLoss(Double baseStopLoss) {
		this.baseStopLoss = baseStopLoss;
	}

	public Double getTargetPrice() {
		return targetPrice;
	}

	public void setTargetPrice(Double targetPrice) {
		this.targetPrice = targetPrice;
	}

	public Double getTrailingStopLoss() {
		return trailingStopLoss;
	}

	public void setTrailingStopLoss(Double trailingStopLoss) {
		this.trailingStopLoss = trailingStopLoss;
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

	public Double getBuyerAt() {
		return buyerAt;
	}

	public void setBuyerAt(Double buyerAt) {
		this.buyerAt = buyerAt;
	}

	public Double getSellerAt() {
		return sellerAt;
	}

	public void setSellerAt(Double sellerAt) {
		this.sellerAt = sellerAt;
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

	public Long getVolumes() {
		return volumes;
	}

	public void setVolumes(Long volumes) {
		this.volumes = volumes;
	}

	/*public Double getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Double buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public Double getSellQuantity() {
		return sellQuantity;
	}

	public void setSellQuantity(Double sellQuantity) {
		this.sellQuantity = sellQuantity;
	}*/

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

	public Double getTopHigh() {
		return topHigh;
	}

	public void setTopHigh(Double topHigh) {
		this.topHigh = topHigh;
	}

	public Double getTopLow() {
		return topLow;
	}

	public void setTopLow(Double topLow) {
		this.topLow = topLow;
	}

	public Double getProfitLossAmtVal() {
		return profitLossAmtVal;
	}

	public void setProfitLossAmtVal(Double profitLossAmtVal) {
		this.profitLossAmtVal = profitLossAmtVal;
	}

	public Double getBuyerSellerDiffVal() {
		return buyerSellerDiffVal;
	}

	public void setBuyerSellerDiffVal(Double buyerSellerDiffVal) {
		this.buyerSellerDiffVal = buyerSellerDiffVal;
	}

	public Double getNegativeDirectionLtp() {
		return negativeDirectionLtp;
	}

	public void setNegativeDirectionLtp(Double negativeDirectionLtp) {
		this.negativeDirectionLtp = negativeDirectionLtp;
	}

	public Double getNegativeDirectionLoss() {
		return negativeDirectionLoss;
	}

	public void setNegativeDirectionLoss(Double negativeDirectionLoss) {
		this.negativeDirectionLoss = negativeDirectionLoss;
	}

	public Double getPositiveDirectionLtp() {
		return positiveDirectionLtp;
	}

	public void setPositiveDirectionLtp(Double positiveDirectionLtp) {
		this.positiveDirectionLtp = positiveDirectionLtp;
	}

	public Double getPositiveDirectionProfit() {
		return positiveDirectionProfit;
	}

	public void setPositiveDirectionProfit(Double positiveDirectionProfit) {
		this.positiveDirectionProfit = positiveDirectionProfit;
	}

	public Double getPositiveDirectionProfitPrevious() {
		return positiveDirectionProfitPrevious;
	}

	public void setPositiveDirectionProfitPrevious(Double positiveDirectionProfitPrevious) {
		this.positiveDirectionProfitPrevious = positiveDirectionProfitPrevious;
	}

	public Double getProfitChaseVal() {
		return profitChaseVal;
	}

	public void setProfitChaseVal(Double profitChaseVal) {
		this.profitChaseVal = profitChaseVal;
	}

	public Date getTradedDateStamp() {
		return tradedDateStamp;
	}

	public void setTradedDateStamp(Date tradedDateStamp) {
		this.tradedDateStamp = tradedDateStamp;
	}

	public String getTradableStateId() {
		return tradableStateId;
	}

	public void setTradableStateId(String tradableStateId) {
		this.tradableStateId = tradableStateId;
	}

	public String getTradedStateId() {
		return tradedStateId;
	}

	public void setTradedStateId(String tradedStateId) {
		this.tradedStateId = tradedStateId;
	}

	public String getTradableStateDescription() {
		return tradableStateDescription;
	}

	public void setTradableStateDescription(String tradableStateDescription) {
		this.tradableStateDescription = tradableStateDescription;
	}

	public String getTradedStateDescription() {
		return tradedStateDescription;
	}

	public void setTradedStateDescription(String tradedStateDescription) {
		this.tradedStateDescription = tradedStateDescription;
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

	public Integer getTradedLotCount() {
		return tradedLotCount;
	}

	public void setTradedLotCount(Integer tradedLotCount) {
		this.tradedLotCount = tradedLotCount;
	}

	public String getSignalTriggeredInAt() {
		return signalTriggeredInAt;
	}

	public void setSignalTriggeredInAt(String signalTriggeredInAt) {
		this.signalTriggeredInAt = signalTriggeredInAt;
	}

	public String getSignalTriggeredOutAt() {
		return signalTriggeredOutAt;
	}

	public void setSignalTriggeredOutAt(String signalTriggeredOutAt) {
		this.signalTriggeredOutAt = signalTriggeredOutAt;
	}

	/*public String getCandleTypeTrendId() {
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
	}*/

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

	public Double getPrevDayPercentageChange() {
		return prevDayPercentageChange;
	}

	public void setPrevDayPercentageChange(Double prevDayPercentageChange) {
		this.prevDayPercentageChange = prevDayPercentageChange;
	}

	public Double getBuyerA2t() {
		return buyerA2t;
	}

	public void setBuyerA2t(Double buyerA2t) {
		this.buyerA2t = buyerA2t;
	}

	public Double getSeller2At() {
		return seller2At;
	}

	public void setSeller2At(Double seller2At) {
		this.seller2At = seller2At;
	}

	public Double getBuyer3At() {
		return buyer3At;
	}

	public void setBuyer3At(Double buyer3At) {
		this.buyer3At = buyer3At;
	}

	public Double getSeller3At() {
		return seller3At;
	}

	public void setSeller3At(Double seller3At) {
		this.seller3At = seller3At;
	}

	public Double getBuyer4At() {
		return buyer4At;
	}

	public void setBuyer4At(Double buyer4At) {
		this.buyer4At = buyer4At;
	}

	public Double getSeller4At() {
		return seller4At;
	}

	public void setSeller4At(Double seller4At) {
		this.seller4At = seller4At;
	}

	public Double getBuyer5At() {
		return buyer5At;
	}

	public void setBuyer5At(Double buyer5At) {
		this.buyer5At = buyer5At;
	}

	public Double getSeller5At() {
		return seller5At;
	}

	public void setSeller5At(Double seller5At) {
		this.seller5At = seller5At;
	}

	public Double getBuyerSellerDiffVal2() {
		return buyerSellerDiffVal2;
	}

	public void setBuyerSellerDiffVal2(Double buyerSellerDiffVal2) {
		this.buyerSellerDiffVal2 = buyerSellerDiffVal2;
	}

	public Double getBuyerSellerDiffVal3() {
		return buyerSellerDiffVal3;
	}

	public void setBuyerSellerDiffVal3(Double buyerSellerDiffVal3) {
		this.buyerSellerDiffVal3 = buyerSellerDiffVal3;
	}

	public Double getBuyerSellerDiffVal4() {
		return buyerSellerDiffVal4;
	}

	public void setBuyerSellerDiffVal4(Double buyerSellerDiffVal4) {
		this.buyerSellerDiffVal4 = buyerSellerDiffVal4;
	}

	public Double getBuyerSellerDiffVal5() {
		return buyerSellerDiffVal5;
	}

	public void setBuyerSellerDiffVal5(Double buyerSellerDiffVal5) {
		this.buyerSellerDiffVal5 = buyerSellerDiffVal5;
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

	public Double getTradedAtVal4() {
		return tradedAtVal4;
	}

	public void setTradedAtVal4(Double tradedAtVal4) {
		this.tradedAtVal4 = tradedAtVal4;
	}

	public Date getTradedAtDtTm4() {
		return tradedAtDtTm4;
	}

	public void setTradedAtDtTm4(Date tradedAtDtTm4) {
		this.tradedAtDtTm4 = tradedAtDtTm4;
	}

	public Double getTradedAtVal5() {
		return tradedAtVal5;
	}

	public void setTradedAtVal5(Double tradedAtVal5) {
		this.tradedAtVal5 = tradedAtVal5;
	}

	public Date getTradedAtDtTm5() {
		return tradedAtDtTm5;
	}

	public void setTradedAtDtTm5(Date tradedAtDtTm5) {
		this.tradedAtDtTm5 = tradedAtDtTm5;
	}

	public Double getTargetAmtVal() {
		return targetAmtVal;
	}

	public void setTargetAmtVal(Double targetAmtVal) {
		this.targetAmtVal = targetAmtVal;
	}

	public Double getTargetAmtVal2() {
		return targetAmtVal2;
	}

	public void setTargetAmtVal2(Double targetAmtVal2) {
		this.targetAmtVal2 = targetAmtVal2;
	}

	public Double getTargetAmtVal3() {
		return targetAmtVal3;
	}

	public void setTargetAmtVal3(Double targetAmtVal3) {
		this.targetAmtVal3 = targetAmtVal3;
	}

	public Double getTradedAtAvgVal() {
		return tradedAtAvgVal;
	}

	public void setTradedAtAvgVal(Double tradedAtAvgVal) {
		this.tradedAtAvgVal = tradedAtAvgVal;
	}

	public Double getVwapValue() {
		return vwapValue;
	}

	public void setVwapValue(Double vwapValue) {
		this.vwapValue = vwapValue;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
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

	public String getSymbolType() {
		return symbolType;
	}

	public void setSymbolType(String symbolType) {
		this.symbolType = symbolType;
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
	// 04-21-2021 end
	@Override
	public String toString() {
		return "AbstractTradeDataBean [symbolId=" + symbolId + ", symbolName=" + symbolName + ", lotSize=" + lotSize
				+ ", ltp=" + ltp + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close
				+ ", kiteFutureKey=" + kiteFutureKey + ", instrumentToken=" + instrumentToken + ", kiteOrderId="
				+ kiteOrderId + ", kiteOrderType=" + kiteOrderType + ", stopLoss=" + stopLoss + ", baseStopLoss="
				+ baseStopLoss + ", targetPrice=" + targetPrice + ", trailingStopLoss=" + trailingStopLoss
				+ ", tradedAtVal=" + tradedAtVal + ", exitedAtVal=" + exitedAtVal + ", buyAtVal=" + buyAtVal
				+ ", sellAtVal=" + sellAtVal + ", buyerAt=" + buyerAt + ", sellerAt=" + sellerAt + ", tradedAtDtTm="
				+ tradedAtDtTm + ", exitedAtDtTm=" + exitedAtDtTm + ", volumes=" + volumes +  ", buySellQuantityRatio=" + buySellQuantityRatio
				+ ", percentageChange=" + percentageChange + ", profitLossAmtVal=" + profitLossAmtVal
				+ ", buyerSellerDiffVal=" + buyerSellerDiffVal + ", negativeDirectionLtp=" + negativeDirectionLtp
				+ ", negativeDirectionLoss=" + negativeDirectionLoss + ", positiveDirectionLtp=" + positiveDirectionLtp
				+ ", positiveDirectionProfit=" + positiveDirectionProfit + ", profitChaseVal=" + profitChaseVal
				+ ", tradedDateStamp=" + tradedDateStamp + ", tradableStateId=" + tradableStateId + ", tradedStateId="
				+ tradedStateId + ", tradableStateDescription=" + tradableStateDescription + ", tradedStateDescription="
				+ tradedStateDescription + ", ohlcStateId=" + ohlcStateId + ", ohlcStateDescription="
				+ ohlcStateDescription + ", tradedLotCount=" + tradedLotCount + ", signalTriggeredInAt="
				+ signalTriggeredInAt + ", signalTriggeredOutAt=" + signalTriggeredOutAt /*+ ", candleTypeTrendId="
				+ candleTypeTrendId + ", candleTypeTrendDescription=" + candleTypeTrendDescription*/
				+ ", prevDayHrBuyAtVal=" + prevDayHrBuyAtVal + ", prevDayHrSellAtVal=" + prevDayHrSellAtVal
				+ ", prevDayHrCrossInd=" + prevDayHrCrossInd + ", prevDayHigh=" + prevDayHigh + ", prevDayLow="
				+ prevDayLow + ", prevDayPercentageChange=" + prevDayPercentageChange + ", buyerA2t=" + buyerA2t
				+ ", seller2At=" + seller2At + ", buyer3At=" + buyer3At + ", seller3At=" + seller3At + ", buyer4At="
				+ buyer4At + ", seller4At=" + seller4At + ", buyer5At=" + buyer5At + ", seller5At=" + seller5At
				+ ", buyerSellerDiffVal2=" + buyerSellerDiffVal2 + ", buyerSellerDiffVal3=" + buyerSellerDiffVal3
				+ ", buyerSellerDiffVal4=" + buyerSellerDiffVal4 + ", buyerSellerDiffVal5=" + buyerSellerDiffVal5
				+ ", buyerQuantityVal=" + buyerQuantityVal + ", sellerQuantityVal=" + sellerQuantityVal
				+ ", tradedAtVal2=" + tradedAtVal2 + ", tradedAtDtTm2=" + tradedAtDtTm2 + ", tradedAtVal3="
				+ tradedAtVal3 + ", tradedAtDtTm3=" + tradedAtDtTm3 + ", tradedAtVal4=" + tradedAtVal4
				+ ", tradedAtDtTm4=" + tradedAtDtTm4 + ", tradedAtVal5=" + tradedAtVal5 + ", tradedAtDtTm5="
				+ tradedAtDtTm5 + ", targetAmtVal=" + targetAmtVal + ", targetAmtVal2=" + targetAmtVal2
				+ ", targetAmtVal3=" + targetAmtVal3 + ", tradedAtAvgVal=" + tradedAtAvgVal + ", vwapValue=" + vwapValue
				+ ", tradeType=" + tradeType + ", tradeCountPerDay=" + tradeCountPerDay + ", profitLossAmtValFinal="
				+ profitLossAmtValFinal + "]" +"\r\n"+super.toString();
	}
	
	
	
	
	
	private String trendTradableStateId;

	public String getTrendTradableStateId() {
		return trendTradableStateId;
	}

	public void setTrendTradableStateId(String trendTradableStateId) {
		this.trendTradableStateId = trendTradableStateId;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
}