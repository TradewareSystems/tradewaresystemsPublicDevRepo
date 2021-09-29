package com.tradeware.stockmarket.bean;

import java.util.Date;

public class StrategyGapUpDownDataBean extends AbstractTradeDataBean {

	private static final long serialVersionUID = 7049971424190468168L;

	public StrategyGapUpDownDataBean(Integer lotSize, String symbolId) {
		super(lotSize, symbolId);
	}

	public StrategyGapUpDownDataBean clone() {
		StrategyGapUpDownDataBean bean = new StrategyGapUpDownDataBean(super.getLotSize(), super.getSymbolId());
		super.clone(bean);

		// bean.setStrengthTradableState(strengthTradableState);
		return bean;
	}

	private Double tradedBuyAtVal;
	private Double exitedBuyAtVal;
	private Date tradedBuyAtDtTm;
	private Date exitedBuyAtDtTm;
	
	private Double tradedSellAtVal;
	private Double exitedSellAtVal;
	private Date tradedSellAtDtTm;
	private Date exitedSellAtDtTm;
	
	private String tradedStateBuyId;
	private String tradedStateSellId;
	private Double prevDayPercentageChange;
	
	private Double first5minCndlHigh;
	private Double first5minCndlLow;
	
}
