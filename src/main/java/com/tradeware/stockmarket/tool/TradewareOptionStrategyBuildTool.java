package com.tradeware.stockmarket.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.clouddatabase.engine.NSEIndiaBankNiftyTradeToolNewNseSite;
import com.tradeware.stockmarket.bean.KiteLiveOHLCDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeMainDataBean;
import com.tradeware.stockmarket.bean.SymbolDataBean;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TradewareOptionStrategyBuildTool implements Constants {

	@Autowired
	private TradewareTool tradewareTool;

	public List<String> optionTradeSubStrategy(String tradeStrategy) {
		List<String> subList = null;
		if (Constants.OPTION_STRATEGY_SPREAD.equals(tradeStrategy)) {
			subList = tradewareTool.getSpreadStrategyList();
		} else if (Constants.OPTION_STRATEGY_STRADDLE.equals(tradeStrategy)) {
			subList = tradewareTool.getStraddleStrategyList();
		} else if (Constants.OPTION_STRATEGY_STRANGLE.equals(tradeStrategy)) {
			subList = tradewareTool.getStrangleSubStrategyList();
		} else if (Constants.OPTION_STRATEGY_BUTTER_FLY.equals(tradeStrategy)) {
			subList = tradewareTool.getButterFlyStrategyList();
		} else if (Constants.OPTION_STRATEGY_RATIO_SPREAD.equals(tradeStrategy)) {
			subList = tradewareTool.getRatioSpreadStrategyList();
		}
		return subList;
	}

	public List<Integer> getTradeMainStrikePricesList(String symbolId, String tradeStrategy, String indexLastPrice) {
		List<Integer> list = new ArrayList<Integer>(tradewareTool.getTradeStrikePricesList(symbolId));
		if (Constants.OPTION_STRATEGY_STRANGLE.equals(tradeStrategy)) {
			if (null != indexLastPrice) {
				Integer element = null;
				Iterator<Integer> it = list.iterator();
				while (it.hasNext()) {
					element = it.next();
					if (element > Double.valueOf(indexLastPrice)) {
						it.remove();
					}
				}
			}
		} else if (Constants.OPTION_STRATEGY_BUTTER_FLY.equals(tradeStrategy) ||
				Constants.OPTION_STRATEGY_STRADDLE.equals(tradeStrategy)) {
			int maxFieldOptionCont = 5;
			if (Constants.OPTION_STRATEGY_STRADDLE.equals(tradeStrategy)) {
				maxFieldOptionCont = 3;
			}
			if (null != indexLastPrice) {
				Integer element = null;
				int counter = 0;
				ListIterator<Integer> it = list.listIterator();
				while (it.hasNext()) {
					element = it.next();
					if (element > Double.valueOf(indexLastPrice)) {
						if (counter < maxFieldOptionCont) {
							counter++;
						} else {
							it.remove();
						}
					}
				}

				counter = 0;
				while (it.hasPrevious()) {
					element = it.previous();
					if (element < Double.valueOf(indexLastPrice)) {
						if (counter < maxFieldOptionCont) {
							counter++;
						} else {
							it.remove();
						}
					}
				}
			}
		}
		return list;
	}

	public Map<Object, Object> getSpreadProtectionTradeStrikePricesList(String symbolId, String tradeStrategy,
			String tradeSubStrategy, String indexLastPrice, String expiryDate, String strikePriceSelectedInput) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Integer> strikePricesList = new LinkedList<Integer>();
		if (null != tradeSubStrategy && null != expiryDate && null != strikePriceSelectedInput) {
			Integer strikePriceAtClonse = new Integer(strikePriceSelectedInput);
			if (OPTION_STRATEGY_BEAR_CALL.equals(tradeSubStrategy)) {
				for (Integer element : tradewareTool.getTradeStrikePricesList(symbolId)) {
					if (element.intValue() > strikePriceAtClonse) {
						strikePricesList.add(element);
					}
				}
				ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCMap = tradewareTool
						.getOptionAskBidBidValue123(symbolId, expiryDate, strikePriceSelectedInput);
				
				map.put(PARAM_LOWER_OF_ATM_TRADE_TYPE, Constants.SELL);
				map.put(PARAM_HIGHER_OF_ATM_TRADE_TYPE, Constants.BUY);
				map.put(PARAM_ASK_PRICE_LOWER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
						strikePriceSelectedInput, Constants.OPTION_CALL, Constants.OPTION_ASK_VALUE, liveOHLCMap));
				map.put(PARAM_BID_PRICE_LOWER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
						strikePriceSelectedInput, Constants.OPTION_CALL, Constants.OPTION_BID_VALUE, liveOHLCMap));
			} else if (OPTION_STRATEGY_BULL_PUT.equals(tradeSubStrategy)) {
				for (Integer element : tradewareTool.getTradeStrikePricesList(symbolId)) {
					if (element.intValue() < strikePriceAtClonse) {
						strikePricesList.add(element);
					}
				}
				ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCMap = tradewareTool
						.getOptionAskBidBidValue123(symbolId, expiryDate, strikePriceSelectedInput);
				
				map.put(PARAM_LOWER_OF_ATM_TRADE_TYPE, Constants.SELL);
				map.put(PARAM_HIGHER_OF_ATM_TRADE_TYPE, Constants.BUY);
				map.put(PARAM_ASK_PRICE_LOWER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
						strikePriceSelectedInput, Constants.OPTION_PUT, Constants.OPTION_ASK_VALUE, liveOHLCMap));
				map.put(PARAM_BID_PRICE_LOWER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
						strikePriceSelectedInput, Constants.OPTION_PUT, Constants.OPTION_BID_VALUE, liveOHLCMap));
			} else if (OPTION_STRATEGY_BEAR_PUT.equals(tradeSubStrategy)) {

			} else if (OPTION_STRATEGY_BEAR_CALL.equals(tradeSubStrategy)) {

			} else if (Constants.OPTION_STRATEGY_SHORT_STRANGLE.equals(tradeSubStrategy)) {
				for (Integer element : tradewareTool.getTradeStrikePricesList(symbolId)) {
					if (element.intValue() > getSpotPrice(symbolId)) {
						strikePricesList.add(element);
					}
				}
				ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCMap = tradewareTool
						.getOptionAskBidBidValue123(symbolId, expiryDate, strikePriceSelectedInput);
				
				map.put(PARAM_LOWER_OF_ATM_TRADE_TYPE, Constants.SELL);
				map.put(PARAM_HIGHER_OF_ATM_TRADE_TYPE, Constants.SELL);
				map.put(PARAM_ASK_PRICE_LOWER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
						strikePriceSelectedInput, Constants.OPTION_PUT, Constants.OPTION_ASK_VALUE, liveOHLCMap));
				map.put(PARAM_BID_PRICE_LOWER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
						strikePriceSelectedInput, Constants.OPTION_PUT, Constants.OPTION_BID_VALUE, liveOHLCMap));
			} else if (Constants.OPTION_STRATEGY_LONG_STRANGLE.equals(tradeSubStrategy)) {

			}  else if (Constants.OPTION_STRATEGY_SHORT_STRADDLE.equals(tradeSubStrategy)) {
				map.put(PARAM_LOWER_OF_ATM_TRADE_TYPE, Constants.SELL);
				map.put(PARAM_HIGHER_OF_ATM_TRADE_TYPE, Constants.SELL);
				ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCMap = tradewareTool
						.getOptionAskBidBidValue123(symbolId, expiryDate, strikePriceSelectedInput);
				
				map.put(PARAM_ASK_PRICE_LOWER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
						strikePriceSelectedInput, Constants.OPTION_PUT, Constants.OPTION_ASK_VALUE, liveOHLCMap));
				map.put(PARAM_BID_PRICE_LOWER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
						strikePriceSelectedInput, Constants.OPTION_PUT, Constants.OPTION_BID_VALUE, liveOHLCMap));

				map.put(PARAM_ASK_PRICE_HIGHER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
						strikePriceSelectedInput, Constants.OPTION_CALL, Constants.OPTION_ASK_VALUE, liveOHLCMap));
				map.put(PARAM_BID_PRICE_HIGHER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
						strikePriceSelectedInput, Constants.OPTION_CALL, Constants.OPTION_BID_VALUE, liveOHLCMap));
				
				List<Integer> strikePrceListLowerOfAtm = getTradeMainStrikePricesList(symbolId, tradeStrategy, indexLastPrice);
				map.put(PARAM_STRIKE_PRICE_LOWER_OF_ATM, strikePrceListLowerOfAtm);
				
				List<String> strikePrceListHigherOfAtm = new ArrayList<String>();
				strikePrceListHigherOfAtm.add(strikePriceSelectedInput);
				map.put(PARAM_STRIKE_PRICE_HIGHER_OF_ATM, strikePrceListHigherOfAtm);
				map.put(PARAM_STRIKE_PRICE_OF_ATM, strikePriceSelectedInput);
			} else if (Constants.OPTION_STRATEGY_LONG_STRADDLE.equals(tradeSubStrategy)) {

			} else if (Constants.OPTION_STRATEGY_BUTTER_FLY.equals(tradeSubStrategy)) {
				double strikePriceAtmAskPremium = 0;
				double strikePriceAtmBidPremium = 0;
				int lowerHigherstrikeDiff = 0;
				ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCMap = tradewareTool
						.getOptionAskBidBidValue123(symbolId, expiryDate, strikePriceSelectedInput);
				
				if (tradeSubStrategy.contains(Constants.OPTION_CALL)) {
					strikePriceAtmAskPremium = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
							strikePriceSelectedInput, Constants.OPTION_CALL, Constants.OPTION_ASK_VALUE, liveOHLCMap);
					strikePriceAtmBidPremium = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
							strikePriceSelectedInput, Constants.OPTION_CALL, Constants.OPTION_BID_VALUE, liveOHLCMap);
					lowerHigherstrikeDiff = (int) ((strikePriceAtmAskPremium + 100) - (strikePriceAtmAskPremium % 100));
					int strikePriceLowerOfAtm = Integer.valueOf(strikePriceSelectedInput)
							- lowerHigherstrikeDiff;
					int strikePriceHigherOfAtm = Integer.valueOf(strikePriceSelectedInput)
							+ lowerHigherstrikeDiff;
					map.put(PARAM_STRIKE_PRICE_LOWER_OF_ATM, strikePriceLowerOfAtm);
					map.put(PARAM_STRIKE_PRICE_HIGHER_OF_ATM, strikePriceHigherOfAtm);
					map.put(PARAM_ATM_OPTION_TYPE, Constants.OPTION_CALL);
					map.put(PARAM_LOWER_OF_ATM_OPTION_TYPE, Constants.OPTION_CALL);
					map.put(PARAM_HIGHER_OF_ATM_OPTION_TYPE, Constants.OPTION_CALL);

					map.put(PARAM_ASK_PRICE_OF_ATM, strikePriceAtmAskPremium);
					map.put(PARAM_BID_PRICE_OF_ATM, strikePriceAtmBidPremium);
					
					liveOHLCMap = tradewareTool.getOptionAskBidBidValue123(symbolId, expiryDate,
							String.valueOf(strikePriceLowerOfAtm), String.valueOf(strikePriceHigherOfAtm));

					double askPriceLowerOfAtm = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
							String.valueOf(strikePriceLowerOfAtm), Constants.OPTION_CALL, Constants.OPTION_ASK_VALUE, liveOHLCMap);
					double bidPriceLowerOfAtm = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
							String.valueOf(strikePriceLowerOfAtm), Constants.OPTION_CALL, Constants.OPTION_BID_VALUE, liveOHLCMap);
					double askPriceHigherOfAtm = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
							String.valueOf(strikePriceHigherOfAtm), Constants.OPTION_CALL, Constants.OPTION_ASK_VALUE, liveOHLCMap);
					double bidPriceHigherOfAtm = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
							String.valueOf(strikePriceHigherOfAtm), Constants.OPTION_CALL, Constants.OPTION_BID_VALUE, liveOHLCMap);
					map.put(PARAM_ASK_PRICE_LOWER_OF_ATM, askPriceLowerOfAtm);
					map.put(PARAM_BID_PRICE_LOWER_OF_ATM, bidPriceLowerOfAtm);
					map.put(PARAM_ASK_PRICE_HIGHER_OF_ATM, askPriceHigherOfAtm);
					map.put(PARAM_BID_PRICE_HIGHER_OF_ATM, bidPriceHigherOfAtm);

					if (tradeSubStrategy.contains(Constants.OPTION_STRATEGY_SHORT_BUTTER_FLY)) {
						map.put(PARAM_ATM_TRADE_TYPE, Constants.SELL);
						map.put(PARAM_LOWER_OF_ATM_TRADE_TYPE, Constants.BUY);
						map.put(PARAM_HIGHER_OF_ATM_TRADE_TYPE, Constants.BUY);
					} else if (tradeSubStrategy.contains(Constants.OPTION_STRATEGY_SHORT_BUTTER_FLY)) {

					}

				} else if (tradeSubStrategy.contains(Constants.OPTION_PUT)) {

				}
			}

		} else {
			strikePricesList = tradewareTool.getTradeStrikePricesList(symbolId);
		}

		map.put("protectionStrikePriceList", strikePricesList);
		return map;
	}

	// TODO strikePriceLowerOfAtmInput strikePriceHigherOfAtmInput looks like some
	// issue here
	public Map<Object, Object> protectionStrikePriceSelectionEvent(String symbolId, String tradeSubStrategy,
			String expiryDate, String strikePriceLowerOfAtmInput, String strikePriceHigherOfAtmInput) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (null == strikePriceLowerOfAtmInput) {
			strikePriceLowerOfAtmInput = strikePriceHigherOfAtmInput;
		} else if (null == strikePriceHigherOfAtmInput) {
			strikePriceHigherOfAtmInput = strikePriceLowerOfAtmInput;
		}
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCMap = tradewareTool.getOptionAskBidBidValue123(symbolId,
				expiryDate, strikePriceLowerOfAtmInput, strikePriceHigherOfAtmInput);

		
		if (OPTION_STRATEGY_BEAR_CALL.equals(tradeSubStrategy)) {
			map.put(PARAM_ASK_PRICE_HIGHER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
					strikePriceHigherOfAtmInput, Constants.OPTION_CALL, Constants.OPTION_ASK_VALUE, liveOHLCMap));
			map.put(PARAM_BID_PRICE_HIGHER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
					strikePriceLowerOfAtmInput, Constants.OPTION_CALL, Constants.OPTION_BID_VALUE, liveOHLCMap));
		} else if (OPTION_STRATEGY_BULL_PUT.equals(tradeSubStrategy)) {
			map.put(PARAM_ASK_PRICE_HIGHER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
					strikePriceHigherOfAtmInput, Constants.OPTION_PUT, Constants.OPTION_ASK_VALUE, liveOHLCMap));
			map.put(PARAM_BID_PRICE_HIGHER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
					strikePriceLowerOfAtmInput, Constants.OPTION_PUT, Constants.OPTION_BID_VALUE, liveOHLCMap));
		} else if (OPTION_STRATEGY_BEAR_PUT.equals(tradeSubStrategy)) {

		} else if (OPTION_STRATEGY_BULL_CALL.equals(tradeSubStrategy)) {

		} else if (Constants.OPTION_STRATEGY_SHORT_STRANGLE.equals(tradeSubStrategy)) {
			map.put(PARAM_LOWER_OF_ATM_TRADE_TYPE, Constants.SELL);
			map.put(PARAM_HIGHER_OF_ATM_TRADE_TYPE, Constants.SELL);
			map.put(PARAM_ASK_PRICE_HIGHER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
					strikePriceHigherOfAtmInput, Constants.OPTION_CALL, Constants.OPTION_ASK_VALUE, liveOHLCMap));
			map.put(PARAM_BID_PRICE_HIGHER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
					strikePriceHigherOfAtmInput, Constants.OPTION_CALL, Constants.OPTION_BID_VALUE, liveOHLCMap));
		} else if (Constants.OPTION_STRATEGY_LONG_STRANGLE.equals(tradeSubStrategy)) {

		}
		return map;
	}

	public Map<Object, Object> getCallRatioSpreadStrategyTradeData(String symbolId, String tradeSubStrategy,
			String expiryDate, String indexLastPrice) {
		Map<Object, Object> map = new HashMap<>();
		String strikePriceOfAtm = tradewareTool.findNearByStrikePrice(symbolId, Double.valueOf(indexLastPrice),
				tradewareTool.getSymbolOptionTickerSizeMap(symbolId));

		OptionLiveTradeMainDataBean mainBean = new OptionLiveTradeMainDataBean();
		mainBean.setSymbolId(symbolId);
		mainBean.setLotSize(tradewareTool.getSymbolLotSizeMap(symbolId));
		mainBean.setOptionTickerSize(tradewareTool.getSymbolOptionTickerSizeMap(symbolId));
		mainBean.setExpiryDate(expiryDate);
		KiteLiveOHLCDataBean liveBeanOfAtm = null;
		KiteLiveOHLCDataBean liveBeanHigherOfAtm = null;
		KiteLiveOHLCDataBean liveBeanLowerOfAtm = null;

		Double askPriceOfAtm = null;
		String optiontype = null;
		if (Constants.OPTION_STRATEGY_RATIO_SPREAD_CALL.equals(tradeSubStrategy)) {
			strikePriceOfAtm = tradewareTool.stripTrailingZeros(
					(Double.valueOf(strikePriceOfAtm) - (tradewareTool.getSymbolOptionTickerSizeMap(symbolId) * 2)));
			optiontype = Constants.OPTION_CALL;
		} else if (Constants.OPTION_STRATEGY_RATIO_SPREAD_PUT.equals(tradeSubStrategy)) {
			strikePriceOfAtm = tradewareTool.stripTrailingZeros(
					(Double.valueOf(strikePriceOfAtm) + (tradewareTool.getSymbolOptionTickerSizeMap(symbolId) * 2)));
			optiontype = Constants.OPTION_PUT;
		}

		ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCMap = tradewareTool
				.getOptionStrickListForStrategyAdustments(mainBean, Double.valueOf(strikePriceOfAtm), optiontype);
		liveOHLCMap = tradewareTool.updateLiveOHLCOptionDataWith(liveOHLCMap);
		askPriceOfAtm = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate, strikePriceOfAtm, optiontype,
				Constants.OPTION_ASK_VALUE, liveOHLCMap);
		liveBeanOfAtm = tradewareTool.findNearestOptionCallOrPutForAdustment(liveOHLCMap, askPriceOfAtm);

		liveBeanHigherOfAtm = tradewareTool.findNearestOptionCallOrPutForAdustment(liveOHLCMap,
				askPriceOfAtm * Constants.RATIO_SPREAD_ADJUSTMENT_NEW_TRADE_VAUE);

		liveBeanLowerOfAtm = tradewareTool.findNearestOptionCallOrPutForAdustment(liveOHLCMap,
				liveBeanHigherOfAtm.getAskPrice() * Constants.RATIO_SPREAD_ADJUSTMENT_NEW_TRADE_VAUE_2);

		map.put(PARAM_ATM_TRADE_TYPE, Constants.SELL);
		map.put(PARAM_HIGHER_OF_ATM_TRADE_TYPE, Constants.BUY);
		map.put(PARAM_LOWER_OF_ATM_TRADE_TYPE, Constants.SELL);
		map.put(PARAM_ATM_OPTION_TYPE, optiontype);
		map.put(PARAM_HIGHER_OF_ATM_OPTION_TYPE, optiontype);
		map.put(PARAM_LOWER_OF_ATM_OPTION_TYPE, optiontype);

		List<String> atmMap = new ArrayList<String>();
		List<String> higherMap = new ArrayList<String>();
		List<String> lowerMap = new ArrayList<String>();
		atmMap.add(liveBeanOfAtm.getOptionStrikePrice().toString());
		higherMap.add(liveBeanHigherOfAtm.getOptionStrikePrice().toString());
		lowerMap.add(liveBeanLowerOfAtm.getOptionStrikePrice().toString());
		map.put(PARAM_STRIKE_PRICE_OF_ATM, atmMap);
		map.put(PARAM_STRIKE_PRICE_HIGHER_OF_ATM, higherMap);
		map.put(PARAM_STRIKE_PRICE_LOWER_OF_ATM, lowerMap);

		map.put(PARAM_ASK_PRICE_OF_ATM, liveBeanOfAtm.getAskPrice());
		map.put(PARAM_BID_PRICE_OF_ATM, liveBeanOfAtm.getBidPrice());
		map.put(PARAM_ASK_PRICE_HIGHER_OF_ATM, liveBeanHigherOfAtm.getAskPrice());
		map.put(PARAM_BID_PRICE_HIGHER_OF_ATM, liveBeanHigherOfAtm.getBidPrice());
		map.put(PARAM_ASK_PRICE_LOWER_OF_ATM, liveBeanLowerOfAtm.getAskPrice());
		map.put(PARAM_BID_PRICE_LOWER_OF_ATM, liveBeanLowerOfAtm.getBidPrice());
		return map;
	}
	
	public Map<Object, Object> getStraddleStrategyTradeData(String symbolId, String tradeStrategy,
			String tradeSubStrategy, String expiryDate, String indexLastPrice) {
		Map<Object, Object> map = new HashMap<>();

		List<Integer> strikePrceList = getTradeMainStrikePricesList(symbolId, tradeStrategy, indexLastPrice);
		List<String> strikePrceListStr = new ArrayList<String>();
		for(int i=0;i<strikePrceList.size();i++) {
			strikePrceListStr.add(String.valueOf(strikePrceList.get(i)));
		}
		
		SymbolDataBean symbolDataBean = DatabaseHelper.getInstance()
				.findBySymbolId(tradewareTool.getTradewareSymbolId(symbolId));
		String strikePriceNearToSpot = tradewareTool.findNearByStrikePrice(symbolId, Double.valueOf(indexLastPrice),
				symbolDataBean.getOptionTickerSize());

		map.put(PARAM_LOWER_OF_ATM_TRADE_TYPE, Constants.SELL);
		map.put(PARAM_HIGHER_OF_ATM_TRADE_TYPE, Constants.SELL);
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCMap = tradewareTool.getOptionAskBidBidValue(symbolId,
				expiryDate, strikePrceListStr);

		map.put(PARAM_ASK_PRICE_LOWER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
				strikePriceNearToSpot, Constants.OPTION_PUT, Constants.OPTION_ASK_VALUE, liveOHLCMap));
		map.put(PARAM_BID_PRICE_LOWER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
				strikePriceNearToSpot, Constants.OPTION_PUT, Constants.OPTION_BID_VALUE, liveOHLCMap));

		map.put(PARAM_ASK_PRICE_HIGHER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
				strikePriceNearToSpot, Constants.OPTION_CALL, Constants.OPTION_ASK_VALUE, liveOHLCMap));
		map.put(PARAM_BID_PRICE_HIGHER_OF_ATM, tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
				strikePriceNearToSpot, Constants.OPTION_CALL, Constants.OPTION_BID_VALUE, liveOHLCMap));
		
		map.put(PARAM_STRIKE_PRICE_LOWER_OF_ATM, strikePrceList);
		map.put(PARAM_STRIKE_PRICE_HIGHER_OF_ATM, strikePrceList);
		map.put(PARAM_STRIKE_PRICE_OF_ATM, strikePriceNearToSpot);

		return map;
	}

	public Map<Object, Object> onAtmStrikePriceSelectionOfButterFly(String symbolId, String tradeStrategy,
			String tradeSubStrategy, String expiryDate, String strikePriceOfAtmInput) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (null != tradeSubStrategy && null != strikePriceOfAtmInput) {
			if (Constants.OPTION_STRATEGY_BUTTER_FLY.equals(tradeStrategy)) {
				double strikePriceAtmAskPremium = 0;
				double strikePriceAtmBidPremium = 0;
				int lowerHigherstrikeDiff = 0;
				
				ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCMap = tradewareTool
						.getOptionAskBidBidValue123(symbolId, expiryDate, strikePriceOfAtmInput);
				
				
				if (tradeSubStrategy.contains(TRADE_OPTION_TYPE_CALL)) {
					strikePriceAtmAskPremium = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
							strikePriceOfAtmInput, Constants.OPTION_CALL, Constants.OPTION_ASK_VALUE, liveOHLCMap);
					strikePriceAtmBidPremium = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
							strikePriceOfAtmInput, Constants.OPTION_CALL, Constants.OPTION_BID_VALUE, liveOHLCMap);
					lowerHigherstrikeDiff = (int) ((strikePriceAtmAskPremium + 100) - (strikePriceAtmAskPremium % 100));
					Integer strikePriceLowerOfAtm = Integer.valueOf(strikePriceOfAtmInput)
							- lowerHigherstrikeDiff;
					Integer strikePriceHigherOfAtm = Integer.valueOf(strikePriceOfAtmInput)
							+ lowerHigherstrikeDiff;
					liveOHLCMap = tradewareTool.getOptionAskBidBidValue123(symbolId, expiryDate,
							String.valueOf(strikePriceLowerOfAtm), String.valueOf(strikePriceHigherOfAtm));

					List<String> lowerMap = new ArrayList<String>();
					List<String> higherMap = new ArrayList<String>();
					lowerMap.add(strikePriceLowerOfAtm.toString());
					higherMap.add(strikePriceHigherOfAtm.toString());
					map.put(PARAM_STRIKE_PRICE_LOWER_OF_ATM, lowerMap);
					map.put(PARAM_STRIKE_PRICE_HIGHER_OF_ATM, higherMap);

					map.put(PARAM_ATM_OPTION_TYPE, Constants.OPTION_CALL);
					map.put(PARAM_LOWER_OF_ATM_OPTION_TYPE, Constants.OPTION_CALL);
					map.put(PARAM_HIGHER_OF_ATM_OPTION_TYPE, Constants.OPTION_CALL);

					map.put(PARAM_ASK_PRICE_OF_ATM, strikePriceAtmAskPremium);
					map.put(PARAM_BID_PRICE_OF_ATM, strikePriceAtmBidPremium);
					double askPriceLowerOfAtm = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
							String.valueOf(strikePriceLowerOfAtm), Constants.OPTION_CALL, Constants.OPTION_ASK_VALUE, liveOHLCMap);
					double bidPriceLowerOfAtm = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
							String.valueOf(strikePriceLowerOfAtm), Constants.OPTION_CALL, Constants.OPTION_BID_VALUE, liveOHLCMap);
					double askPriceHigherOfAtm = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
							String.valueOf(strikePriceHigherOfAtm), Constants.OPTION_CALL, Constants.OPTION_ASK_VALUE, liveOHLCMap);
					double bidPriceHigherOfAtm = tradewareTool.getOptionAskBidBidValue(symbolId, expiryDate,
							String.valueOf(strikePriceHigherOfAtm), Constants.OPTION_CALL, Constants.OPTION_BID_VALUE, liveOHLCMap);
					map.put(PARAM_ASK_PRICE_LOWER_OF_ATM, askPriceLowerOfAtm);
					map.put(PARAM_BID_PRICE_LOWER_OF_ATM, bidPriceLowerOfAtm);
					map.put(PARAM_ASK_PRICE_HIGHER_OF_ATM, askPriceHigherOfAtm);
					map.put(PARAM_BID_PRICE_HIGHER_OF_ATM, bidPriceHigherOfAtm);

					if (tradeSubStrategy.contains(Constants.OPTION_STRATEGY_LONG_BUTTER_FLY)) {
						map.put(PARAM_ATM_TRADE_TYPE, Constants.SELL);
						map.put(PARAM_LOWER_OF_ATM_TRADE_TYPE, Constants.BUY);
						map.put(PARAM_HIGHER_OF_ATM_TRADE_TYPE, Constants.BUY);
					} else if (tradeSubStrategy.contains(Constants.OPTION_STRATEGY_SHORT_BUTTER_FLY)) {

					}

				} else if (tradeSubStrategy.contains(TRADE_OPTION_TYPE_PUT)) {

				}
			}
		}
		return map;
	}

	/// TODO
	@Autowired
	private KiteConnectTool kiteConnectTool;

	public Double getSpotPrice(String symbolId) {
		Double lastPrie = 0.05;
		if (kiteConnectTool.validKiteAccess()) {
			kiteConnectTool.getIndexesLiveOHLCData();
		} else {
			if (null == tradewareTool.getNiftyLiveStockPrice()) {
				prepareDataMapsForIndexWithNseSite();
				if (Constants.NIFTY.equals(symbolId)) {
					lastPrie = tradewareTool.getNiftyLiveStockPrice();
				} else if (Constants.BANKNIFTY.equals(symbolId)) {
					lastPrie = tradewareTool.getBankNiftyLiveStockPrice();
				} else if (Constants.FINNIFTY.equals(symbolId)) {
					lastPrie = tradewareTool.getFinanceNiftyLiveStockPrice();
				}
			}
		}
		return lastPrie;
	}

	@Autowired
	private NSEIndiaBankNiftyTradeToolNewNseSite bankNiftyTradeTool;

	public void prepareDataMapsForIndexWithNseSite() {
		bankNiftyTradeTool.connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(Constants.BANKNIFTY);
		bankNiftyTradeTool.connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(Constants.NIFTY);
		bankNiftyTradeTool.connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(Constants.FINNIFTY);
		// Filter out next four available expire dates
		bankNiftyTradeTool.flterOutNextExpiryDates(Constants.NIFTY);

		//09/26/2021 -- On page initialization this call is not reqired.
		//tradewareTool.prepareLiveOHLCDataMapsForIndexOptions();
	}
}
