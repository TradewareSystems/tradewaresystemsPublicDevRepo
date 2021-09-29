package org.tradeware.stockmarket.scanner;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.tradeware.stockmarket.bean.Narrow7StockDataBean;
import org.tradeware.stockmarket.bean.OptionStockDataBean;
import org.tradeware.stockmarket.brokerengine.KiteConnectTool;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;
import org.tradeware.stockmarket.engine.tool.FreeCallDataTool;
import org.tradeware.stockmarket.engine.tool.TradewareTraderUtil;
import org.tradeware.stockmarket.util.Constants;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.models.Quote;

public class BuyOrSellStocksScannerThread_V2 implements Runnable {// extends Thread {
	private KiteConnect kiteConnect = null;
	private KiteConnectTool kiteConnectTool = null;
	private Hashtable<String, OptionStockDataBean> optionMapToPlaceCalls = null;
	private Hashtable<String, OptionStockDataBean> monitorPlacedCall = null;

	public BuyOrSellStocksScannerThread_V2(KiteConnect kiteConnect) {
		this.kiteConnect = kiteConnect;
		this.kiteConnectTool = KiteConnectTool.getInstance(this.kiteConnect);
		prepareDate();
	}

	private void prepareDate() {
		optionMapToPlaceCalls = new Hashtable<String, OptionStockDataBean>();
		monitorPlacedCall = new Hashtable<String, OptionStockDataBean>();

		@SuppressWarnings("unchecked")
		Hashtable<String, OptionStockDataBean> tempMap = (Hashtable<String, OptionStockDataBean>) NSEIndiaTool
				.getInstance().getPositionalOptionsMap().clone();
		for (String symbol : tempMap.keySet()) {
			OptionStockDataBean bean = tempMap.get(symbol);
			try {
				optionMapToPlaceCalls.put(symbol, bean.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		// super.run();

		while (TradewareTraderUtil.getInstance().isTradingTime()) {
			//updateFreeCallDataMapToGoogleSheet();

			if (!optionMapToPlaceCalls.isEmpty()) {
				optionsBuySellThreadScanner();
			}

			/*if (!narrow7MapToPlaceCalls.isEmpty()) {
				narrowBuySellThreadScanner();
			}*/
		}
	}

	private void updateFreeCallDataMapToGoogleSheet() {
		List<String> kiteFreeCallFutKeys = FreeCallDataTool.getInstance().getFreeCallFutKeyList();
		String[] kiteFreeCallFutKeyArray = kiteFreeCallFutKeys.toArray(new String[0]);
		Map<String, Quote> quotesMap = kiteConnectTool.getKiteQuotes(kiteFreeCallFutKeyArray);
		FreeCallDataTool.getInstance().updateFreeCallDataMap(quotesMap);
	}

	private void optionsBuySellThreadScanner() {
		System.out.println("OPTIONS>> BuyOrSellStocksScannerThread > positionalOptionsMap scanner list count - "
				+ optionMapToPlaceCalls.size());
		Map<String, Quote> quotesMap = kiteConnectTool.getKiteQuotes(optionMapToPlaceCalls.keySet());
		monitorPlacedCall.clear();
		boolean tradableRule = false;
		boolean additionalCheck = false;

		for (String symbol : optionMapToPlaceCalls.keySet()) {
			OptionStockDataBean optionBean = optionMapToPlaceCalls.get(symbol);
			String futureKey = NSEIndiaTool.getInstance().getKiteFutureKeyMap().get(symbol);
			double lastPrice = optionBean.getScaledVal(quotesMap.get(futureKey).lastPrice);
			double close = quotesMap.get(futureKey).ohlc.close;
			double high = quotesMap.get(futureKey).ohlc.high;
			double low = quotesMap.get(futureKey).ohlc.low;
			double uptrendVal = optionBean.getScaledVal(optionBean.getPositionalUptrendValue().doubleValue());
			double downptrendVal = optionBean.getScaledVal(optionBean.getPositionalDowntrendValue().doubleValue());

			optionBean.setClose(optionBean.getScaledValueObj(close));
			optionBean.setLtp(optionBean.getScaledValueObj(lastPrice));
			optionBean.setHigh(optionBean.getScaledValueObj(high));
			optionBean.setLow(optionBean.getScaledValueObj(low));
			
			if (Constants.NA.equals(optionBean.getTradeState())) {
				if (TradewareTraderUtil.getInstance().canOptionOrderPlaceNow()) {

					if (lastPrice > uptrendVal) {
						String tradeOption = TradewareTraderUtil.getInstance().getOptionTickerKey(symbol, lastPrice,
								Constants.OPTION_CALL);
						String[] symbolsArray = { tradeOption };
						optionBean.setTradeState(Constants.FUTURE_BUY);
						optionBean.setKiteOptionKey(tradeOption);
						if (null == optionBean.getTradedVal()) {
							optionBean.setTradedVal(optionBean.getScaledValueObj(lastPrice));
						}
						tradableRule = ((quotesMap.get(futureKey).volumeTradedToday > Constants.ONE_LAC)
								&& ((quotesMap.get(futureKey).buyQuantity) > (quotesMap.get(futureKey).sellQuantity)));
						optionBean.setTradableVolumeRule(tradableRule);
						
						if (TradewareTraderUtil.getInstance().changePercentageValidation(optionBean)) {
							// call is placing first time
							if (null == optionBean.getTopHigh()
									|| uptrendVal > optionBean.getScaledValue(optionBean.getTopHigh())) {
								if (tradableRule) {
									if (!Constants.EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())
											&& optionBean.getSignalTriggeredInAt()
													.contains(Constants.FUT_BUY_TRADE_RULE_FAILED)
											&& !optionBean.getSignalTriggeredInAt()
													.contains(Constants.FUT_BUY_TRADE_RULE_SUCCESS)) {
										optionBean.setSignalTriggeredInAt(
												TradewareTraderUtil.getInstance().getCurrentTime(), Constants.LINE_BREAK,
												Constants.FUT_BUY_TRADE_RULE_SUCCESS, Constants.AT_RATE_OF, lastPrice);
									}
									if (Constants.EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())
											|| !optionBean.getSignalTriggeredInAt().contains(Constants.FRESH_CALL)) {
										optionBean.setSignalTriggeredInAt(
												TradewareTraderUtil.getInstance().getCurrentTime(), Constants.LINE_BREAK,
												Constants.FRESH_CALL, Constants.AT_RATE_OF, lastPrice);
									}
								} else {
									if (Constants.EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())
											|| !optionBean.getSignalTriggeredInAt()
													.contains(Constants.FUT_BUY_TRADE_RULE_FAILED)) {
										optionBean.setSignalTriggeredInAt(
												TradewareTraderUtil.getInstance().getCurrentTime(), Constants.LINE_BREAK,
												Constants.FUT_BUY_TRADE_RULE_FAILED, Constants.AT_RATE_OF, lastPrice);
									}
								}
								TradewareTraderUtil.getInstance().placeOptionOrder(tradeOption, Constants.BUY, optionBean,
										kiteConnectTool.getKiteQuotes(symbolsArray));
							} else {
								additionalCheck = (quotesMap
										.get(futureKey).buyQuantity) > ((quotesMap.get(futureKey).sellQuantity)
												* Constants.ADDITONAL_CHECK);
								optionBean.setTradableVolumeRule(additionalCheck);
								if (tradableRule && additionalCheck) {
									if (!Constants.EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())
											&& optionBean.getSignalTriggeredInAt()
													.contains(Constants.FUT_BUY_TRADE_RULE_FAILED)
											&& !optionBean.getSignalTriggeredInAt()
													.contains(Constants.FUT_BUY_TRADE_RULE_SUCCESS)) {
										optionBean.setSignalTriggeredInAt(
												TradewareTraderUtil.getInstance().getCurrentTime(), Constants.LINE_BREAK,
												Constants.FUT_BUY_TRADE_RULE_SUCCESS, Constants.AT_RATE_OF, lastPrice);
									}
									if (Constants.EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())
											|| !optionBean.getSignalTriggeredInAt().contains(Constants.CALL_ALREADY_PLACED_VALID)) {
										optionBean.setSignalTriggeredInAt(
												TradewareTraderUtil.getInstance().getCurrentTime(), Constants.LINE_BREAK,
												Constants.CALL_ALREADY_PLACED_VALID, Constants.AT_RATE_OF, lastPrice,
												//May not be required below 3 lines
												Constants.LINE_BREAK, Constants.TRADABLE_RULE, Constants.AT_RATE_OF,
												tradableRule, Constants.ADDITIONAL_CHECK_RULE, Constants.AT_RATE_OF,
												additionalCheck);
									}
								} else {
									if (Constants.EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())
											|| !optionBean.getSignalTriggeredInAt()
													.contains(Constants.FUT_BUY_TRADE_RULE_FAILED)) {
										optionBean.setSignalTriggeredInAt(
												TradewareTraderUtil.getInstance().getCurrentTime(), Constants.LINE_BREAK,
												Constants.FUT_BUY_TRADE_RULE_FAILED, Constants.AT_RATE_OF, lastPrice,
												Constants.LINE_BREAK, Constants.TRADABLE_RULE, Constants.AT_RATE_OF,
												tradableRule, Constants.ADDITIONAL_CHECK_RULE, Constants.AT_RATE_OF,
												additionalCheck);
									}
								}
								TradewareTraderUtil.getInstance().placeOptionOrder(tradeOption, Constants.BUY, optionBean,
										kiteConnectTool.getKiteQuotes(symbolsArray));
							}
							if(!optionBean.getTradeState().contains(Constants.OPTION_BUY)) {
								optionBean.setTradeState(Constants.NA);
							}
							NSEIndiaTool.getInstance().getPositionalOptionsMap().put(symbol, optionBean);
							optionMapToPlaceCalls.put(symbol, optionBean);
						}
					} else if (lastPrice < downptrendVal){
						String tradeOption = TradewareTraderUtil.getInstance().getOptionTickerKey(symbol, lastPrice,
								Constants.OPTION_PUT);
						String[] symbolsArray = { tradeOption };
						if (null == optionBean.getTradedVal()) {
							optionBean.setTradedVal(optionBean.getScaledValueObj(lastPrice));
						}
						optionBean.setTradeState(Constants.FUTURE_SELL);
						optionBean.setKiteOptionKey(tradeOption);
						tradableRule = ((quotesMap.get(futureKey).volumeTradedToday > Constants.ONE_LAC)
								&& ((quotesMap.get(futureKey).buyQuantity) < (quotesMap.get(futureKey).sellQuantity)));
						optionBean.setTradableVolumeRule(tradableRule);

						if (TradewareTraderUtil.getInstance().changePercentageValidation(optionBean)) {
							if (null == optionBean.getTopLow()
									|| downptrendVal < optionBean.getScaledValue(optionBean.getTopLow())) {
								if (tradableRule) {
									if (!Constants.EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())
											&& optionBean.getSignalTriggeredInAt()
													.contains(Constants.FUT_SELL_TRADE_RULE_FAILED)
											&& !optionBean.getSignalTriggeredInAt()
													.contains(Constants.FUT_SELL_TRADE_RULE_SUCCESS)) {
										optionBean.setSignalTriggeredInAt(
												TradewareTraderUtil.getInstance().getCurrentTime(), Constants.LINE_BREAK,
												Constants.FUT_SELL_TRADE_RULE_SUCCESS, Constants.AT_RATE_OF, lastPrice);
									}
									if (Constants.EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())
											|| !optionBean.getSignalTriggeredInAt().contains(Constants.FRESH_CALL)) {
										optionBean.setSignalTriggeredInAt(
												TradewareTraderUtil.getInstance().getCurrentTime(), Constants.LINE_BREAK,
												Constants.FRESH_CALL, Constants.AT_RATE_OF, lastPrice);
									}
								} else {
									if (Constants.EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())
											|| !optionBean.getSignalTriggeredInAt()
													.contains(Constants.FUT_SELL_TRADE_RULE_FAILED)) {
										optionBean.setSignalTriggeredInAt(
												TradewareTraderUtil.getInstance().getCurrentTime(), Constants.LINE_BREAK,
												Constants.FUT_SELL_TRADE_RULE_FAILED, Constants.AT_RATE_OF, lastPrice);
									}
								}
								TradewareTraderUtil.getInstance().placeOptionOrder(tradeOption, Constants.BUY, optionBean,
										kiteConnectTool.getKiteQuotes(symbolsArray));
							} else {
								additionalCheck = ((quotesMap.get(futureKey).buyQuantity)
										* Constants.ADDITONAL_CHECK) < (quotesMap.get(futureKey).sellQuantity);
								optionBean.setTradableVolumeRule(additionalCheck);
								if (tradableRule && additionalCheck) {
									if (!Constants.EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())
											&& optionBean.getSignalTriggeredInAt()
													.contains(Constants.FUT_SELL_TRADE_RULE_FAILED)
											&& !optionBean.getSignalTriggeredInAt()
													.contains(Constants.FUT_SELL_TRADE_RULE_SUCCESS)) {
										optionBean.setSignalTriggeredInAt(
												TradewareTraderUtil.getInstance().getCurrentTime(), Constants.LINE_BREAK,
												Constants.FUT_SELL_TRADE_RULE_SUCCESS, Constants.AT_RATE_OF, lastPrice);
									}
									if (Constants.EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())
											|| !optionBean.getSignalTriggeredInAt().contains(Constants.CALL_ALREADY_PLACED_VALID)) {
										optionBean.setSignalTriggeredInAt(
												TradewareTraderUtil.getInstance().getCurrentTime(), Constants.LINE_BREAK,
												Constants.CALL_ALREADY_PLACED_VALID, Constants.AT_RATE_OF, lastPrice,
												//May not be required below 3 lines
												Constants.LINE_BREAK, Constants.TRADABLE_RULE, Constants.AT_RATE_OF,
												tradableRule, Constants.ADDITIONAL_CHECK_RULE, Constants.AT_RATE_OF,
												additionalCheck);
									}
								} else {
									if (Constants.EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())
											|| !optionBean.getSignalTriggeredInAt()
													.contains(Constants.FUT_SELL_TRADE_RULE_FAILED)) {
										optionBean.setSignalTriggeredInAt(
												TradewareTraderUtil.getInstance().getCurrentTime(), Constants.LINE_BREAK,
												Constants.FUT_SELL_TRADE_RULE_FAILED, Constants.AT_RATE_OF, lastPrice,
												Constants.LINE_BREAK, Constants.TRADABLE_RULE, Constants.AT_RATE_OF,
												tradableRule, Constants.ADDITIONAL_CHECK_RULE, Constants.AT_RATE_OF,
												additionalCheck);
									}
								}
								TradewareTraderUtil.getInstance().placeOptionOrder(tradeOption, Constants.BUY, optionBean,
										kiteConnectTool.getKiteQuotes(symbolsArray));
							}
							if(!optionBean.getTradeState().contains(Constants.OPTION_SELL)) {
								optionBean.setTradeState(Constants.NA);
							}
							NSEIndiaTool.getInstance().getPositionalOptionsMap().put(symbol, optionBean);
							optionMapToPlaceCalls.put(symbol, optionBean);
						}
					}
				}
			} else {
				/*if ((Constants.OPTION_BUY.equals(optionBean.getTradeState())
						|| Constants.OPTION_SELL.equals(optionBean.getTradeState()))) {
					monitorPlacedCall.put(optionBean.getKiteOptionKey(), optionBean);
				}*/
				if (!optionBean.getTradeState().contains("EXIT")) {
					monitorPlacedCall.put(optionBean.getKiteOptionKey(), optionBean);
				}
			}
		}

		if (!monitorPlacedCall.isEmpty()) {
			String[] symbolsArrayOption = monitorPlacedCall.keySet().toArray(new String[0]);
			Map<String, Quote> quotesMapOption = kiteConnectTool.getKiteQuotes(symbolsArrayOption);
			TradewareTraderUtil.getInstance().monitorAndChaseProfitLossOptions(monitorPlacedCall, quotesMapOption);
		}
	}

	private ConcurrentHashMap<String, Narrow7StockDataBean> narrow7MapToPlaceCalls = NSEIndiaTool.getInstance()
			.getNarrow7Map();

	private void narrowBuySellThreadScanner() {
		System.out.println("Narrow 7 scanner list count - " + narrow7MapToPlaceCalls.size());
		Map<String, Quote> quotesMap = kiteConnectTool.getKiteQuotes(narrow7MapToPlaceCalls.keySet());
		// monitorNarrow7PlacedCall.clear();
		for (String symbol : narrow7MapToPlaceCalls.keySet()) {
			Narrow7StockDataBean narrow7Bean = narrow7MapToPlaceCalls.get(symbol);
			//if (narrow7Bean.getCanTradeToday()) {
				double lastPrice = quotesMap.get(narrow7Bean.getKiteFutureKey()).lastPrice;
				double open = quotesMap.get(narrow7Bean.getKiteFutureKey()).ohlc.open;
				narrow7Bean.setLtp(narrow7Bean.getScaledValueObj(lastPrice));
				narrow7Bean.setOpen(narrow7Bean.getScaledValueObj(open));
				narrow7Bean.setHigh(
						narrow7Bean.getScaledValueObj(quotesMap.get(narrow7Bean.getKiteFutureKey()).ohlc.high));
				narrow7Bean
						.setLow(narrow7Bean.getScaledValueObj(quotesMap.get(narrow7Bean.getKiteFutureKey()).ohlc.low));

				if (0 == open) {
					NSEIndiaTool.getInstance().getNarrow7Map().put(symbol, narrow7Bean);
					continue;
				} else if (narrow7Bean.getScaledValue(narrow7Bean.getOpen()) > narrow7Bean.getBuyValue()) {
					narrow7Bean.setBuyStatus("Diaqualified (Gap up)");
					//narrow7Bean.setCanTradeToday(Boolean.FALSE);
					// continue;
				} else if (narrow7Bean.getScaledValue(narrow7Bean.getOpen()) < narrow7Bean.getSellValue()) {
					narrow7Bean.setSellStatus("Diaqualified (Gap down)");
					//narrow7Bean.setCanTradeToday(Boolean.FALSE);
					// continue;
				}

				if (Constants.NA.equals(narrow7Bean.getBuyStatus()) && lastPrice > narrow7Bean.getBuyValue()) {
					narrow7Bean.setBuyStatus("Activated");
					narrow7Bean.setSignalTriggeredInAt("BUY Activated @ ", Constants.AT_RATE_OF,
							TradewareTraderUtil.getInstance().getCurrentTime());
				} else if (Constants.NA.equals(narrow7Bean.getSellStatus()) && lastPrice < narrow7Bean.getSellValue()) {
					narrow7Bean.setSellStatus("Activated");
					narrow7Bean.setSignalTriggeredInAt("SELL Activated  @ ", Constants.AT_RATE_OF,
							TradewareTraderUtil.getInstance().getCurrentTime());
				} else if ("Activated".equals(narrow7Bean.getBuyStatus())) {
					if (lastPrice > narrow7Bean.getBuyValueTarget()) {
						narrow7Bean.setSignalTriggeredOutAt(TradewareTraderUtil.getInstance().getCurrentTime(),
								Constants.SPACE, "BUY TARGET DONE @ ", lastPrice, Constants.BREAK, "FINAL PROFIT",
								Constants.AT_RATE_OF, narrow7Bean.getScaledVal(
										((lastPrice - narrow7Bean.getBuyValue()) * narrow7Bean.getLotSize())));
						narrow7Bean.setBuyStatus("Target Done");
						narrow7Bean.setProfitLossAmt(narrow7Bean
								.getScaledValueObj((narrow7Bean.getScaledValue(narrow7Bean.getProfitLossAmt()))
										+ (((lastPrice - narrow7Bean.getBuyValue()) * narrow7Bean.getLotSize()))));
					} else if (lastPrice < narrow7Bean.getSellValue()) {
						narrow7Bean.setSignalTriggeredOutAt(TradewareTraderUtil.getInstance().getCurrentTime(),
								Constants.SPACE, "CLOSED WITH LOSE @ ", lastPrice, Constants.BREAK, "FINAL LOSS",
								Constants.AT_RATE_OF, Constants.SPACE, narrow7Bean.getScaledVal(
										((lastPrice - narrow7Bean.getBuyValue()) * narrow7Bean.getLotSize())));
						narrow7Bean.setBuyStatus("Closed with loss");
						narrow7Bean.setProfitLossAmt(narrow7Bean
								.getScaledValueObj((narrow7Bean.getScaledValue(narrow7Bean.getProfitLossAmt()))
										+ (((lastPrice - narrow7Bean.getBuyValue()) * narrow7Bean.getLotSize()))));
					}
				} else if ("Activated".equals(narrow7Bean.getSellStatus())) {
					if (lastPrice < narrow7Bean.getSellValueTarget()) {
						narrow7Bean.setSignalTriggeredOutAt(TradewareTraderUtil.getInstance().getCurrentTime(),
								Constants.SPACE, "SELL TARGET DONE @ ", lastPrice, Constants.BREAK, "FINAL PROFIT",
								Constants.AT_RATE_OF, narrow7Bean.getScaledVal(
										((narrow7Bean.getSellValue() - lastPrice) * narrow7Bean.getLotSize())));
						narrow7Bean.setSellStatus("Target Done");
						narrow7Bean.setProfitLossAmt(narrow7Bean
								.getScaledValueObj((narrow7Bean.getScaledValue(narrow7Bean.getProfitLossAmt()))
										+ (((narrow7Bean.getSellValue() - lastPrice) * narrow7Bean.getLotSize()))));
					} else if (lastPrice > narrow7Bean.getBuyValue()) {
						narrow7Bean.setSignalTriggeredOutAt(TradewareTraderUtil.getInstance().getCurrentTime(),
								Constants.SPACE, "CLOSED WITH LOSE @ ", lastPrice, Constants.BREAK, "FINAL LOSS",
								Constants.AT_RATE_OF,
								(((narrow7Bean.getSellValue() - lastPrice) * narrow7Bean.getLotSize())));
						narrow7Bean.setSellStatus("Closed with loss");
						narrow7Bean.setProfitLossAmt(narrow7Bean
								.getScaledValueObj((narrow7Bean.getScaledValue(narrow7Bean.getProfitLossAmt()))
										+ (((narrow7Bean.getSellValue() - lastPrice) * narrow7Bean.getLotSize()))));
					}
				}
				NSEIndiaTool.getInstance().getNarrow7Map().put(symbol, narrow7Bean);
			//}
		}

	}
}
