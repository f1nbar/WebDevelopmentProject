package app.entities;

import app.services.SkuGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator = "SkuGenerator")
    @GenericGenerator(name = "SkuGenerator", strategy = "app.services.SkuGenerator", parameters = @org.hibernate.annotations.Parameter(name = SkuGenerator.VALUE_PREFIX_PARAMETER, value = "BASICS"))
    private String id;
    @Column(name = "productname")
    private String productName;
    @Column(name = "price")
    private float price;
    @Column(name = "isvisible")
    private boolean isVisible;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy="product")
    @JsonIgnore
    Set<ProductOrder> orders;

    public String getId(){ return id;}

    public String getProductId() {
        return id;
    }

    public void setProductId(String productId) {
        this.id = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Set<ProductOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<ProductOrder> orders) {
        this.orders = orders;
    }

    public void setDescription(String description) { this.description = description; }

    public String getDescription(){ return this.description;}

    public Product(){

    }

    public Product(String productName, float price, Boolean isVisible, String description) {
        this.productName = productName;
        this.price = price;
        this.isVisible = isVisible;
        this.description = description;
    }

}
