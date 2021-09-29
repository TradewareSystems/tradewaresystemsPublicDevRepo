package org.tradeware.stockmarket.engine.googlesheettool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.tradeware.stockmarket.bean.OptionStockDataBean;
import org.tradeware.stockmarket.bean.StockDataBean;
import org.tradeware.stockmarket.util.Constants;
import org.tradeware.stockmarket.util.StockUtil;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.SessionExpiryHook;
import com.zerodhatech.models.User;

public class GoogleSheetUtil implements GoogleSheetConstants {
	private static GoogleSheetUtil singleton = null;
	private StringBuilder postURL = new StringBuilder("");
	private List<OptionStockDataBean> optionUpdatesToGoogleSheet = null;
	private Gson gson = new GsonBuilder().create();
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	private GoogleSheetUtil() {
		optionUpdatesToGoogleSheet = new ArrayList<OptionStockDataBean>();
	}

	public static GoogleSheetUtil getInstance() {
		if (null == singleton) {
			singleton = new GoogleSheetUtil();
		}
		return singleton;
	}

	public void clearOptionDate() {
		optionUpdatesToGoogleSheet.clear();
	}

	public void addOptionData(OptionStockDataBean bean) {
		optionUpdatesToGoogleSheet.add(bean);
	}

	public void publishOptionCallDataUpdates() {
		Gson gson = new GsonBuilder().create();
		List<Map<String, String>> buyListMap = new ArrayList<Map<String, String>>();
		for (OptionStockDataBean bean : optionUpdatesToGoogleSheet) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("optionSymbol", bean.getKiteOptionKey().replace("NFO:", ""));
			map.put("open", String.valueOf(bean.getOpenOption()));
			map.put("high", String.valueOf(bean.getHighOption()));
			map.put("low", String.valueOf(bean.getLowOption()));
			map.put("close", String.valueOf(bean.getCloseOption()));
			map.put("ltp", String.valueOf(bean.getLtpOption()));
			buyListMap.add(map);
		}
		postURL.delete(0, postURL.length());
		postURL.append(GOOGL_SHEET_POST_URL_OPTION_CALL_DATA_UPDATE).append(gson.toJson(buyListMap));
		// System.out.println("postURL.toString() >>>>>> "+postURL.toString());
		publishDataToGoogleSheets(postURL.toString());
	}

	public void publishFreeCallDataInitial(List<String> freeCallSet) {
		if (!freeCallSet.isEmpty()) {
			list.clear();
			for (String symbol : freeCallSet) {
				Map<String, String> map = new HashMap<String, String>();
				map.put(SYMBOL, symbol);
				map.put(LOTSIZE, StockUtil.getSymbols().get(symbol).toString());
				map.put(BUY, DEFAULT_ZERO);
				map.put(SELL, DEFAULT_ZERO);
				map.put(LTP, DEFAULT_ZERO);
				map.put(OPEN, DEFAULT_ZERO);
				map.put(HIGH, DEFAULT_ZERO);
				map.put(LOW, DEFAULT_ZERO);
				map.put(CLOSE, DEFAULT_ZERO);
				map.put(VOLUMES, DEFAULT_ZERO);
				map.put(BUYQUANTITY, DEFAULT_ZERO);
				map.put(SELLQUANTITY, DEFAULT_ZERO);
				map.put(OI, DEFAULT_ZERO);
				map.put(CHANGE, DEFAULT_ZERO);
				map.put(NKPPROFIT, DEFAULT_ZERO);
				list.add(map);
			}
			postURL.delete(0, postURL.length());
			postURL.append(GOOGL_SHEET_POST_URL_FREE_CALL_DATA_UPDATE).append(gson.toJson(list));
			// System.out.println("postURL.toString() >>>>>> "+postURL.toString());
			publishDataToGoogleSheets(postURL.toString());
		}
	}

	public void publishFreeCallDataUpdates(Map<String, StockDataBean> freeCallsMap ) {
		list.clear();
		for (String symbol : freeCallsMap.keySet()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(SYMBOL, symbol);
			map.put(LOTSIZE, freeCallsMap.get(symbol).getLotSize().toString());
			map.put(BUY, replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getIntradayUptrendValue()));
			map.put(SELL, replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getIntradayDowntrendValue()));
			map.put(LTP, replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getLtp()));
			map.put(OPEN, replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getOpen()));
			map.put(HIGH, replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getHigh()));
			map.put(LOW, replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getLow()));
			map.put(CLOSE, replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getClose()));
			map.put(VOLUMES, replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getVolumeTradedToday()));
			map.put(BUYQUANTITY, replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getBuyQuantity()));
			map.put(SELLQUANTITY, replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getSellQuantity()));
			map.put(OI,  replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getOiToday()));
			map.put(CHANGE,  replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getChange()));
			map.put(NKPPROFIT, replaceNullWithEmptyStr((freeCallsMap.get(symbol)).getProfitLossAmt()));
			list.add(map);
		}
		postURL.delete(0, postURL.length());
		postURL.append(GOOGL_SHEET_POST_URL_FREE_CALL_DATA_UPDATE).append(gson.toJson(list));
		publishDataToGoogleSheets(postURL.toString());
	}

	private void publishDataToGoogleSheets(String dataToPublish) {
		try {
			URL postURLObj = new URL("");//(dataToPublish);
			HttpsURLConnection urlConn = (HttpsURLConnection) postURLObj.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setRequestProperty("User-Agent", "Mozilla/5.0");

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println("***************		postBuySellDataToGoogleSheets		***************   -    "
					+ response.toString());

		} catch (MalformedURLException e) {
			// System.out.println("Error at postBuySellDataToGoogleSheets - \r\n" +
			// e.getMessage());
		} catch (FailingHttpStatusCodeException | IOException e1) {
			// System.out.println("Error at postBuySellDataToGoogleSheets 2222- \r\n" +
			// e1.getMessage());
		} catch (Exception e2) {
			// System.out.println("Error at postBuySellDataToGoogleSheets 333- \r\n" +
			// e2.getMessage());
		}
	}
	
	public String replaceNullWithEmptyStr(Object obj) {
		if (null == obj) {
			if (obj instanceof String) {
				return Constants.EMPTY_STRING;
			} else if (obj instanceof BigDecimal || obj instanceof Double) {
				return DEFAULT_ZERO;
			} else if (obj instanceof Long) {
				return ZERO_STRING;
			} else {
				return Constants.EMPTY_STRING;
			}
		} else {
			return obj.toString();
		}
	}

	// Temporary
	public void createUserKiteSession() {
		try {
			String url = "https://nkpalgotraderWeb.cfapps.io/getAccessToken?userId=AF7508";

			URL postURLObj = new URL(url);
			HttpsURLConnection urlConn = (HttpsURLConnection) postURLObj.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setRequestProperty("User-Agent", "Mozilla/5.0");

			// int responseCode = urlConn.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println("***************		Session creatted in system status		***************   -    "
					+ response.toString());

		} catch (Exception e) {

		}
	}

	private static final String USER_ACCESS_TOKEN = "https://script.google.com/macros/s/AKfycbzXaQkhLSIm6yIqTwR50mwRxMQkIfwQKBrO_oAKN_XzsxlU089Z/exec?action=getUserApi&uid=ZI7952";

	public void createUserKiteNkpalgoSession() {
		StringBuilder postURL = new StringBuilder("");
		postURL.delete(0, postURL.length());
		StringBuffer response = new StringBuffer();
		try {
			postURL.append(USER_ACCESS_TOKEN);
			// System.out.println(postURL.toString());
			URL postURLObj = new URL(postURL.toString());
			HttpsURLConnection urlConn = (HttpsURLConnection) postURLObj.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setRequestProperty("User-Agent", "Mozilla/5.0");

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// :"wERCTjkMhmTdoO7xCpz24xL8THPdglZz"}]}
			response.delete(0, response.lastIndexOf(":\"") + 2);
			response.delete(response.lastIndexOf("\""), response.length());
			// print result
			System.out.println(
					"***************		User access toknen		***************   -    " + response.toString());

		} catch (MalformedURLException e) {
			System.out.println("Error at User access toknen - \r\n" + e.getMessage());
		} catch (FailingHttpStatusCodeException | IOException e1) {
			System.out.println("Error at User access toknen 2222- \r\n" + e1.getMessage());
		} catch (Exception e2) {
			System.out.println("Error at User access toknen 333- \r\n" + e2.getMessage());
		}

		String userId = "ZI7952";

		if (!StockUtil.getAdmins().containsKey(userId)) {
			// return new ResponseEntity<>("Invaliduser, Not an admin user.",
			// HttpStatus.BAD_REQUEST);
		} /*
			 * else if (StockUtil.kiteConnectObjectsForAdmins.keySet().contains(userId) &&
			 * StockUtil.kiteConnectAdminUsers.keySet().contains(userId)) { return new
			 * ResponseEntity<>("Nkp algo trader admin user " + userId +
			 * " alrady logged in. And setup the Nkp algo trader data for the day.",
			 * HttpStatus.OK); }
			 */

		try {
			KiteConnect kiteConnect = null;
			kiteConnect = new KiteConnect(StockUtil.getAdmins().get(userId));
			kiteConnect.setUserId(userId);
			kiteConnect.setAccessToken(response.toString());

			System.out.println(
					"*******************kiteConnect created for userId = " + userId + " *****************************");
			kiteConnect.setSessionExpiryHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					System.out.println("session expired");
				}
			});

			/*
			 * The request token can to be obtained after completion of login process. Check
			 * out https://kite.trade/docs/connect/v1/#login-flow for more information. A
			 * request token is valid for only a couple of minutes and can be used only
			 * once. An access token is valid for one whole day. Don't call this method for
			 * every app run. Once an access token is received it should be stored in
			 * preferences or database for further usage.
			 */

			StockUtil.kiteConnectObjectsForAdmins.put(userId, kiteConnect);
			StockUtil.kiteConnectAdminUsers.put(userId, new User());
			System.out.println(StockUtil.kiteConnectAdminUsers.size());
			if (StockUtil.DEFAULT_USER.equals(userId)) {
				// prepareFreeCallsData(kiteConnect);
			}
		} /*
			 * catch (KiteException e) { System.out.println("kiteRedirect - " + e.message +
			 * " " + e.code + " " + e.getClass().getName()); }
			 */ catch (Exception e) {
			e.printStackTrace();
		} /*
			 * catch (IOException e) { e.printStackTrace(); }
			 */
		// return new ResponseEntity<>("KiteConnect-Nkpalgotrader session successfully
		// crated..", HttpStatus.OK);
		System.out.println("KiteConnect-Nkpalgotrader session successfully crated..");

	}
}
