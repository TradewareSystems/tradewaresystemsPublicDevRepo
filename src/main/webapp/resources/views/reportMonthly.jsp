<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>

<t:masterTemplate2>
	<jsp:attribute name="body_area">
            	<div
			style="padding-top: 5px; padding-bottom: 5px; padding-left: 10px; padding-right: 20px;">
				<div
				style="border-radius: 25px; border: 1px solid #d6d6c2; padding: 5px; font-family: Verdana; font-size: 12px;">
        			<a style="float: left;" href='<c:url value = "/"/>'><img
					align="top"
					src="<c:url value="/resources/static/images/home.png"/>" />&nbsp; Home</a>
        			
        			${totalTrades} <b>${monthlyReportFor} </b>
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
								<th>S.NO</th>
								<th>Trade Date</th>
								<th>Total Trades</th>
								<th>Profit/Loss</th>
								<th>Profit/Loss(After Taxes)</th>
								<th>Manual Profit/Loss(After Taxes)</th>
							</tr>
						<!-- /thead -->
					</table>
					<table style="min-height: 90%; width: 100%;" class="" id="mytable">
        				<tbody>
						<c:forEach items="${reportList}" var="reportDataBean" varStatus="loop">
							<tr>
								<td align="center">${loop.index + 1}</td>
								<td align="center"><fmt:formatDate pattern = "dd-MM-yyyy" value = "${reportDataBean.tradedDateStamp}" /></td>
								<td align="center">${reportDataBean.tradeCountPerDay}</td>
								<td align="center">${reportDataBean.profitLossAmtVal}</td>
								<td align="center">${reportDataBean.profitLossAmtValFinal}</td>
								<td align="center">${reportDataBean.profitLossAmtValManalFinal}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>	
			</div>
	</jsp:attribute>
</t:masterTemplate2>