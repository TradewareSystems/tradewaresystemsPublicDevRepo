package org.tradeware.stockmarket.scanner;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.tradeware.stockmarket.bean.OptionStockDataBean;
import org.tradeware.stockmarket.brokerengine.KiteConnectTool;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;
import org.tradeware.stockmarket.engine.tool.TradewareTraderUtil;
import org.tradeware.stockmarket.util.Constants;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.models.Quote;

public class BuyOrSellStocksScannerThread extends Thread {
	private KiteConnect kiteConnect = null;
	private KiteConnectTool kiteConnectTool = null;
	private Hashtable<String, OptionStockDataBean> optionsMap = null;
	private List<OptionStockDataBean> optionUpdatesToGoogleSheet = null;
	private Hashtable<String, OptionStockDataBean> monitorPlacedCall = null;

	public BuyOrSellStocksScannerThread(KiteConnect kiteConnect) {
		this.kiteConnect = kiteConnect;
		this.kiteConnectTool =  KiteConnectTool.getInstance(this.kiteConnect);
		prepareDate();
	}

	private void prepareDate() {
		optionsMap = new Hashtable<String, OptionStockDataBean>();
		optionUpdatesToGoogleSheet = new ArrayList<OptionStockDataBean>();
		monitorPlacedCall = new Hashtable<String, OptionStockDataBean>();
		for (String symbol : NSEIndiaTool.getInstance().getPositionalOptionsMap().keySet()) {
			OptionStockDataBean bean = NSEIndiaTool.getInstance().getPositionalOptionsMap().get(symbol);
			try {
				optionsMap.put(symbol, bean.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		super.run();

		while (TradewareTraderUtil.getInstance().isTradingTime()) {
			if (!optionsMap.isEmpty()) {
				// optionsBuySellThreadScanner();
				optionsBuySellThreadScanner();
			}
		}
	}

	private void optionsBuySellThreadScanner() {
		System.out.println("positionalOptionsMap scanner list count - " + optionsMap.size());
		Map<String, Quote> quotesMap = kiteConnectTool.getKiteQuotes(optionsMap.keySet());
		boolean tradableRule = false;

		for (String symbol : optionsMap.keySet()) {
			OptionStockDataBean optionBean = optionsMap.get(symbol);
			String futureKey = NSEIndiaTool.getInstance().getKiteFutureKeyMap().get(symbol);
			double lastPrice = quotesMap.get(futureKey).lastPrice;
			double uptrendVal = optionBean.getScaledVal(optionBean.getPositionalUptrendValue().doubleValue());
			double downptrendVal = optionBean.getScaledVal(optionBean.getPositionalDowntrendValue().doubleValue());

			if (Constants.NA.equals(optionBean.getTradeState())) {
				boolean tempCheck = false;
				if (lastPrice > uptrendVal && lastPrice < (uptrendVal + (uptrendVal * Constants.LIMIT_RANGE))) {
					String traderOption = TradewareTraderUtil.getInstance().getOptionTickerKey(symbol, lastPrice, Constants.OPTION_CALL);
					String[] symbolsArray = { traderOption };
					optionBean.setTradedVal(optionBean.getScaledValueObj(lastPrice));
					optionBean.setTradeState(Constants.FUTURE_BUY);

					tradableRule = ((quotesMap.get(futureKey).volumeTradedToday > Constants.ONE_LAC)
							&& ((quotesMap.get(futureKey).buyQuantity) > (quotesMap.get(futureKey).sellQuantity)));

					// call is placing first time
					if (null == optionBean.getTopHigh() || uptrendVal > optionBean.getTopHigh().doubleValue()) {
						if (tradableRule) {
							if (TradewareTraderUtil.getInstance().canOptionOrderPlaceNow()) {
								optionBean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime()
										+ Constants.LINE_BREAK + "Fresh call");

								TradewareTraderUtil.getInstance().placeOptionOrder(traderOption, Constants.BUY, optionBean,
										kiteConnectTool.getKiteQuotes(symbolsArray));
							} else {
								optionBean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime()
										+ "<BR> - @ " + traderOption + "<BR>"
										+ "FRESH call placed for this month. <BR> after 11:30 am now.");
							}
						} else {
							optionBean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime()
									+ "<BR> - @ " + traderOption + "<BR>"
									+ "FRESH placed for this month. <BR> Rule not matced, Not valid now.");
						}
					} else {
						tempCheck = (quotesMap.get(futureKey).buyQuantity) > ((quotesMap.get(futureKey).sellQuantity)
								* 1.5);
						if (TradewareTraderUtil.getInstance().canOptionOrderPlaceNow()) {
							if (tempCheck) {
								TradewareTraderUtil.getInstance().placeOptionOrder(traderOption, Constants.BUY, optionBean,
										kiteConnectTool.getKiteQuotes(symbolsArray));
								optionBean.setSignalTriggeredInAt(null != optionBean.getSignalTriggeredInAt()
										? optionBean.getSignalTriggeredInAt()
										: "" + " <BR>"
												+ "Call already placed for this month. <BR> Still valid as huge buyers are present.");
							} else {
								optionBean.setSignalTriggeredInAt(null != optionBean.getSignalTriggeredInAt()
										? optionBean.getSignalTriggeredInAt()
										: "" + " <BR>"
												+ "Call already placed for this month. <BR> NOT valid as huge buyers are NOT  present.");
							}
						} else {
							optionBean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime()
									+ " @ <BR>" + traderOption + "<BR> HUGE valume check" + tempCheck
									+ "<BR>Call triggered, But not yet placed. <BR> Wating in watchist, process after 3:15 PM");
						}

						tempCheck = (quotesMap.get(futureKey).buyQuantity) > ((quotesMap.get(futureKey).sellQuantity)
								* 1.5);
					}

				} else if (lastPrice < downptrendVal
						&& lastPrice > (uptrendVal - (uptrendVal * Constants.LIMIT_RANGE))) {

				}

			}
		}
	}
}
