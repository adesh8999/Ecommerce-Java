package springboot.mysql.SpringBootRestApi.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="image_url")
    private String imageUrl;
    
    @Column(name="name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name="quantity")
    private int quantity;

  
    @Column(name="product_id")
    private int product_id;
    
    @Column(name="total_amount")
    private BigDecimal totalAmount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	
	
    
    public BigDecimal getTotalAmount()
	{
    	 return BigDecimal.valueOf( this.quantity ).multiply( this.price );
		
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = BigDecimal.valueOf( this.quantity ).multiply( this.price );
	}

	
	
	public String getName() {
		return name;
	}

//	public int getTotalAmount() {
//		return totalAmount;
//	}
//
//	public void setTotalAmount(int totalAmount) {
//		this.totalAmount = totalAmount;
//	}

	public void setName(String name) {
		this.name = name;
	}

	
    

}
