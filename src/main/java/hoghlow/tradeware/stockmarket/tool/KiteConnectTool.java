package hoghlow.tradeware.stockmarket.tool;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.json.JSONException;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.OrderParams;

import hoghlow.tradeware.stockmarket.bean.StockDataBean;
import hoghlow.tradeware.stockmarket.log.NkpAlgoLogger;
import hoghlow.tradeware.stockmarket.util.Constants;
import hoghlow.tradeware.stockmarket.util.StockUtil;

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
		NkpAlgoLogger.printWithNewLine("orderParams.orderType- " + orderParams.orderType + ", orderParams.product- "
				+ orderParams.product + ", orderParams.validity- " + orderParams.validity + ", orderParams.transactionType- "
				+ orderParams.transactionType + ", orderParams.quantity- " + orderParams.quantity
				+ ", orderParams.tradingsymbol- " + orderParams.tradingsymbol + ", orderParams.exchange- "
				+ orderParams.exchange + ", orderParams.price- " + orderParams.price + ", orderParams.squareoff- "
				+ orderParams.squareoff + ", orderParams.stoploss- " + orderParams.stoploss
				+ ", orderParams.trailingStoploss- " + orderParams.trailingStoploss);
		Order order = null;
		try {
			order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).placeOrder(orderParams,
					Constants.ORDER_VARIETY_BO);
			bean.setKiteOrder(order);
			bean.setKiteOrderId(order.orderId);
		} catch (JSONException | IOException | KiteException e) {
			NkpAlgoLogger.printWithNewLine(Constants.ORDER_PLACEMENT_FAILED + bean.getStockName() + Constants.UNDER_SCRORE
					+ HighLowStrategyTool.getInstance().getCurrentTime() + Constants.SPACE + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			NkpAlgoLogger.printWithNewLine("EXCEPTION>>>>>>>>>>>>>>>>>>>>>>" + Constants.ORDER_PLACEMENT_FAILED
					+ bean.getStockName() + Constants.UNDER_SCRORE + HighLowStrategyTool.getInstance().getCurrentTime()
					+ Constants.SPACE + e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}

	public StockDataBean placeCoverOrder(StockDataBean bean) {
		OrderParams orderParams = new OrderParams();
		orderParams.orderType = Constants.ORDER_TYPE_MARKET;// "BO";//"MARKET";
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
		
		NkpAlgoLogger.printWithNewLine(
				"orderParams.orderType- " + orderParams.orderType + ", orderParams.product- " + orderParams.product
						+ ", orderParams.validity- " + orderParams.validity + ", orderParams.transactionType- "
						+ orderParams.transactionType + ", orderParams.quantity- " + orderParams.quantity
						+ ", orderParams.tradingsymbol- " + orderParams.tradingsymbol + ", orderParams.exchange- "
						+ orderParams.exchange + ", orderParams.triggerPrice- " + orderParams.triggerPrice);
		
		
		Order order = null;
		try {
			order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER).placeOrder(orderParams,
					Constants.ORDER_VARIETY_CO); // "regular");//"bo");
			bean.setKiteOrder(order);
			bean.setKiteOrderId(order.orderId);
			
			NkpAlgoLogger.printWithNewLine("After Order Placement >>> "+order.tradingSymbol + " -----  order.orderId----   " + order.orderId
					+ " ---- order.parentOrderId ----" + order.parentOrderId + " ----- order.status ----- "
					+ order.status + " ----- order.statusMessage ----- " + order.statusMessage);
		
		} catch (JSONException | IOException | KiteException e) {
			NkpAlgoLogger.printWithNewLine(Constants.ORDER_PLACEMENT_FAILED + bean.getStockName() + Constants.UNDER_SCRORE
					+ HighLowStrategyTool.getInstance().getCurrentTime() + Constants.SPACE + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			NkpAlgoLogger.printWithNewLine("EXCEPTION>>>>>>>>>>>>>>>>>>>>>>" + Constants.ORDER_PLACEMENT_FAILED
					+ bean.getStockName() + Constants.UNDER_SCRORE + HighLowStrategyTool.getInstance().getCurrentTime()
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
				NkpAlgoLogger.printWithNewLine("myTrade -"+myTrade+",  trades.size() -- "+trades.size());
				for (Order trade : trades) {

					NkpAlgoLogger.printWithNewLine("Before cancelling >>> " + trade.tradingSymbol + " -----  order.orderId----   "
							+ trade.orderId + " ---- order.parentOrderId ----" + trade.parentOrderId
							+ " ----- order.status ----- " + trade.status + " ----- order.statusMessage ----- "
							+ trade.statusMessage );

					if (trade.tradingSymbol.equals(myTrade)
							&& Constants.ORDER_STATUS_CO_2ND_LEG_TRIGGER_PENDING.equals(trade.status)) {

						NkpAlgoLogger.printWithNewLine("HeRE acTUAL TIGGER CLOSE bean.getKiteOrderId() -" + bean.getKiteOrderId()
								+ ", bean.getKiteOrder().orderVariety -" + bean.getKiteOrder().orderVariety
								+ ", trade.orderVariety -" + trade.orderVariety+ " -----  order.orderId----   "
								+ trade.orderId);

						order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
								.cancelOrder(trade.orderId, bean.getKiteOrderId(), Constants.ORDER_VARIETY_CO);

						bean.setKiteOrder(order);
						NkpAlgoLogger.printWithNewLine(Constants.ORDER_CANCELLED_SUCCESSFUL + bean.getStockName()
								+ Constants.UNDER_SCRORE + HighLowStrategyTool.getInstance().getCurrentTime());
						NkpAlgoLogger.printWithNewLine(order.tradingSymbol + " -----  order.orderId----   " + order.orderId
								+ " ---- order.parentOrderId ----" + order.parentOrderId + " ----- order.status ----- "
								+ order.status + " ----- order.statusMessage ----- " + order.statusMessage);
						break;
					} else {
						NkpAlgoLogger.printWithNewLine("No real order placed on this - >>>>" + Constants.ORDER_CANCELLED_ERROR
								+ bean.getStockName() + Constants.UNDER_SCRORE
								+ HighLowStrategyTool.getInstance().getCurrentTime());
					}
				}
			}
		} catch (JSONException | IOException | KiteException e) {
			NkpAlgoLogger.printWithNewLine("excEpTiOn"+Constants.ORDER_CANCELLED_ERROR + bean.getStockName() + Constants.UNDER_SCRORE
					+ HighLowStrategyTool.getInstance().getCurrentTime());
			e.printStackTrace();
		}
		return bean;
	}

	public StockDataBean cancelBracketOrder(StockDataBean bean) {
		Order order = null;
		try {
			/*if (null != bean.getKiteOrder() && null != bean.getKiteOrder().orderId) {
				List<Order> trades = StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER).getOrders();
				for (Order trade : trades) {
					if (trade.orderId.equals(bean.getKiteOrder().orderId)
							&& trade.parentOrderId.equals(bean.getKiteOrder().parentOrderId)
							&& Constants.ORDER_STATUS_OPEN.equals(trade.status)) {
						order = StockUtil.kiteConnectObjectsForAdmins.get(StockUtil.DEFAULT_USER).cancelOrder(
								bean.getKiteOrder().orderId, bean.getKiteOrder().parentOrderId,
								bean.getKiteOrder().orderVariety);
						bean.setKiteOrder(order);
						System.out.println(Constants.ORDER_CANCELLED_SUCCESSFUL + bean.getStockName()
								+ Constants.UNDER_SCRORE + HighLowStrategyTool.getInstance().getCurrentTime());
					}
				}
			}*/
			
			if (null != bean.getKiteOrderId()) {
				order = StockUtil.kiteConnectAdmins.get(StockUtil.DEFAULT_USER)
						.cancelOrder(bean.getKiteOrderId(), bean.getKiteOrder().orderVariety);
				bean.setKiteOrder(order);
				NkpAlgoLogger.printWithNewLine(Constants.ORDER_CANCELLED_SUCCESSFUL + bean.getStockName() + Constants.UNDER_SCRORE
						+ HighLowStrategyTool.getInstance().getCurrentTime());
			}
		} catch (JSONException | IOException | KiteException e) {
			NkpAlgoLogger.printWithNewLine(Constants.ORDER_CANCELLED_ERROR + bean.getStockName() + Constants.UNDER_SCRORE
					+ HighLowStrategyTool.getInstance().getCurrentTime());
			e.printStackTrace();
		}
		return bean;
	}
}
