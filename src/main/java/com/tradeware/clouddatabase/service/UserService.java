package com.tradeware.clouddatabase.service;

import java.util.List;

import com.tradeware.clouddatabase.bean.UserBean;

public interface UserService {

	List<UserBean> findAll();

	UserBean findByUserId(String userId);

	UserBean findByEmail(String email);

	UserBean findByConfirmationToken(String confirmationToken);

	UserBean save(UserBean user);
}
