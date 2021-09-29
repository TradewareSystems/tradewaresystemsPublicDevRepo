package org.tradeware.stockmarket.bean;

import java.math.BigDecimal;

public class Narrow7StockDataBean extends StockDataBean {

	private static final long serialVersionUID = 2282884528244653176L;

	private BigDecimal day1High;
	private BigDecimal day1Low;
	private BigDecimal day2High;
	private BigDecimal day2Low;
	private BigDecimal day3High;
	private BigDecimal day3Low;
	private BigDecimal day4High;
	private BigDecimal day4Low;
	private BigDecimal day5High;
	private BigDecimal day5Low;
	private BigDecimal day6High;
	private BigDecimal day6Low;
	private BigDecimal day7High;
	private BigDecimal day7Low;
	private Boolean narrow7Rule = Boolean.FALSE;
	
	public Narrow7StockDataBean(Integer lotSize, String stockName, String kiteFutreKey,
			BigDecimal positionalUptrendValue, BigDecimal positionalDowntrendValue, BigDecimal topHigh,
			BigDecimal topLow) {
		super(lotSize, stockName);
		super.setKiteFutureKey(kiteFutreKey);
		super.setPositionalUptrendValue(positionalUptrendValue);
		super.setPositionalDowntrendValue(positionalDowntrendValue);
		super.setTopHigh(topHigh);
		super.setTopLow(topLow);
	}

	public Narrow7StockDataBean(StockDataBean bean) {
		this(bean.getLotSize(), bean.getStockName(), bean.getKiteFutureKey(), bean.getPositionalUptrendValue(),
				bean.getPositionalDowntrendValue(), bean.getTopHigh(), bean.getTopLow());
	}

	public BigDecimal getDay1High() {
		return day1High;
	}

	public void setDay1High(BigDecimal day1High) {
		this.day1High = day1High;
	}
	public BigDecimal getDay1Low() {
		return day1Low;
	}
	public void setDay1Low(BigDecimal day1Low) {
		this.day1Low = day1Low;
	}
	public BigDecimal getDay2High() {
		return day2High;
	}
	public void setDay2High(BigDecimal day2High) {
		this.day2High = day2High;
	}
	public BigDecimal getDay2Low() {
		return day2Low;
	}
	public void setDay2Low(BigDecimal day2Low) {
		this.day2Low = day2Low;
	}

	public BigDecimal getDay3High() {
		return day3High;
	}
	public void setDay3High(BigDecimal day3High) {
		this.day3High = day3High;
	}
	public BigDecimal getDay3Low() {
		return day3Low;
	}
	public void setDay3Low(BigDecimal day3Low) {
		this.day3Low = day3Low;
	}
	public BigDecimal getDay4High() {
		return day4High;
	}
	public void setDay4High(BigDecimal day4High) {
		this.day4High = day4High;
	}
	public BigDecimal getDay4Low() {
		return day4Low;
	}
	public void setDay4Low(BigDecimal day4Low) {
		this.day4Low = day4Low;
	}
	public BigDecimal getDay5High() {
		return day5High;
	}
	public void setDay5High(BigDecimal day5High) {
		this.day5High = day5High;
	}
	public BigDecimal getDay5Low() {
		return day5Low;
	}
	public void setDay5Low(BigDecimal day5Low) {
		this.day5Low = day5Low;
	}
	public BigDecimal getDay6High() {
		return day6High;
	}
	public void setDay6High(BigDecimal day6High) {
		this.day6High = day6High;
	}
	public BigDecimal getDay6Low() {
		return day6Low;
	}
	public void setDay6Low(BigDecimal day6Low) {
		this.day6Low = day6Low;
	}
	public BigDecimal getDay7High() {
		return day7High;
	}
	public void setDay7High(BigDecimal day7High) {
		this.day7High = day7High;
	}
	public BigDecimal getDay7Low() {
		return day7Low;
	}
	public void setDay7Low(BigDecimal day7Low) {
		this.day7Low = day7Low;
	}
	public Boolean getNarrow7Rule() {
		return narrow7Rule;
	}
	public void setNarrow7Rule(Boolean narrow7Rule) {
		this.narrow7Rule = narrow7Rule;
	}

	private String buyStatus = "NA";
	private String sellStatus = "NA";
	//private Boolean canTradableToday = Boolean.TRUE;
	public double getBuyValue() {
		return getScaledValue(day1High);
	}
	public double getSellValue() {
		return getScaledValue(day1Low);
	}
	public double getBuyValueTarget33() {
		return getScaledVal(getBuyValue() * 0.0033);
	}
	public double getSellValueTarget33() {
		return getScaledVal(getSellValue() * 0.0033);
	}
	public double getBuyValueTarget() {
		return getScaledVal(getBuyValue() + getBuyValueTarget33());
	}
	public double getSellValueTarget() {
		return getScaledVal(getSellValue() - getSellValueTarget33());
	}
	public String getBuyStatus() {
		return buyStatus;
	}
	public void setBuyStatus(String buyStatus) {
		this.buyStatus = buyStatus;
	}
	public String getSellStatus() {
		return sellStatus;
	}
	public void setSellStatus(String sellStatus) {
		this.sellStatus = sellStatus;
	}
	/*public Boolean getCanTradableToday() {
		return canTradableToday;
	}
	public void setCanTradableToday(Boolean canTradableToday) {
		this.canTradableToday = canTradableToday;
	}*/

	// TODO - temp
	public double getDay1HighLowDiff() {
		return getScaledVal(day1High.doubleValue() -day1Low.doubleValue());
	}
	public double getDay2HighLowDiff() {
		return getScaledVal(day2High.doubleValue() -day2Low.doubleValue());
	}
	public double getDay3HighLowDiff() {
		return getScaledVal(day3High.doubleValue() -day3Low.doubleValue());
	}
	public double getDay4HighLowDiff() {
		return getScaledVal(day4High.doubleValue() -day4Low.doubleValue());
	}
	public double getDay5HighLowDiff() {
		return getScaledVal(day5High.doubleValue() -day5Low.doubleValue());
	}
	public double getDay6HighLowDiff() {
		return getScaledVal(day6High.doubleValue() -day6Low.doubleValue());
	}
	public double getDay7HighLowDiff() {
		return getScaledVal(day7High.doubleValue() -day7Low.doubleValue());
	}
	
	public String getTradableState() {
		String canTradableState = "NA";
		if (getScaledValue(getLtp()) > getScaledValue(day1High))  {
			canTradableState = "BUY";
		}  else if (getScaledValue(getLtp()) < getScaledValue(day1Low))  {
			canTradableState = "SELL";
		}
		return canTradableState;
	}
}