package com.tradeware.stockmarket.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.clouddatabase.bean.OptionChainInfoBean;
import com.tradeware.clouddatabase.bean.OptionLiveTradeMainBean;
import com.tradeware.clouddatabase.bean.ProfitLossSummaryBean;
import com.tradeware.clouddatabase.bean.StrategyOrbBean;
import com.tradeware.clouddatabase.bean.StrategyOrbMonthlyReportBean;
import com.tradeware.clouddatabase.bean.SymbolBean;
import com.tradeware.clouddatabase.bean.SystemErrorBean;
import com.tradeware.clouddatabase.bean.UserBean;
import com.tradeware.clouddatabase.engine.NSEIndiaToolOptionChainTool;
import com.tradeware.clouddatabase.service.OptionChainInfoService;
import com.tradeware.clouddatabase.service.OptionStrategyTradeService;
import com.tradeware.clouddatabase.service.ProfitLossSummaryService;
import com.tradeware.clouddatabase.service.StrategyOrbMonthlyReportService;
import com.tradeware.clouddatabase.service.StrategyOrbService;
import com.tradeware.clouddatabase.service.SymbolService;
import com.tradeware.clouddatabase.service.SystemErrorService;
import com.tradeware.clouddatabase.service.UserService;
import com.tradeware.stockmarket.bean.OptionChainDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeMainDataBean;
import com.tradeware.stockmarket.bean.ProfitLossSummaryDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbMonthlyReportDataBean;
import com.tradeware.stockmarket.bean.SymbolDataBean;
import com.tradeware.stockmarket.bean.SystemErrorDataBean;
import com.tradeware.stockmarket.bean.UserDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.tool.TelegramMessageTool;
import com.tradeware.stockmarket.tool.TradewareTool;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON) // SCOPE_PROTOTYPE)
public class DatabaseHelper extends AbstractDatabaseHelper implements ApplicationContextAware, Constants {

	private static ApplicationContext ac;

	@Override
	public void setApplicationContext(ApplicationContext ac) {
		DatabaseHelper.ac = ac;
	}

	private static DatabaseHelper singletonTool;

	public DatabaseHelper() {
	}

	public static DatabaseHelper getInstance() {
		if (null == singletonTool) {
			singletonTool = (DatabaseHelper) ac.getBean("databaseHelper");
		}
		return singletonTool;
	}

	@Autowired
	private UserService userService;

	@Autowired
	private SymbolService symbolService;

	@Autowired
	private SystemErrorService systemErrorService;

	@Autowired
	private StrategyOrbService strategyOrbService;

	@Autowired
	private ProfitLossSummaryService profitLossSummaryService;
	
	@Autowired
	private StrategyOrbMonthlyReportService monthlyReportService;
	
	/*
	 * @Autowired private ActivityAuditService activityAuditService;
	 * 
	 * @Autowired private OptionChainInfoService optionChainInfoService;
	 */

	@Autowired
	private TradewareTool tradewareTool;

	@Autowired
	private DatabaseMapper databaseMapper;

	public UserDataBean getUserDataBean(String userId) {
		UserDataBean userDataBean = null;
		try {
			UserBean userBean = userService.findByUserId(userId);
			if (null != userBean) {
				userDataBean = databaseMapper.mapUserBeanToUserDataBean(userBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_GET_USER_DATA_BEAN, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return userDataBean;
	}

	public UserDataBean getUserDataBeanByEmail(String email) {
		UserDataBean userDataBean = null;
		try {
			UserBean userBean = userService.findByEmail(email);
			if (null != userBean) {
				userDataBean = databaseMapper.mapUserBeanToUserDataBean(userBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_GET_USER_DATA_BEAN_BY_EMAIL, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return userDataBean;
	}

	public UserDataBean getUserDataBeanByConfirmationToken(String confirmationToken) {
		UserDataBean userDataBean = null;
		try {
			UserBean userBean = userService.findByConfirmationToken(confirmationToken);
			if (null != userBean) {
				userDataBean = databaseMapper.mapUserBeanToUserDataBean(userBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_GET_USER_DATA_BEAN_BY_CONFIRMATION_TOKEN, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return userDataBean;
	}

	public void saveUserDataBean(UserDataBean userDataBean) {
		try {
			UserBean userBean = databaseMapper.mapUserDataBeanToUserBean(userDataBean);
			userService.save(userBean);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_SAVE_USER_DATA_BEAN, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
	}

	// system errors start
	private ConcurrentHashMap<String, SystemErrorBean> errorMap = new ConcurrentHashMap<String, SystemErrorBean>();
	private StringBuffer errorKeyBuffer = new StringBuffer();
	private void clearBuffer() {
		if (errorKeyBuffer.length() > 0) {
			errorKeyBuffer.delete(0, errorKeyBuffer.length());
		}
	}

	public void saveSystemErrorDataBean(SystemErrorDataBean systemErrorDataBean) {
		try {
			clearBuffer();
			SystemErrorBean systemErrorBean = null;
			errorKeyBuffer.append(systemErrorDataBean.getClassName()).append(systemErrorDataBean.getMethodName())
					.append(systemErrorDataBean.getErrorType()).append(systemErrorDataBean.getErrorSevirity());

			if (Constants.ERROR_JUST_INFO.equals(systemErrorDataBean.getErrorSevirity())
					|| null == errorMap.get(errorKeyBuffer.toString())) {
				systemErrorBean = databaseMapper.mapSystemErrorDataBeanToSystemErrorBean(systemErrorDataBean);
				systemErrorBean.setErrorCount(1);
				systemErrorBean = systemErrorService.save(systemErrorBean);
				if (!Constants.ERROR_JUST_INFO.equals(systemErrorDataBean.getErrorSevirity())) {
					errorMap.put(errorKeyBuffer.toString(), systemErrorBean);
				}
			} else {
				systemErrorBean = errorMap.get(errorKeyBuffer.toString());
				systemErrorBean.setErrorCount(
						null != systemErrorBean.getErrorCount() ? systemErrorBean.getErrorCount() + 1 : 1);
				systemErrorBean = systemErrorService.save(systemErrorBean);
				errorMap.replace(errorKeyBuffer.toString(), systemErrorBean);
			}
		} catch (Throwable e) {
			/*
			 * TradewareLogger.saveFatalErrorLog(Constants.CLASS_SYSTEMERRORSERVICE,
			 * Constants.METHOD_SAVESYSTEMERRORDATABEAN, e, Constants.ERROR_TYPE_EXCEPTION);
			 */
			// tempSendErrorToTelegram(e);
			e.printStackTrace();
		}
	}

	private void tempSendErrorToTelegram(Throwable e) {
		try {
			String message = TradewareLogger.getExceptionStackTraceAsStrting(e);
			if (null != message) {
				if (message.length() > 253) {
					message = message.substring(0, 253);
				}
			} else {
				message = "NULL MESSAGE";
			}
			TelegramMessageTool.getInstance().sendTelegramMessage(message);
		} catch (Throwable e1) {
			// Do nothng
		}
	}

	public List<SystemErrorDataBean> findAllByDateStampOrderByDateTimeStampDesc() {
		List<SystemErrorDataBean> systemErrorDataBeanList = null;
		try {
			List<SystemErrorBean> systemErrorBeanList = systemErrorService
					.findAllByDateStampOrderByDateTimeStampDesc(tradewareTool.getTradeDateForReport());
			systemErrorDataBeanList = new ArrayList<SystemErrorDataBean>(systemErrorBeanList.size());
			for (SystemErrorBean systemErrorBean : systemErrorBeanList) {
				SystemErrorDataBean dataBean = databaseMapper.mapSystemErrorBeanToSystemErrorDataBean(systemErrorBean);
				systemErrorDataBeanList.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_SYSTEM_ERROR_SERVICE,
					Constants.METHOD_FIND_ALL_BY_DATE_STAMP_ORDER_BY_DATE_TIME_STAMP_DESC, e,
					Constants.ERROR_TYPE_EXCEPTION);

			// e.printStackTrace();
		}
		return systemErrorDataBeanList;
	}
	// system error end

	// Strategy orb start
	public List<StrategyOrbDataBean> getRunningTrades() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			List<StrategyOrbBean> baseList = strategyOrbService.getRunningTrades(tradeDay);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_GET_RUNNING_TRADES, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}

	public Integer saveTradeOnEntry(StrategyOrbDataBean baseBean) {
		Integer itemId = null;
		StrategyOrbBean bean = null;
		try {
			bean = databaseMapper.mapStrategyOrbDataBeanToStrategyOrbBean(baseBean);
			//System.out.println(bean.getSymbolName()+" --  "+bean.getPositiveMoveVal()+" --  "+bean.getNegativeMoveVal());
			bean = strategyOrbService.save(bean);
			itemId = bean.getItemId();
		} catch (Exception e) {
			TradewareLogger.sysoutPrintlnForLocalTest(baseBean.toString());
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_SAVE_TRADE_ON_ENTRY, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return itemId;
	}

	public void updateTrade(StrategyOrbDataBean bean) {
		try {
			strategyOrbService.updateTrade(bean.getTradedStateId(), bean.getExitedAtVal(), bean.getExitedAtDtTm(),
					bean.getProfitLossAmtVal(), bean.getPositiveDirectionProfit(), bean.getNegativeDirectionLoss(),
					bean.getPositiveDirectionLtp() != null ? bean.getPositiveDirectionLtp() : 0,
					bean.getNegativeDirectionLtp() != null ? bean.getNegativeDirectionLtp() : 0,
					bean.getStopLoss() != null ? bean.getStopLoss() : 0, Integer.valueOf(bean.getItemId()));
		} catch (Exception e) {
			System.out.println(bean);
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------------");
			e.printStackTrace();
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_UPDATE_TRADE, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
	}
	
	// 04-21-2021 start - afterSomeSuccess [04-27-2021]
	public void manualTradeClose(StrategyOrbDataBean bean) {
		try {
		strategyOrbService.manualTradeClose(bean.getManualTradeExitStateId(),
				bean.getManualExitedAtVal() != null ? bean.getManualExitedAtVal() : 0, bean.getManualExitedAtDtTm(),
				bean.getManualBookProfitLossAmtVal() != null ? bean.getManualBookProfitLossAmtVal() : 0,
				Integer.valueOf(bean.getItemId()));
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_TRADE_CLOSE, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
	}
	// 04-21-2021 end


	public void updatePositiveNegativeMoves(StrategyOrbDataBean bean) {
		try {
			System.out.println(bean.getSymbolName() + " --  " + bean.getPositiveDirectionProfit() + " --  "
					+ bean.getNegativeDirectionLoss() + " --  " + bean.getStopLoss());

			strategyOrbService.updatePositiveNegativeMoves(bean.getProfitLossAmtVal(),
					bean.getPositiveDirectionProfit(), bean.getNegativeDirectionLoss(),
					bean.getPositiveDirectionLtp() != null ? bean.getPositiveDirectionLtp() : 0,
					bean.getNegativeDirectionLtp() != null ? bean.getNegativeDirectionLtp() : 0,
					bean.getStopLoss() != null ? bean.getStopLoss() : 0, Integer.valueOf(bean.getItemId()));
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_UPDATE_POSITIVE_NEGATIVE_MOVES, e, Constants.ERROR_TYPE_EXCEPTION);
		}
	}

	public void updateAdujustTrade(StrategyOrbDataBean bean) {
		try {
			strategyOrbService.updateAdujustTrade(bean.getTradedLotCount(), bean.getTradedAtAvgVal(),
					bean.getTradedAtVal2(), bean.getTradedAtDtTm2(), bean.getTradedAtVal3(), bean.getTradedAtDtTm3(),
					bean.getTargetAmtVal2(), bean.getTargetAmtVal3(), Integer.valueOf(bean.getItemId()));
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_UPDATE_ADUJUST_TRADE, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
	}
	
	public List<StrategyOrbDataBean> findAllByTradedDateStampOrderBySymbolNameAscTradedAtDtTmAsc() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			List<StrategyOrbBean> baseList = strategyOrbService
					.findAllByTradedDateStampOrderBySymbolNameAscTradedAtDtTmAsc(tradeDay);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(
						databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_TRADED_DATE_STAMP_ORDER_BY_SYMBOL_NAME_ASC_TRADED_AT_DTTM_ASC, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	public List<StrategyOrbDataBean> findAllByTradedDateStampFilterItemsOrderByTradedAtDtTmDesc() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService
					.findAllByTradedDateStampFilterItemsOrderByTradedAtDtTmDesc(tradeDay, queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(
						databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_TRADED_DATE_STAMP_FILTER_ITEMS_ORDER_BY_TRADED_AT_DTTM_DESC, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByTradedDateStampAndOhlcStateEqTradableStateOrderBySymbolAscTradedAtDtTmAsc() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			List<StrategyOrbBean> baseList = strategyOrbService
					.findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtTmAsc(
							tradeDay);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_TRADED_DATE_STAMP_AND_OHLC_STATE_EQ_TRADABLE_STATE_ORDER_BY_SYMBOL_ASC_TRADED_AT_DT_TM_ASC,
					e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	//public List<StrategyOrbDataBean> findAllByStrongVwapAndVolumeRule() {
	public List<StrategyOrbDataBean> findAllByStrongVwapAndVolumeRule() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByStrongVwapAndVolumeRule(tradeDay,
					queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_STRONG_VWAP_AND_VOLUME_RULE, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	//public List<StrategyOrbDataBean> findAllByCustomRule1() {
	public List<StrategyOrbDataBean> findAllByTradingRule7TimesStrenth() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByTradingRule7TimesStrenth(tradeDay);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(
						databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_TRADING_RULE_7TIMES_STRENTH,
					e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	//public List<StrategyOrbDataBean> findAllByCustomRule2() {
	public List<StrategyOrbDataBean> findAllByTradingRuleOpenBtwnAvgHiLoClsAndVwap() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByTradingRuleOpenBtwnAvgHiLoClsAndVwap(tradeDay,
					queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(
						databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_TRADING_RULE_OPEN_BTWN_AVG_HI_LO_CLS_AND_VWAP,
					e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	//Day Level start
	public List<StrategyOrbDataBean> getRunningTradesDayLevelTrade() {
		Date tradeDay = tradewareTool.getTradeDateForReport();
		List<StrategyOrbBean> baseList = strategyOrbService.getRunningTradesDayLevelTrade(tradeDay);
		List<StrategyOrbDataBean> reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
		for (StrategyOrbBean rowBean : baseList) {
			reportList.add(
					databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
		}
		return reportList;
	}
	public List<StrategyOrbDataBean> findAllByTradedDateStampAndDayLevelTradeIndOrderBySymbolNameAscTradedAtDtTmDesc(
			Boolean dayLevelTradeInd) {
		Date tradeDay = tradewareTool.getTradeDateForReport();
		List<StrategyOrbBean> baseList = strategyOrbService
				.findAllByTradedDateStampAndDayLevelTradeIndOrderBySymbolNameAscTradedAtDtTmDesc(tradeDay, dayLevelTradeInd);
		List<StrategyOrbDataBean> reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
		for (StrategyOrbBean rowBean : baseList) {
			reportList.add(
					databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByTradedDateFilterAndDayLevelTradeindItemsOrderByTradedAtDtTmDesc(
			Boolean dayLevelTradeInd) {
		Date tradeDay = tradewareTool.getTradeDateForReport();
		Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
				? tradewareTool.getQueryCandleNumber()
				: 25;
		List<StrategyOrbBean> baseList = strategyOrbService
				.findAllByTradedDateFilterAndDayLevelTradeindItemsOrderByTradedAtDtTmDesc(tradeDay, queryCandleNumber);
		List<StrategyOrbDataBean> reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
		for (StrategyOrbBean rowBean : baseList) {
			reportList.add(
					databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByTradedDateAndOhlcStateNotNullAndDayLevelIndOrderBySymbolAscTradedAtDtTmAsc() {
		Date tradeDay = tradewareTool.getTradeDateForReport();
		List<StrategyOrbBean> baseList = strategyOrbService
				.findAllByTradedDateAndOhlcStateNotNullAndDayLevelIndOrderBySymbolAscTradedAtDtTmAsc(tradeDay);
		List<StrategyOrbDataBean> reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
		for (StrategyOrbBean rowBean : baseList) {
			reportList.add(
					databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
		}
		return reportList;
	}

	public List<StrategyOrbDataBean> findAllByTradedDateAndOhlcStateNotNullAndOhlcStateEqualToTradableStateAndDayLevelIndOrderBySymbolAscTradedAtDtTmAsc() {
		Date tradeDay = tradewareTool.getTradeDateForReport();
		List<StrategyOrbBean> baseList = strategyOrbService
				.findAllByTradedDateAndOhlcStateNotNullAndOhlcStateEqualToTradableStateAndDayLevelIndOrderBySymbolAscTradedAtDtTmAsc(
						tradeDay);
		List<StrategyOrbDataBean> reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
		for (StrategyOrbBean rowBean : baseList) {
			reportList.add(
					databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
		}
		return reportList;
	}
	//Day Level end
	
	public List<StrategyOrbDataBean> findAllNIFTYorBANKNIFTY() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllNIFTYorBANKNIFTY(tradeDay,
					queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(
						databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_NIFTY_OR_BANKNIFTY, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	
	//04-10-2021 start
	
	public List<StrategyOrbDataBean> findAllByStrongVolumePressureRule() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByStrongVolumePressureRule(tradeDay,
					queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_STRONG_VOLUME_PRESSURE_RULE, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByStrongVolumePressureRuleMatch() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByStrongVolumePressureRuleMatch(tradeDay,
					queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_STRONG_VOLUME_PRESSURE_RULE_MATCH, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	
	public List<StrategyOrbDataBean> findAllBySmaVwapLevel2Rule() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllBySmaVwapLevel2Rule(tradeDay,
					queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_SMA_VWAP_LEVEL_2_RULE, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllBySmaVwapLevel2PlusRule() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllBySmaVwapLevel2PlusRule(tradeDay,
					queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_SMA_VWAP_LEVEL_2_PLUS_RULE, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	//04-10-2021 end
	
	// phase 4 start 04-23-2021 start
	public List<StrategyOrbDataBean> findAllByInitProfitableProdRule() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByInitProfitableProdRule(tradeDay,
					queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_INIT_PROFITABLE_PROD_RULE, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByInitProfitableFilterProdRule() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Date tradedCloseTimeBeffore = tradewareTool.getTradedCloseTimeBeffore();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByInitProfitableFilterProdRule(tradeDay,
					tradedCloseTimeBeffore, queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_INIT_PROFITABLE_FILTER_PROD_RULE, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public Double checkTodayTradingNotForceClosedByCrossMaxLossLimit() {
		Double maxDayLoss = 0d;
		try {
			maxDayLoss = strategyOrbService.checkTodayTradingNotForceClosedByCrossMaxLossLimit();
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_CHECK_TODAY_TRADING_NOT_FORCE_CLOSED_BY_CROSS_MAX_LOSS_LIMIT, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return maxDayLoss;
	}
	// phase 4 end 04-23-2021
	
	// Phase 4 :: 05-09-2021 start - afterSomeSuccess
	public List<StrategyOrbDataBean> findAllBySmaVwapRuleTrades() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllBySmaVwapRuleTrades(tradeDay,
					 queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_INIT_PROFITABLE_FILTER_PROD_RULE, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByForwardEngulfingRuleTrades() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByForwardEngulfingRuleTrades(tradeDay,
					 queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_INIT_PROFITABLE_FILTER_PROD_RULE, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	// Phase 4 :: 05-09-2021 end - afterSomeSuccess
	
	public List<StrategyOrbDataBean> findAllByVolumeStrengthTrades() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByVolumeStrengthTrades(tradeDay,
					 queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_VOLUME_STRENGTH_TRADES, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByStochasticVolumeStrengthTrades() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByStochasticVolumeStrengthTrades(tradeDay,
					 queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_STOCHASTIC_VOLUME_STRENGTH_TRADES, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByStrongStochasticVolumeStrengthTrades() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByStrongStochasticVolumeStrengthTrades(tradeDay,
					 queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_STRONG_STOCHASTIC_VOLUME_STRENGTH_TRADES, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByStochasticRule1Trades() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByStochasticRule1Trades(tradeDay,
					 queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_STOCHASTIC_RULE1_TRADES, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByStochasticRule2Trades() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByStochasticRule2Trades(tradeDay,
					 queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_STOCHASTIC_RULE2_TRADES, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByStochasticStrongRule3Trades() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByStochasticStrongRule3Trades(tradeDay,
					 queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_STOCHASTIC_STRONG_RULE3_TRADES, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByStochasticBasicRuleTrades() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByStochasticBasicRuleTrades(tradeDay,
					 queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_STOCHASTIC_BASIC_RULE_TRADES, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbDataBean> findAllByProfiitTrades() {
		List<StrategyOrbDataBean> reportList = null;
		try {
			Date tradeDay = tradewareTool.getTradeDateForReport();
			Integer queryCandleNumber = (0 != tradewareTool.getQueryCandleNumber())
					? tradewareTool.getQueryCandleNumber()
					: 25;
			List<StrategyOrbBean> baseList = strategyOrbService.findAllByProfiitTrades(tradeDay,
					 queryCandleNumber);
			reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
			for (StrategyOrbBean rowBean : baseList) {
				reportList.add(databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_BY_PROFIIT_TRADES, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	
	// Strategy orb END
	
	
	
	
	
	
	
	
	//Sysmbol start
	
	public SymbolDataBean findBySymbolId(String symbolId) {
		SymbolDataBean dataBean = null;
		try {
			SymbolBean symbolBean = symbolService.findBySymbolId(symbolId);
			dataBean = DatabaseMapper.mapSymbolBeanToSymbolDataBean(symbolBean);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_FIND_BY_SYMBOL_ID, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return dataBean;
	}
	
	public List<SymbolDataBean> findAllValidSymbols() {
		List<SymbolDataBean> dataBeans = null;
		try {
			List<SymbolBean> symbols = symbolService.findAllByValidIndAndIndexIndOrderBySymbolId(Boolean.TRUE,
					Boolean.FALSE);
			dataBeans = new ArrayList<SymbolDataBean>(symbols.size());
			for (SymbolBean symbolBean : symbols) {
				SymbolDataBean dataBean = DatabaseMapper.mapSymbolBeanToSymbolDataBean(symbolBean);
				dataBeans.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_FIND_ALL_VALID_SYMBOLS, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return dataBeans;
	}

	public List<SymbolDataBean> findAllValidIndexSymbols() {
		List<SymbolDataBean> dataBeans = null;
		try {
			List<SymbolBean> symbols = symbolService.findAllByValidIndAndIndexIndOrderBySymbolId(Boolean.FALSE,
					Boolean.TRUE);
			dataBeans = new ArrayList<SymbolDataBean>(symbols.size());
			for (SymbolBean symbolBean : symbols) {
				SymbolDataBean dataBean = DatabaseMapper.mapSymbolBeanToSymbolDataBean(symbolBean);
				dataBeans.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_FIND_ALL_VALID_INDEX_SYMBOLS,
					e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return dataBeans;
	}
	
	public List<SymbolDataBean> findAllSymbols() {
		List<SymbolDataBean> dataBeans = null;
		try {
			List<SymbolBean> symbols = symbolService.findAll();
			dataBeans = new ArrayList<SymbolDataBean>(symbols.size());
			for (SymbolBean symbolBean : symbols) {
				SymbolDataBean dataBean = DatabaseMapper.mapSymbolBeanToSymbolDataBean(symbolBean);
				dataBeans.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_FIND_ALL_SYMBOLS,
					e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return dataBeans;
	}
	
	public List<SymbolDataBean> findAllOrderByClose200SmaTradableRatio() {
		List<SymbolDataBean> dataBeans = null;
		try {
			List<SymbolBean> symbols = symbolService.findAllOrderByClose200SmaTradableRatio();
			dataBeans = new ArrayList<SymbolDataBean>(symbols.size());
			for (SymbolBean symbolBean : symbols) {
				SymbolDataBean dataBean = DatabaseMapper.mapSymbolBeanToSymbolDataBean(symbolBean);
				dataBeans.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_ALL_ORDER_BY_CLOSE_200_SMA_TRADABLE_RATIO, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return dataBeans;
	}
	
	public List<SymbolDataBean> findAllOrderByYrHiLoNearPerDesc() {
		List<SymbolDataBean> dataBeans = null;
		try {
			List<SymbolBean> symbols = symbolService.findAllOrderByYrHiLoNearPerDesc();
			dataBeans = new ArrayList<SymbolDataBean>(symbols.size());
			for (SymbolBean symbolBean : symbols) {
				SymbolDataBean dataBean = DatabaseMapper.mapSymbolBeanToSymbolDataBean(symbolBean);
				dataBeans.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_FIND_ALL_SYMBOLS,
					e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return dataBeans;
	}
	
	public List<SymbolDataBean> findAllByFnoIndAndValidIndOrderBySymbolId(Boolean fnoInd, Boolean validInd) {
		List<SymbolDataBean> dataBeans = null;
		try {
			List<SymbolBean> symbols = symbolService.findAllByFnoIndAndValidIndOrderBySymbolId(fnoInd, validInd);
			dataBeans = new ArrayList<SymbolDataBean>(symbols.size());
			for (SymbolBean symbolBean : symbols) {
				SymbolDataBean dataBean = DatabaseMapper.mapSymbolBeanToSymbolDataBean(symbolBean);
				dataBeans.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FINDALLBYFNOINDANDVALIDINDORDERBYSYMBOLID, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return dataBeans;
	}
	
	
	public List<SymbolDataBean> findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId(Boolean fnoInd,
			Boolean validInd, Integer categoryFilter) {
		List<SymbolDataBean> dataBeans = null;
		try {
			List<SymbolBean> symbols = symbolService.findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId(fnoInd,
					validInd, categoryFilter);
			dataBeans = new ArrayList<SymbolDataBean>(symbols.size());
			for (SymbolBean symbolBean : symbols) {
				SymbolDataBean dataBean = DatabaseMapper.mapSymbolBeanToSymbolDataBean(symbolBean);
				dataBeans.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_FINDALLBYFNOINDANDVALIDINDANDCATEGORYFILTERORDERBYSYMBOLID, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return dataBeans;
	}
	public void saveAllSymbols(List<SymbolDataBean> dataBeanList) {
		try {
			List<SymbolBean> symbolBeanList = new ArrayList<SymbolBean>(dataBeanList.size());
			for (SymbolDataBean dataBean : dataBeanList) {
				SymbolBean symbolBean = DatabaseMapper.mapSymbolDataBeanToSymbolBean(dataBean);
				symbolBeanList.add(symbolBean);
			}
			symbolService.saveAll(symbolBeanList);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_SAVE_ALL_SYMBOLS, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
	}
	//symbol end
	
	// profit loss summary start
	public void saveAllProfitLossSummaryList(List<ProfitLossSummaryDataBean> dataBeanList) {
		try {
			List<ProfitLossSummaryBean> profLossSummList = new ArrayList<ProfitLossSummaryBean>(dataBeanList.size());
			for (ProfitLossSummaryDataBean dataBean : dataBeanList) {
				ProfitLossSummaryBean profLossSummBean = DatabaseMapper
						.mapProfitLossSummaryDataBeanToProfitLossSummaryBean(dataBean);
				profLossSummList.add(profLossSummBean);
			}
			profLossSummList = profitLossSummaryService.saveAll(profLossSummList);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_PROFIT_LOSS_SUMMARY_SERVICE,
					Constants.METHOD_SAVE_ALL_PROFIT_LOSS_SUMMARY_LIST, e, Constants.ERROR_TYPE_EXCEPTION);
		}
	}

	public void saveProfitLossSummaryBean(ProfitLossSummaryDataBean profLossSummDataBean) {
		try {
			ProfitLossSummaryBean profLossSummBean = DatabaseMapper
					.mapProfitLossSummaryDataBeanToProfitLossSummaryBean(profLossSummDataBean);
			profitLossSummaryService.save(profLossSummBean);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_PROFIT_LOSS_SUMMARY_SERVICE,
					Constants.METHOD_SAVE_PROFIT_LOSS_SUMMARY_BEAN, e, Constants.ERROR_TYPE_EXCEPTION);
		}
	}

	public List<ProfitLossSummaryDataBean> findAllProfLossSummByDate() {
		List<ProfitLossSummaryDataBean> profLossSummDataBeanList = null;
		try {
			List<ProfitLossSummaryBean> profLossSummBeanList = profitLossSummaryService
					.findAllByDateStampOrderByDateStampDesc(tradewareTool.getTradeDateForReport());
			profLossSummDataBeanList = new ArrayList<ProfitLossSummaryDataBean>(profLossSummBeanList.size());
			for (ProfitLossSummaryBean profLossSummBean : profLossSummBeanList) {
				ProfitLossSummaryDataBean dataBean = DatabaseMapper
						.mapProfitLossSummaryBeanToProfitLossSummaryDataBean(profLossSummBean);
				profLossSummDataBeanList.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_PROFIT_LOSS_SUMMARY_SERVICE,
					Constants.METHOD_FIND_ALL_PROF_LOSS_SUMM_BY_DATE, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return profLossSummDataBeanList;
	}
	// profit loss summary end
	
	// Monthly reports start
	public List<StrategyOrbMonthlyReportDataBean> findAllMonthlyReportByForwardEngulfingRuleTrades() {
		List<StrategyOrbMonthlyReportDataBean> reportList = null;
		try {
			List<StrategyOrbMonthlyReportBean> baseList = monthlyReportService
					.findAllMonthlyReportByForwardEngulfingRuleTrades(tradewareTool.getMonthlyReportStartDate(),
							tradewareTool.getMonthlyReportEndDate(),
							((0 != tradewareTool.getQueryCandleNumber()) ? tradewareTool.getQueryCandleNumber() : 25));
			reportList = new ArrayList<StrategyOrbMonthlyReportDataBean>(baseList.size());
			for (StrategyOrbMonthlyReportBean rowBean : baseList) {
				reportList.add(databaseMapper.mapMonthlyReportDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_MONTHLY_REPORT_FWD_ENGULFING, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbMonthlyReportDataBean> findAllMonthlyReportByForwardEngulfingLvl2RuleTrades() {
		List<StrategyOrbMonthlyReportDataBean> reportList = null;
		try {
			List<StrategyOrbMonthlyReportBean> baseList = monthlyReportService
					.findAllMonthlyReportByForwardEngulfingLvl2RuleTrades(tradewareTool.getMonthlyReportStartDate(),
							tradewareTool.getMonthlyReportEndDate(),
							((0 != tradewareTool.getQueryCandleNumber()) ? tradewareTool.getQueryCandleNumber() : 25));
			reportList = new ArrayList<StrategyOrbMonthlyReportDataBean>(baseList.size());
			for (StrategyOrbMonthlyReportBean rowBean : baseList) {
				reportList.add(databaseMapper.mapMonthlyReportDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_MONTHLY_REPORT_FWD_ENGULFING_LVL2, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbMonthlyReportDataBean> findAllMonthlyReportByForwardEngulfingLvl3RuleTrades() {
		List<StrategyOrbMonthlyReportDataBean> reportList = null;
		try {
			List<StrategyOrbMonthlyReportBean> baseList = monthlyReportService
					.findAllMonthlyReportByForwardEngulfingLvl3RuleTrades(tradewareTool.getMonthlyReportStartDate(),
							tradewareTool.getMonthlyReportEndDate(),
							((0 != tradewareTool.getQueryCandleNumber()) ? tradewareTool.getQueryCandleNumber() : 25));
			reportList = new ArrayList<StrategyOrbMonthlyReportDataBean>(baseList.size());
			for (StrategyOrbMonthlyReportBean rowBean : baseList) {
				reportList.add(databaseMapper.mapMonthlyReportDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_MONTHLY_REPORT_FWD_ENGULFING_LVL3, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}

	public List<StrategyOrbMonthlyReportDataBean> findAllMonthlyReportBySmaVwapRuleTrades() {
		List<StrategyOrbMonthlyReportDataBean> reportList = null;
		try {
			List<StrategyOrbMonthlyReportBean> baseList = monthlyReportService.findAllMonthlyReportBySmaVwapRuleTrades(
					tradewareTool.getMonthlyReportStartDate(), tradewareTool.getMonthlyReportEndDate(),
					((0 != tradewareTool.getQueryCandleNumber()) ? tradewareTool.getQueryCandleNumber() : 25));
			reportList = new ArrayList<StrategyOrbMonthlyReportDataBean>(baseList.size());
			for (StrategyOrbMonthlyReportBean rowBean : baseList) {
				reportList.add(databaseMapper.mapMonthlyReportDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_MONTHLY_REPORT_SMA_VWAP,
					e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	
	public List<StrategyOrbMonthlyReportDataBean> findAllMonthlyReportBySmaVwapRuleUnionTrades() {
		List<StrategyOrbMonthlyReportDataBean> reportList = null;
		try {
			List<StrategyOrbMonthlyReportBean> baseList = monthlyReportService
					.findAllMonthlyReportBySmaVwapRuleUnionTrades(tradewareTool.getMonthlyReportStartDate(),
							tradewareTool.getMonthlyReportEndDate(),
							((0 != tradewareTool.getQueryCandleNumber()) ? tradewareTool.getQueryCandleNumber() : 25));
			reportList = new ArrayList<StrategyOrbMonthlyReportDataBean>(baseList.size());
			for (StrategyOrbMonthlyReportBean rowBean : baseList) {
				reportList.add(databaseMapper.mapMonthlyReportDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_MONTHLY_REPORT_SMA_VWAP_UNION, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	
	public List<StrategyOrbMonthlyReportDataBean> findAllMonthlyReportBySmaVwapRule2Trades() {
		List<StrategyOrbMonthlyReportDataBean> reportList = null;
		try {
			List<StrategyOrbMonthlyReportBean> baseList = monthlyReportService
					.findAllMonthlyReportBySmaVwapRule2Trades(tradewareTool.getMonthlyReportStartDate(),
							tradewareTool.getMonthlyReportEndDate(),
							((0 != tradewareTool.getQueryCandleNumber()) ? tradewareTool.getQueryCandleNumber() : 25));
			reportList = new ArrayList<StrategyOrbMonthlyReportDataBean>(baseList.size());
			for (StrategyOrbMonthlyReportBean rowBean : baseList) {
				reportList.add(databaseMapper.mapMonthlyReportDataBean(rowBean));
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_MONTHLY_REPORT_SMA_VWAP_RULE2, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return reportList;
	}
	// Monthly reports end
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Option Chain
	@Autowired
	private OptionChainInfoService optionChainInfoService;
	
		@Autowired
		private NSEIndiaToolOptionChainTool nSEIndiaToolOptionChainTool;
		
		public OptionChainDataBean saveOptionChainData(OptionChainDataBean dataBean) {
			OptionChainInfoBean bean = null;
			try {
				bean = databaseMapper.mapToOptionChainInforBean(dataBean);
				bean = optionChainInfoService.save(bean);
				dataBean = databaseMapper.mapToOptionChainInforBean(bean);
			} catch (Exception e) {
				//System.out.println(bean.toString());			
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_SAVE_OPTION_CHAIN_DATA, e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return dataBean;
		}

		public List<OptionChainDataBean> findAllByCandleNumberAndDateStampOrderByStrongOrderDesc() {
			List<OptionChainDataBean> reportList = null;
			try {
				Date tradeDay = tradewareTool.getTradeDateForReport();
				List<OptionChainInfoBean> baseList = optionChainInfoService
						.findAllByCandleNumberAndDateStampOrderByStrongOrderDesc(
								nSEIndiaToolOptionChainTool.getCandleNumber(), tradeDay);
				reportList = new ArrayList<OptionChainDataBean>(baseList.size());
				for (OptionChainInfoBean rowBean : baseList) {
					reportList.add(databaseMapper.mapToOptionChainInforBean(rowBean));
				}
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, "findAllByCandleNumberAndDateStampOrderByStrongOrderDesc", e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return reportList;
		}
		
		public List<OptionChainDataBean> findAllByLatestOptinChainData() {
			List<OptionChainDataBean> reportList = null;
			try {
				Date tradeDay = tradewareTool.getTradeDateForReport();
				List<OptionChainInfoBean> baseList = optionChainInfoService.findAllByLatestOptinChainData(tradeDay);
				reportList = new ArrayList<OptionChainDataBean>(baseList.size());
				for (OptionChainInfoBean rowBean : baseList) {
					reportList.add(databaseMapper.mapToOptionChainInforBean(rowBean));
				}
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, "findAllByLatestOptinChainData", e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return reportList;
		}
		
		public List<OptionChainDataBean> findAllByLatestOptinChainDataForBankNifty() {
			List<OptionChainDataBean> reportList = null;
			try {
				Date tradeDay = tradewareTool.getTradeDateForReport();
				List<OptionChainInfoBean> baseList = optionChainInfoService.findAllByLatestOptinChainDataForBankNifty(tradeDay);
				reportList = new ArrayList<OptionChainDataBean>(baseList.size());
				for (OptionChainInfoBean rowBean : baseList) {
					reportList.add(databaseMapper.mapToOptionChainInforBean(rowBean));
				}
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, "findAllByLatestOptinChainDataForBankNifty", e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return reportList;
		}
		
		public List<OptionChainDataBean> findAllOptinChainDataByGivenDate() {
			List<OptionChainDataBean> reportList = null;
			try {
				Date startDate = tradewareTool.getTradeDateForReport();
				Date endDate = new Date(startDate.getTime() + (24 * 60 * 60 * 1000));
				List<OptionChainInfoBean> baseList = optionChainInfoService.findAllOptinChainDataByGivenDate(startDate,
						endDate);
				//System.out.println(startDate+ " : "+endDate);
				reportList = new ArrayList<OptionChainDataBean>(baseList.size());
				for (OptionChainInfoBean rowBean : baseList) {
					reportList.add(databaseMapper.mapToOptionChainInforBean(rowBean));
				}
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, "findAllOptinChainDataByGivenDate", e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return reportList;
		}
		public OptionChainDataBean findSymbolByLatestOptinChainData(String symbolId) {
			OptionChainDataBean dataBean = null;
			try {
				Date tradeDay = tradewareTool.getTradeDateForReport();
				OptionChainInfoBean infoBean = optionChainInfoService.findSymbolByLatestOptinChainData(tradeDay, symbolId);
			    dataBean = databaseMapper.mapToOptionChainInforBean(infoBean);
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, "findSymbolByLatestOptinChainData_"+symbolId, e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return dataBean;
		}
		
		public List<OptionChainDataBean> findAllBestTradeOptinChainData() {
			List<OptionChainDataBean> reportList = null;
			try {
				Date tradeDay = tradewareTool.getTradeDateForReport();
				List<OptionChainInfoBean> baseList = optionChainInfoService.findAllBestTradeOptinChainData(tradeDay);
				reportList = new ArrayList<OptionChainDataBean>(baseList.size());
				for (OptionChainInfoBean rowBean : baseList) {
					reportList.add(databaseMapper.mapToOptionChainInforBean(rowBean));
				}
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, "findAllBestTradeOptinChainData", e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return reportList;
		}
		
		public List<OptionChainDataBean> findAllBySymbolIdAndDateStampOrderByDateTimeStampAsc(String symbolId) {
			List<OptionChainDataBean> reportList = null;
			try {
				Date tradeDay = tradewareTool.getTradeDateForReport();
				List<OptionChainInfoBean> baseList = optionChainInfoService
						.findAllBySymbolIdAndDateStampOrderByDateTimeStampAsc(symbolId, tradeDay);
				reportList = new ArrayList<OptionChainDataBean>(baseList.size());
				for (OptionChainInfoBean rowBean : baseList) {
					reportList.add(databaseMapper.mapToOptionChainInforBean(rowBean));
				}
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, "findAllBySymbolIdAndDateStampOrderByDateTimeStampAsc", e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return reportList;
		}
		
		

		public void updateBestTrade(OptionChainDataBean optionChainDataBean) {
			try {
				optionChainInfoService.updateBestTrade(optionChainDataBean.getBestTradeInd(),
						optionChainDataBean.getUnderlyingPrice(), optionChainDataBean.getBestEntry(),
						optionChainDataBean.getChangePercentage(), optionChainDataBean.getGoForTrade(),
						optionChainDataBean.getOptionChainId(), optionChainDataBean.getSymbol());
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_UPDATE_BEST_TRADE, e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
		}

		public OptionChainDataBean findBySymbolIdAndDateStampAndParentRecordIndTrue(String symbolId) {
			OptionChainDataBean dataBean = null;
			try {
				Date tradeDay = tradewareTool.getTradeDateForReport();
				OptionChainInfoBean bean = optionChainInfoService.findBySymbolIdAndDateStampAndParentRecordIndTrue(symbolId,
						tradeDay);
				dataBean = databaseMapper.mapToOptionChainInforBean(bean);
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_FIND_BY_SYMBOLID_AND_DATESTAMP_AND_PARENTRECORDIND_TRUE, e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return dataBean;
		}
		
		public OptionChainDataBean findBySymbolIdAndParentRecordIndTrueForCurrentDay(String symbolId) {
			OptionChainDataBean dataBean = null;
			try {
				//Date tradeDay = tradewareTool.getTradeDateForReport();
				OptionChainInfoBean bean = optionChainInfoService.findBySymbolIdAndDateStampAndParentRecordIndTrue(symbolId);
				dataBean = databaseMapper.mapToOptionChainInforBean(bean);
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_FIND_BY_SYMBOLID_AND_PARENTRECORDIND_TRUE_FOR_CURRENTDAY, e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return dataBean;
		}
		
		// Attention trades
		/*public List<OptionChainDataBean> findAllByAttentionIndAndDateStamp(String orperator) {
			List<OptionChainDataBean> reportList = null;
			try {
				Date tradeDay = tradewareTool.getTradeDateForReport();
				List<OptionChainInfoBean> baseList = optionChainInfoService.findAllByAttentionIndAndDateStamp(orperator,
						tradeDay);
				reportList = new ArrayList<OptionChainDataBean>(baseList.size());
				for (OptionChainInfoBean rowBean : baseList) {
					reportList.add(databaseMapper.mapToOptionChainInforBean(rowBean));
				}
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_GETRUNNINGTRADES, e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return reportList;
		}*/
		
		public List<OptionChainDataBean> findAllByAttentionIndAndDateStampStrict() {
			List<OptionChainDataBean> reportList = null;
			try {
				Date tradeDay = tradewareTool.getTradeDateForReport();
				List<OptionChainInfoBean> baseList = optionChainInfoService
						.findAllByAttentionIndAndDateStampStrict(tradeDay);
				reportList = new ArrayList<OptionChainDataBean>(baseList.size());
				for (OptionChainInfoBean rowBean : baseList) {
					reportList.add(databaseMapper.mapToOptionChainInforBean(rowBean));
				}
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, "findAllByAttentionIndAndDateStampStrict", e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return reportList;

		}

		public List<OptionChainDataBean> findAllByAttentionIndAndDateStampPartial() {
			List<OptionChainDataBean> reportList = null;
			try {
				Date tradeDay = tradewareTool.getTradeDateForReport();
				List<OptionChainInfoBean> baseList = optionChainInfoService
						.findAllByAttentionIndAndDateStampPartial(tradeDay);
				reportList = new ArrayList<OptionChainDataBean>(baseList.size());
				for (OptionChainInfoBean rowBean : baseList) {
					reportList.add(databaseMapper.mapToOptionChainInforBean(rowBean));
				}
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, "findAllByAttentionIndAndDateStampPartial", e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return reportList;
		}
		
		
		//test
		
		public void updateTradeAVGHLC(Double avgHigh, Double avgLow, Double avgClose, Double currentCandleOpen,
				Double avgHighMinusClose, Double avgCloseMinusLow, Double vwapVal, Integer itemId) {
			try {
				strategyOrbService.updateTradeAVGHLC(avgHigh, avgLow, avgClose, currentCandleOpen, avgHighMinusClose,
						avgCloseMinusLow, vwapVal, itemId);
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_UPDATE_TRADE_AVGHLC, e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
		}
		
		
		public List<StrategyOrbDataBean> findAllByTradedDateStampOrderByTradedAtDtTmDesc() {
			List<StrategyOrbDataBean> reportList = null;
			try {
				Date tradeDay = tradewareTool.getTradeDateForReport();
				List<StrategyOrbBean> baseList = strategyOrbService
						.findAllByTradedDateStampOrderByTradedAtDtTmDesc(tradeDay);
				reportList = new ArrayList<StrategyOrbDataBean>(baseList.size());
				for (StrategyOrbBean rowBean : baseList) {
					reportList.add(
							databaseMapper.mapStrategyOrbBeanToStrategyOrbDataBean(rowBean));
				}
			} catch (Exception e) {
				TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
						Constants.METHOD_FIND_BY_TRADED_DATESTAMP_ORDERBY_TRADEDATDTTMDESC, e,
						Constants.ERROR_TYPE_EXCEPTION);
			}
			return reportList;
		}
		
		
		
		
		
		
		
		
		
		
		
	// Option strategy trading start
	@Autowired
	private OptionStrategyTradeService optionStrategyTradeService;

	public OptionLiveTradeMainDataBean saveShortStrangleOptionTrade(OptionLiveTradeMainDataBean dataBean) {
		try {
			OptionLiveTradeMainBean mainBean = databaseMapper.mapToOptionLiveShortStrangleTradeMainBean(dataBean);
			mainBean = optionStrategyTradeService.save(mainBean);
			dataBean = databaseMapper.mapToOptionLiveShortStrangleTradeMainDataBean(mainBean);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_SAVE_SHORT_STRANGLE_OPTION_TRADE, e, Constants.ERROR_TYPE_EXCEPTION);
		}
		return dataBean;
	}
	
	
	public List<OptionLiveTradeMainDataBean> findAllOptionStrategyTrades() {
		List<OptionLiveTradeMainDataBean> optionTradeDataBeanList = null;
		try {
			List<OptionLiveTradeMainBean> optionTradeBeanList = optionStrategyTradeService
					.findAll();
			optionTradeDataBeanList = new ArrayList<OptionLiveTradeMainDataBean>(optionTradeBeanList.size());
			for (OptionLiveTradeMainBean optionTradeBean : optionTradeBeanList) {
				OptionLiveTradeMainDataBean dataBean = databaseMapper.mapToOptionLiveShortStrangleTradeMainDataBean(optionTradeBean);
				optionTradeDataBeanList.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_BY_OPEN_OPTION_STRATEGY_TRADES, e,
					Constants.ERROR_TYPE_EXCEPTION);

			// e.printStackTrace();
		}
		return optionTradeDataBeanList;
	}
	
	public List<OptionLiveTradeMainDataBean> findByOpenOptionStrategyTrades() {
		List<OptionLiveTradeMainDataBean> optionTradeDataBeanList = null;
		try {
			List<OptionLiveTradeMainBean> optionTradeBeanList = optionStrategyTradeService
					.findByTradePosition(Constants.TRADE_POSIITON_OPEN);
			optionTradeDataBeanList = new ArrayList<OptionLiveTradeMainDataBean>(optionTradeBeanList.size());
			for (OptionLiveTradeMainBean optionTradeBean : optionTradeBeanList) {
				OptionLiveTradeMainDataBean dataBean = databaseMapper.mapToOptionLiveShortStrangleTradeMainDataBean(optionTradeBean);
				optionTradeDataBeanList.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_BY_OPEN_OPTION_STRATEGY_TRADES, e,
					Constants.ERROR_TYPE_EXCEPTION);

			// e.printStackTrace();
		}
		return optionTradeDataBeanList;
	}
	
	public List<OptionLiveTradeMainDataBean> findAllByTradedDateStamp() {
		List<OptionLiveTradeMainDataBean> optionTradeDataBeanList = null;
		try {
			List<OptionLiveTradeMainBean> optionTradeBeanList = optionStrategyTradeService
					.findAllByTradedDateStamp(tradewareTool.getTradeDateForReport());
			optionTradeDataBeanList = new ArrayList<OptionLiveTradeMainDataBean>(optionTradeBeanList.size());
			for (OptionLiveTradeMainBean optionTradeBean : optionTradeBeanList) {
				OptionLiveTradeMainDataBean dataBean = databaseMapper.mapToOptionLiveShortStrangleTradeMainDataBean(optionTradeBean);
				optionTradeDataBeanList.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_BY__TRADED_DATE_STAMP, e,
					Constants.ERROR_TYPE_EXCEPTION);

			// e.printStackTrace();
		}
		return optionTradeDataBeanList;
	}
	
	
	public OptionLiveTradeMainDataBean findByOptionStrategyTradeId(Integer optionStrategyTradeId) {
		OptionLiveTradeMainDataBean optionTradeDataBean = null;
		try {
			OptionLiveTradeMainBean optionTradeBean = optionStrategyTradeService
					.findByTradeId(optionStrategyTradeId);
			optionTradeDataBean = databaseMapper.mapToOptionLiveShortStrangleTradeMainDataBean(optionTradeBean);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_BY_OPTION_STRATEGY_TRADE_ID, e,
					Constants.ERROR_TYPE_EXCEPTION);

			// e.printStackTrace();
		}
		return optionTradeDataBean;
	}
	
	public OptionLiveTradeMainDataBean findRunningTradeByTradeId(Integer optionStrategyTradeId) {
		OptionLiveTradeMainDataBean optionTradeDataBean = null;
		try {
			OptionLiveTradeMainBean optionTradeBean = optionStrategyTradeService
					.findRunningTradeByTradeId(optionStrategyTradeId);
			optionTradeDataBean = databaseMapper.mapToOptionLiveShortStrangleTradeMainDataBean(optionTradeBean);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_RUNNING_TRADE_BY_TRADE_ID, e,
					Constants.ERROR_TYPE_EXCEPTION);

			// e.printStackTrace();
		}
		return optionTradeDataBean;
	}
	
	/*public List<OptionLiveTradeMainDataBean> findAllByRunningTrade() {
		List<OptionLiveTradeMainDataBean> optionTradeDataBeanList = null;
		try {
			List<OptionLiveTradeMainBean> optionTradeBeanList = optionStrategyTradeService.findAllByRunningTrade(tradewareTool.getTradeDateForReport());
			optionTradeDataBeanList = new ArrayList<OptionLiveTradeMainDataBean>(optionTradeBeanList.size());
			for (OptionLiveTradeMainBean optionTradeBean : optionTradeBeanList) {
				OptionLiveTradeMainDataBean dataBean = databaseMapper
						.mapToOptionLiveShortStrangleTradeMainDataBean(optionTradeBean);
				optionTradeDataBeanList.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_FIND_BY_RUNNING_TRADE,
					e, Constants.ERROR_TYPE_EXCEPTION);

			// e.printStackTrace();
		}
			return optionTradeDataBeanList;
		}*/
	
	public List<OptionLiveTradeMainDataBean> findAllByRunningTrade() {
		List<OptionLiveTradeMainDataBean> optionTradeDataBeanList = null;
		try {
			List<OptionLiveTradeMainBean> optionTradeBeanList = optionStrategyTradeService.findAllByRunningTrade();
			optionTradeDataBeanList = new ArrayList<OptionLiveTradeMainDataBean>(optionTradeBeanList.size());
			for (OptionLiveTradeMainBean optionTradeBean : optionTradeBeanList) {
				OptionLiveTradeMainDataBean dataBean = databaseMapper
						.mapToOptionLiveShortStrangleTradeMainDataBean(optionTradeBean);
				optionTradeDataBeanList.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER, Constants.METHOD_FIND_BY_RUNNING_TRADE,
					e, Constants.ERROR_TYPE_EXCEPTION);

			// e.printStackTrace();
		}
			return optionTradeDataBeanList;
		}

	public List<OptionLiveTradeMainDataBean> findByTradePositionAndTradeStrategy(String tradePosition,
			String radeStrategy) {
		List<OptionLiveTradeMainDataBean> optionTradeDataBeanList = null;
		try {
			List<OptionLiveTradeMainBean> optionTradeBeanList = optionStrategyTradeService
					.findByTradePositionAndTradeStrategy(tradePosition, radeStrategy);
			optionTradeDataBeanList = new ArrayList<OptionLiveTradeMainDataBean>(optionTradeBeanList.size());
			for (OptionLiveTradeMainBean optionTradeBean : optionTradeBeanList) {
				OptionLiveTradeMainDataBean dataBean = databaseMapper
						.mapToOptionLiveShortStrangleTradeMainDataBean(optionTradeBean);
				optionTradeDataBeanList.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_DATABASE_HELPER,
					Constants.METHOD_FIND_BY_POSITION_AND_STRATEGY, e, Constants.ERROR_TYPE_EXCEPTION);

			// e.printStackTrace();
		}
		return optionTradeDataBeanList;
	}

	// Option strategy trading END
		
		

}
