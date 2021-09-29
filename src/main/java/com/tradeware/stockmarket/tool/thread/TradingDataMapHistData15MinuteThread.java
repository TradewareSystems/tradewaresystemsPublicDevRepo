package com.tradeware.stockmarket.tool.thread;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

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
public class TradingDataMapHistData15MinuteThread extends /*TradingDataMapHistDataThread*/ VolumeStrenthTrendHelper {
	private int currentCandleFrameCount;
	@Override
	public void run() {
		super.threadName = Thread.currentThread().getName();
		long entry = 0;
		if (TradewareLogger.isDebugLevelEnable) {
			entry = System.currentTimeMillis();
			TradewareLogger.saveInfoLog(CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " ["+super.threadName+"]", METHOD_RUN,
					CALL_ENTRY + tradewareTool.getCurrentDateTimeAsAMPM());
		}

		refreshTradingDataMap15Minute();
		initGet15MinuteCandleData();

		if (TradewareLogger.isDebugLevelEnable) {
			long exit = System.currentTimeMillis();
			TradewareLogger.saveInfoLog(CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " ["+super.threadName+"]", METHOD_RUN,
					tradewareTool.getCurrentDateTimeAsAMPM() + CALL_ENTRY_EXIT_DURATION + ((exit - entry) / 1000));
			//databaseHelper.activityKite15MinuteHistDataProcess( (" Total time " + (exit - entry) / 1000)+("; SymbolsProcessed:"+currentCandleFrameCount));
			currentCandleFrameCount = 0;
		}
	}

	private void refreshTradingDataMap15Minute() {
		processedSymbols.clear();
		processedCounter = 0;
		// Must clear toDateForKiteHistData before loop start
		tradewareTool.setToDateForKiteHistDate(null);
		if (tradewareTool.isTimeBefore10_15AM()) {
			tradewareTool.setIsTimeBefore10_15AM(null);
		}
		try {
			if (null != tradewareTool.getTradingDataMap15Minute()
					&& !tradewareTool.getTradingDataMap15Minute().isEmpty()) {
				tradewareTool.setScanForBuyOrSell15Minute(false);
				for (String key : tradewareTool.getTradingDataMap15Minute().keySet()) {
					StrategyOrbDataBean bean = tradewareTool.getTradingDataMap15Minute().get(key);
					if (!(BUY.equals(bean.getTradedStateId()) || SELL.equals(bean.getTradedStateId()))) {
						tradewareTool.getTradingDataMap15Minute().remove(key);
						kiteConnectTool.getSymbolSetForOHLCInput().remove(key);
						continue;
					} else {
						processedSymbols.add(key);
					}
				}
			}
			tradewareTool.setScanForBuyOrSell15Minute(true);
			tradewareTool.refreshScanForBuyOrSellThread();
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " ["+super.threadName+"]",
					METHOD_REFRESH_TRADING_DATA_MAP_15_MINUTE + dummyKey, e, ERROR_TYPE_KITE_EXCEPTION);

			//TODO : May not require
			TradewareLogger.saveInfoLog(CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " ["+super.threadName+"]",
					METHOD_REFRESH_TRADING_DATA_MAP_15_MINUTE,
					"FALED_EXIT..." + " > processed - " + processedSymbols.size() + "  -getTradingDataMap15Minute- "
							+ tradewareTool.getTradingDataMap15Minute().size() + "  -basemap "
							+ tradewareTool.getIterableBaseDataMapFor15MiniuteLoop().size() + "processedCounter-"
							+ processedCounter);
		}
	}
	
	private void initGet15MinuteCandleData() {
		long entry = System.currentTimeMillis();
		try {
			if (TradewareLogger.isDebugLevelEnable) {
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
				String times = formatter.format(tradewareTool.getFromDateForKiteHistDataOnCurrentDay_9_15())
						+ COMMA_SPACE + formatter.format(tradewareTool.getToDateForKiteHistDate_WithCurrentTime());
				TradewareLogger.saveInfoLog(CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " ["+super.threadName+"]",
						METHOD_INIT_GET_15_MINUTE_CANDLE_DATA,
						CALL_ENTRY + COMMA_SPACE + times + " getTradingDataMap15Minute().size():"
								+ tradewareTool.getTradingDataMap15Minute().size());
			}

			if (null != tradewareTool.getBaseDataMap() && !tradewareTool.getBaseDataMap().isEmpty()) {
				tradewareTool.restCandleNumber();
				tradewareTool.getIterableBaseDataMapFor15MiniuteLoop().clear();
				tradewareTool.getIterableBaseDataMapFor15MiniuteLoop().putAll(tradewareTool.getBaseDataMap());
				if (null != tradewareTool.getBaseDataMapAll() && !tradewareTool.getBaseDataMapAll().isEmpty()) {
					tradewareTool.getIterableBaseDataMapFor15MiniuteLoop().putAll(tradewareTool.getBaseDataMapAll());
				}
				repaeat15MinutueCandleDataFull();
			} else {
				TradewareLogger.saveInfoLog(CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " ["+super.threadName+"]",
						METHOD_INIT_GET_15_MINUTE_CANDLE_DATA, METHOD_REPAEAT15MINUTUECANDLEDATAFULL_FAILED);
			}

			processedSymbols.clear();
			processedCounter = 0;
			tradewareTool.setIs15MinteDayLevelCandle(null);
			
			if (TradewareLogger.isDebugLevelEnable) {
				long exit = System.currentTimeMillis();
				TradewareLogger.saveInfoLog(CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " ["+super.threadName+"]",
						METHOD_INIT_GET_15_MINUTE_CANDLE_DATA,
						CALL_EXIT + " > processed - " + processedSymbols.size() + "; getTradingDataMap15Minute- "
								+ tradewareTool.getTradingDataMap15Minute().size() + "; basemap- "
								+ tradewareTool.getIterableBaseDataMapFor15MiniuteLoop().size()
								+ "; processedERRORCounter- " + processedCounter + "; Total time "
								+ (exit - entry) / 1000);
			}
			
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " ["+super.threadName+"]",
					METHOD_INIT_GET_15_MINUTE_CANDLE_DATA + dummyKey, e, ERROR_TYPE_KITE_EXCEPTION);

			// TODO : May not require
			TradewareLogger.saveInfoLog(CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " ["+super.threadName+"]", METHOD_INIT_GET_15_MINUTE_CANDLE_DATA,
					"FALED_EXIT..." + " > processed - " + processedSymbols.size() + "  -getTradingDataMap15Minute- "
							+ tradewareTool.getTradingDataMap15Minute().size() + "  -basemap "
							+ tradewareTool.getIterableBaseDataMapFor15MiniuteLoop().size() + "processedCounter-"
							+ processedCounter);
		}
	}
	
	private void repaeat15MinutueCandleDataFull() {
		try {
			if (tradewareTool.canPlaceOrders() && tradewareTool.isCanUpdate15MinutesData()) {
				get15MinutueCandleDataFull();
			}
		} catch ( KiteException |Exception e) {
			processedCounter++;
			
			//Mostly it is NetworkException, and very frequently occurring
			/*TradewareLogger.saveFatalErrorLog(CLASS_SATHVIKASHVIKTECHTRADERTOOL+dummyKey,
					METHOD_GET15MINUTUECANDLEDATA_LOOP+processedCounter, e, ERROR_TYPE_NETWORKEXCEPTION);*/
			try {
			TradewareLogger.saveInfoLog(CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " ["+super.threadName+"]"+dummyKey,
					METHOD_REPAEAT_15_MINUTUE_CANDLE_DATA_FULL+processedCounter,
					e.getCause().getClass().toString());
			} catch(Exception e1) {
				//do nothing
			}
			if (tradewareTool.getIterableBaseDataMapFor15MiniuteLoop().size() != processedSymbols.size() && processedCounter <=100) {
				repaeat15MinutueCandleDataFull();
			}
		}
	}
	
	//Should not write try/catch, repaeat15MinutueCandleDataFull will catch the error trown here and retry until 100 time process count reach
	private void get15MinutueCandleDataFull() throws JSONException, IOException, KiteException, NetworkException {
		// Must clear toDateForKiteHistData before loop start
		tradewareTool.setToDateForKiteHistDate(null);
		if (tradewareTool.isTimeBefore10_15AM()) {
			tradewareTool.setIsTimeBefore10_15AM(null);
		}
		
		for (String key : tradewareTool.getIterableBaseDataMapFor15MiniuteLoop().keySet()) {
			dummyKey = key;
			if (!tradewareTool.getTradingDataMap15Minute().keySet().contains(key) && !processedSymbols.contains(key) && tradewareTool.getTradingDataMap15Minute().size() < 200) {
				StrategyOrbDataBean bean = tradewareTool.getIterableBaseDataMapFor15MiniuteLoop().get(key).clone();
				HistoricalData histData = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER).getHistoricalData(
						tradewareTool.getHeikinAshiFromDateForKiteHistData(),/* getFromDateForKiteHistData_9_15() *//*getFromDateForKiteHistDataOnCurrentDay_9_15(),*/
						tradewareTool.getToDateForKiteHistDate_WithCurrentTime(),//getToDateForKiteHistDataOnCurrentDay_9_45(), 
						String.valueOf(bean.getInstrumentToken()),
						//KITE_HIST_DATA_15_MINUTES_INTERVAL, false);
						KITE_HIST_DATA_5_MINUTES_INTERVAL, false);
				processedSymbols.add(key);

				List<HistoricalData> histDataList = histData.dataArrayList;
				if(!applyNR7Rule( histData,  bean)) {
				if (null != histDataList && histDataList.size() >= 3) {
					HistoricalData data = histDataList.get(histDataList.size() - 2);
					double high4 = data.high;
					double low4 = data.low;
					double close4 = data.close;
					double open4 = data.open;

					data = histDataList.get(histDataList.size() - 3);
					double high3 = data.high;
					double low3 = data.low;
					double close3 = data.close;
					double open3 = data.open;

					double highMinusClose4 = kiteConnectTool.getRoundupToTwoValue(high4 - close4);
					double closeMinusLow4 = kiteConnectTool.getRoundupToTwoValue(close4 - low4);
					double highMinusClose3 = kiteConnectTool.getRoundupToTwoValue(high3 - close3);
					double closeMinusLow3 = kiteConnectTool.getRoundupToTwoValue(close3 - low3);
					//if ((high1 > high2) && (low1 < low2)) {
					if (((high3 > high4) && (low3 < low4)) || ((high3 < high4) && (low3 > low4))) { //To handle reverse
						
							
							//11/25/2020 start
							double candleHighsDiff = kiteConnectTool.getRoundupToTwoValue((high3 - high4) * bean.getLotSize());
							double candleLowsDiff  = kiteConnectTool.getRoundupToTwoValue((low4 - low3) * bean.getLotSize());
							if (((highMinusClose4 > 1 && ((highMinusClose4 * 7) <= closeMinusLow4))
									&& candleHighsDiff <= 10000)
									|| ((closeMinusLow4 > 1 && ((closeMinusLow4 * 7) <= highMinusClose4))
											&& candleLowsDiff <= 10000)) {
								bean.setStrategyRule(STRATEGY_ORB_R1);
								// bean.setTempCustomTradingRuleInd(true);
							} else if (((high4 == close4 && open4 == low4) || (high4 == low4 && open4== close4))
									|| ((highMinusClose4 <= 1 && ((high4 - open4) > ((open4 - low4) * 5)))
											&& candleHighsDiff <= 10000)
									|| ((closeMinusLow4 <= 1 && ((high4 - close4) > ((high4 - open4) * 5)))
											&& candleLowsDiff <= 10000))

							{
								bean.setStrategyRule(STRATEGY_ORB_R2);
								// bean.setTempCustomTradingRuleInd(true);
							} else {
								bean.setStrategyRule(STRATEGY_ORB);
								//continue;  //06/23/2021
							}
						
						bean.setBuyAtVal(high4);
						bean.setSellAtVal(low4);
						if (open4 < close4) {
							if (closeMinusLow4 > highMinusClose4) {
								bean.setStrengthStyleClass(GREEN_BOLD_FONT_STYLE_CLASS);
								bean.setStrengthableTradeStateId(GREEN_BUY_PRESSURE);
								bean.setStrengthableTradeInfo(getStrengthTradeBUY(closeMinusLow4,  highMinusClose4));
							} else {
								bean.setStrengthStyleClass(RED_BOLD_FONT_STYLE_CLASS);
								bean.setStrengthableTradeStateId(GREEN_SELL_PRESSURE);
								bean.setStrengthableTradeInfo(getStrengthTradeSELL(closeMinusLow4,  highMinusClose4));
							}
						} else if (open4 > close4) {
							if (highMinusClose4 > closeMinusLow4) {
								bean.setStrengthStyleClass(GREEN_BOLD_FONT_STYLE_CLASS);
								bean.setStrengthableTradeStateId(RED_SELL_PRESSURE);
								bean.setStrengthableTradeInfo(getStrengthTradeSELL(closeMinusLow4,  highMinusClose4));
							} else {
								bean.setStrengthStyleClass(RED_BOLD_FONT_STYLE_CLASS);
								bean.setStrengthableTradeStateId(RED_BUY_PRESSURE);
								bean.setStrengthableTradeInfo(getStrengthTradeBUY(closeMinusLow4,  highMinusClose4));
							}
						} else {
							bean.setStrengthableTradeStateId(STNA); 
						}

						bean.setCandle4SizeAmt(kiteConnectTool.getRoundupToTwoValue((high4 - low4) * bean.getLotSize()));
						bean.setCandle3SizeAmt(kiteConnectTool.getRoundupToTwoValue((high3 - low3) * bean.getLotSize()));
						bean.setCandle4HighMinusClose(highMinusClose4);
						bean.setCandle4CloseMinusLow(closeMinusLow4);
						bean.setCandle3HighMinusClose(highMinusClose3);
						bean.setCandle3CloseMinusLow(closeMinusLow3);
						bean.setCandle3Type(open3 < close3 ? GREEN_CANDLE : RED_CANDLE);
						bean.setCandle4Type(open4 < close4 ? GREEN_CANDLE : RED_CANDLE);
						
						bean.setCandleHighsDiff(kiteConnectTool.getRoundupToTwoValue((high3 - high4) * bean.getLotSize()));
						bean.setCandleLowsDiff(kiteConnectTool.getRoundupToTwoValue((low4 - low3) * bean.getLotSize()));
						//bean = validateOHLCRule(bean);
						updateCandleTypeTrendId(bean, open3, close3, close4);
						bean.setCandleNumber(tradewareTool.getCandleNumber());
						//bean.setCandleTimeFrame(KITE_HIST_DATA_15_MINUTES_INTERVAL);
						bean.setCandleTimeFrame(KITE_HIST_DATA_5_MINUTES_INTERVAL);
						
						kiteConnectTool.getSymbolSetForOHLCInput().add(key);
						volumeStrenthTrend(histDataList, bean);
						caluclatePivotPoints(bean, high4, low4, close4);
						
						if (((high3 < high4) && (low3 > low4))) {
							//info = info + REEVERSE_MOTHER_CANDLE;
							//bean.setTradableStrength(info);
							bean.setReverseMotherCandleInd(true);
							bean.setCandleHighsDiff(Math.abs(bean.getCandleHighsDiff()));
							bean.setCandleLowsDiff(Math.abs(bean.getCandleLowsDiff()));
						}
						
						copyCurrentCandleAverages(bean, histDataList.get(histDataList.size() - 1).open);
						setPreviousCandleOhlcData(histDataList, bean);
						//Looks like not much useful, commenting out on Nov/14/2020
						findHeikinAshiTrend( histDataList, bean);
						
						if (tradewareTool.is15MinteDayLevelCandle()) {
							tradewareTool.getTradingDataMap15MinuteDayLevels().put(bean.getKiteFutureKey(), bean);	
						} else {
							tradewareTool.getTradingDataMap15Minute().put(bean.getKiteFutureKey(), bean);	
						}
						currentCandleFrameCount++;
					} else {
						continue;
					}
				} else {

					TradewareLogger.saveErrorLog(
							CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " [" + super.threadName + "]",
							METHOD_GET_15_MINUTUE_CANDLE_DATA_FULL, new Exception(METHOD_KITE_HIST_DATE_ERROR),
							ERROR_SEVERIRITY_ERROR, ERROR_SEVERIRITY_ERROR);
				}
			} else {
					/*TradewareLogger.saveInfoLog(CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " ["+super.threadName+"]",
							METHOD_GET_15_MINUTUE_CANDLE_DATA_FULL,
							METHOD_KITE_HIST_DATE_ERROR);*/
					/*
					TradewareLogger.saveErrorLog(
							CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " [" + super.threadName + "]",
							METHOD_GET_15_MINUTUE_CANDLE_DATA_FULL, new Exception(METHOD_KITE_HIST_DATE_ERROR),
							ERROR_SEVERIRITY_ERROR, ERROR_SEVERIRITY_ERROR);*/
				}
			} 
		}
		
	}
	
	private static boolean narrow7CompressRuleInd = false;
	private boolean applyNR7Rule (HistoricalData histData, StrategyOrbDataBean bean) {
		boolean narrow7RuleInd = false;
		narrow7CompressRuleInd = false;
		List<HistoricalData> histDataList = histData.dataArrayList;
			if (null != histDataList && histDataList.size() >= 5 && !tradewareTool.isTimeBefore10_15AM()) {
			
			HistoricalData data = histDataList.get(histDataList.size() - 2);
			double high4 = data.high;
			double low4 = data.low;
			double close4 = data.close;
			double open4 = data.open;
			
			data = histDataList.get(histDataList.size() - 3);
			double high3 = data.high;
			double low3 = data.low;
			double close3 = data.close;
			double open3 = data.open;

			data = histDataList.get(histDataList.size() - 4);
			double high2 = data.high;
			double low2 = data.low;
			double close2 = data.close;
			double open2 = data.open;

			data = histDataList.get(histDataList.size() - 5);
			double high1 = data.high;
			double low1 = data.low;
			double close1 = data.close;
			double open1 = data.open;

			// NR7 logic
			double day4HighLowDiff = high4 - low4;
			double day3HighLowDiff = high3 - low3;
			double day2HighLowDiff = high2 - low2;
			double day1HighLowDiff = high1 - low1;

			if (day4HighLowDiff < day3HighLowDiff && day4HighLowDiff < day2HighLowDiff
					&& day4HighLowDiff < day1HighLowDiff) {
				// Rule 2
				if (((high4 < high3) && (low4 > low3)) || ((high4 > high3) && (low4 < low3))) {
					narrow7RuleInd = true;
				}
			} // 04-21-2021  start - afterSomeSuccess - 06/08/2021
			if (!narrow7RuleInd && (high1 >= high2 && high1 >= high3 && high1 >= high4)
					&& (low1 <= low2 && low1 <= low3 && low1 <= low4)) {
					//&& (low1 <= low2 && low1 <= low2 && low3 <= low4)) {
				narrow7RuleInd = true;
				narrow7CompressRuleInd = true;
				bean.setTradePlaceRule(TRADE_PLACE_RULE_COMPRESS_BLAST);
			}
			// 04-21-2021  end - afterSomeSuccess - 06/08/2021
				if (narrow7RuleInd) {
					
					bean.setStrategyRule(STRATEGY_NR7);
					double highMinusClose4 = kiteConnectTool.getRoundupToTwoValue(high4 - close4);
					double closeMinusLow4 = kiteConnectTool.getRoundupToTwoValue(close4 - low4);
					double highMinusClose3 = kiteConnectTool.getRoundupToTwoValue(high3 - close3);
					double closeMinusLow3 = kiteConnectTool.getRoundupToTwoValue(close3 - low3);
					double highMinusClose2 = kiteConnectTool.getRoundupToTwoValue(high2 - close2);
					double closeMinusLow2 = kiteConnectTool.getRoundupToTwoValue(close2 - low2);
					double highMinusClose1 = kiteConnectTool.getRoundupToTwoValue(high1 - close1);
					double closeMinusLow1 = kiteConnectTool.getRoundupToTwoValue(close1 - low1);
				//11/25/2020 start
				double candleHighsDiff = kiteConnectTool.getRoundupToTwoValue((high3 - high4) * bean.getLotSize());
				double candleLowsDiff = kiteConnectTool.getRoundupToTwoValue((low4 - low3) * bean.getLotSize());
				if (((highMinusClose4 > 1 && ((highMinusClose4 * 7) <= closeMinusLow4)) && candleHighsDiff <= 10000)
						|| ((closeMinusLow4 > 1 && ((closeMinusLow4 * 7) <= highMinusClose4))
								&& candleLowsDiff <= 10000)) {
					bean.setStrategyRule(STRATEGY_NR7_R1);
					// bean.setTempCustomTradingRuleInd(true);
				} else if ((((highMinusClose4 == 0) || (highMinusClose4 * 5) <= closeMinusLow4))
						|| (((closeMinusLow4 == 0) || (closeMinusLow4 * 5) <= highMinusClose4))) {
					bean.setStrategyRule(STRATEGY_NR7_R2);

				} else {
					bean.setStrategyRule(STRATEGY_NR7);
					// continue;
				}
					
				bean.setBuyAtVal(high4);
				bean.setSellAtVal(low4);
				// 04-21-2021 start - afterSomeSuccess - 06/08/2021
				if (narrow7CompressRuleInd) {
					bean.setBuyAtVal(high1);
					bean.setSellAtVal(low1);
				}
				// 04-21-2021 end - afterSomeSuccess - 06/08/2021
				
				if (open4 < close4) {
					if (closeMinusLow4 > highMinusClose4) {
						bean.setStrengthStyleClass(GREEN_BOLD_FONT_STYLE_CLASS);
						bean.setStrengthableTradeStateId(GREEN_BUY_PRESSURE);
						bean.setStrengthableTradeInfo(getStrengthTradeBUY(closeMinusLow4,  highMinusClose4));
					} else {
						bean.setStrengthStyleClass(RED_BOLD_FONT_STYLE_CLASS);
						bean.setStrengthableTradeStateId(GREEN_SELL_PRESSURE);
						bean.setStrengthableTradeInfo(getStrengthTradeSELL(closeMinusLow4,  highMinusClose4));
					}
				} else if (open4 > close4) {
					if (highMinusClose4 > closeMinusLow4) {
						bean.setStrengthStyleClass(GREEN_BOLD_FONT_STYLE_CLASS);
						bean.setStrengthableTradeStateId(RED_SELL_PRESSURE);
						bean.setStrengthableTradeInfo(getStrengthTradeSELL(closeMinusLow4,  highMinusClose4));
					} else {
						bean.setStrengthStyleClass(RED_BOLD_FONT_STYLE_CLASS);
						bean.setStrengthableTradeStateId(RED_BUY_PRESSURE);
						bean.setStrengthableTradeInfo(getStrengthTradeBUY(closeMinusLow4,  highMinusClose4));
					}
				} else {
					bean.setStrengthableTradeStateId(STNA); 
				}

				bean.setCandle4SizeAmt(kiteConnectTool.getRoundupToTwoValue((high4 - low4) * bean.getLotSize()));
				bean.setCandle3SizeAmt(kiteConnectTool.getRoundupToTwoValue((high3 - low3) * bean.getLotSize()));
				bean.setCandle2SizeAmt(kiteConnectTool.getRoundupToTwoValue((high2 - low2) * bean.getLotSize()));
				bean.setCandle1SizeAmt(kiteConnectTool.getRoundupToTwoValue((high1 - low1) * bean.getLotSize()));
				
				bean.setCandle4HighMinusClose(highMinusClose4);
				bean.setCandle4CloseMinusLow(closeMinusLow4);
				bean.setCandle3HighMinusClose(highMinusClose3);
				bean.setCandle3CloseMinusLow(closeMinusLow3);
				bean.setCandle2HighMinusClose(highMinusClose2);
				bean.setCandle2CloseMinusLow(closeMinusLow2);
				bean.setCandle1HighMinusClose(highMinusClose1);
				bean.setCandle1CloseMinusLow(closeMinusLow1);
				
				bean.setCandle4Type(open4 < close4 ? GREEN_CANDLE : RED_CANDLE);
				bean.setCandle3Type(open3 < close3 ? GREEN_CANDLE : RED_CANDLE);
				bean.setCandle2Type(open2 < close2 ? GREEN_CANDLE : RED_CANDLE);
				bean.setCandle1Type(open1 < close1 ? GREEN_CANDLE : RED_CANDLE);
				
				bean.setCandleHighsDiff(kiteConnectTool.getRoundupToTwoValue((high3 - high4) * bean.getLotSize()));
				bean.setCandleLowsDiff(kiteConnectTool.getRoundupToTwoValue((low4 - low3) * bean.getLotSize()));
				
				//bean = validateOHLCRule(bean);
				updateCandleTypeTrendId(bean, open3, close3, close4);
				bean.setCandleNumber(tradewareTool.getCandleNumber());
				
				kiteConnectTool.getSymbolSetForOHLCInput().add(bean.getKiteFutureKey());
				
				volumeStrenthTrend(histDataList, bean);
				caluclatePivotPoints(bean, high4, low4, close4);
				
				if (((high4 > high3) && (low4 < low3))) {
					//info = info + REEVERSE_MOTHER_CANDLE;
					//bean.setTradableStrength(info);
					bean.setReverseMotherCandleInd(true);
					bean.setCandleHighsDiff(Math.abs(bean.getCandleHighsDiff()));
					bean.setCandleLowsDiff(Math.abs(bean.getCandleLowsDiff()));
				}

				
				copyCurrentCandleAverages(bean, histDataList.get(histDataList.size() - 1).open);
				setPreviousCandleOhlcData(histDataList, bean);

				//Looks like not much useful, commenting out on Nov/14/2020
				findHeikinAshiTrend( histDataList, bean);
				if (tradewareTool.is15MinteDayLevelCandle()) {
					tradewareTool.getTradingDataMap15MinuteDayLevels().put(bean.getKiteFutureKey(), bean);	
				} else {
					tradewareTool.getTradingDataMap15Minute().put(bean.getKiteFutureKey(), bean);	
				}
				
				currentCandleFrameCount++;
			} 
		} else {
			if (!tradewareTool.isTimeBefore10_15AM()) {
				TradewareLogger.saveInfoLog(CLASS_TRADING_DATA_MAP_HIST_DATA_15_MINUTE_THREAD + " ["+super.threadName+"]",
						METHOD_APPLY_NR7_RULE, INVALID_KITE_HIST_DATA_FATAL_ERROR);
			}
		}
			return narrow7RuleInd;
	}
	
	private void updateCandleTypeTrendId(StrategyOrbDataBean bean, Double open3, Double  close3, Double close4) {
		// start
		if (GREEN_CANDLE.equals(bean.getCandle1Type()) && GREEN_CANDLE.equals(bean.getCandle2Type())) {
			if (close3 < close4) {
				bean.setCandleTypeTrendId(BUY);
			}
		} else if (RED_CANDLE.equals(bean.getCandle1Type()) && RED_CANDLE.equals(bean.getCandle2Type())) {
			if (close3 > close4) {
				bean.setCandleTypeTrendId(SELL);
			}
		}
		if (GREEN_CANDLE.equals(bean.getCandle1Type()) && RED_CANDLE.equals(bean.getCandle2Type())) {
			if (open3 > close4) {
				bean.setCandleTypeTrendId(SELL);
			}
		} else if (RED_CANDLE.equals(bean.getCandle1Type()) && GREEN_CANDLE.equals(bean.getCandle2Type())) {
			if (open3 < close4) {
				bean.setCandleTypeTrendId(BUY);
			}
		}
		// end
	}
}
