<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<t:simpleTemplate>
	<jsp:attribute name="body_area">
		<div
			style="height: 74%; width: 98%; position: absolute; border: 1px solid rgba(18, 27, 27, 0.51); border-radius: 5px; margin: 5px 5px 5px 5px;">
            	<div
				style="padding-top: 5px; padding-bottom: 5px; padding-left: 10px;  padding-right: 20px;">
					<div style="border-radius: 10px; border: 1px solid #d6d6c2; padding: 5px; font-family: Verdana; font-size: 12px;" >
	        			<a style="float: left;" href='<c:url value = "/"/>'><img
						align="top" src="<c:url value="/images/home.png"/>" />&nbsp; Home</a>
	        			${totalTrades} <span
						style="font: normal; font-family: sans-serif; color: light-blue;">Live Running Report </span>
	        			&nbsp;&nbsp;<a href='<c:url value = "/highLowReportRunning"/>'><img
						align="top" src="<c:url value="/images/refresh.png"/>" /></a>
	        			${profitLoss}
	        		</div>
        		</div>
        		
        		<div
				style="width: 99%; max-height: 92%; overflow-x: scroll; padding-left: 10px;">
        			<table style="min-height: 90%; width: 100%; position:sticky;top:0;" class=" " id="mytableHeader">
						<!--  thead class="header"-->
        				<tr style="background:white;">
							<th>Symbols</th>
							<th>Lot Size</th>
							<th>Tradable State</th>
							<th>Buy/Sell_SL</th>
							<th>Traded Status</th>
							<th>Profit/Loss</th>
							<th>B/S Diff</th>
							<th>Loss</th>
							<th>Traded At</th>
							<th>Exited at</th>
							<th>RSI</th>
							<th>Additional</th>
							<!--th>Vol_NIFTY</th>
							<th>Add_NIFTY</th-->
						</tr>
						<!-- /thead -->
					</table>
					<table style="min-height: 90%; width: 100%;" class=" " id="mytable">
						<c:forEach items="${highLowList}" var="highLowBean"
						varStatus="status">
							<tr>
								<td align="center">${highLowBean.stockName}</td>
								<td align="center">${highLowBean.lotSize}</td>
								<td align="center">${highLowBean.tradableState}</td>
								<td align="center">${highLowBean.buyOrSellAt} - ${highLowBean.stopLoss}</td>
								<td align="center">${highLowBean.tradedState}</td>
								<td align="center">${highLowBean.profitLossAmt}</td>
								<td align="center">${highLowBean.buyerSellerDiff}</td>
								<td align="center"><span style='color: green'>${highLowBean.positiveDirectionProfit}</span> / <span
								style='color: red'>${highLowBean.negativeDirectionLoss}</span></td>
								<td align="center">${highLowBean.signalTriggeredInAt}</td>
								<td align="center">${highLowBean.signalTriggeredOutAt}</td>
								<td align="center">${highLowBean.rsi1}, ${highLowBean.rsi2}, ${highLowBean.rsi3}</td>
								<td align="center">${highLowBean.additinalInfo}</td>
								<!--td align="center">${highLowBean.volumeInfoOnTradeEntryNifty50}</td>
								<td align="center">${highLowBean.additionalInfoNifty50}</td-->
							</tr>
						</c:forEach>
					</table>	
			</div>
        </div>
	</jsp:attribute>
</t:simpleTemplate>