package springboot.mysql.SpringBootRestApi.service;

import java.util.Date;
import java.util.List;

import springboot.mysql.SpringBootRestApi.entity.Order;
import springboot.mysql.SpringBootRestApi.exception.RecordNotFoundException;

public interface OrderService {
	
	Order save(Order order);
	
	void clearOrders();
	
	List<Order> orderList () throws RecordNotFoundException;
	
	List<Order> orderListByUserId(long userId);
	
	long changeOrderStatus(long id, int a);
	
	List<Order> getOrdersByStatus(String status);
	
	List<Order> searchByDate(Date from, Date to);
	
	List<Order> getOrdersByName(String name);
	
	

}
