package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.SymbolBean;
import com.tradeware.clouddatabase.entity.SymbolEntity;
import com.tradeware.clouddatabase.repository.SymbolRepository;

@Service
public class SymbolServiceImpl implements SymbolService {

	@Autowired
	private SymbolRepository symbolRepository;

	private List<SymbolBean> convertEntityListToBeanList(List<SymbolEntity> listEntity) {
		List<SymbolBean> listBean = new ArrayList<SymbolBean>(listEntity.size());
		for (SymbolEntity entity : listEntity) {
			listBean.add(entity.populateBean());
		}
		return listBean;
	}

	private List<SymbolEntity> convertBeanListToEntityList(List<SymbolBean> listBean) {
		List<SymbolEntity> listEntity = new ArrayList<SymbolEntity>(listBean.size());
		for (SymbolBean bean : listBean) {
			SymbolEntity entity = new SymbolEntity();
			entity.populateEntity(bean);
			listEntity.add(entity);
		}
		return listEntity;
	}

	@Override
	public SymbolBean findBySymbolId(String symbolId) {
		SymbolEntity entity = symbolRepository.findBySymbolId(symbolId);
		return entity.populateBean();
	}

	@Override
	public List<SymbolBean> findAll() {
		return convertEntityListToBeanList(symbolRepository.findAll());
	}
	
	@Override
	public List<SymbolBean> findAllOrderByClose200SmaTradableRatio() {
		return convertEntityListToBeanList(symbolRepository.findAllByOrderByClose200SmaTradableRatioAsc());
	}

	@Override
	public List<SymbolBean> findAllByValidIndOrderBySymbolId(Boolean validInd) {
		return convertEntityListToBeanList(symbolRepository.findAllByValidIndOrderBySymbolId(validInd));
	}
	
	@Override
	public List<SymbolBean> findAllOrderByYrHiLoNearPerDesc() {
		return convertEntityListToBeanList(symbolRepository.findAllByOrderByValidIndAscFnoIndAscCategoryFilterAscYrHiLoNearPerAsc());
	}

	@Override
	public List<SymbolBean> findAllByValidIndAndIndexIndOrderBySymbolId(Boolean validInd, Boolean indexInd) {
		return convertEntityListToBeanList(
				symbolRepository.findAllByValidIndAndIndexIndOrderBySymbolId(validInd, indexInd));
	}

	@Override
	public SymbolBean save(SymbolBean symbol) {
		SymbolEntity symbolEntity = new SymbolEntity();
		symbolEntity.populateEntity(symbol);
		symbolEntity = symbolRepository.save(symbolEntity);
		return symbolEntity.populateBean();
	}

	@Override
	public void saveAll(List<SymbolBean> symbolList) {
		symbolRepository.saveAll(convertBeanListToEntityList(symbolList));
	}
	
	public List<SymbolBean> findAllByFnoIndAndValidIndOrderBySymbolId(Boolean fnoInd, Boolean validInd) {
		return convertEntityListToBeanList(symbolRepository
				.findAllByFnoIndAndValidIndOrderBySymbolId(fnoInd, validInd));
	}
	
	public List<SymbolBean> findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId(Boolean fnoInd, Boolean validInd,
			Integer categoryFilter) {
		return convertEntityListToBeanList(symbolRepository
				.findAllByFnoIndAndValidIndAndCategoryFilterOrderBySymbolId(fnoInd, validInd, categoryFilter));
	}
}
