package org.tradeware.stockmarket.engine.tool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.tradeware.stockmarket.bean.StockDataBean;
import org.tradeware.stockmarket.engine.googlesheettool.GoogleSheetUtil;
import org.tradeware.stockmarket.engine.nseindiatool.NSEIndiaTool;
import org.tradeware.stockmarket.util.Constants;
import org.tradeware.stockmarket.util.StockUtil;

import com.zerodhatech.models.OHLC;
import com.zerodhatech.models.Quote;

public class FreeCallDataTool {

	private static List<String> freeCallList = null;
	private static List<String> freeCallFutKeyList = null;
	private static FreeCallDataTool singletonTool = null;
	private static Map<String, StockDataBean> freeCallsMap = null;

	public static FreeCallDataTool getInstance() {
		if (null == singletonTool) {
			freeCallList = new ArrayList<String>(Constants.TWENTY);
			freeCallFutKeyList = new ArrayList<String>(Constants.TWENTY);
			freeCallsMap = new HashMap<String, StockDataBean>();
			singletonTool = new FreeCallDataTool();
		}
		return singletonTool;
	}

	public List<String> prepareRandom20StockFreeCall() {
		if (!freeCallList.isEmpty()) {
			return freeCallList;
		}

		Map<String, StockDataBean> positionalResultDataMap = NSEIndiaTool.getInstance().getPositionalResultDataMap();
		int maxSymbolIndex = positionalResultDataMap.size();
		List<String> symbols = positionalResultDataMap.keySet().stream().collect(Collectors.toList());
		long randomNumber = 777;
		randomNumber = System.currentTimeMillis() % randomNumber;
		while (freeCallList.size() < Constants.TWENTY) {
			long randomSymbolIndex = System.currentTimeMillis();
			randomSymbolIndex = randomSymbolIndex % randomNumber;
			while (randomSymbolIndex > maxSymbolIndex) {
				randomSymbolIndex = randomSymbolIndex % maxSymbolIndex;
			}
			String findedSymbol = symbols.get((int) randomSymbolIndex);
			if (!freeCallList.contains(findedSymbol)) {
				int findedSymbolLotSize = StockUtil.getSymbols().get(findedSymbol);
				if (findedSymbolLotSize >= Constants.TWO_HUNDRED && findedSymbolLotSize <= Constants.TARGET_2000) {
					StockDataBean bean = positionalResultDataMap.get(findedSymbol);// clone()
					freeCallList.add(findedSymbol);
					freeCallsMap.put(findedSymbol, bean);
					freeCallFutKeyList.add(bean.getKiteFutureKey());
					randomNumber = bean.getPositionalUptrendValue().intValue();
				}
			}
		}
		Collections.sort(freeCallList);
		return freeCallList;
	}

	public List<String> getFreeCallFutKeyList() {
		return freeCallFutKeyList;
	}

	// updateFreeCallDataMapOHLCWithFullQuotes
	public void updateFreeCallDataMap(Map<String, Quote> quotesMap) {
		for (String symbol : freeCallList) {
			StockDataBean bean = freeCallsMap.get(symbol);

			OHLC ohlc = quotesMap.get(bean.getKiteFutureKey()).ohlc;
			double lastPrice = quotesMap.get(bean.getKiteFutureKey()).lastPrice;
			double open = ohlc.open;
			double high = ohlc.high;
			double low = ohlc.low;
			double close = ohlc.close;

			if (Constants.ZERO_DOUBLE == open || Constants.ZERO_DOUBLE == lastPrice) {
				bean.setLtp(new BigDecimal(Constants.ZERO_DOUBLE));
				bean.setOpen(new BigDecimal(Constants.ZERO_DOUBLE));
				bean.setHigh(new BigDecimal(Constants.ZERO_DOUBLE));
				bean.setLow(new BigDecimal(Constants.ZERO_DOUBLE));
				bean.setClose(new BigDecimal(Constants.ZERO_DOUBLE));
				bean.setIntradayUptrendValue(new BigDecimal(Constants.ZERO_DOUBLE));
				bean.setIntradayDowntrendValue(new BigDecimal(Constants.ZERO_DOUBLE));
			} else {
				bean.setLtp(bean.getScaledValueObj(lastPrice));
				bean.setOpen(bean.getScaledValueObj(open));
				bean.setHigh(bean.getScaledValueObj(high));
				bean.setLow(bean.getScaledValueObj(low));
				bean.setClose(bean.getScaledValueObj(close));
				if ((null == bean.getIntradayUptrendValue()
						|| Constants.ZERO_DOUBLE == bean.getIntradayUptrendValue().doubleValue())
						|| (null == bean.getIntradayDowntrendValue()
								|| Constants.ZERO_DOUBLE == bean.getIntradayDowntrendValue().doubleValue())) {
					double intrayUptrendval = (open + bean.getLast5DaysAvgMin().doubleValue());
					double intrayDowntrendval = (open - bean.getLast5DaysAvgMin().doubleValue());

					bean.setIntradayUptrendValue(bean.getScaledValueObj(intrayUptrendval));
					bean.setIntradayDowntrendValue(bean.getScaledValueObj(intrayDowntrendval));
				}
				bean.setVolumeTradedToday(
						bean.getScaledValueObj(quotesMap.get(bean.getKiteFutureKey()).volumeTradedToday));
				bean.setBuyQuantity(bean.getScaledValueObj(quotesMap.get(bean.getKiteFutureKey()).buyQuantity));
				bean.setSellQuantity(bean.getScaledValueObj(quotesMap.get(bean.getKiteFutureKey()).sellQuantity));
				bean.setOiToday(bean.getScaledValueObj(quotesMap.get(bean.getKiteFutureKey()).oi));
				bean.setChange(bean.getScaledValueObj(quotesMap.get(bean.getKiteFutureKey()).change));
				// calculate profit loss
				if (!Constants.DONE.equals(bean.getTradeState())) {
					if (Constants.NA.equals(bean.getTradeState())) {
						if (bean.getLtp().doubleValue() > bean.getIntradayUptrendValue().doubleValue()) {
							bean.setTradedVal(bean.getScaledValueObj(bean.getLtp().doubleValue()));
							bean.setTradeState(Constants.BUY);
						} else if (bean.getLtp().doubleValue() < bean.getIntradayDowntrendValue().doubleValue()) {
							bean.setTradedVal(bean.getScaledValueObj(bean.getLtp().doubleValue()));
							bean.setTradeState(Constants.SELL);
						}
					} else if (Constants.BUY.equals(bean.getTradeState())) {
						double netProfitLoss = (bean.getLtp().doubleValue() - bean.getTradedValAsDouble())
								* bean.getLotSize();
						if (netProfitLoss >= Constants.TARGET_2000 || netProfitLoss <= Constants.MAX_LOSS_2000) {
							bean.setTradeState(Constants.DONE);
							bean.setProfitLossAmt(bean.getScaledValueObj(netProfitLoss));
						}
					} else if (Constants.SELL.equals(bean.getTradeState())) {
						double netProfitLoss = (bean.getTradedValAsDouble() - bean.getLtp().doubleValue())
								* bean.getLotSize();
						if (netProfitLoss >= Constants.TARGET_2000 || netProfitLoss <= Constants.MAX_LOSS_2000) {
							bean.setTradeState(Constants.DONE);
							bean.setProfitLossAmt(bean.getScaledValueObj(netProfitLoss));
						}

					}
				}
			}
		}
		GoogleSheetUtil.getInstance().publishFreeCallDataUpdates(freeCallsMap);
	}
}
