package com.tradeware.stockmarket.bean;

public class StrategyOrbPreviousDayHistDataBean extends StrategyOrbVwapAndVolumeInfoDataBean {

	private static final long serialVersionUID = -7603993932558329696L;

	private Integer prevDayHistId;
	private Double prevDayHrBuyAtVal;
	private Double prevDayHrSellAtVal;
	private Boolean prevDayHrCrossInd;
	private Double prevDayHigh;
	private Double prevDayLow;
	private Boolean prevDayLevelCrossInd;
	private Double prevDayPercentageChange;
	private String candleTypeTrendId;
	private String candleTypeTrendDescription;
	
	public StrategyOrbPreviousDayHistDataBean clone(StrategyOrbPreviousDayHistDataBean bean) {
		super.clone(bean);
		bean.setPrevDayHistId(this.prevDayHistId);
		bean.setPrevDayHrBuyAtVal(this.prevDayHrBuyAtVal);
		bean.setPrevDayHrSellAtVal(this.prevDayHrSellAtVal);
		bean.setPrevDayHrCrossInd(this.prevDayHrCrossInd);
		bean.setPrevDayHigh(this.prevDayHigh);
		bean.setPrevDayLow(this.prevDayLow);
		bean.setPrevDayLevelCrossInd(this.prevDayLevelCrossInd);
		bean.setPrevDayPercentageChange(this.prevDayPercentageChange);
		bean.setCandleTypeTrendId(this.candleTypeTrendId);
		bean.setCandleTypeTrendDescription(this.candleTypeTrendDescription);
		return bean; 
	}
	public Integer getPrevDayHistId() {
		return prevDayHistId;
	}
	public void setPrevDayHistId(Integer prevDayHistId) {
		this.prevDayHistId = prevDayHistId;
	}
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
	public Boolean getPrevDayLevelCrossInd() {
		return prevDayLevelCrossInd;
	}
	public void setPrevDayLevelCrossInd(Boolean prevDayLevelCrossInd) {
		this.prevDayLevelCrossInd = prevDayLevelCrossInd;
	}
	public Double getPrevDayPercentageChange() {
		return prevDayPercentageChange;
	}
	public void setPrevDayPercentageChange(Double prevDayPercentageChange) {
		this.prevDayPercentageChange = prevDayPercentageChange;
	}
	public String getCandleTypeTrendId() {
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
	}
	@Override
	public String toString() {
		return "StrategyOrbPreviousDayHistDataBean [prevDayHistId=" + prevDayHistId + ", prevDayHrBuyAtVal="
				+ prevDayHrBuyAtVal + ", prevDayHrSellAtVal=" + prevDayHrSellAtVal + ", prevDayHrCrossInd="
				+ prevDayHrCrossInd + ", prevDayHigh=" + prevDayHigh + ", prevDayLow=" + prevDayLow
				+ ", prevDayLevelCrossInd=" + prevDayLevelCrossInd + ", prevDayPercentageChange="
				+ prevDayPercentageChange + ", candleTypeTrendId=" + candleTypeTrendId + ", candleTypeTrendDescription="
				+ candleTypeTrendDescription + "]"  +"\r\n"+super.toString();
	}
}
