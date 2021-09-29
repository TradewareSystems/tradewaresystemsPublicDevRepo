package com.tradeware.stockmarket.bean;

public class StrategyOrbTechnicalIndicatorBean extends StrategyOrbStochasticDataBean {

	private static final long serialVersionUID = 1001208169871511080L;
	// CPR values
	private Double cprLowerBound;
	private Double cprPivotalPoint;
	private Double cprUpperBound;
	private Double valueCPR;
	
	public StrategyOrbTechnicalIndicatorBean clone(StrategyOrbTechnicalIndicatorBean bean) {
		bean.setCprLowerBound(this.cprLowerBound);
		bean.setCprPivotalPoint(this.cprPivotalPoint);
		bean.setCprUpperBound(this.cprUpperBound);
		bean.setValueCPR(this.valueCPR);
		return bean; 
	}

	public Double getCprLowerBound() {
		return cprLowerBound;
	}
	public void setCprLowerBound(Double cprLowerBound) {
		this.cprLowerBound = cprLowerBound;
	}
	public Double getCprPivotalPoint() {
		return cprPivotalPoint;
	}
	public void setCprPivotalPoint(Double cprPivotalPoint) {
		this.cprPivotalPoint = cprPivotalPoint;
	}
	public Double getCprUpperBound() {
		return cprUpperBound;
	}
	public void setCprUpperBound(Double cprUpperBound) {
		this.cprUpperBound = cprUpperBound;
	}
	public Double getValueCPR() {
		return valueCPR;
	}
	public void setValueCPR(Double valueCPR) {
		this.valueCPR = valueCPR;
	}
}