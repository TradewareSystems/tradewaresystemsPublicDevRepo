package com.tradeware.stockmarket.scanner;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.stockmarket.bean.KiteLiveOHLCDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeChildDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeMainDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TradewareOptionIntradayStraddleScanner extends AbstractTradewareOptionScanner {

	@Override
	public void run() {
		// DatabaseHelper.getInstance().activityScannerStartup();
		List<OptionLiveTradeMainDataBean> optionTradingDataList = databaseHelper
				.findByTradePositionAndTradeStrategy(TRADE_POSIITON_OPEN, OPTION_STRATEGY_STRADDLE);
		if (!optionTradingDataList.isEmpty()) {
			for (OptionLiveTradeMainDataBean dbBean : optionTradingDataList) {
				if (!tradewareTool.getTradingIntradayStraddleOptionDataMap().keySet().contains(dbBean.getTradeName())) {
					tradewareTool.getTradingIntradayStraddleOptionDataMap().put(dbBean.getTradeName(), dbBean);
				}
			}
		} else if (optionTradingDataList.size()  < 2) {
			//Take intraday straddle entry at 10:00 AM
			tradewareTool.algoTradeEntryForIntradayForStraddleEntry();
		}
		while (tradewareTool.isTradingTime()) {
			try {
				ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap = tradewareTool
						.getTradingIntradayStraddleOptionDataMap();

				if (!tradewareTool.getTradingIntradayStraddleOptionDataMap().isEmpty()) {
					ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveDataMap = updateLiveOHLCOptionData(
							optionTradingDataMap);
					if (tradewareTool.isScanForBuyOrSell()) {
						runBuySellScanner(liveDataMap, optionTradingDataMap);
						profitLossMonitorScan(optionTradingDataMap, liveDataMap);
					} else if (null != getKiteConnect() && !tradewareTool.canPlaceOptionOrders()) {
						profitLossMonitorScan(optionTradingDataMap, liveDataMap);
					}
					if (loopCounter > 250) {
						System.out.println("TradewareScanner >>>>  " + loopCounter);
						loopCounter = 0;
						// profitLossSummaryTool.calculateProfitLoss(TRADE_PLACE_RULE_INIT_PROFITABLE_PROD_RULE);
					} else {
						loopCounter++;
					}
				}
				tradewareTool.setThreadScannerLastRunnngSatusTime(tradewareTool.getCurrentDateTime());

			} catch (/* Exception */Throwable e) {
				TradewareLogger.saveFatalErrorLog(CLASS_TRADEWARE_SCANNER, METHOD_RUN, e, ERROR_TYPE_EXCEPTION);
				tradewareTool.setThreadScannerStatus(APP_SERVER_STATUS_SCANNERS_TERMINATED);
			}
		}
		DatabaseHelper.getInstance().activityScannerEndup();
		tradewareTool.setThreadScannerLastRunnngSatusTime(
				/* "Thread out of ofloop - " + */tradewareTool.getCurrentDateTime());
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	@Override
	protected void verifyAndMakeStrangleAdjustment(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap) { }
	@Override
	protected void verifyAndMakeButterFlyAdjustment(OptionLiveTradeMainDataBean mainBean) { }

	@Override
	protected void verifyAndBookProfitOfIntradayStraddle(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap) {
		if (Constants.OPTION_STRATEGY_STRADDLE.equals(mainBean.getTradeStrategy())) {
			if (OPTION_STRATEGY_SHORT_STRADDLE.equals(mainBean.getTradeSubStrategy())) {
				if (null != mainBean.getIntradayTradeInd() && mainBean.getIntradayTradeInd()) {
					double currentPremium = 0d;
					for (OptionLiveTradeChildDataBean childBean : mainBean.getTradeChildBeanList()) {
						childBean.setStopLossVal(kiteConnectTool.getRoundupToTwoValue(childBean.getTradedAtVal()
								+ (childBean.getTradedAtVal() * STRATEGY_STRADDLE_STOP_LOSS_VALUE)));
						currentPremium = currentPremium + childBean.getAskPrice();
					}
					if (currentPremium < mainBean.getTargetVal()) {
						// Book full Profit
						bookWholeTradeToClose(mainBean, optionTradingDataMap);
					}
				} else {
					//adjustment logic
					verifyAndMakeStraddlePositionalTradeAdjustment(mainBean);
				}
			}
		}
	}
	
	private  void verifyAndMakeStraddlePositionalTradeAdjustment(OptionLiveTradeMainDataBean mainBean ) {
		double totalPutPremium = 0;
		double totalCallPremium = 0;
		int currentOpenPutTrades = 0;
		int currentOpenCallTrades = 0;
		for (OptionLiveTradeChildDataBean childDataBean : mainBean.getTradeChildBeanList()) {
			if (Constants.TRADE_POSIITON_OPEN.equals(childDataBean.getTradePosition())) {
				if (Constants.OPTION_PUT.equals(childDataBean.getOptionType())) {
					totalPutPremium = totalPutPremium + childDataBean.getLastPrice();
					currentOpenPutTrades++;
				} else if (Constants.OPTION_CALL.equals(childDataBean.getOptionType())) {
					totalCallPremium = totalCallPremium + childDataBean.getLastPrice();
					currentOpenCallTrades++;
				}
			} else if (  Constants.TRADE_POSIITON_NEW.equals(childDataBean.getTradePosition())) {
				return;
			}
		}
		
		if ((totalPutPremium / totalCallPremium) >= Constants.STRANGLE_ADJUSTMENT_RATIO) {
			
		} else if ((totalCallPremium / totalPutPremium) >= Constants.STRANGLE_ADJUSTMENT_RATIO) {
			
		} else if ((currentOpenPutTrades + currentOpenCallTrades) > 2) {
			if (currentOpenPutTrades > 1 && (totalCallPremium < totalCallPremium)) {
				
			} else if (currentOpenCallTrades > 1 && (totalCallPremium < totalCallPremium)) {
				
			}
		} else if  (false)  {
			//if current index vaulue is more than or less than break even points close the trade.
		}

		OptionLiveTradeChildDataBean putDataBean = null;
		OptionLiveTradeChildDataBean callDataBean = null;

		for (OptionLiveTradeChildDataBean childDataBean : mainBean.getTradeChildBeanList()) {
			if (Constants.TRADE_POSIITON_OPEN.equals(childDataBean.getTradePosition())
					|| Constants.TRADE_POSIITON_NEW.equals(childDataBean.getTradePosition())) {
				if (Constants.OPTION_PUT.equals(childDataBean.getOptionType())) {
					putDataBean = childDataBean;
				} else if (Constants.OPTION_CALL.equals(childDataBean.getOptionType())) {
					callDataBean = childDataBean;
				}
			}
		}
		
		//if (Constants.OPTION_STRATEGY_SHORT_STRANGLE.equals(mainBean.getTradeSubStrategy())) {
			if ((callDataBean.getAskPrice() / putDataBean.getAskPrice()) >= Constants.STRANGLE_ADJUSTMENT_RATIO) {
				ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = tradewareTool
						.getOptionStrickListForStrategyAdustments(mainBean, putDataBean.getStrikePrice(),
								putDataBean.getOptionType());
				map = tradewareTool.updateLiveOHLCOptionDataWith(map);
				KiteLiveOHLCDataBean liveBean = tradewareTool.findNearestOptionCallOrPutForAdustment(map,
						callDataBean.getAskPrice() * Constants.STRANGLE_ADJUSTMENT_NEW_TRADE_VAUE);
				/*KiteLiveOHLCDataBean liveBean = findOptionCallOrPutForAdustment(mainBean, putDataBean.getStrikePrice(),
						putDataBean.getOptionType(),
						callDataBean.getAskPrice() * Constants.STRANGLE_ADJUSTMENT_NEW_TRADE_VAUE);*/

				if (Double.valueOf(callDataBean.getStrikePrice()) > Double.valueOf(liveBean.getOptionStrikePrice())) {
					// Book current put and make adjustment
					updateLiveOHLCFromLiveDataBeanToChildDataBean(map.get(putDataBean.getKiteFutureKey()), putDataBean);
					bookCurrentPutOrCallToMakeAdjustment(mainBean, putDataBean);
					mainBean.getTradeChildBeanList().add(getAdjustmentOptionDataBean(mainBean, putDataBean, liveBean));
					// place order
					// kiteConnectTool.cancelBracketOrder(bean);
					// bean = kiteConnectTool.cancelCoverOrder(bean);
					databaseHelper.saveShortStrangleOptionTrade(mainBean);
					//Need to fresh open child trade
					mainBean = databaseHelper.findRunningTradeByTradeId(mainBean.getTradeId());
					tradewareTool.getTradingStrangleOptionDataMap().replace(mainBean.getTradeName(), mainBean);
				} else {
					bookWholeTradeToClose(mainBean, tradewareTool.getTradingStrangleOptionDataMap());
				}

			} else if ((putDataBean.getAskPrice() / callDataBean.getAskPrice()) >= Constants.STRANGLE_ADJUSTMENT_RATIO) {

				ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = tradewareTool
						.getOptionStrickListForStrategyAdustments(mainBean, callDataBean.getStrikePrice(),
								callDataBean.getOptionType());
				kiteConnectTool.updateLiveOHLC(map);
				KiteLiveOHLCDataBean liveBean = tradewareTool.findNearestOptionCallOrPutForAdustment(map,
						putDataBean.getAskPrice() * Constants.STRANGLE_ADJUSTMENT_NEW_TRADE_VAUE);
				/*KiteLiveOHLCDataBean liveBean = findOptionCallOrPutForAdustment(mainBean,callDataBean.getStrikePrice(),
						callDataBean.getOptionType(),
						putDataBean.getAskPrice() * Constants.STRANGLE_ADJUSTMENT_NEW_TRADE_VAUE);*/

				if (Double.valueOf(putDataBean.getStrikePrice()) < Double.valueOf(liveBean.getOptionStrikePrice())) {
					// Book current put and make adjustment
					updateLiveOHLCFromLiveDataBeanToChildDataBean(map.get(callDataBean.getKiteFutureKey()), callDataBean);
					bookCurrentPutOrCallToMakeAdjustment(mainBean, callDataBean);
					mainBean.getTradeChildBeanList().add(getAdjustmentOptionDataBean(mainBean, callDataBean, liveBean));
					// place order
					// kiteConnectTool.cancelBracketOrder(bean);
					// bean = kiteConnectTool.cancelCoverOrder(bean);
					databaseHelper.saveShortStrangleOptionTrade(mainBean);
					//Need to fresh open child trade
					mainBean = databaseHelper.findRunningTradeByTradeId(mainBean.getTradeId());
					tradewareTool.getTradingStrangleOptionDataMap().replace(mainBean.getTradeName(), mainBean);
				} else {
					bookWholeTradeToClose(mainBean, tradewareTool.getTradingStrangleOptionDataMap());
				}
			}
		//}
	
	}
}
