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
        			<span style="font: normal;font-family: sans-serif; color: light-blue;">Narrow 7 Calls Report </span>
        			&nbsp;&nbsp;<a href = '<c:url value = "/getNarrow7CallDayReport2"/>'><img align="top" src="<c:url value="/images/refresh.png"/>" /></a>
        		</div>
        		<div style="width: 99%;max-height:92%; overflow-x :scroll;padding-left:10px; ">
        			<table style="min-height:90%;width:100%;">
						<tr>
							<th>Symbols</th>
							<th>Lot Size</th>
							<th>Open</th>
							<th>High</th>
							<th>Low</th>
							<th>Buy Value</th>
							<th>Sell Value</th>
							<th>B-T %</th>
							<th>S-T %</th>
							<th>B-T </th>
							<th>S-T </th>
							<th>Buy Status</th>
							<th>Sell Status</th>
							<th>Trade At</th>
							<th>Exit at</th>
							<th>Profit/Loss</th>
						</tr>
        				<c:forEach items="${narrow7Beans}" var="narrow7Bean" varStatus="status">
							<tr>
								<td align="center">${narrow7Bean.stockName}</td>
								<td align="center">${narrow7Bean.lotSize}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.open)}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.high)}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.low)}</td>
								<td align="center">${narrow7Bean.buyValue}</td>
								<td align="center">${narrow7Bean.sellValue}</td>
								<td align="center">${narrow7Bean.buyValueTarget33}</td>
								<td align="center">${narrow7Bean.sellValueTarget33}</td>
								<td align="center">${narrow7Bean.buyValueTarget}</td>
								<td align="center">${narrow7Bean.sellValueTarget}</td>
								<td align="center">${narrow7Bean.buyStatus}</td>
								<td align="center">${narrow7Bean.sellStatus}</td>
								<td align="center">${narrow7Bean.signalTriggeredInAt}</td>
								<td align="center">${narrow7Bean.signalTriggeredOutAt}</td>
								<td align="center">${narrow7Bean.profitLossAmt}</td>
							</tr>
						</c:forEach>
					</table>	
			</div>
        </div>
	</jsp:attribute>
</t:simpleTemplate>