package org.tradeware.stockmarket.bean;

import java.math.BigDecimal;

public class StockDataBean extends AbstractStockDataBean {

	private static final long serialVersionUID = 8558742865827074180L;
	
	private BigDecimal previousClose;
	private BigDecimal firstDayClose;
	private BigDecimal oiPreviousDay;
	private BigDecimal oiToday;
	private BigDecimal change;
	
	private BigDecimal topHigh;
	private BigDecimal topLow;
	
	private BigDecimal turnoverActual;//In rupes
	private BigDecimal turnover;// Turnover (in lacs);
	private BigDecimal settlePrice;
	private Integer numberOfContracts;

	private Integer avgHLC;
	private BigDecimal last5DaysAvgMin;
	private BigDecimal averageContracts;
	private BigDecimal averageTurnover;
	private BigDecimal atpValue;
	private BigDecimal differenceValue;

	// To generate free calls report
	private BigDecimal positionalUptrendValue;
	private BigDecimal positionalDowntrendValue;
	private BigDecimal intradayUptrendValue;
	private BigDecimal intradayDowntrendValue;
	private String previousDayPosSignal = "NA";
	
	private BigDecimal previousLtp;
	private BigDecimal negativeDirectionLtp;
	private BigDecimal negativeDirectionLossAmt;
	
	private BigDecimal previousLow;
	private BigDecimal previousHigh;
	private Boolean tradableVolumeRule;

	public StockDataBean(Integer lotSize, String stockName) {
		super(lotSize, stockName);
	}
	public StockDataBean clone() throws CloneNotSupportedException {
		return (StockDataBean) super.clone();
	}
	public BigDecimal getPreviousClose() {
		return previousClose;
	}

	public void setPreviousClose(BigDecimal previousClose) {
		this.previousClose = previousClose;
	}

	public BigDecimal getFirstDayClose() {
		return firstDayClose;
	}

	public void setFirstDayClose(BigDecimal firstDayClose) {
		this.firstDayClose = firstDayClose;
	}

	public BigDecimal getOiPreviousDay() {
		return oiPreviousDay;
	}

	public void setOiPreviousDay(BigDecimal oiPreviousDay) {
		this.oiPreviousDay = oiPreviousDay;
	}

	public BigDecimal getOiToday() {
		return oiToday;
	}

	public void setOiToday(BigDecimal oiToday) {
		this.oiToday = oiToday;
	}

	public BigDecimal getChange() {
		return change;
	}

	public void setChange(BigDecimal change) {
		this.change = change;
	}

	public BigDecimal getTopHigh() {
		return topHigh;
	}

	public void setTopHigh(BigDecimal topHigh) {
		this.topHigh = topHigh;
	}

	public BigDecimal getTopLow() {
		return topLow;
	}

	public void setTopLow(BigDecimal topLow) {
		this.topLow = topLow;
	}

	public BigDecimal getTurnover() {
		return turnover;
	}

	public void setTurnover(BigDecimal turnover) {
		this.turnover = turnover;
	}

	public BigDecimal getTurnoverActual() {
		return turnoverActual;
	}
	public void setTurnoverActual(BigDecimal turnoverActual) {
		this.turnoverActual = turnoverActual;
	}
	public BigDecimal getSettlePrice() {
		return settlePrice;
	}

	public void setSettlePrice(BigDecimal settlePrice) {
		this.settlePrice = settlePrice;
	}

	public Integer getNumberOfContracts() {
		return numberOfContracts;
	}

	public void setNumberOfContracts(Integer numberOfContracts) {
		this.numberOfContracts = numberOfContracts;
	}

	public Integer getAvgHLC() {
		return avgHLC;
	}

	public void setAvgHLC(Integer avgHLC) {
		this.avgHLC = avgHLC;
	}

	public BigDecimal getLast5DaysAvgMin() {
		return last5DaysAvgMin;
	}

	public void setLast5DaysAvgMin(BigDecimal last5DaysAvgMin) {
		this.last5DaysAvgMin = last5DaysAvgMin;
	}

	public BigDecimal getAverageContracts() {
		return averageContracts;
	}

	public void setAverageContracts(BigDecimal averageContracts) {
		this.averageContracts = averageContracts;
	}

	public BigDecimal getAverageTurnover() {
		return averageTurnover;
	}

	public void setAverageTurnover(BigDecimal averageTurnover) {
		this.averageTurnover = averageTurnover;
	}

	public BigDecimal getAtpValue() {
		return atpValue;
	}

	public void setAtpValue(BigDecimal atpValue) {
		this.atpValue = atpValue;
	}

	public BigDecimal getDifferenceValue() {
		return differenceValue;
	}

	public void setDifferenceValue(BigDecimal differenceValue) {
		this.differenceValue = differenceValue;
	}

	public BigDecimal getPositionalUptrendValue() {
		return positionalUptrendValue;
	}

	public void setPositionalUptrendValue(BigDecimal positionalUptrendValue) {
		this.positionalUptrendValue = positionalUptrendValue;
	}

	public BigDecimal getPositionalDowntrendValue() {
		return positionalDowntrendValue;
	}

	public void setPositionalDowntrendValue(BigDecimal positionalDowntrendValue) {
		this.positionalDowntrendValue = positionalDowntrendValue;
	}

	public BigDecimal getIntradayUptrendValue() {
		return intradayUptrendValue;
	}

	public void setIntradayUptrendValue(BigDecimal intradayUptrendValue) {
		this.intradayUptrendValue = intradayUptrendValue;
	}

	public BigDecimal getIntradayDowntrendValue() {
		return intradayDowntrendValue;
	}

	public void setIntradayDowntrendValue(BigDecimal intradayDowntrendValue) {
		this.intradayDowntrendValue = intradayDowntrendValue;
	}

	public String getPreviousDayPosSignal() {
		return previousDayPosSignal;
	}

	public void setPreviousDayPosSignal(String previousDayPosSignal) {
		this.previousDayPosSignal = previousDayPosSignal;
	}

	public double getPreviousLtpAsDouble() {
		return getScaledValue(previousLtp);
	}

	public void setPreviousLtp(BigDecimal previousLtp) {
		this.previousLtp = previousLtp;
	}
	
	public BigDecimal getNegativeDirectionLtp() {
		return negativeDirectionLtp;
	}

	public void setNegativeDirectionLtp(BigDecimal negativeDirectionLtp) {
		this.negativeDirectionLtp = negativeDirectionLtp;
	}

	public BigDecimal getNegativeDirectionLossAmt() {
		return negativeDirectionLossAmt;
	}

	public void setNegativeDirectionLossAmt(BigDecimal negativeDirectionLossAmt) {
		this.negativeDirectionLossAmt = negativeDirectionLossAmt;
	}
	
	public double getNegativeDirectionLtpAsDouble() {
		return getScaledValue(negativeDirectionLtp);
	}
	
	public double getNegativeDirectionLossAmtAsDouble() {
		return getScaledValue(negativeDirectionLossAmt);
	}
	public BigDecimal getPreviousLow() {
		return previousLow;
	}
	public void setPreviousLow(BigDecimal previousLow) {
		this.previousLow = previousLow;
	}
	public BigDecimal getPreviousHigh() {
		return previousHigh;
	}
	public void setPreviousHigh(BigDecimal previousHigh) {
		this.previousHigh = previousHigh;
	}
	public Boolean getTradableVolumeRule() {
		return tradableVolumeRule;
	}
	public void setTradableVolumeRule(Boolean tradableVolumeRule) {
		this.tradableVolumeRule = tradableVolumeRule;
	}
	
	private boolean optionChainRule;

	public boolean isOptionChainRule() {
		return optionChainRule;
	}
	public void setOptionChainRule(boolean optionChainRule) {
		this.optionChainRule = optionChainRule;
	}
	
}