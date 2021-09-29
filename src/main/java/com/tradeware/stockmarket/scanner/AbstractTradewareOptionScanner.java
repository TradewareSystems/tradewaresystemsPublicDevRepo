package com.tradeware.stockmarket.scanner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.stockmarket.bean.KiteLiveOHLCDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeChildDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeMainDataBean;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;
import com.tradeware.stockmarket.util.StockUtil;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class AbstractTradewareOptionScanner extends Thread implements Constants {
	protected int loopCounter;
	@Autowired
	protected TradewareTool tradewareTool;
	@Autowired
	protected KiteConnectTool kiteConnectTool;
	@Autowired
	protected DatabaseHelper databaseHelper;
	
	protected Object getKiteConnect() {
		return StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER);
	}

	protected ConcurrentHashMap<String, KiteLiveOHLCDataBean> updateLiveOHLCOptionData(
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> liveTradeMap) {
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveBeanDataMap = tradewareTool
				.updateToLatestOHLC(liveTradeMap);
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCDataMap = null;

		if (kiteConnectTool.validKiteAccess()) {
			liveOHLCDataMap = kiteConnectTool.updateLiveOHLC(liveBeanDataMap);
		} else {
			if (!liveTradeMap.isEmpty()) {
			 liveOHLCDataMap = new ConcurrentHashMap<String, KiteLiveOHLCDataBean>();
				for (OptionLiveTradeMainDataBean mainDataBean : liveTradeMap.values()) {
					List<String> closeDateList = new ArrayList<String>();
					List<String> strikePriceList = new ArrayList<String>();
					for (OptionLiveTradeChildDataBean childDataBean : mainDataBean.getTradeChildBeanList()) {
						if (!closeDateList.contains(mainDataBean.getExpiryDate())) {
							closeDateList.add(mainDataBean.getExpiryDate());
						}
						String strikePrice = tradewareTool.stripTrailingZeros(String.valueOf(childDataBean.getStrikePrice()));
						if (!strikePriceList.contains(strikePrice)) {
							strikePriceList.add(strikePrice);
						}
					}
					liveOHLCDataMap.putAll(tradewareTool.connectToNSEIndiaAndGetStockOptionData1(
							mainDataBean.getSymbolId(), closeDateList, strikePriceList));
				}
			}

		}
		return liveOHLCDataMap;
	}
	
	/*protected void updateLiveOHLCOptionDataToMain(ConcurrentHashMap<String, OptionLiveTradeMainDataBean> liveTradeMap) {
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveBeanDataMap  = updateLiveOHLCOptionData(liveTradeMap);
		if (!liveTradeMap.isEmpty()) {
			for (OptionLiveTradeMainDataBean mainDataBean : liveTradeMap.values()) {
				for (OptionLiveTradeChildDataBean childDataBean : mainDataBean.getTradeChildBeanList()) {
					updateLiveOHLCFromLiveDataBeanToChildDataBean(liveBeanDataMap.get(childDataBean.getKiteFutureKey()),
							childDataBean);
				}
			}

		}
	}*/
	
	protected void runBuySellScanner(ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveDataMap,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap) {
		for (String key : optionTradingDataMap.keySet()) {
			OptionLiveTradeMainDataBean mainBean = optionTradingDataMap.get(key);
			for (OptionLiveTradeChildDataBean childBean : mainBean.getTradeChildBeanList()) {
				if (Constants.TRADE_POSIITON_NEW.equals(mainBean.getTradePosition())) {
					if (null != childBean && Constants.TRADE_POSIITON_NEW.equals(childBean.getTradePosition())) {
						updateLiveOHLCFromLiveDataBeanToChildDataBean(liveDataMap.get(childBean.getKiteFutureKey()),
								childBean);
						// double lastPrice = childBean.getLastPrice();
						if (Constants.BUY.equals(childBean.getTradeType()) && null != childBean.getBidPrice()
								&& childBean.getAskPrice() <= childBean.getBuyAtValue()
								// && lastPrice < childBean.getBuyAtValue()
								&& validateBuyerSellerRule(mainBean, childBean, BUY)) {
							executeBuyWorkflow(mainBean, childBean);
							placeEntryTradeOrder(mainBean, optionTradingDataMap);
						} else if (Constants.SELL.equals(childBean.getTradeType()) && null != childBean.getAskPrice()
								&& childBean.getBidPrice() >= childBean.getSellAtValue()
								// && lastPrice > childBean.getSellAtValue()
								&& validateBuyerSellerRule(mainBean, childBean, SELL)) {
							executeSellWorkflow(mainBean, childBean);
							placeEntryTradeOrder(mainBean, optionTradingDataMap);
						}
					}
				}
			}
		}
	}
	
	
	protected void executeBuyWorkflow(OptionLiveTradeMainDataBean mainBean, OptionLiveTradeChildDataBean childBean) {
		childBean.setTradedStateId(Constants.BUY);
		childBean.setTradedAtVal(childBean.getAskPrice());
		childBean.setTradedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
		childBean.setTradePosition(Constants.TRADE_POSIITON_OPEN); 
		childBean.setBuyAtValue(childBean.getAskPrice());
		//childBean.setSellAtValue(childBean.getAskPrice());
		tradewareTool.updateLiveIndexPrice(mainBean.getSymbolId(), childBean);
		
		//bean = tradewareTool.handleTrailingStopLoss(bean);
	}
	
	protected void executeSellWorkflow(OptionLiveTradeMainDataBean mainBean, OptionLiveTradeChildDataBean childBean) {
			childBean.setTradedStateId(Constants.SELL);
			childBean.setTradedAtVal(childBean.getBidPrice());
			childBean.setTradedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
			childBean.setTradePosition(Constants.TRADE_POSIITON_OPEN); 
			//childBean.setBuyAtValue(childBean.getBidPrice());
			childBean.setSellAtValue(childBean.getBidPrice());
			tradewareTool.updateLiveIndexPrice(mainBean.getSymbolId(), childBean);
			
			//bean = tradewareTool.handleTrailingStopLoss(bean);
		}
	
	protected void placeTradeOrder(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap) {
		// mainBean = kiteConnectTool.placeCoverOrder(mainBean);
		mainBean = databaseHelper.saveShortStrangleOptionTrade(mainBean);
		optionTradingDataMap.replace(mainBean.getTradeName(), mainBean);
	}
	
	protected void placeEntryTradeOrder(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap) {
		for (int i = 0; i < mainBean.getTradeChildBeanList().size(); i++) {
			OptionLiveTradeChildDataBean childBean = mainBean.getTradeChildBeanList().get(i);
			if (Constants.TRADE_POSIITON_NEW.equals(childBean.getTradePosition())) {
				placeTradeOrder(mainBean, optionTradingDataMap);
				break;
				/*if (Constants.BUY.equals(childBean.getTradeType())) {
					childBean.setBuyAtValue(childBean.getAskPrice() +.05);
				} else if (Constants.SELL.equals(childBean.getTradeType())) {
					childBean.setSellAtValue(childBean.getAskPrice() -.05);
				}*/
				
			} else if (i==mainBean.getTradeChildBeanList().size()-1) {
				mainBean.setTradedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
				mainBean.setTradePosition(Constants.TRADE_POSIITON_OPEN); 
				caluclateBreakEvenPoints(mainBean);
				caluclateStopLossAndTargetValues(mainBean);
				placeTradeOrder(mainBean, optionTradingDataMap);
				break;
			}
		}
	}
	
	private void caluclateBreakEvenPoints(OptionLiveTradeMainDataBean mainBean) {
		int counter = 0;
		double totalPremium = 0;
		// double strikePriceOfAtm = 0;
		double strikePriceLowerOfAtm = 0;
		double strikePriceHigherOfAtm = 0;
		for (OptionLiveTradeChildDataBean childBean : mainBean.getTradeChildBeanList()) {
			if (Constants.OPTION_STRATEGY_STRANGLE.equals(mainBean.getTradeStrategy())) {
				totalPremium = totalPremium + childBean.getTradedAtVal();
				if (childBean.getStockPriceEntry() < childBean.getStrikePrice()) {
					strikePriceHigherOfAtm = childBean.getStrikePrice();
				} else if (childBean.getStockPriceEntry() > childBean.getStrikePrice()) {
					strikePriceLowerOfAtm = childBean.getStrikePrice();
				}
				if (mainBean.getTradeChildBeanList().size() == counter) {
					mainBean.setBreakEvenLower1(
							kiteConnectTool.getRoundupToTwoValue(strikePriceLowerOfAtm - totalPremium));
					mainBean.setBreakEvenHigher1(
							kiteConnectTool.getRoundupToTwoValue(strikePriceHigherOfAtm - totalPremium));
				}
			} else if (Constants.OPTION_STRATEGY_BUTTER_FLY.equals(mainBean.getTradeStrategy())) {

			}
			counter++;
		}
	}
	
	protected boolean validateBuyerSellerRule(OptionLiveTradeMainDataBean mainBean,
			OptionLiveTradeChildDataBean chiildBean, String mode) {
		boolean buyerSellerRule = true;
		double buyerSellerDiff = kiteConnectTool.getRoundupToTwoValue(
				(chiildBean.getAskPrice() - chiildBean.getBidPrice()) * mainBean.getLotSize());
		if (chiildBean.getAskPrice() == 0 || chiildBean.getBidPrice() == 0 || buyerSellerDiff < 0
				|| buyerSellerDiff > 250) {
			buyerSellerRule = false;
		}
		return buyerSellerRule;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
	protected void profitLossMonitorScan(ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap,
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveDataMap) {
		for (String symbol : optionTradingDataMap.keySet()) {
			OptionLiveTradeMainDataBean bean = optionTradingDataMap.get(symbol);
			profitLossMonitorScan(bean, optionTradingDataMap, liveDataMap);
		}
	}

	protected void profitLossMonitorScan(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap,
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveDataMap) {
		if (Constants.TRADE_POSIITON_OPEN.equals(mainBean.getTradePosition())) {
			double netProfitLossVal = 0;
			double netProfitLossValMain = 0;
			// bean = tradewareTool.handleStopLossValue2(bean);
						// bean = tradewareTool.handleTrailingStopLossValue(bean);
						// Need adjust stopLoss/target based on profit/loss
			
			for (OptionLiveTradeChildDataBean childDataBean : mainBean.getTradeChildBeanList()) {
				if (Constants.TRADE_POSIITON_OPEN.equals(childDataBean.getTradePosition())) {
					updateLiveOHLCFromLiveDataBeanToChildDataBean(liveDataMap.get(childDataBean.getKiteFutureKey()),
							childDataBean);
					
					if(null == childDataBean) {
						System.out.println(childDataBean);
						continue;
					}
					if(null == childDataBean.getAskPrice()) {
						System.out.println(childDataBean.getAskPrice());
						continue;
					}
					double lastPrice = childDataBean.getAskPrice();
					if (Constants.SELL.equals(childDataBean.getTradeType())) {
						netProfitLossVal = (childDataBean.getTradedAtVal().doubleValue() - lastPrice);
					} else if (Constants.BUY.equals(childDataBean.getTradeType())) {
						netProfitLossVal = (lastPrice - childDataBean.getTradedAtVal().doubleValue());
					}
					
					netProfitLossVal = kiteConnectTool.getRoundupToTwoValue(
							netProfitLossVal * (mainBean.getLotSize() * childDataBean.getNumberOfLots()));
					childDataBean.setProfitLossAmtVal(netProfitLossVal);
					if (childDataBean
							.getProfitLossAmtVal() < childDataBean.getNegativeMoveVal().doubleValue()) {
						childDataBean.setNegativeMoveVal(childDataBean.getProfitLossAmtVal());
						childDataBean.setNegativeMoveLtp(lastPrice);
					} else if (childDataBean
							.getProfitLossAmtVal() > childDataBean.getPositiveMoveVal().doubleValue()) {
						childDataBean.setPositiveMoveVal(childDataBean.getProfitLossAmtVal());
						childDataBean.setPositiveMoveLtp(lastPrice);
					}
					netProfitLossValMain = netProfitLossValMain + netProfitLossVal;
				} else  if (Constants.TRADE_POSIITON_CLOSE.equals(childDataBean.getTradePosition())) {
					netProfitLossValMain = netProfitLossValMain + childDataBean.getProfitLossAmtVal();
				}
			}
			
			mainBean.setProfitLossAmtVal(kiteConnectTool.getRoundupToTwoValue(netProfitLossValMain));
			if (( mainBean.getProfitLossAmtVal() < mainBean.getNegativeMoveVal().doubleValue())) {
				mainBean.setNegativeMoveVal(mainBean.getProfitLossAmtVal());
			} else if (mainBean.getProfitLossAmtVal() > mainBean.getPositiveMoveVal().doubleValue()) {
				mainBean.setPositiveMoveVal(mainBean.getProfitLossAmtVal());
			}

			if (loopCounter > 250) {
				databaseHelper.saveShortStrangleOptionTrade(mainBean);
			}

			optionTradingDataMap.replace(mainBean.getTradeName(), mainBean);

			if(Constants.OPTION_STRATEGY_STRANGLE.equals(mainBean.getTradeStrategy()) ) {
				verifyAndMakeStrangleAdjustment(mainBean, optionTradingDataMap);
			} else if(Constants.OPTION_STRATEGY_BUTTER_FLY.equals(mainBean.getTradeStrategy()) ) {
				verifyAndMakeButterFlyAdjustment(mainBean);
			} else if (Constants.OPTION_STRATEGY_STRADDLE.equals(mainBean.getTradeStrategy())) {
				if (OPTION_STRATEGY_SHORT_STRADDLE.equals(mainBean.getTradeSubStrategy())
						&& Constants.TRADE_POSIITON_OPEN.equals(mainBean.getTradePosition())) {
					verifyAndBookProfitOfIntradayStraddle(mainBean, optionTradingDataMap);
				}
			}
		}
	}
	
	/*private void caluclateOverallTradeProfitLoss(OptionLiveTradeMainDataBean mainBean) {
		double netProfitValChilld = 0;
		double netLossValChild = 0;
		double netProfitValMain = 0;
		double netLossValMain = 0;
		for (OptionLiveTradeChildDataBean childDataBean : mainBean.getTradeChildBeanList()) {
			if (mainBean.getTradeSubStrategy().startsWith(OPTION_STRATEGY_SHORT)) {
				if (Constants.SELL.equals(childDataBean.getTradeType())) {
					netProfitValChilld = netProfitValChilld + childDataBean.getPositiveMoveVal();
					netLossValChild = netLossValChild + childDataBean.getNegativeMoveVal();
				} else if (Constants.BUY.equals(childDataBean.getTradeType())) {
					netProfitValChilld = netProfitValChilld + childDataBean.getNegativeMoveVal();
					netLossValChild = netLossValChild + childDataBean.getPositiveMoveVal();
				}
			} else if (mainBean.getTradeSubStrategy().startsWith(OPTION_STRATEGY_LONG)) {
				if (Constants.SELL.equals(childDataBean.getTradeType())) {
					netProfitValChilld = netProfitValChilld + childDataBean.getNegativeMoveVal();
					netLossValChild = netLossValChild + childDataBean.getPositiveMoveVal();
				} else if (Constants.BUY.equals(childDataBean.getTradeType())) {
					netProfitValChilld = netProfitValChilld + childDataBean.getPositiveMoveVal();
					netLossValChild = netLossValChild + childDataBean.getNegativeMoveVal();
				}
			} else {
				netProfitValChilld = netProfitValChilld + childDataBean.getPositiveMoveVal();
				netLossValChild = netLossValChild + childDataBean.getNegativeMoveVal();
			}
			
			if ((netLossValChild < childDataBean.getNegativeMoveVal().doubleValue())) {
				childDataBean.setNegativeMoveVal(kiteConnectTool.getRoundupToTwoValue(netLossValChild));
			}
			if (netProfitValChilld > childDataBean.getPositiveMoveVal().doubleValue()) {
				childDataBean.setPositiveMoveVal(kiteConnectTool.getRoundupToTwoValue(netProfitValChilld));
			}
			netProfitValMain = netProfitValMain + netProfitValChilld;
			 netLossValMain = netLossValMain + netLossValChild;
		}
		kiteConnectTool.getRoundupToTwoValue(netProfitValMain + netLossValMain);
		if ((netLossValMain < mainBean.getNegativeMoveVal().doubleValue())) {
			mainBean.setNegativeMoveVal(kiteConnectTool.getRoundupToTwoValue(netLossValMain));
		}
		if (netProfitValMain > mainBean.getPositiveMoveVal().doubleValue()) {
			mainBean.setPositiveMoveVal(kiteConnectTool.getRoundupToTwoValue(netProfitValMain));
		}
	}*/
	/*private double getStrategyProfitLoss(OptionLiveTradeMainDataBean mainBean,
			OptionLiveTradeChildDataBean childDataBean, double currentChildNetProfitLossVal) {
		if (mainBean.getTradeSubStrategy().startsWith(OPTION_STRATEGY_SHORT)
				&& Constants.BUY.equals(childDataBean.getTradeType())) {
			currentChildNetProfitLossVal = currentChildNetProfitLossVal * -1;
		} else if (mainBean.getTradeSubStrategy().startsWith(OPTION_STRATEGY_LONG)
				&& Constants.SELL.equals(childDataBean.getTradeType())) {
			currentChildNetProfitLossVal = currentChildNetProfitLossVal * -1;
		}
		return currentChildNetProfitLossVal;
	}*/
	protected abstract void verifyAndMakeStrangleAdjustment(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap);

	protected abstract void verifyAndMakeButterFlyAdjustment(OptionLiveTradeMainDataBean mainBean);

	protected abstract void verifyAndBookProfitOfIntradayStraddle(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap);

	public void bookCurrentPutOrCallToMakeAdjustment(OptionLiveTradeMainDataBean mainBean,
			OptionLiveTradeChildDataBean childBean) {
		childBean.setTradePosition(Constants.TRADE_POSIITON_CLOSE);
		childBean.setExitedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());

		if (Constants.SELL.equals(childBean.getTradeType())) {
			childBean.setBuyAtValue(childBean.getAskPrice());
		} else if (Constants.BUY.equals(childBean.getTradeType())) {
			childBean.setSellAtValue(childBean.getBidPrice());
		}
		childBean.setExitedAtVal(childBean.getAskPrice());

		tradewareTool.updateLiveIndexPrice(mainBean.getSymbolId(), childBean);
	}
	
	public void bookWholeTradeToClose(OptionLiveTradeMainDataBean mainBean,
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> optionTradingDataMap) {
		// close whole trade
		mainBean.setExitedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
		mainBean.setTradePosition(Constants.TRADE_POSIITON_CLOSE);
		// place order
		// kiteConnectTool.cancelBracketOrder(bean);
		// bean = kiteConnectTool.cancelCoverOrder(bean);
		
		for (OptionLiveTradeChildDataBean childDataBean : mainBean.getTradeChildBeanList()) {
			bookCurrentPutOrCallToToWholeTradeToClose(mainBean, childDataBean);
		}
		databaseHelper.saveShortStrangleOptionTrade(mainBean);
		optionTradingDataMap.remove(mainBean.getTradeName());
	}
	
	public void bookCurrentPutOrCallToToWholeTradeToClose(OptionLiveTradeMainDataBean mainBean,
			OptionLiveTradeChildDataBean childBean) {
		if (Constants.TRADE_POSIITON_OPEN.equals(childBean.getTradePosition())) {
			childBean.setTradePosition(Constants.TRADE_POSIITON_CLOSE);
			childBean.setExitedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());

			if (Constants.SELL.equals(childBean.getTradeType())) {
				childBean.setBuyAtValue(childBean.getAskPrice());
				childBean.setExitedAtVal(childBean.getAskPrice());
			} else if (Constants.BUY.equals(childBean.getTradeType())) {
				childBean.setSellAtValue(childBean.getBidPrice());
				childBean.setExitedAtVal(childBean.getBidPrice());
			}
			tradewareTool.updateLiveIndexPrice(mainBean.getSymbolId(), childBean);
		}
	}
	
	public OptionLiveTradeChildDataBean getAdjustmentOptionDataBean(OptionLiveTradeMainDataBean mainDataBean,
			OptionLiveTradeChildDataBean childDataBean, KiteLiveOHLCDataBean liveBean) {
		OptionLiveTradeChildDataBean putAdjustDataBean = new OptionLiveTradeChildDataBean();
		putAdjustDataBean.setStrikePrice(Double.valueOf(liveBean.getOptionStrikePrice()));
		putAdjustDataBean.setTradeType(childDataBean.getTradeType());
		putAdjustDataBean.setTradePosition(Constants.TRADE_POSIITON_OPEN);
		putAdjustDataBean.setOptionType(childDataBean.getOptionType());
		putAdjustDataBean.setKiteFutureKey(liveBean.getKiteFutureKey());
		if (Constants.SELL.equals(putAdjustDataBean.getTradeType())) {
			putAdjustDataBean.setSellAtValue(liveBean.getBidPrice());
			putAdjustDataBean.setTradedAtVal(liveBean.getBidPrice());
		} else if (Constants.BUY.equals(putAdjustDataBean.getTradeType())) {
			putAdjustDataBean.setBuyAtValue(liveBean.getAskPrice());
			putAdjustDataBean.setTradedAtVal(liveBean.getAskPrice());
		}
		putAdjustDataBean.setNumberOfLots(childDataBean.getNumberOfLots());
		putAdjustDataBean.setTradedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
		LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
		putAdjustDataBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
		putAdjustDataBean.setOptionChainTrend(null);
		putAdjustDataBean.setOptionChainPriceTrend(null);
		putAdjustDataBean.setOptionChainId(null);
		putAdjustDataBean.setOiInfo(null);
		putAdjustDataBean.setBidPrice(liveBean.getBidPrice());
		putAdjustDataBean.setAskPrice(liveBean.getAskPrice());
		putAdjustDataBean.setBidQuantity(liveBean.getBidQuantity());
		putAdjustDataBean.setAskQuantity(liveBean.getAskQuantity());
		tradewareTool.updateLiveIndexPrice(mainDataBean.getSymbolId(), putAdjustDataBean);
		return putAdjustDataBean;
	}
	
	public OptionLiveTradeChildDataBean getAdjustmentOptionDataBeanForButterFly(OptionLiveTradeMainDataBean mainDataBean,
			KiteLiveOHLCDataBean liveBean, String tradeType, String optionType, Integer numberOfLots) {
		OptionLiveTradeChildDataBean putAdjustDataBean = new OptionLiveTradeChildDataBean();
		putAdjustDataBean.setStrikePrice(Double.valueOf(liveBean.getOptionStrikePrice()));
		putAdjustDataBean.setTradeType(tradeType);
		putAdjustDataBean.setTradePosition(Constants.TRADE_POSIITON_OPEN);
		putAdjustDataBean.setOptionType(optionType);
		putAdjustDataBean.setKiteFutureKey(liveBean.getKiteFutureKey());
		if (Constants.SELL.equals(putAdjustDataBean.getTradeType())) {
			putAdjustDataBean.setSellAtValue(liveBean.getBidPrice());
			putAdjustDataBean.setTradedAtVal(liveBean.getBidPrice());
		} else if (Constants.BUY.equals(putAdjustDataBean.getTradeType())) {
			putAdjustDataBean.setBuyAtValue(liveBean.getAskPrice());
			putAdjustDataBean.setTradedAtVal(liveBean.getAskPrice());
		}
		putAdjustDataBean.setNumberOfLots(numberOfLots);
		putAdjustDataBean.setTradedAtDtTm(tradewareTool.getCurrentDateTimeAsDate());
		LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
		putAdjustDataBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
		putAdjustDataBean.setOptionChainTrend(null);
		putAdjustDataBean.setOptionChainPriceTrend(null);
		putAdjustDataBean.setOptionChainId(null);
		putAdjustDataBean.setOiInfo(null);
		putAdjustDataBean.setBidPrice(liveBean.getBidPrice());
		putAdjustDataBean.setAskPrice(liveBean.getAskPrice());
		putAdjustDataBean.setBidQuantity(liveBean.getBidQuantity());
		putAdjustDataBean.setAskQuantity(liveBean.getAskQuantity());
		tradewareTool.updateLiveIndexPrice(mainDataBean.getSymbolId(), putAdjustDataBean);
		return putAdjustDataBean;
	}
	
	public void updateLiveOHLCFromLiveDataBeanToChildDataBean(KiteLiveOHLCDataBean liveDataBean,
			OptionLiveTradeChildDataBean childBean) {
		childBean.setLastPrice(liveDataBean.getLtp());
		childBean.setBidPrice(liveDataBean.getBidPrice());
		childBean.setAskPrice(liveDataBean.getAskPrice());
		childBean.setBidQuantity(liveDataBean.getBidQuantity());
		childBean.setAskQuantity(liveDataBean.getAskQuantity());
		//main stock and future price
	}
	
	private void caluclateStopLossAndTargetValues(OptionLiveTradeMainDataBean mainBean) {
		double totalPaidPremium = 0;
		if ((OPTION_STRATEGY_STRADDLE.equals(mainBean.getTradeStrategy())
				&& OPTION_STRATEGY_SHORT_STRADDLE.equals(mainBean.getTradeSubStrategy()))
				|| (OPTION_STRATEGY_STRANGLE.equals(mainBean.getTradeStrategy())
						&& OPTION_STRATEGY_SHORT_STRANGLE.equals(mainBean.getTradeSubStrategy()))) {
			for (OptionLiveTradeChildDataBean childBean : mainBean.getTradeChildBeanList()) {
				childBean.setStopLossVal(kiteConnectTool.getRoundupToTwoValue(
						childBean.getTradedAtVal() + (childBean.getTradedAtVal() * STRATEGY_STRADDLE_STOP_LOSS_VALUE)));
				totalPaidPremium = totalPaidPremium + childBean.getTradedAtVal();
			}
			mainBean.setTargetVal(
					kiteConnectTool.getRoundupToTwoValue(totalPaidPremium * STRATEGY_STRADDLE_STOP_TARGET_VALUE));
		}
	}
	
	//May be dead code
	public KiteLiveOHLCDataBean findOptionCallOrPutForAdustment(OptionLiveTradeMainDataBean mainBean,
			Double strikePrice, String callOrPut, Double findThisAdjustmentValue) {
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = tradewareTool
				.getOptionStrickListForStrategyAdustments(mainBean, strikePrice, callOrPut);
		map = kiteConnectTool.updateLiveOHLC(map);
		KiteLiveOHLCDataBean liveBean = tradewareTool.findNearestOptionCallOrPutForAdustment(map,
				findThisAdjustmentValue);
		return liveBean;
	}
	//May be dead code
	public KiteLiveOHLCDataBean findOptionCallOrPutForAdustment(
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> map, Double findThisAdjustmentValue) {
		KiteLiveOHLCDataBean liveBean = tradewareTool.findNearestOptionCallOrPutForAdustment(map,
				findThisAdjustmentValue);
		return liveBean;
	}
}
