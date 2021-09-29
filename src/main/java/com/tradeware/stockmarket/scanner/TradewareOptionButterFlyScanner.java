package com.tradeware.stockmarket.scanner;

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
public class TradewareOptionButterFlyScanner extends AbstractTradewareOptionScanner {
	@Override
	public void run() {
		// DatabaseHelper.getInstance().activityScannerStartup();
		while (tradewareTool.isTradingTime()) {
			try {
				/*ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap = tradewareTool
						.getTradingButterFlyOptionDataMap();
				if (!tradewareTool.getTradingButterFlyOptionDataMap().isEmpty()) {
					ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveDataMap = tradewareTool
							.updateToLatestOHLC(tradewareTool.getTradingButterFlyOptionDataMap());
					if (tradewareTool.isScanForBuyOrSell()) {
						runBuySellScanner(liveDataMap, optionTradingDataMap);
						profitLossMonitorScan(optionTradingDataMap,liveDataMap);
					} else if (null != getKiteConnect() && !tradewareTool.canPlaceOptionOrders()) {
						profitLossMonitorScan(optionTradingDataMap,liveDataMap);
					}
					if (loopCounter > 250) {
						System.out.println("TradewareScanner >>>>  " + loopCounter);
						loopCounter = 0;
						// profitLossSummaryTool.calculateProfitLoss(TRADE_PLACE_RULE_INIT_PROFITABLE_PROD_RULE);
					} else {
						loopCounter++;
					}
				}*/
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

	private Double targetValue = null;
	private Double stockOrFutureLastPrice = null;
	private Double adjustmentPremiumPrice = null;
	@Override
	protected void verifyAndMakeStrangleAdjustment(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap) {
	}
	
	@Override
	protected void verifyAndBookProfitOfIntradayStraddle(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap) {
		
	}
	@Override
	protected void verifyAndMakeButterFlyAdjustment(OptionLiveTradeMainDataBean mainBean) {
		OptionLiveTradeChildDataBean atmTradeDataBean = null;
		OptionLiveTradeChildDataBean lowerTradeDataBean = null;
		OptionLiveTradeChildDataBean higherTradeDataBean = null;

		for (OptionLiveTradeChildDataBean childDataBean : mainBean.getTradeChildBeanList()) {
			if (OPTION_STRATEGY_LONG_BUTTER_FLY.equals(mainBean.getTradeSubStrategy())) {
				if (Constants.TRADE_POSIITON_OPEN.equals(childDataBean.getTradePosition())) {
					if (Constants.SELL.equals(childDataBean.getTradeType())) {
						if (mainBean.getAtmStrikePrice() == childDataBean.getStrikePrice()) {
							atmTradeDataBean = childDataBean;
						}
					} else if (Constants.BUY.equals(childDataBean.getTradeType())) {
						if (mainBean.getAtmStrikePrice() > childDataBean.getStrikePrice()) {
							higherTradeDataBean = childDataBean;
						} else {
							lowerTradeDataBean = childDataBean;
						}
					}
				}
			} else if (OPTION_STRATEGY_SHORT_BUTTER_FLY.equals(mainBean.getTradeSubStrategy())) {

			}

		}

		targetValue = atmTradeDataBean.getTradedAtVal() * BUTTER_FLY_TARGET_PROFIT;
		if (atmTradeDataBean.getBidPrice() < targetValue) {
			/////////////bookWholeTradeToClose(mainBean, tradewareTool.getTradingButterFlyOptionDataMap());
		} else {
			stockOrFutureLastPrice = tradewareTool.isTodayExpiryDay(mainBean.getSymbolId())
					? mainBean.getFutureLastPrice()
					: mainBean.getStockLastPrice();

			if (stockOrFutureLastPrice <= lowerTradeDataBean.getStrikePrice()
					|| stockOrFutureLastPrice >= higherTradeDataBean.getStrikePrice()) {
				adjustmentPremiumPrice = Math.abs((((2 * atmTradeDataBean.getTradedAtVal())
						- (higherTradeDataBean.getTradedAtVal() + lowerTradeDataBean.getTradedAtVal())) / 2)
						+ ADJUST_AND_EARN_TRADE_CHARGES_POINTS);
				if (OPTION_STRATEGY_LONG_BUTTER_FLY.equals(mainBean.getTradeSubStrategy())) {
					if (stockOrFutureLastPrice <= lowerTradeDataBean.getStrikePrice()) {
						// Adjustment with call
						ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = tradewareTool
								.getOptionStrickListForStrategyAdustments(mainBean, lowerTradeDataBean.getStrikePrice(),
										Constants.OPTION_CALL);
						map = kiteConnectTool.updateLiveOHLC(map);
						KiteLiveOHLCDataBean liveBean = tradewareTool.findNearestOptionCallOrPutForAdustment(map,
								adjustmentPremiumPrice);

						mainBean.getTradeChildBeanList().add(getAdjustmentOptionDataBeanForButterFly(mainBean, liveBean,
								SELL, OPTION_CALL, atmTradeDataBean.getNumberOfLots()));
						// place order
						// kiteConnectTool.cancelBracketOrder(bean);
						// bean = kiteConnectTool.cancelCoverOrder(bean);
						databaseHelper.saveShortStrangleOptionTrade(mainBean);
						//Need to fresh open child trade
						mainBean = databaseHelper.findRunningTradeByTradeId(mainBean.getTradeId());
			/////////////tradewareTool.getTradingButterFlyOptionDataMap().replace(mainBean.getTradeName(), mainBean);
					} else if (stockOrFutureLastPrice >= higherTradeDataBean.getStrikePrice()) {
						// Adjustment with put
						ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = tradewareTool
								.getOptionStrickListForStrategyAdustments(mainBean,
										higherTradeDataBean.getStrikePrice(), Constants.OPTION_PUT);
						map = kiteConnectTool.updateLiveOHLC(map);
						KiteLiveOHLCDataBean liveBean = tradewareTool.findNearestOptionCallOrPutForAdustment(map,
								adjustmentPremiumPrice);

						mainBean.getTradeChildBeanList().add(getAdjustmentOptionDataBeanForButterFly(mainBean, liveBean,
								SELL, OPTION_PUT, atmTradeDataBean.getNumberOfLots()));
						// place order
						// kiteConnectTool.cancelBracketOrder(bean);
						// bean = kiteConnectTool.cancelCoverOrder(bean);
						databaseHelper.saveShortStrangleOptionTrade(mainBean);
						//Need to fresh open child trade
						mainBean = databaseHelper.findRunningTradeByTradeId(mainBean.getTradeId());
			/////////////tradewareTool.getTradingButterFlyOptionDataMap().replace(mainBean.getTradeName(), mainBean);
					}
				} else if (OPTION_STRATEGY_SHORT_BUTTER_FLY.equals(mainBean.getTradeSubStrategy())) {

				}
			}
		}
	}
}
