package com.tradeware.stockmarket.bean;

public class StrategyOrbStochasticDataBean extends StrategyOrbHeikinAshiDataBean {

	private static final long serialVersionUID = 1001208169871511080L;
	private Double min60StochasticValK1;
	private Double min60StochasticValD3;
	private Double min60StochasticValK2;
	private Double min60StochasticValD4;
	private Double min60StochasticValK3;
	private Double min60StochasticValD5;
	private String min60StochasticTrend;

	private Double min15StochasticValK1;
	private Double min15StochasticValD3;
	private Double min15StochasticValK2;
	private Double min15StochasticValD4;
	private Double min15StochasticValK3;
	private Double min15StochasticValD5;
	private String min15StochasticTrend;

	private Double min5StochasticValK1;
	private Double min5StochasticValD3;
	private Double min5StochasticValK2;
	private Double min5StochasticValD4;
	private Double min5StochasticValK3;
	private Double min5StochasticValD5;
	private String min5StochasticTrend;
	
	private Double rsi1;
	private Double rsi2;
	private Double rsi3;
	private String min60RsiTrend;
	
	protected String min5HeikinAshiTrendId;
	protected String min15HeikinAshiTrendId;
	protected String min60HeikinAshiTrendId;
	
	public StrategyOrbStochasticDataBean clone(StrategyOrbStochasticDataBean bean) {
		/*bean.setCprLowerBound(this.cprLowerBound);
		bean.setCprPivotalPoint(this.cprPivotalPoint);
		bean.setCprUpperBound(this.cprUpperBound);
		bean.setValueCPR(this.valueCPR);*/
		return bean; 
	}

	public Double getMin60StochasticValK1() {
		return min60StochasticValK1;
	}

	public void setMin60StochasticValK1(Double min60StochasticValK1) {
		this.min60StochasticValK1 = min60StochasticValK1;
	}

	public Double getMin60StochasticValD3() {
		return min60StochasticValD3;
	}

	public void setMin60StochasticValD3(Double min60StochasticValD3) {
		this.min60StochasticValD3 = min60StochasticValD3;
	}

	public Double getMin60StochasticValK2() {
		return min60StochasticValK2;
	}

	public void setMin60StochasticValK2(Double min60StochasticValK2) {
		this.min60StochasticValK2 = min60StochasticValK2;
	}

	public Double getMin60StochasticValD4() {
		return min60StochasticValD4;
	}

	public void setMin60StochasticValD4(Double min60StochasticValD4) {
		this.min60StochasticValD4 = min60StochasticValD4;
	}

	public Double getMin60StochasticValK3() {
		return min60StochasticValK3;
	}

	public void setMin60StochasticValK3(Double min60StochasticValK3) {
		this.min60StochasticValK3 = min60StochasticValK3;
	}

	public Double getMin60StochasticValD5() {
		return min60StochasticValD5;
	}

	public void setMin60StochasticValD5(Double min60StochasticValD5) {
		this.min60StochasticValD5 = min60StochasticValD5;
	}

	public String getMin60StochasticTrend() {
		return min60StochasticTrend;
	}

	public void setMin60StochasticTrend(String min60StochasticTrend) {
		this.min60StochasticTrend = min60StochasticTrend;
	}

	public Double getMin15StochasticValK1() {
		return min15StochasticValK1;
	}

	public void setMin15StochasticValK1(Double min15StochasticValK1) {
		this.min15StochasticValK1 = min15StochasticValK1;
	}

	public Double getMin15StochasticValD3() {
		return min15StochasticValD3;
	}

	public void setMin15StochasticValD3(Double min15StochasticValD3) {
		this.min15StochasticValD3 = min15StochasticValD3;
	}

	public Double getMin15StochasticValK2() {
		return min15StochasticValK2;
	}

	public void setMin15StochasticValK2(Double min15StochasticValK2) {
		this.min15StochasticValK2 = min15StochasticValK2;
	}

	public Double getMin15StochasticValD4() {
		return min15StochasticValD4;
	}

	public void setMin15StochasticValD4(Double min15StochasticValD4) {
		this.min15StochasticValD4 = min15StochasticValD4;
	}

	public Double getMin15StochasticValK3() {
		return min15StochasticValK3;
	}

	public void setMin15StochasticValK3(Double min15StochasticValK3) {
		this.min15StochasticValK3 = min15StochasticValK3;
	}

	public Double getMin15StochasticValD5() {
		return min15StochasticValD5;
	}

	public void setMin15StochasticValD5(Double min15StochasticValD5) {
		this.min15StochasticValD5 = min15StochasticValD5;
	}

	public String getMin15StochasticTrend() {
		return min15StochasticTrend;
	}

	public void setMin15StochasticTrend(String min15StochasticTrend) {
		this.min15StochasticTrend = min15StochasticTrend;
	}

	public Double getMin5StochasticValK1() {
		return min5StochasticValK1;
	}

	public void setMin5StochasticValK1(Double min5StochasticValK1) {
		this.min5StochasticValK1 = min5StochasticValK1;
	}

	public Double getMin5StochasticValD3() {
		return min5StochasticValD3;
	}

	public void setMin5StochasticValD3(Double min5StochasticValD3) {
		this.min5StochasticValD3 = min5StochasticValD3;
	}

	public Double getMin5StochasticValK2() {
		return min5StochasticValK2;
	}

	public void setMin5StochasticValK2(Double min5StochasticValK2) {
		this.min5StochasticValK2 = min5StochasticValK2;
	}

	public Double getMin5StochasticValD4() {
		return min5StochasticValD4;
	}

	public void setMin5StochasticValD4(Double min5StochasticValD4) {
		this.min5StochasticValD4 = min5StochasticValD4;
	}

	public Double getMin5StochasticValK3() {
		return min5StochasticValK3;
	}

	public void setMin5StochasticValK3(Double min5StochasticValK3) {
		this.min5StochasticValK3 = min5StochasticValK3;
	}

	public Double getMin5StochasticValD5() {
		return min5StochasticValD5;
	}

	public void setMin5StochasticValD5(Double min5StochasticValD5) {
		this.min5StochasticValD5 = min5StochasticValD5;
	}

	public String getMin5StochasticTrend() {
		return min5StochasticTrend;
	}

	public void setMin5StochasticTrend(String min5StochasticTrend) {
		this.min5StochasticTrend = min5StochasticTrend;
	}

	public Double getRsi1() {
		return rsi1;
	}

	public void setRsi1(Double rsi1) {
		this.rsi1 = rsi1;
	}

	public Double getRsi2() {
		return rsi2;
	}

	public void setRsi2(Double rsi2) {
		this.rsi2 = rsi2;
	}

	public Double getRsi3() {
		return rsi3;
	}

	public void setRsi3(Double rsi3) {
		this.rsi3 = rsi3;
	}

	public String getMin60RsiTrend() {
		return min60RsiTrend;
	}

	public void setMin60RsiTrend(String min60RsiTrend) {
		this.min60RsiTrend = min60RsiTrend;
	}

	public String getMin5HeikinAshiTrendId() {
		return min5HeikinAshiTrendId;
	}

	public void setMin5HeikinAshiTrendId(String min5HeikinAshiTrendId) {
		this.min5HeikinAshiTrendId = min5HeikinAshiTrendId;
	}

	public String getMin15HeikinAshiTrendId() {
		return min15HeikinAshiTrendId;
	}

	public void setMin15HeikinAshiTrendId(String min15HeikinAshiTrendId) {
		this.min15HeikinAshiTrendId = min15HeikinAshiTrendId;
	}

	public String getMin60HeikinAshiTrendId() {
		return min60HeikinAshiTrendId;
	}

	public void setMin60HeikinAshiTrendId(String min60HeikinAshiTrendId) {
		this.min60HeikinAshiTrendId = min60HeikinAshiTrendId;
	}
}