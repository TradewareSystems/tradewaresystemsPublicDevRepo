package com.tradeware.clouddatabase.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tradeware.clouddatabase.bean.StrategyOrbBean;

@Entity
@Table(name = "T_ORB_PREV_DAY_HIST", uniqueConstraints = {
		@UniqueConstraint(name = "OPEN_RANGE_BREAK_OUT_CONSTRAINT", columnNames = {
		"OPEN_RANGE_BREAK_OUT_ID" }) })
public class StrategyOrbPreviousDayHistEntity extends AbstractEntity {

	private static final long serialVersionUID = -7603993932558329696L;

	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORB_PREV_DAY_HIST_ID", nullable = false, updatable = false)
	private Integer prevDayHistId;*/
	
	@Column(name = "PREV_DAY_HR_BUY_AT_VAL", precision=7, scale=2)
	private Double prevDayHrBuyAtVal;
	
	@Column(name = "PREV_DAY_HR_SELL_AT_VAL", precision=7, scale=2)
	private Double prevDayHrSellAtVal;
	
	@Column(name = "PREV_DAY_HR_CROSS_IND")
	private Boolean prevDayHrCrossInd;
	
	@Column(name = "PREV_DAY_HIGH", precision=7, scale=2)
	private Double prevDayHigh;
	
	@Column(name = "PREV_DAY_LOW", precision=7, scale=2)
	private Double prevDayLow;
	
	@Column(name = "PREV_DAY_LVL_CROSS_IND")
	private Boolean prevDayLevelCrossInd;
	
	@Column(name = "PREV_DAY_PERCEN_CHANGE", precision=5, scale=2)
	private Double prevDayPercentageChange;
	
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "CNDL_TYPE_TREND", nullable = true, 
		updatable = true, insertable = true, unique = false, referencedColumnName = "TRADE_STATE_ID")
	private TradeStateLookupEntity candleTypeTrendId;
	
	public void populateEntity(StrategyOrbBean bean) {
		//this.prevDayHistId = bean.getPrevDayHistId();
		this.prevDayHrBuyAtVal = bean.getPrevDayHrBuyAtVal();
		this.prevDayHrSellAtVal = bean.getPrevDayHrSellAtVal();
		this.prevDayHrCrossInd = bean.getPrevDayHrCrossInd();
		this.prevDayHigh = bean.getPrevDayHigh();
		this.prevDayLow = bean.getPrevDayLow();
		this.prevDayLevelCrossInd = bean.getPrevDayLevelCrossInd();
		this.prevDayPercentageChange = bean.getPrevDayPercentageChange();
		this.candleTypeTrendId = new TradeStateLookupEntity(bean.getCandleTypeTrendId());
	}
	
	public void populateBean(StrategyOrbBean bean) {
		 //bean.setPrevDayHistId(this.prevDayHistId);
		 bean.setPrevDayHrBuyAtVal(this.prevDayHrBuyAtVal);
		 bean.setPrevDayHrSellAtVal(this.prevDayHrSellAtVal);
		 bean.setPrevDayHrCrossInd(this.prevDayHrCrossInd);
		 bean.setPrevDayHigh(this.prevDayHigh);
		 bean.setPrevDayLow(this.prevDayLow);
		 bean.setPrevDayLevelCrossInd(this.prevDayLevelCrossInd);
		 bean.setPrevDayPercentageChange(this.prevDayPercentageChange);
		 bean.setCandleTypeTrendId(this.candleTypeTrendId.getTradeStateId());
		 bean.setCandleTypeTrendDescription(this.candleTypeTrendId.getTradeStateDescription());
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
