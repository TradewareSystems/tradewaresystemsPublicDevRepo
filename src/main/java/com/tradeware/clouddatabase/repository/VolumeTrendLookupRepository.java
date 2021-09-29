package com.tradeware.clouddatabase.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tradeware.clouddatabase.entity.VolumeTrendLookupEntity;

@Repository
public interface VolumeTrendLookupRepository extends CrudRepository<VolumeTrendLookupEntity, String> {
	public List<VolumeTrendLookupEntity> findAll();
	//public List<VolumeTrendLookupEntity> findAllOrderByVolumeTrendDescription();

	public VolumeTrendLookupEntity findByVolumeTrendId(String volumeTrendId);

	@SuppressWarnings("unchecked")
	public VolumeTrendLookupEntity save(VolumeTrendLookupEntity volumeTrendLookupBean);
}