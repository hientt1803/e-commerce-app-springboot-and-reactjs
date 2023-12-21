package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poly.model.Account;
import com.poly.repository.AccountDAO;
import com.poly.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	AccountDAO accountDAO;

	@Autowired
	AccountDAO adao;

//	@Autowired
//	PasswordEncoder pe;

	@Override
	public Optional<Account> findById(String username) {
		return adao.findById(username);
	}

	@Override
	public List<Account> findAll() {
		return adao.getAll();
	}

//	@Override
//	public List<Account> getAdministrators() {
//		return adao.getAdministrators();
//	}

	@Override
	public Account create(Account account) {
		return adao.save(account);
	}

	@Override
	public Account update(Account account) {
		return accountDAO.save(account);
	}

	@Override
	public void delete(String username) {
		adao.deleteById(username);
	}

//	@Override
//	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) {
//		// String fullname = oauth2.getPrincipal().getAttribute("name");
//		String email = oauth2.getPrincipal().getAttribute("email");
//		String password = Long.toHexString(System.currentTimeMillis());
//
//		UserDetails user = User.withUsername(email).password(pe.encode(password)).roles("CUST").build();
//		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//		SecurityContextHolder.getContext().setAuthentication(auth);
//	}

	@Override
	public void updateToken(String token, String email) throws Exception {
		Account entity = adao.findByEmail(email);
		if (entity != null) {
			entity.setToken(token);
			adao.save(entity);
		} else {
			throw new Exception("Cannot find any account with email: " + email);
		}
	}

	@Override
	public Account getByToken(String token) {
		return adao.findByToken(token);
	}

	@Override
	public void updatePassword(Account entity, String newPassword) {
		entity.setPassword(newPassword);
		entity.setToken(null);
		adao.save(entity);
	}

	@Override
	public void changePassword(Account entity, String newPassword) {
		entity.setPassword(newPassword);
		adao.save(entity);
	}
}
