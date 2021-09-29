package org.tradeware.stockmarket.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStockDataBean extends ReportBean implements Serializable, Cloneable{
	private static final long serialVersionUID = -1452479149544042543L;
	private Long stockId; //Symbol Id
	private String stockName; //Symbol Name
	private String kiteFutureKey; //Kite Future Symbol
	private Integer lotSize;
	private BigDecimal ltp;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal close;
	private Date tradedAt;
	private Date tradedExitAt;
	
	private BigDecimal tradedVal;
	private BigDecimal profitLossAmt;
	private String tradeState = "NA";
	private String profitLossStatus;
	
	private BigDecimal volumeTradedToday;
	private BigDecimal buyQuantity;
	private BigDecimal sellQuantity;
	
	private BigDecimal buyerAt;
	private BigDecimal sellerAt;
	private BigDecimal buyerAt2;
	private BigDecimal selleerAt2;
	
	private Map<String, String> userIdOrderIdMap = new HashMap<String, String>();
	
	public Map<String, String> getUserIdOrderIdMap() {
		return userIdOrderIdMap;
	}

	public AbstractStockDataBean(Integer lotSize, String stockName) {
		this.lotSize = lotSize;
		this.stockName = stockName;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getKiteFutureKey() {
		return kiteFutureKey;
	}

	public void setKiteFutureKey(String kiteFutureKey) {
		this.kiteFutureKey = kiteFutureKey;
	}

	public Integer getLotSize() {
		return lotSize;
	}

	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}

	public BigDecimal getLtp() {
		return ltp;
	}

	public void setLtp(BigDecimal ltp) {
		this.ltp = ltp;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public Date getTradedAt() {
		return tradedAt;
	}

	public void setTradedAt(Date tradedAt) {
		this.tradedAt = tradedAt;
	}

	public Date getTradedExitAt() {
		return tradedExitAt;
	}

	public void setTradedExitAt(Date tradedExitAt) {
		this.tradedExitAt = tradedExitAt;
	}

	public BigDecimal getTradedVal() {
		return tradedVal;
	}
	public double getTradedValAsDouble() {
		return getScaledValue(tradedVal);
	}
	public void setTradedVal(BigDecimal tradedVal) {
		this.tradedVal = tradedVal;
	}
	public BigDecimal getProfitLossAmt() {
		return profitLossAmt;
	}

	public void setProfitLossAmt(BigDecimal profitLossAmt) {
		this.profitLossAmt = profitLossAmt;
	}
	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public String getProfitLossStatus() {
		return profitLossStatus;
	}

	public void setProfitLossStatus(String profitLossStatus) {
		this.profitLossStatus = profitLossStatus;
	}

	public BigDecimal getVolumeTradedToday() {
		return volumeTradedToday;
	}

	public void setVolumeTradedToday(BigDecimal volumeTradedToday) {
		this.volumeTradedToday = volumeTradedToday;
	}

	public BigDecimal getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(BigDecimal buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public BigDecimal getSellQuantity() {
		return sellQuantity;
	}

	public void setSellQuantity(BigDecimal sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

	public BigDecimal getBuyerAt() {
		return buyerAt;
	}

	public void setBuyerAt(BigDecimal buyerAt) {
		this.buyerAt = buyerAt;
	}

	public BigDecimal getSellerAt() {
		return sellerAt;
	}

	public void setSellerAt(BigDecimal sellerAt) {
		this.sellerAt = sellerAt;
	}

	public BigDecimal getBuyerAt2() {
		return buyerAt2;
	}

	public void setBuyerAt2(BigDecimal buyerAt2) {
		this.buyerAt2 = buyerAt2;
	}

	public BigDecimal getSelleerAt2() {
		return selleerAt2;
	}

	public void setSelleerAt2(BigDecimal selleerAt2) {
		this.selleerAt2 = selleerAt2;
	}
	
	// sclae amount value to
	/*public double getScaledValue(double value) {
		return new BigDecimal(value).setScale(1, 0).setScale(2, 0).doubleValue();
	}*/

	// sclae amount value to
	/*public double getScaledValue(BigDecimal value) {
		return value.setScale(1, 0).setScale(2, 0).doubleValue();
	}*/

	/*public BigDecimal getScaledValueObj(double value) {
		return new BigDecimal(value).setScale(1, 0).setScale(2, 0);
	}*/
}