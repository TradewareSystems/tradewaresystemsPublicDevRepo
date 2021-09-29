package nr4.sathvikashviktechtrader.stockmarket.helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.tradeware.clouddatabase.bean.Narrow7Bean;
import com.tradeware.clouddatabase.bean.SystemErrorBean;
import com.tradeware.clouddatabase.service.Narrow7Service;
import com.tradeware.clouddatabase.service.SystemErrorService;
import com.tradeware.stockmarket.bean.SystemErrorDataBean;

import nr4.sathvikashviktechtrader.stockmarket.bean.Narrow7DataBean;
import nr4.sathvikashviktechtrader.stockmarket.log.NkpAlgoLogger;
import nr4.sathvikashviktechtrader.stockmarket.tool.NR7AndUTCommonTool;
import nr4.sathvikashviktechtrader.stockmarket.util.Constants;

@Component
public class DatabaseHelper implements ApplicationContextAware, Constants {

	  private static ApplicationContext ac;

	  @Override
	  public void setApplicationContext(ApplicationContext ac) {
		  DatabaseHelper.ac = ac;
	  }

	private static DatabaseHelper singletonTool;

	public DatabaseHelper() {
		// service = new StrategyHighLowService();
	}

	public static DatabaseHelper getInstance() {
		if (null == singletonTool) {
			singletonTool = (DatabaseHelper) ac.getBean("databaseHelper");
		}
		return singletonTool;
	}

	@Autowired
	private Narrow7Service service;

	public Integer saveTradeOnEntry(Narrow7DataBean baseBean) {
		Narrow7Bean bean = getSymbolBeanToSave(baseBean);
		bean = service.save(bean);
		NkpAlgoLogger.printWithNewLine("saveTradeOnEntry --> bean.getSymbol() - " + bean.getSymbol()
				+ " -- bean.getItemId() - " + bean.getItemId());
		return bean.getItemId();
	}

	public void updateTrade(Narrow7DataBean bean) {
		NkpAlgoLogger.printWithNewLine("updateTrade --> bean.getStockName() - " + bean.getSymbolName()
				+ " -- bean.getStockId() -- " + bean.getSymbolId() + " --bean.getProfitLossAmt() -- "
				+ bean.getProfitLossAmt().doubleValue());
		service.updateTrade(bean.getTradedState(), bean.getSellerAt(), bean.getTradedOutAt(), bean.getProfitLossAmt(),
				bean.getPositiveDirectionProfit(), bean.getNegativeDirectionLoss(),
				Integer.valueOf(bean.getSymbolId()));
	}

	public List<Narrow7DataBean> findAllTrades() {
		List<Narrow7Bean> baseList = service.findAll();
		List<Narrow7DataBean> reportList = new ArrayList<Narrow7DataBean>(baseList.size());
		for (Narrow7Bean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}

	public List<Narrow7DataBean> getRunningTrades() {
		Date tradeDay = NR7AndUTCommonTool.getInstance().getTradeDateForReport();
		List<Narrow7Bean> baseList = service.getRunningTrades(tradeDay);
		List<Narrow7DataBean> reportList = new ArrayList<Narrow7DataBean>(baseList.size());
		for (Narrow7Bean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}

	public List<Narrow7DataBean> findAllByTradedDateStampOrderByTradedAtDtDesc(Date tradedDateStamp) {
		Date tradeDay = NR7AndUTCommonTool.getInstance().getTradeDateForReport();
		List<Narrow7Bean> baseList = service.findAllByTradedDateStampOrderByTradedAtDtDesc(tradeDay);
		List<Narrow7DataBean> reportList = new ArrayList<Narrow7DataBean>(baseList.size());
		for (Narrow7Bean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}

	public List<Narrow7DataBean> findAllByTradedDateStampOrderBySymbolAscTradedAtDtAsc() {
		Date tradeDay = NR7AndUTCommonTool.getInstance().getTradeDateForReport();
		List<Narrow7Bean> baseList = service.findAllByTradedDateStampOrderByTradedAtDtDesc(tradeDay);
		List<Narrow7DataBean> reportList = new ArrayList<Narrow7DataBean>(baseList.size());
		for (Narrow7Bean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	
	public List<Narrow7DataBean> findAllByTradedDateStampFilterItemsOrderByTradedAtDtDesc() {
		Date tradeDay = NR7AndUTCommonTool.getInstance().getTradeDateForReport();
		List<Narrow7Bean> baseList = service.findAllByTradedDateStampFilterItemsOrderByTradedAtDtDesc(tradeDay);
		List<Narrow7DataBean> reportList = new ArrayList<Narrow7DataBean>(baseList.size());
		for (Narrow7Bean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}

	public List<Narrow7DataBean> findAllByTempCustomTradingRuleIndOrderByTradedAtDt(Boolean tempCustomTradingRuleInd) {
		List<Narrow7Bean> baseList = service
				.findAllByTempCustomTradingRuleIndOrderByTradedAtDt(tempCustomTradingRuleInd);
		List<Narrow7DataBean> reportList = new ArrayList<Narrow7DataBean>(baseList.size());
		for (Narrow7Bean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}

	public List<Narrow7DataBean> findAllByTempCustomTradingRuleIndAndTradedDateStampOrderByTradedAtDt(
			Boolean tempCustomTradingRuleInd) {
		Date tradeDay = NR7AndUTCommonTool.getInstance().getTradeDateForReport();
		List<Narrow7Bean> baseList = service
				.findAllByTempCustomTradingRuleIndAndTradedDateStampOrderByTradedAtDt(tempCustomTradingRuleInd, tradeDay);
		List<Narrow7DataBean> reportList = new ArrayList<Narrow7DataBean>(baseList.size());
		for (Narrow7Bean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}
	
	public List<Narrow7DataBean> findAllByTradedDateStampAndOhlcStateNotNullOrderBySymbolAscTradedAtDtAsc() {
		Date tradeDay = NR7AndUTCommonTool.getInstance().getTradeDateForReport();
		List<Narrow7Bean> baseList = service
				.findAllByTradedDateStampAndOhlcStateNotNullOrderBySymbolAscTradedAtDtAsc(tradeDay);
		List<Narrow7DataBean> reportList = new ArrayList<Narrow7DataBean>(baseList.size());
		for (Narrow7Bean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}

	public List<Narrow7DataBean> findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtAsc() {
		Date tradeDay = NR7AndUTCommonTool.getInstance().getTradeDateForReport();
		List<Narrow7Bean> baseList = service
				.findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtAsc(
						tradeDay);
		List<Narrow7DataBean> reportList = new ArrayList<Narrow7DataBean>(baseList.size());
		for (Narrow7Bean rowBean : baseList) {
			reportList.add(mapBeanData(rowBean));
		}
		return reportList;
	}

	public Narrow7Bean getSymbolBeanToSave(Narrow7DataBean baseBean) {
		Narrow7Bean bean = new Narrow7Bean();
		bean.setSymbol(baseBean.getSymbolName());
		bean.setLotSize(baseBean.getLotSize());
		bean.setBuyAtVal(baseBean.getBuyAtVal());
		bean.setSellAtVal(baseBean.getSellAtVal());
		bean.setTradableState(baseBean.getTradableState());
		bean.setTradedState(baseBean.getTradedState());
		bean.setTradedAtVal(baseBean.getTradedVal());
		bean.setTradedAtDt(baseBean.getTradedInAt());
		bean.setBuySellDiff(baseBean.getBuyerSellerDiff());
		bean.setVolumes(baseBean.getVolumes());
		bean.setTradableStrength(baseBean.getTradableStrength());
		bean.setStrengthTradableState(baseBean.getStrengthTradableState());
		bean.setCandle1SizeAmt(baseBean.getCandle1SizeAmt());
		bean.setCandle2SizeAmt(baseBean.getCandle2SizeAmt());
		bean.setCandle3SizeAmt(baseBean.getCandle3SizeAmt());
		bean.setCandle4SizeAmt(baseBean.getCandle4SizeAmt());
		bean.setCandle1HighMinusClose(baseBean.getCandle1HighMinusClose());
		bean.setCandle1CloseMinusLow(baseBean.getCandle1CloseMinusLow());
		bean.setCandle2HighMinusClose(baseBean.getCandle2HighMinusClose());
		bean.setCandle2CloseMinusLow(baseBean.getCandle2CloseMinusLow());
		bean.setCandle3HighMinusClose(baseBean.getCandle3HighMinusClose());
		bean.setCandle3CloseMinusLow(baseBean.getCandle3CloseMinusLow());
		bean.setCandle4HighMinusClose(baseBean.getCandle4HighMinusClose());
		bean.setCandle4CloseMinusLow(baseBean.getCandle4CloseMinusLow());
		bean.setCandle1Type(baseBean.getCandle1Type());
		bean.setCandle2Type(baseBean.getCandle2Type());
		bean.setCandle3Type(baseBean.getCandle3Type());
		bean.setCandle4Type(baseBean.getCandle4Type());
		bean.setTempCustomTradingRuleInd(baseBean.isTempCustomTradingRuleInd());
		bean.setOhlcState(baseBean.getOhlcState());
		//ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(TIME_ZONE)).atZone(ZoneId.of(TIME_ZONE));
		//bean.setTradedDateStamp(Date.from(zdt.toInstant()));
		bean.setTradedDateStamp(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		bean.setOiInfo(baseBean.getOiInfo());
		return bean;
	}

	private Narrow7DataBean mapBeanData(Narrow7Bean rowBean) {
		Narrow7DataBean bean = new Narrow7DataBean(rowBean.getLotSize(), rowBean.getSymbol());
		bean.setSymbolId(rowBean.getItemId().toString());
		bean.setSymbolName(rowBean.getSymbol());
		bean.setLotSize(rowBean.getLotSize());
		bean.setBuyOrSellAt(rowBean.getBuyAtVal());
		bean.setStopLoss(rowBean.getSellAtVal());
		bean.setTradableState(rowBean.getTradableState());
		bean.setTradedState(rowBean.getTradedState());
		bean.setTradedVal(rowBean.getTradedAtVal());
		//bean.setExitedAtVal (rowBean.getExitedAtVal());
		bean.setTradedInAt(rowBean.getTradedAtDt());
		bean.setTradedOutAt(rowBean.getExitedAtDt());
		bean.setProfitLossAmt(rowBean.getProfitLossAmt());
		bean.setBuyerSellerDiff(rowBean.getBuySellDiff());
		bean.setPositiveDirectionProfit(rowBean.getPositiveMoveValue());
		bean.setNegativeDirectionLoss(rowBean.getNegativeMoveValue());
		bean.setVolumes(rowBean.getVolumes());
		bean.setTradableStrength(rowBean.getTradableStrength());
		bean.setStrengthTradableState(rowBean.getStrengthTradableState());
		bean.setCandle1SizeAmt(rowBean.getCandle1SizeAmt());
		bean.setCandle2SizeAmt(rowBean.getCandle2SizeAmt());
		bean.setCandle3SizeAmt(rowBean.getCandle3SizeAmt());
		bean.setCandle4SizeAmt(rowBean.getCandle4SizeAmt());
		bean.setCandle1HighMinusClose(rowBean.getCandle1HighMinusClose());
		bean.setCandle1CloseMinusLow(rowBean.getCandle1CloseMinusLow());
		bean.setCandle2HighMinusClose(rowBean.getCandle2HighMinusClose());
		bean.setCandle2CloseMinusLow(rowBean.getCandle2CloseMinusLow());
		bean.setCandle3HighMinusClose(rowBean.getCandle3HighMinusClose());
		bean.setCandle3CloseMinusLow(rowBean.getCandle3CloseMinusLow());
		bean.setCandle4HighMinusClose(rowBean.getCandle4HighMinusClose());
		bean.setCandle4CloseMinusLow(rowBean.getCandle4CloseMinusLow());
		bean.setCandle1Type(rowBean.getCandle1Type());
		bean.setCandle2Type(rowBean.getCandle2Type());
		bean.setCandle3Type(rowBean.getCandle3Type());
		bean.setCandle4Type(rowBean.getCandle4Type());
		bean.setTempCustomTradingRuleInd(rowBean.getTempCustomTradingRuleInd());
		bean.setOhlcState(rowBean.getOhlcState());
		// bean.setTradedDateStamp(rowBean.getTradedDateStamp());
		bean.setOiInfo(rowBean.getOiInfo());
		
		prepareTradeInfo(bean);
		return bean;
	}

	private static StringBuffer sb = new StringBuffer();
	private static StringBuffer sbExit = new StringBuffer();

	private void prepareTradeInfo(Narrow7DataBean bean) {
		clearBuffer();
		if (null != bean.getTradedState()) {
			if (bean.getTradedState().startsWith(BUY)) {
				sb.append(NR7AndUTCommonTool.getInstance().getCurrentTime(bean.getTradedInAt()))
						.append(BUY_ACTIVATE).append(bean.getTradedVal());
				// if (bean.getTradedState().equals(BUY_EXIT_PROFIT)) {
				if (bean.getTradedState().contains(BUY_EXIT_PROFIT)) {
					sbExit.append(NR7AndUTCommonTool.getInstance().getCurrentTime(bean.getTradedOutAt()))
							.append(BUY_TARGET).append(bean.getBuyerAt()).append(WITH_PROFIT)
							.append(bean.getProfitLossAmt());
					// } else if (bean.getTradedState().equals(BUY_EXIT_LOSS)) {
				} else if (bean.getTradedState().contains(BUY_EXIT_LOSS)) {
					sbExit.append(NR7AndUTCommonTool.getInstance().getCurrentTime(bean.getTradedOutAt()))
							.append(CLOSED_WITH_LOSE).append(bean.getBuyerAt()).append(WITH_LOSS)
							.append(bean.getProfitLossAmt());
				}
			} else if (bean.getTradedState().startsWith(SELL)) {
				sb.append(NR7AndUTCommonTool.getInstance().getCurrentTime(bean.getTradedInAt()))
						.append(SELL_ACTIVATE).append(bean.getTradedVal());
				// if (bean.getTradedState().equals(SELL_EXIT_PROFIT)) {
				if (bean.getTradedState().contains(SELL_EXIT_PROFIT)) {
					sbExit.append(NR7AndUTCommonTool.getInstance().getCurrentTime(bean.getTradedOutAt()))
							.append(SELL_TARGET).append(bean.getBuyerAt()).append(WITH_PROFIT)
							.append(bean.getProfitLossAmt());
					// } else if (bean.getTradedState().equals(SELL_EXIT_LOSS)) {
				} else if (bean.getTradedState().contains(SELL_EXIT_LOSS)) {
					sbExit.append(NR7AndUTCommonTool.getInstance().getCurrentTime(bean.getTradedOutAt()))
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
	
	//System Error start
	@Autowired
	private SystemErrorService systemErrorService;
	
	public void saveSystemErrorDataBean(SystemErrorDataBean systemErrorDataBean) {
		try {
			SystemErrorBean systemErrorBean = mapSystemErrorDataBeanToSystemErrorBean(systemErrorDataBean);
			systemErrorService.save(systemErrorBean);
		} catch (Exception e) {
		}
	}

	public List<SystemErrorDataBean> findAllByDateStampOrderByDateTimeStampDesc() {
		List<SystemErrorDataBean> systemErrorDataBeanList = null;
		try {
			Date tradeReportDate = NR7AndUTCommonTool.getInstance().getTradeDateForReport();
			List<SystemErrorBean> systemErrorBeanList = systemErrorService
					.findAllByDateStampOrderByDateTimeStampDesc(tradeReportDate);
			systemErrorDataBeanList = new ArrayList<SystemErrorDataBean>(systemErrorBeanList.size());
			for (SystemErrorBean systemErrorBean : systemErrorBeanList) {
				SystemErrorDataBean dataBean = mapSystemErrorBeanToSystemErrorDataBean(systemErrorBean);
				systemErrorDataBeanList.add(dataBean);
			}
		} catch (Exception e) {
			systemErrorDataBeanList = new ArrayList<SystemErrorDataBean>(0);
		}
		return systemErrorDataBeanList;
	}
	public static SystemErrorDataBean mapSystemErrorBeanToSystemErrorDataBean(SystemErrorBean systemErrorBean) {
		SystemErrorDataBean bean = new SystemErrorDataBean();
		bean.setErrorId(systemErrorBean.getErrorId());
		bean.setClassName(systemErrorBean.getClassName());
		bean.setMethodName(systemErrorBean.getMethodName());
		bean.setErrorMessage(systemErrorBean.getErrorMessage());
		bean.setErrorType(systemErrorBean.getErrorType());
		bean.setErrorSevirity(systemErrorBean.getErrorSevirity());
		bean.setErrorInd(systemErrorBean.getErrorInd());
		bean.setDateTimeStamp(systemErrorBean.getDateTimeStamp());
		//bean.setDateTimeStampStr(systemErrorBean.getDateTimeStampStr());
		bean.setDateStamp(systemErrorBean.getDateStamp());
		return bean;
	}

	public static SystemErrorBean mapSystemErrorDataBeanToSystemErrorBean(SystemErrorDataBean systemErrorDataBean) {
		SystemErrorBean bean = new SystemErrorBean();
		bean.setErrorId(systemErrorDataBean.getErrorId());
		bean.setClassName(systemErrorDataBean.getClassName());
		bean.setMethodName(systemErrorDataBean.getMethodName());
		bean.setErrorMessage(systemErrorDataBean.getErrorMessage());
		bean.setErrorType(systemErrorDataBean.getErrorType());
		bean.setErrorSevirity(systemErrorDataBean.getErrorSevirity());
		bean.setErrorInd(systemErrorDataBean.getErrorInd());

				//bean.setDateTimeStampStr(NR7AndUTCommonTool.getInstance().getCurrentDateTime());
		//bean.setDateTimeStamp(NR7AndUTCommonTool.getInstance().getCurrentDateTimeAsDate());
		//ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(TIME_ZONE)).atZone(ZoneId.of(TIME_ZONE));
		//zdt.format(DateTimeFormatter.ofPattern(DATE_PATTERN_HIST_DATA));
		//bean.setDateStamp(Date.from(zdt.toInstant()));
		bean.setDateTimeStamp(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		bean.setDateStamp(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		return bean;
	}
	//System error end
	
}