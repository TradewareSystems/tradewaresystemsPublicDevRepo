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

import com.tradeware.clouddatabase.bean.OptionStrategyBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "T_OPTION_TRADE_HEDGE", uniqueConstraints = {
		@UniqueConstraint(name = "OPTION_TRADE_HEDGE_CONSTRAINT", columnNames = {
				// "OPTION_TRADE_HEDGE_ID"/* ,"col2" */ }) })
				"OPTION_TRADE_ID"/* ,"col2" */ }) })
public class OptionStrategyHedgeEntity extends AbstractEntity {

	private static final long serialVersionUID = -8194700246114827893L;

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)
	 * 
	 * @Column(name = "OPTION_TRADE_HEDGE_ID", unique = true, nullable = false,
	 * columnDefinition="serial") private Integer itemId;
	 */

	@Column(name = "STRIKE_PRICE_MAIN", precision = 12, scale = 2)
	private Integer strikePriceMain;// strikePriceAt;

	@Column(name = "STRIKE_MAIN_TRADE_TYPE", length = 12)
	private String strikePriceMainTradeType;// Buy or Sell

	@Column(name = "STRIKE_PRICE_LOS_PROTECT", precision = 12, scale = 2)
	private Integer strikePriceLossProtect;

	@Column(name = "STRIKE_LOS_PROTECT_TRADE_TYPE", length = 12)
	private String strikePriceLossProtectTradeType;// Buy or Sell

	@Column(name = "STRIKE_MAIN_OPT_TRADE_TYPE", length = 12)
	private String strikePriceMainOptionType;// call or put

	@Column(name = "STRIKE_LOS_PROTECT_OPT_TRADE_TYPE", length = 12)
	private String strikePriceLossProtectOptionType;// call or put

	@Column(name = "STRIKE_MAIN_BUY_AT", precision = 12, scale = 2)
	private Double buyAtMain;// bidPriceOfAtm;//buyerAt

	@Column(name = "STRIKE_MAIN_SELL_AT", precision = 12, scale = 2)
	private Double sellAtMain;// askPriceOfAtm;//sellerAt

	@Column(name = "STRIKE_MAIN_BUY_QUANTITY", precision = 24, scale = 2)
	private Integer buyQuantityMain;// bidQuantityOfAtm;

	@Column(name = "STRIKE_MAIN_SELL_QUANTITY", precision = 24, scale = 2)
	private Integer sellQuantityMain;// askQuantityOfAtm;

	@Column(name = "STRIKE_LOS_PROTECT_BUY_AT", precision = 12, scale = 2)
	private Double buyAtLossProtect;// bidPriceOfInOrOut;//buyerAt

	@Column(name = "STRIKE_LOS_PROTECT_SELL_AT", precision = 12, scale = 2)
	private Double sellAtLossProtect;// askPriceOfInOrOut;//sellerAt

	@Column(name = "STRIKE_LOS_PROTECT_BUY_QUANTITY", precision = 24, scale = 2)
	private Integer buyQuantityLossProtect;// bidQuantityOfInOrOut;

	@Column(name = "STRIKE_LOS_PROTECT_SELL_QUANTITY", precision = 24, scale = 2)
	private Integer sellQuantityLossProtect;// askQuantityOfInOrOut;

	@Column(name = "STRIKE_MAIN_GAIN_POINTS", precision = 12, scale = 2)
	private Double gainPointsMain;

	@Column(name = "STRIKE_LOS_PROTECT_GAIN_POINTS", precision = 12, scale = 2)
	private Double gainPointsLossProtect;

	@Column(name = "STRIKE_MAIN_EXPIRY_DATE", length = 12)
	private String expiryDateMain;

	@Column(name = "STRIKE_LOS_PROTECT_EXPIRY_DATE", length = 12)
	private String expiryDateLossProtect;
	
	@Column(name = "MAIN_POS_MOVE_VAL", precision=7, scale=2)
	private Double positiveMoveValMain;
	
	@Column(name = "MAIN_NEG_MOVE_VAL", precision=7, scale=2)
	private Double negativeMoveValMain;
	
	@Column(name = "MAIN_POS_MOVE_LTP", precision=7, scale=2)
	private Double positiveMoveLtpMain;
	
	@Column(name = "MAIN_NEG_MOVE_LTP", precision=7, scale=2)
	private Double negativeMoveLtpMain;
	
	@Column(name = "LOS_PROTECT_POS_MOVE_VAL", precision=7, scale=2)
	private Double positiveMoveValLossProtect;
	
	@Column(name = "LOS_PROTECT_NEG_MOVE_VAL", precision=7, scale=2)
	private Double negativeMoveValLossProtect;
	
	@Column(name = "LOS_PROTECT_POS_MOVE_LTP", precision=7, scale=2)
	private Double positiveMoveLtpLossProtect;
	
	@Column(name = "LOS_PROTECT_NEG_MOVE_LTP", precision=7, scale=2)
	private Double negativeMoveLtpLossProtect;

	/////////
	public void populateEntity(OptionStrategyBean bean) {
		this.strikePriceMain = bean.getStrikePriceMain();
		this.strikePriceMainTradeType = bean.getStrikePriceMainTradeType();
		this.strikePriceLossProtect = bean.getStrikePriceLossProtect();
		this.strikePriceLossProtectTradeType = bean.getStrikePriceLossProtectTradeType();
		this.strikePriceMainOptionType = bean.getStrikePriceMainOptionType();
		this.strikePriceLossProtectOptionType = bean.getStrikePriceLossProtectOptionType();
		this.buyAtMain = bean.getBuyAtMain();
		this.sellAtMain = bean.getSellAtMain();
		this.buyQuantityMain = bean.getBuyQuantityMain();
		this.sellQuantityMain = bean.getSellQuantityMain();
		this.buyAtLossProtect = bean.getBuyAtLossProtect();
		this.sellAtLossProtect = bean.getSellAtLossProtect();
		this.buyQuantityLossProtect = bean.getBuyQuantityLossProtect();
		this.sellQuantityLossProtect = bean.getSellQuantityLossProtect();
		this.gainPointsMain = bean.getGainPointsMain();
		this.gainPointsLossProtect = bean.getGainPointsLossProtect();
		this.expiryDateMain = bean.getExpiryDateMain();
		this.expiryDateLossProtect = bean.getExpiryDateLossProtect();
		this.positiveMoveValMain = bean.getPositiveMoveValMain();
		this.negativeMoveValMain = bean.getNegativeMoveValMain();
		this.positiveMoveLtpMain = bean.getPositiveMoveLtpMain();
		this.negativeMoveLtpMain = bean.getNegativeMoveLtpMain();
		this.positiveMoveValLossProtect = bean.getPositiveMoveValLossProtect();
		this.negativeMoveValLossProtect = bean.getNegativeMoveValLossProtect();
		this.positiveMoveLtpLossProtect = bean.getPositiveMoveLtpLossProtect();
		this.negativeMoveLtpLossProtect = bean.getNegativeMoveLtpLossProtect();
	}

	public void populateBean(OptionStrategyBean bean) {
		bean.setStrikePriceMain(this.strikePriceMain);
		bean.setStrikePriceMainTradeType(this.strikePriceMainTradeType);
		bean.setStrikePriceLossProtect(this.strikePriceLossProtect);
		bean.setStrikePriceLossProtectTradeType(this.strikePriceLossProtectTradeType);
		bean.setStrikePriceMainOptionType(this.strikePriceMainOptionType);
		bean.setStrikePriceLossProtectOptionType(this.strikePriceLossProtectOptionType);
		bean.setBuyAtMain(this.buyAtMain);
		bean.setSellAtMain(this.sellAtMain);
		bean.setBuyQuantityMain(this.buyQuantityMain);
		bean.setSellQuantityMain(this.sellQuantityMain);
		bean.setBuyAtLossProtect(this.buyAtLossProtect);
		bean.setSellAtLossProtect(this.sellAtLossProtect);
		bean.setBuyQuantityLossProtect(this.buyQuantityLossProtect);
		bean.setSellQuantityLossProtect(this.sellQuantityLossProtect);
		bean.setGainPointsMain(this.gainPointsMain);
		bean.setGainPointsLossProtect(this.gainPointsLossProtect);
		bean.setExpiryDateMain(this.expiryDateMain);
		bean.setExpiryDateLossProtect(this.expiryDateLossProtect);
		bean.setPositiveMoveValMain(this.positiveMoveValMain);
		bean.setNegativeMoveValMain(this.negativeMoveValMain);
		bean.setPositiveMoveLtpMain(this.positiveMoveLtpMain);
		bean.setNegativeMoveLtpMain(this.negativeMoveLtpMain);
		bean.setPositiveMoveValLossProtect(this.positiveMoveValLossProtect);
		bean.setNegativeMoveValLossProtect(this.negativeMoveValLossProtect);
		bean.setPositiveMoveLtpLossProtect(this.positiveMoveLtpLossProtect);
		bean.setNegativeMoveLtpLossProtect(this.negativeMoveLtpLossProtect);
	}

	@Id
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "OPTION_TRADE_ID")
	@MapsId
	private OptionStrategyEntity optionStrategyEntity;

	public OptionStrategyEntity getOptionStrategyEntity() {
		return optionStrategyEntity;
	}

	public void setOptionStrategyEntity(OptionStrategyEntity optionStrategyEntity) {
		this.optionStrategyEntity = optionStrategyEntity;
	}
}
