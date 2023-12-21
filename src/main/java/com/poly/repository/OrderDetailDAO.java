package com.poly.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.poly.model.OrderDetail;
import com.poly.model.ReportByCategory;
import com.poly.model.ReportByProduct;
import com.poly.model.ReportByUser;
import com.poly.model.ReportTop10;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {
	@Query("SELECT   new ReportTop10( o.product.name, o.product.category.name , sum(o.price * o.quantity), sum(o.quantity)) FROM OrderDetail o WHERE o.order.status = 'DG' GROUP BY o.product.name, o.product.category.name ORDER BY sum(o.price * o.quantity) DESC")
	public List<ReportTop10> getTopProduct();

	@Query("SELECT   new ReportTop10( o.product.name, o.product.category.name , sum(o.price * o.quantity), sum(o.quantity)) FROM OrderDetail o WHERE DAY(o.order.createDate) = ?1 and  MONTH(o.order.createDate) = ?2 and YEAR(o.order.createDate) = ?3 AND o.order.status = 'DG' GROUP BY o.product.name, o.product.category.name ORDER BY sum(o.price * o.quantity) DESC")
	public List<ReportTop10> getTopProductByDate(int date, int month, int year);

	@Query("SELECT   new ReportTop10( o.product.name, o.product.category.name , sum(o.price * o.quantity), sum(o.quantity)) FROM OrderDetail o WHERE   WEEK(o.order.createDate) = ?1 and YEAR(o.order.createDate) = ?2 AND o.order.status = 'DG' GROUP BY o.product.name, o.product.category.name")
	public List<ReportTop10> getTopProductByWeek(int week, int year);

	@Query("SELECT   new ReportTop10( o.product.name, o.product.category.name , sum(o.price * o.quantity), sum(o.quantity)) FROM OrderDetail o WHERE   MONTH(o.order.createDate) = ?1 and YEAR(o.order.createDate) = ?2 AND o.order.status = 'DG' GROUP BY o.product.name, o.product.category.name")
	public List<ReportTop10> getTopProductByMonth(int month, int year);

	@Query("SELECT   new ReportTop10( o.product.name, o.product.category.name , sum(o.price * o.quantity), sum(o.quantity)) FROM OrderDetail o WHERE YEAR(o.order.createDate) = ?1 AND o.order.status = 'DG' GROUP BY o.product.name, o.product.category.name")
	public List<ReportTop10> getTopProductByYear(int year);

	// getReportByCategories
	@Query("SELECT  new ReportByCategory( o.product.category.name , sum(o.quantity), sum(o.price * o.quantity)) FROM OrderDetail o WHERE o.order.status = 'DG' GROUP BY  o.product.category.name ORDER BY sum(o.price * o.quantity) DESC")
	public List<ReportByCategory> getReportByCategories();

	@Query("SELECT  new ReportByCategory( o.product.category.name , sum(o.quantity), sum(o.price * o.quantity)) FROM OrderDetail o WHERE (o.order.createDate BETWEEN  ?1 AND ?2) AND o.order.status = 'DG' GROUP BY  o.product.category.name ORDER BY sum(o.price * o.quantity) DESC")
	public List<ReportByCategory> getReportByCategoriesByDate(Date startDate, Date endDate);

	@Query("SELECT  new ReportByCategory( o.product.category.name , sum(o.quantity), sum(o.price * o.quantity)) FROM OrderDetail o WHERE DAY(o.order.createDate) = ?1 and  MONTH(o.order.createDate) = ?2 and YEAR(o.order.createDate) = ?3 AND o.order.status = 'DG' GROUP BY  o.product.category.name")
	public List<ReportByCategory> getReportByCategoriesByDate(int date, int month, int year);

	@Query("SELECT  new ReportByCategory( o.product.category.name , sum(o.quantity), sum(o.price * o.quantity)) FROM OrderDetail o WHERE WEEK(o.order.createDate) = ?1 and YEAR(o.order.createDate) = ?2 AND o.order.status = 'DG'  GROUP BY  o.product.category.name")
	public List<ReportByCategory> getReportByCategoriesByWeek(int week, int year);

	@Query("SELECT  new ReportByCategory( o.product.category.name , sum(o.quantity), sum(o.price * o.quantity)) FROM OrderDetail o WHERE MONTH(o.order.createDate) = ?1 and YEAR(o.order.createDate) = ?2 AND o.order.status = 'DG' GROUP BY  o.product.category.name")
	public List<ReportByCategory> getReportByCategoriesByMonth(int month, int year);

	@Query("SELECT  new ReportByCategory( o.product.category.name , sum(o.quantity), sum(o.price * o.quantity)) FROM OrderDetail o WHERE YEAR(o.order.createDate) = ?1 AND o.order.status = 'DG' GROUP BY  o.product.category.name")
	public List<ReportByCategory> getReportByCategoriesByYear(int year);

	// getReportByProducts
	@Query("SELECT  new ReportByProduct( o.product.name ,  o.product.category.name, sum(o.quantity), sum(o.price * o.quantity))  FROM OrderDetail o WHERE o.order.status = 'DG' GROUP BY  o.product.name,  o.product.category.name ORDER BY sum(o.price * o.quantity) DESC")
	public List<ReportByProduct> getReportByProducts();

	@Query("SELECT  new ReportByProduct( o.product.name ,  o.product.category.name, sum(o.quantity), sum(o.price * o.quantity))  FROM OrderDetail o  WHERE (o.order.createDate BETWEEN  ?1 AND ?2) AND o.order.status = 'DG' GROUP BY  o.product.name,  o.product.category.name ORDER BY sum(o.price * o.quantity) DESC")
	public List<ReportByProduct> getReportByProductsByDate(Date startDate, Date endDate);

	@Query("SELECT  new ReportByProduct( o.product.name ,  o.product.category.name, sum(o.quantity), sum(o.price * o.quantity)) FROM OrderDetail o WHERE DAY(o.order.createDate) = ?1 and  MONTH(o.order.createDate) = ?2 and YEAR(o.order.createDate) = ?3 AND o.order.status = 'DG' GROUP BY  o.product.name,  o.product.category.name ORDER BY sum(o.price * o.quantity) DESC")
	public List<ReportByProduct> getReportByProductsByDate(int date, int month, int year);

	@Query("SELECT  new ReportByProduct( o.product.name ,  o.product.category.name, sum(o.quantity), sum(o.price * o.quantity)) FROM OrderDetail o WHERE WEEK(o.order.createDate) = ?1 and YEAR(o.order.createDate) = ?2 AND o.order.status = 'DG' GROUP BY  o.product.name,  o.product.category.name ORDER BY sum(o.price * o.quantity) DESC")
	public List<ReportByProduct> getReportByProductsByWeek(int week, int year);

	@Query("SELECT  new ReportByProduct( o.product.name ,  o.product.category.name, sum(o.quantity), sum(o.price * o.quantity)) FROM OrderDetail o WHERE MONTH(o.order.createDate) = ?1 and YEAR(o.order.createDate) = ?2 AND o.order.status = 'DG' GROUP BY  o.product.name,  o.product.category.name ORDER BY sum(o.price * o.quantity) DESC")
	public List<ReportByProduct> getReportByProductsByMonth(int month, int year);

	@Query("SELECT  new ReportByProduct( o.product.name ,  o.product.category.name, sum(o.quantity), sum(o.price * o.quantity)) FROM OrderDetail o WHERE YEAR(o.order.createDate) = ?1 AND o.order.status = 'DG' GROUP BY  o.product.name,  o.product.category.name ORDER BY sum(o.price * o.quantity) DESC")
	public List<ReportByProduct> getReportByProductsByYear(int year);

	// getReportByUsers
	@Query("SELECT  new ReportByUser( o.account.fullname ,  o.address, count(o.id), sum(o.totalPrice)) FROM Order o WHERE o.status = 'DG' GROUP BY    o.account.fullname ,  o.address ORDER BY sum(o.totalPrice)")
	public List<ReportByUser> getReportByUsers();

	@Query("SELECT  new ReportByUser( o.account.fullname ,  o.address, count(o.account.username), sum(o.totalPrice)) FROM Order o WHERE DAY(o.createDate) = ?1 and  MONTH(o.createDate) = ?2 and YEAR(o.createDate) = ?3 AND  o.status = 'DG' GROUP BY   o.account.fullname ,  o.address ORDER BY sum(o.totalPrice) DESC")
	public List<ReportByUser> getReportByUsersByDate(int date, int month, int year);

	@Query("SELECT  new ReportByUser( o.account.fullname ,  o.address, count(o.account.username), sum(o.totalPrice)) FROM Order o WHERE (o.createDate BETWEEN  ?1 AND ?2)  AND  o.status = 'DG' GROUP BY   o.account.fullname ,  o.address ORDER BY sum(o.totalPrice) DESC")
	public List<ReportByUser> getReportByUsersByDate(Date startDate, Date endDate);

	@Query("SELECT  new ReportByUser( o.account.fullname ,  o.address, count(o.account.username), sum(o.totalPrice)) FROM Order o  WHERE WEEK(o.createDate) = ?1 and YEAR(o.createDate) = ?2 AND o.status = 'DG' GROUP BY   o.account.fullname ,  o.address ORDER BY sum(o.totalPrice) DESC")
	public List<ReportByUser> getReportByUsersByWeek(int week, int year);

	@Query("SELECT  new ReportByUser( o.account.fullname ,  o.address, count(o.account.username), sum(o.totalPrice)) FROM Order o  WHERE MONTH(o.createDate) = ?1 and YEAR(o.createDate) = ?2 AND o.status = 'DG' GROUP BY   o.account.fullname ,  o.address ORDER BY sum(o.totalPrice) DESC")
	public List<ReportByUser> getReportByUsersByMonth(int month, int year);

	@Query("SELECT  new ReportByUser( o.account.fullname ,  o.address, count(o.account.username), sum(o.totalPrice)) FROM Order o  WHERE YEAR(o.createDate) = ?1 AND o.status = 'DG' GROUP BY   o.account.fullname ,  o.address ORDER BY sum(o.totalPrice) DESC")
	public List<ReportByUser> getReportByUsersByYear(int year);

	// @Query("SELECT od FROM OrderDetail ORDER BY p.product.createDate DESC")
	// List<OrderDetail> findTop10ByOrderByCreateDateDesc();
	// @Query("SELECT od FROM OrderDetail od JOIN od.product p ORDER BY p.createDate
	// DESC")
	// List<OrderDetail> findTop10ByOrderByCreateDateDesc();

	// get transaction
	@Query("SELECT o FROM OrderDetail o WHERE o.order.status != 'H' ORDER BY o.order.createDate DESC")
	List<OrderDetail> getTop10OrderDetail(Pageable pageable);

	@Query("SELECT o FROM OrderDetail o WHERE o.order.status != 'H' ORDER BY o.order.createDate DESC")
	List<OrderDetail> getTop10OrderDetailNonePageAble();

	List<OrderDetail> findAllByOrder_Id(Long id);
}
