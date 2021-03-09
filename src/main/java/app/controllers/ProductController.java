package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import app.repositories.ProductRepository;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest req) {
        System.out.println(productRepository.findAll().get(0).getId());
        return "index";
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
