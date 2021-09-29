package com.tradeware.clouddatabase.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tradeware.clouddatabase.entity.PositionalInfoEntity;

@Repository
public interface PositionalInfoRepository extends CrudRepository<PositionalInfoEntity, Integer> {
	
	public List<PositionalInfoEntity> findAllByDateStampOrderBySymbolId(Date dateStamp);
	
	@SuppressWarnings("unchecked")
	public PositionalInfoEntity save(PositionalInfoEntity symbol);
	
	//public Iterable<PositionalInfoEntity> saveAll(Iterable<PositionalInfoEntity> symbol);
}
