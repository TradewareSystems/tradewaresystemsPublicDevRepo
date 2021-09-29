package com.tradeware.clouddatabase.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tradeware.stockmarket.util.DatabaseHelper;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OptionChainUpdateScannerThread implements Runnable {
	@Autowired
	private NSEIndiaToolOptionChainTool nSEIndiaTool;

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		long entry = 0;
		NSEIndiaToolOptionChainTool.totalSymbolsProcessed = 0;
		nSEIndiaTool.retrieveTopGainerLooserData(null);
		nSEIndiaTool.refreshOIDataForAll(false);

		long exit = System.currentTimeMillis();
		DatabaseHelper.getInstance()
				.activityOptionChainDataProcess((threadName + "@ Total time " + (exit - entry) / 1000)
						+ ("; SymbolsProcessed:" + NSEIndiaToolOptionChainTool.totalSymbolsProcessed));
	}

}
