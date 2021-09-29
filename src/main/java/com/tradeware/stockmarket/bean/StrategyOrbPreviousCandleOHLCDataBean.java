package com.tradeware.stockmarket.bean;

public abstract class StrategyOrbPreviousCandleOHLCDataBean extends StrategyOrbPreviousCandleAverageHistDataBean {
	
	private static final long serialVersionUID = -4854347594746211083L;
	
	private Integer previousCandleOhlcId;
	private Double openCandle1;
	private Double highCandle1;
	private Double lowCandle1;
	private Double closeCandle1;
	private Double openCandle2;
	private Double highCandle2;
	private Double lowCandle2;
	private Double closeCandle2;
	private Double openCandle3;
	private Double highCandle3;
	private Double lowCandle3;
	private Double closeCandle3;
	private Double openCandle4;
	private Double highCandle4;
	private Double lowCandle4;
	private Double closeCandle4;
	
	public StrategyOrbPreviousCandleOHLCDataBean clone(StrategyOrbPreviousCandleOHLCDataBean bean) {
		super.clone(bean);
		bean.setPreviousCandleOhlcId(this.previousCandleOhlcId);
		bean.setOpenCandle1(this.openCandle1);
		bean.setHighCandle1(this.highCandle1);
		bean.setLowCandle1(this.lowCandle1);
		bean.setCloseCandle1(this.closeCandle1);
		bean.setOpenCandle2(this.openCandle2);
		bean.setHighCandle2(this.highCandle2);
		bean.setLowCandle2(this.lowCandle2);
		bean.setCloseCandle2(this.closeCandle2);
		bean.setOpenCandle3(this.openCandle3);
		bean.setHighCandle3(this.highCandle3);
		bean.setLowCandle3(this.lowCandle3);
		bean.setCloseCandle3(this.closeCandle3);
		bean.setOpenCandle4(this.openCandle4);
		bean.setHighCandle4(this.highCandle4);
		bean.setLowCandle4(this.lowCandle4);
		bean.setCloseCandle4(this.closeCandle4);
		return bean;
	}
	public Integer getPreviousCandleOhlcId() {
		return previousCandleOhlcId;
	}
	public void setPreviousCandleOhlcId(Integer previousCandleOhlcId) {
		this.previousCandleOhlcId = previousCandleOhlcId;
	}
	public Double getOpenCandle1() {
		return openCandle1;
	}
	public void setOpenCandle1(Double openCandle1) {
		this.openCandle1 = openCandle1;
	}
	public Double getHighCandle1() {
		return highCandle1;
	}
	public void setHighCandle1(Double highCandle1) {
		this.highCandle1 = highCandle1;
	}
	public Double getLowCandle1() {
		return lowCandle1;
	}
	public void setLowCandle1(Double lowCandle1) {
		this.lowCandle1 = lowCandle1;
	}
	public Double getCloseCandle1() {
		return closeCandle1;
	}
	public void setCloseCandle1(Double closeCandle1) {
		this.closeCandle1 = closeCandle1;
	}
	public Double getOpenCandle2() {
		return openCandle2;
	}
	public void setOpenCandle2(Double openCandle2) {
		this.openCandle2 = openCandle2;
	}
	public Double getHighCandle2() {
		return highCandle2;
	}
	public void setHighCandle2(Double highCandle2) {
		this.highCandle2 = highCandle2;
	}
	public Double getLowCandle2() {
		return lowCandle2;
	}
	public void setLowCandle2(Double lowCandle2) {
		this.lowCandle2 = lowCandle2;
	}
	public Double getCloseCandle2() {
		return closeCandle2;
	}
	public void setCloseCandle2(Double closeCandle2) {
		this.closeCandle2 = closeCandle2;
	}
	public Double getOpenCandle3() {
		return openCandle3;
	}
	public void setOpenCandle3(Double openCandle3) {
		this.openCandle3 = openCandle3;
	}
	public Double getHighCandle3() {
		return highCandle3;
	}
	public void setHighCandle3(Double highCandle3) {
		this.highCandle3 = highCandle3;
	}
	public Double getLowCandle3() {
		return lowCandle3;
	}
	public void setLowCandle3(Double lowCandle3) {
		this.lowCandle3 = lowCandle3;
	}
	public Double getCloseCandle3() {
		return closeCandle3;
	}
	public void setCloseCandle3(Double closeCandle3) {
		this.closeCandle3 = closeCandle3;
	}
	public Double getOpenCandle4() {
		return openCandle4;
	}
	public void setOpenCandle4(Double openCandle4) {
		this.openCandle4 = openCandle4;
	}
	public Double getHighCandle4() {
		return highCandle4;
	}
	public void setHighCandle4(Double highCandle4) {
		this.highCandle4 = highCandle4;
	}
	public Double getLowCandle4() {
		return lowCandle4;
	}
	public void setLowCandle4(Double lowCandle4) {
		this.lowCandle4 = lowCandle4;
	}
	public Double getCloseCandle4() {
		return closeCandle4;
	}
	public void setCloseCandle4(Double closeCandle4) {
		this.closeCandle4 = closeCandle4;
	}
	@Override
	public String toString() {
		return "StrategyOrbPreviousCandleOHLCDataBean [previousCandleOhlcId=" + previousCandleOhlcId + ", openCandle1="
				+ openCandle1 + ", highCandle1=" + highCandle1 + ", lowCandle1=" + lowCandle1 + ", closeCandle1="
				+ closeCandle1 + ", openCandle2=" + openCandle2 + ", highCandle2=" + highCandle2 + ", lowCandle2="
				+ lowCandle2 + ", closeCandle2=" + closeCandle2 + ", openCandle3=" + openCandle3 + ", highCandle3="
				+ highCandle3 + ", lowCandle3=" + lowCandle3 + ", closeCandle3=" + closeCandle3 + ", openCandle4="
				+ openCandle4 + ", highCandle4=" + highCandle4 + ", lowCandle4=" + lowCandle4 + ", closeCandle4="
				+ closeCandle4 + "]" +"\r\n"+super.toString();
	}
	
	
}