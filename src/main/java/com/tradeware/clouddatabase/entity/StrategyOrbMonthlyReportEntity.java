package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tradeware.clouddatabase.bean.StrategyOrbMonthlyReportBean;

@Entity
@Table(name = "T_OPEN_RANGE_BREAK_OUT_MONTHLY")
public class StrategyOrbMonthlyReportEntity extends AbstractEntity {

	private static final long serialVersionUID = -6910815743655948235L;

	@Id 
	@Column(name = "DT_TM_STAMP")
	@Temporal(TemporalType.DATE)
	private Date tradedDateStamp;
	
	@Column(name = "TRADE_COUNT")
	private Integer tradeCountPerDay;
	
	@Column(name = "PROF_LOS_AMT_VAL")
	private Double profitLossAmtVal; 
	
	@Column(name = "PROF_LOS_AMT_VAL_FINAL")
	private Double profitLossAmtValFinal;
	
	@Column(name = "PROF_LOS_AMT_VAL_MNAL_FINAL")
	private Double profitLossAmtValManalFinal;
	
	public void populateEntity(StrategyOrbMonthlyReportBean bean) {
		this.tradeCountPerDay = bean.getTradeCountPerDay();
		this.profitLossAmtVal = bean.getProfitLossAmtVal();
		this.tradedDateStamp = bean.getTradedDateStamp();
		this.profitLossAmtValFinal = bean.getProfitLossAmtValFinal();
		this.profitLossAmtValManalFinal = bean.getProfitLossAmtValManalFinal();
	}
	
	public void populateBean(StrategyOrbMonthlyReportBean bean) {
		bean.setTradeCountPerDay(this.tradeCountPerDay);
		bean.setProfitLossAmtVal(this.profitLossAmtVal);
		bean.setTradedDateStamp(this.tradedDateStamp);
		bean.setProfitLossAmtValFinal(this.profitLossAmtValFinal);
		bean.setProfitLossAmtValManalFinal(this.profitLossAmtValManalFinal);
	}
}