package com.tradeware.clouddatabase.service;

import java.util.List;

import com.tradeware.clouddatabase.bean.VolumeTrendLookupBean;

public interface VolumeTrendLookupService {

	public List<VolumeTrendLookupBean> findAllOrderByVolumeTrendDescription();

	public VolumeTrendLookupBean findByVolumeTrendId(String volumeTrendId);

	public VolumeTrendLookupBean save(VolumeTrendLookupBean volumeTrendLookupBean);
}
