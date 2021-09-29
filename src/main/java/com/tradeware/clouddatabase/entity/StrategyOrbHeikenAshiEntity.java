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
import com.tradeware.stockmarket.util.Constants;

@Entity
@Table(name = "T_ORB_HEIKENASHI_DTL", uniqueConstraints = {
		@UniqueConstraint(name = "OPEN_RANGE_BREAK_OUT_CONSTRAINT", columnNames = { "OPEN_RANGE_BREAK_OUT_ID" }) })
public class StrategyOrbHeikenAshiEntity extends AbstractEntity {

	private static final long serialVersionUID = 1001208169871511080L;
	// CPR values
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "HEIKIN_ASHI_TREND_MIN5", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "VOLUME_TREND_ID")
	private VolumeTrendLookupEntity heikinAshiTrendIdMin5;
	
	
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "HEIKIN_ASHI_TREND_MIN15", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "VOLUME_TREND_ID")
	private VolumeTrendLookupEntity heikinAshiTrendIdMin15;
	
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "HEIKIN_ASHI_TREND_MIN60", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "VOLUME_TREND_ID")
	private VolumeTrendLookupEntity heikinAshiTrendIdMin60;
	
	@Column(name = "HEIKIN_ASHI_OPEN3_MIN5")
	private Double heikenAshiOpen3Min5;  
	
	@Column(name = "HEIKIN_ASHI_CLOSE3_MIN5")
	private Double heikenAshiClose3Min5; 
	
	@Column(name = "HEIKIN_ASHI_HIGH3_MIN5")
	private Double heikenAshiHigh3Min5;
	
	@Column(name = "HEIKIN_ASHI_LOW3_MIN5")
	private Double heikenAshiLow3Min5; 
	
	@Column(name = "HEIKIN_ASHI_OPEN2_MIN5")
	private Double heikenAshiOpen2Min5;
	
	@Column(name = "HEIKIN_ASHI_CLOSE2_MIN5")
	private Double heikenAshiClose2Min5; 
	
	@Column(name = "HEIKIN_ASHI_HIGH2_MIN5")
	private Double heikenAshiHigh2Min5; 
	
	@Column(name = "HEIKIN_ASHI_LOW2_MIN5")
	private Double heikenAshiLow2Min5; 
	
	@Column(name = "HEIKIN_ASHI_OPEN1_MIN5")
	private Double heikenAshiOpen1Min5; 
	
	@Column(name = "HEIKIN_ASHI_CLOSE1_MIN5")
	private Double heikenAshiClose1Min5; 
	
	@Column(name = "HEIKIN_ASHI_HIGH1_MIN5")
	private Double heikenAshiHigh1Min5; 
	
	@Column(name = "HEIKIN_ASHI_LOW1_MIN5")
	private Double heikenAshiLow1Min5; 
	
	@Column(name = "HEIKIN_ASHI_OPEN3_MIN15")
	private Double heikenAshiOpen3Min15;  
	
	@Column(name = "HEIKIN_ASHI_CLOSE3_MIN15")
	private Double heikenAshiClose3Min15; 
	
	@Column(name = "HEIKIN_ASHI_HIGH3_MIN15")
	private Double heikenAshiHigh3Min15;
	
	@Column(name = "HEIKIN_ASHI_LOW3_MIN15")
	private Double heikenAshiLow3Min15; 
	
	@Column(name = "HEIKIN_ASHI_OPEN2_MIN15")
	private Double heikenAshiOpen2Min15;
	
	@Column(name = "HEIKIN_ASHI_CLOSE2_MIN15")
	private Double heikenAshiClose2Min15; 
	
	@Column(name = "HEIKIN_ASHI_HIGH2_MIN15")
	private Double heikenAshiHigh2Min15; 
	
	@Column(name = "HEIKIN_ASHI_LOW2_MIN15")
	private Double heikenAshiLow2Min15; 
	
	@Column(name = "HEIKIN_ASHI_OPEN1_MIN15")
	private Double heikenAshiOpen1Min15; 
	
	@Column(name = "HEIKIN_ASHI_CLOSE1_MIN15")
	private Double heikenAshiClose1Min15; 
	
	@Column(name = "HEIKIN_ASHI_HIGH1_MIN15")
	private Double heikenAshiHigh1Min15; 
	
	@Column(name = "HEIKIN_ASHI_LOW1_MIN15")
	private Double heikenAshiLow1Min15; 
	
	@Column(name = "HEIKIN_ASHI_OPEN3_MIN60")
	private Double heikenAshiOpen3Min60;  
	
	@Column(name = "HEIKIN_ASHI_CLOSE3_MIN60")
	private Double heikenAshiClose3Min60; 
	
	@Column(name = "HEIKIN_ASHI_HIGH3_MIN60")
	private Double heikenAshiHigh3Min60;
	
	@Column(name = "HEIKIN_ASHI_LOW3_MIN60")
	private Double heikenAshiLow3Min60; 
	
	@Column(name = "HEIKIN_ASHI_OPEN2_MIN60")
	private Double heikenAshiOpen2Min60;
	
	@Column(name = "HEIKIN_ASHI_CLOSE2_MIN60")
	private Double heikenAshiClose2Min60; 
	
	@Column(name = "HEIKIN_ASHI_HIGH2_MIN60")
	private Double heikenAshiHigh2Min60; 
	
	@Column(name = "HEIKIN_ASHI_LOW2_MIN60")
	private Double heikenAshiLow2Min60; 
	
	@Column(name = "HEIKIN_ASHI_OPEN1_MIN60")
	private Double heikenAshiOpen1Min60; 
	
	@Column(name = "HEIKIN_ASHI_CLOSE1_MIN60")
	private Double heikenAshiClose1Min60; 
	
	@Column(name = "HEIKIN_ASHI_HIGH1_MIN60")
	private Double heikenAshiHigh1Min60; 
	
	@Column(name = "HEIKIN_ASHI_LOW1_MIN60")
	private Double heikenAshiLow1Min60; 

	public void populateEntity(StrategyOrbBean bean) {
		// this.heikinAshiTrend = bean.getHeikinAshiTrend();
		// TODO
		this.heikinAshiTrendIdMin60 = new VolumeTrendLookupEntity(
				null != bean.getHeikinAshiTrendIdMin60() ? bean.getHeikinAshiTrendIdMin60() : Constants.NA);
		this.heikenAshiOpen3Min60 = bean.getHeikenAshiOpen3Min60();
		this.heikenAshiClose3Min60 = bean.getHeikenAshiClose3Min60();
		this.heikenAshiHigh3Min60 = bean.getHeikenAshiHigh3Min60();
		this.heikenAshiLow3Min60 = bean.getHeikenAshiLow3Min60();
		this.heikenAshiOpen2Min60 = bean.getHeikenAshiOpen2Min60();
		this.heikenAshiClose2Min60 = bean.getHeikenAshiClose2Min60();
		this.heikenAshiHigh2Min60 = bean.getHeikenAshiHigh2Min60();
		this.heikenAshiLow2Min60 = bean.getHeikenAshiLow2Min60();
		this.heikenAshiOpen1Min60 = bean.getHeikenAshiOpen1Min60();
		this.heikenAshiClose1Min60 = bean.getHeikenAshiClose1Min60();
		this.heikenAshiHigh1Min60 = bean.getHeikenAshiHigh1Min60();
		this.heikenAshiLow1Min60 = bean.getHeikenAshiLow1Min60();

		this.heikinAshiTrendIdMin15 = new VolumeTrendLookupEntity(
				null != bean.getHeikinAshiTrendIdMin15() ? bean.getHeikinAshiTrendIdMin15() : Constants.NA);
		this.heikenAshiOpen3Min15 = bean.getHeikenAshiOpen3Min15();
		this.heikenAshiClose3Min15 = bean.getHeikenAshiClose3Min15();
		this.heikenAshiHigh3Min15 = bean.getHeikenAshiHigh3Min15();
		this.heikenAshiLow3Min15 = bean.getHeikenAshiLow3Min15();
		this.heikenAshiOpen2Min15 = bean.getHeikenAshiOpen2Min15();
		this.heikenAshiClose2Min15 = bean.getHeikenAshiClose2Min15();
		this.heikenAshiHigh2Min15 = bean.getHeikenAshiHigh2Min15();
		this.heikenAshiLow2Min15 = bean.getHeikenAshiLow2Min15();
		this.heikenAshiOpen1Min15 = bean.getHeikenAshiOpen1Min15();
		this.heikenAshiClose1Min15 = bean.getHeikenAshiClose1Min15();
		this.heikenAshiHigh1Min15 = bean.getHeikenAshiHigh1Min15();
		this.heikenAshiLow1Min15 = bean.getHeikenAshiLow1Min15();
		
		this.heikinAshiTrendIdMin5 = new VolumeTrendLookupEntity(
				null != bean.getHeikinAshiTrendIdMin5() ? bean.getHeikinAshiTrendIdMin5() : Constants.NA);
		this.heikenAshiOpen3Min5 = bean.getHeikenAshiOpen3Min5();
		this.heikenAshiClose3Min5 = bean.getHeikenAshiClose3Min5();
		this.heikenAshiHigh3Min5 = bean.getHeikenAshiHigh3Min5();
		this.heikenAshiLow3Min5 = bean.getHeikenAshiLow3Min5();
		this.heikenAshiOpen2Min5 = bean.getHeikenAshiOpen2Min5();
		this.heikenAshiClose2Min5 = bean.getHeikenAshiClose2Min5();
		this.heikenAshiHigh2Min5 = bean.getHeikenAshiHigh2Min5();
		this.heikenAshiLow2Min5 = bean.getHeikenAshiLow2Min5();
		this.heikenAshiOpen1Min5 = bean.getHeikenAshiOpen1Min5();
		this.heikenAshiClose1Min5 = bean.getHeikenAshiClose1Min5();
		this.heikenAshiHigh1Min5 = bean.getHeikenAshiHigh1Min5();
		this.heikenAshiLow1Min5 = bean.getHeikenAshiLow1Min5();
	}

	public void populateBean(StrategyOrbBean bean) {
		// bean.setHeikinAshiTrend(this.heikinAshiTrend);
		// TODO:
		if (null != this.heikinAshiTrendIdMin60) {
			bean.setHeikinAshiTrendIdMin60(this.heikinAshiTrendIdMin60.getVolumeTrendId());
			bean.setHeikinAshiTrendIdMin60(this.heikinAshiTrendIdMin60.getVolumeTrendDescription());
		}
		bean.setHeikenAshiOpen3Min60(this.heikenAshiOpen3Min60);
		bean.setHeikenAshiClose3Min60(this.heikenAshiClose3Min60);
		bean.setHeikenAshiHigh3Min60(this.heikenAshiHigh3Min60);
		bean.setHeikenAshiLow3Min60(this.heikenAshiLow3Min60);
		bean.setHeikenAshiOpen2Min60(this.heikenAshiOpen2Min60);
		bean.setHeikenAshiClose2Min60(this.heikenAshiClose2Min60);
		bean.setHeikenAshiHigh2Min60(this.heikenAshiHigh2Min60);
		bean.setHeikenAshiLow2Min60(this.heikenAshiLow2Min60);
		bean.setHeikenAshiOpen1Min60(this.heikenAshiOpen1Min60);
		bean.setHeikenAshiClose1Min60(this.heikenAshiClose1Min60);
		bean.setHeikenAshiHigh1Min60(this.heikenAshiHigh1Min60);
		bean.setHeikenAshiLow1Min60(this.heikenAshiLow1Min60);
		
		if (null != this.heikinAshiTrendIdMin15) {
			bean.setHeikinAshiTrendIdMin15(this.heikinAshiTrendIdMin15.getVolumeTrendId());
			bean.setHeikinAshiTrendIdMin15(this.heikinAshiTrendIdMin15.getVolumeTrendDescription());
		}
		bean.setHeikenAshiOpen3Min15(this.heikenAshiOpen3Min15);
		bean.setHeikenAshiClose3Min15(this.heikenAshiClose3Min15);
		bean.setHeikenAshiHigh3Min15(this.heikenAshiHigh3Min15);
		bean.setHeikenAshiLow3Min15(this.heikenAshiLow3Min15);
		bean.setHeikenAshiOpen2Min15(this.heikenAshiOpen2Min15);
		bean.setHeikenAshiClose2Min15(this.heikenAshiClose2Min15);
		bean.setHeikenAshiHigh2Min15(this.heikenAshiHigh2Min15);
		bean.setHeikenAshiLow2Min15(this.heikenAshiLow2Min15);
		bean.setHeikenAshiOpen1Min15(this.heikenAshiOpen1Min15);
		bean.setHeikenAshiClose1Min15(this.heikenAshiClose1Min15);
		bean.setHeikenAshiHigh1Min15(this.heikenAshiHigh1Min15);
		bean.setHeikenAshiLow1Min15(this.heikenAshiLow1Min15);
		
		if (null != this.heikinAshiTrendIdMin5) {
			bean.setHeikinAshiTrendIdMin5(this.heikinAshiTrendIdMin5.getVolumeTrendId());
			bean.setHeikinAshiTrendIdMin5(this.heikinAshiTrendIdMin5.getVolumeTrendDescription());
		}
		bean.setHeikenAshiOpen3Min5(this.heikenAshiOpen3Min5);
		bean.setHeikenAshiClose3Min5(this.heikenAshiClose3Min5);
		bean.setHeikenAshiHigh3Min5(this.heikenAshiHigh3Min5);
		bean.setHeikenAshiLow3Min5(this.heikenAshiLow3Min5);
		bean.setHeikenAshiOpen2Min5(this.heikenAshiOpen2Min5);
		bean.setHeikenAshiClose2Min5(this.heikenAshiClose2Min5);
		bean.setHeikenAshiHigh2Min5(this.heikenAshiHigh2Min5);
		bean.setHeikenAshiLow2Min5(this.heikenAshiLow2Min5);
		bean.setHeikenAshiOpen1Min5(this.heikenAshiOpen1Min5);
		bean.setHeikenAshiClose1Min5(this.heikenAshiClose1Min5);
		bean.setHeikenAshiHigh1Min5(this.heikenAshiHigh1Min5);
		bean.setHeikenAshiLow1Min5(this.heikenAshiLow1Min5);
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
