package com.tradeware.stockmarket.scanner;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.StockUtil;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TradewareDayLevelScanner extends AbstractTradewareScanner {

	@Override
	public void run() {
		try {
			// while (tradewareTool.isTradingTime()) {
			if (!tradewareTool.getTradingDataMap15MinuteDayLevels().isEmpty()) {
				if (null != StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)) {
					if (tradewareTool.isScanForBuyOrSell()) {

						ConcurrentHashMap<String, StrategyOrbDataBean> tempMap = kiteConnectTool
								.updateDayOHLC(tradewareTool.getTradingDataMap15MinuteDayLevels());
						/*tradewareTool.getTradingDataMap15MinuteDayLevels().clear();
						tradewareTool.setTradingDataMap15MinuteDayLevels(tempMap);*/

						runBuySellScanner();
						profitLossMonitorScan();
					} else {
						profitLossMonitorScan();
					}
				}
				if (loopCounter > 250) {
					//System.out.println("TradewareDayLevelScanner >>>>  "+loopCounter);
					loopCounter = 0;
				} else {
					loopCounter++;
				}
			}
		} catch (/*Exception*/Throwable e) {
			TradewareLogger.saveFatalErrorLog(CLASS_TRADEWARE_DAY_LEVEL_SCANNER, METHOD_RUN, e,
					Constants.ERROR_TYPE_EXCEPTION);
			tradewareTool.setThreadScannerStatus(APP_SERVER_STATUS_SCANNERS_TERMINATED);
		}
	}

	private void runBuySellScanner() {
		for (String symbol : tradewareTool.getTradingDataMap15MinuteDayLevels().keySet()) {
			StrategyOrbDataBean bean = tradewareTool.getTradingDataMap15MinuteDayLevels().get(symbol);
			if (null != bean) {

				if (Constants.NA.equals(bean.getTradedStateId())) {
					double lastPrice = bean.getLtp();
					if (null != bean.getBuyerAt() && bean.getBuyerAt() >= bean.getBuyAtVal()
							&& lastPrice > bean.getBuyAtVal() && validateBuyerSellerRule(bean, Constants.BUY,
									tradewareTool.getTradingDataMap15MinuteDayLevels())) {
						bean.setDayLevelTradeInd(true);
						executeBuyWorkflow(bean, tradewareTool.getTradingDataMap15MinuteDayLevels());
					} else if (null != bean.getSellerAt() && bean.getSellerAt() <= bean.getSellAtVal()
							&& lastPrice < bean.getSellAtVal() && validateBuyerSellerRule(bean, Constants.SELL,
									tradewareTool.getTradingDataMap15MinuteDayLevels())) {
						bean.setDayLevelTradeInd(true);
						executeSellWorkflow(bean, tradewareTool.getTradingDataMap15MinuteDayLevels());
					}
				}
			}
		}
	}

	private void profitLossMonitorScan() {
		//loopCounter++;
		for (String symbol : tradewareTool.getTradingDataMap15MinuteDayLevels().keySet()) {
			StrategyOrbDataBean bean = tradewareTool.getTradingDataMap15MinuteDayLevels().get(symbol);
			if (null != bean) {
				if (Constants.BUY.equals(bean.getTradedStateId())) {
					buyOrderProfitLossMonitorScan(bean, tradewareTool.getTradingDataMap15MinuteDayLevels());
				} else if (Constants.SELL.equals(bean.getTradedStateId())) {
					sellOrderProfitLossMonitorScan(bean, tradewareTool.getTradingDataMap15MinuteDayLevels());
				}
			}
		}
	}
}
