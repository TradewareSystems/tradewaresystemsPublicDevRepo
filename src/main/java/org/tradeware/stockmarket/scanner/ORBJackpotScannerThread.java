package org.tradeware.stockmarket.scanner;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.tradeware.stockmarket.bean.ORBStockDataBean;
import org.tradeware.stockmarket.bean.StockDataBean;
import org.tradeware.stockmarket.brokerengine.KiteConnectTool;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.models.OHLC;
import com.zerodhatech.models.OHLCQuote;

public class ORBJackpotScannerThread implements Runnable {// extends Thread {
	private KiteConnect kiteConnect = null;
	//private KiteConnectTool kiteConnectTool = null;
	private ConcurrentHashMap<String, ORBStockDataBean> orbJackpotMap = null;
	public ORBJackpotScannerThread(KiteConnect kiteConnect) {
		this.kiteConnect = kiteConnect;
		//this.kiteConnectTool = KiteConnectTool.getInstance(this.kiteConnect);
		try {
			prepareDate();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void prepareDate() throws CloneNotSupportedException {
		@SuppressWarnings("unchecked")
		Hashtable<String, StockDataBean> dataMap = (Hashtable<String, StockDataBean>) NSEIndiaTool.getInstance()
				.getLast5DaysResultDataMap().clone();
		Map<String, OHLCQuote> quotesMap = KiteConnectTool.getInstance().getKiteOHLCQuotes(dataMap.keySet());

		for (String symbol : dataMap.keySet()) {
			StockDataBean bean = dataMap.get(symbol);
			OHLC ohlc = quotesMap.get(bean.getKiteFutureKey()).ohlc;
			double open = ohlc.open;
			double high = ohlc.high;
			double low = ohlc.low;
			if (open == low || open == high) {
				NSEIndiaTool.getInstance().getOrbJackPotMap().put(symbol, (ORBStockDataBean) bean.clone());
			}
		}
		orbJackpotMap = NSEIndiaTool.getInstance().getOrbJackPotMap();
	}

	@Override
	public void run() {
		System.out.println("<<<<<<<<<<< ORB JACKPOT SCANNER LIST COUNT >>>>>>>>>>>>>- " + orbJackpotMap.size());
		if (!orbJackpotMap.isEmpty()) {
			Object[] symbolArr = orbJackpotMap.keySet().toArray();
			String[] symbolsArray = new String[symbolArr.length];
			for (int i = 0; i < symbolArr.length; i++) {
				symbolsArray[i] = ((ORBStockDataBean)symbolArr[i]).getKiteFutureKey();
			}
			Map<String, OHLCQuote> quotesMap = KiteConnectTool.getInstance().getKiteOHLCQuotes((symbolsArray));
			for (String symbol : orbJackpotMap.keySet()) {
				ORBStockDataBean bean = orbJackpotMap.get(symbol);
				
				double lastPrice = quotesMap.get(bean.getKiteFutureKey()).lastPrice;
				double open = quotesMap.get(bean.getKiteFutureKey()).ohlc.open;
				double high = quotesMap.get(bean.getKiteFutureKey()).ohlc.high;
				double low = quotesMap.get(bean.getKiteFutureKey()).ohlc.low;
				
				if("NA".equals(bean.getTradeState())) {
					
				}
			}
		}
	}

}
