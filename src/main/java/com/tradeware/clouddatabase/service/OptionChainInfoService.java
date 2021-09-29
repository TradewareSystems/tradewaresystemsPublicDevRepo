package com.tradeware.clouddatabase.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.tradeware.clouddatabase.bean.OptionChainInfoBean;

public interface OptionChainInfoService {
	
	OptionChainInfoBean findBySymbolIdAndDateStampAndParentRecordIndTrue(String symbolId, Date dateStamp);
	OptionChainInfoBean findBySymbolIdAndDateStampAndParentRecordIndTrue(String symbolId);

	List<OptionChainInfoBean> findAllBySymbolIdAndDateStampOrderByDateTimeStampAsc(String symbolId, Date dateStamp);
	List<OptionChainInfoBean> findAllBySymbolIdAndDateStampGreaterThanEqualAndDateStampLessThanOrderByDateTimeStampDesc(String symbolId, Date dateStamp, Date dateStampMax);

	OptionChainInfoBean save(OptionChainInfoBean beans);

	List<OptionChainInfoBean> saveAll(List<OptionChainInfoBean> beans);

	List<OptionChainInfoBean> findTopByDateStampOrderByDateTimeStampStrongOrderDesc(Date dateStamp);

	List<OptionChainInfoBean> findAllByCandleNumberAndDateStampOrderByStrongOrderDesc(Integer candleNumber,
			Date dateStamp);

	List<OptionChainInfoBean> findAllByLatestOptinChainData(Date dateStamp);
	
	List<OptionChainInfoBean> findAllByLatestOptinChainDataForBankNifty(Date dateStamp);

	List<OptionChainInfoBean> findAllOptinChainDataByGivenDate(Date startDate, Date endDate);
	
	OptionChainInfoBean findSymbolByLatestOptinChainData(Date dateStamp, String symbolId);

	List<OptionChainInfoBean> findAllBestTradeOptinChainData(Date dateStamp);

	void updateBestTrade(Boolean bestTradeInd, Double ltpOnDecision, Double bestEntry, Double changePercentage,
			String goForTrade, Integer optionChainId, String symbolId);
	
	//List<OptionChainInfoBean> findAllByAttentionIndAndDateStamp(String orperator, Date dateStamp);
	
	List<OptionChainInfoBean> findAllByAttentionIndAndDateStampStrict(Date dateStamp);
	
	List<OptionChainInfoBean> findAllByAttentionIndAndDateStampPartial(Date dateStamp);
}
