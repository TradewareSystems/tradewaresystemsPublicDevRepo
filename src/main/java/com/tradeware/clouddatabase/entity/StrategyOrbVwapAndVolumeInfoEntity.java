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
@Table(name = "T_ORB_VWAP_VOLUME_DETAIL", uniqueConstraints = {
		@UniqueConstraint(name = "OPEN_RANGE_BREAK_OUT_CONSTRAINT", columnNames = {
		"OPEN_RANGE_BREAK_OUT_ID" }) })
public class StrategyOrbVwapAndVolumeInfoEntity extends AbstractEntity {

	private static final long serialVersionUID = -5952254517027794854L;

	/*@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORB_VWAP_VOLUME_DETAIL_ID", nullable = false, updatable = false)
	private Integer vwapVolumeDetailId;*/
	
	@Column(name = "VWAP_VAL", precision=7, scale=2)
	private Double vwapValue;//In other entity also
	
	@Column(name = "VOL_STRNTH_STY_CLS", length=16)
	private String volStrengthStyleClass;
	
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "VOLUME_TRADE_STATE_ID", nullable = true, 
		updatable = true, insertable = true, unique = false, referencedColumnName = "VOLUME_TREND_ID")
	private VolumeTrendLookupEntity volumeTradeStateId;

	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "VWAP_TRADE_STATE_ID", nullable = true, 
		updatable = true, insertable = true, unique = false, referencedColumnName = "VOLUME_TREND_ID")
	private VolumeTrendLookupEntity vwapTradeStateId;
	
	@Column(name = "CNDL_4_VOL")
	private Long volume4;
	
	@Column(name = "CNDL_3_VOL")
	private Long volume3;
	
	@Column(name = "CNDL_2_VOL")
	private Long volume2;
	
	@Column(name = "CNDL_1_VOL")
	private Long volume1;
	
	@Column(name = "SMALL_VOL")
	private Long smallVolume;
	
	@Column(name = "CNDL_4_VOL_RATIO", precision=7, scale=2)
	private Double vol4Ratio;
	
	@Column(name = "CNDL_3_VOL_RATIO", precision=7, scale=2)
	private Double vol3Ratio;
	
	@Column(name = "CNDL_2_VOL_RATIO", precision=7, scale=2)
	private Double vol2Ratio;
	
	@Column(name = "CNDL_1_VOL_RATIO", precision=7, scale=2)
	private Double vol1Ratio;
	
	public void populateEntity(StrategyOrbBean bean) {
		//this.vwapVolumeDetailId = bean.getPreviousCandleAvgHistId();
		this.vwapValue = bean.getVwapValue();
		this.volStrengthStyleClass = bean.getVolStrengthStyleClass();
		this.volumeTradeStateId = new VolumeTrendLookupEntity(bean.getVolumeTradeStateId());
		this.vwapTradeStateId = new VolumeTrendLookupEntity(bean.getVwapTradeStateId());
		this.volume4 = bean.getVolume4();
		this.volume3 = bean.getVolume3();
		this.volume2 = bean.getVolume2();
		this.volume1 = bean.getVolume1();
		this.smallVolume = bean.getSmallVolume();
		this.vol4Ratio = bean.getVol4Ratio();
		this.vol3Ratio = bean.getVol3Ratio();
		this.vol2Ratio = bean.getVol2Ratio();
		this.vol1Ratio = bean.getVol1Ratio();
	}

	public void populateBean(StrategyOrbBean bean) {
		//this.vwapVolumeDetailId = bean.getPreviousCandleAvgHistId();
		bean.setVwapValue(this.vwapValue);
		bean.setVolStrengthStyleClass(this.volStrengthStyleClass);
		bean.setVolumeTradeStateId(this.volumeTradeStateId.getVolumeTrendId());
		bean.setVolumeTradeStateDescription(this.volumeTradeStateId.getVolumeTrendDescription());
		bean.setVwapTradeStateId(this.vwapTradeStateId.getVolumeTrendId());
		bean.setVwapTradeStateDescription(this.vwapTradeStateId.getVolumeTrendDescription());
		bean.setVolume4(this.volume4);
		bean.setVolume3(this.volume3);
		bean.setVolume2(this.volume2);
		bean.setVolume1(this.volume1);
		bean.setSmallVolume(this.smallVolume);
		bean.setVol4Ratio(this.vol4Ratio);
		bean.setVol3Ratio(this.vol3Ratio);
		bean.setVol2Ratio(this.vol2Ratio);
		bean.setVol1Ratio(this.vol1Ratio);
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
