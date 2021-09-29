package org.tradeware.stockmarket.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tradeware.stockmarket.engine.googlesheettool.GoogleSheetUtil;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;
import org.tradeware.stockmarket.engine.tool.FreeCallDataTool;
import org.tradeware.stockmarket.scanner.BuyOrSellStocksScannerThread_V2;
import org.tradeware.stockmarket.scanner.BuySellFutureScannerThead;
import org.tradeware.stockmarket.util.StockUtil;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.zerodhatech.models.User;
@RestController
public class TradewareRestController {

	//@RequestMapping(value = "test", method = RequestMethod.GET)
	public String initialize() {
		// return "NkpAlgoTradeRestController >> Welcome to nkp algo trader.......";
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress() + "/forceStartApp";
			System.out.println(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "<html><head><title>NKP ALGO TRADER</title></head><form action='" + ip
				+ "'> <p>NkpAlgoTradeRestController >> "
				+ "Welcome to nkp algo trader....... </p> </br></br><input type='submit' value='Submit'> </form></html>";
	}

	private String getCurrentTime() {
		// String PATTERN = "yyyy-MMM-dd hh:mm:ss.SSS";
		String PATTERN = "hh:mm:ss";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN);
		return LocalDateTime.now(ZoneId.of("Asia/Kolkata")).format(dtf);
	}

	// force starters
	//@RequestMapping(value = "forceStartApp", method = RequestMethod.GET)
	public @ResponseBody String setupNkpAlgoTraderTradeDataForcely() {
		String response = "App forcely starting..";
		if (StockUtil.kiteConnectObjectsForAdmins.keySet().contains(StockUtil.DEFAULT_USER)) {
			try {

				// First kill the old instance and clean up all properties
				// NSEIndiaTool.killNSEIndiaToolInstance();

				// Create a fresh instance
				try {
					NSEIndiaTool.getInstance();
					System.out.println("********* NkpAlgoTrader Data set completed ********** at" + getCurrentTime());
					// NSEIndiaTool.getInstance().prepareAndPostToGoogleSheetsTheBuyCallList();
					// NSEIndiaTool.getInstance().prepareAndPostToGoogleSheetsTheSellCallList();
					// System.out.println("********* Buy Sell call data successfuly posted to
					// NkpAlgoTrader Google sheets ********** at" + getCurrentTime());
				} catch (FailingHttpStatusCodeException /* | IOException */ e) {
					System.out.println(
							">>>>> Exception thrown at NKPAlgoTraderSchedulerConfig.setupNkpAlgoTraderTradeData"
									+ e.getMessage());
				}

				try {
					// NSEIndiaTool.getInstance().createUserKiteNkpalgoSession();
					//GoogleSheetUtil.getInstance().createUserKiteSession();
				} catch (FailingHttpStatusCodeException /* | IOException */ e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<String> freeCallList = FreeCallDataTool.getInstance().prepareRandom20StockFreeCall();
				GoogleSheetUtil.getInstance().publishFreeCallDataInitial(freeCallList);
				String adminUser = StockUtil.DEFAULT_USER;
				// for (String adminUser : StockUtil.kiteConnectAdminUsers.keySet()) {
				/*BuyOrSellStocksScannerThread_V2 scan = new BuyOrSellStocksScannerThread_V2(
						StockUtil.kiteConnectObjectsForAdmins.get(adminUser));
				scan.start();*/
				Thread scan = new Thread( new BuyOrSellStocksScannerThread_V2(
						StockUtil.kiteConnectObjectsForAdmins.get(adminUser)));
				scan.start();
				System.out.println("********* NkpAlgoTrader startAdmnBuySellThreads started for user " + adminUser
						+ " ********** at" + getCurrentTime());
				// }
				Thread scanFuturesThread = new Thread( new BuySellFutureScannerThead(
						StockUtil.kiteConnectObjectsForAdmins.get(adminUser)));
						scanFuturesThread.start();
				System.out.println("********* NkpAlgoTrader startAdmnBuySellThreads for futures started for user " + adminUser
						+ " ********** at" + getCurrentTime());
				StockUtil.getAppBean().setAppStatus(1);
				response = "app force started successfully.";
			} catch (FailingHttpStatusCodeException /* O */ e) {
				response = ">>>>> Exception thrown at NKPAlgoTraderSchedulerConfig.setupNkpAlgoTraderTradeData"
						+ e.getMessage();
				System.out.println(response);
			}
		} else {
			response = "Please log into application and establish the kite connect access token....";
		}
		return response;
	}
	
	//Temp
		@RequestMapping(value = "/getOiTrend", method = RequestMethod.GET)
		public String getOiTrend(@RequestParam(value = "symbol") String symbol){
			String oiTrend = "NOT FOUND";
			try {
				if (NSEIndiaTool.getInstance().getOptionChainDataMap().containsKey(symbol)) {
					oiTrend = NSEIndiaTool.getInstance().getOptionChainDataMap().get(symbol).getOITrend();
				}
			} catch(Exception e) {
				oiTrend = "NOT FOUND + Exception";
				//do nothing
			}
			//TelegramMessageTool.getInstance().sendTelegramMessage(symbol +"-"+oiTrend);
			return oiTrend;
		}
		
		//TODO:CONFLICT_ORB @RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
		public String getUserAccessToken(@RequestParam(value = "userId") String userId) {
			String accessToken = "";
			if (StockUtil.kiteConnectAdminUsers.keySet().contains(userId)) {
				User kiteConnectUser = StockUtil.kiteConnectAdminUsers.get(userId);
				accessToken = kiteConnectUser.accessToken;
				//TelegramMessageTool.getInstance().sendTelegramMessage("getAccessToken : "+accessToken);
			}
			return accessToken;
		}
}
