package springboot.mysql.SpringBootRestApi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import springboot.mysql.SpringBootRestApi.dto.EmailDetails;
import springboot.mysql.SpringBootRestApi.entity.Order;
import springboot.mysql.SpringBootRestApi.entity.OrderItem;
import springboot.mysql.SpringBootRestApi.exception.RecordNotFoundException;
import springboot.mysql.SpringBootRestApi.repository.CartRepository;
import springboot.mysql.SpringBootRestApi.repository.OrderRepo;
import springboot.mysql.SpringBootRestApi.service.EmailService;
import springboot.mysql.SpringBootRestApi.service.OrderService;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepo orderRepo;

	@Autowired
	CartRepository cartRepo;

	@Autowired
	EmailService emailService;

	@Override
	public Order save(Order order) {
		order.setStatus("Pending");

		Order ord = orderRepo.save(order);

		Set<OrderItem> orderItems = order.getOrderItems();

		List<Order> orderList = new ArrayList<>();
		orderList.add(ord);

		EmailDetails emailDetails = new EmailDetails(ord.getCustomerEmail(), "Thank You For Visit!", "Success", null,
				orderItems, orderList);
		emailService.sendSimpleMail(emailDetails);

		long userId = order.getUserId();

		long count = cartRepo.deleteByUserId(userId);
		System.out.println(count);

		return ord;
	}

	@Override
	public List<Order> orderList() throws RecordNotFoundException {

		return orderRepo.findAll();
	}

	@Override
	public void clearOrders() {
		orderRepo.deleteAll();

	}

	@Override
	public List<Order> orderListByUserId(long userId) {

		List<Order> orders = orderRepo.findByUserId(userId);

		return orders;
	}

	@Override
	public long changeOrderStatus(long id, int orderStatus) {
		if (orderStatus == 1) {
			orderRepo.changeStatus(id, "Processing");
			return 1;
		} else if (orderStatus == 2) {
			orderRepo.changeStatus(id, "Delivered");
			return 1;
		} else if (orderStatus == 3) {
			orderRepo.changeStatus(id, "Cancelled");
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public List<Order> getOrdersByStatus(String status) {
		return orderRepo.getOrdersByStatus(status);
	}

	@Override
	public List<Order> searchByDate(Date from, Date to) {
		
		System.out.println(from);
		List<Order> list = orderRepo.searchOrdersByDate(from, to);
		
		return list;
	}

	@Override
	public List<Order> getOrdersByName(String name) {
		System.out.println(name);
		List<Order> list = orderRepo.getOrdersByCustomerName(name);
		
		return list;
	}

}
