package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.OptionChainInfoBean;
import com.tradeware.clouddatabase.entity.OptionChainInfoEntity;
import com.tradeware.clouddatabase.repository.OptionChainInfoRepository;

@Service
public class OptionChainInfoServiceImpl implements OptionChainInfoService {

	@Autowired
	private OptionChainInfoRepository repository;

	@SuppressWarnings("unused")
	private List<OptionChainInfoBean> convertEntityListToBeanList(List<OptionChainInfoEntity> listEntity) {
		List<OptionChainInfoBean> listBean = new ArrayList<OptionChainInfoBean>(listEntity.size());
		for (OptionChainInfoEntity entity : listEntity) {
			listBean.add(entity.populateBean());
		}
		return listBean;
	}

	private Iterable<OptionChainInfoEntity> convertBeanListToEntityList(List<OptionChainInfoBean> listBean) {
		List<OptionChainInfoEntity> listEntity = new ArrayList<OptionChainInfoEntity>(listBean.size());
		for (OptionChainInfoBean bean : listBean) {
			OptionChainInfoEntity entity = new OptionChainInfoEntity();
			entity.populateEntity(bean);
			listEntity.add(entity);
		}
		return listEntity;
	}

	@Override
	public OptionChainInfoBean findBySymbolIdAndDateStampAndParentRecordIndTrue(String symbolId, Date dateStamp) {
		OptionChainInfoEntity entity = repository.findBySymbolIdAndDateStampAndParentRecordIndTrue(symbolId, dateStamp);
		return entity.populateBean();
	}
	
	@Override
	public OptionChainInfoBean findBySymbolIdAndDateStampAndParentRecordIndTrue(String symbolId) {
		OptionChainInfoEntity entity = repository.findBySymbolIdAndDateStampAndParentRecordIndTrue(symbolId);
		return entity.populateBean();
	}
	
	@Override
	public List<OptionChainInfoBean> findAllBySymbolIdAndDateStampOrderByDateTimeStampAsc(String symbolId,
			Date dateStamp) {
		/*return convertEntityListToBeanList(
				repository.findAllBySymbolIdAndDateStampGreaterThanEqualOrderByDateTimeStampDesc(symbolId, dateStamp));
*/				
		return findAllBySymbolIdAndDateStampGreaterThanEqualAndDateStampLessThanOrderByDateTimeStampDesc(
						symbolId, dateStamp, new Date(dateStamp.getTime()+(24*60*60*1000)));
				
	}

	@Override
	public List<OptionChainInfoBean> findAllBySymbolIdAndDateStampGreaterThanEqualAndDateStampLessThanOrderByDateTimeStampDesc(
			String symbolId, Date dateStamp, Date dateStampMax) {
		return convertEntityListToBeanList(
				repository.findAllBySymbolIdAndDateStampGreaterThanEqualAndDateStampLessThanOrderByDateTimeStampDesc(
						symbolId, dateStamp, dateStampMax));
	}


	@Override
	public List<OptionChainInfoBean> findAllByCandleNumberAndDateStampOrderByStrongOrderDesc(Integer candleNumber,
			Date dateStamp) {
		return convertEntityListToBeanList(
				repository.findAllByCandleNumberAndDateStampOrderByIndexIndDescOiTrendDescStrongOrderDesc(candleNumber,
						dateStamp));
	}

	@Override
	public List<OptionChainInfoBean> findTopByDateStampOrderByDateTimeStampStrongOrderDesc(Date dateStamp) {
		return convertEntityListToBeanList(
				repository.findTopByDateStampOrderByDateTimeStampDescStrongOrderDesc(dateStamp));
	}

	@Override
	public List<OptionChainInfoBean> findAllByLatestOptinChainData(Date dateStamp) {
		return convertEntityListToBeanList(repository.findAllByLatestOptinChainData(dateStamp));
	}
	
	@Override
	public List<OptionChainInfoBean> findAllByLatestOptinChainDataForBankNifty(Date dateStamp) {
		return convertEntityListToBeanList(repository.findAllByLatestOptinChainDataForBankNifty(dateStamp));
	}
	
	@Override
	public List<OptionChainInfoBean> findAllOptinChainDataByGivenDate(Date startDate, Date endDate) {
		return convertEntityListToBeanList(repository.findAllOptinChainDataByGivenDate(startDate, endDate));
	}
	
	@Override
	public OptionChainInfoBean findSymbolByLatestOptinChainData(Date dateStamp, String symbolId) {
		//return repository.findSymbolByLatestOptinChainData(dateStamp, symbolId).populateBean();
		return repository.findSymbolByLatestOptinChainData(symbolId).populateBean();
	}

	@Override
	public OptionChainInfoBean save(OptionChainInfoBean bean) {
		OptionChainInfoEntity entity = new OptionChainInfoEntity();
		entity.populateEntity(bean);
		repository.save(entity);
		return entity.populateBean();
	}

	@Override
	public List<OptionChainInfoBean> saveAll(List<OptionChainInfoBean> beans) {
		Iterable<OptionChainInfoEntity> result = repository.saveAll(convertBeanListToEntityList(beans));
		List<OptionChainInfoBean> resultList = new ArrayList<OptionChainInfoBean>();
		for (OptionChainInfoEntity entity : result) {
			resultList.add(entity.populateBean());
		}
		return resultList;
	}

	@Override
	public List<OptionChainInfoBean> findAllBestTradeOptinChainData(Date dateStamp) {
		return convertEntityListToBeanList(repository.findAllBestTradeOptinChainData(dateStamp));
	}

	@Override
	public void updateBestTrade(Boolean bestTradeInd, Double ltpOnDecision, Double bestEntry, Double changePercentage,
			String goForTrade, Integer optionChainId, String symbolId) {
		repository.updateBestTrade(bestTradeInd, ltpOnDecision, bestEntry, changePercentage, goForTrade, optionChainId/*, symbolId*/);
	}

	//Attention trades
	/*@Override
	public List<OptionChainInfoBean> findAllByAttentionIndAndDateStamp(String orperator, Date dateStamp) {
		return convertEntityListToBeanList(repository.findAllByAttentionIndAndDateStamp(orperator, dateStamp,
				new Date(dateStamp.getTime() + (24 * 60 * 60 * 1000))));

	}*/
	
	@Override
	public List<OptionChainInfoBean> findAllByAttentionIndAndDateStampStrict(Date dateStamp) {
		return convertEntityListToBeanList(repository.findAllByAttentionIndAndDateStampStrict(dateStamp,
				new Date(dateStamp.getTime() + (24 * 60 * 60 * 1000))));

	}

	@Override
	public List<OptionChainInfoBean> findAllByAttentionIndAndDateStampPartial(Date dateStamp) {
		return convertEntityListToBeanList(repository.findAllByAttentionIndAndDateStampPartial(dateStamp,
				new Date(dateStamp.getTime() + (24 * 60 * 60 * 1000))));
	}
	
}
