package springboot.mysql.SpringBootRestApi.repository;

import java.util.List;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import springboot.mysql.SpringBootRestApi.dto.ProductResponse;
import springboot.mysql.SpringBootRestApi.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("Select p from Product p Where " + "p.productName LIKE CONCAT('%', :query, '%')"
			+ "Or p.description LIKE CONCAT('%', :query, '%') ")
	List<Product> searchProducts(String query);

//	@Query("Select p from Product p Where "
//			+ "p.productName LIKE concat('%', :query, '%')" +
//			"Or p.description LIKE CONCAT('%' :query, '%') ")
//	Optional<Product> findByProductName(String query);

	List<Product> findByCatId(int catId);

	@Query("SELECT new springboot.mysql.SpringBootRestApi.dto.ProductResponse(p.productId, p.productName, p.price, p.description , p.catId, p.photo, c.quantity, c.cartId)"
			+ " FROM Cart as c INNER JOIN Product p ON c.productId = p.productId INNER JOIN User as u ON c.userId = u.id WHERE p.productId = :productId AND u.id = :userId")
	ProductResponse findProductWithCart(long userId, long productId);

}
