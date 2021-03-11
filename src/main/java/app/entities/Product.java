package app.entities;

import app.services.SkuGenerator;
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
    private int price;
    @Column(name = "isvisible")
    private boolean isVisible;

    @OneToMany(mappedBy="product")
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public Product(){

    }

}
