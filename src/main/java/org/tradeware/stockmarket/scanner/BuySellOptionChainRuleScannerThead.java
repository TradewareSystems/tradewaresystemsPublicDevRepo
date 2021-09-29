package org.tradeware.stockmarket.scanner;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.tradeware.stockmarket.bean.OptionChainDataBean;
import org.tradeware.stockmarket.brokerengine.KiteConnectTool;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;
import org.tradeware.stockmarket.engine.telegramtool.TelegramMessageTool;
import org.tradeware.stockmarket.engine.tool.TradewareTraderUtil;
import org.tradeware.stockmarket.util.StockUtil;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.models.OHLCQuote;

public class BuySellOptionChainRuleScannerThead implements Runnable {
	private KiteConnectTool kiteConnectTool = null;
	private Hashtable<String, OptionChainDataBean> optionChainDataMap = null;
	private Hashtable<String, OptionChainDataBean> monitorPlacedCall = null;

	public BuySellOptionChainRuleScannerThead(KiteConnect kiteConnect) {
		this.kiteConnectTool = KiteConnectTool.getInstance(kiteConnect);
		prepareDate();
	}

	private void prepareDate() {
		optionChainDataMap = new Hashtable<String, OptionChainDataBean>();
		monitorPlacedCall = new Hashtable<String, OptionChainDataBean>();

		Set<String> list = NSEIndiaTool.getInstance().getOptionChainDataMap().keySet();
		for (String symbol : list) {
			try {
				OptionChainDataBean bean = NSEIndiaTool.getInstance().getOptionChainDataMap().get(symbol);
				if (bean.getOITrend().contains("SELL") || bean.getOITrend().contains("BUY")) {
					OptionChainDataBean ocBean = new OptionChainDataBean(symbol);
					ocBean.setTop1StrikePriceCall(bean.getTop1StrikePriceCall());
					ocBean.setTop1StrikePricePut(bean.getTop1StrikePricePut());
					ocBean.setOITrend(ocBean.getOITrend());
					optionChainDataMap.put(symbol, ocBean);
				}
			} catch (/* CloneNotSupported */Exception e) {
				System.out.println("BuySellOptionChainRuleScannerThead>prepareDate : " + e.getMessage());
			}
		}
	}

	@Override
	public void run() {
		while (TradewareTraderUtil.getInstance().isTradingTime()) {
		if (!optionChainDataMap.isEmpty()) {
				futureTradeOnOpenInterestThreadScanner();
			}
			// TelegramMessageTool.getInstance().setLastRuntime(TradewareTraderUtil.getInstance().getCurrentTime());
		}
	}

	private void futureTradeOnOpenInterestThreadScanner() {
		System.out.println("FUTURES>> BuySellOptionChainRuleScannerThead > optionChainDataMap scanner list count - "
				+ optionChainDataMap.size());
		Map<String, OHLCQuote> quotesMap = kiteConnectTool.getKiteOHLCQuotes(optionChainDataMap.keySet());
		monitorPlacedCall.clear();

		for (String symbol : optionChainDataMap.keySet()) {
			OptionChainDataBean bean = optionChainDataMap.get(symbol);
			double open = quotesMap.get(NSEIndiaTool.getInstance().getKiteFutureKeyMap().get(symbol)).ohlc.open;
			// double lastPrice =
			// quotesMap.get(NSEIndiaTool.getInstance().getKiteFutureKeyMap().get(symbol)).lastPrice;
			double high = quotesMap.get(NSEIndiaTool.getInstance().getKiteFutureKeyMap().get(symbol)).ohlc.high;
			;
			double low = quotesMap.get(NSEIndiaTool.getInstance().getKiteFutureKeyMap().get(symbol)).ohlc.low;
			;
			if (bean.getOITrend().contains("BUY")) {
				if (open < bean.getTop1StrikePriceCall()) {
					if (high > bean.getTop1StrikePriceCall()) {
						TelegramMessageTool.getInstance().sendTelegramMessage(
								"Open Interest SELL Activated on " + symbol + " " + bean.getTop1StrikePriceCall()+ " " + bean.getOITrend());
						optionChainDataMap.remove(symbol);
					}
				} else if (open < (bean.getTop1StrikePriceCall() + (bean.getTop1StrikePriceCall() * 0.01))) {
					TelegramMessageTool.getInstance().sendTelegramMessage("Open Interest SELL Activated WITH GAP UP on "
							+ symbol + " " + bean.getTop1StrikePriceCall()+ " " + bean.getOITrend());
					optionChainDataMap.remove(symbol);
				} else {
					optionChainDataMap.remove(symbol);
				}
			} else if (bean.getOITrend().contains("SELL")) {
				if (open > bean.getTop1StrikePricePut()) {
					if (low < bean.getTop1StrikePricePut()) {
						TelegramMessageTool.getInstance().sendTelegramMessage(
								"Open Interest BUY Activated on " + symbol + " " + bean.getTop1StrikePricePut()+ " " + bean.getOITrend());
						optionChainDataMap.remove(symbol);
					}
				} else if (open > (bean.getTop1StrikePricePut() + (bean.getTop1StrikePricePut() * 0.02))) {
					TelegramMessageTool.getInstance().sendTelegramMessage("Open Interest SELL Activated WITH GAP UP on "
							+ symbol + " " + bean.getTop1StrikePricePut()+ " " + bean.getOITrend());
					optionChainDataMap.remove(symbol);
				} else {
					optionChainDataMap.remove(symbol);
				}
			
			}
		}
	}
	
	//temp
	private static BuySellOptionChainRuleScannerThead singletonTool;
	public static BuySellOptionChainRuleScannerThead getInstance() {
			if (null == singletonTool) {
				if (null !=StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER)) {
					singletonTool = new BuySellOptionChainRuleScannerThead(StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER));
				} else {
					//singletonTool = new BuySellOptionChainRuleScannerThead(KiteConnectTool.getInstance());
				}
				
			}
		return singletonTool;
	}
}
