package org.tradeware.stockmarket.scanner;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.tradeware.stockmarket.bean.Narrow7StockDataBean;
import org.tradeware.stockmarket.brokerengine.KiteConnectTool;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;
import org.tradeware.stockmarket.engine.tool.TradewareTraderUtil;
import org.tradeware.stockmarket.util.Constants;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.models.OHLCQuote;

public class Narrow7StockScannerThread implements Runnable {

	// Start -Temp code to consider it as normal bject rather than a Thread
	private static Narrow7StockScannerThread singletonInstance = null;

	private Narrow7StockScannerThread() {
	}

	public static Narrow7StockScannerThread getThreadInstance() {
		if (null == singletonInstance) {
			singletonInstance = new Narrow7StockScannerThread();
		}
		return singletonInstance;
	}

	public void runDummy() {
		if (null != narrow7MapToPlaceCalls && !narrow7MapToPlaceCalls.isEmpty()) {
			updateOHLCOnNR7Map();
			narrowBuySellThreadScanner();
			profitLossMonitrForNR7();
		}
	}
	// End -Temp code to consider it as normal bject rather than a Thread

	private KiteConnect kiteConnect = null;
	private KiteConnectTool kiteConnectTool = null;
	private ConcurrentHashMap<String, Narrow7StockDataBean> narrow7MapToPlaceCalls = null;

	public Narrow7StockScannerThread(KiteConnect kiteConnect) {
		this.kiteConnect = kiteConnect;
		this.kiteConnectTool = KiteConnectTool.getInstance(this.kiteConnect);
		singletonInstance = this;// TODO : temp
		narrow7MapToPlaceCalls = NSEIndiaTool.getInstance().getNarrow7Map();
	}

	@Override
	public void run() {
		while (TradewareTraderUtil.getInstance().isTradingTime()) {
			if (!narrow7MapToPlaceCalls.isEmpty()) {
				updateOHLCOnNR7Map();
				narrowBuySellThreadScanner();
				profitLossMonitrForNR7();
			}
		}
	}

	private void updateOHLCOnNR7Map() {
		narrow7MapToPlaceCalls.clear();
		Map<String, OHLCQuote> quotesMap = kiteConnectTool
				.getKiteOHLCQuotes(NSEIndiaTool.getInstance().getNarrow7Map().keySet());
		for (String symbol : narrow7MapToPlaceCalls.keySet()) {
			Narrow7StockDataBean narrow7Bean = NSEIndiaTool.getInstance().getNarrow7Map().get(symbol);
			narrow7Bean.setLtp(narrow7Bean.getScaledValueObj(quotesMap.get(narrow7Bean.getKiteFutureKey()).lastPrice));
			narrow7Bean.setOpen(narrow7Bean.getScaledValueObj(quotesMap.get(narrow7Bean.getKiteFutureKey()).ohlc.open));
			narrow7Bean.setHigh(narrow7Bean.getScaledValueObj(quotesMap.get(narrow7Bean.getKiteFutureKey()).ohlc.high));
			narrow7Bean.setLow(narrow7Bean.getScaledValueObj(quotesMap.get(narrow7Bean.getKiteFutureKey()).ohlc.low));
			narrow7MapToPlaceCalls.put(symbol, narrow7Bean);
		}
	}

	private void narrowBuySellThreadScanner() {
		System.out.println("Narrow 7 scanner list count - " + narrow7MapToPlaceCalls.size());

		for (String symbol : narrow7MapToPlaceCalls.keySet()) {
			Narrow7StockDataBean narrow7Bean = narrow7MapToPlaceCalls.get(symbol);
			if (Constants.NA.equals(narrow7Bean.getBuyStatus()) || Constants.NA.equals(narrow7Bean.getSellStatus())) {
				double open = narrow7Bean.getScaledValue(narrow7Bean.getOpen());
				double lastPrice = narrow7Bean.getScaledValue(narrow7Bean.getLtp());

				if (0 == open || 0 == lastPrice) {
					NSEIndiaTool.getInstance().getNarrow7Map().put(symbol, narrow7Bean);
					continue;
				} else if (narrow7Bean.getScaledValue(narrow7Bean.getOpen()) > narrow7Bean.getBuyValue()) {
					narrow7Bean.setBuyStatus(Constants.DISQUALIFIED_GAP_UP);
				} else if (narrow7Bean.getScaledValue(narrow7Bean.getOpen()) < narrow7Bean.getSellValue()) {
					narrow7Bean.setSellStatus(Constants.DISQUALIFIED_GAP_DOWN);
				}
				if (Constants.NA.equals(narrow7Bean.getBuyStatus()) && lastPrice > narrow7Bean.getBuyValue()) {
					narrow7Bean.setBuyStatus(Constants.ACTIVATED);
					narrow7Bean.setSignalTriggeredInAt(Constants.BUY, Constants.SPACE, Constants.ACTIVATED,
							Constants.AT_RATE_OF, TradewareTraderUtil.getInstance().getCurrentTime());
					// place BUY order
					narrow7Bean.setTradedVal(narrow7Bean.getLtp());
					narrow7Bean.setTradedAt(Calendar.getInstance().getTime());
				} else if (Constants.NA.equals(narrow7Bean.getSellStatus()) && lastPrice < narrow7Bean.getSellValue()) {
					narrow7Bean.setSellStatus(Constants.ACTIVATED);
					narrow7Bean.setSignalTriggeredInAt(Constants.SELL, Constants.SPACE, Constants.ACTIVATED,
							Constants.AT_RATE_OF, TradewareTraderUtil.getInstance().getCurrentTime());
					// place SELL order
					narrow7Bean.setTradedVal(narrow7Bean.getLtp());
					narrow7Bean.setTradedAt(Calendar.getInstance().getTime());
				}
			}
			NSEIndiaTool.getInstance().getNarrow7Map().replace(symbol, narrow7Bean);
		}
	}

	public void profitLossMonitrForNR7() {
		System.out.println("Narrow 7 scanner Profit loss monitor list count - " + narrow7MapToPlaceCalls.size());
		for (String symbol : narrow7MapToPlaceCalls.keySet()) {
			Narrow7StockDataBean narrow7Bean = narrow7MapToPlaceCalls.get(symbol);
			double lastPrice = narrow7Bean.getLtp().doubleValue();

			if (Constants.ACTIVATED.equals(narrow7Bean.getBuyStatus())) {
				if (lastPrice > narrow7Bean.getBuyValueTarget()) {
					narrow7Bean.setBuyStatus(Constants.TARGET_DONE);
					narrow7Bean.setProfitLossAmt(
							narrow7Bean.getScaledValueObj((narrow7Bean.getScaledValue(narrow7Bean.getProfitLossAmt()))
									+ ((lastPrice - narrow7Bean.getBuyValue()) * narrow7Bean.getLotSize())));
					narrow7Bean.setSignalTriggeredOutAt(TradewareTraderUtil.getInstance().getCurrentTime(),
							Constants.BREAK, Constants.BUY, Constants.SPACE, Constants.TARGET_DONE,
							Constants.AT_RATE_OF, lastPrice, Constants.BREAK, Constants.FINAL_PROFIT,
							narrow7Bean.getScaledVal(
									((lastPrice - narrow7Bean.getBuyValue()) * narrow7Bean.getLotSize())));
				} else if (lastPrice < narrow7Bean.getSellValue()) {
					narrow7Bean.setBuyStatus(Constants.TARGET_CLOSE_LOSS);
					narrow7Bean.setProfitLossAmt(
							narrow7Bean.getScaledValueObj((narrow7Bean.getScaledValue(narrow7Bean.getProfitLossAmt()))
									+ ((lastPrice - narrow7Bean.getBuyValue()) * narrow7Bean.getLotSize())));
					narrow7Bean.setSignalTriggeredOutAt(TradewareTraderUtil.getInstance().getCurrentTime(),
							Constants.BREAK, Constants.CLOSED_LOSS, lastPrice, Constants.BREAK, Constants.FINAL_LOSS,
							narrow7Bean.getScaledVal(
									((lastPrice - narrow7Bean.getBuyValue()) * narrow7Bean.getLotSize())));
				}
			} else if (Constants.ACTIVATED.equals(narrow7Bean.getSellStatus())) {
				if (lastPrice < narrow7Bean.getSellValueTarget()) {
					narrow7Bean.setSellStatus(Constants.TARGET_DONE);
					narrow7Bean.setProfitLossAmt(
							narrow7Bean.getScaledValueObj((narrow7Bean.getScaledValue(narrow7Bean.getProfitLossAmt()))
									+ ((narrow7Bean.getSellValue() - lastPrice) * narrow7Bean.getLotSize())));
					narrow7Bean.setSignalTriggeredOutAt(TradewareTraderUtil.getInstance().getCurrentTime(),
							Constants.BREAK, Constants.SELL, Constants.SPACE, Constants.TARGET_DONE,
							Constants.AT_RATE_OF, lastPrice, Constants.BREAK, Constants.FINAL_PROFIT,
							narrow7Bean.getScaledVal(
									((narrow7Bean.getSellValue() - lastPrice) * narrow7Bean.getLotSize())));
				} else if (lastPrice > narrow7Bean.getBuyValue()) {
					narrow7Bean.setBuyStatus(Constants.TARGET_CLOSE_LOSS);
					narrow7Bean.setProfitLossAmt(
							narrow7Bean.getScaledValueObj((narrow7Bean.getScaledValue(narrow7Bean.getProfitLossAmt()))
									+ ((narrow7Bean.getSellValue() - lastPrice) * narrow7Bean.getLotSize())));

					narrow7Bean.setSignalTriggeredOutAt(TradewareTraderUtil.getInstance().getCurrentTime(),
							Constants.BREAK, Constants.CLOSED_LOSS, lastPrice, Constants.BREAK, Constants.FINAL_LOSS,
							narrow7Bean.getScaledVal(
									((narrow7Bean.getSellValue() - lastPrice) * narrow7Bean.getLotSize())));
				}
			}
			NSEIndiaTool.getInstance().getNarrow7Map().replace(symbol, narrow7Bean);
		}
	}

}
