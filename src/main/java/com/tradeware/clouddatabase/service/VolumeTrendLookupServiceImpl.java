package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.VolumeTrendLookupBean;
import com.tradeware.clouddatabase.entity.VolumeTrendLookupEntity;
import com.tradeware.clouddatabase.repository.VolumeTrendLookupRepository;
@Service
public class VolumeTrendLookupServiceImpl implements  VolumeTrendLookupService {

	@Autowired
	private VolumeTrendLookupRepository volumeTrendLookupRepository;
	
	private List<VolumeTrendLookupBean> convertEntityListToBeanList(List< VolumeTrendLookupEntity> listEntity) {
		List<VolumeTrendLookupBean> listBean = new ArrayList<VolumeTrendLookupBean>(listEntity.size());
		for (VolumeTrendLookupEntity entity : listEntity) {
			listBean.add(entity.populateBean());
		}
		return listBean;
	}
	
	public List<VolumeTrendLookupBean> findAllOrderByVolumeTrendDescription(){
		//return convertEntityListToBeanList(volumeTrendLookupRepository.findAllOrderByVolumeTrendDescription() );
		return convertEntityListToBeanList(volumeTrendLookupRepository.findAll());
	}

	public VolumeTrendLookupBean findByVolumeTrendId(String volumeTrendId){
		VolumeTrendLookupEntity entity = volumeTrendLookupRepository.findByVolumeTrendId(volumeTrendId);
		return entity.populateBean();
	}

	public VolumeTrendLookupBean save(VolumeTrendLookupBean volumeTrendLookupBean){
		VolumeTrendLookupEntity volumeTrendLookupEntity = new VolumeTrendLookupEntity();
		volumeTrendLookupEntity.populateEntity(volumeTrendLookupBean);
		volumeTrendLookupEntity = volumeTrendLookupRepository.save(volumeTrendLookupEntity);
		return volumeTrendLookupEntity.populateBean();
	}
}
