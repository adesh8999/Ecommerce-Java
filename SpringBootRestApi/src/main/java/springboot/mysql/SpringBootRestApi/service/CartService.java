package springboot.mysql.SpringBootRestApi.service;

import java.util.List;

import springboot.mysql.SpringBootRestApi.dto.CartResponse;
import springboot.mysql.SpringBootRestApi.entity.Cart;


public interface CartService {
	
	Cart addToCart(Cart cart);
	
	void removeCartItem(long cartId);
	
	List<CartResponse> getAllCartItems(int userId);

}
