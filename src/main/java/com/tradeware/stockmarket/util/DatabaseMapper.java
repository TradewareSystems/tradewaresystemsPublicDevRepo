package com.tradeware.stockmarket.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.clouddatabase.bean.ActivityAuditBean;
import com.tradeware.clouddatabase.bean.OptionChainInfoBean;
import com.tradeware.clouddatabase.bean.OptionLiveTradeChildBean;
import com.tradeware.clouddatabase.bean.OptionLiveTradeMainBean;
import com.tradeware.clouddatabase.bean.ProfitLossSummaryBean;
import com.tradeware.clouddatabase.bean.StrategyOrbBean;
import com.tradeware.clouddatabase.bean.StrategyOrbMonthlyReportBean;
import com.tradeware.clouddatabase.bean.SymbolBean;
import com.tradeware.clouddatabase.bean.SystemErrorBean;
import com.tradeware.clouddatabase.bean.UserBean;
import com.tradeware.stockmarket.bean.ActivityAuditDataBean;
import com.tradeware.stockmarket.bean.OptionChainDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeChildDataBean;
import com.tradeware.stockmarket.bean.OptionLiveTradeMainDataBean;
import com.tradeware.stockmarket.bean.ProfitLossSummaryDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbMonthlyReportDataBean;
import com.tradeware.stockmarket.bean.SymbolDataBean;
import com.tradeware.stockmarket.bean.SystemErrorDataBean;
import com.tradeware.stockmarket.bean.UserDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.tradeware.stockmarket.tool.KiteConnectTool;
import com.tradeware.stockmarket.tool.TradewareTool;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON) 
public class DatabaseMapper {
	@Autowired
	private TradewareTool tradewareTool;
	
	@Autowired
	private KiteConnectTool kiteConnectTool;
	
	public UserDataBean mapUserBeanToUserDataBean(UserBean userBean) {
		UserDataBean bean = new UserDataBean();
		bean.setUserId(userBean.getUserId());
		bean.setUserName(userBean.getUserName());
		bean.setFirstName(userBean.getFirstName());
		bean.setLastName(userBean.getLastName());
		bean.setPassword(userBean.getPassword());
		bean.setDateOfBirth(userBean.getDateOfBirth());
		bean.setEmail(userBean.getEmail());
		bean.setEmailActivationInd(userBean.getEmailActivationInd());
		bean.setConfirmationToken(userBean.getConfirmationToken());
		bean.setApiKey(userBean.getApiKey());
		bean.setSecretKey(userBean.getSecretKey());
		bean.setHistApiKey(userBean.getHistApiKey());
		bean.setHistSecretKey(userBean.getHistSecretKey());
		bean.setActiveInd(userBean.getActiveInd());
		bean.setAccessToken(userBean.getAccessToken());
		bean.setPublicToken(userBean.getPublicToken());
		bean.setRequestToken(userBean.getRequestToken());
		bean.setHistAccessToken(userBean.getHistAccessToken());
		bean.setHistPublicToken(userBean.getHistPublicToken());
		bean.setHistRequestToken(userBean.getHistRequestToken());
		bean.setUserRoleId(userBean.getUserRoleId());
		bean.setDateTimeStamp(userBean.getDateTimeStamp());
		bean.setAccountExpiredInd(userBean.getAccountExpiredInd());
		bean.setCredentialsExpiredInd(userBean.getCredentialsExpiredInd());
		bean.setAccountLockedInd(userBean.getAccountLockedInd());
		return bean;
	}

	public UserBean mapUserDataBeanToUserBean(UserDataBean userDataBean) {
		UserBean bean = new UserBean();
		bean.setUserId(userDataBean.getUserId());
		bean.setUserName(userDataBean.getUserName());
		bean.setFirstName(userDataBean.getFirstName());
		bean.setLastName(userDataBean.getLastName());
		bean.setPassword(userDataBean.getPassword());
		bean.setDateOfBirth(userDataBean.getDateOfBirth());
		bean.setEmail(userDataBean.getEmail());
		bean.setEmailActivationInd(userDataBean.getEmailActivationInd());
		bean.setConfirmationToken(userDataBean.getConfirmationToken());
		bean.setApiKey(userDataBean.getApiKey());
		bean.setSecretKey(userDataBean.getSecretKey());
		bean.setHistApiKey(userDataBean.getHistApiKey());
		bean.setHistSecretKey(userDataBean.getHistSecretKey());
		bean.setActiveInd(userDataBean.getActiveInd());
		bean.setAccessToken(userDataBean.getAccessToken());
		bean.setPublicToken(userDataBean.getPublicToken());
		bean.setRequestToken(userDataBean.getRequestToken());
		bean.setHistAccessToken(userDataBean.getHistAccessToken());
		bean.setHistPublicToken(userDataBean.getHistPublicToken());
		bean.setHistRequestToken(userDataBean.getHistRequestToken());
		bean.setUserRoleId(userDataBean.getUserRoleId());
		bean.setAccountExpiredInd(userDataBean.getAccountExpiredInd());
		bean.setCredentialsExpiredInd(userDataBean.getCredentialsExpiredInd());
		bean.setAccountLockedInd(userDataBean.getAccountLockedInd());
		
		LocalDateTime ldt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateTimeStamp(Timestamp.valueOf(ldt));
//		LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
//		bean.setDateStamp(java.sql.Date.valueOf(ld));
		return bean;
	}
	
	public SystemErrorDataBean mapSystemErrorBeanToSystemErrorDataBean(SystemErrorBean systemErrorBean) {
		SystemErrorDataBean bean = new SystemErrorDataBean();
		bean.setErrorId(systemErrorBean.getErrorId());
		bean.setClassName(systemErrorBean.getClassName());
		bean.setMethodName(systemErrorBean.getMethodName());
		bean.setErrorMessage(systemErrorBean.getErrorMessage());
		bean.setErrorType(systemErrorBean.getErrorType());
		bean.setErrorSevirity(systemErrorBean.getErrorSevirity());
		bean.setErrorCount(systemErrorBean.getErrorCount());
		bean.setErrorInd(systemErrorBean.getErrorInd());
		bean.setDateTimeStamp(systemErrorBean.getDateTimeStamp());
		/*LocalDateTime dtTm = systemErrorBean.getDateTimeStamp().toInstant().atZone(ZoneId.of(Constants.TIME_ZONE)).toLocalDateTime();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_WITH_AM_PM);
		bean.setDateTimeStampStr(dtTm.format(dtf));*/
		SimpleDateFormat dtf = new SimpleDateFormat(Constants.DATE_PATTERN_WITH_AM_PM);
		bean.setDateTimeStampStr(dtf.format(systemErrorBean.getDateTimeStamp()));
		bean.setDateStamp(systemErrorBean.getDateStamp());
		return bean;
	}

	public SystemErrorBean mapSystemErrorDataBeanToSystemErrorBean(SystemErrorDataBean systemErrorDataBean) {
		SystemErrorBean bean = new SystemErrorBean();
		//bean.setErrorId(systemErrorDataBean.getErrorId());
		bean.setClassName(systemErrorDataBean.getClassName());
		bean.setMethodName(systemErrorDataBean.getMethodName());
		bean.setErrorMessage(systemErrorDataBean.getErrorMessage());
		bean.setErrorType(systemErrorDataBean.getErrorType());
		bean.setErrorSevirity(systemErrorDataBean.getErrorSevirity());
		bean.setErrorCount(systemErrorDataBean.getErrorCount());
		bean.setErrorInd(systemErrorDataBean.getErrorInd());
		/*ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)).atZone(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateTimeStamp(Date.from(zdt.toInstant()));
		bean.setDateStamp(Date.from(zdt.toInstant()));*/
		/*ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateStamp(Date.from(zdt.toInstant()));
		bean.setDateTimeStamp(Date.from(zdt.toInstant()));*/
		LocalDateTime ldt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateTimeStamp(Timestamp.valueOf(ldt));
		LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateStamp(java.sql.Date.valueOf(ld));
		return bean;
	}
	
	public static SymbolDataBean mapSymbolBeanToSymbolDataBean(SymbolBean symbolBean) {
		SymbolDataBean bean = new SymbolDataBean();
		bean.setSymbolId(symbolBean.getSymbolId());
		bean.setLotSize(symbolBean.getLotSize());
		bean.setOptionTickerSize(symbolBean.getOptionTickerSize());
		bean.setFnoInd(symbolBean.getFnoInd());
		bean.setEquityInd(symbolBean.getEquityInd());
		bean.setValidInd(symbolBean.getValidInd());
		bean.setIndexInd(symbolBean.getIndexInd());
		bean.setInstrumentToken(symbolBean.getInstrumentToken());
		bean.setCategoryFilter(symbolBean.getCategoryFilter());
		bean.setWeek52High(symbolBean.getWeek52High());
		bean.setWeek52Low(symbolBean.getWeek52Low());
		bean.setSector(symbolBean.getSector());
		bean.setDateTimeStamp(symbolBean.getDateTimeStamp());
		
		bean.setLastPrice(symbolBean.getLastPrice());
		bean.setYrHiLoNearInd(symbolBean.getYrHiLoNearInd() );
		bean.setYrHiLoNearPer(symbolBean.getYrHiLoNearPer());
		bean.setYrHiLoNearTrend(symbolBean.getYrHiLoNearTrend());
		bean.setClose9Sma(symbolBean.getClose9Sma());
		bean.setClose21Sma(symbolBean.getClose21Sma());
		bean.setClose50Sma(symbolBean.getClose50Sma());
		bean.setClose100Sma(symbolBean.getClose100Sma());
		bean.setClose200Sma(symbolBean.getClose200Sma());
		bean.setClose200SmaTradableRatio(symbolBean.getClose200SmaTradableRatio());
		bean.setIsClose200SmaTradable(symbolBean.getIsClose200SmaTradable());
		return bean;
	}

	public static SymbolBean mapSymbolDataBeanToSymbolBean(SymbolDataBean symbolDataBean) {
		SymbolBean bean = new SymbolBean();
		bean.setSymbolId(symbolDataBean.getSymbolId());
		bean.setLotSize(symbolDataBean.getLotSize());
		bean.setOptionTickerSize(symbolDataBean.getOptionTickerSize());
		bean.setFnoInd(symbolDataBean.getFnoInd());
		bean.setEquityInd(symbolDataBean.getEquityInd());
		bean.setValidInd(symbolDataBean.getValidInd());
		bean.setIndexInd(symbolDataBean.getIndexInd());
		bean.setInstrumentToken(symbolDataBean.getInstrumentToken());
		bean.setCategoryFilter(symbolDataBean.getCategoryFilter());
		bean.setWeek52High(symbolDataBean.getWeek52High());
		bean.setWeek52Low(symbolDataBean.getWeek52Low());
		bean.setSector(symbolDataBean.getSector());
		LocalDateTime ldt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateTimeStamp(Timestamp.valueOf(ldt));
		
		bean.setLastPrice(symbolDataBean.getLastPrice());
		bean.setYrHiLoNearInd(symbolDataBean.getYrHiLoNearInd() );
		bean.setYrHiLoNearPer(symbolDataBean.getYrHiLoNearPer());
		bean.setYrHiLoNearTrend(symbolDataBean.getYrHiLoNearTrend());
		bean.setClose9Sma(symbolDataBean.getClose9Sma());
		bean.setClose21Sma(symbolDataBean.getClose21Sma());
		bean.setClose50Sma(symbolDataBean.getClose50Sma());
		bean.setClose100Sma(symbolDataBean.getClose100Sma());
		bean.setClose200Sma(symbolDataBean.getClose200Sma());
		bean.setClose200SmaTradableRatio(symbolDataBean.getClose200SmaTradableRatio());
		bean.setIsClose200SmaTradable(symbolDataBean.getIsClose200SmaTradable());
		return bean;
	}
	
	
	//StrategyObrBean start
	public StrategyOrbBean mapStrategyOrbDataBeanToStrategyOrbBean(StrategyOrbDataBean rowBean) {
		StrategyOrbBean bean = new StrategyOrbBean();

		bean.setSymbolId(rowBean.getSymbolId());
		bean.setSymbolName(rowBean.getSymbolName());
		bean.setLotSize(rowBean.getLotSize());
		bean.setBuyAtVal(rowBean.getBuyAtVal());
		bean.setSellAtVal(rowBean.getSellAtVal());
		bean.setTradedAtVal(rowBean.getTradedAtVal());
		bean.setExitedAtVal(rowBean.getExitedAtVal());
		bean.setTradedAtDtTm(rowBean.getTradedAtDtTm());
		bean.setExitedAtDtTm(rowBean.getExitedAtDtTm());
		bean.setTradableStateId(rowBean.getTradableStateId());
		bean.setTradableStateDescription(rowBean.getTradableStateDescription());
		bean.setTradedStateId(rowBean.getTradedStateId());
		bean.setTradedStateDescription(rowBean.getTradedStateDescription());
		bean.setBuyerSellerDiffVal(rowBean.getBuyerSellerDiffVal());
		bean.setVwapValue(rowBean.getVwapValue());
		bean.setTradeType(rowBean.getTradeType());
		bean.setTradedLotCount(rowBean.getTradedLotCount());
		bean.setBuyerQuantityVal(rowBean.getBuyerQuantityVal());
		bean.setSellerQuantityVal(rowBean.getSellerQuantityVal());
		bean.setProfitLossAmtVal(rowBean.getProfitLossAmtVal());
		bean.setPositiveMoveVal(rowBean.getPositiveDirectionProfit());
		bean.setNegativeMoveVal(rowBean.getNegativeDirectionLoss());
		bean.setPositiveMoveLtp(rowBean.getPositiveDirectionLtp());
		bean.setNegativeMoveLtp(rowBean.getNegativeDirectionLtp());
		bean.setVolumes(rowBean.getVolumes());
		bean.setOhlcStateId(rowBean.getOhlcStateId());
		bean.setOhlcStateDescription(rowBean.getOhlcStateDescription());
		bean.setStrengthableTradeStateId(rowBean.getStrengthableTradeStateId());
		bean.setStrengthableTradeStateDescription(rowBean.getStrengthableTradeStateDescription());
		bean.setStrengthStyleClass(rowBean.getStrengthStyleClass());
		bean.setCandleNumber(rowBean.getCandleNumber());
		bean.setDayLevelTradeInd(rowBean.getDayLevelTradeInd());
		bean.setGapUpDownMoveVal(rowBean.getGapUpDownMoveVal());
		/*ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setTradedDateStamp(Date.from(zdt.toInstant()));*/
		/*LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setTradedDateStamp((Date)Timestamp.valueOf(ld.atStartOfDay()));*/
		LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setTradedDateStamp(java.sql.Date.valueOf(ld));
		bean.setHeikinAshiTrendId(rowBean.getHeikinAshiTrendId());
		bean.setHeikinAshiTrendDescription(rowBean.getHeikinAshiTrendDescription());
		
		bean.setBuySellQuantityRatio(rowBean.getBuySellQuantityRatio());
		bean.setPercentageChange(rowBean.getPercentageChange());
		bean.setStrategyRule(rowBean.getStrategyRule());
		bean.setCandleTimeFrame(rowBean.getCandleTimeFrame());
		
		bean.setCurrentCandleOpen(rowBean.getCurrentCandleOpen());
		bean.setBuyerSellerDiffVal2(rowBean.getBuyerSellerDiffVal2());
		bean.setBuyerSellerDiffVal3(rowBean.getBuyerSellerDiffVal3());
		
		bean.setTradeCountPerDay(rowBean.getTradeCountPerDay());
		bean.setProfitLossAmtValFinal(rowBean.getProfitLossAmtValFinal());
		bean.setReverseMotherCandleInd(rowBean.getReverseMotherCandleInd());
		
		//bean.setStrengthableTradeInfo(rowBean.getStrengthableTradeInfo()); //Need to think
		bean.setVwapTradeStateId(rowBean.getVwapTradeStateId());
		bean.setVwapTradeStateDescription(rowBean.getVwapTradeStateDescription());
		bean.setVolumeTradeStateId(rowBean.getVolumeTradeStateId());
		bean.setVolumeTradeStateDescription(rowBean.getVolumeTradeStateDescription());
		
		bean.setOptionChainTrend(rowBean.getOptionChainTrend());
		bean.setOptionChainPriceTrend(rowBean.getOptionChainPriceTrend());
		bean.setOptionChainId(rowBean.getOptionChainId());
		bean.setTargetPrice(rowBean.getTargetPrice());
		bean.setStopLoss(rowBean.getStopLoss());
		//bean.setOiInfo(rowBean.getoiInfo);
		
		// relations start
		// StrategyOrbPreviousDayHistEntity
		bean.setPrevDayHistId(rowBean.getPrevDayHistId());
		bean.setPrevDayHrBuyAtVal(rowBean.getPrevDayHrBuyAtVal());
		bean.setPrevDayHrSellAtVal(rowBean.getPrevDayHrSellAtVal());
		bean.setPrevDayHrCrossInd(rowBean.getPrevDayHrCrossInd());
		bean.setPrevDayHigh(rowBean.getPrevDayHigh());
		bean.setPrevDayLow(rowBean.getPrevDayLow());
		bean.setPrevDayLevelCrossInd(rowBean.getPrevDayLevelCrossInd());
		bean.setPrevDayPercentageChange(rowBean.getPrevDayPercentageChange());
		bean.setCandleTypeTrendId(rowBean.getCandleTypeTrendId());
		bean.setCandleTypeTrendDescription(rowBean.getCandleTypeTrendDescription());
		//StrategyOrbMultilevelTradingEntity
		bean.setMultiLevelTradeId(rowBean.getTradedLotCount());
		// bean.setTradedLotCount(rowBean.getTradedLotCount());
		bean.setTradedAtAvgVal(rowBean.getTradedAtAvgVal());
		bean.setTargetAmtVal(rowBean.getTargetAmtVal());
		bean.setTradedAtVal2(rowBean.getTradedAtVal2());
		bean.setTradedAtDtTm2(rowBean.getTradedAtDtTm2());
		bean.setTargetAmtVal2(rowBean.getTargetAmtVal2());
		// bean.setBuyerSellerDiffVal2(rowBean.getBuyerSellerDiffVal2());
		bean.setTradedAtVal3(rowBean.getTradedAtVal3());
		bean.setTradedAtDtTm3(rowBean.getTradedAtDtTm3());
		bean.setTargetAmtVal3(rowBean.getTargetAmtVal3());
		// bean.setBuyerSellerDiffVal3(rowBean.getBuyerSellerDiffVal3());
		
		//StrategyOrbPreviousCandleDeatilEntity
		bean.setPreviousCandleDeatilId(rowBean.getPreviousCandleDeatilId());
		bean.setCandle1SizeAmt(rowBean.getCandle1SizeAmt());
		bean.setCandle2SizeAmt(rowBean.getCandle2SizeAmt());
		bean.setCandle3SizeAmt(rowBean.getCandle3SizeAmt());
		bean.setCandle4SizeAmt(rowBean.getCandle4SizeAmt());
		bean.setCandleHighsDiff(rowBean.getCandleHighsDiff());
		bean.setCandleLowsDiff(rowBean.getCandleLowsDiff());
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
		//StrategyOrbPreviousCandleAverageHistEntity
		bean.setPreviousCandleAvgHistId(rowBean.getPreviousCandleAvgHistId());
		//bean.setCurrentCandleOpen(rowBean.getCurrentCandleOpen());
		bean.setAvgHigh1min(rowBean.getAvgHigh1min());
		bean.setAvgLow1min(rowBean.getAvgLow1min());
		bean.setAvgClose1min(rowBean.getAvgClose1min());
		bean.setAvgHighMinusClose1min(rowBean.getAvgHighMinusClose1min());
		bean.setAvgCloseMinusLow1min(rowBean.getAvgCloseMinusLow1min());
		bean.setAvgHigh5min(rowBean.getAvgHigh5min());
		bean.setAvgLow5min(rowBean.getAvgLow5min());
		bean.setAvgClose5min(rowBean.getAvgClose5min());
		bean.setAvgHighMinusClose5min(rowBean.getAvgHighMinusClose5min());
		bean.setAvgCloseMinusLow5min(rowBean.getAvgCloseMinusLow5min());
		bean.setMin5HeikinAshiTrendId(rowBean.getMin5HeikinAshiTrendId());
		bean.setMin5HeikinAshiTrendIdDescription(rowBean.getMin5HeikinAshiTrendIdDescription());
		//StrategyOrbPreviousCandleOHLCEntity
		bean.setPreviousCandleOhlcId(rowBean.getPreviousCandleOhlcId());
		bean.setOpenCandle1(rowBean.getOpenCandle1());
		bean.setHighCandle1(rowBean.getHighCandle1());
		bean.setLowCandle1(rowBean.getLowCandle1());
		bean.setCloseCandle1(rowBean.getCloseCandle1());
		bean.setOpenCandle2(rowBean.getOpenCandle2());
		bean.setHighCandle2(rowBean.getHighCandle2());
		bean.setLowCandle2(rowBean.getLowCandle2());
		bean.setCloseCandle2(rowBean.getCloseCandle2());
		bean.setOpenCandle3(rowBean.getOpenCandle3());
		bean.setHighCandle3(rowBean.getHighCandle3());
		bean.setLowCandle3(rowBean.getLowCandle3());
		bean.setCloseCandle3(rowBean.getCloseCandle3());
		bean.setOpenCandle4(rowBean.getOpenCandle4());
		bean.setHighCandle4(rowBean.getHighCandle4());
		bean.setLowCandle4(rowBean.getLowCandle4());
		bean.setCloseCandle4(rowBean.getCloseCandle4());
		//StrategyOrbVwapAndVolumeInfoEntity
		bean.setVwapVolumeDetailId(rowBean.getVwapVolumeDetailId());
		bean.setVwapValue(rowBean.getVwapValue());
		bean.setVolStrengthStyleClass(rowBean.getVolStrengthStyleClass());
		bean.setVolumeTradeStateId(rowBean.getVolumeTradeStateId());
		bean.setVolumeTradeStateDescription(rowBean.getVolumeTradeStateDescription());
		bean.setVwapTradeStateId(rowBean.getVwapTradeStateId());
		bean.setVwapTradeStateDescription(rowBean.getVwapTradeStateDescription());
		bean.setVolume4(rowBean.getVolume4());
		bean.setVolume3(rowBean.getVolume3() );
		bean.setVolume2(rowBean.getVolume2());
		bean.setVolume1(rowBean.getVolume1());
		bean.setSmallVolume(rowBean.getSmallVolume());
		bean.setVol4Ratio(rowBean.getVol4Ratio());
		bean.setVol3Ratio(rowBean.getVol3Ratio());
		bean.setVol2Ratio(rowBean.getVol2Ratio());
		bean.setVol1Ratio(rowBean.getVol1Ratio());
		
		bean.setVwapTradeVolInfo(rowBean.getVwapTradeVolInfo());
		bean.setVwapTradeVolRatioInfo(rowBean.getVwapTradeVolRatioInfo());
		
		// StrategyOrbTradingRuleEntity
		bean.setTradePlacedRuleInd(rowBean.getTradePlacedRuleInd());
		bean.setTradePlaceRule(rowBean.getTradePlaceRule());
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd(rowBean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd());
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd(rowBean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd());
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd(
				rowBean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd());
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd(
				rowBean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd());
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd(
				rowBean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd());
		bean.setTradingRuleOHLCAnd3TimesStrengthInd(rowBean.getTradingRuleOHLCAnd3TimesStrengthInd());
		bean.setTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd(
				rowBean.getTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd());
		bean.setTradingRuleVwapStrongInd(rowBean.getTradingRuleVwapStrongInd());
		bean.setTradingRuleVolumeStrengthInd(rowBean.getTradingRuleVolumeStrengthInd());
		bean.setTradingRule7TimesStrengthInd(rowBean.getTradingRule7TimesStrengthInd());
		bean.setTradingRuleOpenBtwnAvgHiLoClsAndVwapInd(rowBean.getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd());
		bean.setTradingRuleCandleTypeTrendAndPrevHrCrossInd(rowBean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd());
		bean.setTradingRuleVolumeTradeStateInd(rowBean.getTradingRuleVolumeTradeStateInd());
		bean.setTradingRuleProfitableCustomeStrongVolumeVwapInd(
				rowBean.getTradingRuleProfitableCustomeStrongVolumeVwapInd());
		bean.setTradingRuleForwardEngulfingInd(rowBean.getTradingRuleForwardEngulfingInd());
		bean.setTradingRuleReverseEngulfingInd(rowBean.getTradingRuleReverseEngulfingInd());
		bean.setTradingRuleForwardEngulfingLvl2Ind(rowBean.getTradingRuleForwardEngulfingLvl2Ind());
		bean.setTradingRuleForwardEngulfingLvl3Ind(rowBean.getTradingRuleForwardEngulfingLvl3Ind());
				
		//CPR - StrategyOrbTechnicalIndicatorEntity
		bean.setCprLowerBound(rowBean.getCprLowerBound());
		bean.setCprPivotalPoint(rowBean.getCprPivotalPoint());
		bean.setCprUpperBound(rowBean.getCprUpperBound());
		bean.setValueCPR(rowBean.getValueCPR());
		// relations END
		//bean.setLastCndleOhlcVal(rowBean.getLastCndleOhlcVal());
		
		// 04-21-2021 start - afterSomeSuccess
		bean.setManualTradeExitInd(rowBean.getManualTradeExitInd());
		bean.setManualExitedAtVal(rowBean.getManualExitedAtVal());
		bean.setManualExitedAtDtTm(rowBean.getManualExitedAtDtTm());
		bean.setManualBookProfitLossAmtVal(rowBean.getManualBookProfitLossAmtVal());
		bean.setManualTradeExitStateId(rowBean.getManualTradeExitStateId());
		bean.setManualTradeExitStateDescription(rowBean.getManualTradeExitStateDescription());
		bean.setWaitForEngulfingCount(rowBean.getWaitForEngulfingCount());
		// 04-21-2021 end
		
		bean.setMin60StochasticValK1(rowBean.getMin60StochasticValK1());
		bean.setMin60StochasticValD3(rowBean.getMin60StochasticValD3());
		bean.setMin60StochasticValK2(rowBean.getMin60StochasticValK2());
		bean.setMin60StochasticValD4(rowBean.getMin60StochasticValD4());
		bean.setMin60StochasticValK3(rowBean.getMin60StochasticValK3());
		bean.setMin60StochasticValD5(rowBean.getMin60StochasticValD5());
		bean.setMin60StochasticTrend(rowBean.getMin60StochasticTrend());
		bean.setMin15StochasticValK1(rowBean.getMin15StochasticValK1());
		bean.setMin15StochasticValD3(rowBean.getMin15StochasticValD3());
		bean.setMin15StochasticValK2(rowBean.getMin15StochasticValK2());
		bean.setMin15StochasticValD4(rowBean.getMin15StochasticValD4());
		bean.setMin15StochasticValK3(rowBean.getMin15StochasticValK3());
		bean.setMin15StochasticValD5(rowBean.getMin15StochasticValD5());
		bean.setMin15StochasticTrend(rowBean.getMin15StochasticTrend());
		bean.setMin5StochasticValK1(rowBean.getMin5StochasticValK1());
		bean.setMin5StochasticValD3(rowBean.getMin5StochasticValD3());
		bean.setMin5StochasticValK2(rowBean.getMin5StochasticValK2());
		bean.setMin5StochasticValD4(rowBean.getMin5StochasticValD4());
		bean.setMin5StochasticValK3(rowBean.getMin5StochasticValK3());
		bean.setMin5StochasticValD5(rowBean.getMin5StochasticValD5());
		bean.setMin5StochasticTrend(rowBean.getMin5StochasticTrend());
		bean.setMin5StochasticTrend(rowBean.getMin5StochasticTrend());
		bean.setStochasticBasicRule1Ind(rowBean.getStochasticBasicRule1Ind());
		
		bean.setHeikinAshiTrendIdMin60(rowBean.getHeikinAshiTrendIdMin60());
		bean.setHeikenAshiOpen3Min5(rowBean.getHeikenAshiOpen3Min5());
		bean.setHeikenAshiClose3Min5(rowBean.getHeikenAshiClose3Min5());
		bean.setHeikenAshiHigh3Min5(rowBean.getHeikenAshiHigh3Min5());
		bean.setHeikenAshiLow3Min5(rowBean.getHeikenAshiLow3Min5());
		bean.setHeikenAshiOpen2Min5(rowBean.getHeikenAshiOpen2Min5());
		bean.setHeikenAshiClose2Min5(rowBean.getHeikenAshiClose2Min5());
		bean.setHeikenAshiHigh2Min5(rowBean.getHeikenAshiHigh2Min5());
		bean.setHeikenAshiLow2Min5(rowBean.getHeikenAshiLow2Min5());
		bean.setHeikenAshiOpen1Min5(rowBean.getHeikenAshiOpen1Min5());
		bean.setHeikenAshiClose1Min5(rowBean.getHeikenAshiClose1Min5());
		bean.setHeikenAshiHigh1Min5(rowBean.getHeikenAshiHigh1Min5());
		bean.setHeikenAshiLow1Min5(rowBean.getHeikenAshiLow1Min5());
		
		bean.setHeikinAshiTrendIdMin15(rowBean.getHeikinAshiTrendIdMin15());
		bean.setHeikenAshiOpen3Min15(rowBean.getHeikenAshiOpen3Min15());
		bean.setHeikenAshiClose3Min15(rowBean.getHeikenAshiClose3Min15());
		bean.setHeikenAshiHigh3Min15(rowBean.getHeikenAshiHigh3Min15());
		bean.setHeikenAshiLow3Min15(rowBean.getHeikenAshiLow3Min15());
		bean.setHeikenAshiOpen2Min15(rowBean.getHeikenAshiOpen2Min15());
		bean.setHeikenAshiClose2Min15(rowBean.getHeikenAshiClose2Min15());
		bean.setHeikenAshiHigh2Min15(rowBean.getHeikenAshiHigh2Min15());
		bean.setHeikenAshiLow2Min15(rowBean.getHeikenAshiLow2Min15());
		bean.setHeikenAshiOpen1Min15(rowBean.getHeikenAshiOpen1Min15());
		bean.setHeikenAshiClose1Min15(rowBean.getHeikenAshiClose1Min15());
		bean.setHeikenAshiHigh1Min15(rowBean.getHeikenAshiHigh1Min15());
		bean.setHeikenAshiLow1Min15(rowBean.getHeikenAshiLow1Min15());

		bean.setHeikinAshiTrendIdMin5(rowBean.getHeikinAshiTrendIdMin5());
		bean.setHeikenAshiOpen3Min60(rowBean.getHeikenAshiOpen3Min60());
		bean.setHeikenAshiClose3Min60(rowBean.getHeikenAshiClose3Min60());
		bean.setHeikenAshiHigh3Min60(rowBean.getHeikenAshiHigh3Min60());
		bean.setHeikenAshiLow3Min60(rowBean.getHeikenAshiLow3Min60());
		bean.setHeikenAshiOpen2Min60(rowBean.getHeikenAshiOpen2Min60());
		bean.setHeikenAshiClose2Min60(rowBean.getHeikenAshiClose2Min60());
		bean.setHeikenAshiHigh2Min60(rowBean.getHeikenAshiHigh2Min60());
		bean.setHeikenAshiLow2Min60(rowBean.getHeikenAshiLow2Min60());
		bean.setHeikenAshiOpen1Min60(rowBean.getHeikenAshiOpen1Min60());
		bean.setHeikenAshiClose1Min60(rowBean.getHeikenAshiClose1Min60());
		bean.setHeikenAshiHigh1Min60(rowBean.getHeikenAshiHigh1Min60());
		bean.setHeikenAshiLow1Min60(rowBean.getHeikenAshiLow1Min60());
		bean.setTrendTradableStateId(rowBean.getTrendTradableStateId());
		
		return bean;
	}

	public StrategyOrbDataBean mapStrategyOrbBeanToStrategyOrbDataBean(StrategyOrbBean rowBean) {
		StrategyOrbDataBean bean = new StrategyOrbDataBean(rowBean.getLotSize(), rowBean.getSymbolId());
		
		bean.setItemId(rowBean.getItemId());
		bean.setSymbolId(rowBean.getSymbolId());
		bean.setSymbolName(rowBean.getSymbolName());
		bean.setLotSize(rowBean.getLotSize());
		bean.setBuyAtVal(rowBean.getBuyAtVal());
		bean.setSellAtVal(rowBean.getSellAtVal());
		bean.setPrevDayHrBuyAtVal(rowBean.getPrevDayHrBuyAtVal());
		bean.setPrevDayHrSellAtVal(rowBean.getPrevDayHrSellAtVal());
		bean.setPrevDayHrCrossInd(rowBean.getPrevDayHrCrossInd());
		bean.setTradedAtVal(rowBean.getTradedAtVal());
		bean.setExitedAtVal(rowBean.getExitedAtVal());
		bean.setTradedAtDtTm(rowBean.getTradedAtDtTm());
		bean.setExitedAtDtTm(rowBean.getExitedAtDtTm());
		bean.setTradableStateId(rowBean.getTradableStateId());
		bean.setTradableStateDescription(rowBean.getTradableStateDescription());
		bean.setTradedStateId(rowBean.getTradedStateId());
		bean.setTradedStateDescription(rowBean.getTradedStateDescription());
		bean.setBuyerSellerDiffVal(rowBean.getBuyerSellerDiffVal());
		bean.setVwapValue(rowBean.getVwapValue());
		bean.setTradeType(rowBean.getTradeType());
		bean.setTradedLotCount(rowBean.getTradedLotCount());
		bean.setTradedAtAvgVal(rowBean.getTradedAtAvgVal());
		bean.setTargetAmtVal(rowBean.getTargetAmtVal());
		bean.setBuyerQuantityVal(rowBean.getBuyerQuantityVal());
		bean.setSellerQuantityVal(rowBean.getSellerQuantityVal());
		bean.setProfitLossAmtVal(rowBean.getProfitLossAmtVal());
		bean.setPositiveDirectionProfit(rowBean.getPositiveMoveVal());
		bean.setNegativeDirectionLoss(rowBean.getNegativeMoveVal());
		bean.setPositiveDirectionLtp(rowBean.getPositiveMoveLtp());
		bean.setNegativeDirectionLtp(rowBean.getNegativeMoveLtp());
		bean.setVolumes(rowBean.getVolumes());
		bean.setTargetPrice(rowBean.getTargetPrice());
		bean.setStopLoss(rowBean.getStopLoss());
		bean.setReverseMotherCandleInd(rowBean.getReverseMotherCandleInd());
		bean.setCandle1SizeAmt(rowBean.getCandle1SizeAmt());
		bean.setCandle2SizeAmt(rowBean.getCandle2SizeAmt());
		bean.setCandle3SizeAmt(rowBean.getCandle3SizeAmt());
		bean.setCandle4SizeAmt(rowBean.getCandle4SizeAmt());
		bean.setCandleHighsDiff(rowBean.getCandleHighsDiff());
		bean.setCandleLowsDiff(rowBean.getCandleLowsDiff());
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
		bean.setOhlcStateId(rowBean.getOhlcStateId());
		bean.setOhlcStateDescription(rowBean.getOhlcStateDescription());
		bean.setStrengthableTradeStateId(rowBean.getStrengthableTradeStateId());
		bean.setStrengthableTradeStateDescription(rowBean.getStrengthableTradeStateDescription());
		bean.setStrengthStyleClass(rowBean.getStrengthStyleClass());
		bean.setCandleNumber(rowBean.getCandleNumber());
		bean.setDayLevelTradeInd(rowBean.getDayLevelTradeInd());
		bean.setGapUpDownMoveVal(rowBean.getGapUpDownMoveVal());
		bean.setTradedDateStamp(rowBean.getTradedDateStamp());
		bean.setHeikinAshiTrendId(rowBean.getHeikinAshiTrendId());
		bean.setHeikinAshiTrendDescription(rowBean.getHeikinAshiTrendDescription());
		bean.setOptionChainTrend(rowBean.getOptionChainTrend());
		bean.setOptionChainPriceTrend(rowBean.getOptionChainPriceTrend());
		bean.setOptionChainId(rowBean.getOptionChainId());
		bean.setBuySellQuantityRatio(rowBean.getBuySellQuantityRatio());
		bean.setPercentageChange(rowBean.getPercentageChange());
		bean.setStrategyRule(rowBean.getStrategyRule());
		bean.setCandleTimeFrame(rowBean.getCandleTimeFrame());
		bean.setVolStrengthStyleClass(rowBean.getVolStrengthStyleClass());
		bean.setAvgHigh1min(rowBean.getAvgHigh1min());
		bean.setAvgLow1min(rowBean.getAvgLow1min());
		bean.setAvgClose1min(rowBean.getAvgClose1min());
		bean.setCurrentCandleOpen(rowBean.getCurrentCandleOpen());
		bean.setAvgHighMinusClose1min(rowBean.getAvgHighMinusClose1min());
		bean.setAvgCloseMinusLow1min(rowBean.getAvgCloseMinusLow1min());
		bean.setAvgHigh5min(rowBean.getAvgHigh5min());
		bean.setAvgLow5min(rowBean.getAvgLow5min());
		bean.setAvgClose5min(rowBean.getAvgClose5min());
		bean.setAvgHighMinusClose5min(rowBean.getAvgHighMinusClose5min());
		bean.setAvgCloseMinusLow5min(rowBean.getAvgCloseMinusLow5min());
		bean.setMin5HeikinAshiTrendId(rowBean.getMin5HeikinAshiTrendId());
		bean.setMin5HeikinAshiTrendIdDescription(rowBean.getMin5HeikinAshiTrendIdDescription());
		bean.setTradedAtVal2(rowBean.getTradedAtVal2());
		bean.setTradedAtDtTm2(rowBean.getTradedAtDtTm2());
		bean.setTargetAmtVal2(rowBean.getTargetAmtVal2());
		bean.setBuyerSellerDiffVal2(rowBean.getBuyerSellerDiffVal2());
		bean.setTradedAtVal3(rowBean.getTradedAtVal3());
		bean.setTradedAtDtTm3(rowBean.getTradedAtDtTm3());
		bean.setTargetAmtVal3(rowBean.getTargetAmtVal3());
		bean.setBuyerSellerDiffVal3(rowBean.getBuyerSellerDiffVal3());
		bean.setTradeCountPerDay(rowBean.getTradeCountPerDay());
		bean.setProfitLossAmtValFinal(rowBean.getProfitLossAmtValFinal());
		
		bean.setPrevDayHigh(rowBean.getPrevDayHigh());
		bean.setPrevDayLow(rowBean.getPrevDayLow());
		bean.setPrevDayPercentageChange(rowBean.getPrevDayPercentageChange());
		bean.setCandleTypeTrendId(rowBean.getCandleTypeTrendId());
		bean.setCandleTypeTrendDescription(rowBean.getCandleTypeTrendDescription());
		bean.setStrengthableTradeInfo(rowBean.getStrengthableTradeInfo());
		bean.setVwapTradeStateId(rowBean.getVwapTradeStateId());
		bean.setVwapTradeStateDescription(rowBean.getVwapTradeStateDescription());
		bean.setVwapTradeVolInfo(rowBean.getVwapTradeVolInfo());
		bean.setVwapTradeVolRatioInfo(rowBean.getVwapTradeVolRatioInfo());
		//bean.setLastCndleOhlcVal(rowBean.getLastCndleOhlcVal());
		bean.setVolumeTradeStateId(rowBean.getVolumeTradeStateId());
		bean.setVolumeTradeStateDescription(rowBean.getVolumeTradeStateDescription());
		
		// StrategyOrbTradingRuleEntity
		bean.setTradePlacedRuleInd(rowBean.getTradePlacedRuleInd());
		bean.setTradePlaceRule(rowBean.getTradePlaceRule());
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd(rowBean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl1RuleInd());
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd(rowBean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd());
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd(
				rowBean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleInd());
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd(
				rowBean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd());
		bean.setTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd(
				rowBean.getTradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd());
		bean.setTradingRuleOHLCAnd3TimesStrengthInd(rowBean.getTradingRuleOHLCAnd3TimesStrengthInd());
		bean.setTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd(
				rowBean.getTradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd());
		bean.setTradingRuleVwapStrongInd(rowBean.getTradingRuleVwapStrongInd());
		bean.setTradingRuleVolumeStrengthInd(rowBean.getTradingRuleVolumeStrengthInd());
		bean.setTradingRule7TimesStrengthInd(rowBean.getTradingRule7TimesStrengthInd());
		bean.setTradingRuleOpenBtwnAvgHiLoClsAndVwapInd(rowBean.getTradingRuleOpenBtwnAvgHiLoClsAndVwapInd());
		bean.setTradingRuleCandleTypeTrendAndPrevHrCrossInd(rowBean.getTradingRuleCandleTypeTrendAndPrevHrCrossInd());
		bean.setTradingRuleVolumeTradeStateInd(rowBean.getTradingRuleVolumeTradeStateInd());
		bean.setTradingRuleProfitableCustomeStrongVolumeVwapInd(
				rowBean.getTradingRuleProfitableCustomeStrongVolumeVwapInd());
		bean.setTradingRuleForwardEngulfingInd(rowBean.getTradingRuleForwardEngulfingInd());
		bean.setTradingRuleReverseEngulfingInd(rowBean.getTradingRuleReverseEngulfingInd());
		bean.setTradingRuleForwardEngulfingLvl2Ind(rowBean.getTradingRuleForwardEngulfingLvl2Ind());
		bean.setTradingRuleForwardEngulfingLvl3Ind(rowBean.getTradingRuleForwardEngulfingLvl3Ind());
		
		bean.setCprLowerBound(rowBean.getCprLowerBound());
		bean.setCprPivotalPoint(rowBean.getCprPivotalPoint());
		bean.setCprUpperBound(rowBean.getCprUpperBound());
		bean.setValueCPR(rowBean.getValueCPR());
		
		// 04-21-2021 start - afterSomeSuccess
		bean.setManualTradeExitInd(rowBean.getManualTradeExitInd());
		bean.setManualExitedAtVal(rowBean.getManualExitedAtVal());
		bean.setManualExitedAtDtTm(rowBean.getManualExitedAtDtTm());
		bean.setManualBookProfitLossAmtVal(rowBean.getManualBookProfitLossAmtVal());
		bean.setManualTradeExitStateId(rowBean.getManualTradeExitStateId());
		bean.setManualTradeExitStateDescription(rowBean.getManualTradeExitStateDescription());
		bean.setWaitForEngulfingCount(rowBean.getWaitForEngulfingCount());
		// 04-21-2021 end
		
		bean.setMin60StochasticValK1(rowBean.getMin60StochasticValK1());
		bean.setMin60StochasticValD3(rowBean.getMin60StochasticValD3());
		bean.setMin60StochasticValK2(rowBean.getMin60StochasticValK2());
		bean.setMin60StochasticValD4(rowBean.getMin60StochasticValD4());
		bean.setMin60StochasticValK3(rowBean.getMin60StochasticValK3());
		bean.setMin60StochasticValD5(rowBean.getMin60StochasticValD5());
		bean.setMin60StochasticTrend(rowBean.getMin60StochasticTrend());
		bean.setMin15StochasticValK1(rowBean.getMin15StochasticValK1());
		bean.setMin15StochasticValD3(rowBean.getMin15StochasticValD3());
		bean.setMin15StochasticValK2(rowBean.getMin15StochasticValK2());
		bean.setMin15StochasticValD4(rowBean.getMin15StochasticValD4());
		bean.setMin15StochasticValK3(rowBean.getMin15StochasticValK3());
		bean.setMin15StochasticValD5(rowBean.getMin15StochasticValD5());
		bean.setMin15StochasticTrend(rowBean.getMin15StochasticTrend());
		bean.setMin5StochasticValK1(rowBean.getMin5StochasticValK1());
		bean.setMin5StochasticValD3(rowBean.getMin5StochasticValD3());
		bean.setMin5StochasticValK2(rowBean.getMin5StochasticValK2());
		bean.setMin5StochasticValD4(rowBean.getMin5StochasticValD4());
		bean.setMin5StochasticValK3(rowBean.getMin5StochasticValK3());
		bean.setMin5StochasticValD5(rowBean.getMin5StochasticValD5());
		bean.setMin5StochasticTrend(rowBean.getMin5StochasticTrend());
		bean.setStochasticBasicRule1Ind(rowBean.getStochasticBasicRule1Ind());
		
		bean.setHeikinAshiTrendIdMin60(rowBean.getHeikinAshiTrendIdMin60());
		bean.setHeikenAshiOpen3Min5(rowBean.getHeikenAshiOpen3Min5());
		bean.setHeikenAshiClose3Min5(rowBean.getHeikenAshiClose3Min5());
		bean.setHeikenAshiHigh3Min5(rowBean.getHeikenAshiHigh3Min5());
		bean.setHeikenAshiLow3Min5(rowBean.getHeikenAshiLow3Min5());
		bean.setHeikenAshiOpen2Min5(rowBean.getHeikenAshiOpen2Min5());
		bean.setHeikenAshiClose2Min5(rowBean.getHeikenAshiClose2Min5());
		bean.setHeikenAshiHigh2Min5(rowBean.getHeikenAshiHigh2Min5());
		bean.setHeikenAshiLow2Min5(rowBean.getHeikenAshiLow2Min5());
		bean.setHeikenAshiOpen1Min5(rowBean.getHeikenAshiOpen1Min5());
		bean.setHeikenAshiClose1Min5(rowBean.getHeikenAshiClose1Min5());
		bean.setHeikenAshiHigh1Min5(rowBean.getHeikenAshiHigh1Min5());
		bean.setHeikenAshiLow1Min5(rowBean.getHeikenAshiLow1Min5());
		
		bean.setHeikinAshiTrendIdMin15(rowBean.getHeikinAshiTrendIdMin15());
		bean.setHeikenAshiOpen3Min15(rowBean.getHeikenAshiOpen3Min15());
		bean.setHeikenAshiClose3Min15(rowBean.getHeikenAshiClose3Min15());
		bean.setHeikenAshiHigh3Min15(rowBean.getHeikenAshiHigh3Min15());
		bean.setHeikenAshiLow3Min15(rowBean.getHeikenAshiLow3Min15());
		bean.setHeikenAshiOpen2Min15(rowBean.getHeikenAshiOpen2Min15());
		bean.setHeikenAshiClose2Min15(rowBean.getHeikenAshiClose2Min15());
		bean.setHeikenAshiHigh2Min15(rowBean.getHeikenAshiHigh2Min15());
		bean.setHeikenAshiLow2Min15(rowBean.getHeikenAshiLow2Min15());
		bean.setHeikenAshiOpen1Min15(rowBean.getHeikenAshiOpen1Min15());
		bean.setHeikenAshiClose1Min15(rowBean.getHeikenAshiClose1Min15());
		bean.setHeikenAshiHigh1Min15(rowBean.getHeikenAshiHigh1Min15());
		bean.setHeikenAshiLow1Min15(rowBean.getHeikenAshiLow1Min15());

		bean.setHeikinAshiTrendIdMin5(rowBean.getHeikinAshiTrendIdMin5());
		bean.setHeikenAshiOpen3Min60(rowBean.getHeikenAshiOpen3Min60());
		bean.setHeikenAshiClose3Min60(rowBean.getHeikenAshiClose3Min60());
		bean.setHeikenAshiHigh3Min60(rowBean.getHeikenAshiHigh3Min60());
		bean.setHeikenAshiLow3Min60(rowBean.getHeikenAshiLow3Min60());
		bean.setHeikenAshiOpen2Min60(rowBean.getHeikenAshiOpen2Min60());
		bean.setHeikenAshiClose2Min60(rowBean.getHeikenAshiClose2Min60());
		bean.setHeikenAshiHigh2Min60(rowBean.getHeikenAshiHigh2Min60());
		bean.setHeikenAshiLow2Min60(rowBean.getHeikenAshiLow2Min60());
		bean.setHeikenAshiOpen1Min60(rowBean.getHeikenAshiOpen1Min60());
		bean.setHeikenAshiClose1Min60(rowBean.getHeikenAshiClose1Min60());
		bean.setHeikenAshiHigh1Min60(rowBean.getHeikenAshiHigh1Min60());
		bean.setHeikenAshiLow1Min60(rowBean.getHeikenAshiLow1Min60());
		bean.setTrendTradableStateId(rowBean.getTrendTradableStateId());
		
		prepareTradeInfo(bean);
		return bean;
	}
	
	public StrategyOrbDataBean mapStrategyOrbMonthlyBeanToStrategyOrbDataBean(StrategyOrbBean rowBean) {
		StrategyOrbDataBean bean = new StrategyOrbDataBean(rowBean.getLotSize(), rowBean.getSymbolId());
		bean.setTradeCountPerDay(rowBean.getTradeCountPerDay());
		bean.setProfitLossAmtVal(rowBean.getProfitLossAmtVal());
		bean.setProfitLossAmtValFinal(rowBean.getProfitLossAmtValFinal());
		bean.setTradedDateStamp(rowBean.getTradedDateStamp());
		return bean;
	}

	private StringBuffer sb = new StringBuffer();
	private StringBuffer sbExit = new StringBuffer();

	// private String BUY = "BUY";
	// private String SELL = "SELL";
	private void prepareTradeInfo(StrategyOrbDataBean bean) {
		clearBuffer();
		if (null != bean.getTradedStateId()) {
			if (bean.getTradableStateId().equals(Constants.BUY)) {
				sb.append(tradewareTool.getCurrentTime(bean.getTradedAtDtTm())).append(Constants.BUY_ACTIVATE)
						.append(bean.getTradedAtVal());
				if (bean.getTradedStateId().equals(Constants.BUY_EXIT_PROFIT)
						|| bean.getTradedStateId().equals(Constants.BUY_EXIT_PROFIT_FORCE)) {
					sbExit.append(tradewareTool.getCurrentTime(bean.getExitedAtDtTm())).append(Constants.BUY_TARGET)
							.append(bean.getExitedAtVal()).append(Constants.WITH_PROFIT)
							.append(bean.getProfitLossAmtVal());
				} else if (bean.getTradedStateId().equals(Constants.BUY_EXIT_LOSS)
						|| bean.getTradedStateId().equals(Constants.BUY_EXIT_LOSS_FORCE)) {
					sbExit.append(tradewareTool.getCurrentTime(bean.getExitedAtDtTm()))
							.append(Constants.CLOSED_WITH_LOSE).append(bean.getExitedAtVal())
							.append(Constants.WITH_LOSS).append(bean.getProfitLossAmtVal());
				}
			} else if (bean.getTradableStateId().equals(Constants.SELL)) {
				sb.append(tradewareTool.getCurrentTime(bean.getTradedAtDtTm())).append(Constants.SELL_ACTIVATE)
						.append(bean.getTradedAtVal());
				if (bean.getTradedStateId().contains(Constants.SELL_EXIT_PROFIT)
						|| bean.getTradedStateId().equals(Constants.SELL_EXIT_PROFIT_FORCE)) {
					sbExit.append(tradewareTool.getCurrentTime(bean.getExitedAtDtTm())).append(Constants.SELL_TARGET)
							.append(bean.getExitedAtVal()).append(Constants.WITH_PROFIT)
							.append(bean.getProfitLossAmtVal());
				} else if (bean.getTradedStateId().contains(Constants.SELL_EXIT_LOSS)
						|| bean.getTradedStateId().equals(Constants.SELL_EXIT_LOSS_FORCE)) {
					sbExit.append(tradewareTool.getCurrentTime(bean.getExitedAtDtTm()))
							.append(Constants.CLOSED_WITH_LOSE).append(bean.getExitedAtVal())
							.append(Constants.WITH_LOSS).append(bean.getProfitLossAmtVal());
				}
			} else {
				sb.append(tradewareTool.getCurrentTime(bean.getTradedAtDtTm()));
				sbExit.append(tradewareTool.getCurrentTime(bean.getTradedAtDtTm()));
			}
			
			if(bean.getTradedLotCount()>=2) {
				if (null == bean.getTradedAtDtTm2()) {
					bean.setTradedAtDtTm2(bean.getTradedAtDtTm());
				}
				if (null == bean.getTradedAtVal2()) {
					bean.setTradedAtVal2(bean.getTradedAtVal());
				}
				sb.append(Constants.COMMA_SPACE).append(tradewareTool.getCurrentTime(bean.getTradedAtDtTm2()))
				.append(Constants.AT).append(bean.getTradedAtVal2());
			}
			if(bean.getTradedLotCount()>=3) {
				if (null == bean.getTradedAtDtTm3()) {
					bean.setTradedAtDtTm3(bean.getTradedAtDtTm());
				}
				if (null == bean.getTradedAtVal3()) {
					bean.setTradedAtVal3(bean.getTradedAtVal());
				}
				sb.append(Constants.COMMA_SPACE).append(tradewareTool.getCurrentTime(bean.getTradedAtDtTm3()))
				.append(Constants.AT).append(bean.getTradedAtVal3());
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
	//StrateyOrbBean end
	
	
	
	
	
	
	
	
	static String DATE_PATTERN_WITH_AM_PM_3 = "hh:mm:ss a yyyy-MM-dd";
	public static ActivityAuditDataBean mapActivityAuditBeanToActivityAuditDataBean(ActivityAuditBean activityAuditBean) {
		ActivityAuditDataBean bean = new ActivityAuditDataBean();
		bean.setActivityId(activityAuditBean.getActivityId());
		bean.setActivityCategory(activityAuditBean.getActivityCategory());
		bean.setActivityDesc(activityAuditBean.getActivityDesc());
		
		SimpleDateFormat dtf = new SimpleDateFormat(DATE_PATTERN_WITH_AM_PM_3);
		bean.setDateTimeStampStr(dtf.format(activityAuditBean.getDateTimeStamp()));
		bean.setDateStamp(activityAuditBean.getDateStamp());
		
		bean.setDateTimeStamp(bean.getDateTimeStamp());
		LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateStamp(java.sql.Date.valueOf(ld));
		return bean;
	}

	public static ActivityAuditBean mapActivityAuditDataBeanToSActivityAuditBean(String category, String description) {
		ActivityAuditBean bean = new ActivityAuditBean();
		bean.setActivityCategory(category);
		bean.setActivityDesc(description);
		LocalDateTime ldt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateTimeStamp(Timestamp.valueOf(ldt));
		LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateStamp(java.sql.Date.valueOf(ld));
		return bean;
	}
	
	
	public static ProfitLossSummaryBean mapProfitLossSummaryDataBeanToProfitLossSummaryBean(
			ProfitLossSummaryDataBean rowBean) {
		ProfitLossSummaryBean bean = new ProfitLossSummaryBean();
		bean.setProfitLossSummaryId(rowBean.getProfitLossSummaryId());
		bean.setStrategyRule(rowBean.getStrategyRule());
		bean.setProfitLossBookedValueMinus5k(rowBean.getProfitLossBookedValueMinus5k());
		bean.setDateTimeStampMinus5k(rowBean.getDateTimeStampMinus5k());
		bean.setProfitLossBookedValue5k(rowBean.getProfitLossBookedValue5k());
		bean.setDateTimeStamp5k(rowBean.getDateTimeStamp5k());
		bean.setProfitLossBookedValue10k(rowBean.getProfitLossBookedValue10k());
		bean.setDateTimeStamp10k(rowBean.getDateTimeStamp10k());
		bean.setProfitLossBookedValue15k(rowBean.getProfitLossBookedValue15k());
		bean.setDateTimeStamp15k(rowBean.getDateTimeStamp15k());
		bean.setProfitLossBookedValue20k(rowBean.getProfitLossBookedValue20k());
		bean.setDateTimeStamp20k(rowBean.getDateTimeStamp20k());
		bean.setProfitLossBookedValue25k(rowBean.getProfitLossBookedValue25k());
		bean.setDateTimeStamp25k(rowBean.getDateTimeStamp25k());
		bean.setProfitLossBookedValue30k(rowBean.getProfitLossBookedValue30k());
		bean.setDateTimeStamp30k(rowBean.getDateTimeStamp30k());
		bean.setProfitLossBookedValue35k(rowBean.getProfitLossBookedValue35k());
		bean.setDateTimeStamp35k(rowBean.getDateTimeStamp35k());
		bean.setProfitLossBookedValue40k(rowBean.getProfitLossBookedValue40k());
		bean.setDateTimeStamp40k(rowBean.getDateTimeStamp40k());
		bean.setProfitLossBookedValue45k(rowBean.getProfitLossBookedValue45k());
		bean.setDateTimeStamp45k(rowBean.getDateTimeStamp45k());
		bean.setProfitLossBookedValue50k(rowBean.getProfitLossBookedValue50k());
		bean.setDateTimeStamp50k(rowBean.getDateTimeStamp50k());
		bean.setMaxProfitMoveValue(rowBean.getMaxProfitMoveValue());
		bean.setMaxLossMoveValue(rowBean.getMaxLossMoveValue());
		LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateStamp(java.sql.Date.valueOf(ld));

		bean.setDayProfitLossValue(rowBean.getDayProfitLossValue());
		bean.setDateTimeStampDayProfitLoss(rowBean.getDateTimeStampDayProfitLoss());
		bean.setScannerUpdatable(rowBean.getScannerUpdatable());
		bean.setTrailingStopLossValue(rowBean.getTrailingStopLossValue());
		return bean;
	}
	
	public static ProfitLossSummaryDataBean mapProfitLossSummaryBeanToProfitLossSummaryDataBean(
			ProfitLossSummaryBean rowBean) {
		ProfitLossSummaryDataBean bean = new ProfitLossSummaryDataBean();
		bean.setProfitLossSummaryId(rowBean.getProfitLossSummaryId());
		bean.setStrategyRule(rowBean.getStrategyRule());
		bean.setProfitLossBookedValueMinus5k(rowBean.getProfitLossBookedValueMinus5k());
		bean.setDateTimeStampMinus5k(rowBean.getDateTimeStampMinus5k());
		bean.setProfitLossBookedValue5k(rowBean.getProfitLossBookedValue5k());
		bean.setDateTimeStamp5k(rowBean.getDateTimeStamp5k());
		bean.setProfitLossBookedValue10k(rowBean.getProfitLossBookedValue10k());
		bean.setDateTimeStamp10k(rowBean.getDateTimeStamp10k());
		bean.setProfitLossBookedValue15k(rowBean.getProfitLossBookedValue15k());
		bean.setDateTimeStamp15k(rowBean.getDateTimeStamp15k());
		bean.setProfitLossBookedValue20k(rowBean.getProfitLossBookedValue20k());
		bean.setDateTimeStamp20k(rowBean.getDateTimeStamp20k());
		bean.setProfitLossBookedValue25k(rowBean.getProfitLossBookedValue25k());
		bean.setDateTimeStamp25k(rowBean.getDateTimeStamp25k());
		bean.setProfitLossBookedValue30k(rowBean.getProfitLossBookedValue30k());
		bean.setDateTimeStamp30k(rowBean.getDateTimeStamp30k());
		bean.setProfitLossBookedValue35k(rowBean.getProfitLossBookedValue35k());
		bean.setDateTimeStamp35k(rowBean.getDateTimeStamp35k());
		bean.setProfitLossBookedValue40k(rowBean.getProfitLossBookedValue40k());
		bean.setDateTimeStamp40k(rowBean.getDateTimeStamp40k());
		bean.setProfitLossBookedValue45k(rowBean.getProfitLossBookedValue45k());
		bean.setDateTimeStamp45k(rowBean.getDateTimeStamp45k());
		bean.setProfitLossBookedValue50k(rowBean.getProfitLossBookedValue50k());
		bean.setDateTimeStamp50k(rowBean.getDateTimeStamp50k());
		bean.setMaxProfitMoveValue(rowBean.getMaxProfitMoveValue());
		bean.setMaxLossMoveValue(rowBean.getMaxLossMoveValue());
		bean.setDateStamp(rowBean.getDateStamp());

		bean.setDayProfitLossValue(rowBean.getDayProfitLossValue());
		bean.setDateTimeStampDayProfitLoss(rowBean.getDateTimeStampDayProfitLoss());
		bean.setScannerUpdatable(rowBean.getScannerUpdatable());
		bean.setTrailingStopLossValue(rowBean.getTrailingStopLossValue());
		return bean;
	}
	
	// Monthly reports
	public StrategyOrbMonthlyReportDataBean mapMonthlyReportDataBean(StrategyOrbMonthlyReportBean monthlyReportBean) {
		StrategyOrbMonthlyReportDataBean bean = new StrategyOrbMonthlyReportDataBean();
		bean.setTradeCountPerDay(monthlyReportBean.getTradeCountPerDay());
		bean.setProfitLossAmtVal(monthlyReportBean.getProfitLossAmtVal());
		bean.setTradedDateStamp(monthlyReportBean.getTradedDateStamp());
		bean.setProfitLossAmtValFinal(monthlyReportBean.getProfitLossAmtValFinal());
		bean.setProfitLossAmtValManalFinal(monthlyReportBean.getProfitLossAmtValManalFinal());
		return bean;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*public OptionChainInfoBean mapToOptionChainInforBean(OptionChainDataBean optionChainDataBean) {
		OptionChainInfoBean bean = new OptionChainInfoBean();
		if(null !=optionChainDataBean.getOptionChainId()) {
		bean.setOptionChainId(optionChainDataBean.getOptionChainId());
		}
		bean.setSymbolId(optionChainDataBean.getSymbol());
		bean.setLotSize(optionChainDataBean.getLotSize());
		bean.setTickerSize(optionChainDataBean.getTickerSize());
		bean.setStyle(optionChainDataBean.getStyle());
		bean.setOITrend(optionChainDataBean.getOITrend());
		bean.setIndexInd(optionChainDataBean.getIndexInd());
		bean.setTop1OpenInterestCall(optionChainDataBean.getTop1OpenInterestCall());
		bean.setTop2OpenInterestCall(optionChainDataBean.getTop2OpenInterestCall());
		bean.setTop3OpenInterestCall(optionChainDataBean.getTop3OpenInterestCall());
		bean.setTop1OpenInterestChangeCall(optionChainDataBean.getTop1OpenInterestChangeCall());
		bean.setTop2OpenInterestChangeCall(optionChainDataBean.getTop2OpenInterestChangeCall());
		bean.setTop3OpenInterestChangeCall(optionChainDataBean.getTop3OpenInterestChangeCall());
		bean.setTop1OIVolumesCall(optionChainDataBean.getTop1OIVolumesCall());
		bean.setTop2OIVolumesCall(optionChainDataBean.getTop2OIVolumesCall());
		bean.setTop3OIVolumesCall(optionChainDataBean.getTop3OIVolumesCall());
		bean.setTop1OINetChangeCall(optionChainDataBean.getTop1OINetChangeCall());
		bean.setTop2OINetChangeCall(optionChainDataBean.getTop2OINetChangeCall());
		bean.setTop3OINetChangeCall(optionChainDataBean.getTop3OINetChangeCall());
		bean.setTop1StrikePriceCall(optionChainDataBean.getTop1StrikePriceCall());
		bean.setTop2StrikePriceCall(optionChainDataBean.getTop2StrikePriceCall());
		bean.setTop3StrikePriceCall(optionChainDataBean.getTop3StrikePriceCall());
		bean.setTop1OpenInterestPut(optionChainDataBean.getTop1OpenInterestPut());
		bean.setTop2OpenInterestPut(optionChainDataBean.getTop2OpenInterestPut());
		bean.setTop3OpenInterestPut(optionChainDataBean.getTop3OpenInterestPut());
		bean.setTop1OpenInterestChangePut(optionChainDataBean.getTop1OpenInterestChangePut());
		bean.setTop2OpenInterestChangePut(optionChainDataBean.getTop2OpenInterestChangePut());
		bean.setTop3OpenInterestChangePut(optionChainDataBean.getTop3OpenInterestChangePut());
		bean.setTop1OIVolumesPut(optionChainDataBean.getTop1OIVolumesPut());
		bean.setTop2OIVolumesPut(optionChainDataBean.getTop2OIVolumesPut());
		bean.setTop3OIVolumesPut(optionChainDataBean.getTop3OIVolumesPut());
		bean.setTop1OINetChangePut(optionChainDataBean.getTop1OINetChangePut());
		bean.setTop2OINetChangePut(optionChainDataBean.getTop2OINetChangePut());
		bean.setTop3OINetChangePut(optionChainDataBean.getTop3OINetChangePut());
		bean.setTop1StrikePricePut(optionChainDataBean.getTop1StrikePricePut());
		bean.setTop2StrikePricePut(optionChainDataBean.getTop2StrikePricePut());
		bean.setTop3StrikePricePut(optionChainDataBean.getTop3StrikePricePut());
		bean.setTotalOpenInterestCall(optionChainDataBean.getTotalOpenInterestCall());
		bean.setTotalOpenInterestPut(optionChainDataBean.getTotalOpenInterestPut());
		bean.setTotalOIVolumesCall(optionChainDataBean.getTotalOIVolumesCall());
		bean.setTotalOIVolumesPut(optionChainDataBean.getTotalOIVolumesPut());
		bean.setDay1OpenInterest(optionChainDataBean.getDay1OpenInterest());
		bean.setDay2OpenInterest(optionChainDataBean.getDay2OpenInterest());
		bean.setOpenInterestChange(optionChainDataBean.getOpenInterestChange());
		bean.setOpenInterestChangePercentage(optionChainDataBean.getOpenInterestChangePercentage());
		bean.setOpenInterestVolumes(optionChainDataBean.getOpenInterestVolumes());// contracts
		bean.setUnderlyingPrice(optionChainDataBean.getUnderlyingPrice());// current market price value
		bean.setTimeFrameNumber(optionChainDataBean.getTimeFrameNumber());
		if (optionChainDataBean.getDateTimeStamp() == null) {
		//ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)).atZone(ZoneId.of(Constants.TIME_ZONE));
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateTimeStamp(Date.from(zdt.toInstant()));
		//bean.setDateStamp(Date.from(zdt.toInstant()));
		} else {
			bean.setDateTimeStamp(optionChainDataBean.getDateTimeStamp());
			bean.setDateStamp(optionChainDataBean.getDateStamp());
		}
		if (optionChainDataBean.getDateStamp() == null) {
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateStamp(Date.from(zdt.toInstant()));
		}else {
			bean.setDateStamp(optionChainDataBean.getDateStamp());
		}
		
		if (optionChainDataBean.getDateTimeStamp() == null) {
			LocalDateTime ldt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
			bean.setDateTimeStamp(Timestamp.valueOf(ldt));
			} else {
				bean.setDateTimeStamp(optionChainDataBean.getDateTimeStamp());
				bean.setDateStamp(optionChainDataBean.getDateStamp());
			}
			if (optionChainDataBean.getDateStamp() == null) {
				LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
				bean.setDateStamp(java.sql.Date.valueOf(ld));
			}else {
				bean.setDateStamp(optionChainDataBean.getDateStamp());
			}
		
		bean.setDay1OpenInterest(optionChainDataBean.getDay1OpenInterest());
		bean.setDay2OpenInterest(optionChainDataBean.getDay2OpenInterest());
		bean.setOpenInterestChange(optionChainDataBean.getOpenInterestChange());
		bean.setOpenInterestChangePercentage(optionChainDataBean.getOpenInterestChangePercentage());
		bean.setOpenInterestVolumes(optionChainDataBean.getOpenInterestVolumes());// contracts
		bean.setUnderlyingPrice(optionChainDataBean.getUnderlyingPrice());// current market price value
		bean.setTimeFrameNumber(optionChainDataBean.getTimeFrameNumber());
		//bean.setDateTimeStamp(optionChainDataBean.getDateTimeStamp());
		//bean.setDateStamp(optionChainBean.getDateStamp());
		bean.setBestTradeInd(optionChainDataBean.getBestTradeInd());
		
		bean.setStrongOrder(optionChainDataBean.getStrongOrder());
		bean.setLtpOnDecision(optionChainDataBean.getLtpOnDecision());
		bean.setLastPrice(optionChainDataBean.getLtp());
		bean.setBestEntry(optionChainDataBean.getBestEntry());
		bean.setGoForTrade(optionChainDataBean.getGoForTrade());
		bean.setChangePercentage(optionChainDataBean.getChangePercentage());
		bean.setCandleNumber(optionChainDataBean.getCandleNumber());
		bean.setParentRecordInd(optionChainDataBean.getParentRecordInd());
		
		bean.setPreviousTop1OpenInterestCall(optionChainBean.getPreviousTop1OpenInterestCall());
		bean.setPreviousTop2OpenInterestCall(optionChainBean.getPreviousTop2OpenInterestCall());
		bean.setPreviousTop3OpenInterestCall(optionChainBean.getPreviousTop3OpenInterestCall());
		bean.setPreviousTop1OpenInterestPut(optionChainBean.getPreviousTop1OpenInterestPut());
		bean.setPreviousTop2OpenInterestPut(optionChainBean.getPreviousTop2OpenInterestPut());
		bean.setPreviousTop3OpenInterestPut(optionChainBean.getPreviousTop3OpenInterestPut());
		bean.setTop1OpenInterestCallStyle(optionChainDataBean.getTop1OpenInterestCallStyle());
		bean.setTop2OpenInterestCallStyle(optionChainDataBean.getTop2OpenInterestCallStyle());
		bean.setTop3OpenInterestCallStyle(optionChainDataBean.getTop3OpenInterestCallStyle());
		bean.setTop1OpenInterestPutStyle(optionChainDataBean.getTop1OpenInterestPutStyle());
		bean.setTop2OpenInterestPutStyle(optionChainDataBean.getTop2OpenInterestPutStyle());
		bean.setTop3OpenInterestPutStyle(optionChainDataBean.getTop3OpenInterestPutStyle());
		bean.setAttentionStyleBuy(optionChainDataBean.getAttentionStyleBuy());
		bean.setAttentionStyleSell(optionChainDataBean.getAttentionStyleSell());
		
		bean.setTotalOINetChangeCall(optionChainDataBean.getTotalOINetChangeCall());
		bean.setTotalOINetChangePut(optionChainDataBean.getTotalOINetChangePut());
		bean.setPriceMoveTrend(optionChainDataBean.getPriceMoveTrend());
		bean.setTotalOINetChangeCallStyle(optionChainDataBean.getTotalOINetChangeCallStyle());
		bean.setTotalOINetChangePutStyle(optionChainDataBean.getTotalOINetChangePutStyle());
		
		//System.out.println(optionChainDataBean.getSymbol()+ " - "+optionChainDataBean.getParentRecordInd());
		
		bean.setSortOrder(optionChainDataBean.getSortOrder());
		 bean.setTopTenGainLooseSortOrder(optionChainDataBean.getTopTenGainLooseSortOrder());
		 bean.setYearHigh(optionChainDataBean.getYearHigh());
		 bean.setYearLow(optionChainDataBean.getYearLow());
		 if (null != bean.getTotalOpenInterestCall() && null != bean.getTotalOpenInterestPut()) {
			 bean.setPutCallRatio(kiteConnectTool.getRoundupToTwoValue(bean.getTotalOpenInterestPut()/bean.getTotalOpenInterestCall()));
			 if (bean.getPutCallRatio() >= Constants.PCR_CRASH_DOWN_TREND) {
				 bean.setPutCallRatioStyleClass(Constants.RED_BOLD_FONT_STYLE_CLASS);
			 } else  if (bean.getPutCallRatio() <= Constants.PCR_UP_TREND) {
				 bean.setPutCallRatioStyleClass(Constants.GREEN_BOLD_FONT_STYLE_CLASS);
			 }
		 }
		 
		return bean;
	}*/
	
	public OptionChainDataBean mapToOptionChainInforBean( OptionChainInfoBean optionChainBean) {
		OptionChainDataBean bean = new OptionChainDataBean(optionChainBean.getSymbolId());
		try {
		bean.setOptionChainId(optionChainBean.getOptionChainId());
		bean.setSymbol(optionChainBean.getSymbolId());
		bean.setLotSize(optionChainBean.getLotSize());
		bean.setTickerSize(optionChainBean.getTickerSize());
		bean.setStyle(optionChainBean.getStyle());
		bean.setOITrend(optionChainBean.getOITrend());
		bean.setIndexInd(optionChainBean.getIndexInd());
		bean.setTop1OpenInterestCall(optionChainBean.getTop1OpenInterestCall());
		bean.setTop2OpenInterestCall(optionChainBean.getTop2OpenInterestCall());
		bean.setTop3OpenInterestCall(optionChainBean.getTop3OpenInterestCall());
		bean.setTop1OpenInterestChangeCall(optionChainBean.getTop1OpenInterestChangeCall());
		bean.setTop2OpenInterestChangeCall(optionChainBean.getTop2OpenInterestChangeCall());
		bean.setTop3OpenInterestChangeCall(optionChainBean.getTop3OpenInterestChangeCall());
		bean.setTop1OIVolumesCall(optionChainBean.getTop1OIVolumesCall());
		bean.setTop2OIVolumesCall(optionChainBean.getTop2OIVolumesCall());
		bean.setTop3OIVolumesCall(optionChainBean.getTop3OIVolumesCall());
		bean.setTop1OINetChangeCall(optionChainBean.getTop1OINetChangeCall());
		bean.setTop2OINetChangeCall(optionChainBean.getTop2OINetChangeCall());
		bean.setTop3OINetChangeCall(optionChainBean.getTop3OINetChangeCall());
		bean.setTop1StrikePriceCall(optionChainBean.getTop1StrikePriceCall());
		bean.setTop2StrikePriceCall(optionChainBean.getTop2StrikePriceCall());
		bean.setTop3StrikePriceCall(optionChainBean.getTop3StrikePriceCall());
		bean.setTop1OpenInterestPut(optionChainBean.getTop1OpenInterestPut());
		bean.setTop2OpenInterestPut(optionChainBean.getTop2OpenInterestPut());
		bean.setTop3OpenInterestPut(optionChainBean.getTop3OpenInterestPut());
		bean.setTop1OpenInterestChangePut(optionChainBean.getTop1OpenInterestChangePut());
		bean.setTop2OpenInterestChangePut(optionChainBean.getTop2OpenInterestChangePut());
		bean.setTop3OpenInterestChangePut(optionChainBean.getTop3OpenInterestChangePut());
		bean.setTop1OIVolumesPut(optionChainBean.getTop1OIVolumesPut());
		bean.setTop2OIVolumesPut(optionChainBean.getTop2OIVolumesPut());
		bean.setTop3OIVolumesPut(optionChainBean.getTop3OIVolumesPut());
		bean.setTop1OINetChangePut(optionChainBean.getTop1OINetChangePut());
		bean.setTop2OINetChangePut(optionChainBean.getTop2OINetChangePut());
		bean.setTop3OINetChangePut(optionChainBean.getTop3OINetChangePut());
		bean.setTop1StrikePricePut(optionChainBean.getTop1StrikePricePut());
		bean.setTop2StrikePricePut(optionChainBean.getTop2StrikePricePut());
		bean.setTop3StrikePricePut(optionChainBean.getTop3StrikePricePut());
		bean.setTotalOpenInterestCall(optionChainBean.getTotalOpenInterestCall());
		bean.setTotalOpenInterestPut(optionChainBean.getTotalOpenInterestPut());
		bean.setTotalOIVolumesCall(optionChainBean.getTotalOIVolumesCall());
		bean.setTotalOIVolumesPut(optionChainBean.getTotalOIVolumesPut());
		bean.setDay1OpenInterest(optionChainBean.getDay1OpenInterest());
		bean.setDay2OpenInterest(optionChainBean.getDay2OpenInterest());
		bean.setOpenInterestChange(optionChainBean.getOpenInterestChange());
		bean.setOpenInterestChangePercentage(optionChainBean.getOpenInterestChangePercentage());
		bean.setOpenInterestVolumes(optionChainBean.getOpenInterestVolumes());// contracts
		bean.setUnderlyingPrice(optionChainBean.getUnderlyingPrice());// current market price value
		bean.setTimeFrameNumber(optionChainBean.getTimeFrameNumber());
		//ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)).atZone(ZoneId.of(Constants.TIME_ZONE));
		//bean.setDateTimeStamp(optionChainBean.getDateTimeStamp());
		//bean.setDateStamp(optionChainBean.getDateStamp());
		
		bean.setDay1OpenInterest(optionChainBean.getDay1OpenInterest());
		bean.setDay2OpenInterest(optionChainBean.getDay2OpenInterest());
		bean.setOpenInterestChange(optionChainBean.getOpenInterestChange());
		bean.setOpenInterestChangePercentage(optionChainBean.getOpenInterestChangePercentage());
		bean.setOpenInterestVolumes(optionChainBean.getOpenInterestVolumes());// contracts
		bean.setUnderlyingPrice(optionChainBean.getUnderlyingPrice());// current market price value
		bean.setTimeFrameNumber(optionChainBean.getTimeFrameNumber());
		bean.setDateTimeStamp(optionChainBean.getDateTimeStamp());
		bean.setDateStamp(optionChainBean.getDateStamp());
		bean.setTimeStampStr(tradewareTool.getCurrentTimeAsString(optionChainBean.getDateTimeStamp()));
		bean.setBestTradeInd(optionChainBean.getBestTradeInd());
		
		bean.setStrongOrder(optionChainBean.getStrongOrder());
		bean.setLtpOnDecision(optionChainBean.getLtpOnDecision());
		bean.setLtp(optionChainBean.getLastPrice());
		bean.setBestEntry(optionChainBean.getBestEntry());
		bean.setGoForTrade(optionChainBean.getGoForTrade());
		//bean.setChangePercentage(optionChainBean.getChangePercentage());
		bean.setCandleNumber(optionChainBean.getCandleNumber());
		bean.setParentRecordInd(optionChainBean.getParentRecordInd());
		
		/*bean.setPreviousTop1OpenInterestCall(optionChainBean.getPreviousTop1OpenInterestCall());
		bean.setPreviousTop2OpenInterestCall(optionChainBean.getPreviousTop2OpenInterestCall());
		bean.setPreviousTop3OpenInterestCall(optionChainBean.getPreviousTop3OpenInterestCall());
		bean.setPreviousTop1OpenInterestPut(optionChainBean.getPreviousTop1OpenInterestPut());
		bean.setPreviousTop2OpenInterestPut(optionChainBean.getPreviousTop2OpenInterestPut());
		bean.setPreviousTop3OpenInterestPut(optionChainBean.getPreviousTop3OpenInterestPut());*/
		bean.setTop1OpenInterestCallStyle(optionChainBean.getTop1OpenInterestCallStyle());
		bean.setTop2OpenInterestCallStyle(optionChainBean.getTop2OpenInterestCallStyle());
		bean.setTop3OpenInterestCallStyle(optionChainBean.getTop3OpenInterestCallStyle());
		bean.setTop1OpenInterestPutStyle(optionChainBean.getTop1OpenInterestPutStyle());
		bean.setTop2OpenInterestPutStyle(optionChainBean.getTop2OpenInterestPutStyle());
		bean.setTop3OpenInterestPutStyle(optionChainBean.getTop3OpenInterestPutStyle());
		bean.setAttentionStyleBuy(optionChainBean.getAttentionStyleBuy());
		bean.setAttentionStyleSell(optionChainBean.getAttentionStyleSell());
		
			bean.setTotalOINetChangeCall(optionChainBean.getTotalOINetChangeCall());
			bean.setTotalOINetChangePut(optionChainBean.getTotalOINetChangePut());
		bean.setPriceMoveTrend(optionChainBean.getPriceMoveTrend());
		bean.setTotalOINetChangeCallStyle(optionChainBean.getTotalOINetChangeCallStyle());
		bean.setTotalOINetChangePutStyle(optionChainBean.getTotalOINetChangePutStyle());

		bean.setTotalOpenInterestChangeCall(optionChainBean.getTotalOpenInterestChangeCall());
		bean.setTotalOpenInterestChangePut(optionChainBean.getTotalOpenInterestChangePut());
	bean.setPriceOpenIntrestChangeTrend(optionChainBean.getPriceOpenIntrestChangeTrend());
	bean.setTotalOpenInterestChangeCallStyle(optionChainBean.getTotalOpenInterestChangeCallStyle());
	bean.setTotalOpenInterestChangePutStyle(optionChainBean.getTotalOpenInterestChangePutStyle());
	
	 bean.setTop1OpenInterestCallBidVal1(optionChainBean.getTop1OpenInterestCallBidVal1());
	 bean.setTop1OpenInterestCallAskVal1(optionChainBean.getTop1OpenInterestCallAskVal1());
	 bean.setTop1OpenInterestCallBidAskDiffVal1(optionChainBean.getTop1OpenInterestCallBidAskDiffVal1());
	 bean.setTop1OpenInterestCallBidAskAmtDiffVal1(optionChainBean.getTop1OpenInterestCallBidAskAmtDiffVal1());
	 bean.setTop1OpenInterestPutBidVal1(optionChainBean.getTop1OpenInterestPutBidVal1());
	 bean.setTop1OpenInterestPutAskVal1(optionChainBean.getTop1OpenInterestPutAskVal1());
	 bean.setTop1OpenInterestPutBidAskDiffVal1(optionChainBean.getTop1OpenInterestPutBidAskDiffVal1());
	 bean.setTop1OpenInterestPutBidAskAmtDiffVal1(optionChainBean.getTop1OpenInterestPutBidAskDiffAmtVal1());
	 bean.setTop2OpenInterestCallBidVal1(optionChainBean.getTop2OpenInterestCallBidVal1());
	 bean.setTop2OpenInterestCallAskVal1(optionChainBean.getTop2OpenInterestCallAskVal1());
	 bean.setTop2OpenInterestCallBidAskDiffVal1(optionChainBean.getTop2OpenInterestCallBidAskDiffVal1());
	 bean.setTop2OpenInterestCallBidAskAmtDiffVal1(optionChainBean.getTop2OpenInterestCallBidAskAmtDiffVal1());
	 bean.setTop2OpenInterestPutBidVal1(optionChainBean.getTop2OpenInterestPutBidVal1());
	 bean.setTop2OpenInterestPutAskVal1(optionChainBean.getTop2OpenInterestPutAskVal1());
	 bean.setTop2OpenInterestPutBidAskDiffVal1(optionChainBean.getTop2OpenInterestPutBidAskDiffVal1());
	 bean.setTop2OpenInterestPutBidAskAmtDiffVal1(optionChainBean.getTop2OpenInterestPutBidAskDiffAmtVal1());
	 bean.setTop3OpenInterestCallBidVal1(optionChainBean.getTop3OpenInterestCallBidVal1());
	 bean.setTop3OpenInterestCallAskVal1(optionChainBean.getTop3OpenInterestCallAskVal1());
	 bean.setTop3OpenInterestCallBidAskDiffVal1(optionChainBean.getTop3OpenInterestCallBidAskDiffVal1());
	 bean.setTop3OpenInterestCallBidAskAmtDiffVal1(optionChainBean.getTop3OpenInterestCallBidAskAmtDiffVal1());
	 bean.setTop3OpenInterestPutBidVal1(optionChainBean.getTop3OpenInterestPutBidVal1());
	 bean.setTop3OpenInterestPutAskVal1(optionChainBean.getTop3OpenInterestPutAskVal1());
	 bean.setTop3OpenInterestPutBidAskDiffVal1(optionChainBean.getTop3OpenInterestPutBidAskDiffVal1());
	 bean.setTop3OpenInterestPutBidAskAmtDiffVal1(optionChainBean.getTop3OpenInterestPutBidAskDiffAmtVal1());
	 bean.setTop1ImpliedVolatilityCall(optionChainBean.getTop1ImpliedVolatilityCall());
	 bean.setTop2ImpliedVolatilityCall(optionChainBean.getTop2ImpliedVolatilityCall());
	 bean.setTop3ImpliedVolatilityCall(optionChainBean.getTop3ImpliedVolatilityCall());
	 bean.setTop1ImpliedVolatilityPut(optionChainBean.getTop1ImpliedVolatilityPut());
	 bean.setTop2ImpliedVolatilityPut(optionChainBean.getTop2ImpliedVolatilityPut());
	 bean.setTop3ImpliedVolatilityPut(optionChainBean.getTop3ImpliedVolatilityPut());
	 bean.setTotalImpliedVolatilityCall(optionChainBean.getTotalImpliedVolatilityCall());
	 bean.setTotalImpliedVolatilityPut(optionChainBean.getTotalImpliedVolatilityPut());
	 bean.setTotalImpliedVolatilityCallStyle(optionChainBean.getTotalImpliedVolatilityCallStyle());
	 bean.setTotalImpliedVolatilityPutStyle(optionChainBean.getTotalImpliedVolatilityPutStyle());
		
		bean.setChangePercentage(optionChainBean.getChangePercentage());
		bean.setSortOrder(optionChainBean.getSortOrder());
		 bean.setYearHigh(optionChainBean.getYearHigh());
		 bean.setYearLow(optionChainBean.getYearLow());
		 bean.setPutCallRatio(optionChainBean.getPutCallRatio());
		 bean.setPutCallRatioStyleClass(optionChainBean.getPutCallRatioStyleClass());
		 
		bean.setTopTenGainLooseSortOrder(optionChainBean.getTopTenGainLooseSortOrder());
			if (null != optionChainBean.getChangePercentage() && optionChainBean.getChangePercentage() > 0) {
				if (null != optionChainBean.getTopTenGainLooseSortOrder()
						&& optionChainBean.getTopTenGainLooseSortOrder() <= 10) {
					bean.setSortOrderStyle(Constants.GREEN_BOLD_FONT_STYLE_CLASS);
				} else {
					bean.setSortOrderStyle(Constants.GREEN_FONT_STYLE_CLASS);
				}
			} else {
				if (null != optionChainBean.getTopTenGainLooseSortOrder()
						&& optionChainBean.getTopTenGainLooseSortOrder() >= -10
						&& optionChainBean.getTopTenGainLooseSortOrder() <= 0) {
					bean.setSortOrderStyle(Constants.RED_BOLD_FONT_STYLE_CLASS);
				} else {
					bean.setSortOrderStyle(Constants.RED_FONT_STYLE_CLASS);
				}
			}
 
		bean.handleSortOrderAndStyle();
		} catch(Exception e) {
			TradewareLogger.saveFatalErrorLog("DatabaseMapper", "mapToOptionChainInforBean "+bean.getSymbol(), e,
					Constants.ERROR_TYPE_EXCEPTION);
		}
		return bean;
	}
	
	
	
	
	
	
	
	
	
	
	
	public OptionChainInfoBean mapToOptionChainInforBean(OptionChainDataBean optionChainDataBean) {
		OptionChainInfoBean bean = new OptionChainInfoBean();
		try {
			TradewareLogger.sysoutPrintlnForLocalTest("1");
		if(null !=optionChainDataBean.getOptionChainId()) {
		bean.setOptionChainId(optionChainDataBean.getOptionChainId());
		}
		TradewareLogger.sysoutPrintlnForLocalTest("2");
		bean.setSymbolId(optionChainDataBean.getSymbol());
		TradewareLogger.sysoutPrintlnForLocalTest("2iii");
		bean.setLotSize(optionChainDataBean.getLotSize());
		TradewareLogger.sysoutPrintlnForLocalTest("3");
		bean.setTickerSize(optionChainDataBean.getTickerSize());
		TradewareLogger.sysoutPrintlnForLocalTest("3iii");
		bean.setStyle(optionChainDataBean.getStyle());
		TradewareLogger.sysoutPrintlnForLocalTest("4");
		bean.setOITrend(optionChainDataBean.getOITrend());
		TradewareLogger.sysoutPrintlnForLocalTest("5");
		bean.setIndexInd(optionChainDataBean.getIndexInd());
		TradewareLogger.sysoutPrintlnForLocalTest("6");
		bean.setTop1OpenInterestCall(optionChainDataBean.getTop1OpenInterestCall());
		TradewareLogger.sysoutPrintlnForLocalTest("7");
		bean.setTop2OpenInterestCall(optionChainDataBean.getTop2OpenInterestCall());
		TradewareLogger.sysoutPrintlnForLocalTest("8");
		bean.setTop3OpenInterestCall(optionChainDataBean.getTop3OpenInterestCall());
		TradewareLogger.sysoutPrintlnForLocalTest("9");
		bean.setTop1OpenInterestChangeCall(optionChainDataBean.getTop1OpenInterestChangeCall());
		TradewareLogger.sysoutPrintlnForLocalTest("10");
		bean.setTop2OpenInterestChangeCall(optionChainDataBean.getTop2OpenInterestChangeCall());
		TradewareLogger.sysoutPrintlnForLocalTest("11");
		bean.setTop3OpenInterestChangeCall(optionChainDataBean.getTop3OpenInterestChangeCall());
		TradewareLogger.sysoutPrintlnForLocalTest("12");
		bean.setTop1OIVolumesCall(optionChainDataBean.getTop1OIVolumesCall());
		TradewareLogger.sysoutPrintlnForLocalTest("13");
		bean.setTop2OIVolumesCall(optionChainDataBean.getTop2OIVolumesCall());
		TradewareLogger.sysoutPrintlnForLocalTest("14");
		bean.setTop3OIVolumesCall(optionChainDataBean.getTop3OIVolumesCall());
		TradewareLogger.sysoutPrintlnForLocalTest("15");
		bean.setTop1OINetChangeCall(optionChainDataBean.getTop1OINetChangeCall());
		TradewareLogger.sysoutPrintlnForLocalTest("16");
		bean.setTop2OINetChangeCall(optionChainDataBean.getTop2OINetChangeCall());
		TradewareLogger.sysoutPrintlnForLocalTest("17");
		bean.setTop3OINetChangeCall(optionChainDataBean.getTop3OINetChangeCall());
		TradewareLogger.sysoutPrintlnForLocalTest("18");
		bean.setTop1StrikePriceCall(optionChainDataBean.getTop1StrikePriceCall());
		TradewareLogger.sysoutPrintlnForLocalTest("19");
		bean.setTop2StrikePriceCall(optionChainDataBean.getTop2StrikePriceCall());
		TradewareLogger.sysoutPrintlnForLocalTest("20");
		bean.setTop3StrikePriceCall(optionChainDataBean.getTop3StrikePriceCall());
		TradewareLogger.sysoutPrintlnForLocalTest("21");
		bean.setTop1OpenInterestPut(optionChainDataBean.getTop1OpenInterestPut());
		TradewareLogger.sysoutPrintlnForLocalTest("22");
		bean.setTop2OpenInterestPut(optionChainDataBean.getTop2OpenInterestPut());
		TradewareLogger.sysoutPrintlnForLocalTest("23");
		bean.setTop3OpenInterestPut(optionChainDataBean.getTop3OpenInterestPut());
		TradewareLogger.sysoutPrintlnForLocalTest("24");
		bean.setTop1OpenInterestChangePut(optionChainDataBean.getTop1OpenInterestChangePut());
		TradewareLogger.sysoutPrintlnForLocalTest("25");
		bean.setTop2OpenInterestChangePut(optionChainDataBean.getTop2OpenInterestChangePut());
		TradewareLogger.sysoutPrintlnForLocalTest("26");
		bean.setTop3OpenInterestChangePut(optionChainDataBean.getTop3OpenInterestChangePut());
		TradewareLogger.sysoutPrintlnForLocalTest("27");
		bean.setTop1OIVolumesPut(optionChainDataBean.getTop1OIVolumesPut());
		TradewareLogger.sysoutPrintlnForLocalTest("28");
		bean.setTop2OIVolumesPut(optionChainDataBean.getTop2OIVolumesPut());
		TradewareLogger.sysoutPrintlnForLocalTest("29");
		bean.setTop3OIVolumesPut(optionChainDataBean.getTop3OIVolumesPut());
		TradewareLogger.sysoutPrintlnForLocalTest("30");
		bean.setTop1OINetChangePut(optionChainDataBean.getTop1OINetChangePut());
		TradewareLogger.sysoutPrintlnForLocalTest("31");
		bean.setTop2OINetChangePut(optionChainDataBean.getTop2OINetChangePut());
		TradewareLogger.sysoutPrintlnForLocalTest("32");
		bean.setTop3OINetChangePut(optionChainDataBean.getTop3OINetChangePut());
		TradewareLogger.sysoutPrintlnForLocalTest("33");
		bean.setTop1StrikePricePut(optionChainDataBean.getTop1StrikePricePut());
		TradewareLogger.sysoutPrintlnForLocalTest("34");
		bean.setTop2StrikePricePut(optionChainDataBean.getTop2StrikePricePut());
		TradewareLogger.sysoutPrintlnForLocalTest("35");
		bean.setTop3StrikePricePut(optionChainDataBean.getTop3StrikePricePut());
		TradewareLogger.sysoutPrintlnForLocalTest("36");
		bean.setTotalOpenInterestCall(optionChainDataBean.getTotalOpenInterestCall());
		TradewareLogger.sysoutPrintlnForLocalTest("37");
		bean.setTotalOpenInterestPut(optionChainDataBean.getTotalOpenInterestPut());
		TradewareLogger.sysoutPrintlnForLocalTest("38");
		bean.setTotalOIVolumesCall(optionChainDataBean.getTotalOIVolumesCall());
		TradewareLogger.sysoutPrintlnForLocalTest("39");
		bean.setTotalOIVolumesPut(optionChainDataBean.getTotalOIVolumesPut());
		TradewareLogger.sysoutPrintlnForLocalTest("40");
		bean.setDay1OpenInterest(optionChainDataBean.getDay1OpenInterest());
		TradewareLogger.sysoutPrintlnForLocalTest("41");
		bean.setDay2OpenInterest(optionChainDataBean.getDay2OpenInterest());
		TradewareLogger.sysoutPrintlnForLocalTest("42");
		bean.setOpenInterestChange(optionChainDataBean.getOpenInterestChange());
		TradewareLogger.sysoutPrintlnForLocalTest("43");
		bean.setOpenInterestChangePercentage(optionChainDataBean.getOpenInterestChangePercentage());
		TradewareLogger.sysoutPrintlnForLocalTest("44");
		bean.setOpenInterestVolumes(optionChainDataBean.getOpenInterestVolumes());// contracts
		TradewareLogger.sysoutPrintlnForLocalTest("45");
		bean.setUnderlyingPrice(optionChainDataBean.getUnderlyingPrice());// current market price value
		TradewareLogger.sysoutPrintlnForLocalTest("46");
		bean.setTimeFrameNumber(optionChainDataBean.getTimeFrameNumber());
		/*if (optionChainDataBean.getDateTimeStamp() == null) {
		//ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)).atZone(ZoneId.of(Constants.TIME_ZONE));
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateTimeStamp(Date.from(zdt.toInstant()));
		//bean.setDateStamp(Date.from(zdt.toInstant()));
		} else {
			bean.setDateTimeStamp(optionChainDataBean.getDateTimeStamp());
			bean.setDateStamp(optionChainDataBean.getDateStamp());
		}
		if (optionChainDataBean.getDateStamp() == null) {
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateStamp(Date.from(zdt.toInstant()));
		}else {
			bean.setDateStamp(optionChainDataBean.getDateStamp());
		}*/
		
		if (optionChainDataBean.getDateTimeStamp() == null) {
			LocalDateTime ldt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE));
			TradewareLogger.sysoutPrintlnForLocalTest("47");
			bean.setDateTimeStamp(Timestamp.valueOf(ldt));
			} else {
				TradewareLogger.sysoutPrintlnForLocalTest("48");
				bean.setDateTimeStamp(optionChainDataBean.getDateTimeStamp());
				TradewareLogger.sysoutPrintlnForLocalTest("49");
				bean.setDateStamp(optionChainDataBean.getDateStamp());
			}
			if (optionChainDataBean.getDateStamp() == null) {
				LocalDate ld = LocalDate.now(ZoneId.of(Constants.TIME_ZONE));
				TradewareLogger.sysoutPrintlnForLocalTest("50");
				bean.setDateStamp(java.sql.Date.valueOf(ld));
			}else {
				TradewareLogger.sysoutPrintlnForLocalTest("51");
				bean.setDateStamp(optionChainDataBean.getDateStamp());
			}
			TradewareLogger.sysoutPrintlnForLocalTest("52");
		bean.setDay1OpenInterest(optionChainDataBean.getDay1OpenInterest());
		TradewareLogger.sysoutPrintlnForLocalTest("53");
		bean.setDay2OpenInterest(optionChainDataBean.getDay2OpenInterest());
		TradewareLogger.sysoutPrintlnForLocalTest("54");
		bean.setOpenInterestChange(optionChainDataBean.getOpenInterestChange());
		TradewareLogger.sysoutPrintlnForLocalTest("55");
		bean.setOpenInterestChangePercentage(optionChainDataBean.getOpenInterestChangePercentage());
		TradewareLogger.sysoutPrintlnForLocalTest("56");
		bean.setOpenInterestVolumes(optionChainDataBean.getOpenInterestVolumes());// contracts
		TradewareLogger.sysoutPrintlnForLocalTest("57");
		bean.setUnderlyingPrice(optionChainDataBean.getUnderlyingPrice());// current market price value
		TradewareLogger.sysoutPrintlnForLocalTest("58");
		bean.setTimeFrameNumber(optionChainDataBean.getTimeFrameNumber());
		//bean.setDateTimeStamp(optionChainDataBean.getDateTimeStamp());
		//bean.setDateStamp(optionChainBean.getDateStamp());
		bean.setBestTradeInd(optionChainDataBean.getBestTradeInd());
		TradewareLogger.sysoutPrintlnForLocalTest("59");
		bean.setStrongOrder(optionChainDataBean.getStrongOrder());
		TradewareLogger.sysoutPrintlnForLocalTest("60");
		bean.setLtpOnDecision(optionChainDataBean.getLtpOnDecision());
		TradewareLogger.sysoutPrintlnForLocalTest("61");
		bean.setLastPrice(optionChainDataBean.getLtp());
		TradewareLogger.sysoutPrintlnForLocalTest("62");
		bean.setBestEntry(optionChainDataBean.getBestEntry());
		TradewareLogger.sysoutPrintlnForLocalTest("63");
		bean.setGoForTrade(optionChainDataBean.getGoForTrade());
		TradewareLogger.sysoutPrintlnForLocalTest("64");
		bean.setChangePercentage(optionChainDataBean.getChangePercentage());
		TradewareLogger.sysoutPrintlnForLocalTest("65");
		bean.setCandleNumber(optionChainDataBean.getCandleNumber());
		TradewareLogger.sysoutPrintlnForLocalTest("66");
		bean.setParentRecordInd(optionChainDataBean.getParentRecordInd());
		
		/*bean.setPreviousTop1OpenInterestCall(optionChainBean.getPreviousTop1OpenInterestCall());
		bean.setPreviousTop2OpenInterestCall(optionChainBean.getPreviousTop2OpenInterestCall());
		bean.setPreviousTop3OpenInterestCall(optionChainBean.getPreviousTop3OpenInterestCall());
		bean.setPreviousTop1OpenInterestPut(optionChainBean.getPreviousTop1OpenInterestPut());
		bean.setPreviousTop2OpenInterestPut(optionChainBean.getPreviousTop2OpenInterestPut());
		bean.setPreviousTop3OpenInterestPut(optionChainBean.getPreviousTop3OpenInterestPut());*/
		TradewareLogger.sysoutPrintlnForLocalTest("67");
		bean.setTop1OpenInterestCallStyle(optionChainDataBean.getTop1OpenInterestCallStyle());
		TradewareLogger.sysoutPrintlnForLocalTest("68");
		bean.setTop2OpenInterestCallStyle(optionChainDataBean.getTop2OpenInterestCallStyle());
		TradewareLogger.sysoutPrintlnForLocalTest("69");
		bean.setTop3OpenInterestCallStyle(optionChainDataBean.getTop3OpenInterestCallStyle());
		TradewareLogger.sysoutPrintlnForLocalTest("70");
		bean.setTop1OpenInterestPutStyle(optionChainDataBean.getTop1OpenInterestPutStyle());
		TradewareLogger.sysoutPrintlnForLocalTest("71");
		bean.setTop2OpenInterestPutStyle(optionChainDataBean.getTop2OpenInterestPutStyle());
		TradewareLogger.sysoutPrintlnForLocalTest("72");
		bean.setTop3OpenInterestPutStyle(optionChainDataBean.getTop3OpenInterestPutStyle());
		TradewareLogger.sysoutPrintlnForLocalTest("73");
		bean.setAttentionStyleBuy(optionChainDataBean.getAttentionStyleBuy());
		TradewareLogger.sysoutPrintlnForLocalTest("74");
		bean.setAttentionStyleSell(optionChainDataBean.getAttentionStyleSell());
		TradewareLogger.sysoutPrintlnForLocalTest("75");
		bean.setTotalOINetChangeCall(optionChainDataBean.getTotalOINetChangeCall());
		TradewareLogger.sysoutPrintlnForLocalTest("76");
		bean.setTotalOINetChangePut(optionChainDataBean.getTotalOINetChangePut());
		TradewareLogger.sysoutPrintlnForLocalTest("77");
		bean.setPriceMoveTrend(optionChainDataBean.getPriceMoveTrend());
		TradewareLogger.sysoutPrintlnForLocalTest("78");
		bean.setTotalOINetChangeCallStyle(optionChainDataBean.getTotalOINetChangeCallStyle());
		TradewareLogger.sysoutPrintlnForLocalTest("79");
		bean.setTotalOINetChangePutStyle(optionChainDataBean.getTotalOINetChangePutStyle());
		
		bean.setTotalOpenInterestChangeCall(optionChainDataBean.getTotalOpenInterestChangeCall());
		bean.setTotalOpenInterestChangePut(optionChainDataBean.getTotalOpenInterestChangePut());
		bean.setPriceOpenIntrestChangeTrend(optionChainDataBean.getPriceOpenIntrestChangeTrend());
		bean.setTotalOpenInterestChangeCallStyle(optionChainDataBean.getTotalOpenInterestChangeCallStyle());
		bean.setTotalOpenInterestChangePutStyle(optionChainDataBean.getTotalOpenInterestChangePutStyle());
		//TradewareLogger.sysoutPrintlnForLocalTest(optionChainDataBean.getSymbol()+ " - "+optionChainDataBean.getParentRecordInd());
		
		bean.setTop1OpenInterestCallBidVal1(optionChainDataBean.getTop1OpenInterestCallBidVal1());
		 bean.setTop1OpenInterestCallAskVal1(optionChainDataBean.getTop1OpenInterestCallAskVal1());
		 bean.setTop1OpenInterestCallBidAskDiffVal1(optionChainDataBean.getTop1OpenInterestCallBidAskDiffVal1());
		 bean.setTop1OpenInterestCallBidAskAmtDiffVal1(optionChainDataBean.getTop1OpenInterestCallBidAskAmtDiffVal1());
		 bean.setTop1OpenInterestPutBidVal1(optionChainDataBean.getTop1OpenInterestPutBidVal1());
		 bean.setTop1OpenInterestPutAskVal1(optionChainDataBean.getTop1OpenInterestPutAskVal1());
		 bean.setTop1OpenInterestPutBidAskDiffVal1(optionChainDataBean.getTop1OpenInterestPutBidAskDiffVal1());
		 bean.setTop1OpenInterestPutBidAskDiffAmtVal1(optionChainDataBean.getTop1OpenInterestPutBidAskAmtDiffVal1());
		 bean.setTop2OpenInterestCallBidVal1(optionChainDataBean.getTop2OpenInterestCallBidVal1());
		 bean.setTop2OpenInterestCallAskVal1(optionChainDataBean.getTop2OpenInterestCallAskVal1());
		 bean.setTop2OpenInterestCallBidAskDiffVal1(optionChainDataBean.getTop2OpenInterestCallBidAskDiffVal1());
		 bean.setTop2OpenInterestCallBidAskAmtDiffVal1(optionChainDataBean.getTop2OpenInterestCallBidAskAmtDiffVal1());
		 bean.setTop2OpenInterestPutBidVal1(optionChainDataBean.getTop2OpenInterestPutBidVal1());
		 bean.setTop2OpenInterestPutAskVal1(optionChainDataBean.getTop2OpenInterestPutAskVal1());
		 bean.setTop2OpenInterestPutBidAskDiffVal1(optionChainDataBean.getTop2OpenInterestPutBidAskDiffVal1());
		 bean.setTop2OpenInterestPutBidAskDiffAmtVal1(optionChainDataBean.getTop2OpenInterestPutBidAskAmtDiffVal1());
		 bean.setTop3OpenInterestCallBidVal1(optionChainDataBean.getTop3OpenInterestCallBidVal1());
		 bean.setTop3OpenInterestCallAskVal1(optionChainDataBean.getTop3OpenInterestCallAskVal1());
		 bean.setTop3OpenInterestCallBidAskDiffVal1(optionChainDataBean.getTop3OpenInterestCallBidAskDiffVal1());
		 bean.setTop3OpenInterestCallBidAskAmtDiffVal1(optionChainDataBean.getTop3OpenInterestCallBidAskAmtDiffVal1());
		 bean.setTop3OpenInterestPutBidVal1(optionChainDataBean.getTop3OpenInterestPutBidVal1());
		 bean.setTop3OpenInterestPutAskVal1(optionChainDataBean.getTop3OpenInterestPutAskVal1());
		 bean.setTop3OpenInterestPutBidAskDiffVal1(optionChainDataBean.getTop3OpenInterestPutBidAskDiffVal1());
		 bean.setTop3OpenInterestPutBidAskDiffAmtVal1(optionChainDataBean.getTop3OpenInterestPutBidAskAmtDiffVal1());
		 bean.setTop1ImpliedVolatilityCall(optionChainDataBean.getTop1ImpliedVolatilityCall());
		 bean.setTop2ImpliedVolatilityCall(optionChainDataBean.getTop2ImpliedVolatilityCall());
		 bean.setTop3ImpliedVolatilityCall(optionChainDataBean.getTop3ImpliedVolatilityCall());
		 bean.setTop1ImpliedVolatilityPut(optionChainDataBean.getTop1ImpliedVolatilityPut());
		 bean.setTop2ImpliedVolatilityPut(optionChainDataBean.getTop2ImpliedVolatilityPut());
		 bean.setTop3ImpliedVolatilityPut(optionChainDataBean.getTop3ImpliedVolatilityPut());
		 bean.setTotalImpliedVolatilityCall(optionChainDataBean.getTotalImpliedVolatilityCall());
		 bean.setTotalImpliedVolatilityPut(optionChainDataBean.getTotalImpliedVolatilityPut());
		 bean.setTotalImpliedVolatilityCallStyle(optionChainDataBean.getTotalImpliedVolatilityCallStyle());
		 bean.setTotalImpliedVolatilityPutStyle(optionChainDataBean.getTotalImpliedVolatilityPutStyle());
		
		bean.setSortOrder(optionChainDataBean.getSortOrder());
		TradewareLogger.sysoutPrintlnForLocalTest("80");
		bean.setTopTenGainLooseSortOrder(optionChainDataBean.getTopTenGainLooseSortOrder());
		TradewareLogger.sysoutPrintlnForLocalTest("81");
		bean.setYearHigh(optionChainDataBean.getYearHigh());
		TradewareLogger.sysoutPrintlnForLocalTest("82");
		bean.setYearLow(optionChainDataBean.getYearLow());
		 if (null != bean.getTotalOpenInterestCall() && null != bean.getTotalOpenInterestPut()) {
			 TradewareLogger.sysoutPrintlnForLocalTest("83");
			 bean.setPutCallRatio(kiteConnectTool.getRoundupToTwoValue(bean.getTotalOpenInterestPut()/bean.getTotalOpenInterestCall()));
			 if (bean.getPutCallRatio() >= Constants.PCR_CRASH_DOWN_TREND) {
				 TradewareLogger.sysoutPrintlnForLocalTest("84");
				 bean.setPutCallRatioStyleClass(Constants.RED_BOLD_FONT_STYLE_CLASS);
			 } else  if (bean.getPutCallRatio() <= Constants.PCR_UP_TREND) {
				 TradewareLogger.sysoutPrintlnForLocalTest("85");
				 bean.setPutCallRatioStyleClass(Constants.GREEN_BOLD_FONT_STYLE_CLASS);
			 }
		 }
		 TradewareLogger.sysoutPrintlnForLocalTest("86");
		 
	} catch (Exception e) {
		TradewareLogger.saveFatalErrorLog(Constants.CLASS_KITE_CONNECT_TOOL,
				Constants.METHOD_GET_ROUND_UP_TO_TWO_VALUE_UP, e,
				Constants.ERROR_TYPE_KITE_EXCEPTION);
	}
		return bean;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static OptionChainInfoBean mapToOptionChainInforBean(org.tradeware.stockmarket.bean.OptionChainDataBean optionChainDataBean) {
		OptionChainInfoBean bean = new OptionChainInfoBean();
		//bean.setOptionChainId(optionChainId);
		bean.setSymbolId(optionChainDataBean.getSymbol());
		bean.setLotSize(optionChainDataBean.getLotSize());
		bean.setTickerSize(optionChainDataBean.getTickerSize());
		bean.setStyle(optionChainDataBean.getStyle());
		bean.setOITrend(optionChainDataBean.getOITrend());
		bean.setIndexInd(optionChainDataBean.getIndexInd());
		bean.setTop1OpenInterestCall(optionChainDataBean.getTop1OpenInterestCall());
		bean.setTop2OpenInterestCall(optionChainDataBean.getTop2OpenInterestCall());
		bean.setTop3OpenInterestCall(optionChainDataBean.getTop3OpenInterestCall());
		bean.setTop1OpenInterestChangeCall(optionChainDataBean.getTop1OpenInterestChangeCall());
		bean.setTop2OpenInterestChangeCall(optionChainDataBean.getTop2OpenInterestChangeCall());
		bean.setTop3OpenInterestChangeCall(optionChainDataBean.getTop3OpenInterestChangeCall());
		bean.setTop1OIVolumesCall(optionChainDataBean.getTop1OIVolumesCall());
		bean.setTop2OIVolumesCall(optionChainDataBean.getTop2OIVolumesCall());
		bean.setTop3OIVolumesCall(optionChainDataBean.getTop3OIVolumesCall());
		bean.setTop1OINetChangeCall(optionChainDataBean.getTop1OINetChangeCall());
		bean.setTop2OINetChangeCall(optionChainDataBean.getTop2OINetChangeCall());
		bean.setTop3OINetChangeCall(optionChainDataBean.getTop3OINetChangeCall());
		bean.setTop1StrikePriceCall(optionChainDataBean.getTop1StrikePriceCall());
		bean.setTop2StrikePriceCall(optionChainDataBean.getTop2StrikePriceCall());
		bean.setTop3StrikePriceCall(optionChainDataBean.getTop3StrikePriceCall());
		bean.setTop1OpenInterestPut(optionChainDataBean.getTop1OpenInterestPut());
		bean.setTop2OpenInterestPut(optionChainDataBean.getTop2OpenInterestPut());
		bean.setTop3OpenInterestPut(optionChainDataBean.getTop3OpenInterestPut());
		bean.setTop1OpenInterestChangePut(optionChainDataBean.getTop1OpenInterestChangePut());
		bean.setTop2OpenInterestChangePut(optionChainDataBean.getTop2OpenInterestChangePut());
		bean.setTop3OpenInterestChangePut(optionChainDataBean.getTop3OpenInterestChangePut());
		bean.setTop1OIVolumesPut(optionChainDataBean.getTop1OIVolumesPut());
		bean.setTop2OIVolumesPut(optionChainDataBean.getTop2OIVolumesPut());
		bean.setTop3OIVolumesPut(optionChainDataBean.getTop3OIVolumesPut());
		bean.setTop1OINetChangePut(optionChainDataBean.getTop1OINetChangePut());
		bean.setTop2OINetChangePut(optionChainDataBean.getTop2OINetChangePut());
		bean.setTop3OINetChangePut(optionChainDataBean.getTop3OINetChangePut());
		bean.setTop1StrikePricePut(optionChainDataBean.getTop1StrikePricePut());
		bean.setTop2StrikePricePut(optionChainDataBean.getTop2StrikePricePut());
		bean.setTop3StrikePricePut(optionChainDataBean.getTop3StrikePricePut());
		bean.setTotalOpenInterestCall(optionChainDataBean.getTotalOpenInterestCall());
		bean.setTotalOpenInterestPut(optionChainDataBean.getTotalOpenInterestPut());
		bean.setTotalOIVolumesCall(optionChainDataBean.getTotalOIVolumesCall());
		bean.setTotalOIVolumesPut(optionChainDataBean.getTotalOIVolumesPut());
		bean.setDay1OpenInterest(optionChainDataBean.getDay1OpenInterest());
		bean.setDay2OpenInterest(optionChainDataBean.getDay2OpenInterest());
		bean.setOpenInterestChange(optionChainDataBean.getOpenInterestChange());
		bean.setOpenInterestChangePercentage(optionChainDataBean.getOpenInterestChangePercentage());
		bean.setOpenInterestVolumes(optionChainDataBean.getOpenInterestVolumes());// contracts
		bean.setUnderlyingPrice(optionChainDataBean.getUnderlyingPrice());// current market price value
		bean.setTimeFrameNumber(optionChainDataBean.getTimeFrameNumber());
		ZonedDateTime zdt = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)).atZone(ZoneId.of(Constants.TIME_ZONE));
		bean.setDateTimeStamp(Date.from(zdt.toInstant()));
		bean.setDateStamp(Date.from(zdt.toInstant()));
		return bean;
	}
	
	
	
	
	
	
	
	
	
	
	
	// Option strategy trading start
	public OptionLiveTradeMainBean mapToOptionLiveShortStrangleTradeMainBean(OptionLiveTradeMainDataBean dataBean) {
		OptionLiveTradeMainBean bean = new OptionLiveTradeMainBean();
		bean.setTradeId(dataBean.getTradeId());
		bean.setTradeName(dataBean.getTradeName());
		bean.setSymbolId(dataBean.getSymbolId());
		bean.setLotSize(dataBean.getLotSize());
		bean.setOptionTickerSize(dataBean.getOptionTickerSize());
		bean.setExpiryDate(dataBean.getExpiryDate());
		bean.setTradeStrategy(dataBean.getTradeStrategy());
		bean.setTradeSubStrategy(dataBean.getTradeSubStrategy());
		bean.setTradedAtDtTm(dataBean.getTradedAtDtTm());
		bean.setExitedAtDtTm(dataBean.getExitedAtDtTm());
		bean.setProfitLossAmtVal(dataBean.getProfitLossAmtVal());
		bean.setPositiveMoveVal(dataBean.getPositiveMoveVal());
		bean.setNegativeMoveVal(dataBean.getNegativeMoveVal());
		bean.setTradedDateStamp(dataBean.getTradedDateStamp());
		bean.setTradePosition(dataBean.getTradePosition());
		bean.setComments(dataBean.getComments());
		bean.setAtmStrikePrice(dataBean.getAtmStrikePrice());
		bean.setBreakEvenLower1(dataBean.getBreakEvenLower1());
		bean.setBreakEvenHigher1(dataBean.getBreakEvenHigher1());
		bean.setStopLossVal(dataBean.getStopLossVal());
		bean.setTargetVal(dataBean.getTargetVal());
		bean.setIntradayTradeInd(dataBean.getIntradayTradeInd());

		if (null != dataBean.getTradeChildBeanList() && !dataBean.getTradeChildBeanList().isEmpty()) {
			List<OptionLiveTradeChildBean> tradeChildBeanList = new ArrayList<OptionLiveTradeChildBean>();
			for (OptionLiveTradeChildDataBean childDataBean : dataBean.getTradeChildBeanList()) {
				tradeChildBeanList.add(mapToOptionLiveShortStrangleTradeMainBean(childDataBean));
			}
			bean.setTradeChildBeanList(tradeChildBeanList);
		}

		return bean;
	}
	
	public OptionLiveTradeChildBean mapToOptionLiveShortStrangleTradeMainBean(OptionLiveTradeChildDataBean dataBean) {
		OptionLiveTradeChildBean bean = new OptionLiveTradeChildBean();
		bean.setItemIdChild(dataBean.getItemIdChild());
		bean.setStrikePrice(dataBean.getStrikePrice());
		bean.setTradeType(dataBean.getTradeType());
		bean.setTradePosition(dataBean.getTradePosition());
		bean.setOptionType(dataBean.getOptionType());
		bean.setKiteFutureKey(dataBean.getKiteFutureKey());
		bean.setBuyAtValue(dataBean.getBuyAtValue());
		bean.setSellAtValue(dataBean.getSellAtValue());
		bean.setTradedAtVal(dataBean.getTradedAtVal());
		bean.setExitedAtVal(dataBean.getExitedAtVal());
		bean.setStockPriceEntry(dataBean.getStockPriceEntry());
		bean.setFuturePriceEntry(dataBean.getFuturePriceEntry());
		bean.setStockPriceExit(dataBean.getStockPriceExit());
		bean.setFuturePriceExit(dataBean.getFuturePriceExit());
		bean.setNumberOfLots(dataBean.getNumberOfLots());
		bean.setTradedAtDtTm(dataBean.getTradedAtDtTm());
		bean.setExitedAtDtTm(dataBean.getExitedAtDtTm());
		bean.setProfitLossAmtVal(dataBean.getProfitLossAmtVal());
		bean.setPositiveMoveVal(dataBean.getPositiveMoveVal());
		bean.setNegativeMoveVal(dataBean.getNegativeMoveVal());
		bean.setPositiveMoveLtp(dataBean.getPositiveMoveLtp());
		bean.setNegativeMoveLtp(dataBean.getNegativeMoveLtp());
		bean.setTradedDateStamp(dataBean.getTradedDateStamp());
		bean.setOptionChainTrend(dataBean.getOptionChainTrend());
		bean.setOptionChainPriceTrend(dataBean.getOptionChainPriceTrend());
		bean.setOptionChainId(dataBean.getOptionChainId());
		bean.setOiInfo(dataBean.getOiInfo());
		bean.setBidPrice(dataBean.getBidPrice());
		bean.setAskPrice(dataBean.getAskPrice());
		bean.setLastPrice(dataBean.getLastPrice());
		bean.setBidQuantity(dataBean.getBidQuantity());
		bean.setAskQuantity(dataBean.getAskQuantity());
		bean.setInstrumentToken(dataBean.getInstrumentToken());
		bean.setKiteOrderId(dataBean.getKiteOrderId());
		bean.setKiteOrderType(dataBean.getKiteOrderType());
		bean.setStopLossVal(dataBean.getStopLossVal());
		bean.setTargetVal(dataBean.getTargetVal());
		return bean;
	}
	
	
	public OptionLiveTradeMainDataBean mapToOptionLiveShortStrangleTradeMainDataBean(OptionLiveTradeMainBean bean) {
		OptionLiveTradeMainDataBean dataBean = new OptionLiveTradeMainDataBean();
		dataBean.setTradeId(bean.getTradeId());
		dataBean.setTradeName(bean.getTradeName());
		dataBean.setSymbolId(bean.getSymbolId());
		dataBean.setLotSize(bean.getLotSize());
		dataBean.setOptionTickerSize(bean.getOptionTickerSize());
		dataBean.setExpiryDate(bean.getExpiryDate());
		dataBean.setTradeStrategy(bean.getTradeStrategy());
		dataBean.setTradeSubStrategy(bean.getTradeSubStrategy());
		dataBean.setTradedAtDtTm(bean.getTradedAtDtTm());
		dataBean.setExitedAtDtTm(bean.getExitedAtDtTm());
		dataBean.setProfitLossAmtVal(bean.getProfitLossAmtVal());
		dataBean.setPositiveMoveVal(bean.getPositiveMoveVal());
		dataBean.setNegativeMoveVal(bean.getNegativeMoveVal());
		dataBean.setTradedDateStamp(bean.getTradedDateStamp());
		dataBean.setTradePosition(bean.getTradePosition());
		dataBean.setComments(bean.getComments());
		dataBean.setAtmStrikePrice(bean.getAtmStrikePrice());
		dataBean.setBreakEvenLower1(bean.getBreakEvenLower1());
		dataBean.setBreakEvenHigher1(bean.getBreakEvenHigher1());
		dataBean.setStopLossVal(bean.getStopLossVal());
		dataBean.setTargetVal(bean.getTargetVal());
		dataBean.setIntradayTradeInd(bean.getIntradayTradeInd());
		
		if (null != bean.getTradeChildBeanList() && !bean.getTradeChildBeanList().isEmpty()) {
			List<OptionLiveTradeChildDataBean> tradeChildDataBeanList = new ArrayList<OptionLiveTradeChildDataBean>();
			for (OptionLiveTradeChildBean childBean : bean.getTradeChildBeanList()) {
				tradeChildDataBeanList.add(mapToOptionLiveShortStrangleTradeMainDataBean(childBean));
			}
			dataBean.setTradeChildBeanList(tradeChildDataBeanList);
		}

		return dataBean;
	}
	
	public OptionLiveTradeChildDataBean mapToOptionLiveShortStrangleTradeMainDataBean(OptionLiveTradeChildBean bean) {
		OptionLiveTradeChildDataBean dataBean = new OptionLiveTradeChildDataBean();
		dataBean.setItemIdChild(bean.getItemIdChild());
		dataBean.setStrikePrice(bean.getStrikePrice());
		dataBean.setTradeType(bean.getTradeType());
		dataBean.setTradePosition(bean.getTradePosition());
		dataBean.setOptionType(bean.getOptionType());
		dataBean.setKiteFutureKey(bean.getKiteFutureKey());
		dataBean.setBuyAtValue(bean.getBuyAtValue());
		dataBean.setSellAtValue(bean.getSellAtValue());
		dataBean.setTradedAtVal(bean.getTradedAtVal());
		dataBean.setExitedAtVal(bean.getExitedAtVal());
		dataBean.setStockPriceEntry(bean.getStockPriceEntry());
		dataBean.setFuturePriceEntry(bean.getFuturePriceEntry());
		dataBean.setStockPriceExit(bean.getStockPriceExit());
		dataBean.setFuturePriceExit(bean.getFuturePriceExit());
		dataBean.setNumberOfLots(bean.getNumberOfLots());
		dataBean.setTradedAtDtTm(bean.getTradedAtDtTm());
		dataBean.setExitedAtDtTm(bean.getExitedAtDtTm());
		dataBean.setProfitLossAmtVal(bean.getProfitLossAmtVal());
		dataBean.setPositiveMoveVal(bean.getPositiveMoveVal());
		dataBean.setNegativeMoveVal(bean.getNegativeMoveVal());
		dataBean.setPositiveMoveLtp(bean.getPositiveMoveLtp());
		dataBean.setNegativeMoveLtp(bean.getNegativeMoveLtp());
		dataBean.setTradedDateStamp(bean.getTradedDateStamp());
		dataBean.setOptionChainTrend(bean.getOptionChainTrend());
		dataBean.setOptionChainPriceTrend(bean.getOptionChainPriceTrend());
		dataBean.setOptionChainId(bean.getOptionChainId());
		dataBean.setOiInfo(bean.getOiInfo());
		dataBean.setBidPrice(bean.getBidPrice());
		dataBean.setAskPrice(bean.getAskPrice());
		dataBean.setLastPrice(bean.getLastPrice());
		dataBean.setBidQuantity(bean.getBidQuantity());
		dataBean.setAskQuantity(bean.getAskQuantity());
		dataBean.setInstrumentToken(bean.getInstrumentToken());
		dataBean.setKiteOrderId(bean.getKiteOrderId());
		dataBean.setKiteOrderType(bean.getKiteOrderType());
		dataBean.setStopLossVal(bean.getStopLossVal());
		dataBean.setTargetVal(bean.getTargetVal());
		return dataBean;
	}

	// Option strategy trading END
}
