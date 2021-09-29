package com.tradeware.clouddatabase.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tradeware.clouddatabase.bean.StrategyOrbBean;
import com.tradeware.stockmarket.util.Constants;

@Entity
@Table(name = "T_ORB_PREV_CNDL_AVG_HIST", uniqueConstraints = {
		@UniqueConstraint(name = "OPEN_RANGE_BREAK_OUT_CONSTRAINT", columnNames = {
		"OPEN_RANGE_BREAK_OUT_ID" }) })
public class StrategyOrbPreviousCandleAverageHistEntity extends AbstractEntity {

	private static final long serialVersionUID = 7657046723467733937L;

	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORB_PREV_CNDL_AVG_HIST_ID", nullable = false, updatable = false)
	private Integer previousCandleAvgHistId;
*/
	@Column(name = "CURRENT_CNDL_OPEN", precision=7, scale=2)
	private Double currentCandleOpen;//In other entity also
	
	//1minute
	@Column(name = "CNDL_AVG_HIH_1_MIN", precision=7, scale=2)
	private Double avgHigh1min;
	
	@Column(name = "CNDL_AVG_LOW_1_MIN", precision=7, scale=2)
	private Double avgLow1min;
	
	@Column(name = "CNDL_AVG_CLOSE_1_MIN", precision=7, scale=2)
	private Double avgClose1min;
	
	@Column(name = "CNDL_AVG_HIGH_MINUS_CLS_1_MIN", precision=7, scale=2)
	private Double avgHighMinusClose1min;
	
	@Column(name = "CNDL_AVG_CLS_MINUS_LOW_1_MIN", precision=7, scale=2)
	private Double avgCloseMinusLow1min;
	
	//5minutes
	@Column(name = "CNDL_AVG_HIH_5_MIN", precision=7, scale=2)
	private Double avgHigh5min;
	
	@Column(name = "CNDL_AVG_LOW_5_MIN", precision=7, scale=2)
	private Double avgLow5min;
	
	@Column(name = "CNDL_AVG_CLOSE_5_MIN", precision=7, scale=2)
	private Double avgClose5min;
	
	@Column(name = "CNDL_AVG_HIGH_MINUS_CLS_5_MIN", precision=7, scale=2)
	private Double avgHighMinusClose5min;
	
	@Column(name = "CNDL_AVG_CLS_MINUS_LOW_5_MIN", precision=7, scale=2)
	private Double avgCloseMinusLow5min;
	
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "HEIKIN_ASHI_TREND_5_MIN", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "VOLUME_TREND_ID")
	private VolumeTrendLookupEntity min5HeikinAshiTrendId;
	
	public void populateEntity(StrategyOrbBean bean) {
		//this.previousCandleAvgHistId = bean.getPreviousCandleAvgHistId();
		this.currentCandleOpen = bean.getCurrentCandleOpen();
		this.avgHigh1min = bean.getAvgHigh1min();
		this.avgLow1min = bean.getAvgLow1min();
		this.avgClose1min = bean.getAvgClose1min();
		this.avgHighMinusClose1min = bean.getAvgHighMinusClose1min();
		this.avgCloseMinusLow1min = bean.getAvgCloseMinusLow1min();
		this.avgHigh5min = bean.getAvgHigh5min();
		this.avgLow5min = bean.getAvgLow5min();
		this.avgClose5min = bean.getAvgClose5min();
		this.avgHighMinusClose5min = bean.getAvgHighMinusClose5min();
		this.avgCloseMinusLow5min = bean.getAvgCloseMinusLow5min();
		this.min5HeikinAshiTrendId = new VolumeTrendLookupEntity(
				null != bean.getMin5HeikinAshiTrendId() ? bean.getMin5HeikinAshiTrendId() : Constants.NA);
	}

	public void populateBean(StrategyOrbBean bean) {
		//bean.setPreviousCandleAvgHistId(this.previousCandleAvgHistId);
		bean.setCurrentCandleOpen(this.currentCandleOpen);
		bean.setAvgHigh1min(this.avgHigh1min);
		bean.setAvgLow1min(this.avgLow1min);
		bean.setAvgClose1min(this.avgClose1min);
		bean.setAvgHighMinusClose1min(this.avgHighMinusClose1min);
		bean.setAvgCloseMinusLow1min(this.avgCloseMinusLow1min);
		bean.setAvgHigh5min(this.avgHigh5min);
		bean.setAvgLow5min(this.avgLow5min);
		bean.setAvgClose5min(this.avgClose5min);
		bean.setAvgHighMinusClose5min(this.avgHighMinusClose5min);
		bean.setAvgCloseMinusLow5min(this.avgCloseMinusLow5min);
		if (null != this.min5HeikinAshiTrendId) {
			bean.setMin5HeikinAshiTrendId(this.min5HeikinAshiTrendId.getVolumeTrendId());
			bean.setMin5HeikinAshiTrendIdDescription(this.min5HeikinAshiTrendId.getVolumeTrendDescription());
			}
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
