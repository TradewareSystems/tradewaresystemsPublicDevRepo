package com.tradeware.stockmarket.bean;

import com.tradeware.stockmarket.util.Constants;

public class StrategyOrbDataBean extends AbstractTradeDataBean {

	private static final long serialVersionUID = 8722408458311868861L;

	private Integer itemId;
	//ORB CPR properties
	private Double candleHighsDiff;
	private Double candleLowsDiff;
	
	/*private Double candle1SizeAmt;
	private Double candle2SizeAmt;
	private Double candle3SizeAmt;
	private Double candle4SizeAmt;
	private Double candle1HighMinusClose;
	private Double candle1CloseMinusLow;
	private Double candle2HighMinusClose;
	private Double candle2CloseMinusLow;
	private Double candle3HighMinusClose;
	private Double candle3CloseMinusLow;
	private Double candle4HighMinusClose;
	private Double candle4CloseMinusLow;
	private String candle1Type;
	private String candle2Type;
	private String candle3Type;
	private String candle4Type;*/
	
	/*private Double currentCandleAvgHigh;
	private Double currentCandleAvgLow;
	private Double currentCandleAvgClose;
	private Double currentCandleOpen;
	private Double avgHighMinusClose;
	private Double avgCloseMinusLow;
	private Double avgHigh5min;
	private Double avgLow5min;
	private Double avgClose5min;
	private Double avgHighMinusClose5min;
	private Double avgCloseMinusLow5min;
	*/
	private Double gapUpDownMoveVal;
	private Integer candleNumber;
	private String candleTimeFrame;
	private Boolean dayLevelTradeInd;
	
	private Integer optionChainId;
	private String optionChainTrend;
	private String optionChainPriceTrend;
	private String heikinAshiTrendId;
	private String heikinAshiTrendDescription;
	private String strategyRule;
	private String strengthStyleClass;
	
	// private String tradableStrength;
	private String strengthableTradeStateId;
	private String strengthableTradeStateDescription;
	private String strengthableTradeInfo;
	
					//private String volumeTradeStateId;
					//private String volumeTradeStateDescription;
					//private String vwapTradeStateId;
					//private String vwapTradeStateDescription;
	//private String vwapTradeVolInfo;
	//private String vwapTradeVolRatioInfo;
	//private String volStrengthStyleClass;
	
	private boolean isFirstLevelSmaVwapRuleInd;
	private boolean isSecondLevelSmaVwapRuleInd;
	private boolean isBuyerSelleDiffRuleValid;
	
	private boolean isFirstLevelSmaVwapMin5RuleInd;
	private boolean isSecondLevelSmaVwapMin5RuleInd;
	private Boolean reverseMotherCandleInd;
	private Integer waitForEngulfingCount;
	private Boolean waitForEngulfingInd;
	
	private Boolean stochasticBasicRule1Ind;
	
	public StrategyOrbDataBean(Integer lotSize, String symbolId) {
		super(lotSize, symbolId);
		this.isBuyerSelleDiffRuleValid = true;
		this.volumeTradeStateId = Constants.NA;
		//this.vwapTradeStateId = Constants.VWNA;
		this.heikinAshiTrendId = Constants.NA;
		
		super.setCandleTypeTrendId(Constants.NA);
		super.setVolumeTradeStateId(Constants.NA);
		super.setVwapTradeStateId(Constants.NA);
		
	}

	public StrategyOrbDataBean clone() {
		StrategyOrbDataBean bean = new StrategyOrbDataBean(super.getLotSize(), super.getSymbolId());
		super.clone(bean);
		
		bean.setItemId(this.itemId);
		bean.setCandleHighsDiff(this.candleHighsDiff);
		bean.setCandleLowsDiff(this.candleLowsDiff);
		bean.setGapUpDownMoveVal(this.gapUpDownMoveVal);
		bean.setCandleNumber(this.candleNumber);
		bean.setCandleTimeFrame(this.candleTimeFrame);
		bean.setDayLevelTradeInd(this.dayLevelTradeInd);
		bean.setOptionChainId(this.optionChainId);
		bean.setOptionChainTrend(this.optionChainTrend);
		bean.setOptionChainPriceTrend(this.optionChainPriceTrend);
		bean.setHeikinAshiTrendId(this.heikinAshiTrendId);
		bean.setHeikinAshiTrendDescription(this.heikinAshiTrendDescription);
		bean.setStrategyRule(this.strategyRule);
		bean.setStrengthStyleClass(this.strengthStyleClass);
		bean.setStrengthableTradeStateId(this.strengthableTradeStateId);
		bean.setStrengthableTradeStateDescription(this.strengthableTradeStateDescription);
		bean.setStrengthableTradeInfo(this.strengthableTradeInfo);
		bean.setFirstLevelSmaVwapRuleInd(this.isFirstLevelSmaVwapRuleInd);
		bean.setSecondLevelSmaVwapRuleInd(isSecondLevelSmaVwapRuleInd);
		bean.setBuyerSelleDiffRuleValid(this.isBuyerSelleDiffRuleValid);
		bean.setFirstLevelSmaVwapMin5RuleInd(this.isFirstLevelSmaVwapMin5RuleInd);
		bean.setSecondLevelSmaVwapMin5RuleInd(this.isSecondLevelSmaVwapMin5RuleInd);
		bean.setReverseMotherCandleInd(this.reverseMotherCandleInd);
		bean.setWaitForEngulfingCount(this.waitForEngulfingCount);
		bean.setWaitForEngulfingInd(this.waitForEngulfingInd);
		bean.setOhlcTrendTradable(this.ohlcTrendTradable);
		bean.setNarrowRangeTradable(this.narrowRangeTradable);
		bean.setLevelOneSMAandVWAPformulaTradable(this.levelOneSMAandVWAPformulaTradable);
		bean.setBuyerSellerDiffRule500Tradable(this.buyerSellerDiffRule500Tradable);
		bean.setBuyerSellerDiffRule1000Tradable(this.buyerSellerDiffRule1000Tradable);
		bean.setPrevDayHrBreakTrendTradable(this.PrevDayHrBreakTrendTradable);
		bean.setCustomTradableOnOHLCDiff3with0(this.buyerSellerDiffRule1000Tradable);
		return bean;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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

	public Double getGapUpDownMoveVal() {
		return gapUpDownMoveVal;
	}

	public void setGapUpDownMoveVal(Double gapUpDownMoveVal) {
		this.gapUpDownMoveVal = gapUpDownMoveVal;
	}

	public Integer getCandleNumber() {
		return candleNumber;
	}

	public void setCandleNumber(Integer candleNumber) {
		this.candleNumber = candleNumber;
	}

	public String getCandleTimeFrame() {
		return candleTimeFrame;
	}

	public void setCandleTimeFrame(String candleTimeFrame) {
		this.candleTimeFrame = candleTimeFrame;
	}

	public Boolean getDayLevelTradeInd() {
		return dayLevelTradeInd;
	}

	public void setDayLevelTradeInd(Boolean dayLevelTradeInd) {
		this.dayLevelTradeInd = dayLevelTradeInd;
	}

	public Integer getOptionChainId() {
		return optionChainId;
	}

	public void setOptionChainId(Integer optionChainId) {
		this.optionChainId = optionChainId;
	}

	public String getOptionChainTrend() {
		return optionChainTrend;
	}

	public void setOptionChainTrend(String optionChainTrend) {
		this.optionChainTrend = optionChainTrend;
	}

	public String getOptionChainPriceTrend() {
		return optionChainPriceTrend;
	}

	public void setOptionChainPriceTrend(String optionChainPriceTrend) {
		this.optionChainPriceTrend = optionChainPriceTrend;
	}

	public String getHeikinAshiTrendId() {
		return heikinAshiTrendId;
	}

	public void setHeikinAshiTrendId(String heikinAshiTrendId) {
		this.heikinAshiTrendId = heikinAshiTrendId;
	}

	public String getHeikinAshiTrendDescription() {
		return heikinAshiTrendDescription;
	}

	public void setHeikinAshiTrendDescription(String heikinAshiTrendDescription) {
		this.heikinAshiTrendDescription = heikinAshiTrendDescription;
	}

	public String getStrategyRule() {
		return strategyRule;
	}

	public void setStrategyRule(String strategyRule) {
		this.strategyRule = strategyRule;
	}

	public String getStrengthStyleClass() {
		return strengthStyleClass;
	}

	public void setStrengthStyleClass(String strengthStyleClass) {
		this.strengthStyleClass = strengthStyleClass;
	}

	public String getStrengthableTradeStateId() {
		return strengthableTradeStateId;
	}

	public void setStrengthableTradeStateId(String strengthableTradeStateId) {
		this.strengthableTradeStateId = strengthableTradeStateId;
	}

	public String getStrengthableTradeStateDescription() {
		return strengthableTradeStateDescription;
	}

	public void setStrengthableTradeStateDescription(String strengthableTradeStateDescription) {
		this.strengthableTradeStateDescription = strengthableTradeStateDescription;
	}

	public String getStrengthableTradeInfo() {
		return strengthableTradeInfo;
	}

	public void setStrengthableTradeInfo(String strengthableTradeInfo) {
		this.strengthableTradeInfo = strengthableTradeInfo;
	}


	public boolean isFirstLevelSmaVwapRuleInd() {
		return isFirstLevelSmaVwapRuleInd;
	}

	public void setFirstLevelSmaVwapRuleInd(boolean isFirstLevelSmaVwapRuleInd) {
		this.isFirstLevelSmaVwapRuleInd = isFirstLevelSmaVwapRuleInd;
	}

	public boolean isSecondLevelSmaVwapRuleInd() {
		return isSecondLevelSmaVwapRuleInd;
	}

	public void setSecondLevelSmaVwapRuleInd(boolean isSecondLevelSmaVwapRuleInd) {
		this.isSecondLevelSmaVwapRuleInd = isSecondLevelSmaVwapRuleInd;
	}

	public boolean isBuyerSelleDiffRuleValid() {
		return isBuyerSelleDiffRuleValid;
	}

	public void setBuyerSelleDiffRuleValid(boolean isBuyerSelleDiffRuleValid) {
		this.isBuyerSelleDiffRuleValid = isBuyerSelleDiffRuleValid;
	}

	public boolean isFirstLevelSmaVwapMin5RuleInd() {
		return isFirstLevelSmaVwapMin5RuleInd;
	}

	public void setFirstLevelSmaVwapMin5RuleInd(boolean isFirstLevelSmaVwapMin5RuleInd) {
		this.isFirstLevelSmaVwapMin5RuleInd = isFirstLevelSmaVwapMin5RuleInd;
	}

	public boolean isSecondLevelSmaVwapMin5RuleInd() {
		return isSecondLevelSmaVwapMin5RuleInd;
	}

	public void setSecondLevelSmaVwapMin5RuleInd(boolean isSecondLevelSmaVwapMin5RuleInd) {
		this.isSecondLevelSmaVwapMin5RuleInd = isSecondLevelSmaVwapMin5RuleInd;
	}

	public Boolean getReverseMotherCandleInd() {
		return reverseMotherCandleInd;
	}

	public void setReverseMotherCandleInd(Boolean reverseMotherCandleInd) {
		this.reverseMotherCandleInd = reverseMotherCandleInd;
	}

	public Integer getWaitForEngulfingCount() {
		return waitForEngulfingCount;
	}

	public void setWaitForEngulfingCount(Integer waitForEngulfingCount) {
		this.waitForEngulfingCount = waitForEngulfingCount;
	}

	public Boolean getWaitForEngulfingInd() {
		return waitForEngulfingInd;
	}

	public void setWaitForEngulfingInd(Boolean waitForEngulfingInd) {
		this.waitForEngulfingInd = waitForEngulfingInd;
	}

	public String getVolumeTradeStateId() {
		return volumeTradeStateId;
	}

	public void setVolumeTradeStateId(String volumeTradeStateId) {
		this.volumeTradeStateId = volumeTradeStateId;
	}

	/*public String getVolumeTradeStateDescription() {
		return volumeTradeStateDescription;
	}

	public void setVolumeTradeStateDescription(String volumeTradeStateDescription) {
		this.volumeTradeStateDescription = volumeTradeStateDescription;
	}

	public String getVwapTradeStateId() {
		return vwapTradeStateId;
	}

	public void setVwapTradeStateId(String vwapTradeStateId) {
		this.vwapTradeStateId = vwapTradeStateId;
	}

	public String getVwapTradeStateDescription() {
		return vwapTradeStateDescription;
	}

	public void setVwapTradeStateDescription(String vwapTradeStateDescription) {
		this.vwapTradeStateDescription = vwapTradeStateDescription;
	}*/

	
	
	
	//may be different class
	private  Boolean ohlcTrendTradable;
	private  Boolean narrowRangeTradable;
	private  Boolean levelOneSMAandVWAPformulaTradable;
	
	
	private  Boolean buyerSellerDiffRule500Tradable;
	private  Boolean buyerSellerDiffRule1000Tradable;
	
	private  Boolean PrevDayHrBreakTrendTradable;
	private  Boolean customTradableOnOHLCDiff3with0;


	public Boolean getOhlcTrendTradable() {
		return ohlcTrendTradable;
	}

	public void setOhlcTrendTradable(Boolean ohlcTrendTradable) {
		this.ohlcTrendTradable = ohlcTrendTradable;
	}

	public Boolean getNarrowRangeTradable() {
		return narrowRangeTradable;
	}

	public void setNarrowRangeTradable(Boolean narrowRangeTradable) {
		this.narrowRangeTradable = narrowRangeTradable;
	}

	public Boolean getLevelOneSMAandVWAPformulaTradable() {
		return levelOneSMAandVWAPformulaTradable;
	}

	public void setLevelOneSMAandVWAPformulaTradable(Boolean levelOneSMAandVWAPformulaTradable) {
		this.levelOneSMAandVWAPformulaTradable = levelOneSMAandVWAPformulaTradable;
	}

	public Boolean getBuyerSellerDiffRule500Tradable() {
		return buyerSellerDiffRule500Tradable;
	}

	public void setBuyerSellerDiffRule500Tradable(Boolean buyerSellerDiffRule500Tradable) {
		this.buyerSellerDiffRule500Tradable = buyerSellerDiffRule500Tradable;
	}

	public Boolean getBuyerSellerDiffRule1000Tradable() {
		return buyerSellerDiffRule1000Tradable;
	}

	public void setBuyerSellerDiffRule1000Tradable(Boolean buyerSellerDiffRule1000Tradable) {
		this.buyerSellerDiffRule1000Tradable = buyerSellerDiffRule1000Tradable;
	}

	public Boolean getPrevDayHrBreakTrendTradable() {
		return PrevDayHrBreakTrendTradable;
	}

	public void setPrevDayHrBreakTrendTradable(Boolean prevDayHrBreakTrendTradable) {
		PrevDayHrBreakTrendTradable = prevDayHrBreakTrendTradable;
	}

	public Boolean getCustomTradableOnOHLCDiff3with0() {
		return customTradableOnOHLCDiff3with0;
	}

	public void setCustomTradableOnOHLCDiff3with0(Boolean customTradableOnOHLCDiff3with0) {
		this.customTradableOnOHLCDiff3with0 = customTradableOnOHLCDiff3with0;
	}

	public Boolean getStochasticBasicRule1Ind() {
		return stochasticBasicRule1Ind;
	}

	public void setStochasticBasicRule1Ind(Boolean stochasticBasicRule1Ind) {
		this.stochasticBasicRule1Ind = stochasticBasicRule1Ind;
	}

	@Override
	public String toString() {
		return "StrategyOrbDataBean [itemId=" + itemId + ", candleHighsDiff=" + candleHighsDiff + ", candleLowsDiff="
				+ candleLowsDiff + ", gapUpDownMoveVal=" + gapUpDownMoveVal + ", candleNumber=" + candleNumber
				+ ", candleTimeFrame=" + candleTimeFrame + ", dayLevelTradeInd=" + dayLevelTradeInd
				+ ", optionChainId=" + optionChainId
				+ ", optionChainTrend=" + optionChainTrend + ", optionChainPriceTrend=" + optionChainPriceTrend
				+ ", heikinAshiTrendId=" + heikinAshiTrendId + ", strategyRule=" + strategyRule + ", strengthStyleClass="
				+ strengthStyleClass + ", strengthableTradeStateId=" + strengthableTradeStateId
				+ ", strengthableTradeStateDescription=" + strengthableTradeStateDescription
				+ ", strengthableTradeInfo=" + strengthableTradeInfo /*+ ", volumeTradeStateId=" + volumeTradeStateId
				+ ", volumeTradeStateDescription=" + volumeTradeStateDescription + ", vwapTradeStateId="
				+ vwapTradeStateId + ", vwapTradeStateDescription=" + vwapTradeStateDescription*/
				+ ", isFirstLevelSmaVwapRuleInd=" + isFirstLevelSmaVwapRuleInd + ", isBuyerSelleDiffRuleValid="
				+ isBuyerSelleDiffRuleValid + ", ohlcTrendTradable=" + ohlcTrendTradable + ", narrowRangeTradable="
				+ narrowRangeTradable + ", levelOneSMAandVWAPformulaTradable=" + levelOneSMAandVWAPformulaTradable
				+ ", buyerSellerDiffRule500Tradable=" + buyerSellerDiffRule500Tradable
				+ ", buyerSellerDiffRule1000Tradable=" + buyerSellerDiffRule1000Tradable
				+ ", PrevDayHrBreakTrendTradable=" + PrevDayHrBreakTrendTradable + ", customTradableOnOHLCDiff3with0="
				+ customTradableOnOHLCDiff3with0 + "]" +"\r\n"+super.toString();
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = super.equals(obj);
		StrategyOrbDataBean other = (StrategyOrbDataBean) obj;
		isEqual = (null != this.itemId && null != other.getItemId()
				&& this.itemId.intValue() == other.itemId.intValue());
		return isEqual;
	}
}