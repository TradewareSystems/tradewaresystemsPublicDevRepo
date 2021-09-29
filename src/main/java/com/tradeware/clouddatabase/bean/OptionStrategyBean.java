package com.tradeware.clouddatabase.bean;

import java.util.Date;

public class OptionStrategyBean extends AbstractBean {

	private static final long serialVersionUID = 525944689646409461L;
	
	private Integer itemId;
	private String symbolId;
	private String symbolName;
	private Double lotSize;
	private Date tradedAtDtTm;
	private Date exitedAtDtTm;
	private String tradableStateId;
	private String tradableStateDesc;
	private String tradedStateId;
	private String tradedStateDesc;
	private Double buyerSellerDiffVal; 
	private Integer tradedLotCount;//In other entity also
	private Double profitLossAmtVal; 
	private Double positiveMoveVal;
	private Double negativeMoveVal;
	private Double positiveMoveLtp;
	private Double negativeMoveLtp;
	private Integer candleNumber;
	private Date tradedDateStamp;
	private String optionChainTrend;
	private String optionChainPriceTrend;
	private Integer optionChainId;
	private String oiInfo;
	private String candleTimeFrame;
	private Double targetPrice;
	private Double stopLoss;
	private Integer tradeCountPerDay;
	private Double profitLossAmtValFinal;
	private Boolean manualTradeExitInd;
	private Double manualExitedAtVal;
	private Date manualExitedAtDtTm;
	private Double manualBookProfitLossAmtVal;
	private String manualTradeExitStateId;
	private String manualTradeExitStateDesc;
	private String optionTradeStrategy;
	private String optionTradeSubStrategy;
	private Double underlyingStockPrice;
	private Double underlyingFuturePrice;
	
	private Integer strikePriceMain;//strikePriceAt;
	private String strikePriceMainTradeType;//Buy or Sell
	private Integer strikePriceLossProtect;
	private String strikePriceLossProtectTradeType;//Buy or Sell
	private String strikePriceMainOptionType;//call or put
	private String strikePriceLossProtectOptionType;//call or put
	private Double buyAtMain;//bidPriceOfAtm;//buyerAt
	private Double sellAtMain;//askPriceOfAtm;//sellerAt
	private Integer buyQuantityMain;//bidQuantityOfAtm;
	private Integer sellQuantityMain;//askQuantityOfAtm; 
	private Double buyAtLossProtect;//bidPriceOfInOrOut;//buyerAt
	private Double sellAtLossProtect;//askPriceOfInOrOut;//sellerAt
	private Integer buyQuantityLossProtect;//bidQuantityOfInOrOut;
	private Integer sellQuantityLossProtect;//askQuantityOfInOrOut; 
	private Double gainPointsMain;
	private Double gainPointsLossProtect;
	private String expiryDateMain;
	private String expiryDateLossProtect;
	private Double positiveMoveValMain;
	private Double negativeMoveValMain;
	private Double positiveMoveLtpMain;
	private Double negativeMoveLtpMain;
	private Double positiveMoveValLossProtect;
	private Double negativeMoveValLossProtect;
	private Double positiveMoveLtpLossProtect;
	private Double negativeMoveLtpLossProtect;
	
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
	public Double getLotSize() {
		return lotSize;
	}
	public void setLotSize(Double lotSize) {
		this.lotSize = lotSize;
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
	public String getTradableStateDesc() {
		return tradableStateDesc;
	}
	public void setTradableStateDesc(String tradableStateDesc) {
		this.tradableStateDesc = tradableStateDesc;
	}
	public String getTradedStateId() {
		return tradedStateId;
	}
	public void setTradedStateId(String tradedStateId) {
		this.tradedStateId = tradedStateId;
	}
	public String getTradedStateDesc() {
		return tradedStateDesc;
	}
	public void setTradedStateDesc(String tradedStateDesc) {
		this.tradedStateDesc = tradedStateDesc;
	}
	public Double getBuyerSellerDiffVal() {
		return buyerSellerDiffVal;
	}
	public void setBuyerSellerDiffVal(Double buyerSellerDiffVal) {
		this.buyerSellerDiffVal = buyerSellerDiffVal;
	}
	public Integer getTradedLotCount() {
		return tradedLotCount;
	}
	public void setTradedLotCount(Integer tradedLotCount) {
		this.tradedLotCount = tradedLotCount;
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
	public Integer getCandleNumber() {
		return candleNumber;
	}
	public void setCandleNumber(Integer candleNumber) {
		this.candleNumber = candleNumber;
	}
	public Date getTradedDateStamp() {
		return tradedDateStamp;
	}
	public void setTradedDateStamp(Date tradedDateStamp) {
		this.tradedDateStamp = tradedDateStamp;
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
	public String getOiInfo() {
		return oiInfo;
	}
	public void setOiInfo(String oiInfo) {
		this.oiInfo = oiInfo;
	}
	public String getCandleTimeFrame() {
		return candleTimeFrame;
	}
	public void setCandleTimeFrame(String candleTimeFrame) {
		this.candleTimeFrame = candleTimeFrame;
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
	public String getManualTradeExitStateDesc() {
		return manualTradeExitStateDesc;
	}
	public void setManualTradeExitStateDesc(String manualTradeExitStateDesc) {
		this.manualTradeExitStateDesc = manualTradeExitStateDesc;
	}
	public String getOptionTradeStrategy() {
		return optionTradeStrategy;
	}
	public void setOptionTradeStrategy(String optionTradeStrategy) {
		this.optionTradeStrategy = optionTradeStrategy;
	}
	public String getOptionTradeSubStrategy() {
		return optionTradeSubStrategy;
	}
	public void setOptionTradeSubStrategy(String optionTradeSubStrategy) {
		this.optionTradeSubStrategy = optionTradeSubStrategy;
	}
	public Double getUnderlyingStockPrice() {
		return underlyingStockPrice;
	}
	public void setUnderlyingStockPrice(Double underlyingStockPrice) {
		this.underlyingStockPrice = underlyingStockPrice;
	}
	public Double getUnderlyingFuturePrice() {
		return underlyingFuturePrice;
	}
	public void setUnderlyingFuturePrice(Double underlyingFuturePrice) {
		this.underlyingFuturePrice = underlyingFuturePrice;
	}
	
	
	public Integer getStrikePriceMain() {
		return strikePriceMain;
	}
	public void setStrikePriceMain(Integer strikePriceMain) {
		this.strikePriceMain = strikePriceMain;
	}
	public String getStrikePriceMainTradeType() {
		return strikePriceMainTradeType;
	}
	public void setStrikePriceMainTradeType(String strikePriceMainTradeType) {
		this.strikePriceMainTradeType = strikePriceMainTradeType;
	}
	public Integer getStrikePriceLossProtect() {
		return strikePriceLossProtect;
	}
	public void setStrikePriceLossProtect(Integer strikePriceLossProtect) {
		this.strikePriceLossProtect = strikePriceLossProtect;
	}
	public String getStrikePriceLossProtectTradeType() {
		return strikePriceLossProtectTradeType;
	}
	public void setStrikePriceLossProtectTradeType(String strikePriceLossProtectTradeType) {
		this.strikePriceLossProtectTradeType = strikePriceLossProtectTradeType;
	}
	public String getStrikePriceMainOptionType() {
		return strikePriceMainOptionType;
	}
	public void setStrikePriceMainOptionType(String strikePriceMainOptionType) {
		this.strikePriceMainOptionType = strikePriceMainOptionType;
	}
	public String getStrikePriceLossProtectOptionType() {
		return strikePriceLossProtectOptionType;
	}
	public void setStrikePriceLossProtectOptionType(String strikePriceLossProtectOptionType) {
		this.strikePriceLossProtectOptionType = strikePriceLossProtectOptionType;
	}
	public Double getBuyAtMain() {
		return buyAtMain;
	}
	public void setBuyAtMain(Double buyAtMain) {
		this.buyAtMain = buyAtMain;
	}
	public Double getSellAtMain() {
		return sellAtMain;
	}
	public void setSellAtMain(Double sellAtMain) {
		this.sellAtMain = sellAtMain;
	}
	public Integer getBuyQuantityMain() {
		return buyQuantityMain;
	}
	public void setBuyQuantityMain(Integer buyQuantityMain) {
		this.buyQuantityMain = buyQuantityMain;
	}
	public Integer getSellQuantityMain() {
		return sellQuantityMain;
	}
	public void setSellQuantityMain(Integer sellQuantityMain) {
		this.sellQuantityMain = sellQuantityMain;
	}
	public Double getBuyAtLossProtect() {
		return buyAtLossProtect;
	}
	public void setBuyAtLossProtect(Double buyAtLossProtect) {
		this.buyAtLossProtect = buyAtLossProtect;
	}
	public Double getSellAtLossProtect() {
		return sellAtLossProtect;
	}
	public void setSellAtLossProtect(Double sellAtLossProtect) {
		this.sellAtLossProtect = sellAtLossProtect;
	}
	public Integer getBuyQuantityLossProtect() {
		return buyQuantityLossProtect;
	}
	public void setBuyQuantityLossProtect(Integer buyQuantityLossProtect) {
		this.buyQuantityLossProtect = buyQuantityLossProtect;
	}
	public Integer getSellQuantityLossProtect() {
		return sellQuantityLossProtect;
	}
	public void setSellQuantityLossProtect(Integer sellQuantityLossProtect) {
		this.sellQuantityLossProtect = sellQuantityLossProtect;
	}
	public Double getGainPointsMain() {
		return gainPointsMain;
	}
	public void setGainPointsMain(Double gainPointsMain) {
		this.gainPointsMain = gainPointsMain;
	}
	public Double getGainPointsLossProtect() {
		return gainPointsLossProtect;
	}
	public void setGainPointsLossProtect(Double gainPointsLossProtect) {
		this.gainPointsLossProtect = gainPointsLossProtect;
	}
	public String getExpiryDateMain() {
		return expiryDateMain;
	}
	public void setExpiryDateMain(String expiryDateMain) {
		this.expiryDateMain = expiryDateMain;
	}
	public String getExpiryDateLossProtect() {
		return expiryDateLossProtect;
	}
	public void setExpiryDateLossProtect(String expiryDateLossProtect) {
		this.expiryDateLossProtect = expiryDateLossProtect;
	}
	public Double getPositiveMoveValMain() {
		return positiveMoveValMain;
	}
	public void setPositiveMoveValMain(Double positiveMoveValMain) {
		this.positiveMoveValMain = positiveMoveValMain;
	}
	public Double getNegativeMoveValMain() {
		return negativeMoveValMain;
	}
	public void setNegativeMoveValMain(Double negativeMoveValMain) {
		this.negativeMoveValMain = negativeMoveValMain;
	}
	public Double getPositiveMoveLtpMain() {
		return positiveMoveLtpMain;
	}
	public void setPositiveMoveLtpMain(Double positiveMoveLtpMain) {
		this.positiveMoveLtpMain = positiveMoveLtpMain;
	}
	public Double getNegativeMoveLtpMain() {
		return negativeMoveLtpMain;
	}
	public void setNegativeMoveLtpMain(Double negativeMoveLtpMain) {
		this.negativeMoveLtpMain = negativeMoveLtpMain;
	}
	public Double getPositiveMoveValLossProtect() {
		return positiveMoveValLossProtect;
	}
	public void setPositiveMoveValLossProtect(Double positiveMoveValLossProtect) {
		this.positiveMoveValLossProtect = positiveMoveValLossProtect;
	}
	public Double getNegativeMoveValLossProtect() {
		return negativeMoveValLossProtect;
	}
	public void setNegativeMoveValLossProtect(Double negativeMoveValLossProtect) {
		this.negativeMoveValLossProtect = negativeMoveValLossProtect;
	}
	public Double getPositiveMoveLtpLossProtect() {
		return positiveMoveLtpLossProtect;
	}
	public void setPositiveMoveLtpLossProtect(Double positiveMoveLtpLossProtect) {
		this.positiveMoveLtpLossProtect = positiveMoveLtpLossProtect;
	}
	public Double getNegativeMoveLtpLossProtect() {
		return negativeMoveLtpLossProtect;
	}
	public void setNegativeMoveLtpLossProtect(Double negativeMoveLtpLossProtect) {
		this.negativeMoveLtpLossProtect = negativeMoveLtpLossProtect;
	}
	@Override
	public String toString() {
		return "OptionStrategyBean [itemId=" + itemId+ ", \r\n symbolId=" + symbolId+ ", \r\n symbolName=" + symbolName
				+ ", lotSize=" + lotSize+ ", \r\n tradedAtDtTm=" + tradedAtDtTm+ ", \r\n exitedAtDtTm=" + exitedAtDtTm
				+ ", tradableStateId=" + tradableStateId+ ", \r\n tradableStateDesc=" + tradableStateDesc
				+ ", tradedStateId=" + tradedStateId+ ", \r\n tradedStateDesc=" + tradedStateDesc+ ", \r\n buyerSellerDiffVal="
				+ buyerSellerDiffVal+ ", \r\n tradedLotCount=" + tradedLotCount+ ", \r\n profitLossAmtVal=" + profitLossAmtVal
				+ ", positiveMoveVal=" + positiveMoveVal+ ", \r\n negativeMoveVal=" + negativeMoveVal+ ", \r\n positiveMoveLtp="
				+ positiveMoveLtp+ ", \r\n negativeMoveLtp=" + negativeMoveLtp+ ", \r\n candleNumber=" + candleNumber
				+ ", tradedDateStamp=" + tradedDateStamp+ ", \r\n optionChainTrend=" + optionChainTrend
				+ ", optionChainPriceTrend=" + optionChainPriceTrend+ ", \r\n optionChainId=" + optionChainId+ ", \r\n oiInfo="
				+ oiInfo+ ", \r\n candleTimeFrame=" + candleTimeFrame+ ", \r\n targetPrice=" + targetPrice+ ", \r\n stopLoss="
				+ stopLoss+ ", \r\n tradeCountPerDay=" + tradeCountPerDay+ ", \r\n profitLossAmtValFinal="
				+ profitLossAmtValFinal+ ", \r\n manualTradeExitInd=" + manualTradeExitInd+ ", \r\n manualExitedAtVal="
				+ manualExitedAtVal+ ", \r\n manualExitedAtDtTm=" + manualExitedAtDtTm+ ", \r\n manualBookProfitLossAmtVal="
				+ manualBookProfitLossAmtVal+ ", \r\n manualTradeExitStateId=" + manualTradeExitStateId
				+ ", manualTradeExitStateDesc=" + manualTradeExitStateDesc+ ", \r\n optionTradeStrategy="
				+ optionTradeStrategy+ ", \r\n optionTradeSubStrategy=" + optionTradeSubStrategy+ ", \r\n underlyingStockPrice="
				+ underlyingStockPrice+ ", \r\n underlyingFuturePrice=" + underlyingFuturePrice+ ", \r\n strikePriceMain="
				+ strikePriceMain+ ", \r\n strikePriceMainTradeType=" + strikePriceMainTradeType
				+ ", strikePriceLossProtect=" + strikePriceLossProtect+ ", \r\n strikePriceLossProtectTradeType="
				+ strikePriceLossProtectTradeType+ ", \r\n strikePriceMainOptionType=" + strikePriceMainOptionType
				+ ", strikePriceLossProtectOptionType=" + strikePriceLossProtectOptionType+ ", \r\n buyAtMain=" + buyAtMain
				+ ", sellAtMain=" + sellAtMain+ ", \r\n buyQuantityMain=" + buyQuantityMain+ ", \r\n sellQuantityMain="
				+ sellQuantityMain+ ", \r\n buyAtLossProtect=" + buyAtLossProtect+ ", \r\n sellAtLossProtect="
				+ sellAtLossProtect+ ", \r\n buyQuantityLossProtect=" + buyQuantityLossProtect
				+ ", sellQuantityLossProtect=" + sellQuantityLossProtect+ ", \r\n gainPointsMain=" + gainPointsMain
				+ ", gainPointsLossProtect=" + gainPointsLossProtect+ ", \r\n expiryDateMain=" + expiryDateMain
				+ ", expiryDateLossProtect=" + expiryDateLossProtect+ ", \r\n positiveMoveValMain=" + positiveMoveValMain
				+ ", negativeMoveValMain=" + negativeMoveValMain+ ", \r\n positiveMoveLtpMain=" + positiveMoveLtpMain
				+ ", negativeMoveLtpMain=" + negativeMoveLtpMain+ ", \r\n positiveMoveValLossProtect="
				+ positiveMoveValLossProtect+ ", \r\n negativeMoveValLossProtect=" + negativeMoveValLossProtect
				+ ", positiveMoveLtpLossProtect=" + positiveMoveLtpLossProtect+ ", \r\n negativeMoveLtpLossProtect="
				+ negativeMoveLtpLossProtect + "]";
	}
	
	
}
