package com.tradeware.stockmarket.tool;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.clouddatabase.bean.OptionTradeFormDataBean;
import com.tradeware.clouddatabase.bean.OptionTradeLiveDataBean;
import com.tradeware.clouddatabase.bean.SymbolBean;
import com.tradeware.clouddatabase.engine.NSEIndiaBankNiftyTradeToolNewNseSite;
import com.tradeware.stockmarket.bean.KiteLiveOHLCDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeChildDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeMainDataBean;
import com.tradeware.stockmarket.bean.SymbolDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TradewareOptionTradingTool implements Constants {
	
	private Double niftyLiveStockPrice;
	private Double niftyLiveFuturePrice;
	private Double bankNiftyLiveStockPrice;
	private Double bankNiftyLiveFuturePrice;
	private Double financeNiftyLiveStockPrice;
	private Double financeNiftyLiveFuturePrice;
	
	public Double getNiftyLiveStockPrice() {
		return niftyLiveStockPrice;
	}
	public void setNiftyLiveStockPrice(Double niftyLiveStockPrice) {
		this.niftyLiveStockPrice = niftyLiveStockPrice;
	}
	public Double getNiftyLiveFuturePrice() {
		return niftyLiveFuturePrice;
	}
	public void setNiftyLiveFuturePrice(Double niftyLiveFuturePrice) {
		this.niftyLiveFuturePrice = niftyLiveFuturePrice;
	}
	public Double getBankNiftyLiveStockPrice() {
		return bankNiftyLiveStockPrice;
	}
	public void setBankNiftyLiveStockPrice(Double bankNiftyLiveStockPrice) {
		this.bankNiftyLiveStockPrice = bankNiftyLiveStockPrice;
	}
	public Double getBankNiftyLiveFuturePrice() {
		return bankNiftyLiveFuturePrice;
	}
	public void setBankNiftyLiveFuturePrice(Double bankNiftyLiveFuturePrice) {
		this.bankNiftyLiveFuturePrice = bankNiftyLiveFuturePrice;
	}
	public Double getFinanceNiftyLiveStockPrice() {
		return financeNiftyLiveStockPrice;
	}
	public void setFinanceNiftyLiveStockPrice(Double financeNiftyLiveStockPrice) {
		this.financeNiftyLiveStockPrice = financeNiftyLiveStockPrice;
	}
	public Double getFinanceNiftyLiveFuturePrice() {
		return financeNiftyLiveFuturePrice;
	}
	public void setFinanceNiftyLiveFuturePrice(Double financeNiftyLiveFuturePrice) {
		this.financeNiftyLiveFuturePrice = financeNiftyLiveFuturePrice;
	}








	private List<OptionTradeLiveDataBean> liveWholeOptionDataList;
	private Map<String, List<OptionTradeLiveDataBean>> liveWholeOptionDataMap;
	
	public List<OptionTradeLiveDataBean> getLiveWholeOptionDataList() {
		return liveWholeOptionDataList;
	}
	public void setLiveWholeOptionDataList(List<OptionTradeLiveDataBean> liveWholeOptionDataList) {
		this.liveWholeOptionDataList = liveWholeOptionDataList;
	}
	public Map<String, List<OptionTradeLiveDataBean>> getLiveWholeOptionDataMap() {
		return liveWholeOptionDataMap;
	}
	public void setLiveWholeOptionDataMap(Map<String, List<OptionTradeLiveDataBean>> liveWholeOptionDataMap) {
		this.liveWholeOptionDataMap = liveWholeOptionDataMap;
	}








	private LinkedHashMap<String, String> indexMap = new LinkedHashMap<String, String>();
	//private List<String> tradeExpiryDatesList = new LinkedList<String>();
	//private List<Integer> tradeStrikePricesList = new LinkedList<Integer>();
	private Map<String, List<String>> tradeExpiryDatesMap = new LinkedHashMap<String, List<String>>();
	private Map<String,List<Integer>> tradeStrikePricesMap = new LinkedHashMap<String, List<Integer>>();
	
	private List<String> spreadStrategyList = new LinkedList<String>();
	private List<String> strangleStrategyList = new LinkedList<String>();
	private List<String> straddleStrategyList = new LinkedList<String>();
	private List<String> butterFlyStrategyList = new LinkedList<String>();
	private Map<String, String> tradeStrategyOptionsMap = new HashMap<String, String>();
	private List<String> ratioSpreadStrategyList = new LinkedList<String>();
	
	public LinkedHashMap<String, String> getIndexMap() {
		if (indexMap.isEmpty()) {
			indexMap.put(NIFTY, NIFTY);
			indexMap.put(BANKNIFTY, BANK_NIFTY);
			indexMap.put(FINNIFTY, FINNIFTY);
		}
		return indexMap;
	}
	
	public Map<String, String> getTradeStrategyOptionsMap() {
		if (tradeStrategyOptionsMap.isEmpty()) {
			tradeStrategyOptionsMap.put(OPTION_STRATEGY_SPREAD, OPTION_STRATEGY_SPREAD);
			tradeStrategyOptionsMap.put(OPTION_STRATEGY_STRADDLE, OPTION_STRATEGY_STRADDLE);
			tradeStrategyOptionsMap.put(OPTION_STRATEGY_STRANGLE, OPTION_STRATEGY_STRANGLE);
			tradeStrategyOptionsMap.put(OPTION_STRATEGY_BUTTER_FLY, OPTION_STRATEGY_BUTTER_FLY);
			tradeStrategyOptionsMap.put(OPTION_STRATEGY_RATIO_SPREAD, OPTION_STRATEGY_RATIO_SPREAD);
		}
		return tradeStrategyOptionsMap;
	}
	
	public List<String> getSpreadStrategyList() {
		if (spreadStrategyList.isEmpty()) {
			spreadStrategyList.add(OPTION_STRATEGY_BEAR_CALL);
			spreadStrategyList.add(OPTION_STRATEGY_BULL_PUT);
			spreadStrategyList.add(OPTION_STRATEGY_BEAR_PUT);
			spreadStrategyList.add(OPTION_STRATEGY_BULL_CALL);
		}
		return spreadStrategyList;
	}
	
	public List<String> getStrangleSubStrategyList() {
		if (strangleStrategyList.isEmpty()) {
			strangleStrategyList.add(OPTION_STRATEGY_SHORT_STRANGLE);
			strangleStrategyList.add(OPTION_STRATEGY_LONG_STRANGLE);
		}
		return strangleStrategyList;
	}
	
	public List<String> getStraddleStrategyList() {
		if (straddleStrategyList.isEmpty()) {
			straddleStrategyList.add(OPTION_STRATEGY_SHORT_STRADDLE);
			straddleStrategyList.add(OPTION_STRATEGY_LONG_STRADDLE);
		}
		return straddleStrategyList;
	}
	
	public List<String> getButterFlyStrategyList() {
		if (butterFlyStrategyList.isEmpty()) {
			butterFlyStrategyList.add(OPTION_STRATEGY_SHORT_BUTTER_FLY_CALL);
			butterFlyStrategyList.add(OPTION_STRATEGY_SHORT_BUTTER_FLY_PUT);
			butterFlyStrategyList.add(OPTION_STRATEGY_LONG_BUTTER_FLY_CALL);
			butterFlyStrategyList.add(OPTION_STRATEGY_LONG_BUTTER_FLY_PUT);
		}
		return butterFlyStrategyList;
	}
	
	public List<String> getRatioSpreadStrategyList() {
		if (ratioSpreadStrategyList.isEmpty()) {
			ratioSpreadStrategyList.add(OPTION_STRATEGY_RATIO_SPREAD_CALL);
			ratioSpreadStrategyList.add(OPTION_STRATEGY_RATIO_SPREAD_PUT);
		}
		return ratioSpreadStrategyList;
	}
	public List<String> getTradeExpiryDatesList(String symbolId) {
		if (null == tradeExpiryDatesMap.get(symbolId)) {
			tradeExpiryDatesMap.put(symbolId, new LinkedList<String>());
		}
		return tradeExpiryDatesMap.get(symbolId);
	}
	public List<Integer> getTradeStrikePricesList(String symbolId) {
		if (null == tradeStrikePricesMap.get(symbolId)) {
			tradeStrikePricesMap.put(symbolId, new LinkedList<Integer>());
		}
		return tradeStrikePricesMap.get(symbolId);
	}
	
	private List<String> nextExpiryDatesList = new LinkedList<String>();
	public List<String> getNextExpiryDatesList() {
		return nextExpiryDatesList;
	}
	
	public boolean isTodayExpiryDay(String expiryDate) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN_EXP_DATES);
		return tradeExpiryDatesMap.get(expiryDate).contains(LocalDate.now().format(dtf));
	}
	
	private Map<String, Integer> symbolLotSizeMap = new HashMap<String, Integer>();
	private Map<String, Double> symbolOptionTickerSizeMap = new HashMap<String, Double>();
	
	public Integer getSymbolLotSizeMap(String symbolId) {
		if (symbolLotSizeMap.isEmpty()) {
			retrieveSymbolBeanList();
		}
		return symbolLotSizeMap.get(symbolId);
	}
	public Double getSymbolOptionTickerSizeMap(String symbolId) {
		if (symbolOptionTickerSizeMap.isEmpty()) {
			retrieveSymbolBeanList();
		}
		return symbolOptionTickerSizeMap.get(symbolId);
	}
	public void retrieveSymbolBeanList() {
		try {
			List<SymbolDataBean> symbolDataBeans = DatabaseHelper.getInstance()
					.findAllByFnoIndAndValidIndOrderBySymbolId(Boolean.TRUE, Boolean.TRUE);
			for (SymbolDataBean symbolDataBean : symbolDataBeans) {
				symbolLotSizeMap.put(symbolDataBean.getSymbolId(), symbolDataBean.getLotSize());
				symbolOptionTickerSizeMap.put(symbolDataBean.getSymbolId(), symbolDataBean.getOptionTickerSize());
				if (symbolDataBean.getSymbolId() != getTradewareSymbolId(symbolDataBean.getSymbolId())) {
					symbolLotSizeMap.put(getTradewareSymbolId(symbolDataBean.getSymbolId()),
							symbolDataBean.getLotSize());
					symbolOptionTickerSizeMap.put(getTradewareSymbolId(symbolDataBean.getSymbolId()),
							symbolDataBean.getOptionTickerSize());
				}
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_NSEINDIATOOLOPTIONCHAINTOOL,
					Constants.METHOD_RETRIEVESYMBOLBEANLIST, e, Constants.ERROR_TYPE_EXCEPTION);
		}
	}
	//
	private Double getOptionAskValue(String symbolId, String expiryDate, String strikePrice, String optionType) {
		double optionAskValue = -0.05;

		if (null != symbolId && null != expiryDate && null != strikePrice && null != optionType) {
			if (null != liveWholeOptionDataMap && null != liveWholeOptionDataMap.get(symbolId)) {
				for (OptionTradeLiveDataBean bean : liveWholeOptionDataMap.get(symbolId)) {
					if (expiryDate.equals(bean.getExpiryDate())
							&& Double.valueOf(strikePrice).doubleValue() == bean.getStrikePrice().doubleValue()) {
						if (Constants.OPTION_CALL.equals(optionType)) {
							optionAskValue = null != bean.getAskPriceCall() ? bean.getAskPriceCall() : optionAskValue;
						} else if (Constants.OPTION_PUT.equals(optionType)) {
							optionAskValue = null != bean.getAskPricePut() ? bean.getAskPricePut() : optionAskValue;
						}
						break;
					}
				}
			} else {
				prepareLiveOHLCDataMapsForIndexOptions(symbolId, expiryDate, strikePrice);
				getOptionAskValue(symbolId, expiryDate, strikePrice, optionType);
			}
		}

		return optionAskValue;
	}
	private Double getOptionBidValue(String symbolId, String expiryDate, String strikePrice, String optionType) {
		double optionBidValue = -0.05;

		if (null != symbolId && null != expiryDate && null != strikePrice && null != optionType) {
			if (null != liveWholeOptionDataMap && !liveWholeOptionDataMap.isEmpty()) {
				for (OptionTradeLiveDataBean bean : liveWholeOptionDataMap.get(symbolId)) {
					if (expiryDate.equals(bean.getExpiryDate())
							&& Double.valueOf(strikePrice).doubleValue() == bean.getStrikePrice().doubleValue()) {
						if (Constants.OPTION_CALL.equals(optionType)) {
							optionBidValue = null != bean.getBidPriceCall() ? bean.getBidPriceCall() : optionBidValue;
						} else if (Constants.OPTION_PUT.equals(optionType)) {
							optionBidValue = null != bean.getBidPricePut() ? bean.getBidPricePut() : optionBidValue;
						}
						break;
					}
				}
			}
		}

		return optionBidValue;
	}
	
	public OptionTradeLiveDataBean getOptionTradeLiveDataBean(String symbolId, String expiryDate, String strikePrice) {
		OptionTradeLiveDataBean retValue = null;

		if (null != symbolId && null != expiryDate && null != strikePrice) {
			if (null != liveWholeOptionDataMap && !liveWholeOptionDataMap.isEmpty()) {
				for (OptionTradeLiveDataBean bean : liveWholeOptionDataMap.get(symbolId)) {
					if (expiryDate.equals(bean.getExpiryDate())
							&& Double.valueOf(strikePrice).doubleValue() == bean.getStrikePrice().doubleValue()) {
						retValue = bean;
						break;
					}
				}
			}
		}
		return retValue;
	}
	
	public Double getOptionAskBidBidValue(String symbolId, String expiryDate, String strikePrice, String optionType,
			String askOrBidValue, ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveDataMap) {
		double optionAskBidBidValue = -0.05;
		if (kiteConnectTool.validKiteAccess()) {
			KiteLiveOHLCDataBean bean = null;
			if (null == liveDataMap) {
				liveDataMap = getSelectedOptionLtpByKite(symbolId, expiryDate,
						strikePrice);
			}
			for (String key : liveDataMap.keySet()) {
				if (key.endsWith(optionType)) {
					bean = liveDataMap.get(key);
					break;
				}
			}
			if (OPTION_ASK_VALUE.equals(askOrBidValue)) {
				optionAskBidBidValue = bean.getAskPrice();
			} else if (OPTION_BID_VALUE.equals(askOrBidValue)) {
				optionAskBidBidValue = bean.getBidPrice();
			}

		} else {
			if (OPTION_ASK_VALUE.equals(askOrBidValue)) {
				optionAskBidBidValue = getOptionAskValue(symbolId, expiryDate, strikePrice, optionType);
			} else if (OPTION_BID_VALUE.equals(askOrBidValue)) {
				optionAskBidBidValue = getOptionBidValue(symbolId, expiryDate, strikePrice, optionType);
			}
		}

		return optionAskBidBidValue;
	}
	
	public ConcurrentHashMap<String, KiteLiveOHLCDataBean> getOptionAskBidBidValue123(String symbolId, String expiryDate,
			String... strikePrieArray) {
		List<String> strikePrceListStr = new ArrayList<String>();
		for (int i = 0; i < strikePrieArray.length; i++) {
			strikePrceListStr.add(String.valueOf(strikePrieArray[i]));
		}

		ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCMap = getOptionAskBidBidValue(symbolId,
				expiryDate, strikePrceListStr);
		return liveOHLCMap;
	}
	
	public ConcurrentHashMap<String, KiteLiveOHLCDataBean> getOptionAskBidBidValue(String symbolId, String expiryDate,
			List<String> strikePriceList) {
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = null;
		if (kiteConnectTool.validKiteAccess()) {
			KiteLiveOHLCDataBean bean = null;
			map = getSelectedOptionLtpByKite(symbolId, expiryDate, strikePriceList);
			 /*for (String key : map.keySet()) {
				if (key.endsWith(optionType)) {
					bean = map.get(key);
					break;
				}
			}
			if (OPTION_ASK_VALUE.equals(askOrBidValue)) {
				optionAskBidBidValue = bean.getAskPrice();
			} else if (OPTION_BID_VALUE.equals(askOrBidValue)) {
				optionAskBidBidValue = bean.getBidPrice();
			}*/

		} /*else {
			if (OPTION_ASK_VALUE.equals(askOrBidValue)) {
				optionAskBidBidValue = getOptionAskValue(symbolId, expiryDate, strikePrice, optionType);
			} else if (OPTION_BID_VALUE.equals(askOrBidValue)) {
				optionAskBidBidValue = getOptionBidValue(symbolId, expiryDate, strikePrice, optionType);
			}
		}*/

		return map;
	}

	private ConcurrentHashMap<String, KiteLiveOHLCDataBean> getSelectedOptionLtpByKite(String symbolId,
			String expiryDate, String strikePrice) {
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = new ConcurrentHashMap<String, KiteLiveOHLCDataBean>();
		if (kiteConnectTool.validKiteAccess()) {
			prepareOHLCDataMapsForSelectedOptionsWithAPIAccess(symbolId, expiryDate, strikePrice, map);
			map = kiteConnectTool.updateLiveOHLC(map);
		}
		return map;
	}
	
	private ConcurrentHashMap<String, KiteLiveOHLCDataBean> getSelectedOptionLtpByKite(String symbolId,
			String expiryDate, List<String> strikePriceList) {
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = new ConcurrentHashMap<String, KiteLiveOHLCDataBean>();
		if (kiteConnectTool.validKiteAccess()) {
			prepareOHLCDataMapsForSelectedOptionsWithAPIAccess(map, symbolId, expiryDate, strikePriceList);
			map = kiteConnectTool.updateLiveOHLC(map);
		}
		return map;
	}
	
	//
	
	@Autowired
	private TradewareTool tradewareTool;

	public OptionLiveTradeMainDataBean submitOptionTradeOrder(OptionTradeFormDataBean optionTradeOrder) {
		OptionLiveTradeMainDataBean tradeBean = new OptionLiveTradeMainDataBean();
		if ("Bear Call".equals(optionTradeOrder.getTradeSubStrategy())) {

			//bean.setStrikePriceMainTradeType(Constants.SELL);
			//bean.setStrikePriceMainOptionType(Constants.OPTION_CALL);
			//bean.setStrikePriceLossProtectTradeType(Constants.BUY);
			//bean.setStrikePriceLossProtectOptionType(Constants.OPTION_CALL);

			//bean.setBuyAtMain(optionTradeOrder.getAskPriceLowerOfAtm());
			// bean.setSellAtMain(this.sellAtMain);
			//bean.setBuyAtLossProtect(optionTradeOrder.getAskPriceHigherOfAtm());
			//// bean.setSellAtLossProtect(this.sellAtLossProtect);
			// bean.setGainPointsMain(this.gainPointsMain);
			/// bean.setGainPointsLossProtect(this.gainPointsLossProtect);
			/*
			 * bean.setPositiveMoveValMain(this.positiveMoveValMain);
			 * bean.setNegativeMoveValMain(this.negativeMoveValMain);
			 * bean.setPositiveMoveLtpMain(this.positiveMoveLtpMain);
			 * bean.setNegativeMoveLtpMain(this.negativeMoveLtpMain);
			 * bean.setPositiveMoveValLossProtect(this.positiveMoveValLossProtect);
			 * bean.setNegativeMoveValLossProtect(this.negativeMoveValLossProtect);
			 * bean.setPositiveMoveLtpLossProtect(this.positiveMoveLtpLossProtect);
			 * bean.setNegativeMoveLtpLossProtect(this.negativeMoveLtpLossProtect);
			 */
		} else if ("Bull Put".equals(optionTradeOrder.getTradeSubStrategy())) {
			//bean.setStrikePriceMainTradeType(Constants.SELL);
			//bean.setStrikePriceMainOptionType(Constants.OPTION_PUT);
			//bean.setStrikePriceLossProtectTradeType(Constants.BUY);
			//bean.setStrikePriceLossProtectOptionType(Constants.OPTION_PUT);

			//ean.setBuyAtMain(optionTradeOrder.getAskPriceLowerOfAtm());
			// bean.setSellAtMain(this.sellAtMain);
			//bean.setBuyAtLossProtect(optionTradeOrder.getAskPriceHigherOfAtm());
			//// bean.setSellAtLossProtect(this.sellAtLossProtect);
			// bean.setGainPointsMain(this.gainPointsMain);
			/// bean.setGainPointsLossProtect(this.gainPointsLossProtect);
			/*
			 * bean.setPositiveMoveValMain(this.positiveMoveValMain);
			 * bean.setNegativeMoveValMain(this.negativeMoveValMain);
			 * bean.setPositiveMoveLtpMain(this.positiveMoveLtpMain);
			 * bean.setNegativeMoveLtpMain(this.negativeMoveLtpMain);
			 * bean.setPositiveMoveValLossProtect(this.positiveMoveValLossProtect);
			 * bean.setNegativeMoveValLossProtect(this.negativeMoveValLossProtect);
			 * bean.setPositiveMoveLtpLossProtect(this.positiveMoveLtpLossProtect);
			 * bean.setNegativeMoveLtpLossProtect(this.negativeMoveLtpLossProtect);
			 */
		} else if ("Bear Put".equals(optionTradeOrder.getTradeSubStrategy())) {

		} else if ("Bull Call".equals(optionTradeOrder.getTradeSubStrategy())) {

		} else if (Constants.OPTION_STRATEGY_STRANGLE.equals(optionTradeOrder.getTradeStrategy())) {
			tradeBean = optionStrategyStrangleBuilder(optionTradeOrder);
		} else if (Constants.OPTION_STRATEGY_STRADDLE.equals(optionTradeOrder.getTradeStrategy())) {
			tradeBean = optionStrategyStraddleBuilder(optionTradeOrder);
		} else if (Constants.OPTION_STRATEGY_BUTTER_FLY.equals(optionTradeOrder.getTradeStrategy())) {
			// Additional start
			// if no kite access start
			/*OptionTradeLiveDataBean lowerOfAtmBeanLive = getOptionTradeLiveDataBean(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm()));
			OptionTradeLiveDataBean higherOfAtmBeanLive = getOptionTradeLiveDataBean(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()));
			OptionTradeLiveDataBean atmBeanLive = getOptionTradeLiveDataBean(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceAtm()));*/
			
			
			if (optionTradeOrder.getTradeSubStrategy().contains(TRADE_OPTION_TYPE_CALL)) {
				optionTradeOrder.setLowerOfAtmOptionType(OPTION_CALL);
				optionTradeOrder.setHigherOfAtmOptionType(OPTION_CALL);
				optionTradeOrder.setAtmOptionType(OPTION_CALL);
			} else if (optionTradeOrder.getTradeSubStrategy().contains(TRADE_OPTION_TYPE_PUT)) {
				optionTradeOrder.setLowerOfAtmOptionType(OPTION_PUT);
				optionTradeOrder.setHigherOfAtmOptionType(OPTION_PUT);
				optionTradeOrder.setAtmOptionType(OPTION_PUT);
			}
			
			String  lowerOfAtmKey = tradewareTool.getKiteOptionKeyWeeklyOrMonthly(
					optionTradeOrder.getSymbolId(), optionTradeOrder.getExpiryDate(),
					String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm()),
					optionTradeOrder.getLowerOfAtmOptionType());
			
			String  higherOfAtmKey = tradewareTool.getKiteOptionKeyWeeklyOrMonthly(
					optionTradeOrder.getSymbolId(), optionTradeOrder.getExpiryDate(),
					String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()),
					optionTradeOrder.getHigherOfAtmOptionType());
			
			String  atmKey = tradewareTool.getKiteOptionKeyWeeklyOrMonthly(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceAtm()),
					optionTradeOrder.getAtmOptionType());
			
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = tradewareTool.getOptionAskBidBidValue123(optionTradeOrder.getSymbolId(),
						optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm())
						,String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()),
						String.valueOf(optionTradeOrder.getStrikePriceAtm()));
			KiteLiveOHLCDataBean lowerOfAtmBeanLive = map.get(lowerOfAtmKey);
			KiteLiveOHLCDataBean higherOfAtmBeanLive = map.get(higherOfAtmKey);
			KiteLiveOHLCDataBean atmBeanLive = map.get(atmKey);
			
			// if no kite access end
			// additional end

			setButterFlyOptionType(optionTradeOrder);
			SymbolDataBean symbolDataBean = DatabaseHelper.getInstance()
					.findBySymbolId(getTradewareSymbolId(optionTradeOrder.getSymbolId()));
			// tradeBean.setItemId(itemId);
			tradeBean.setTradeName(getOptionTradeUnqueName(optionTradeOrder));
			tradeBean.setSymbolId(optionTradeOrder.getSymbolId());
			tradeBean.setLotSize(symbolDataBean.getLotSize());
			tradeBean.setOptionTickerSize(symbolDataBean.getOptionTickerSize());
			tradeBean.setExpiryDate(optionTradeOrder.getExpiryDate());
			tradeBean.setTradeStrategy(optionTradeOrder.getTradeStrategy());
			tradeBean.setTradeSubStrategy(optionTradeOrder.getTradeSubStrategy());
			LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
			tradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
			/*
			 * LocalDateTime ldt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
			 * tradeBean.setTradedAtDtTm(Timestamp.valueOf(ldt));
			 */
			// tradeBean.setExitedAtDtTm(exitedAtDtTm);
			// tradeBean.setProfitLossAmtVal(profitLossAmtVal);
			// tradeBean.setPositiveMoveVal(positiveMoveVal);
			// tradeBean.setNegativeMoveVal(negativeMoveVal);

			// set tradedAtDtTm while placing order
			/*
			 * LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
			 * tradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
			 */
			tradeBean.setTradePosition(Constants.TRADE_POSIITON_NEW);
			tradeBean.setAtmStrikePrice(optionTradeOrder.getStrikePriceAtm());

			OptionLiveTradeChildDataBean lowerOfAtmTradeBean = new OptionLiveTradeChildDataBean();
			OptionLiveTradeChildDataBean higherOfAtmTradeBean = new OptionLiveTradeChildDataBean();
			OptionLiveTradeChildDataBean atmTradeBean = new OptionLiveTradeChildDataBean();

			// putBean.setItemIdChild(itemIdChild);
			lowerOfAtmTradeBean.setStrikePrice(optionTradeOrder.getStrikePriceLowerOfAtm());
			lowerOfAtmTradeBean.setTradeType(optionTradeOrder.getLowerOfAtmTradeType());
			lowerOfAtmTradeBean.setTradePosition(TRADE_POSIITON_NEW);
			lowerOfAtmTradeBean.setOptionType(optionTradeOrder.getLowerOfAtmOptionType());
			lowerOfAtmTradeBean.setKiteFutureKey(lowerOfAtmKey);
			if (Constants.SELL.equals(optionTradeOrder.getLowerOfAtmTradeType())) {
				lowerOfAtmTradeBean.setSellAtValue(optionTradeOrder.getBidPriceLowerOfAtm());
				// lowerOfAtmTradeBean.setTradedAtVal(optionTradeOrder.getBidPriceLowerOfAtm());
			} else if (Constants.BUY.equals(optionTradeOrder.getLowerOfAtmTradeType())) {
				lowerOfAtmTradeBean.setBuyAtValue(optionTradeOrder.getBidPriceLowerOfAtm());
				// lowerOfAtmTradeBean.setTradedAtVal(optionTradeOrder.getBidPriceLowerOfAtm());
			}
			lowerOfAtmTradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
			// putBean.setBuyAtValue(buyAtValue);
			// putBean.setExitedAtVal(exitedAtVal);

			/*
			 * putBean.setStockPriceEntry(optionTradeOrder.getUnderlyingStockPrice());
			 * putBean.setFuturePriceEntry(optionTradeOrder.getUnderlyingFuturePrice());
			 */
			// putBean.setStockPriceEntry(putBeanLive.getUnderlyingStockPrice());
			// putBean.setFuturePriceEntry(putBeanLive.getUnderlyingFuturePrice());
			///////////// updateLiveIndexPrice(optionTradeOrder.getSymbolId(),
			// lowerOfAtmTradeBean);

			// putBean.setStockPriceExit(stockPriceExit);
			// putBean.setFuturePriceExit(futurePriceExit);
			lowerOfAtmTradeBean.setNumberOfLots(optionTradeOrder.getNumberOfLots() / 2);
			// set tradedAtDtTm while placing order
			//// lowerOfAtmTradeBean.setTradedAtDtTm(Timestamp.valueOf(ldt));
			// putBean.setExitedAtDtTm(exitedAtDtTm);
			// putBean.setProfitLossAmtVal(profitLossAmtVal);
			// putBean.setPositiveMoveVal(positiveMoveVal);
			// putBean.setNegativeMoveVal(negativeMoveVal);
			// putBean.setPositiveMoveLtp(positiveMoveLtp);
			// putBean.setNegativeMoveLtp(negativeMoveLtp);
			//// lowerOfAtmTradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
			// putBean.setOptionChainTrend(optionChainTrend);;
			// putBean.setOptionChainPriceTrend(optionChainPriceTrend);
			// putBean.setOptionChainId(optionChainId);
			// putBean.setOiInfo(oiInfo);
			/*lowerOfAtmTradeBean.setBidPrice(lowerOfAtmBeanLive.getBidPricePut());
			lowerOfAtmTradeBean.setAskPrice(lowerOfAtmBeanLive.getAskPricePut());
			lowerOfAtmTradeBean.setBidQuantity(lowerOfAtmBeanLive.getBidQuantityPut());
			lowerOfAtmTradeBean.setAskQuantity(lowerOfAtmBeanLive.getAskQuantityPut());*/
			lowerOfAtmTradeBean.setBidPrice(lowerOfAtmBeanLive.getBidPrice());
			lowerOfAtmTradeBean.setAskPrice(lowerOfAtmBeanLive.getAskPrice());
			lowerOfAtmTradeBean.setBidQuantity(lowerOfAtmBeanLive.getBidQuantity());
			lowerOfAtmTradeBean.setAskQuantity(lowerOfAtmBeanLive.getAskQuantity());
			/*
			 * putBean.setInstrumentToken(dataBean.getInstrumentToken());
			 * putBean.setKiteOrderId(dataBean.getKiteOrderId());
			 * putBean.setKiteOrderType(dataBean.getKiteOrderType());
			 */

			// callBean.setItemIdChild(itemIdChild);
			higherOfAtmTradeBean.setStrikePrice(optionTradeOrder.getStrikePriceHigherOfAtm());
			higherOfAtmTradeBean.setTradeType(optionTradeOrder.getHigherOfAtmTradeType());
			higherOfAtmTradeBean.setTradePosition(TRADE_POSIITON_NEW);
			higherOfAtmTradeBean.setOptionType(optionTradeOrder.getHigherOfAtmOptionType());
			higherOfAtmTradeBean.setKiteFutureKey(higherOfAtmKey);
			// callBean.setBuyAtValue(buyAtValue);
			if (Constants.SELL.equals(optionTradeOrder.getHigherOfAtmTradeType())) {
				higherOfAtmTradeBean.setSellAtValue(optionTradeOrder.getBidPriceHigherOfAtm());
				// higherOfAtmTradeBean.setTradedAtVal(optionTradeOrder.getBidPriceHigherOfAtm());
			} else if (Constants.BUY.equals(optionTradeOrder.getHigherOfAtmTradeType())) {
				higherOfAtmTradeBean.setBuyAtValue(optionTradeOrder.getBidPriceHigherOfAtm());
				// higherOfAtmTradeBean.setTradedAtVal(optionTradeOrder.getBidPriceHigherOfAtm());
			}
			higherOfAtmTradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
			// callBean.setExitedAtVal(exitedAtVal);
			/*
			 * callBean.setStockPriceEntry(optionTradeOrder.getUnderlyingStockPrice());
			 * callBean.setFuturePriceEntry(optionTradeOrder.getUnderlyingFuturePrice());
			 */
			// callBean.setStockPriceEntry(callBeanLive.getUnderlyingStockPrice());
			// callBean.setFuturePriceEntry(callBeanLive.getUnderlyingFuturePrice());
			// Need not update again, already updated above, but need to get from putBean
			// and set
			//////////// updateLiveIndexPrice(optionTradeOrder.getSymbolId(),
			// higherOfAtmTradeBean);

			// callBean.setStockPriceExit(stockPriceExit);
			// callBean.setFuturePriceExit(futurePriceExit);
			higherOfAtmTradeBean.setNumberOfLots(optionTradeOrder.getNumberOfLots() / 2);
			////// higherOfAtmTradeBean.setTradedAtDtTm(Timestamp.valueOf(ldt));
			// callBean.setExitedAtDtTm(exitedAtDtTm);
			// callBean.setProfitLossAmtVal(profitLossAmtVal);
			// callBean.setPositiveMoveVal(positiveMoveVal);
			// callBean.setNegativeMoveVal(negativeMoveVal);
			// callBean.setPositiveMoveLtp(positiveMoveLtp);
			// callBean.setNegativeMoveLtp(negativeMoveLtp);
			////////// higherOfAtmTradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
			// callBean.setOptionChainTrend(optionChainTrend);;
			// callBean.setOptionChainPriceTrend(optionChainPriceTrend);
			// callBean.setOptionChainId(optionChainId);
			// callBean.setOiInfo(oiInfo);
			/*higherOfAtmTradeBean.setBidPrice(higherOfAtmBeanLive.getBidPriceCall());
			higherOfAtmTradeBean.setAskPrice(higherOfAtmBeanLive.getAskPriceCall());
			higherOfAtmTradeBean.setBidQuantity(higherOfAtmBeanLive.getBidQuantityCall());
			higherOfAtmTradeBean.setAskQuantity(higherOfAtmBeanLive.getAskQuantityCall());*/
			higherOfAtmTradeBean.setBidPrice(higherOfAtmBeanLive.getBidPrice());
			higherOfAtmTradeBean.setAskPrice(higherOfAtmBeanLive.getAskPrice());
			higherOfAtmTradeBean.setBidQuantity(higherOfAtmBeanLive.getBidQuantity());
			higherOfAtmTradeBean.setAskQuantity(higherOfAtmBeanLive.getAskQuantity());
			/*
			 * callBean.setInstrumentToken(dataBean.getInstrumentToken());
			 * callBean.setKiteOrderId(dataBean.getKiteOrderId());
			 * callBean.setKiteOrderType(dataBean.getKiteOrderType());
			 */

			// atmTradeBean.setItemIdChild(itemIdChild);
			atmTradeBean.setStrikePrice(optionTradeOrder.getStrikePriceAtm());
			atmTradeBean.setTradeType(optionTradeOrder.getAtmTradeType());
			atmTradeBean.setTradePosition(TRADE_POSIITON_NEW);
			atmTradeBean.setOptionType(optionTradeOrder.getAtmOptionType());
			atmTradeBean.setKiteFutureKey(atmKey);
			// callBean.setBuyAtValue(buyAtValue);
			if (Constants.SELL.equals(optionTradeOrder.getAtmTradeType())) {
				atmTradeBean.setSellAtValue(optionTradeOrder.getAskPriceOfAtm());
				//////// atmTradeBean.setTradedAtVal(optionTradeOrder.getBidPriceOfAtm());
			} else if (Constants.BUY.equals(optionTradeOrder.getAtmTradeType())) {
				atmTradeBean.setBuyAtValue(optionTradeOrder.getBidPriceOfAtm());
				/////// atmTradeBean.setTradedAtVal(optionTradeOrder.getBidPriceOfAtm());
			}
			atmTradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
			// callBean.setExitedAtVal(exitedAtVal);
			/*
			 * callBean.setStockPriceEntry(optionTradeOrder.getUnderlyingStockPrice());
			 * callBean.setFuturePriceEntry(optionTradeOrder.getUnderlyingFuturePrice());
			 */
			// callBean.setStockPriceEntry(callBeanLive.getUnderlyingStockPrice());
			// callBean.setFuturePriceEntry(callBeanLive.getUnderlyingFuturePrice());
			// Need not update again, already updated above, but need to get from putBean
			// and set
			////////// updateLiveIndexPrice(optionTradeOrder.getSymbolId(), atmTradeBean);

			// callBean.setStockPriceExit(stockPriceExit);
			// callBean.setFuturePriceExit(futurePriceExit);
			atmTradeBean.setNumberOfLots(optionTradeOrder.getNumberOfLots());
			// atmTradeBean.setTradedAtDtTm(Timestamp.valueOf(ldt));
			// callBean.setExitedAtDtTm(exitedAtDtTm);
			// callBean.setProfitLossAmtVal(profitLossAmtVal);
			// callBean.setPositiveMoveVal(positiveMoveVal);
			// callBean.setNegativeMoveVal(negativeMoveVal);
			// callBean.setPositiveMoveLtp(positiveMoveLtp);
			// callBean.setNegativeMoveLtp(negativeMoveLtp);
			////////// atmTradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
			// callBean.setOptionChainTrend(optionChainTrend);;
			// callBean.setOptionChainPriceTrend(optionChainPriceTrend);
			// callBean.setOptionChainId(optionChainId);
			// callBean.setOiInfo(oiInfo);
			/*atmTradeBean.setBidPrice(atmBeanLive.getBidPriceCall());
			atmTradeBean.setAskPrice(atmBeanLive.getAskPriceCall());
			atmTradeBean.setBidQuantity(atmBeanLive.getBidQuantityCall());
			atmTradeBean.setAskQuantity(atmBeanLive.getAskQuantityCall());*/
			atmTradeBean.setBidPrice(atmBeanLive.getBidPrice());
			atmTradeBean.setAskPrice(atmBeanLive.getAskPrice());
			atmTradeBean.setBidQuantity(atmBeanLive.getBidQuantity());
			atmTradeBean.setAskQuantity(atmBeanLive.getAskQuantity());
			/*
			 * callBean.setInstrumentToken(dataBean.getInstrumentToken());
			 * callBean.setKiteOrderId(dataBean.getKiteOrderId());
			 * callBean.setKiteOrderType(dataBean.getKiteOrderType());
			 */

			List<OptionLiveTradeChildDataBean> tradeChildBeanList = new ArrayList<OptionLiveTradeChildDataBean>();
			tradeChildBeanList.add(lowerOfAtmTradeBean);
			tradeChildBeanList.add(higherOfAtmTradeBean);
			tradeChildBeanList.add(atmTradeBean);
			tradeBean.setTradeChildBeanList(tradeChildBeanList);

			tradeBean = DatabaseHelper.getInstance().saveShortStrangleOptionTrade(tradeBean);
			//tradewareTool.getTradingButterFlyOptionDataMap().put(tradeBean.getTradeName(), tradeBean);
			tradewareTool.getTradingStrangleOptionDataMap().put(tradeBean.getTradeName(), tradeBean);

		} else if (Constants.OPTION_STRATEGY_RATIO_SPREAD.equals(optionTradeOrder.getTradeStrategy())) {
			// Additional start
			// if no kite access start
			OptionTradeLiveDataBean lowerOfAtmBeanLive = getOptionTradeLiveDataBean(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm()));
			OptionTradeLiveDataBean higherOfAtmBeanLive = getOptionTradeLiveDataBean(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()));
			OptionTradeLiveDataBean atmBeanLive = getOptionTradeLiveDataBean(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceAtm()));
			// if no kite access end
			// additional end

			SymbolDataBean symbolDataBean = DatabaseHelper.getInstance()
					.findBySymbolId(getTradewareSymbolId(optionTradeOrder.getSymbolId()));
			tradeBean.setTradeName(getOptionTradeUnqueName(optionTradeOrder));
			tradeBean.setSymbolId(optionTradeOrder.getSymbolId());
			tradeBean.setLotSize(symbolDataBean.getLotSize());
			tradeBean.setOptionTickerSize(symbolDataBean.getOptionTickerSize());
			tradeBean.setExpiryDate(optionTradeOrder.getExpiryDate());
			tradeBean.setTradeStrategy(optionTradeOrder.getTradeStrategy());
			tradeBean.setTradeSubStrategy(optionTradeOrder.getTradeSubStrategy());
			LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
			tradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
			tradeBean.setTradePosition(Constants.TRADE_POSIITON_NEW);

			OptionLiveTradeChildDataBean atmTradeBean = new OptionLiveTradeChildDataBean();
			OptionLiveTradeChildDataBean higherOfAtmTradeBean = new OptionLiveTradeChildDataBean();
			OptionLiveTradeChildDataBean lowerOfAtmTradeBean = new OptionLiveTradeChildDataBean();
			String optionType = null;
			if (Constants.OPTION_STRATEGY_RATIO_SPREAD_CALL.equals(optionTradeOrder.getTradeSubStrategy())) {
				optionType = Constants.OPTION_CALL;
			} else if (Constants.OPTION_STRATEGY_RATIO_SPREAD_PUT.equals(optionTradeOrder.getTradeSubStrategy())) {
				optionType = Constants.OPTION_PUT;
			}
			atmTradeBean.setStrikePrice(optionTradeOrder.getStrikePriceAtm());
			atmTradeBean.setTradeType(optionTradeOrder.getAtmTradeType());
			atmTradeBean.setTradePosition(TRADE_POSIITON_NEW);
			atmTradeBean.setOptionType(optionType);
			atmTradeBean.setKiteFutureKey(tradewareTool.getKiteOptionKeyWeeklyOrMonthly(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceAtm()),
					optionType));
			atmTradeBean.setSellAtValue(optionTradeOrder.getBidPriceOfAtm());
			atmTradeBean.setNumberOfLots(optionTradeOrder.getNumberOfLots());
			atmTradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));

			higherOfAtmTradeBean.setStrikePrice(optionTradeOrder.getStrikePriceHigherOfAtm());
			higherOfAtmTradeBean.setTradeType(optionTradeOrder.getHigherOfAtmTradeType());
			higherOfAtmTradeBean.setTradePosition(TRADE_POSIITON_NEW);
			higherOfAtmTradeBean.setOptionType(optionType);
			higherOfAtmTradeBean.setKiteFutureKey(tradewareTool.getKiteOptionKeyWeeklyOrMonthly(
					optionTradeOrder.getSymbolId(), optionTradeOrder.getExpiryDate(),
					String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()), optionType));
			higherOfAtmTradeBean.setBuyAtValue(optionTradeOrder.getAskPriceHigherOfAtm());
			higherOfAtmTradeBean.setNumberOfLots(optionTradeOrder.getNumberOfLots() * 2);
			higherOfAtmTradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));

			lowerOfAtmTradeBean.setStrikePrice(optionTradeOrder.getStrikePriceLowerOfAtm());
			lowerOfAtmTradeBean.setTradeType(optionTradeOrder.getLowerOfAtmTradeType());
			lowerOfAtmTradeBean.setTradePosition(TRADE_POSIITON_NEW);
			lowerOfAtmTradeBean.setOptionType(optionType);
			lowerOfAtmTradeBean.setKiteFutureKey(tradewareTool.getKiteOptionKeyWeeklyOrMonthly(
					optionTradeOrder.getSymbolId(), optionTradeOrder.getExpiryDate(),
					String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm()), optionType));
			lowerOfAtmTradeBean.setSellAtValue(optionTradeOrder.getBidPriceLowerOfAtm());
			lowerOfAtmTradeBean.setNumberOfLots(optionTradeOrder.getNumberOfLots());
			lowerOfAtmTradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));

			if (Constants.OPTION_CALL.equals(optionType)) {
				atmTradeBean.setBidPrice(atmBeanLive.getBidPriceCall());
				atmTradeBean.setAskPrice(atmBeanLive.getAskPriceCall());
				atmTradeBean.setBidQuantity(atmBeanLive.getBidQuantityCall());
				atmTradeBean.setAskQuantity(atmBeanLive.getAskQuantityCall());
				higherOfAtmTradeBean.setBidPrice(higherOfAtmBeanLive.getBidPriceCall());
				higherOfAtmTradeBean.setAskPrice(higherOfAtmBeanLive.getAskPriceCall());
				higherOfAtmTradeBean.setBidQuantity(higherOfAtmBeanLive.getBidQuantityCall());
				higherOfAtmTradeBean.setAskQuantity(higherOfAtmBeanLive.getAskQuantityCall());
				lowerOfAtmTradeBean.setBidPrice(lowerOfAtmBeanLive.getBidPriceCall());
				lowerOfAtmTradeBean.setAskPrice(lowerOfAtmBeanLive.getAskPriceCall());
				lowerOfAtmTradeBean.setBidQuantity(lowerOfAtmBeanLive.getBidQuantityCall());
				lowerOfAtmTradeBean.setAskQuantity(lowerOfAtmBeanLive.getAskQuantityCall());
			} else if (Constants.OPTION_PUT.equals(optionType)) {
				atmTradeBean.setBidPrice(atmBeanLive.getBidPricePut());
				atmTradeBean.setAskPrice(atmBeanLive.getAskPricePut());
				atmTradeBean.setBidQuantity(atmBeanLive.getBidQuantityPut());
				atmTradeBean.setAskQuantity(atmBeanLive.getAskQuantityPut());
				higherOfAtmTradeBean.setBidPrice(higherOfAtmBeanLive.getBidPricePut());
				higherOfAtmTradeBean.setAskPrice(higherOfAtmBeanLive.getAskPricePut());
				higherOfAtmTradeBean.setBidQuantity(higherOfAtmBeanLive.getBidQuantityPut());
				higherOfAtmTradeBean.setAskQuantity(higherOfAtmBeanLive.getAskQuantityPut());
				lowerOfAtmTradeBean.setBidPrice(lowerOfAtmBeanLive.getBidPricePut());
				lowerOfAtmTradeBean.setAskPrice(lowerOfAtmBeanLive.getAskPricePut());
				lowerOfAtmTradeBean.setBidQuantity(lowerOfAtmBeanLive.getBidQuantityPut());
				lowerOfAtmTradeBean.setAskQuantity(lowerOfAtmBeanLive.getAskQuantityPut());
			}

			List<OptionLiveTradeChildDataBean> tradeChildBeanList = new ArrayList<OptionLiveTradeChildDataBean>();
			tradeChildBeanList.add(atmTradeBean);
			tradeChildBeanList.add(higherOfAtmTradeBean);
			tradeChildBeanList.add(lowerOfAtmTradeBean);
			tradeBean.setTradeChildBeanList(tradeChildBeanList);

			tradeBean = DatabaseHelper.getInstance().saveShortStrangleOptionTrade(tradeBean);
			tradewareTool.getTradingStrangleOptionDataMap().put(tradeBean.getTradeName(), tradeBean);

		}
		return tradeBean;
	}
	
	private void setButterFlyOptionType (OptionTradeFormDataBean optionTradeOrder) {
		if ( optionTradeOrder.getTradeSubStrategy().contains("Call")) { 
			optionTradeOrder.setAtmOptionType(Constants.OPTION_CALL);
			optionTradeOrder.setLowerOfAtmOptionType(Constants.OPTION_CALL);
			optionTradeOrder.setHigherOfAtmOptionType(Constants.OPTION_CALL);
		} else if ( optionTradeOrder.getTradeSubStrategy().contains("Put")) { 
			optionTradeOrder.setAtmOptionType(Constants.OPTION_PUT);
			optionTradeOrder.setLowerOfAtmOptionType(Constants.OPTION_PUT);
			optionTradeOrder.setHigherOfAtmOptionType(Constants.OPTION_PUT);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private ConcurrentHashMap<String, OptionLiveTradeMainDataBean> tradingStrangleOptionDataMap = new ConcurrentHashMap<String, OptionLiveTradeMainDataBean>();

	public ConcurrentHashMap<String, OptionLiveTradeMainDataBean> getTradingStrangleOptionDataMap() {
		return tradingStrangleOptionDataMap;
	}

	/*private ConcurrentHashMap<String, OptionLiveTradeMainDataBean> tradingButterFlyOptionDataMap = new ConcurrentHashMap<String, OptionLiveTradeMainDataBean>();

	public ConcurrentHashMap<String, OptionLiveTradeMainDataBean> getTradingButterFlyOptionDataMap() {
		return tradingButterFlyOptionDataMap;
	}*/

	private ConcurrentHashMap<String, OptionLiveTradeMainDataBean> tradingIntradayStraddleOptionDataMap = new ConcurrentHashMap<String, OptionLiveTradeMainDataBean>();

	public ConcurrentHashMap<String, OptionLiveTradeMainDataBean> getTradingIntradayStraddleOptionDataMap() {
		return tradingIntradayStraddleOptionDataMap;
	}





	//private ConcurrentHashMap<String, KiteLiveOHLCDataBean> strangleLiveDataMap = new ConcurrentHashMap<String, KiteLiveOHLCDataBean>();

	public ConcurrentHashMap<String, KiteLiveOHLCDataBean> updateToLatestOHLC(
			ConcurrentHashMap<String, OptionLiveTradeMainDataBean> dataMap) {
		// strangleLiveDataMap.clear();
		 ConcurrentHashMap<String, KiteLiveOHLCDataBean> strangleLiveDataMap = new ConcurrentHashMap<String, KiteLiveOHLCDataBean>();

		for (String key : dataMap.keySet()) {
			OptionLiveTradeMainDataBean mainBean = dataMap.get(key);
			for (OptionLiveTradeChildDataBean childBean : mainBean.getTradeChildBeanList()) {
				if (Constants.TRADE_POSIITON_NEW.equals(childBean.getTradePosition())
						|| Constants.TRADE_POSIITON_OPEN.equals(childBean.getTradePosition())) {

					String fiteFutureKey = tradewareTool.getKiteOptionKeyWeeklyOrMonthly(mainBean.getSymbolId(),
							mainBean.getExpiryDate(), String.valueOf(childBean.getStrikePrice()),
							childBean.getOptionType());
					if (!strangleLiveDataMap.contains(fiteFutureKey)) {
						KiteLiveOHLCDataBean dataBean = new KiteLiveOHLCDataBean();

						dataBean.setSymbolId(mainBean.getSymbolId());
						dataBean.setLotSize(mainBean.getLotSize());
						dataBean.setOptionTickerSize(mainBean.getOptionTickerSize());
						dataBean.setOptionStrikePrice(String.valueOf(childBean.getStrikePrice()));
						dataBean.setOptionExpiryDate(mainBean.getExpiryDate());
						dataBean.setKiteFutureKey(fiteFutureKey);

						strangleLiveDataMap.put(dataBean.getKiteFutureKey(), dataBean);
					}
				}
			}
		}
		return strangleLiveDataMap;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ConcurrentHashMap<String, KiteLiveOHLCDataBean> getOptionStrickListForStrategyAdustments(OptionLiveTradeMainDataBean bean,
			Double strikePrice, String callPutType) {
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = new ConcurrentHashMap<String, KiteLiveOHLCDataBean>();
		for (int i = 0; i <= 50; i++) {
			KiteLiveOHLCDataBean dataBean = new KiteLiveOHLCDataBean();

			dataBean.setSymbolId(bean.getSymbolId());
			dataBean.setLotSize(bean.getLotSize());
			dataBean.setOptionTickerSize(bean.getOptionTickerSize());
			dataBean.setOptionStrikePrice(
					tradewareTool.stripTrailingZeros(strikePrice + (i * bean.getOptionTickerSize())));
			dataBean.setOptionExpiryDate(bean.getExpiryDate());
			dataBean.setKiteFutureKey(tradewareTool.getKiteOptionKeyWeeklyOrMonthly(bean.getSymbolId(),
					bean.getExpiryDate(), dataBean.getOptionStrikePrice(), callPutType));
			map.put(dataBean.getKiteFutureKey(), dataBean);

			dataBean = new KiteLiveOHLCDataBean();
			dataBean.setSymbolId(bean.getSymbolId());
			dataBean.setLotSize(bean.getLotSize());
			dataBean.setOptionTickerSize(bean.getOptionTickerSize());
			dataBean.setOptionStrikePrice(
					tradewareTool.stripTrailingZeros(strikePrice - (i * bean.getOptionTickerSize())));
			dataBean.setOptionExpiryDate(bean.getExpiryDate());
			dataBean.setKiteFutureKey(tradewareTool.getKiteOptionKeyWeeklyOrMonthly(bean.getSymbolId(),
					bean.getExpiryDate(), dataBean.getOptionStrikePrice(), callPutType));
			map.put(dataBean.getKiteFutureKey(), dataBean);
		}

		return map;
	}
	
	//additional start
		public ConcurrentHashMap<String, KiteLiveOHLCDataBean> updateLiveOHLCOptionDataWith(
				ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveTradeMap) {
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveOHLCDataMap = null;

			if (kiteConnectTool.validKiteAccess()) {
				liveOHLCDataMap = kiteConnectTool.updateLiveOHLC(liveTradeMap);
			} else {
				if (!liveTradeMap.isEmpty()) {
					String symbolId = null;
					List<String> closeDateList = new ArrayList<String>();
					List<String> strikePriceList = new ArrayList<String>();
					
					for (KiteLiveOHLCDataBean mainDataBean : liveTradeMap.values()) {

						if (!closeDateList.contains(mainDataBean.getOptionExpiryDate())) {
							closeDateList.add(mainDataBean.getOptionExpiryDate());
							if (null == symbolId) {
								symbolId = mainDataBean.getSymbolId();
							}
						}
						String strikePrice = tradewareTool
								.stripTrailingZeros(String.valueOf(mainDataBean.getOptionStrikePrice()));
						if (!strikePriceList.contains(strikePrice)) {
							strikePriceList.add(strikePrice);
						}
					}
					liveOHLCDataMap = (tradewareTool.connectToNSEIndiaAndGetStockOptionData1(
							symbolId, closeDateList, strikePriceList));
				}

			}
			return liveOHLCDataMap;
		}
		//Addtonal end
	//Need to complete it
	public ConcurrentHashMap<String, KiteLiveOHLCDataBean> getOptionLiveDataBean(OptionLiveTradeMainDataBean bean,
			Integer strikePrice, String callPutType) {
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = new ConcurrentHashMap<String, KiteLiveOHLCDataBean>();
		for (int i = 0; i <= 20; i++) {
			KiteLiveOHLCDataBean dataBean = new KiteLiveOHLCDataBean();

			dataBean.setSymbolId(bean.getSymbolId());
			dataBean.setLotSize(bean.getLotSize());
			dataBean.setOptionTickerSize(bean.getOptionTickerSize());
			dataBean.setOptionStrikePrice(
					tradewareTool.stripTrailingZeros(strikePrice + (i * bean.getOptionTickerSize())));
			dataBean.setOptionExpiryDate(bean.getExpiryDate());
			dataBean.setKiteFutureKey(tradewareTool.getKiteOptionKeyWeeklyOrMonthly(bean.getSymbolId(),
					bean.getExpiryDate(), dataBean.getOptionStrikePrice(), callPutType));
			map.put(dataBean.getKiteFutureKey(), dataBean);

			dataBean = new KiteLiveOHLCDataBean();
			dataBean.setSymbolId(bean.getSymbolId());
			dataBean.setLotSize(bean.getLotSize());
			dataBean.setOptionTickerSize(bean.getOptionTickerSize());
			dataBean.setOptionStrikePrice(
					tradewareTool.stripTrailingZeros(strikePrice - (i * bean.getOptionTickerSize())));
			dataBean.setOptionExpiryDate(bean.getExpiryDate());
			dataBean.setKiteFutureKey(tradewareTool.getKiteOptionKeyWeeklyOrMonthly(bean.getSymbolId(),
					bean.getExpiryDate(), dataBean.getOptionStrikePrice(), callPutType));
			map.put(dataBean.getKiteFutureKey(), dataBean);
		}

		return map;
	}

	public KiteLiveOHLCDataBean findNearestOptionCallOrPutForAdustment(
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> map, Double findValueAs) {
		KiteLiveOHLCDataBean bean = null;
		double findValueAsNearst = 0;

		for (KiteLiveOHLCDataBean liveBean : map.values()) {
			if (null != liveBean.getBidPrice()) {
				if ((findValueAsNearst == 0 && null == bean)
						|| ((Math.abs(findValueAs - liveBean.getBidPrice())) < findValueAsNearst)) {
					findValueAsNearst = Math.abs(findValueAs - liveBean.getBidPrice());
					bean = liveBean;
				}
			}
		}
		return bean;
	}
	
	
	
	
	
	
	
	
	
	
	public Double updateLiveIndexPrice(String symbolId) {
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = null;
		if (kiteConnectTool.validKiteAccess()) {
			map = kiteConnectTool.getIndexesLiveOHLCData();
		} else {
			prepareLiveOHLCDataMapsForIndexOptionsWithKiteApiOrNseSite(symbolId, null, null);
		}

		Double lastPrie = 0.05;
		if (Constants.NIFTY.equals(symbolId)) {
			lastPrie = tradewareTool.getNiftyLiveStockPrice();
		} else if (Constants.BANKNIFTY.equals(symbolId)) {
			lastPrie = tradewareTool.getBankNiftyLiveStockPrice();
		} else if (Constants.FINNIFTY.equals(symbolId)) {
			lastPrie = tradewareTool.getFinanceNiftyLiveStockPrice();
		}
		return lastPrie;
	}

	public void updateLiveIndexPrice(String symbolId, OptionLiveTradeChildDataBean childDataBean) {
		updateLiveIndexPrice(symbolId);
		if (Constants.NIFTY.equals(symbolId)) {
			if (Constants.TRADE_POSIITON_OPEN.equals(childDataBean.getTradePosition())) {
				childDataBean.setStockPriceEntry(tradewareTool.getNiftyLiveStockPrice());
				childDataBean.setFuturePriceEntry(tradewareTool.getNiftyLiveFuturePrice());
			} else if (Constants.TRADE_POSIITON_CLOSE.equals(childDataBean.getTradePosition())) {
				childDataBean.setStockPriceExit(tradewareTool.getNiftyLiveStockPrice());
				childDataBean.setFuturePriceExit(tradewareTool.getNiftyLiveFuturePrice());
			}
		} else if (Constants.BANKNIFTY.equals(symbolId)) {
			if (Constants.TRADE_POSIITON_OPEN.equals(childDataBean.getTradePosition())) {
				childDataBean.setStockPriceEntry(tradewareTool.getBankNiftyLiveStockPrice());
				childDataBean.setFuturePriceEntry(tradewareTool.getBankNiftyLiveFuturePrice());
			}
			if (Constants.TRADE_POSIITON_CLOSE.equals(childDataBean.getTradePosition())) {
				childDataBean.setStockPriceExit(tradewareTool.getBankNiftyLiveStockPrice());
				childDataBean.setFuturePriceExit(tradewareTool.getBankNiftyLiveFuturePrice());
			}

		} else if (Constants.FINNIFTY.equals(symbolId)) {
			if (Constants.TRADE_POSIITON_OPEN.equals(childDataBean.getTradePosition())) {
				childDataBean.setStockPriceEntry(tradewareTool.getFinanceNiftyLiveStockPrice());
				childDataBean.setFuturePriceEntry(tradewareTool.getFinanceNiftyLiveFuturePrice());
			}
			if (Constants.TRADE_POSIITON_CLOSE.equals(childDataBean.getTradePosition())) {
				childDataBean.setStockPriceExit(tradewareTool.getFinanceNiftyLiveStockPrice());
				childDataBean.setFuturePriceExit(tradewareTool.getFinanceNiftyLiveFuturePrice());
			}
		}
	}
	
	public double getIndexValue(String symbolId) {
		double indexLastestValue = 0;
		updateLiveIndexPrice(symbolId);
		if (Constants.NIFTY.equals(symbolId)) {
			indexLastestValue = tradewareTool.getNiftyLiveStockPrice();
		} else if (Constants.BANKNIFTY.equals(symbolId)) {
			indexLastestValue = tradewareTool.getBankNiftyLiveStockPrice();
		} else if (Constants.FINNIFTY.equals(symbolId)) {
			indexLastestValue = tradewareTool.getFinanceNiftyLiveStockPrice();
		}
		return indexLastestValue;
	}

	public double getIndexFutureValue(String symbolId) {
		updateLiveIndexPrice(symbolId);
		double indexFutureLastestValue = 0;
		if (Constants.NIFTY.equals(symbolId)) {
			indexFutureLastestValue = tradewareTool.getNiftyLiveFuturePrice();
		} else if (Constants.BANKNIFTY.equals(symbolId)) {
			indexFutureLastestValue = tradewareTool.getBankNiftyLiveFuturePrice();
		} else if (Constants.FINNIFTY.equals(symbolId)) {
			indexFutureLastestValue = tradewareTool.getFinanceNiftyLiveFuturePrice();
		}
		return indexFutureLastestValue;
	}
	
	@Autowired
	private NSEIndiaBankNiftyTradeToolNewNseSite bankNiftyTradeTool;
	@Autowired 
	protected KiteConnectTool kiteConnectTool;
	public void prepareDataMapsForIndexWithNseSiteIfNoAPIAccess(String symbolId) {
		bankNiftyTradeTool.connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(Constants.BANKNIFTY);
		bankNiftyTradeTool.connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(Constants.NIFTY);
		bankNiftyTradeTool.connectToNSEIndiaAndGetStockOptionDataToPrepareDataMaps(Constants.FINNIFTY);
		//Filter out next four available expire dates 
		bankNiftyTradeTool.flterOutNextExpiryDates(Constants.NIFTY);

		prepareLiveOHLCDataMapsForIndexOptionsWithKiteApiOrNseSite(symbolId, null, null);

	}
	
	public ConcurrentHashMap<String, KiteLiveOHLCDataBean> connectToNSEIndiaAndGetStockOptionData1(String symbolId,
			List<String> expiryDate, List<String> strikePriceList) {
		return bankNiftyTradeTool.connectToNSEIndiaAndGetStockOptionData1( symbolId,
				 expiryDate,  strikePriceList);
	}
	
	public void prepareLiveOHLCDataMapsForIndexOptionsWithNseSiteIfNoAPIAccess() {
		
		/*List<String> closeDateList = new ArrayList<String>();
		closeDateList.add("18-Aug-2021");
		closeDateList.add("26-Aug-2021");*/

		/*List<String> strikePriceList = new ArrayList<String>();
		strikePriceList.add("39200");
		strikePriceList.add("35900");
		strikePriceList.add("36500");
		strikePriceList.add("35000");
		strikePriceList.add("36200");*/
		
		
		/*List<String> closeDateList = tradewareTool.getNextExpiryDatesList();
		List<String> strikePriceList = tradewareTool.getTradeStrikePricesList(Constants.BANKNIFTY).stream()
				.map(Object::toString).collect(Collectors.toList());
		bankNiftyTradeTool.connectToNSEIndiaAndGetStockOptionData(Constants.BANKNIFTY, closeDateList, strikePriceList);*/
		
		//alternate of above code
		Map<String, List<OptionTradeLiveDataBean>> map1 = bankNiftyTradeTool.connectToNSEIndiaAndGetStockWholeOptionData(Constants.NIFTY);
		Map<String, List<OptionTradeLiveDataBean>> map2 = bankNiftyTradeTool.connectToNSEIndiaAndGetStockWholeOptionData(Constants.BANKNIFTY);
		tradewareTool.getLiveWholeOptionDataMap().putAll(map1);
		tradewareTool.getLiveWholeOptionDataMap().putAll(map2);
	}
	
	
	List<String> closeDateList = new ArrayList<String>();
	List<String> strikePriceList = new ArrayList<String>();
	public void prepareLiveOHLCDataMapsForIndexOptionsWithNseSiteIfNoAPIAccessForSelected(
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> liveBeanDataMap) {

		/*
		 * List<String> closeDateList = new ArrayList<String>();
		 * closeDateList.add("18-Aug-2021"); closeDateList.add("26-Aug-2021");
		 */

		/*
		 * List<String> strikePriceList = new ArrayList<String>();
		 * strikePriceList.add("39200"); strikePriceList.add("35900");
		 * strikePriceList.add("36500"); strikePriceList.add("35000");
		 * strikePriceList.add("36200");
		 */

		List<String> closeDateList = tradewareTool.getNextExpiryDatesList();
		List<String> strikePriceList = tradewareTool.getTradeStrikePricesList(Constants.BANKNIFTY).stream()
				.map(Object::toString).collect(Collectors.toList());
		
		closeDateList.clear();
		strikePriceList.clear();
		for (KiteLiveOHLCDataBean liveDataBean : liveBeanDataMap.values()) {
			if (!closeDateList.contains(liveDataBean.getOptionExpiryDate())) {
				closeDateList.add(liveDataBean.getOptionExpiryDate());
			}
			if (!strikePriceList.contains(liveDataBean.getOptionStrikePrice())) {
				strikePriceList.add(liveDataBean.getOptionStrikePrice());
			}
		}
		bankNiftyTradeTool.connectToNSEIndiaAndGetStockOptionData(Constants.BANKNIFTY, closeDateList, strikePriceList);
	}

	/*public void prepareOHLCDataMapsForIndexOptionsWithAPIAccess(String symbolId, String expiryDate,
			String strikePrice) {
		List<String> expiryDateList = new ArrayList<String>();
		expiryDateList.add(expiryDate);
		List<String> strikePriceList = new ArrayList<String>();
		strikePriceList.add(strikePrice);
		prepareOHLCDataMapsForIndexOptionsWithAPIAccess(symbolId, expiryDateList, strikePriceList);
	}*/

	public void prepareOHLCDataMapsForIndexOptionsWithAPIAccess(String symbolId, String expiryDate,
			String... strikePrice) {
		List<String> expiryDateList = new ArrayList<String>();
		expiryDateList.add(expiryDate);
		List<String> strikePriceList = Arrays.asList(strikePrice);
		prepareOHLCDataMapsForIndexOptionsWithAPIAccess(symbolId, expiryDateList, strikePriceList);
	}
	
	public void prepareOHLCDataMapsForIndexOptionsWithAPIAccess(String symbolId, String expiryDate,
			List<String> strikePriceList) {
		List<String> expiryDateList = new ArrayList<String>();
		expiryDateList.add(expiryDate);
		prepareOHLCDataMapsForIndexOptionsWithAPIAccess(symbolId, expiryDateList, strikePriceList);
	}
	public void prepareOHLCDataMapsForIndexOptionsWithAPIAccess(String symbolId, List<String> expiryDateList,
			List<String> strikePriceList) {
		try {
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = new ConcurrentHashMap<String, KiteLiveOHLCDataBean>();
			if (null == expiryDateList) {
				expiryDateList = tradewareTool.getTradeExpiryDatesList(symbolId);
			}
			if (null == strikePriceList) {
				strikePriceList = tradewareTool.getTradeStrikePricesList(symbolId).stream().map(Object::toString)
						.collect(Collectors.toList());
			}
			bankNiftyTradeTool.prepareResponseWholeData(symbolId, expiryDateList, strikePriceList);
			for (String expiryDate : expiryDateList) {
				for (String strikePrice : strikePriceList) {
					 prepareOHLCDataMapsForSelectedOptionsWithAPIAccess(
							 symbolId,  expiryDate,  strikePrice, map);
				}
			}
			
			map = kiteConnectTool.updateLiveOHLCForHugeMap(map);
			updateLiveIndexPrice(symbolId);
			
			//set lve data to llivedatamap
			for (String key : map.keySet()) {
				KiteLiveOHLCDataBean bean = map.get(key);
				OptionTradeLiveDataBean liveDataBean = bankNiftyTradeTool.findResponseDataBean(tradewareTool.getLiveWholeOptionDataList(),
						String.valueOf(bean.getOptionExpiryDate()),
						String.valueOf(bean.getOptionStrikePrice()));
				
				if (key.endsWith(Constants.OPTION_CALL)) {
					liveDataBean.setLastPriceCall(bean.getLtp());
					//liveDataBean.setChangeCall();
					//liveDataBean.setPchangeCall();
					
					liveDataBean.setBidPriceCall(bean.getBidPrice());
					liveDataBean.setAskPriceCall(bean.getAskPrice());
					liveDataBean.setBidQuantityCall(bean.getBidQuantity());
					liveDataBean.setAskQuantityCall(bean.getAskQuantity());
					//liveDataBean.setVwapCall( );
					//liveDataBean.setOpenInterestCall( );
					//liveDataBean.setChangeInOpenInterestCall();
					//liveDataBean.setPchangeInOpenInterestCall( );
					//liveDataBean.setLotSize( );
					//liveDataBean.setImpliedVolatilityCall( );
					//liveDataBean.setDailyvolatilityCall( );
					//liveDataBean.setAnnualisedVolatilityCall( );
			} else if (key.endsWith(Constants.OPTION_PUT)) {
				liveDataBean.setLastPricePut(bean.getLtp());
				//liveDataBean.setChangePut( );
				//liveDataBean.setPchangePut( );
				liveDataBean.setBidPricePut(bean.getBidPrice());
				liveDataBean.setAskPricePut(bean.getAskPrice());
				liveDataBean.setBidQuantityPut(bean.getBidQuantity() );
				liveDataBean.setAskQuantityPut(bean.getAskQuantity());
				
				//liveDataBean.setVwapPut( );
				//liveDataBean.setOpenInterestPut( );
				//liveDataBean.setChangeInOpenInterestPut( );
				//liveDataBean.setPchangeInOpenInterestPut( );
				//liveDataBean.setLotSize( );
				//liveDataBean.setImpliedVolatilityPut( );
				//liveDataBean.setDailyvolatilityPut( );
				//liveDataBean.setAnnualisedVolatilityPut( );
					liveDataBean.setUnderlyingStockPrice(
							kiteConnectTool.getIndexLiveDataMap().get(FUTURE_KEY_PREFIX_NSE + getTradewareSymbolId(symbolId)).getLtp());
					liveDataBean.setUnderlyingFuturePrice(
							kiteConnectTool.getIndexLiveDataMap().get(tradewareTool.getKiteFutureKey(symbolId)).getLtp());
				}
			}

		} catch (Exception e) {
System.out.println(e.getMessage());
		}
	}

	private void prepareOHLCDataMapsForSelectedOptionsWithAPIAccess(String symbolId, String expiryDate,
			String strikePrice, ConcurrentHashMap<String, KiteLiveOHLCDataBean> map) {
		String optionCallKiteKey = tradewareTool.getKiteOptionKeyWeeklyOrMonthly(symbolId, expiryDate, strikePrice,
				Constants.OPTION_CALL);
		String optionPutKiteKey = tradewareTool.getKiteOptionKeyWeeklyOrMonthly(symbolId, expiryDate, strikePrice,
				Constants.OPTION_PUT);

		KiteLiveOHLCDataBean callLiveDataBean = new KiteLiveOHLCDataBean();
		callLiveDataBean.setKiteFutureKey(optionCallKiteKey);
		callLiveDataBean.setSymbolId(symbolId);
		callLiveDataBean.setOptionStrikePrice(optionCallKiteKey);
		callLiveDataBean.setOptionStrikePrice(strikePrice);
		callLiveDataBean.setOptionExpiryDate(expiryDate);
		map.put(optionCallKiteKey, callLiveDataBean);

		KiteLiveOHLCDataBean putLiveDataBean = new KiteLiveOHLCDataBean();
		putLiveDataBean.setKiteFutureKey(optionPutKiteKey);
		putLiveDataBean.setSymbolId(symbolId);
		putLiveDataBean.setOptionStrikePrice(optionPutKiteKey);
		putLiveDataBean.setOptionStrikePrice(strikePrice);
		putLiveDataBean.setOptionExpiryDate(expiryDate);
		map.put(optionPutKiteKey, putLiveDataBean);
	}
	
	private void prepareOHLCDataMapsForSelectedOptionsWithAPIAccess(ConcurrentHashMap<String, KiteLiveOHLCDataBean> map,
			String symbolId, String expiryDate, List<String> strikePriceList) {
		for (String strikePrice : strikePriceList) {

			String optionCallKiteKey = tradewareTool.getKiteOptionKeyWeeklyOrMonthly(symbolId, expiryDate, strikePrice,
					Constants.OPTION_CALL);
			String optionPutKiteKey = tradewareTool.getKiteOptionKeyWeeklyOrMonthly(symbolId, expiryDate, strikePrice,
					Constants.OPTION_PUT);

			KiteLiveOHLCDataBean callLiveDataBean = new KiteLiveOHLCDataBean();
			callLiveDataBean.setKiteFutureKey(optionCallKiteKey);
			callLiveDataBean.setSymbolId(symbolId);
			callLiveDataBean.setOptionStrikePrice(optionCallKiteKey);
			callLiveDataBean.setOptionStrikePrice(strikePrice);
			callLiveDataBean.setOptionExpiryDate(expiryDate);
			map.put(optionCallKiteKey, callLiveDataBean);

			KiteLiveOHLCDataBean putLiveDataBean = new KiteLiveOHLCDataBean();
			putLiveDataBean.setKiteFutureKey(optionPutKiteKey);
			putLiveDataBean.setSymbolId(symbolId);
			putLiveDataBean.setOptionStrikePrice(optionPutKiteKey);
			putLiveDataBean.setOptionStrikePrice(strikePrice);
			putLiveDataBean.setOptionExpiryDate(expiryDate);
			map.put(optionPutKiteKey, putLiveDataBean);
		}
	}
	
	public void prepareLiveOHLCDataMapsForIndexOptionsWithKiteApiOrNseSite(String symbolId, List<String> expiryDateList,
			List<String> strikePriceList) {
		if (kiteConnectTool.validKiteAccess()) {
			/*prepareOHLCDataMapsForIndexOptionsWithAPIAccess(Constants.NIFTY);
			prepareOHLCDataMapsForIndexOptionsWithAPIAccess(Constants.BANKNIFTY);*/
			
			//When we have kite connect  access need not to get whole data, just fetch Index live data 
			/*prepareOHLCDataMapsForIndexOptionsWithAPIAccess( symbolId,  expiryDateList,
					 strikePriceList);*/
			updateLiveIndexPrice(symbolId);
			
		} else {
			prepareLiveOHLCDataMapsForIndexOptionsWithNseSiteIfNoAPIAccess();
		}
	}
	
	public void prepareLiveOHLCDataMapsForIndexOptionsWithString(String symbolId, String expiryDate,
			String... strikePrice) {
		List<String> expiryDateList = new ArrayList<String>();
		expiryDateList.add(expiryDate);
		List<String> strikePriceList = Arrays.asList(strikePrice);
		prepareOHLCDataMapsForIndexOptionsWithAPIAccess(symbolId, expiryDateList, strikePriceList);
	}
	public void prepareLiveOHLCDataMapsForIndexOptions(String symbolId, String expiryDate,
			String... strikePrice) {
		List<String> expiryDateList = new ArrayList<String>();
		expiryDateList.add(expiryDate);
		List<String> strikePriceList = Arrays.asList(strikePrice);
		prepareOHLCDataMapsForIndexOptionsWithAPIAccess(symbolId, expiryDateList, strikePriceList);
	}
	
	
	public void prepareLiveOHLCDataMapsForIndexOptions(String symbolId, String expiryDate,
			List<String> strikePriceList) {
		List<String> expiryDateList = new ArrayList<String>();
		expiryDateList.add(expiryDate);
		prepareOHLCDataMapsForIndexOptionsWithAPIAccess(symbolId, expiryDateList, strikePriceList);
	}
	
	public String getOptionTradeUnqueName(OptionTradeFormDataBean optionTradeOrder) {
		return optionTradeOrder.getSymbolId() + Constants.SPACE + optionTradeOrder.getTradeSubStrategy()
				+ Constants.SPACE + tradewareTool.getCurrentDateTimeForTradeName();
	}
	
	public String getTradewareSymbolId(String symbolId) {
		if (Constants.NIFTY.equals(symbolId)) {
			symbolId = Constants.NIFTY_50;
		} else if (Constants.BANKNIFTY.equals(symbolId)) {
			symbolId = NSE_SITE_NIFTY_BANK_SYMBOL;
		} else if (Constants.FINNIFTY.equals(symbolId)) {
			symbolId = NSE_SITE_FINNIFTY_SYMBOL;
		} else if (Constants.NIFTY_50.equals(symbolId)) {
			symbolId = Constants.NIFTY;
		} else if (Constants.NSE_SITE_NIFTY_BANK_SYMBOL.equals(symbolId)) {
			symbolId = BANKNIFTY;
		} else if (Constants.NSE_SITE_FINNIFTY_SYMBOL.equals(symbolId)) {
			symbolId = FINNIFTY;
		}
		return symbolId;
	}
	
	public String findNearByStrikePrice(String symbolId, Double currentPrice, Double optionTickerSize) {
		int nearLow = (int) (currentPrice - (currentPrice % optionTickerSize));
		int nearHigh = (int) (nearLow + optionTickerSize);

		return String.valueOf(((nearHigh - currentPrice) < (currentPrice - nearLow)) ? nearHigh : nearLow);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// Straddle intraday entry
	@Autowired
	private TradewareOptionStrategyBuildTool strategyBuildTool;
	public void algoTradeEntryForIntradayForStraddleEntry() {
		OptionTradeFormDataBean niftyTradeEntryBean = new OptionTradeFormDataBean();
		Double niftyIndexPrice = updateLiveIndexPrice(NIFTY);
		String targetStriePriceOfAtm = findNearByStrikePrice(NIFTY, niftyIndexPrice,
				getSymbolOptionTickerSizeMap(NIFTY));
		niftyTradeEntryBean.setSymbolId(NIFTY);
		niftyTradeEntryBean.setStrikePriceLowerOfAtm(Double.valueOf(targetStriePriceOfAtm));
		niftyTradeEntryBean.setStrikePriceHigherOfAtm(Double.valueOf(targetStriePriceOfAtm));
		niftyTradeEntryBean.setTradeStrategy(Constants.OPTION_STRATEGY_STRADDLE);
		niftyTradeEntryBean.setTradeSubStrategy(Constants.OPTION_STRATEGY_SHORT_STRADDLE);
		niftyTradeEntryBean.setNumberOfLots(20);
		niftyTradeEntryBean.setStockLastPrice(niftyIndexPrice);
		niftyTradeEntryBean.setIntradayTradeInd(Boolean.TRUE);
		List<String> dateList = tradewareTool.getTradeExpiryDatesList(NIFTY);
		if(dateList == null || dateList.isEmpty()) {
			if (tradewareTool.getTradeExpiryDatesList(Constants.NIFTY).isEmpty()
					|| tradewareTool.getTradeStrikePricesList(Constants.NIFTY).isEmpty()) {
				strategyBuildTool.prepareDataMapsForIndexWithNseSite();
			}
		}
			
		
		if (dateList.size() > 1) {
			Date expiryDate = tradewareTool.convertStringToDate(dateList.get(0), DATE_PATTERN_EXP_DATES);
			LocalDate localDateExpiry = expiryDate.toInstant().atZone(ZoneId.of(TIME_ZONE)).toLocalDate();
			if (LocalDate.now().isBefore(localDateExpiry)) {
				niftyTradeEntryBean.setExpiryDate(dateList.get(0));
			} else {
				niftyTradeEntryBean.setExpiryDate(dateList.get(1));
			}
		}
		optionStrategyStraddleBuilder(niftyTradeEntryBean);
		
		OptionTradeFormDataBean bankNiftyTradeEntryBean = new OptionTradeFormDataBean();
		Double bankNiftyIndexPrice = updateLiveIndexPrice(Constants.BANKNIFTY);
		targetStriePriceOfAtm = findNearByStrikePrice(Constants.BANKNIFTY, bankNiftyIndexPrice,
				getSymbolOptionTickerSizeMap(BANKNIFTY));
		bankNiftyTradeEntryBean.setSymbolId(Constants.BANKNIFTY);
		bankNiftyTradeEntryBean.setStrikePriceLowerOfAtm(Double.valueOf(targetStriePriceOfAtm));
		bankNiftyTradeEntryBean.setStrikePriceHigherOfAtm(Double.valueOf(targetStriePriceOfAtm));
		bankNiftyTradeEntryBean.setTradeStrategy(Constants.OPTION_STRATEGY_STRADDLE);
		bankNiftyTradeEntryBean.setTradeSubStrategy(Constants.OPTION_STRATEGY_SHORT_STRADDLE);
		bankNiftyTradeEntryBean.setNumberOfLots(12);
		bankNiftyTradeEntryBean.setStockLastPrice(bankNiftyIndexPrice);
		bankNiftyTradeEntryBean.setIntradayTradeInd(Boolean.TRUE);
		dateList = tradewareTool.getTradeExpiryDatesList(Constants.BANKNIFTY);
		if (dateList.size() > 1) {
			Date expiryDate = tradewareTool.convertStringToDate(dateList.get(0), DATE_PATTERN_EXP_DATES);
			LocalDate localDateExpiry = expiryDate.toInstant().atZone(ZoneId.of(TIME_ZONE)).toLocalDate();
			if (LocalDate.now().isBefore(localDateExpiry)) {
				bankNiftyTradeEntryBean.setExpiryDate(dateList.get(0));
			} else {
				bankNiftyTradeEntryBean.setExpiryDate(dateList.get(1));
			}
		}
		optionStrategyStraddleBuilder(bankNiftyTradeEntryBean);
	}
	// Strangle intraday entry
	/***TODO: add logic like if previous day close is greater than or less than of 25-50 points rage of current open for 
	 * Nifty and 100 points range for bank nifty. while entry of Algo Intra day low - open - high range should 50 - 0 -50
	 * for Nifty and 200 - 0 - 200 for bank nifty.   
	 * 
	 */
		public void algoTradeEntryForIntradayForStrangleleEntry() {
			OptionTradeFormDataBean niftyTradeEntryBean = new OptionTradeFormDataBean();
			Double niftyIndexPrice = updateLiveIndexPrice(NIFTY);
			String targetStriePriceOfAtm = findNearByStrikePrice(NIFTY, niftyIndexPrice,
					getSymbolOptionTickerSizeMap(NIFTY));
			niftyTradeEntryBean.setSymbolId(NIFTY);
			niftyTradeEntryBean.setStrikePriceLowerOfAtm(Double.valueOf(targetStriePriceOfAtm) - 300);
			niftyTradeEntryBean.setStrikePriceHigherOfAtm(Double.valueOf(targetStriePriceOfAtm) + 300);
			niftyTradeEntryBean.setTradeStrategy(Constants.OPTION_STRATEGY_STRANGLE);
			niftyTradeEntryBean.setTradeSubStrategy(Constants.OPTION_STRATEGY_SHORT_STRANGLE);
			niftyTradeEntryBean.setNumberOfLots(20);
			niftyTradeEntryBean.setStockLastPrice(niftyIndexPrice);
			niftyTradeEntryBean.setIntradayTradeInd(Boolean.TRUE);
			List<String> dateList = tradewareTool.getTradeExpiryDatesList(NIFTY);
			if(dateList == null || dateList.isEmpty()) {
				if (tradewareTool.getTradeExpiryDatesList(Constants.NIFTY).isEmpty()
						|| tradewareTool.getTradeStrikePricesList(Constants.NIFTY).isEmpty()) {
					strategyBuildTool.prepareDataMapsForIndexWithNseSite();
				}
			}
			if (dateList.size() > 1) {
				Date expiryDate = tradewareTool.convertStringToDate(dateList.get(0), DATE_PATTERN_EXP_DATES);
				LocalDate localDateExpiry = expiryDate.toInstant().atZone(ZoneId.of(TIME_ZONE)).toLocalDate();
				if (LocalDate.now().isBefore(localDateExpiry)) {
					niftyTradeEntryBean.setExpiryDate(dateList.get(0));
				} else {
					niftyTradeEntryBean.setExpiryDate(dateList.get(1));
				}
			}
			optionStrategyStrangleBuilder(niftyTradeEntryBean);
			
			OptionTradeFormDataBean bankNiftyTradeEntryBean = new OptionTradeFormDataBean();
			Double bankNiftyIndexPrice = updateLiveIndexPrice(Constants.BANKNIFTY);
			targetStriePriceOfAtm = findNearByStrikePrice(Constants.BANKNIFTY, bankNiftyIndexPrice,
					getSymbolOptionTickerSizeMap(BANKNIFTY));
			bankNiftyTradeEntryBean.setSymbolId(Constants.BANKNIFTY);
			bankNiftyTradeEntryBean.setStrikePriceLowerOfAtm(Double.valueOf(targetStriePriceOfAtm) - 500);
			bankNiftyTradeEntryBean.setStrikePriceHigherOfAtm(Double.valueOf(targetStriePriceOfAtm) + 500);
			bankNiftyTradeEntryBean.setTradeStrategy(Constants.OPTION_STRATEGY_STRANGLE);
			bankNiftyTradeEntryBean.setTradeSubStrategy(Constants.OPTION_STRATEGY_SHORT_STRANGLE);
			bankNiftyTradeEntryBean.setNumberOfLots(12);
			bankNiftyTradeEntryBean.setStockLastPrice(bankNiftyIndexPrice);
			bankNiftyTradeEntryBean.setIntradayTradeInd(Boolean.TRUE);
			dateList = tradewareTool.getTradeExpiryDatesList(Constants.BANKNIFTY);
			if (dateList.size() > 1) {
				Date expiryDate = tradewareTool.convertStringToDate(dateList.get(0), DATE_PATTERN_EXP_DATES);
				LocalDate localDateExpiry = expiryDate.toInstant().atZone(ZoneId.of(TIME_ZONE)).toLocalDate();
				if (LocalDate.now().isBefore(localDateExpiry)) {
					bankNiftyTradeEntryBean.setExpiryDate(dateList.get(0));
				} else {
					bankNiftyTradeEntryBean.setExpiryDate(dateList.get(1));
				}
			}
			optionStrategyStrangleBuilder(bankNiftyTradeEntryBean);
		}
	
	private OptionLiveTradeMainDataBean optionStrategyStraddleBuilder(OptionTradeFormDataBean optionTradeOrder) {
		OptionLiveTradeMainDataBean tradeBean = new OptionLiveTradeMainDataBean();
		if (Constants.OPTION_STRATEGY_STRADDLE.equals(optionTradeOrder.getTradeStrategy())) {

			/*prepareLiveOHLCDataMapsForIndexOptions(optionTradeOrder.getSymbolId(), optionTradeOrder.getExpiryDate(),
					String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm()),
					String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()));

			OptionTradeLiveDataBean putBeanLive = getOptionTradeLiveDataBean(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm()));
			OptionTradeLiveDataBean callBeanLive = getOptionTradeLiveDataBean(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()));*/
			String  putKey = tradewareTool.getKiteOptionKeyWeeklyOrMonthly(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm()),
					Constants.OPTION_PUT);
			String callKey = tradewareTool.getKiteOptionKeyWeeklyOrMonthly(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()),
					Constants.OPTION_CALL);
			ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = tradewareTool.getOptionAskBidBidValue123(optionTradeOrder.getSymbolId(),
						optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm()));
			KiteLiveOHLCDataBean putBeanLive = map.get(putKey);
			map = tradewareTool.getOptionAskBidBidValue123(optionTradeOrder.getSymbolId(),
						optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()));
			KiteLiveOHLCDataBean callBeanLive = map.get(callKey);
			
			tradeBean.setTradeName(getOptionTradeUnqueName(optionTradeOrder));
			tradeBean.setSymbolId(optionTradeOrder.getSymbolId());
			tradeBean.setLotSize(getSymbolLotSizeMap(optionTradeOrder.getSymbolId()));
			tradeBean.setOptionTickerSize(getSymbolOptionTickerSizeMap(optionTradeOrder.getSymbolId()));
			tradeBean.setExpiryDate(optionTradeOrder.getExpiryDate());
			tradeBean.setTradeStrategy(optionTradeOrder.getTradeStrategy());
			tradeBean.setTradeSubStrategy(optionTradeOrder.getTradeSubStrategy());
			LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
			tradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
			tradeBean.setTradePosition(Constants.TRADE_POSIITON_NEW);
			tradeBean.setIntradayTradeInd(optionTradeOrder.getIntradayTradeInd());
			
			OptionLiveTradeChildDataBean putBean = new OptionLiveTradeChildDataBean();
			OptionLiveTradeChildDataBean callBean = new OptionLiveTradeChildDataBean();

			putBean.setStrikePrice(optionTradeOrder.getStrikePriceLowerOfAtm());
			putBean.setTradePosition(TRADE_POSIITON_NEW);
			putBean.setOptionType(Constants.OPTION_PUT);
			putBean.setKiteFutureKey(tradewareTool.getKiteOptionKeyWeeklyOrMonthly(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm()),
					Constants.OPTION_PUT));
			putBean.setNumberOfLots(optionTradeOrder.getNumberOfLots());
			putBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
			/*putBean.setBidPrice(putBeanLive.getBidPricePut());
			putBean.setAskPrice(putBeanLive.getAskPricePut());
			putBean.setBidQuantity(putBeanLive.getBidQuantityPut());
			putBean.setAskQuantity(putBeanLive.getAskQuantityPut());*/
			putBean.setBidPrice(putBeanLive.getBidPrice());
			putBean.setAskPrice(putBeanLive.getAskPrice());
			putBean.setBidQuantity(putBeanLive.getBidQuantity());
			putBean.setAskQuantity(putBeanLive.getAskQuantity());

			callBean.setStrikePrice(optionTradeOrder.getStrikePriceHigherOfAtm());
			callBean.setTradePosition(TRADE_POSIITON_NEW);
			callBean.setOptionType(Constants.OPTION_CALL);
			callBean.setKiteFutureKey(tradewareTool.getKiteOptionKeyWeeklyOrMonthly(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()),
					Constants.OPTION_CALL));
			callBean.setNumberOfLots(optionTradeOrder.getNumberOfLots());
			callBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
			/*callBean.setBidPrice(callBeanLive.getBidPriceCall());
			callBean.setAskPrice(callBeanLive.getAskPriceCall());
			callBean.setBidQuantity(callBeanLive.getBidQuantityCall());
			callBean.setAskQuantity(callBeanLive.getAskQuantityCall());*/
			callBean.setBidPrice(callBeanLive.getBidPrice());
			callBean.setAskPrice(callBeanLive.getAskPrice());
			callBean.setBidQuantity(callBeanLive.getBidQuantity());
			callBean.setAskQuantity(callBeanLive.getAskQuantity());

			if (Constants.OPTION_STRATEGY_SHORT_STRADDLE.equals(optionTradeOrder.getTradeSubStrategy())) {
				putBean.setTradeType(Constants.SELL);
				putBean.setSellAtValue(putBeanLive.getBidPrice());
				callBean.setTradeType(Constants.SELL);
				callBean.setSellAtValue(callBeanLive.getBidPrice());
			} else if (Constants.OPTION_STRATEGY_LONG_STRADDLE.equals(optionTradeOrder.getTradeSubStrategy())) {
				putBean.setTradeType(Constants.BUY);
				putBean.setBuyAtValue(putBeanLive.getAskPrice());
				callBean.setTradeType(Constants.BUY);
				callBean.setBuyAtValue(callBeanLive.getAskPrice());
			}
			
			List<OptionLiveTradeChildDataBean> tradeChildBeanList = new ArrayList<OptionLiveTradeChildDataBean>();
			tradeChildBeanList.add(putBean);
			tradeChildBeanList.add(callBean);
			tradeBean.setTradeChildBeanList(tradeChildBeanList);

			tradeBean = DatabaseHelper.getInstance().saveShortStrangleOptionTrade(tradeBean);
			//tradewareTool.getTradingIntradayStraddleOptionDataMap().put(tradeBean.getTradeName(), tradeBean);
			tradewareTool.getTradingStrangleOptionDataMap().put(tradeBean.getTradeName(), tradeBean);
		}
		return tradeBean;
	}
	
	private OptionLiveTradeMainDataBean optionStrategyStrangleBuilder(OptionTradeFormDataBean optionTradeOrder) {
		OptionLiveTradeMainDataBean tradeBean = new OptionLiveTradeMainDataBean();
		if (Constants.OPTION_STRATEGY_STRANGLE.equals(optionTradeOrder.getTradeStrategy())) {
		// Additional start
		// if no kite access start
		/*OptionTradeLiveDataBean putBeanLive = getOptionTradeLiveDataBean(optionTradeOrder.getSymbolId(),
				optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm()));
		OptionTradeLiveDataBean callBeanLive = getOptionTradeLiveDataBean(optionTradeOrder.getSymbolId(),
				optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()));*/
		String  putKey = tradewareTool.getKiteOptionKeyWeeklyOrMonthly(optionTradeOrder.getSymbolId(),
				optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm()),
				Constants.OPTION_PUT);
		String callKey = tradewareTool.getKiteOptionKeyWeeklyOrMonthly(optionTradeOrder.getSymbolId(),
				optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()),
				Constants.OPTION_CALL);
		ConcurrentHashMap<String, KiteLiveOHLCDataBean> map = tradewareTool.getOptionAskBidBidValue123(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceLowerOfAtm()));
		KiteLiveOHLCDataBean putBeanLive = map.get(putKey);
		map = tradewareTool.getOptionAskBidBidValue123(optionTradeOrder.getSymbolId(),
					optionTradeOrder.getExpiryDate(), String.valueOf(optionTradeOrder.getStrikePriceHigherOfAtm()));
		KiteLiveOHLCDataBean callBeanLive = map.get(callKey);
		// if no kite access end
		// additional end
		
		// tradeBean.setItemId(itemId);
		tradeBean.setTradeName(getOptionTradeUnqueName(optionTradeOrder));
		tradeBean.setSymbolId(optionTradeOrder.getSymbolId());
		tradeBean.setLotSize(tradewareTool.getSymbolLotSizeMap(optionTradeOrder.getSymbolId()));
		tradeBean.setOptionTickerSize(tradewareTool.getSymbolOptionTickerSizeMap(optionTradeOrder.getSymbolId()));
		tradeBean.setExpiryDate(optionTradeOrder.getExpiryDate());
		tradeBean.setTradeStrategy(optionTradeOrder.getTradeStrategy());
		tradeBean.setTradeSubStrategy(optionTradeOrder.getTradeSubStrategy());

		/*
		 * LocalDateTime ldt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
		 * tradeBean.setTradedAtDtTm(Timestamp.valueOf(ldt));
		 */
		// set tradedAtDtTm while placing order
		// tradeBean.setExitedAtDtTm(exitedAtDtTm);
		// tradeBean.setProfitLossAmtVal(profitLossAmtVal);
		// tradeBean.setPositiveMoveVal(positiveMoveVal);
		// tradeBean.setNegativeMoveVal(negativeMoveVal);
		LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
		tradeBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
		tradeBean.setTradePosition(Constants.TRADE_POSIITON_NEW);
		tradeBean.setIntradayTradeInd(optionTradeOrder.getIntradayTradeInd());
		
		OptionLiveTradeChildDataBean putBean = new OptionLiveTradeChildDataBean();
		OptionLiveTradeChildDataBean callBean = new OptionLiveTradeChildDataBean();

		// putBean.setItemIdChild(itemIdChild);
		putBean.setStrikePrice(optionTradeOrder.getStrikePriceLowerOfAtm());
		putBean.setTradeType(optionTradeOrder.getLowerOfAtmTradeType());
		putBean.setTradePosition(TRADE_POSIITON_NEW);
		putBean.setOptionType(Constants.OPTION_PUT);
		putBean.setKiteFutureKey(putKey);
		/////putBean.setSellAtValue(optionTradeOrder.getBidPriceLowerOfAtm());
		///////// putBean.setTradedAtVal(optionTradeOrder.getBidPriceLowerOfAtm());
		// putBean.setBuyAtValue(buyAtValue);
		// putBean.setExitedAtVal(exitedAtVal);

		/*
		 * putBean.setStockPriceEntry(optionTradeOrder.getUnderlyingStockPrice());
		 * putBean.setFuturePriceEntry(optionTradeOrder.getUnderlyingFuturePrice());
		 */
		// putBean.setStockPriceEntry(putBeanLive.getUnderlyingStockPrice());
		// putBean.setFuturePriceEntry(putBeanLive.getUnderlyingFuturePrice());
		////////// updateLiveIndexPrice(optionTradeOrder.getSymbolId(), putBean);

		// putBean.setStockPriceExit(stockPriceExit);
		// putBean.setFuturePriceExit(futurePriceExit);
		putBean.setNumberOfLots(optionTradeOrder.getNumberOfLots());
		// set tradedAtDtTm while placing order
		/// putBean.setTradedAtDtTm(Timestamp.valueOf(ldt));
		// putBean.setExitedAtDtTm(exitedAtDtTm);
		// putBean.setProfitLossAmtVal(profitLossAmtVal);
		// putBean.setPositiveMoveVal(positiveMoveVal);
		// putBean.setNegativeMoveVal(negativeMoveVal);
		// putBean.setPositiveMoveLtp(positiveMoveLtp);
		// putBean.setNegativeMoveLtp(negativeMoveLtp);
		putBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
		// putBean.setOptionChainTrend(optionChainTrend);;
		// putBean.setOptionChainPriceTrend(optionChainPriceTrend);
		// putBean.setOptionChainId(optionChainId);
		// putBean.setOiInfo(oiInfo);

		/*putBean.setBidPrice(putBeanLive.getBidPricePut());
		putBean.setAskPrice(putBeanLive.getAskPricePut());
		putBean.setBidQuantity(putBeanLive.getBidQuantityPut());
		putBean.setAskQuantity(putBeanLive.getAskQuantityPut());*/
		putBean.setBidPrice(putBeanLive.getBidPrice());
		putBean.setAskPrice(putBeanLive.getAskPrice());
		putBean.setBidQuantity(putBeanLive.getBidQuantity());
		putBean.setAskQuantity(putBeanLive.getAskQuantity());
		/*putBean.setBidPrice(putBeanLive.getBidPricePut());
		putBean.setAskPrice(putBeanLive.getAskPricePut());
		putBean.setBidQuantity(putBeanLive.getBidQuantityPut());
		putBean.setAskQuantity(putBeanLive.getAskQuantityPut());*/
		
		/*
		 * putBean.setInstrumentToken(dataBean.getInstrumentToken());
		 * putBean.setKiteOrderId(dataBean.getKiteOrderId());
		 * putBean.setKiteOrderType(dataBean.getKiteOrderType());
		 */

		// callBean.setItemIdChild(itemIdChild);
		callBean.setStrikePrice(optionTradeOrder.getStrikePriceHigherOfAtm());
		callBean.setTradeType(optionTradeOrder.getHigherOfAtmTradeType());
		callBean.setTradePosition(TRADE_POSIITON_NEW);
		callBean.setOptionType(Constants.OPTION_CALL);
		callBean.setKiteFutureKey(callKey);
		// callBean.setBuyAtValue(buyAtValue);
		callBean.setSellAtValue(optionTradeOrder.getBidPriceHigherOfAtm());
		/////////////// callBean.setTradedAtVal(optionTradeOrder.getBidPriceHigherOfAtm());
		// callBean.setExitedAtVal(exitedAtVal);
		/*
		 * callBean.setStockPriceEntry(optionTradeOrder.getUnderlyingStockPrice());
		 * callBean.setFuturePriceEntry(optionTradeOrder.getUnderlyingFuturePrice());
		 */
		// callBean.setStockPriceEntry(callBeanLive.getUnderlyingStockPrice());
		// callBean.setFuturePriceEntry(callBeanLive.getUnderlyingFuturePrice());
		// Need not update again, already updated above, but need to get from putBean
		// and set
		///////////// updateLiveIndexPrice(optionTradeOrder.getSymbolId(), callBean);

		// callBean.setStockPriceExit(stockPriceExit);
		// callBean.setFuturePriceExit(futurePriceExit);
		callBean.setNumberOfLots(optionTradeOrder.getNumberOfLots());
		// set tradedAtDtTm while placing order
		//////// callBean.setTradedAtDtTm(Timestamp.valueOf(ldt));
		// callBean.setExitedAtDtTm(exitedAtDtTm);
		// callBean.setProfitLossAmtVal(profitLossAmtVal);
		// callBean.setPositiveMoveVal(positiveMoveVal);
		// callBean.setNegativeMoveVal(negativeMoveVal);
		// callBean.setPositiveMoveLtp(positiveMoveLtp);
		// callBean.setNegativeMoveLtp(negativeMoveLtp);
		callBean.setTradedDateStamp(java.sql.Date.valueOf(ld));
		// callBean.setOptionChainTrend(optionChainTrend);;
		// callBean.setOptionChainPriceTrend(optionChainPriceTrend);
		// callBean.setOptionChainId(optionChainId);
		// callBean.setOiInfo(oiInfo);
		
		/*callBean.setBidPrice(callBeanLive.getBidPriceCall());
		callBean.setAskPrice(callBeanLive.getAskPriceCall());
		callBean.setBidQuantity(callBeanLive.getBidQuantityCall());
		callBean.setAskQuantity(callBeanLive.getAskQuantityCall());*/
		callBean.setBidPrice(callBeanLive.getBidPrice());
		callBean.setAskPrice(callBeanLive.getAskPrice());
		callBean.setBidQuantity(callBeanLive.getBidQuantity());
		callBean.setAskQuantity(callBeanLive.getAskQuantity());
		/*callBean.setBidPrice(callBeanLive.getBidPriceCall());
		callBean.setAskPrice(callBeanLive.getAskPriceCall());
		callBean.setBidQuantity(callBeanLive.getBidQuantityCall());
		callBean.setAskQuantity(callBeanLive.getAskQuantityCall());*/
		/*
		 * callBean.setInstrumentToken(dataBean.getInstrumentToken());
		 * callBean.setKiteOrderId(dataBean.getKiteOrderId());
		 * callBean.setKiteOrderType(dataBean.getKiteOrderType());
		 */
		if (Constants.OPTION_STRATEGY_SHORT_STRANGLE.equals(optionTradeOrder.getTradeSubStrategy())) {
			putBean.setTradeType(Constants.SELL);
			//putBean.setSellAtValue(putBeanLive.getBidPricePut());
			putBean.setSellAtValue(putBeanLive.getBidPrice());
			callBean.setTradeType(Constants.SELL);
			//callBean.setSellAtValue(callBeanLive.getBidPriceCall());
			callBean.setSellAtValue(callBeanLive.getBidPrice());
		} else if (Constants.OPTION_STRATEGY_LONG_STRANGLE.equals(optionTradeOrder.getTradeSubStrategy())) {
			putBean.setTradeType(Constants.BUY);
			//putBean.setBuyAtValue(putBeanLive.getAskPricePut());
			putBean.setBuyAtValue(putBeanLive.getAskPrice());
			callBean.setTradeType(Constants.BUY);
			//callBean.setBuyAtValue(callBeanLive.getAskPriceCall());
			callBean.setBuyAtValue(callBeanLive.getAskPrice());
		}

		List<OptionLiveTradeChildDataBean> tradeChildBeanList = new ArrayList<OptionLiveTradeChildDataBean>();
		tradeChildBeanList.add(putBean);
		tradeChildBeanList.add(callBean);
		tradeBean.setTradeChildBeanList(tradeChildBeanList);

		tradeBean = DatabaseHelper.getInstance().saveShortStrangleOptionTrade(tradeBean);
		tradewareTool.getTradingStrangleOptionDataMap().put(tradeBean.getTradeName(), tradeBean);
		}
		return tradeBean;
	}
}
