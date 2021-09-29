package com.tradeware.stockmarket.bean;

public class KiteLiveOHLCDataBean extends AbstractDataBean {

	private static final long serialVersionUID = -2938693058097056612L;
	private String symbolId;
	private Integer lotSize;
	private Double optionTickerSize;
	private Double ltp;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private String optionStrikePrice;
	private String optionExpiryDate;
	private String kiteFutureKey;
	private Long instrumentToken;
	
	private Long bidQuantity;
	private Long askQuantity;
	private Double buySellQuantityRatio;
	private Double percentageChange;
	
	private Double bidPrice;
	private Double askPrice;
	private Double bidPrice2;
	private Double askPrice2;
	private Double bidPrice3;
	private Double askPrice3;
	private Double bidPrice4;
	private Double askPrice4;
	private Double bidPrice5;
	private Double askPrice5;
	
	
	private Double askBidPriceDiffVal;
	private Double askBidPriceDiffVal2;
	private Double askBidPriceDiffVal3;
	private Double askBidPriceDiffVal4;
	private Double askBidPriceDiffVal5;
	
	private Double buyerQuantityVal; 
	private Double sellerQuantityVal;
	private Long volumes;
	
	private boolean isAskBidPriceDiffRuleValid;
	
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
	public Double getOptionTickerSize() {
		return optionTickerSize;
	}
	public void setOptionTickerSize(Double optionTickerSize) {
		this.optionTickerSize = optionTickerSize;
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
	public String getOptionStrikePrice() {
		return optionStrikePrice;
	}
	public void setOptionStrikePrice(String optionStrikePrice) {
		this.optionStrikePrice = optionStrikePrice;
	}
	public String getOptionExpiryDate() {
		return optionExpiryDate;
	}
	public void setOptionExpiryDate(String optionExpiryDate) {
		this.optionExpiryDate = optionExpiryDate;
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
	public Double getBidPrice2() {
		return bidPrice2;
	}
	public void setBidPrice2(Double bidPrice2) {
		this.bidPrice2 = bidPrice2;
	}
	public Double getAskPrice2() {
		return askPrice2;
	}
	public void setAskPrice2(Double askPrice2) {
		this.askPrice2 = askPrice2;
	}
	public Double getBidPrice3() {
		return bidPrice3;
	}
	public void setBidPrice3(Double bidPrice3) {
		this.bidPrice3 = bidPrice3;
	}
	public Double getAskPrice3() {
		return askPrice3;
	}
	public void setAskPrice3(Double askPrice3) {
		this.askPrice3 = askPrice3;
	}
	public Double getBidPrice4() {
		return bidPrice4;
	}
	public void setBidPrice4(Double bidPrice4) {
		this.bidPrice4 = bidPrice4;
	}
	public Double getAskPrice4() {
		return askPrice4;
	}
	public void setAskPrice4(Double askPrice4) {
		this.askPrice4 = askPrice4;
	}
	public Double getBidPrice5() {
		return bidPrice5;
	}
	public void setBidPrice5(Double bidPrice5) {
		this.bidPrice5 = bidPrice5;
	}
	public Double getAskPrice5() {
		return askPrice5;
	}
	public void setAskPrice5(Double askPrice5) {
		this.askPrice5 = askPrice5;
	}
	public Double getAskBidPriceDiffVal() {
		return askBidPriceDiffVal;
	}
	public void setAskBidPriceDiffVal(Double askBidPriceDiffVal) {
		this.askBidPriceDiffVal = askBidPriceDiffVal;
	}
	public Double getAskBidPriceDiffVal2() {
		return askBidPriceDiffVal2;
	}
	public void setAskBidPriceDiffVal2(Double askBidPriceDiffVal2) {
		this.askBidPriceDiffVal2 = askBidPriceDiffVal2;
	}
	public Double getAskBidPriceDiffVal3() {
		return askBidPriceDiffVal3;
	}
	public void setAskBidPriceDiffVal3(Double askBidPriceDiffVal3) {
		this.askBidPriceDiffVal3 = askBidPriceDiffVal3;
	}
	public Double getAskBidPriceDiffVal4() {
		return askBidPriceDiffVal4;
	}
	public void setAskBidPriceDiffVal4(Double askBidPriceDiffVal4) {
		this.askBidPriceDiffVal4 = askBidPriceDiffVal4;
	}
	public Double getAskBidPriceDiffVal5() {
		return askBidPriceDiffVal5;
	}
	public void setAskBidPriceDiffVal5(Double askBidPriceDiffVal5) {
		this.askBidPriceDiffVal5 = askBidPriceDiffVal5;
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
	public Long getVolumes() {
		return volumes;
	}
	public void setVolumes(Long volumes) {
		this.volumes = volumes;
	}
	public boolean isAskBidPriceDiffRuleValid() {
		return isAskBidPriceDiffRuleValid;
	}
	public void setAskBidPriceDiffRuleValid(boolean isAskBidPriceDiffRuleValid) {
		this.isAskBidPriceDiffRuleValid = isAskBidPriceDiffRuleValid;
	}
	
}
