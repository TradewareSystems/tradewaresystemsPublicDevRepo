package com.tradeware.clouddatabase.service;

import java.util.List;

import com.tradeware.clouddatabase.bean.SymbolBean;

public interface SymbolService {
	
	List<SymbolBean> findAll();
	
	List<SymbolBean> findAllOrderByClose200SmaTradableRatio();
	
	SymbolBean findBySymbolId(String symbolId);

	List<SymbolBean> findAllOrderByYrHiLoNearPerDesc();
	
	List<SymbolBean> findAllByValidIndOrderBySymbolId(Boolean validInd);
	
	List<SymbolBean> findAllByValidIndAndIndexIndOrderBySymbolId(Boolean validInd, Boolean indexInd);

	SymbolBean save(SymbolBean symbol);
	
	void saveAll(List<SymbolBean> symbolList);
	
	List<SymbolBean> findAllByFnoIndAndValidIndOrderBySymbolId(Boolean fnoInd, Boolean validInd);
	
	List<SymbolBean> findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId(Boolean fnoInd, Boolean validInd, Integer categoryFilter);
}
