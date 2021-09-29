package com.tradeware.clouddatabase.bean;

public class OptionStrangleLiveTradeBean extends AbstractBean {
	private static final long serialVersionUID = -3529377001690091193L;

	private String symbolId;
	private String expiryDate;
	private String optionTradeStrategy;
	private String optionTradeSubStrategy;
	private Integer strikePricePut;
	private String strikePricePutTradeType;
	private Integer strikePriceCall;
	private String strikePriceCallTradeType;
	private String kiteFutureKeyPut;
	private String kiteFutureKeyCall;
	
	private Double bidPriceOfPut;//buyerAt
	private Double askPriceOfPut;//sellerAt
	private Integer bidQuantityOfPut;
	private Integer askQuantityOfPut; 
	private Double bidPriceOfCall;//buyerAt
	private Double askPriceOfCall;//sellerAt
	private Integer bidQuantityOfCall;
	private Integer askQuantityOfCall; 
	private Double underlyingStockPrice;
	private Double underlyingFuturePrice;
	private Integer lotSize;
	private Integer numberOfLots;
	
}
