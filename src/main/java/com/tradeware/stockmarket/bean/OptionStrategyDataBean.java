package com.tradeware.stockmarket.bean;

import java.util.Date;

public class OptionStrategyDataBean extends AbstractTradeDataBean {

	private static final long serialVersionUID = 8722408458311868861L;

	private String strategyRule;
	private String strengthStyleClass;
	
	
	private String expiryDate;
	private Double strikePrice;
	
	private Double bidPriceCall;//buyerAt
	private Double askPriceCall;//sellerAt
	private Integer bidQuantityCall;
	private Integer askQuantityCall; 
	private Double lastPriceCall;
	private Double changeCall;
	private Double pchangeCall;
	private Double vwapCall;
	private Double openInterestCall;
	private Double changeInOpenInterestCall;
	private Double pchangeInOpenInterestCall;
	private Double impliedVolatilityCall;
	private Double dailyvolatilityCall;
	private Double annualisedVolatilityCall;
	private Double bidPricePut;//buyerAt
	private Double askPricePut;//sellerAt
	private Integer bidQuantityPut;
	private Integer askQuantityPut; 
	private Double lastPricePut;
	private Double changePut;
	private Double pchangePut;
	private Double vwapPut;
	private Double openInterestPut;
	private Double changeInOpenInterestPut;
	private Double pchangeInOpenInterestPut;
	private Double impliedVolatilityPut;
	private Double dailyvolatilityPut;
	private Double annualisedVolatilityPut;
	
	//private String symbolId;//
	//private String expiryDate;
	//private String optionTradeStrategy;
	//private String optionTradeSubStrategy;
	private Integer strikePriceAt;
	private String strikePriceAtTradeType;
	private Integer strikePriceInOrOut;
	private String strikePriceInOrOutTradeType;
	private String strikePriceAtOptionType;
	private String strikePriceInOrOutOptionType;
	
	private Double bidPriceOfAtm;//buyerAt
	private Double askPriceOfAtm;//sellerAt
	private Integer bidQuantityOfAtm;
	private Integer askQuantityOfAtm; 
	private Double bidPriceOfInOrOut;//buyerAt
	private Double askPriceOfInOrOut;//sellerAt
	private Integer bidQuantityOfInOrOut;
	private Integer askQuantityOfInOrOut; 
	
	public OptionStrategyDataBean(Integer lotSize, String symbolId) {
		super(lotSize, symbolId);
		
	}

	public OptionStrategyDataBean clone() {
		OptionStrategyDataBean bean = new OptionStrategyDataBean(super.getLotSize(), super.getSymbolId());
		super.clone(bean);
		
		return bean;
	}

	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	/*@Override
	public boolean equals(Object obj) {
		boolean isEqual = super.equals(obj);
		OptionStrategyDataBean other = (OptionStrategyDataBean) obj;
		isEqual = (null != this.itemId && null != other.getItemId()
				&& this.itemId.intValue() == other.itemId.intValue());
		return isEqual;
	}*/
	


	private Integer itemId;
	private String symbolId;
	private String symbolName;
	private Integer lotSize;
	private Date tradedAtDtTm;
	private Date exitedAtDtTm;
	private String tradableStateId;
	private String tradableStateDesc;
	private String tradedStateId;
	private String tradedStateDesc;
	private Integer tradedLotCount;//In other entity also
	private Double profitLossAmtVal; 
	private Date tradedDateStamp;
	private String optionChainTrend;
	private String optionChainPriceTrend;
	private Integer optionChainId;
	private String oiInfo;
	private Double targetPrice;
	private Double stopLoss;
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
	
	private Double strikePriceMain;//strikePriceAt;
	private String strikePriceMainTradeType;//Buy or Sell
	private Double strikePriceLossProtect;
	private String strikePriceLossProtectTradeType;//Buy or Sell
	private String strikePriceMainOptionType;//call or put
	private String strikePriceLossProtectOptionType;//call or put
	private Double buyAtMain;//bidPriceOfAtm;//buyerAt
	private Double sellAtMain;//askPriceOfAtm;//sellerAt
	private Double lastPriceCallMain;//bidPriceOfAtm;//buyerAt
	private Double lastPricePutMain;//bidPriceOfAtm;//buyerAt
	private Integer buyQuantityMain;//bidQuantityOfAtm;
	private Integer sellQuantityMain;//askQuantityOfAtm; 
	private Double buyAtLossProtect;//bidPriceOfInOrOut;//buyerAt
	private Double sellAtLossProtect;//askPriceOfInOrOut;//sellerAt
	private Double lastPriceCallLossProtect;//bidPriceOfAtm;//buyerAt
	private Double lastPricePutLossProtect;//bidPriceOfAtm;//buyerAt
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
	public String getStrategyRule() {
		return strategyRule;
	}

	public void setStrategyRule(String strategyRule) {
		this.strategyRule = strategyRule;
	}

	public String getStrengthStyleClass() {
		return strengthStyleClass;
	}

	public void setStrengthStyleClass(String strengthStyleClass) {
		this.strengthStyleClass = strengthStyleClass;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Double getStrikePrice() {
		return strikePrice;
	}

	public void setStrikePrice(Double strikePrice) {
		this.strikePrice = strikePrice;
	}

	public Double getBidPriceCall() {
		return bidPriceCall;
	}

	public void setBidPriceCall(Double bidPriceCall) {
		this.bidPriceCall = bidPriceCall;
	}

	public Double getAskPriceCall() {
		return askPriceCall;
	}

	public void setAskPriceCall(Double askPriceCall) {
		this.askPriceCall = askPriceCall;
	}

	public Integer getBidQuantityCall() {
		return bidQuantityCall;
	}

	public void setBidQuantityCall(Integer bidQuantityCall) {
		this.bidQuantityCall = bidQuantityCall;
	}

	public Integer getAskQuantityCall() {
		return askQuantityCall;
	}

	public void setAskQuantityCall(Integer askQuantityCall) {
		this.askQuantityCall = askQuantityCall;
	}

	public Double getLastPriceCall() {
		return lastPriceCall;
	}

	public void setLastPriceCall(Double lastPriceCall) {
		this.lastPriceCall = lastPriceCall;
	}

	public Double getChangeCall() {
		return changeCall;
	}

	public void setChangeCall(Double changeCall) {
		this.changeCall = changeCall;
	}

	public Double getPchangeCall() {
		return pchangeCall;
	}

	public void setPchangeCall(Double pchangeCall) {
		this.pchangeCall = pchangeCall;
	}

	public Double getVwapCall() {
		return vwapCall;
	}

	public void setVwapCall(Double vwapCall) {
		this.vwapCall = vwapCall;
	}

	public Double getOpenInterestCall() {
		return openInterestCall;
	}

	public void setOpenInterestCall(Double openInterestCall) {
		this.openInterestCall = openInterestCall;
	}

	public Double getChangeInOpenInterestCall() {
		return changeInOpenInterestCall;
	}

	public void setChangeInOpenInterestCall(Double changeInOpenInterestCall) {
		this.changeInOpenInterestCall = changeInOpenInterestCall;
	}

	public Double getPchangeInOpenInterestCall() {
		return pchangeInOpenInterestCall;
	}

	public void setPchangeInOpenInterestCall(Double pchangeInOpenInterestCall) {
		this.pchangeInOpenInterestCall = pchangeInOpenInterestCall;
	}

	public Double getImpliedVolatilityCall() {
		return impliedVolatilityCall;
	}

	public void setImpliedVolatilityCall(Double impliedVolatilityCall) {
		this.impliedVolatilityCall = impliedVolatilityCall;
	}

	public Double getDailyvolatilityCall() {
		return dailyvolatilityCall;
	}

	public void setDailyvolatilityCall(Double dailyvolatilityCall) {
		this.dailyvolatilityCall = dailyvolatilityCall;
	}

	public Double getAnnualisedVolatilityCall() {
		return annualisedVolatilityCall;
	}

	public void setAnnualisedVolatilityCall(Double annualisedVolatilityCall) {
		this.annualisedVolatilityCall = annualisedVolatilityCall;
	}

	public Double getBidPricePut() {
		return bidPricePut;
	}

	public void setBidPricePut(Double bidPricePut) {
		this.bidPricePut = bidPricePut;
	}

	public Double getAskPricePut() {
		return askPricePut;
	}

	public void setAskPricePut(Double askPricePut) {
		this.askPricePut = askPricePut;
	}

	public Integer getBidQuantityPut() {
		return bidQuantityPut;
	}

	public void setBidQuantityPut(Integer bidQuantityPut) {
		this.bidQuantityPut = bidQuantityPut;
	}

	public Integer getAskQuantityPut() {
		return askQuantityPut;
	}

	public void setAskQuantityPut(Integer askQuantityPut) {
		this.askQuantityPut = askQuantityPut;
	}

	public Double getLastPricePut() {
		return lastPricePut;
	}

	public void setLastPricePut(Double lastPricePut) {
		this.lastPricePut = lastPricePut;
	}

	public Double getChangePut() {
		return changePut;
	}

	public void setChangePut(Double changePut) {
		this.changePut = changePut;
	}

	public Double getPchangePut() {
		return pchangePut;
	}

	public void setPchangePut(Double pchangePut) {
		this.pchangePut = pchangePut;
	}

	public Double getVwapPut() {
		return vwapPut;
	}

	public void setVwapPut(Double vwapPut) {
		this.vwapPut = vwapPut;
	}

	public Double getOpenInterestPut() {
		return openInterestPut;
	}

	public void setOpenInterestPut(Double openInterestPut) {
		this.openInterestPut = openInterestPut;
	}

	public Double getChangeInOpenInterestPut() {
		return changeInOpenInterestPut;
	}

	public void setChangeInOpenInterestPut(Double changeInOpenInterestPut) {
		this.changeInOpenInterestPut = changeInOpenInterestPut;
	}

	public Double getPchangeInOpenInterestPut() {
		return pchangeInOpenInterestPut;
	}

	public void setPchangeInOpenInterestPut(Double pchangeInOpenInterestPut) {
		this.pchangeInOpenInterestPut = pchangeInOpenInterestPut;
	}

	public Double getImpliedVolatilityPut() {
		return impliedVolatilityPut;
	}

	public void setImpliedVolatilityPut(Double impliedVolatilityPut) {
		this.impliedVolatilityPut = impliedVolatilityPut;
	}

	public Double getDailyvolatilityPut() {
		return dailyvolatilityPut;
	}

	public void setDailyvolatilityPut(Double dailyvolatilityPut) {
		this.dailyvolatilityPut = dailyvolatilityPut;
	}

	public Double getAnnualisedVolatilityPut() {
		return annualisedVolatilityPut;
	}

	public void setAnnualisedVolatilityPut(Double annualisedVolatilityPut) {
		this.annualisedVolatilityPut = annualisedVolatilityPut;
	}

	public Integer getStrikePriceAt() {
		return strikePriceAt;
	}

	public void setStrikePriceAt(Integer strikePriceAt) {
		this.strikePriceAt = strikePriceAt;
	}

	public String getStrikePriceAtTradeType() {
		return strikePriceAtTradeType;
	}

	public void setStrikePriceAtTradeType(String strikePriceAtTradeType) {
		this.strikePriceAtTradeType = strikePriceAtTradeType;
	}

	public Integer getStrikePriceInOrOut() {
		return strikePriceInOrOut;
	}

	public void setStrikePriceInOrOut(Integer strikePriceInOrOut) {
		this.strikePriceInOrOut = strikePriceInOrOut;
	}

	public String getStrikePriceInOrOutTradeType() {
		return strikePriceInOrOutTradeType;
	}

	public void setStrikePriceInOrOutTradeType(String strikePriceInOrOutTradeType) {
		this.strikePriceInOrOutTradeType = strikePriceInOrOutTradeType;
	}

	public String getStrikePriceAtOptionType() {
		return strikePriceAtOptionType;
	}

	public void setStrikePriceAtOptionType(String strikePriceAtOptionType) {
		this.strikePriceAtOptionType = strikePriceAtOptionType;
	}

	public String getStrikePriceInOrOutOptionType() {
		return strikePriceInOrOutOptionType;
	}

	public void setStrikePriceInOrOutOptionType(String strikePriceInOrOutOptionType) {
		this.strikePriceInOrOutOptionType = strikePriceInOrOutOptionType;
	}

	public Double getBidPriceOfAtm() {
		return bidPriceOfAtm;
	}

	public void setBidPriceOfAtm(Double bidPriceOfAtm) {
		this.bidPriceOfAtm = bidPriceOfAtm;
	}

	public Double getAskPriceOfAtm() {
		return askPriceOfAtm;
	}

	public void setAskPriceOfAtm(Double askPriceOfAtm) {
		this.askPriceOfAtm = askPriceOfAtm;
	}

	public Integer getBidQuantityOfAtm() {
		return bidQuantityOfAtm;
	}

	public void setBidQuantityOfAtm(Integer bidQuantityOfAtm) {
		this.bidQuantityOfAtm = bidQuantityOfAtm;
	}

	public Integer getAskQuantityOfAtm() {
		return askQuantityOfAtm;
	}

	public void setAskQuantityOfAtm(Integer askQuantityOfAtm) {
		this.askQuantityOfAtm = askQuantityOfAtm;
	}

	public Double getBidPriceOfInOrOut() {
		return bidPriceOfInOrOut;
	}

	public void setBidPriceOfInOrOut(Double bidPriceOfInOrOut) {
		this.bidPriceOfInOrOut = bidPriceOfInOrOut;
	}

	public Double getAskPriceOfInOrOut() {
		return askPriceOfInOrOut;
	}

	public void setAskPriceOfInOrOut(Double askPriceOfInOrOut) {
		this.askPriceOfInOrOut = askPriceOfInOrOut;
	}

	public Integer getBidQuantityOfInOrOut() {
		return bidQuantityOfInOrOut;
	}

	public void setBidQuantityOfInOrOut(Integer bidQuantityOfInOrOut) {
		this.bidQuantityOfInOrOut = bidQuantityOfInOrOut;
	}

	public Integer getAskQuantityOfInOrOut() {
		return askQuantityOfInOrOut;
	}

	public void setAskQuantityOfInOrOut(Integer askQuantityOfInOrOut) {
		this.askQuantityOfInOrOut = askQuantityOfInOrOut;
	}

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

	public Double getStrikePriceMain() {
		return strikePriceMain;
	}

	public void setStrikePriceMain(Double strikePriceMain) {
		this.strikePriceMain = strikePriceMain;
	}

	public String getStrikePriceMainTradeType() {
		return strikePriceMainTradeType;
	}

	public void setStrikePriceMainTradeType(String strikePriceMainTradeType) {
		this.strikePriceMainTradeType = strikePriceMainTradeType;
	}

	public Double getStrikePriceLossProtect() {
		return strikePriceLossProtect;
	}

	public void setStrikePriceLossProtect(Double strikePriceLossProtect) {
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

	public Double getLastPriceCallMain() {
		return lastPriceCallMain;
	}

	public void setLastPriceCallMain(Double lastPriceCallMain) {
		this.lastPriceCallMain = lastPriceCallMain;
	}

	public Double getLastPricePutMain() {
		return lastPricePutMain;
	}

	public void setLastPricePutMain(Double lastPricePutMain) {
		this.lastPricePutMain = lastPricePutMain;
	}

	public Double getLastPriceCallLossProtect() {
		return lastPriceCallLossProtect;
	}

	public void setLastPriceCallLossProtect(Double lastPriceCallLossProtect) {
		this.lastPriceCallLossProtect = lastPriceCallLossProtect;
	}

	public Double getLastPricePutLossProtect() {
		return lastPricePutLossProtect;
	}

	public void setLastPricePutLossProtect(Double lastPricePutLossProtect) {
		this.lastPricePutLossProtect = lastPricePutLossProtect;
	}
}