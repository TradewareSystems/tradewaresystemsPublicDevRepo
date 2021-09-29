package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.tradeware.clouddatabase.bean.StrategyOrbBean;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "T_ORB_MULTI_LVL_TRADE", uniqueConstraints = {
		@UniqueConstraint(name = "OPEN_RANGE_BREAK_OUT_CONSTRAINT", columnNames = {
		"OPEN_RANGE_BREAK_OUT_ID" }) })
public class StrategyOrbMultilevelTradingEntity extends AbstractEntity {

	private static final long serialVersionUID = -7146204625586485599L;

	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORB_MULTI_LVL_TRADE_ID", nullable = false, updatable = false)
	private Integer multiLevelTradeId;*/
	
	@Column(name = "TRADED_LOT_COUNT")
	private Integer tradedLotCount;//In other entity also
	
	@Column(name = "TRADED_AT_AVG_VAL", precision=7, scale=2)
	private Double tradedAtAvgVal;
	
	@Column(name = "TARGET_AMT_VAL", precision=7, scale=2)
	private Double targetAmtVal;
	
	@Column(name = "TRADED_AT_VAL_2", precision=7, scale=2)
	private Double tradedAtVal2;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRADED_AT_DT_TM_2")
	private Date tradedAtDtTm2;
	
	@Column(name = "TARGET_AMT_VAL_2", precision=7, scale=2)
	private Double targetAmtVal2;
	
	@Column(name = "BUY_SELL_DIFF_VAL_2", precision=7, scale=2)
	private Double buyerSellerDiffVal2;
	
	@Column(name = "TRADED_AT_VAL_3", precision=7, scale=2)
	private Double tradedAtVal3;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRADED_AT_DT_TM_3")
	private Date tradedAtDtTm3;
	
	@Column(name = "TARGET_AMT_VAL_3", precision=7, scale=2)
	private Double targetAmtVal3;
	
	@Column(name = "BUY_SELL_DIFF_VAL_3", precision=7, scale=2)
	private Double buyerSellerDiffVal3;
	
	public void populateEntity(StrategyOrbBean bean) {
		//this.multiLevelTradeId = bean.getMultiLevelTradeId();
		this.tradedLotCount = bean.getTradedLotCount();
		this.tradedAtAvgVal = bean.getTradedAtAvgVal();
		this.targetAmtVal = bean.getTargetAmtVal();
		this.tradedAtVal2 = bean.getTradedAtVal2();
		this.tradedAtDtTm2 = bean.getTradedAtDtTm2();
		this.targetAmtVal2 = bean.getTargetAmtVal2();
		this.buyerSellerDiffVal2 = bean.getBuyerSellerDiffVal2();
		this.tradedAtVal3 = bean.getTradedAtVal3();
		this.tradedAtDtTm3 = bean.getTradedAtDtTm3();
		this.targetAmtVal3 = bean.getTargetAmtVal3();
		this.buyerSellerDiffVal3 = bean.getBuyerSellerDiffVal3();
	}
	
	public void populateBean(StrategyOrbBean bean) {
		 //bean.setMultiLevelTradeId(this.multiLevelTradeId);
		 bean.setTradedLotCount(this.tradedLotCount);
		 bean.setTradedAtAvgVal(this.tradedAtAvgVal);
		 bean.setTargetAmtVal(this.targetAmtVal);
		 bean.setTradedAtVal2(this.tradedAtVal2);
		 bean.setTradedAtDtTm2(this.tradedAtDtTm2);
		 bean.setTargetAmtVal2(this.targetAmtVal2);
		 bean.setBuyerSellerDiffVal2(this.buyerSellerDiffVal2);
		 bean.setTradedAtVal3(this.tradedAtVal3);
		 bean.setTradedAtDtTm3(this.tradedAtDtTm3);
		 bean.setTargetAmtVal3(this.targetAmtVal3);
		 bean.setBuyerSellerDiffVal3(this.buyerSellerDiffVal3);
	}
	
	@Id
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "OPEN_RANGE_BREAK_OUT_ID")
	@MapsId
	private StrategyOrbEntity strategyOrbEntity;

	public StrategyOrbEntity getStrategyOrbEntity() {
		return strategyOrbEntity;
	}

	public void setStrategyOrbEntity(StrategyOrbEntity strategyOrbEntity) {
		this.strategyOrbEntity = strategyOrbEntity;
	}
}
