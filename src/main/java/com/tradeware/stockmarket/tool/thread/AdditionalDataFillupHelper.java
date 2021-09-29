package com.tradeware.stockmarket.tool.thread;

import java.util.List;

import com.tradeware.stockmarket.bean.AverageHistDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.util.Constants;
import com.zerodhatech.models.HistoricalData;

public abstract class AdditionalDataFillupHelper extends TradingDataMapHistDataThread {

	protected void setPreviousCandleOhlcData(List<HistoricalData> histDataList, StrategyOrbDataBean bean) {
		if (null != histDataList && histDataList.size() >= 5) {
			HistoricalData data = histDataList.get(histDataList.size() - 2);
			bean.setHighCandle4(data.high);
			bean.setLowCandle4(data.low);
			bean.setCloseCandle4(data.close);
			bean.setOpenCandle4(data.open);
			
			data = histDataList.get(histDataList.size() - 3);
			bean.setHighCandle3(data.high);
			bean.setLowCandle3(data.low);
			bean.setCloseCandle3(data.close);
			bean.setOpenCandle3(data.open);

			data = histDataList.get(histDataList.size() - 4);
			bean.setHighCandle2(data.high);
			bean.setLowCandle2(data.low);
			bean.setCloseCandle2(data.close);
			bean.setOpenCandle2(data.open);

			data = histDataList.get(histDataList.size() - 5);
			bean.setHighCandle1(data.high);
			bean.setLowCandle1(data.low);
			bean.setCloseCandle1(data.close);
			bean.setOpenCandle1(data.open);
			
		} else if (null != histDataList && histDataList.size() >= 3) {
			HistoricalData data = histDataList.get(histDataList.size() - 2);
			bean.setHighCandle4(data.high);
			bean.setLowCandle4(data.low);
			bean.setCloseCandle4(data.close);
			bean.setOpenCandle4(data.open);

			data = histDataList.get(histDataList.size() - 3);
			bean.setHighCandle3(data.high);
			bean.setLowCandle3(data.low);
			bean.setCloseCandle3(data.close);
			bean.setOpenCandle3(data.open);
	}
}
	
	
	//Heikin-Ashi levels
		//private static boolean openBuy , openSell, closeBuy, closeSell, lowBuy, highSell;
		//private static StringBuffer sbha = new StringBuffer();
	protected void findHeikinAshiTrend(List<HistoricalData> histDataList, StrategyOrbDataBean bean) {
		try {
			/*if (null != histDataList && histDataList.size() >= 5 && !tradewareTool.isTimeBefore10_15AM()) {
				openBuy = false;
				openSell = false;
				closeBuy = false;
				closeSell = false;
				lowBuy = false;
				highSell = false;
				HistoricalData data = histDataList.get(histDataList.size() - 2);
				double open3 = data.open;
				double high3 = data.high;
				double low3 = data.low;
				double close3 = data.close;
				// long volume3 = data.volume;

				data = histDataList.get(histDataList.size() - 3);
				double open2 = data.open;
				double high2 = data.high;
				double low2 = data.low;
				double close2 = data.close;
				// long volume2 = data.volume;

				data = histDataList.get(histDataList.size() - 4);
				double open1 = data.open;
				double high1 = data.high;
				double low1 = data.low;
				double close1 = data.close;
				// long volume1 = data.volume;

				data = histDataList.get(histDataList.size() - 5);
				double open0 = data.open;
				// double high0 = data.high;
				// double low0 = data.low;
				double close0 = data.close;
				// long volume0 = data.volume;

				double heikenAshiOpen3 = kiteConnectTool.getRoundupToTwoValue((open2 + close2) / 2);
				double heikenAshiClose3 = kiteConnectTool.getRoundupToTwoValue((open3 + high3 + low3 + close3) / 4);
				double heikenAshiHigh3 = Math.max(Math.max(heikenAshiOpen3, heikenAshiClose3), high3);
				double heikenAshiLow3 = Math.min(Math.min(heikenAshiOpen3, heikenAshiClose3), low3);

				double heikenAshiOpen2 = kiteConnectTool.getRoundupToTwoValue((open1 + close1) / 2);
				double heikenAshiClose2 = kiteConnectTool.getRoundupToTwoValue((open2 + high2 + low2 + close2) / 4);
				double heikenAshiHigh2 = Math.max(Math.max(heikenAshiOpen2, heikenAshiClose2), high2);
				double heikenAshiLow2 = Math.min(Math.min(heikenAshiOpen2, heikenAshiClose2), low2);

				double heikenAshiOpen1 = kiteConnectTool.getRoundupToTwoValue((open0 + close0) / 2);
				double heikenAshiClose1 = kiteConnectTool.getRoundupToTwoValue((open1 + high1 + low1 + close1) / 4);
				double heikenAshiHigh1 = Math.max(Math.max(heikenAshiOpen1, heikenAshiClose1), high1);
				double heikenAshiLow1 = Math.min(Math.min(heikenAshiOpen1, heikenAshiClose1), low1);

				if (heikenAshiHigh3 > heikenAshiHigh2 && heikenAshiHigh2 > heikenAshiHigh1) {
					bean.setHeikinAshiTrendId(BUY);
					if (heikenAshiOpen3 > heikenAshiOpen2 && heikenAshiOpen2 > heikenAshiOpen1) {
						openBuy = true;
					}
					if (heikenAshiClose3 > heikenAshiClose2 && heikenAshiClose2 > heikenAshiClose1) {
						closeBuy = true;
					}
					if (heikenAshiLow3 > heikenAshiLow2 && heikenAshiLow2 > heikenAshiLow1) {
						lowBuy = true;
					}
					if (lowBuy && closeBuy && openBuy) {
						bean.setHeikinAshiTrendId(VV_STRONG_BUY);
					} else if (lowBuy && closeBuy) {
						bean.setHeikinAshiTrendId(V_STRONG_BUY);
					} else if (closeBuy) {
						bean.setHeikinAshiTrendId(STRONG_BUY_CLOSE);
					} else if (openBuy) {
						bean.setHeikinAshiTrendId(STRONG_BUY_OPEN);
					}
				} else if (heikenAshiLow3 < heikenAshiLow2 && heikenAshiLow2 < heikenAshiLow1) {
					bean.setHeikinAshiTrendId(SELL);
					if (heikenAshiOpen3 < heikenAshiOpen2 && heikenAshiOpen2 < heikenAshiOpen1) {
						openSell = true;
					}
					if (heikenAshiClose3 < heikenAshiClose2 && heikenAshiClose2 < heikenAshiClose1) {
						closeSell = true;
					}
					if (heikenAshiHigh3 < heikenAshiHigh2 && heikenAshiHigh2 < heikenAshiHigh1) {
						highSell = true;
					}
					if (highSell && closeSell && openSell) {
						bean.setHeikinAshiTrendId(VV_STRONG_SELL);
					} else if (highSell && closeSell) {
						bean.setHeikinAshiTrendId(V_STRONG_SELL);
					} else if (closeSell) {
						bean.setHeikinAshiTrendId(STRONG_SELL_CLOSE);
					} else if (openSell) {
						bean.setHeikinAshiTrendId(STRONG_SELL_OPEN);
					}
				} else {
					bean.setHeikinAshiTrendId(NA);
				}
			}*/
			
			/*sbha.delete(0, sbha.length());
			if ((heikenAshiHigh2 > heikenAshiHigh3) && (heikenAshiHigh1 > heikenAshiHigh2)
					&& (heikenAshiLow2 < heikenAshiLow3) && (heikenAshiLow1 < heikenAshiLow2)) {
				sbha.append(COMMA_SPACE).append(VV_STRONG_SELL);
			} else if ((heikenAshiHigh2 > heikenAshiHigh3) && (heikenAshiLow2 < heikenAshiLow3)) {
				sbha.append(COMMA_SPACE).append(V_STRONG_SELL);
			} else {
				sbha.append(COMMA_SPACE).append(NA);
			}
			sbha.append(COMMA_SPACE).append(String.valueOf(heikenAshiHigh3)).append(COMMA).append(String.valueOf(heikenAshiLow3)).append(COMMA_SPACE).
			append((heikenAshiClose1 > heikenAshiOpen1) ? GREEN_CANDLE : RED_CANDLE).append(UNDER_SCORE).
			append((heikenAshiClose2 > heikenAshiOpen2) ? GREEN_CANDLE : RED_CANDLE).append(UNDER_SCORE).
			append((heikenAshiClose3 > heikenAshiOpen3) ? GREEN_CANDLE : RED_CANDLE).append(COMMA_SPACE);
			
			if (heikenAshiOpen1 == heikenAshiLow1) {
				sbha.append(OPEN_EQ_LOW).append(HYPHEN);
			}
			else if (heikenAshiOpen1 == heikenAshiHigh1) {
				sbha.append(OPEN_EQ_HIGH).append(HYPHEN);
			} else {
				sbha.append(NA).append(HYPHEN);
			}
			if (heikenAshiOpen2 == heikenAshiLow2) {
				sbha.append(OPEN_EQ_LOW).append(HYPHEN);
			}
			else if (heikenAshiOpen2 == heikenAshiHigh2) {
				sbha.append(OPEN_EQ_HIGH).append(HYPHEN);
			} else {
				sbha.append(NA).append(HYPHEN);
			}
			if (heikenAshiOpen3 == heikenAshiLow3) {
				sbha.append(OPEN_EQ_LOW);
			}
			else if (heikenAshiOpen3 == heikenAshiHigh3) {
				sbha.append(OPEN_EQ_HIGH);
			} else {
				sbha.append(NA);
			}*/
			
			//05-29-2021 start  - afterSomeSuccess
			if (null != histDataList && histDataList.size() >= 5 && !tradewareTool.isTimeBefore10_15AM()) {
				HistoricalData data = histDataList.get(histDataList.size() - 2);
				double open3 = data.open;
				double high3 = data.high;
				double low3 = data.low;
				double close3 = data.close;
				// long volume3 = data.volume;

				data = histDataList.get(histDataList.size() - 3);
				double open2 = data.open;
				double high2 = data.high;
				double low2 = data.low;
				double close2 = data.close;
				// long volume2 = data.volume;

				data = histDataList.get(histDataList.size() - 4);
				double open1 = data.open;
				double high1 = data.high;
				double low1 = data.low;
				double close1 = data.close;
				// long volume1 = data.volume;

				data = histDataList.get(histDataList.size() - 5);
				double open0 = data.open;
				// double high0 = data.high;
				// double low0 = data.low;
				double close0 = data.close;
				// long volume0 = data.volume;

				double heikenAshiOpen3 = kiteConnectTool.getRoundupToTwoValue((open2 + close2) / 2);
				double heikenAshiClose3 = kiteConnectTool.getRoundupToTwoValue((open3 + high3 + low3 + close3) / 4);
				double heikenAshiHigh3 = Math.max(Math.max(heikenAshiOpen3, heikenAshiClose3), high3);
				double heikenAshiLow3 = Math.min(Math.min(heikenAshiOpen3, heikenAshiClose3), low3);

				double heikenAshiOpen2 = kiteConnectTool.getRoundupToTwoValue((open1 + close1) / 2);
				double heikenAshiClose2 = kiteConnectTool.getRoundupToTwoValue((open2 + high2 + low2 + close2) / 4);
				double heikenAshiHigh2 = Math.max(Math.max(heikenAshiOpen2, heikenAshiClose2), high2);
				double heikenAshiLow2 = Math.min(Math.min(heikenAshiOpen2, heikenAshiClose2), low2);

				double heikenAshiOpen1 = kiteConnectTool.getRoundupToTwoValue((open0 + close0) / 2);
				double heikenAshiClose1 = kiteConnectTool.getRoundupToTwoValue((open1 + high1 + low1 + close1) / 4);
				double heikenAshiHigh1 = Math.max(Math.max(heikenAshiOpen1, heikenAshiClose1), high1);
				double heikenAshiLow1 = Math.min(Math.min(heikenAshiOpen1, heikenAshiClose1), low1);

				if (heikenAshiClose3 > heikenAshiOpen3 && heikenAshiClose2 > heikenAshiOpen2
						&& heikenAshiClose1 > heikenAshiOpen1) {
					bean.setHeikinAshiTrendId(BUY);
					if (heikenAshiOpen3 == heikenAshiLow3 && heikenAshiOpen2 == heikenAshiLow2
							&& heikenAshiOpen1 == heikenAshiLow1) {
						bean.setHeikinAshiTrendId(V_STRONG_BUY);
						if (((heikenAshiHigh3 - heikenAshiClose3) < (heikenAshiClose3 - heikenAshiOpen3))
								&& ((heikenAshiHigh2 - heikenAshiClose2) < (heikenAshiClose2 - heikenAshiOpen2))
								&& ((heikenAshiHigh1 - heikenAshiClose1) < (heikenAshiClose1 - heikenAshiOpen1))) {
							bean.setHeikinAshiTrendId(VV_STRONG_BUY);
						}
					}

				} else if (heikenAshiClose3 < heikenAshiOpen3 && heikenAshiClose2 < heikenAshiOpen2 && heikenAshiClose1 < heikenAshiOpen1) {
					bean.setHeikinAshiTrendId(SELL);
					if (heikenAshiOpen3 == heikenAshiHigh3 &&  heikenAshiOpen2 == heikenAshiHigh2 && heikenAshiOpen1 == heikenAshiHigh1 ) {
						bean.setHeikinAshiTrendId(V_STRONG_SELL);
						if (((heikenAshiHigh3 - heikenAshiClose3) > (heikenAshiClose3 - heikenAshiLow3))
								&& ((heikenAshiHigh2 - heikenAshiClose2) > (heikenAshiClose2 - heikenAshiLow2))
								&& ((heikenAshiHigh1 - heikenAshiClose1) > (heikenAshiClose1 - heikenAshiLow1))) {
							bean.setHeikinAshiTrendId(VV_STRONG_SELL);
						}
					}
				} else {
					bean.setHeikinAshiTrendId(NA);
				}
			}
			//05-29-2021 end  - afterSomeSuccess
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(CLASS_ADDITIONAL_DATA_FILLUP_HELPER, METHOD_FIND_HEIKIN_ASHI_TREND, e,
					ERROR_TYPE_EXCEPTION);
		}

	}
	
	//Duplicate method
	private String heikinAshiTrendId = NA;
	private void clearHeikinAshiTrendId() {
		heikinAshiTrendId = NA;
	}
	
	protected void findHeikinAshiTrend(List<HistoricalData> histDataList, AverageHistDataBean bean, String candleFrame) {
		try {
			clearHeikinAshiTrendId();
			// 05-29-2021 start - afterSomeSuccess
			if (null != histDataList && histDataList.size() >= 5 /*&& !tradewareTool.isTimeBefore10_15AM()*/) {
				HistoricalData data = histDataList.get(histDataList.size() - 2);
				double open3 = data.open;
				double high3 = data.high;
				double low3 = data.low;
				double close3 = data.close;
				// long volume3 = data.volume;

				data = histDataList.get(histDataList.size() - 3);
				double open2 = data.open;
				double high2 = data.high;
				double low2 = data.low;
				double close2 = data.close;
				// long volume2 = data.volume;

				data = histDataList.get(histDataList.size() - 4);
				double open1 = data.open;
				double high1 = data.high;
				double low1 = data.low;
				double close1 = data.close;
				// long volume1 = data.volume;

				data = histDataList.get(histDataList.size() - 5);
				double open0 = data.open;
				// double high0 = data.high;
				// double low0 = data.low;
				double close0 = data.close;
				// long volume0 = data.volume;

				double heikenAshiOpen3 = kiteConnectTool.getRoundupToTwoValue((open2 + close2) / 2);
				double heikenAshiClose3 = kiteConnectTool.getRoundupToTwoValue((open3 + high3 + low3 + close3) / 4);
				double heikenAshiHigh3 = Math.max(Math.max(heikenAshiOpen3, heikenAshiClose3), high3);
				double heikenAshiLow3 = Math.min(Math.min(heikenAshiOpen3, heikenAshiClose3), low3);

				double heikenAshiOpen2 = kiteConnectTool.getRoundupToTwoValue((open1 + close1) / 2);
				double heikenAshiClose2 = kiteConnectTool.getRoundupToTwoValue((open2 + high2 + low2 + close2) / 4);
				double heikenAshiHigh2 = Math.max(Math.max(heikenAshiOpen2, heikenAshiClose2), high2);
				double heikenAshiLow2 = Math.min(Math.min(heikenAshiOpen2, heikenAshiClose2), low2);

				double heikenAshiOpen1 = kiteConnectTool.getRoundupToTwoValue((open0 + close0) / 2);
				double heikenAshiClose1 = kiteConnectTool.getRoundupToTwoValue((open1 + high1 + low1 + close1) / 4);
				double heikenAshiHigh1 = Math.max(Math.max(heikenAshiOpen1, heikenAshiClose1), high1);
				double heikenAshiLow1 = Math.min(Math.min(heikenAshiOpen1, heikenAshiClose1), low1);

				if (heikenAshiClose3 > heikenAshiOpen3 && heikenAshiClose2 > heikenAshiOpen2
						&& heikenAshiClose1 > heikenAshiOpen1) {
					heikinAshiTrendId = BUY;
					if (heikenAshiOpen3 == heikenAshiLow3 && heikenAshiOpen2 == heikenAshiLow2
							&& heikenAshiOpen1 == heikenAshiLow1) {
						if (heikenAshiClose3 > heikenAshiClose2 && heikenAshiClose2 > heikenAshiClose1) {
							heikinAshiTrendId = V_STRONG_BUY;
							if (/*(((heikenAshiHigh3 - heikenAshiClose3) < (heikenAshiClose3 - heikenAshiOpen3))
									&& ((heikenAshiHigh2 - heikenAshiClose2) < (heikenAshiClose2 - heikenAshiOpen2))
									&& ((heikenAshiHigh1 - heikenAshiClose1) < (heikenAshiClose1 - heikenAshiOpen1)))
									||*/ (((heikenAshiClose3 - heikenAshiOpen3) < (heikenAshiClose2 - heikenAshiOpen2))
											&& ((heikenAshiClose1 - heikenAshiOpen1) < (heikenAshiClose1
													- heikenAshiOpen1)))) {
								heikinAshiTrendId = VV_STRONG_BUY;
							}
						}
					}

				} else if (heikenAshiClose3 < heikenAshiOpen3 && heikenAshiClose2 < heikenAshiOpen2
						&& heikenAshiClose1 < heikenAshiOpen1) {
					heikinAshiTrendId = SELL;
					if (heikenAshiOpen3 == heikenAshiHigh3 && heikenAshiOpen2 == heikenAshiHigh2
							&& heikenAshiOpen1 == heikenAshiHigh1) {
						if (heikenAshiClose3 < heikenAshiClose2 && heikenAshiClose2 < heikenAshiClose1) {
							heikinAshiTrendId = V_STRONG_SELL;
							if (/*(((heikenAshiHigh3 - heikenAshiClose3) > (heikenAshiClose3 - heikenAshiLow3))
									&& ((heikenAshiHigh2 - heikenAshiClose2) > (heikenAshiClose2 - heikenAshiLow2))
									&& ((heikenAshiHigh1 - heikenAshiClose1) > (heikenAshiClose1 - heikenAshiLow1)))
									||*/ (((heikenAshiOpen3 - heikenAshiClose3) > (heikenAshiOpen2 - heikenAshiClose2))
											&& ((heikenAshiOpen2 - heikenAshiClose2) > (heikenAshiOpen1
													- heikenAshiClose1)))) {
								heikinAshiTrendId = VV_STRONG_SELL;
							}
						}
					}
				} else {
					heikinAshiTrendId = NA;
				}
				
				if (KITE_HIST_DATA_60_MINUTES_INTERVAL.equals(candleFrame)) {
					bean.setHeikinAshiTrendIdMin60(heikinAshiTrendId);
					bean.setHeikenAshiOpen3Min60(heikenAshiOpen3);
					bean.setHeikenAshiClose3Min60(heikenAshiClose3);
					bean.setHeikenAshiHigh3Min60(heikenAshiHigh3);
					bean.setHeikenAshiLow3Min60(heikenAshiLow3);
					bean.setHeikenAshiOpen2Min60(heikenAshiOpen2);
					bean.setHeikenAshiClose2Min60(heikenAshiClose2);
					bean.setHeikenAshiHigh2Min60(heikenAshiHigh2 );
					bean.setHeikenAshiLow2Min60(heikenAshiLow2);
					bean.setHeikenAshiOpen1Min60(heikenAshiOpen1);
					bean.setHeikenAshiClose1Min60(heikenAshiClose1);
					bean.setHeikenAshiHigh1Min60(heikenAshiHigh1);
					bean.setHeikenAshiLow1Min60(heikenAshiLow1);
				} else if (KITE_HIST_DATA_15_MINUTES_INTERVAL.equals(candleFrame)) {
					bean.setHeikinAshiTrendIdMin15(heikinAshiTrendId);
					bean.setHeikenAshiOpen3Min15(heikenAshiOpen3);
					bean.setHeikenAshiClose3Min15(heikenAshiClose3);
					bean.setHeikenAshiHigh3Min15(heikenAshiHigh3);
					bean.setHeikenAshiLow3Min15(heikenAshiLow3);
					bean.setHeikenAshiOpen2Min15(heikenAshiOpen2);
					bean.setHeikenAshiClose2Min15(heikenAshiClose2);
					bean.setHeikenAshiHigh2Min15(heikenAshiHigh2 );
					bean.setHeikenAshiLow2Min15(heikenAshiLow2);
					bean.setHeikenAshiOpen1Min15(heikenAshiOpen1);
					bean.setHeikenAshiClose1Min15(heikenAshiClose1);
					bean.setHeikenAshiHigh1Min15(heikenAshiHigh1);
					bean.setHeikenAshiLow1Min15(heikenAshiLow1);
				} else if (KITE_HIST_DATA_5_MINUTES_INTERVAL.equals(candleFrame)) {
					bean.setHeikinAshiTrendIdMin5(heikinAshiTrendId);
					bean.setHeikenAshiOpen3Min5(heikenAshiOpen3);
					bean.setHeikenAshiClose3Min5(heikenAshiClose3);
					bean.setHeikenAshiHigh3Min5(heikenAshiHigh3);
					bean.setHeikenAshiLow3Min5(heikenAshiLow3);
					bean.setHeikenAshiOpen2Min5(heikenAshiOpen2);
					bean.setHeikenAshiClose2Min5(heikenAshiClose2);
					bean.setHeikenAshiHigh2Min5(heikenAshiHigh2 );
					bean.setHeikenAshiLow2Min5(heikenAshiLow2);
					bean.setHeikenAshiOpen1Min5(heikenAshiOpen1);
					bean.setHeikenAshiClose1Min5(heikenAshiClose1);
					bean.setHeikenAshiHigh1Min5(heikenAshiHigh1);
					bean.setHeikenAshiLow1Min5(heikenAshiLow1);
				}
			}
			// 05-29-2021 end - afterSomeSuccess
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(CLASS_ADDITIONAL_DATA_FILLUP_HELPER, METHOD_FIND_HEIKIN_ASHI_TREND, e,
					ERROR_TYPE_EXCEPTION);
		}
	}
	
	private double previousClose;
	private double lowForK;
	private double highForK;
	
	private double valueK1;
	private double valueD3;
	private double valueK2;
	private double valueD4;
	private double valueK3;
	private double valueD5;
	private double valueK4;
	private double valueK5;
	private String stochasticTrend; 
	
	private double valueK1Temp;
	private double valueK2Temp;
	private double valueK3Temp;
	
	private void clear() {
		previousClose = 0;
		lowForK = 0;
		highForK = 0;
	}
	private void clearValues() {
		valueK1 = 0;
		valueD3 = 0;
		valueK2 = 0;
		valueD4 = 0;
		valueK3 = 0;
		valueD5 = 0;
		valueK4 = 0;
		valueK5 = 0;
		valueK1Temp = 0;
		valueK2Temp = 0;
		valueK3Temp = 0;
		stochasticTrend = Constants.NA;
	}
	private double caluclateStochasticK(double prvClose, double topHigh, double topLow) {
		return ((prvClose - topLow) / (topHigh - (topLow != 0 ? topLow : 1))) * 100;
	}

	/*private double caluclateStochasticD(double topHigh, double topLow) {
		return (topHigh / (topLow != 0 ? topLow : 1)* 100);
	}*/

	protected void findStochasticTrend(List<HistoricalData> histDataList, AverageHistDataBean bean, String candleFrame) {
		clear();
		clearValues();
		try {
			if (null != histDataList && histDataList.size() >= 9) {
				HistoricalData data = histDataList.get(histDataList.size() - 1);
				double high1 = data.high;
				double low1 = data.low;
				double close1 = data.close;

				data = histDataList.get(histDataList.size() - 2);
				double high2 = data.high;
				double low2 = data.low;
				double close2 = data.close;

				data = histDataList.get(histDataList.size() - 3);
				double high3 = data.high;
				double low3 = data.low;
				double close3 = data.close;

				data = histDataList.get(histDataList.size() - 4);
				double high4 = data.high;
				double low4 = data.low;
				double close4 = data.close;

				data = histDataList.get(histDataList.size() - 5);
				double high5 = data.high;
				double low5 = data.low;
				double close5 = data.close;

				data = histDataList.get(histDataList.size() - 6);
				double high6 = data.high;
				double low6 = data.low;

				data = histDataList.get(histDataList.size() - 7);
				double high7 = data.high;
				double low7 = data.low;

				data = histDataList.get(histDataList.size() - 8);
				double high8 = data.high;
				double low8 = data.low;

				data = histDataList.get(histDataList.size() - 9);
				double high9 = data.high;
				double low9 = data.low;
				
				previousClose = close1;
				lowForK = Math.min(Math.min(Math.min(Math.min(low1, low2), low3), low4), low5);
				highForK = Math.max(Math.max(Math.max(Math.max(high1, high2), high3), high4), high5);
				valueK1 = caluclateStochasticK(previousClose, highForK, lowForK);

				clear();
				previousClose = close2;
				lowForK = Math.min(Math.min(Math.min(Math.min(low2, low3), low4), low5), low6);
				highForK = Math.max(Math.max(Math.max(Math.max(high2, high3), high4), high5), high6);
				valueK2 = caluclateStochasticK(previousClose, highForK, lowForK);

				clear();
				previousClose = close3;
				lowForK = Math.min(Math.min(Math.min(Math.min(low3, low4), low5), low6), low7);
				highForK = Math.max(Math.max(Math.max(Math.max(high3, high4), high5), high6), high7);
				valueK3 = caluclateStochasticK(previousClose, highForK, lowForK);

				clear();
				previousClose = close4;
				lowForK = Math.min(Math.min(Math.min(Math.min(low4, low5), low6), low7), low8);
				highForK = Math.max(Math.max(Math.max(Math.max(high4, high5), high6), high7), high8);
				valueK4 = caluclateStochasticK(previousClose, highForK, lowForK);

				previousClose = close5;
				lowForK = Math.min(Math.min(Math.min(Math.min(low5, low6), low7), low8), low9);
				highForK = Math.max(Math.max(Math.max(Math.max(high5, high6), high7), high8), high9);
				valueK5 = caluclateStochasticK(previousClose, highForK, lowForK);

				valueK1Temp = kiteConnectTool.getRoundupToTwoValue(((valueK1 + valueK2 + valueK3) / 3));
				valueK2Temp = kiteConnectTool.getRoundupToTwoValue(((valueK2 + valueK3 + valueK4) / 3));
				valueK3Temp = kiteConnectTool.getRoundupToTwoValue(((valueK3 + valueK4 + valueK5) / 3));
				
				valueK1 = valueK1Temp;
				valueK2 = valueK2Temp;
				valueK3 = valueK3Temp;
				
				valueD3 = kiteConnectTool.getRoundupToTwoValue(((valueK1 + valueK2 + valueK3) / 3));
				valueD4  = kiteConnectTool.getRoundupToTwoValue(((valueK2 + valueK3 + valueK4) / 3));
				valueD5 = kiteConnectTool.getRoundupToTwoValue(((valueK3 + valueK4 + valueK5) / 3));
				/*
				 * if (valueAvgK > valueAvgD && valueAvgK < 40) { stochasticTrend = BUY; } else
				 * if (valueAvgK < valueAvgD && valueAvgK > 60) { stochasticTrend = SELL; }
				 */

				if (valueK1 > valueD3 && valueK1 <= 25 && (valueK3 < valueK2 && valueK2 < valueK1)) {
					stochasticTrend = "VS_BUY";
				} else if (valueK1 < valueD3 && valueD3 > 75 && (valueK3 > valueK2 && valueK2 > valueK1)) {
					stochasticTrend = "VS_SELL";
				}

				else if (valueK1 > valueD3 && valueK1 < 25) {
					stochasticTrend = "BUY";
				} else if (valueK1 < valueD3 && valueD3 > 75) {
					stochasticTrend = "SELL";
				}
				if (KITE_HIST_DATA_60_MINUTES_INTERVAL.equals(candleFrame)) {
					bean.setMin60StochasticValK1(kiteConnectTool.getRoundupToTwoValue(valueK1));
					bean.setMin60StochasticValD3(kiteConnectTool.getRoundupToTwoValue(valueD3));
					bean.setMin60StochasticValK2(kiteConnectTool.getRoundupToTwoValue(valueK2));
					bean.setMin60StochasticValD4(kiteConnectTool.getRoundupToTwoValue(valueD4));
					bean.setMin60StochasticValK3(kiteConnectTool.getRoundupToTwoValue(valueK3));
					bean.setMin60StochasticValD5(kiteConnectTool.getRoundupToTwoValue(valueD5));
					bean.setMin60StochasticTrend(stochasticTrend);
				} else if (KITE_HIST_DATA_15_MINUTES_INTERVAL.equals(candleFrame)) {
					bean.setMin15StochasticValK1(kiteConnectTool.getRoundupToTwoValue(valueK1));
					bean.setMin15StochasticValD3(kiteConnectTool.getRoundupToTwoValue(valueD3));
					bean.setMin15StochasticValK2(kiteConnectTool.getRoundupToTwoValue(valueK2));
					bean.setMin15StochasticValD4(kiteConnectTool.getRoundupToTwoValue(valueD4));
					bean.setMin15StochasticValK3(kiteConnectTool.getRoundupToTwoValue(valueK3));
					bean.setMin15StochasticValD5(kiteConnectTool.getRoundupToTwoValue(valueD5));
					bean.setMin15StochasticTrend(stochasticTrend);
				} else if (KITE_HIST_DATA_5_MINUTES_INTERVAL.equals(candleFrame)) {
					bean.setMin5StochasticValK1(kiteConnectTool.getRoundupToTwoValue(valueK1));
					bean.setMin5StochasticValD3(kiteConnectTool.getRoundupToTwoValue(valueD3));
					bean.setMin5StochasticValK2(kiteConnectTool.getRoundupToTwoValue(valueK2));
					bean.setMin5StochasticValD4(kiteConnectTool.getRoundupToTwoValue(valueD4));
					bean.setMin5StochasticValK3(kiteConnectTool.getRoundupToTwoValue(valueK3));
					bean.setMin5StochasticValD5(kiteConnectTool.getRoundupToTwoValue(valueD5));
					bean.setMin5StochasticTrend(stochasticTrend);
				}
				
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(CLASS_ADDITIONAL_DATA_FILLUP_HELPER, METHOD_FIND_STOCHASTIC_TREND, e,
					ERROR_TYPE_EXCEPTION);
		}

	}
	
	public void caluclateRSI(List<HistoricalData> histDataList, AverageHistDataBean bean, String candleFrame) {
		try {
			if (null != histDataList && histDataList.size() >= 20) {
				double change = 0d, uptrend = 0d, downtrend = 0d, avgUptrend = 0d, avgDowntrend = 0d, rsi = 0d;
				for (int i = histDataList.size() - 19; i > 1; i--) {
					double close_1 = histDataList.get(i).close;
					double close = histDataList.get(i - 1).close;
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
							bean.setRsi1(kiteConnectTool.getRoundupToTwoValue(rsi));
						} else if (i == 3) {
							bean.setRsi2(kiteConnectTool.getRoundupToTwoValue(rsi));
						} else if (i == 2) {
							bean.setRsi3(kiteConnectTool.getRoundupToTwoValue(rsi));
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(CLASS_ADDITIONAL_DATA_FILLUP_HELPER, METHOD_FIND_STOCHASTIC_TREND, e,
					ERROR_TYPE_EXCEPTION);
		}
	}
	
	private void caluclateRSIForCurrentDay(List<HistoricalData> histDataList, AverageHistDataBean bean,
			String candleFrame) {
		try {
			if (null != histDataList && histDataList.size() >= 20) {
				double change = 0d, uptrend = 0d, downtrend = 0d, avgUptrend = 0d, avgDowntrend = 0d, rsi = 0d;
				for (int i = histDataList.size() - 1; /* i > 1 */i >= 0; i--) {
					double close_1 = histDataList.get(i).close;
					double close = histDataList.get(i - 1).close;
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
							// bean.setDayRsi(kiteConnectTool.getRoundupToTwoValue(rsi));
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(CLASS_ADDITIONAL_DATA_FILLUP_HELPER, METHOD_FIND_STOCHASTIC_TREND, e,
					ERROR_TYPE_EXCEPTION);
		}
	}
}
