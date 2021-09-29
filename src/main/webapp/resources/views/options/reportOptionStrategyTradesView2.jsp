<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<t:simpleTemplate>
	<jsp:attribute name="body_area">
        <div class="dataTableTopic">
			<div class="dataTableTopicInnerDiv">
        		<a class="space-nbsp2 float-l" href='<c:url value = "/"/>'>
        			<img align="top"
					src="<c:url value="/resources/static/images/home.png"/>" /> Home
        		</a>
        		<span class="redBold pad-r-25">${candleNumber}</span>
        		<span class="dataTableTopicContent space-nbsp4"><b>Option Strategy Trades Report </b></span>
        		<a href='<c:url value = "/getOptionChainReport2"/>'>
        			<img align="top"
					src="<c:url value="/resources/static/images/refresh.png"/>" />
        		</a>
        		<span class="float-r redBold">${lastUpdateTime}</span>
        		<a id="checkBoxClick" class="float-r" href='<c:url value = "/applyBestTrades"/>'></a>
        		<span class="float-r redBold pad-r-25">${failedOptionChainSymbolCount}</span> 
        	</div>
        </div>
 		<div class="container dataTableContentPanel">
 			<c:forEach items="${tradeList}" var="mainBean" varStatus="status">
	        	<table class="dataTaleHeaderStyeNonSticky optionStrategyTradeView2Report" id="mytableHeader">
	        		<thead>
		   				<tr class="dataTaleHeaderRowStye">
		   					<th colspan="${mainBean.tableColSpan}" align="center">${mainBean.tradeName} </th>
						</tr>
						<tr class="dataTaleHeaderRowStye">
		   					<th colspan="2" align="center">Overall Strategy Performance </th>
		   					<th colspan="${mainBean.subHeaderColSpan}" align="center">Each Option Performance</th>
						</tr>
					</thead>
				</table>
				<table class="dataTaleContentStye" id="mytable">
	        		<tbody>
	        			<tr>
		   					<td class="optionTableColHeader">Symbol</td>
		   					<td class="optionTableColVal" colspan="1">${mainBean.symbolId}</td> 
		   					<td class="optionTableColHeader">Strike</td>
		   					<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.strikePrice}</td> 
							</c:forEach>		
						
		   				</tr>
		   				<tr>	
		   					<td class="optionTableColHeader">Position</td>
		   					<td class="optionTableColVal" colspan="1">${mainBean.tradePosition}</td>
		   					<td class="optionTableColHeader">Position</td>
		   					<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.tradePosition}</td> 
							</c:forEach>
		   				</tr>
		   				<tr>	
							<td class="optionTableColHeader">Lot Size</td>
							<td class="optionTableColVal" colspan="1">${mainBean.lotSize}</td>
							<td class="optionTableColHeader">TradeType</td>
							<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.tradeType}</td> 
							</c:forEach>
						</tr>
		   				<tr>	
							<td class="optionTableColHeader">Ticker Size</td>
							<td class="optionTableColVal" colspan="1">${mainBean.optionTickerSize}</td>
							<td class="optionTableColHeader">OptionType</td>
							<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.optionType}</td> 
							</c:forEach>
						</tr>
		   				<tr>	
							<td class="optionTableColHeader">Strategy</td>
							<td class="optionTableColVal" colspan="1">${mainBean.tradeStrategy}</td>
							<td class="optionTableColHeader">Number Of Lots</td>
							<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.numberOfLots}</td> 
							</c:forEach>
							
						</tr>
		   				<tr>	
							<td class="optionTableColHeader">Sub Strategy</td>
							<td class="optionTableColVal" colspan="1">${mainBean.tradeSubStrategy}</td>
							<td class="optionTableColHeader">TradedAtVal</td>
							<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.tradedAtVal}</td> 
							</c:forEach>
						</tr>
		   				<tr>	
							<td class="optionTableColHeader">Expiry Date</td>
							<td class="optionTableColVal" colspan="1">${mainBean.expiryDate}</td>
							<td class="optionTableColHeader">ExitedAtVal</td>
							<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.exitedAtVal}</td> 
							</c:forEach>
						</tr>
		   				<tr>	
							<td class="optionTableColHeader">Profit/Loss</td>
							<td class="optionTableColVal" colspan="1">${mainBean.profitLossAmtVal}</td>
							<td class="optionTableColHeader">Profit/Loss</td>
							<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.profitLossAmtVal}</td> 
							</c:forEach>
						</tr>
		   				<tr>	
							<td class="optionTableColHeader">Max Profit</td>
							<td class="optionTableColVal" colspan="1">${mainBean.positiveMoveVal}</td>
							<td class="optionTableColHeader">Max Profit</td>
							<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.positiveMoveVal}  /  (${childBean.positiveMoveLtp})</td> 
							</c:forEach>
						</tr>
		   				<tr>	
							<td class="optionTableColHeader">Max Loss</td>
							<td class="optionTableColVal" colspan="1">${mainBean.negativeMoveVal}</td>
							<td class="optionTableColHeader">Max Loss</td>
							<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.negativeMoveVal}  /  (${childBean.negativeMoveLtp})</td> 
							</c:forEach>
						</tr>
		   				<tr>	
							<td class="optionTableColHeader">Traded At</td>
							<td class="optionTableColVal" colspan="1">${mainBean.tradedAtDtTm}</td>
							<td class="optionTableColHeader">Traded At</td>
							<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.tradedAtDtTm}</td> 
							</c:forEach>
							
						</tr>
		   				<tr>	
							<td class="optionTableColHeader">Exited At</td>
		   					<td class="optionTableColVal" colspan="1">${mainBean.exitedAtDtTm}</td>
		   					<td class="optionTableColHeader">Exited At</td>
		   					<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.exitedAtDtTm}</td> 
							</c:forEach>	
						</tr>
						
						
						<tr>	
							<td></td>
		   					<td class="optionTableColVal" colspan="1"></td>
		   					<td class="optionTableColHeader">IndexAtEntry</td>
		   					<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.stockPriceEntry}</td> 
							</c:forEach>	
						</tr>
						<tr>	
							<td></td>
		   					<td class="optionTableColVal" colspan="1"></td>
		   					<td class="optionTableColHeader">IndexAtExit</td>
		   					<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.stockPriceExit}</td> 
							</c:forEach>	
						</tr>
						<tr>	
							<td></td>
		   					<td class="optionTableColVal" colspan="1"></td>
		   					<td class="optionTableColHeader">FutureAtEntry</td>	
		   					<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.futurePriceEntry}</td> 
							</c:forEach>
						</tr>
						<tr>	
							<td></td>
		   					<td class="optionTableColVal" colspan="1"></td>
		   					<td class="optionTableColHeader">FutureAtExit</td>	
		   					<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
								<td class="optionTableColVal">${childBean.futurePriceExit}</td> 
							</c:forEach>
						</tr>
			    	</tbody>
			    </table>
			   <br>
			</c:forEach>	
		</div>
	</jsp:attribute>
</t:simpleTemplate>