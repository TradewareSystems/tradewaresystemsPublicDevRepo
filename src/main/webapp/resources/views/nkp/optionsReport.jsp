<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<t:simpleTemplate>
	<jsp:attribute name="body_area">
		<div
			style="height: 74%; width: 98%; position: absolute; border: 1px solid rgba(18, 27, 27, 0.51); border-radius: 5px; margin: 5px 5px 5px 5px;">
            	<div
				style="padding-top: 5px; padding-bottom: 5px; padding-left: 10px; padding-right: 20px;">
				<div
					style="border-radius: 25px; border: 1px solid #d6d6c2; padding: 5px; font-family: Verdana; font-size: 12px;">
        			<a style="float: left;" href='<c:url value = "/"/>'><img
						align="top"
						src="<c:url value="/resources/static/images/home.png"/>" />&nbsp; Home</a>
        			
					<span
						style="font: normal; font-family: sans-serif; color: light-blue;">Option Calls Report</span>
        			&nbsp;&nbsp;<a href='<c:url value = "/getOptionCallDayReport"/>'><img
						align="top"
						src="<c:url value="/resources/static/images/refresh.png"/>" /></a>
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
							<th>Size</th>
							<th>Trade State</th>
							<th>P-uptrend</th>
							<th>P-downtrend</th>
							<th>Traded Val</th>
							<th>Traded at</th>
							<th>Exit at</th>
							<th>Profit/Loss</th>
							<th>Profit/Loss Amount</th>
							<th>Volumes-Quantities</th>
						</tr>
						<!-- /thead -->
					</table>
					<table style="min-height: 90%; width: 100%;" class="" id="mytable">
        				<tbody>
						<c:forEach items="${optionCalls}" var="optionBean" varStatus="status">
							<tr>
								<td align="center">${optionBean.stockName}</td>
								<td align="center">${optionBean.lotSize}</td>
								<td align="center">${optionBean.tradeState}</td>
								<td align="center">${optionBean.positionalUptrendValue}</td>
								<td align="center">${optionBean.positionalDowntrendValue}</td>
								<td align="center">${optionBean.tradedVal} <br> ${optionBean.tradedValOption}</td>
								<td align="center">${optionBean.signalTriggeredInAt}</td>
								<td align="center">${optionBean.signalTriggeredOutAt}</td>
								<td align="center">${optionBean.profitLossStatus}</td>
								<td align="center">${optionBean.profitLossAmt}</td>
								<td align="center"> </td>
							</tr>
						</c:forEach>
						</tbody>
					</table>	
			</div>
        </div>
	</jsp:attribute>
</t:simpleTemplate>