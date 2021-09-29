package com.tradeware.clouddatabase.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tradeware.clouddatabase.entity.SymbolEntity;

@Repository
public interface SymbolRepository extends CrudRepository<SymbolEntity, String> {

	List<SymbolEntity> findAll();
	
	List<SymbolEntity> findAllByOrderByClose200SmaTradableRatioAsc();
	
	List<SymbolEntity> findAllByOrderByYrHiLoNearPerDesc();
	
	List<SymbolEntity> findAllByOrderByValidIndAscFnoIndAscCategoryFilterAscYrHiLoNearPerAsc();
	
	SymbolEntity findBySymbolId(String symbolId);

	List<SymbolEntity> findAllByValidIndOrderBySymbolId(Boolean validInd);

	List<SymbolEntity> findAllByValidIndAndIndexIndOrderBySymbolId(Boolean validInd, Boolean indexInd);

	@SuppressWarnings("unchecked")
	SymbolEntity save(SymbolEntity symbol);

	//List<SymbolEntity> saveAll(Iterable<SymbolEntity> symbolList);
	
	List<SymbolEntity>  findAllByFnoIndAndValidIndOrderBySymbolId(Boolean fnoInd, Boolean validInd);
	
	List<SymbolEntity> findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId(Boolean fnoInd, Boolean validInd, Integer categoryFilter);
}
