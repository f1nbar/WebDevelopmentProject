package app.entities;

import app.keys.ProductOrderKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public
class ProductOrder {

    @EmbeddedId
    ProductOrderKey id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    Order order;

    int quantity;

    public ProductOrderKey getId() {
        return id;
    }

    public void setId(ProductOrderKey id) {
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


