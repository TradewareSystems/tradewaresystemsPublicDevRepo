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
@Table(name = "T_ORB_TECH_INDICATOR_DTL", uniqueConstraints = {
		@UniqueConstraint(name = "OPEN_RANGE_BREAK_OUT_CONSTRAINT", columnNames = { "OPEN_RANGE_BREAK_OUT_ID" }) })
public class StrategyOrbTechnicalIndicatorEntity extends AbstractEntity {

	private static final long serialVersionUID = 1001208169871511080L;
	// CPR values
	@Column(name = "CPR_LOWER_BOUND")
	private Double cprLowerBound;

	@Column(name = "CPR_PIVOT_POINT")
	private Double cprPivotalPoint;

	@Column(name = "CPR_UPPER_BOUND")
	private Double cprUpperBound;

	@Column(name = "CPR_VAL")
	private Double valueCPR;

	public void populateEntity(StrategyOrbBean bean) {
		this.cprLowerBound = bean.getCprLowerBound();
		this.cprPivotalPoint = bean.getCprPivotalPoint();
		this.cprUpperBound = bean.getCprUpperBound();
		this.valueCPR = bean.getValueCPR();
	}

	public void populateBean(StrategyOrbBean bean) {
		bean.setCprLowerBound(this.cprLowerBound);
		bean.setCprPivotalPoint(this.cprPivotalPoint);
		bean.setCprUpperBound(this.cprUpperBound);
		bean.setValueCPR(this.valueCPR);
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
