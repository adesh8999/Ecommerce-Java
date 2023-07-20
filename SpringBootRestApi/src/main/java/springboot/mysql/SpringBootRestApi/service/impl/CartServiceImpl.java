package springboot.mysql.SpringBootRestApi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import springboot.mysql.SpringBootRestApi.dto.CartResponse;
import springboot.mysql.SpringBootRestApi.entity.Cart;
import springboot.mysql.SpringBootRestApi.repository.CartRepository;
import springboot.mysql.SpringBootRestApi.service.CartService;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

	@Autowired
	CartRepository cartRepository;

	@Override
	public Cart addToCart(Cart cart) {

		return cartRepository.save(cart);
	}

	@Override
	public void removeCartItem(long cartId) {

		cartRepository.deleteById(cartId);

	}

	@Override
	public List<CartResponse> getAllCartItems(int userId) {

		List<CartResponse> cartList = cartRepository.getCartItems(userId);

		return cartList;
	}

}
