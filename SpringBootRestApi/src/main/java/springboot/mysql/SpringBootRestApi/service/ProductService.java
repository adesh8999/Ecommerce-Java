package springboot.mysql.SpringBootRestApi.service;

import java.util.List;
import java.util.Optional;

import springboot.mysql.SpringBootRestApi.entity.Product;
import springboot.mysql.SpringBootRestApi.exception.AlreadyExistsException;
import springboot.mysql.SpringBootRestApi.exception.RecordNotFoundException;

public interface ProductService {
	
	Product addProduct(Product product) throws AlreadyExistsException;
	
	//Optional<Product> getProductById(Long productId) throws RecordNotFoundException;
	
	List<Product> getProductByName(String query);
	
	Optional<Product> getProductById(Long productId) throws RecordNotFoundException;

	List<Product> getAllProducts();

	Product updateProduct( Product product);

	void deleteProduct(Long productId);
	
	List<Product> getProductsByCatId(int catId);
	
	Object getProductsByUserAndProdId(long userId, long productId);
	
	Object getProductWithPagination(int pageNo , int pageSize);
	
	

}
