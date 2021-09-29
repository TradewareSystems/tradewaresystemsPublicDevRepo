package hoghlow.tradeware.stockmarket.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.tradeware.clouddatabase.bean.StrategyHighLowBean;
import com.tradeware.clouddatabase.service.StrategyHighLowService;

import hoghlow.tradeware.stockmarket.bean.StockDataBean;
import hoghlow.tradeware.stockmarket.log.NkpAlgoLogger;
import hoghlow.tradeware.stockmarket.tool.HighLowStrategyTool;
import hoghlow.tradeware.stockmarket.util.Constants;

@Component  
public class DatabaseHelper  implements ApplicationContextAware, Constants {

	  private static ApplicationContext ac;

	  @Override
	  public void setApplicationContext(ApplicationContext ac) {
		  DatabaseHelper.ac = ac;
	  }
	
	private static DatabaseHelper singletonTool;

	public DatabaseHelper() {
		//service = new StrategyHighLowService();
	}

	public static DatabaseHelper getInstance() {
		if (null == singletonTool) {
			singletonTool = (DatabaseHelper) ac.getBean("databaseHelper");
		}
		return singletonTool;
	}
	
	@Autowired
	private StrategyHighLowService service;
	
	public Integer saveTradeOnEntry(StockDataBean baseBean) {
		StrategyHighLowBean bean =  getSymbolBeanToSave(baseBean);
		bean = service.saveTrade(bean);
		NkpAlgoLogger.printWithNewLine("saveTradeOnEntry --> bean.getSymbol() - "+bean.getSymbol()+" -- bean.getItemId() - "+bean.getItemId());
		return bean.getItemId();
	}
	
	public void updateTrade(StockDataBean bean) {
		NkpAlgoLogger.printWithNewLine(
				"updateTrade --> bean.getStockName() - " + bean.getStockName() + " -- bean.getStockId() -- "
						+ bean.getStockId() + " --bean.getProfitLossAmt() -- " + bean.getProfitLossAmt().doubleValue());
		service.updateTrade(bean.getTradedState(), bean.getBuyerAt(), bean.getTradedOutAt(), bean.getProfitLossAmt(),
				bean.getPositiveDirectionProfit(), bean.getNegativeDirectionLoss(), Integer.valueOf(bean.getStockId()));
	}
	
	public List<StockDataBean> findAllTrades() {
		List<StrategyHighLowBean>  baseList = service.findAllTrades();
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	
	/*public List<StockDataBean> findAllTradesByTradedDate(String reportDate) {
		Date tradeDay = null;
		if (null != reportDate && !EMPTY_STRING.equals(reportDate)) {
			LocalDate dateInstance = LocalDate.parse(reportDate);
			tradeDay = Date.from(dateInstance.atStartOfDay().atZone(ZoneId.of(TIME_ZONE))
				      .toInstant());
		} else {
			tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		}
		
		List<StrategyHighLowBean>  baseList = service.findAllTradesByTradedDate(tradeDay);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}*/
	public List<StockDataBean> findAllTradesByTradedDate() {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowBean>  baseList = service.findAllTradesByTradedDate(tradeDay);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	
	public List<StockDataBean> findAllTradesByTradedDateAndTrendMactchWithNifty50() {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowBean>  baseList = service.findAllTradesByTradedDateAndTrendMactchWithNifty50(tradeDay);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	
	public List<StockDataBean> findAllTradesByTradedDateAndTrendMactchWithNifty50AndCloseRule() {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowBean>  baseList = service.findAllTradesByTradedDateAndTrendMactchWithNifty50AndCloseRuleOrderBySymbolAscTradedAtDtAsc(tradeDay);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	
	public List<StockDataBean> getRunningTrades() {
		List<StrategyHighLowBean>  baseList = service.getRunningTrades();
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	
	public List<StockDataBean> getBuyOrSellOnlyTrades(String tradedState) {
		//List<StrategyHighLowBean>  baseList = service.findAllByTradedStateStartingWithAndTradedDateAfterOrderByTradedAtDt(tradedState);
		List<StrategyHighLowBean>  baseList = service.findAllByTradedStateStartingWithAndTradedDateOrderBySymbolTradedAtDt(tradedState);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}

	public List<StockDataBean> getProfitOrLossOnlyTrades(String tradedState) {
		//List<StrategyHighLowBean>  baseList = service.findAllByTradedStateContainsAndTradedDateAfterOrderByTradedAtDt(tradedState);
		List<StrategyHighLowBean>  baseList = service.findAllByTradedStateContainsAndTradedDateOrderBySymbolTradedAtDt(tradedState);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	
	public List<StockDataBean> getBuyOrSellOnlyWithClosableRule(String tradedState) {
		List<StrategyHighLowBean>  baseList = service.findAllByTradedStateAndCloseRulesAndTradedDateAfterOrderByTradedAtDt(tradedState);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	
	public List<StockDataBean> findAllByCustomRuleSell() {
		List<StrategyHighLowBean>  baseList = service.findAllByCustomRuleSell();
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	public List<StockDataBean> findAllByCustomRuleBuy() {
		List<StrategyHighLowBean>  baseList = service.findAllByCustomRuleBuy();
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	
	public List<StockDataBean> findAllByNr4RuleIndAndReportDate() {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowBean>  baseList = service.findAllByNr4RuleAndTradedDateOrderBySymbolAscTradedAtDtAsc(true, tradeDay);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	public List<StockDataBean> findAllByNr4RuleInd() {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowBean>  baseList = service.findAllByNr4RuleAndTradedDateGreaterThanEqualOrderBySymbolAscTradedAtDtAsc(true, tradeDay);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	public List<StockDataBean> findAllByTradedStateAndCPRGreaterThanZero(String tradeState) {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowBean>  baseList = service.findAllByTradedStateAndCPROrderBySymbolAscTradedAtDtAsc(tradeState, tradeDay);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	public List<StockDataBean> findAllByTradedStateAndCPRLessThanOREqualToZero(String tradeState) {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowBean>  baseList = service.findAllByTradedStateAndNotCPROrderBySymbolAscTradedAtDtAsc(tradeState, tradeDay);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	
	public List<StockDataBean> findAllByTradedStateAndCPRGreaterThanZeroAndCloseRules(String tradeState) {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowBean>  baseList = service.findAllByTradedStateAndCPROAndCloserderBySymbolAscTradedAtDtAsc(tradeState, tradeDay);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	public List<StockDataBean> findAllByTradedStateAndCPRLessThanOREqualToZeroAndCloseRules(String tradeState) {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowBean>  baseList = service.findAllByTradedStateAndNotCPRAndCloseOrderBySymbolAscTradedAtDtAsc(tradeState, tradeDay);
		List<StockDataBean> reportList = new ArrayList<StockDataBean>(baseList.size());
		for(StrategyHighLowBean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	
	private StockDataBean mapBeanData(StrategyHighLowBean rowBean ) {
		StockDataBean bean = new StockDataBean(rowBean.getLotSize(), rowBean.getSymbol());	
		bean.setStockId(rowBean.getItemId().toString());
		bean.setStockName(rowBean.getSymbol());
		bean.setLotSize(rowBean.getLotSize());
		bean.setBuyOrSellAt(rowBean.getBuyValue());
		bean.setStopLoss(rowBean.getSellValue());
		bean.setTradableState(rowBean.getTradableState());
		bean.setTradedState(rowBean.getTradedState());
		bean.setTradedVal(rowBean.getTradedAt());
		bean.setBuyerAt(rowBean.getExitedAt());//bean.setExitedAt(rowBean.getexitedAt());
		bean.setTradedInAt(rowBean.getTradedAtDt());
		bean.setTradedOutAt(rowBean.getExitedAtDt());
		bean.setProfitLossAmt(rowBean.getProfitLossAmt());
		bean.setBuyerSellerDiff(rowBean.getBuySellDiff());
		bean.setPositiveDirectionProfit(rowBean.getPositiveMoveValue());
		bean.setNegativeDirectionLoss(rowBean.getNegativeMoveValue());
		bean.setAdditinalInfo(rowBean.getAdditionalInfo1());
		bean.setVolumes(rowBean.getVolumes());
		bean.setCloseRule(rowBean.getCloseRule());
		bean.setCloseHighRule1(rowBean.getCloseHighRule1());
		bean.setCloseHighRule2(rowBean.getCloseHighRule2());
		bean.setOpenHighLowRule(rowBean.getOpenHighLowRule());
		bean.setOpenHighLowHARule(rowBean.getOpenHighLowHARule());
		//bean.setTradingDayDt(rowBean.getTradingDayDt());
		bean.setSmallCandle(rowBean.getSmallCandle());
		bean.setOppositeHighLowRule(rowBean.getOppositeHighLowRule());
		bean.setNr4Rule(rowBean.getNr4Rule());
		bean.setDayHighLowRule(rowBean.getDayHighLowRule());
		bean.setVolumeTrend(rowBean.getVolumeTrend());
		bean.setLastCandle(rowBean.getLastCandle());
		bean.setOhlcState(rowBean.getOhlcState());
		bean.setStrengthFactor(rowBean.getStrengthFactor());

		bean.setDay1VolStrengthFactor(rowBean.getDay1VolStrengthFactor());
		bean.setDay2VolStrengthFactor(rowBean.getDay2VolStrengthFactor());
		bean.setDay1CandleVolumes(rowBean.getDay1CandleVolumes());
		bean.setCurrentCandleVolumesOnTradeEntry(rowBean.getCurrentCandleVolumesOnTradeEntry());
		bean.setCurrentCandleOHLRuleInd(rowBean.getCurrentCandleOHLRuleInd());
		
		bean.setDayRsi(rowBean.getRsiVal());
		bean.setVwapVal(rowBean.getVwapVal());
		bean.setCandleMovement(rowBean.getCandleMovement());
		bean.setDay1Movement(rowBean.getDay1Movement());
		bean.setDay2Movement(rowBean.getDay2Movement());
		bean.setMinute5CPR(rowBean.getMinute5CPR());
		bean.setMinute15CPR(rowBean.getMinute15CPR());
		bean.setAdditionalInfoNifty50(rowBean.getAdditionalInfoNifty50());
		bean.setStrenthTradableState(rowBean.getStrenthTradableState());
		bean.setStrenthTradableStateNifty(rowBean.getStrenthTradableStateNifty());
		
		prepareTradeInfo(bean);
		return bean;
	}
	
	private static StringBuffer sb = new StringBuffer();
	private static StringBuffer sbExit = new StringBuffer();
	//private static String BUY = "BUY";
	//private static String SELL = "SELL";
	private void prepareTradeInfo(StockDataBean bean) {
		clearBuffer();
		if (null != bean.getTradedState()) {
			if (bean.getTradedState().startsWith(BUY)) {
				sb.append(HighLowStrategyTool.getInstance().getCurrentTime(bean.getTradedInAt())).append(BUY_ACTIVATE)
						.append(bean.getTradedVal());
				//if (bean.getTradedState().equals(BUY_EXIT_PROFIT)) {
				if (bean.getTradedState().contains(BUY_EXIT_PROFIT)) {
					sbExit.append(HighLowStrategyTool.getInstance().getCurrentTime(bean.getTradedOutAt()))
							.append(BUY_TARGET).append(bean.getBuyerAt()).append(WITH_PROFIT)
							.append(bean.getProfitLossAmt());
				//} else if (bean.getTradedState().equals(BUY_EXIT_LOSS)) {
				} else if (bean.getTradedState().contains(BUY_EXIT_LOSS)) {
					sbExit.append(HighLowStrategyTool.getInstance().getCurrentTime(bean.getTradedOutAt()))
							.append(CLOSED_WITH_LOSE).append(bean.getBuyerAt()).append(WITH_LOSS)
							.append(bean.getProfitLossAmt());
				}
			} else if (bean.getTradedState().startsWith(SELL)) {
				sb.append(HighLowStrategyTool.getInstance().getCurrentTime(bean.getTradedInAt())).append(SELL_ACTIVATE)
						.append(bean.getTradedVal());
				//if (bean.getTradedState().equals(SELL_EXIT_PROFIT)) {
				if (bean.getTradedState().contains(SELL_EXIT_PROFIT)) {
					sbExit.append(HighLowStrategyTool.getInstance().getCurrentTime(bean.getTradedOutAt()))
							.append(SELL_TARGET).append(bean.getBuyerAt()).append(WITH_PROFIT)
							.append(bean.getProfitLossAmt());
				//} else if (bean.getTradedState().equals(SELL_EXIT_LOSS)) {
				} else if (bean.getTradedState().contains(SELL_EXIT_LOSS)) {
					sbExit.append(HighLowStrategyTool.getInstance().getCurrentTime(bean.getTradedOutAt()))
							.append(CLOSED_WITH_LOSE).append(bean.getBuyerAt()).append(WITH_LOSS)
							.append(bean.getProfitLossAmt());
				}
			}
			bean.setSignalTriggeredInAt(sb.toString());
			bean.setSignalTriggeredOutAt(sbExit.toString());
		}
	}

	private void clearBuffer() {
		if (sb.length() > 0) {
			sb.delete(0, sb.length());
		}
		if (sbExit.length() > 0) {
			sbExit.delete(0, sbExit.length());
		}
	}

	public StrategyHighLowBean getSymbolBeanToSave(StockDataBean baseBean) {
		StrategyHighLowBean bean = new StrategyHighLowBean();
		bean.setSymbol(baseBean.getStockName());
		bean.setLotSize(baseBean.getLotSize());
		if ("BUY".equals(baseBean.getTradableState())) {
			bean.setBuyValue(baseBean.getBuyOrSellAt());
			bean.setSellValue(baseBean.getStopLoss());
		} else if ("SELL".equals(baseBean.getTradableState())) {
			bean.setBuyValue(baseBean.getStopLoss());
			bean.setSellValue(baseBean.getBuyOrSellAt());	
		}
		bean.setTradableState(baseBean.getTradableState());
		bean.setTradedState(baseBean.getTradedState());
		bean.setTradedAt(baseBean.getTradedVal());
		//bean.setExitedAt(baseBean.getBuyerAt());
		bean.setTradedAtDt(baseBean.getTradedInAt());;
		//bean.setExitedAtDt(baseBean.getTradedOutAt());
		//bean.setProfitLossAmt(baseBean.getProfitLossAmt()); 
		bean.setBuySellDiff(baseBean.getBuyerSellerDiff()); 
		//bean.setPositiveMoveValue(baseBean.getPositiveDirectionProfit());
		//bean.setNegativeMoveValue(baseBean.getNegativeDirectionLoss());
		bean.setAdditionalInfo1(baseBean.getAdditinalInfo());
		bean.setVolumes(baseBean.getVolumes());
		bean.setCloseRule(baseBean.isCloseRule());
		bean.setCloseHighRule1(baseBean.isCloseHighRule1());
		bean.setCloseHighRule2(baseBean.isCloseHighRule2());
		bean.setOpenHighLowRule(baseBean.isOpenHighLowRule());
		bean.setOpenHighLowHARule(baseBean.isOpenHighLowHARule());
		ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(TIME_ZONE)).atZone(ZoneId.of(TIME_ZONE));
		bean.setTradedDate(Date.from(zdt.toInstant()));
		bean.setSmallCandle(baseBean.isSmallCandle());
		bean.setOppositeHighLowRule(baseBean.isOppositeHighLowRule());
		bean.setNr4Rule(baseBean.isNr4Rule());
		bean.setDayHighLowRule(baseBean.isDayHighLowRule());
		bean.setVolumeTrend(baseBean.getVolumeTrend());
		bean.setLastCandle(baseBean.getLastCandle());
		bean.setOhlcState(baseBean.getOhlcState());
		bean.setStrengthFactor(baseBean.getStrengthFactor());
		

		bean.setDay1VolStrengthFactor(baseBean.getDay1VolStrengthFactor());
		bean.setDay2VolStrengthFactor(baseBean.getDay2VolStrengthFactor());
		bean.setDay1CandleVolumes(baseBean.getDay1CandleVolumes());
		bean.setCurrentCandleVolumesOnTradeEntry(baseBean.getCurrentCandleVolumesOnTradeEntry());
		bean.setCurrentCandleOHLRuleInd(baseBean.isCurrentCandleOHLRuleInd());
		
		bean.setRsiVal(baseBean.getDayRsi());
		bean.setVwapVal(baseBean.getVwapVal());
		bean.setCandleMovement(baseBean.getCandleMovement());
		bean.setDay1Movement(baseBean.getDay1Movement());
		bean.setDay2Movement(baseBean.getDay2Movement());
		bean.setMinute5CPR(baseBean.getMinute5CPR());
		bean.setMinute15CPR(baseBean.getMinute15CPR());
		bean.setAdditionalInfoNifty50(baseBean.getAdditionalInfoNifty50());
		bean.setStrenthTradableState(baseBean.getStrenthTradableState());
		bean.setStrenthTradableStateNifty(baseBean.getStrenthTradableStateNifty());
		return bean;
	}
	
	public StrategyHighLowBean getSymbolBeanToSaveForTestData() {
		StrategyHighLowBean bean = new StrategyHighLowBean();
		bean.setItemId(999999999);
		bean.setSymbol("TCS");
		bean.setLotSize(250);
			bean.setBuyValue(2000d);
			bean.setSellValue(1900d);
		bean.setTradableState(BUY);
		bean.setTradedState(BUY);
		bean.setTradedAt(2000.15);
		bean.setTradedAtDt(Calendar.getInstance().getTime());;
		bean.setBuySellDiff(749.99); 
		bean.setAdditionalInfo1("02:40:02, R, R, R, 1512.5, <span style='color:green'><b>SELL-0.64,0.45</b></span>, 374000, 295625, 345125, DH-360.2,DL-339.1");
		bean.setVolumes(32813000L);
		bean.setCloseRule(true);
		bean.setCloseHighRule1(true);
		bean.setCloseHighRule2(false);
		bean.setOpenHighLowRule(false);
		bean.setOpenHighLowHARule(true);
		ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(TIME_ZONE)).atZone(ZoneId.of(TIME_ZONE));
		bean.setTradedDate(Date.from(zdt.toInstant()));
		bean.setSmallCandle(false);
		bean.setOppositeHighLowRule(true);
		bean.setNr4Rule(true);
		bean.setDayHighLowRule(true);
		bean.setVolumeTrend(LEVEL_NA);
		bean.setLastCandle(LAST_CANDLE_RED);
		bean.setOhlcState(BUY);
		bean.setStrengthFactor(121.12);
		

		bean.setDay1VolStrengthFactor(0.99);
		bean.setDay2VolStrengthFactor(1.11);
		bean.setDay1CandleVolumes(111111111L);
		bean.setCurrentCandleVolumesOnTradeEntry(111111111L);
		bean.setCurrentCandleOHLRuleInd(true);
		
		bean.setRsiVal(79.89);
		bean.setVwapVal(2100.95d);
		bean.setCandleMovement(5000d);
		bean.setDay1Movement(15000.55);
		bean.setDay2Movement(2500.05);
		bean.setMinute5CPR(11000.15);
		bean.setMinute15CPR(790.75);
		bean.setAdditionalInfoNifty50("	02:40:02, TREND_SELL, VOL_TREND_NA, CANDLE_MVMNT_986.24, LTP_9292.6, "
				+ "VWAP_null, DAY1_MOVE_828.75, DAY2_MOVE_596.24, CPR_5_96.25, CPR_15_96.25TRADABLE_STRENTH_-3.8600000000000003, "
				+ "DAY1_STRN_FACT_null, DAY2_STRN_FACT_null, NR4_RULE_false, SMALL_CANDLE_true,02:40:02, R, R, R, 986.24, "
				+ "<span style='color:red'><b>BUY-4.64,8.5</b></span>");
		bean = service.saveTrade(bean);
		NkpAlgoLogger.printWithNewLine("saveTradeOnEntry --> bean.getSymbol() - "+bean.getSymbol()+" -- bean.getItemId() - "+bean.getItemId());
		return bean;
	}
	
	public void updateTradeForTestData() {
		StrategyHighLowBean bean = new StrategyHighLowBean();
		bean.setItemId(999999999);
		bean.setTradedState(BUY_EXIT_PROFIT + EXIT_FORCE);
		bean.setExitedAt(2100.85);
		bean.setExitedAtDt(Calendar.getInstance().getTime());
		bean.setProfitLossAmt(2100d);
		bean.setPositiveMoveValue(1999.99);
		bean.setNegativeMoveValue(0d);

		service.updateTrade(bean.getTradedState(), bean.getExitedAt(), bean.getExitedAtDt(), bean.getProfitLossAmt(),
				bean.getPositiveMoveValue(), bean.getNegativeMoveValue(), 1);
	}
}
