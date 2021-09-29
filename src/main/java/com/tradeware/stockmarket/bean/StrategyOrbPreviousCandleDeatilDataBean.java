package com.tradeware.stockmarket.bean;

public class StrategyOrbPreviousCandleDeatilDataBean  extends StrategyOrbPreviousDayHistDataBean {

	private static final long serialVersionUID = 237667789096867936L;
	
	protected Integer previousCandleDeatilId;
	protected Double candle1SizeAmt;
	protected Double candle2SizeAmt;
	protected Double candle3SizeAmt;
	protected Double candle4SizeAmt;
	protected Double candleHighsDiff;
	protected Double candleLowsDiff;
	protected Double candle1HighMinusClose;
	protected Double candle1CloseMinusLow;
	protected Double candle2HighMinusClose;
	protected Double candle2CloseMinusLow;
	protected Double candle3HighMinusClose;
	protected Double candle3CloseMinusLow;
	protected Double candle4HighMinusClose;
	protected Double candle4CloseMinusLow;
	protected String candle1Type;
	protected String candle2Type;
	protected String candle3Type;
	protected String candle4Type;
	
	public StrategyOrbPreviousCandleDeatilDataBean clone(StrategyOrbPreviousCandleDeatilDataBean bean) {
		super.clone(bean);
		bean.setPreviousCandleDeatilId(this.previousCandleDeatilId); 
		bean.setCandle1SizeAmt(this.candle1SizeAmt);
		bean.setCandle2SizeAmt(this.candle2SizeAmt);
		bean.setCandle3SizeAmt(this.candle3SizeAmt);
		bean.setCandle4SizeAmt(this.candle4SizeAmt);
		bean.setCandleHighsDiff(this.candleHighsDiff);
		bean.setCandleLowsDiff(this.candleLowsDiff);
		bean.setCandle1HighMinusClose(this.candle1HighMinusClose);
		bean.setCandle1CloseMinusLow(this.candle1CloseMinusLow);
		bean.setCandle2HighMinusClose(this.candle2HighMinusClose); 
		bean.setCandle2CloseMinusLow(this.candle2CloseMinusLow);
		bean.setCandle3HighMinusClose(this.candle3HighMinusClose);
		bean.setCandle3CloseMinusLow(this.candle3CloseMinusLow);
		bean.setCandle4HighMinusClose(this.candle4HighMinusClose);
		bean.setCandle4CloseMinusLow(this.candle4CloseMinusLow);
		bean.setCandle1Type(this.candle1Type);
		bean.setCandle2Type(this.candle2Type);
		bean.setCandle3Type(this.candle3Type);
		bean.setCandle4Type(this.candle4Type);
		return bean; 
	}
	
	
	public Integer getPreviousCandleDeatilId() {
		return previousCandleDeatilId;
	}
	public void setPreviousCandleDeatilId(Integer previousCandleDeatilId) {
		this.previousCandleDeatilId = previousCandleDeatilId;
	}
	public Double getCandle1SizeAmt() {
		return candle1SizeAmt;
	}
	public void setCandle1SizeAmt(Double candle1SizeAmt) {
		this.candle1SizeAmt = candle1SizeAmt;
	}
	public Double getCandle2SizeAmt() {
		return candle2SizeAmt;
	}
	public void setCandle2SizeAmt(Double candle2SizeAmt) {
		this.candle2SizeAmt = candle2SizeAmt;
	}
	public Double getCandle3SizeAmt() {
		return candle3SizeAmt;
	}
	public void setCandle3SizeAmt(Double candle3SizeAmt) {
		this.candle3SizeAmt = candle3SizeAmt;
	}
	public Double getCandle4SizeAmt() {
		return candle4SizeAmt;
	}
	public void setCandle4SizeAmt(Double candle4SizeAmt) {
		this.candle4SizeAmt = candle4SizeAmt;
	}
	public Double getCandleHighsDiff() {
		return candleHighsDiff;
	}
	public void setCandleHighsDiff(Double candleHighsDiff) {
		this.candleHighsDiff = candleHighsDiff;
	}
	public Double getCandleLowsDiff() {
		return candleLowsDiff;
	}
	public void setCandleLowsDiff(Double candleLowsDiff) {
		this.candleLowsDiff = candleLowsDiff;
	}
	public Double getCandle1HighMinusClose() {
		return candle1HighMinusClose;
	}
	public void setCandle1HighMinusClose(Double candle1HighMinusClose) {
		this.candle1HighMinusClose = candle1HighMinusClose;
	}
	public Double getCandle1CloseMinusLow() {
		return candle1CloseMinusLow;
	}
	public void setCandle1CloseMinusLow(Double candle1CloseMinusLow) {
		this.candle1CloseMinusLow = candle1CloseMinusLow;
	}
	public Double getCandle2HighMinusClose() {
		return candle2HighMinusClose;
	}
	public void setCandle2HighMinusClose(Double candle2HighMinusClose) {
		this.candle2HighMinusClose = candle2HighMinusClose;
	}
	public Double getCandle2CloseMinusLow() {
		return candle2CloseMinusLow;
	}
	public void setCandle2CloseMinusLow(Double candle2CloseMinusLow) {
		this.candle2CloseMinusLow = candle2CloseMinusLow;
	}
	public Double getCandle3HighMinusClose() {
		return candle3HighMinusClose;
	}
	public void setCandle3HighMinusClose(Double candle3HighMinusClose) {
		this.candle3HighMinusClose = candle3HighMinusClose;
	}
	public Double getCandle3CloseMinusLow() {
		return candle3CloseMinusLow;
	}
	public void setCandle3CloseMinusLow(Double candle3CloseMinusLow) {
		this.candle3CloseMinusLow = candle3CloseMinusLow;
	}
	public Double getCandle4HighMinusClose() {
		return candle4HighMinusClose;
	}
	public void setCandle4HighMinusClose(Double candle4HighMinusClose) {
		this.candle4HighMinusClose = candle4HighMinusClose;
	}
	public Double getCandle4CloseMinusLow() {
		return candle4CloseMinusLow;
	}
	public void setCandle4CloseMinusLow(Double candle4CloseMinusLow) {
		this.candle4CloseMinusLow = candle4CloseMinusLow;
	}
	public String getCandle1Type() {
		return candle1Type;
	}
	public void setCandle1Type(String candle1Type) {
		this.candle1Type = candle1Type;
	}
	public String getCandle2Type() {
		return candle2Type;
	}
	public void setCandle2Type(String candle2Type) {
		this.candle2Type = candle2Type;
	}
	public String getCandle3Type() {
		return candle3Type;
	}
	public void setCandle3Type(String candle3Type) {
		this.candle3Type = candle3Type;
	}
	public String getCandle4Type() {
		return candle4Type;
	}
	public void setCandle4Type(String candle4Type) {
		this.candle4Type = candle4Type;
	}
	@Override
	public String toString() {
		return "StrategyOrbPreviousCandleDeatilDataBean [previousCandleDeatilId=" + previousCandleDeatilId
				+ ", candle1SizeAmt=" + candle1SizeAmt + ", candle2SizeAmt=" + candle2SizeAmt + ", candle3SizeAmt="
				+ candle3SizeAmt + ", candle4SizeAmt=" + candle4SizeAmt + ", candleHighsDiff=" + candleHighsDiff
				+ ", candleLowsDiff=" + candleLowsDiff + ", candle1HighMinusClose=" + candle1HighMinusClose
				+ ", candle1CloseMinusLow=" + candle1CloseMinusLow + ", candle2HighMinusClose=" + candle2HighMinusClose
				+ ", candle2CloseMinusLow=" + candle2CloseMinusLow + ", candle3HighMinusClose=" + candle3HighMinusClose
				+ ", candle3CloseMinusLow=" + candle3CloseMinusLow + ", candle4HighMinusClose=" + candle4HighMinusClose
				+ ", candle4CloseMinusLow=" + candle4CloseMinusLow + ", candle1Type=" + candle1Type + ", candle2Type="
				+ candle2Type + ", candle3Type=" + candle3Type + ", candle4Type=" + candle4Type + "]" +"\r\n"+super.toString();
	}
	
	
}
