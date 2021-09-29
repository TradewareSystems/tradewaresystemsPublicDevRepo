package org.tradeware.stockmarket.engine.tool;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.tradeware.stockmarket.bean.Narrow7StockDataBean;
import org.tradeware.stockmarket.bean.StockDataBean;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;
import org.tradeware.stockmarket.util.Constants;

import com.zerodhatech.models.OHLC;
import com.zerodhatech.models.OHLCQuote;
import com.zerodhatech.models.Quote;

public class AlgoLevelUpdaterTool {
	private static AlgoLevelUpdaterTool singletonTool = null;

	public static AlgoLevelUpdaterTool getInstance() {
		if (null == singletonTool) {
			singletonTool = new AlgoLevelUpdaterTool();
		}
		return singletonTool;
	}

	public void updateIntradyLevels(Map<String, OHLCQuote> quotesMap) {
		ConcurrentHashMap<String, StockDataBean> dataMap = NSEIndiaTool.getInstance().getPositionalResultDataMap();
		for (String symbol : dataMap.keySet()) {
			StockDataBean bean = dataMap.get(symbol);

			OHLC ohlc = quotesMap.get(bean.getKiteFutureKey()).ohlc;
			double open = ohlc.open;

			if (Constants.ZERO_DOUBLE == open) {
				bean.setIntradayUptrendValue(new BigDecimal(Constants.ZERO_DOUBLE));
				bean.setIntradayDowntrendValue(new BigDecimal(Constants.ZERO_DOUBLE));
			} else {
				bean.setOpen(bean.getScaledValueObj(open));
				if ((null == bean.getIntradayUptrendValue()
						|| Constants.ZERO_DOUBLE == bean.getIntradayUptrendValue().doubleValue())
						|| (null == bean.getIntradayDowntrendValue()
								|| Constants.ZERO_DOUBLE == bean.getIntradayDowntrendValue().doubleValue())) {
					double intrayUptrendval = (open + bean.getLast5DaysAvgMin().doubleValue());
					double intrayDowntrendval = (open - bean.getLast5DaysAvgMin().doubleValue());

					bean.setIntradayUptrendValue(bean.getScaledValueObj(intrayUptrendval));
					bean.setIntradayDowntrendValue(bean.getScaledValueObj(intrayDowntrendval));
				}
			}
			NSEIndiaTool.getInstance().getPositionalResultDataMap().replace(symbol, bean);
		}
	}

	public void updateNarrow7Levels(Map<String, Quote> quotesMap) {
		ConcurrentHashMap<String, Narrow7StockDataBean> dataMap = NSEIndiaTool.getInstance().getNarrow7Map();
		for (String symbol : dataMap.keySet()) {
			Narrow7StockDataBean bean = dataMap.get(symbol);

			double lastPrice = quotesMap.get(bean.getKiteFutureKey()).lastPrice;
			OHLC ohlc = quotesMap.get(bean.getKiteFutureKey()).ohlc;
			double open = ohlc.open;
			bean.setOpen(bean.getScaledValueObj(open));
			bean.setLtp(bean.getScaledValueObj(lastPrice));
			NSEIndiaTool.getInstance().getNarrow7Map().replace(symbol, bean);
		}
	}
}
