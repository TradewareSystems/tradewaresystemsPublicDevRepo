package com.tradeware.stockmarket.traderules;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.tool.TwilioSendSmsTool;
import com.tradeware.stockmarket.util.Constants;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TradewareOrderPlacementRuleTool {

	@Autowired
	protected TradewareTool tradewareTool;

	@Autowired
	protected KiteConnectTool kiteConnectTool;

//Top level filters to reduce trades start

	// can verifiable before Candle/Scanner start
	public boolean isOhlcTrendTradable(StrategyOrbDataBean bean) {
		boolean isValid = false;
		if (null == bean.getOhlcTrendTradable()) {
			if (bean.getOhlcStateId() != null) {
				if (bean.getOhlcStateId() != null
						&& (Constants.BUY.equals(bean.getOhlcStateId()) || Constants.SELL.equals(bean.getOhlcStateId())
								|| Constants.BUY_SPIKE.equals(bean.getOhlcStateId())
								|| Constants.SELL_SPIKE.equals(bean.getOhlcStateId())
								|| Constants.BUY_SPIKE_1.equals(bean.getOhlcStateId())
								|| Constants.SELL_SPIKE_1.equals(bean.getOhlcStateId()))) {
					isValid = true;
				}
				bean.setOhlcTrendTradable(isValid);
			}
		} else {
			isValid = bean.getOhlcTrendTradable();
		}
		return isValid;
	}

	public boolean isNarrowRangeTradable(StrategyOrbDataBean bean) {
		boolean isValid = false;
		if (null == bean.getNarrowRangeTradable()) {
			if (bean.getStrategyRule() != null) {
				if (bean.getStrategyRule() != null && (Constants.STRATEGY_NR7_R1.equals(bean.getStrategyRule())
						|| Constants.STRATEGY_NR7_R2.equals(bean.getStrategyRule())
						|| Constants.STRATEGY_ORB_R1.equals(bean.getStrategyRule())
						|| Constants.STRATEGY_ORB_R2.equals(bean.getStrategyRule()))) {
					isValid = true;
				}
				bean.setNarrowRangeTradable(isValid);
			}
		} else {
			isValid = bean.getNarrowRangeTradable();
		}
		return isValid;
	}

	public boolean isLevelOneSMAandVWAPformulaTradable(StrategyOrbDataBean bean) {
		boolean ruleLevel1Passed = false;
		if (null == bean.getLevelOneSMAandVWAPformulaTradable()) {
			if (null != bean && null != bean.getAvgHigh1min() && null != bean.getAvgLow1min()
					&& null != bean.getAvgClose1min() && null != bean.getCurrentCandleOpen()
					&& null != bean.getVwapValue() && (null != bean.getBuyAtVal() && null != bean.getSellAtVal())) {
				if ((bean.getCurrentCandleOpen() > bean.getAvgLow1min())
						&& (bean.getCurrentCandleOpen() < bean.getAvgHigh1min())
						&& (bean.getVwapValue() > bean.getSellAtVal() && bean.getVwapValue() < bean.getBuyAtVal())) {
					ruleLevel1Passed = true;
				}
				bean.setLevelOneSMAandVWAPformulaTradable(ruleLevel1Passed);
			}
		} else {
			ruleLevel1Passed = bean.getLevelOneSMAandVWAPformulaTradable();
		}
		return ruleLevel1Passed;
	}

	// can verifiable before Candle/Scanner end

	// can verifiable once level crossed start
	public boolean isBuyerSellerDiffRule1000Tradable(StrategyOrbDataBean bean) {
		boolean buyerSellerDiffRule = false;
		if (null == bean.getBuyerSellerDiffRule1000Tradable()) {
			double buyerSellerDiff = kiteConnectTool
					.getRoundupToTwoValue((bean.getSellerAt() - bean.getBuyerAt()) * bean.getLotSize());
			bean.setBuyerSellerDiffVal(buyerSellerDiff);
			if (null != bean.getBuyerSellerDiffVal2()) {
				bean.setBuyerSellerDiffVal2(kiteConnectTool.getRoundupToTwoValue(bean.getBuyerSellerDiffVal2()));
			}
			buyerSellerDiffRule = (bean.getSellerAt() == 0 || bean.getBuyerAt() == 0
					|| buyerSellerDiff <= 1000/* 750 */);
			bean.setBuyerSellerDiffRule1000Tradable(buyerSellerDiffRule);
		} else {
			buyerSellerDiffRule = bean.getBuyerSellerDiffRule1000Tradable();
		}
		return buyerSellerDiffRule;
	}

	public boolean isBuyerSellerDiffRule500Tradable(StrategyOrbDataBean bean) {
		boolean buyerSellerDiffRule = false;
		if (null == bean.getBuyerSellerDiffRule500Tradable()) {
			if (null == bean.getBuyerSellerDiffVal()) {
				isBuyerSellerDiffRule1000Tradable(bean);
			}
			bean.setBuyerSellerDiffRule500Tradable(bean.getBuyerSellerDiffVal() <= 500);
		} else {
			buyerSellerDiffRule = bean.getBuyerSellerDiffRule500Tradable();
		}
		return buyerSellerDiffRule;
	}

	// Can verifiable once level crossed end

	public boolean isGapUpDownMoveValTradable(StrategyOrbDataBean bean) {
		return (bean.getGapUpDownMoveVal() > 1500);
	}

	public boolean isGapUpDownMoveValMonitorrTradable(StrategyOrbDataBean bean) {
		return (bean.getCandle2SizeAmt() < 2500
				&& !(Constants.NIFTY_50.equals(bean.getSymbolId()) || Constants.BANKNIFTY.equals(bean.getSymbolId())));

	}

	public boolean isPrevDayHrBreaTrendTradable(StrategyOrbDataBean bean) {
		boolean isValid = false;
		if ((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
				&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
				&& (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId()))) {
			isValid = true;
		}
		bean.setPrevDayHrBreakTrendTradable(isValid);
		return isValid;
	}

	public boolean isCustomTradableOnOHLC_Diff_3_000(StrategyOrbDataBean bean) {
		boolean isValid = false;
		if (((bean.getCandleHighsDiff() < bean.getCandleLowsDiff()) && ((bean.getCandle2HighMinusClose() == 0)
				|| (bean.getCandle2HighMinusClose() * 3) <= bean.getCandle2CloseMinusLow()))
				|| ((bean.getCandleLowsDiff() < bean.getCandleHighsDiff()) && ((bean.getCandle2CloseMinusLow() == 0)
						|| (bean.getCandle2CloseMinusLow() * 3) <= bean.getCandle2HighMinusClose()))) {
			isValid = true;
		}

		bean.setCustomTradableOnOHLCDiff3with0(isValid);
		return isValid;
	}

//Top level filters to reduce trades end

	// Update fields once trade triggered start

	// Update fields once trade triggered end

	// Custom Trading rule
	public boolean CustomTradingRule1(StrategyOrbDataBean bean) {
		boolean tradableInd = false;
		if (bean.getTradableStateId().equals(bean.getOhlcStateId()))
			if (bean.getCustomTradableOnOHLCDiff3with0()) {
				if (bean.getPrevDayHrBreakTrendTradable()) {
					tradableInd = true;
				}
			}
		return tradableInd;
	}

	
	
	
	
	
	
	
	// Phase 2 - 03-12-2021 start
	public void tradingRuleOHLCAnd3TimesStrength(StrategyOrbDataBean bean) {
		if (null != bean.getCandleLowsDiff() && null != bean.getCandleHighsDiff()
				&& null != bean.getCandle2HighMinusClose() && (null != bean.getCandle2CloseMinusLow())) {
			if (((bean.getCandleHighsDiff() < bean.getCandleLowsDiff()) && ((bean.getCandle2HighMinusClose() == 0)
					|| (bean.getCandle2HighMinusClose() * 3) <= bean.getCandle2CloseMinusLow()))
					|| ((bean.getCandleLowsDiff() < bean.getCandleHighsDiff()) && ((bean.getCandle2CloseMinusLow() == 0)
							|| (bean.getCandle2CloseMinusLow() * 3) <= bean.getCandle2HighMinusClose()))) {
				if (null != bean.getCandleTypeTrendId() && null != bean.getOhlcStateId()
						&& bean.getCandleTypeTrendId().equals(bean.getOhlcStateId())
						&& bean.getTradableStateId().equals(bean.getOhlcStateId())) {
					bean.setTradingRuleOHLCAnd3TimesStrengthInd(true);
				}
			}
		}
	}
	
	public void tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCross(StrategyOrbDataBean bean) {
		if (bean.getTradableStateId().equals(bean.getOhlcStateId())) {
			if (bean.getTradableStateId().equals(bean.getCandleTypeTrendId())) {
				if (null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd()) {
					if (bean.getGapUpDownMoveVal().doubleValue() < /*1000*/ 2000)
						bean.setTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd(true);
				}
			}
		}
	}
	
	public boolean verifySMAandVWAPformulaLevel1(StrategyOrbDataBean bean) {
		boolean ruleLevel1Passed = false;
		if (null != bean && null != bean.getAvgHigh1min() && null != bean.getAvgLow1min()
				&& null != bean.getAvgClose1min() && null != bean.getCurrentCandleOpen() && null != bean.getVwapValue()
				&& (null != bean.getBuyAtVal() && null != bean.getSellAtVal())) {
			if ((bean.getCurrentCandleOpen() > bean.getAvgLow1min())
					&& (bean.getCurrentCandleOpen() < bean.getAvgHigh1min())
					&& (bean.getVwapValue() > bean.getSellAtVal() && bean.getVwapValue() < bean.getBuyAtVal())) {
				ruleLevel1Passed = true;
				bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd(true);
			}
		}
		return ruleLevel1Passed;
	}
	
	public boolean verifySMAandVWAPformulaLevel1Min5(StrategyOrbDataBean bean) {
		boolean ruleLevel5min5Passed = false;
		if (null != bean && null != bean.getAvgHigh5min() && null != bean.getAvgLow5min()
				&& null != bean.getAvgClose5min() && null != bean.getCurrentCandleOpen() && null != bean.getVwapValue()
				&& (null != bean.getBuyAtVal() && null != bean.getSellAtVal())) {
			if ((bean.getCurrentCandleOpen() > bean.getAvgLow5min())
					&& (bean.getCurrentCandleOpen() < bean.getAvgHigh5min())
					&& (bean.getVwapValue() > bean.getSellAtVal() && bean.getVwapValue() < bean.getBuyAtVal())) {
				ruleLevel5min5Passed = true;
				bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1Min5RuleInd(true);
			}
		}
		return ruleLevel5min5Passed;
	}
	
	public boolean verifySMAandVWAPformulaLevel2(StrategyOrbDataBean bean) {
		boolean ruleLevel2Passed = false;
		if (null != bean && null != bean.getAvgHigh1min() && null != bean.getAvgLow1min()
				&& null != bean.getAvgClose1min() && null != bean.getCurrentCandleOpen() && null != bean.getVwapValue()
				&& null != bean.getTradableStateId() && null != bean.getGapUpDownMoveVal()
				&& null != bean.getBuyerSellerDiffVal()) {
			if ((bean.getCurrentCandleOpen() > bean.getAvgLow1min())
					&& (bean.getCurrentCandleOpen() < bean.getAvgHigh1min())
					&& (bean.getGapUpDownMoveVal() < 500 && bean.getBuyerSellerDiffVal() < 500 && bean.getBuyerSellerDiffVal2() < 1000)
					&& ((Constants.BUY.equals(bean.getTradableStateId())
							&& bean.getCurrentCandleOpen() > bean.getAvgClose1min()
							&& bean.getCurrentCandleOpen() > bean.getVwapValue())
							|| (Constants.SELL.equals(bean.getTradableStateId())
									&& bean.getCurrentCandleOpen() < bean.getAvgClose1min()
									&& bean.getCurrentCandleOpen() < bean.getVwapValue()))) {
				ruleLevel2Passed = true;
				bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd(true);
			}
		}
		bean.setSecondLevelSmaVwapRuleInd(ruleLevel2Passed);
		return ruleLevel2Passed;
	}
	
	public boolean verifySMAandVWAPformulaLevel2Min5(StrategyOrbDataBean bean) {
		boolean ruleLevel2Min5Passed = false;
		if (null != bean && null != bean.getAvgHigh5min() && null != bean.getAvgLow5min()
				&& null != bean.getAvgClose5min() && null != bean.getCurrentCandleOpen() && null != bean.getVwapValue()
				&& null != bean.getTradableStateId() && null != bean.getGapUpDownMoveVal()
				&& null != bean.getBuyerSellerDiffVal()) {
			if ((bean.getCurrentCandleOpen() > bean.getAvgLow5min())
					&& (bean.getCurrentCandleOpen() < bean.getAvgHigh5min())
					
					&& (bean.getGapUpDownMoveVal() < 500 && bean.getBuyerSellerDiffVal() < 500 && bean.getBuyerSellerDiffVal2() < 1000)
					&& ((Constants.BUY.equals(bean.getTradableStateId())
							&& bean.getCurrentCandleOpen() > bean.getAvgClose5min()
							&& bean.getCurrentCandleOpen() > bean.getVwapValue())
							|| (Constants.SELL.equals(bean.getTradableStateId())
									&& bean.getCurrentCandleOpen() < bean.getAvgClose5min()
									&& bean.getCurrentCandleOpen() < bean.getVwapValue()))) {
				ruleLevel2Min5Passed = true;
				bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd(true);
			}
		}
		bean.setSecondLevelSmaVwapMin5RuleInd(ruleLevel2Min5Passed);
		return ruleLevel2Min5Passed;
	}
	
	public void tradingRule7TimesStrenthCustom(StrategyOrbDataBean bean) {
		if ( (Constants.BUY.equals(bean.getTradableStateId())
				&& (null != bean.getCandleLowsDiff() && bean.getCandleLowsDiff() <= 7000)
				&& (null != bean.getCandle2HighMinusClose() && bean.getCandle2HighMinusClose() > 1
						&& ((bean.getCandle2HighMinusClose() * 7) <= bean.getCandle2CloseMinusLow()))) 
				||
				(Constants.SELL.equals(bean.getTradableStateId())
						&& (null != bean.getCandleHighsDiff() && bean.getCandleHighsDiff() <= 7000)
						&& (null != bean.getCandle2CloseMinusLow() && bean.getCandle2CloseMinusLow() > 1
								&& ((bean.getCandle2CloseMinusLow() * 7) <= bean.getCandle2HighMinusClose())))) {
			
			/*if (null != bean.getCandleTypeTrendId() && null != bean.getOhlcStateId()
					&& bean.getCandleTypeTrendId().equals(bean.getOhlcStateId()) && bean.getTradableStateId().equals(bean.getOhlcStateId())) {
				//bean.setTradingRuleOHLCAnd3TimesStrenthInd(true);
			}*/
			bean.setTradingRule7TimesStrengthInd(true);
		}

	}
	
	public void tradingRule7TimesStrenthAdditional(StrategyOrbDataBean bean) {
		if ((null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd())
				&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())
				// && (null != bean.getOhlcStateId() &&
				// bean.getTradableStateId().equals(bean.getOhlcStateId()))

				&& ((bean.getTradableStateId().equals(Constants.BUY) && null != bean.getBuySellQuantityRatio()
						&& bean.getBuySellQuantityRatio() > 1) || bean.getTradableStateId().equals(Constants.SELL))
		) {
			bean.setTradingRule7TimesStrenthAdditionalInd(true);
		}
	}

	public void tradingRuleOpenBtwnAvgHiLoClsAndVwapCustom(StrategyOrbDataBean bean) {
		if (null != bean.getCurrentCandleOpen() && null != bean.getAvgLow1min() && null != bean.getAvgHigh1min()
				&& null != bean.getAvgClose1min() && (bean.getCurrentCandleOpen() > bean.getAvgLow1min())
				&& (bean.getCurrentCandleOpen() < bean.getAvgHigh1min())) {

			if ((Constants.BUY.equals(bean.getTradableStateId()) && bean.getCurrentCandleOpen() > bean.getAvgClose1min()
					&& bean.getCurrentCandleOpen() > bean.getVwapValue())
					|| (Constants.SELL.equals(bean.getTradableStateId())
							&& bean.getCurrentCandleOpen() < bean.getAvgClose1min()
							&& bean.getCurrentCandleOpen() < bean.getVwapValue())) {

				if (null != bean.getGapUpDownMoveVal() && null != bean.getBuyerSellerDiffVal()
						&& bean.getGapUpDownMoveVal() < 500 && bean.getBuyerSellerDiffVal() < 1000) {
					/*if (null != bean.getCandleTypeTrendId() && null != bean.getOhlcStateId()
							&& bean.getCandleTypeTrendId().equals(bean.getOhlcStateId())
							&& bean.getTradableStateId().equals(bean.getOhlcStateId())) {
						bean.setTradingRuleOHLCAnd3TimesStrenthInd(true);
					}*/
					bean.setTradingRuleOpenBtwnAvgHiLoClsAndVwapInd(true);
				}

			}
		}
	}
	
	public void tradingRuleCandleTypeTrendEqOhlcStateAndPrevHrCross(StrategyOrbDataBean bean) {
		if (null != bean.getCandleTypeTrendId() && null != bean.getOhlcStateId()
				&& bean.getCandleTypeTrendId().equals(bean.getOhlcStateId())
				&& bean.getTradableStateId().equals(bean.getOhlcStateId())) {
			bean.setTradingRuleCandleTypeTrendAndPrevHrCrossInd(true);
		}
	}
	//Phase 2 - 03-12-2021 end
	
	// Phase 4 :: 05-09-2021 start - afterSomeSuccess
	public void verifySMAandVWAPformulaLevel2Prod(StrategyOrbDataBean bean) {
		if (null != bean && null != bean.getAvgHigh1min() && null != bean.getAvgLow1min()
				&& null != bean.getAvgClose1min() && null != bean.getCurrentCandleOpen() && null != bean.getVwapValue()
				&& null != bean.getTradableStateId() && null != bean.getGapUpDownMoveVal()
				&& null != bean.getBuyerSellerDiffVal() && null != bean.getBuyerSellerDiffVal2()
				&& null != bean.getCandle4SizeAmt() && null != bean.getCandle4SizeAmt() && null != bean.getLotSize()) {
			if ((bean.getCurrentCandleOpen() > bean.getAvgLow1min()
					&& bean.getCurrentCandleOpen() < bean.getAvgHigh1min())
					&& ((Constants.BUY.equals(bean.getTradableStateId())
							&& bean.getCurrentCandleOpen() > bean.getAvgClose1min()
							&& bean.getCurrentCandleOpen() > bean.getVwapValue())
							|| (Constants.SELL.equals(bean.getTradableStateId())
									&& bean.getCurrentCandleOpen() < bean.getAvgClose1min()
									&& bean.getCurrentCandleOpen() < bean.getVwapValue()))) {
				
				if (bean.getBuyerSellerDiffVal() <= 300 && bean.getBuyerSellerDiffVal2() < 500
						&& bean.getGapUpDownMoveVal() <= 500) {
					if ((Constants.BUY.equals(bean.getTradableStateId())
							&& (((bean.getBuyAtVal() - bean.getVwapValue()) * bean.getLotSize()) >= 500)
							&& (((bean.getBuyAtVal() - bean.getVwapValue()) * bean.getLotSize()) <= 3000))
							|| (Constants.SELL.equals(bean.getTradableStateId())
									&& (((bean.getVwapValue() - bean.getSellAtVal()) * bean.getLotSize()) >= 2000)
									&& (((bean.getVwapValue() - bean.getSellAtVal()) * bean.getLotSize()) <= 7500))) {
						if (/*bean.getCandle4SizeAmt() >= 100 && */bean.getCandle4SizeAmt() <= 3000) {
							bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd(true);
						}

					}
				}
			}
		}
	}

	public void verifySMAandVWAPformulaLevel2Min5Prod(StrategyOrbDataBean bean) {
		if (null != bean && null != bean.getAvgHigh5min() && null != bean.getAvgLow5min()
				&& null != bean.getAvgClose5min() && null != bean.getCurrentCandleOpen() && null != bean.getVwapValue()
				&& null != bean.getTradableStateId() && null != bean.getGapUpDownMoveVal()
				&& null != bean.getBuyerSellerDiffVal() && null != bean.getBuyerSellerDiffVal2()
				&& null != bean.getCandle4SizeAmt() && null != bean.getCandle4SizeAmt() && null != bean.getLotSize()) {
			if ((bean.getCurrentCandleOpen() > bean.getAvgLow5min())
					&& (bean.getCurrentCandleOpen() < bean.getAvgHigh5min())
					&& ((Constants.BUY.equals(bean.getTradableStateId())
							&& bean.getCurrentCandleOpen() > bean.getAvgClose5min()
							&& bean.getCurrentCandleOpen() > bean.getVwapValue())
							|| (Constants.SELL.equals(bean.getTradableStateId())
									&& bean.getCurrentCandleOpen() < bean.getAvgClose5min()
									&& bean.getCurrentCandleOpen() < bean.getVwapValue()))) {

				if (bean.getBuyerSellerDiffVal() <= 300 && bean.getBuyerSellerDiffVal2() < 500
						&& bean.getGapUpDownMoveVal() <= 500) {
					if ((Constants.BUY.equals(bean.getTradableStateId())
							&& (((bean.getBuyAtVal() - bean.getVwapValue()) * bean.getLotSize()) >= 500)
							&& (((bean.getBuyAtVal() - bean.getVwapValue()) * bean.getLotSize()) <= 3000))
							|| (Constants.SELL.equals(bean.getTradableStateId())
									&& (((bean.getVwapValue() - bean.getSellAtVal()) * bean.getLotSize()) >= 2000)
									&& (((bean.getVwapValue() - bean.getSellAtVal()) * bean.getLotSize()) <= 5000))) {
						if (/*bean.getCandle4SizeAmt() >= 100 && */bean.getCandle4SizeAmt() <= 2000) {
							bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd(true);
						}

					}
				}
			}
		}
	}

	
	
	
	// Phase 4 :: 05-09-2021 end - afterSomeSuccess
	
	// Phase 5 :: 05-15-2021 start - afterSomeSuccess
	public void verifyEngulfingStrategyRule(StrategyOrbDataBean bean) {
		if (null != bean && null != bean.getHighCandle3() && null != bean.getHighCandle4()
				&& null != bean.getCloseCandle3() && null != bean.getCloseCandle4() && null != bean.getOpenCandle3()
				&& null != bean.getOpenCandle4() && null != bean.getCandle3Type() && null != bean.getCandle4Type()
				&& null != bean.getTradableStateId() && null != bean.getGapUpDownMoveVal()
				&& null != bean.getBuyerSellerDiffVal() && null != bean.getBuyerSellerDiffVal2()) {
			// forward type mother candle
			if (bean.getHighCandle3() > bean.getHighCandle4()) {
				if ((Constants.GREEN_CANDLE.equals(bean.getCandle3Type())
						&& bean.getCloseCandle3() >= bean.getOpenCandle4()
						&& bean.getCloseCandle3() >= bean.getCloseCandle4()
						&& bean.getOpenCandle3() <= bean.getOpenCandle4()
						&& bean.getOpenCandle3() <= bean.getCloseCandle4())
						|| (Constants.RED_CANDLE.equals(bean.getCandle3Type())
								&& bean.getOpenCandle3() >= bean.getOpenCandle4()
								&& bean.getOpenCandle3() >= bean.getCloseCandle4()
								&& bean.getCloseCandle3() <= bean.getOpenCandle4()
								&& bean.getCloseCandle3() <= bean.getCloseCandle4())) {
					if ((Constants.GREEN_CANDLE.equals(bean.getCandle3Type())
							&& Constants.RED_CANDLE.equals(bean.getCandle4Type())
							&& Constants.BUY.equals(bean.getTradableStateId()))
							|| (Constants.RED_CANDLE.equals(bean.getCandle3Type())
									&& Constants.GREEN_CANDLE.equals(bean.getCandle4Type())
									&& Constants.SELL.equals(bean.getTradableStateId()))) {
						//TODO:  getGapUpDownMoveVal may need refactor
						if (bean.getBuyerSellerDiffVal() <= 300 && bean.getBuyerSellerDiffVal2() <= 500
								&& bean.getGapUpDownMoveVal() <= 1500) {
							bean.setTradingRuleForwardEngulfingInd(true);
							
							if ((Constants.GREEN_CANDLE.equals(bean.getCandle3Type())
									&& Constants.RED_CANDLE.equals(bean.getCandle4Type())
									&& Constants.BUY.equals(bean.getTradableStateId())
									&& bean.getOpenCandle3() <= bean.getLowCandle4())
									|| (Constants.RED_CANDLE.equals(bean.getCandle3Type())
											&& Constants.GREEN_CANDLE.equals(bean.getCandle4Type())
											&& Constants.SELL.equals(bean.getTradableStateId())
											&& bean.getOpenCandle3() >= bean.getHighCandle4())) {
								bean.setTradingRuleForwardEngulfingLvl2Ind(true);
								
								if (null != bean.getVwapValue()) {
									if ((Constants.BUY.equals(bean.getTradableStateId())
											&& bean.getBuyAtVal() >= bean.getVwapValue()
													/* && (bean.getOpenCandle4() >= ((bean.getOpenCandle3() +
									 * bean.getCloseCandle3()) / 2)) */) 
										|| (Constants.SELL.equals(bean.getTradableStateId())
											&& bean.getVwapValue() >= bean.getSellAtVal()
											/*&& (bean.getOpenCandle4() <= ((bean.getOpenCandle3()
													+ bean.getCloseCandle3()) / 2))*/ )) {
										bean.setTradingRuleForwardEngulfingLvl3Ind(true);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	//Refactor needed, temporary method to catch engulfing trades
	public boolean waitForEngulfingStrategyRule(StrategyOrbDataBean bean) {
		boolean waitForEngulfingStrategyRule = false;
		try {
		if (null == bean.getWaitForEngulfingCount()) {
			if (null != bean && null != bean.getHighCandle3() && null != bean.getHighCandle4()
					&& null != bean.getCloseCandle3() && null != bean.getCloseCandle4() && null != bean.getOpenCandle3()
					&& null != bean.getOpenCandle4() && null != bean.getCandle3Type() && null != bean.getCandle4Type()
					&& null != bean.getTradableStateId() && null != bean.getGapUpDownMoveVal()
					&& null != bean.getBuyerSellerDiffVal() && null != bean.getBuyerSellerDiffVal2()) {
				// forward type mother candle
				if (bean.getHighCandle3() > bean.getHighCandle4()) {
					if ((Constants.GREEN_CANDLE.equals(bean.getCandle3Type())
							&& bean.getCloseCandle3() >= bean.getOpenCandle4()
							&& bean.getCloseCandle3() >= bean.getCloseCandle4()
							&& bean.getOpenCandle3() <= bean.getOpenCandle4()
							&& bean.getOpenCandle3() <= bean.getCloseCandle4())
							|| (Constants.RED_CANDLE.equals(bean.getCandle3Type())
									&& bean.getOpenCandle3() >= bean.getOpenCandle4()
									&& bean.getOpenCandle3() >= bean.getCloseCandle4()
									&& bean.getCloseCandle3() <= bean.getOpenCandle4()
									&& bean.getCloseCandle3() <= bean.getCloseCandle4())) {
						if ((Constants.GREEN_CANDLE.equals(bean.getCandle3Type())
								&& Constants.RED_CANDLE.equals(bean.getCandle4Type())
								&& Constants.BUY.equals(bean.getTradableStateId()))
								|| (Constants.RED_CANDLE.equals(bean.getCandle3Type())
										&& Constants.GREEN_CANDLE.equals(bean.getCandle4Type())
										&& Constants.SELL.equals(bean.getTradableStateId()))) {
							
							if (bean.getBuyerSellerDiffVal() <= 300 && bean.getBuyerSellerDiffVal2() <= 500
									&& bean.getGapUpDownMoveVal() <= 1500) {
								bean.setTradingRuleForwardEngulfingInd(true);
								bean.setWaitForEngulfingInd(false);
							} else {
								waitForEngulfingStrategyRule = true;
								bean.setWaitForEngulfingInd(waitForEngulfingStrategyRule);
								bean.setWaitForEngulfingCount(1);
							}
						}
					}
				}
			}
		} else {
			if (bean.getBuyerSellerDiffVal() <= 300 && bean.getBuyerSellerDiffVal2() <= 500
					&& bean.getGapUpDownMoveVal() <= 1500) {
				bean.setTradingRuleForwardEngulfingInd(true);
				waitForEngulfingStrategyRule = false;
				bean.setWaitForEngulfingInd(waitForEngulfingStrategyRule);
			} else {
				waitForEngulfingStrategyRule = true;
				bean.setWaitForEngulfingInd(waitForEngulfingStrategyRule);
				bean.setWaitForEngulfingCount(
						null == bean.getWaitForEngulfingCount() ? 1 : bean.getWaitForEngulfingCount() + 1);
			}
		}
		} catch(Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_TRADEWARE_ORDER_PLACEMENT_RULE_TOOL,
					Constants.METHOD_WAIT_FOR_ENGULFING_STRATEGY_RULE, e, Constants.ERROR_SEVERIRITY_FATAL);
		}
		return waitForEngulfingStrategyRule; 
	}
	// Phase 5 :: 05-15-2021 start - afterSomeSuccess
	
	//Phase 3 - 04-02-2021 start
	public void tradingRuleVwapStrong(StrategyOrbDataBean bean) {
		if (((Constants.BUY.equals(bean.getTradableStateId()) && null != bean.getVolumeTradeStateId()
				&& bean.getVolumeTradeStateId().contains(Constants.VWAP_STRONG_BUY))
				|| (Constants.SELL.equals(bean.getTradableStateId()) && null != bean.getVolumeTradeStateId()
						&& bean.getVolumeTradeStateId().contains(Constants.VWAP_STRONG_SELL)))) {

			if (null != bean.getGapUpDownMoveVal() && null != bean.getBuyerSellerDiffVal()
					&& bean.getGapUpDownMoveVal() < 500 && bean.getBuyerSellerDiffVal() < 1000) {
				/*
				 * if (null != bean.getCandleTypeTrendId() && null != bean.getOhlcStateId() &&
				 * bean.getCandleTypeTrendId().equals(bean.getOhlcStateId()) &&
				 * bean.getTradableStateId().equals(bean.getOhlcStateId())) {
				 * bean.setTradingRuleOHLCAnd3TimesStrenthInd(true); }
				 */
				bean.setTradingRuleOpenBtwnAvgHiLoClsAndVwapInd(true);
			}
		}
	}
	public void tradingRuleCandleTypeTrendEqOhlcStateAndPrevHrCrossAdditional(StrategyOrbDataBean bean) {
		if ((null != bean.getCandleTypeTrendId() && null != bean.getTradableStateId() && null != bean.getOhlcStateId()
				&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId()))) {
			
			if (null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd()) {
				
				if ((!getOhlcStateList().contains(bean.getOhlcStateId())) || (Constants.BUY.equals(bean.getOhlcStateId())
						&& Constants.RED_CANDLE.equals(bean.getCandle2Type())) || (Constants.SELL.equals(bean.getOhlcStateId())
								&& Constants.GREEN_CANDLE.equals(bean.getCandle2Type()))) {
					bean.setTradingRuleCandleTypeTrendAndPrevHrCrossInd(true);
				}
				
			}
			
		}
	}
	private static List<String> ohlcList = new ArrayList<String>();

	private List<String> getOhlcStateList() {
		if (ohlcList.isEmpty()) {
			//ohlcList.add(Constants.BUY_SPIKE_1);
			//ohlcList.add(Constants.SELL_SPIKE_1);
			//ohlcList.add(Constants.BUY_SPIKE_1DOT5);
			//ohlcList.add(Constants.SELL_SPIKE_1DOT5);
			//ohlcList.add(Constants.BUY_SPIKE_2);
			//ohlcList.add(Constants.SELL_SPIKE_2);
			ohlcList.add(Constants.BUY_SPIKE_2DOT5);
			ohlcList.add(Constants.SELL_SPIKE_2DOT5);
			ohlcList.add(Constants.BUY_SPIKE_3);
			ohlcList.add(Constants.SELL_SPIKE_3);
			ohlcList.add(Constants.BUY_SELL_SPIKE_3);
		}
		return ohlcList;
	}

	public void tradingRuleVolumeStrengthTradeStateIdInd(StrategyOrbDataBean bean) {
		if ((Constants.BUY.equals(bean.getTradableStateId()) && (Constants.BUY3.equals(bean.getVolumeTradeStateId())
				|| Constants.BUY4.equals(bean.getVolumeTradeStateId())
				|| Constants.BUY5.equals(bean.getVolumeTradeStateId())
				|| Constants.BUY6.equals(bean.getVolumeTradeStateId())
				|| Constants.BUY7.equals(bean.getVolumeTradeStateId())))
				|| (Constants.SELL.equals(bean.getTradableStateId())
						&& (Constants.SEL3.equals(bean.getVolumeTradeStateId())
								|| Constants.SEL4.equals(bean.getVolumeTradeStateId())
								|| Constants.SEL5.equals(bean.getVolumeTradeStateId())
								|| Constants.SEL6.equals(bean.getVolumeTradeStateId())
								|| Constants.SEL7.equals(bean.getVolumeTradeStateId())))) {
			bean.setTradingRuleVolumeTradeStateInd(true);
		}
	}
	
	public void tradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd(StrategyOrbDataBean bean) {
		if (null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd()
				&& bean.getTradableStateId().equals(bean.getCandleTypeTrendId())) {
			bean.setTradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd(true);
		}
	}
	
	//04-21-2021  start - afterSomeSuccess
	public void tradingRuleProfitableBuyerSellerDiff(StrategyOrbDataBean bean) {
		if ((bean.getBuyerSellerDiffVal() != null && bean.getBuyerSellerDiffVal() <=200 
				&& bean.getBuyerSellerDiffVal2() != null && bean.getBuyerSellerDiffVal2() < 250 )
				&& tradewareTool.isTimeBefore03_20PM()) {
			bean.setTradingRuleProfitableBuyerSellerDiffInd(true);
		}
	}
	//customRuleStrongVolumeFilterVwap
	public void tradingRuleProfitableCustomeStrongVolumeVWap(StrategyOrbDataBean bean) {
		if ((Constants.BUY.equals(bean.getTradableStateId())
				&& (null != bean.getVwapValue() && null != bean.getBuyAtVal()
						&& bean.getVwapValue() < bean.getBuyAtVal())
				&& (((bean.getVwapValue() - bean.getBuyAtVal())
						* bean.getLotSize()) <= Constants.TARGET_CO_STOP_LOSS_PROFIT_7500))
				|| (Constants.SELL.equals(bean.getTradableStateId())
						&& (null != bean.getVwapValue() && null != bean.getBuyAtVal()
								&& bean.getVwapValue() > bean.getSellAtVal())
						&& (((bean.getVwapValue() - bean.getSellAtVal())
								* bean.getLotSize()) <= Constants.TARGET_CO_STOP_LOSS_PROFIT_7500))

		) {
			boolean strongVolumeVwapInd = ((null != bean.getTradingRuleVolumeTradeStateInd()
					&& bean.getTradingRuleVolumeTradeStateInd()) &&
					(null != bean.getTradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd()
							&& bean.getTradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd()));
			bean.setTradingRuleProfitableCustomeStrongVolumeVwapInd(strongVolumeVwapInd);
		}
	}
	//04-21-2021  end

	public void verifyStochasticBasicRule(StrategyOrbDataBean bean) {
//		if (null != bean && null != bean.getTradableStateId() && null != bean.getGapUpDownMoveVal()
//				&& null != bean.getBuyerSellerDiffVal() && null != bean.getBuyerSellerDiffVal2()) {
//			if (bean.getBuyerSellerDiffVal() <= 500 && bean.getBuyerSellerDiffVal2() <= 750
//					&& bean.getGapUpDownMoveVal() <= 2500) {
				if (null != bean && null != bean.getMin5StochasticValK1() && null != bean.getMin15StochasticValK1()
						&& null != bean.getMin60StochasticValK1()) {
					if ((Constants.BUY.equals(bean.getTradableStateId()) && (bean.getMin5StochasticValK1() < 40
							|| bean.getMin15StochasticValK1() < 40 || bean.getMin60StochasticValK1() < 40))
							|| (Constants.SELL.equals(bean.getTradableStateId()) && (bean.getMin5StochasticValK1() > 60
									|| bean.getMin15StochasticValK1() > 60 || bean.getMin60StochasticValK1() > 60))) {
						bean.setStochasticBasicRule1Ind(true);
					}
				}
			/*}
		}*/
	}
	
	public StrategyOrbDataBean placeTradeOrder(StrategyOrbDataBean bean) {
		try {
		//if ( TradewareTool.todayTradingGoInd) {
			verifyStochasticBasicRule(bean);
			
			// Need to check to update TradePlaceRule
			// Phase 5 :: 05-15-2021 start - afterSomeSuccess
			verifyEngulfingStrategyRule(bean);
			if (null != bean.getTradingRuleForwardEngulfingInd() && bean.getTradingRuleForwardEngulfingInd()) {
				bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_ENGULFING_FORWARD_PROD);
				placeTradeOrderIfNotAlreadyPlaced(bean);
			}
			// Phase 5 :: 05-15-2021 end - afterSomeSuccess
			
			// Phase 4 :: 05-09-2021 start - afterSomeSuccess
			
			verifySMAandVWAPformulaLevel2Min5Prod( bean);
			if (null  != bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd() &&
					bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd()) {
				bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_SMA_VWAP_LVL2_MIN5_PROD);
				placeTradeOrderIfNotAlreadyPlaced(bean);
			}
			verifySMAandVWAPformulaLevel2Prod(bean);
			if (null  != bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd() &&
					bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd()) {
				//TODO::::::::::::::
				if (null != bean.getTradePlacedRuleInd() && bean.getTradePlacedRuleInd()
						&& (null != bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd()
								&& bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd())) {
					/*bean.setTradePlacedRuleInd(false);
					bean.setTradedLotCount(bean.getTradedLotCount()+1);*/
					bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_SMA_VWAP_LVL2_MIN1_PROD);
					placeTradeOrderIfNotAlreadyPlaced(bean);
					
				} else {
					bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_SMA_VWAP_LVL2_MIN1_PROD);
					placeTradeOrderIfNotAlreadyPlaced(bean);
				}
			}
			// Phase 4 :: 05-09-2021 end - afterSomeSuccess
			
			//04-21-2021  start - afterSomeSuccess
			tradingRuleProfitableBuyerSellerDiff(bean);
			if (null != bean.getTradingRuleProfitableBuyerSellerDiffInd()
					&& bean.getTradingRuleProfitableBuyerSellerDiffInd()) {
				bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_INIT_PROFITABLE_PROD_RULE);
				placeTradeOrderIfNotAlreadyPlaced(bean);
			}
			//04-21-2021  end
			
			
			tradingRuleCandleTypeTrendEqOhlcStateAndPrevHrCross(bean);
			tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCross(bean);
			verifySMAandVWAPformulaLevel1(bean);
			verifySMAandVWAPformulaLevel2(bean);
			verifySMAandVWAPformulaLevel2Min5(bean);
			tradingRuleCandleTypeTrendEqOhlcStateAndPrevHrCrossAdditional(bean);
			tradingRuleVolumeStrengthTradeStateIdInd(bean);
			tradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd(bean);
			tradingRuleProfitableCustomeStrongVolumeVWap(bean);
			
			// Existing logic
			//if (null == bean.getTradePlacedRuleInd() || !bean.getTradePlacedRuleInd()) {
				if (null != bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd()
						&& bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd() ) {
					bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_SMA_VWAP_LVL1);
				}
				if (null != bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1Min5RuleInd()
						&& bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1Min5RuleInd() ) {
					bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_SMA_VWAP_LVL1_MIN5);
				}
				if ((null != bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd()
						&& bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd())
						&& (null != bean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd()
								&& bean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd())) {
					if (null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd()) {
						if (bean.getGapUpDownMoveVal().doubleValue() < 500) {
							// bean.setTradePlacedRuleInd(true);
							// bean = kiteConnectTool.placeCoverOrder(bean);
							bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_SMA_VWAP_LVL2);
						}
					}
				}
				//04-21-2021  start - afterSomeSuccess [05-06-2021]
				if ((null != bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd()
						&& bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd())
						&& (null != bean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd()
								&& bean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd())) {
					if (null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd()) {
						if (bean.getGapUpDownMoveVal().doubleValue() < 2500) {
							// bean.setTradePlacedRuleInd(true);
							// bean = kiteConnectTool.placeCoverOrder(bean);
							bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_SMA_VWAP_LVL2_MIN5);
						}
					}
				}
				//04-21-2021  end - afterSomeSuccess [05-06-2021]
			//}
			
			if ((null != bean.getTradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd()
					&& bean.getTradingRulePrevDayHrCrossAndCandleTrendEqTradableStateInd())
					&& (null != bean.getTradingRuleVolumeTradeStateInd() && bean.getTradingRuleVolumeTradeStateInd())) {
				bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_CUSTOM_STRONG_VOLUME);
				placeTradeOrderIfNotAlreadyPlaced(bean);
			}
			
			if ((null != bean.getTradingRuleProfitableCustomeStrongVolumeVwapInd()
					&& bean.getTradingRuleProfitableCustomeStrongVolumeVwapInd())) {
				bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_CUSTOM_STRONG_VOLUME_VWAP);
				placeTradeOrderIfNotAlreadyPlaced(bean);
			}
			
			// for custom7times rule
			//if (null == bean.getTradePlacedRuleInd() || !bean.getTradePlacedRuleInd()) {
				tradingRule7TimesStrenthCustom(bean);
				tradingRule7TimesStrenthAdditional(bean);
				if (null != bean.getTradingRule7TimesStrengthInd() && bean.getTradingRule7TimesStrengthInd()
						&& null != bean.getTradingRule7TimesStrenthAdditionalInd()
						&& bean.getTradingRule7TimesStrenthAdditionalInd()) {
					placeTradeOrderIfNotAlreadyPlaced(bean);
					bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_7TIMES);
					
					if(null != bean.getOhlcStateId() &&  bean.getTradableStateId().equals(bean.getOhlcStateId())) {
						bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_7TIMES_OHLC);
					}
				}
			//}

			// for custom rule 2 - FLTR_7.Custom Rule2 $$$$ SMA/VWAP
			/*if (null == bean.getTradePlacedRuleInd() || !bean.getTradePlacedRuleInd()) {
				sathvikAshvikTechTraderTool.tradingRuleOpenBtwnAvgHiLoClsAndVwapCustom(bean);
				sathvikAshvikTechTraderTool.tradingRuleCandleTypeTrendEqOhlcStateAndPrevHrCross(bean);
				if (null != bean.getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd()
						&& bean.getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd()
						&& null != bean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd()
						&& bean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd()) {
					bean.setTradePlacedRuleInd(true);
					bean = kiteConnectTool.placeCoverOrder(bean);
				}
			}*/
				
			//if (null == bean.getTradePlacedRuleInd() || !bean.getTradePlacedRuleInd()) {
				tradingRuleOpenBtwnAvgHiLoClsAndVwapCustom(bean);
				if (null != bean.getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd()
						&& bean.getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd()) {
					if (null != bean.getPrevDayHrCrossInd() && bean.getPrevDayHrCrossInd()) {
						placeTradeOrderIfNotAlreadyPlaced(bean);
						bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_OPGTAVGLO_OPLTAVGHI);
					}
					if(null != bean.getOhlcStateId() &&  bean.getTradableStateId().equals(bean.getOhlcStateId())) {
						bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_7TIMES_OHLC);
					}
				}
			//}
			// for FLTR_VWAP_STRONG 
			if (null == bean.getTradePlacedRuleInd() || !bean.getTradePlacedRuleInd()) {
				tradingRuleVwapStrong(bean);
				//tradingRuleCandleTypeTrendEqOhlcStateAndPrevHrCrossAdditional(bean);
				if (null != bean.getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd()
						&& bean.getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd()
						&& null != bean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd()
						&& bean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd()) {
					placeTradeOrderIfNotAlreadyPlaced(bean);
					bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_VWAP_STRONG);
					if (null != bean.getOhlcStateId() && bean.getTradableStateId().equals(bean.getOhlcStateId())) {
						bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_VWAP_STRONG_OHLC);
					}
				} else if (null != bean.getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd()
						&& bean.getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd()) {
					bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_VWAP_STRONG_OBSERVE);
				}
			}
			
			tradingRuleOHLCAnd3TimesStrength(bean);
			tradingRuleCandleTypeTrendEqOhlcStateAndPrevHrCross(bean);
			if (null != bean.getTradingRuleOHLCAnd3TimesStrengthInd() && bean.getTradingRuleOHLCAnd3TimesStrengthInd()
					&& null != bean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd()
					&& bean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd()) {
				if (bean.getGapUpDownMoveVal().doubleValue() < 1000) {
					// placeTradeOrderIfNotAlreadyPlaced(bean);
					bean.setTradePlaceRule(Constants.TRADE_PLACE_RULE_3TIMES_OHLC);
				}
			}
		//}
			
		} catch(Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_TRADEWARE_ORDER_PLACEMENT_RULE_TOOL,
					Constants.METHOD_PLACE_TRADE_ORDER, e, Constants.ERROR_SEVERIRITY_FATAL);
		}
		return bean;
	}
	
	private void placeTradeOrderIfNotAlreadyPlaced(StrategyOrbDataBean bean) {
		if (null == bean.getTradePlacedRuleInd() || !bean.getTradePlacedRuleInd()) {
			tradewareTool.checkTodayTradingNotForceClosedByCrossMaxLossLimit();
			if (TradewareTool.todayTradingGoInd && TradewareTool.todayTradingNotForceClosedInd) {
				// TODO: need to refactor, this additional code to filter,unneccessary double
				// filter,need to handle very before.
				/*if (bean.getTradePlaceRule().contains(Constants.TRADE_PLACE_RULE_INIT_PROFITABLE_PROD_RULE)
						|| bean.getTradePlaceRule().contains(Constants.TRADE_PLACE_RULE_CUSTOM_STRONG_VOLUME)) {
					bean.setTradePlacedRuleInd(true);
					bean = kiteConnectTool.placeCoverOrder(bean);
					
					TwilioSendSmsTool.sendTradeEntrySms(tradewareTool.getTradeEntryMessage(bean));
				}*/
				
				//TODO:::::STILL NEED TO VALIDATE  *********
				/*if (null != bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd()
						&& bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd()
						&& Constants.BUY.equals(bean.getTradableStateId())) {
					bean.setTradePlacedRuleInd(true);
					bean = kiteConnectTool.placeCoverOrder(bean);
					
					TwilioSendSmsTool.sendTradeEntrySms(tradewareTool.getTradeEntryMessage(bean));
				}*/
				/*if ((null != bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd()
						&& bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd())
						|| (null != bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd()
								&& bean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd())) {
					if (Constants.BUY.equals(bean.getTradableStateId())) {
						bean.setTradePlacedRuleInd(true);
						bean = kiteConnectTool.placeCoverOrder(bean);

						TwilioSendSmsTool.sendTradeEntrySms(tradewareTool.getTradeEntryMessage(bean));
					}
				}*/
				
				//04-21-2021  start - afterSomeSuccess [05-16-2021]
				if (null != bean.getTradingRuleForwardEngulfingInd() && bean.getTradingRuleForwardEngulfingInd()
						&& bean.getTradePlaceRule().contains(Constants.TRADE_PLACE_RULE_ENGULFING_FORWARD_PROD)) {
					//04-21-2021  start - afterSomeSuccess [05-31-2021]
					/*bean.setTradePlacedRuleInd(true);
					bean = kiteConnectTool.placeCoverOrder(bean);*/
					if ((null != bean.getTradingRuleForwardEngulfingLvl2Ind()
							&& bean.getTradingRuleForwardEngulfingLvl2Ind())
							|| (null != bean.getTradingRuleForwardEngulfingLvl3Ind()
									&& bean.getTradingRuleForwardEngulfingLvl3Ind())) {
						bean.setTradePlacedRuleInd(true);
						bean = kiteConnectTool.placeCoverOrder(bean);
					}
					//04-21-2021  ends - afterSomeSuccess [05-31-2021]
					TwilioSendSmsTool.sendTradeEntrySms(tradewareTool.getTradeEntryMessage(bean));
				}
				//04-21-2021  end - afterSomeSuccess [05-16-2021]
			}
		}
	}
	//Phase 3 - 04-02-2021 end
}
