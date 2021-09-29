function fullDayReportByDate() {
	try {
		var reportLink = document.getElementById('applyReportDateLink');
		var href = reportLink.getAttribute("href");
		reportLink.setAttribute("href", href + "?reportDate="
				+ document.getElementById('reportDate').value);
	} catch (e) {
		alert('Error - ' + e.message);
	}
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

function myFunction() {
	try {
		var table = document.getElementById('mytable');
		var mytableHeader = document.getElementById('mytableHeader');

		if (null != mytableHeader
				&& null != mytableHeader.className
				&& (mytableHeader.className.includes("getOptionChainReport") || mytableHeader.className.includes("rowSpanOptionChainReport") || mytableHeader.className.includes("rowSpanReportFull"))) {
			myFunctionForOptionChainTables();
		} else if (null != mytableHeader
				&& null != mytableHeader.className
				&& (mytableHeader.className.includes("optionStrategyTradeReport") )) {
			myFunctonForOptionStrategyReportTable();
		} else if (null != mytableHeader
				&& null != mytableHeader.className
				&& (mytableHeader.className.includes("optionStrategyTradeView2Report") )) {
			myFunctonForOptionStrategyReportTableSubHeaderAlignment();
		} else if (null != table && table.rows.length >= 1) {
			var rowFirst = table.rows[0];
			var rowFirstHeader = mytableHeader.rows[0];
			for (var j = 0; j < rowFirst.cells.length; j++) {
				var tableCell = rowFirst.cells[j];
				var tableHeaderCell = rowFirstHeader.cells[j];
				var minWidth = tableCell.offsetWidth + 'px';
				if  (null != tableHeaderCell) {
					if (tableCell.offsetWidth < tableHeaderCell.offsetWidth) {
						minWidth = tableHeaderCell.offsetWidth + 'px';
					}
					tableCell.style.minWidth = minWidth;
					tableHeaderCell.style.minWidth = minWidth;	
				}
			}
		}
	} catch (e) {
		alert("Error - " + e.message);
	}
}

function myFunctionForOptionChainTables() {
	try {
		var table = document.getElementById('mytable');
		var mytableHeader = document.getElementById('mytableHeader');
		var counter1 = 0;
		var counter2 = 0;
		var span3Width = 0;
		var span15Width = 0;
		var headerFirst = 0;
		var headerRowSecond = 0;
		if (mytableHeader.className.includes("getOptionChainReport")) {
			if (null != table && table.rows.length > 0) {
				var rowFirst = table.rows[0];
				var rowFirstHeader = mytableHeader.rows[0];
				var rowSecondHeader = mytableHeader.rows[1];
				for (var j = 0; j < rowFirst.cells.length; j++) {
					var tableCell = rowFirst.cells[j];
					var tableHeaderCellRowFirst = rowFirstHeader.cells[headerFirst];
					var tableHeaderCellRowSecond = rowSecondHeader.cells[headerRowSecond];

					if (null != tableHeaderCellRowFirst.rowSpan
							&& tableHeaderCellRowFirst.rowSpan == "2") {
						var minWidth = tableCell.offsetWidth + 'px';
						if (tableCell.offsetWidth < tableHeaderCellRowFirst.offsetWidth) {
							minWidth = tableHeaderCellRowFirst.offsetWidth
									+ 'px';
						}
						tableCell.style.minWidth = minWidth;
						tableHeaderCellRowFirst.style.minWidth = minWidth;
						headerFirst++;
					} else if (null != tableHeaderCellRowFirst.colSpan
							&& tableHeaderCellRowFirst.colSpan == "15") {
						counter1++;
						counter2++;
						span3Width = span3Width + tableCell.offsetWidth;
						span15Width = span15Width + tableCell.offsetWidth;
						if (counter1 == "3") {
							span3Width = span3Width + 3;// to adjust borderline
														// width add 3
							tableHeaderCellRowSecond.style.minWidth = span3Width
									+ 'px';
							counter1 = 0;
							span3Width = 0;
							headerRowSecond++;
						} else {
							var minWidth = tableCell.offsetWidth + 'px';
							tableCell.style.minWidth = minWidth;
						}
						if (counter2 == "15") {
							span15Width = span15Width + 15;// to adjust
															// borderline width
															// add 3
							tableHeaderCellRowFirst.style.minWidth = span15Width
									+ 'px';
							counter2 = 0;
							span15Width = 0;
							headerFirst++;
						}
					}
				}
			}
		} else if (mytableHeader.className.includes("rowSpanOptionChainReport")) {
			if (null != table && table.rows.length > 0) {
				var headerIndex = 0;
				var counter = 0;
				var span3Width = 0;
				var rowFirst = table.rows[0];
				var rowFirstHeader = mytableHeader.rows[0];
				for (var j = 0; j < rowFirst.cells.length; j++) {
					var tableCell = rowFirst.cells[j];
					var tableHeaderCell = rowFirstHeader.cells[headerIndex];
					if (null != tableHeaderCell) {
						if (null != tableCell.rowSpan
								&& tableCell.rowSpan == "2") {
							var minWidth = tableCell.offsetWidth + 'px';
							if (tableCell.offsetWidth < tableHeaderCell.offsetWidth) {
								minWidth = tableHeaderCell.offsetWidth + 'px';
							}
							tableCell.style.minWidth = minWidth;
							tableHeaderCell.style.minWidth = minWidth;
							headerIndex++;
						} else if (null != tableCell.rowSpan
								&& tableCell.rowSpan == "1") {
							if (null != tableHeaderCell.colSpan
									&& tableHeaderCell.colSpan == "3") {
								counter++;
								span3Width = span3Width + tableCell.offsetWidth;
								if (counter == "3") {
									span3Width = span3Width + 6;// to adjust
																// borderline
																// width add 3
									tableHeaderCell.style.minWidth = span3Width
											+ 'px';
									counter = 0;
									span3Width = 0;
									headerIndex++;
								} /* else { */
								var minWidth = tableCell.offsetWidth + 'px';
								tableCell.style.minWidth = minWidth;
								/* } */
							} else if (null != tableHeaderCell.colSpan
									&& tableHeaderCell.colSpan == "2") {
								counter++;
								span3Width = span3Width + tableCell.offsetWidth;
								if (counter == "2") {
									span3Width = span3Width + 4// 6; to adjust
																// borderline
																// width add 3
									tableHeaderCell.style.minWidth = span3Width
											+ 'px';
									counter = 0;
									span3Width = 0;
									headerIndex++;
								} /* else { */
								var minWidth = tableCell.offsetWidth + 'px';
								tableCell.style.minWidth = minWidth;
								/* } */
							} else if (null != tableHeaderCell.colSpan
									&& tableHeaderCell.colSpan == "1") {
								var minWidth = tableCell.offsetWidth + 'px';
								if (tableCell.offsetWidth < tableHeaderCell.offsetWidth) {
									minWidth = tableHeaderCell.offsetWidth
											+ 'px';
								}
								tableCell.style.minWidth = minWidth;
								tableHeaderCell.style.minWidth = minWidth;
								headerIndex++;
							}
						}
					}
				}
			}
		} else if (mytableHeader.className.includes("rowSpanReportFull")) {
			if (null != table && table.rows.length > 0) {
				var headerIndex = 0;
				var counter = 0;
				var span2Width = 0;
				var rowFirst = table.rows[0];
				var rowFirstHeader = mytableHeader.rows[0];
				for (var j = 0; j < rowFirst.cells.length; j++) {
					var tableCell = rowFirst.cells[j];
					var tableHeaderCell = rowFirstHeader.cells[headerIndex];
					//console.log('headerIndex:'+headerIndex+'tableHeaderCell:'+tableHeaderCell)
					if (null == tableHeaderCell.colSpan
							&& tableHeaderCell.colSpan == "1") {
						var minWidth = tableCell.offsetWidth + 'px';
						if (tableCell.offsetWidth < tableHeaderCell.offsetWidth) {
							minWidth = tableHeaderCell.offsetWidth + 'px';
						}
						tableCell.style.minWidth = minWidth;
						tableHeaderCell.style.minWidth = minWidth;
						headerIndex++;
					} else if (null != tableHeaderCell.colSpan
							&& tableHeaderCell.colSpan == "2") {
						counter++;
						span2Width = span2Width + tableCell.offsetWidth;
						if (counter == "2") {
							span2Width = span2Width + 3// 6; to adjust borderline width add 3
							tableHeaderCell.style.minWidth = span2Width + 'px';
							counter = 0;
							span2Width = 0;
							headerIndex++;
						}
						var minWidth = tableCell.offsetWidth + 'px';
						tableCell.style.minWidth = minWidth;

					} else if (null != tableHeaderCell.colSpan
							&& tableHeaderCell.colSpan == "1") {
						var minWidth = tableCell.offsetWidth + 'px';
						if (tableCell.offsetWidth < tableHeaderCell.offsetWidth) {
							minWidth = tableHeaderCell.offsetWidth + 'px';
						}
						tableCell.style.minWidth = minWidth;
						tableHeaderCell.style.minWidth = minWidth;
						headerIndex++;
					}
				}
			}
		}
	} catch (e) {
		alert("Error Option Table - " + e.message);
	}
}

function handlePageLoader() {
	 if (document.readyState !== "complete") {
    	//alert(document.readyState);
        /* document.querySelector( 
          "body").style.visibility = "hidden";  */
          /* document.querySelector( 
          "body").style.visibility = "visible";
        document.querySelector( 
          "#pageContentArea").style.visibility = "visible";  */
        
       /*  document.querySelector( 
        "loader").style.visibility = "hidden";  */
        document.getElementById("loader").style.visibility = "visible";
        document.getElementById("pageContentArea").classList.remove("modal-backdrop");
    } else { 
    	//alert(document.readyState);
        /*  document.querySelector( 
          "#pageContentArea").style.display = "none";  
        document.querySelector( 
          "body").style.visibility = "visible";  */
        
        /* document.querySelector( 
        "loader").style.visibility = "visible";  */
    	document.getElementById("loader").style.visibility = "hidden";
    	
    } 
}

function enablePageLoader() {
	document.getElementById("loader").style.visibility = "visible";
	document.getElementById("pageContentArea").classList.add("modal-backdrop");
}

function closePageLoaderManally() {
	document.getElementById("loader").style.visibility = "hidden";
	document.getElementById("pageContentArea").classList
			.remove("modal-backdrop");
}

function reportScreenAutoRefreshEnableDisable(myWindow) {
	var autoRefreshChkBx = document.getElementById('reportAutoRefreshId');
	if (null != autoRefreshChkBx && autoRefreshChkBx.checked) {
		myWindow.reload();
	}
}

//below code is duplicate of else if (mytableHeader.className.includes("rowSpanReportFull")) { except var rowFirst = table.rows[1];
function myFunctonForOptionStrategyReportTable() {
	var table = document.getElementById('mytable');
	var mytableHeader = document.getElementById('mytableHeader');
	if (null != table && table.rows.length >= 1) {
		var headerIndex = 0;
		var counter = 0;
		var span2Width = 0;
		var rowFirst = table.rows[1];
		var rowFirstHeader = mytableHeader.rows[0];
		for (var j = 0; j < rowFirst.cells.length; j++) {
			var tableCell = rowFirst.cells[j];
			var tableHeaderCell = rowFirstHeader.cells[headerIndex];
			// console.log('headerIndex:'+headerIndex+'tableHeaderCell:'+tableHeaderCell)
			if (null == tableHeaderCell.colSpan
					&& tableHeaderCell.colSpan == "1") {
				var minWidth = tableCell.offsetWidth + 'px';
				if (tableCell.offsetWidth < tableHeaderCell.offsetWidth) {
					minWidth = tableHeaderCell.offsetWidth + 'px';
				}
				tableCell.style.minWidth = minWidth;
				tableHeaderCell.style.minWidth = minWidth;
				headerIndex++;
			} else if (null != tableHeaderCell.colSpan
					&& tableHeaderCell.colSpan == "2") {
				counter++;
				span2Width = span2Width + tableCell.offsetWidth;
				if (counter == "2") {
					span2Width = span2Width + 3// 6; to adjust borderline width
												// add 3
					tableHeaderCell.style.minWidth = span2Width + 'px';
					counter = 0;
					span2Width = 0;
					headerIndex++;
				}
				var minWidth = tableCell.offsetWidth + 'px';
				tableCell.style.minWidth = minWidth;

			} else if (null != tableHeaderCell.colSpan
					&& tableHeaderCell.colSpan == "1") {
				var minWidth = tableCell.offsetWidth + 'px';
				if (tableCell.offsetWidth < tableHeaderCell.offsetWidth) {
					minWidth = tableHeaderCell.offsetWidth + 'px';
				}
				tableCell.style.minWidth = minWidth;
				tableHeaderCell.style.minWidth = minWidth;
				headerIndex++;
			}
		}
	}
}

function myFunctonForOptionStrategyReportTableSubHeaderAlignment() {
	var tableList = document.querySelectorAll('table[id=mytable]');
	var mytableHeaderList = document.querySelectorAll('table[id=mytableHeader]');
	
	for (var i = 0; i < tableList.length; i++) {
		var table = tableList[i];
		var mytableHeader = mytableHeaderList[i];
		if (null != table && table.rows.length >= 1) {
			var headerIndex = 0;
			var counter = 0;
			var spanWidth = 0;
			var rowFirst = table.rows[0];
			var rowFirstHeader = mytableHeader.rows[1];
			for (var j = 0; j < rowFirst.cells.length; j++) {
				counter++;
				var tableCell = rowFirst.cells[j];
				var tableHeaderCell = rowFirstHeader.cells[headerIndex];
				spanWidth = spanWidth + tableCell.offsetWidth;
				// console.log('headerIndex:'+headerIndex+'tableHeaderCell:'+tableHeaderCell)
				if (null != tableHeaderCell && null != tableHeaderCell.colSpan
						&& tableHeaderCell.colSpan == counter) {
					if (headerIndex == 0) {
						//adjust first header
						spanWidth = (spanWidth - (spanWidth * 0.048));
					}
					tableHeaderCell.style.width = spanWidth + 'px';
					tableHeaderCell.style.minWidth = spanWidth + 'px';
					tableHeaderCell.style.maxWidth = spanWidth + 'px';
					headerIndex++;
					spanWidth = 0;
					counter = 0;
				}
				var minWidth = tableCell.offsetWidth + 'px';
				tableCell.style.minWidth = minWidth;
			}
		}
	}
	
}