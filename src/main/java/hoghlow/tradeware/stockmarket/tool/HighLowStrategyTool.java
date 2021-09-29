package hoghlow.tradeware.stockmarket.tool;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;

import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.StockUtil;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.NetworkException;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.TokenException;
import com.zerodhatech.models.HistoricalData;
import com.zerodhatech.models.Quote;

import hoghlow.tradeware.stockmarket.bean.StockDataBean;
import hoghlow.tradeware.stockmarket.helper.DatabaseHelper;
import hoghlow.tradeware.stockmarket.log.NkpAlgoLogger;

public class HighLowStrategyTool extends AbstractAlgoTraderTool {
	private static HighLowStrategyTool singletonTool;

	private HighLowStrategyTool() {
	}

	public static HighLowStrategyTool getInstance() {
		if (null == singletonTool) {
			singletonTool = new HighLowStrategyTool();
		}
		return singletonTool;
	}
	
	//private StringBuilder volumeInfo = new StringBuilder("");
	private StringBuilder additinalInfo = new StringBuilder("");
	private Date fromDateForKiteHistData_9_15;
	public Date getFromDateForKiteHistData_9_15() {
		if (null == fromDateForKiteHistData_9_15) {
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
		Date from = Calendar.getInstance().getTime();
		if (currentTime.getDayOfWeek() == DayOfWeek.MONDAY) {
			currentTime = currentTime.minusDays(3);
		} else {
			currentTime = currentTime.minusDays(1);
		}
		
		for (LocalDate holiday : getNSEHolidays()) {
			NkpAlgoLogger.printWithNewLine(holiday +" - "+currentTime.toLocalDate()+" - "+holiday.isEqual(currentTime.toLocalDate()));
			if (holiday.isEqual(currentTime.toLocalDate())) {
				currentTime = currentTime.minusDays(1);
				//break;
			}
		}
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
			currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(),
					9, 15, 0);
			from = formatter.parse(currentTime.format(dtf));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fromDateForKiteHistData_9_15 = from;
		NkpAlgoLogger.printWithNewLine("fromDateForKiteHistData_9_15  --- "+fromDateForKiteHistData_9_15);
		}
		return fromDateForKiteHistData_9_15;
	}
	
	public Date getFromDateForKiteHistDataOnCurrentDay_9_15() {
		if (null == fromDateForKiteHistData_9_15) {
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
		Date from = Calendar.getInstance().getTime();
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
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
			LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
			Date toDate = Calendar.getInstance().getTime();
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
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

	public void get5MinutueCandleData() {
		processedSymbols.clear();
		long entry = System.currentTimeMillis();
		try {
			NkpAlgoLogger.logInfo(HighLowStrategyTool.class, "get5MinutueCandleData",  "  ENTRY "+getCurrentDateTime());

			if (null != getHighLowMap1200() && !getHighLowMap1200().isEmpty()) {
				for (String key : getHighLowMap1200().keySet()) {
					StockDataBean bean = getHighLowMap1200().get(key);
					if ((BUY.equals(bean.getTradedState()) || SELL.equals(bean.getTradedState()))) {
						processedSymbols.add(key);
						continue;
					} else {
						getHighLowMap1200().remove(key);
					}
				}
			}
			
			try {
				//if (HighLowStrategyTool.getInstance().canPlaceOrders()) {
				if (HighLowStrategyTool.canPlaceOrders) {
					get5MinutueCandleDataFull();
				}
			} catch (NetworkException e) {
				NkpAlgoLogger.printWithNewLine("NetworkException --- **** processed - "+processedSymbols.size()+"  -basemap - "+getBaseDataMap().size()  );
				e.printStackTrace();
				if (getBaseDataMap().size() != processedSymbols.size()) {
					get5MinutueCandleDataFull();
				}
				
			}
			
			long exit = System.currentTimeMillis();
			NkpAlgoLogger.logInfo(HighLowStrategyTool.class, "get5MinutueCandleData",  "  EXIT "+getCurrentDateTime() +"  -   "+"Total time " + (exit - entry) / 1000);
			NkpAlgoLogger.printWithNewLine("************************* processed - "+processedSymbols.size()+"  -basemap - "+getBaseDataMap().size()  );
		}  catch (JSONException | IOException | KiteException e) {
			e.printStackTrace();
		}
	}
	private Set<String> processedSymbols = new HashSet<String>();
	private void get5MinutueCandleDataFull() throws JSONException, IOException, KiteException, NetworkException {
		//Must clear toDateForKiteHistData before loop start
		toDateForKiteHistData = null;
		updateDayOHLC(getBaseDataMap());
		for (String key : getBaseDataMap().keySet()) {
			
			if (!getHighLowMap1200().keySet().contains(key) && !processedSymbols.contains(key)) {
				StockDataBean bean = getBaseDataMap().get(key).clone();
				HistoricalData histData = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER)
				//HistoricalData histData = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
						.getHistoricalData(/*getFromDateForKiteHistData_9_15()*/getFromDateForKiteHistDataOnCurrentDay_9_15(),
								getToDateForKiteHistData_WithCurrentTime(), String.valueOf(bean.getInstrumentToken()),
								//KITE_HIST_DATA_5_MINUTES_INTERVAL, false);
								KITE_HIST_DATA_15_MINUTES_INTERVAL, false);
				processedSymbols.add(key);
				List<HistoricalData> histDataList = histData.dataArrayList;
				if (null != histDataList && histDataList.size() >= 4) {
					HistoricalData data = histDataList.get(histDataList.size() - 2);
					double high3 = data.high;
					double low3 = data.low;

					data = histDataList.get(histDataList.size() - 3);
					double high2 = data.high;
					double low2 = data.low;

					data = histDataList.get(histDataList.size() - 4);
					double high1 = data.high;
					double low1 = data.low;

					if (high3 > high2 && high2 > high1) {
						bean.setTradableState(BUY);
						bean.setBuyOrSellAt(high3);
						bean.setStopLoss(low3);
						bean.setOppositeHighLowRule(low3 > low2 && low2 > low1);
					} else if (low3 < low2 && low2 < low1) {
						bean.setTradableState(SELL);
						bean.setBuyOrSellAt(low3);
						bean.setStopLoss(high3);
						bean.setOppositeHighLowRule(high3 < high2 && high2 < high1);
					} else {
						bean.setTradableState(NA);
						continue;
					}

					// current price or LTP
					data = histDataList.get(histDataList.size() - 1);
					bean.setLtp(data.close);
					
					//
					bean.setHistData(histData);
					bean = get5MinutueCandleDataForAdditionalInfo(bean, histData);
					//

					getHighLowMap1200().put(key, bean);
				} else {
					NkpAlgoLogger.printWithNewLine("SOFT ERROR>>>>>>>>>>>>>>>>>>>java.lang.ArrayIndexOutOfBoundsException: -1s>>>>>>>>>>>>>>>>>>");
				}
			} else {
				NkpAlgoLogger.printWithNewLine("Already trading>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + key);
			}
		}
	}
	
	public StockDataBean get5MinutueCandleDataForAdditionalInfo(StockDataBean bean, HistoricalData histData) {
		try {
			NkpAlgoLogger.logInfo(HighLowStrategyTool.class, "get5MinutueCandleDataForAdditionalInfo",
					bean.getKiteFutureKey() + getCurrentDateTime());

			List<HistoricalData> histDataList = histData.dataArrayList;
			if (null != histDataList && !histDataList.isEmpty()) {
				if (additinalInfo.length() > 0) {
					additinalInfo.delete(0, additinalInfo.length());
				}
				additinalInfo.append(getCurrentTime(getToDateForKiteHistData_WithCurrentTime())).append(COMMA_SPACE);

				HistoricalData data = histDataList.get(histDataList.size() - 2);
				double open3 = data.open;
				double high3 = data.high;
				double low3 = data.low;
				double close3 = data.close;
				long volume3 = data.volume;
	
				data = histDataList.get(histDataList.size() - 3);
				double open2 = data.open;
				double high2 = data.high;
				double low2 = data.low;
				double close2 = data.close;
				long volume2 = data.volume;

				data = histDataList.get(histDataList.size() - 4);
				double open1 = data.open;
				double high1 = data.high;
				double low1 = data.low;
				double close1 = data.close;
				long volume1 = data.volume;
				double heihenAshiopen = BigDecimal.valueOf((open2+close2)/2).setScale(2, 0).doubleValue();
				
				caluclateDayVWAP(bean, histDataList);
				//caluclateRSIForCurrentDay(bean);
				/*long pricevolume3=(((long)(high3 + low3 + close3)/3) * volume3);
				long pricevolume2=(((long)(high2 + low2 + close2)/3) * volume2);
				long pricevolume1=(((long)(high1 + low1 + close1)/3) * volume1);

				long pricevolumeTotals = (pricevolume3 + pricevolume2 +  pricevolume1);
				long volumesTotals = (volume3 + volume2 + volume1);
				//additinalInfo.append(pricevolumeTotals).append(COMMA_SPACE).append(volumesTotals).append(COMMA_SPACE);
				if (pricevolumeTotals != 0 && volumesTotals != 0) {
					double vwap = (pricevolumeTotals)/(volumesTotals);
					vwap = KiteConnectTool.getInstance().getRoundupValue2(vwap);	
					bean.setVwapVal(vwap);
				}*/
				
				
				double candleMovement = KiteConnectTool.getInstance().getRoundupValue2((high3 - low3) * bean.getLotSize());
				
				bean.setCandleMovement(candleMovement);
				 

				additinalInfo.append(open1 <= close1 ? "G" : "R").append(COMMA_SPACE)
						.append(open2 <= close2 ? "G" : "R").append(COMMA_SPACE).append(open3 <= close3 ? "G" : "R")
						.append(COMMA_SPACE);

				if ((open3 <= close3) && (open2 <= close2) && (open1 <= close1)) {
					bean.setGreen3Candle(true);
				}
				if ((open3 >= close3) && (open2 >= close2) && (open1 >= close1)) {
					bean.setRed3Candle(true);
				}
				bean.setLastCandle(open1 <= close1 ? "G" : "R");
				
				additinalInfo
				.append(KiteConnectTool.getInstance().getRoundupValue2((high3 - low3) * bean.getLotSize()));
				// if (BUY.equals(bean.getTradableState())) {
				if (volume3 != 0 && volume2 != 0 && volume1 != 0) {
					if (volume3 > volume2 && volume2 > volume1) {
						// additinalInfo.append(LEVEL2_BUY);

						bean.setVolumeTrend(LEVEL2_BUY);
					} else if (volume3 > volume2 && volume3 > volume1) {
						// additinalInfo.append(LEVEL1_BUY);
						bean.setVolumeTrend(LEVEL1_BUY);
					} else if (volume3 < volume2 && volume2 < volume1) {
						// additinalInfo.append(LEVEL2_SELL);
						bean.setVolumeTrend(LEVEL2_SELL);
					} else if (volume3 < volume2 && volume3 < volume1) {
						// additinalInfo.append(LEVEL1_SELL);
						bean.setVolumeTrend(LEVEL1_SELL);
					} else {
						// additinalInfo.append(LEVEL_NA);
						bean.setVolumeTrend(LEVEL_NA);
					}
				}
				// }
				double highMinusClose = KiteConnectTool.getInstance().getRoundupValue2(high3 - close3);
				double CloseMinusLow = KiteConnectTool.getInstance().getRoundupValue2(close3 - low3);
				String info = EMPTY_STRING;
				if (BUY.equals(bean.getTradableState())) {
					additinalInfo.append(COMMA_SPACE);
					/*if (high2 < close3 || high1 < close2) {
						additinalInfo.append("CAND_1_2_****---"+(high2 < close3 && high1 < close2));
					} else {
						additinalInfo.append("CAND_1_2_FAIL");
					}*/
					
					if((CloseMinusLow) > highMinusClose) {
						 info = "<span style='color:green'><b>BUY-"+CloseMinusLow+COMMA+highMinusClose+"</b></span>";
						 bean.setStrenthTradableState(GREEN_BUY);
						additinalInfo.append(info);
					} else {
						 info = "<span style='color:red'><b>SELL-"+CloseMinusLow+COMMA+highMinusClose+"</b></span>";
						 bean.setStrenthTradableState(RED_SELL);
						additinalInfo.append(info);	
					}
					bean.setStrengthFactor(CloseMinusLow-highMinusClose);
					bean.setStrengthFactorString(info);
					
					bean.setCloseHighRule1(high2 < close3);
					bean.setCloseHighRule2(high1 < close2);
					bean.setOpenHighLowRule(open3 == low3);
					bean.setOpenHighLowHARule(heihenAshiopen < open3 && heihenAshiopen < close3 && heihenAshiopen < low3);
					/*additinalInfo.append(COMMA_SPACE).append(high1).append(UNDER_SCRORE).append(high2)
							.append(UNDER_SCRORE).append(high3);*/
					if (close1 < close2 && close2 < close3) {
						//bean.setCloseHigherHighRule(true);
						bean.setCloseRule(true);
						//additinalInfo.append(COMMA_SPACE).append("CloseRuleTRUE");
					}
					//additinalInfo.append(COMMA_SPACE).append("3_CandleRule_").append(String.valueOf(bean.isGreen3Candle()));
					if(null != bean.getHigh()) {
						bean.setDayHighLowRule(high3 == bean.getHigh().doubleValue());
					}
					// OHLC RULE
					if ((null == bean.getOhlcState() || BUY.equals(bean.getOhlcState())) && null != bean.getOpen()
							&& null != bean.getLow()) {
						double ignorableSpike = KiteConnectTool.getInstance()
								.getRoundupValue(bean.getOpen().doubleValue() * LIMIT_ORDER_PLACABLE_PRICE);
						if (bean.getOpen().doubleValue() == bean.getLow().doubleValue()
								|| bean.getLow().doubleValue() >= (bean.getOpen().doubleValue() - ignorableSpike)) {
							bean.setOhlcState(BUY);
						} else {
							bean.setOhlcState(OHLC_BUY_FAIL);
						}
					}
					
					if (volume3 != 0 && volume2 != 0 && volume1 != 0) {
					bean.setDay1VolStrengthFactor(KiteConnectTool.getInstance().getRoundupValue2((double)volume3/(double)volume1));
					bean.setDay2VolStrengthFactor(KiteConnectTool.getInstance().getRoundupValue2((double)volume2/(double)volume1));
					additinalInfo.append(COMMA_SPACE).append(volume3).append(COMMA_SPACE).append(volume2).append(COMMA_SPACE).append(volume1);
					}
					
					double day1Movement = KiteConnectTool.getInstance().getRoundupValue2((high3 - high2) * bean.getLotSize());
					double day2Movement = KiteConnectTool.getInstance().getRoundupValue2((high2 - high1) * bean.getLotSize());
					
					double cprLowerBound = (high3 + low3)/2;
					double cprPivotalPoint = (high3 + low3 + close3)/3;
					double cprUpperBound = ((cprPivotalPoint - cprLowerBound) + cprPivotalPoint);
					double min5CPR = KiteConnectTool.getInstance().getRoundupValue2((cprUpperBound - cprLowerBound) * bean.getLotSize());
					
					double Min15Low = low3 < low2 ? low3 : low2;
					Min15Low = Min15Low < low1 ? Min15Low : low1;
					double cprLowerBound15 = (high3 + Min15Low)/2; 
					double cprPivotalPoint15 = (high3 + Min15Low + close3)/3;
					double cprUpperBound15 = ((cprPivotalPoint15 - cprLowerBound15) + cprPivotalPoint15);
					double min15CPR = KiteConnectTool.getInstance().getRoundupValue2((cprUpperBound15 - cprLowerBound15) * bean.getLotSize());
					bean.setDay1Movement(day1Movement);
					bean.setDay2Movement(day2Movement);
					bean.setMinute5CPR(min5CPR);
					bean.setMinute15CPR(min15CPR);
				} else if (SELL.equals(bean.getTradableState())) {
					additinalInfo.append(COMMA_SPACE);
					/*if (low2 > close3 || low1 > close2) {
						additinalInfo.append("CAND_1_2_****---"+(low2 > close3 && low1 > close2));
					} else {
						additinalInfo.append("CAND_1_2_FAIL");
					}*/
					if( highMinusClose > CloseMinusLow) {
						 info = "<span style='color:green'><b>SELL-"+highMinusClose+COMMA+CloseMinusLow+"</b></span>";
						 bean.setStrenthTradableState(GREEN_SELL);
						additinalInfo.append(info);
					} else {
						 info = "<span style='color:red'><b>BUY-"+highMinusClose+COMMA+CloseMinusLow+"</b></span>";
						 bean.setStrenthTradableState(RED_BUY);
						additinalInfo.append(info);	
					}
					bean.setStrengthFactor(highMinusClose-CloseMinusLow);
					bean.setStrengthFactorString(info);
					
					
					bean.setCloseHighRule1(low2 > close3);
					bean.setCloseHighRule2(low1 > close2);
					bean.setOpenHighLowRule(open3 == high3);
					bean.setOpenHighLowHARule(heihenAshiopen > open3 && heihenAshiopen > close3 && heihenAshiopen > high3);
					/*additinalInfo.append(COMMA_SPACE).append(low1).append(UNDER_SCRORE).append(low2)
							.append(UNDER_SCRORE).append(low3);*/
					if (close1 > close2 && close2 > close3) {
						//bean.setCloseHigherHighRule(true);
						bean.setCloseRule(true);
						//additinalInfo.append(COMMA_SPACE).append("CloseRuleTRUE");
					}
					//additinalInfo.append(COMMA_SPACE).append("3_CandleRule_").append(String.valueOf(bean.isRed3Candle()));
					if(null != bean.getLow()) {
						bean.setDayHighLowRule(low3 == bean.getLow().doubleValue());
					}
					// OHLC RULE
					if ((null == bean.getOhlcState() || SELL.equals(bean.getOhlcState())) && null != bean.getOpen()
							&& null != bean.getHigh()) {
						double ignorableSpike = KiteConnectTool.getInstance()
								.getRoundupValue(bean.getOpen().doubleValue() * LIMIT_ORDER_PLACABLE_PRICE);
						if (bean.getOpen().doubleValue() == bean.getHigh().doubleValue()
								|| bean.getHigh().doubleValue() <= (bean.getOpen().doubleValue() + ignorableSpike)) {
							bean.setOhlcState(SELL);
						} else {
							bean.setOhlcState(OHLC_SELL_FAIL);
						}
					}
					if (volume3 != 0 && volume2 != 0 && volume1 != 0) {
					bean.setDay1VolStrengthFactor(KiteConnectTool.getInstance().getRoundupValue2((double)volume1/(double)volume3));
					bean.setDay2VolStrengthFactor(KiteConnectTool.getInstance().getRoundupValue2((double)volume2/(double)volume1));
					additinalInfo.append(COMMA_SPACE).append(volume1).append(COMMA_SPACE).append(volume2).append(COMMA_SPACE).append(volume3);
					}
					
					double day1Movement = KiteConnectTool.getInstance().getRoundupValue2((low2 - low3) * bean.getLotSize());
					double day2Movement = KiteConnectTool.getInstance().getRoundupValue2((low1 - low2) * bean.getLotSize());
					
					double cprLowerBound = (high3 + low3)/2;
					double cprPivotalPoint = (high3 + low3 + close3)/3;
					double cprUpperBound = ((cprPivotalPoint - cprLowerBound) + cprPivotalPoint);
					double min5CPR = KiteConnectTool.getInstance().getRoundupValue2((cprUpperBound - cprLowerBound) * bean.getLotSize());
					
					double Min15High = high3 > high2 ? high3 : high2;
					Min15High = Min15High > high1 ? Min15High : high1;
					double cprLowerBound15 = (Min15High + low3)/2; 
					double cprPivotalPoint15 = (Min15High + low3 + close3)/3;
					double cprUpperBound15 = ((cprPivotalPoint15 - cprLowerBound15) + cprPivotalPoint15);
					double min15CPR = KiteConnectTool.getInstance().getRoundupValue2((cprUpperBound15 - cprLowerBound15) * bean.getLotSize());
					bean.setDay1Movement(day1Movement);
					bean.setDay2Movement(day2Movement);
					bean.setMinute5CPR(min5CPR);
					bean.setMinute15CPR(min15CPR);
				}
				//additinalInfo.append(COMMA_SPACE).append("3_CandleRule_").append(String.valueOf(bean.isGreen3Candle()));
				//additinalInfo.append(COMMA_SPACE).append("3_CandleRule_").append(String.valueOf(bean.isRed3Candle()));
				//additinalInfo.append(COMMA_SPACE).append("O=H=L---" + (bean.isOpenHighLowRule())).append(COMMA_SPACE).append("O=H=L(HA)---" + (bean.isOpenHighLowHARule()));
				
				//NR4
				double day1HighLowDiff = high3-low3;
				double day2HighLowDiff = high2-low2;
				double day3HighLowDiff = high1-low1;
				double day4HighLowDiff = day1HighLowDiff +1;//hack it
				if (histDataList.size() >=5) {
					data = histDataList.get(histDataList.size() - 5);
					//double open0 = data.open;
					double high0 = data.high;
					double low0 = data.low;
					//double close0 = data.close;
					//long volume0 = data.volume;
					 day4HighLowDiff = high0-low0;
				}
				
				bean.setNr4Rule((day1HighLowDiff < day2HighLowDiff && day1HighLowDiff < day3HighLowDiff && day1HighLowDiff < day4HighLowDiff));

				bean.setDay1CandleVolumes(volume3);
				/*if (((high3-low3)*bean.getLotSize()) < 2500) {
					bean.setSmallCandle(true);
					//additinalInfo.append(COMMA_SPACE).append("SMALL_CANDLE");
				}*/
				if (((high3-low3)*bean.getLotSize()) < 2500) {
					if (bean.isNr4Rule()) {
						if (((high3-low3)*bean.getLotSize()) < 1500) {
							
						}
					} else {
						bean.setSmallCandle(true);
					}
					additinalInfo.append(COMMA_SPACE).append("SMALL_CANDLE");
				}
					// temp start
					if (null != bean.getHigh() && null != bean.getLow()) {
						additinalInfo.append(COMMA_SPACE).append("DH-").append(bean.getHigh().doubleValue()).append(COMMA)
								.append("DL-").append(bean.getLow().doubleValue());
					}
					//temp end
					bean.setCprRuleInd(true);
				if ((null != bean.getMinute5CPR() && bean.getMinute5CPR() <= 0)
						|| (null != bean.getMinute15CPR() && bean.getMinute15CPR() <= 0)) {
					bean.setCprRuleInd(false);
				}
				bean.setAdditinalInfo(additinalInfo.toString());
			}

		} catch (/*JSONException | IOException | KiteException*/Exception e) {
			NkpAlgoLogger.logError(HighLowStrategyTool.class, "get5MinutueCandleDataForAdditionalInfo",
					bean.getKiteFutureKey() + getCurrentDateTime());
			e.printStackTrace();
		}
		return bean;
	}
	
	private void caluclateDayVWAP(StockDataBean bean, List<HistoricalData> histDataList) {
		long volumesTotals = 0;
		long pricevolumeTotals = 0;
		
		try {
			for (int i = 0; i < histDataList.size() - 1; i++) {
				HistoricalData data = histDataList.get(i);
				long tempPricevolumeTotals = (((long) (data.high + data.low + data.close) / 3) * data.volume);
				pricevolumeTotals = pricevolumeTotals + tempPricevolumeTotals; 
				volumesTotals = volumesTotals + data.volume;
			}
			if (pricevolumeTotals != 0 && volumesTotals != 0) {
				double vwap = (pricevolumeTotals)/(volumesTotals);
				vwap = KiteConnectTool.getInstance().getRoundupValue2(vwap);	
				bean.setVwapVal(vwap);
			}
		} catch (ArithmeticException ae) {
			NkpAlgoLogger.printWithNewLine(getCurrentTime() + " ArithmeticException ---" + bean.getStockName());
		}
	}
	
	public void caluclateRSI(StockDataBean bean) {
		if (getHighLowMap1200().keySet().contains(bean.getKiteFutureKey()) && null != bean.getHistData()
				&& bean.getHistData().dataArrayList.size() > 20) {
			double change = 0d, uptrend = 0d, downtrend = 0d, avgUptrend = 0d, avgDowntrend = 0d, rsi = 0d;
			for (int i = bean.getHistData().dataArrayList.size() - 19; i > 1; i--) {
				double close_1 = bean.getHistData().dataArrayList.get(i).close;
				double close = bean.getHistData().dataArrayList.get(i - 1).close;
				change = close - close_1;
				if (i > 4) {
					if (change > 0) {
						uptrend = uptrend + change;
					} else {
						downtrend = downtrend + Math.abs(change);
					}
				}
				if (i == 5) {
					avgUptrend = uptrend / 14;
					avgDowntrend = downtrend / 14;
				} else if (i == 4 || i == 3 || i == 2) {
					if (change > 0) {
						avgUptrend = (avgUptrend * 13 + change) / 14;
						avgDowntrend = (avgDowntrend * 13) / 14;
						rsi = 100 - 100 / ((avgUptrend / avgDowntrend) + 1);
					} else {
						avgDowntrend = (avgDowntrend * 13 + Math.abs(change)) / 14;
						avgUptrend = (avgUptrend * 13) / 14;
						rsi = 100 - 100 / ((avgUptrend / avgDowntrend) + 1);
					}

					if (i == 4) {
						bean.setRsi1(BigDecimal.valueOf(rsi).setScale(2, 0).doubleValue());
					} else if (i == 3) {
						bean.setRsi2(BigDecimal.valueOf(rsi).setScale(2, 0).doubleValue());
					} else if (i == 2) {
						bean.setRsi3(BigDecimal.valueOf(rsi).setScale(2, 0).doubleValue());
						break;
					}
				}
			}
			getHighLowMap1200().replace(bean.getKiteFutureKey(), bean);
		} else {
			NkpAlgoLogger.printWithNewLine("Already trading>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
		}
	}
	
	private void caluclateRSIForCurrentDay(StockDataBean bean) {
		if (getHighLowMap1200().keySet().contains(bean.getKiteFutureKey()) && null != bean.getHistData()
				/*&& bean.getHistData().dataArrayList.size() > 20*/) {
			double change = 0d, uptrend = 0d, downtrend = 0d, avgUptrend = 0d, avgDowntrend = 0d, rsi = 0d;
			for (int i = bean.getHistData().dataArrayList.size() - 1; /*i > 1*/i>=0; i--) {
				double close_1 = bean.getHistData().dataArrayList.get(i).close;
				double close = bean.getHistData().dataArrayList.get(i - 1).close;
				change = close - close_1;
				if (i > 2) {
					if (change > 0) {
						uptrend = uptrend + change;
					} else {
						downtrend = downtrend + Math.abs(change);
					}
				}
				if (i == 1) {
					avgUptrend = uptrend / 14;
					avgDowntrend = downtrend / 14;
				} else if (i == 0) {
					if (change > 0) {
						avgUptrend = (avgUptrend * 13 + change) / 14;
						avgDowntrend = (avgDowntrend * 13) / 14;
						rsi = 100 - 100 / ((avgUptrend / avgDowntrend) + 1);
					} else {
						avgDowntrend = (avgDowntrend * 13 + Math.abs(change)) / 14;
						avgUptrend = (avgUptrend * 13) / 14;
						rsi = 100 - 100 / ((avgUptrend / avgDowntrend) + 1);
					}
					 if (i == 0) {
						bean.setDayRsi(BigDecimal.valueOf(rsi).setScale(2, 0).doubleValue());
						break;
					}
				}
			}
			getHighLowMap1200().replace(bean.getKiteFutureKey(), bean);
		} else {
			NkpAlgoLogger.printWithNewLine("Already trading>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
		}
	}
	/*public StockDataBean get5MinutueCandleDataOnTradeEntryForVolumes(StockDataBean bean) {
		try {
			NkpAlgoLogger.logInfo(HighLowStrategyTool.class, "get5MinutueCandleDataOnTradeEntryForVolumes",
					bean.getKiteFutureKey() + getCurrentDateTime());

			if (volumeInfo.length() > 0) {
				volumeInfo.delete(0, volumeInfo.length());
			}
			volumeInfo.append(updateDayOHLC_VolumeOnCurrent(bean)).append(COMMA_SPACE);

			HistoricalData histData = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER)
					.getHistoricalData(getFromDateForKiteHistData_9_15(), getToDateForKiteHistData_WithCurrentTime(),
							String.valueOf(bean.getInstrumentToken()), KITE_HIST_DATA_5_MINUTES_INTERVAL, false);

			List<HistoricalData> histDataList = histData.dataArrayList;
			if (null != histDataList && !histDataList.isEmpty()) {
				int counter = 0;
				for (int i = 0; i < histDataList.size(); i++) {
					HistoricalData data = histDataList.get(histDataList.size() - (i + 1));
					volumeInfo.append(data.timeStamp).append(COMMA).append(data.volume);
					counter++;

					if (counter < 4) {
						volumeInfo.append(COMMA_SPACE);// instead of COMMA_SPACE , can add line break also
					} else {
						break;
					}
				}
				bean.setVolumeInfoOnTradeEntry(volumeInfo.toString());

				//
				if (additinalInfo.length() > 0) {
					additinalInfo.delete(0, additinalInfo.length());
				}
				additinalInfo.append(bean.getVolumes()).append(COMMA_SPACE);

				HistoricalData data = histDataList.get(histDataList.size() - 2);
				double open3 = data.open;
				double high3 = data.high;
				double low3 = data.low;
				double close3 = data.close;
				long volume3 = data.volume;

				data = histDataList.get(histDataList.size() - 3);
				double open2 = data.open;
				double high2 = data.high;
				double low2 = data.low;
				double close2 = data.close;
				long volume2 = data.volume;

				data = histDataList.get(histDataList.size() - 4);
				double open1 = data.open;
				double high1 = data.high;
				double low1 = data.low;
				double close1 = data.close;
				long volume1 = data.volume;

				additinalInfo.append(open1 <= close1 ? "G" : "R").append(COMMA_SPACE)
						.append(open2 <= close2 ? "G" : "R").append(COMMA_SPACE).append(open3 <= close3 ? "G" : "R")
						.append(COMMA_SPACE);

				if ((open3 <= close3) && (open2 <= close2) && (open1 <= close1)) {
					bean.setGreen3Candle(true);
				}
				if ((open3 >= close3) && (open2 >= close2) && (open1 >= close1)) {
					bean.setRed3Candle(true);
				}

				// if (BUY.equals(bean.getTradableState())) {
				if (volume3 > volume2 && volume2 > volume1) {
					additinalInfo.append(LEVEL2_BUY);
					bean.setVolumeTrend(LEVEL2_BUY);
				} else if (volume3 > volume2 && volume3 > volume1) {
					additinalInfo.append(LEVEL1_BUY);
					bean.setVolumeTrend(LEVEL1_BUY);
				} else if (volume3 < volume2 && volume2 < volume1) {
					additinalInfo.append(LEVEL2_SELL);
					bean.setVolumeTrend(LEVEL2_SELL);
				} else if (volume3 < volume2 && volume3 < volume1) {
					additinalInfo.append(LEVEL1_SELL);
					bean.setVolumeTrend(LEVEL1_SELL);
				} else {
					additinalInfo.append(LEVEL_NA);
					bean.setVolumeTrend(LEVEL_NA);
				}
				// }
				if (BUY.equals(bean.getTradedState())) {
					additinalInfo.append(COMMA_SPACE);
					if (high2 < close3) {
						additinalInfo.append("CAND_1_2_****");
					} else {
						additinalInfo.append("CAND_1_2_FAIL");
					}
				} else if (SELL.equals(bean.getTradedState())) {
					additinalInfo.append(COMMA_SPACE);
					if (low2 > close3) {
						additinalInfo.append("CAND_1_2_****");
					} else {
						additinalInfo.append("CAND_1_2_FAIL");
					}
				}

				bean.setAdditinalInfo(additinalInfo.toString());
			}

		} catch (JSONException | IOException | KiteException e) {
			NkpAlgoLogger.logError(HighLowStrategyTool.class, "get5MinutueCandleDataOnTradeEntryForVolumes",
					bean.getKiteFutureKey() + getCurrentDateTime());
			e.printStackTrace();
		}
		return bean;
	}*/
	
	/*public StockDataBean get5MinutueCandleDataOnTradeEntryForVolumesForNifty50(StockDataBean bean) {
		StockDataBean nifty50Bean = get5MinutueCandleDataOnTradeEntryForVolumes(getNifty50Bean());
		setNifty50Bean(nifty50Bean);
		bean.setVolumeInfoOnTradeEntryNifty50(nifty50Bean.getVolumeInfoOnTradeEntry());
		bean.setAdditionalInfoNifty50(nifty50Bean.getAdditinalInfo());
		bean.setGreen3CandleNifty50(nifty50Bean.isGreen3Candle());
		bean.setRed3CandleNifty50(nifty50Bean.isRed3Candle());
		bean.setVolumeTrendNifty50(nifty50Bean.getVolumeTrend());
		// Care about it
		bean.setVolumeTrend(bean.getVolumeTrend() + Constants.COMMA_SPACE + Constants.NIFTY_50 + ":"
				+ nifty50Bean.getVolumeTrend());
		return bean;
	}*/
	
	public String updateDayOHLC_VolumeOnCurrent(StockDataBean bean) {
		String currentVolumes = "currentVolumes";
		try {
			String[] symbolsArray = new String[1];
			symbolsArray[0] = bean.getKiteFutureKey();
			Map<String, Quote> ohlcMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
					.getQuote(symbolsArray);
			Quote quote = ohlcMap.get(bean.getKiteFutureKey());
			currentVolumes = getDateAsString(quote.timestamp) + COMMA + String.valueOf(quote.volumeTradedToday) + COMMA
					+ String.valueOf(quote.averagePrice);

		} catch (Exception e) {
			e.printStackTrace();
		} catch (TokenException e) {
			e.printStackTrace();
		} catch (KiteException e) {
			e.printStackTrace();
		}
		return currentVolumes;
	}
	
	protected String getDateAsString(Date date) {
		String datestr = EMPTY_STRING;
		try {
			// DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA);
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_HIST_DATA);
			datestr = formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datestr.replace(SPACE, UNDER_SCRORE);
	}
	
	public ConcurrentHashMap<String, StockDataBean> updateDayOHLC(
			ConcurrentHashMap<String, StockDataBean> dataMap) {
		try {
			if (null != StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)) {
				// NkpAlgoLogger.logInfo(HighLowStrategyTool.class, "updateDayOHLC",
				// getCurrentDateTime());
				Object[] symbolArr = dataMap.keySet().toArray();
				String[] symbolsArray = new String[symbolArr.length];
				for (int i = 0; i < symbolArr.length; i++) {
					symbolsArray[i] = symbolArr[i].toString();
				}

				Map<String, Quote> ohlcMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
						.getQuote(symbolsArray);

				for (String symbol : ohlcMap.keySet()) {
					StockDataBean bean = dataMap.get(symbol);
					if (null != bean) {
						double lastPrice = ohlcMap.get(symbol).lastPrice;
						double open = ohlcMap.get(symbol).ohlc.open;
						double high = ohlcMap.get(symbol).ohlc.high;
						double low = ohlcMap.get(symbol).ohlc.low;
						double close = ohlcMap.get(symbol).ohlc.close;

						bean.setLtp(lastPrice);
						bean.setOpen(open);
						bean.setHigh(high);
						bean.setLow(low);
						bean.setClose(close);
						if (ohlcMap.get(symbol).depth.buy.size() > 5 && ohlcMap.get(symbol).depth.sell.size() >= 5) {
							if (symbol.startsWith(FUTURE_KEY_PREFIX_NSE)) {
								bean.setBuyerAt(ohlcMap.get(symbol).depth.buy.get(4).getPrice());
								bean.setSellerAt(ohlcMap.get(symbol).depth.sell.get(4).getPrice());
							} else {
								bean.setBuyerAt(ohlcMap.get(symbol).depth.buy.get(0).getPrice());
								bean.setSellerAt(ohlcMap.get(symbol).depth.sell.get(0).getPrice());
							}
						} else {
							bean.setBuyerAt(ohlcMap.get(symbol).depth.buy.get(0).getPrice());
							bean.setSellerAt(ohlcMap.get(symbol).depth.sell.get(0).getPrice());
						}
						if (NA.equals(bean.getTradedState())) {
							bean.setVolumes((long)ohlcMap.get(symbol).volumeTradedToday);
						}
						dataMap.replace(symbol, bean);
					}
				}
			} else {
				NkpAlgoLogger.logError(AbstractAlgoTraderTool.class, "updateKiteFutureKeyAndInstrumentTokenForNifty50",
						"FATAL ERROR ADMIN LOGIN REQUIRED**********************************************"
								+ getCurrentDateTime());
			}
			
		} catch (java.net.UnknownHostException e) {
				NkpAlgoLogger.logError(HighLowStrategyTool.class, "updateDayOHLC",
						"UnknownHostException" + getCurrentDateTime());
				NkpAlgoLogger.printWithNewLine(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + dataMap.size());
				e.printStackTrace();
				//updateDayOHLC(dataMap);
		} catch (java.net.SocketTimeoutException e) {
			NkpAlgoLogger.logError(HighLowStrategyTool.class, "updateDayOHLC",
					"SocketTimeoutException" + getCurrentDateTime());
			NkpAlgoLogger.printWithNewLine(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+dataMap.size());
			e.printStackTrace();
			updateDayOHLC(dataMap);
		} 
		catch (NetworkException e) {
			NkpAlgoLogger.logError(HighLowStrategyTool.class, "updateDayOHLC",
					"NetworkException" + getCurrentDateTime());
			NkpAlgoLogger.printWithNewLine(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+dataMap.size());
			e.printStackTrace();
		} catch (Exception e) {
			NkpAlgoLogger.logError(HighLowStrategyTool.class, "updateDayOHLC", "Exception" + getCurrentDateTime());
			e.printStackTrace();
		} catch (TokenException e) {
			NkpAlgoLogger.logError(HighLowStrategyTool.class, "updateDayOHLC", "TokenException" + getCurrentDateTime());
			e.printStackTrace();
		} catch (KiteException e) {
			NkpAlgoLogger.logError(HighLowStrategyTool.class, "updateDayOHLC", "KiteException" + getCurrentDateTime());
			e.printStackTrace();
		}
		return dataMap;
	}
	
	public void forceExitTradesAt3_20PM() {
		for (String symbol : HighLowStrategyTool.getInstance().getHighLowMap1200().keySet()) {
			StockDataBean bean = HighLowStrategyTool.getInstance().getHighLowMap1200().get(symbol);
			double lastPrice = bean.getLtp().doubleValue();
			if (BUY.equals(bean.getTradedState())) {
				lastPrice = bean.getBuyerAt();

				if (lastPrice > bean.getTradedVal().doubleValue()) {
					bean.setTradedState(BUY_EXIT_PROFIT + EXIT_FORCE);
					bean.setTradedOutAt(HighLowStrategyTool.getInstance().getCurrentDateTimeAsDate());
					double netProfitVal = new BigDecimal(
							(lastPrice - bean.getTradedVal().doubleValue()) * bean.getLotSize()).setScale(2, 0)
									.doubleValue();
					bean.setProfitLossAmt(netProfitVal);
					bean.setSignalTriggeredOutAt(
							getCurrentTime() + BUY_TARGET + lastPrice + WITH_PROFIT + netProfitVal);

					DatabaseHelper.getInstance().updateTrade(bean);
					HighLowStrategyTool.getInstance().getHighLowMap1200().replace(symbol, bean);
				} else if (lastPrice <= bean.getTradedVal().doubleValue()) {
					bean.setTradedState(BUY_EXIT_LOSS + EXIT_FORCE);
					bean.setTradedOutAt(HighLowStrategyTool.getInstance().getCurrentDateTimeAsDate());
					double netLossVal = new BigDecimal(
							(lastPrice - bean.getTradedVal().doubleValue()) * bean.getLotSize()).setScale(2, 0)
									.doubleValue();
					bean.setProfitLossAmt(netLossVal);
					bean.setSignalTriggeredOutAt(
							getCurrentTime() + CLOSED_WITH_LOSE + lastPrice + WITH_LOSS + netLossVal);// +
																										// additionalMsg);

					DatabaseHelper.getInstance().updateTrade(bean);
					HighLowStrategyTool.getInstance().getHighLowMap1200().replace(symbol, bean);
				}
			} else if (SELL.equals(bean.getTradedState())) {
				lastPrice = bean.getBuyerAt();// lastPrice = narrow7Bean.getSellerAt();
				if (lastPrice < bean.getTradedVal().doubleValue()) {
					bean.setTradedState(SELL_EXIT_PROFIT + EXIT_FORCE);
					bean.setTradedOutAt(HighLowStrategyTool.getInstance().getCurrentDateTimeAsDate());
					double netProfitVal = new BigDecimal(
							(bean.getTradedVal().doubleValue() - lastPrice) * bean.getLotSize()).setScale(2, 0)
									.doubleValue();
					bean.setProfitLossAmt(netProfitVal);
					bean.setSignalTriggeredOutAt(
							getCurrentTime() + SELL_TARGET + lastPrice + WITH_PROFIT + netProfitVal);

					DatabaseHelper.getInstance().updateTrade(bean);
					HighLowStrategyTool.getInstance().getHighLowMap1200().replace(symbol, bean);
				} else if (lastPrice >= bean.getTradedVal().doubleValue()) {
					bean.setTradedState(SELL_EXIT_LOSS + EXIT_FORCE);
					bean.setTradedOutAt(HighLowStrategyTool.getInstance().getCurrentDateTimeAsDate());
					double netLossVal = new BigDecimal(
							(bean.getTradedVal().doubleValue() - lastPrice) * bean.getLotSize()).setScale(2, 0)
									.doubleValue();
					bean.setProfitLossAmt(netLossVal);
					bean.setSignalTriggeredOutAt(
							getCurrentTime() + CLOSED_WITH_LOSE + lastPrice + WITH_LOSS + netLossVal);

					DatabaseHelper.getInstance().updateTrade(bean);
					HighLowStrategyTool.getInstance().getHighLowMap1200().replace(symbol, bean);
				}
			}
		}
	}
	
	//May need to remove this call soon May 02 2020
	public StockDataBean get5MinutueCandleDataOnTradeEntryForVolumes(StockDataBean bean) {
		try {
			NkpAlgoLogger.logInfo(HighLowStrategyTool.class, "get5MinutueCandleDataOnTradeEntryForVolumes",
					bean.getKiteFutureKey() + getCurrentDateTime());

			toDateForKiteHistData = null;
			HistoricalData histData = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER)
					.getHistoricalData(/*getFromDateForKiteHistData_9_15()*/getFromDateForKiteHistDataOnCurrentDay_9_15(), 
							getToDateForKiteHistData_WithCurrentTime(),
							String.valueOf(bean.getInstrumentToken()), KITE_HIST_DATA_5_MINUTES_INTERVAL, false);

			List<HistoricalData> histDataList = histData.dataArrayList;
			if (null != histDataList && !histDataList.isEmpty()) {
				HistoricalData data = histDataList.get(histDataList.size() - 1);
				double open = data.open;
				double high = data.high;
				double low = data.low;
				//double close = data.close;
				long volume = data.volume;
				
				if (BUY.equals(bean.getTradableState())) {
					bean.setCurrentCandleOHLRuleInd(open == low);
				} else if (SELL.equals(bean.getTradableState())) {
					bean.setCurrentCandleOHLRuleInd(open == high);
				}
				bean.setCurrentCandleVolumesOnTradeEntry(volume);
			}
		} catch (JSONException | IOException | KiteException e) {
			NkpAlgoLogger.logError(HighLowStrategyTool.class, "get5MinutueCandleDataOnTradeEntryForVolumes",
					bean.getKiteFutureKey() + getCurrentDateTime());
			e.printStackTrace();
		}
		return bean;
	}
	
	public StockDataBean get5MinutueCandleDataForNifty50(StockDataBean bean) {
	try {
		NkpAlgoLogger.logInfo(HighLowStrategyTool.class, "get5MinutueCandleDataForNifty50",
				bean.getKiteFutureKey() + getCurrentDateTime());

		if (HighLowStrategyTool.canPlaceOrders) {
			//Need to update OHLC for nifty50
			HistoricalData histData = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER)
					.getHistoricalData(/*getFromDateForKiteHistData_9_15(),*/getFromDateForKiteHistDataOnCurrentDay_9_15(),
							getToDateForKiteHistData_WithCurrentTime(), String.valueOf(bean.getInstrumentToken()),
							KITE_HIST_DATA_5_MINUTES_INTERVAL, false);
			
			List<HistoricalData> histDataList = histData.dataArrayList;
			if (null != histDataList && histDataList.size() >= 4) {
				HistoricalData data = histDataList.get(histDataList.size() - 2);
				double high3 = data.high;
				double low3 = data.low;

				data = histDataList.get(histDataList.size() - 3);
				double high2 = data.high;
				double low2 = data.low;

				data = histDataList.get(histDataList.size() - 4);
				double high1 = data.high;
				double low1 = data.low;

				if (high3 > high2 && high2 > high1) {
					bean.setTradableState(BUY);
					bean.setBuyOrSellAt(high3);
					bean.setStopLoss(low3);
					bean.setOppositeHighLowRule(low3 > low2 && low2 > low1);
				} else if (low3 < low2 && low2 < low1) {
					bean.setTradableState(SELL);
					bean.setBuyOrSellAt(low3);
					bean.setStopLoss(high3);
					bean.setOppositeHighLowRule(high3 < high2 && high2 < high1);
				} else {
					bean.setTradableState(NA);
				}
				// current price or LTP
				data = histDataList.get(histDataList.size() - 1);
				bean.setLtp(data.close);
				
				//
				bean.setHistData(histData);
				bean = get5MinutueCandleDataForAdditionalInfo(bean, histData);
				//
				if (additinalInfo.length() > 0) {
					additinalInfo.delete(0, additinalInfo.length());
				}
					additinalInfo.append(getCurrentTime(getToDateForKiteHistData_WithCurrentTime())).append(COMMA_SPACE);
					additinalInfo.append(getCurrentTime(getToDateForKiteHistData_WithCurrentTime())).append(COMMA_SPACE);
					if (BUY.equals(bean.getTradableState())) {
						additinalInfo
						.append("<span style='color:blue'><b>TRADABLE_TREND-BUY, CNDLE_SIZE"+bean.getCandleMovement()+"</b></span>");
					} else if (SELL.equals(bean.getTradableState())) {
						additinalInfo
						.append("<span style='color:orange'><b>TRADABLE_TREND-SELL, CNDLE_SIZE"+bean.getCandleMovement()+"</b></span>");
					} else {
						additinalInfo
						.append("<span style='color:black'><b>TRADABLE_TREND-SELL, CNDLE_SIZE"+bean.getCandleMovement()+"</b></span>");
					}
					additinalInfo.append(COMMA_SPACE).append(STRENTH_TREND).append(bean.getStrenthTradableState());
					
					additinalInfo.append(COMMA_SPACE).append(VOLUME_TREND).append(bean.getVolumeTrend()).append(COMMA_SPACE)
							.append(CANDLE_MOVEMENT).append(bean.getCandleMovement()).append(COMMA_SPACE).append(LTP)
							.append(data.close).append(COMMA_SPACE).append(VWAP).append(bean.getVwapVal())
							.append(COMMA_SPACE).append(DAY1_MOVEMENT).append(bean.getDay1Movement())
							.append(COMMA_SPACE).append(DAY2_MOVEMENT).append(bean.getDay2Movement())
							.append(COMMA_SPACE).append(CPR_5).append(bean.getMinute5CPR()).append(COMMA_SPACE)
							.append(CPR_15).append(bean.getMinute5CPR()).append(TRADABLE_STRENTH)
							.append(bean.getStrengthFactor()).append(COMMA_SPACE).append(DAY1_STRENTH_FACTOR)
							.append(bean.getDay1VolStrengthFactor()).append(COMMA_SPACE).append(DAY2_STRENTH_FACTOR)
							.append(bean.getDay2VolStrengthFactor()).append(COMMA_SPACE).append(NR4_RULE)
							.append(bean.isNr4Rule()).append(COMMA_SPACE).append(SMALL_CANDLE)
							.append(bean.isSmallCandle()).append(COMMA).append(bean.getAdditinalInfo());
					bean.setAdditinalInfo(additinalInfo.toString());
					bean.setAdditionalInfoNifty50(additinalInfo.toString());
					
					if (additinalInfo.length() > 0) {
						additinalInfo.delete(0, additinalInfo.length());
					}
			} else {
				NkpAlgoLogger.printWithNewLine(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>NIFTY 50 java.lang.ArrayIndexOutOfBoundsException: -1s>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}
		}

	} catch (JSONException | IOException | KiteException | NullPointerException e) {
		NkpAlgoLogger.logError(HighLowStrategyTool.class, "get5MinutueCandleDataForNifty50",
				bean.getKiteFutureKey() + getCurrentDateTime());
		e.printStackTrace();
		bean.setAdditinalInfo(getCurrentTime(getToDateForKiteHistData_WithCurrentTime())+"_EXCEPRION");
		bean.setAdditionalInfoNifty50(getCurrentTime(getToDateForKiteHistData_WithCurrentTime())+"_EXCEPRION");
	}
	return bean;
}
/*public StockDataBean get5MinutueCandleDataOnTradeEntryForVolumesForNifty50(StockDataBean bean) {
	StockDataBean nifty50Bean = get5MinutueCandleDataOnTradeEntryForVolumes(getNifty50Bean());
	setNifty50Bean(nifty50Bean);
	bean.setVolumeInfoOnTradeEntryNifty50(nifty50Bean.getVolumeInfoOnTradeEntry());
	bean.setAdditionalInfoNifty50(nifty50Bean.getAdditinalInfo());
	bean.setGreen3CandleNifty50(nifty50Bean.isGreen3Candle());
	bean.setRed3CandleNifty50(nifty50Bean.isRed3Candle());
	bean.setVolumeTrendNifty50(nifty50Bean.getVolumeTrend());
	// Care about it
	bean.setVolumeTrend(bean.getVolumeTrend() + Constants.COMMA_SPACE + Constants.NIFTY_50 + ":"
			+ nifty50Bean.getVolumeTrend());
	return bean;
}*/
	
	private StringBuffer sb = new StringBuffer();
	public String tempVerify(boolean logInfoIntiDatabase) {
		sb.delete(0, sb.length());
		MemoryMXBean memBean = ManagementFactory.getMemoryMXBean() ;
        MemoryUsage heapMemoryUsage = memBean.getHeapMemoryUsage();
		try {
			/*if (logInfoIntiDatabase) {
				sb.append(getThreadScannerLastRunnngSatusTime());
				sb.append("TradingDataMapSize:" +getTradingDataMap().size()).append(Constants.COMMA_SPACE);
				sb.append("SymbolArraySize:" + kiteConnectTool.getSymbolSetForOHLCInput().size());
				for (String key : getTradingDataMap().keySet()) {
					sb.append(key).append(Constants.COMMA_SPACE);
				}
				TechTraderLogger.saveInfoLog(Constants.CLASS_SCHEDULERCONFIG, "tempVerify-getTradingDataMap",
						sb.toString());
				
				sb.delete(0, sb.length());
				sb.append(getThreadScannerLastRunnngSatusTime());
				sb.append("getTradingDataMap().size() - " +getTradingDataMap().size());
				sb.append("getSymbolArrayForOHLC().size() - " + kiteConnectTool.getSymbolSetForOHLCInput().size());
				for (String key : kiteConnectTool.getSymbolSetForOHLCInput()) {
					sb.append(key).append(Constants.COMMA_SPACE);
				}
				TechTraderLogger.saveInfoLog(Constants.CLASS_SCHEDULERCONFIG, "tempVerify-getSymbolArrayForOHLC",
						sb.toString());
				
				 //System.out.println(heapMemoryUsage.getMax()); // max memory allowed for jvm -Xmx flag (-1 if isn't specified)
			       // System.out.println(heapMemoryUsage.getCommitted()); // given memory to JVM by OS ( may fail to reach getMax, if there isn't more memory)
			       // System.out.println(heapMemoryUsage.getUsed()); // used now by your heap
			       // System.out.println(heapMemoryUsage.getInit()); // -Xms flag
			        
			        sb.delete(0, sb.length());
			        sb.append("getMax():").append(heapMemoryUsage.getMax() * MEMORY_IN_MB).append(Constants.COMMA_SPACE);
			        sb.append("getCommitted():").append(heapMemoryUsage.getCommitted() * MEMORY_IN_MB).append(Constants.COMMA_SPACE);
			        sb.append("getUsed():").append(heapMemoryUsage.getUsed() * MEMORY_IN_MB).append(Constants.COMMA_SPACE);
			        sb.append("getInit():").append(heapMemoryUsage.getInit() * MEMORY_IN_MB).append(Constants.COMMA_SPACE);
			        
			       // memoryMxBean.getHeapMemoryUsage().getUsed()      <=> runtime.totalMemory() - runtime.freeMemory()
			      //  		memoryMxBean.getHeapMemoryUsage().getCommitted() <=> runtime.totalMemory()
			      //  		memoryMxBean.getHeapMemoryUsage().getMax()       <=> runtime.maxMemory()
			        TechTraderLogger.saveInfoLog(Constants.CLASS_SCHEDULERCONFIG, "tempVerify-MemoryDetails",
							sb.toString());
			} else {
				sb.append(getThreadScannerLastRunnngSatusTime());
				sb.append(Constants.BREAK_LINE);
				sb.append("KITE_KEY:").append(StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).getAccessToken());
				sb.append(Constants.BREAK_LINE);
				sb.append("TradingDataMapSize:" +getTradingDataMap().size());
				sb.append(Constants.BREAK_LINE);
				sb.append("SymbolArraySize:" + kiteConnectTool.getSymbolSetForOHLCInput().size());
				sb.append(Constants.BREAK_LINE);
				
				sb.append("getMax():").append(heapMemoryUsage.getMax() * MEMORY_IN_MB).append(Constants.BREAK_LINE);
		        sb.append("getCommitted():").append(heapMemoryUsage.getCommitted() * MEMORY_IN_MB).append(Constants.BREAK_LINE);
		        sb.append("getUsed():").append(heapMemoryUsage.getUsed() * MEMORY_IN_MB).append(Constants.BREAK_LINE);
		        sb.append("getInit():").append(heapMemoryUsage.getInit() * MEMORY_IN_MB).append(Constants.BREAK_LINE);
			}*/
			
			sb.append(getThreadScannerLastRunnngSatusTime());
			sb.append(Constants.BREAK_LINE);
			
			
			sb.append("getMax():").append(heapMemoryUsage.getMax() * MEMORY_IN_MB).append(Constants.BREAK_LINE);
	        sb.append("getCommitted():").append(heapMemoryUsage.getCommitted() * MEMORY_IN_MB).append(Constants.BREAK_LINE);
	        sb.append("getUsed():").append(heapMemoryUsage.getUsed() * MEMORY_IN_MB).append(Constants.BREAK_LINE);
	        sb.append("getInit():").append(heapMemoryUsage.getInit() * MEMORY_IN_MB).append(Constants.BREAK_LINE);
		} catch (Exception e) {
			sb.append(e.getMessage());
			/*TechTraderLogger.saveFatalErrorLog(Constants.CLASS_SCHEDULERCONFIG, "tempVerify", e,
					Constants.ERROR_TYPE_EXCEPTION);*/
		}
		return sb.toString();
	}
}