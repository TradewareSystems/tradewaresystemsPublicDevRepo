package com.tradeware.stockmarket.tool.thread;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.util.StockUtil;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.NetworkException;
import com.zerodhatech.models.HistoricalData;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TradingDataMapHistData60MinuteThread extends TradingDataMapHistDataThread {

	@Override
	public void run() {
		super.threadName = Thread.currentThread().getName();
		long entry = 0;
		if (TradewareLogger.isDebugLevelEnable) {
			entry = System.currentTimeMillis();
			TradewareLogger.saveInfoLog(
					CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]", METHOD_RUN,
					CALL_ENTRY + tradewareTool.getCurrentDateTimeAsAMPM());
		}
		
		refreshTradingDataMap60Minute();
		initGet60MinuteCandleData();
		
		refreshTradingDataMap60Minute();
		initGetDayCandleData();
		
		if (TradewareLogger.isDebugLevelEnable) {
			long exit = System.currentTimeMillis();
			TradewareLogger.saveInfoLog(CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " ["+super.threadName+"]", METHOD_RUN,
					tradewareTool.getCurrentDateTimeAsAMPM() + CALL_ENTRY_EXIT_DURATION + ((exit - entry) / 1000));
			//databaseHelper.activityKite15MinuteHistDataProcess( (" Total time " + (exit - entry) / 1000)+("; SymbolsProcessed:"+currentCandleFrameCount));
			//currentCandleFrameCount = 0;
		}
		
	}
	
	private void refreshTradingDataMap60Minute() {
		processedSymbols.clear();
		processedCounter = 0;
	}
	
	private void initGet60MinuteCandleData() {
		long entry = System.currentTimeMillis();
		try {
			if (TradewareLogger.isDebugLevelEnable) {
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
				String times = formatter.format(fromDate) + COMMA_SPACE + formatter.format(toDate);
				TradewareLogger.saveInfoLog(
						CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]",
						METHOD_INIT_GET_60_MINUTE_CANDLE_DATA, CALL_ENTRY + COMMA_SPACE + times
								+ " radewareTool.getBaseDataMap().size():" + tradewareTool.getBaseDataMap().size());
			}
			repaeat60MinutueCandleDataFull(tradewareTool.getBaseDataMap());
			repaeat60MinutueCandleDataFull(tradewareTool.getBaseDataMapAll());
			if (TradewareLogger.isDebugLevelEnable) {
				long exit = System.currentTimeMillis();
				TradewareLogger.saveInfoLog(
						CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]",
						METHOD_INIT_GET_60_MINUTE_CANDLE_DATA,
						CALL_EXIT + " > processed - " + processedSymbols.size() + "; tradewareTool.getBaseDataMap()- "
								+ tradewareTool.getBaseDataMap().size() + "; processedERRORCounter- " + processedCounter
								+ "; Total time " + (exit - entry) / 1000);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(
					CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]",
					METHOD_INIT_GET_60_MINUTE_CANDLE_DATA + dummyKey, e, ERROR_TYPE_KITE_EXCEPTION);

			TradewareLogger.saveInfoLog(
					CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]",
					METHOD_INIT_GET_60_MINUTE_CANDLE_DATA,
					"FALED_EXIT..." + " > processed - " + processedSymbols.size() + "  -tradewareTool.getBaseDataMap()- "
							+ tradewareTool.getBaseDataMap().size() + "processedCounter-" + processedCounter);
		}
	}
	
	private void repaeat60MinutueCandleDataFull(ConcurrentHashMap<String, StrategyOrbDataBean> dataMap) {
		try {
			if (null != dataMap && !dataMap.isEmpty()) {
				get60MinutueCandleDataFull(dataMap);
			}
		} catch (KiteException | Exception e) {
			processedCounter++;
			try {
				TradewareLogger.saveInfoLog(
						CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]" + dummyKey,
						METHOD_REPAEAT_60_MINUTUE_CANDLE_DATA_FULL + processedCounter,
						e.getCause().getClass().toString());
			} catch (Exception e1) {
				// do nothing
			}
			if (dataMap.size() != processedSymbols.size() && processedCounter <= 100) {
				repaeat60MinutueCandleDataFull(dataMap);
			}
		}
	}
	
	// Should not write try/catch, repaeat5MinutueCandleDataFull will catch the
		// error thrown here and retry until 100 times process count reach
		private void get60MinutueCandleDataFull(ConcurrentHashMap<String, StrategyOrbDataBean> dataMap) throws JSONException, IOException, KiteException, NetworkException {

			if (!(null == dataMap || dataMap.isEmpty() || null == fromDate || null == toDate)) {
				for (String key : dataMap.keySet()) {
					dummyKey = key;
					if (!processedSymbols.contains(key)) {
						StrategyOrbDataBean bean = dataMap.get(key).clone();
						HistoricalData histData = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER)
								.getHistoricalData(fromDate, toDate, String.valueOf(bean.getInstrumentToken()),
										KITE_HIST_DATA_60_MINUTES_INTERVAL, false);
						processedSymbols.add(key);

						List<HistoricalData> histDataList = histData.dataArrayList;
						if (null != histDataList && histDataList.size() >= 3) {
							HistoricalData data = histDataList.get(histDataList.size() - 1);
							double high3 = data.high;
							double low3 = data.low;

							data = histDataList.get(histDataList.size() - 2);
							double high2 = data.high;
							double low2 = data.low;

							data = histDataList.get(histDataList.size() - 3);
							double high1 = data.high;
							double low1 = data.low;

							prevDayHigh = high3;

							if (high2 > prevDayHigh) {
								prevDayHigh = high2;
							}
							if (high1 > prevDayHigh) {
								prevDayHigh = high1;
							}

							prevDayLow = low3;
							if (low2 < prevDayLow) {
								prevDayLow = low2;
							}

							if (low1 < prevDayLow) {
								prevDayLow = low1;
							}
							dataMap.get(key).setPrevDayHrBuyAtVal(prevDayHigh);
							dataMap.get(key).setPrevDayHrSellAtVal(prevDayLow);
							
							//System.out.println(key+" :  "+prevDayHigh+" :  "+prevDayLow);
						} else {
							TradewareLogger.saveInfoLog(
									CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]",
									METHOD_GET_60_MINUTUE_CANDLE_DATA_FULL, ">>> histDataList is null OR INVALID >>>");
						}
					} else {
						// Do nothing, Symbol already processed.
					}
				}
			} else {
				TradewareLogger.saveInfoLog(
						CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]",
						METHOD_GET_60_MINUTUE_CANDLE_DATA_FULL, fromDate + COMMA_SPACE + toDate + COMMA_SPACE
								+ dataMap + COMMA_SPACE);
			}
		}
		
		
		//Day Level data start
		private void initGetDayCandleData() {
			long entry = System.currentTimeMillis();
			try {
				if (TradewareLogger.isDebugLevelEnable) {
					SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
					String times = formatter.format(fromDate) + COMMA_SPACE + formatter.format(toDate);
					TradewareLogger.saveInfoLog(
							CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]",
							METHOD_INIT_GET_DAY_CANDLE_DATA, CALL_ENTRY + COMMA_SPACE + times
									+ " radewareTool.getBaseDataMap().size():" + tradewareTool.getBaseDataMap().size());
				}
				repaeatDayCandleDataFull(tradewareTool.getBaseDataMap());
				repaeatDayCandleDataFull(tradewareTool.getBaseDataMapAll());
				if (TradewareLogger.isDebugLevelEnable) {
					long exit = System.currentTimeMillis();
					TradewareLogger.saveInfoLog(
							CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]",
							METHOD_INIT_GET_DAY_CANDLE_DATA,
							CALL_EXIT + " > processed - " + processedSymbols.size() + "; tradewareTool.getBaseDataMap()- "
									+ tradewareTool.getBaseDataMap().size() + "; processedERRORCounter- " + processedCounter
									+ "; Total time " + (exit - entry) / 1000);
				}
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(
						CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]",
						METHOD_INIT_GET_DAY_CANDLE_DATA + dummyKey, e, ERROR_TYPE_KITE_EXCEPTION);

				TradewareLogger.saveInfoLog(
						CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]",
						METHOD_INIT_GET_DAY_CANDLE_DATA,
						"FALED_EXIT..." + " > processed - " + processedSymbols.size() + "  -tradewareTool.getBaseDataMap()- "
								+ tradewareTool.getBaseDataMap().size() + "processedCounter-" + processedCounter);
			}
		}
		private void repaeatDayCandleDataFull(ConcurrentHashMap<String, StrategyOrbDataBean> dataMap) {
			try {
				if (null != dataMap && !dataMap.isEmpty()) {
					getDayCandleDataFull(dataMap);
				}
			} catch (KiteException | Exception e) {
				processedCounter++;
				try {
					TradewareLogger.saveInfoLog(
							CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]" + dummyKey,
							METHOD_REPAEAT_DAY_CANDLE_DATA_FULL + processedCounter,
							e.getCause().getClass().toString());
				} catch (Exception e1) {
					// do nothing
				}
				if (dataMap.size() != processedSymbols.size() && processedCounter <= 100) {
					repaeatDayCandleDataFull(dataMap);
				}
			}
		}
		// Should not write try/catch, repaeat5MinutueCandleDataFull will catch the
				// error thrown here and retry until 100 times process count reach
				private void getDayCandleDataFull(ConcurrentHashMap<String, StrategyOrbDataBean> dataMap) throws JSONException, IOException, KiteException, NetworkException {

					if (!(null == dataMap || dataMap.isEmpty() || null == fromDate || null == toDate)) {
						for (String key : dataMap.keySet()) {
							dummyKey = key;
							if (!processedSymbols.contains(key)) {
								StrategyOrbDataBean bean = dataMap.get(key).clone();
								HistoricalData histData = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER)
										.getHistoricalData(fromDate, toDate, String.valueOf(bean.getInstrumentToken()),
												KITE_HIST_DATA_DAY_INTERVAL, false);
								processedSymbols.add(key);

								List<HistoricalData> histDataList = histData.dataArrayList;
								if (null != histDataList && histDataList.size() >= 1) {
									HistoricalData data = histDataList.get(histDataList.size() - 1);
									
									dataMap.get(key).setPrevDayHigh(data.high);
									dataMap.get(key).setPrevDayLow(data.low);
									dataMap.get(key).setPrevDayPercentageChange(tradewareTool.getchangePercentage(dataMap.get(key).getLtp(), data.close));
									
									//System.out.println(key+" :  "+prevDayHigh+" :  "+prevDayLow);
								} else {
									TradewareLogger.saveInfoLog(
											CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]",
											METHOD_GET_DAY_CANDLE_DATA_FULL, ">>> histDataList is null OR INVALID >>>");
								}
							} else {
								// Do nothing, Symbol already processed.
							}
						}
					} else {
						TradewareLogger.saveInfoLog(
								CLASS_TRADING_DATA_MAP_HIST_DATA_60_MINUTE_THREAD + " [" + super.threadName + "]",
								METHOD_GET_DAY_CANDLE_DATA_FULL, fromDate + COMMA_SPACE + toDate + COMMA_SPACE
										+ dataMap + COMMA_SPACE);
					}
				}
		//Day Level dataend
	
	public static Date fromDate = null;
	public static Date toDate = null;
	private static double prevDayLow;
	private static double prevDayHigh;
}
