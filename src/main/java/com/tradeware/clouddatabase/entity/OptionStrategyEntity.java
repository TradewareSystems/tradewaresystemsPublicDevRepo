package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.tradeware.clouddatabase.bean.OptionStrategyBean;
import com.tradeware.stockmarket.util.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "T_OPTION_TRADE", uniqueConstraints = {
		@UniqueConstraint(name = "OPTION_TRADE_CONSTRAINT", columnNames = {
				"OPTION_TRADE_ID"/* ,"col2" */ }) })
public class OptionStrategyEntity extends AbstractEntity {

	private static final long serialVersionUID = -995503667687397893L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OPTION_TRADE_ID", unique = true, nullable = false, columnDefinition="serial")
	private Integer itemId;
	
	@Column(name = "SYMBOL_ID", length=32)
	private String symbolId;
	
	@Column(name = "SYMBOL_NAME", length=32)
	private String symbolName;
	
	@Column(name = "LOT_SIZE", precision=6)
	private Double lotSize;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRADED_AT_DT_TM")
	private Date tradedAtDtTm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXITED_AT_DT_TM")
	private Date exitedAtDtTm;
	
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "TRADABLE_STATE_ID", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "TRADE_STATE_ID")
	private TradeStateLookupEntity tradableStateId;

	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "TRADED_STATE_ID", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "TRADE_STATE_ID")
	private TradeStateLookupEntity tradedStateId;
	
	@Column(name = "TRADED_LOT_COUNT")
	private Integer tradedLotCount;//In other entity also
	
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
	
	@Column(name = "TARGET_PRICE", precision=7, scale=2)
	private Double targetPrice;
	
	@Column(name = "STOP_LOSS", precision = 7, scale = 2, nullable = true)
	private Double stopLoss;
	
	@Column(name = "PROF_LOS_AMT_VAL_FINAL", precision=9, scale=2)
	private Double profitLossAmtValFinal;
	
	@Column(name = "MNAL_TRADE_EXIT_IND")
	private Boolean manualTradeExitInd;
	
	@Column(name = "MNAL_EXITED_AT_VAL", precision=7, scale=2)
	private Double manualExitedAtVal;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MNAL_EXITED_AT_DT_TM")
	private Date manualExitedAtDtTm;
	
	@Column(name = "MNAL_PROF_LOS_AMT_VAL", precision=7, scale=2)
	private Double manualBookProfitLossAmtVal;
	
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "MNAL_TRADE_EXIT_STATE_ID", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "TRADE_STATE_ID")
	private TradeStateLookupEntity manualTradeExitStateId;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "optionStrategyEntity")
	private  OptionStrategyHedgeEntity optionStrategyHedgeEntity;
	
	
	@Column(name = "OPTION_TRADE_STRATEGE", length=128)
	private String optionTradeStrategy;
	
	@Column(name = "OPTION_TRADE_SUB_STRATEGE", length=128)
	private String optionTradeSubStrategy;
	
	@Column(name = "STOCK_PRICE", precision=7, scale=2)
	private Double underlyingStockPrice;
	
	@Column(name = "FUTURE_PRICE", precision=7, scale=2)
	private Double underlyingFuturePrice;

	public void populateEntity(OptionStrategyBean bean) {
		this.symbolId = bean.getSymbolId();
		this.symbolName = bean.getSymbolName();
		this.lotSize = bean.getLotSize();
		this.tradedAtDtTm = bean.getTradedAtDtTm();
		this.exitedAtDtTm = bean.getExitedAtDtTm();
		this.tradableStateId = new TradeStateLookupEntity(bean.getTradableStateId());
		this.tradedStateId = new TradeStateLookupEntity(bean.getTradedStateId());
		this.tradedLotCount = bean.getTradedLotCount();
		this.tradedDateStamp = bean.getTradedDateStamp();
		this.profitLossAmtValFinal = bean.getProfitLossAmtValFinal();
		
		this.oiInfo = bean.getOiInfo();
		this.optionChainTrend = bean.getOptionChainTrend();
		this.optionChainPriceTrend = bean.getOptionChainPriceTrend();
		this.optionChainId = bean.getOptionChainId();
		this.targetPrice = bean.getTargetPrice();
		this.stopLoss = bean.getStopLoss();
		
		this.manualTradeExitInd = bean.getManualTradeExitInd();
		this.manualExitedAtVal = bean.getManualExitedAtVal();
		this.manualExitedAtDtTm = bean.getManualExitedAtDtTm(); 
		this.manualBookProfitLossAmtVal = bean.getManualBookProfitLossAmtVal();
		//TODO
		this.manualTradeExitStateId = new TradeStateLookupEntity(
				null != bean.getManualTradeExitStateId() ? bean.getManualTradeExitStateId() : Constants.NA);
		
		optionStrategyHedgeEntity = new OptionStrategyHedgeEntity();
		optionStrategyHedgeEntity.populateEntity(bean);
		optionStrategyHedgeEntity.setOptionStrategyEntity(this);
		
		
		this.optionTradeStrategy = bean.getOptionTradeStrategy();
		this.optionTradeSubStrategy = bean.getOptionTradeSubStrategy();
		this.underlyingStockPrice = bean.getUnderlyingStockPrice();
		this.underlyingFuturePrice = bean.getUnderlyingFuturePrice();
	}
	
	public void populateBean(OptionStrategyBean bean) {
		bean.setItemId(this.itemId);
		bean.setSymbolId(this.symbolId);
		bean.setSymbolName(this.symbolName);
		bean.setLotSize(this.lotSize);
		bean.setTradedAtDtTm(this.tradedAtDtTm);
		bean.setExitedAtDtTm(this.exitedAtDtTm);
		bean.setTradableStateId(this.tradableStateId.getTradeStateId());
		bean.setTradableStateDesc(this.tradableStateId.getTradeStateDescription());
		bean.setTradedStateId(this.tradedStateId.getTradeStateId());
		bean.setTradedStateDesc(this.tradedStateId.getTradeStateDescription());
		bean.setTradedLotCount(this.tradedLotCount);
		bean.setOiInfo(this.oiInfo);
		bean.setTradedDateStamp(this.tradedDateStamp);
				
		bean.setOptionChainTrend(this.optionChainTrend);
		bean.setOptionChainPriceTrend(this.optionChainPriceTrend);
		bean.setOptionChainId(this.optionChainId);
		bean.setProfitLossAmtValFinal(this.profitLossAmtValFinal);
		
		bean.setTargetPrice(this.targetPrice);
		bean.setStopLoss(this.stopLoss);

		bean.setManualTradeExitInd(this.manualTradeExitInd);
		bean.setManualExitedAtVal(this.manualExitedAtVal);
		bean.setManualExitedAtDtTm(this.manualExitedAtDtTm);
		bean.setManualBookProfitLossAmtVal(this.manualBookProfitLossAmtVal);
		//TODO:
		if (null != this.manualTradeExitStateId) {
		bean.setManualTradeExitStateId(this.manualTradeExitStateId.getTradeStateId());
		bean.setManualTradeExitStateDesc(this.manualTradeExitStateId.getTradeStateDescription());
		}
		
		if (null != optionStrategyHedgeEntity) {
			optionStrategyHedgeEntity.populateBean(bean);
		}
		
		bean.setOptionTradeStrategy(this.optionTradeStrategy);
		bean.setOptionTradeSubStrategy(this.optionTradeSubStrategy);
		bean.setUnderlyingStockPrice(this.underlyingStockPrice);
		bean.setUnderlyingFuturePrice(this.underlyingFuturePrice);
	}
}
