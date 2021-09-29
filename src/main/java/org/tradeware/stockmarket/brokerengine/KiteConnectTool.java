package org.tradeware.stockmarket.brokerengine;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.tradeware.stockmarket.bean.OptionChainDataBean;
import org.tradeware.stockmarket.bean.OptionStockDataBean;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;
import org.tradeware.stockmarket.util.Constants;
import org.tradeware.stockmarket.util.StockUtil;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.LTPQuote;
import com.zerodhatech.models.OHLCQuote;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.OrderParams;
import com.zerodhatech.models.Quote;

public class KiteConnectTool {
	private KiteConnect kiteConnect = null;
	private static KiteConnectTool singleton = null; 
	
	private KiteConnectTool(KiteConnect kiteConnect) {
		this.kiteConnect = kiteConnect;
	}

	public static KiteConnectTool getInstance() {
		return singleton;
	}

	public static KiteConnectTool getInstance(KiteConnect kiteConnect) {
		if (null == singleton && null != kiteConnect && kiteConnect instanceof KiteConnect) {
			singleton = new KiteConnectTool(kiteConnect);
		}
		return singleton;
	}
	
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
			quotesMap = kiteConnect.getOHLC(symbolsArray);
		} catch (JSONException | IOException | KiteException e) {
			e.printStackTrace();
		}
		return quotesMap;
	}
	
	public Map<String, OHLCQuote> getKiteOHLCQuotes(String[] symbolsArray) {
		Map<String, OHLCQuote> quotesMap = null;
		try {
			quotesMap = kiteConnect.getOHLC(symbolsArray);
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
			quotesMap = kiteConnect.getLTP(symbolsArray);
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
			quotesMap = kiteConnect.getQuote(symbolsArray);
		} catch (JSONException | IOException | KiteException e) {
			e.printStackTrace();
		}
		return quotesMap;
	}

	public Map<String, Quote> getKiteQuotes(String[] symbolsArray) {
		Map<String, Quote> quotesMap = null;
		try {
			quotesMap = kiteConnect.getQuote(symbolsArray);
		} catch (JSONException | IOException | KiteException e) {
			e.printStackTrace();
		}
		return quotesMap;
	}

	public void placeOptionOrder(OptionStockDataBean bean) {
		try {
			OrderParams orderParams = new OrderParams();
			orderParams.tradingsymbol = bean.getKiteOptionKey().replace(Constants.EXCHANGE_FUTUE_NFO, Constants.EMPTY_STRING);
			orderParams.exchange = Constants.EXCHANGE_NFO;
			orderParams.transactionType = Constants.BUY; // transactionType;
			orderParams.orderType = Constants.LIMIT;// "BO";//"MARKET";
			orderParams.quantity = bean.getLotSize();
			orderParams.product = Constants.NRML;
			orderParams.validity = Constants.DAY;
			orderParams.price = bean.getScaledValue(bean.getSellerAt());

			Order order = null;
			for (String kiteConnectId : StockUtil.kiteConnectObjectsForAdmins.keySet()) {
				//order = StockUtil.kiteConnectObjectsForAdmins.get(kiteConnectId).placeOrder(orderParams, Constants.REGULAR);
				//bean.getUserIdOrderIdMap().put(kiteConnectId, order.orderId);
			}
			/*if (((sellerAt - buyerAt) * lotSize) <= 500) {
				Order order = kiteConnect.placeOrder(orderParams, Constants.REGULAR);
				userOrders.put(StockUtil.DEFAULT_USER, order.orderId);
			}*/
		} catch (Exception e) { //} catch (IOException | KiteException | JSONException e) {
			e.printStackTrace();
		}
	}
	
	//private Order cancelOptionOrder(String orderId, KiteConnect kite, Order order, double buyerAt, String tradingSymbol, Integer lotSize) {
	public Order cancelOptionOrder(OptionStockDataBean bean) {
		Order order = null;
		try { 
				
				OrderParams orderParams = new OrderParams();
				orderParams.tradingsymbol = bean.getKiteOptionKey().replace(Constants.EXCHANGE_FUTUE_NFO, Constants.EMPTY_STRING);
				orderParams.exchange = Constants.EXCHANGE_NFO;
				orderParams.transactionType = Constants.SELL; // transactionType;
				orderParams.orderType = Constants.LIMIT;// "BO";//"MARKET";
				orderParams.quantity = bean.getLotSize();
				orderParams.product = Constants.NRML;
				orderParams.validity = Constants.DAY;
				orderParams.price = bean.getScaledValue(bean.getBuyerAt2());
				
				for (String kiteConnectId : StockUtil.kiteConnectObjectsForAdmins.keySet()) {
					//order = StockUtil.kiteConnectObjectsForAdmins.get(kiteConnectId).placeOrder(orderParams, Constants.REGULAR);
					//bean.getUserIdOrderIdMap().put(kiteConnectId, order.orderId);
				}
		} catch (Exception e) { //(JSONException | IOException | KiteException e) {
			System.out.println("cancelOptionOrder >>>>>>>>>>>>>>"+e.getMessage());
		}
		return order;
	}
	
	private Order placeCoverOrder(String symbol, String transactionType, Double squareoff) {
		OrderParams orderParams = new OrderParams();
		orderParams.orderType = Constants.MARKET;// "BO";//"MARKET";
		orderParams.product = Constants.MIS;
		orderParams.validity = Constants.DAY;
		orderParams.tradingsymbol = NSEIndiaTool.getInstance().getKiteFutureKeyMap().get(symbol).replace("NFO:", "");
		orderParams.exchange = Constants.EXCHANGE_NFO;
		orderParams.transactionType = transactionType;
		orderParams.quantity = StockUtil.getSymbols().get(symbol);
		orderParams.triggerPrice = squareoff;
		Order order = null;
		/*try {
			if (NSEIndiaTool.getInstance().canOrderPlaceNow() && orderParams.quantity <= 2000) {
				order = kiteConnectAdmin.placeOrder(orderParams, "co"); // "regular");//"bo");
				for (String kiteConnectId : StockUtil.kiteConnectObjectsForAdmins.keySet()) {
					order = StockUtil.kiteConnectObjectsForAdmins.get(kiteConnectId).placeOrder(orderParams, Constants.REGULAR);
					//bean.getUserIdOrderIdMap().put(kiteConnectId, order.orderId);
				}
			}
		} catch (JSONException | IOException | KiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return order;
	}
	
	private Order cancelCoverOrder(String orderId) {
		Order order = null;
		/*try {
			if (null != orderId) {
				order = kiteConnectAdmin.cancelOrder(orderId, "co");
			}
		} catch (JSONException | IOException | KiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return order;
	}
	
	
	 public Hashtable<String, OptionChainDataBean> updateValues() {
		 // Hashtable<String, OptionChainDataBean> optionChainDataMapPlayable 
		 return null;
	 }
}
