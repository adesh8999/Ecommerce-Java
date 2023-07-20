package springboot.mysql.SpringBootRestApi.dto;


//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CartResponse {
private Long productId;
    private String productName;
    private int userId ;
    private float price;
    private int quantity;
    private String photo;
    private String category;
    private long cartId;
    
    


	public CartResponse(Long productId, String productName, int userId, float price, int quantity, String photo,
			String category, long cartId) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.userId = userId;
		this.price = price;
		this.quantity = quantity;
		this.photo = photo;
		this.category = category;
		this.cartId = cartId;
	}


	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public long getCartId() {
		return cartId;
	}


	public void setCardId(long cartId) {
		this.cartId = cartId;
	}
	
	
    
	
    

	
   
    
   
}                                                      