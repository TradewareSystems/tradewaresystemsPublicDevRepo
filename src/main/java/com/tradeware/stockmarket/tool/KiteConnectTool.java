package com.tradeware.stockmarket.tool;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.tradeware.stockmarket.bean.KiteLiveOHLCDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.bean.UserDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.Constants2;
import com.tradeware.stockmarket.util.DatabaseHelper;
import com.tradeware.stockmarket.util.StockUtil;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.SessionExpiryHook;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.InputException;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.NetworkException;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.TokenException;
import com.zerodhatech.models.Depth;
import com.zerodhatech.models.HistoricalData;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.OrderParams;
import com.zerodhatech.models.Quote;
import com.zerodhatech.models.User;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class KiteConnectTool /* extends AbstractTechTraderTool */  extends AbstractKiteConnectTool {

	public HttpHeaders kiteConnectLogin(UserDataBean user) {
		KiteConnect kiteConnect = new KiteConnect(user.getApiKey());
		kiteConnect.setUserId(user.getUserId());
		String kiteConnectURL = kiteConnect.getLoginURL();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(URI.create(kiteConnectURL));
		return header;
	}

	public UserDataBean kiteConnectRedirectAfterLogin(UserDataBean userDataBean) {
		try {
			KiteConnect kiteConnectSession = new KiteConnect(userDataBean.getApiKey());
			kiteConnectSession.setUserId(userDataBean.getUserId());

			kiteConnectSession.setSessionExpiryHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					// TradewareLogger.sysoutPrintlnForLocalTest("session expired");
				}
			});

			User kiteConnectUser = kiteConnectSession.generateSession(userDataBean.getRequestToken(),
					userDataBean.getSecretKey());// "your_apiSecret");
			kiteConnectSession.setAccessToken(kiteConnectUser.accessToken);
			kiteConnectSession.setPublicToken(kiteConnectUser.publicToken);

			userDataBean.setAccessToken(kiteConnectUser.accessToken);
			userDataBean.setPublicToken(kiteConnectUser.publicToken);
			// TODO
			StockUtil.kiteConnectAdmins.put(userDataBean.getUserId(), kiteConnectSession);
			
			StockUtil.kiteConnectAdminsHist.put(userDataBean.getUserId(), kiteConnectSession);
			DatabaseHelper.getInstance().activityUserLogon();

		} catch (JSONException | IOException | KiteException e) {
			TradewareLogger.saveFatalErrorLog(Constants2.CLASS_KITE_CONNECT_TOOL,
					Constants2.METHOD_KITE_CONNECT_REDIRECT_AFTER_LOGIN, e, Constants2.ERROR_TYPE_KITE_EXCEPTION);
		}
		return userDataBean;
	}

	public HttpHeaders kiteConnectLoginHist(UserDataBean user) {
		KiteConnect kiteConnect = new KiteConnect(user.getHistApiKey());
		kiteConnect.setUserId(user.getUserId());
		String kiteConnectURL = kiteConnect.getLoginURL();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(URI.create(kiteConnectURL));
		return header;
	}

	public UserDataBean kiteConnectRedirectHistAfterLogin(UserDataBean userDataBean) {
		try {
			KiteConnect kiteConnectSession = new KiteConnect(userDataBean.getHistApiKey());
			kiteConnectSession.setUserId(userDataBean.getUserId());

			kiteConnectSession.setSessionExpiryHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					// TradewareLogger.sysoutPrintlnForLocalTest("session expired");
				}
			});

			User kiteConnectUser = kiteConnectSession.generateSession(userDataBean.getRequestToken(),
					userDataBean.getHistSecretKey());// "your_apiSecret");
			kiteConnectSession.setAccessToken(kiteConnectUser.accessToken);
			kiteConnectSession.setPublicToken(kiteConnectUser.publicToken);

			userDataBean.setAccessToken(kiteConnectUser.accessToken);
			userDataBean.setPublicToken(kiteConnectUser.publicToken);
			// TODO
			StockUtil.kiteConnectAdminsHist.put(userDataBean.getUserId(), kiteConnectSession);

		} catch (JSONException | IOException | KiteException e) {
			TradewareLogger.saveFatalErrorLog(Constants2.CLASS_KITE_CONNECT_TOOL,
					Constants2.METHOD_KITE_CONNECT_REDIRECT_HIST_AFTER_LOGIN, e, Constants2.ERROR_TYPE_KITE_EXCEPTION);
		}
		return userDataBean;
	}

	public UserDataBean kiteConnectCreateSession(UserDataBean userDataBean) {
		try {
			KiteConnect kiteConnect = new KiteConnect(StockUtil.getAdmins().get(userDataBean.getUserId()));//get it frm DB
			kiteConnect.setUserId(userDataBean.getUserId());
			kiteConnect.setAccessToken(userDataBean.getAccessToken());

			kiteConnect.setSessionExpiryHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					// TradewareLogger.sysoutPrintlnForLocalTest("session expired");
				}
			});

			// TODO
			StockUtil.kiteConnectAdmins.put(userDataBean.getUserId(), kiteConnect);
			TradewareLogger.sysoutPrintlnForLocalTest(kiteConnect.getAccessToken()+"  ->>kiteConnect created for userId = " + userDataBean.getUserId());
			StockUtil.kiteConnectAdminsHist.put(userDataBean.getUserId(), kiteConnect);
			//DatabaseHelper.getInstance().activityUserLogon();
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants2.CLASS_KITE_CONNECT_TOOL,
					Constants2.METHOD_KITE_CONNECT_CREATE_SESSION, e, Constants2.ERROR_TYPE_KITE_EXCEPTION);
		}
		return userDataBean;
	}

	public UserDataBean kiteConnectCreateSessionHist(UserDataBean userDataBean) {
		try {
			KiteConnect kiteConnect = new KiteConnect(userDataBean.getHistApiKey());
			kiteConnect.setUserId(userDataBean.getUserId());
			kiteConnect.setAccessToken(userDataBean.getHistAccessToken());

			kiteConnect.setSessionExpiryHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					// TradewareLogger.sysoutPrintlnForLocalTest("session expired");
				}
			});

			// TODO
			StockUtil.kiteConnectAdminsHist.put(userDataBean.getUserId(), kiteConnect);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants2.CLASS_KITE_CONNECT_TOOL,
					Constants2.METHOD_KITE_CONNECT_CREATE_SESSION_HIST, e, Constants2.ERROR_TYPE_KITE_EXCEPTION);
		}
		return userDataBean;
	}
	
	
	
	
	
	
	
	
	//Phase 2 start
	private static double avgBuyerAt=0;
	private static double avgSellerAt=0;
	private static List<Depth> depthListBuyer = null;
	private static List<Depth> depthListSeller = null;

	private void updateAdditionals(StrategyOrbDataBean bean, Quote ohlcQuote) {
		bean.setBuyerSellerDiffVal2(getRoundupToTwoValue(
				(ohlcQuote.depth.sell.get(1).getPrice() - ohlcQuote.depth.buy.get(1).getPrice()) * bean.getLotSize()));
		bean.setBuyerSellerDiffVal3(getRoundupToTwoValue(
				(ohlcQuote.depth.sell.get(2).getPrice() - ohlcQuote.depth.buy.get(2).getPrice()) * bean.getLotSize()));

		bean.setBuyerQuantityVal(getRoundupToTwoValue(ohlcQuote.depth.buy.get(0).getQuantity()
				+ ohlcQuote.depth.buy.get(1).getQuantity() + ohlcQuote.depth.buy.get(2).getQuantity()
				+ ohlcQuote.depth.buy.get(3).getQuantity() + ohlcQuote.depth.buy.get(4).getQuantity()));

		bean.setSellerQuantityVal(getRoundupToTwoValue(ohlcQuote.depth.sell.get(0).getQuantity()
				+ ohlcQuote.depth.sell.get(1).getQuantity() + ohlcQuote.depth.sell.get(2).getQuantity()
				+ ohlcQuote.depth.sell.get(3).getQuantity() + ohlcQuote.depth.sell.get(4).getQuantity()));
	}
	
	private static int errorCounterUpdateDayOHLC = 0;
	public ConcurrentHashMap<String, StrategyOrbDataBean> updateDayOHLC(
			ConcurrentHashMap<String, StrategyOrbDataBean> dataMap) {
		String[] symbolsArray = null;
		try {
			if (null != StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)) {
				Object[] symbolArr = dataMap.keySet().toArray();
				symbolsArray = new String[symbolArr.length];
				for (int i = 0; i < symbolArr.length; i++) {
					symbolsArray[i] = symbolArr[i].toString();
				}

				if (null != symbolsArray && symbolsArray.length > 0) {
					Map<String, Quote> ohlcMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
							.getQuote(symbolsArray);
					for (String symbol : ohlcMap.keySet()) {
						StrategyOrbDataBean bean = dataMap.get(symbol);
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

							if (ohlcMap.get(symbol).depth.buy.size() >= 5
									&& ohlcMap.get(symbol).depth.sell.size() >= 5) {
								if (symbol.startsWith(FUTURE_KEY_PREFIX_NSE)) {
									depthListBuyer = ohlcMap.get(symbol).depth.buy;
									avgBuyerAt = ((depthListBuyer.get(0).getPrice() + depthListBuyer.get(1).getPrice()
											+ depthListBuyer.get(2).getPrice() + depthListBuyer.get(3).getPrice()
											+ depthListBuyer.get(4).getPrice()) / 5);
									depthListSeller = ohlcMap.get(symbol).depth.sell;
									avgSellerAt = ((depthListSeller.get(0).getPrice()
											+ depthListSeller.get(1).getPrice() + depthListSeller.get(2).getPrice()
											+ depthListSeller.get(3).getPrice() + depthListSeller.get(4).getPrice())
											/ 5);
									bean.setBuyerAt(getRoundupToTwoValue(avgBuyerAt));
									bean.setSellerAt(getRoundupToTwoValue(avgSellerAt));

									//updateAdditionals(bean, ohlcMap.get(symbol));
								} else {
									bean.setBuyerAt(ohlcMap.get(symbol).depth.buy.get(0).getPrice());
									bean.setSellerAt(ohlcMap.get(symbol).depth.sell.get(0).getPrice());

									//updateAdditionals(bean, ohlcMap.get(symbol));
								}
							} else {
								bean.setBuyerAt(ohlcMap.get(symbol).depth.buy.get(0).getPrice());
								bean.setSellerAt(ohlcMap.get(symbol).depth.sell.get(0).getPrice());

								//updateAdditionals(bean, ohlcMap.get(symbol));
							}
							bean.setBuyerSelleDiffRuleValid(
									((bean.getSellerAt() - bean.getBuyerAt()) * bean.getLotSize()) < 2500);
							if (NA.equals(bean.getTradedStateId())) {
								bean.setVolumes((long) ohlcMap.get(symbol).volumeTradedToday);
								
								bean.setBuyerSelleDiffRuleValid(
										((bean.getSellerAt() - bean.getBuyerAt()) * bean.getLotSize()) < 2500);
								updateAdditionals(bean, ohlcMap.get(symbol));
							}
							dataMap.replace(symbol, bean);
						}
					}
				} else {
					errorCounterUpdateDayOHLC++;
					if (errorCounterUpdateDayOHLC > ERROR_COUNTER) {
						errorCounterUpdateDayOHLC = 0;
						TradewareLogger.saveInfoLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC,
								ERROR_KITE_INPUT_SYMBOLS_ARRAY_IS_NULL_OR_EMPTY + symbolsArray);
					}
				}
			} else {
				TradewareLogger.saveInfoLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, ERROR_ON_UPDATE_OHLC);
			}
		} catch (org.json.JSONException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_JSON_EXCEPTION);
			// updateDayOHLC(dataMap);
		} catch (java.net.UnknownHostException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_UNKNOWN_HOST_EXCEPTION);
			// updateDayOHLC(dataMap);
		} catch (java.net.SocketTimeoutException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_SOCKET_TIME_OUT_EXCEPTION);
			updateDayOHLC(dataMap);
		} catch (NetworkException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_NETWORK_EXCEPTION);
			/*TradewareLogger.saveInfoLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC,
					symbolsArray.toString().toString());*/

		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_EXCEPTION);
		} catch (TokenException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_TOKEN_EXCEPTION);
		} catch (KiteException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_KITE_EXCEPTION);
		}
		return dataMap;
	}
	
	public synchronized ConcurrentHashMap<String, KiteLiveOHLCDataBean> updateLiveOHLC(
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> dataMap) {
		String[] symbolsArray = null;
		try {
			if (null != StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)) {
				Object[] symbolArr = dataMap.keySet().toArray();
				symbolsArray = new String[symbolArr.length];
				for (int i = 0; i < symbolArr.length; i++) {
					symbolsArray[i] = symbolArr[i].toString();
				}

				if (null != symbolsArray && symbolsArray.length > 0) {
					Map<String, Quote> ohlcMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
							.getQuote(symbolsArray);
					for (String symbol : ohlcMap.keySet()) {
						KiteLiveOHLCDataBean bean = dataMap.get(symbol);
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
							// bean.setInstrumentToken(quote.instrumentToken);
if (null != ohlcMap.get(symbol).depth) {
							if (ohlcMap.get(symbol).depth.buy.size() >= 5
									&& ohlcMap.get(symbol).depth.sell.size() >= 5) {
								if (symbol.startsWith(FUTURE_KEY_PREFIX_NSE)) {
									depthListBuyer = ohlcMap.get(symbol).depth.buy;
									avgBuyerAt = ((depthListBuyer.get(0).getPrice() + depthListBuyer.get(1).getPrice()
											+ depthListBuyer.get(2).getPrice() + depthListBuyer.get(3).getPrice()
											+ depthListBuyer.get(4).getPrice()) / 5);
									depthListSeller = ohlcMap.get(symbol).depth.sell;
									avgSellerAt = ((depthListSeller.get(0).getPrice()
											+ depthListSeller.get(1).getPrice() + depthListSeller.get(2).getPrice()
											+ depthListSeller.get(3).getPrice() + depthListSeller.get(4).getPrice())
											/ 5);
									bean.setBidPrice(getRoundupToTwoValue(avgBuyerAt));
									bean.setAskPrice(getRoundupToTwoValue(avgSellerAt));

									// updateAdditionals(bean, ohlcMap.get(symbol));
								} else {
									bean.setBidPrice(ohlcMap.get(symbol).depth.buy.get(0).getPrice());
									bean.setAskPrice(ohlcMap.get(symbol).depth.sell.get(0).getPrice());

									// updateAdditionals(bean, ohlcMap.get(symbol));
								}
							} else {
								bean.setBidPrice(ohlcMap.get(symbol).depth.buy.get(0).getPrice());
								bean.setAskPrice(ohlcMap.get(symbol).depth.sell.get(0).getPrice());

								// updateAdditionals(bean, ohlcMap.get(symbol));
							}
						}
							if (null != bean.getAskPrice() && null != bean.getBidPrice() && null != bean.getLotSize()) {
								bean.setAskBidPriceDiffVal(getRoundupToTwoValue(
										(bean.getAskPrice() - bean.getBidPrice()) * bean.getLotSize()));

								bean.setAskBidPriceDiffRuleValid(
										((bean.getAskPrice() - bean.getBidPrice()) * bean.getLotSize()) < 2500);
								// if (NA.equals(bean.getTradedStateId())) {
								bean.setVolumes((long) ohlcMap.get(symbol).volumeTradedToday);

								bean.setAskBidPriceDiffRuleValid(
										((bean.getAskPrice() - bean.getBidPrice()) * bean.getLotSize()) < 2500);
								if (null != ohlcMap.get(symbol).depth) {
								updateAdditionals(bean, ohlcMap.get(symbol));
								}
								// }
							} else {
								System.out.println("No kite Data ::::  " + symbol);
							}
							if ((null == bean.getAskPrice() || 0 == bean.getAskPrice())
									|| (null == bean.getBidPrice() || 0 == bean.getBidPrice())
							/* && null != bean.getLotSize() */) {
								if (null == bean.getAskPrice() || 0 == bean.getAskPrice()) {
									bean.setAskPrice(bean.getLtp());
								}
								if (null == bean.getBidPrice() || 0 == bean.getBidPrice()) {
									bean.setBidPrice(bean.getLtp());
								}
							}
							dataMap.replace(symbol, bean);
						}
					}
				} else {
					errorCounterUpdateDayOHLC++;
					if (errorCounterUpdateDayOHLC > ERROR_COUNTER) {
						errorCounterUpdateDayOHLC = 0;
						TradewareLogger.saveInfoLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_LIVE_OHLC,
								ERROR_KITE_INPUT_SYMBOLS_ARRAY_IS_NULL_OR_EMPTY + symbolsArray);
					}
				}
			} else {
				TradewareLogger.saveInfoLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_LIVE_OHLC, ERROR_ON_UPDATE_OHLC);
			}
		} catch (org.json.JSONException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_LIVE_OHLC, e,
					ERROR_TYPE_JSON_EXCEPTION);
			// updateDayOHLC(dataMap);
		} catch (java.net.UnknownHostException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_LIVE_OHLC, e,
					ERROR_TYPE_UNKNOWN_HOST_EXCEPTION);
			// updateDayOHLC(dataMap);
		} catch (java.net.SocketTimeoutException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_LIVE_OHLC, e,
					ERROR_TYPE_SOCKET_TIME_OUT_EXCEPTION);
			updateLiveOHLC(dataMap);
		} catch (NetworkException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_LIVE_OHLC, e,
					ERROR_TYPE_NETWORK_EXCEPTION);
			/*TradewareLogger.saveInfoLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC,
					symbolsArray.toString().toString());*/

		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_LIVE_OHLC, e,
					ERROR_TYPE_EXCEPTION);
		} catch (TokenException e) {
			dataMap = null;
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_LIVE_OHLC, e,
					ERROR_TYPE_TOKEN_EXCEPTION);
		} catch (KiteException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_LIVE_OHLC, e,
					ERROR_TYPE_KITE_EXCEPTION);
		}
		return dataMap;
	}
	
	private void updateAdditionals(KiteLiveOHLCDataBean bean, Quote ohlcQuote) {
		bean.setAskBidPriceDiffVal2(getRoundupToTwoValue(
				(ohlcQuote.depth.sell.get(1).getPrice() - ohlcQuote.depth.buy.get(1).getPrice()) * bean.getLotSize()));
		bean.setAskBidPriceDiffVal3(getRoundupToTwoValue(
				(ohlcQuote.depth.sell.get(2).getPrice() - ohlcQuote.depth.buy.get(2).getPrice()) * bean.getLotSize()));

		bean.setBuyerQuantityVal(getRoundupToTwoValue(ohlcQuote.depth.buy.get(0).getQuantity()
				+ ohlcQuote.depth.buy.get(1).getQuantity() + ohlcQuote.depth.buy.get(2).getQuantity()
				+ ohlcQuote.depth.buy.get(3).getQuantity() + ohlcQuote.depth.buy.get(4).getQuantity()));

		bean.setSellerQuantityVal(getRoundupToTwoValue(ohlcQuote.depth.sell.get(0).getQuantity()
				+ ohlcQuote.depth.sell.get(1).getQuantity() + ohlcQuote.depth.sell.get(2).getQuantity()
				+ ohlcQuote.depth.sell.get(3).getQuantity() + ohlcQuote.depth.sell.get(4).getQuantity()));
	}
	
	
	public ConcurrentHashMap<String, KiteLiveOHLCDataBean> updateLiveOHLCForHugeMap(
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> dataMap) {
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> wholeDataMap = new ConcurrentHashMap<String, KiteLiveOHLCDataBean>();
		if (dataMap.size() > 200) {
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> inputDataMap = new ConcurrentHashMap<String, KiteLiveOHLCDataBean>();
			Set<String> keys = dataMap.keySet();
			List<String> listKeys = new ArrayList<String>(keys);
			for (int i = 0; i < dataMap.size(); i++) {
				if ((i+1) % 200 == 0) {
					inputDataMap = updateLiveOHLC(inputDataMap);
					wholeDataMap.putAll(inputDataMap);
					inputDataMap.clear();
				} else {
					inputDataMap.put(listKeys.get(i), dataMap.get(listKeys.get(i)));
				}
			}
		} else {
			wholeDataMap = updateLiveOHLC(dataMap);
		}
		return wholeDataMap;
	}
	//Phase 2 end 
	
	// 04-21-2021 start - afterSomeSuccess [06/01/2021]
	private static int loopConter=0;
	private static int avgConter=0;
	private static int buyerQuantity=0;
	private static int sellerQuantity=0;
	
	private void clearAvgValues() {
		loopConter=0;
		avgConter=0;
		avgBuyerAt=0;
		avgSellerAt=0;
		buyerQuantity=0;
		sellerQuantity=0;
	}
	public ConcurrentHashMap<String, StrategyOrbDataBean> updateDayOHLC_New(
			ConcurrentHashMap<String, StrategyOrbDataBean> dataMap) {
		String[] symbolsArray = null;
		try {
			if (null != StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)) {
				Object[] symbolArr = dataMap.keySet().toArray();
				symbolsArray = new String[symbolArr.length];
				for (int i = 0; i < symbolArr.length; i++) {
					symbolsArray[i] = symbolArr[i].toString();
				}

				if (null != symbolsArray && symbolsArray.length > 0) {
					Map<String, Quote> ohlcMap = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
							.getQuote(symbolsArray);
					for (String symbol : ohlcMap.keySet()) {
						StrategyOrbDataBean bean = dataMap.get(symbol);
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

							if (ohlcMap.get(symbol).depth.buy.size() >= 5
									&& ohlcMap.get(symbol).depth.sell.size() >= 5) {
								if (symbol.startsWith(FUTURE_KEY_PREFIX_NSE)) {
									depthListBuyer = ohlcMap.get(symbol).depth.buy;
									
									clearAvgValues();
									for (loopConter=0;loopConter<5;loopConter++)  {
										if (depthListBuyer.get(loopConter).getPrice() > 0) {
											avgConter++;
											avgBuyerAt = avgBuyerAt + depthListBuyer.get(loopConter).getPrice();
											buyerQuantity = buyerQuantity + depthListBuyer.get(loopConter).getQuantity();
											if (buyerQuantity < bean.getLotSize()) {
												continue; 	
											} else {
												break;
											}	
										}
									}
									bean.setBuyerAt(avgBuyerAt/avgConter);
									
									depthListSeller = ohlcMap.get(symbol).depth.sell;
									clearAvgValues();
									for (loopConter=0;loopConter<5;loopConter++)  {
										if (depthListSeller.get(loopConter).getPrice() > 0) {
											avgConter++;
											avgSellerAt = avgSellerAt + depthListSeller.get(loopConter).getPrice();
											sellerQuantity = sellerQuantity + depthListSeller.get(loopConter).getQuantity();
											if (sellerQuantity < bean.getLotSize()) {
												continue; 	
											} else {
												break;
											}	
										}
									}
									bean.setSellerAt(avgSellerAt/avgConter);
								} else {
									bean.setBuyerAt(ohlcMap.get(symbol).depth.buy.get(0).getPrice());
									bean.setSellerAt(ohlcMap.get(symbol).depth.sell.get(0).getPrice());
								}
							} else {
								bean.setBuyerAt(ohlcMap.get(symbol).depth.buy.get(0).getPrice());
								bean.setSellerAt(ohlcMap.get(symbol).depth.sell.get(0).getPrice());
							}
							bean.setBuyerSelleDiffRuleValid(
									((bean.getSellerAt() - bean.getBuyerAt()) * bean.getLotSize()) < 2500);
							if (NA.equals(bean.getTradedStateId())) {
								bean.setVolumes((long) ohlcMap.get(symbol).volumeTradedToday);
								
								bean.setBuyerSelleDiffRuleValid(
										((bean.getSellerAt() - bean.getBuyerAt()) * bean.getLotSize()) < 2500);
								updateAdditionals_New(bean, ohlcMap.get(symbol));
							}
							dataMap.replace(symbol, bean);
						}
					}
				} else {
					errorCounterUpdateDayOHLC++;
					if (errorCounterUpdateDayOHLC > ERROR_COUNTER) {
						errorCounterUpdateDayOHLC = 0;
						TradewareLogger.saveInfoLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC,
								ERROR_KITE_INPUT_SYMBOLS_ARRAY_IS_NULL_OR_EMPTY + symbolsArray);
					}
				}
			} else {
				TradewareLogger.saveInfoLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, ERROR_ON_UPDATE_OHLC);
			}
		} catch (org.json.JSONException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_JSON_EXCEPTION);
		} catch (java.net.UnknownHostException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_UNKNOWN_HOST_EXCEPTION);
		} catch (java.net.SocketTimeoutException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_SOCKET_TIME_OUT_EXCEPTION);
			updateDayOHLC_New(dataMap);
		} catch (NetworkException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_NETWORK_EXCEPTION);

		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_EXCEPTION);
		} catch (TokenException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_TOKEN_EXCEPTION);
		} catch (KiteException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL, METHOD_UPDATE_DAY_OHLC, e,
					ERROR_TYPE_KITE_EXCEPTION);
		}
		return dataMap;
	}
	
	private void updateAdditionals_New(StrategyOrbDataBean bean, Quote ohlcQuote) {
		if (bean.getKiteFutureKey().startsWith(FUTURE_KEY_PREFIX_NSE)) {
			
			clearAvgValues();
			bean.setBuyerA2t(null);
			bean.setSeller2At(null);
			bean.setBuyer3At(null);
			bean.setSeller3At(null);
			depthListBuyer = ohlcQuote.depth.buy;
			for (loopConter=0;loopConter<5;loopConter++)  {
				if (depthListBuyer.get(loopConter).getPrice() > 0) {
					avgConter++;
					avgBuyerAt = avgBuyerAt + depthListBuyer.get(loopConter).getPrice();
					buyerQuantity = buyerQuantity + depthListBuyer.get(loopConter).getQuantity();
					if (null == bean.getBuyerA2t() && buyerQuantity < (bean.getLotSize() * 2 )) {
						continue; 	
					} else if (null == bean.getBuyerA2t()){
						bean.setBuyerA2t(avgBuyerAt/(bean.getLotSize() * 2 ));
					} else if (null == bean.getBuyer3At() && buyerQuantity < (bean.getLotSize() * 3 )) {
						continue; 	
					} else if (null == bean.getBuyer3At()){
						bean.setBuyer3At(avgBuyerAt/(bean.getLotSize() * 3 ));
						break;
					}
				}
				if (4 == loopConter) {
					//bean.setBuyerSelleDiffRuleValid(false);
				}
			}
			
			depthListSeller = ohlcQuote.depth.sell;
			clearAvgValues();
			for (loopConter=0;loopConter<5;loopConter++)  {
				if (depthListSeller.get(loopConter).getPrice() > 0) {
					avgConter++;
					avgSellerAt = avgSellerAt + depthListSeller.get(loopConter).getPrice();
					sellerQuantity = sellerQuantity + depthListSeller.get(loopConter).getQuantity();
					if (null == bean.getSeller2At() && sellerQuantity < (bean.getLotSize() * 2 )) {
						continue; 	
					} else if (null == bean.getSeller2At()){
						bean.setSeller2At(avgSellerAt/(bean.getLotSize() * 2 ));
					} else if (null == bean.getSeller3At() && sellerQuantity < (bean.getLotSize() * 3 )) {
						continue; 	
					} else if (null == bean.getSeller3At()){
						bean.setSeller3At(avgSellerAt/(bean.getLotSize() * 3 ));
						break;
					}
				}
				if (4 == loopConter) {
					//bean.setBuyerSelleDiffRuleValid(false);
				}
			}
		} else {
		bean.setBuyerSellerDiffVal2(getRoundupToTwoValue(
				(ohlcQuote.depth.sell.get(1).getPrice() - ohlcQuote.depth.buy.get(1).getPrice()) * bean.getLotSize()));
		bean.setBuyerSellerDiffVal3(getRoundupToTwoValue(
				(ohlcQuote.depth.sell.get(2).getPrice() - ohlcQuote.depth.buy.get(2).getPrice()) * bean.getLotSize()));
		}
		
		clearAvgValues();
		
		for (loopConter=0;loopConter<20;loopConter++)  {
			buyerQuantity = buyerQuantity + depthListBuyer.get(loopConter).getQuantity();
			sellerQuantity = sellerQuantity + depthListSeller.get(loopConter).getQuantity();
		}
	}
	// 04-21-2021 END - afterSomeSuccess [06/01/2021]
	
	//Phase 3 Start - place orders 
	/*public StrategyOrbDataBean placeBracketOrder(StrategyOrbDataBean bean) {
	OrderParams orderParams = new OrderParams();
	orderParams.orderType = Constants.ORDER_TYPE_LIMIT;// "BO";//"MARKET";
	orderParams.product = Constants.PRODUCT_MIS;
	orderParams.validity = Constants.VALIDITY_DAY;
	orderParams.transactionType = bean.getTradableStateId();
	orderParams.quantity = bean.getLotSize();

	if (bean.getKiteFutureKey().startsWith(Constants.FUTURE_KEY_PREFIX_NFO)) {
		orderParams.tradingsymbol = bean.getKiteFutureKey().replace(Constants.FUTURE_KEY_PREFIX_NFO,
				Constants.EMPTY_STRING);
		orderParams.exchange = Constants.EXCHANGE_NFO;
	} else if (bean.getKiteFutureKey().startsWith(Constants.FUTURE_KEY_PREFIX_NSE)) {
		orderParams.tradingsymbol = bean.getKiteFutureKey().replace(Constants.FUTURE_KEY_PREFIX_NSE,
				Constants.EMPTY_STRING);
		orderParams.exchange = Constants.EXCHANGE_NSE;
	} else {
		return null;
	}

	double priceVal = Constants.BUY.equals(bean.getTradableStateId())
			? (bean.getTradedAtVal() + (bean.getTradedAtVal() * Constants.LIMIT_ORDER_PLACABLE_PRICE))
			: (bean.getTradedAtVal() - (bean.getTradedAtVal() * Constants.LIMIT_ORDER_PLACABLE_PRICE));
	priceVal = getRoundupValue(priceVal);
	double squareoff = Constants.BUY.equals(bean.getTradableStateId())
			? (bean.getTradedAtVal() + (Constants.TARGET_AMOUNT / bean.getLotSize()))
			: (bean.getTradedAtVal() - (Constants.TARGET_AMOUNT / bean.getLotSize()));
	squareoff = Constants.BUY.equals(bean.getTradableStateId())
			? getRoundupValue(squareoff - priceVal) : getRoundupValue(priceVal - squareoff);
			squareoff = Math.abs(squareoff);//Not required just for worst case scenario	
			
	double stopLoss = Constants.BUY.equals(bean.getTradableStateId())
			? (bean.getTradedAtVal() - (Constants.TARGET_CO_STOP_LOSS / bean.getLotSize()))
			: (bean.getTradedAtVal() + (Constants.TARGET_CO_STOP_LOSS / bean.getLotSize()));
	stopLoss = Constants.BUY.equals(bean.getTradableStateId())
			? stopLoss > bean.getStopLoss() ? stopLoss : bean.getStopLoss()
			: stopLoss > bean.getStopLoss() ? bean.getStopLoss() : stopLoss;
	stopLoss = Math.abs(getRoundupValue(priceVal - bean.getStopLoss()));
	
	orderParams.price = priceVal;
	orderParams.squareoff = squareoff;
	orderParams.stoploss = stopLoss;
	orderParams.trailingStoploss = getRoundupValue(stopLoss / Constants.TWO);
	Order order = null;
	try {
		order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).placeOrder(orderParams,
				Constants.ORDER_VARIETY_BO);
		bean.setKiteOrderId(order.orderId);
	} catch (JSONException | IOException | KiteException e) {
		NkpAlgoLogger.printWithNewLine(Constants.ORDER_PLACEMENT_FAILED + bean.getSymbolName() + Constants.UNDER_SCORE
				+ getCurrentTime() + Constants.SPACE + e.getMessage());
		e.printStackTrace();
	} catch (Exception e) {
		NkpAlgoLogger.printWithNewLine("EXCEPTION>>> " + Constants.ORDER_PLACEMENT_FAILED
				+ bean.getSymbolName() + Constants.UNDER_SCORE + getCurrentTime()
				+ Constants.SPACE + e.getMessage());
		e.printStackTrace();
	}
	return bean;
}*/

public StrategyOrbDataBean placeCoverOrder(StrategyOrbDataBean bean) {
	OrderParams orderParams = new OrderParams();
	orderParams.orderType = Constants.ORDER_TYPE_MARKET;//Constants.ORDER_TYPE_LIMIT; //Constants.ORDER_TYPE_MARKET;// "BO";//"MARKET";
	orderParams.product = Constants.PRODUCT_MIS;
	orderParams.validity = Constants.VALIDITY_DAY;
	orderParams.transactionType = bean.getTradableStateId();
	orderParams.quantity = bean.getLotSize();
	if (bean.getKiteFutureKey().startsWith(Constants.FUTURE_KEY_PREFIX_NFO)) {
		orderParams.tradingsymbol = bean.getKiteFutureKey().replace(Constants.FUTURE_KEY_PREFIX_NFO,
				Constants.EMPTY_STRING);
		orderParams.exchange = Constants.EXCHANGE_NFO;
	} else if (bean.getKiteFutureKey().startsWith(Constants.FUTURE_KEY_PREFIX_NSE)) {
		orderParams.tradingsymbol = bean.getKiteFutureKey().replace(Constants.FUTURE_KEY_PREFIX_NSE,
				Constants.EMPTY_STRING);
		orderParams.exchange = Constants.EXCHANGE_NSE;
	} else {
		return null;
	}
	
	double triggerPrice = Constants.BUY.equals(bean.getTradableStateId())
			? (bean.getTradedAtVal() - (Constants.TARGET_CO_STOP_LOSS / bean.getLotSize()))
			: (bean.getTradedAtVal() + (Constants.TARGET_CO_STOP_LOSS / bean.getLotSize()));
	triggerPrice = Constants.BUY.equals(bean.getTradableStateId())
			? triggerPrice > bean.getSellAtVal() ? triggerPrice : bean.getSellAtVal()
			: triggerPrice > bean.getBuyAtVal() ? bean.getBuyAtVal() : triggerPrice;
	triggerPrice = getRoundupToOneValue(triggerPrice);
	orderParams.triggerPrice = triggerPrice;
	orderParams.price = getRoundupToOneValue(bean.getTradedAtVal());
	
	Order order = null;
	//commentng it as exposed to public in vps nextra
	try {
		order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).placeOrder(orderParams,
				Constants.ORDER_VARIETY_CO); // "regular");//"bo");
		bean.setKiteOrderId(order.orderId);
		bean.setKiteOrderType(Constants.ORDER_VARIETY_CO);
	}  catch (InputException e) {
		if (isTimeBefore3_20PM()) {
			placeAfter3_20Order(bean);
		}
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL+bean.getSymbolName(),
				Constants.METHOD_PLACE_COVER_ORDER, e, Constants.ERROR_TYPE_INPUT_EXCEPTION);
	} catch (JSONException | IOException | KiteException e) {
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL+bean.getSymbolName(),
				Constants.METHOD_PLACE_COVER_ORDER, e, Constants.ERROR_TYPE_KITE_EXCEPTION);
	} catch (Exception e) {
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL+bean.getSymbolName(),
				Constants.METHOD_PLACE_COVER_ORDER, e, Constants.ERROR_TYPE_EXCEPTION);
	}
	return bean;
}

public boolean isTimeBefore3_20PM() {
	LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(TIME_ZONE)); // Trading closed
	LocalDateTime timeAt03_20 = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(15, 19, 59, 00));
	
	LocalDateTime timeAt03_25 = LocalDate.now(ZoneId.of(TIME_ZONE)).atTime(LocalTime.of(15, 25, 59, 00));
	return (currentTime.isAfter(timeAt03_20) && currentTime.isBefore(timeAt03_25));

}

	public StrategyOrbDataBean cancelCoverOrder(StrategyOrbDataBean bean) {
	Order order = null;
	try {
		if(Constants.ORDER_VARIETY_CO.equals(bean.getKiteOrderType())) {
			if (null != bean && null != bean.getKiteOrderId()) {
				List<Order> trades = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).getOrders();
				String myTrade = bean.getKiteFutureKey().split(":")[1];
				for (Order trade : trades) {
					if (trade.tradingSymbol.equals(myTrade)
							&& Constants.ORDER_STATUS_CO_2ND_LEG_TRIGGER_PENDING.equals(trade.status)) {
						order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).cancelOrder(trade.orderId,
								bean.getKiteOrderId(), Constants.ORDER_VARIETY_CO);
					/*	NkpAlgoLogger.printWithNewLine(Constants.ORDER_CANCELLED_SUCCESSFUL + bean.getSymbolName()
								+ Constants.UNDER_SCORE + getCurrentTime());*/
						
							if (bean.getTradedLotCount() == 1) {
								break;
							} else {
								bean.setTradedLotCount(bean.getTradedLotCount() - 1);
							}
						 
					} 
				}
			}
		} else if(Constants.ORDER_VARIETY_REGULAR.equals(bean.getKiteOrderType())) {
			cancelAfter3_20Order(bean);
		}
	} catch (JSONException | IOException | KiteException  e) {
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL+bean.getSymbolName(),
				Constants.METHOD_CANCEL_COVER_ORDER, e, Constants.ERROR_TYPE_KITE_EXCEPTION);
	} 
	catch (Exception  e) {
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL+bean.getSymbolName(),
				Constants.METHOD_CANCEL_COVER_ORDER, e, Constants.ERROR_TYPE_EXCEPTION);
	}
	return bean;
}

/*public StrategyOrbDataBean cancelBracketOrder(StrategyOrbDataBean bean) {
	Order order = null;
	try {
		if (null != bean.getKiteOrderId()) {
			order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
					.cancelOrder(bean.getKiteOrderId(), Constants.ORDER_VARIETY_BO);
			NkpAlgoLogger.printWithNewLine(Constants.ORDER_CANCELLED_SUCCESSFUL + bean.getSymbolName() + Constants.UNDER_SCORE
					+ getCurrentTime());
		}
	} catch (JSONException | IOException | KiteException e) {
		NkpAlgoLogger.printWithNewLine(Constants.ORDER_CANCELLED_ERROR + bean.getSymbolName() + Constants.UNDER_SCORE
				+ getCurrentTime());
		e.printStackTrace();
	}
	return bean;
}*/


//To place CNC order after 3:20PM
public StrategyOrbDataBean placeAfter3_20Order(StrategyOrbDataBean bean) {
	OrderParams orderParams = new OrderParams();
	orderParams.orderType = Constants.ORDER_TYPE_MARKET;//Constants.ORDER_TYPE_LIMIT; //Constants.ORDER_TYPE_MARKET;// "BO";//"MARKET";
	//orderParams.product = Constants.PRODUCT_MIS;
	orderParams.validity = Constants.VALIDITY_DAY;
	orderParams.transactionType = bean.getTradableStateId();
	orderParams.quantity = bean.getLotSize();
	if (bean.getKiteFutureKey().startsWith(Constants.FUTURE_KEY_PREFIX_NFO)) {
		orderParams.tradingsymbol = bean.getKiteFutureKey().replace(Constants.FUTURE_KEY_PREFIX_NFO,
				Constants.EMPTY_STRING);
		orderParams.exchange = Constants.EXCHANGE_NFO;
		orderParams.product = Constants.PRODUCT_FNO_NRML;
	} else if (bean.getKiteFutureKey().startsWith(Constants.FUTURE_KEY_PREFIX_NSE)) {
		orderParams.tradingsymbol = bean.getKiteFutureKey().replace(Constants.FUTURE_KEY_PREFIX_NSE,
				Constants.EMPTY_STRING);
		orderParams.exchange = Constants.EXCHANGE_NSE;
		orderParams.product = Constants.PRODUCT_CASH_AND_CARRY;
	} else {
		return null;
	}
	
	double triggerPrice = Constants.BUY.equals(bean.getTradableStateId())
			? (bean.getTradedAtVal() - (Constants.TARGET_CO_STOP_LOSS / bean.getLotSize()))
			: (bean.getTradedAtVal() + (Constants.TARGET_CO_STOP_LOSS / bean.getLotSize()));
	triggerPrice = Constants.BUY.equals(bean.getTradableStateId())
			? triggerPrice > bean.getSellAtVal() ? triggerPrice : bean.getSellAtVal()
			: triggerPrice > bean.getBuyAtVal() ? bean.getBuyAtVal() : triggerPrice;
	triggerPrice = getRoundupToOneValue(triggerPrice);
	orderParams.triggerPrice = triggerPrice;
	orderParams.price = getRoundupToOneValue(bean.getTradedAtVal());
	
	Order order = null;
	//commentng it as exposed to public in vps nextra
	try {
		order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).placeOrder(orderParams,
				Constants.ORDER_VARIETY_REGULAR); // "regular");//"bo");
		bean.setKiteOrderId(order.orderId);
		bean.setKiteOrderType(Constants.ORDER_VARIETY_REGULAR);
	}  catch (InputException e) {
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL+bean.getSymbolName(),
				Constants.METHOD_PLACE_COVER_ORDER, e, Constants.ERROR_TYPE_INPUT_EXCEPTION);
	} catch (JSONException | IOException | KiteException e) {
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL+bean.getSymbolName(),
				Constants.METHOD_PLACE_COVER_ORDER, e, Constants.ERROR_TYPE_KITE_EXCEPTION);
	} catch (Exception e) {
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL+bean.getSymbolName(),
				Constants.METHOD_PLACE_COVER_ORDER, e, Constants.ERROR_TYPE_EXCEPTION);
	}
	return bean;
}

public StrategyOrbDataBean cancelAfter3_20Order(StrategyOrbDataBean bean) {
	Order order = null;
	try {
		if (null != bean && null != bean.getKiteOrderId()) {
			order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).cancelOrder(bean.getKiteOrderId(),
					Constants.ORDER_VARIETY_REGULAR);
		}
	} catch (JSONException | IOException | KiteException e) {
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL + bean.getSymbolName(),
				Constants.METHOD_CANCEL_COVER_ORDER, e, Constants.ERROR_TYPE_KITE_EXCEPTION);
	} catch (Exception e) {
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL + bean.getSymbolName(),
				Constants.METHOD_CANCEL_COVER_ORDER, e, Constants.ERROR_TYPE_EXCEPTION);
	}
	return bean;
}
	//Phase 3 END - place orders 


	private  static Date fromDate = null;
	private static Date toDate = null;

	private void refreshTradingDataFromDateToDate() {
		tradewareTool.setToDateForKiteHistDate(null);
		fromDate = tradewareTool.getFromDateForKiteHistDataStochastic5Or15Min_9_00();
		toDate = tradewareTool.getToDateForKiteHistDate_WithCurrentTime(); 
	}
	public void findHeikinAshiTrend(StrategyOrbDataBean bean, String candeFrame) {
		try {
			if (null != fromDate && null !=toDate) {
			HistoricalData histData = StockUtil.kiteConnectAdminsHist.get(StockUtil.DEFAULT_USER).getHistoricalData(
					fromDate, toDate, String.valueOf(bean.getInstrumentToken()), candeFrame,
					false);
			
			}
		} catch (KiteException e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL,
					METHOD_FIND_HEIKIN_ASHI_TREND + bean.getSymbolName(), e, ERROR_TYPE_KITE_EXCEPTION);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(CLASS_KITE_CONNECT_TOOL,
					METHOD_FIND_HEIKIN_ASHI_TREND + bean.getSymbolName(), e, ERROR_TYPE_EXCEPTION);

		}
	}
	//Duplicate method
	protected void findHeikinAshiTrend(List<HistoricalData> histDataList, StrategyOrbDataBean bean) {
		try {

// 05-29-2021 start - afterSomeSuccess
			if (null != histDataList && histDataList.size() >= 5) {
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

				double heikenAshiOpen3 = getRoundupToTwoValue((open2 + close2) / 2);
				double heikenAshiClose3 = getRoundupToTwoValue((open3 + high3 + low3 + close3) / 4);
				double heikenAshiHigh3 = Math.max(Math.max(heikenAshiOpen3, heikenAshiClose3), high3);
				double heikenAshiLow3 = Math.min(Math.min(heikenAshiOpen3, heikenAshiClose3), low3);

				double heikenAshiOpen2 = getRoundupToTwoValue((open1 + close1) / 2);
				double heikenAshiClose2 = getRoundupToTwoValue((open2 + high2 + low2 + close2) / 4);
				double heikenAshiHigh2 = Math.max(Math.max(heikenAshiOpen2, heikenAshiClose2), high2);
				double heikenAshiLow2 = Math.min(Math.min(heikenAshiOpen2, heikenAshiClose2), low2);

				double heikenAshiOpen1 = getRoundupToTwoValue((open0 + close0) / 2);
				double heikenAshiClose1 = getRoundupToTwoValue((open1 + high1 + low1 + close1) / 4);
				double heikenAshiHigh1 = Math.max(Math.max(heikenAshiOpen1, heikenAshiClose1), high1);
				double heikenAshiLow1 = Math.min(Math.min(heikenAshiOpen1, heikenAshiClose1), low1);

				if (heikenAshiClose3 > heikenAshiOpen3 && heikenAshiClose2 > heikenAshiOpen2
						&& heikenAshiClose1 > heikenAshiOpen1) {
					bean.setMin5HeikinAshiTrendId(BUY);
					if (heikenAshiOpen3 == heikenAshiLow3 && heikenAshiOpen2 == heikenAshiLow2
							&& heikenAshiOpen1 == heikenAshiLow1) {
						if (heikenAshiClose3 > heikenAshiClose2 && heikenAshiClose2 > heikenAshiClose1) {
							bean.setMin5HeikinAshiTrendId(V_STRONG_BUY);
							if (/*
								 * (((heikenAshiHigh3 - heikenAshiClose3) < (heikenAshiClose3 -
								 * heikenAshiOpen3)) && ((heikenAshiHigh2 - heikenAshiClose2) <
								 * (heikenAshiClose2 - heikenAshiOpen2)) && ((heikenAshiHigh1 -
								 * heikenAshiClose1) < (heikenAshiClose1 - heikenAshiOpen1))) ||
								 */ (((heikenAshiClose3 - heikenAshiOpen3) < (heikenAshiClose2 - heikenAshiOpen2))
									&& ((heikenAshiClose1 - heikenAshiOpen1) < (heikenAshiClose1 - heikenAshiOpen1)))) {
								bean.setMin5HeikinAshiTrendId(VV_STRONG_BUY);
							}
						}
					}

				} else if (heikenAshiClose3 < heikenAshiOpen3 && heikenAshiClose2 < heikenAshiOpen2
						&& heikenAshiClose1 < heikenAshiOpen1) {
					bean.setMin5HeikinAshiTrendId(SELL);
					if (heikenAshiOpen3 == heikenAshiHigh3 && heikenAshiOpen2 == heikenAshiHigh2
							&& heikenAshiOpen1 == heikenAshiHigh1) {
						if (heikenAshiClose3 < heikenAshiClose2 && heikenAshiClose2 < heikenAshiClose1) {
							bean.setMin5HeikinAshiTrendId(V_STRONG_SELL);
							if (/*
								 * (((heikenAshiHigh3 - heikenAshiClose3) > (heikenAshiClose3 - heikenAshiLow3))
								 * && ((heikenAshiHigh2 - heikenAshiClose2) > (heikenAshiClose2 -
								 * heikenAshiLow2)) && ((heikenAshiHigh1 - heikenAshiClose1) > (heikenAshiClose1
								 * - heikenAshiLow1))) ||
								 */ (((heikenAshiOpen3 - heikenAshiClose3) > (heikenAshiOpen2 - heikenAshiClose2))
									&& ((heikenAshiOpen2 - heikenAshiClose2) > (heikenAshiOpen1 - heikenAshiClose1)))) {
								bean.setMin5HeikinAshiTrendId(VV_STRONG_SELL);
							}
						}
					}
				} else {
					bean.setMin5HeikinAshiTrendId(NA);
				}
			}
// 05-29-2021 end - afterSomeSuccess
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(CLASS_ADDITIONAL_DATA_FILLUP_HELPER, METHOD_FIND_HEIKIN_ASHI_TREND, e,
					ERROR_TYPE_EXCEPTION);
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//this code should be in Tradeware Tool and list should be ready by morning with Scheduler
	
	
		private static ConcurrentHashMap<String, KiteLiveOHLCDataBean> indexLiveDataMap = new ConcurrentHashMap<String, KiteLiveOHLCDataBean>();
		
		public ConcurrentHashMap<String, KiteLiveOHLCDataBean> getIndexLiveDataMap() {
			if (indexLiveDataMap.isEmpty()) {
				KiteLiveOHLCDataBean bean2 = new KiteLiveOHLCDataBean();
				//bean2.setSymbolName(NIFTY_50_FUT);
				bean2.setKiteFutureKey(FUTURE_KEY_PREFIX_NSE + NIFTY_50);
				bean2.setSymbolId(NIFTY_50);
				bean2.setLotSize(50);
				bean2.setOptionTickerSize(50d);
				bean2.setOptionStrikePrice(bean2.getKiteFutureKey());
				//bean2.setOptionExpiryDate(bean.getExpiryDate());
				indexLiveDataMap.put(bean2.getKiteFutureKey(), bean2);
				
				KiteLiveOHLCDataBean bean3 = new KiteLiveOHLCDataBean();
				//bean3.setSymbolName(NIFTY_50_FUT);
				bean3.setKiteFutureKey(tradewareTool.getKiteFutureKey(NIFTY));
				bean3.setSymbolId(NIFTY_50_FUT);
				bean3.setLotSize(50);
				bean3.setOptionTickerSize(50d);
				bean3.setOptionStrikePrice(bean3.getKiteFutureKey());
				//bean3.setOptionExpiryDate(bean.getExpiryDate());
				indexLiveDataMap.put(bean3.getKiteFutureKey(), bean3);

				KiteLiveOHLCDataBean bean4 = new KiteLiveOHLCDataBean();
				//bean4.setSymbolName(BANKNIFTY_FUT);
				bean4.setKiteFutureKey(NSE_BANK_NIFTY_KITE_INDEX_KEY);
				bean4.setSymbolId(BANKNIFTY);
				bean4.setLotSize(25);
				bean4.setOptionTickerSize(100d);
				bean4.setOptionStrikePrice(bean4.getKiteFutureKey());
				//bean4.setOptionExpiryDate(bean.getExpiryDate());
				indexLiveDataMap.put(bean4.getKiteFutureKey(), bean4);
				
				KiteLiveOHLCDataBean bean5 = new KiteLiveOHLCDataBean();
				//bean4.setSymbolName(BANKNIFTY_FUT);
				bean5.setKiteFutureKey(tradewareTool.getKiteFutureKey(BANKNIFTY));
				bean5.setSymbolId(BANKNIFTY_FUT);
				bean5.setLotSize(25);
				bean5.setOptionTickerSize(100d);
				bean5.setOptionStrikePrice(bean5.getKiteFutureKey());
				//bean5.setOptionExpiryDate(bean.getExpiryDate());
				indexLiveDataMap.put(bean5.getKiteFutureKey(), bean5);

				KiteLiveOHLCDataBean bean10 = new KiteLiveOHLCDataBean();
				//bean10.setSymbolName("FINNIFTY(FUT)");
				bean10.setKiteFutureKey(NSE_NIFTY_FIN_SERVICE_KITE_INDEX_KEY);
				bean10.setSymbolId(FINNIFTY);
				bean10.setLotSize(40);
				bean10.setOptionTickerSize(50d);
				bean10.setOptionStrikePrice(bean10.getKiteFutureKey());
				//bean10.setOptionExpiryDate(bean.getExpiryDate());
				indexLiveDataMap.put(bean10.getKiteFutureKey(), bean10);
				
				KiteLiveOHLCDataBean bean11 = new KiteLiveOHLCDataBean();
				//bean11.setSymbolName("FINNIFTY(FUT)");
				bean11.setKiteFutureKey(tradewareTool.getKiteFutureKey(FINNIFTY));
				bean11.setSymbolId("FINNIFTY(FUT)");
				bean11.setLotSize(40);
				bean11.setOptionTickerSize(50d);
				bean11.setOptionStrikePrice(bean11.getKiteFutureKey());
				//bean11.setOptionExpiryDate(bean.getExpiryDate());
				indexLiveDataMap.put(bean11.getKiteFutureKey(), bean11);
			}
			return indexLiveDataMap;
		}
		
	public ConcurrentHashMap<String, KiteLiveOHLCDataBean> getIndexesLiveOHLCData() {
		try {
			indexLiveDataMap = updateLiveOHLC(getIndexLiveDataMap());
			if (validKiteAccess()) {

				// update variables
				tradewareTool.setNiftyLiveStockPrice(indexLiveDataMap.get(FUTURE_KEY_PREFIX_NSE + NIFTY_50).getLtp());
				tradewareTool
						.setNiftyLiveFuturePrice(indexLiveDataMap.get(tradewareTool.getKiteFutureKey(NIFTY)).getLtp());
				tradewareTool
						.setBankNiftyLiveStockPrice(indexLiveDataMap.get(NSE_BANK_NIFTY_KITE_INDEX_KEY).getLtp());
				tradewareTool.setBankNiftyLiveFuturePrice(
						indexLiveDataMap.get(tradewareTool.getKiteFutureKey(BANKNIFTY)).getLtp());
				tradewareTool
						.setFinanceNiftyLiveStockPrice(indexLiveDataMap.get(NSE_NIFTY_FIN_SERVICE_KITE_INDEX_KEY).getLtp());
				tradewareTool.setFinanceNiftyLiveFuturePrice(
						indexLiveDataMap.get(tradewareTool.getKiteFutureKey(FINNIFTY)).getLtp());
				// } catch (JSONException | IOException | KiteException e) {
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL, Constants.METHOD_ADD_INDEXES_TO_TRADE2,
					e, Constants.ERROR_TYPE_KITE_EXCEPTION);
			TradewareLogger.sysoutPrintlnForLocalTest(Constants.CLASS_KITE_CONNECT_TOOL
					+ Constants.METHOD_ADD_INDEXES_TO_TRADE2 + Constants.ERROR_TYPE_KITE_EXCEPTION);
		}
		return indexLiveDataMap;
	}
}
