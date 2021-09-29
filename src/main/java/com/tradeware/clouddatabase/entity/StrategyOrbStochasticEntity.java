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
@Table(name = "T_ORB_STOCHASTIC_DTL", uniqueConstraints = {
		@UniqueConstraint(name = "OPEN_RANGE_BREAK_OUT_CONSTRAINT", columnNames = { "OPEN_RANGE_BREAK_OUT_ID" }) })
public class StrategyOrbStochasticEntity extends AbstractEntity {

	private static final long serialVersionUID = 1001208169871511080L;
	// CPR values
	@Column(name = "STOCH_MIN_60_VAL_K1")
	private Double min60StochasticValK1;
	
	@Column(name = "STOCH_MIN_60_VAL_D3")
	private Double min60StochasticValD3;
	
	@Column(name = "STOCH_MIN_60_VAL_K2")
	private Double min60StochasticValK2;
	
	@Column(name = "STOCH_MIN_60_VAL_D4")
	private Double min60StochasticValD4;
	
	@Column(name = "STOCH_MIN_60_VAL_K3")
	private Double min60StochasticValK3;
	
	@Column(name = "STOCH_MIN_60_VAL_D5")
	private Double min60StochasticValD5;

	@Column(name = "STOCH_MIN_60_TREND")
	private String min60StochasticTrend;

	@Column(name = "STOCH_MIN_15_VAL_K1")
	private Double min15StochasticValK1;
	
	@Column(name = "STOCH_MIN_15_VAL_D3")
	private Double min15StochasticValD3;
	
	@Column(name = "STOCH_MIN_15_VAL_K2")
	private Double min15StochasticValK2;
	
	@Column(name = "STOCH_MIN_15_VAL_D4")
	private Double min15StochasticValD4;
	
	@Column(name = "STOCH_MIN_15_VAL_K3")
	private Double min15StochasticValK3;
	
	@Column(name = "STOCH_MIN_15_VAL_D5")
	private Double min15StochasticValD5;

	@Column(name = "STOCH_MIN_15_TREND")
	private String min15StochasticTrend;

	@Column(name = "STOCH_MIN_5_VAL_K1")
	private Double min5StochasticValK1;
	
	@Column(name = "STOCH_MIN_5_VAL_D3")
	private Double min5StochasticValD3;
	
	@Column(name = "STOCH_MIN_5_VAL_K2")
	private Double min5StochasticValK2;
	
	@Column(name = "STOCH_MIN_5_VAL_D4")
	private Double min5StochasticValD4;
	
	@Column(name = "STOCH_MIN_5_VAL_K3")
	private Double min5StochasticValK3;
	
	@Column(name = "STOCH_MIN_5_VAL_D5")
	private Double min5StochasticValD5;

	@Column(name = "STOCH_MIN_5_TREND")
	private String min5StochasticTrend;
	
	@Column(name = "STOCH_BASIC_RULE1_IND")
	private Boolean stochasticBasicRule1Ind;

	public void populateEntity(StrategyOrbBean bean) {
		this.min60StochasticValK1 = bean.getMin60StochasticValK1();
		this.min60StochasticValD3 = bean.getMin60StochasticValD3();
		this.min60StochasticValK2 = bean.getMin60StochasticValK2();
		this.min60StochasticValD4 = bean.getMin60StochasticValD4();
		this.min60StochasticValK3 = bean.getMin60StochasticValK3();
		this.min60StochasticValD5 = bean.getMin60StochasticValD5();
		this.min60StochasticTrend = bean.getMin60StochasticTrend();

		this.min15StochasticValK1 = bean.getMin15StochasticValK1();
		this.min15StochasticValD3 = bean.getMin15StochasticValD3();
		this.min15StochasticValK2 = bean.getMin15StochasticValK2();
		this.min15StochasticValD4 = bean.getMin15StochasticValD4();
		this.min15StochasticValK3 = bean.getMin15StochasticValK3();
		this.min15StochasticValD5 = bean.getMin15StochasticValD5();
		this.min15StochasticTrend = bean.getMin15StochasticTrend();
		
		this.min5StochasticValK1 = bean.getMin5StochasticValK1();
		this.min5StochasticValD3 = bean.getMin5StochasticValD3();
		this.min5StochasticValK2 = bean.getMin5StochasticValK2();
		this.min5StochasticValD4 = bean.getMin5StochasticValD4();
		this.min5StochasticValK3 = bean.getMin5StochasticValK3();
		this.min5StochasticValD5 = bean.getMin5StochasticValD5();
		this.min5StochasticTrend = bean.getMin5StochasticTrend();
		this.stochasticBasicRule1Ind = bean.getStochasticBasicRule1Ind();
	}

	public void populateBean(StrategyOrbBean bean) {
		bean.setMin60StochasticValK1(this.min60StochasticValK1);
		bean.setMin60StochasticValD3(this.min60StochasticValD3);
		bean.setMin60StochasticValK2(this.min60StochasticValK2);
		bean.setMin60StochasticValD4(this.min60StochasticValD4);
		bean.setMin60StochasticValK3(this.min60StochasticValK3);
		bean.setMin60StochasticValD5(this.min60StochasticValD5);
		bean.setMin60StochasticTrend(this.min60StochasticTrend);
		
		bean.setMin15StochasticValK1(this.min15StochasticValK1);
		bean.setMin15StochasticValD3(this.min15StochasticValD3);
		bean.setMin15StochasticValK2(this.min15StochasticValK2);
		bean.setMin15StochasticValD4(this.min15StochasticValD4);
		bean.setMin15StochasticValK3(this.min15StochasticValK3);
		bean.setMin15StochasticValD5(this.min15StochasticValD5);
		bean.setMin15StochasticTrend(this.min15StochasticTrend);
		
		bean.setMin5StochasticValK1(this.min5StochasticValK1);
		bean.setMin5StochasticValD3(this.min5StochasticValD3);
		bean.setMin5StochasticValK2(this.min5StochasticValK2);
		bean.setMin5StochasticValD4(this.min5StochasticValD4);
		bean.setMin5StochasticValK3(this.min5StochasticValK3);
		bean.setMin5StochasticValD5(this.min5StochasticValD5);
		bean.setMin5StochasticTrend(this.min5StochasticTrend);
		bean.setStochasticBasicRule1Ind(this.stochasticBasicRule1Ind);
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
