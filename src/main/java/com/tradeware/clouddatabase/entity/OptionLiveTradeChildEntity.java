package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tradeware.clouddatabase.bean.OptionLiveTradeChildBean;

/*@Entity
@Table(name = "T_OPTION_TRADE_CHILD", uniqueConstraints = {
		@UniqueConstraint(name = "OPTION_TRADE_MAIN_CONSTRAINT", columnNames = { "OPTION_TRADE_MAIN_ID" }) })
*/
@Entity/*(name = "T_OPTION_TRADE_CHILD")*/
@Table(name = "T_OPTION_TRADE_CHILD")
public class OptionLiveTradeChildEntity extends AbstractEntity {

	private static final long serialVersionUID = -5660536719759828136L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OPTION_TRADE_CHILD_ID", unique = true, nullable = false, columnDefinition = "serial")
	private Integer itemIdChild;
	
	@Column(name = "STRIKE_PRICE", precision=12)
	private Double strikePrice;
	
	@Column(name = "TRADE_TYPE", length=6)
	private String tradeType;
	
	@Column(name = "TRADE_POSITION", length=6)
	private String tradePosition; //OPEN/CLOSE
	
	@Column(name = "OPTION_TYPE", length=6)
	private String optionType; //Call/Put
	
	@Column(name = "KITE_OPTION_KEY", length=36)
	private String kiteFutureKey;
	
	@Column(name = "BUY_AT_VALUE", precision=7, scale=2)
	private Double buyAtValue;//buyerAt
	
	@Column(name = "SELL_AT_VALUE", precision=7, scale=2)
	private Double sellAtValue;//sellerAt

	@Column(name = "TRADED_AT_VAL", precision=7, scale=2)
	private Double tradedAtVal;
	
	@Column(name = "EXITED_AT_VAL", precision=7, scale=2)
	private Double exitedAtVal;
	
	@Column(name = "STOCK_PRICE_ENTRY", precision=7, scale=2)
	private Double stockPriceEntry;
	
	@Column(name = "FUTURE_PRICE_ENTRY", precision=7, scale=2)
	private Double futurePriceEntry;
	
	@Column(name = "STOCK_PRICE_EXIT", precision=7, scale=2)
	private Double stockPriceExit;
	
	@Column(name = "FUTURE_PRICE_EXIT", precision=7, scale=2)
	private Double futurePriceExit;
	
	@Column(name = "NUMBER_OF_LOTS", precision=6)
	private Integer numberOfLots;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRADED_AT_DT_TM")
	private Date tradedAtDtTm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXITED_AT_DT_TM")
	private Date exitedAtDtTm;
	
	@Column(name = "PROF_LOS_AMT_VAL", precision=7, scale=2)
	private Double profitLossAmtVal; 
	
	@Column(name = "POS_MOVE_VAL", precision=7, scale=2)
	private Double positiveMoveVal;
	
	@Column(name = "NEG_MOVE_VAL", precision=7, scale=2)
	private Double negativeMoveVal;
	
	@Column(name = "POS_MOVE_LTP", precision=7, scale=2)
	private Double positiveMoveLtp;
	
	@Column(name = "NEG_MOVE_LTP", precision=7, scale=2)
	private Double negativeMoveLtp;
	
	@Column(name = "DT_STAMP")
	@Temporal(TemporalType.DATE)
	private Date tradedDateStamp;
	
	@Column(name = "OI_TREND")
	private String optionChainTrend;
	
	@Column(name = "OI_PRICE_TREND")
	private String optionChainPriceTrend;
	
	@Column(name = "OPTION_CHAIN_ID")
	private Integer optionChainId;
	
	@Column(name = "OI_INFO", length=128)
	private String oiInfo;
	
	@Column(name = "BID_PRICE", precision=7, scale=2)
	private Double bidPrice;//buyerAt
	
	@Column(name = "ASK_PRICE", precision=7, scale=2)
	private Double askPrice;//sellerAt
	
	@Column(name = "LAST_PRICE", precision=7, scale=2)
	private Double lastPrice;
	
	@Column(name = "BID_QUANTTY", precision=32)
	private Long bidQuantity;
	
	@Column(name = "ASK_QUANTTY", precision=32)
	private Long askQuantity; 
	
	@Column(name = "KITE_INSTRUMENT_TOKEN")
	private Long instrumentToken;
	
	@Column(name = "KITE_ORDER_ID")
	private String kiteOrderId;
	
	@Column(name = "KITE_ORDER_TYPE")
	private String kiteOrderType;//CO/BO/Regular
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_OPTION_TRADE_MAIN_ID", referencedColumnName="OPTION_TRADE_MAIN_ID")
	private OptionLiveTradeMainEntity optionLiveTradeMainEntity;
	public OptionLiveTradeMainEntity getOptionLiveTradeMainEntity() {
		return optionLiveTradeMainEntity;
	}
	public void setOptionLiveTradeMainEntity(OptionLiveTradeMainEntity optionLiveTradeMainEntity) {
		this.optionLiveTradeMainEntity = optionLiveTradeMainEntity;
	}
	
	
	/////////
	@Column(name = "STOP_LOSS_VAL", precision=7, scale=2)
	private Double stopLossVal;
	
	@Column(name = "TARGET_VAL", precision=7, scale=2)
	private Double targetVal;
	
	
	/////////

	public void populateEntity(OptionLiveTradeChildBean bean) {
		this.itemIdChild = bean.getItemIdChild();
		this.strikePrice = bean.getStrikePrice();
		this.tradeType = bean.getTradeType();
		this.tradePosition = bean.getTradePosition();
		this.optionType = bean.getOptionType();
		this.kiteFutureKey = bean.getKiteFutureKey();
		this.buyAtValue = bean.getBuyAtValue();
		this.sellAtValue = bean.getSellAtValue();
		this.tradedAtVal = bean.getTradedAtVal();
		this.exitedAtVal = bean.getExitedAtVal();
		this.stockPriceEntry = bean.getStockPriceEntry();
		this.futurePriceEntry = bean.getFuturePriceEntry();
		this.stockPriceExit = bean.getStockPriceExit();
		this.futurePriceExit = bean.getFuturePriceExit();
		this.numberOfLots = bean.getNumberOfLots();
		this.tradedAtDtTm = bean.getTradedAtDtTm();
		this.exitedAtDtTm = bean.getExitedAtDtTm();
		this.profitLossAmtVal = bean.getProfitLossAmtVal();
		this.positiveMoveVal = bean.getPositiveMoveVal();
		this.negativeMoveVal = bean.getNegativeMoveVal();
		this.positiveMoveLtp = bean.getPositiveMoveLtp();
		this.negativeMoveLtp = bean.getNegativeMoveLtp();
		this.tradedDateStamp = bean.getTradedDateStamp();
		this.optionChainTrend = bean.getOptionChainTrend();
		this.optionChainPriceTrend = bean.getOptionChainPriceTrend();
		this.optionChainId = bean.getOptionChainId();
		this.oiInfo = bean.getOiInfo();
		this.bidPrice = bean.getBidPrice();
		this.askPrice = bean.getAskPrice();
		this.lastPrice = bean.getLastPrice();
		this.bidQuantity = bean.getBidQuantity();
		this.askQuantity = bean.getAskQuantity();
		this.instrumentToken = bean.getInstrumentToken();
		this.kiteOrderId = bean.getKiteOrderId();
		this.kiteOrderType = bean.getKiteOrderType();
		this.stopLossVal = bean.getStopLossVal();
		this.targetVal = bean.getTargetVal();
	}
	
	public void populateBean(OptionLiveTradeChildBean bean) {
		bean.setItemIdChild(this.itemIdChild);
		bean.setStrikePrice(this.strikePrice);
		bean.setTradeType(this.tradeType);
		bean.setTradePosition(this.tradePosition);
		bean.setOptionType(this.optionType);
		bean.setKiteFutureKey(this.kiteFutureKey);
		bean.setBuyAtValue(this.buyAtValue);
		bean.setSellAtValue(this.sellAtValue);
		bean.setTradedAtVal(this.tradedAtVal);
		bean.setExitedAtVal(this.exitedAtVal);
		bean.setStockPriceEntry(this.stockPriceEntry);
		bean.setFuturePriceEntry(this.futurePriceEntry);
		bean.setStockPriceExit(this.stockPriceExit);
		bean.setFuturePriceExit(this.futurePriceExit);
		bean.setNumberOfLots(this.numberOfLots);
		bean.setTradedAtDtTm(this.tradedAtDtTm);
		bean.setExitedAtDtTm(this.exitedAtDtTm);
		bean.setProfitLossAmtVal(this.profitLossAmtVal);
		bean.setPositiveMoveVal(this.positiveMoveVal);
		bean.setNegativeMoveVal(this.negativeMoveVal);
		bean.setPositiveMoveLtp(this.positiveMoveLtp);
		bean.setNegativeMoveLtp(this.negativeMoveLtp);
		bean.setTradedDateStamp(this.tradedDateStamp);
		bean.setOptionChainTrend(this.optionChainTrend);
		bean.setOptionChainPriceTrend(this.optionChainPriceTrend);
		bean.setOptionChainId(this.optionChainId);
		bean.setOiInfo(this.oiInfo);
		bean.setBidPrice(this.bidPrice);
		bean.setAskPrice(this.askPrice);
		bean.setLastPrice(this.lastPrice);
		bean.setBidQuantity(this.bidQuantity);
		bean.setAskQuantity(this.askQuantity);
		bean.setInstrumentToken(this.instrumentToken);
		bean.setKiteOrderId(this.kiteOrderId);
		bean.setKiteOrderType(this.kiteOrderType);
		bean.setStopLossVal(this.stopLossVal);
		bean.setTargetVal(this.targetVal);
	}
}
