package com.tradeware.clouddatabase.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tradeware.clouddatabase.bean.StrategyOrbBean;

@Entity
@Table(name = "T_ORB_PREV_CNDL_OHLC", uniqueConstraints = {
		@UniqueConstraint(name = "OPEN_RANGE_BREAK_OUT_CONSTRAINT", columnNames = {
		"OPEN_RANGE_BREAK_OUT_ID" }) })
public class StrategyOrbPreviousCandleOHLCEntity extends AbstractEntity {
	
	private static final long serialVersionUID = -4854347594746211083L;
	
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORB_PREV_CNDL_OHLC_ID", nullable = false, updatable = false)
	private Integer previousCandleOhlcId;*/
	
	@Column(name = "OPEN_1", precision=13, scale=2)
	private Double openCandle1;
	
	@Column(name = "HIGH_1", precision=13, scale=2)
	private Double highCandle1;
	
	@Column(name = "LOW_1", precision=13, scale=2)
	private Double lowCandle1;
	
	@Column(name = "CLOSE_1", precision=13, scale=2)
	private Double closeCandle1;
	
	@Column(name = "OPEN_2", precision=13, scale=2)
	private Double openCandle2;
	
	@Column(name = "HIGH_2", precision=13, scale=2)
	private Double highCandle2;
	
	@Column(name = "LOW_2", precision=13, scale=2)
	private Double lowCandle2;
	
	@Column(name = "CLOSE_2", precision=13, scale=2)
	private Double closeCandle2;
	
	@Column(name = "OPEN_3", precision=13, scale=2)
	private Double openCandle3;
	
	@Column(name = "HIGH_3", precision=13, scale=2)
	private Double highCandle3;
	
	@Column(name = "LOW_3", precision=13, scale=2)
	private Double lowCandle3;
	
	@Column(name = "CLOSE_3", precision=13, scale=2)
	private Double closeCandle3;
	
	@Column(name = "OPEN_4", precision=13, scale=2)
	private Double openCandle4;
	
	@Column(name = "HIGH_4", precision=13, scale=2)
	private Double highCandle4;
	
	@Column(name = "LOW_4", precision=13, scale=2)
	private Double lowCandle4;
	
	@Column(name = "CLOSE_4", precision=13, scale=2)
	private Double closeCandle4;
	
	public void populateEntity(StrategyOrbBean bean) {
		//this.previousCandleOhlcId = bean.getPreviousCandleOhlcId();
		this.openCandle1 = bean.getOpenCandle1();
		this.highCandle1 = bean.getHighCandle1();
		this.lowCandle1 = bean.getLowCandle1();
		this.closeCandle1 = bean.getCloseCandle1();
		this.openCandle2 = bean.getOpenCandle2();
		this.highCandle2 = bean.getHighCandle2();
		this.lowCandle2 = bean.getLowCandle2();
		this.closeCandle2 = bean.getCloseCandle2();
		this.openCandle3 = bean.getOpenCandle3();
		this.highCandle3 = bean.getHighCandle3();
		this.lowCandle3 = bean.getLowCandle3();
		this.closeCandle3 = bean.getCloseCandle3();
		this.openCandle4 = bean.getOpenCandle4();
		this.highCandle4 = bean.getHighCandle4();
		this.lowCandle4 = bean.getLowCandle4();
		this.closeCandle4 = bean.getCloseCandle4();
	}

	public void populateBean(StrategyOrbBean bean) {
		//bean.setPreviousCandleOhlcId(this.previousCandleOhlcId);
		bean.setOpenCandle1(this.openCandle1);
		bean.setHighCandle1(this.highCandle1);
		bean.setLowCandle1(this.lowCandle1);
		bean.setCloseCandle1(this.closeCandle1);
		bean.setOpenCandle2(this.openCandle2);
		bean.setHighCandle2(this.highCandle2);
		bean.setLowCandle2(this.lowCandle2);
		bean.setCloseCandle2(this.closeCandle2);
		bean.setOpenCandle3(this.openCandle3);
		bean.setHighCandle3(this.highCandle3);
		bean.setLowCandle3(this.lowCandle3);
		bean.setCloseCandle3(this.closeCandle3);
		bean.setOpenCandle4(this.openCandle4);
		bean.setHighCandle4(this.highCandle4);
		bean.setLowCandle4(this.lowCandle4);
		bean.setCloseCandle4(this.closeCandle4);
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
