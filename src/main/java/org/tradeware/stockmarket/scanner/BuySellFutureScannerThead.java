package org.tradeware.stockmarket.scanner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.tradeware.stockmarket.bean.OptionChainDataBean;
import org.tradeware.stockmarket.bean.StockDataBean;
import org.tradeware.stockmarket.brokerengine.KiteConnectTool;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;
import org.tradeware.stockmarket.engine.telegramtool.TelegramMessageTool;
import org.tradeware.stockmarket.engine.tool.TradewareTraderUtil;
import org.tradeware.stockmarket.util.Constants;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.models.Quote;

public class BuySellFutureScannerThead implements Runnable {

	private KiteConnectTool kiteConnectTool = null;
	private Hashtable<String, StockDataBean> positionalFutureDataMap = null;
	private Hashtable<String, StockDataBean> monitorPlacedCall = null;

	public BuySellFutureScannerThead(KiteConnect kiteConnect) {
		this.kiteConnectTool = KiteConnectTool.getInstance(kiteConnect);
		prepareDate();
	}

/*	private void prepareDate() {
		positionalFutureDataMap = new Hashtable<String, OptionStockDataBean>();
		monitorPlacedCall = new Hashtable<String, OptionStockDataBean>();

		Hashtable<String, OptionStockDataBean> tempMap = (Hashtable<String, OptionStockDataBean> )NSEIndiaTool.getInstance().getPositionalOptionsMap().clone();
		for (String symbol : tempMap.keySet()) {
			OptionStockDataBean bean = tempMap.get(symbol);
			try {
				positionalFutureDataMap.put(symbol, bean.clone());
			} catch (CloneNotSupportedException e) {
				System.out.println("BuySellFtureScannerThead>prepareDate : " + e.getMessage());
			}
		}
	}*/
	
	/*private void prepareDate() {
		positionalFutureDataMap = new Hashtable<String, StockDataBean>();
		monitorPlacedCall = new Hashtable<String, StockDataBean>();

		Set<String> list = NSEIndiaTool.getInstance().getPositionalOptionsMap().keySet();
		for (String symbol : list) {
			try {
				StockDataBean bean = (StockDataBean) NSEIndiaTool.getInstance().getPositionalOptionsMap().get(symbol)
						.clone();
				positionalFutureDataMap.put(symbol, bean);
			} catch (CloneNotSupportedException e) {
				System.out.println("BuySellFtureScannerThead>prepareDate : " + e.getMessage());
			}
		}
	}*/
	private void prepareDate() {
		positionalFutureDataMap = new Hashtable<String, StockDataBean>();
		monitorPlacedCall = new Hashtable<String, StockDataBean>();

		Set<String> list = NSEIndiaTool.getInstance().getPositionalOptionsMap().keySet();
		for (String symbol : list) {
			try {
				StockDataBean bean = (StockDataBean) NSEIndiaTool.getInstance().getPositionalOptionsMap().get(symbol);
				StockDataBean futureBbean = new StockDataBean(bean.getLotSize(),  symbol);	
				futureBbean.setPositionalUptrendValue(bean.getPositionalUptrendValue());
				futureBbean.setPositionalDowntrendValue(bean.getPositionalDowntrendValue());
				futureBbean.setTopHigh(bean.getTopHigh());
				futureBbean.setTopLow(bean.getTopLow());
				futureBbean.setKiteFutureKey(bean.getKiteFutureKey());
				positionalFutureDataMap.put(symbol, futureBbean);
				
			} catch (/*CloneNotSupported*/Exception e) {
				System.out.println("BuySellFtureScannerThead>prepareDate : " + e.getMessage());
			}
		}
	}

	@Override
	public void run() {
		while (TradewareTraderUtil.getInstance().isTradingTime()) {

			if (!positionalFutureDataMap.isEmpty()) {
				futureTradePosLvelThreadScanner();
			}
			TelegramMessageTool.getInstance().setLastRuntime(TradewareTraderUtil.getInstance().getCurrentTime());
		}
	}

	private void futureTradePosLvelThreadScanner() {
		System.out.println("FUTURES>> BuyOrSellStocksScannerThread > positionaFutureMap scanner list count - "
				+ positionalFutureDataMap.size());
		Map<String, Quote> quotesMap = kiteConnectTool.getKiteQuotes(positionalFutureDataMap.keySet());
		monitorPlacedCall.clear();
		boolean tradableRule = false;
		boolean additionalCheck = false;

		for (String symbol : positionalFutureDataMap.keySet()) {
			StockDataBean bean = positionalFutureDataMap.get(symbol);

			double lastPrice = quotesMap.get(bean.getKiteFutureKey()).lastPrice;
			double close = quotesMap.get(bean.getKiteFutureKey()).ohlc.close;
			double high = quotesMap.get(bean.getKiteFutureKey()).ohlc.high;
			double low = quotesMap.get(bean.getKiteFutureKey()).ohlc.low;
			double uptrendVal = bean.getScaledVal(bean.getPositionalUptrendValue().doubleValue());
			double downptrendVal = bean.getScaledVal(bean.getPositionalDowntrendValue().doubleValue());
			bean.setLtp(bean.getScaledValueObj(lastPrice));
			bean.setClose(bean.getScaledValueObj(close));
			bean.setBuyerAt(bean.getScaledValueObj(quotesMap.get(bean.getKiteFutureKey()).depth.buy.get(0).getPrice()));
			bean.setSellerAt(bean.getScaledValueObj(quotesMap.get(bean.getKiteFutureKey()).depth.sell.get(0).getPrice()));
			if (Constants.NA.equals(bean.getTradeState())) {
				if (TradewareTraderUtil.getInstance().canOptionOrderPlaceNow()) {

					if (lastPrice > uptrendVal) {
						bean.setTradedVal(bean.getScaledValueObj(lastPrice));
						bean.setTradeState(Constants.BUY);

						tradableRule = ((quotesMap
								.get(bean.getKiteFutureKey()).volumeTradedToday > Constants.ONE_LAC)
								&& ((quotesMap.get(bean.getKiteFutureKey()).buyQuantity) > (quotesMap
										.get(bean.getKiteFutureKey()).sellQuantity)));
						bean.setTradableVolumeRule(tradableRule);
						
						double changePercentage = getchangePercentage(high, close);
						if ((high > (uptrendVal + (uptrendVal * 0.0020)))
								|| (changePercentage > 5)) {
							String ruleString = "";
							if (changePercentage > 5) {
								ruleString = "5% Change Rule failed";
							} else if (high > (uptrendVal + (uptrendVal * 0.0020))){
								ruleString = "Max Level failed";
							}
							bean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime() + " @    LTP : " + lastPrice
									+ "  <br>  Change % : " + changePercentage + "  @   MaxLevel : "
									+ bean.getScaledVal(uptrendVal + (uptrendVal * 0.0020)) + "<BR>" + ruleString);
							//bean.setTradeState(Constants.BUY+Constants.FAIL);
							NSEIndiaTool.getInstance().getPositionalFutureMap().put(symbol, bean);
							positionalFutureDataMap.put(symbol, bean);
							continue;
						}

						// call is placing first time
						if (null == bean.getTopHigh() || uptrendVal > bean.getTopHigh().doubleValue()) {
							// if (tradableRule) {
							bean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime()
									 + Constants.AT_RATE_OF + "LTP : " + lastPrice+ Constants.AT_RATE_OF +  Constants.FRESH_CALL);

							/*
							 * TradewareTraderUtil.getInstance().placeOptionOrder(tradeOption, Constants.BUY,
							 * optionBean, kiteConnectTool.getKiteQuotes(symbolsArray));
							 */
							// }
						} else {
							additionalCheck = (quotesMap.get(bean.getKiteFutureKey()).buyQuantity) > ((quotesMap
									.get(bean.getKiteFutureKey()).sellQuantity) * Constants.ADDITONAL_CHECK);
							bean.setTradableVolumeRule(additionalCheck);
							// if (additionalCheck) {
							/*
							 * TradewareTraderUtil.getInstance().placeOptionOrder(tradeOption, Constants.BUY,
							 * optionBean, kiteConnectTool.getKiteQuotes(symbolsArray));
							 */
							bean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime()
									+ Constants.AT_RATE_OF /* + optionBean.getSignalTriggeredInAt() + Constants.BREAK */+ " LTP : " + lastPrice
									+ Constants.AT_RATE_OF  + Constants.CALL_ALREADY_PLACED_VALID);
							// }
						}
						bean = updateOptionChainRule(bean);
						NSEIndiaTool.getInstance().getPositionalFutureMap().put(symbol, bean);
						positionalFutureDataMap.put(symbol, bean);
					} else if (lastPrice < downptrendVal) {
						bean.setTradedVal(bean.getScaledValueObj(lastPrice));
						bean.setLtp(bean.getScaledValueObj(lastPrice));
						bean.setTradeState(Constants.SELL);

						tradableRule = ((quotesMap
								.get(bean.getKiteFutureKey()).volumeTradedToday > Constants.ONE_LAC)
								&& ((quotesMap.get(bean.getKiteFutureKey()).buyQuantity) < (quotesMap
										.get(bean.getKiteFutureKey()).sellQuantity)));
						bean.setTradableVolumeRule(tradableRule);
						double changePercentage = getchangePercentage(low, close);				
		if ((low < (downptrendVal - (downptrendVal * 0.0020)))
				|| (changePercentage < -5)) {
			String ruleString = "";
			if (changePercentage < -5) {
				ruleString = "5% Change Rule failed";
			} else if (low < (downptrendVal - (downptrendVal * 0.0020))){
				ruleString = "Min Level failed";
			}
			bean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime() + " @    LTP : " + lastPrice
					+ "  <br>  Change % : " + changePercentage + " @   MinLevel : "
					+ bean.getScaledVal(downptrendVal - (downptrendVal * 0.0020)) + "<BR>" + ruleString);
			//bean.setTradeState(Constants.SELL+Constants.FAIL);
			NSEIndiaTool.getInstance().getPositionalFutureMap().put(symbol, bean);
			positionalFutureDataMap.put(symbol, bean);
			continue;
		}

						if (null != bean.getTopLow() && bean.getPositionalDowntrendValue()
								.doubleValue() < bean.getTopLow().doubleValue()) {
							// if (tradableRule) {
							bean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime()
									+ Constants.AT_RATE_OF + " LTP : " + lastPrice+ Constants.AT_RATE_OF + Constants.FRESH_CALL);
							/*
							 * TradewareTraderUtil.getInstance().placeOptionOrder(tradeOption, Constants.BUY,
							 * optionBean, kiteConnectTool.getKiteQuotes(symbolsArray));
							 */
							// }
						} else {
							additionalCheck = ((quotesMap.get(bean.getKiteFutureKey()).buyQuantity)
									* Constants.ADDITONAL_CHECK) < (quotesMap
											.get(bean.getKiteFutureKey()).sellQuantity);
							// if (additionalCheck) {
							bean.setTradableVolumeRule(additionalCheck);
							/*
							 * TradewareTraderUtil.getInstance().placeOptionOrder(tradeOption, Constants.BUY,
							 * optionBean, kiteConnectTool.getKiteQuotes(symbolsArray));
							 */
							bean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime()
									+ Constants.AT_RATE_OF /* + optionBean.getSignalTriggeredInAt() + Constants.BREAK */
									+" LTP : " + lastPrice+ Constants.AT_RATE_OF + Constants.CALL_ALREADY_PLACED_VALID);
							// }
						}
						bean = updateOptionChainRule(bean);
						NSEIndiaTool.getInstance().getPositionalFutureMap().put(symbol, bean);
						positionalFutureDataMap.put(symbol, bean);
					}
				}
			} else {
				/*if ((Constants.OPTION_BUY.equals(optionBean.getTradeState())
						|| Constants.OPTION_SELL.equals(optionBean.getTradeState()))) {
					monitorPlacedCall.put(optionBean.getKiteOptionKey(), optionBean);
				}*/
				monitprPlacedCalls(bean);
			}
		}
		/*if (!monitorPlacedCall.isEmpty()) {
			String[] symbolsArrayOption = monitorPlacedCall.keySet().toArray(new String[0]);
			Map<String, Quote> quotesMapOption = kiteConnectTool.getKiteQuotes(symbolsArrayOption);
			TradewareTraderUtil.getInstance().monitorAndChaseProfitLossOptions(monitorPlacedCall, quotesMapOption);
		}*/
	}

	/*
	 * @Override public void run() { while
	 * (TradewareTraderUtil.getInstance().isTradingTime()) { Collection<StockDataBean>
	 * list = NSEIndiaTool.getInstance().getPositionalFutureMap().values(); if (null
	 * != list && !list.isEmpty()) { Map<String, OHLCQuote> quotesMap =
	 * kiteConnectTool
	 * .getKiteOHLCQuotes(NSEIndiaTool.getInstance().getPositionalFutureMap().keySet
	 * ()); for (StockDataBean bean : list) {
	 * bean.setLtp(bean.getScaledValueObj(quotesMap.get(bean.getKiteFutureKey()).
	 * lastPrice)); monitprPlacedCalls(bean); } } System.out.
	 * println("BuySellFtureScannerThead>>monitprPlacedCalls processing ..." +
	 * list.size()); } }
	 */

	private void monitprPlacedCalls(StockDataBean bean) {
		// if (Constants.NA.equals(bean.getTradeState())) {
		// if (Constants.NA.equals(bean.getTradeState()) && null ==
		// bean.getProfitLossStatus()) {
		if (Constants.BUY.equals(bean.getTradeState())) {
			double targetVal = Constants.TARGET_2000;// Target Profit
			double targetPrice = bean.getScaledVal(targetVal / bean.getLotSize());

			// book loss at early if stock moves in negative direction after placing the
			// call.
			double lastPrice = bean.getBuyerAt().doubleValue();//bean.getLtp().doubleValue();
			if ((lastPrice >= (bean.getTradedValAsDouble() + targetPrice))
					|| (Constants.ZERO_DOUBLE != bean.getPreviousLtpAsDouble())) {
				if ((Constants.ZERO_DOUBLE != bean.getPreviousLtpAsDouble())
						&& bean.getPreviousLtpAsDouble() >= lastPrice) {
					// Do nothing, so that proft loss monitor continues.
				} else {
					bean.setPreviousLtp(bean.getBuyerAt());
					return;// continue;
				}

				bean.setSignalTriggeredOutAtClear(TradewareTraderUtil.getInstance().getCurrentTime(),
						Constants.LINE_BREAK, bean.getBuyerAt().toString());
				bean.setProfitLossStatus(Constants.PROFIT);
				bean.setProfitLossAmt(bean.getScaledValueObj(
						(bean.getLotSize() * lastPrice) - (bean.getLotSize() * bean.getTradedValAsDouble())));

				// cancelCoverOrder(bean.getKiteOrderId());
				// nonBuySellSymbols.add(symbol);
				bean.setTradeState(bean.getTradeState() + "EXIT");
				sendTelegramMessage(bean);
			} else if ((lastPrice < (bean.getTradedValAsDouble()))
					&& (((bean.getLotSize() * bean.getTradedValAsDouble())
							- (bean.getLotSize() * lastPrice)) >= targetVal/.8)) {

				bean.setSignalTriggeredOutAtClear(TradewareTraderUtil.getInstance().getCurrentTime(),
						Constants.LINE_BREAK, bean.getBuyerAt().toString());
				bean.setProfitLossStatus(Constants.LOSS);
				bean.setProfitLossAmt(bean.getScaledValueObj(
						(bean.getLotSize() * lastPrice) - (bean.getLotSize() * bean.getTradedValAsDouble())));

				// cancelCoverOrder(bean.getKiteOrderId());
				// nonBuySellSymbols.add(symbol);
				bean.setTradeState(bean.getTradeState() + "EXIT");
				sendTelegramMessage(bean);
			}
			
			//temp start
			if (null!=  bean.getPreviousClose() && bean.getTradedVal().doubleValue() > bean.getPreviousClose().doubleValue()) {
				bean.setTempString(bean.getProfitLossAmt()+"<BR> P-Close-"+bean.getPreviousClose()+" - P-HIGH-"+bean.getPreviousHigh());
			}
			//temp end
		} else if (Constants.SELL.equals(bean.getTradeState())) {
			double targetVal = Constants.TARGET_2000;// Target Profit
			double targetPrice = bean.getScaledVal(targetVal / bean.getLotSize());

			// book loss at early if stock moves in negative direction after placing the
			// call.
			double lastPrice = bean.getSellerAt().doubleValue(); //bean.getLtp().doubleValue();
			if ((bean.getTradedValAsDouble() - targetPrice) >= lastPrice
					|| (Constants.ZERO_DOUBLE != bean.getPreviousLtpAsDouble())) {

				if (Constants.ZERO_DOUBLE != bean.getPreviousLtpAsDouble()
						&& bean.getPreviousLtpAsDouble() <= lastPrice) {
					// Do nothing, so that profit loss monitor continues.
				} else {
					bean.setPreviousLtp(bean.getSellerAt());
					return;// continue;
				}

				bean.setSignalTriggeredOutAtClear(TradewareTraderUtil.getInstance().getCurrentTime(),
						Constants.LINE_BREAK, bean.getSellerAt().toString());
				bean.setProfitLossStatus(Constants.PROFIT);
				bean.setProfitLossAmt(bean.getScaledValueObj(
						(bean.getLotSize() * bean.getTradedValAsDouble()) - (bean.getLotSize() * lastPrice)));
				// cancelCoverOrder(bean.getKiteOrderId());
				// nonBuySellSymbols.add(symbol);
				bean.setTradeState(bean.getTradeState() + "EXIT");
				sendTelegramMessage(bean);
			} else if ((lastPrice > (bean.getTradedValAsDouble())) && (((bean.getLotSize() * lastPrice)
					- (bean.getLotSize() * bean.getTradedValAsDouble())) >= targetVal)) {

				bean.setSignalTriggeredOutAtClear(TradewareTraderUtil.getInstance().getCurrentTime(),
						Constants.LINE_BREAK, bean.getSellerAt().toString());
				bean.setProfitLossStatus(Constants.LOSS);
				bean.setProfitLossAmt(bean.getScaledValueObj(
						(bean.getLotSize() * bean.getTradedValAsDouble()) - (bean.getLotSize() * lastPrice)));
				// cancelCoverOrder(bean.getKiteOrderId());
				// nonBuySellSymbols.add(symbol);
				bean.setTradeState(bean.getTradeState() + "EXIT");
				sendTelegramMessage(bean);
			}
			//temp start
			if (null!=  bean.getPreviousClose() && bean.getTradedVal().doubleValue() < bean.getPreviousClose().doubleValue()) {
				bean.setTempString(bean.getProfitLossAmt()+"<BR> P-Close-"+bean.getPreviousClose()+" - P-HIGH-"+bean.getPreviousHigh());
			}
			//temp end
		}

		// }
		NSEIndiaTool.getInstance().getPositionalFutureMap().put(bean.getStockName(), bean);
		positionalFutureDataMap.put(bean.getStockName(), bean);
	}
	
	
	public double getchangePercentage(double lastPrice, double close) {
		double change_percent = 0d;
		change_percent = (((lastPrice - close) * 100) / close);
		BigDecimal bd = new BigDecimal(Double.toString(change_percent));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		change_percent = bd.doubleValue();
		//System.out.println(change_percent);
		return change_percent;
	}
	
	private StockDataBean updateOptionChainRule(StockDataBean bean) {
		try {
			OptionChainDataBean beanOI = NSEIndiaTool.getInstance().getOptionChainDataMap().get(bean.getStockName());

			if (Constants.BUY.equals(bean.getTradeState())) {
				/*if (bean.getTradedVal().doubleValue() < beanOI.getTop1OpenInterestCall()
						&& beanOI.getTop1OpenInterestCall() > beanOI.getTop1OpenInterestPut()) {
					bean.setOptionChainRule(true);
					TelegramMessageTool.getInstance().sendTelegramMessage("MvcWebApp: "+bean.getSignalTriggeredInAt().trim());
				}*/
				if (beanOI.getOITrend().contains(Constants.BUY)) {
					bean.setOptionChainRule(true);
					
					TelegramMessageTool.getInstance().sendTelegramMessage("MvcWebApp: (futures) BUY "
							+ bean.getStockName() + Constants.AT_RATE_OF + bean.getSignalTriggeredInAt().trim() + Constants.AT_RATE_OF +beanOI.getOITrend());
				}

			} else if (Constants.SELL.equals(bean.getTradeState())) {
				/*if (bean.getTradedVal().doubleValue() > beanOI.getTop1OpenInterestPut()
						&& beanOI.getTop1OpenInterestCall() < beanOI.getTop1OpenInterestPut()) {
					bean.setOptionChainRule(true);
					TelegramMessageTool.getInstance().sendTelegramMessage("MvcWebApp: "+bean.getSignalTriggeredInAt().trim());
				}*/
				if (beanOI.getOITrend().contains(Constants.SELL)) {
					bean.setOptionChainRule(true);
					TelegramMessageTool.getInstance().sendTelegramMessage("MvcWebApp: (futures) SELL "
							+ bean.getStockName() + Constants.AT_RATE_OF + bean.getSignalTriggeredInAt().trim() + Constants.AT_RATE_OF +beanOI.getOITrend());
				}
			}
			bean.setSignalTriggeredInAt("OI Trend : "+beanOI.getOITrend());
			bean.setSignalTriggeredInAt("optionChainRule : ",bean.isOptionChainRule());
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	// temp
	private void sendTelegramMessage(StockDataBean bean) {
		if (bean.isOptionChainRule()) {
			TelegramMessageTool.getInstance()
					.sendTelegramMessage("MvcWebApp: (futures)  " + bean.getStockName() + Constants.AT_RATE_OF
							+ bean.getSignalTriggeredOutAt().trim() + Constants.AT_RATE_OF + bean.getProfitLossStatus()
							+ Constants.AT_RATE_OF + bean.getProfitLossAmt());
		}
	}
}
