package com.tradeware.stockmarket.scanner;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.stockmarket.bean.KiteLiveOHLCDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeChildDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeMainDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TradewareOptionStrangleScanner extends AbstractTradewareOptionScanner {

	@Override
	public void run() {
		// DatabaseHelper.getInstance().activityScannerStartup();
		List<OptionLiveTradeMainDataBean> optionTradingDataList = databaseHelper.findAllByRunningTrade();
		if (!optionTradingDataList.isEmpty()) {
			for(OptionLiveTradeMainDataBean dbBean : optionTradingDataList) {
				if (!tradewareTool.getTradingStrangleOptionDataMap().keySet().contains(dbBean.getTradeName())) {
					tradewareTool.getTradingStrangleOptionDataMap().put(dbBean.getTradeName(), dbBean);
				}
			}
		}
		while (tradewareTool.isTradingTime()) {
			try {
				ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap = tradewareTool
						.getTradingStrangleOptionDataMap();
				
				if (!tradewareTool.getTradingStrangleOptionDataMap().isEmpty()) {
					ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveDataMap = updateLiveOHLCOptionData(
							optionTradingDataMap);
					if (tradewareTool.isScanForBuyOrSell()) {
						runBuySellScanner(liveDataMap, optionTradingDataMap);
						profitLossMonitorScan(optionTradingDataMap,liveDataMap);
					} else if (null != getKiteConnect() && !tradewareTool.canPlaceOptionOrders()) {
						profitLossMonitorScan(optionTradingDataMap,liveDataMap);
					}
					if (loopCounter > 250) {
						System.out.println("TradewareScanner >>>>  " + loopCounter);
						loopCounter = 0;
						// profitLossSummaryTool.calculateProfitLoss(TRADE_PLACE_RULE_INIT_PROFITABLE_PROD_RULE);
					} else {
						loopCounter++;
					}
				}
				tradewareTool.setThreadScannerLastRunnngSatusTime(tradewareTool.getCurrentDateTime());

			} catch (/* Exception */Throwable e) {
				TradewareLogger.saveFatalErrorLog(CLASS_TRADEWARE_SCANNER, METHOD_RUN, e, ERROR_TYPE_EXCEPTION);
				tradewareTool.setThreadScannerStatus(APP_SERVER_STATUS_SCANNERS_TERMINATED);
			}
		}
		DatabaseHelper.getInstance().activityScannerEndup();
		tradewareTool.setThreadScannerLastRunnngSatusTime(
				/* "Thread out of ofloop - " + */tradewareTool.getCurrentDateTime());
	}
	
	

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	/*protected void strangleProfitLossMonitorScan(OptionLiveTradeMainDataBean mainBean) {
		if (Constants.TRADE_POSIITON_OPEN.equals(mainBean.getTradePosition())) {
			double netProfitLossValPut = 0;
			double netProfitLossValCall = 0;
			OptionLiveTradeChildDataBean putDataBean = null;
			OptionLiveTradeChildDataBean callDataBean = null;

			for (OptionLiveTradeChildDataBean childDataBean : mainBean.getTradeChildBeanList()) {
				if (Constants.TRADE_POSIITON_OPEN.equals(childDataBean.getTradePosition())) {
					if (Constants.OPTION_PUT.equals(childDataBean.getOptionType())) {
						putDataBean = childDataBean;
					} else if (Constants.OPTION_CALL.equals(childDataBean.getOptionType())) {
						callDataBean = childDataBean;
					}
				}
			}

			// bean = tradewareTool.handleStopLossValue2(bean);
			// bean = tradewareTool.handleTrailingStopLossValue(bean);
			// Need adjust stopLoss/target based on profit/loss

			// profit/loss monitor of Put
			double lastPricePut = putDataBean.getAskPrice();
			if (Constants.SELL.equals(putDataBean.getTradeType())) {
				netProfitLossValPut = (putDataBean.getTradedAtVal().doubleValue() - lastPricePut);
			} else if (Constants.BUY.equals(putDataBean.getTradeType())) {
				netProfitLossValPut = (lastPricePut - putDataBean.getTradedAtVal().doubleValue());
			}

			netProfitLossValPut = kiteConnectTool.getRoundupToTwoValue(
					netProfitLossValPut * (mainBean.getLotSize() * putDataBean.getNumberOfLots()));
			putDataBean.setProfitLossAmtVal(netProfitLossValPut);

			// profit/loss monitor of Call
			double lastPriceCall = callDataBean.getAskPrice();
			if (Constants.SELL.equals(putDataBean.getTradeType())) {
				netProfitLossValCall = (callDataBean.getTradedAtVal().doubleValue() - lastPriceCall);
			} else if (Constants.BUY.equals(putDataBean.getTradeType())) {
				netProfitLossValCall = (lastPriceCall - callDataBean.getTradedAtVal().doubleValue());
			}
			netProfitLossValCall = kiteConnectTool.getRoundupToTwoValue(
					netProfitLossValCall * (mainBean.getLotSize() * callDataBean.getNumberOfLots()));
			callDataBean.setProfitLossAmtVal(netProfitLossValCall);

			if (null == putDataBean.getNegativeMoveVal()
					|| putDataBean.getProfitLossAmtVal() < putDataBean.getNegativeMoveVal().doubleValue()) {
				putDataBean.setNegativeMoveVal(putDataBean.getProfitLossAmtVal());
				putDataBean.setNegativeMoveLtp(lastPricePut);
			} else if (null == putDataBean.getPositiveMoveVal()
					|| putDataBean.getProfitLossAmtVal() > putDataBean.getPositiveMoveVal().doubleValue()) {
				putDataBean.setProfitLossAmtVal(putDataBean.getProfitLossAmtVal());
				putDataBean.setPositiveMoveLtp(lastPricePut);
			}

			if (null == callDataBean.getNegativeMoveVal()
					|| callDataBean.getProfitLossAmtVal() < callDataBean.getNegativeMoveVal().doubleValue()) {
				callDataBean.setNegativeMoveVal(callDataBean.getProfitLossAmtVal());
				callDataBean.setNegativeMoveLtp(lastPriceCall);
			} else if (null == callDataBean.getPositiveMoveVal()
					|| callDataBean.getProfitLossAmtVal() > callDataBean.getPositiveMoveVal().doubleValue()) {
				callDataBean.setProfitLossAmtVal(callDataBean.getProfitLossAmtVal());
				callDataBean.setPositiveMoveLtp(lastPriceCall);
			}

			mainBean.setProfitLossAmtVal(mainBean.getProfitLossAmtVal() + netProfitLossValPut + netProfitLossValCall);
			if (null == mainBean.getNegativeMoveVal()
					|| mainBean.getProfitLossAmtVal() < mainBean.getNegativeMoveVal().doubleValue()) {
				mainBean.setNegativeMoveVal(mainBean.getProfitLossAmtVal());
			} else if (null == mainBean.getPositiveMoveVal()
					|| mainBean.getProfitLossAmtVal() > mainBean.getPositiveMoveVal().doubleValue()) {
				mainBean.setProfitLossAmtVal(mainBean.getProfitLossAmtVal());
			}

			if (loopCounter > 250) {
				databaseHelper.saveShortStrangleOptionTrade(mainBean);
			}

			tradewareTool.getTradingStrangleOptionDataMap().replace(mainBean.getTradeName(), mainBean);

			verifyAndMakeStrangleAdjustment(mainBean, putDataBean, callDataBean);
		}
	}*/
	
	protected void verifyAndMakeStrangleAdjustment(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap) {
		if (null != mainBean.getIntradayTradeInd() && mainBean.getIntradayTradeInd()) {
			verifyAndBookProfitOfIntradayStrangle(mainBean, optionTradingDataMap);
		} else {
			verifyAndMakeStrangleAdjustmentForPositional(mainBean);
		}
	}
	
	//@Override //It is same as straddle intraday now
	protected void verifyAndBookProfitOfIntradayStrangle(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap) {
		if (Constants.OPTION_STRATEGY_STRANGLE.equals(mainBean.getTradeStrategy())) {
			if (OPTION_STRATEGY_SHORT_STRANGLE.equals(mainBean.getTradeSubStrategy())) {
				if (null != mainBean.getIntradayTradeInd() && mainBean.getIntradayTradeInd()) {
					double premiumBefit = 0d;
					boolean canBookProfit = true;
					boolean canBookonMaxLoss = false;
					for (OptionLiveTradeChildDataBean childBean : mainBean.getTradeChildBeanList()) {
						if(null == childBean) {
							System.out.println(childBean + "	::::  childBean");
							continue;
						}
						if(null == childBean.getAskPrice()) {
							System.out.println(childBean.getAskPrice()+"  ::::: childBean.getAskPrice()");
							continue;
						}
						
						canBookProfit = (canBookProfit && (null != childBean.getAskPrice()) );
						if (!canBookonMaxLoss && null != childBean.getAskPrice() 
								&& ( childBean.getAskPrice() > childBean.getStopLossVal())) {
							canBookonMaxLoss = true;
						}
						premiumBefit = premiumBefit + (childBean.getTradedAtVal() - childBean.getAskPrice());
					}
					if (canBookProfit && premiumBefit > mainBean.getTargetVal()) {
						//Book full Profit
						bookWholeTradeToClose(mainBean, optionTradingDataMap);
					}
					if (canBookonMaxLoss) {
						//Cose on loss
						bookWholeTradeToClose(mainBean, optionTradingDataMap);
					}
				} else {
					//adjustment logic
					//verifyAndMakeStraddlePositionalTradeAdjustment(mainBean);
				}
			}
		}
	}
	//@Override
	protected  void verifyAndMakeStrangleAdjustmentForPositional(OptionLiveTradeMainDataBean mainBean ) {

		OptionLiveTradeChildDataBean putDataBean = null;
		OptionLiveTradeChildDataBean callDataBean = null;

		for (OptionLiveTradeChildDataBean childDataBean : mainBean.getTradeChildBeanList()) {
			if (Constants.TRADE_POSIITON_OPEN.equals(childDataBean.getTradePosition())
					|| Constants.TRADE_POSIITON_NEW.equals(childDataBean.getTradePosition())) {
				if (Constants.OPTION_PUT.equals(childDataBean.getOptionType())) {
					putDataBean = childDataBean;
				} else if (Constants.OPTION_CALL.equals(childDataBean.getOptionType())) {
					callDataBean = childDataBean;
				}
			}
		}
		
		if (null != callDataBean.getAskPrice() && null !=  putDataBean.getAskPrice()) {
		//if (Constants.OPTION_STRATEGY_SHORT_STRANGLE.equals(mainBean.getTradeSubStrategy())) {
			if ((callDataBean.getAskPrice() / putDataBean.getAskPrice()) >= Constants.STRANGLE_ADJUSTMENT_RATIO) {
				ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = tradewareTool
						.getOptionStrickListForStrategyAdustments(mainBean, putDataBean.getStrikePrice(),
								putDataBean.getOptionType());
				map = tradewareTool.updateLiveOHLCOptionDataWith(map);
				KiteLiveOHLCDataBean liveBean = tradewareTool.findNearestOptionCallOrPutForAdustment(map,
						callDataBean.getAskPrice() * Constants.STRANGLE_ADJUSTMENT_NEW_TRADE_VAUE);
				/*KiteLiveOHLCDataBean liveBean = findOptionCallOrPutForAdustment(mainBean, putDataBean.getStrikePrice(),
						putDataBean.getOptionType(),
						callDataBean.getAskPrice() * Constants.STRANGLE_ADJUSTMENT_NEW_TRADE_VAUE);*/

				if (Double.valueOf(callDataBean.getStrikePrice()) > Double.valueOf(liveBean.getOptionStrikePrice())) {
					// Book current put and make adjustment
					updateLiveOHLCFromLiveDataBeanToChildDataBean(map.get(putDataBean.getKiteFutureKey()), putDataBean);
					bookCurrentPutOrCallToMakeAdjustment(mainBean, putDataBean);
					mainBean.getTradeChildBeanList().add(getAdjustmentOptionDataBean(mainBean, putDataBean, liveBean));
					// place order
					// kiteConnectTool.cancelBracketOrder(bean);
					// bean = kiteConnectTool.cancelCoverOrder(bean);
					databaseHelper.saveShortStrangleOptionTrade(mainBean);
					//Need to fresh open child trade
					mainBean = databaseHelper.findRunningTradeByTradeId(mainBean.getTradeId());
					tradewareTool.getTradingStrangleOptionDataMap().replace(mainBean.getTradeName(), mainBean);
				} else {
					bookWholeTradeToClose(mainBean, tradewareTool.getTradingStrangleOptionDataMap());
				}

			} else if ((putDataBean.getAskPrice() / callDataBean.getAskPrice()) >= Constants.STRANGLE_ADJUSTMENT_RATIO) {

				ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = tradewareTool
						.getOptionStrickListForStrategyAdustments(mainBean, callDataBean.getStrikePrice(),
								callDataBean.getOptionType());
				kiteConnectTool.updateLiveOHLC(map);
				KiteLiveOHLCDataBean liveBean = tradewareTool.findNearestOptionCallOrPutForAdustment(map,
						putDataBean.getAskPrice() * Constants.STRANGLE_ADJUSTMENT_NEW_TRADE_VAUE);
				/*KiteLiveOHLCDataBean liveBean = findOptionCallOrPutForAdustment(mainBean,callDataBean.getStrikePrice(),
						callDataBean.getOptionType(),
						putDataBean.getAskPrice() * Constants.STRANGLE_ADJUSTMENT_NEW_TRADE_VAUE);*/

				if (Double.valueOf(putDataBean.getStrikePrice()) < Double.valueOf(liveBean.getOptionStrikePrice())) {
					// Book current put and make adjustment
					updateLiveOHLCFromLiveDataBeanToChildDataBean(map.get(callDataBean.getKiteFutureKey()), callDataBean);
					bookCurrentPutOrCallToMakeAdjustment(mainBean, callDataBean);
					mainBean.getTradeChildBeanList().add(getAdjustmentOptionDataBean(mainBean, callDataBean, liveBean));
					// place order
					// kiteConnectTool.cancelBracketOrder(bean);
					// bean = kiteConnectTool.cancelCoverOrder(bean);
					databaseHelper.saveShortStrangleOptionTrade(mainBean);
					//Need to fresh open child trade
					mainBean = databaseHelper.findRunningTradeByTradeId(mainBean.getTradeId());
					tradewareTool.getTradingStrangleOptionDataMap().replace(mainBean.getTradeName(), mainBean);
				} else {
					bookWholeTradeToClose(mainBean, tradewareTool.getTradingStrangleOptionDataMap());
				}
			}
		//}
		} else {
			System.out.println("callDataBean.getAskPrice() && putDataBean.getAskPrice() are null");

			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> map = new ConcurrentHashMap<String, OptionLiveTradeMainDataBean>();
			map.put(mainBean.getTradeName(), mainBean);
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveDataMap = updateLiveOHLCOptionData(map);
			for (OptionLiveTradeChildDataBean childDataBean : mainBean.getTradeChildBeanList()) {
				if (Constants.TRADE_POSIITON_OPEN.equals(childDataBean.getTradePosition())) {
					updateLiveOHLCFromLiveDataBeanToChildDataBean(liveDataMap.get(childDataBean.getKiteFutureKey()),
							childDataBean);
				}
			}
			verifyAndMakeStrangleAdjustmentForPositional(mainBean);
		}
	
	}
	
	
	private Double targetValue = null;
	private Double stockOrFutureLastPrice = null;
	private Double adjustmentPremiumPrice = null;
	@Override
	protected void verifyAndMakeButterFlyAdjustment(OptionLiveTradeMainDataBean mainBean) {
		OptionLiveTradeChildDataBean atmTradeDataBean = null;
		OptionLiveTradeChildDataBean lowerTradeDataBean = null;
		OptionLiveTradeChildDataBean higherTradeDataBean = null;

		for (OptionLiveTradeChildDataBean childDataBean : mainBean.getTradeChildBeanList()) {
			if (Constants.TRADE_POSIITON_OPEN.equals(childDataBean.getTradePosition())) {
				if (mainBean.getTradeSubStrategy().contains(OPTION_STRATEGY_LONG_BUTTER_FLY)) {
					if (Constants.SELL.equals(childDataBean.getTradeType())) {
						if (mainBean.getAtmStrikePrice().doubleValue() == childDataBean.getStrikePrice()
								.doubleValue()) {
							atmTradeDataBean = childDataBean;
						}
					} else if (Constants.BUY.equals(childDataBean.getTradeType())) {
						if (mainBean.getAtmStrikePrice() > childDataBean.getStrikePrice()) {
							lowerTradeDataBean = childDataBean;
						} else {
							higherTradeDataBean = childDataBean;
						}
					}
				} else if (mainBean.getTradeSubStrategy().contains(OPTION_STRATEGY_SHORT_BUTTER_FLY)) {

				}
			}

		}

		if (null != atmTradeDataBean && null != lowerTradeDataBean && null != higherTradeDataBean) {
			targetValue = atmTradeDataBean.getTradedAtVal() * BUTTER_FLY_TARGET_PROFIT;
			if (atmTradeDataBean.getBidPrice() < targetValue) {
				bookWholeTradeToClose(mainBean, tradewareTool.getTradingStrangleOptionDataMap());
			} else if (mainBean.getTradeChildBeanList().size() == 3) {
				/*stockOrFutureLastPrice = tradewareTool.isTodayExpiryDay(mainBean.getExpiryDate())
						? mainBean.getFutureLastPrice()
						: mainBean.getStockLastPrice();*/
				
				stockOrFutureLastPrice = tradewareTool.getIndexValue(mainBean.getSymbolId());

				if (stockOrFutureLastPrice <= lowerTradeDataBean.getStrikePrice()
						|| stockOrFutureLastPrice >= higherTradeDataBean.getStrikePrice()) {
					adjustmentPremiumPrice = Math.abs((((2 * atmTradeDataBean.getTradedAtVal())
							- (higherTradeDataBean.getTradedAtVal() + lowerTradeDataBean.getTradedAtVal())) / 2)
							+ ADJUST_AND_EARN_TRADE_CHARGES_POINTS);
					if (mainBean.getTradeSubStrategy().contains(OPTION_STRATEGY_LONG_BUTTER_FLY)) {
						if (stockOrFutureLastPrice <= lowerTradeDataBean.getStrikePrice()) {
							// Adjustment with call
							ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = tradewareTool
									.getOptionStrickListForStrategyAdustments(mainBean,
											lowerTradeDataBean.getStrikePrice(), Constants.OPTION_CALL);
							map = kiteConnectTool.updateLiveOHLC(map);
							KiteLiveOHLCDataBean liveBean = tradewareTool.findNearestOptionCallOrPutForAdustment(map,
									adjustmentPremiumPrice);

							mainBean.getTradeChildBeanList().add(getAdjustmentOptionDataBeanForButterFly(mainBean,
									liveBean, SELL, OPTION_CALL, atmTradeDataBean.getNumberOfLots()));
							// place order
							// kiteConnectTool.cancelBracketOrder(bean);
							// bean = kiteConnectTool.cancelCoverOrder(bean);
							databaseHelper.saveShortStrangleOptionTrade(mainBean);
							// Need to fresh open child trade
							mainBean = databaseHelper.findRunningTradeByTradeId(mainBean.getTradeId());
							tradewareTool.getTradingStrangleOptionDataMap().replace(mainBean.getTradeName(), mainBean);
						} else if (stockOrFutureLastPrice >= higherTradeDataBean.getStrikePrice()) {
							// Adjustment with put
							ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = tradewareTool
									.getOptionStrickListForStrategyAdustments(mainBean,
											higherTradeDataBean.getStrikePrice(), Constants.OPTION_PUT);
							map = kiteConnectTool.updateLiveOHLC(map);
							KiteLiveOHLCDataBean liveBean = tradewareTool.findNearestOptionCallOrPutForAdustment(map,
									adjustmentPremiumPrice);

							mainBean.getTradeChildBeanList().add(getAdjustmentOptionDataBeanForButterFly(mainBean,
									liveBean, SELL, OPTION_PUT, atmTradeDataBean.getNumberOfLots()));
							// place order
							// kiteConnectTool.cancelBracketOrder(bean);
							// bean = kiteConnectTool.cancelCoverOrder(bean);
							databaseHelper.saveShortStrangleOptionTrade(mainBean);
							// Need to fresh open child trade
							mainBean = databaseHelper.findRunningTradeByTradeId(mainBean.getTradeId());
							tradewareTool.getTradingStrangleOptionDataMap().replace(mainBean.getTradeName(), mainBean);
						}
					} else if (mainBean.getTradeSubStrategy().contains(OPTION_STRATEGY_SHORT_BUTTER_FLY)) {

					}
				}
			}
		}
	}
	
	@Override
	protected void verifyAndBookProfitOfIntradayStraddle(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap) {
		if (Constants.OPTION_STRATEGY_STRADDLE.equals(mainBean.getTradeStrategy())) {
			if (OPTION_STRATEGY_SHORT_STRADDLE.equals(mainBean.getTradeSubStrategy())) {
				if (null != mainBean.getIntradayTradeInd() && mainBean.getIntradayTradeInd()) {
					double premiumBefit = 0d;
					boolean canBookProfit = true;
					boolean canBookonMaxLoss = false;
					for (OptionLiveTradeChildDataBean childBean : mainBean.getTradeChildBeanList()) {
						if(null == childBean) {
							System.out.println(childBean + "	::::  childBean");
							continue;
						}
						if(null == childBean.getAskPrice()) {
							System.out.println(childBean.getAskPrice()+"  ::::: childBean.getAskPrice()");
							continue;
						}
						
						canBookProfit = (canBookProfit && (null != childBean.getAskPrice()) );
						if (!canBookonMaxLoss && null != childBean.getAskPrice() 
								&& ( childBean.getAskPrice() > childBean.getStopLossVal())) {
							canBookonMaxLoss = true;
						}
						premiumBefit = premiumBefit + (childBean.getTradedAtVal() - childBean.getAskPrice());
					}
					if (canBookProfit && premiumBefit > mainBean.getTargetVal()) {
						//Book full Profit
						bookWholeTradeToClose(mainBean, optionTradingDataMap);
					}
					if (canBookonMaxLoss) {
						//Cose on loss
						bookWholeTradeToClose(mainBean, optionTradingDataMap);
					}
				} else {
					//adjustment logic
					//verifyAndMakeStraddlePositionalTradeAdjustment(mainBean);
				}
			}
		}
	}
	
	
}
