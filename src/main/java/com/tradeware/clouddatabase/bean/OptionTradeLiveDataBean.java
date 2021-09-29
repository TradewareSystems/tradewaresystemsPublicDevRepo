package com.tradeware.clouddatabase.bean;

public class OptionTradeLiveDataBean extends AbstractBean {
	private static final long serialVersionUID = -3529377001690091193L;

	private String symbolId;
	private Double lotSize;
	private String expiryDate;
	private Double strikePrice;
	private Double underlyingStockPrice;
	private Double underlyingFuturePrice;
	private Double bidPriceCall;//buyerAt
	private Double askPriceCall;//sellerAt
	private Long bidQuantityCall;
	private Long askQuantityCall; 
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
	private Long bidQuantityPut;
	private Long askQuantityPut; 
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
	
	public String getSymbolId() {
		return symbolId;
	}
	public void setSymbolId(String symbolId) {
		this.symbolId = symbolId;
	}
	public Double getLotSize() {
		return lotSize;
	}
	public void setLotSize(Double lotSize) {
		this.lotSize = lotSize;
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
	public Long getBidQuantityCall() {
		return bidQuantityCall;
	}
	public void setBidQuantityCall(Long bidQuantityCall) {
		this.bidQuantityCall = bidQuantityCall;
	}
	public Long getAskQuantityCall() {
		return askQuantityCall;
	}
	public void setAskQuantityCall(Long askQuantityCall) {
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
	public Long getBidQuantityPut() {
		return bidQuantityPut;
	}
	public void setBidQuantityPut(Long bidQuantityPut) {
		this.bidQuantityPut = bidQuantityPut;
	}
	public Long getAskQuantityPut() {
		return askQuantityPut;
	}
	public void setAskQuantityPut(Long askQuantityPut) {
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
	
	@Override
	public String toString() {
		return "OptionTradeLiveDataBean [ \r\n symbolId=" + symbolId + ", \r\n expiryDate=" + expiryDate + ", \r\n strikePrice="
				+ strikePrice + ", \r\n underlyingStockPrice=" + underlyingStockPrice + ", \r\n underlyingFuturePrice="
				+ underlyingFuturePrice + ", \r\n bidPriceCall=" + bidPriceCall + ", \r\n askPriceCall=" + askPriceCall
				+ ", \r\n bidQuantityCall=" + bidQuantityCall + ", \r\n askQuantityCall=" + askQuantityCall + ", \r\n lastPriceCall="
				+ lastPriceCall + ", \r\n changeCall=" + changeCall + ", \r\n pchangeCall=" + pchangeCall + ", \r\n vwapCall="
				+ vwapCall + ", \r\n openInterestCall=" + openInterestCall + ", \r\n changeInOpenInterestCall="
				+ changeInOpenInterestCall + ", \r\n pchangeInOpenInterestCall=" + pchangeInOpenInterestCall
				+ ", \r\n impliedVolatilityCall=" + impliedVolatilityCall + ", \r\n dailyvolatilityCall=" + dailyvolatilityCall
				+ ", \r\n annualisedVolatilityCall=" + annualisedVolatilityCall + ", \r\n bidPricePut=" + bidPricePut
				+ ", \r\n askPricePut=" + askPricePut + ", \r\n bidQuantityPut=" + bidQuantityPut + ", \r\n askQuantityPut="
				+ askQuantityPut + ", \r\n lastPricePut=" + lastPricePut + ", \r\n changePut=" + changePut + ", \r\n pchangePut="
				+ pchangePut + ", \r\n vwapPut=" + vwapPut + ", \r\n openInterestPut=" + openInterestPut
				+ ", \r\n changeInOpenInterestPut=" + changeInOpenInterestPut + ", \r\n pchangeInOpenInterestPut="
				+ pchangeInOpenInterestPut + ", \r\n impliedVolatilityPut=" + impliedVolatilityPut + ", \r\n dailyvolatilityPut="
				+ dailyvolatilityPut + ", \r\n annualisedVolatilityPut=" + annualisedVolatilityPut + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + ((strikePrice == null) ? 0 : strikePrice.hashCode());
		result = prime * result + ((symbolId == null) ? 0 : symbolId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OptionTradeLiveDataBean other = (OptionTradeLiveDataBean) obj;
		if (expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!expiryDate.equals(other.expiryDate))
			return false;
		if (strikePrice == null) {
			if (other.strikePrice != null)
				return false;
		} else if (!strikePrice.equals(other.strikePrice))
			return false;
		if (symbolId == null) {
			if (other.symbolId != null)
				return false;
		} else if (!symbolId.equals(other.symbolId))
			return false;
		return true;
	}
	
	
}
