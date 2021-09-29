package org.tradeware.stockmarket.bean;

import java.math.BigDecimal;

public class OptionStockDataBean extends StockDataBean {

	private static final long serialVersionUID = -4083616501538930615L;

	public OptionStockDataBean(Integer lotSize, String stockName, String kiteFutreKey,
			BigDecimal positionalUptrendValue, BigDecimal positionalDowntrendValue, BigDecimal topHigh,
			BigDecimal topLow) {
		super(lotSize, stockName);
		super.setKiteFutureKey(kiteFutreKey);
		super.setPositionalUptrendValue(positionalUptrendValue);
		super.setPositionalDowntrendValue(positionalDowntrendValue);
		super.setTopHigh(topHigh);
		super.setTopLow(topLow);
	}

	public OptionStockDataBean(StockDataBean bean) {
		this(bean.getLotSize(), bean.getStockName(), bean.getKiteFutureKey(), bean.getPositionalUptrendValue(),
				bean.getPositionalDowntrendValue(), bean.getTopHigh(), bean.getTopLow());
	}

	public OptionStockDataBean clone() throws CloneNotSupportedException {
		return (OptionStockDataBean) super.clone();
	}

	private BigDecimal ltpOption;
	private BigDecimal openOption;
	private BigDecimal highOption;
	private BigDecimal lowOption;
	private BigDecimal closeOption;
	private BigDecimal tradedValOption;
	private BigDecimal buyerSellerDiff;
	
	private String kiteOptionKey;

	public BigDecimal getLtpOption() {
		return ltpOption;
	}

	public void setLtpOption(BigDecimal ltpOption) {
		this.ltpOption = ltpOption;
	}

	public BigDecimal getOpenOption() {
		return openOption;
	}

	public void setOpenOption(BigDecimal openOption) {
		this.openOption = openOption;
	}

	public BigDecimal getHighOption() {
		return highOption;
	}

	public void setHighOption(BigDecimal highOption) {
		this.highOption = highOption;
	}

	public BigDecimal getLowOption() {
		return lowOption;
	}

	public void setLowOption(BigDecimal lowOption) {
		this.lowOption = lowOption;
	}

	public BigDecimal getCloseOption() {
		return closeOption;
	}

	public void setCloseOption(BigDecimal closeOption) {
		this.closeOption = closeOption;
	}

	public String getKiteOptionKey() {
		return kiteOptionKey;
	}

	public void setKiteOptionKey(String kiteOptionKey) {
		this.kiteOptionKey = kiteOptionKey;
	}

	public BigDecimal getBuyerSellerDiff() {
		return buyerSellerDiff;
	}

	public void setBuyerSellerDiff(BigDecimal buyerSellerDiff) {
		this.buyerSellerDiff = buyerSellerDiff;
	}

	public BigDecimal getTradedValOption() {
		return tradedValOption;
	}
	public double getTradedValOptionAsDouble() {
		return getScaledValue(tradedValOption);
	}
	
	public void setTradedValOption(BigDecimal tradedValOption) {
		this.tradedValOption = tradedValOption;
	}
}