package com.tradeware.stockmarket.bean;

public abstract class StrategyOrbPreviousCandleAverageHistDataBean extends StrategyOrbPreviousCandleDeatilDataBean  {

	private static final long serialVersionUID = 7657046723467733937L;
	protected Integer previousCandleAvgHistId;
	protected Double currentCandleOpen;//In other entity also
	protected Double avgHigh1min;
	protected Double avgLow1min;
	protected Double avgClose1min;
	protected Double avgHighMinusClose1min;
	protected Double avgCloseMinusLow1min;
	protected Double avgHigh5min;
	protected Double avgLow5min;
	protected Double avgClose5min;
	protected Double avgHighMinusClose5min;
	protected Double avgCloseMinusLow5min;
	protected String min5HeikinAshiTrendId;
	protected String min5HeikinAshiTrendIdDescription;
	
	public StrategyOrbPreviousCandleAverageHistDataBean clone(StrategyOrbPreviousCandleAverageHistDataBean bean) {
		super.clone(bean);
		bean.setPreviousCandleAvgHistId(this.previousCandleAvgHistId);
		bean.setCurrentCandleOpen(this.currentCandleOpen); //In other entity also
		bean.setAvgHigh1min(this.avgHigh1min);
		bean.setAvgLow1min(this.avgLow1min);
		bean.setAvgClose1min(this.avgClose1min);
		bean.setAvgHighMinusClose1min(this.avgHighMinusClose1min);
		bean.setAvgCloseMinusLow1min(this.avgCloseMinusLow1min);
		bean.setAvgHigh5min(this.avgHigh5min);
		bean.setAvgLow5min(this.avgLow5min);
		bean.setAvgClose5min(this.avgClose5min);
		bean.setAvgHighMinusClose5min(this.avgHighMinusClose5min);
		bean.setAvgCloseMinusLow5min(this.avgCloseMinusLow5min);
		bean.setMin5HeikinAshiTrendId(this.min5HeikinAshiTrendId);
		bean.setMin5HeikinAshiTrendIdDescription(this.min5HeikinAshiTrendIdDescription);
		return bean; 
	}
	public Integer getPreviousCandleAvgHistId() {
		return previousCandleAvgHistId;
	}
	public void setPreviousCandleAvgHistId(Integer previousCandleAvgHistId) {
		this.previousCandleAvgHistId = previousCandleAvgHistId;
	}
	public Double getCurrentCandleOpen() {
		return currentCandleOpen;
	}
	public void setCurrentCandleOpen(Double currentCandleOpen) {
		this.currentCandleOpen = currentCandleOpen;
	}
	public Double getAvgHigh1min() {
		return avgHigh1min;
	}
	public void setAvgHigh1min(Double avgHigh1min) {
		this.avgHigh1min = avgHigh1min;
	}
	public Double getAvgLow1min() {
		return avgLow1min;
	}
	public void setAvgLow1min(Double avgLow1min) {
		this.avgLow1min = avgLow1min;
	}
	public Double getAvgClose1min() {
		return avgClose1min;
	}
	public void setAvgClose1min(Double avgClose1min) {
		this.avgClose1min = avgClose1min;
	}
	public Double getAvgHighMinusClose1min() {
		return avgHighMinusClose1min;
	}
	public void setAvgHighMinusClose1min(Double avgHighMinusClose1min) {
		this.avgHighMinusClose1min = avgHighMinusClose1min;
	}
	public Double getAvgCloseMinusLow1min() {
		return avgCloseMinusLow1min;
	}
	public void setAvgCloseMinusLow1min(Double avgCloseMinusLow1min) {
		this.avgCloseMinusLow1min = avgCloseMinusLow1min;
	}
	public Double getAvgHigh5min() {
		return avgHigh5min;
	}
	public void setAvgHigh5min(Double avgHigh5min) {
		this.avgHigh5min = avgHigh5min;
	}
	public Double getAvgLow5min() {
		return avgLow5min;
	}
	public void setAvgLow5min(Double avgLow5min) {
		this.avgLow5min = avgLow5min;
	}
	public Double getAvgClose5min() {
		return avgClose5min;
	}
	public void setAvgClose5min(Double avgClose5min) {
		this.avgClose5min = avgClose5min;
	}
	public Double getAvgHighMinusClose5min() {
		return avgHighMinusClose5min;
	}
	public void setAvgHighMinusClose5min(Double avgHighMinusClose5min) {
		this.avgHighMinusClose5min = avgHighMinusClose5min;
	}
	public Double getAvgCloseMinusLow5min() {
		return avgCloseMinusLow5min;
	}
	public void setAvgCloseMinusLow5min(Double avgCloseMinusLow5min) {
		this.avgCloseMinusLow5min = avgCloseMinusLow5min;
	}
	public String getMin5HeikinAshiTrendId() {
		return min5HeikinAshiTrendId;
	}
	public void setMin5HeikinAshiTrendId(String min5HeikinAshiTrendId) {
		this.min5HeikinAshiTrendId = min5HeikinAshiTrendId;
	}
	public String getMin5HeikinAshiTrendIdDescription() {
		return min5HeikinAshiTrendIdDescription;
	}
	public void setMin5HeikinAshiTrendIdDescription(String min5HeikinAshiTrendIdDescription) {
		this.min5HeikinAshiTrendIdDescription = min5HeikinAshiTrendIdDescription;
	}
	@Override
	public String toString() {
		return "StrategyOrbPreviousCandleAverageHistDataBean [previousCandleAvgHistId=" + previousCandleAvgHistId
				+ ", currentCandleOpen=" + currentCandleOpen + ", avgHigh1min=" + avgHigh1min + ", avgLow1min="
				+ avgLow1min + ", avgClose1min=" + avgClose1min + ", avgHighMinusClose1min=" + avgHighMinusClose1min
				+ ", avgCloseMinusLow1min=" + avgCloseMinusLow1min + ", avgHigh5min=" + avgHigh5min + ", avgLow5min="
				+ avgLow5min + ", avgClose5min=" + avgClose5min + ", avgHighMinusClose5min=" + avgHighMinusClose5min
				+ ", avgCloseMinusLow5min=" + avgCloseMinusLow5min + "]" +"\r\n"+super.toString();
	}
	
	
}
