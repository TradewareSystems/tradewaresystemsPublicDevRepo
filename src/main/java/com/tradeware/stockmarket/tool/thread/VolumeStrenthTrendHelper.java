package com.tradeware.stockmarket.tool.thread;

import java.util.List;

import com.tradeware.stockmarket.bean.AverageHistDataBean;
import com.tradeware.stockmarket.bean.StrategyOrbDataBean;
import com.tradeware.stockmarket.log.TradewareLogger;
import com.zerodhatech.models.HistoricalData;

public abstract class VolumeStrenthTrendHelper extends AdditionalDataFillupHelper {

	private static long volume4, volume3, volume2, volume1, smallVolume, totalCandleVolume = 0;
	private static double vol4Ratio, vol3Ratio, vol2Ratio, vol1Ratio;
	private static double lastCandleHigh = 0, lastCandleLow = 0, lastCandleClose = 0, lastCandleOpen = 0,
			totalCandleAvgPrice = 0, totalCandleCloseAvgPrice = 0, calculatedVWAP = 0;
	private HistoricalData vwapHistdata = null;

	private void clearVWAPParams() {
		
		//Will be available in Candle details info, need to refactor
		lastCandleLow = 0;
		lastCandleOpen = 0;
		lastCandleHigh = 0;
		lastCandleClose = 0;
		
		vwapHistdata = null;
		calculatedVWAP = 0;
		totalCandleVolume = 0;
		totalCandleAvgPrice = 0;
		totalCandleCloseAvgPrice = 0;
	}

	private StringBuffer volStrenthStrBuffer = new StringBuffer();

	private void clearVolStrenthStrBuffer() {
		volume4 = 0;
		volume3 = 0;
		volume2 = 0;
		volume1 = 0;
		vol4Ratio = 0;
		vol3Ratio = 0;
		vol2Ratio = 0;
		vol1Ratio = 0;
		smallVolume = 0;
		volStrenthStrBuffer.delete(0, volStrenthStrBuffer.length());
	}

	public void volumeStrenthTrend(List<HistoricalData> histDataList, StrategyOrbDataBean bean) {
		clearVolStrenthStrBuffer();
		if (null != histDataList && histDataList.size() >= 5) {
			volume4 = histDataList.get(histDataList.size() - 2).volume;
			volume3 = histDataList.get(histDataList.size() - 3).volume;
			volume2 = histDataList.get(histDataList.size() - 4).volume;
			volume1 = histDataList.get(histDataList.size() - 5).volume;
			smallVolume = volume1;
			if (volume2 < smallVolume) {
				smallVolume = volume2;
			}
			if (volume3 < smallVolume) {
				smallVolume = volume3;
			}
			if (volume4 < smallVolume) {
				smallVolume = volume4;
			}

			vol4Ratio = kiteConnectTool.getRoundupToTwoValue((double) volume4 / (double) smallVolume);
			vol3Ratio = kiteConnectTool.getRoundupToTwoValue((double) volume3 / (double) smallVolume);
			vol2Ratio = kiteConnectTool.getRoundupToTwoValue((double) volume2 / (double) smallVolume);
			vol1Ratio = kiteConnectTool.getRoundupToTwoValue((double) volume1 / (double) smallVolume);
			setVolumeValues(bean, volume4, volume3, volume2, volume1, vol4Ratio, vol3Ratio, vol2Ratio, vol1Ratio,
					smallVolume);
			
			volStrenthStrBuffer.append(volume4).append(COMMA_SPACE).append(volume3).append(COMMA_SPACE).append(volume2)
					.append(COMMA_SPACE).append(volume1);
			bean.setVwapTradeVolInfo(volStrenthStrBuffer.toString());

			volStrenthStrBuffer.delete(0, volStrenthStrBuffer.length());
			volStrenthStrBuffer.append(vol4Ratio).append(UNDER_SCORE).append(vol3Ratio).append(UNDER_SCORE)
					.append(vol2Ratio).append(UNDER_SCORE).append(vol1Ratio);
			bean.setVwapTradeVolRatioInfo(volStrenthStrBuffer.toString());
			
			if ((volume4 < volume3) && (volume3 < volume2) && (volume2 < volume1)) {
				bean.setVolStrengthStyleClass(RED_BOLD_FONT_STYLE_CLASS);
				bean.setVolumeTradeStateId(SELL);
				bean.setTradingRuleVolumeStrengthInd(true);
				if ((volume4 * 7) <= volume1) {
					bean.setVolumeTradeStateId(SEL7);
				} else if ((volume4 * 5) <= volume1) {
					bean.setVolumeTradeStateId(SEL5);
				} else if ((volume4 * 4) <= volume1) {
					bean.setVolumeTradeStateId(SEL4);
				} else if ((volume4 * 3) <= volume1) {
					bean.setVolumeTradeStateId(SEL3);
				}
			} else if ((volume4 > volume3) && (volume3 > volume2) && (volume2 > volume1)) {
				bean.setVolStrengthStyleClass(GREEN_BOLD_FONT_STYLE_CLASS);
				bean.setVolumeTradeStateId(BUY);
				bean.setTradingRuleVolumeStrengthInd(true);
				if (volume4 >= (volume1 * 7)) {
					bean.setVolumeTradeStateId(BUY7);
				} else if (volume4 >= (volume1 * 5)) {
					bean.setVolumeTradeStateId(BUY5);
				} else if (volume4 >= (volume1 * 4)) {
					bean.setVolumeTradeStateId(BUY4);
				} else if (volume4 >= (volume1 * 3)) {
					bean.setVolumeTradeStateId(BUY3);
				}
			} else {
				bean.setVolStrengthStyleClass(EMPTY_STRING);
				bean.setVolumeTradeStateId(NA);
			}

		} else if (null != histDataList && histDataList.size() >= 3) {
			volume4 = histDataList.get(histDataList.size() - 2).volume;
			volume3 = histDataList.get(histDataList.size() - 3).volume;
			smallVolume = volume4 > volume3 ? volume3 : volume4;
			vol4Ratio = kiteConnectTool.getRoundupToTwoValue((double) volume4 / (double) smallVolume);
			vol3Ratio = kiteConnectTool.getRoundupToTwoValue((double) volume3 / (double) smallVolume);

			volStrenthStrBuffer.append(volume4).append(COMMA_SPACE).append(volume3);
			bean.setVwapTradeVolInfo(volStrenthStrBuffer.toString());
			volStrenthStrBuffer.delete(0, volStrenthStrBuffer.length());
			volStrenthStrBuffer.append(vol4Ratio).append(UNDER_SCORE).append(vol3Ratio);
			bean.setVwapTradeVolRatioInfo(volStrenthStrBuffer.toString());
	
			if (volume4 < (volume3 * VOL_1_5_DOUBLE)) {
				bean.setVolStrengthStyleClass(RED_BOLD_FONT_STYLE_CLASS);
				bean.setVolumeTradeStateId(SELL);
				bean.setTradingRuleVolumeStrengthInd(volume4 < (volume3 * 4));
			} else if ((volume4 > (volume3 * VOL_1_5_DOUBLE))) {
				bean.setVolStrengthStyleClass(GREEN_BOLD_FONT_STYLE_CLASS);
				bean.setVolumeTradeStateId(BUY);
				bean.setTradingRuleVolumeStrengthInd(volume4 > (volume3 * 4));
			} else {
				bean.setVolStrengthStyleClass(EMPTY_STRING);
				bean.setVolumeTradeStateId(NA);
			}
		}

		// calculate VWAP
		clearVWAPParams();
		for (int i = 0; i <= histDataList.size() - 2; i++) {
			vwapHistdata = histDataList.get(i);
			totalCandleAvgPrice = totalCandleAvgPrice
					+ (((vwapHistdata.high + vwapHistdata.low + vwapHistdata.close) / 3) * vwapHistdata.volume);
			totalCandleVolume = totalCandleVolume + vwapHistdata.volume;

			totalCandleCloseAvgPrice = totalCandleCloseAvgPrice + (vwapHistdata.close * vwapHistdata.volume);

			if (i == (histDataList.size() - 2)) {
				lastCandleHigh = vwapHistdata.high;
				lastCandleLow = vwapHistdata.low;
				lastCandleClose = vwapHistdata.close;
				lastCandleOpen = vwapHistdata.open;
			}
		}

		calculatedVWAP = kiteConnectTool.getRoundupToTwoValue(totalCandleAvgPrice / totalCandleVolume);
		//volStrenthStrBuffer.append(COMMA_SPACE).append("VWAP:" + calculatedVWAP);
		bean.setVwapValue(calculatedVWAP);

		if ((lastCandleOpen < calculatedVWAP && calculatedVWAP < lastCandleClose)
				|| (lastCandleOpen > calculatedVWAP && calculatedVWAP > lastCandleClose)) {
			if (lastCandleOpen < lastCandleClose) {
				bean.setVwapTradeStateId(VWAP_STRONG_BUY);
				// TelegramMessageTool.getInstance().sendTelegramMessageTrade(VWAP_STRONG_BUY+bean.getKiteFutureKey()+Constants.SPACE+Constants.STRONG_BUY);
			} else if (lastCandleOpen > lastCandleClose) {
				bean.setVwapTradeStateId(VWAP_STRONG_SELL);
				// TelegramMessageTool.getInstance().sendTelegramMessageTrade(VWAP_STRONG_SELL+bean.getKiteFutureKey()+Constants.SPACE+Constants.STRONG_SELL);
			}
			bean.setTradingRuleVwapStrongInd(true);
		} else if ((lastCandleLow < calculatedVWAP && calculatedVWAP < lastCandleHigh)) {
			if (lastCandleOpen < lastCandleClose) {
				bean.setVwapTradeStateId(VWAP_BUY);
				// TelegramMessageTool.getInstance().sendTelegramMessageTrade(VWAP_BUY+bean.getKiteFutureKey()+Constants.SPACE+Constants.BUY);
			} else if (lastCandleOpen > lastCandleClose) {
				bean.setVwapTradeStateId(VWAP_SELL);
				// TelegramMessageTool.getInstance().sendTelegramMessageTrade(VWAP_SELL+bean.getKiteFutureKey()+Constants.SPACE+Constants.SELL);
			}
			bean.setTradingRuleVwapStrongInd(true);
		} else {
			bean.setVwapTradeStateId(VWAP_NO_DIRECTION);
		}

		/*volStrenthStrBuffer.delete(0, volStrenthStrBuffer.length());
		volStrenthStrBuffer.append(" OHLC:").append(String.valueOf(lastCandleOpen)).append(COMMA)
				.append(String.valueOf(lastCandleHigh)).append(COMMA).append(String.valueOf(lastCandleLow))
				.append(COMMA).append(String.valueOf(lastCandleClose)).append(COMMA);
		bean.setLastCndleOhlcVal(volStrenthStrBuffer.toString());*/
		volStrenthStrBuffer.delete(0, volStrenthStrBuffer.length());
	}
	
		protected void copyCurrentCandleAverages(StrategyOrbDataBean bean, Double currentCandleOpen) {
			try {
			/*bean.setCurrentCandleAvgLow(TradingDataMapHistDataThread.avgLow.get(bean.getKiteFutureKey()));
			bean.setCurrentCandleAvgHigh(TradingDataMapHistDataThread.avgHigh.get(bean.getKiteFutureKey()));
			bean.setCurrentCandleAvgClose(TradingDataMapHistDataThread.avgClose.get(bean.getKiteFutureKey()));
			bean.setCurrentCandleAvgHighMinusClose(
					kiteConnectTool.getRoundupToTwoValue(TradingDataMapHistDataThread.avgHigh.get(bean.getKiteFutureKey())
							- TradingDataMapHistDataThread.avgClose.get(bean.getKiteFutureKey())));
			bean.setCurrentCandleAvgCloseMinusLow(kiteConnectTool.getRoundupToTwoValue(
					TradingDataMapHistDataThread.avgClose.get(bean.getKiteFutureKey()) - TradingDataMapHistDataThread.avgLow.get(bean.getKiteFutureKey())));*/
			AverageHistDataBean avgBean = tradewareTool.getAverageHLCDataMap().get(bean.getKiteFutureKey());
			bean.setAvgLow1min(avgBean.getAvgLowMin1());
			bean.setAvgHigh1min(avgBean.getAvgHighMin1());
			bean.setAvgClose1min(avgBean.getAvgCloseMin1());
			bean.setAvgHighMinusClose1min(kiteConnectTool
					.getRoundupToTwoValue(bean.getAvgHigh1min() - bean.getAvgClose1min()));
			bean.setAvgCloseMinusLow1min(kiteConnectTool
					.getRoundupToTwoValue(bean.getAvgClose1min() - bean.getAvgLow1min()));
			bean.setCurrentCandleOpen(currentCandleOpen);
			bean.setFirstLevelSmaVwapRuleInd(tradewareOrderPlacementRuleTool.verifySMAandVWAPformulaLevel1(bean));
			bean.setFirstLevelSmaVwapRuleInd(tradewareOrderPlacementRuleTool.verifySMAandVWAPformulaLevel1Min5(bean));
			
			bean.setAvgLow5min(avgBean.getAvgLowMin5());
			bean.setAvgHigh5min(avgBean.getAvgHighMin5());
			bean.setAvgClose5min(
					avgBean.getAvgCloseMin5());
			bean.setAvgHighMinusClose5min(
					kiteConnectTool.getRoundupToTwoValue(bean.getAvgHigh5min() - bean.getAvgClose5min()));
			bean.setAvgCloseMinusLow5min(
					kiteConnectTool.getRoundupToTwoValue(bean.getAvgClose5min() - bean.getAvgLow5min()));
			
		bean.setMin5HeikinAshiTrendId(
				avgBean.getMin5HeikinAshiTrendId());
		
		
		bean.setMin60StochasticValK1(avgBean.getMin60StochasticValK1());
		bean.setMin60StochasticValD3(avgBean.getMin60StochasticValD3());
		bean.setMin60StochasticValK2(avgBean.getMin60StochasticValK2());
		bean.setMin60StochasticValD4(avgBean.getMin60StochasticValD4());
		bean.setMin60StochasticValK3(avgBean.getMin60StochasticValK3());
		bean.setMin60StochasticValD5(avgBean.getMin60StochasticValD5());
		bean.setMin60StochasticTrend(avgBean.getMin60StochasticTrend());

		bean.setMin15StochasticValK1(avgBean.getMin15StochasticValK1());
		bean.setMin15StochasticValD3(avgBean.getMin15StochasticValD3());
		bean.setMin15StochasticValK2(avgBean.getMin15StochasticValK2());
		bean.setMin15StochasticValD4(avgBean.getMin15StochasticValD4());
		bean.setMin15StochasticValK3(avgBean.getMin15StochasticValK3());
		bean.setMin15StochasticValD5(avgBean.getMin15StochasticValD5());
		bean.setMin15StochasticTrend(avgBean.getMin15StochasticTrend());

		bean.setMin5StochasticValK1(avgBean.getMin5StochasticValK1());
		bean.setMin5StochasticValD3(avgBean.getMin5StochasticValD3());
		bean.setMin5StochasticValK2(avgBean.getMin5StochasticValK2());
		bean.setMin5StochasticValD4(avgBean.getMin5StochasticValD4());
		bean.setMin5StochasticValK3(avgBean.getMin5StochasticValK3());
		bean.setMin5StochasticValD5(avgBean.getMin5StochasticValD5());
		bean.setMin5StochasticTrend(avgBean.getMin5StochasticTrend());
		
		bean.setHeikinAshiTrendIdMin5(avgBean.getHeikinAshiTrendIdMin5());
		bean.setHeikenAshiOpen3Min5(avgBean.getHeikenAshiOpen3Min5());
		bean.setHeikenAshiClose3Min5(avgBean.getHeikenAshiClose3Min5());
		bean.setHeikenAshiHigh3Min5(avgBean.getHeikenAshiHigh3Min5());
		bean.setHeikenAshiLow3Min5(avgBean.getHeikenAshiLow3Min5());
		bean.setHeikenAshiOpen2Min5(avgBean.getHeikenAshiOpen2Min5());
		bean.setHeikenAshiClose2Min5(avgBean.getHeikenAshiClose2Min5());
		bean.setHeikenAshiHigh2Min5(avgBean.getHeikenAshiHigh2Min5());
		bean.setHeikenAshiLow2Min5(avgBean.getHeikenAshiLow2Min5());
		bean.setHeikenAshiOpen1Min5(avgBean.getHeikenAshiOpen1Min5());
		bean.setHeikenAshiClose1Min5(avgBean.getHeikenAshiClose1Min5());
		bean.setHeikenAshiHigh1Min5(avgBean.getHeikenAshiHigh1Min5());
		bean.setHeikenAshiLow1Min5(avgBean.getHeikenAshiLow1Min5());
		
		bean.setHeikinAshiTrendIdMin15(avgBean.getHeikinAshiTrendIdMin15());
		bean.setHeikenAshiOpen3Min15(avgBean.getHeikenAshiOpen3Min15());
		bean.setHeikenAshiClose3Min15(avgBean.getHeikenAshiClose3Min15());
		bean.setHeikenAshiHigh3Min15(avgBean.getHeikenAshiHigh3Min15());
		bean.setHeikenAshiLow3Min15(avgBean.getHeikenAshiLow3Min15());
		bean.setHeikenAshiOpen2Min15(avgBean.getHeikenAshiOpen2Min15());
		bean.setHeikenAshiClose2Min15(avgBean.getHeikenAshiClose2Min15());
		bean.setHeikenAshiHigh2Min15(avgBean.getHeikenAshiHigh2Min15());
		bean.setHeikenAshiLow2Min15(avgBean.getHeikenAshiLow2Min15());
		bean.setHeikenAshiOpen1Min15(avgBean.getHeikenAshiOpen1Min15());
		bean.setHeikenAshiClose1Min15(avgBean.getHeikenAshiClose1Min15());
		bean.setHeikenAshiHigh1Min15(avgBean.getHeikenAshiHigh1Min15());
		bean.setHeikenAshiLow1Min15(avgBean.getHeikenAshiLow1Min15());

		bean.setHeikinAshiTrendIdMin60(avgBean.getHeikinAshiTrendIdMin60());
		bean.setHeikenAshiOpen3Min60(avgBean.getHeikenAshiOpen3Min60());
		bean.setHeikenAshiClose3Min60(avgBean.getHeikenAshiClose3Min60());
		bean.setHeikenAshiHigh3Min60(avgBean.getHeikenAshiHigh3Min60());
		bean.setHeikenAshiLow3Min60(avgBean.getHeikenAshiLow3Min60());
		bean.setHeikenAshiOpen2Min60(avgBean.getHeikenAshiOpen2Min60());
		bean.setHeikenAshiClose2Min60(avgBean.getHeikenAshiClose2Min60());
		bean.setHeikenAshiHigh2Min60(avgBean.getHeikenAshiHigh2Min60());
		bean.setHeikenAshiLow2Min60(avgBean.getHeikenAshiLow2Min60());
		bean.setHeikenAshiOpen1Min60(avgBean.getHeikenAshiOpen1Min60());
		bean.setHeikenAshiClose1Min60(avgBean.getHeikenAshiClose1Min60());
		bean.setHeikenAshiHigh1Min60(avgBean.getHeikenAshiHigh1Min60());
		bean.setHeikenAshiLow1Min60(avgBean.getHeikenAshiLow1Min60());
		
			}catch(Exception e) {
				TradewareLogger.saveFatalErrorLog(
						"VolumeStrenthTrendHelper",
						"copyCurrentCandleAverages", e, ERROR_TYPE_KITE_EXCEPTION);
			}
		}
		
		
		//refactor need
		
	private void setVolumeValues(StrategyOrbDataBean bean, Long volume4, Long volume3, Long volume2, Long volume1,
			Double vol4Ratio, Double vol3Ratio, Double vol2Ratio, Double vol1Ratio, Long smallVolume) {
		bean.setVolume4(volume4);
		bean.setVolume3(volume3);
		bean.setVolume2(volume2);
		bean.setVolume1(volume1);
		bean.setVol4Ratio(vol4Ratio);
		bean.setVol3Ratio(vol3Ratio);
		bean.setVol2Ratio(vol2Ratio);
		bean.setVol1Ratio(vol1Ratio);
		bean.setSmallVolume(smallVolume);
	}
	
	
	//03-14-2021 start
	protected void caluclatePivotPoints(StrategyOrbDataBean bean, Double high, Double low, Double close) {
		double cprLowerBound = kiteConnectTool.getRoundupToTwoValue((high + low)/2);
		double cprPivotalPoint = kiteConnectTool.getRoundupToTwoValue((high + low + close)/3);
		double cprUpperBound = kiteConnectTool.getRoundupToTwoValue(((cprPivotalPoint - cprLowerBound) + cprPivotalPoint));
		double valueCPR = kiteConnectTool.getRoundupToTwoValue((cprUpperBound - cprLowerBound) * bean.getLotSize());
		
		bean.setCprLowerBound(cprLowerBound);
		bean.setCprPivotalPoint(cprPivotalPoint);
		bean.setCprUpperBound(cprUpperBound);
		bean.setValueCPR(valueCPR);
	}
	//03-14-2021 end
}
