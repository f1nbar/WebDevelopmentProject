package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import repository.ProductRepository;

@Controller
public class ProductController {

    ProductRepository productRepository;

    @Autowired
    public ProductController (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(value = "/")
    public String getProducts(){
        return "index.html";
    }

    @GetMapping(value = "/products/{id}")
    public String viewProduct(){
        return "product.html";
    }

    @PostMapping(value = "/products/add")
    public String addProduct(){
        return "Added product ID";

    }

    @GetMapping(value = "products/remove/{id}")
    public String removeProduct(){
       return "Removed Product ID:";
    }

    @GetMapping(value = "products/edit/{id}")
    public String editProduct(){
        return "Edited Product ID";
    }

}
