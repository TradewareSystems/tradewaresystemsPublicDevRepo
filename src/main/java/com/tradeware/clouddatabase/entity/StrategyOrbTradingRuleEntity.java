package com.tradeware.clouddatabase.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tradeware.clouddatabase.bean.StrategyOrbBean;

@Entity
@Table(name = "T_ORB_TRADE_RULE", uniqueConstraints = {
		@UniqueConstraint(name = "OPEN_RANGE_BREAK_OUT_CONSTRAINT", columnNames = {
		"OPEN_RANGE_BREAK_OUT_ID" }) })
public class StrategyOrbTradingRuleEntity extends AbstractEntity {

	private static final long serialVersionUID = -6461001754943639868L;

	@Column(name = "TRADE_PLACED_RULE_IND")
	private Boolean tradePlacedRuleInd;
	
	@Column(name = "TRADABLE_RULE")
	private String tradePlaceRule;
	
	@Column(name = "TRADE_ON_SMA_VWAP_OPEN_BETWEEN_AVG_HI_LO_LVL1_IND")
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd;
	
	@Column(name = "TRADE_ON_SMA_VWAP_OPEN_BETWEEN_AVG_HI_LO_LVL2_IND")
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd;
	
	//04-21-2021  start - afterSomeSuccess [05-06-2021]
	@Column(name = "TRADE_ON_SMA_VWAP_OPEN_BETWEEN_AVG_HI_LO_LVL2_MIN5_IND")
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd;
	
	@Column(name = "TRADE_ON_SMA_VWAP_OPEN_BTWN_AVG_HI_LO_LVL2_PROD_IND")
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd;
	
	@Column(name = "TRADE_ON_SMA_VWAP_OPEN_BTWN_AVG_HI_LO_LVL2_MIN5_PROD_IND")
	private Boolean tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd;
	//04-21-2021  end - afterSomeSuccess [05-06-2021]
	
	@Column(name = "CNDL_OHLC_3TIMES_STRNTH_IND")
	private Boolean tradingRuleOHLCAnd3TimesStrengthInd;
	
	@Column(name = "CNDL_TYPE_EQ_TRADABLE_EQ_OHLC_HR_CROSS_IND")
	private Boolean tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd;
	
	//VWAP & VOLUME
	@Column(name = "TRADE_ON_VWAP_IND")
	private Boolean tradingRuleVwapStrongInd;
	
	@Column(name = "TRADE_ON_VOLUME_STRNTH_IND")
	private Boolean tradingRuleVolumeStrengthInd;
	
	@Column(name = "TRADE_7TIMES_STRNTH_IND")
	private Boolean tradingRule7TimesStrengthInd;
	
	@Column(name = "CNDL_OPEN_BETWEEN_AVG_HI_LO_CLS_AND_VWAP_IND")
	private Boolean tradingRuleOpenBtwnAvgHiLoClsAndVwapInd;
	
	@Column(name = "CNDL_TYPE_TREND_AND_PREV_HR_CROSS_IND")
	private Boolean tradingRuleCandleTypeTrendAndPrevHrCrossInd;
	
	//[BUY3,SEL3,BUY4,SEL4,BUY5,SEL5,BUY6,SEL6,BUY7,SEL7]
	@Column(name = "TRADE_ON_VOLUME_TRADE_IND")
	private Boolean tradingRuleVolumeTradeStateInd;
	
	@Column(name = "TRADE_ON_STRONG_VOLUME_VWAP_TRADE_IND")
	private Boolean tradingRuleProfitableCustomeStrongVolumeVwapInd;
	
	@Column(name = "TRADE_ON_FWD_ENGULFING_IND")
	private Boolean tradingRuleForwardEngulfingInd;
	
	@Column(name = "TRADE_ON_RVRSE_ENGULFING_IND")
	private Boolean tradingRuleReverseEngulfingInd;
	
	@Column(name = "TRADE_ON_FWD_ENGULFING_LVL2_IND")
	private Boolean tradingRuleForwardEngulfingLvl2Ind;
	
	@Column(name = "TRADE_ON_FWD_ENGULFING_LVL3_IND")
	private Boolean tradingRuleForwardEngulfingLvl3Ind;

	public void populateEntity(StrategyOrbBean bean) {
		this.tradePlacedRuleInd = bean.getTradePlacedRuleInd();
		this.tradePlaceRule = bean.getTradePlaceRule();
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd = bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd();
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd = bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd();
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd = bean
				.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd();
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd = bean
				.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd();
		this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd = bean
				.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd();
		this.tradingRuleOHLCAnd3TimesStrengthInd = bean.getTradingRuleOHLCAnd3TimesStrengthInd();
		this.tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd = bean
				.getTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd();
		this.tradingRuleVwapStrongInd = bean.getTradingRuleVwapStrongInd();
		this.tradingRuleVolumeStrengthInd = bean.getTradingRuleVolumeStrengthInd();
		this.tradingRule7TimesStrengthInd = bean.getTradingRule7TimesStrengthInd();
		this.tradingRuleOpenBtwnAvgHiLoClsAndVwapInd = bean.getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd();
		this.tradingRuleCandleTypeTrendAndPrevHrCrossInd = bean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd();
		this.tradingRuleVolumeTradeStateInd = bean.getTradingRuleVolumeTradeStateInd();
		this.tradingRuleProfitableCustomeStrongVolumeVwapInd = bean
				.getTradingRuleProfitableCustomeStrongVolumeVwapInd();
		this.tradingRuleForwardEngulfingInd = bean.getTradingRuleForwardEngulfingInd();
		this.tradingRuleReverseEngulfingInd = bean.getTradingRuleReverseEngulfingInd();
		this.tradingRuleForwardEngulfingLvl2Ind = bean.getTradingRuleForwardEngulfingLvl2Ind();
		this.tradingRuleForwardEngulfingLvl3Ind = bean.getTradingRuleForwardEngulfingLvl3Ind();
	}

	public void populateBean(StrategyOrbBean bean) {
		bean.setTradePlacedRuleInd(this.tradePlacedRuleInd);
		bean.setTradePlaceRule(this.tradePlaceRule);
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd(this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd);
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd(this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd);
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd(this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd);
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd(this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd);
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd(
				this.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd);
		bean.setTradingRuleOHLCAnd3TimesStrengthInd(this.tradingRuleOHLCAnd3TimesStrengthInd);
		bean.setTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd(
				this.tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd);
		bean.setTradingRuleVwapStrongInd(this.tradingRuleVwapStrongInd);
		bean.setTradingRuleVolumeStrengthInd(this.tradingRuleVolumeStrengthInd);
		bean.setTradingRule7TimesStrengthInd(this.tradingRule7TimesStrengthInd);
		bean.setTradingRuleOpenBtwnAvgHiLoClsAndVwapInd(this.tradingRuleOpenBtwnAvgHiLoClsAndVwapInd);
		bean.setTradingRuleCandleTypeTrendAndPrevHrCrossInd(this.tradingRuleCandleTypeTrendAndPrevHrCrossInd);
		bean.setTradingRuleVolumeTradeStateInd(this.tradingRuleVolumeTradeStateInd);
		bean.setTradingRuleProfitableCustomeStrongVolumeVwapInd(this.tradingRuleProfitableCustomeStrongVolumeVwapInd);
		bean.setTradingRuleForwardEngulfingInd(this.tradingRuleForwardEngulfingInd);
		bean.setTradingRuleReverseEngulfingInd(this.tradingRuleReverseEngulfingInd);
		bean.setTradingRuleForwardEngulfingLvl2Ind(this.tradingRuleForwardEngulfingLvl2Ind);
		bean.setTradingRuleForwardEngulfingLvl3Ind(this.tradingRuleForwardEngulfingLvl3Ind);
	}
	
	@Id
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "OPEN_RANGE_BREAK_OUT_ID")
	@MapsId
	private StrategyOrbEntity strategyOrbEntity;

	public StrategyOrbEntity getStrategyOrbEntity() {
		return strategyOrbEntity;
	}

	public void setStrategyOrbEntity(StrategyOrbEntity strategyOrbEntity) {
		this.strategyOrbEntity = strategyOrbEntity;
	}

}
