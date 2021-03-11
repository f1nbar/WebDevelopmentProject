package app.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProductOrderKey implements Serializable {

    public ProductOrderKey(String productId, int orderId) {
        this.productId = productId;
        this.orderId = orderId;
    }

    @Column(name = "product_id")
    String productId;

    @Column(name = "order_id")
    int orderId;

}
