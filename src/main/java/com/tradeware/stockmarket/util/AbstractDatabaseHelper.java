package com.tradeware.stockmarket.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tradeware.clouddatabase.bean.ActivityAuditBean;
import com.tradeware.clouddatabase.service.ActivityAuditService;
import com.tradeware.stockmarket.bean.ActivityAuditDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.tool.TradewareTool;

public class AbstractDatabaseHelper {
	@Autowired
	private TradewareTool tradewareTool;
	
	@Autowired
	private ActivityAuditService activityAuditService;
	
	private static final String ACTIVITY_SERVER_STARTUP = "SERVER_STARTUP_ACTIVITY";
	private static final String ACTIVITY_SERVER_DOWN = "SERVER_DOWN_ACTIVITY";
	private static final String ACTIVITY_SERVER_STARTUP_AT = "SERVER_STARTUP_@ ";
	private static final String ACTIVITY_SERVER_DOWN_AT = "SERVER_DOWN_@ ";

	private static final String ACTIVITY_USER_LOGON = "ACTIVITY_USER_LOGON";
	private static final String ACTIVITY_USER_LOGOUT = "ACTIVITY_USER_LOGOUT";
	private static final String ACTIVITY_USER_LOGON_AT = "ACTIVITY_USER_LOGON_@ ";
	private static final String ACTIVITY_USER_LOGOUT_AT = "ACTIVITY_USER_LOGOUT_@ ";

	private static final String ACTIVITY_SCANNER_STARTUP = "SCANNER_STARTUP_ACTIVITY";
	private static final String ACTIVITY_SCANNER_DOWN = "SCANNER_DOWN_ACTIVITY";
	private static final String ACTIVITY_SCANNER_STARTUP_AT = "SCANNER_STARTUP_@ ";
	private static final String ACTIVITY_SCANNER_DOWN_AT = "SCANNER_LAST_RUN_@ ";

	private static final String ACTIVITY_SCANNER_FORCE_STARTUP = "SCANNER_FORCE_STARTUP_ACTIVITY";
	private static final String ACTIVITY_SCANNER_FORCE_STARTUP_AT = "SCANNER_FORCE_STARTUP_@ ";

	// Activity start
	public void saveActivityAuditDataBean(ActivityAuditBean activityAuditDataBean) {
		try {
			activityAuditService.save(activityAuditDataBean);
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_ACTIVITY_AUDIT_SERVICE,
					Constants.METHOD_SAVE_ACTIVITY_AUDIT_DATA_BEAN, e, Constants.ERROR_TYPE_EXCEPTION);
		}
	}

	public List<ActivityAuditDataBean> findAllActivityByDateStampOrderByDateTimeStampDesc() {
		List<ActivityAuditDataBean> activityAuditDataBeanList = null;
		try {
			List<ActivityAuditBean> activityAuditBeanList = activityAuditService
					.findAllByDateStampOrderByDateTimeStampDesc(tradewareTool.getTradeDateForReport());
			activityAuditDataBeanList = new ArrayList<ActivityAuditDataBean>(activityAuditBeanList.size());
			for (ActivityAuditBean activityAuditBean : activityAuditBeanList) {
				ActivityAuditDataBean dataBean = DatabaseMapper
						.mapActivityAuditBeanToActivityAuditDataBean(activityAuditBean);
				activityAuditDataBeanList.add(dataBean);
			}
		} catch (Exception e) {
			TradewareLogger.saveFatalErrorLog(Constants.CLASS_ACTIVITY_AUDIT_SERVICE,
					Constants.METHOD_FIND_ALL_ACTIVITY_BY_DATE_STAMP_ORDER_BY_DATE_TIME_STAMP_DESC, e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return activityAuditDataBeanList;
	}


	public void activityServerStartup() {
		ActivityAuditBean bean = DatabaseMapper.mapActivityAuditDataBeanToSActivityAuditBean(ACTIVITY_SERVER_STARTUP,
				ACTIVITY_SERVER_STARTUP_AT + tradewareTool.getCurrentDateTimeAsAMPM_12HrsMode());
		saveActivityAuditDataBean(bean);
	}

	public void activityServerDown() {
		ActivityAuditBean bean = DatabaseMapper.mapActivityAuditDataBeanToSActivityAuditBean(ACTIVITY_SERVER_DOWN,
				ACTIVITY_SERVER_DOWN_AT + tradewareTool.getCurrentDateTimeAsAMPM_12HrsMode());
		saveActivityAuditDataBean(bean);
		activityUserLogout();
		activityScannerEndup();
	}

	public void activityUserLogon() {
		ActivityAuditBean bean = DatabaseMapper.mapActivityAuditDataBeanToSActivityAuditBean(ACTIVITY_USER_LOGON,
				ACTIVITY_USER_LOGON_AT + tradewareTool.getCurrentDateTimeAsAMPM_12HrsMode());
		saveActivityAuditDataBean(bean);
	}

	public void activityUserLogout() {
		ActivityAuditBean bean = DatabaseMapper.mapActivityAuditDataBeanToSActivityAuditBean(ACTIVITY_USER_LOGOUT,
				ACTIVITY_USER_LOGOUT_AT + tradewareTool.getCurrentDateTimeAsAMPM_12HrsMode());
		saveActivityAuditDataBean(bean);
	}

	public void activityScannerStartup() {
		ActivityAuditBean bean = DatabaseMapper.mapActivityAuditDataBeanToSActivityAuditBean(ACTIVITY_SCANNER_STARTUP,
				ACTIVITY_SCANNER_STARTUP_AT + tradewareTool.getCurrentDateTimeAsAMPM_12HrsMode());
		saveActivityAuditDataBean(bean);
	}

	public void activityScannerEndup() {
		ActivityAuditBean bean = DatabaseMapper.mapActivityAuditDataBeanToSActivityAuditBean(ACTIVITY_SCANNER_DOWN,
				ACTIVITY_SCANNER_DOWN_AT + tradewareTool.getThreadScannerLastRunnngSatusTime());
		saveActivityAuditDataBean(bean);
	}

	public void activityScannerForceStartup() {
		ActivityAuditBean bean = DatabaseMapper.mapActivityAuditDataBeanToSActivityAuditBean(
				ACTIVITY_SCANNER_FORCE_STARTUP,
				ACTIVITY_SCANNER_FORCE_STARTUP_AT + tradewareTool.getCurrentDateTimeAsAMPM_12HrsMode());
		saveActivityAuditDataBean(bean);
	}
	
	private static final String ACTIVITY_THREAD_PROCESS_DURATION = "ACTIVITY_THREAD_PROCESS_DURATION";
	private static final String ACTIVITY_15_MINUTE_HISTDATA = "KITE_15_MINUTE_HISTDATA_DURATON@";
	private static final String ACTIVITY_OPTION_CHAIN_DATA_PROCESS = "OPTION_CHAIN_DATA_PROCESS@";
	public void activityKite15MinuteHistDataProcess(String message) {
		ActivityAuditBean bean = DatabaseMapper.mapActivityAuditDataBeanToSActivityAuditBean(
				ACTIVITY_THREAD_PROCESS_DURATION,
				ACTIVITY_15_MINUTE_HISTDATA + tradewareTool.getCurrentDateTimeAsAMPM_12HrsMode() + message);
		saveActivityAuditDataBean(bean);
	}
	public void activityOptionChainDataProcess(String message) {
		ActivityAuditBean bean = DatabaseMapper.mapActivityAuditDataBeanToSActivityAuditBean(
				ACTIVITY_THREAD_PROCESS_DURATION,
				ACTIVITY_OPTION_CHAIN_DATA_PROCESS + tradewareTool.getCurrentDateTimeAsAMPM_12HrsMode() + message);
		saveActivityAuditDataBean(bean);
	}
	// Activity end
}
