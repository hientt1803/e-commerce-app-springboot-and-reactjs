package com.poly.repository;

import com.poly.model.Account;
import com.poly.model.Order;
import com.poly.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Long> {
	@Query("SELECT o FROM Order o WHERE  o.status != 'H' ")
	Page<Order> findOrderActive(Pageable pageable);

	// DSL
	List<Order> findAllByStatusNotLike(String key, Pageable pageable);

	List<Order> findAllByStatusNotLikeAndAccount_Username(String key, String username);

	// List<Order> findByStatus(String key);
	@Query("SELECT o.account FROM Order o WHERE o.id = ?1")
	Account findOrderById(Long id);

	@Query("SELECT o FROM Order o WHERE  MONTH(o.createDate) = ?1 and YEAR(o.createDate) = ?2 and o.status != 'H'  ")
	Page<Order> findAllByCreateddateMonth(int month, int year, Pageable pageable);

	@Query("SELECT o FROM Order o WHERE  DAY(o.createDate) = ?1 and  MONTH(o.createDate) = ?2 and YEAR(o.createDate) = ?3 and o.status != 'H'  ")
	Page<Order> findAllByCreateddateDate(int date, int month, int year, Pageable pageable);

	Order findByAccountUsername(String uname);

	@Query("SELECT c FROM Order c WHERE c.account.username = ?1")
	List<Order> findByAccountName(String name);

	@Query("SELECT c FROM Order c WHERE c.createDate = ?1")
	List<Order> findByCreatedDate(Date ngayDat);

	@Query("SELECT c FROM Order c WHERE c.id LIKE ?1")
	List<Order> findByID(Long id);

	@Query("SELECT c FROM Order c WHERE c.status = ?1 AND c.account.username = ?2")
	List<Order> findByStatusAndUser(String status, String username);

	@Query("SELECT c FROM Order c WHERE c.status = ?1")
	List<Order> findByStatus(String status);

	// SELECT All Doanh thu
	@Query("SELECT SUM(c.totalPrice) FROM Order c")
	Double getTotalRevenue();

	// SELECT revenue of 1 month past
	@Query("SELECT SUM(c.totalPrice) FROM Order c WHERE c.createDate BETWEEN :startDate AND :endDate")
	Double getTotalRevenueByMonth(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	// SELECT ALL PRODUCT SOLD IN DAY
	@Query("SELECT COUNT(op) FROM Order o JOIN o.orderDetails.product op WHERE o.createDate BETWEEN :startDate AND :endDate")
	Long countProductsSoldByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	// SELECT TOp newest 10 Product havebeen sold
	@Query("SELECT DISTINCT p FROM Product p JOIN FETCH p.orderDetails od "
			+ "JOIN FETCH od.order ORDER BY od.order.createDate DESC")
	List<Product> findLastestOrder(PageRequest of);

	// get turnover
	@Query("SELECT sum(o.totalPrice) FROM Order o WHERE o.createDate = ?1 AND o.status = 'DG'")
	Double getTurnoverByDay(Date today);

	@Query("SELECT sum(o.totalPrice) FROM Order o WHERE MONTH(o.createDate) = MONTH(?1) AND YEAR(o.createDate) = YEAR(?1)  AND o.status = 'DG'")
	Double getTurnoverByMonth(Date today);

	@Query("SELECT sum(o.totalPrice) FROM Order o WHERE YEAR(o.createDate) = YEAR(?1)  AND o.status = 'DG'")
	Double getTurnoverByYear(Date today);
}
