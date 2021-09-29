package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.UserBean;
import com.tradeware.clouddatabase.entity.UserEntity;
import com.tradeware.clouddatabase.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	private List<UserBean> convertEntityListToBeanList(List<UserEntity> listEntity) {
		List<UserBean> listBean = new ArrayList<UserBean>(listEntity.size());
		for (UserEntity entity : listEntity) {
			listBean.add(entity.populateBean());
		}
		return listBean;
	}
	
	@Override
	public List<UserBean> findAll() {
		return convertEntityListToBeanList(userRepository.findAll());
	}

	@Override
	public UserBean findByUserId(String userId) {
		UserBean userBean = null;
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (null != userEntity) {
			userBean = userEntity.populateBean();
		}
		return userBean;
	}
	
	@Override
	public UserBean findByEmail(String email) {
		UserBean userBean = null;
		UserEntity userEntity = userRepository.findByEmail(email);
		if (null != userEntity) {
			userBean = userEntity.populateBean();
		}
		return userBean;
	}
	
	@Override
	public UserBean findByConfirmationToken(String confirmationToken) {
		UserBean userBean = null;
		UserEntity userEntity = userRepository.findByConfirmationToken(confirmationToken);
		if (null != userEntity) {
			userBean = userEntity.populateBean();
		}
		return userBean;
	}

	@Override
	public UserBean save(UserBean user) {
		UserEntity userEntity = new UserEntity();
		userEntity.populateEntity(user);
		userEntity = userRepository.save(userEntity);
		userEntity.populateBean();
		return user;
	}
}
