package springboot.mysql.SpringBootRestApi.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import springboot.mysql.SpringBootRestApi.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
	
	List<Order> findByUserId(Long userId);
	
	@Modifying
	@Transactional
	@Query("Update Order o SET o.status =:s where o.id=:id")
	int changeStatus(long id, String s);
	
	@Query("SELECT o FROM Order o WHERE o.status=:status")
	List<Order> getOrdersByStatus(String status);
	
	@Query("SELECT o FROM Order o WHERE o.dateCreated BETWEEN :from AND :to")
	List<Order> searchOrdersByDate(Date from, Date to);
	
	@Query("SELECT o FROM Order o WHERE o.customerName LIKE CONCAT(:input,'%') OR "
			+ " o.customerEmail LIKE CONCAT(:input,'%') OR "
			+ " o.customerMobile LIKE CONCAT(:input,'%')")
	List<Order> getOrdersByCustomerName(String input);
	
	
	

}
