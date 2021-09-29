package com.tradeware.stockmarket.tool;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;

import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.bean.SymbolDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.StockUtil;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.HistoricalData;
import com.zerodhatech.models.LTPQuote;
import com.zerodhatech.models.OHLCQuote;
import com.zerodhatech.models.Quote;

public class AbstractKiteConnectTool implements Constants {

	@Autowired
	protected TradewareTool tradewareTool;

	private Set<String> symbolSetForOHLCInput = new HashSet<String>();

	public Set<String> getSymbolSetForOHLCInput() {
		return symbolSetForOHLCInput;
	}

	public void setSymbolSetForOHLCInput(Set<String> symbolSetForOHLCInput) {
		this.symbolSetForOHLCInput = symbolSetForOHLCInput;
	}

	public double getRoundupToOneValue(double value) {
		return new BigDecimal(value).setScale(ONE, RoundingMode.DOWN).doubleValue();
	}

	public double getRoundupToTwoValue(double value) {
		double roundupVal = 0d;
		try {
			roundupVal = new BigDecimal(value).setScale(TWO, RoundingMode.DOWN).doubleValue();
		} catch (java.lang.NumberFormatException e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL,
					Constants.METHOD_GET_ROUND_UP_TO_TWO_VALUE, e,
					Constants.ERROR_TYPE_NUMBER_FORMAT_EXCEPTION);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL,
					Constants.METHOD_GET_ROUND_UP_TO_TWO_VALUE, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return roundupVal;
	}

	public double getRoundupToTwoValueUp(double value) {
		double roundupVal = 0d;
		try {
			roundupVal = new BigDecimal(value).setScale(TWO, RoundingMode.UP).doubleValue();
		} catch (java.lang.NumberFormatException e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL,
					Constants.METHOD_GET_ROUND_UP_TO_TWO_VALUE_UP, e,
					Constants.ERROR_TYPE_NUMBER_FORMAT_EXCEPTION);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL,
					Constants.METHOD_GET_ROUND_UP_TO_TWO_VALUE_UP, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return roundupVal;
	}
	// Phase 4 strat
	public void updateKiteFutureKeyAndInstrumentTokenWithOutKite(List<SymbolDataBean> symbolDataBeans,
			ConcurrentHashMap<String, StrategyOrbDataBean> baseDataMap) {
		try {
			TradewareLogger.saveInfoLog(Constants.CLASS_KITE_CONNECT_TOOL,
					Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN, Constants.CALL_ENTRY);
			if (null != symbolDataBeans && !symbolDataBeans.isEmpty()) {
				Map<String, Integer> symbolMap = new HashMap<String, Integer>();
				for (SymbolDataBean dataBean : symbolDataBeans) {
					if (null != dataBean.getEquityInd() && dataBean.getEquityInd()) {
						symbolMap.put((FUTURE_KEY_PREFIX_NSE + dataBean.getSymbolId()), dataBean.getLotSize());
					}
					if (null != dataBean.getFnoInd() && dataBean.getFnoInd()) {
						symbolMap.put(tradewareTool.getKiteFutureKey(dataBean.getSymbolId()), dataBean.getLotSize());
					}
				}

				for (String key : symbolMap.keySet()) {
					StrategyOrbDataBean bean = null;
					if (key.startsWith(Constants.FUTURE_KEY_PREFIX_NSE)) {
						bean = new StrategyOrbDataBean(symbolMap.get(key), tradewareTool.getSymbolName(key));
						bean.setSymbolName(tradewareTool.getSymbolName(key));
					} else {
						bean = new StrategyOrbDataBean(symbolMap.get(key), tradewareTool.getSymbolName(key));
						bean.setSymbolName(tradewareTool.getSymbolName(key));
					}

					bean.setCandleTimeFrame(KITE_HIST_DATA_15_MINUTES_INTERVAL);
					bean.setKiteFutureKey(key);
					// 04/06/2021 Start
					if (bean.getLotSize() >= 100
							&& ((key.startsWith(Constants.FUTURE_KEY_PREFIX_NSE) && bean.getLotSize() < 4000)
									|| (key.startsWith(Constants.FUTURE_KEY_PREFIX_NFO) && bean.getLotSize() < 7000))) {
						baseDataMap.put(key, bean);
					} else {
						System.out.println("Ignored ... " + key + " - " + bean.getLotSize());
					}
					// 04/06/2021 End
				}
			} else {
				TradewareLogger.sysoutPrintlnForLocalTest(
						Constants.CLASS_KITE_CONNECT_TOOL + Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN
								+ Constants.ERROR_ON_UPDATE_OHLC);
				TradewareLogger.saveInfoLog(Constants.CLASS_KITE_CONNECT_TOOL,
						Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN, Constants.ERROR_ON_UPDATE_OHLC);
			}
			TradewareLogger.saveInfoLog(Constants.CLASS_KITE_CONNECT_TOOL,
					Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN, Constants.CALL_EXIT);
			TradewareLogger.sysoutPrintlnForLocalTest(Constants.CLASS_KITE_CONNECT_TOOL
					+ Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN + Constants.CALL_EXIT);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL,
					Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN, e,
					Constants.ERROR_TYPE_KITE_EXCEPTION);
			TradewareLogger.sysoutPrintlnForLocalTest(
					Constants.CLASS_KITE_CONNECT_TOOL + Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN
							+ Constants.ERROR_TYPE_KITE_EXCEPTION);
		}
	}
	
	public ConcurrentHashMap<String, StrategyOrbDataBean> addIndexesToTrade2WithOutKite() {
		ConcurrentHashMap<String, StrategyOrbDataBean> baseDataMapIndex = new ConcurrentHashMap<String, StrategyOrbDataBean>();
		try { 
			
				StrategyOrbDataBean bean2 = new StrategyOrbDataBean(75, NIFTY_50);
				bean2.setSymbolName(NIFTY_50_FUT);
				bean2.setKiteFutureKey(tradewareTool.getKiteFutureKey(NIFTY));
				baseDataMapIndex.put(bean2.getKiteFutureKey(), bean2);

				StrategyOrbDataBean bean4 = new StrategyOrbDataBean(25, BANKNIFTY);
				bean4.setSymbolName(BANKNIFTY_FUT);
				bean4.setKiteFutureKey(tradewareTool.getKiteFutureKey(BANKNIFTY));
				baseDataMapIndex.put(bean4.getKiteFutureKey(), bean4);

				StrategyOrbDataBean bean10 = new StrategyOrbDataBean(40, FINNIFTY);
				bean10.setSymbolName(FINNIFTY_FUT);
				bean10.setKiteFutureKey(tradewareTool.getKiteFutureKey(FINNIFTY));
				baseDataMapIndex.put(bean10.getKiteFutureKey(), bean10);
				 
			/*for (String expDate : tradewareTool.getNextExpiryDatesList()) {
				StrategyOrbDataBean bean2 = new StrategyOrbDataBean(75, NIFTY_50+expDate);
				bean2.setSymbolName(NIFTY_50_FUT + SPACE + expDate);
				bean2.setKiteFutureKey(tradewareTool.getKiteFutureKey(NIFTY));
				bean2.setExpiryDate(expDate);
				baseDataMapIndex.put(bean2.getKiteFutureKey(), bean2);

				StrategyOrbDataBean bean4 = new StrategyOrbDataBean(25, BANKNIFTY+expDate);
				bean4.setSymbolName(BANKNIFTY_FUT + SPACE + expDate);
				bean4.setKiteFutureKey(tradewareTool.getKiteFutureKey(BANKNIFTY));
				bean4.setExpiryDate(expDate);
				baseDataMapIndex.put(bean4.getKiteFutureKey(), bean4);

				StrategyOrbDataBean bean10 = new StrategyOrbDataBean(40, FINNIFTY+expDate);
				bean10.setSymbolName(FINNIFTY_FUT + SPACE + expDate);
				bean10.setKiteFutureKey(tradewareTool.getKiteFutureKey(FINNIFTY));
				bean10.setExpiryDate(expDate);
				baseDataMapIndex.put(bean10.getKiteFutureKey(), bean10);

				
				 * StrategyOrbDataBean bean5 = new StrategyOrbDataBean(25, BANKNIFTY);
				 * bean5.setSymbolName(BANK_NIFTY); bean5.setKiteFutureKey(NSE_BANK_NIFTY);
				 * baseDataMapIndex.put(bean5.getKiteFutureKey(), bean5);
				 
			}*/

			for (String key : baseDataMapIndex.keySet()) {

					StrategyOrbDataBean beanClone = baseDataMapIndex.get(key);

					beanClone.setCandleTimeFrame(KITE_HIST_DATA_15_MINUTES_INTERVAL);
					//beanClone.setInstrumentToken(quote.instrumentToken);
					baseDataMapIndex.replace(key, beanClone);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL, Constants.METHOD_ADD_INDEXES_TO_TRADE2,
					e, Constants.ERROR_TYPE_KITE_EXCEPTION);
			TradewareLogger.sysoutPrintlnForLocalTest(Constants.CLASS_KITE_CONNECT_TOOL
					+ Constants.METHOD_ADD_INDEXES_TO_TRADE2 + Constants.ERROR_TYPE_KITE_EXCEPTION);
		}
		return baseDataMapIndex;
	}
	
	public void updateKiteFutureKeyAndInstrumentToken(List<SymbolDataBean> symbolDataBeans,
			ConcurrentHashMap<String, StrategyOrbDataBean> baseDataMap) {
		try {
			TradewareLogger.saveInfoLog(Constants.CLASS_KITE_CONNECT_TOOL,
					Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN, Constants.CALL_ENTRY);
			if (null != symbolDataBeans && !symbolDataBeans.isEmpty()) {
				Map<String, Integer> symbolMap = new HashMap<String, Integer>();
				for (SymbolDataBean dataBean : symbolDataBeans) {
					if (null != dataBean.getEquityInd() && dataBean.getEquityInd()) {
						symbolMap.put((FUTURE_KEY_PREFIX_NSE + dataBean.getSymbolId()), dataBean.getLotSize());
					}
					if (null != dataBean.getFnoInd() && dataBean.getFnoInd()) {
						symbolMap.put(tradewareTool.getKiteFutureKey(dataBean.getSymbolId()), dataBean.getLotSize());
					}
				}

				Map<String, LTPQuote> quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
						.getLTP(symbolMap.keySet().toArray(new String[0]));

				for (String key : quotesMap.keySet()) {
					LTPQuote quote = quotesMap.get(key);

					StrategyOrbDataBean bean = null;
					if (key.startsWith(Constants.FUTURE_KEY_PREFIX_NSE)) {
						bean = new StrategyOrbDataBean(symbolMap.get(key), tradewareTool.getSymbolName(key));
						bean.setSymbolName(tradewareTool.getSymbolName(key));
					} else {
						bean = new StrategyOrbDataBean(symbolMap.get(key), tradewareTool.getSymbolName(key));
						bean.setSymbolName(tradewareTool.getSymbolName(key));
					}

					bean.setCandleTimeFrame(KITE_HIST_DATA_15_MINUTES_INTERVAL);
					bean.setKiteFutureKey(key);
					bean.setInstrumentToken(quote.instrumentToken);
					bean.setLtp(quote.lastPrice);

					// baseDataMap.put(key, bean);
					// 04/06/2021 Start
					if (bean.getLotSize() >= 100
							&& ((key.startsWith(Constants.FUTURE_KEY_PREFIX_NSE) && bean.getLotSize() < 4000)
									|| (key.startsWith(Constants.FUTURE_KEY_PREFIX_NFO) && bean.getLotSize() < 7000))) {
						baseDataMap.put(key, bean);
					} else {
						System.out.println("Ignored ... " + key + " - " + bean.getLotSize());
					}
					// 04/06/2021 End
				}
			} else {
				TradewareLogger.sysoutPrintlnForLocalTest(
						Constants.CLASS_KITE_CONNECT_TOOL + Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN
								+ Constants.ERROR_ON_UPDATE_OHLC);
				TradewareLogger.saveInfoLog(Constants.CLASS_KITE_CONNECT_TOOL,
						Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN, Constants.ERROR_ON_UPDATE_OHLC);
			}
			TradewareLogger.saveInfoLog(Constants.CLASS_KITE_CONNECT_TOOL,
					Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN, Constants.CALL_EXIT);
			TradewareLogger.sysoutPrintlnForLocalTest(Constants.CLASS_KITE_CONNECT_TOOL
					+ Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN + Constants.CALL_EXIT);
		} catch (JSONException | IOException | KiteException e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL,
					Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN, e,
					Constants.ERROR_TYPE_KITE_EXCEPTION);
			TradewareLogger.sysoutPrintlnForLocalTest(
					Constants.CLASS_KITE_CONNECT_TOOL + Constants.METHOD_UPDATE_KITE_FUTURE_KEY_AND_INSTRUMENT_TOKEN
							+ Constants.ERROR_TYPE_KITE_EXCEPTION);
		}
	}

	public ConcurrentHashMap<String, StrategyOrbDataBean> addIndexesToTrade2() {
		ConcurrentHashMap<String, StrategyOrbDataBean> baseDataMapIndex = new ConcurrentHashMap<String, StrategyOrbDataBean>();
		try {
			StrategyOrbDataBean bean2 = new StrategyOrbDataBean(75, NIFTY_50);
			bean2.setSymbolName(NIFTY_50_FUT);
			bean2.setKiteFutureKey(tradewareTool.getKiteFutureKey(NIFTY));
			baseDataMapIndex.put(bean2.getKiteFutureKey(), bean2);

			StrategyOrbDataBean bean4 = new StrategyOrbDataBean(25, BANKNIFTY);
			bean4.setSymbolName(BANKNIFTY_FUT);
			bean4.setKiteFutureKey(tradewareTool.getKiteFutureKey(BANKNIFTY));
			baseDataMapIndex.put(bean4.getKiteFutureKey(), bean4);

			StrategyOrbDataBean bean10 = new StrategyOrbDataBean(40, FINNIFTY);
			bean10.setSymbolName("FINNIFTY(FUT)");
			bean10.setKiteFutureKey(tradewareTool.getKiteFutureKey(FINNIFTY));
			baseDataMapIndex.put(bean10.getKiteFutureKey(), bean10);

			/*
			 * StrategyOrbDataBean bean5 = new StrategyOrbDataBean(25, BANKNIFTY);
			 * bean5.setSymbolName(BANK_NIFTY); bean5.setKiteFutureKey(NSE_BANK_NIFTY);
			 * baseDataMapIndex.put(bean5.getKiteFutureKey(), bean5);
			 */

			Map<String, Quote> quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
					.getQuote(baseDataMapIndex.keySet().toArray(new String[0]));

			for (String key : quotesMap.keySet()) {
				Quote quote = quotesMap.get(key);

				if (null != quotesMap.get(key).depth && null != quotesMap.get(key).depth.buy
						&& !quotesMap.get(key).depth.buy.isEmpty()) {

					StrategyOrbDataBean beanClone = baseDataMapIndex.get(key);

					beanClone.setCandleTimeFrame(KITE_HIST_DATA_15_MINUTES_INTERVAL);
					beanClone.setInstrumentToken(quote.instrumentToken);
					baseDataMapIndex.replace(key, beanClone);
				} else {
					System.out.println("Removed : " + key);
					tradewareTool.getBaseDataMapAll().remove(key);
				}
			}
		} catch (JSONException | IOException | KiteException e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL, Constants.METHOD_ADD_INDEXES_TO_TRADE2,
					e, Constants.ERROR_TYPE_KITE_EXCEPTION);
			TradewareLogger.sysoutPrintlnForLocalTest(Constants.CLASS_KITE_CONNECT_TOOL
					+ Constants.METHOD_ADD_INDEXES_TO_TRADE2 + Constants.ERROR_TYPE_KITE_EXCEPTION);
		}
		return baseDataMapIndex;
	}
	// Phase 4 end

	public static ConcurrentHashMap<String, HistoricalData> minute5KiteHistDataMap = new ConcurrentHashMap<String, HistoricalData>();








	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Map<String, OHLCQuote> getKiteOHLCQuotes(Set<String> symbols) {
		Map<String, OHLCQuote> quotesMap = null;
		//String[] symbolsArray = symbols.toArray(new String[0]);
		
		String[] symbolsArray = new String[symbols.size()];
		int counter = 0;
		for (String symbol : symbols) {
			symbolsArray[counter] = NSEIndiaTool.getInstance().getKiteFutureKeyMap().get(symbol);
			counter++;
		}
		try {
			quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).getOHLC(symbolsArray);
		} catch (JSONException | IOException | KiteException e) {
			e.printStackTrace();
		}
		return quotesMap;
	}
	
	public Map<String, OHLCQuote> getKiteOHLCQuotes(String[] symbolsArray) {
		Map<String, OHLCQuote> quotesMap = null;
		try {
			quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).getOHLC(symbolsArray);
		} catch (JSONException | IOException | KiteException e) {
			e.printStackTrace();
		}
		return quotesMap;
	}
	
	public Map<String, LTPQuote> getKiteLTPQuotes(Set<String> symbols) {
		Map<String, LTPQuote> quotesMap = null;
		String[] symbolsArray = new String[symbols.size()];

		int counter = 0;
		for (String symbol : symbols) {
			symbolsArray[counter] = NSEIndiaTool.getInstance().getKiteFutureKeyMap().get(symbol);
			counter++;
		}
		try {
			quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).getLTP(symbolsArray);
		} catch (JSONException | IOException | KiteException e) {
			e.printStackTrace();
		}
		return quotesMap;
	}
	
	public Map<String, Quote> getKiteQuotes(Set<String> symbols) {
		Map<String, Quote> quotesMap = null;
		String[] symbolsArray = new String[symbols.size()];

		int counter = 0;
		for (String symbol : symbols) {
			symbolsArray[counter] = NSEIndiaTool.getInstance().getKiteFutureKeyMap().get(symbol);
			counter++;
		}
		//String[] symbolsArray = symbols.toArray(new String[0]);
		try {
			quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).getQuote(symbolsArray);
		} catch (JSONException | IOException | KiteException e) {
			e.printStackTrace();
		}
		return quotesMap;
	}

	public Map<String, Quote> getKiteQuotes(String[] symbolsArray) {
		Map<String, Quote> quotesMap = null;
		try {
			quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).getQuote(symbolsArray);
		} catch (JSONException | IOException | KiteException e) {
			e.printStackTrace();
		}
		return quotesMap;
	}
	
	public String getKiteOptionKey(String symbolId, String expiiryDate, String strikePrice, String callPutType) {
		return (Constants.FUTURE_KEY_PREFIX_NFO + symbolId + expiiryDate.substring(9, 11) + expiiryDate.substring(3, 6)
				+ strikePrice + callPutType).toUpperCase();
	}
	
	//NFO:NIFTY21Sep17000CE


		//NIFTY21J0713600CE
		
		/*
		 * if (Constants.OPTION_CALL.equals(callPutType)) {
					key = ("NFO:" + symbol + currExp.substring(7, 9) + currExp.substring(2, 5) + optionTradeAt + callPutType)
							.toUpperCase();
				} else if (Constants.OPTION_PUT.equals(callPutType)) {
					key = ("NFO:" + symbol + currExp.substring(7, 9) + currExp.substring(2, 5) + optionTradeAt + callPutType)
							.toUpperCase();
				}
		 */
	
	
	// Just to check Kite Access
	private static Boolean validKiteAccess = null;

	public Boolean validKiteAccess() {
		if (null == validKiteAccess) {
			try {
				Map<String, Long> indexOptionTradeMap = tradewareTool.getIndexOptionTradeKiteInstrumentMap();
				Map<String, LTPQuote> quotesMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
						.getLTP(indexOptionTradeMap.keySet().toArray(new String[0]));

				for (String key : quotesMap.keySet()) {
					LTPQuote quote = quotesMap.get(key);
					indexOptionTradeMap.replace(key, quote.instrumentToken);
				}
				validKiteAccess = true;
			} catch (JSONException | IOException | KiteException e) {
				validKiteAccess = false;
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL, Constants.METHOD_VALID_KITE_ACCESS,
						e, Constants.ERROR_TYPE_KITE_EXCEPTION);
				TradewareLogger.sysoutPrintlnForLocalTest(Constants.CLASS_KITE_CONNECT_TOOL
						+ Constants.METHOD_VALID_KITE_ACCESS + Constants.ERROR_TYPE_KITE_EXCEPTION);
			}
		}
		return validKiteAccess;
	}
}
