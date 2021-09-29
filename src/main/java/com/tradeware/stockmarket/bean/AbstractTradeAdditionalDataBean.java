package com.tradeware.stockmarket.bean;

public abstract class AbstractTradeAdditionalDataBean extends StrategyOrbPreviousCandleOHLCDataBean {
	private static final long serialVersionUID = -394384230418549378L;

	private Double trailingStopLoss250;
	private Double trailingStopLoss1500;
	private Double trailingStopLoss2000;
	private Double trailingStopLoss2500;
	private Double trailingStopLoss3000;
	private Double trailingStopLoss3500;
	private Double trailingStopLoss4000;
	private Double trailingStopLoss4500;
	
	private Double trailingStopLoss5000;
	private Double trailingStopLoss6000;
	private Double trailingStopLoss7500;
	private Double trailingStopLoss10000;
	private Double trailingStopLoss12500;
	
	public AbstractTradeAdditionalDataBean clone(AbstractTradeAdditionalDataBean bean) {
		super.clone(bean);
		bean.setTrailingStopLoss250(this.trailingStopLoss250);
		bean.setTrailingStopLoss1500(this.trailingStopLoss1500);
		bean.setTrailingStopLoss2000(this.trailingStopLoss2000);
		bean.setTrailingStopLoss2500(this.trailingStopLoss2500);
		bean.setTrailingStopLoss3000(this.trailingStopLoss3000);
		bean.setTrailingStopLoss3500(this.trailingStopLoss3500);
		bean.setTrailingStopLoss4000(this.trailingStopLoss4000);
		bean.setTrailingStopLoss4500(this.trailingStopLoss4500);
		bean.setTrailingStopLoss5000(this.trailingStopLoss5000);
		bean.setTrailingStopLoss6000(this.trailingStopLoss6000);
		bean.setTrailingStopLoss7500(this.trailingStopLoss7500);
		bean.setTrailingStopLoss10000(this.trailingStopLoss10000);
		bean.setTrailingStopLoss12500(this.trailingStopLoss12500);
		return bean;
	}

	public Double getTrailingStopLoss250() {
		return trailingStopLoss250;
	}
	public void setTrailingStopLoss250(Double trailingStopLoss250) {
		this.trailingStopLoss250 = trailingStopLoss250;
	}
	public Double getTrailingStopLoss1500() {
		return trailingStopLoss1500;
	}
	public void setTrailingStopLoss1500(Double trailingStopLoss1500) {
		this.trailingStopLoss1500 = trailingStopLoss1500;
	}
	public Double getTrailingStopLoss2000() {
		return trailingStopLoss2000;
	}
	public void setTrailingStopLoss2000(Double trailingStopLoss2000) {
		this.trailingStopLoss2000 = trailingStopLoss2000;
	}
	public Double getTrailingStopLoss2500() {
		return trailingStopLoss2500;
	}
	public void setTrailingStopLoss2500(Double trailingStopLoss2500) {
		this.trailingStopLoss2500 = trailingStopLoss2500;
	}
	public Double getTrailingStopLoss3000() {
		return trailingStopLoss3000;
	}
	public void setTrailingStopLoss3000(Double trailingStopLoss3000) {
		this.trailingStopLoss3000 = trailingStopLoss3000;
	}
	public Double getTrailingStopLoss3500() {
		return trailingStopLoss3500;
	}
	public void setTrailingStopLoss3500(Double trailingStopLoss3500) {
		this.trailingStopLoss3500 = trailingStopLoss3500;
	}
	public Double getTrailingStopLoss4000() {
		return trailingStopLoss4000;
	}
	public void setTrailingStopLoss4000(Double trailingStopLoss4000) {
		this.trailingStopLoss4000 = trailingStopLoss4000;
	}
	public Double getTrailingStopLoss4500() {
		return trailingStopLoss4500;
	}
	public void setTrailingStopLoss4500(Double trailingStopLoss4500) {
		this.trailingStopLoss4500 = trailingStopLoss4500;
	}
	public Double getTrailingStopLoss5000() {
		return trailingStopLoss5000;
	}
	public void setTrailingStopLoss5000(Double trailingStopLoss5000) {
		this.trailingStopLoss5000 = trailingStopLoss5000;
	}
	public Double getTrailingStopLoss6000() {
		return trailingStopLoss6000;
	}
	public void setTrailingStopLoss6000(Double trailingStopLoss6000) {
		this.trailingStopLoss6000 = trailingStopLoss6000;
	}
	public Double getTrailingStopLoss7500() {
		return trailingStopLoss7500;
	}
	public void setTrailingStopLoss7500(Double trailingStopLoss7500) {
		this.trailingStopLoss7500 = trailingStopLoss7500;
	}
	public Double getTrailingStopLoss10000() {
		return trailingStopLoss10000;
	}
	public void setTrailingStopLoss10000(Double trailingStopLoss10000) {
		this.trailingStopLoss10000 = trailingStopLoss10000;
	}
	public Double getTrailingStopLoss12500() {
		return trailingStopLoss12500;
	}
	public void setTrailingStopLoss12500(Double trailingStopLoss12500) {
		this.trailingStopLoss12500 = trailingStopLoss12500;
	}

	@Override
	public String toString() {
		return "AbstractTradeAdditionalDataBean [trailingStopLoss1500=" + trailingStopLoss1500
				+ ", trailingStopLoss2000=" + trailingStopLoss2000 + ", trailingStopLoss2500=" + trailingStopLoss2500
				+ ", trailingStopLoss3000=" + trailingStopLoss3000 + ", trailingStopLoss3500=" + trailingStopLoss3500
				+ ", trailingStopLoss4000=" + trailingStopLoss4000 + ", trailingStopLoss4500=" + trailingStopLoss4500
				+ ", trailingStopLoss5000=" + trailingStopLoss5000 + ", trailingStopLoss7500=" + trailingStopLoss7500
				+ ", trailingStopLoss10000=" + trailingStopLoss10000 + ", trailingStopLoss12500="
				+ trailingStopLoss12500 + "]" +"\r\n"+super.toString();
	}
}
