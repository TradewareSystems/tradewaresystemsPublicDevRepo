package com.tradeware.stockmarket.tool;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.stockmarket.bean.ProfitLossSummaryDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.util.Constants;
import com.tradeware.stockmarket.util.DatabaseHelper;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ProfitLossSummaryTool {
	@Autowired 
	protected KiteConnectTool kiteConnectTool;
	private Map<String, ProfitLossSummaryDataBean> map = new HashMap<String, ProfitLossSummaryDataBean>();

	public ProfitLossSummaryTool() {
		map.put(Constants.STRATEGY_NR7_R2, new ProfitLossSummaryDataBean());
		map.put(Constants.STRATEGY_NR7_R1, new ProfitLossSummaryDataBean());
		map.put(Constants.STRATEGY_NR7, new ProfitLossSummaryDataBean());
		map.put(Constants.STRATEGY_NR7_R2, new ProfitLossSummaryDataBean());
		map.put(Constants.TRADE_PLACE_RULE_INIT_PROFITABLE_PROD_RULE, new ProfitLossSummaryDataBean());
	}

	public Map<String, ProfitLossSummaryDataBean> getProfitLossSummaryMap() {
		return map;
	}

	public void calculateProfitLoss(String strategyRule) {
		try {
			ProfitLossSummaryDataBean beanSumm = map.get(strategyRule);
			if (null != beanSumm) {
				if (beanSumm.getScannerUpdatable()) {
				/*List<StrategyOrbDataBean> list = new ArrayList<StrategyOrbDataBean>(
						DatabaseHelper.getInstance().findAllByCustomRule4AllStrategyRuleTrack(strategyRule));

				double totalProfitLoss = 0;
				double totalProfitLossAfterTaxes = 0;
				for (StrategyOrbDataBean bean : list) {
					totalProfitLoss = totalProfitLoss
							+ (null != bean.getProfitLossAmtVal() ? bean.getProfitLossAmtVal() : 0);
				}
				totalProfitLoss = new BigDecimal(totalProfitLoss).setScale(2, 0).doubleValue();
				totalProfitLossAfterTaxes = BigDecimal.valueOf(totalProfitLoss - (list.size() * 150)).setScale(2, 0)
						.doubleValue();*/
				
				
				Double totalProfitLoss = DatabaseHelper.getInstance().checkTodayTradingNotForceClosedByCrossMaxLossLimit();
				totalProfitLoss = null != totalProfitLoss ? kiteConnectTool.getRoundupToTwoValue(totalProfitLoss): 0;
				Double totalProfitLossAfterTaxes = totalProfitLoss;
				
				if (totalProfitLossAfterTaxes < beanSumm.getMaxLossMoveValue()) {
					beanSumm.setMaxLossMoveValue(totalProfitLossAfterTaxes);
				} else if (totalProfitLossAfterTaxes > beanSumm.getMaxProfitMoveValue()) {
					beanSumm.setMaxProfitMoveValue(totalProfitLossAfterTaxes);
				}

				
					if (beanSumm.getProfitLossBookedValueMinus5k() == null && totalProfitLossAfterTaxes < LOSS_5K) {
						beanSumm.setProfitLossBookedValueMinus5k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStampMinus5k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(MAX_DAILY_LOSS);
					} else if (beanSumm.getProfitLossBookedValue5k() == null && totalProfitLossAfterTaxes > PROFIT_5K) {
						beanSumm.setProfitLossBookedValue5k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp5k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_0);
					} else if (beanSumm.getProfitLossBookedValue10k() == null
							&& totalProfitLossAfterTaxes > PROFIT_10K) {
						beanSumm.setProfitLossBookedValue10k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp10k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_5K);
					} else if (beanSumm.getProfitLossBookedValue15k() == null
							&& totalProfitLossAfterTaxes > PROFIT_15K) {
						beanSumm.setProfitLossBookedValue15k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp15k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_10K);
					} else if (beanSumm.getProfitLossBookedValue20k() == null
							&& totalProfitLossAfterTaxes > PROFIT_20K) {
						beanSumm.setProfitLossBookedValue20k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp20k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_15K);
					} else if (beanSumm.getProfitLossBookedValue25k() == null
							&& totalProfitLossAfterTaxes > PROFIT_25K) {
						beanSumm.setProfitLossBookedValue25k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp25k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_20K);
					} else if (beanSumm.getProfitLossBookedValue30k() == null
							&& totalProfitLossAfterTaxes > PROFIT_30K) {
						beanSumm.setProfitLossBookedValue30k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp30k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_25K);
					} else if (beanSumm.getProfitLossBookedValue35k() == null
							&& totalProfitLossAfterTaxes > PROFIT_35K) {
						beanSumm.setProfitLossBookedValue35k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp35k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_30K);
					} else if (beanSumm.getProfitLossBookedValue40k() == null
							&& totalProfitLossAfterTaxes > PROFIT_40K) {
						beanSumm.setProfitLossBookedValue40k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp40k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_35K);
					} else if (beanSumm.getProfitLossBookedValue45k() == null
							&& totalProfitLossAfterTaxes > PROFIT_45K) {
						beanSumm.setProfitLossBookedValue45k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp45k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_40K);
					} else if (beanSumm.getProfitLossBookedValue50k() == null
							&& totalProfitLossAfterTaxes > PROFIT_50K) {
						beanSumm.setProfitLossBookedValue50k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp50k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_45K);
					} else if (beanSumm.getProfitLossBookedValue50k() == null
							&& totalProfitLossAfterTaxes > PROFIT_55K) {
						beanSumm.setProfitLossBookedValue50k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp50k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_50K);
					} else if (beanSumm.getProfitLossBookedValue50k() == null
							&& totalProfitLossAfterTaxes > PROFIT_60K) {
						beanSumm.setProfitLossBookedValue50k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp50k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_55K);
					} else if (beanSumm.getProfitLossBookedValue50k() == null
							&& totalProfitLossAfterTaxes > PROFIT_65K) {
						beanSumm.setProfitLossBookedValue50k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp50k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_60K);
					} else if (beanSumm.getProfitLossBookedValue50k() == null
							&& totalProfitLossAfterTaxes > PROFIT_70K) {
						beanSumm.setProfitLossBookedValue50k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp50k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_65K);
					} else if (beanSumm.getProfitLossBookedValue50k() == null
							&& totalProfitLossAfterTaxes > PROFIT_75K) {
						beanSumm.setProfitLossBookedValue50k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp50k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_70K);
					} else if (beanSumm.getProfitLossBookedValue50k() == null
							&& totalProfitLossAfterTaxes > PROFIT_80K) {
						beanSumm.setProfitLossBookedValue50k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp50k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_75K);
					} else if (beanSumm.getProfitLossBookedValue50k() == null
							&& totalProfitLossAfterTaxes > PROFIT_85K) {
						beanSumm.setProfitLossBookedValue50k(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStamp50k(getCurrentDateTimeStamp());
						beanSumm.setTrailingStopLossValue(PROFIT_80K);
					} else if ((totalProfitLossAfterTaxes <= MAX_DAILY_LOSS)
							|| (null != beanSumm.getTrailingStopLossValue())
									&& totalProfitLossAfterTaxes <= beanSumm.getTrailingStopLossValue()) {
						beanSumm.setScannerUpdatable(Boolean.FALSE);
						beanSumm.setDayProfitLossValue(totalProfitLossAfterTaxes);
						beanSumm.setDateTimeStampDayProfitLoss(getCurrentDateTimeStamp());
					}
				
				map.replace(strategyRule, beanSumm);
				}
			}
		} catch (Exception e) {
			TradewareLogger
					.sysoutPrintlnForLocalTest("<<<<<<<<<<<<>>>>>>>>Error calculateProfitLoss " + e.getMessage());
			TradewareLogger.saveFatalErrorLog("ProfitLossSummaryTool", "calculateProfitLoss", e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
	}

	public static final double PROFIT_0 = 0d;
	public static final double PROFIT_5K = 5000d;
	public static final double PROFIT_10K = 10000d;
	public static final double PROFIT_15K = 15000d;
	public static final double PROFIT_20K = 20000d;
	public static final double PROFIT_25K = 25000d;
	public static final double PROFIT_30K = 30000d;
	public static final double PROFIT_35K = 35000d;
	public static final double PROFIT_40K = 40000d;
	public static final double PROFIT_45K = 45000d;
	public static final double PROFIT_50K = 50000d;
	public static final double PROFIT_55K = 55000d;
	public static final double PROFIT_60K = 60000d;
	public static final double PROFIT_65K = 65000d;
	public static final double PROFIT_70K = 70000d;
	public static final double PROFIT_75K = 75000d;
	public static final double PROFIT_80K = 80000d;
	public static final double PROFIT_85K = 85000d;
	public static final double LOSS_5K = -5000d;
	public static final double MAX_DAILY_LOSS = -10000d;
	private Date getCurrentDateTimeStamp() {
		LocalDateTime ldt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
		return Timestamp.valueOf(ldt);
	}
}
