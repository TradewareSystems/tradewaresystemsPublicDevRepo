package com.tradeware.stockmarket.scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.tool.ProfitLossSummaryTool;
import com.tradeware.stockmarket.util.DatabaseHelper;
import com.tradeware.stockmarket.util.StockUtil;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TradewareScanner extends AbstractTradewareScanner {
	@Autowired
	private ProfitLossSummaryTool profitLossSummaryTool;
	
	@Autowired
	private TradewareDayLevelScanner dayLevelScanner;

	private Object getKiteConnect() {
		return StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER);
	}


	@Override
	public void run() {
		DatabaseHelper.getInstance().activityScannerStartup();
		while (tradewareTool.isTradingTime()) {
			try {
				if (null != getKiteConnect() && !tradewareTool.getTradingDataMap15Minute().isEmpty()) {
					kiteConnectTool
							.updateDayOHLC(tradewareTool.getTradingDataMap15Minute());
					if ( tradewareTool.isScanForBuyOrSell()) {
						runBuySellScanner();
						profitLossMonitorScan();
					} else if (null != getKiteConnect() /* && !SathvikAshvikTechTraderTool.canPlaceOrders */) {
						profitLossMonitorScan();
					}
					if (loopCounter > 250) {
						System.out.println("TradewareScanner >>>>  "+loopCounter);
						loopCounter = 0;
						profitLossSummaryTool.calculateProfitLoss(TRADE_PLACE_RULE_INIT_PROFITABLE_PROD_RULE);
					} else {
						loopCounter++;
					}
				}
				tradewareTool
						.setThreadScannerLastRunnngSatusTime(tradewareTool.getCurrentDateTime());
				if (null != tradewareTool.getTradingDataMap15MinuteDayLevels()
						&& !tradewareTool.getTradingDataMap15MinuteDayLevels().isEmpty()) {
					dayLevelScanner.run();
				}

			} catch (/*Exception*/Throwable e) {
				TradewareLogger.saveFatalErrorLog(CLASS_TRADEWARE_SCANNER, METHOD_RUN, e,
						ERROR_TYPE_EXCEPTION);
				tradewareTool.setThreadScannerStatus(APP_SERVER_STATUS_SCANNERS_TERMINATED);
			}
		}
		DatabaseHelper.getInstance().activityScannerEndup();
		tradewareTool.setThreadScannerLastRunnngSatusTime(
				/*"Thread out of ofloop - " + */tradewareTool.getCurrentDateTime());
	}

	/*private void runBuySellScanner() {
		for (String symbol : tradewareTool.getTradingDataMap15Minute().keySet()) {
			StrategyOrbDataBean bean = tradewareTool.getTradingDataMap15Minute().get(symbol);
			if (null != bean) {

				if (NA.equals(bean.getTradedStateId())) {
					double lastPrice = bean.getLtp();
					if (null != bean.getBuyerAt() && bean.getBuyerAt() >= bean.getBuyAtVal()
							&& lastPrice > bean.getBuyAtVal() && validateBuyerSellerRule(bean, BUY,
									tradewareTool.getTradingDataMap15Minute())) {
						executeBuyWorkflow(bean, tradewareTool.getTradingDataMap15Minute());
					} else if (null != bean.getSellerAt() && bean.getSellerAt() <= bean.getSellAtVal()
							&& lastPrice < bean.getSellAtVal() && validateBuyerSellerRule(bean, SELL,
									tradewareTool.getTradingDataMap15Minute())) {
						executeSellWorkflow(bean, tradewareTool.getTradingDataMap15Minute());
					}
				}
			}
		}
	}*/
	private void runBuySellScanner() {
		for (String symbol : tradewareTool.getTradingDataMap15Minute().keySet()) {
			StrategyOrbDataBean bean = tradewareTool.getTradingDataMap15Minute().get(symbol);
			if (null != bean) {
				findOutTrendTradableStateId(bean);
				if (NA.equals(bean.getTradedStateId())) {
					double lastPrice = bean.getLtp();
					if (null != bean.getBuyerAt() && bean.getBuyerAt() >= bean.getBuyAtVal()
							&& lastPrice > bean.getBuyAtVal() && validateBuyerSellerRule(bean, BUY,
									tradewareTool.getTradingDataMap15Minute())) {
						if (SELL.equals(bean.getTrendTradableStateId()) && (null == bean.getVwapTradeStateId()
								|| !bean.getVwapTradeStateId().contains("BUY"))) {
							bean.setTradeType(TRADE_TYPE_REVERSE);
							executeSellWorkflow(bean, tradewareTool.getTradingDataMap15Minute());
						} else {
							executeBuyWorkflow(bean, tradewareTool.getTradingDataMap15Minute());
						}
						
					} else if (null != bean.getSellerAt() && bean.getSellerAt() <= bean.getSellAtVal()
							&& lastPrice < bean.getSellAtVal() && validateBuyerSellerRule(bean, SELL,
									tradewareTool.getTradingDataMap15Minute())) {
						if (BUY.equals(bean.getTrendTradableStateId()) && (null == bean.getVwapTradeStateId()
								|| !bean.getVwapTradeStateId().contains("SEL"))) {
							bean.setTradeType(TRADE_TYPE_REVERSE);
							executeBuyWorkflow(bean, tradewareTool.getTradingDataMap15Minute());
						} else {
							executeSellWorkflow(bean, tradewareTool.getTradingDataMap15Minute());
						}
					}
				}
			}
		}
	}

	private void profitLossMonitorScan() {
		for (String symbol : tradewareTool.getTradingDataMap15Minute().keySet()) {
			StrategyOrbDataBean bean = tradewareTool.getTradingDataMap15Minute().get(symbol);
			if (null != bean) {
				if (BUY.equals(bean.getTradedStateId())) {
					buyOrderProfitLossMonitorScan(bean, tradewareTool.getTradingDataMap15Minute());
				} else if (SELL.equals(bean.getTradedStateId())) {
					sellOrderProfitLossMonitorScan(bean, tradewareTool.getTradingDataMap15Minute());
				}
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
}
