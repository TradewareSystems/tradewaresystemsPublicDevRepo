package nr4.sathvikashviktechtrader.stockmarket.tool;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.json.JSONException;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.OrderParams;

import nr4.sathvikashviktechtrader.stockmarket.bean.StockDataBean;
import nr4.sathvikashviktechtrader.stockmarket.log.NkpAlgoLogger;
import nr4.sathvikashviktechtrader.stockmarket.util.Constants;
import nr4.sathvikashviktechtrader.stockmarket.util.StockUtil;

public class KiteConnectTool {

	public double getRoundupValue(double value) {
		return new BigDecimal(value).setScale(1, RoundingMode.DOWN).doubleValue();
	}

	public double getRoundupValue2(double value) {
		return new BigDecimal(value).setScale(2, RoundingMode.DOWN).doubleValue();
	}
	
	private static KiteConnectTool singletonTool;

	protected KiteConnectTool() {
	}

	public static KiteConnectTool getInstance() {
		if (null == singletonTool) {
			singletonTool = new KiteConnectTool();
		}
		return singletonTool;
	}
	public String getCurrentTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.TIME_PATTERN);
		return LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE)).format(dtf);
	}
	public StockDataBean placeBracketOrder(StockDataBean bean) {
		OrderParams orderParams = new OrderParams();
		orderParams.orderType = Constants.ORDER_TYPE_LIMIT;// "BO";//"MARKET";
		orderParams.product = Constants.PRODUCT_MIS;
		orderParams.validity = Constants.VALIDITY_DAY;
		orderParams.transactionType = bean.getTradableState();
		orderParams.quantity = bean.getLotSize();

		if (bean.getKiteFutureKey().startsWith(Constants.FUTURE_KEY_PREFIX_NFO)) {
			orderParams.tradingsymbol = bean.getKiteFutureKey().replace(Constants.FUTURE_KEY_PREFIX_NFO,
					Constants.EMPTY_STRING);
			orderParams.exchange = Constants.EXCHANGE_NFO;
		} else if (bean.getKiteFutureKey().startsWith(Constants.FUTURE_KEY_PREFIX_NSE)) {
			orderParams.tradingsymbol = bean.getKiteFutureKey().replace(Constants.FUTURE_KEY_PREFIX_NSE,
					Constants.EMPTY_STRING);
			orderParams.exchange = Constants.EXCHANGE_NSE;
		} else {
			return null;
		}

		double priceVal = Constants.BUY.equals(bean.getTradableState())
				? (bean.getTradedVal() + (bean.getTradedVal() * Constants.LIMIT_ORDER_PLACABLE_PRICE))
				: (bean.getTradedVal() - (bean.getTradedVal() * Constants.LIMIT_ORDER_PLACABLE_PRICE));
		priceVal = getRoundupValue(priceVal);
		double squareoff = Constants.BUY.equals(bean.getTradableState())
				? (bean.getTradedVal() + (Constants.TARGET_AMOUNT / bean.getLotSize()))
				: (bean.getTradedVal() - (Constants.TARGET_AMOUNT / bean.getLotSize()));
		squareoff = Constants.BUY.equals(bean.getTradableState())
				? getRoundupValue(squareoff - priceVal) : getRoundupValue(priceVal - squareoff);
				squareoff = Math.abs(squareoff);//Not required just for worst case scenario	
				
		double stopLoss = Constants.BUY.equals(bean.getTradableState())
				? (bean.getTradedVal() - (Constants.TARGET_CO_STOP_LOSS / bean.getLotSize()))
				: (bean.getTradedVal() + (Constants.TARGET_CO_STOP_LOSS / bean.getLotSize()));
		stopLoss = Constants.BUY.equals(bean.getTradableState())
				? stopLoss > bean.getStopLoss() ? stopLoss : bean.getStopLoss()
				: stopLoss > bean.getStopLoss() ? bean.getStopLoss() : stopLoss;
		stopLoss = Math.abs(getRoundupValue(priceVal - bean.getStopLoss()));
		
		orderParams.price = priceVal;
		orderParams.squareoff = squareoff;
		orderParams.stoploss = stopLoss;
		orderParams.trailingStoploss = getRoundupValue(stopLoss / Constants.TWO);
		Order order = null;
		try {
			order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).placeOrder(orderParams,
					Constants.ORDER_VARIETY_BO);
			bean.setKiteOrderId(order.orderId);
		} catch (JSONException | IOException | KiteException e) {
			NkpAlgoLogger.printWithNewLine(Constants.ORDER_PLACEMENT_FAILED + bean.getSymbolName() + Constants.UNDER_SCORE
					+ getCurrentTime() + Constants.SPACE + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			NkpAlgoLogger.printWithNewLine("EXCEPTION>>> " + Constants.ORDER_PLACEMENT_FAILED
					+ bean.getSymbolName() + Constants.UNDER_SCORE + getCurrentTime()
					+ Constants.SPACE + e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}

	public StockDataBean placeCoverOrder(StockDataBean bean) {
		OrderParams orderParams = new OrderParams();
		orderParams.orderType = Constants.ORDER_TYPE_MARKET;//Constants.ORDER_TYPE_LIMIT; //Constants.ORDER_TYPE_MARKET;// "BO";//"MARKET";
		orderParams.product = Constants.PRODUCT_MIS;
		orderParams.validity = Constants.VALIDITY_DAY;
		orderParams.transactionType = bean.getTradableState();
		orderParams.quantity = bean.getLotSize();
		if (bean.getKiteFutureKey().startsWith(Constants.FUTURE_KEY_PREFIX_NFO)) {
			orderParams.tradingsymbol = bean.getKiteFutureKey().replace(Constants.FUTURE_KEY_PREFIX_NFO,
					Constants.EMPTY_STRING);
			orderParams.exchange = Constants.EXCHANGE_NFO;
		} else if (bean.getKiteFutureKey().startsWith(Constants.FUTURE_KEY_PREFIX_NSE)) {
			orderParams.tradingsymbol = bean.getKiteFutureKey().replace(Constants.FUTURE_KEY_PREFIX_NSE,
					Constants.EMPTY_STRING);
			orderParams.exchange = Constants.EXCHANGE_NSE;
		} else {
			return null;
		}
		
		double triggerPrice = Constants.BUY.equals(bean.getTradableState())
				? (bean.getTradedVal() - (Constants.TARGET_CO_STOP_LOSS / bean.getLotSize()))
				: (bean.getTradedVal() + (Constants.TARGET_CO_STOP_LOSS / bean.getLotSize()));
		triggerPrice = Constants.BUY.equals(bean.getTradableState())
				? triggerPrice > bean.getStopLoss() ? triggerPrice : bean.getStopLoss()
				: triggerPrice > bean.getStopLoss() ? bean.getStopLoss() : triggerPrice;
		triggerPrice = getRoundupValue(triggerPrice);
		orderParams.triggerPrice = triggerPrice;
		orderParams.price = getRoundupValue(bean.getTradedVal());
		
		Order order = null;
		try {
			order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).placeOrder(orderParams,
					Constants.ORDER_VARIETY_CO); // "regular");//"bo");
			bean.setKiteOrderId(order.orderId);
		} catch (JSONException | IOException | KiteException e) {
			NkpAlgoLogger.printWithNewLine(Constants.ORDER_PLACEMENT_FAILED + bean.getSymbolName() + Constants.UNDER_SCORE
					+ getCurrentTime() + Constants.SPACE + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			NkpAlgoLogger.printWithNewLine("EXCEPTION>>> " + Constants.ORDER_PLACEMENT_FAILED
					+ bean.getSymbolName() + Constants.UNDER_SCORE + getCurrentTime()
					+ Constants.SPACE + e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}

	public StockDataBean cancelCoverOrder(StockDataBean bean) {
		Order order = null;
		try {
			if (null != bean && null != bean.getKiteOrderId()) {
				List<Order> trades = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).getOrders();
				String myTrade = bean.getKiteFutureKey().split(":")[1];
				for (Order trade : trades) {
					if (trade.tradingSymbol.equals(myTrade)
							&& Constants.ORDER_STATUS_CO_2ND_LEG_TRIGGER_PENDING.equals(trade.status)) {
						order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).cancelOrder(trade.orderId,
								bean.getKiteOrderId(), Constants.ORDER_VARIETY_CO);
					/*	NkpAlgoLogger.printWithNewLine(Constants.ORDER_CANCELLED_SUCCESSFUL + bean.getSymbolName()
								+ Constants.UNDER_SCORE + getCurrentTime());*/
						break;
					} 
				}
			}
		} catch (JSONException | IOException | KiteException e) {
			NkpAlgoLogger.printWithNewLine("excEpTiOn" + Constants.ORDER_CANCELLED_ERROR + bean.getSymbolName()
					+ Constants.UNDER_SCORE + getCurrentTime());
			e.printStackTrace();
		}
		return bean;
	}

	public StockDataBean cancelBracketOrder(StockDataBean bean) {
		Order order = null;
		try {
			if (null != bean.getKiteOrderId()) {
				order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
						.cancelOrder(bean.getKiteOrderId(), Constants.ORDER_VARIETY_BO);
				NkpAlgoLogger.printWithNewLine(Constants.ORDER_CANCELLED_SUCCESSFUL + bean.getSymbolName() + Constants.UNDER_SCORE
						+ getCurrentTime());
			}
		} catch (JSONException | IOException | KiteException e) {
			NkpAlgoLogger.printWithNewLine(Constants.ORDER_CANCELLED_ERROR + bean.getSymbolName() + Constants.UNDER_SCORE
					+ getCurrentTime());
			e.printStackTrace();
		}
		return bean;
	}
}
