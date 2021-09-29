<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<div class="topHeaderSimple">
	<img class="topHeaderLogoImage"
		src="<c:url value="/resources/static/images/logo.png"/>" />

	<div class="topHeaderRightBanner_Temporary">
		<table class="topHeaderRightBannerTable">
			<tr>
				<td class="topHeaderRightBannerTableCellLeft">Trading Switch</td>
				<td class="topHeaderRightBannerTableCellRight"><a
					href='<c:url value = "/tradingSwitch"/>'>${tradingSwitch}</a> 
					 &nbsp; Force Close &nbsp; ${tradingNotForceClosedSwitch}
					<a
					id="myBtn" target="#settingsModal" class="float-r pad-r-2 cursorPointer" ><img
						align="top"
						src="<c:url value="/resources/static/images/setting2.png"/>"
						onclick="document.getElementById('myModal').style.display = 'block';" /></a></td>
			</tr>
			<tr>
				<td class="topHeaderRightBannerTableCellLeft">Engine Status</td>
				<td class="topHeaderRightBannerTableCellRight"><b>${threadScannerStatus}</b>
					<a id="logOutBtn" class="float-r pad-r-2 cursorPointer"
						href='<c:url value = "/logout"/>'><img align="top"
							title="logout"
							src="<c:url value="/resources/static/images/logoff.png"/>" /></a></td>
			</tr>
			<tr>
				<td class="topHeaderRightBannerTableCellLeft">Scanner Update</td>
				<td class="topHeaderRightBannerTableCellRight"><b>${scannerRunnngSatusTime}</b>
				<input id="reportAutoRefreshId" class="stylishCheckbox float-r pad-r-2" type="checkbox" value="true" />
				</td>
			</tr>
			<tr>
				<td class="topHeaderRightBannerTableCellLeft">Report Date</td>
				<td class="topHeaderRightBannerTableCellRight pad-t-2"><input
					type="date" id="reportDate" name="reportDate" value="${reportDate}"
					onchange="enablePageLoader();document.getElementById('applyReportDateLink').click();">
					<span id="readOnlyCandleNumber" class="float-r pad-r-2 pad-t-5">${queryCandleNumber}
				</span></td>
			</tr>
		</table>
	</div>

	<div style="width: 45%; float: right">
		<!-- div class="topHeaderMiddleBanner">Tradeware Systems</div>
			<div class="topHeaderMiddleBannerLower">
				An Automated Trading Platform</div -->
		<img class="topHeaderBannerLogoImage"
			src="<c:url value="/resources/static/images/bannerLogo.png"/>" />
	</div>
</div>
</html>