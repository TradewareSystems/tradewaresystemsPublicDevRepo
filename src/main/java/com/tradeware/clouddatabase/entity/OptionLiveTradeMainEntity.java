package com.tradeware.clouddatabase.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.tradeware.clouddatabase.bean.OptionLiveTradeChildBean;
import com.tradeware.clouddatabase.bean.OptionLiveTradeMainBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "T_OPTION_TRADE_MAIN", uniqueConstraints = {
		@UniqueConstraint(name = "OPTION_TRADE_MAIN_CONSTRAINT", columnNames = {
				"OPTION_TRADE_MAIN_ID"/* ,"col2" */ }) })
public class OptionLiveTradeMainEntity extends AbstractEntity {

	private static final long serialVersionUID = -7159026868996827779L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OPTION_TRADE_MAIN_ID", unique = true, nullable = false, columnDefinition="serial")
	private Integer tradeId;
	
	@Column(name = "TRADE_NM", length=256)
	private String tradeName;
	
	@Column(name = "SYMBOL_ID", length=32)
	private String symbolId;
	
	@Column(name = "LOT_SIZE", precision=6)
	private Integer lotSize;
	
	@Column(name = "OPT_TICK_SIZE")
	private Double optionTickerSize;
	
	@Column(name = "EXPIRY_DATE", length=24)
	private String expiryDate;
	
	@Column(name = "OPT_STRATEGY", length=36)
	private String tradeStrategy;
	
	@Column(name = "OPT_SUB_STRATEGY", length=36)
	private String tradeSubStrategy;
	
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
	
	@Column(name = "DT_STAMP")
	@Temporal(TemporalType.DATE)
	private Date tradedDateStamp;
	
	@Column(name = "TRADE_POSITION", length=6)
	private String tradePosition; //OPEN/CLOSE
	
	@Column(name = "COMMENTS", length=1024)
	private String comments;
	
	@OneToMany(mappedBy = "optionLiveTradeMainEntity", fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("tradedAtDtTm ASC")
	//@OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "FK_OPTION_TRADE_MAIN_ID")
	private List<OptionLiveTradeChildEntity> tradeChildEntity;
	
	// properties specific to Butter Fly start
	@Column(name = "ATM_STRIKE_PRICE", precision = 7, scale = 2)
	private Double atmStrikePrice;
	// properties specific to Butter Fly start
	
	@Column(name = "BREAK_EVEN_LOWER_1", precision=7, scale=2)
	private Double breakEvenLower1;
	
	@Column(name = "BREAK_EVEN_HIGHER_1", precision=7, scale=2)
	private Double breakEvenHigher1;
	
	@Column(name = "STOP_LOSS_VAL", precision=7, scale=2)
	private Double stopLossVal;
	
	@Column(name = "TARGET_VAL", precision=7, scale=2)
	private Double targetVal;
	
	@Column(name = "INTRA_TRADE_IND")
	private Boolean intradayTradeInd;
	
	public void populateEntity(OptionLiveTradeMainBean bean) {
		this.tradeId = bean.getTradeId();
		this.tradeName = bean.getTradeName();
		this.symbolId = bean.getSymbolId();
		this.lotSize = bean.getLotSize();
		this.optionTickerSize = bean.getOptionTickerSize();
		this.expiryDate = bean.getExpiryDate();
		this.tradeStrategy = bean.getTradeStrategy();
		this.tradeSubStrategy = bean.getTradeSubStrategy();
		this.tradedAtDtTm = bean.getTradedAtDtTm();
		this.exitedAtDtTm = bean.getExitedAtDtTm();
		this.profitLossAmtVal = bean.getProfitLossAmtVal();
		this.positiveMoveVal = bean.getPositiveMoveVal();
		this.negativeMoveVal = bean.getNegativeMoveVal();
		this.tradedDateStamp = bean.getTradedDateStamp();
		this.tradePosition = bean.getTradePosition();
		this.comments = bean.getComments();
		this.breakEvenLower1 = bean.getBreakEvenLower1();
		this.breakEvenHigher1 = bean.getBreakEvenHigher1();
		this.atmStrikePrice = bean.getAtmStrikePrice();
		this.stopLossVal = bean.getStopLossVal();
		this.targetVal = bean.getTargetVal();
		this.intradayTradeInd = bean.getIntradayTradeInd();
		
		if (null != bean.getTradeChildBeanList() && !bean.getTradeChildBeanList().isEmpty()) {
			List<OptionLiveTradeChildEntity> tradeChildEntityList = new ArrayList<OptionLiveTradeChildEntity>();
			for (OptionLiveTradeChildBean childBean : bean.getTradeChildBeanList()) {
				OptionLiveTradeChildEntity childEntity = new OptionLiveTradeChildEntity();
				childEntity.populateEntity(childBean);
				childEntity.setOptionLiveTradeMainEntity(this);
				tradeChildEntityList.add(childEntity);
			}
			this.tradeChildEntity = tradeChildEntityList;
		}
	}
	
	public OptionLiveTradeMainBean populateBean() {
		OptionLiveTradeMainBean bean = new OptionLiveTradeMainBean();
		bean.setTradeId(this.tradeId);
		bean.setTradeName(this.tradeName);
		bean.setSymbolId(this.symbolId);
		bean.setLotSize(this.lotSize);
		bean.setOptionTickerSize(this.optionTickerSize);
		bean.setExpiryDate(this.expiryDate);
		bean.setTradeStrategy(this.tradeStrategy);
		bean.setTradeSubStrategy(this.tradeSubStrategy);
		bean.setTradedAtDtTm(this.tradedAtDtTm);
		bean.setExitedAtDtTm(this.exitedAtDtTm);
		bean.setProfitLossAmtVal(this.profitLossAmtVal);
		bean.setPositiveMoveVal(this.positiveMoveVal);
		bean.setNegativeMoveVal(this.negativeMoveVal);
		bean.setTradedDateStamp(this.tradedDateStamp);
		bean.setTradePosition(this.tradePosition);
		bean.setComments(this.comments);
		bean.setBreakEvenLower1(this.breakEvenLower1);
		bean.setBreakEvenHigher1(this.breakEvenHigher1);
		bean.setAtmStrikePrice(this.atmStrikePrice);
		bean.setStopLossVal(this.stopLossVal);
		bean.setTargetVal(this.targetVal);
		bean.setIntradayTradeInd(this.intradayTradeInd);
		
		if (null != tradeChildEntity && !tradeChildEntity.isEmpty()) {
			List<OptionLiveTradeChildBean> tradeChildBeanList = new ArrayList<OptionLiveTradeChildBean>();
			for (OptionLiveTradeChildEntity childEntity : tradeChildEntity) {
				OptionLiveTradeChildBean childBean = new OptionLiveTradeChildBean();
				childEntity.populateBean(childBean);
				tradeChildBeanList.add(childBean);
			}
			bean.setTradeChildBeanList(tradeChildBeanList);
		}
		
		return bean;
	}
}
