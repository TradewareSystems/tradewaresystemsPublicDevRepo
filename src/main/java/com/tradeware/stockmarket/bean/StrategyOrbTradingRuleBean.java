package com.tradeware.stockmarket.bean;

import com.tradeware.stockmarket.util.Constants;

public class StrategyOrbTradingRuleBean extends StrategyOrbTechnicalIndicatorBean {

	private static final long serialVersionUID = -6461001754943639868L;
	private Boolean tradePlacedRuleInd;
	private StringBuffer tradePlaceRule = new StringBuffer();
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd;
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1Min5RuleInd;
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd;
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd;
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd;
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd;
	private Boolean tradingRuleOHLCAnd3TimesStrengthInd;
	private Boolean tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd;
	//VWAP & VOLUME
	private Boolean tradingRuleVwapStrongInd;
	private Boolean tradingRuleVolumeStrengthInd;
	private Boolean tradingRule7TimesStrengthInd;
	private Boolean tradingRule7TimesStrenthAdditionalInd;
	private Boolean tradingRuleOpenBtwnAvgHiLoClsAndVwapInd;
	private Boolean tradingRuleCandleTypeTrendAndPrevHrCrossInd;
	private Boolean tradingRuleVolumeTradeStateInd;
	private Boolean tradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd;//support property
	//04-21-2021  start - afterSomeSuccess
	private Boolean tradingRuleProfitableBuyerSellerDiffInd;
	private Boolean tradingRuleProfitableCustomeStrongVolumeVwapInd;
	//04-21-2021  end
	private Boolean tradingRuleForwardEngulfingInd;
	private Boolean tradingRuleReverseEngulfingInd;
	private Boolean tradingRuleForwardEngulfingLvl2Ind;
	private Boolean tradingRuleForwardEngulfingLvl3Ind;
	
	public StrategyOrbTradingRuleBean clone(StrategyOrbTradingRuleBean bean) {
		super.clone(bean);
		bean.setTradePlacedRuleInd(this.tradePlacedRuleInd);
		bean.setTradePlaceRule(this.tradePlaceRule.toString());
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd(this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd);
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1Min5RuleInd(this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1Min5RuleInd);
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd(this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd);
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd(this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd);
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd(this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd);
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd(this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd);
		bean.setTradingRuleOHLCAnd3TimesStrengthInd(this.tradingRuleOHLCAnd3TimesStrengthInd);
		bean.setTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd(this.tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd);
		bean.setTradingRuleVwapStrongInd(this.tradingRuleVwapStrongInd);
		bean.setTradingRuleVolumeStrengthInd(this.tradingRuleVolumeStrengthInd);
		bean.setTradingRule7TimesStrengthInd(this.tradingRule7TimesStrengthInd);
		bean.setTradingRule7TimesStrenthAdditionalInd(this.tradingRule7TimesStrenthAdditionalInd);
		bean.setTradingRuleOpenBtwnAvgHiLoClsAndVwapInd(this.tradingRuleOpenBtwnAvgHiLoClsAndVwapInd); 
		bean.setTradingRuleCandleTypeTrendAndPrevHrCrossInd(this.tradingRuleCandleTypeTrendAndPrevHrCrossInd);
		bean.setTradingRuleVolumeTradeStateInd(this.tradingRuleVolumeTradeStateInd);
		bean.setTradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd(this.tradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd); //support property
		bean.setTradingRuleProfitableBuyerSellerDiffInd(this.tradingRuleProfitableBuyerSellerDiffInd);
		bean.setTradingRuleProfitableCustomeStrongVolumeVwapInd(this.tradingRuleProfitableCustomeStrongVolumeVwapInd);
		bean.setTradingRuleForwardEngulfingInd(this.tradingRuleForwardEngulfingInd);
		bean.setTradingRuleReverseEngulfingInd(this.tradingRuleReverseEngulfingInd);
		bean.setTradingRuleForwardEngulfingLvl2Ind(this.tradingRuleForwardEngulfingLvl2Ind);
		bean.setTradingRuleForwardEngulfingLvl3Ind(this.tradingRuleForwardEngulfingLvl3Ind);
		return bean; 
	}
	
	public Boolean getTradePlacedRuleInd() {
		return tradePlacedRuleInd;
	}
	public void setTradePlacedRuleInd(Boolean tradePlacedRuleInd) {
		this.tradePlacedRuleInd = tradePlacedRuleInd;
	}
	public String getTradePlaceRule() {
		return tradePlaceRule.toString();
	}
	public void setTradePlaceRule(String tradePlaceRule) {
		if (null != tradePlaceRule && !Constants.EMPTY_STRING.equals(tradePlaceRule)) {
			//this.tradePlaceRule = this.tradePlaceRule.append(tradePlaceRule).append(Constants.COMMA_SPACE);
			if (this.tradePlaceRule.length() > 0) {
				this.tradePlaceRule = this.tradePlaceRule.append(Constants.COMMA_SPACE);
			}
			this.tradePlaceRule = this.tradePlaceRule.append(tradePlaceRule);
		}
	}
	public Boolean getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd() {
		return tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd;
	}
	public void setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd(Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd) {
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd = tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd;
	}
	public Boolean getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1Min5RuleInd() {
		return tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1Min5RuleInd;
	}
	public void setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1Min5RuleInd(
			Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1Min5RuleInd) {
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1Min5RuleInd = tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1Min5RuleInd;
	}
	public Boolean getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd() {
		return tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd;
	}
	public void setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd(Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd) {
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd = tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd;
	}
	public Boolean getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd() {
		return tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd;
	}
	public void setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd(
			Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd) {
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd = tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd;
	}
	public Boolean getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd() {
		return tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd;
	}
	public void setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd(
			Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd) {
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd = tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd;
	}
	public Boolean getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd() {
		return tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd;
	}
	public void setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd(
			Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd) {
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd = tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd;
	}
	public Boolean getTradingRuleOHLCAnd3TimesStrengthInd() {
		return tradingRuleOHLCAnd3TimesStrengthInd;
	}
	public void setTradingRuleOHLCAnd3TimesStrengthInd(Boolean tradingRuleOHLCAnd3TimesStrengthInd) {
		this.tradingRuleOHLCAnd3TimesStrengthInd = tradingRuleOHLCAnd3TimesStrengthInd;
	}
	public Boolean getTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd() {
		return tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd;
	}
	public void setTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd(
			Boolean tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd) {
		this.tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd = tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd;
	}
	public Boolean getTradingRuleVwapStrongInd() {
		return tradingRuleVwapStrongInd;
	}
	public void setTradingRuleVwapStrongInd(Boolean tradingRuleVwapStrongInd) {
		this.tradingRuleVwapStrongInd = tradingRuleVwapStrongInd;
	}
	public Boolean getTradingRuleVolumeStrengthInd() {
		return tradingRuleVolumeStrengthInd;
	}
	public void setTradingRuleVolumeStrengthInd(Boolean tradingRuleVolumeStrengthInd) {
		this.tradingRuleVolumeStrengthInd = tradingRuleVolumeStrengthInd;
	}
	public Boolean getTradingRule7TimesStrengthInd() {
		return tradingRule7TimesStrengthInd;
	}
	public void setTradingRule7TimesStrengthInd(Boolean tradingRule7TimesStrengthInd) {
		this.tradingRule7TimesStrengthInd = tradingRule7TimesStrengthInd;
	}
	public Boolean getTradingRule7TimesStrenthAdditionalInd() {
		return tradingRule7TimesStrenthAdditionalInd;
	}
	public void setTradingRule7TimesStrenthAdditionalInd(Boolean tradingRule7TimesStrenthAdditionalInd) {
		this.tradingRule7TimesStrenthAdditionalInd = tradingRule7TimesStrenthAdditionalInd;
	}
	public Boolean getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd() {
		return tradingRuleOpenBtwnAvgHiLoClsAndVwapInd;
	}
	public void setTradingRuleOpenBtwnAvgHiLoClsAndVwapInd(Boolean tradingRuleOpenBtwnAvgHiLoClsAndVwapInd) {
		this.tradingRuleOpenBtwnAvgHiLoClsAndVwapInd = tradingRuleOpenBtwnAvgHiLoClsAndVwapInd;
	}
	public Boolean getTradingRuleCandleTypeTrendAndPrevHrCrossInd() {
		return tradingRuleCandleTypeTrendAndPrevHrCrossInd;
	}
	public void setTradingRuleCandleTypeTrendAndPrevHrCrossInd(Boolean tradingRuleCandleTypeTrendAndPrevHrCrossInd) {
		this.tradingRuleCandleTypeTrendAndPrevHrCrossInd = tradingRuleCandleTypeTrendAndPrevHrCrossInd;
	}
	public Boolean getTradingRuleVolumeTradeStateInd() {
		return tradingRuleVolumeTradeStateInd;
	}
	public void setTradingRuleVolumeTradeStateInd(Boolean tradingRuleVolumeTradeStateInd) {
		this.tradingRuleVolumeTradeStateInd = tradingRuleVolumeTradeStateInd;
	}
	public Boolean getTradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd() {
		return tradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd;
	}
	public void setTradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd(
			Boolean tradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd) {
		this.tradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd = tradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd;
	}
	public Boolean getTradingRuleProfitableBuyerSellerDiffInd() {
		return tradingRuleProfitableBuyerSellerDiffInd;
	}
	public void setTradingRuleProfitableBuyerSellerDiffInd(Boolean tradingRuleProfitableBuyerSellerDiffInd) {
		this.tradingRuleProfitableBuyerSellerDiffInd = tradingRuleProfitableBuyerSellerDiffInd;
	}
	public Boolean getTradingRuleProfitableCustomeStrongVolumeVwapInd() {
		return tradingRuleProfitableCustomeStrongVolumeVwapInd;
	}
	public void setTradingRuleProfitableCustomeStrongVolumeVwapInd(
			Boolean tradingRuleProfitableCustomeStrongVolumeVwapInd) {
		this.tradingRuleProfitableCustomeStrongVolumeVwapInd = tradingRuleProfitableCustomeStrongVolumeVwapInd;
	}
	public Boolean getTradingRuleForwardEngulfingInd() {
		return tradingRuleForwardEngulfingInd;
	}
	public void setTradingRuleForwardEngulfingInd(Boolean tradingRuleForwardEngulfingInd) {
		this.tradingRuleForwardEngulfingInd = tradingRuleForwardEngulfingInd;
	}
	public Boolean getTradingRuleReverseEngulfingInd() {
		return tradingRuleReverseEngulfingInd;
	}
	public void setTradingRuleReverseEngulfingInd(Boolean tradingRuleReverseEngulfingInd) {
		this.tradingRuleReverseEngulfingInd = tradingRuleReverseEngulfingInd;
	}
	public Boolean getTradingRuleForwardEngulfingLvl2Ind() {
		return tradingRuleForwardEngulfingLvl2Ind;
	}
	public void setTradingRuleForwardEngulfingLvl2Ind(Boolean tradingRuleForwardEngulfingLvl2Ind) {
		this.tradingRuleForwardEngulfingLvl2Ind = tradingRuleForwardEngulfingLvl2Ind;
	}
	public Boolean getTradingRuleForwardEngulfingLvl3Ind() {
		return tradingRuleForwardEngulfingLvl3Ind;
	}
	public void setTradingRuleForwardEngulfingLvl3Ind(Boolean tradingRuleForwardEngulfingLvl3Ind) {
		this.tradingRuleForwardEngulfingLvl3Ind = tradingRuleForwardEngulfingLvl3Ind;
	}
}
