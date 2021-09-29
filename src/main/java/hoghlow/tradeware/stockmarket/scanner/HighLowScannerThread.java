package hoghlow.tradeware.stockmarket.scanner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;

import com.tradeware.stockmarket.util.StockUtil;
import com.zerodhatech.kiteconnect.KiteConnect;

import hoghlow.tradeware.stockmarket.bean.StockDataBean;
import hoghlow.tradeware.stockmarket.helper.DatabaseHelper;
import hoghlow.tradeware.stockmarket.tool.HighLowStrategyTool;
import hoghlow.tradeware.stockmarket.tool.KiteConnectTool;
import hoghlow.tradeware.stockmarket.util.Constants;

public class HighLowScannerThread extends Thread implements Constants {

	private KiteConnect getKiteConnect() {
		return StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER);
	}

	@Override
	public void run() {

		while (HighLowStrategyTool.getInstance().isTradingTime()) {
			if (!HighLowStrategyTool.getInstance().getHighLowMap1200().isEmpty()) {
				ConcurrentHashMap<String, StockDataBean> map = HighLowStrategyTool.getInstance().getHighLowMap1200();
				ConcurrentHashMap<String, StockDataBean> updatedMap = HighLowStrategyTool.getInstance()
						.updateDayOHLC(map);
				HighLowStrategyTool.getInstance().setHighLowMap1200(updatedMap);
				if (null != getKiteConnect() && HighLowStrategyTool.scanForBuyOrSell) {
					highLowStrategyBuySellScanner_1200();
					profitLossMonitrForHighLowStrategy_1200();
				} else if (null != getKiteConnect() && !HighLowStrategyTool.canPlaceOrders) {
					profitLossMonitrForHighLowStrategy_1200();
				}
				/*
				 * if (runCounter > 500000000) { outTime = System.currentTimeMillis();
				 * NkpAlgoLogger.printWithNewLine(runCounter + SCANNER_PROGRESS_STRING +
				 * ((outTime - inTime) / 1000d)); runCounter = 0; inTime =
				 * System.currentTimeMillis(); } else { runCounter++; }
				 */
				HighLowStrategyTool.getInstance()
						.setThreadScannerLastRunnngSatusTime(HighLowStrategyTool.getInstance().getCurrentDateTime());
			}
		}
	}
	/*private static int runCounter;
	private static long inTime = System.currentTimeMillis();
	private static long outTime = System.currentTimeMillis();*/

	private boolean validateBuyerSellerRule_1200(StockDataBean bean) {
		boolean buyerSellerRule = true;
		double buyerSellerDiff = HighLowStrategyTool.getInstance().getRoundupValue2((bean.getSellerAt() - bean.getBuyerAt()) * bean.getLotSize());
		bean.setBuyerSellerDiff(buyerSellerDiff);
		if (bean.getSellerAt() == 0 || bean.getBuyerAt() == 0 || buyerSellerDiff > 750/* 200 */) {
			bean.setSignalTriggeredInAt(getCurrentTime() + " @ BUYER-SELLER DIFF FAILED @ " + buyerSellerDiff);
			HighLowStrategyTool.getInstance().getHighLowMap1200().replace(bean.getKiteFutureKey(), bean);
			bean.setTradedState(Constants.BUYER_SELLER_DIFF_FAILED);
			buyerSellerRule = false;
		} 
		if (bean.isSmallCandle()) {
			//bean.setTradedState("SMALL_CANDLE");
			//buyerSellerRule = false;
			bean.setAdditinalInfo(bean.getAdditinalInfo() + COMMA_SPACE + "SMALL_CANDLE");
		}
		if (bean.getMinute5CPR()<=0 || bean.getMinute15CPR() <= 0) {
			//bean.setTradedState("SMALL_CANDLE");
			//buyerSellerRule = false;
			bean.setCprRuleInd(true);
			bean.setAdditinalInfo(bean.getAdditinalInfo() + COMMA_SPACE + "CPR RULE FAIL");
		}
		return buyerSellerRule;
	}

	private String getCurrentTime() {
		String PATTERN = "hh:mm:ss";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN);
		return LocalDateTime.now(ZoneId.of("Asia/Kolkata")).format(dtf);
	}

	// Target 1200
	public void highLowStrategyBuySellScanner_1200() {
		for (String symbol : HighLowStrategyTool.getInstance().getHighLowMap1200().keySet()) {
			StockDataBean bean = HighLowStrategyTool.getInstance().getHighLowMap1200().get(symbol);
			if (null != bean) {
				if (NA.equals(bean.getTradedState())
						&& (BUY.equals(bean.getTradableState()) || SELL.equals(bean.getTradableState()))) {
					
					double lastPrice = bean.getLtp();
					if (BUY.equals(bean.getTradableState()) && null != bean.getBuyerAt()
							&& bean.getBuyerAt() >= bean.getBuyOrSellAt() && lastPrice >= bean.getBuyOrSellAt()
							&& validateBuyerSellerRule_1200(bean)) {
						//HighLowStrategyTool.getInstance().caluclateRSI(bean);
						bean.setTradedState(BUY);
						bean.setTradedVal(bean.getBuyerAt());
						bean.setTradedInAt(HighLowStrategyTool.getInstance().getCurrentDateTimeAsDate());
						bean.setSignalTriggeredInAt(getCurrentTime() + BUY_ACTIVATE + bean.getBuyerAt());

						bean = HighLowStrategyTool.getInstance().get5MinutueCandleDataOnTradeEntryForVolumes(bean);
						/*bean = HighLowStrategyTool.getInstance()
								.get5MinutueCandleDataOnTradeEntryForVolumesForNifty50(bean);*/
						bean.setAdditionalInfoNifty50(HighLowStrategyTool.getInstance().getNifty50Bean().getAdditinalInfo());
						bean.setStrenthTradableStateNifty(HighLowStrategyTool.getInstance().getNifty50Bean().getStrenthTradableState());
						bean.setAdditinalInfo(bean.getAdditinalInfo()+NIFTY_50_STRENGTH_TREND+bean.getStrengthFactorString());
						
						if (HighLowStrategyTool.getInstance().canPlaceOrderBasedAlgoRules(bean)) {
							//bean = KiteConnectTool.getInstance().placeBracketOrder(bean);
							bean = KiteConnectTool.getInstance().placeCoverOrder(bean);
						}
						Integer itemId = DatabaseHelper.getInstance().saveTradeOnEntry(bean);
						bean.setStockId(String.valueOf(itemId));
						HighLowStrategyTool.getInstance().getHighLowMap1200().replace(symbol, bean);

						bean.setReportListKey(symbol + UNDER_SCRORE + getCurrentTime());
						//HighLowStrategyTool.getInstance().getHighLowMap1200Report().put(bean.getReportListKey(), bean);
					} /*else if (SELL.equals(bean.getTradableState()) && null != bean.getBuyerAt()
							&& bean.getBuyerAt() <= bean.getBuyOrSellAt() && lastPrice <= bean.getBuyOrSellAt()
							&& validateBuyerSellerRule_1200(bean)) {*/
					else if (SELL.equals(bean.getTradableState()) && null != bean.getSellerAt()
							&& bean.getSellerAt() <= bean.getBuyOrSellAt() && lastPrice <= bean.getBuyOrSellAt()
							&& validateBuyerSellerRule_1200(bean)) {
						//HighLowStrategyTool.getInstance().caluclateRSI(bean);
						bean.setTradedState(SELL);
						bean.setTradedVal(bean.getSellerAt());
						bean.setTradedInAt(HighLowStrategyTool.getInstance().getCurrentDateTimeAsDate());
						bean.setSignalTriggeredInAt(getCurrentTime() + SELL_ACTIVATE + bean.getSellerAt());

						bean = HighLowStrategyTool.getInstance().get5MinutueCandleDataOnTradeEntryForVolumes(bean);
						/*bean = HighLowStrategyTool.getInstance()
								.get5MinutueCandleDataOnTradeEntryForVolumesForNifty50(bean);*/
						bean.setAdditionalInfoNifty50(HighLowStrategyTool.getInstance().getNifty50Bean().getAdditinalInfo());
						bean.setStrenthTradableStateNifty(HighLowStrategyTool.getInstance().getNifty50Bean().getStrenthTradableState());
						bean.setAdditinalInfo(bean.getAdditinalInfo()+NIFTY_50_STRENGTH_TREND+bean.getStrengthFactorString());
						
						
						if (HighLowStrategyTool.getInstance().canPlaceOrderBasedAlgoRules(bean)) {
							//bean = KiteConnectTool.getInstance().placeBracketOrder(bean);
							bean = KiteConnectTool.getInstance().placeCoverOrder(bean);
						}
						Integer itemId = DatabaseHelper.getInstance().saveTradeOnEntry(bean);
						bean.setStockId(String.valueOf(itemId));
						
						HighLowStrategyTool.getInstance().getHighLowMap1200().replace(symbol, bean);

						bean.setReportListKey(symbol + UNDER_SCRORE + getCurrentTime());
						//HighLowStrategyTool.getInstance().getHighLowMap1200Report().put(bean.getReportListKey(), bean);
					}
				}
			}
		}
	}

	public void profitLossMonitrForHighLowStrategy_1200() {
		for (String symbol : HighLowStrategyTool.getInstance().getHighLowMap1200().keySet()) {
			StockDataBean bean = HighLowStrategyTool.getInstance().getHighLowMap1200().get(symbol);
			
			double lastPrice = bean.getLtp().doubleValue();
			double targetPrice = HighLowStrategyTool.getInstance().getRoundupValue2((double)2000 / bean.getLotSize());
			/*double targetPriceStoploss = targetPrice;
			if (null != bean.getPositiveDirectionProfit() && bean.getPositiveDirectionProfit()>500) {
				targetPriceStoploss = new BigDecimal((double)500 / bean.getLotSize()).setScale(2, 0).doubleValue();
			}*/
			
			if (BUY.equals(bean.getTradedState())) {
				/*double baseStopLossPrice = (bean.getPositiveDirectionLtp() != null
						&& bean.getPositiveDirectionLtp().doubleValue() > bean.getTradedVal().doubleValue())
								? bean.getPositiveDirectionLtp().doubleValue()
								: bean.getTradedVal().doubleValue();
				double stopLoss = bean.getLow_5MinCandle().doubleValue() > (baseStopLossPrice - targetPriceStoploss)
						? bean.getLow_5MinCandle().doubleValue()
						: (baseStopLossPrice - targetPriceStoploss);*/
				//double stopLoss = bean.getStopLoss();
				double stopLoss = bean.getStopLoss();
				if (null !=bean.getPositiveDirectionLtp()) {
					stopLoss = bean.getStopLoss() + (bean.getPositiveDirectionLtp() - bean.getTradedVal());
				}
				/*if (null != bean.getPositiveDirectionProfit() && bean.getPositiveDirectionProfit().doubleValue()  > 1500d) {
					stopLoss = bean.getTradedVal();	
				} */
			
				if ((bean.getBuyerAt() > (bean.getTradedVal().doubleValue() + targetPrice)) || null != bean.getProfitChaseVal() /*&& lastPrice != 0*/) {
					if (null == bean.getProfitChaseVal() || bean.getBuyerAt() > bean.getProfitChaseVal().doubleValue()) {
						bean.setProfitChaseVal(bean.getBuyerAt());
						continue;
					}
					bean.setTradedState(BUY_EXIT_PROFIT);
					bean.setTradedOutAt(HighLowStrategyTool.getInstance().getCurrentDateTimeAsDate());
					double netProfitVal = HighLowStrategyTool.getInstance().getRoundupValue2(
							(bean.getBuyerAt() - bean.getTradedVal().doubleValue()) * bean.getLotSize());
					bean.setProfitLossAmt(netProfitVal);
					bean.setSignalTriggeredOutAt(getCurrentTime() + BUY_TARGET + bean.getBuyerAt()
							+ WITH_PROFIT + netProfitVal);
					
					//KiteConnectTool.getInstance().cancelBracketOrder(bean);
					bean=KiteConnectTool.getInstance().cancelCoverOrder(bean);
					DatabaseHelper.getInstance().updateTrade(bean);
					
					HighLowStrategyTool.getInstance().getHighLowMap1200().replace(symbol, bean);
					
					//HighLowStrategyTool.getInstance().getHighLowMap1200Report().replace(bean.getReportListKey(), bean.clone());
					
				} else if (bean.getSellerAt() <= stopLoss  || (null != bean.getNegativeDirectionLoss() && bean.getNegativeDirectionLoss().doubleValue() <= -TARGET_CO_STOP_LOSS)) {
					bean.setTradedState(BUY_EXIT_LOSS);
					bean.setTradedOutAt(HighLowStrategyTool.getInstance().getCurrentDateTimeAsDate());
					double netLossVal = HighLowStrategyTool.getInstance().getRoundupValue2(
							(bean.getSellerAt() - bean.getTradedVal().doubleValue()) * bean.getLotSize());
					bean.setProfitLossAmt(netLossVal);
					bean.setSignalTriggeredOutAt(
							getCurrentTime() + CLOSED_WITH_LOSE + bean.getSellerAt() + WITH_LOSS + netLossVal );//+ additionalMsg);
					
					//KiteConnectTool.getInstance().cancelBracketOrder(bean);
					bean=KiteConnectTool.getInstance().cancelCoverOrder(bean);
					DatabaseHelper.getInstance().updateTrade(bean);
					
					HighLowStrategyTool.getInstance().getHighLowMap1200().replace(symbol, bean);

					//HighLowStrategyTool.getInstance().getHighLowMap1200Report().replace(bean.getReportListKey(), bean.clone());
					
				}  else {// track the price moment 
					double netProfitLossVal = HighLowStrategyTool.getInstance().getRoundupValue2(
							(lastPrice - bean.getTradedVal().doubleValue()) * bean.getLotSize());
					if (null == bean.getNegativeDirectionLoss() || netProfitLossVal < bean.getNegativeDirectionLoss().doubleValue()) {
						bean.setNegativeDirectionLoss(netProfitLossVal);
						bean.setNegativeDirectionLtp(lastPrice);
						bean.setProfitLossAmt(netProfitLossVal);
					} else if (null == bean.getProfitLossAmt() || netProfitLossVal > bean.getPositiveDirectionProfit().doubleValue()) {
						bean.setPositiveDirectionLtp(lastPrice);
						bean.setPositiveDirectionProfit(netProfitLossVal);
						bean.setProfitLossAmt(netProfitLossVal);	
					}
					HighLowStrategyTool.getInstance().getHighLowMap1200().replace(symbol, bean);

					//HighLowStrategyTool.getInstance().getHighLowMap1200Report().replace(bean.getReportListKey(), bean);
					
				}
			
			} else if (SELL.equals(bean.getTradedState())) {
				/*double stopLoss = bean.getHigh_5MinCandle().doubleValue() < (bean.getTradedVal().doubleValue() + targetPrice)
								? bean.getHigh_5MinCandle().doubleValue()
								: (bean.getTradedVal().doubleValue() + targetPrice);*/
				/*double baseStopLossPrice = (bean.getPositiveDirectionLtp() != null
						&& bean.getPositiveDirectionLtp().doubleValue() < bean.getTradedVal().doubleValue())
								? bean.getPositiveDirectionLtp().doubleValue()
								: bean.getTradedVal().doubleValue();
				double stopLoss = bean.getHigh_5MinCandle().doubleValue() < (baseStopLossPrice + targetPriceStoploss)
						? bean.getHigh_5MinCandle().doubleValue()
						: (baseStopLossPrice + targetPriceStoploss);*/
				double stopLoss = bean.getStopLoss();
				/*if (null != bean.getPositiveDirectionProfit() && bean.getPositiveDirectionProfit().doubleValue()  > 1200d) {
					stopLoss = bean.getTradedVal();	
				} */
				if (null !=bean.getPositiveDirectionLtp()) {
					stopLoss = bean.getStopLoss() - (bean.getTradedVal() - bean.getPositiveDirectionLtp());
				}
				
				if ((bean.getSellerAt() < (bean.getTradedVal().doubleValue() - targetPrice)) || null != bean.getProfitChaseVal() /*&& lastPrice != 0*/) {
					if (null == bean.getProfitChaseVal() || bean.getSellerAt() < bean.getProfitChaseVal().doubleValue()) {
						bean.setProfitChaseVal(bean.getSellerAt());
						continue;
					}
					bean.setTradedState(SELL_EXIT_PROFIT);
					bean.setTradedOutAt(HighLowStrategyTool.getInstance().getCurrentDateTimeAsDate());
					double netProfitVal = HighLowStrategyTool.getInstance().getRoundupValue2(
							(bean.getTradedVal().doubleValue() - bean.getSellerAt()) * bean.getLotSize());
					bean.setProfitLossAmt(netProfitVal);
					bean.setSignalTriggeredOutAt(
							getCurrentTime() + SELL_TARGET + bean.getSellerAt() + WITH_PROFIT + netProfitVal);
					
					//KiteConnectTool.getInstance().cancelBracketOrder(bean);
					bean=KiteConnectTool.getInstance().cancelCoverOrder(bean);
					DatabaseHelper.getInstance().updateTrade(bean);
					
					HighLowStrategyTool.getInstance().getHighLowMap1200().replace(symbol, bean);

					//HighLowStrategyTool.getInstance().getHighLowMap1200Report().replace(bean.getReportListKey(), bean.clone());
					
				}  else if (bean.getBuyerAt() >= stopLoss || (null != bean.getNegativeDirectionLoss() && bean.getNegativeDirectionLoss().doubleValue() <= -TARGET_CO_STOP_LOSS)) {
					bean.setTradedState(SELL_EXIT_LOSS);
					bean.setTradedOutAt(HighLowStrategyTool.getInstance().getCurrentDateTimeAsDate());
					double netLossVal = HighLowStrategyTool.getInstance().getRoundupValue2(
							(bean.getTradedVal().doubleValue() - bean.getBuyerAt()) * bean.getLotSize());
					bean.setProfitLossAmt(netLossVal);
					bean.setSignalTriggeredOutAt(
							getCurrentTime() + CLOSED_WITH_LOSE + bean.getBuyerAt() + WITH_LOSS + netLossVal);
					
					//KiteConnectTool.getInstance().cancelBracketOrder(bean);
					bean=KiteConnectTool.getInstance().cancelCoverOrder(bean);
					DatabaseHelper.getInstance().updateTrade(bean);
					
					HighLowStrategyTool.getInstance().getHighLowMap1200().replace(symbol, bean);

					//HighLowStrategyTool.getInstance().getHighLowMap1200Report().replace(bean.getReportListKey(), bean.clone());
					
				} else {// track the price moment 
					double netProfitLossVal = HighLowStrategyTool.getInstance().getRoundupValue2(
							(bean.getTradedVal().doubleValue() - lastPrice) * bean.getLotSize());
					if (null == bean.getNegativeDirectionLoss() || netProfitLossVal < bean.getNegativeDirectionLoss().doubleValue()) {
						bean.setNegativeDirectionLoss(netProfitLossVal);
						bean.setNegativeDirectionLtp(lastPrice);
						bean.setProfitLossAmt(netProfitLossVal);
					} else if (null == bean.getProfitLossAmt() || netProfitLossVal > bean.getPositiveDirectionProfit().doubleValue()) {
						bean.setPositiveDirectionLtp(lastPrice);
						bean.setPositiveDirectionProfit(netProfitLossVal);
						bean.setProfitLossAmt(netProfitLossVal);	
					}
					HighLowStrategyTool.getInstance().getHighLowMap1200().replace(symbol, bean);

					//HighLowStrategyTool.getInstance().getHighLowMap1200Report().replace(bean.getReportListKey(), bean);
					
				}
			}
		}
	}

}
