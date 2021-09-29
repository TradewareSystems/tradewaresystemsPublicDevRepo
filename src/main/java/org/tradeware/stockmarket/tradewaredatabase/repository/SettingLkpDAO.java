package org.tradeware.stockmarket.tradewaredatabase.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.tradeware.stockmarket.tradewaredatabase.entity.SettingLkpEntity;

public interface SettingLkpDAO  extends CrudRepository<SettingLkpEntity, Integer> {

	List<SettingLkpEntity> findAll();
}
