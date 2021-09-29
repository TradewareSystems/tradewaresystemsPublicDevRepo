package com.tradeware.stockmarket.bean;

import java.util.Date;

public class StrategyOrbMultilevelTradingDataBean extends StrategyOrbTradingRuleBean {

	private static final long serialVersionUID = -7146204625586485599L;
	private Integer multiLevelTradeId;
	private Integer tradedLotCount;//In other entity also
	private Double tradedAtAvgVal;
	private Double targetAmtVal;
	private Double tradedAtVal2;
	private Date tradedAtDtTm2;
	private Double targetAmtVal2;
	private Double buyerSellerDiffVal2;
	private Double tradedAtVal3;
	private Date tradedAtDtTm3;
	private Double targetAmtVal3;
	private Double buyerSellerDiffVal3;
	
	public StrategyOrbMultilevelTradingDataBean clone(StrategyOrbMultilevelTradingDataBean bean) {
		super.clone(bean);
		bean.setMultiLevelTradeId(this.multiLevelTradeId);
		bean.setTradedLotCount(this.tradedLotCount); //In other entity also
		bean.setTradedAtAvgVal(this.tradedAtAvgVal);
		bean.setTargetAmtVal(this.targetAmtVal);
		bean.setTradedAtVal2(this.tradedAtVal2);
		bean.setTradedAtDtTm2(this.tradedAtDtTm2);
		bean.setTargetAmtVal2(this.targetAmtVal2);
		bean.setBuyerSellerDiffVal2(this.buyerSellerDiffVal2);
		bean.setTradedAtVal3(this.tradedAtVal3);
		bean.setTradedAtDtTm3(this.tradedAtDtTm3);
		bean.setTargetAmtVal3(this.targetAmtVal3);
		bean.setBuyerSellerDiffVal3(this.buyerSellerDiffVal3);
		return bean; 
	}
	
	public Integer getMultiLevelTradeId() {
		return multiLevelTradeId;
	}
	public void setMultiLevelTradeId(Integer multiLevelTradeId) {
		this.multiLevelTradeId = multiLevelTradeId;
	}
	public Integer getTradedLotCount() {
		return tradedLotCount;
	}
	public void setTradedLotCount(Integer tradedLotCount) {
		this.tradedLotCount = tradedLotCount;
	}
	public Double getTradedAtAvgVal() {
		return tradedAtAvgVal;
	}
	public void setTradedAtAvgVal(Double tradedAtAvgVal) {
		this.tradedAtAvgVal = tradedAtAvgVal;
	}
	public Double getTargetAmtVal() {
		return targetAmtVal;
	}
	public void setTargetAmtVal(Double targetAmtVal) {
		this.targetAmtVal = targetAmtVal;
	}
	public Double getTradedAtVal2() {
		return tradedAtVal2;
	}
	public void setTradedAtVal2(Double tradedAtVal2) {
		this.tradedAtVal2 = tradedAtVal2;
	}
	public Date getTradedAtDtTm2() {
		return tradedAtDtTm2;
	}
	public void setTradedAtDtTm2(Date tradedAtDtTm2) {
		this.tradedAtDtTm2 = tradedAtDtTm2;
	}
	public Double getTargetAmtVal2() {
		return targetAmtVal2;
	}
	public void setTargetAmtVal2(Double targetAmtVal2) {
		this.targetAmtVal2 = targetAmtVal2;
	}
	public Double getBuyerSellerDiffVal2() {
		return buyerSellerDiffVal2;
	}
	public void setBuyerSellerDiffVal2(Double buyerSellerDiffVal2) {
		this.buyerSellerDiffVal2 = buyerSellerDiffVal2;
	}
	public Double getTradedAtVal3() {
		return tradedAtVal3;
	}
	public void setTradedAtVal3(Double tradedAtVal3) {
		this.tradedAtVal3 = tradedAtVal3;
	}
	public Date getTradedAtDtTm3() {
		return tradedAtDtTm3;
	}
	public void setTradedAtDtTm3(Date tradedAtDtTm3) {
		this.tradedAtDtTm3 = tradedAtDtTm3;
	}
	public Double getTargetAmtVal3() {
		return targetAmtVal3;
	}
	public void setTargetAmtVal3(Double targetAmtVal3) {
		this.targetAmtVal3 = targetAmtVal3;
	}
	public Double getBuyerSellerDiffVal3() {
		return buyerSellerDiffVal3;
	}
	public void setBuyerSellerDiffVal3(Double buyerSellerDiffVal3) {
		this.buyerSellerDiffVal3 = buyerSellerDiffVal3;
	}
	@Override
	public String toString() {
		return "StrategyOrbMultilevelTradingDataBean [multiLevelTradeId=" + multiLevelTradeId + ", tradedLotCount="
				+ tradedLotCount + ", tradedAtAvgVal=" + tradedAtAvgVal + ", targetAmtVal=" + targetAmtVal
				+ ", tradedAtVal2=" + tradedAtVal2 + ", tradedAtDtTm2=" + tradedAtDtTm2 + ", targetAmtVal2="
				+ targetAmtVal2 + ", buyerSellerDiffVal2=" + buyerSellerDiffVal2 + ", tradedAtVal3=" + tradedAtVal3
				+ ", tradedAtDtTm3=" + tradedAtDtTm3 + ", targetAmtVal3=" + targetAmtVal3 + ", buyerSellerDiffVal3="
				+ buyerSellerDiffVal3 + "]" +"\r\n"+super.toString();
	}
}
