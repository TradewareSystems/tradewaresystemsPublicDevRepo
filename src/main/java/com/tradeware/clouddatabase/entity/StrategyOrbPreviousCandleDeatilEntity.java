package com.tradeware.clouddatabase.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tradeware.clouddatabase.bean.StrategyOrbBean;

@Entity
@Table(name = "T_ORB_PREV_CNDL_DETAIL", uniqueConstraints = {
		@UniqueConstraint(name = "OPEN_RANGE_BREAK_OUT_CONSTRAINT", columnNames = {
		"OPEN_RANGE_BREAK_OUT_ID" }) })
public class StrategyOrbPreviousCandleDeatilEntity extends AbstractEntity {

	private static final long serialVersionUID = 237667789096867936L;
	
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORB_PREV_CNDL_DETAIL_ID", nullable = false, updatable = false)
	private Integer previousCandleDeatilId;*/

	@Column(name = "CNDL_1_SIZE", precision=7, scale=2)
	private Double candle1SizeAmt;
	
	@Column(name = "CNDL_2_SIZE", precision=7, scale=2)
	private Double candle2SizeAmt;
	
	@Column(name = "CNDL_3_SIZE", precision=7, scale=2)
	private Double candle3SizeAmt;
	
	@Column(name = "CNDL_4_SIZE", precision=7, scale=2)
	private Double candle4SizeAmt;
	
	@Column(name = "CNDL_HIGHS_DIFF", precision=7, scale=2)
	private Double candleHighsDiff;
	
	@Column(name = "CNDL_LOWS_DIFF", precision=7, scale=2)
	private Double candleLowsDiff;
	
	@Column(name = "CNDL_1_HIG_MINUS_CLS", precision=7, scale=2)
	private Double candle1HighMinusClose;
	
	@Column(name = "CNDL_1_CLS_MINUS_LOW", precision=7, scale=2)
	private Double candle1CloseMinusLow;
	
	@Column(name = "CNDL_2_HIG_MINUS_CLS", precision=7, scale=2)
	private Double candle2HighMinusClose;
	
	@Column(name = "CNDL_2_CLS_MINUS_LOW", precision=7, scale=2)
	private Double candle2CloseMinusLow;
	
	@Column(name = "CNDL_3_HIG_MINUS_CLS", precision=7, scale=2)
	private Double candle3HighMinusClose;
	
	@Column(name = "CNDL_3_CLS_MINUS_LOW", precision=7, scale=2)
	private Double candle3CloseMinusLow;
	
	@Column(name = "CNDL_4_HIG_MINUS_CLS", precision=7, scale=2)
	private Double candle4HighMinusClose;
	
	@Column(name = "CNDL_4_CLS_MINUS_LOW", precision=7, scale=2)
	private Double candle4CloseMinusLow;
	
	@Column(name = "CNDL_1_TYPE", length=4)
	private String candle1Type;
	
	@Column(name = "CNDL_2_TYPE", length=4)
	private String candle2Type;
	
	@Column(name = "CNDL_3_TYPE", length=4)
	private String candle3Type;
	
	@Column(name = "CNDL_4_TYPE", length=4)
	private String candle4Type;
	
	public void populateEntity(StrategyOrbBean bean) {
		//this.previousCandleDeatilId = bean.getPreviousCandleDeatilId();
		this.candle1SizeAmt = bean.getCandle1SizeAmt();
		this.candle2SizeAmt = bean.getCandle2SizeAmt();
		this.candle3SizeAmt = bean.getCandle3SizeAmt();
		this.candle4SizeAmt = bean.getCandle4SizeAmt();
		this.candleHighsDiff = bean.getCandleHighsDiff();
		this.candleLowsDiff = bean.getCandleLowsDiff();
		this.candle1HighMinusClose = bean.getCandle1HighMinusClose();
		this.candle1CloseMinusLow = bean.getCandle1CloseMinusLow();
		this.candle2HighMinusClose = bean.getCandle2HighMinusClose();
		this.candle2CloseMinusLow = bean.getCandle2CloseMinusLow();
		this.candle3HighMinusClose = bean.getCandle3HighMinusClose();
		this.candle3CloseMinusLow = bean.getCandle3CloseMinusLow();
		this.candle4HighMinusClose = bean.getCandle4HighMinusClose();
		this.candle4CloseMinusLow = bean.getCandle4CloseMinusLow();
		this.candle1Type = bean.getCandle1Type();
		this.candle2Type = bean.getCandle2Type();
		this.candle3Type = bean.getCandle3Type();
		this.candle4Type = bean.getCandle4Type();
	}
	
	public void populateBean(StrategyOrbBean bean) {
		//bean.setPreviousCandleDeatilId(this.previousCandleDeatilId);
		bean.setCandle1SizeAmt(this.candle1SizeAmt);
		bean.setCandle2SizeAmt(this.candle2SizeAmt);
		bean.setCandle3SizeAmt(this.candle3SizeAmt);
		bean.setCandle4SizeAmt(this.candle4SizeAmt);
		bean.setCandleHighsDiff(this.candleHighsDiff);
		bean.setCandleLowsDiff(this.candleLowsDiff);
		bean.setCandle1HighMinusClose(this.candle1HighMinusClose);
		bean.setCandle1CloseMinusLow(this.candle1CloseMinusLow);
		bean.setCandle2HighMinusClose(this.candle2HighMinusClose);
		bean.setCandle2CloseMinusLow(this.candle2CloseMinusLow);
		bean.setCandle3HighMinusClose(this.candle3HighMinusClose);
		bean.setCandle3CloseMinusLow(this.candle3CloseMinusLow);
		bean.setCandle4HighMinusClose(this.candle4HighMinusClose);
		bean.setCandle4CloseMinusLow(this.candle4CloseMinusLow);
		bean.setCandle1Type(this.candle1Type);
		bean.setCandle2Type(this.candle2Type);
		bean.setCandle3Type(this.candle3Type);
		bean.setCandle4Type(this.candle4Type);
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
