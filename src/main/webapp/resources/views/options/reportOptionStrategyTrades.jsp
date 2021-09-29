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
        	<table class="dataTaleHeaderStye optionStrategyTradeReport" id="mytableHeader">
        		<thead>
	   				<tr class="dataTaleHeaderRowStye">
	   					<th>Symbol</th>
	   					<th>Position</th>
						<th>Lot Size</th>
						<th>Ticker Size</th>
						<th>Strategy</th>
						<th>Sub Strategy</th>
						<th>Expiry Date</th>
						<th>Profit/Loss</th>
						<th>Max Profit</th>
						<th>Max Loss</th>
						<th>Traded At</th>
						<th>Exited At</th>
					</tr>
				</thead>
			</table>
			<table class="dataTaleContentStye" id="mytable">
        		<tbody>
        			<c:forEach items="${tradeList}" var="mainBean" varStatus="status">
        				<tr>
		      				<th colspan="16" align="center">${mainBean.tradeName}</th>
		    			</tr>
		    			<tr>
							<td align="center" colspan="2">${mainBean.symbolId}</td> 
							<td align="center" colspan="1">${mainBean.tradePosition}</td>
							<td align="center" colspan="1">${mainBean.lotSize}</td>
							<td align="center" colspan="1">${mainBean.optionTickerSize}</td>
							<td align="center" colspan="1">${mainBean.tradeStrategy}</td>
							<td align="center" colspan="2">${mainBean.tradeSubStrategy}</td>
							<td align="center" colspan="1">${mainBean.expiryDate}</td>
							<td align="center" colspan="1">${mainBean.profitLossAmtVal}</td>
							<td align="center" colspan="1">${mainBean.positiveMoveVal}</td>
							<td align="center" colspan="1">${mainBean.negativeMoveVal}</td>
							<td align="center" colspan="2">${mainBean.tradedAtDtTm}</td>
							<td align="center" colspan="2">${mainBean.exitedAtDtTm}</td>   			
		    			</tr>
		    			
		    			<tr class="dataTaleSubHeaderRowStye">
		   					<th>Strike</th>
		   					<th>Position</th>
							<th>TradeType</th>
							<th>OptionType</th>
							<th>TradedAt</th>
							<th>ExitedAt</th>
							<th>IndexAtEntry</th>
							<th>IndexAtExit</th>
							<th>FutureAtEntry</th>
							<th>FutureAtExit</th>
							<th>NoOfLots</th>
							<th>Profit/Loss</th>
							<th>Max Profit</th>
							<th>Max Loss</th>
							<th>Traded At</th>
							<th>Exited At</th>
						</tr>
		    			<tbody>
		    				<c:forEach items="${mainBean.tradeChildBeanList}" var="childBean" varStatus="index">
		    					<tr>
									<td align="center">${childBean.strikePrice}</td> 
									<td align="center">${childBean.tradePosition}</td>
									<td align="center">${childBean.tradeType}</td>
									<td align="center">${childBean.optionType}</td>
									<td align="center">${childBean.tradedAtVal}</td>
									<td align="center">${childBean.exitedAtVal}</td>
									<td align="center">${childBean.stockPriceEntry}</td>
									<td align="center">${childBean.stockPriceExit}</td>
									<td align="center">${childBean.futurePriceEntry}</td>
									<td align="center">${childBean.futurePriceExit}</td>
									<td align="center">${childBean.numberOfLots}</td>
									<td align="center">${childBean.profitLossAmtVal}</td>
									<td align="center">${childBean.positiveMoveVal}/(${childBean.positiveMoveLtp})</td>
									<td align="center">${childBean.negativeMoveVal}/(${childBean.negativeMoveLtp})</td>
									<td align="center">${childBean.tradedAtDtTm}</td>
									<td align="center">${childBean.exitedAtDtTm}</td>   			
				    			</tr>	
		    				</c:forEach>
		    			</tbody>
		    			
					</c:forEach>
				</tbody>
			</table>	
		</div>
	</jsp:attribute>
</t:simpleTemplate>