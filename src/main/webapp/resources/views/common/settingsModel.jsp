<!DOCTYPE html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href='<spring:url value="/resources/static/styles/modelStyle.css"/>'
	rel="stylesheet" />
<script type="text/javascript"
	src='<spring:url value="/resources/static/js/modelApp.js"/>'></script>
	<script type="text/javascript"
	src='<spring:url value="/resources/static/js/app.js"/>'></script>
<script>
	// Get the modal
	var modal = document.getElementById("myModal");

	// Get the button that opens the modal
	var btn = document.getElementById("myBtn");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	// When the user clicks the button, open the modal 
	btn.onclick = function() {
		//modal.style.display = "block";
	}

	// When the user clicks on <span> (x), close the modal
	/* span.onclick = function() {
		 modal.style.display = "none";
	} */

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			//modal.style.display = "none";
		}
	}
	
	function closeModelwindow() {
		document.getElementById("myModal").style.display = "none";
	}
	
	function queryCandleNumber() {
		try {
			var queryCandleNumberLink = document
					.getElementById('queryCandleNumberLink');
			var href = queryCandleNumberLink.getAttribute("href");
			queryCandleNumberLink.setAttribute("href", href + "?queryCandleNumber="
					+ document.getElementById('queryCandleNumber').value);
		} catch (e) {
			alert('Error - ' + e.message);
		}
	}
</script>
</head>
<title>Tradeware</title>
<body>
	<div id="myModal" class="modal">

		<!-- Modal content -->
		<div class="modal-content">
			<div class="modal-header">
				<span class="close" onclick="closeModelwindow();">&times;</span>
				<h2>Tradeware - Settings</h2>
			</div>
			<div class="modal-body">

				<div class="myContentDiv" id="rightDayStatusInnerId">
					<table style="border-collapse: collapse;">
						<tr>
							<td style="padding-left: 15px; padding-top: 5px; border: 0px;">
								Trading Switch</td>
							<td style="padding-left: 15px; border: 0px;"><a
								href='<c:url value = "/tradingSwitch"/>'>${tradingSwitch}</a></td>
						</tr>
						<tr>
							<td style="padding-left: 15px; padding-top: 5px; border: 0px;">
								Trading Force Close Switch</td>
							<td style="padding-left: 15px; border: 0px;">${tradingNotForceClosedSwitch}</td>
						</tr>
						<tr>
							<td style="padding-left: 15px; padding-top: 5px; border: 0px;">
								4. customO=H=L(Match)(B-B^^S-S)</td>
							<td style="padding-left: 15px; border: 0px;"><a
								href='<c:url value = "/tradingCustomOHLCMatchSwitch"/>'>${tradingCustomOHLCMatchSwitch}</a></td>
						</tr>
						<tr>
							<td
								style="padding-left: 15px; padding-top: 5px; border: 0px; 5 px; padding-bottom: 10px;">
								Trading all OHLC MATCH Switch</td>
							<td style="padding-left: 15px; border: 0px;"><a
								href='<c:url value = "/tradingOhlcTradeStateMatchSwitch"/>'>${tradingOhlcTradeStateMatchSwitch}</a></td>
						</tr>
						<tr>
							<td
								style="padding-left: 15px; padding-top: 5px; border: 0px; 5 px; padding-bottom: 10px;">
								Trading All OHLC Switch</td>
							<td style="padding-left: 15px; border: 0px;"><a
								href='<c:url value = "/tradingAllOhlcSwitch"/>'>${tradingAllOhlcSwitch}</a></td>
						</tr>
						<tr>
							<td style="padding-left: 15px; padding-top: 5px; border: 0px;">
								Trading All Switch</td>
							<td style="padding-left: 15px; border: 0px;"><a
								href='<c:url value = "/tradingAllSwitch"/>'>${tradingAllSwitch}</a></td>
						</tr>

						<tr>
							<td style="padding-left: 15px; border: 0px;">Report Date</td>
							<td style="padding-left: 15px; padding-top: 2px; border: 0px;">
								<input type="date" id="reportDate" name="reportDate"
								value="${reportDate}">
							</td>
							<td style="padding-left: 5px; border: 0px; display: none;"><a
								id="applyReportDateLink" onclick="fullDayReportByDate();"
								href="/applyReportDate"> Report Date</a></td>

						</tr>
						<tr>
							<td style="padding-left: 15px; border: 0px;">Query Candle</td>
							<td style="padding-left: 15px; padding-top: 2px; border: 0px;">
								<input type="number" id="queryCandleNumber" 
								name="queryCandleNumber" value="${queryCandleNumber}"
								onchange="try{ enablePageLoader();document.getElementById('queryCandleNumberLink').click(); }catch(e) {alert('ERR1'+e.message);}">
							</td>
							<td style="padding-left: 5px; border: 0px; display: none;"><a
								id="queryCandleNumberLink" onclick="queryCandleNumber();"
								href="/queryCandleNumber"> Query Candle Number Submit</a></td>
						</tr>
						<tr>
							<td style="padding-left: 15px; border: 0px;">Scanner Status</td>
							<td style="padding-left: 15px; padding-top: 2px; border: 0px;">
								<b><span style='color: green'>&nbsp;
										Status>>${scannerRunnngSatusTime} </span></b>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				<h3>Copy right@2020 All rights reserved to tradeware.in</h3>
			</div>
		</div>

	</div>
</body>
</html>