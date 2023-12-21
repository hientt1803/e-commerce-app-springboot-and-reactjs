package com.poly.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.Account;

public interface AccountDAO extends JpaRepository<Account, String> {
	public Page<Account> findByFullnameLike(String keyword, Pageable page);

//	@Query("SELECT DISTINCT ar.account FROM Authority ar WHERE ar.role.id_role IN('ADMIN')")
//	List<Account> getAdministrators();

	@Query("SELECT a FROM Account a WHERE a.username =?1 and a.password=?2")
	Account getAccount(String username, String password);

	// Phuc vu viec gui mail
	@Query("SELECT a FROM Account a WHERE a.email=?1")
	public Account findByEmail(String email);

	@Query("SELECT a FROM Account a WHERE a.token=?1")
	public Account findByToken(String token);

	@Query("SELECT u FROM Account u WHERE u.username = ?1")
	Account findByUsername(String username);

	@Query("SELECT u FROM Account u")
	List<Account> getAll();

//	@Query(value = "SELECT count(a.username) FROM Accounts a", nativeQuery = true)
//	Integer countAllAccount();

}
