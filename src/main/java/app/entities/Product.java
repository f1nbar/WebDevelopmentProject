package app.entities;

import app.services.SkuGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


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

    public String getId(){ return id;}

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

    public Product(){

    }

}
