package com.tradeware.stockmarket.tool.thread;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.stockmarket.bean.AverageHistDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.util.StockUtil;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.NetworkException;
import com.zerodhatech.models.HistoricalData;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TradingDataMapHistData5MinuteThread extends AdditionalDataFillupHelper {

	@Override
	public void run() {
		super.threadName = Thread.currentThread().getName();
		long entry = 0;
		if (TradewareLogger.isDebugLevelEnable) {
			entry = System.currentTimeMillis();
			TradewareLogger.saveInfoLog(
					CLASS_TRADING_DATA_MAP_HIST_DATA_1_MINUTE_THREAD + " [" + super.threadName + "]", METHOD_RUN,
					CALL_ENTRY + tradewareTool.getCurrentDateTimeAsAMPM());
		}
		refreshTradingDataFromDateToDate();
		clearInits();
		if (minute5HistDataMap.isEmpty()) {
			minute5HistDataMap.putAll(tradewareTool.getBaseDataMap());
			minute5HistDataMap.putAll(tradewareTool.getBaseDataMapAll());
		}
		if (null != minute5HistDataMap && !minute5HistDataMap.isEmpty()) {
			initGet1MinuteCandleData();
		}

		if (null != tradewareTool.getBaseDataMap() && !tradewareTool.getBaseDataMap().isEmpty()) {
			tradewareTool.setBaseDataMap(kiteConnectTool.updateDayOHLC(tradewareTool.getBaseDataMap()));
		}
		if (null != tradewareTool.getBaseDataMapAll() && !tradewareTool.getBaseDataMapAll().isEmpty()) {
			tradewareTool.setBaseDataMapAll(kiteConnectTool.updateDayOHLC(tradewareTool.getBaseDataMapAll()));
		}

		if (TradewareLogger.isDebugLevelEnable) {
			long exit = System.currentTimeMillis();
			TradewareLogger.saveInfoLog(
					CLASS_TRADING_DATA_MAP_HIST_DATA_1_MINUTE_THREAD + " [" + super.threadName + "]", METHOD_RUN,
					minute5HistDataMap.size() + COMMA_SPACE + tradewareTool.getCurrentDateTimeAsAMPM()
							+ CALL_ENTRY_EXIT_DURATION + ((exit - entry) / 1000));
		}

		destroyValues();
	}

	private void initGet1MinuteCandleData() {
		long entry = System.currentTimeMillis();
		try {
			if (TradewareLogger.isDebugLevelEnable) {
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
				String times = formatter.format(fromDate) + COMMA_SPACE + formatter.format(toDate);
				TradewareLogger.saveInfoLog(
						CLASS_TRADING_DATA_MAP_HIST_DATA_1_MINUTE_THREAD + " [" + super.threadName + "]",
						METHOD_INIT_GET_5_MINUTE_CANDLE_DATA, CALL_ENTRY + COMMA_SPACE + times
								+ " getTradingDataMap5Minute().size():" + minute5HistDataMap.size());
			}
			repaeat1MinutueCandleDataFull();
			if (TradewareLogger.isDebugLevelEnable) {
				long exit = System.currentTimeMillis();
				TradewareLogger.saveInfoLog(
						CLASS_TRADING_DATA_MAP_HIST_DATA_1_MINUTE_THREAD + " [" + super.threadName + "]",
						METHOD_INIT_GET_5_MINUTE_CANDLE_DATA,
						CALL_EXIT + " > processed - " + processedSymbols.size() + "; minute5HistDataMap- "
								+ minute5HistDataMap.size() + "; processedERRORCounter- " + processedCounter
								+ "; Total time " + (exit - entry) / 1000);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(
					CLASS_TRADING_DATA_MAP_HIST_DATA_1_MINUTE_THREAD + " [" + super.threadName + "]",
					METHOD_INIT_GET_5_MINUTE_CANDLE_DATA + dummyKey, e, ERROR_TYPE_KITE_EXCEPTION);

			TradewareLogger.saveInfoLog(
					CLASS_TRADING_DATA_MAP_HIST_DATA_1_MINUTE_THREAD + " [" + super.threadName + "]",
					METHOD_INIT_GET_5_MINUTE_CANDLE_DATA,
					"FALED_EXIT..." + " > processed - " + processedSymbols.size() + "  -minute5HistDataMap- "
							+ minute5HistDataMap.size() + "processedCounter-" + processedCounter);
		}
	}
	
	private void refreshTradingDataFromDateToDate() {
		tradewareTool.setToDateForKiteHistDate(null);
		fromDate = tradewareTool.getFromDateForKiteHistDataStochastic5Or15Min_9_00();
		toDate = tradewareTool.getToDateForKiteHistDate_WithCurrentTime(); 
	}

	private void repaeat1MinutueCandleDataFull() {
		try {
			if (tradewareTool.canPlaceOrders() && tradewareTool.isCanUpdate15MinutesData()) {
				get1MinutueCandleDataFull();
			}
		} catch (KiteException | Exception e) {
			processedCounter++;
			try {
				TradewareLogger.saveInfoLog(
						CLASS_TRADING_DATA_MAP_HIST_DATA_1_MINUTE_THREAD + " [" + super.threadName + "]" + dummyKey,
						METHOD_REPAEAT_1_MINUTUE_CANDLE_DATA_FULL + processedCounter,
						e.getCause().getClass().toString());
			} catch (Exception e1) {
				// do nothing
			}
			if (minute5HistDataMap.size() != processedSymbols.size() && processedCounter <= 100) {
				repaeat1MinutueCandleDataFull();
			}
		}
	}

	// Should not write try/catch, repaeat5MinutueCandleDataFull will catch the
	// error thrown here and retry until 100 times process count reach
	private void get1MinutueCandleDataFull() throws JSONException, IOException, KiteException, NetworkException {

		if (!(null == minute5HistDataMap || minute5HistDataMap.isEmpty() || null == fromDate || null == toDate)) {

			for (String key : minute5HistDataMap.keySet()) {
				// clean up old averages;
				clearInitsValues();
				dummyKey = key;
				if (!processedSymbols.contains(key)) {
					HistoricalData histData = null;
					StrategyOrbDataBean bean = minute5HistDataMap.get(key).clone();
					if (KiteConnectTool.minute5KiteHistDataMap.contains(key)) {
						histData = KiteConnectTool.minute5KiteHistDataMap.get(key);
					} else {
						histData = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER).getHistoricalData(
								fromDate, toDate, String.valueOf(bean.getInstrumentToken()),
								KITE_HIST_DATA_5_MINUTES_INTERVAL, false);
						KiteConnectTool.minute5KiteHistDataMap.put(key, histData);
					}
					
					processedSymbols.add(key);

					List<HistoricalData> histDataList = histData.dataArrayList;
					if (null != histDataList && !histDataList.isEmpty()) {

						for (int i = 0; i <= histDataList.size() - 1; i++) {
							HistoricalData data = histDataList.get(i);
							totalLow = totalLow + data.low;
							totalHigh = totalHigh + data.high;
							totalClose = totalClose + data.close;
						}
						
						/*AverageHistDataBean avgBean = new AverageHistDataBean(bean.getLotSize(), bean.getSymbolId(),
								bean.getKiteFutureKey());*/
						AverageHistDataBean avgBean = tradewareTool.getAverageHLCDataMap().get(key);
						avgBean.setAvgLowMin5( kiteConnectTool.getRoundupToTwoValue(totalLow / (histDataList.size() )));
						avgBean.setAvgHighMin5(kiteConnectTool.getRoundupToTwoValue(totalHigh / (histDataList.size() )));
						avgBean.setAvgCloseMin5(kiteConnectTool.getRoundupToTwoValue(totalClose / (histDataList.size() )));
						//findHeikinAshiTrend(histDataList, avgBean);
						tradewareTool.getAverageHLCDataMap().replace(key, avgBean);
					} else {
						TradewareLogger.saveInfoLog(
								CLASS_TRADING_DATA_MAP_HIST_DATA_1_MINUTE_THREAD + " [" + super.threadName + "]",
								METHOD_GET_1_MINUTUE_CANDLE_DATA_FULL, ">>> histDataList is null OR INVALID >>>");
					}
				} else {
					// Do nothing, Symbol already processed.
				}
			}
		} else {
			TradewareLogger.saveInfoLog(
					CLASS_TRADING_DATA_MAP_HIST_DATA_1_MINUTE_THREAD + " [" + super.threadName + "]",
					METHOD_GET_1_MINUTUE_CANDLE_DATA_FULL, fromDate + COMMA_SPACE + toDate + COMMA_SPACE
							+ String.valueOf(candleNumber) + COMMA_SPACE + minute5HistDataMap + COMMA_SPACE);
		}
	}
	
	public static double totalLow;
	public static double totalHigh;
	public static double totalClose;
	
	protected static Date fromDate = null;
	protected static Date toDate = null;
	protected static int candleNumber;
	
	protected void clearInitsValues() {
		totalLow = 0;
		totalHigh = 0;
		totalClose = 0;
	}
	protected void clearInits() {
		totalLow = 0;
		totalHigh = 0;
		totalClose = 0;

		//avgBeanMin5.clear();

		processedSymbols.clear();
		processedCounter = 0;
	}
	
	protected void destroyValues() {
		fromDate = null;
		toDate = null;
		candleNumber = 0;
	}
}
