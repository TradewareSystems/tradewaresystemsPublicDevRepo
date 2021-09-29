package com.tradeware.stockmarket.tool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.traderules.TradewareOrderPlacementRuleTool;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TradewareTool extends AbstractTradewareTool {

	

	public String getKiteFutureKey(String symbol) {
		String currExp = getCurrentMonthExpiryDate();
		return (FUTURE_KEY_PREFIX_NFO + symbol + currExp.substring(7, 9) + currExp.substring(2, 5)
				+ FUTURE_KEY_SUFFIX_NFO).toUpperCase();
	}
	public String getSymbolName(String kiteFuturekey) {
		if (kiteFuturekey.startsWith(FUTURE_KEY_PREFIX_NSE)) {
			return kiteFuturekey.replace(FUTURE_KEY_PREFIX_NSE, EMPTY_STRING);
		} else {
			String currExp = getCurrentMonthExpiryDate();
			kiteFuturekey = kiteFuturekey.replace(FUTURE_KEY_PREFIX_NFO, EMPTY_STRING);
			return kiteFuturekey.replace(
					((currExp.substring(7, 9) + currExp.substring(2, 5) + FUTURE_KEY_SUFFIX_NFO).toUpperCase()),
					EMPTY_STRING) + FUTURE_SYMBOL;
		}
	}
	
	public String getKiteOptionKey(String symbolId, String expiryDate, String strikePrice, String callPutType) {
		return (Constants.FUTURE_KEY_PREFIX_NFO + symbolId + expiryDate.substring(9, 11) + expiryDate.substring(3, 6)
				+ stripTrailingZeros(strikePrice) + callPutType).toUpperCase();
	}

	public String getKiteOptionKeyWeekly(String symbolId, String expiryDate, String strikePrice,
			String callPutType) {
		return (Constants.FUTURE_KEY_PREFIX_NFO + symbolId + expiryDate.substring(9, 11) + expiryDate.substring(3, 4)
				+ expiryDate.substring(0, 2) + stripTrailingZeros(strikePrice) + callPutType).toUpperCase();
	}
	
	public String getKiteOptionKeyWeeklyOrMonthly(String symbolId, String expiryDate, String strikePrice,
			String callPutType) {
		String optionKKey = null;
		if (isMontlyOrWeeklyExpiry(expiryDate)) {
			optionKKey = getKiteOptionKey(symbolId, expiryDate, strikePrice, callPutType);
		} else {
			optionKKey = getKiteOptionKeyWeekly(symbolId, expiryDate, strikePrice, callPutType);
		}
		return optionKKey;
	}
	
	public boolean isTodayExpiryDay() {
		boolean isTodayExpiryDay = false;
		return isTodayExpiryDay;
	}
	
	//NFO:NIFTY21Sep17000CE


	//NIFTY21J0713600CE
	
	/*
	 * if (Constants.OPTION_CALL.equals(callPutType)) {
				key = ("NFO:" + symbol + currExp.substring(7, 9) + currExp.substring(2, 5) + optionTradeAt + callPutType)
						.toUpperCase();
			} else if (Constants.OPTION_PUT.equals(callPutType)) {
				key = ("NFO:" + symbol + currExp.substring(7, 9) + currExp.substring(2, 5) + optionTradeAt + callPutType)
						.toUpperCase();
			}
	 */
	
	public StrategyOrbDataBean handleTrailingStopLoss222(StrategyOrbDataBean bean) {
		double targetStopLoss = kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS / bean.getLotSize());

		if (BUY.equals(bean.getTradedStateId())) {

//			bean.setTargetPrice(kiteConnectTool.getRoundupToTwoValue(TARGET_AMOUNT_4000 / bean.getLotSize())
//					+ bean.getTradedAtVal().doubleValue());
			bean.setTargetPrice(kiteConnectTool.getRoundupToTwoValue(TARGET_AMOUNT_5000 / bean.getLotSize()
					+ bean.getTradedAtVal().doubleValue()));

			targetStopLoss = bean.getTradedAtVal() - targetStopLoss;
			bean.setBaseStopLoss(kiteConnectTool
					.getRoundupToTwoValueUp(targetStopLoss > bean.getSellAtVal() ? targetStopLoss : bean.getSellAtVal()));

			bean.setTrailingStopLoss(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp((TARGET_CO_STOP_LOSS_PROFIT_1000 / bean.getLotSize())));
			bean.setTrailingStopLoss1500(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp((TARGET_CO_STOP_LOSS_PROFIT_1500 / bean.getLotSize())));
			bean.setTrailingStopLoss2000(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp(TARGET_CO_STOP_LOSS_PROFIT_2000 / bean.getLotSize()));
			bean.setTrailingStopLoss2500(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp(TARGET_CO_STOP_LOSS_PROFIT_2500 / bean.getLotSize()));
			bean.setTrailingStopLoss3000(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp(TARGET_CO_STOP_LOSS_PROFIT_3000 / bean.getLotSize()));

//10-DEC-2020
if (TRADE_TYPE_REVERSE.equals(bean.getTradeType())) {
	bean.setBaseStopLoss(kiteConnectTool
			.getRoundupToTwoValueUp(targetStopLoss));
}
// 10-DEC-2020			
		} else if (SELL.equals(bean.getTradedStateId())) {

			bean.setTargetPrice(kiteConnectTool.getRoundupToTwoValue(bean.getTradedAtVal().doubleValue()
					- TARGET_AMOUNT_4000 / bean.getLotSize()));

			targetStopLoss = bean.getTradedAtVal() + targetStopLoss;
			bean.setBaseStopLoss(kiteConnectTool
					.getRoundupToTwoValue(targetStopLoss < bean.getBuyAtVal() ? targetStopLoss : bean.getBuyAtVal()));

			bean.setTrailingStopLoss(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_1000 / bean.getLotSize()));
			bean.setTrailingStopLoss1500(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_1500 / bean.getLotSize()));
			bean.setTrailingStopLoss2000(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_2000 / bean.getLotSize()));
			bean.setTrailingStopLoss2500(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_2500 / bean.getLotSize()));
			bean.setTrailingStopLoss3000(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_3000 / bean.getLotSize()));
		
//10-DEC-2020
if (TRADE_TYPE_REVERSE.equals(bean.getTradeType())) {
	bean.setBaseStopLoss(kiteConnectTool
			.getRoundupToTwoValueUp(targetStopLoss));
}
// 10-DEC-2020
		}
		
		/*if (STRATEGY_NR7_R1.equals(bean.getStrategyRule())
				|| STRATEGY_ORB_R1.equals(bean.getStrategyRule())) {
			if (BUY.equals(bean.getTradedStateId())) {
				bean.setBaseStopLoss(kiteConnectTool.getRoundupToTwoValueUp(bean.getSellAtVal()));
			} else if (SELL.equals(bean.getTradedStateId())) {
				bean.setBaseStopLoss(kiteConnectTool.getRoundupToTwoValueUp(bean.getBuyAtVal()));
			}
		}*/
		
		return bean;
	}
	public StrategyOrbDataBean handleTrailingStopLoss(StrategyOrbDataBean bean) {
		double targetStopLoss = kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS / bean.getLotSize());

		if (BUY.equals(bean.getTradedStateId())) {
			bean.setTargetPrice(kiteConnectTool.getRoundupToTwoValue(TARGET_AMOUNT_7500 / bean.getLotSize()
					+ bean.getTradedAtVal().doubleValue()));
			if (bean.getDayLevelTradeInd() != null && bean.getDayLevelTradeInd()) {
				bean.setTargetPrice(kiteConnectTool.getRoundupToTwoValue(
						TARGET_AMOUNT_15000 / bean.getLotSize() + bean.getTradedAtVal().doubleValue()));
			}

			targetStopLoss = bean.getTradedAtVal() - targetStopLoss;
			//if (bean.getSymbolName().contains(OPTION_KEY_SUFFIX)) {
			if ((bean.getSymbolName().endsWith(OPTION_CALL))
					|| bean.getSymbolName().endsWith(OPTION_PUT)) {
				targetStopLoss = kiteConnectTool.getRoundupToTwoValue(TARGET_SAFE_STOP_LOSS / bean.getLotSize());
				bean.setBaseStopLoss(kiteConnectTool.getRoundupToTwoValueUp(targetStopLoss));
			} else {
				bean.setBaseStopLoss(kiteConnectTool.getRoundupToTwoValueUp(
						targetStopLoss > bean.getSellAtVal() ? targetStopLoss : bean.getSellAtVal()));
			}
			
			bean.setTrailingStopLoss250(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp((TARGET_CO_STOP_LOSS_PROFIT_250 / bean.getLotSize())));
			
			bean.setTrailingStopLoss(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp((TARGET_CO_STOP_LOSS_PROFIT_1000 / bean.getLotSize())));
			bean.setTrailingStopLoss1500(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp((TARGET_CO_STOP_LOSS_PROFIT_1500 / bean.getLotSize())));
			bean.setTrailingStopLoss2000(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp(TARGET_CO_STOP_LOSS_PROFIT_2000 / bean.getLotSize()));
			bean.setTrailingStopLoss2500(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp(TARGET_CO_STOP_LOSS_PROFIT_2500 / bean.getLotSize()));
			bean.setTrailingStopLoss3000(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp(TARGET_CO_STOP_LOSS_PROFIT_3000 / bean.getLotSize()));
			bean.setTrailingStopLoss4000(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp(TARGET_CO_STOP_LOSS_PROFIT_4000 / bean.getLotSize()));
			
			bean.setTrailingStopLoss5000(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp(TARGET_CO_STOP_LOSS_PROFIT_5000 / bean.getLotSize()));
			bean.setTrailingStopLoss6000(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp(TARGET_CO_STOP_LOSS_PROFIT_6000 / bean.getLotSize()));
			
			bean.setTrailingStopLoss7500(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp(TARGET_CO_STOP_LOSS_PROFIT_7500 / bean.getLotSize()));
			bean.setTrailingStopLoss10000(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp(TARGET_CO_STOP_LOSS_PROFIT_10000 / bean.getLotSize()));
			bean.setTrailingStopLoss12500(
					bean.getTradedAtVal() + kiteConnectTool.getRoundupToTwoValueUp(TARGET_CO_STOP_LOSS_PROFIT_12500 / bean.getLotSize()));

//10-DEC-2020
if (TRADE_TYPE_REVERSE.equals(bean.getTradeType())) {
	bean.setBaseStopLoss(kiteConnectTool.getRoundupToTwoValueUp(targetStopLoss));
}
// 10-DEC-2020			
		} else if (SELL.equals(bean.getTradedStateId())) {

			bean.setTargetPrice(kiteConnectTool.getRoundupToTwoValue(bean.getTradedAtVal().doubleValue()
					- TARGET_AMOUNT_7500 / bean.getLotSize()));
			if (bean.getDayLevelTradeInd() != null && bean.getDayLevelTradeInd()) {
				bean.setTargetPrice(kiteConnectTool.getRoundupToTwoValue(bean.getTradedAtVal().doubleValue()
						- TARGET_AMOUNT_15000 / bean.getLotSize()));
			}

			targetStopLoss = bean.getTradedAtVal() + targetStopLoss;
			bean.setBaseStopLoss( kiteConnectTool.getRoundupToTwoValue(targetStopLoss < bean.getBuyAtVal() ? targetStopLoss : bean.getBuyAtVal()));

			bean.setTrailingStopLoss250(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_250 / bean.getLotSize()));
			bean.setTrailingStopLoss(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_1000 / bean.getLotSize()));
			bean.setTrailingStopLoss1500(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_1500 / bean.getLotSize()));
			bean.setTrailingStopLoss2000(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_2000 / bean.getLotSize()));
			bean.setTrailingStopLoss2500(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_2500 / bean.getLotSize()));
			bean.setTrailingStopLoss3000(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_3000 / bean.getLotSize()));
			bean.setTrailingStopLoss4000(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_4000 / bean.getLotSize()));
			
			bean.setTrailingStopLoss5000(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_5000 / bean.getLotSize()));
			bean.setTrailingStopLoss6000(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_6000 / bean.getLotSize()));
			bean.setTrailingStopLoss7500(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_7500 / bean.getLotSize()));
			bean.setTrailingStopLoss10000(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_10000 / bean.getLotSize()));
			bean.setTrailingStopLoss12500(
					bean.getTradedAtVal() - kiteConnectTool.getRoundupToTwoValue(TARGET_CO_STOP_LOSS_PROFIT_12500 / bean.getLotSize()));
		
//10-DEC-2020
if (TRADE_TYPE_REVERSE.equals(bean.getTradeType())) {
	bean.setBaseStopLoss( kiteConnectTool.getRoundupToTwoValueUp(targetStopLoss));
}
// 10-DEC-2020
		}
		
		/*if (STRATEGY_NR7_R1.equals(bean.getStrategyRule())
				|| STRATEGY_ORB_R1.equals(bean.getStrategyRule())) {
			if (BUY.equals(bean.getTradedStateId())) {
				bean.setBaseStopLoss(kiteConnectTool.getRoundupToTwoValueUp(bean.getSellAtVal()));
			} else if (SELL.equals(bean.getTradedStateId())) {
				bean.setBaseStopLoss(kiteConnectTool.getRoundupToTwoValueUp(bean.getBuyAtVal()));
			}
		}*/
		
		return bean;
	}
	public StrategyOrbDataBean handleStopLossValue(StrategyOrbDataBean bean) {

		if (null != bean.getPositiveDirectionProfit()) {
			if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_3500) {
				bean.setStopLoss(bean.getTrailingStopLoss3000());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_3000) {
				bean.setStopLoss(bean.getTrailingStopLoss2500());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_2500) {
				bean.setStopLoss(bean.getTrailingStopLoss2000());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_2000) {
				bean.setStopLoss(bean.getTrailingStopLoss1500());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_1500) {
				bean.setStopLoss(bean.getTrailingStopLoss());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_1000) {
				bean.setStopLoss(bean.getTradedAtVal()); // cost to cost
			} else {
				/*if (!(STRATEGY_NR7_R1.equals(bean.getStrategyRule())
						|| STRATEGY_ORB_R1.equals(bean.getStrategyRule()))) {
					if (null != bean.getPositiveDirectionLtp()) {
						if (BUY.equals(bean.getTradedStateId())) {
							bean.setStopLoss(
									bean.getBaseStopLoss() + (bean.getPositiveDirectionLtp() - bean.getTradedAtVal()));
						} else if (SELL.equals(bean.getTradedStateId())) {
							bean.setStopLoss(
									bean.getBaseStopLoss() - (bean.getTradedAtVal() - bean.getPositiveDirectionLtp()));
						}
					} else {
						bean.setStopLoss(bean.getBaseStopLoss());
					}
				} else {
					bean.setStopLoss(bean.getBaseStopLoss());
				}*/
				if (null != bean.getPositiveDirectionLtp()) {
					if (BUY.equals(bean.getTradedStateId())) {
						bean.setStopLoss(
								bean.getBaseStopLoss() + (bean.getPositiveDirectionLtp() - bean.getTradedAtVal()));
					} else if (SELL.equals(bean.getTradedStateId())) {
						bean.setStopLoss(
								bean.getBaseStopLoss() - (bean.getTradedAtVal() - bean.getPositiveDirectionLtp()));
					}
				} else {
					bean.setStopLoss(bean.getBaseStopLoss());
				}
			}
		}
		return bean;
	}
public StrategyOrbDataBean handleStopLossValue2(StrategyOrbDataBean bean) {
		
		if (isIndexOrOptionTrade(bean)) {
			bean = handleStopLossValue2_IndexOrOption(bean);
		} else 

		if (null != bean.getPositiveDirectionProfit()) {
			if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_15000) {
				bean.setStopLoss(bean.getTrailingStopLoss12500());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_12500) {
				bean.setStopLoss(bean.getTrailingStopLoss10000());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_10000) {
				bean.setStopLoss(bean.getTrailingStopLoss7500());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_7500) {
				bean.setStopLoss(bean.getTrailingStopLoss5000());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_5000) {
				bean.setStopLoss(bean.getTrailingStopLoss2500());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_2500) {
				bean.setStopLoss(bean.getTradedAtVal()); // cost to cost
			} else {
				
				/*if (null != bean.getPositiveDirectionLtp()) {
					if (BUY.equals(bean.getTradedStateId())) {
						bean.setStopLoss(
								bean.getBaseStopLoss() + (bean.getPositiveDirectionLtp() - bean.getTradedAtVal()));
					} else if (SELL.equals(bean.getTradedStateId())) {
						bean.setStopLoss(
								bean.getBaseStopLoss() - (bean.getTradedAtVal() - bean.getPositiveDirectionLtp()));
					}
				} else {
					bean.setStopLoss(bean.getBaseStopLoss());
				}*/
			}
		}
		return bean;
	}
	
	public StrategyOrbDataBean handleStopLossValue2_IndexOrOption(StrategyOrbDataBean bean) {

		if (null != bean.getPositiveDirectionProfit()) {
			if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_15000) {
				bean.setStopLoss(bean.getTrailingStopLoss12500());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_12500) {
				bean.setStopLoss(bean.getTrailingStopLoss10000());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_10000) {
				bean.setStopLoss(bean.getTrailingStopLoss7500());
			} else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_7500) {
				bean.setStopLoss(bean.getTrailingStopLoss6000());
			}
			 else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_6000) {
					bean.setStopLoss(bean.getTrailingStopLoss5000());
				}
			 else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_5000) {
					bean.setStopLoss(bean.getTrailingStopLoss4000());
				}
			 else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_4000) {
					bean.setStopLoss(bean.getTrailingStopLoss3000());
				}
			
			 else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_3000) {
					bean.setStopLoss(bean.getTrailingStopLoss2000());
				}
			
			else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_2000) {
				bean.setStopLoss(bean.getTrailingStopLoss());
			} 
			else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_1200) {
				bean.setStopLoss(bean.getTrailingStopLoss250());
			} 
			else if (bean.getPositiveDirectionProfit() > TARGET_CO_STOP_LOSS_PROFIT_1000) {
				bean.setStopLoss(bean.getTradedAtVal()); // cost to cost
			} else {
				
				/*if (null != bean.getPositiveDirectionLtp()) {
					if (BUY.equals(bean.getTradedStateId())) {
						bean.setStopLoss(
								bean.getBaseStopLoss() + (bean.getPositiveDirectionLtp() - bean.getTradedAtVal()));
					} else if (SELL.equals(bean.getTradedStateId())) {
						bean.setStopLoss(
								bean.getBaseStopLoss() - (bean.getTradedAtVal() - bean.getPositiveDirectionLtp()));
					}
				} else {
					bean.setStopLoss(bean.getBaseStopLoss());
				}*/
			}
		}
		return bean;
	}
	
	//Phase 5 start
	public StrategyOrbDataBean handleTrailingStopLossValue(StrategyOrbDataBean bean) {
		if (null == bean.getPositiveDirectionProfitPrevious() || 0 == bean.getPositiveDirectionProfitPrevious()
				|| bean.getPositiveDirectionProfit() > bean.getPositiveDirectionProfitPrevious()) {
			bean.setPositiveDirectionProfitPrevious(bean.getPositiveDirectionProfit());

			if (null != bean.getPositiveDirectionProfitPrevious() && null != bean.getPositiveDirectionProfit()
					&& bean.getPositiveDirectionProfit() >= bean.getPositiveDirectionProfitPrevious()) {

				double positiveDirectionLtp = (null != bean.getPositiveDirectionLtp()
						&& 0 != bean.getPositiveDirectionLtp()) ? bean.getPositiveDirectionLtp()
								: bean.getTradedAtVal();
				// 04-21-2021 start - afterSomeSuccess [05-07-2021]
				double targetStopLoss = kiteConnectTool.getRoundupToTwoValue(
						(isIndexOrOptionTrade(bean) ? TARGET_SAFE_STOP_LOSS_600 : getTargetStopLossValue(bean))
								/ bean.getLotSize());
				// 04-21-2021 end - afterSomeSuccess
				//04-21-2021  end - afterSomeSuccess
				/*double targetStopLoss = kiteConnectTool.getRoundupToTwoValue(
						(isIndexOrOptionTrade(bean) ? TARGET_SAFE_STOP_LOSS_500 : TARGET_AMOUNT)
								/ bean.getLotSize());*/

				if (BUY.equals(bean.getTradedStateId())) {
					bean.setStopLoss(kiteConnectTool.getRoundupToTwoValue(positiveDirectionLtp - targetStopLoss));
					bean.setBaseStopLoss(kiteConnectTool.getRoundupToTwoValue(positiveDirectionLtp - targetStopLoss));
				} else if (SELL.equals(bean.getTradedStateId())) {
					bean.setStopLoss(kiteConnectTool.getRoundupToTwoValue(positiveDirectionLtp + targetStopLoss));
					bean.setBaseStopLoss(kiteConnectTool.getRoundupToTwoValue(positiveDirectionLtp + targetStopLoss));
				}
			}
		}
		return bean;
	}
	
	// 04-21-2021 start - afterSomeSuccess [06/01/2021]
	private double getTargetStopLossValue(StrategyOrbDataBean bean) {
		double targetStopLoss = TARGET_AMOUNT;
		if (null != bean.getPositiveDirectionProfit()) {
			if (bean.getPositiveDirectionProfit() >= TARGET_CO_STOP_LOSS_PROFIT_6000) {
				targetStopLoss = TARGET_SAFE_STOP_LOSS_1000;
			} else if (bean.getPositiveDirectionProfit() >= TARGET_CO_STOP_LOSS_PROFIT_4500) {
				targetStopLoss = TARGET_SAFE_STOP_LOSS_1250;
			} else if (bean.getPositiveDirectionProfit() >= TARGET_CO_STOP_LOSS_PROFIT_3000) {
				targetStopLoss = TARGET_SAFE_STOP_LOSS_1500;
			}
		}
		return targetStopLoss;
	}
	// 04-21-2021 END - afterSomeSuccess [06/01/2021]
	//Phase 5 end
	
	//Phase 3 start
	protected ConcurrentHashMap<String, StrategyOrbDataBean> ohlcRuleBreakDataMap = new ConcurrentHashMap<String, StrategyOrbDataBean>();

	public void validateOHLCRule() {
		if(null!=getBaseDataMap() && !getBaseDataMap().isEmpty()) {
				setBaseDataMap(kiteConnectTool.updateDayOHLC(getBaseDataMap()));
				if (getBaseDataMap().keySet().size() > 0) {
					for (String key : getBaseDataMap().keySet()) {
						StrategyOrbDataBean bean = validateOHLCRule(getBaseDataMap().get(key));
						
							if (bean.getSellerAt() == 0 || bean.getBuyerAt() == 0
									|| !bean.isBuyerSelleDiffRuleValid()) {
								bean.setOhlcStateId(BUY_SELL_BREAK);
								bean.setTradableStateId(NA);
								bean.setTradedStateId(BUY_SELL_BREAK_TRADED_STATE);
								bean.setStrengthableTradeStateId(STNA); 
								bean.setVolumeTradeStateId(NA);
								bean.setVwapTradeStateId(VWNA);
								bean.setBuyAtVal(bean.getHigh());
								bean.setSellAtVal(bean.getLow());
								bean.setExitedAtDtTm(getCurrentDateTimeAsDate());
								bean.setTradedAtDtTm(getCurrentDateTimeAsDate());
								databaseHelper.saveTradeOnEntry(bean);
								getBaseDataMap().replace(bean.getKiteFutureKey(), bean);
								//getBaseDataMap().remove(bean.getKiteFutureKey());
								ohlcRuleBreakDataMap.put(bean.getKiteFutureKey()+UNDER_SCORE+getCurrentTime(), bean);
							}

							else if (null != bean.getOhlcStateId() && !OHLC_RULE_BREAK.equals(bean.getOhlcStateId())) {
							getBaseDataMap().replace(bean.getKiteFutureKey(), bean);
						} else if (OHLC_RULE_BREAK.equals(bean.getOhlcStateId())) {
							bean.setOhlcStateId(OHLC_RULE_BREAK);
							bean.setTradableStateId(NA);
							bean.setBuyAtVal(bean.getHigh());
							bean.setSellAtVal(bean.getLow());
							bean.setTradedStateId(BUY_SELL_BREAK_TRADED_STATE);
							bean.setStrengthableTradeStateId(STNA); 
							bean.setVolumeTradeStateId(NA);
							bean.setVwapTradeStateId(VWNA);
							bean.setExitedAtDtTm(getCurrentDateTimeAsDate());
							bean.setTradedAtDtTm(getCurrentDateTimeAsDate());
							databaseHelper.saveTradeOnEntry(bean);
							//getBaseDataMap().remove(bean.getKiteFutureKey());
							ohlcRuleBreakDataMap.put(bean.getKiteFutureKey()+UNDER_SCORE+getCurrentTime(), bean);
						}
					}
				}
			} else {
				TradewareLogger.saveInfoLog("getBaseDataMap()",
		"getBaseDataMap()",
						"getBaseDataMap()  --- IS STILL NULL");
			}
				if(null!=getBaseDataMapAll() && !getBaseDataMapAll().isEmpty()) {
				setBaseDataMapAll(kiteConnectTool.updateDayOHLC(getBaseDataMapAll()));
				if (getBaseDataMapAll().keySet().size() > 0) {
					for (String key : getBaseDataMapAll().keySet()) {
						StrategyOrbDataBean bean = validateOHLCRule(getBaseDataMapAll().get(key));

						if (bean.getSellerAt() == 0 || bean.getBuyerAt() == 0
								|| !bean.isBuyerSelleDiffRuleValid()) {
							bean.setOhlcStateId(BUY_SELL_BREAK);
							bean.setTradableStateId(NA);
							bean.setTradedStateId(BUY_SELL_BREAK_TRADED_STATE);
							bean.setStrengthableTradeStateId(STNA);
							bean.setBuyAtVal(bean.getHigh());
							bean.setSellAtVal(bean.getLow());
							bean.setExitedAtDtTm(getCurrentDateTimeAsDate());
							bean.setTradedAtDtTm(getCurrentDateTimeAsDate());
							bean.setVolumeTradeStateId(NA);
							bean.setVwapTradeStateId(VWNA);
							databaseHelper.saveTradeOnEntry(bean);
							getBaseDataMapAll().replace(bean.getKiteFutureKey(), bean);
							//getBaseDataMapAll().remove(bean.getKiteFutureKey());
							//ohlcRuleBreakDataMap.put(bean.getKiteFutureKey(), bean);
						}
						if (!OHLC_RULE_BREAK.equals(bean.getOhlcStateId())) {
							getBaseDataMapAll().replace(bean.getKiteFutureKey(), bean);
						} else if (OHLC_RULE_BREAK.equals(bean.getOhlcStateId())) {
							bean.setOhlcStateId(OHLC_RULE_BREAK);
							bean.setTradableStateId(NA);
							bean.setBuyAtVal(bean.getHigh());
							bean.setSellAtVal(bean.getLow());
							bean.setStrengthableTradeStateId(STNA); 
							bean.setVolumeTradeStateId(NA);
							bean.setVwapTradeStateId(VWNA);
							//04-21-2021  start - afterSomeSuccess
							bean.setTradedStateId(NA);
							//bean.setTradedStateId(BUY_SELL_BREAK_TRADED_STATE);
							//bean.setExitedAtDtTm(getCurrentDateTimeAsDate());
							//bean.setTradedAtDtTm(getCurrentDateTimeAsDate());
							//databaseHelper.saveTradeOnEntry(bean);
							//04-21-2021  end
							
							//getBaseDataMapAll().remove(bean.getKiteFutureKey());
							ohlcRuleBreakDataMap.put(bean.getKiteFutureKey(), bean);
						}
					}
				} }else {
					TradewareLogger.saveInfoLog("getBaseDataMapAll()",
							"getBaseDataMapAll()",
											"getBaseDataMapAll()  --- IS STILL NULL");
								}
			}
	private double ignorableSpike;
	private double ignorableSpike1;
	private double ignorableSpike1Dot5;
	private double ignorableSpike2;
	private double ignorableSpike2Dot5;
	private StrategyOrbDataBean validateOHLCRule(StrategyOrbDataBean bean) {
		 ignorableSpike = kiteConnectTool.getRoundupToTwoValue(
				bean.getOpen().doubleValue() * IGNORABLE_OPEN_SPIKE);
		 ignorableSpike1 = kiteConnectTool.getRoundupToTwoValue(
					bean.getOpen().doubleValue() * IGNORABLE_OPEN_SPIKE_1);
		 ignorableSpike1Dot5 = kiteConnectTool.getRoundupToTwoValue(
					bean.getOpen().doubleValue() * IGNORABLE_OPEN_SPIKE_1DOT5);
		 ignorableSpike2 = kiteConnectTool.getRoundupToTwoValue(
					bean.getOpen().doubleValue() * IGNORABLE_OPEN_SPIKE_2);
		 ignorableSpike2Dot5= kiteConnectTool.getRoundupToTwoValue(
					bean.getOpen().doubleValue() * IGNORABLE_OPEN_SPIKE_2DOT5);
		
		if (bean.getOpen().doubleValue() == bean.getLow().doubleValue()) {
			bean.setOhlcStateId(BUY);
		} else if (bean.getOpen().doubleValue() == bean.getHigh().doubleValue()) {
			bean.setOhlcStateId(SELL);
		} else if (bean.getLow().doubleValue() >= (bean.getOpen().doubleValue() - ignorableSpike)) {
			bean.setOhlcStateId(BUY_SPIKE);
		} else if (bean.getHigh().doubleValue() <= (bean.getOpen().doubleValue() + ignorableSpike)) {
			bean.setOhlcStateId(SELL_SPIKE);
		} else if (bean.getLow().doubleValue() >= (bean.getOpen().doubleValue() - ignorableSpike1)) {
			bean.setOhlcStateId(BUY_SPIKE_1);
		} else if (bean.getHigh().doubleValue() <= (bean.getOpen().doubleValue() + ignorableSpike1)) {
			bean.setOhlcStateId(SELL_SPIKE_1);
		} else if (bean.getLow().doubleValue() >= (bean.getOpen().doubleValue() - ignorableSpike1Dot5)) {
			bean.setOhlcStateId(BUY_SPIKE_1DOT5);
		} else if (bean.getHigh().doubleValue() <= (bean.getOpen().doubleValue() + ignorableSpike1Dot5)) {
			bean.setOhlcStateId(SELL_SPIKE_1DOT5);
		} else if (bean.getLow().doubleValue() >= (bean.getOpen().doubleValue() - ignorableSpike2)) {
			bean.setOhlcStateId(BUY_SPIKE_2);
		} else if (bean.getHigh().doubleValue() <= (bean.getOpen().doubleValue() + ignorableSpike2)) {
			bean.setOhlcStateId(SELL_SPIKE_2);
		} else if (bean.getLow().doubleValue() >= (bean.getOpen().doubleValue() - ignorableSpike2Dot5)) {
			bean.setOhlcStateId(BUY_SPIKE_2DOT5);
		} else if (bean.getHigh().doubleValue() <= (bean.getOpen().doubleValue() + ignorableSpike2Dot5)) {
			bean.setOhlcStateId(SELL_SPIKE_2DOT5);
		} else {
			//Change on 11/21/2020 start
			bean.setOhlcStateId(BUY_SELL_SPIKE_3);//bean.setOhlcStateId(OHLC_RULE_BREAK);
			//Change on 11/21/2020 end
			
			/*getBaseDataMap().remove(bean.getKiteFutureKey());
			
			bean.setOhlcStateId(OHLC_RULE_BREAK);
			bean.setTradableStateId(NA);
			bean.setBuyAtVal(bean.getHigh());
			bean.setSellAtVal(bean.getLow());
			bean.setTradedStateId(OHLC_RULE_BREAK);
			bean.setExitedAtDtTm(getCurrentDateTimeAsDate());
			bean.setTradedAtDtTm(getCurrentDateTimeAsDate());
			databaseHelper.saveTradeOnEntry(bean);*/
		}
		/*if (null != bean.getOhlcStateId() && !OHLC_RULE_BREAK.equals(bean.getOhlcStateId())) {
			getBaseDataMap().replace(bean.getKiteFutureKey(),  bean);
		}*/
		return bean;
	}
	
	
	
	public void forceExitTradesAt3_20PM(ConcurrentHashMap<String, StrategyOrbDataBean> tradingDataMap) {
		for (String symbol : tradingDataMap.keySet()) {
			StrategyOrbDataBean bean = tradingDataMap.get(symbol);
			double lastPrice = bean.getLtp().doubleValue();
			if (BUY.equals(bean.getTradedStateId())) {
				lastPrice = bean.getBuyerAt();

				if (lastPrice > bean.getTradedAtAvgVal().doubleValue()) {
					bean.setTradedStateId(BUY_EXIT_PROFIT_FORCE);
					bean.setExitedAtVal(bean.getBuyerAt());
					bean.setExitedAtDtTm(getCurrentDateTimeAsDate());
					/*
					 * double netProfitVal = new BigDecimal( (lastPrice -
					 * bean.getTradedAtVal().doubleValue()) * bean.getLotSize()).setScale(2, 0)
					 * .doubleValue();
					 */
					double netProfitVal = new BigDecimal((lastPrice - bean.getTradedAtAvgVal().doubleValue())
							* (bean.getLotSize() * bean.getTradedLotCount())).setScale(2, 0).doubleValue();

					bean.setProfitLossAmtVal(netProfitVal);
					bean.setSignalTriggeredOutAt(
							getCurrentTime() + BUY_TARGET + lastPrice + WITH_PROFIT + netProfitVal);

					DatabaseHelper.getInstance().updateTrade(bean);
					tradingDataMap.replace(symbol, bean);
				} else if (lastPrice <= bean.getTradedAtAvgVal().doubleValue()) {
					bean.setTradedStateId(BUY_EXIT_LOSS_FORCE);
					bean.setExitedAtVal(bean.getBuyerAt());
					bean.setExitedAtDtTm(getCurrentDateTimeAsDate());
					/*
					 * double netLossVal = new BigDecimal( (lastPrice -
					 * bean.getTradedAtVal().doubleValue()) * bean.getLotSize()).setScale(2, 0)
					 * .doubleValue();
					 */
					double netLossVal = new BigDecimal((lastPrice - bean.getTradedAtAvgVal().doubleValue())
							* (bean.getLotSize() * bean.getTradedLotCount())).setScale(2, 0).doubleValue();
					bean.setProfitLossAmtVal(netLossVal);
					bean.setSignalTriggeredOutAt(
							getCurrentTime() + CLOSED_WITH_LOSE + lastPrice + WITH_LOSS + netLossVal);// +
					// additionalMsg);

					DatabaseHelper.getInstance().updateTrade(bean);
					tradingDataMap.replace(symbol, bean);
				}
			} else if (SELL.equals(bean.getTradedStateId())) {
				lastPrice = bean.getSellerAt();
				bean.getBuyerAt();// lastPrice = narrow7Bean.getSellerAt();
				if (lastPrice < bean.getTradedAtAvgVal().doubleValue()) {
					bean.setTradedStateId(SELL_EXIT_PROFIT_FORCE);
					bean.setExitedAtDtTm(getCurrentDateTimeAsDate());
					bean.setExitedAtVal(bean.getSellerAt());
					/*
					 * double netProfitVal = new BigDecimal( (bean.getTradedAtVal().doubleValue() -
					 * lastPrice) * bean.getLotSize()).setScale(2, 0) .doubleValue();
					 */
					double netProfitVal = new BigDecimal((bean.getTradedAtAvgVal().doubleValue() - lastPrice)
							* (bean.getLotSize() * bean.getTradedLotCount())).setScale(2, 0).doubleValue();
					bean.setProfitLossAmtVal(netProfitVal);
					bean.setSignalTriggeredOutAt(
							getCurrentTime() + SELL_TARGET + lastPrice + WITH_PROFIT + netProfitVal);

					DatabaseHelper.getInstance().updateTrade(bean);
					tradingDataMap.replace(symbol, bean);
				} else if (lastPrice >= bean.getTradedAtAvgVal().doubleValue()) {
					bean.setTradedStateId(SELL_EXIT_LOSS_FORCE);
					bean.setExitedAtVal(bean.getSellerAt());
					bean.setExitedAtDtTm(getCurrentDateTimeAsDate());
					/*
					 * double netLossVal = new BigDecimal( (bean.getTradedAtVal().doubleValue() -
					 * lastPrice) * bean.getLotSize()).setScale(2, 0) .doubleValue();
					 */
					double netLossVal = new BigDecimal((bean.getTradedAtAvgVal().doubleValue() - lastPrice)
							* (bean.getLotSize() * bean.getTradedLotCount())).setScale(2, 0).doubleValue();
					bean.setProfitLossAmtVal(netLossVal);
					bean.setSignalTriggeredOutAt(
							getCurrentTime() + CLOSED_WITH_LOSE + lastPrice + WITH_LOSS + netLossVal);

					DatabaseHelper.getInstance().updateTrade(bean);
					tradingDataMap.replace(symbol, bean);
				}
			}
		}
	}
	//Phase 3 end
	
	public boolean isIndexOrOptionTrade(StrategyOrbDataBean bean) {
		return (/*(null != bean.getKiteOptionKey()) ||*/ (NIFTY_50.equals(bean.getSymbolId())
				|| BANKNIFTY.equals(bean.getSymbolId()) || FINNIFTY.equals(bean.getSymbolId())));
	}
	
	// 04-21-2021 start - afterSomeSuccess [04-27-2021]
	public StrategyOrbDataBean executeManualTradeClose(StrategyOrbDataBean bean) {
		if (Constants.BUY.equals(bean.getTradedStateId())) {
			bean.setManualExitedAtVal(bean.getBuyerAt());
			bean.setManualExitedAtDtTm(getCurrentDateTimeAsDate());
			bean.setManualTradeExitInd(true);

			double netLossVal = kiteConnectTool
					.getRoundupToTwoValue((bean.getBuyerAt() - bean.getTradedAtAvgVal().doubleValue())
							* (bean.getLotSize() * bean.getTradedLotCount()));
			bean.setManualBookProfitLossAmtVal(netLossVal);
			if (netLossVal <= 0) {
				bean.setManualTradeExitStateId(Constants.BUY_EXIT_MANUAL_LOSS);
			} else {
				bean.setManualTradeExitStateId(Constants.BUY_EXIT_MANUAL_PROFIT);
			}
		} else if (Constants.SELL.equals(bean.getTradedStateId())) {
			bean.setManualExitedAtVal(bean.getSellerAt());
			bean.setManualExitedAtDtTm(getCurrentDateTimeAsDate());
			bean.setManualTradeExitInd(true);
			double netLossVal = kiteConnectTool
					.getRoundupToTwoValue((bean.getTradedAtAvgVal().doubleValue() - bean.getSellerAt())
							* (bean.getLotSize() * bean.getTradedLotCount()));
			bean.setManualBookProfitLossAmtVal(netLossVal);
			if (netLossVal <= 0) {
				bean.setManualTradeExitStateId(Constants.SELL_EXIT_MANUAL_LOSS);
			} else {
				bean.setManualTradeExitStateId(Constants.SELL_EXIT_MANUAL_PROFIT);
			}
		}

		databaseHelper.manualTradeClose(bean);
		return bean;
	}
	
	private StringBuffer tradeEntrySMS = new StringBuffer();

	public String getTradeEntryMessage(StrategyOrbDataBean bean) {
		if (tradeEntrySMS.length() > 0) {
			tradeEntrySMS.delete(0, tradeEntrySMS.length());
		}
		if (null != bean.getTradedStateId()) {
			if (bean.getTradableStateId().equals(Constants.BUY)) {
				tradeEntrySMS.append(bean.getSymbolName()).append(Constants.BUY_ACTIVATE).append(bean.getTradedAtVal())
						.append(Constants.AT).append(getCurrentTime(bean.getTradedAtDtTm()));

			} else if (bean.getTradableStateId().equals(Constants.SELL)) {
				tradeEntrySMS.append(bean.getSymbolName()).append(Constants.SELL_ACTIVATE).append(bean.getTradedAtVal())
						.append(Constants.AT).append(getCurrentTime(bean.getTradedAtDtTm()));

			}
		}
		return tradeEntrySMS.toString();
	}
	// 04-21-2021 end
	
	// 04-21-2021 start - afterSomeSuccess [05-01-2021]
		public StrategyOrbDataBean manualTradeClose(String tradeId) {
			StrategyOrbDataBean bean = null;
			if (null != tradeId) {
				List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
						getTradingDataMap15Minute().values());
				list.addAll(getTradingDataMap15MinuteDayLevels().values());
				for (StrategyOrbDataBean element : list) {
					// if (tradeId.equals(element.getKiteFutureKey())) {
					if (null != element.getItemId()
							&& Integer.valueOf(tradeId).intValue() == element.getItemId().intValue()) {
						bean = element;
						break;
					}
				}

				if (null != bean) {
					if (null == bean.getManualTradeExitInd() || !bean.getManualTradeExitInd()) {
						if (Constants.BUY.equals(bean.getTradedStateId())
								|| Constants.SELL.equals(bean.getTradedStateId())) {
							if (null != bean.getTradePlacedRuleInd() && bean.getTradePlacedRuleInd()) {
								kiteConnectTool.cancelCoverOrder(bean);
							}
							executeManualTradeClose(bean);
						}
					}
				}
			}
			return bean;
		}
		
	public void checkTodayTradingNotForceClosedByCrossMaxLossLimit() {
		Double maxDayLoss = databaseHelper.checkTodayTradingNotForceClosedByCrossMaxLossLimit();
		if (TradewareTool.todayTradingNotForceClosedInd && null != maxDayLoss && maxDayLoss <= TARGET_MAX_DAY_LOSS) {
			TradewareTool.todayTradingNotForceClosedInd = Boolean.FALSE;
		}
	}
		// 05-01-2021 end
	
	
	//temporary start
	@Autowired
	protected TradewareOrderPlacementRuleTool tradewareOrderPlacementRuleTool;

	public boolean verifyProfits(StrategyOrbDataBean bean) {
		if (tradewareOrderPlacementRuleTool.verifySMAandVWAPformulaLevel1(bean) || (bean.getOhlcStateId() != null
				&& (Constants.BUY.equals(bean.getOhlcStateId()) || Constants.SELL.equals(bean.getOhlcStateId())
						|| Constants.BUY_SPIKE.equals(bean.getOhlcStateId())
						|| Constants.SELL_SPIKE.equals(bean.getOhlcStateId())
						|| Constants.BUY_SPIKE_1.equals(bean.getOhlcStateId())
						|| Constants.SELL_SPIKE_1.equals(bean.getOhlcStateId())
						// 04-21-2021 start - afterSomeSuccess
						|| Constants.BUY_SPIKE_1DOT5.equals(bean.getOhlcStateId())
						|| Constants.SELL_SPIKE_1DOT5.equals(bean.getOhlcStateId())
						|| Constants.BUY_SPIKE_2.equals(bean.getOhlcStateId())
						|| Constants.SELL_SPIKE_2.equals(bean.getOhlcStateId())
						|| Constants.BUY_SPIKE_2DOT5.equals(bean.getOhlcStateId())
						|| Constants.SELL_SPIKE_2DOT5.equals(bean.getOhlcStateId())
				// 04-21-2021 end
				))
				|| (bean.getStrategyRule() != null && (Constants.STRATEGY_NR7_R1.equals(bean.getStrategyRule())
						|| Constants.STRATEGY_NR7_R2.equals(bean.getStrategyRule())
						|| Constants.STRATEGY_ORB_R1.equals(bean.getStrategyRule())
						|| Constants.STRATEGY_ORB_R2.equals(bean.getStrategyRule())))) {
			if (null != bean.getCandle2SizeAmt() && bean.getCandle2SizeAmt() >= 2500) {
				return true;
			}

		}
		return false;
	}
	//temporary end
	
	
	
	
	
	
	
	
	
	
	
	public Map<String, Long> indexOptionTradeKiteInstrumentMap = new HashMap<String, Long>();

	public Map<String, Long> getIndexOptionTradeKiteInstrumentMap() {
		if (indexOptionTradeKiteInstrumentMap.isEmpty()) {
			/*
			symbolsArray.add("NSE:NIFTY 50");// NIFTY 50
			symbolsArray.add("NSE:NIFTY BANK");// NIFTY 50
			String nigtyBankFut = sathvikAshvikTechTraderTool.getKiteFutureKey("BANKNIFTY");
			symbolsArray.add("NSE:NIFTY FIN SERVICE");// NIFTY 50
			String niftyFinancalFut = sathvikAshvikTechTraderTool.getKiteFutureKey("FINNIFTY");
			symbolsArray.add("NSE:NIFTY AUTO");
			symbolsArray.add("NSE:NIFTY MEDIA");
			symbolsArray.add("NSE:NIFTY INFRA");
			symbolsArray.add("NSE:NIFTY CONSUMPTION");
			symbolsArray.add("NSE:NIFTY FMCG");

			symbolsArray.add("NSE:NIFTY FMCG");
			symbolsArray.add("NSE:NIFTY FIN SERVICE");
			symbolsArray.add("NSE:NIFTY ENERGY");
			symbolsArray.add("NSE:NIFTY 100");
			symbolsArray.add("NSE:NIFTY 200");
			symbolsArray.add("NSE:NIFTY 500");

			symbolsArray.add("NSE:NIFTY IT");
			symbolsArray.add("NSE:NIFTY METAL");
			symbolsArray.add("NSE:NIFTY ENERGY");
			symbolsArray.add("NSE:NIFTY PHARMA");
			symbolsArray.add("NSE:NIFTY REALTY");
			symbolsArray.add("NSE:NIFTY PSU BANK");
			symbolsArray.add("NSE:NIFTY PRIVATEBANK");*/
			indexOptionTradeKiteInstrumentMap.put(NIFTY_50_KITE_INDEX_KEY, null);
			indexOptionTradeKiteInstrumentMap.put(getKiteFutureKey(NIFTY), null);
			indexOptionTradeKiteInstrumentMap.put(NSE_BANK_NIFTY_KITE_INDEX_KEY, null);
			indexOptionTradeKiteInstrumentMap.put(getKiteFutureKey(BANKNIFTY), null);
			indexOptionTradeKiteInstrumentMap.put(NSE_NIFTY_FIN_SERVICE_KITE_INDEX_KEY, null);
			indexOptionTradeKiteInstrumentMap.put(getKiteFutureKey(FINNIFTY), null);
		}
		return indexOptionTradeKiteInstrumentMap;
	}
}
