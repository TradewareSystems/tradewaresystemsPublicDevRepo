package nr4.sathvikashviktechtrader.stockmarket.tool;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.NetworkException;
import com.zerodhatech.models.HistoricalData;
import com.zerodhatech.models.LTPQuote;

import nr4.sathvikashviktechtrader.stockmarket.bean.Narrow7DataBean;
import nr4.sathvikashviktechtrader.stockmarket.helper.DatabaseHelper;
import nr4.sathvikashviktechtrader.stockmarket.log.NkpAlgoLogger;
import nr4.sathvikashviktechtrader.stockmarket.util.Constants;
import nr4.sathvikashviktechtrader.stockmarket.util.StockUtil;


public class NR7AndUTCommonTool extends AbstractTool {

	// Strategy logic
	public void applyNarrow7StrategyRule(Narrow7DataBean bean) {
		double day1HighLowDiff = bean.getDay1High().doubleValue() - bean.getDay1Low().doubleValue();
		double day2HighLowDiff = bean.getDay2High().doubleValue() - bean.getDay2Low().doubleValue();
		double day3HighLowDiff = bean.getDay3High().doubleValue() - bean.getDay3Low().doubleValue();
		double day4HighLowDiff = bean.getDay4High().doubleValue() - bean.getDay4Low().doubleValue();
		/*double day5HighLowDiff = bean.getDay5High().doubleValue() - bean.getDay5Low().doubleValue();
		double day6HighLowDiff = bean.getDay6High().doubleValue() - bean.getDay6Low().doubleValue();
		double day7HighLowDiff = bean.getDay7High().doubleValue() - bean.getDay7Low().doubleValue();*/

		/*// Rule 1
		if (day1HighLowDiff < day2HighLowDiff && day1HighLowDiff < day3HighLowDiff && day1HighLowDiff < day4HighLowDiff
				&& day1HighLowDiff < day5HighLowDiff && day1HighLowDiff < day6HighLowDiff
				&& day1HighLowDiff < day7HighLowDiff) {
			// Rule 2
			if ((bean.getDay1High().doubleValue() < bean.getDay2High().doubleValue())
					&& (bean.getDay1Low().doubleValue() > bean.getDay2Low().doubleValue())) {
				bean.setNarrow7RuleInd(Boolean.TRUE);
			}
		}*/
		
		// Rule 1
				if (day1HighLowDiff < day2HighLowDiff && day1HighLowDiff < day3HighLowDiff && day1HighLowDiff < day4HighLowDiff) {
					// Rule 2
					if ((bean.getDay1High().doubleValue() < bean.getDay2High().doubleValue())
							&& (bean.getDay1Low().doubleValue() > bean.getDay2Low().doubleValue())) {
						bean.setNarrow7RuleInd(Boolean.TRUE);
					}
				}
	}

	private static NR7AndUTCommonTool singletonTool;

	private NR7AndUTCommonTool() {
	}

	public static NR7AndUTCommonTool getInstance() {
		if (null == singletonTool) {
			singletonTool = new NR7AndUTCommonTool();
		}
		return singletonTool;
	}

	private static boolean isTradingTime = false;
	private static boolean scanForBuyOrSell = false;

	public static boolean isScanForBuyOrSell() {
		return scanForBuyOrSell;
	}

	public static void setScanForBuyOrSell(boolean scanForBuyOrSell) {
		NR7AndUTCommonTool.scanForBuyOrSell = scanForBuyOrSell;
	}

	public boolean isTradingTime() {
		if (!isTradingTime) {
			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)); // Trading closed
			LocalDateTime tradeStartTime = LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).atTime(LocalTime.of(9, 15, 00, 59)); // Trading
																													// start
			LocalDateTime tradeClosedTime = LocalDate.now(ZoneId.of(Constants.TIME_ZONE)).atTime(LocalTime.of(15, 29, 59, 59)); // closed

			// Still have to consider , NSE holidays from DB table
			if (!(currentTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				if (currentTime.isAfter(tradeStartTime) && currentTime.isBefore(tradeClosedTime)) {
					isTradingTime = true;
				}
			}
		}
		return isTradingTime;
	}

	public void setTradingTime(boolean isTradingTimeInput) {
		isTradingTime = isTradingTimeInput;
	}

	public void forceExitTradesAt3_20PM() {
		for (String symbol : getMonitorNR7().keySet()) {
			Narrow7DataBean bean = getMonitorNR7().get(symbol);
			double lastPrice = bean.getLtp().doubleValue();
			if (Constants.BUY.equals(bean.getTradedState())) {
				lastPrice = bean.getBuyerAt();

				if (lastPrice > bean.getTradedVal().doubleValue()) {
					bean.setTradedState(Constants.BUY_EXIT_PROFIT + Constants.EXIT_FORCE);
					bean.setTradedOutAt(getCurrentDateTimeAsDate());
					double netProfitVal =  getRoundupValue2(
							(lastPrice - bean.getTradedVal().doubleValue()) * bean.getLotSize());
					bean.setProfitLossAmt(netProfitVal);
					bean.setSignalTriggeredOutAt(
							getCurrentTime() + Constants.BUY_TARGET + lastPrice + Constants.WITH_PROFIT + netProfitVal);

					DatabaseHelper.getInstance().updateTrade(bean);
					getMonitorNR7().replace(symbol, bean);
				} else if (lastPrice <= bean.getTradedVal().doubleValue()) {
					bean.setTradedState(Constants.BUY_EXIT_LOSS + Constants.EXIT_FORCE);
					bean.setTradedOutAt(getCurrentDateTimeAsDate());
					double netLossVal = getRoundupValue2(
							(lastPrice - bean.getTradedVal().doubleValue()) * bean.getLotSize());
					bean.setProfitLossAmt(netLossVal);
					bean.setSignalTriggeredOutAt(getCurrentTime() + Constants.CLOSED_WITH_LOSE + lastPrice
							+ Constants.WITH_LOSS + netLossVal);// +
					// additionalMsg);

					DatabaseHelper.getInstance().updateTrade(bean);
					getMonitorNR7().replace(symbol, bean);
				}
			} else if (Constants.SELL.equals(bean.getTradedState())) {
				lastPrice = bean.getBuyerAt();// lastPrice = narrow7Bean.getSellerAt();
				if (lastPrice < bean.getTradedVal().doubleValue()) {
					bean.setTradedState(Constants.SELL_EXIT_PROFIT + Constants.EXIT_FORCE);
					bean.setTradedOutAt(getCurrentDateTimeAsDate());
					double netProfitVal = getRoundupValue2(
							(bean.getTradedVal().doubleValue() - lastPrice) * bean.getLotSize());
					bean.setProfitLossAmt(netProfitVal);
					bean.setSignalTriggeredOutAt(getCurrentTime() + Constants.SELL_TARGET + lastPrice
							+ Constants.WITH_PROFIT + netProfitVal);

					DatabaseHelper.getInstance().updateTrade(bean);
					getMonitorNR7().replace(symbol, bean);
				} else if (lastPrice >= bean.getTradedVal().doubleValue()) {
					bean.setTradedState(Constants.SELL_EXIT_LOSS + Constants.EXIT_FORCE);
					bean.setTradedOutAt(getCurrentDateTimeAsDate());
					double netLossVal = getRoundupValue2(
							(bean.getTradedVal().doubleValue() - lastPrice) * bean.getLotSize());
					bean.setProfitLossAmt(netLossVal);
					bean.setSignalTriggeredOutAt(getCurrentTime() + Constants.CLOSED_WITH_LOSE + lastPrice
							+ Constants.WITH_LOSS + netLossVal);

					DatabaseHelper.getInstance().updateTrade(bean);
					getMonitorNR7().replace(symbol, bean);
				}
			}
		}
	}
	
	// One time call from scheduler to set future key and instrument
			public void updateKiteFutureKeyAndInstrumentToken() {
				try {
					NkpAlgoLogger.saveInfoLog(Constants.CLASS_SATHVIKASHVIKTECHTRADERTOOL, Constants.METHO_UPDATEKITEFUTUREKEYANDINSTRUMENTTOKEN,
							Constants.CALL_ENTRY);
					
					if (null != StockUtil.getSymbols() && !StockUtil.getSymbols().isEmpty()) {
						Map<String, LTPQuote> quotesMap = null;
						Object[] symbolArr = StockUtil.getSymbols().keySet().toArray();
						List<String> symbolsArray = new ArrayList<String>();
						for (int i = 0; i < symbolArr.length; i++) {
							symbolsArray.add(Constants.FUTURE_KEY_PREFIX_NSE + symbolArr[i].toString());
							symbolsArray.add(getKiteFuturekey(symbolArr[i].toString()));
						}
						quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
								.getLTP(symbolsArray.toArray(new String[0]));

						for (String key : quotesMap.keySet()) {
							System.out.println(key);
							if (key.startsWith(Constants.FUTURE_KEY_PREFIX_NSE)) {
								String symbol = key.replace(Constants.FUTURE_KEY_PREFIX_NSE, Constants.EMPTY_STRING);
								String futureKey = getKiteFuturekey(symbol);
								LTPQuote cashQuote = quotesMap.get(key);
								LTPQuote futureQuote = quotesMap.get(futureKey);

								Narrow7DataBean cashBean = new Narrow7DataBean(StockUtil.getSymbols().get(symbol), symbol);
								cashBean.setKiteFutureKey(key);
								cashBean.setInstrumentToken(cashQuote.instrumentToken);
								Narrow7DataBean futureBean = new Narrow7DataBean(StockUtil.getSymbols().get(symbol),
										symbol + Constants.FUTURE_SYMBOL);
								futureBean.setKiteFutureKey(futureKey);
								futureBean.setInstrumentToken(futureQuote.instrumentToken);

								/*getMonitorNR7().put(key, cashBean);
								getMonitorNR7().put(futureKey, futureBean);*/
								getBaseForNR7AndUT().put(key, cashBean);
								getBaseForNR7AndUT().put(futureKey, futureBean);
							}
						}
						NkpAlgoLogger.saveInfoLog(Constants.CLASS_SATHVIKASHVIKTECHTRADERTOOL, Constants.METHO_UPDATEKITEFUTUREKEYANDINSTRUMENTTOKEN,
								"baseDataMap -------   "+getBaseForNR7AndUT().size());
					} else {
						NkpAlgoLogger.saveInfoLog(Constants.CLASS_SATHVIKASHVIKTECHTRADERTOOL, Constants.METHO_UPDATEKITEFUTUREKEYANDINSTRUMENTTOKEN,
								"FATAL ERROR - base map is empty");
					}
					NkpAlgoLogger.saveInfoLog(Constants.CLASS_SATHVIKASHVIKTECHTRADERTOOL, Constants.METHO_UPDATEKITEFUTUREKEYANDINSTRUMENTTOKEN,
							Constants.CALL_EXIT);
				} catch (JSONException | IOException | KiteException e) {
					NkpAlgoLogger.logError(NR7AndUTCommonTool.class, "updateKiteFutureKeyAndInstrumentToken",
							"FATAL ERROR EXCEPTION - base map is empty" + getCurrentDateTime());
				}
			}
			
		private Set<String> processedSymbols = new HashSet<String>();

		public void get15MinutueCandleData() {
			long entry = System.currentTimeMillis();
			NkpAlgoLogger.saveInfoLog(Constants.CLASS_SATHVIKASHVIKTECHTRADERTOOL, Constants.METHOD_GET15MINUTUECANDLEDATA,
					Constants.CALL_ENTRY);
			processedSymbols.clear();
			try {
			if (null != getMonitorNR7() && !getMonitorNR7().isEmpty()) {
				for (String key : getMonitorNR7().keySet()) {
					Narrow7DataBean bean = getMonitorNR7().get(key);
					if (Constants.BUY.equals(bean.getTradedState()) || Constants.SELL.equals(bean.getTradedState())) {
						processedSymbols.add(key);
						continue;
					} else {
						getMonitorNR7().remove(key);
					}
				}
			}

				try {
					if (canPlaceOrders() && canUpdate15MinutesData()) {
						get15MinutueCandleDataFull();
					}
					processedSymbols.clear();
				} catch (NetworkException e) {
					NkpAlgoLogger.saveErrorLog(Constants.CLASS_SATHVIKASHVIKTECHTRADERTOOL,
							Constants.METHOD_GET15MINUTUECANDLEDATAFULL, e, Constants.ERROR_TYPE_NETWORKEXCEPTION,
							Constants.ERROR_SEVERIRITY_FATAL);
					if (getMonitorNR7().size() != processedSymbols.size()) {
						get15MinutueCandleDataFull();
					}
				}

				long exit = System.currentTimeMillis();
				NkpAlgoLogger.saveInfoLog(Constants.CLASS_SATHVIKASHVIKTECHTRADERTOOL,
						Constants.METHOD_GET15MINUTUECANDLEDATA,
						Constants.CALL_EXIT + " > processed - " + processedSymbols.size() + "  -basemap - "
								+ getMonitorNR7().size() + "Total time " + (exit - entry) / 1000);

			} catch (JSONException | IOException | KiteException e) {
				NkpAlgoLogger.saveErrorLog(Constants.CLASS_SATHVIKASHVIKTECHTRADERTOOL,
						Constants.METHOD_GET15MINUTUECANDLEDATA, e, Constants.ERROR_TYPE_KITEEXCEPTION,
						Constants.ERROR_SEVERIRITY_FATAL);
			}
		}
			private Date fromDateForKiteHistData_9_15;
			public Date getFromDateForKiteHistDataOnCurrentDay_9_15() {
				if (null == fromDateForKiteHistData_9_15) {
				LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)); // Trading closed
				SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_PATTERN_HIST_DATA);
				Date from = Calendar.getInstance().getTime();
				try {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_HIST_DATA);
					currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(),
							9, 15, 0);
					from = formatter.parse(currentTime.format(dtf));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fromDateForKiteHistData_9_15 = from;
				NkpAlgoLogger.printWithNewLine("getFromDateForKiteHistDataOnCurrentDay_9_15  --- "+fromDateForKiteHistData_9_15);
				}
				return fromDateForKiteHistData_9_15;
			}
			private Date toDateForKiteHistData;
			public Date getToDateForKiteHistData_WithCurrentTime() {
				if (null == toDateForKiteHistData) {
					LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)); // Trading closed
					SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_PATTERN_HIST_DATA);
					Date toDate = Calendar.getInstance().getTime();
					try {
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_HIST_DATA);
						currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
								currentTime.getDayOfMonth(), currentTime.getHour(), currentTime.getMinute(),
								currentTime.getSecond());
						toDate = formatter.parse(currentTime.format(dtf));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					toDateForKiteHistData = toDate;
				}
				return toDateForKiteHistData;
			}
			private void get15MinutueCandleDataFull() throws JSONException, IOException, KiteException, NetworkException {
				//Must clear toDateForKiteHistData before loop start
				toDateForKiteHistData = null;
				setBaseForNR7AndUT(updateDayOHLC(getBaseForNR7AndUT()));
				for (String key : getBaseForNR7AndUT().keySet()) {
					
					if (!getMonitorNR7().keySet().contains(key) && !processedSymbols.contains(key)) {
						Narrow7DataBean bean = getBaseForNR7AndUT().get(key).clone();
						HistoricalData histData = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER)
								.getHistoricalData(/*getFromDateForKiteHistData_9_15()*/getFromDateForKiteHistDataOnCurrentDay_9_15(),
										getToDateForKiteHistData_WithCurrentTime(), String.valueOf(bean.getInstrumentToken()),
										//KITE_HIST_DATA_5_MINUTES_INTERVAL, false);
										Constants.KITE_HIST_DATA_15_MINUTES_INTERVAL, false);
						processedSymbols.add(key);
						List<HistoricalData> histDataList = histData.dataArrayList;
						if (null != histDataList && histDataList.size() > 4) {
							HistoricalData data = histDataList.get(histDataList.size() - 2);
							double high1 = data.high;
							double low1= data.low;
							double close1= data.close;
							double open1= data.open;

							data = histDataList.get(histDataList.size() - 3);
							double high2 = data.high;
							double low2 = data.low;
							double close2= data.close;
							double open2= data.open;
							
							data = histDataList.get(histDataList.size() - 4);
							double high3 = data.high;
							double low3 = data.low;
							double close3= data.close;
							double open3= data.open;
							
							data = histDataList.get(histDataList.size() - 5);
							double high4 = data.high;
							double low4 = data.low;
							double close4= data.close;
							double open4= data.open;
							
							bean.setDay1Open(open1);
							bean.setDay1High(high1);
							bean.setDay1Low(low1);
							bean.setDay1Close(close1);
							
							bean.setDay2Open(open2);
							bean.setDay2High(high2);
							bean.setDay2Low(low2);
							bean.setDay2Close(close2);
							
							bean.setDay3Open(open3);
							bean.setDay3High(high3);
							bean.setDay3Low(low3);
							bean.setDay3Close(close3);

							bean.setDay4Open(open4);
							bean.setDay4High(high4);
							bean.setDay4Low(low4);
							bean.setDay4Close(close4);
							applyNarrow7StrategyRule(bean);
					System.out.println(key + " -  "+bean.getNarrow7RuleInd());		
							if(null != bean.getNarrow7RuleInd() && bean.getNarrow7RuleInd()) {
								bean.setBuyAtVal(high1);
								bean.setSellAtVal(low1);
								
								double highMinusClose1 = getRoundupValue2(high1 - close1);
								double closeMinusLow1 = getRoundupValue2(close1 - low1);
								double highMinusClose2 = getRoundupValue2(high2 - close2);
								double closeMinusLow2 = getRoundupValue2(close2 - low2);
								double highMinusClose3 = getRoundupValue2(high3 - close3);
								double closeMinusLow3 = getRoundupValue2(close3 - low3);
								double highMinusClose4 = getRoundupValue2(high4 - close4);
								double closeMinusLow4 = getRoundupValue2(close4 - low4);
								
								String info = Constants.EMPTY_STRING;
								if (open1 < close1) {
									if(closeMinusLow1 > highMinusClose1) {
										 info = "<span style='color:green'><b>G-BUY-PRESSURE-"+closeMinusLow1+Constants.COMMA+highMinusClose1+"</b></span>";
										 bean.setStrengthTradableState(Constants.GREEN_BUY_PRESSURE);
									} else {
										 info = "<span style='color:red'><b>G-SELL-PRESSURE"+closeMinusLow1+Constants.COMMA+highMinusClose1+"</b></span>";
										 bean.setStrengthTradableState(Constants.GREEN_SELL_PRESSURE);
									}
								} else if (open1 > close1) {
									if( highMinusClose1 > closeMinusLow1) {
										 info = "<span style='color:green'><b>R-SELL-PRESSURE"+highMinusClose1+Constants.COMMA+closeMinusLow1+"</b></span>";
										 bean.setStrengthTradableState(Constants.RED_SELL_PRESSURE);
									} else {
										 info = "<span style='color:red'><b>R-BUY-PRESSURE"+highMinusClose1+Constants.COMMA+closeMinusLow1+"</b></span>";
										 bean.setStrengthTradableState(Constants.RED_BUY_PRESSURE);
									}
								
							}
								
								bean.setCandleHighsDiff(getRoundupValue2((high1-high2)*bean.getLotSize()));
								bean.setCandleLowsDiff(getRoundupValue2((low2-low1)*bean.getLotSize()));
								
								bean.setTradableStrength(info);
								bean.setCandle1SizeAmt(getRoundupValue2((high1-low1)*bean.getLotSize()));
								bean.setCandle2SizeAmt(getRoundupValue2((high2-low2)*bean.getLotSize()));
								bean.setCandle3SizeAmt(getRoundupValue2((high3-low3)*bean.getLotSize()));
								bean.setCandle4SizeAmt(getRoundupValue2((high4-low4)*bean.getLotSize()));
								
								bean.setCandle1HighMinusClose(highMinusClose1);
								bean.setCandle1CloseMinusLow(closeMinusLow1);
								bean.setCandle2HighMinusClose(highMinusClose2);
								bean.setCandle2CloseMinusLow(closeMinusLow2);
								
								bean.setCandle3HighMinusClose(highMinusClose3);
								bean.setCandle3CloseMinusLow(closeMinusLow3);
								bean.setCandle4HighMinusClose(highMinusClose4);
								bean.setCandle4CloseMinusLow(closeMinusLow4);
								
								bean.setCandle1Type(open1<close1? Constants.GREEN_CANDLE : Constants.RED_CANDLE);
								bean.setCandle2Type(open2<close2? Constants.GREEN_CANDLE : Constants.RED_CANDLE);
								bean.setCandle3Type(open3<close3? Constants.GREEN_CANDLE : Constants.RED_CANDLE);
								bean.setCandle4Type(open4<close4? Constants.GREEN_CANDLE : Constants.RED_CANDLE);
								
								double ignorableSpike = getRoundupValue2(bean.getOpen().doubleValue() * Constants.IGNORABLE_OPEN_SPIKE);
								if (bean.getOpen().doubleValue() == bean.getLow().doubleValue()) {
									bean.setOhlcState(Constants.BUY);
								} else if (bean.getOpen().doubleValue() == bean.getHigh().doubleValue()) {
									bean.setOhlcState(Constants.SELL);
								} else if (bean.getLow().doubleValue() >= (bean.getOpen().doubleValue() - ignorableSpike)) {
									bean.setOhlcState(Constants.BUY_SPIKE);
								} else if (bean.getHigh().doubleValue() <= (bean.getOpen().doubleValue() + ignorableSpike)) {
									bean.setOhlcState(Constants.SELL_SPIKE);
								}
								getMonitorNR7().put(key, bean);
							} else {
								continue;
							}
						} else {
							//NkpAlgoLogger.printWithNewLine(">>> java.lang.ArrayIndexOutOfBoundsException: -1s>>>");
						}
					} else {
						//NkpAlgoLogger.printWithNewLine("Already trading>>> " + key);
					}
				}
			}
}