package springboot.mysql.SpringBootRestApi.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import springboot.mysql.SpringBootRestApi.ResponseHandler;
import springboot.mysql.SpringBootRestApi.dto.CartResponse;
import springboot.mysql.SpringBootRestApi.entity.Address;
import springboot.mysql.SpringBootRestApi.entity.Cart;
import springboot.mysql.SpringBootRestApi.entity.Category;
import springboot.mysql.SpringBootRestApi.entity.Order;
import springboot.mysql.SpringBootRestApi.entity.Product;
import springboot.mysql.SpringBootRestApi.exception.AlreadyExistsException;
import springboot.mysql.SpringBootRestApi.exception.RecordNotFoundException;
import springboot.mysql.SpringBootRestApi.service.AddressService;
import springboot.mysql.SpringBootRestApi.service.CartService;
import springboot.mysql.SpringBootRestApi.service.CategoryService;
import springboot.mysql.SpringBootRestApi.service.OrderService;
import springboot.mysql.SpringBootRestApi.service.ProductService;
import springboot.mysql.SpringBootRestApi.utils.FileUploadUtil;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/users/products")
public class Pro_Cat_Controller {

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AddressService addressService;
	
	//*********************************Product***************************

	// build add Product REST API
	// http://localhost:8080/products/add
	@PostMapping("/add")
	public ResponseEntity<Object> addProducts(@ModelAttribute Product product,
			@RequestParam("file") MultipartFile multipartFile) throws IOException, AlreadyExistsException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String filecode = FileUploadUtil.saveFile(fileName, multipartFile);

		product.setPhoto(filecode);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		product.setActive(true);
		product.setCreatedDate(timestamp);
		product.setUpdatedDate(timestamp);

		Product savedProduct = productService.addProduct(product);
		return ResponseHandler.generateResponse("Product Added Successfully", HttpStatus.CREATED, savedProduct);
	}

	// build get Product by name REST API
	// http://localhost:8080/products/productByName
	@GetMapping("/productByName")
	public ResponseEntity<Object> getProductByName(@RequestParam("query") String query) throws RecordNotFoundException {
		List<Product> product = productService.getProductByName(query);
		return ResponseHandler.generateResponse("Product by Name fetch successfully", HttpStatus.OK, product);
	}

	// Build Get All Products REST API
	// http://localhost:8080/products
	@GetMapping
	public ResponseEntity<Object> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return ResponseHandler.generateResponse("All Products fetch successfully", HttpStatus.OK, products);
	}

	// Build Update product REST API
	@PostMapping("/update")
	public ResponseEntity<Object> updateProduct(@ModelAttribute Product product,@RequestParam(value = "file",required = false) MultipartFile multipartFile) throws IOException
	{
		product.setProductId(product.getProductId());
		if(multipartFile != null) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			String fileCode = FileUploadUtil.saveFile(fileName, multipartFile);
			product.setPhoto(fileCode);
		}
		
		Product updatedProduct = productService.updateProduct(product);
		
		return ResponseHandler.generateResponse("Product updated successfully", HttpStatus.OK, updatedProduct);
	}
	

	
	

	// build get Product by id REST API
	// http://localhost:8080/products/{"productId"}
	@GetMapping("/{productId}")
	public ResponseEntity<Object> getProductById(@PathVariable("productId") Long productId)
			throws RecordNotFoundException {
		Optional<Product> product = productService.getProductById(productId);
		return ResponseHandler.generateResponse("Product by Id fetch successfully", HttpStatus.OK, product);
	}

	// Build Delete User REST API
	@DeleteMapping("{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("productId") Long productId) {
		productService.deleteProduct(productId);
		return ResponseHandler.generateResponse("Product deleted successfully", HttpStatus.OK, productService);
	}
	
	// build get Product by id REST API
	// http://localhost:8080/products/{"productId"}
	@GetMapping("/catId/{catId}")
	public ResponseEntity<Object> getProductByCatId(@PathVariable("catId") Integer catId)
			throws RecordNotFoundException {
		List<Product> product = productService.getProductsByCatId(catId);
		return ResponseHandler.generateResponse("Product by catId fetch successfully", HttpStatus.OK, product);
	}
	
	// build get Product by userId And cartId REST API
	// http://localhost:8080/products/getProdByUserAndCartId?userId=2&productId=2
	@GetMapping("/getProdByUserAndCartId")
	public ResponseEntity<Object> getProductByUserAndProdId(@RequestParam("userId") long userId , @RequestParam("productId") long productId) {
		Object product = productService.getProductsByUserAndProdId(userId,productId); 
		return ResponseHandler.generateResponse("Product by UserId & ProdId fetch successfully", HttpStatus.OK, product);
	}
	
	// build get Product by Pagination REST API
	// http://localhost:8080/products/productPagiantion?pageNo=1&pageSize=1
	@GetMapping("/productPagiantion")
	public ResponseEntity<?> getAllProductWithPagination(@RequestParam("pageNo") int pageNo , @RequestParam("pageSize") int pageSize) {
		Object products = productService.getProductWithPagination(pageNo,pageSize); 
		return ResponseHandler.generateResponse("Product by pagination fetch successfully", HttpStatus.OK, products);
	
	}
	
	
	
	
	//**************************Category********************************

	@GetMapping("/cat")
	public ResponseEntity<Object> getAllCategories() {
		List<Category> catList = categoryService.getAllCategories();
		return ResponseHandler.generateResponse("All Categories fetch Successfully", HttpStatus.OK, catList);
	}

	@PostMapping("/cat/addCat")
	public ResponseEntity<Object> addCategories(@RequestBody Category category) {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		category.setActive(true);
		category.setCreatedDate(timestamp);
		category.setUpdatedDate(timestamp);

		Category cat = categoryService.addCategory(category);
		return ResponseHandler.generateResponse("Category Added Successfully", HttpStatus.CREATED, cat);
	}

	@PutMapping("/cat/{catId}")
	// http://localhost:8080/products/{id}
	public ResponseEntity<Object> updateCategory(@PathVariable("catId") Long catId, @RequestBody Category category) {
		category.setCatId(catId);
		Category updatedCategory = categoryService.updateCategory(category);
		return ResponseHandler.generateResponse("Category updated successfully", HttpStatus.OK, updatedCategory);
	}

	@GetMapping("/cat/{catId}")
	public ResponseEntity<Object> getCategoryById(@PathVariable("catId") Long catId) throws RecordNotFoundException {
		Optional<Category> category = categoryService.getCategoryById(catId);
		return ResponseHandler.generateResponse("Category by Id fetch successfully", HttpStatus.OK, category);
	}

	@DeleteMapping("/cat/{catId}")
	public ResponseEntity<Object> deleteCategory(@PathVariable("catId") Long catId) {
		categoryService.deleteCategory(catId);
		return ResponseHandler.generateResponse("Product deleted successfully", HttpStatus.OK, categoryService);
	}
	
	//**********************************Cart*************************************************
	
	@PostMapping("/cart/addToCart")
	public ResponseEntity<Object> addToCart(@RequestBody Cart cart) {

		Cart cart1 = cartService.addToCart(cart);
		return ResponseHandler.generateResponse("Product added into Cart Successfully", HttpStatus.CREATED, cart1);
	}
	
	@GetMapping("/cart/getCartItems/{id}")
	public ResponseEntity<Object> getCartItems(@PathVariable("id") int userId) {
		List<CartResponse> cartList = cartService.getAllCartItems(userId);
		return ResponseHandler.generateResponse("All Cart Items fetch Successfully", HttpStatus.OK, cartList);
	}
	
	@DeleteMapping("/cart/deleteCart/{id}")
	public ResponseEntity<Object> deleteCartItem(@PathVariable("id") long cartId) {
		cartService.removeCartItem(cartId);
		return ResponseHandler.generateResponse("Cart Item deleted Successfully", HttpStatus.OK, "Deleted");
	}
	
	//*******************************order****************************************
	
	@PostMapping("/order/placeOrder")
	public ResponseEntity<?> placeOrder(@RequestBody Order order) {

//		OrderItem oitem=order.getOrderItems();
//		oitem.setOrder(order);
		Order savedData = orderService.save(order);
		return ResponseHandler.generateResponse("Order placed successfully", HttpStatus.OK,savedData);

	}
	
	
	@GetMapping("/order/getAllOrder")
	public ResponseEntity<?> getAllOrder() {

		List<Order> orderList = orderService.orderList();
		return ResponseHandler.generateResponse("Order fetched successfully", HttpStatus.OK,orderList);

	}
	
	@DeleteMapping("/order/clearAllOrder")
	public ResponseEntity<Object> clearOrders() {
		orderService.clearOrders();
		return ResponseHandler.generateResponse("Order History deleted Successfully", HttpStatus.OK, "Deleted");
	}
	

	@GetMapping("/order/getOrderByUserId/{id}")
	public ResponseEntity<?> getOrderByUserIdr(@PathVariable("id") Long userId) {

		List<Order> orderList = orderService.orderListByUserId(userId);
		return ResponseHandler.generateResponse("Order fetched by user Id successfully", HttpStatus.OK,orderList);

	}
	
	@PutMapping("/order/changeOrderStatus")
	public ResponseEntity<?> changeStatus(@RequestParam("id") Long id, @RequestParam("status") int status) {

		long orderStatus = orderService.changeOrderStatus(id, status);
		return ResponseHandler.generateResponse("Order status changed", HttpStatus.OK,orderStatus);

	}
	
	@GetMapping("/order/getbystatus")
	public ResponseEntity<?> getByStatus(@RequestParam("status") String status){
		List<Order> orderData=orderService.getOrdersByStatus(status);
		return ResponseHandler.generateResponse("Order fetched by status successfully", HttpStatus.OK,orderData);
	}
	
	@GetMapping("/order/getbyDate")
	public ResponseEntity<?> getOrderByDate(@RequestParam("from") Date from,@RequestParam("to") Date to){
		List<Order> orderData=orderService.searchByDate(from,to);
		return ResponseHandler.generateResponse("Order fetched by Date successfully", HttpStatus.OK,orderData);
	}
	
	@GetMapping("/order/getbyName")
	public ResponseEntity<?> getOrderByName(@RequestParam("input") String input){
		List<Order> orderData=orderService.getOrdersByName(input);
		return ResponseHandler.generateResponse("Order fetched by status successfully", HttpStatus.OK,orderData);
	}
	
	
	
	//***************************************Address********************************
	
	@PostMapping("/address/save")
	public ResponseEntity<?> saveUserAddress(@RequestBody Address address) {
//		System.out.println(cart);
		Address savedData = addressService.save(address);
		return ResponseHandler.generateResponse("Address saved successfully", HttpStatus.OK,savedData);

	}

}
