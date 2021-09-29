package nr4.sathvikashviktechtrader.stockmarket.scanner;

import java.util.concurrent.ConcurrentHashMap;

import com.zerodhatech.kiteconnect.KiteConnect;

import nr4.sathvikashviktechtrader.stockmarket.bean.Narrow7DataBean;
import nr4.sathvikashviktechtrader.stockmarket.bean.StockDataBean;
import nr4.sathvikashviktechtrader.stockmarket.helper.DatabaseHelper;
import nr4.sathvikashviktechtrader.stockmarket.tool.KiteConnectTool;
import nr4.sathvikashviktechtrader.stockmarket.tool.NR7AndUTCommonTool;
import nr4.sathvikashviktechtrader.stockmarket.util.Constants;
import nr4.sathvikashviktechtrader.stockmarket.util.StockUtil;

public class NR7andUTScannerThread extends Thread {
	public static final String BUY = "BUY";
	public static final String SELL = "SELL";
	public static final String ACTIVATED = "Activated";

	private KiteConnect getKiteConnect() {
		return StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER);
	}

	@Override
	public void run() {
		while (NR7AndUTCommonTool.getInstance().isTradingTime()) {
			if (!NR7AndUTCommonTool.getInstance().getMonitorNR7().isEmpty()) {
				ConcurrentHashMap<String, Narrow7DataBean> map = NR7AndUTCommonTool.getInstance().getMonitorNR7();
				ConcurrentHashMap<String, Narrow7DataBean> updatedMap = NR7AndUTCommonTool.getInstance()
						.updateDayOHLC(map);
				//System.out.println("1 -"+map.size());
				NR7AndUTCommonTool.getInstance().setMonitorNR7(updatedMap);
				if (null != getKiteConnect() && NR7AndUTCommonTool.isScanForBuyOrSell()) {
					nr7StrategyBuySellScanner();
					profitLossMonitrForNR7();
				} else if (null != getKiteConnect() /* && !NR7AndUTCommonTool.canPlaceOrders */) {
					profitLossMonitrForNR7();
				}
			}
			StockUtil.setThreadScannerLastRunnngSatusTime(NR7AndUTCommonTool.getInstance().getCurrentDateTime());
		}
	}

	private boolean validateBuyerSellerRule(Narrow7DataBean bean) {
		boolean buyerSellerRule = true;
		double buyerSellerDiff = NR7AndUTCommonTool.getInstance()
				.getRoundupValue((bean.getSellerAt() - bean.getBuyerAt()) * bean.getLotSize());
		bean.setBuyerSellerDiff(buyerSellerDiff);
		if (bean.getSellerAt() == 0 || bean.getBuyerAt() == 0 || buyerSellerDiff > 1000/* 750 */) {
			bean.setSignalTriggeredInAt(NR7AndUTCommonTool.getInstance().getCurrentTime()
					+ Constants.BUYER_SELLER_DIFF_FAILED_MSG + buyerSellerDiff);
			NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(bean.getKiteFutureKey(), bean);
			bean.setTradedState(Constants.BUYER_SELLER_DIFF_FAILED);
			buyerSellerRule = false;

			/*
			 * String reportKey = bean.getKiteFutureKey() + Constants.UNDER_SCORE +
			 * NR7AndUTCommonTool.getInstance().getCurrentTime();
			 * bean.setReportKey(reportKey);
			 * NR7AndUTCommonTool.getInstance().getReportDataMap().put(reportKey, bean);
			 */
			NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(bean.getKiteFutureKey(), bean);
		}
		if (bean.getCandle1SizeAmt() < 2500) {
			buyerSellerRule = false;
			bean.setTradedState(Constants.CANDLE_MOVEMENT_RULE_FAILED);
			/*
			 * String reportKey = bean.getKiteFutureKey() + Constants.UNDER_SCORE +
			 * NR7AndUTCommonTool.getInstance().getCurrentTime();
			 * bean.setReportKey(reportKey);
			 * NR7AndUTCommonTool.getInstance().getReportDataMap().put(reportKey, bean);
			 */
			NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(bean.getKiteFutureKey(), bean);
		}
		return buyerSellerRule;
	}

	public void nr7StrategyBuySellScanner() {
		for (String symbol : NR7AndUTCommonTool.getInstance().getMonitorNR7().keySet()) {
			Narrow7DataBean bean = NR7AndUTCommonTool.getInstance().getMonitorNR7().get(symbol);
			if (Constants.NA.equals(bean.getBuyStatus()) || Constants.NA.equals(bean.getSellStatus())) {
				double lastPrice = bean.getLtp().doubleValue();
				double open = bean.getOpen().doubleValue();

				/*if (0 == open || 0 == lastPrice ||  0 == bean.getSellerAt() ||  0 == bean.getBuyerAt()) {
					NR7AndUTCommonTool.getInstance().getMonitorNR7().put(symbol, bean);
					continue;
				} else if (bean.getOpen().doubleValue() > bean.getBuyAtVal()) {
					bean.setBuyStatus("Disqualified (Gap up)");
					NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(symbol, bean);
					continue;
				} else if (bean.getOpen().doubleValue()  < bean.getSellAtVal()) {
					bean.setSellStatus("Disqualified (Gap down)");
					NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(symbol, bean);
					continue;
				}*/

				if (Constants.NA.equals(bean.getBuyStatus()) && null != bean.getBuyerAt()
						&& bean.getBuyerAt() >= bean.getBuyAtVal()
						&& lastPrice > bean.getBuyAtVal() && validateBuyerSellerRule(bean)) {
					lastPrice = bean.getSellerAt();

					bean.setTradableState(Constants.BUY);
					bean.setBuyStatus(ACTIVATED);
					bean.setBuyOrSellAt(bean.getSellerAt());// (bean.getBuyAtVal()/*+Constants.TOP_TRADER_INCREAMENT*/);
					bean.setStopLoss(bean.getSellAtVal());

					bean.setTradedState(Constants.BUY);
					bean.setTradedVal(bean.getSellerAt());
					bean.setTradedInAt(NR7AndUTCommonTool.getInstance().getCurrentDateTimeAsDate());
					bean.setSignalTriggeredInAt(NR7AndUTCommonTool.getInstance().getCurrentTime()
							+ Constants.BUY_ACTIVATED_MSG + bean.getSellerAt());
					// bean =
					// NR7AndUTCommonTool.getInstance().updateDayOHLC_OI_String(bean);

					if (NR7AndUTCommonTool.getInstance().canPlaceOrderBasedAlgoRules(bean)) {
						bean.setTempCustomTradingRuleInd(true);
						// bean = KiteConnectTool.getInstance().placeBracketOrder(bean);
						bean = (Narrow7DataBean) KiteConnectTool.getInstance()
								.placeCoverOrder((StockDataBean) bean);
					}

					Integer itemId = DatabaseHelper.getInstance().saveTradeOnEntry(bean);
					bean.setSymbolId(String.valueOf(itemId));
					NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(symbol, bean);
				} else if (Constants.NA.equals(bean.getSellStatus()) && null != bean.getSellerAt()
						&& bean.getSellerAt() <= bean.getSellAtVal()
						&& lastPrice < bean.getSellAtVal() && validateBuyerSellerRule(bean)) {
					lastPrice = bean.getBuyerAt();

					bean.setSellStatus(ACTIVATED);
					bean.setTradableState(Constants.SELL);
					bean.setBuyOrSellAt(bean.getBuyerAt());// bean.getSellAtVal());
					bean.setStopLoss(bean.getBuyAtVal());

					bean.setTradedState(Constants.SELL);
					bean.setTradedVal(bean.getBuyerAt());// bean.getSellerAt());
					bean.setTradedInAt(NR7AndUTCommonTool.getInstance().getCurrentDateTimeAsDate());
					bean.setSignalTriggeredInAt(NR7AndUTCommonTool.getInstance().getCurrentTime()
							+ Constants.SELL_ACTIVATED_MSG + bean.getBuyerAt());// bean.getSellerAt());
					// bean =
					// NR7AndUTCommonTool.getInstance().updateDayOHLC_OI_String(bean);

					if (NR7AndUTCommonTool.getInstance().canPlaceOrderBasedAlgoRules(bean)) {
						bean.setTempCustomTradingRuleInd(true);
						// bean = KiteConnectTool.getInstance().placeBracketOrder(bean);
						bean = (Narrow7DataBean) KiteConnectTool.getInstance()
								.placeCoverOrder((StockDataBean) bean);
					}
					Integer itemId = DatabaseHelper.getInstance().saveTradeOnEntry(bean);
					bean.setSymbolId(String.valueOf(itemId));
					NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(symbol, bean);
				}
			}
		}
	}

	public void profitLossMonitrForNR7() {
		for (String symbol : NR7AndUTCommonTool.getInstance().getMonitorNR7().keySet()) {
			Narrow7DataBean bean = NR7AndUTCommonTool.getInstance().getMonitorNR7().get(symbol);
			double lastPrice = bean.getLtp().doubleValue();
//System.out.println(symbol+"---"+bean.getBuyStatus()+" -- "+bean.getTradedState());
			if (ACTIVATED.equals(bean.getBuyStatus()) && Constants.BUY.equals(bean.getTradedState())) {

				lastPrice = bean.getBuyerAt();
				double stopLoss = bean.getStopLoss();
				if (null != bean.getPositiveDirectionLtp()) {
					stopLoss = bean.getStopLoss() + (bean.getPositiveDirectionLtp() - bean.getTradedVal());
				}
				double targetPrice = bean.getTargetPrice();
				if (targetPrice == 0) {
					targetPrice = NR7AndUTCommonTool.getInstance().getRoundupValue2((double) 2000 / bean.getLotSize());
					targetPrice = targetPrice + bean.getTradedVal().doubleValue();
				}
	//System.out.println(symbol+" - bean.getBuyerAt() - "+bean.getBuyerAt()+" - bean.getProfitChaseVal()"+bean.getProfitChaseVal());
				if ((bean.getBuyerAt() > targetPrice) || null != bean.getProfitChaseVal()) {
					if (null == bean.getProfitChaseVal()
							|| bean.getBuyerAt() > bean.getProfitChaseVal().doubleValue()) {
						bean.setProfitChaseVal(bean.getBuyerAt());
						NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(symbol, bean);
						System.out.println(symbol+" - bean.getBuyerAt(2) - "+bean.getBuyerAt()+" - bean.getProfitChaseVal(2)"+bean.getProfitChaseVal());
						continue;
					}
					bean.setTradedState(Constants.BUY_EXIT_PROFIT);
					System.out.println(symbol+" - bean.setTradedState - "+bean.getTradedState());
					bean.setBuyStatus(Constants.BUY_EXIT_PROFIT);
					System.out.println(symbol+" - bean.setBuyStatus - "+bean.getBuyStatus());
					bean.setTradedOutAt(NR7AndUTCommonTool.getInstance().getCurrentDateTimeAsDate());
					System.out.println(symbol+" - bean.setTradedOutAt - "+bean.getTradedOutAt());
					double netProfitVal = NR7AndUTCommonTool.getInstance().getRoundupValue2(
							(bean.getBuyerAt() - bean.getTradedVal().doubleValue()) * bean.getLotSize());
					bean.setProfitLossAmt(netProfitVal);
					System.out.println(symbol+" - bean.setProfitLossAmt - "+bean.getProfitLossAmt());
					bean.setSignalTriggeredOutAt(NR7AndUTCommonTool.getInstance().getCurrentTime()
							+ Constants.BUY_TARGET + bean.getBuyerAt() + Constants.WITH_PROFIT + netProfitVal);
					System.out.println(symbol+" - bean.setSignalTriggeredOutAt - "+bean.getSignalTriggeredOutAt());
					// KiteConnectTool.getInstance().cancelBracketOrder(bean);
					//bean = (Narrow7DataBean) KiteConnectTool.getInstance().cancelCoverOrder((StockDataBean) bean);
					System.out.println(symbol+"KiteConnectTool.getInstance().cancelCoverOrder");
					DatabaseHelper.getInstance().updateTrade(bean);
					System.out.println("DatabaseHelper.getInstance().updateTrade");
					NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(symbol, bean);
					System.out.println("NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(");

				} else if (bean.getBuyerAt() <= stopLoss || (null != bean.getNegativeDirectionLoss()
						&& bean.getNegativeDirectionLoss().doubleValue() <= -Constants.TARGET_CO_STOP_LOSS)) {
					bean.setTradedState(Constants.BUY_EXIT_LOSS);
					bean.setBuyStatus(Constants.BUY_EXIT_LOSS);
					bean.setTradedOutAt(NR7AndUTCommonTool.getInstance().getCurrentDateTimeAsDate());
					double netLossVal = NR7AndUTCommonTool.getInstance().getRoundupValue2(
							(bean.getBuyerAt() - bean.getTradedVal().doubleValue()) * bean.getLotSize());
					bean.setProfitLossAmt(netLossVal);
					bean.setSignalTriggeredOutAt(NR7AndUTCommonTool.getInstance().getCurrentTime()
							+ Constants.CLOSED_WITH_LOSE + bean.getBuyerAt() + Constants.WITH_LOSS + netLossVal);

					// KiteConnectTool.getInstance().cancelBracketOrder(bean);
					//bean = (Narrow7DataBean) KiteConnectTool.getInstance().cancelCoverOrder((StockDataBean) bean);
					DatabaseHelper.getInstance().updateTrade(bean);
					NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(symbol, bean);
				} else {// track the price moment
					double netProfitLossVal = NR7AndUTCommonTool.getInstance()
							.getRoundupValue2((lastPrice - bean.getTradedVal().doubleValue()) * bean.getLotSize());

					if (null == bean.getNegativeDirectionLoss()
							|| netProfitLossVal < bean.getNegativeDirectionLoss().doubleValue()) {
						bean.setNegativeDirectionLoss(netProfitLossVal);
						bean.setNegativeDirectionLtp(lastPrice);
						bean.setProfitLossAmt(netProfitLossVal);
					} else if (null == bean.getProfitLossAmt()
							|| netProfitLossVal > bean.getPositiveDirectionProfit().doubleValue()) {
						bean.setPositiveDirectionLtp(lastPrice);
						bean.setPositiveDirectionProfit(netProfitLossVal);
						bean.setProfitLossAmt(netProfitLossVal);
					}
					NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(bean.getKiteFutureKey(), bean);
				}
			} else if (ACTIVATED.equals(bean.getSellStatus()) && Constants.SELL.equals(bean.getTradedState())) {
				lastPrice = bean.getSellerAt();
				double stopLoss = bean.getStopLoss();
				if (null != bean.getPositiveDirectionLtp()) {
					stopLoss = bean.getStopLoss() - (bean.getTradedVal() - bean.getPositiveDirectionLtp());
				}
				double targetPrice = bean.getTargetPrice();
				if (targetPrice == 0) {
					targetPrice = NR7AndUTCommonTool.getInstance().getRoundupValue2((double) 2000 / bean.getLotSize());
					targetPrice = bean.getTradedVal().doubleValue() - targetPrice;
				}
	//System.out.println(symbol+" - bean.getBuyerAt() - "+bean.getBuyerAt()+" - bean.getProfitChaseVal()"+bean.getProfitChaseVal());
				if ((bean.getSellerAt() < targetPrice) || null != bean.getProfitChaseVal()) {
					if (null == bean.getProfitChaseVal()
							|| bean.getSellerAt() < bean.getProfitChaseVal().doubleValue()) {
						bean.setProfitChaseVal(bean.getSellerAt());
						NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(symbol, bean);
						continue;
					}
					bean.setSellStatus(Constants.SELL_EXIT_PROFIT);
					bean.setTradedState(Constants.SELL_EXIT_PROFIT);
					bean.setTradedOutAt(NR7AndUTCommonTool.getInstance().getCurrentDateTimeAsDate());
					double netProfitVal = NR7AndUTCommonTool.getInstance().getRoundupValue2(
							(bean.getTradedVal().doubleValue() - bean.getSellerAt()) * bean.getLotSize());
					bean.setProfitLossAmt(netProfitVal);
					bean.setSignalTriggeredOutAt(NR7AndUTCommonTool.getInstance().getCurrentTime()
							+ Constants.SELL_TARGET + bean.getSellerAt() + Constants.WITH_PROFIT + netProfitVal);

					// KiteConnectTool.getInstance().cancelBracketOrder(bean);
					//bean = (Narrow7DataBean) KiteConnectTool.getInstance().cancelCoverOrder((StockDataBean) bean);
					DatabaseHelper.getInstance().updateTrade(bean);
					NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(symbol, bean);
				} else if (bean.getSellerAt() >= stopLoss || (null != bean.getNegativeDirectionLoss()
						&& bean.getNegativeDirectionLoss().doubleValue() <= -Constants.TARGET_CO_STOP_LOSS)) {
					bean.setSellStatus(Constants.SELL_EXIT_LOSS);
					bean.setTradedState(Constants.SELL_EXIT_LOSS);
					bean.setTradedOutAt(NR7AndUTCommonTool.getInstance().getCurrentDateTimeAsDate());
					double netLossVal = NR7AndUTCommonTool.getInstance().getRoundupValue2(
							(bean.getTradedVal().doubleValue() - bean.getSellerAt()) * bean.getLotSize());
					bean.setProfitLossAmt(netLossVal);
					bean.setSignalTriggeredOutAt(NR7AndUTCommonTool.getInstance().getCurrentTime()
							+ Constants.CLOSED_WITH_LOSE + bean.getSellerAt() + Constants.WITH_LOSS + netLossVal);

					// KiteConnectTool.getInstance().cancelBracketOrder(bean);
					//bean = (Narrow7DataBean) KiteConnectTool.getInstance().cancelCoverOrder((StockDataBean) bean);
					DatabaseHelper.getInstance().updateTrade(bean);
					NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(symbol, bean);
				} else {// track the price moment
					double netProfitLossVal = NR7AndUTCommonTool.getInstance()
							.getRoundupValue2((bean.getTradedVal().doubleValue() - lastPrice) * bean.getLotSize());
					if (null == bean.getNegativeDirectionLoss()
							|| netProfitLossVal < bean.getNegativeDirectionLoss().doubleValue()) {
						bean.setNegativeDirectionLoss(netProfitLossVal);
						bean.setNegativeDirectionLtp(lastPrice);
						bean.setProfitLossAmt(netProfitLossVal);
					} else if (null == bean.getProfitLossAmt()
							|| netProfitLossVal > bean.getPositiveDirectionProfit().doubleValue()) {
						bean.setPositiveDirectionLtp(lastPrice);
						bean.setPositiveDirectionProfit(netProfitLossVal);
						bean.setProfitLossAmt(netProfitLossVal);
					}
					NR7AndUTCommonTool.getInstance().getMonitorNR7().replace(bean.getKiteFutureKey(), bean);
				}
			}
		}
	}
}
