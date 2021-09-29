package com.tradeware.clouddatabase.bean;

import java.util.Date;

import javax.persistence.Column;

public class OptionLiveTradeChildBean extends AbstractBean {
	private static final long serialVersionUID = 7229646503787538541L;
	private Integer itemIdChild;
	private Double strikePrice;
	private String tradeType;
	private String tradePosition; //OPEN/CLOSE
	private String optionType; //Call/Put
	private String kiteFutureKey;
	private Double buyAtValue;//buyerAt
	private Double sellAtValue;//sellerAt
	private Double tradedAtVal;
	private Double exitedAtVal;
	private Double stockPriceEntry;
	private Double futurePriceEntry;
	private Double stockPriceExit;
	private Double futurePriceExit;
	private Integer numberOfLots;
	private Date tradedAtDtTm;
	private Date exitedAtDtTm;
	private Double profitLossAmtVal; 
	private Double positiveMoveVal;
	private Double negativeMoveVal;
	private Double positiveMoveLtp;
	private Double negativeMoveLtp;
	private Date tradedDateStamp;
	private String optionChainTrend;
	private String optionChainPriceTrend;
	private Integer optionChainId;
	private String oiInfo;
	
	private Double bidPrice;//buyerAt
	private Double askPrice;//sellerAt
	private Double lastPrice;
	private Long bidQuantity;
	private Long askQuantity; 
	
	private Long instrumentToken;
	private String kiteOrderId;
	private String kiteOrderType;//CO/BO/Regular
	
	
	private Double stopLossVal;
	private Double targetVal;
	
	public Integer getItemIdChild() {
		return itemIdChild;
	}
	public void setItemIdChild(Integer itemIdChild) {
		this.itemIdChild = itemIdChild;
	}
	public Double getStrikePrice() {
		return strikePrice;
	}
	public void setStrikePrice(Double strikePrice) {
		this.strikePrice = strikePrice;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradePosition() {
		return tradePosition;
	}
	public void setTradePosition(String tradePosition) {
		this.tradePosition = tradePosition;
	}
	public String getOptionType() {
		return optionType;
	}
	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}
	public String getKiteFutureKey() {
		return kiteFutureKey;
	}
	public void setKiteFutureKey(String kiteFutureKey) {
		this.kiteFutureKey = kiteFutureKey;
	}
	public Double getBuyAtValue() {
		return buyAtValue;
	}
	public void setBuyAtValue(Double buyAtValue) {
		this.buyAtValue = buyAtValue;
	}
	public Double getSellAtValue() {
		return sellAtValue;
	}
	public void setSellAtValue(Double sellAtValue) {
		this.sellAtValue = sellAtValue;
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
	public Double getStockPriceEntry() {
		return stockPriceEntry;
	}
	public void setStockPriceEntry(Double stockPriceEntry) {
		this.stockPriceEntry = stockPriceEntry;
	}
	public Double getFuturePriceEntry() {
		return futurePriceEntry;
	}
	public void setFuturePriceEntry(Double futurePriceEntry) {
		this.futurePriceEntry = futurePriceEntry;
	}
	public Double getStockPriceExit() {
		return stockPriceExit;
	}
	public void setStockPriceExit(Double stockPriceExit) {
		this.stockPriceExit = stockPriceExit;
	}
	public Double getFuturePriceExit() {
		return futurePriceExit;
	}
	public void setFuturePriceExit(Double futurePriceExit) {
		this.futurePriceExit = futurePriceExit;
	}
	public Integer getNumberOfLots() {
		return numberOfLots;
	}
	public void setNumberOfLots(Integer numberOfLots) {
		this.numberOfLots = numberOfLots;
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
	public Double getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}
	public Double getAskPrice() {
		return askPrice;
	}
	public void setAskPrice(Double askPrice) {
		this.askPrice = askPrice;
	}
	public Double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}
	public Long getBidQuantity() {
		return bidQuantity;
	}
	public void setBidQuantity(Long bidQuantity) {
		this.bidQuantity = bidQuantity;
	}
	public Long getAskQuantity() {
		return askQuantity;
	}
	public void setAskQuantity(Long askQuantity) {
		this.askQuantity = askQuantity;
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
}
