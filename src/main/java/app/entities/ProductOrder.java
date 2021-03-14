package app.entities;

import app.keys.ProductOrderKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public
class ProductOrder {

//    @EmbeddedId
//    ProductOrderKey id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    public ProductOrder() { }

    public ProductOrder(Product product, Order order, int quantity) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
    }

    int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}


