package springboot.mysql.SpringBootRestApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import springboot.mysql.SpringBootRestApi.dto.CartResponse;
import springboot.mysql.SpringBootRestApi.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

//	List<Cart> findByUser_Id(int user_id);
//
//	@Query("SELECT p FROM Product p WHERE id in : ids" )
//	List<Cart> findByProductId(@Param("ids") List<Integer> poductIdList);
//

//	select p.name, c.user_id, p.price,c.quantity,cat.name,p.photo FROM Cart c  JOIN Products p ON c.product_id = p.id AND c.user_id= 5 JOIN Category cat on cat.id=p.cid;

	@Query("SELECT new springboot.mysql.SpringBootRestApi.dto.CartResponse(p.productId, p.productName, c.userId, p.price, c.quantity,p.photo,cat.catName,c.cartId) FROM Cart c  JOIN Product p ON c.productId = p.productId AND c.userId= :userId JOIN Category cat on cat.catId=p.catId")
	public List<CartResponse> getCartItems(long userId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Cart c WHERE c.userId = :user_id")
	int deleteByUserId(long user_id);

}
