<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:masterTemplate2>
	<jsp:attribute name="body_area">
		<div
			style="height: 98%; width: 25%; position: absolute; left: 20%; border: 1px solid rgba(18, 27, 27, 0.51); border-radius: 5px; margin: 5px 5px 5px 5px;">
				  <div class="borderDiv ">
					<form action="/login" method="get">
						<div style="padding-top: 10px;">Default Admn user Kite session- AF7508 - Srinivas</div>
						 <br>
	            		<div style="padding-top: 10px;">Admin User Zerodha Id : 
	            			<select name="userId" disabled="disabled">
	    						<option value="AF7508">AF7508 - Srinivas</option>
	   							<option value="ZI7952">ZI7952 - Niranjan</option>
							</select>
						</div>
	           			 <br><br>
	            		<input type="submit" value="Submit" disabled="disabled" />
	        		</form>
        		</div>
        		
        		<div class="borderDiv ulParentDiv ">
        			<ul>
        				<li><a href='<c:url value = "/getTradeLevels"/>' onclick="enablePageLoader();">Trade levels</a></li>
        				<li><a href='<c:url value = "/getOptionChainReport"/>' onclick="enablePageLoader();">Option chain</a></li>
        				<li><a href='<c:url value = "/getOptionChainReport2"/>' onclick="enablePageLoader();">Option chain (No Row span)</a></li>
        				<li class="spacer"><a href='<c:url value = "/getOptionCallDayReport"/>' onclick="enablePageLoader();">Option calls</a></li>
        				<li><a href='<c:url value = "/getPositionalFutureCallDayReport"/>' onclick="enablePageLoader();">Positional future calls</a></li>
        				<li><a href='<c:url value = "/getNarrow7CallDayReport"/>' onclick="enablePageLoader();">Narror7 calls</a></li>
        				<li class="spacer2"><a href='<c:url value = "/getNarrow7CallDayReport2"/>' onclick="enablePageLoader();">Narror7 calls2</a></li>
        				
        				<li><a href='<c:url value = "/refreshOIDataForAll"/>' onclick="enablePageLoader();">Refresh OI for all trades</a></li>
        				<li><a href='<c:url value = "/refreshOIDataForTops"/>' onclick="enablePageLoader();">Refresh OI for Strong trades</a></li>
        				<li class="spacer2"><a href='<c:url value = "/bestTrades"/>' onclick="enablePageLoader();">Strong TRADABLES</a></li>
        				
        				<li><a href='<c:url value = "/getSymbols"/>' onclick="enablePageLoader();">Symbols</a></li>
        				<li><a href='<c:url value = "/getSymbolsByTrend"/>' onclick="enablePageLoader();">Symbols By Trend</a></li>
        				<li class="spacer2"><a href='<c:url value = "/prepareTradewareDatabase"/>' onclick="enablePageLoader();">Prepare database</a></li>
        				<li class="spacer2"><a href='<c:url value = "/forceStartAppNkp"/>' onclick="enablePageLoader();">Force start (NKP Levels)</a></li>
        				
        				<li class="spacer"><a href='<c:url value = "/forceStartDayTrading"/>' onclick="enablePageLoader();">Force start (Day Trading)</a></li>
        			</ul>
        		</div>
        </div>
        <div
			style="text-align: left !important; height: 98%; width: 50%; position: absolute; left: 46%; border: 1px solid rgba(18, 27, 27, 0.51); border-radius: 5px; margin: 5px 5px 5px 5px; overflow: auto;">
        	<span class="error">${responseMessage}</span>
        		<div
				style="padding-top: 10px !important; padding-left: 5px !important;">
        			Click on a link to Navigate 
        		</div>
        		<div class="ulParentDiv">
        		
        		<ul class="spacer2">
        			<li><a href='<c:url value = "/getAccessToken"/>'>Access Token</a> &nbsp;&nbsp; ${accessToken}</li>
        			<li><a href='<c:url value = "/getAccessTokenHist"/>'>Access Token Hist</a> &nbsp;&nbsp; ${accessTokenHist}</li>
        		</ul>
        		
        		<ul style="float: left; max-width:39%;">
        			<li><a id="reportByDateLink"
						href='<c:url value = "/report"/>'>Running Report</a></li>
        			<li><a href='<c:url value = "/reportFullFilter"/>'>Full Day Report(Filter)</a></li>
        			<li><a href='<c:url value = "/tradedReport1"/>'>Full Report - OHLC = Tradable State</a></li>
        			<li class="spacer"><a
						href='<c:url value = "/tradedReport"/>'>Full Report - OHLC</a></li>
        			
        			<li><a href='<c:url value = "/reportDayLevelRunning"/>'>Day Level Report Running</a> </li>
        			<li><a href='<c:url value = "/reportDayLevelFilter"/>'>Day Level Report (Filter)</a></li>
        			<li><a
						href='<c:url value = "/reportDayLevelFilterOhlcEqualTradable"/>'>Day Level Report (Filter OHLC = Tradable State)</a> </li>
        			<li class="spacer"><a
						href='<c:url value = "/reportDayLevelFilterOhlc"/>'>Day Level Report (OHLC)</a> </li>
        			
        			<li><a href='<c:url value = "/reportSystemError"/>'>System Info/Errors</a></li>
        			<li class="spacer"><a
						href='<c:url value = "/reportActivityList"/>'>System Activity</a></li>
        			
        			<li><a
						href='<c:url value = "/reportFullFilterCustom1"/>'>custom1</a></li>
        			<li><a
						href='<c:url value = "/reportFullFilterCustom2"/>'>customO=H=L(Match)</a></li>
        			<li class="spacer"><a
						href='<c:url value = "/reportFullFilterCustom3"/>'>custom1O=H=L</a></li>
        			
        			<li><a
						href='<c:url value = "/stochasticRule1Trades"/>'>stochasticRule1Trades</a></li>
        			<li><a
						href='<c:url value = "/stochasticRule1Trades"/>'>stochasticRule2Trades</a></li>
        			<li class="spacer2"><a
						href='<c:url value = "/stochasticRule1Trades"/>'>stochasticRule3Trades</a></li>
        			
        			<li><a href='<c:url value = "/forceStartApp"/>'
						onclick="enablePageLoader();">Force Start the Application</a></li>
        			
        		</ul>
        		<ul style="float: right; padding-right: 20px; max-width:60%;">
        			<li class="spacer2"><a
						href='<c:url value = "/filter1000"/>' onclick="enablePageLoader();">Home 2</a></li>
        			
        			<li><a id="reportByDateLink"
						href='<c:url value = "/report"/>'>Running Report</a></li>
        			<li class="spacer"><a href='<c:url value = "/reportFullFilter"/>'>Full Day Report(Filter)</a></li>
        			
        			<li><a href='<c:url value = "/niftyAndBankNiftyTradesVwap"/>'>NIFTY-BANK-NIFTY</a></li>
        			<li class="spacer2"><a href='<c:url value = "/niftyAndBankNiftyTradesFilterVwap"/>'>[FLTR]_NIFTY-BANK-NIFTY</a></li>
        		
        			<li><a href='<c:url value = "/initProfitableProdRuleVwap"/>'>INIT_PROFITABLE_PROD_RULE</a></li>
        			<li><a href='<c:url value = "/initProfitableProdRuleFilterVwap"/>'>INIT_PROFITABLE_PROD_RULE_FLTR</a></li>
        			
        			<li><a href='<c:url value = "/customRuleProfitSmaVwap_Vwap"/>'>FLTR_VOLUME_SMA_PRFT_STRONG</a></li>
        			<li><a href='<c:url value = "/customRuleStrongVolumeFilterVwap"/>'>FLTR_VOLUME_STRONG</a></li>
        			<li class="spacer"><a href='<c:url value = "/customRuleStrongVolumeFilterMatchVwap"/>'>FLTR_VOLUME_STRONG_MATCH</a></li>
        			
        		
        			<li><a href='<c:url value = "/customRule3VwapStrongFilterVwap"/>'>FLTR_VWAP_STRONG</a></li>
        			<li class="spacer"><a href='<c:url value = "/customRule3VwapStrongFilterOHLC"/>'>FLTR_VWAP_STRONG_2</a></li>
        		
        			<li><a href='<c:url value = "/customRuleSmaVwapPlusRule"/>'>FLTR_SMA_VWAP_PLUS_RULE</a></li>
        			<li class="spacer"><a href='<c:url value = "/customRuleSmaVwapPlusRule"/>'>FLTR_SMA_VWAP_PLUS_RULE</a></li>
        		
        			<li><a href='<c:url value = "/reportFullFilterCustom2-1000-filterVwap"/>'>FLTR_3.customO=H=L(Match)_1000</a></li>
        			<li class="spacer"><a href='<c:url value = "/reportFullFilterCustom2-filter"/>'>FLTR_3.customO=H=L(Match)</a></li>
        		
        		</ul>
		</div>
		</div>
	</jsp:attribute>
</t:masterTemplate2>