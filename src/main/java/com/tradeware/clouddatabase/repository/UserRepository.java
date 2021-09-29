package com.tradeware.clouddatabase.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tradeware.clouddatabase.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

	List<UserEntity> findAll();

	UserEntity findByUserId(String userId);

	UserEntity findByEmail(String email);

	UserEntity findByConfirmationToken(String confirmationToken);

	@SuppressWarnings("unchecked")
	UserEntity save(UserEntity user);
}
