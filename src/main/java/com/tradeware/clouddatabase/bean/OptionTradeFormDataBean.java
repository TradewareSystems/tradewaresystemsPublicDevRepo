package com.tradeware.clouddatabase.bean;

public class OptionTradeFormDataBean extends AbstractBean {
	private static final long serialVersionUID = -3529377001690091193L;

	private String symbolId;
	private String expiryDate;
	private String tradeStrategy;
	private String tradeSubStrategy;
	private Double strikePriceLowerOfAtm;
	private String lowerOfAtmTradeType;
	private String lowerOfAtmOptionType;
	private Double strikePriceHigherOfAtm;
	private String higherOfAtmTradeType;
	private String higherOfAtmOptionType;
	private Double strikePriceAtm;
	private String atmTradeType;
	private String atmOptionType;
	private Double stockLastPrice;
	private Double futureLastPrice;
	
	private Double bidPriceLowerOfAtm;//buyerAt
	private Double askPriceLowerOfAtm;//sellerAt
	private Integer bidQuantityLowerOfAtm;
	private Integer askQuantityLowerOfAtm; 
	private Double bidPriceHigherOfAtm;//buyerAt
	private Double askPriceHigherOfAtm;//sellerAt
	private Integer bidQuantityHigherOfAtm;
	private Integer askQuantityHigherOfAtm; 
	private Double bidPriceOfAtm;//buyerAt
	private Double askPriceOfAtm;//sellerAt
	private Integer bidQuantityOfAtm;
	private Integer askQuantityOfAtm; 
	private Double underlyingStockPrice;
	private Double underlyingFuturePrice;
	private Integer lotSize;
	private Integer numberOfLots;
	private Boolean intradayTradeInd = Boolean.FALSE;
	public String getSymbolId() {
		return symbolId;
	}
	public void setSymbolId(String symbolId) {
		this.symbolId = symbolId;
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
	public Double getStrikePriceLowerOfAtm() {
		return strikePriceLowerOfAtm;
	}
	public void setStrikePriceLowerOfAtm(Double strikePriceLowerOfAtm) {
		this.strikePriceLowerOfAtm = strikePriceLowerOfAtm;
	}
	public String getLowerOfAtmTradeType() {
		return lowerOfAtmTradeType;
	}
	public void setLowerOfAtmTradeType(String lowerOfAtmTradeType) {
		this.lowerOfAtmTradeType = lowerOfAtmTradeType;
	}
	public String getLowerOfAtmOptionType() {
		return lowerOfAtmOptionType;
	}
	public void setLowerOfAtmOptionType(String lowerOfAtmOptionType) {
		this.lowerOfAtmOptionType = lowerOfAtmOptionType;
	}
	public Double getStrikePriceHigherOfAtm() {
		return strikePriceHigherOfAtm;
	}
	public void setStrikePriceHigherOfAtm(Double strikePriceHigherOfAtm) {
		this.strikePriceHigherOfAtm = strikePriceHigherOfAtm;
	}
	public String getHigherOfAtmTradeType() {
		return higherOfAtmTradeType;
	}
	public void setHigherOfAtmTradeType(String higherOfAtmTradeType) {
		this.higherOfAtmTradeType = higherOfAtmTradeType;
	}
	public String getHigherOfAtmOptionType() {
		return higherOfAtmOptionType;
	}
	public void setHigherOfAtmOptionType(String higherOfAtmOptionType) {
		this.higherOfAtmOptionType = higherOfAtmOptionType;
	}
	public Double getStrikePriceAtm() {
		return strikePriceAtm;
	}
	public void setStrikePriceAtm(Double strikePriceAtm) {
		this.strikePriceAtm = strikePriceAtm;
	}
	public String getAtmTradeType() {
		return atmTradeType;
	}
	public void setAtmTradeType(String atmTradeType) {
		this.atmTradeType = atmTradeType;
	}
	public String getAtmOptionType() {
		return atmOptionType;
	}
	public void setAtmOptionType(String atmOptionType) {
		this.atmOptionType = atmOptionType;
	}
	public Double getStockLastPrice() {
		return stockLastPrice;
	}
	public void setStockLastPrice(Double stockLastPrice) {
		this.stockLastPrice = stockLastPrice;
	}
	public Double getFutureLastPrice() {
		return futureLastPrice;
	}
	public void setFutureLastPrice(Double futureLastPrice) {
		this.futureLastPrice = futureLastPrice;
	}
	public Double getBidPriceLowerOfAtm() {
		return bidPriceLowerOfAtm;
	}
	public void setBidPriceLowerOfAtm(Double bidPriceLowerOfAtm) {
		this.bidPriceLowerOfAtm = bidPriceLowerOfAtm;
	}
	public Double getAskPriceLowerOfAtm() {
		return askPriceLowerOfAtm;
	}
	public void setAskPriceLowerOfAtm(Double askPriceLowerOfAtm) {
		this.askPriceLowerOfAtm = askPriceLowerOfAtm;
	}
	public Integer getBidQuantityLowerOfAtm() {
		return bidQuantityLowerOfAtm;
	}
	public void setBidQuantityLowerOfAtm(Integer bidQuantityLowerOfAtm) {
		this.bidQuantityLowerOfAtm = bidQuantityLowerOfAtm;
	}
	public Integer getAskQuantityLowerOfAtm() {
		return askQuantityLowerOfAtm;
	}
	public void setAskQuantityLowerOfAtm(Integer askQuantityLowerOfAtm) {
		this.askQuantityLowerOfAtm = askQuantityLowerOfAtm;
	}
	public Double getBidPriceHigherOfAtm() {
		return bidPriceHigherOfAtm;
	}
	public void setBidPriceHigherOfAtm(Double bidPriceHigherOfAtm) {
		this.bidPriceHigherOfAtm = bidPriceHigherOfAtm;
	}
	public Double getAskPriceHigherOfAtm() {
		return askPriceHigherOfAtm;
	}
	public void setAskPriceHigherOfAtm(Double askPriceHigherOfAtm) {
		this.askPriceHigherOfAtm = askPriceHigherOfAtm;
	}
	public Integer getBidQuantityHigherOfAtm() {
		return bidQuantityHigherOfAtm;
	}
	public void setBidQuantityHigherOfAtm(Integer bidQuantityHigherOfAtm) {
		this.bidQuantityHigherOfAtm = bidQuantityHigherOfAtm;
	}
	public Integer getAskQuantityHigherOfAtm() {
		return askQuantityHigherOfAtm;
	}
	public void setAskQuantityHigherOfAtm(Integer askQuantityHigherOfAtm) {
		this.askQuantityHigherOfAtm = askQuantityHigherOfAtm;
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
	public Integer getLotSize() {
		return lotSize;
	}
	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}
	public Integer getNumberOfLots() {
		return numberOfLots;
	}
	public void setNumberOfLots(Integer numberOfLots) {
		this.numberOfLots = numberOfLots;
	}
	public Boolean getIntradayTradeInd() {
		return intradayTradeInd;
	}
	public void setIntradayTradeInd(Boolean intradayTradeInd) {
		this.intradayTradeInd = intradayTradeInd;
	}
}
