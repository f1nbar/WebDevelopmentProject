package app.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator = "SkuGenerator", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "SkuGenerator", strategy = "app.services.SkuGenerator")
    protected String id;
    private String productName;
    private int price;

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

    private boolean isVisible;

    public Product(){

    }

}
