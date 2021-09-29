package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tradeware.clouddatabase.bean.ProfitLossSummaryBean;

@Entity
@Table(name = "T_PROF_LOSS_SUMM")
public class ProfitLossSummaryEntity extends AbstractEntity {

	private static final long serialVersionUID = 3036259852615107521L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROF_LOSS_SUMM_ID")
	private Integer profitLossSummaryId;

	@Column(name = "STRATEGY_RULE")
	private String strategyRule;
	
	@Column(name = "PROF_LOSS_VAL_LOSS_5K")
	private Double profitLossBookedValueMinus5k;
	
	@Column(name = "DT_TM_STAMP_LOSS_5K", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStampMinus5k;

	@Column(name = "PROF_LOSS_VAL_5K")
	private Double profitLossBookedValue5k;

	@Column(name = "DT_TM_STAMP_5K", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp5k;

	@Column(name = "PROF_LOSS_VAL_10K")
	private Double profitLossBookedValue10k;

	@Column(name = "DT_TM_STAMP_10K", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp10k;

	@Column(name = "PROF_LOSS_VAL_15K")
	private Double profitLossBookedValue15k;

	@Column(name = "DT_TM_STAMP_15K", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp15k;

	@Column(name = "PROF_LOSS_VAL_20K")
	private Double profitLossBookedValue20k;

	@Column(name = "DT_TM_STAMP_20K", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp20k;

	@Column(name = "PROF_LOSS_VAL_25K")
	private Double profitLossBookedValue25k;

	@Column(name = "DT_TM_STAMP_25K", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp25k;

	@Column(name = "PROF_LOSS_VAL_30K")
	private Double profitLossBookedValue30k;

	@Column(name = "DT_TM_STAMP_30K", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp30k;

	@Column(name = "PROF_LOSS_VAL_35K")
	private Double profitLossBookedValue35k;

	@Column(name = "DT_TM_STAMP_35K", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp35k;

	@Column(name = "PROF_LOSS_VAL_40K")
	private Double profitLossBookedValue40k;

	@Column(name = "DT_TM_STAMP_40K", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp40k;

	@Column(name = "PROF_LOSS_VAL_45K")
	private Double profitLossBookedValue45k;

	@Column(name = "DT_TM_STAMP_45K", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp45k;

	@Column(name = "PROF_LOSS_VAL_50K")
	private Double profitLossBookedValue50k;

	@Column(name = "DT_TM_STAMP_50K", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp50k;

	@Column(name = "MAX_PROF_VALUE")
	private Double maxProfitMoveValue;

	@Column(name = "MAX_LOSS_VALUE")
	private Double maxLossMoveValue;

	@Column(name = "DT_TM_STAMP")
	@Temporal(TemporalType.DATE)
	private Date dateStamp;

	@Column(name = "DAY_PROF_LOSS_VAL")
	private Double dayProfitLossValue;

	@Column(name = "DT_TM_STAMP_DAY_PROF_LOSS", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStampDayProfitLoss;

	@Column(name = "SCANNER_UPDT")
	private Boolean scannerUpdatable;

	@Column(name = "TRAIL_STOP_LOSS_VAL")
	private Double trailingStopLossValue;

	public void populateEntity(ProfitLossSummaryBean bean) {
		this.profitLossSummaryId = bean.getProfitLossSummaryId();
		this.strategyRule = bean.getStrategyRule();
		this.profitLossBookedValueMinus5k = bean.getProfitLossBookedValueMinus5k();
		this.dateTimeStampMinus5k = bean.getDateTimeStampMinus5k();
		this.profitLossBookedValue5k = bean.getProfitLossBookedValue5k();
		this.dateTimeStamp5k = bean.getDateTimeStamp5k();
		this.profitLossBookedValue10k = bean.getProfitLossBookedValue10k();
		this.dateTimeStamp10k = bean.getDateTimeStamp10k();
		this.profitLossBookedValue15k = bean.getProfitLossBookedValue15k();
		this.dateTimeStamp15k = bean.getDateTimeStamp15k();
		this.profitLossBookedValue20k = bean.getProfitLossBookedValue20k();
		this.dateTimeStamp20k = bean.getDateTimeStamp20k();
		this.profitLossBookedValue25k = bean.getProfitLossBookedValue25k();
		this.dateTimeStamp25k = bean.getDateTimeStamp25k();
		this.profitLossBookedValue30k = bean.getProfitLossBookedValue30k();
		this.dateTimeStamp30k = bean.getDateTimeStamp30k();
		this.profitLossBookedValue35k = bean.getProfitLossBookedValue35k();
		this.dateTimeStamp35k = bean.getDateTimeStamp35k();
		this.profitLossBookedValue40k = bean.getProfitLossBookedValue40k();
		this.dateTimeStamp40k = bean.getDateTimeStamp40k();
		this.profitLossBookedValue45k = bean.getProfitLossBookedValue45k();
		this.dateTimeStamp45k = bean.getDateTimeStamp45k();
		this.profitLossBookedValue50k = bean.getProfitLossBookedValue50k();
		this.dateTimeStamp50k = bean.getDateTimeStamp50k();
		this.maxProfitMoveValue = bean.getMaxProfitMoveValue();
		this.maxLossMoveValue = bean.getMaxLossMoveValue();
		this.dateStamp = bean.getDateStamp();

		this.dayProfitLossValue = bean.getDayProfitLossValue();
		this.dateTimeStampDayProfitLoss = bean.getDateTimeStampDayProfitLoss();
		this.scannerUpdatable = bean.getScannerUpdatable();
		this.trailingStopLossValue = bean.getTrailingStopLossValue();
	}

	public ProfitLossSummaryBean populateBean() {
		ProfitLossSummaryBean bean = new ProfitLossSummaryBean();
		bean.setProfitLossSummaryId(this.profitLossSummaryId);
		bean.setStrategyRule(this.strategyRule);
		bean.setProfitLossBookedValue5k(this.profitLossBookedValue5k);
		bean.setDateTimeStamp5k(this.dateTimeStamp5k);
		bean.setProfitLossBookedValue10k(this.profitLossBookedValue10k);
		bean.setDateTimeStamp10k(this.dateTimeStamp10k);
		bean.setProfitLossBookedValue15k(this.profitLossBookedValue15k);
		bean.setDateTimeStamp15k(this.dateTimeStamp15k);
		bean.setProfitLossBookedValue20k(this.profitLossBookedValue20k);
		bean.setDateTimeStamp20k(this.dateTimeStamp20k);
		bean.setProfitLossBookedValue25k(this.profitLossBookedValue25k);
		bean.setDateTimeStamp25k(this.dateTimeStamp25k);
		bean.setProfitLossBookedValue30k(this.profitLossBookedValue30k);
		bean.setDateTimeStamp30k(this.dateTimeStamp30k);
		bean.setProfitLossBookedValue35k(this.profitLossBookedValue35k);
		bean.setDateTimeStamp35k(this.dateTimeStamp35k);
		bean.setProfitLossBookedValue40k(this.profitLossBookedValue40k);
		bean.setDateTimeStamp40k(this.dateTimeStamp40k);
		bean.setProfitLossBookedValue45k(this.profitLossBookedValue45k);
		bean.setDateTimeStamp45k(this.dateTimeStamp45k);
		bean.setProfitLossBookedValue50k(this.profitLossBookedValue50k);
		bean.setDateTimeStamp50k(this.dateTimeStamp50k);
		bean.setMaxProfitMoveValue(this.maxProfitMoveValue);
		bean.setMaxLossMoveValue(this.maxLossMoveValue);
		bean.setDateStamp(this.dateStamp);

		bean.setDayProfitLossValue(this.dayProfitLossValue);
		bean.setDateTimeStampDayProfitLoss(this.dateTimeStampDayProfitLoss);
		bean.setScannerUpdatable(this.scannerUpdatable);
		bean.setTrailingStopLossValue(this.trailingStopLossValue);
		return bean;
	}
}
