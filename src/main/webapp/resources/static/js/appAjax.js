function ajaxManualClose(component, event) {
	try {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var responseObj = JSON.parse(this.responseText);
				var responseMessage = ' ' + responseObj.symbolName + ' - '
						+ responseObj.itemId + ' '
						+ ' trade exited successfully.  \n \t Profit Loss   : '
						+ responseObj.manualBookProfitLossAmtVal
						+ '\n \t Exited at Val : '
						+ responseObj.manualExitedAtVal;
				alert(responseMessage);

				event.stopPropagation();
				event.stopImmediatePropagation();
				closePageLoaderManally();
				return false;
			}
		};
		var url = component.href;

		xhttp.open("GET", url, true);
		xhttp.send();
	} catch (e) {
		alert('Error - ' + e.message);
	}
}

function onOptionTradeOrderSymbolChange(selectItem, event) {
	try {
		/*var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				addSelectItemOptions(document.getElementById('expiryDate'), JSON.parse(http.responseText));
				event.stopPropagation();
				event.stopImmediatePropagation();
				return false;
			} 
		};
		var url = "/createOptionTrade2?symbolId="+selectItem.value;

		xhttp.open("GET", url, false);
		xhttp.send();*/
		/*var url = "/createOptionTrade2?symbolId="+selectItem.value;
		var xhttp = new XMLHttpRequest();
		xhttp.onload = function(e) {
			addSelectItemOptions(document.getElementById('expiryDate'), JSON.parse(http.responseText));
			}
		xhttp.open("GET", url, false);
		//xhttp.responseType = 'json';
		xhttp.send();*/
		
		var http = new XMLHttpRequest();
		var url = "/createOptionTrade3";
		var params = "symbolId="+selectItem.value;
		http.open('POST', url, true);

		//Send the proper header information along with the request
		http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

		http.onreadystatechange = function() {//Call a function when the state changes.
		    if(http.readyState == 4 && http.status == 200) {
		        //alert(http.responseText);
		        addSelectItemOptions(document.getElementById('expiryDate'), JSON.parse(http.responseText)["expiryDatesList"]);
		        
		        document.getElementById('stockLastPrice').value = JSON.parse(http.responseText)["stockLastPrice"]; 
		        document.getElementById('futureLastPrice').value = JSON.parse(http.responseText)["futureLastPrice"];
		    }
		}
		http.send(params);
		
	} catch (e) {
		alert('Error - ' + e.message);
	}
}

function onOptionTradeStrategySelection(selectItem, event) {
	//alert('3. '+selectItem.value);
	try {
		var http = new XMLHttpRequest();
		var url = "/optionTradeSubStrategy";
		var params = "tradeStrategy="+selectItem.value;
		http.open('POST', url, true);

		//Send the proper header information along with the request
		http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

		http.onreadystatechange = function() {//Call a function when the state changes.
		    if(http.readyState == 4 && http.status == 200) {
		        addSelectItemOptions(document.getElementById('tradeSubStrategy'), JSON.parse(http.responseText));
		    }
		}
		http.send(params);
		
		//Retrieve Main strike price list
		var httpPost = new XMLHttpRequest();
		var url = "/mainStrikePriceList";
		var params = "symbolId="+document.getElementById('symbolId').value
		+"&tradeSubStrategy="+document.getElementById('tradeSubStrategy').value
		+"&indexValue="+document.getElementById('stockLastPrice').value
		+"&tradeStrategy="+selectItem.value;
		httpPost.open('POST', url, true);
		//Send the proper header information along with the request
		httpPost.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

		httpPost.onreadystatechange = function() {//Call a function when the state changes.
		    if(httpPost.readyState == 4 && httpPost.status == 200) {
		    	//Butter Fly
		    	if  (null != document.getElementById('strikePriceAtm') 
		    			&& (document.querySelector('input[name=tradeStrategy]:checked').value == 'Butter Fly')) {
		    		addSelectItemOptions(document.getElementById('strikePriceAtm'), JSON.parse(httpPost.responseText));
		    	}
		        //Strangle
		    	if  (null != document.getElementById('strikePriceLowerOfAtm') &&
		    			(document.querySelector('input[name=tradeStrategy]:checked').value == 'Strangle')) {
		    		 addSelectItemOptions(document.getElementById('strikePriceLowerOfAtm'), JSON.parse(httpPost.responseText));
		    	}
		       
		    }
		}
		httpPost.send(params);
		
		//make panels visible
		if (selectItem.value == 'Strangle' || selectItem.value == 'Straddle') {
			document.getElementById('strangleDataForm').style.display = 'block';
			//document.getElementById('butterFlyDataForm').style.display = 'none';
			var removeComp = document.getElementById('butterFlyDataForm');
			removeComp.remove();
		} else if (selectItem.value == 'Butter Fly' || selectItem.value == 'Ratio Spread') {
			//document.getElementById('strangleDataForm').style.display = 'none';
			document.getElementById('butterFlyDataForm').style.display = 'block';
			var removeComp = document.getElementById('strangleDataForm');
			removeComp.remove();
		}
		//
	} catch (e) {
		alert('Error - ' + e.message);
	}
}

//for call ratio spread
function onOptionTradeSubStrategySelection(selectItem, event) {
	//alert('3. '+selectItem.value);
	try {
		if (document.querySelector('input[name=tradeStrategy]:checked').value == 'Ratio Spread'
			|| document.querySelector('input[name=tradeStrategy]:checked').value == 'Straddle') {
		var httpPost = new XMLHttpRequest();
		var url = "/optionTradeSubStrategySelection";
		var params =  "&tradeSubStrategy="+document.getElementById('tradeSubStrategy').value
		+"&tradeStrategy="+document.querySelector('input[name=tradeStrategy]:checked').value
		+"&expiryDate="+document.getElementById('expiryDate').value
		+"&symbolId="+document.getElementById('symbolId').value
		+"&indexValue="+document.getElementById('stockLastPrice').value;
		httpPost.open('POST', url, true);
		//Send the proper header information along with the request
		httpPost.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

		httpPost.onreadystatechange = function() {//Call a function when the state changes.
		    if(httpPost.readyState == 4 && httpPost.status == 200) {
		    	if (document.querySelector('input[name=tradeStrategy]:checked').value == 'Ratio Spread') {
		    		updateFormFieldsOnRatioSpread(httpPost);
		    	} else if (document.querySelector('input[name=tradeStrategy]:checked').value == 'Straddle') {
		    		updateFormFieldsOnStraddle(httpPost);
		    	}
		    	
		    }
		}
		httpPost.send(params);
	}
	} catch (e) {
		alert('Error - ' + e.message);
	}
}


function onMainStrikePriceSelection(selectItem, event) {
	try {
		var http = new XMLHttpRequest();
		var url = "/protectionStrikePriceList";
		
		
		var strikePriceAtmVal ='';
		var strikePriceLowerOfAtmVal = '';
		//Butter Fly
    	if  (null != document.getElementById('strikePriceAtm') 
    			&& (document.querySelector('input[name=tradeStrategy]:checked').value == 'Butter Fly')) {
    		strikePriceAtmVal = document.getElementById('strikePriceAtm').value;
    	}
        //Strangle
    	if  (null != document.getElementById('strikePriceLowerOfAtm') &&
    			(document.querySelector('input[name=tradeStrategy]:checked').value == 'Strangle')) {
    		strikePriceLowerOfAtmVal  = document.getElementById('strikePriceLowerOfAtm').value;
    	}
		
		var params = "strikePriceLowerOfAtm="+document.getElementById('strikePriceLowerOfAtm').value
		+"&tradeSubStrategy="+document.getElementById('tradeSubStrategy').value
		+"&expiryDate="+document.getElementById('expiryDate').value
		+"&symbolId="+document.getElementById('symbolId').value
		+"&strikePriceOfAtm="+strikePriceAtmVal
		+"&strikePriceLowerOfAtm="+strikePriceLowerOfAtmVal
		+"&tradeStrategy="+document.querySelector('input[name=tradeStrategy]:checked').value
		+"&indexValue="+document.getElementById('stockLastPrice').value;
		http.open('POST', url, true);

		//Send the proper header information along with the request
		http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

		http.onreadystatechange = function() {//Call a function when the state changes.
		    if(http.readyState == 4 && http.status == 200) {
		       // alert(JSON.parse(http.responseText)["higherOfAtmTradeType"]);
		        addSelectItemOptions(document.getElementById('strikePriceHigherOfAtm'), JSON.parse(http.responseText)["protectionStrikePriceList"]);
		        if (document.querySelector('input[name=tradeStrategy]:checked').value == 'Butter Fly') {
		        	 document.getElementById('lowerOfAtmTradeType').value = JSON.parse(http.responseText)["lowerOfAtmTradeType"];
				        document.getElementById('higherOfAtmTradeType').value = JSON.parse(http.responseText)["higherOfAtmTradeType"];
				        document.getElementById('askPriceLowerOfAtm').value = JSON.parse(http.responseText)["askPriceLowerOfAtm"];
				        document.getElementById('bidPriceLowerOfAtm').value = JSON.parse(http.responseText)["bidPriceLowerOfAtm"]; 	
		        } else if (document.querySelector('input[name=tradeStrategy]:checked').value == 'Strangle') {
		        	 document.getElementById('lowerOfAtmTradeType').value = JSON.parse(http.responseText)["lowerOfAtmTradeType"];
				        document.getElementById('higherOfAtmTradeType').value = JSON.parse(http.responseText)["higherOfAtmTradeType"];
				        document.getElementById('askPriceLowerOfAtm').value = JSON.parse(http.responseText)["askPriceLowerOfAtm"];
				        document.getElementById('bidPriceLowerOfAtm').value = JSON.parse(http.responseText)["bidPriceLowerOfAtm"]; 	
		       
		        } else if (document.querySelector('input[name=tradeStrategy]:checked').value == 'Straddle') {
		        	updateFormFieldsOnStraddle(http);
		        }
		        
		    }
		}
		http.send(params);
	} catch (e) {
		alert('Error - ' + e.message);
	}
}

function onProtectStrikePriceSelection(selectItem, event) {
	try {
		var http = new XMLHttpRequest();
		var url = "/protectionStrikePriceSelectionEvent";
		var params = "strikePriceHigherOfAtm="+selectItem.value
		+"&tradeSubStrategy="+document.getElementById('tradeSubStrategy').value
		+"&expiryDate="+document.getElementById('expiryDate').value
		+"&symbolId="+document.getElementById('symbolId').value;
		http.open('POST', url, true);

		//Send the proper header information along with the request
		http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

		http.onreadystatechange = function() {//Call a function when the state changes.
		    if(http.readyState == 4 && http.status == 200) {
		       // alert(JSON.parse(http.responseText)["higherOfAtmTradeType"]);
		        document.getElementById('askPriceHigherOfAtm').value = JSON.parse(http.responseText)["askPriceHigherOfAtm"];
		        document.getElementById('bidPriceHigherOfAtm').value = JSON.parse(http.responseText)["bidPriceHigherOfAtm"];
		    }
		}
		http.send(params);
	} catch (e) {
		alert('Error - ' + e.message);
	}
}

function addSelectItemOptions(select, elmts) {
	//Clear existing options
	 var length = select.options.length;
	  while(length--){
	    select.remove(length);
	  }

	  //Add fresh elements
	  var emptyOption = document.createElement("option");
	  emptyOption.value = ' ';
	  emptyOption.textContent = '--- Select ---';
	  select.appendChild(emptyOption);

	  for (var i = 0; i < elmts.length; i++) {
        var optn = elmts[i];
        var el = document.createElement("option");
        el.textContent = optn;
        el.value = optn;
        select.appendChild(el);
    }
}

function addSelectItemOptions(select, elmts, defaultSelectItem) {
	//Clear existing options
	 var length = select.options.length;
	  while(length--){
	    select.remove(length);
	  }

	  //Add fresh elements
	  var emptyOption = document.createElement("option");
	  emptyOption.value = ' ';
	  emptyOption.textContent = '--- Select ---';
	  select.appendChild(emptyOption);

	  for (var i = 0; i < elmts.length; i++) {
        var optn = elmts[i];
        var el = document.createElement("option");
        el.textContent = optn;
        el.value = optn;
        select.appendChild(el);
        if (optn == defaultSelectItem) {
        	select.selectedIndex = i+1;
        }
    }
}

function selectItemFirstOption(select) {
	if (select.options.length >= 2) {
		select.selectedIndex = "1"
	}
}

//Long Butter Fly
function onAtmStrikePriceSelectionOfButterFly(selectItem, event) {
		try {
			
			alert('4 '+document.querySelector('input[name=tradeStrategy]:checked').value);
			
			var http = new XMLHttpRequest();
			var url = "/onAtmStrikePriceSelectionOfButterFly";
		var params = "strikePriceOfAtm="+selectItem.value
		+"&tradeSubStrategy="+document.getElementById('tradeSubStrategy').value
		+"&tradeStrategy="+document.querySelector('input[name=tradeStrategy]:checked').value
		+"&expiryDate="+document.getElementById('expiryDate').value
		+"&symbolId="+document.getElementById('symbolId').value;
		http.open('POST', url, true);

		//Send the proper header information along with the request
		http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

		http.onreadystatechange = function() {//Call a function when the state changes.
		    if(http.readyState == 4 && http.status == 200) {
		       // alert(JSON.parse(http.responseText)["higherOfAtmTradeType"]);
		        //addSelectItemOptions(document.getElementById('strikePriceHigherOfAtm'), JSON.parse(http.responseText)["protectionStrikePriceList"]);
		        
		        document.getElementById('atmTradeType').value = JSON.parse(http.responseText)["atmTradeType"];
		        document.getElementById('lowerOfAtmTradeType').value = JSON.parse(http.responseText)["lowerOfAtmTradeType"];
		        document.getElementById('higherOfAtmTradeType').value = JSON.parse(http.responseText)["higherOfAtmTradeType"];
		        document.getElementById('askPriceLowerOfAtm').value = JSON.parse(http.responseText)["askPriceLowerOfAtm"];
		        document.getElementById('bidPriceLowerOfAtm').value = JSON.parse(http.responseText)["bidPriceLowerOfAtm"]; 
		        document.getElementById('askPriceOfAtm').value = JSON.parse(http.responseText)["askPriceOfAtm"];
		        document.getElementById('bidPriceOfAtm').value = JSON.parse(http.responseText)["bidPriceOfAtm"]; 
		        document.getElementById('askPriceHigherOfAtm').value = JSON.parse(http.responseText)["askPriceHigherOfAtm"];
		        document.getElementById('bidPriceHigherOfAtm').value = JSON.parse(http.responseText)["bidPriceHigherOfAtm"]; 
		        addSelectItemOptions(document.getElementById('strikePriceLowerOfAtm'), JSON.parse(http.responseText)["strikePriceLowerOfAtm"]);
		        addSelectItemOptions(document.getElementById('strikePriceHigherOfAtm'), JSON.parse(http.responseText)["strikePriceHigherOfAtm"]);
		        selectItemFirstOption(document.getElementById('strikePriceLowerOfAtm'));
		        selectItemFirstOption(document.getElementById('strikePriceHigherOfAtm'));
		    }
		}
		http.send(params);
	} catch (e) {
		alert('Error - ' + e.message);
	}
}

function updateFormFieldsOnRatioSpread(httpPost) {
	document.getElementById('atmTradeType').value = JSON
			.parse(httpPost.responseText)["atmTradeType"];
	document.getElementById('higherOfAtmTradeType').value = JSON
			.parse(httpPost.responseText)["higherOfAtmTradeType"];
	document.getElementById('lowerOfAtmTradeType').value = JSON
			.parse(httpPost.responseText)["lowerOfAtmTradeType"];

	document.getElementById('askPriceOfAtm').value = JSON
			.parse(httpPost.responseText)["askPriceOfAtm"];
	document.getElementById('bidPriceOfAtm').value = JSON
			.parse(httpPost.responseText)["bidPriceOfAtm"];
	document.getElementById('askPriceHigherOfAtm').value = JSON
			.parse(httpPost.responseText)["askPriceHigherOfAtm"];
	document.getElementById('bidPriceHigherOfAtm').value = JSON
			.parse(httpPost.responseText)["bidPriceHigherOfAtm"];
	document.getElementById('askPriceLowerOfAtm').value = JSON
			.parse(httpPost.responseText)["askPriceLowerOfAtm"];
	document.getElementById('bidPriceLowerOfAtm').value = JSON
			.parse(httpPost.responseText)["bidPriceLowerOfAtm"];

	addSelectItemOptions(document.getElementById('strikePriceAtm'), JSON
			.parse(httpPost.responseText)["strikePriceOfAtm"]);
	addSelectItemOptions(document.getElementById('strikePriceHigherOfAtm'),
			JSON.parse(httpPost.responseText)["strikePriceHigherOfAtm"]);
	addSelectItemOptions(document.getElementById('strikePriceLowerOfAtm'), JSON
			.parse(httpPost.responseText)["strikePriceLowerOfAtm"]);
	selectItemFirstOption(document.getElementById('strikePriceAtm'));
	selectItemFirstOption(document.getElementById('strikePriceHigherOfAtm'));
	selectItemFirstOption(document.getElementById('strikePriceLowerOfAtm'));
}

function updateFormFieldsOnStraddle(httpPost) {
	document.getElementById('lowerOfAtmTradeType').value = JSON
			.parse(httpPost.responseText)["lowerOfAtmTradeType"];
	document.getElementById('higherOfAtmTradeType').value = JSON
			.parse(httpPost.responseText)["higherOfAtmTradeType"];
	document.getElementById('askPriceLowerOfAtm').value = JSON
			.parse(httpPost.responseText)["askPriceLowerOfAtm"];
	document.getElementById('bidPriceLowerOfAtm').value = JSON
			.parse(httpPost.responseText)["bidPriceLowerOfAtm"];
	document.getElementById('askPriceHigherOfAtm').value = JSON
			.parse(httpPost.responseText)["askPriceHigherOfAtm"];
	document.getElementById('bidPriceHigherOfAtm').value = JSON
			.parse(httpPost.responseText)["bidPriceHigherOfAtm"];

	addSelectItemOptions(document.getElementById('strikePriceHigherOfAtm'),
			JSON.parse(httpPost.responseText)["strikePriceHigherOfAtm"], JSON
					.parse(httpPost.responseText)["strikePriceOfAtm"]);
	addSelectItemOptions(document.getElementById('strikePriceLowerOfAtm'), JSON
			.parse(httpPost.responseText)["strikePriceLowerOfAtm"], JSON
			.parse(httpPost.responseText)["strikePriceOfAtm"]);
}