package springboot.mysql.SpringBootRestApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springboot.mysql.SpringBootRestApi.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

}
