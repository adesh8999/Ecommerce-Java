package springboot.mysql.SpringBootRestApi.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import springboot.mysql.SpringBootRestApi.dto.ProductResponse;
import springboot.mysql.SpringBootRestApi.entity.Product;
import springboot.mysql.SpringBootRestApi.exception.AlreadyExistsException;
import springboot.mysql.SpringBootRestApi.exception.RecordNotFoundException;
import springboot.mysql.SpringBootRestApi.repository.ProductPaginationRepo;
import springboot.mysql.SpringBootRestApi.repository.ProductRepository;
import springboot.mysql.SpringBootRestApi.service.ProductService;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductPaginationRepo productPaginationRepo;

	@Override
	public Product addProduct(Product product) throws AlreadyExistsException {

		return productRepository.save(product);
	}

//	@Override
//	public Optional<Product> getProductById(Long productId) throws RecordNotFoundException {
//		// TODO Auto-generated method stub
//		return Optional.empty();
//	}

	@Override
	public List<Product> getProductByName(String query) {

		List<Product> productList = productRepository.searchProducts(query);

		return productList;

	}

	@Override
	public List<Product> getAllProducts() {

		return productRepository.findAll();
	}

	@Override
	public Product updateProduct(Product prod) {

		Product products = productRepository.findById(prod.getProductId()).get();
		products.setProductName(prod.getProductName());
		products.setDescription(prod.getDescription());
		products.setPrice(prod.getPrice());

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		prod.setActive(true);
		prod.setCreatedDate(timestamp);
		prod.setUpdatedDate(timestamp);

		products.setCatId(prod.getCatId());

		if (prod.getPhoto() == null) {
			products.setPhoto(products.getPhoto());
		} else {
			products.setPhoto(prod.getPhoto());
		}

		// Product updateProduct = productRepository.save(products);

		return productRepository.save(products);
	}

	@Override
	public void deleteProduct(Long productId) {

		productRepository.deleteById(productId);

	}

	@Override
	public Optional<Product> getProductById(Long productId) throws RecordNotFoundException {
		Optional<Product> product = productRepository.findById(productId);
		if (product.isPresent()) {
			return product;
		} else {
			throw new RecordNotFoundException("Product not found.");
		}
	}

	@Override
	public List<Product> getProductsByCatId(int catId) {
		List<Product> products = productRepository.findByCatId(catId);
		return products;
	}

	@Override
	public Object getProductsByUserAndProdId(long userId, long productId) {

		ProductResponse product = productRepository.findProductWithCart(userId, productId);
		if (product == null) {
			Optional<Product> prod1 = productRepository.findById(productId);

			return prod1.get();
		} else {
			return product;
		}
	}

	@Override
	public Object getProductWithPagination(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo, pageSize);

		Page<Product> allProducts = productPaginationRepo.findAll(pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Product Fetch by Pagination Successfully.");
		map.put("status", 200);
		map.put("data", allProducts.getContent());
		map.put("TotalPages", allProducts.getTotalPages());

		return map;
	}

}
