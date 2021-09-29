package com.tradeware.stockmarket.scanner;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.traderules.TradewareOrderPlacementRuleTool;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;

public abstract class AbstractTradewareScanner extends Thread implements Constants {

	protected int loopCounter;
	@Autowired
	protected TradewareTool tradewareTool;
	@Autowired
	protected KiteConnectTool kiteConnectTool;
	@Autowired
	protected DatabaseHelper databaseHelper;
	
	@Autowired
	protected TradewareOrderPlacementRuleTool tradewareOrderPlacementRuleTool;

	protected boolean validateBuyerSellerRule(StrategyOrbDataBean bean, String mode, ConcurrentHashMap<String, StrategyOrbDataBean> map) {
		boolean buyerSellerRule = true;
		if (tradewareOrderPlacementRuleTool.verifySMAandVWAPformulaLevel1(bean)
				|| (bean.getOhlcStateId() != null
						&& (Constants.BUY.equals(bean.getOhlcStateId()) || Constants.SELL.equals(bean.getOhlcStateId())
								|| Constants.BUY_SPIKE.equals(bean.getOhlcStateId())
								|| Constants.SELL_SPIKE.equals(bean.getOhlcStateId())
								|| Constants.BUY_SPIKE_1.equals(bean.getOhlcStateId())
								|| Constants.SELL_SPIKE_1.equals(bean.getOhlcStateId())
								//04-21-2021  start - afterSomeSuccess
								|| Constants.BUY_SPIKE_1DOT5.equals(bean.getOhlcStateId())
								|| Constants.SELL_SPIKE_1DOT5.equals(bean.getOhlcStateId())
								|| Constants.BUY_SPIKE_2.equals(bean.getOhlcStateId())
								|| Constants.SELL_SPIKE_2.equals(bean.getOhlcStateId())
								|| Constants.BUY_SPIKE_2DOT5.equals(bean.getOhlcStateId())
								|| Constants.SELL_SPIKE_2DOT5.equals(bean.getOhlcStateId())
								//04-21-2021  end
								))
				|| (bean.getStrategyRule() != null && (Constants.STRATEGY_NR7_R1.equals(bean.getStrategyRule())
						|| Constants.STRATEGY_NR7_R2.equals(bean.getStrategyRule())
						|| Constants.STRATEGY_ORB_R1.equals(bean.getStrategyRule())
						|| Constants.STRATEGY_ORB_R2.equals(bean.getStrategyRule())))) {
			
			double buyerSellerDiff = kiteConnectTool
					.getRoundupToTwoValue((bean.getSellerAt() - bean.getBuyerAt()) * bean.getLotSize());
			bean.setBuyerSellerDiffVal(buyerSellerDiff);
			bean.setBuyerSellerDiffVal2(
					kiteConnectTool.getRoundupToTwoValue(bean.getBuyerSellerDiffVal2()));
			bean.setBuyerSellerDiffVal3(
					kiteConnectTool.getRoundupToTwoValue(bean.getBuyerSellerDiffVal3()));
			/*if (bean.getSellerAt() == 0 || bean.getBuyerAt() == 0 || buyerSellerDiff < 0 || buyerSellerDiff > 1000) {// 750 500 ) {
				bean.setSignalTriggeredInAt(
						tradewareTool.getCurrentTime() + Constants.BUYER_SELLER_DIFF_FAILED_MSG + buyerSellerDiff);
				bean.setTradedStateId(Constants.BUYER_SELLER_DIFF_FAILED);
				bean.setTradedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
				databaseHelper.saveTradeOnEntry(bean);
				map.remove(bean.getKiteFutureKey());
				buyerSellerRule = false;
			}*/
			
			//04-21-2021  start - afterSomeSuccess
			if (bean.getSellerAt() == 0 || bean.getBuyerAt() == 0 || buyerSellerDiff < 0 || buyerSellerDiff >  750) {
				//wanted to run until criteria match
				buyerSellerRule = false;
			}
			//04-21-2021  end  
			if ((null != bean.getCandle2SizeAmt() && bean.getCandle2SizeAmt() < 1500 /*2500*/)
					&& !(Constants.NIFTY_50.equals(bean.getSymbolId()) || Constants.BANKNIFTY.equals(bean.getSymbolId())
							|| Constants.FINNIFTY.equals(bean.getSymbolId()))) {
				bean.setTradedStateId(Constants.CANDLE_MOVEMENT_RULE_FAILED);
				bean.setTradedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());

				databaseHelper.saveTradeOnEntry(bean);
				map.remove(bean.getKiteFutureKey());
				buyerSellerRule = false;
			}
			if (buyerSellerRule) {
				/*Double buyAtVal = bean.isFirstLevelSmaVwapRuleInd() ? bean.getCurrentCandleAvgHigh()
						: bean.getBuyAtVal();
				Double sellAtVal = bean.isFirstLevelSmaVwapRuleInd() ? bean.getCurrentCandleAvgLow()
						: bean.getSellAtVal();*/
				Double buyAtVal = bean.getBuyAtVal();
				Double sellAtVal = bean.getSellAtVal();
				if (Constants.BUY.equals(mode)) {
					bean.setGapUpDownMoveVal(kiteConnectTool.getRoundupToTwoValue(
							(bean.getSellerAt() - /* bean.getBuyAtVal() */buyAtVal) * bean.getLotSize()));
				} else if (Constants.SELL.equals(mode)) {
					bean.setGapUpDownMoveVal(kiteConnectTool.getRoundupToTwoValue(
							(/* bean.getSellAtVal() */sellAtVal - bean.getBuyerAt()) * bean.getLotSize()));
				}
				
				if (bean.getGapUpDownMoveVal() > 3500) {
					bean.setTradedStateId(Constants.CANDLE_GAP_UP_DOWN_MOVEMENT_FAILED);

					bean.setTradedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
					databaseHelper.saveTradeOnEntry(bean);
					map.remove(bean.getKiteFutureKey());
					buyerSellerRule = false;
				}
			}
			
			//04-21-2021  start - afterSomeSuccess [05-17-2021]
			if (buyerSellerRule) {
				buyerSellerRule = !tradewareOrderPlacementRuleTool.waitForEngulfingStrategyRule(bean);
			}
			//04-21-2021  end - afterSomeSuccess [05-17-2021]
			
		} else {
			buyerSellerRule = false;
			map.remove(bean.getKiteFutureKey());
		}
		return buyerSellerRule;
	}
	
	protected void executeBuyWorkflow(StrategyOrbDataBean bean,
			ConcurrentHashMap<String, StrategyOrbDataBean> map) {
		bean.setTradableStateId(Constants.BUY);
		bean.setTradedStateId(Constants.BUY);
		bean.setTradedAtVal(bean.getSellerAt());
		bean.setTradedAtAvgVal(bean.getSellerAt());
		bean.setTradedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
		bean.setSignalTriggeredInAt(tradewareTool.getCurrentTime()
				+ Constants.BUY_ACTIVATED_MSG + bean.getSellerAt());

		/*OptionChainDataBean oiDataBean = databaseHelper
				.findSymbolByLatestOptinChainData(bean.getSymbolName().replaceAll(Constants.FUTURE_SYMBOL_REMOVE, Constants.EMPTY_STRING));
		String str = null == oiDataBean ? "oiDataBean is NULL" : oiDataBean.toString();
		
		if (null != oiDataBean) {
			bean.setOptionChainId(oiDataBean.getOptionChainId());
			bean.setOptionChainTrend(oiDataBean.getOITrend());
			bean.setOptionChainPriceTrend(oiDataBean.getPriceMoveTrend() + Constants.SPACE
					+ tradewareTool.getGivenDateTimeAsAMPM(oiDataBean.getDateTimeStamp()));
		} else {
			TradewareLogger.saveInfoLog("OI_DATA_TEST", "OI_DATA_TEST", str);
		}*/
		if (null != bean.getBuyerQuantityVal() && null != bean.getSellerQuantityVal()) {
			bean.setBuySellQuantityRatio(kiteConnectTool
					.getRoundupToTwoValue(bean.getBuyerQuantityVal() / bean.getSellerQuantityVal()));
		}
		bean = tradewareTool.handleTrailingStopLoss(bean);
		bean.setPrevDayHrCrossInd(null != bean.getPrevDayHrBuyAtVal() && bean.getPrevDayHrBuyAtVal() <= bean.getBuyAtVal());
		//May be change the place and add Previous day level cross also
		bean.setPercentageChange(tradewareTool.getchangePercentage(bean.getLtp(), bean.getClose()));
		/*tradewareOrderPlacementRuleTool.tradingRuleCandleTypeTrendEqOhlcStateAndPrevHrCross(bean);
		tradewareOrderPlacementRuleTool.tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCross(bean);
		tradewareOrderPlacementRuleTool.verifySMAandVWAPformulaLevel2(bean);
		tradewareOrderPlacementRuleTool.tradingRuleOHLCAnd3TimesStrength(bean);
		tradewareOrderPlacementRuleTool.tradingRuleOpenBtwnAvgHiLoClsAndVwapCustom(bean);
		tradewareOrderPlacementRuleTool.tradingRule7TimesStrenthCustom(bean);
		tradewareOrderPlacementRuleTool.tradingRule7TimesStrenthAdditional(bean);
		
		if(TradewareTool.todayTradingGoInd) {
			bean = placeTradeOrder(bean);
		}*/
		tradewareOrderPlacementRuleTool.placeTradeOrder(bean);
		
		Integer itemId = databaseHelper.saveTradeOnEntry(bean);
		bean.setItemId(itemId);
		
		map.replace(bean.getKiteFutureKey(), bean);
	}
	
	protected void executeSellWorkflow(StrategyOrbDataBean bean,
			ConcurrentHashMap<String, StrategyOrbDataBean> map) {
		bean.setTradableStateId(Constants.SELL);
		bean.setTradedStateId(Constants.SELL);
		bean.setTradedAtVal(bean.getBuyerAt());
		bean.setTradedAtAvgVal(bean.getBuyerAt());
		bean.setTradedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
		bean.setSignalTriggeredInAt(tradewareTool.getCurrentTime()
				+ Constants.SELL_ACTIVATED_MSG + bean.getBuyerAt());

	/*OptionChainDataBean oiDataBean = databaseHelper
				.findSymbolByLatestOptinChainData(bean.getSymbolName().replaceAll(Constants.FUTURE_SYMBOL_REMOVE, Constants.EMPTY_STRING));
		String str = null == oiDataBean ? "oiDataBean is NULL" : oiDataBean.toString();
		if (null != oiDataBean) {
			bean.setOptionChainId(oiDataBean.getOptionChainId());
			bean.setOptionChainTrend(oiDataBean.getOITrend());
			bean.setOptionChainPriceTrend(oiDataBean.getPriceMoveTrend() + Constants.SPACE
					+ tradewareTool.getGivenDateTimeAsAMPM(oiDataBean.getDateTimeStamp()));
		} else {
			TradewareLogger.saveInfoLog("OI_DATA_TEST", "OI_DATA_TEST", str);
		}*/

		if (null != bean.getBuyerQuantityVal() && null != bean.getSellerQuantityVal()) {
			bean.setBuySellQuantityRatio(kiteConnectTool
					.getRoundupToTwoValue(bean.getSellerQuantityVal()/bean.getBuyerQuantityVal()));
		}
		bean = tradewareTool.handleTrailingStopLoss(bean);
		bean.setPrevDayHrCrossInd(null != bean.getPrevDayHrSellAtVal() && bean.getPrevDayHrSellAtVal() >= bean.getSellAtVal());
		//May be change the place and add Previous day level cross also
		bean.setPercentageChange(tradewareTool.getchangePercentage(bean.getLtp(), bean.getClose()));
		/*tradewareOrderPlacementRuleTool.tradingRuleCandleTypeTrendEqOhlcStateAndPrevHrCross(bean);
		tradewareOrderPlacementRuleTool.tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCross(bean);
		tradewareOrderPlacementRuleTool.verifySMAandVWAPformulaLevel2(bean);
		tradewareOrderPlacementRuleTool.tradingRuleOHLCAnd3TimesStrength(bean);
		tradewareOrderPlacementRuleTool.tradingRuleOpenBtwnAvgHiLoClsAndVwapCustom(bean);
		tradewareOrderPlacementRuleTool.tradingRule7TimesStrenthCustom(bean);
		tradewareOrderPlacementRuleTool.tradingRule7TimesStrenthAdditional(bean);
		
		if( TradewareTool.todayTradingGoInd) {
			bean = placeTradeOrder(bean);
		}*/
		tradewareOrderPlacementRuleTool.placeTradeOrder(bean);
		
		Integer itemId = databaseHelper.saveTradeOnEntry(bean);
		bean.setItemId(itemId);
		
		map.replace(bean.getKiteFutureKey(), bean);
	}
	
	protected void buyOrderProfitLossMonitorScan(StrategyOrbDataBean bean,
			ConcurrentHashMap<String, StrategyOrbDataBean> map) {
		double lastPrice =bean.getBuyerAt();
			//bean = tradewareTool.handleStopLossValue2(bean);
			 bean = tradewareTool.handleTrailingStopLossValue(bean);
			if ((bean.getBuyerAt() > bean.getTargetPrice()) || null != bean.getProfitChaseVal()
					|| (null != bean.getProfitLossAmtVal() && bean.getProfitLossAmtVal() > 7500) ) {
				if (null == bean.getProfitChaseVal() || bean.getBuyerAt() > bean.getProfitChaseVal().doubleValue()) {
					bean.setProfitChaseVal(bean.getBuyerAt());
					//continue;
				} else {
				bean.setTradedStateId(Constants.BUY_EXIT_PROFIT);
				bean.setExitedAtVal(bean.getBuyerAt());
				bean.setExitedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());

				double netProfitVal = kiteConnectTool
						.getRoundupToTwoValue((bean.getBuyerAt() - bean.getTradedAtAvgVal().doubleValue())
								* (bean.getLotSize() * bean.getTradedLotCount()));
				bean.setProfitLossAmtVal(netProfitVal);
				bean.setSignalTriggeredOutAt(tradewareTool.getCurrentTime() + Constants.BUY_TARGET + bean.getBuyerAt()
						+ Constants.WITH_PROFIT + netProfitVal);

				// kiteConnectTool.cancelBracketOrder(bean);
				bean = kiteConnectTool.cancelCoverOrder(bean);
				databaseHelper.updateTrade(bean);
				map.remove(bean.getKiteFutureKey());
				}
			} else /*
					 * if (bean.getBuyerAt() <= stopLoss bean.getStopLoss() || (!isSpecialTrade &&
					 * null != bean.getNegativeDirectionLoss() && bean.getNegativeDirectionLoss()
					 * .doubleValue() <= -Constants.TARGET_SAFE_STOP_LOSS)) {
					 */
		if (/*bean.getBuyerAt() <= bean.getStopLoss()
				|| */
				(null != bean.getStopLoss() && 0 != bean.getStopLoss() && bean.getBuyerAt() <= bean.getStopLoss()) ||
				(null != bean.getNegativeDirectionLoss() && bean.getNegativeDirectionLoss()
				.doubleValue() <= (tradewareTool.isIndexOrOptionTrade(bean) ? -Constants.TARGET_SAFE_STOP_LOSS_1200
						: -Constants.TARGET_SAFE_STOP_LOSS_2500)) ) {
				bean.setExitedAtVal(bean.getBuyerAt());
				bean.setExitedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
				double netLossVal = kiteConnectTool
						.getRoundupToTwoValue((bean.getBuyerAt() - bean.getTradedAtAvgVal().doubleValue())
								* (bean.getLotSize() * bean.getTradedLotCount()));
				bean.setProfitLossAmtVal(netLossVal);
				if (netLossVal <= 0) {
					bean.setTradedStateId(Constants.BUY_EXIT_LOSS);
					bean.setSignalTriggeredOutAt(tradewareTool.getCurrentTime() + Constants.CLOSED_WITH_LOSE
							+ bean.getBuyerAt() + Constants.WITH_LOSS + netLossVal);
				} else {
					bean.setTradedStateId(Constants.BUY_EXIT_PROFIT);
					bean.setSignalTriggeredOutAt(tradewareTool.getCurrentTime() + Constants.BUY_TARGET
							+ bean.getBuyerAt() + Constants.WITH_PROFIT + netLossVal);
				}

				// kiteConnectTool.cancelBracketOrder(bean);
				bean = kiteConnectTool.cancelCoverOrder(bean);
				databaseHelper.updateTrade(bean);

				map.remove(bean.getKiteFutureKey());
			} 
			/*
			 * else if (bean.getTradedLotCount() < 2 && (null !=
			 * bean.getNegativeDirectionLoss() &&
			 * bean.getNegativeDirectionLoss().doubleValue() <=
			 * -Constants.TARGET_MAX_FIRST_LOSS)) { adjustTradings(bean, map); } else if
			 * (bean.getTradedLotCount() < 3 && (null != bean.getNegativeDirectionLoss() &&
			 * bean.getNegativeDirectionLoss().doubleValue() <=
			 * -Constants.TARGET_MAX_SECOND_LOSS)) { adjustTradings(bean, map);
			 }*/ else {
			trackAndUpdatePositiveNegativeMoves(bean, lastPrice, map);
		}
	}
	
	protected void sellOrderProfitLossMonitorScan(StrategyOrbDataBean bean,
			ConcurrentHashMap<String, StrategyOrbDataBean> map) {
		double lastPrice =bean.getSellerAt();;
			//bean = tradewareTool.handleStopLossValue2(bean);
		     bean = tradewareTool.handleTrailingStopLossValue(bean);
			if ((bean.getSellerAt() < /* targetPrice */bean.getTargetPrice())
					|| null != bean.getProfitChaseVal() || (null != bean.getProfitLossAmtVal() && bean.getProfitLossAmtVal() > 7500)  ) {
				if (null == bean.getProfitChaseVal()
						|| bean.getSellerAt() < bean.getProfitChaseVal().doubleValue()) {
					bean.setProfitChaseVal(bean.getSellerAt());
					//continue;
				} else {
				bean.setTradedStateId(Constants.SELL_EXIT_PROFIT);
				bean.setExitedAtVal(bean.getSellerAt());
				bean.setExitedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
				double netProfitVal = kiteConnectTool.getRoundupToTwoValue(
						(bean.getTradedAtAvgVal().doubleValue() - bean.getSellerAt()) * (bean.getLotSize() * bean.getTradedLotCount()));
				bean.setProfitLossAmtVal(netProfitVal);
				bean.setSignalTriggeredOutAt(tradewareTool.getCurrentTime()
						+ Constants.SELL_TARGET + bean.getSellerAt() + Constants.WITH_PROFIT + netProfitVal);

				// kiteConnectTool.cancelBracketOrder(bean);
				bean = kiteConnectTool.cancelCoverOrder(bean);
				databaseHelper.updateTrade(bean);
				map.remove(bean.getKiteFutureKey());
				}
			} else /*if (bean.getSellerAt() >= bean.getStopLoss()
					|| (!isSpecialTrade &&  null != bean.getNegativeDirectionLoss() && bean.getNegativeDirectionLoss()
							.doubleValue() <= -Constants.TARGET_SAFE_STOP_LOSS)) {*/
				if (/* bean.getSellerAt() >= bean.getStopLoss() ||  */
						(null != bean.getStopLoss() && 0 != bean.getStopLoss() && bean.getSellerAt() >= bean.getStopLoss()) ||
						(null != bean.getNegativeDirectionLoss() && bean.getNegativeDirectionLoss()
				.doubleValue() <= (tradewareTool.isIndexOrOptionTrade(bean) ? -Constants.TARGET_SAFE_STOP_LOSS_1200
						: -Constants.TARGET_SAFE_STOP_LOSS_2500)) ) {
					
				bean.setExitedAtVal(bean.getSellerAt());
				bean.setExitedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
				double netLossVal = kiteConnectTool.getRoundupToTwoValue(
						(bean.getTradedAtAvgVal().doubleValue() - bean.getSellerAt()) * (bean.getLotSize() * bean.getTradedLotCount()));
				bean.setProfitLossAmtVal(netLossVal);
				if (netLossVal <= 0) {
					bean.setTradedStateId(Constants.SELL_EXIT_LOSS);
					bean.setSignalTriggeredOutAt(
							tradewareTool.getCurrentTime() + Constants.CLOSED_WITH_LOSE
									+ bean.getSellerAt() + Constants.WITH_LOSS + netLossVal);
				} else {
					bean.setTradedStateId(Constants.SELL_EXIT_PROFIT);
					bean.setSignalTriggeredOutAt(tradewareTool.getCurrentTime()
							+ Constants.SELL_TARGET + bean.getSellerAt() + Constants.WITH_PROFIT + netLossVal);
				}

				// kiteConnectTool.cancelBracketOrder(bean);
				bean = kiteConnectTool.cancelCoverOrder(bean);
				databaseHelper.updateTrade(bean);
				map.remove(bean.getKiteFutureKey());
			} /*
				 * else if (bean.getTradedLotCount() < 2 && (null !=
				 * bean.getNegativeDirectionLoss() &&
				 * bean.getNegativeDirectionLoss().doubleValue() <=
				 * -Constants.TARGET_MAX_FIRST_LOSS)) { adjustTradings(bean,map); } else if
				 * (bean.getTradedLotCount() < 3 && (null != bean.getNegativeDirectionLoss() &&
				 * bean.getNegativeDirectionLoss().doubleValue() <=
				 * -Constants.TARGET_MAX_SECOND_LOSS)) { adjustTradings(bean, map); }
				 */ else {
				trackAndUpdatePositiveNegativeMoves(bean, lastPrice, map);
			}
	}
	
	static double netProfitLossVal = 0;
	private void trackAndUpdatePositiveNegativeMoves(StrategyOrbDataBean bean, double lastPrice,
			ConcurrentHashMap<String, StrategyOrbDataBean> map) {
		// track the price moment
		if (BUY.equals(bean.getTradedStateId())) {
			netProfitLossVal = (lastPrice - bean.getTradedAtAvgVal().doubleValue());
		} else if (SELL.equals(bean.getTradedStateId())) {
			netProfitLossVal = (bean.getTradedAtAvgVal().doubleValue() - lastPrice);
		}
		netProfitLossVal = kiteConnectTool
				.getRoundupToTwoValue(netProfitLossVal * (bean.getLotSize() * bean.getTradedLotCount()));
		bean.setProfitLossAmtVal(netProfitLossVal);
		if (null == bean.getNegativeDirectionLoss()
				|| netProfitLossVal < bean.getNegativeDirectionLoss().doubleValue()) {
			bean.setNegativeDirectionLoss(netProfitLossVal);
			bean.setNegativeDirectionLtp(lastPrice);
		} else if (null == bean.getPositiveDirectionProfit()
				|| netProfitLossVal > bean.getPositiveDirectionProfit().doubleValue()) {
			bean.setPositiveDirectionLtp(lastPrice);
			bean.setPositiveDirectionProfit(netProfitLossVal);
		}
		map.replace(bean.getKiteFutureKey(), bean);
		if (loopCounter > 250) {
			// update database
			databaseHelper.updatePositiveNegativeMoves(bean);
		} else if (((loopCounter % 25) == 0)
				&& (null != bean.getTradePlacedRuleInd() && bean.getTradePlacedRuleInd())) {
			databaseHelper.updatePositiveNegativeMoves(bean);
		}
	}
	
	
	private void adjustTradings(StrategyOrbDataBean bean, ConcurrentHashMap<String, StrategyOrbDataBean> map) {
		bean.setTradedLotCount(bean.getTradedLotCount() + 1);
		
		if (Constants.BUY.equals(bean.getTradedStateId())) {
			if (2 == bean.getTradedLotCount()) {
				bean.setTradedAtDtTm2(tradewareTool.getCurrentDateTimeAsDate());
				bean.setTradedAtVal2(bean.getSellerAt());
				bean.setTradedAtAvgVal(kiteConnectTool.getRoundupToTwoValue((bean.getTradedAtVal() + bean.getTradedAtVal2())/2) );
				
				//dummy
				bean.setTradedAtDtTm3(tradewareTool.getCurrentDateTimeAsDate());
				bean.setTradedAtVal3(bean.getSellerAt());
				//dummy
				
				bean.setTargetPrice(kiteConnectTool.getRoundupToTwoValue(((Constants.TARGET_AMOUNT_5000 /( bean.getLotSize() * bean.getTradedLotCount()))
						+ (bean.getTradedAtAvgVal().doubleValue()))));
				bean.setTargetAmtVal2(bean.getTargetPrice());bean.setTargetAmtVal3(bean.getTargetPrice());
				
			} else if (3 == bean.getTradedLotCount()) {
				bean.setTradedAtDtTm3(tradewareTool.getCurrentDateTimeAsDate());
				bean.setTradedAtVal3(bean.getSellerAt());
				bean.setTradedAtAvgVal(kiteConnectTool.getRoundupToTwoValue((bean.getTradedAtVal() + bean.getTradedAtVal2() + bean.getTradedAtVal3())/3) );
				bean.setTargetPrice(kiteConnectTool.getRoundupToTwoValue(((Constants.TARGET_AMOUNT_5000 /( bean.getLotSize() * bean.getTradedLotCount()))
						+ (bean.getTradedAtAvgVal().doubleValue()))));
				bean.setTargetAmtVal3(bean.getTargetPrice());
			}
		} else if (Constants.SELL.equals(bean.getTradedStateId())) {
			if (2 == bean.getTradedLotCount()) {
				bean.setTradedAtDtTm2(tradewareTool.getCurrentDateTimeAsDate());
				bean.setTradedAtVal2(bean.getBuyerAt());
				bean.setTradedAtAvgVal(kiteConnectTool.getRoundupToTwoValue((bean.getTradedAtVal() + bean.getTradedAtVal2())/2) );
				
				//dummy
				bean.setTradedAtDtTm3(tradewareTool.getCurrentDateTimeAsDate());
				bean.setTradedAtVal3(bean.getBuyerAt());
				//dummy
				
				bean.setTargetPrice(kiteConnectTool.getRoundupToTwoValue((bean.getTradedAtAvgVal().doubleValue()
						- (Constants.TARGET_AMOUNT_5000 / (bean.getLotSize() * bean.getTradedLotCount())))));
				bean.setTargetAmtVal2(bean.getTargetPrice());bean.setTargetAmtVal3(bean.getTargetPrice());
			} else if (3 == bean.getTradedLotCount()) {
				bean.setTradedAtDtTm3(tradewareTool.getCurrentDateTimeAsDate());
				bean.setTradedAtVal3(bean.getBuyerAt());
				bean.setTradedAtAvgVal(kiteConnectTool.getRoundupToTwoValue((bean.getTradedAtVal() + bean.getTradedAtVal2() + bean.getTradedAtVal3())/3) );

				bean.setTargetPrice(kiteConnectTool.getRoundupToTwoValue((bean.getTradedAtAvgVal().doubleValue()
						- (Constants.TARGET_AMOUNT_5000 / (bean.getLotSize() * bean.getTradedLotCount())))));
				bean.setTargetAmtVal3(bean.getTargetPrice());
			}
		}
		
		// Update Databases
		databaseHelper.updateAdujustTrade(bean);
		map.replace(bean.getKiteFutureKey(), bean);
		
	}
	
	
	private StrategyOrbDataBean placeTradeOrder(StrategyOrbDataBean bean) {
		//if (tradewareTool.canPlaceOrderBasedAlgoRules(bean)) {
			// bean.setTempCustomTradingRuleInd(true);
			// bean = kiteConnectTool.placeBracketOrder(bean);
			//bean = kiteConnectTool.placeCoverOrder(bean);
		//}
		//11/25/2020 start
		/*if(bean.isTempCustomTradingRuleInd() && bean.getGapUpDownMoveVal() <= 1500) {
			bean = kiteConnectTool.placeCoverOrder(bean);
		}*/
		//11/25/2020 end
		//12/6/2020 start
		/*if(tradewareTool.verifySMAandVWAPformula(bean) && TradewareTool.todayTradingGoInd) {
			bean.setTempCustomTradingRuleInd(true);
			//bean = kiteConnectTool.placeCoverOrder(bean);
		}*/
		//12/6/2020 end
		if (TradewareTool.todayTradingGoInd) {
			if (bean.isSecondLevelSmaVwapRuleInd()) {
				if (null!= bean.getTradingRuleOHLCAnd3TimesStrengthInd() && bean.getTradingRuleOHLCAnd3TimesStrengthInd()) {
					if (null!= bean.getTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd() && bean.getTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd()) {
						bean.setTradePlacedRuleInd(true);//May be not required, can be delete
						bean = kiteConnectTool.placeCoverOrder(bean);
					}
				}
			}
		}
		return bean;
	}
	
	
	protected void findOutTrendTradableStateId(StrategyOrbDataBean bean) {
		if (null == bean.getTrendTradableStateId()) {
			if (null != bean.getMin60StochasticTrend()&& null != bean.getMin15StochasticTrend()
					&& null != bean.getMin5StochasticTrend()) {
				if (!NA.equals(bean.getMin60StochasticTrend()) || !NA.equals(bean.getMin15StochasticTrend())
						|| !NA.equals(bean.getMin5StochasticTrend())) {
					if (!bean.getMin60StochasticTrend().contains(SELL) && !bean.getMin15StochasticTrend().contains(SELL)
							&& !bean.getMin5StochasticTrend().contains(SELL)) {
						bean.setTrendTradableStateId(BUY);
					} else if (!bean.getMin60StochasticTrend().contains(BUY)
							&& !bean.getMin15StochasticTrend().contains(BUY)
							&& !bean.getMin5StochasticTrend().contains(BUY)) {
						bean.setTrendTradableStateId(SELL);
					} else {
						bean.setTrendTradableStateId(UNCLEAR);
					}
				} else {
					bean.setTrendTradableStateId(ANY);
				}
			}else {
				bean.setTrendTradableStateId(ANYNULL);
			}
		}
	}
}
