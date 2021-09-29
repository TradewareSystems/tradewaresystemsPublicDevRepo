<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:masterTemplate2>
	<jsp:attribute name="body_area">
            	<div
			style="padding-top: 5px; padding-bottom: 5px; padding-left: 10px; padding-right: 20px;">
				<div
				style="border-radius: 25px; border: 1px solid #d6d6c2; padding: 5px; font-family: Verdana; font-size: 12px;">
        			<a style="float: left;" href='<c:url value = "/"/>'><img
					align="top"
					src="<c:url value="/resources/static/images/home.png"/>" />&nbsp; Home</a>
        			
        			${totalTrades} <span
					style="font: normal; font-family: sans-serif; color: light-blue;">Running Report </span>
        			&nbsp;&nbsp;<a href='<c:url value = "/report"/>'><img
					align="top"
					src="<c:url value="/resources/static/images/refresh.png"/>" /></a>
        			${profitLoss}
        			
        			<a style="float: right;"
					href='<c:url value = "/reportFull"/>'>&nbsp; Full Day Report</a>
        		</div>
        		</div>
        		<div class="container"
			style="width: 99%; max-height: 92%; overflow-x: scroll; padding-left: 10px;">
        			<table
				style="min-height: 90%; width: 100%; position: sticky; top: 0;"
				class=" " id="mytableHeader">
        				<!--  thead class="header"-->
        					<tr style="background: lightgrey;">
								<th>Symbols</th>
								<th>Lot Size</th>
								<th>Key</th>
								<th>Tradable State</th>
								<th>Buy/Sell_SL</th>
								<th>Traded Status</th>
								<th>Profit/Loss</th>
								<th>B/S Diff</th>
								<th>Strength</th>
								<th>Loss</th>
								<th>StopLoss</th>
								<th>Traded At</th>
								<th>Exited at</th>
							</tr>
						<!-- /thead -->
					</table>
					<table style="min-height: 90%; width: 100%;" class="" id="mytable">
        				<tbody>
						<c:forEach items="${reportList}" var="reportDataBean">
							<tr>
								<td align="center"><a
								href='<c:url value = "/stockDetails?stockId=${reportDataBean.kiteFutureKey}"/>'>${reportDataBean.symbolName}</a></td>
								<td align="center">${reportDataBean.lotSize}</td>
								<td align="center">${reportDataBean.kiteFutureKey}</td>
								<td align="center">${reportDataBean.tradableStateId}</td>
								<td align="center">${reportDataBean.buyAtVal} - ${reportDataBean.sellAtVal}</td>
								<td align="center">${reportDataBean.tradedStateId}</td>
								<td align="center">${reportDataBean.profitLossAmtVal}</td>
								<td align="center">${reportDataBean.buyerSellerDiffVal}</td>
								<td align="center">${reportDataBean.strengthableTradeStateDescription}</td>
								<td align="center"><span style='color: green'>${reportDataBean.positiveDirectionProfit}</span> / <span
								style='color: red'>${reportDataBean.negativeDirectionLoss}</span></td>
									
								<td align="center">	${reportDataBean.stopLoss};${reportDataBean.targetPrice};${reportDataBean.baseStopLoss}</td>
									
								<td align="center">${reportDataBean.signalTriggeredInAt}</td>
								<td align="center">${reportDataBean.signalTriggeredOutAt}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>	
			</div>
	</jsp:attribute>
</t:masterTemplate2>