package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.StrategyHighLowBean;
import com.tradeware.clouddatabase.entity.StrategyHighLowEntity;
import com.tradeware.clouddatabase.repository.StrategyHighLowRepository;

import hoghlow.tradeware.stockmarket.tool.HighLowStrategyTool;
@Service
public class StrategyHighLowServiceImpl implements StrategyHighLowService {

	@Autowired
	private StrategyHighLowRepository strategyHighLowRepository;
	
	@Override
	public List<StrategyHighLowEntity> findAll() {
		return strategyHighLowRepository.findAll();
	}


	@Override
	public StrategyHighLowEntity save(StrategyHighLowEntity symbol) {
		return strategyHighLowRepository.save(symbol);
	}
	
	@Override
	public List<StrategyHighLowEntity> saveAll(List<StrategyHighLowEntity> symbols) {
		return (List<StrategyHighLowEntity>) strategyHighLowRepository.saveAll(symbols);
	}

	@Override
	public List<StrategyHighLowBean> findAllTrades() {
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAll();
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> getRunningTrades() {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowEntity> list = strategyHighLowRepository.getRunningTrades(tradeDay);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> findAllByTradedStateStartingWithAndTradedDateAfterOrderByTradedAtDt(String tradedState)  {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByTradedStateStartingWithAndTradedDateOrderByTradedAtDt(tradedState, tradeDay);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> findAllByTradedStateStartingWithAndTradedDateOrderBySymbolTradedAtDt(
			String tradedState) {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByTradedStateStartingWithAndTradedDateOrderBySymbolAscTradedAtDtAsc(tradedState, tradeDay);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	//public List<StrategyHighLowBean> findAllByTradedStateLikeOrderByTradedAtDt(String tradedState) {
	public List<StrategyHighLowBean> findAllByTradedStateContainsAndTradedDateAfterOrderByTradedAtDt(String tradedState) {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByTradedStateContainsAndTradedDateOrderByTradedAtDt(tradedState, tradeDay);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> findAllByTradedStateContainsAndTradedDateOrderBySymbolTradedAtDt(
			String tradedState) {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByTradedStateContainsAndTradedDateOrderBySymbolAscTradedAtDtAsc(tradedState, tradeDay);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	//may not require
	@Override
	public List<StrategyHighLowBean> findAllByTradedStateOrderByTradedAtDt(String tradedState)  {
		List<StrategyHighLowEntity> list = strategyHighLowRepository. findAllByTradedStateOrderByTradedAtDt(tradedState);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> findAllByTradedStateOrderBySymbolTradedAtDt(String tradedState) {
		List<StrategyHighLowEntity> list = strategyHighLowRepository. findAllByTradedStateOrderBySymbolAscTradedAtDtAsc(tradedState);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
/*
	@Override
	public List<StrategyHighLowBean> findAllByTradeStrategyRule(String tradeStrategy) {
		List<StrategyHighLowEntity> list = dao.findAllByTradeStrategy(tradeStrategy);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	
	}*/

	@Override
	public List<StrategyHighLowBean> findAllByTradedDateAfter(Date tradedDate) {
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByTradedDate(tradedDate);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> findAllByTradedDateOrderBySymbolTradedAtDt(Date tradedDate) {
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByTradedDateOrderBySymbolAscTradedAtDtAsc(tradedDate);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> findAllTradesByTradedDate(Date tradedDate) {
		//return findAllByTradedDateAfter(tradeDay);
		return findAllByTradedDateOrderBySymbolTradedAtDt(tradedDate);
	}
	
	@Override
	public List<StrategyHighLowBean> findAllTradesByTradedDateAndTrendMactchWithNifty50(Date tradedDate) {
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllTradesByTradedDateAndTrendMactchWithNifty50OrderBySymbolAscTradedAtDtAsc(tradedDate);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> findAllTradesByTradedDateAndTrendMactchWithNifty50AndCloseRuleOrderBySymbolAscTradedAtDtAsc(Date tradedDate) {
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllTradesByTradedDateAndTrendMactchWithNifty50AndCloseRuleOrderBySymbolAscTradedAtDtAsc(tradedDate);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public StrategyHighLowBean saveTrade(StrategyHighLowBean bean) {
		StrategyHighLowEntity entity = new StrategyHighLowEntity();
		entity.populateEntity(bean);
		entity = strategyHighLowRepository.save(entity);
		entity.populateBean(bean);
		return bean;
	}
	
	@Override
	public void updateTrade(String tradedState, Double exitedAt, Date exitedAtDt, Double profitLossAmt,
			Double positiveMoveValue, Double negativeMoveValue, Integer itemId) {
		strategyHighLowRepository.updateTrade(tradedState, exitedAt, exitedAtDt, profitLossAmt, positiveMoveValue, negativeMoveValue, itemId);
	}
	
	@Override
	public List<StrategyHighLowEntity> saveAllTrades(List<StrategyHighLowBean> symbols) {
		List<StrategyHighLowEntity> entityList = new ArrayList<StrategyHighLowEntity>(symbols.size());
		for (StrategyHighLowBean bean : symbols) {
			StrategyHighLowEntity entity = new StrategyHighLowEntity();
			entity.populateEntity(bean);
			entityList.add(entity);
		}
		return (List<StrategyHighLowEntity>) strategyHighLowRepository.saveAll(entityList);
	}

	@Override
	public List<StrategyHighLowBean> findAllByTradedStateAndCloseRulesAndTradedDateAfterOrderByTradedAtDt(
			String tradedState) {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowEntity> list = strategyHighLowRepository
				.findAllByTradedStateAndCloseRulesAndTradedDateOrderByTradedAtDt(tradedState, tradeDay);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}	
	
	@Override
	public List<StrategyHighLowBean> findAllByCustomRuleSell() {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByCustomRuleSell(tradeDay);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> findAllByCustomRuleBuy()  {
		Date tradeDay = HighLowStrategyTool.getInstance().getTradeDateForReport();
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByCustomRuleBuy(tradeDay);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	
	//reports
	@Override
	public List<StrategyHighLowBean> findAllByNr4RuleAndTradedDateOrderBySymbolAscTradedAtDtAsc(boolean nr4RuleInd, Date tradedDate)  {
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByNr4RuleAndTradedDateOrderBySymbolAscTradedAtDtAsc(nr4RuleInd, tradedDate);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> findAllByNr4RuleAndTradedDateGreaterThanEqualOrderBySymbolAscTradedAtDtAsc(boolean nr4RuleInd, Date tradedDate)  {
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByNr4RuleAndTradedDateGreaterThanEqualOrderBySymbolAscTradedAtDtAsc(nr4RuleInd, tradedDate);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> findAllByTradedStateAndCPROrderBySymbolAscTradedAtDtAsc(String tradedState, Date tradedDate){
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByTradedStateAndCPROrderBySymbolAscTradedAtDtAsc(tradedState, tradedDate);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> findAllByTradedStateAndNotCPROrderBySymbolAscTradedAtDtAsc(String tradedState, Date tradedDate){
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByTradedStateAndNotCPROrderBySymbolAscTradedAtDtAsc(tradedState, tradedDate);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	
	@Override
	public List<StrategyHighLowBean> findAllByTradedStateAndCPROAndCloserderBySymbolAscTradedAtDtAsc(String tradedState, Date tradedDate){
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByTradedStateAndCPROAndCloserderBySymbolAscTradedAtDtAsc(tradedState, tradedDate);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
	@Override
	public List<StrategyHighLowBean> findAllByTradedStateAndNotCPRAndCloseOrderBySymbolAscTradedAtDtAsc(String tradedState, Date tradedDate){
		List<StrategyHighLowEntity> list = strategyHighLowRepository.findAllByTradedStateAndNotCPRAndCloseOrderBySymbolAscTradedAtDtAsc(tradedState, tradedDate);
		List<StrategyHighLowBean> listBean = new ArrayList<StrategyHighLowBean>(list.size());
		for (StrategyHighLowEntity entity : list) {
			StrategyHighLowBean bean = new StrategyHighLowBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
}