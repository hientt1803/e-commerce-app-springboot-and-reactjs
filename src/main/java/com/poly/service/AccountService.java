package com.poly.service;

import java.util.List;
import java.util.Optional;

import com.poly.model.Account;

public interface AccountService {
 
	Optional<Account> findById(String username);

	List<Account> findAll();

//	List<Account> getAdministrators();

	Account create(Account account);

	Account update(Account account);

	void delete(String username);

	void updateToken(String token, String email) throws Exception;

	Account getByToken(String token);

	void updatePassword(Account entity, String newPassword);

	void changePassword(Account entity, String newPassword);
}