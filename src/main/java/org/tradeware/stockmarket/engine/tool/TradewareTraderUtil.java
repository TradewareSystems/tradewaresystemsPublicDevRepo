package org.tradeware.stockmarket.engine.tool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Hashtable;
import java.util.Map;

import org.tradeware.stockmarket.bean.OptionStockDataBean;
import org.tradeware.stockmarket.brokerengine.KiteConnectTool;
import org.tradeware.stockmarket.engine.googlesheettool.GoogleSheetUtil;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;
import org.tradeware.stockmarket.util.Constants;
import org.tradeware.stockmarket.util.StockUtil;

import com.zerodhatech.models.Quote;


public class TradewareTraderUtil extends TradewareDateTimeUtil {

	private static TradewareTraderUtil singletonTool;

	public static TradewareTraderUtil getInstance() {
		if (null == singletonTool) {
			singletonTool = new TradewareTraderUtil();
		}
		return singletonTool;
	}

	private TradewareTraderUtil() {
	}

	public String getOptionTickerKey(String symbol, double lastPrice, String callPutType) {
		String key = null;
		String currExp = TradewareTraderUtil.getInstance().getCurrentMonthExpiryDate();
		int roundLtp = (int) lastPrice;
		double ticker = StockUtil.getSymbolTickerMap().get(symbol);
		int nearLow = (int) (roundLtp - (roundLtp % ticker));
		int nearHigh = (int) (nearLow + ticker);

		if (Constants.OPTION_CALL.equals(callPutType)) {
			key = (Constants.EXCHANGE_FUTUE_NFO + symbol + currExp.substring(Constants.SEVEN_INT, Constants.NINE)
					+ currExp.substring(Constants.TWO_INT, Constants.FIVE_INT) + nearHigh + callPutType).toUpperCase();
		} else if (Constants.OPTION_PUT.equals(callPutType)) {
			key = (Constants.EXCHANGE_FUTUE_NFO + symbol + currExp.substring(Constants.SEVEN_INT, Constants.NINE)
					+ currExp.substring(Constants.TWO_INT, Constants.FIVE_INT) + nearLow + callPutType).toUpperCase();
		}
		return key;
	}

	public OptionStockDataBean placeOptionOrder(String optionSymbol, String transactionType, OptionStockDataBean bean,
			Map<String, Quote> quotesMap) {
		if (null != quotesMap.get(optionSymbol)) {
			int lotsize = bean.getLotSize();
			double buyerAt = quotesMap.get(optionSymbol).depth.buy.get(Constants.ZERO_INT).getPrice();
			double sellerAt = quotesMap.get(optionSymbol).depth.sell.get(Constants.ZERO_INT).getPrice();
			double buySellDiff = Constants.BUY_SELL_DIFF_1000;

			buySellDiff = bean.getScaledVal((lotsize * sellerAt) - (lotsize * buyerAt));
			if (buySellDiff >= Constants.BUY_SELL_DIFF_1000 || buyerAt == Constants.ZERO_DOUBLE
					|| sellerAt == Constants.ZERO_DOUBLE) {
				if (bean.getSignalTriggeredInAt().contains(Constants.BUYER_SELLER_RULE)) {
					bean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime(), Constants.BREAK,
							optionSymbol, Constants.AT_RATE_OF, sellerAt, Constants.SPACE, Constants.BREAK,
							bean.getKiteFutureKey(), Constants.AT_RATE_OF, bean.getTradedVal(), Constants.BREAK,
							Constants.BUYER_SELLER_RULE, Constants.BREAK, Constants.BUYER_AT, Constants.AT_RATE_OF,
							buyerAt, Constants.SPACE, Constants.SELLER_AT, Constants.AT_RATE_OF, sellerAt);
					bean.setTradedValOption(BigDecimal.valueOf(sellerAt));
				}
				if (bean.getSignalTriggeredInAt().contains(Constants.TRADABLE_RULE_SUCCESS)
						&& bean.getSignalTriggeredInAt().contains(Constants.BUYER_SELLER_RULE)
						&& !bean.getSignalTriggeredInAt().contains(Constants.BUYER_SELLER_RULE_2)) {
					bean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime(), Constants.BREAK,
							bean.getKiteFutureKey(), Constants.AT_RATE_OF, bean.getTradedVal(), Constants.BREAK,
							Constants.BUYER_SELLER_RULE_2, Constants.BREAK, Constants.BUYER_AT, Constants.AT_RATE_OF,
							buyerAt, Constants.SPACE, Constants.SELLER_AT, Constants.AT_RATE_OF, sellerAt);
					bean.setTradedValOption(BigDecimal.valueOf(sellerAt));
				}
				bean.setTradeState(Constants.NA);
				return bean;
			} else {
				bean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime(), Constants.BREAK,
						optionSymbol, Constants.AT_RATE_OF, sellerAt, Constants.SPACE, Constants.BUY_SELL_DIFF,
						Constants.SPACE, buySellDiff, Constants.BREAK, bean.getKiteFutureKey(), Constants.AT_RATE_OF,
						bean.getTradedVal() + Constants.BREAK, Constants.BUYER_AT, Constants.AT_RATE_OF, buyerAt,
						Constants.SPACE, Constants.SELLER_AT, Constants.AT_RATE_OF, sellerAt);

				if (checkRulesToPlaceOrder(bean)) {
					if ((lotsize >= 200 && lotsize <= 1200)) {
						updateOptionOHLC(bean, quotesMap, optionSymbol);

						if (optionSymbol.endsWith(OPTION_CALL)) {
							bean.setTradeState(OPTION_BUY);
						} else if (optionSymbol.endsWith(OPTION_SELL)) {
							bean.setTradeState(OPTION_SELL);
						}
						bean.setTradedValOption(BigDecimal.valueOf(sellerAt));

						KiteConnectTool.getInstance().placeOptionOrder(bean);
					} else {
						bean.setTradedValOption(BigDecimal.valueOf(sellerAt));
						bean.setSignalTriggeredInAt(LOT_SIZE_OUT_OF_RANGE);
						if (lotsize > 1200) {
							//Re factor the code,   
							updateOptionOHLC(bean, quotesMap, optionSymbol);
							if (optionSymbol.endsWith(OPTION_CALL)) {
								bean.setTradeState(OPTION_BUY);
							} else if (optionSymbol.endsWith(OPTION_SELL)) {
								bean.setTradeState(OPTION_SELL);
							}
							bean.setTradedValOption(BigDecimal.valueOf(sellerAt));

							KiteConnectTool.getInstance().placeOptionOrder(bean);
						}
						return bean;
					}
				}
			}
		} else {
			bean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime() + Constants.BREAK
					+ optionSymbol + Constants.BREAK + Constants.SYMBOL_NOT_AVAILABLE);
			return bean;
		}
		return bean;
	}

	public OptionStockDataBean updateOptionOHLC(OptionStockDataBean bean, Map<String, Quote> quotesMap,
			String optionSymbol) {
		bean.setBuyerAt(new BigDecimal(quotesMap.get(optionSymbol).depth.buy.get(Constants.ZERO_INT).getPrice()));
		bean.setBuyerAt2(new BigDecimal(quotesMap.get(optionSymbol).depth.buy.get(TWO_INT).getPrice()));
		bean.setSellerAt(new BigDecimal(quotesMap.get(optionSymbol).depth.sell.get(Constants.ZERO_INT).getPrice()));
		bean.setOpenOption(new BigDecimal(quotesMap.get(optionSymbol).ohlc.open));
		bean.setHighOption(new BigDecimal(quotesMap.get(optionSymbol).ohlc.high));
		bean.setLowOption(new BigDecimal(quotesMap.get(optionSymbol).ohlc.low));
		bean.setCloseOption(new BigDecimal(quotesMap.get(optionSymbol).ohlc.close));
		bean.setLtpOption(new BigDecimal(quotesMap.get(optionSymbol).lastPrice));
		return bean;
	}

	public void monitorAndChaseProfitLossOptions(Hashtable<String, OptionStockDataBean> monitorPlacedCall,
			Map<String, Quote> quotesMapOption) {
		GoogleSheetUtil.getInstance().clearOptionDate();
		String[] symbolsArrayOption = monitorPlacedCall.keySet().toArray(new String[0]);
		for (String optionKey : symbolsArrayOption) {
			OptionStockDataBean bean = monitorPlacedCall.get(optionKey);
			updateOptionOHLC(bean, quotesMapOption, bean.getKiteOptionKey());
			double buyerAt = bean.getBuyerAt().doubleValue();
			if (!bean.getTradeState().contains(Constants.EXIT)) {
				if (buyerAt > bean.getTradedValOptionAsDouble()) {
					double currentProfitAmt = ((buyerAt - bean.getTradedValOptionAsDouble()) * bean.getLotSize());
					if (null == bean.getProfitLossAmt() && currentProfitAmt > bean.getScaledValue(bean.getProfitLossAmt())) {
						bean.setProfitLossAmt(BigDecimal.valueOf(currentProfitAmt));
						bean.setSignalTriggeredInAt(PROFIT_AMT, currentProfitAmt, Constants.BREAK,
								Constants.BUYER_PRICE, buyerAt);

					} else if ((bean.getScaledValue(bean.getProfitLossAmt()) - currentProfitAmt) >= Constants.TARGET_2000) {
						bean.setSignalTriggeredOutAtClear(TradewareTraderUtil.getInstance().getCurrentTime(), Constants.EXIT,
								Constants.BREAK, Constants.SELLER_PRICE, bean.getSellerAt(), Constants.BUYER_PRICE,
								bean.getBuyerAt(), Constants.BREAK, Constants.LAST_PRICE, bean.getLtpOption(),
								Constants.BREAK, Constants.FINAL_PROFIT,
								((buyerAt - bean.getTradedValAsDouble()) * bean.getLotSize()));
						bean.setTradeState(bean.getTradeState() + EXIT_CALL);
						/*
						 * cancelOptionOrder(bean.getKiteOrderIdNiranjan(),
						 * StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER_NIRANJAN),
						 * bean.getKiteOrderNiranjan(), buyerAt2);
						 * cancelOptionOrder(bean.getKiteOrderId(), kiteConnectAdmin,
						 * bean.getKiteOrder(), buyerAt2);
						 */
					}
				} else {
					if (Constants.ZERO_INT == bean.getNegativeDirectionLtpAsDouble() || buyerAt < bean.getNegativeDirectionLtpAsDouble()) {
						bean.setNegativeDirectionLtp(bean.getScaledValueObj(buyerAt));
						bean.setNegativeDirectionLossAmt(bean.getScaledValueObj((buyerAt - bean.getTradedValAsDouble()) * bean.getLotSize()));
						double lossAmt = bean.getScaledVal((buyerAt - bean.getTradedValAsDouble()) * bean.getLotSize());
						bean.setProfitLossStatus(LOSS_AMT + (lossAmt) + BUYER_PRICE + buyerAt);
						if (!bean.getTradeState().contains("EXIT")) {
							// if (-2000 >= lossAmt) { //As of 03/07/201
							if (MAX_LOSS_2000 >= lossAmt || (MAX_LOSS_2000 >= (lossAmt - bean.getScaledValue(bean.getProfitLossAmt())))) {
								bean.setSignalTriggeredOutAt(TradewareTraderUtil.getInstance().getCurrentTime(),
										Constants.EXIT, Constants.BREAK, Constants.SELLER_PRICE, bean.getSellerAt(),
										Constants.BUYER_PRICE, bean.getBuyerAt(), Constants.BREAK, Constants.LAST_PRICE,
										bean.getLtpOption(), Constants.BREAK, Constants.FINAL_LOSS, lossAmt);
								bean.setTradeState(bean.getTradeState() + EXIT_CALL);
								/*
								 * System.out.println("cancelOptionOrder >> "+bean.getKiteOrderIdNiranjan()+
								 * "  - "+StockUtil.DEFAULT_USER_NIRANJAN);
								 * System.out.println("cancelOptionOrder >> "+bean.getKiteOrderId()+
								 * "  - "+kiteConnectAdmin.getUserId());
								 * cancelOptionOrder(bean.getKiteOrderIdNiranjan(),
								 * StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER_NIRANJAN),
								 * bean.getKiteOrderNiranjan(),buyerAt2);
								 * cancelOptionOrder(bean.getKiteOrderId(), kiteConnectAdmin,
								 * bean.getKiteOrder(), buyerAt2);
								 */
							}

						}
					}
				}
				NSEIndiaTool.getInstance().getPositionalOptionsMap().put(bean.getStockName(), bean);
			}
			
			if ((bean.getTradeState().contains(Constants.OPTION_BUY) || bean.getTradeState().contains(Constants.OPTION_SELL))) {
				GoogleSheetUtil.getInstance().addOptionData(bean);
			}
		}
		GoogleSheetUtil.getInstance().publishOptionCallDataUpdates();
	}
	
	public double getchangePercentage(double lastPrice, double close) {
		double change_percent = 0d;
		change_percent = (((lastPrice - close) * 100) / close);
		BigDecimal bd = new BigDecimal(Double.toString(change_percent));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		change_percent = bd.doubleValue();
		//System.out.println(change_percent);
		return change_percent;
	}
	
	public boolean changePercentageValidation(OptionStockDataBean optionBean) {
		boolean valid = true;
		double high = optionBean.getScaledValue(optionBean.getHigh());
		double low = optionBean.getScaledValue(optionBean.getLow());
		double close = optionBean.getScaledValue(optionBean.getClose());
		double lastPrice = optionBean.getScaledValue(optionBean.getLtp());
		double changePerCentage = getchangePercentage(high, close);
		double uptrendVal = optionBean.getScaledVal(optionBean.getPositionalUptrendValue().doubleValue());
		double downptrendVal = optionBean.getScaledVal(optionBean.getPositionalDowntrendValue().doubleValue());

		if (Constants.FUTURE_BUY.equals(optionBean.getTradeState())) {
			if ((high > (uptrendVal + (uptrendVal * FUTURE_CHANGE_RATE)))
					|| (changePerCentage > BUY_RULE_CHANGE_PERCENTAGE)) {
				valid = false;
				String ruleString = "";
				if (changePerCentage > BUY_RULE_CHANGE_PERCENTAGE) {
					ruleString = Constants.CHANGE_PERCENTAGE_FAILED;
				} else if (high > (uptrendVal + (uptrendVal * FUTURE_CHANGE_RATE))) {
					ruleString = Constants.CHANGE_LEVEL_FAILED;
				}
				if (null == optionBean.getSignalTriggeredInAt()
						|| EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())) {
					optionBean.setSignalTriggeredInAt(getCurrentTime(), Constants.AT_RATE_OF, Constants.LAST_PRICE,
							lastPrice, Constants.BREAK, Constants.CHNAGE_PERCENTAGE, changePerCentage,
							Constants.AT_RATE_OF, MAX_LEVEL,
							optionBean.getScaledVal(uptrendVal + (uptrendVal * FUTURE_CHANGE_RATE)), Constants.BREAK,
							ruleString);
				} else {
					optionBean.setSignalTriggeredInAtClear(optionBean.getSignalTriggeredInAt(), Constants.BREAK,
							getCurrentTime(), Constants.AT_RATE_OF, Constants.LAST_PRICE, lastPrice, Constants.BREAK,
							Constants.CHNAGE_PERCENTAGE, changePerCentage, Constants.AT_RATE_OF, MAX_LEVEL,
							optionBean.getScaledVal(uptrendVal + (uptrendVal * FUTURE_CHANGE_RATE)), Constants.BREAK,
							ruleString);
				}

				NSEIndiaTool.getInstance().getPositionalOptionsMap().put(optionBean.getStockName(), optionBean);
			}
		} else if (Constants.FUTURE_SELL.equals(optionBean.getTradeState())) {
			if ((low < (downptrendVal - (downptrendVal * FUTURE_CHANGE_RATE)))
					|| (changePerCentage < SELL_RULE_CHANGE_PERCENTAGE)) {
				valid = false;
				String ruleString = "";
				if (changePerCentage < SELL_RULE_CHANGE_PERCENTAGE) {
					ruleString = Constants.CHANGE_PERCENTAGE_FAILED;
				} else if (low < (downptrendVal - (downptrendVal * FUTURE_CHANGE_RATE))) {
					ruleString = Constants.CHANGE_LEVEL_FAILED;
				}
				optionBean.setSignalTriggeredInAt();
				if (null == optionBean.getSignalTriggeredInAt()
						|| EMPTY_STRING.equals(optionBean.getSignalTriggeredInAt())) {
					optionBean.setSignalTriggeredInAt(getCurrentTime(), Constants.AT_RATE_OF, Constants.LAST_PRICE,
							lastPrice, Constants.BREAK, Constants.CHNAGE_PERCENTAGE, changePerCentage,
							Constants.AT_RATE_OF, MAX_LEVEL,
							optionBean.getScaledVal(downptrendVal - (downptrendVal * FUTURE_CHANGE_RATE)),
							Constants.BREAK, ruleString);
				} else {
					optionBean.setSignalTriggeredInAtClear(optionBean.getSignalTriggeredInAt(), Constants.BREAK,
							getCurrentTime(), Constants.AT_RATE_OF, Constants.LAST_PRICE, lastPrice, Constants.BREAK,
							Constants.CHNAGE_PERCENTAGE, changePerCentage, Constants.AT_RATE_OF, MAX_LEVEL,
							optionBean.getScaledVal(downptrendVal - (downptrendVal * FUTURE_CHANGE_RATE)),
							Constants.BREAK, ruleString); 
				}
				NSEIndiaTool.getInstance().getPositionalOptionsMap().put(optionBean.getStockName(), optionBean);
			}
		}
		return valid;
	}
	
	private boolean checkRulesToPlaceOrder(OptionStockDataBean bean) {
		/*boolean valid = true;
		double lastPrice = bean.getScaledValue(bean.getTradedVal());
		double close = bean.getScaledValue(bean.getClose());
		double uptrendVal = bean.getScaledVal(bean.getPositionalUptrendValue().doubleValue());
		double downptrendVal = bean.getScaledVal(bean.getPositionalDowntrendValue().doubleValue());
		double changePercentage = getchangePercentage(lastPrice, close);
		if (Constants.FUTURE_BUY.equals(bean.getTradeState())) {
			if (lastPrice > (uptrendVal + (uptrendVal * Constants.LIMIT_RANGE))) {
				bean.setSignalTriggeredInAt(
						TradewareTraderUtil.getInstance().getCurrentTime() + Constants.BREAK + BUY_RULE_FAILED,
						AT_RATE_OF, lastPrice);
				valid = false;
			} else if (changePercentage > BUY_RULE_CHANGE_PERCENTAGE) {
				bean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime() + Constants.BREAK
						+ BUY_RULE_CHANGE_FAILED, AT_RATE_OF, changePercentage);
				valid = false;
			} else if (!bean.getTradableVolumeRule()) {
				bean.setSignalTriggeredInAt(
						TradewareTraderUtil.getInstance().getCurrentTime() + Constants.BREAK + VOLUME_RULE_FAILED);
				valid = false;
			}
		} else if (Constants.FUTURE_SELL.equals(bean.getTradeState())) {
			if (lastPrice < (downptrendVal - (downptrendVal * Constants.LIMIT_RANGE))) {
				bean.setSignalTriggeredInAt(
						TradewareTraderUtil.getInstance().getCurrentTime() + Constants.BREAK + SELL_RULE_FAILED,
						AT_RATE_OF, lastPrice);
				valid = false;
			} else if (changePercentage < SELL_RULE_CHANGE_PERCENTAGE) {
				bean.setSignalTriggeredInAt(TradewareTraderUtil.getInstance().getCurrentTime() + Constants.BREAK
						+ SELL_RULE_CHANGE_FAILED, AT_RATE_OF, changePercentage);
				valid = false;
			} else if (!bean.getTradableVolumeRule()) {
				bean.setSignalTriggeredInAt(
						TradewareTraderUtil.getInstance().getCurrentTime() + Constants.BREAK + VOLUME_RULE_FAILED);
				valid = false;
			}
		}*/
	
		boolean valid = true;
		if (Constants.FUTURE_BUY.equals(bean.getTradeState())) {
			if (!bean.getTradableVolumeRule()) {
				bean.setSignalTriggeredInAt(
						TradewareTraderUtil.getInstance().getCurrentTime() + Constants.BREAK + VOLUME_RULE_FAILED);
				valid = false;
			}
		} else if (Constants.FUTURE_SELL.equals(bean.getTradeState())) {
			if (!bean.getTradableVolumeRule()) {
				bean.setSignalTriggeredInAt(
						TradewareTraderUtil.getInstance().getCurrentTime() + Constants.BREAK + VOLUME_RULE_FAILED);
				valid = false;
			}
		}
		return valid;
	}
}