<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<t:simpleTemplate>
	<jsp:attribute name="body_area">
		<div
			style="height: 74%; width: 98%; position: absolute; 
				border: 1px solid rgba(18, 27, 27, 0.51); border-radius: 5px; margin: 5px 5px 5px 5px;">
            	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 10px;">
        			<a style="float:left;" href = '<c:url value = "/"/>'><img align="top" src="<c:url value="/images/home.png"/>" />&nbsp; Home</a>
        			<span style="font: normal;font-family: sans-serif; color: light-blue;">Future Calls on Positional Levels - Report </span>
        			&nbsp;&nbsp;<a href = '<c:url value = "/getPositionalFutureCallDayReport"/>'><img align="top" src="<c:url value="/images/refresh.png"/>" /></a>
        		</div>
        		<div style="width: 99%;max-height:93%; overflow-x :scroll;padding-left:10px; ">
        			<table style="min-height:90%;width:100%;">
						<tr>
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
        				<c:forEach items="${posFutureCalls}" var="posFutBean" varStatus="status">
							<tr>
								<td align="center">${posFutBean.stockName}</td>
								<td align="center">${posFutBean.lotSize}</td>
								<td align="center">${posFutBean.tradeState}</td>
								<td align="center">${posFutBean.positionalUptrendValue}</td>
								<td align="center">${posFutBean.positionalDowntrendValue}</td>
								<td align="center">${posFutBean.tradedVal}</td>
								<td align="center">${posFutBean.signalTriggeredInAt}</td>
								<td align="center">${posFutBean.signalTriggeredOutAt}</td>
								<td align="center">${posFutBean.profitLossStatus}</td>
								<td align="center">${posFutBean.profitLossAmt}</td>
								<td align="center">${posFutBean.tempString}</td>
							</tr>
						</c:forEach>
					</table>	
			</div>
        </div>
	</jsp:attribute>
</t:simpleTemplate>