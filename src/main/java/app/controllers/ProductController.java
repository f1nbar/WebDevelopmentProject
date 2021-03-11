package app.controllers;

import app.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import app.repositories.ProductRepository;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest req) {
        model.addAttribute("products",productRepository.findAll());
        return "index";
    }

    @PostMapping(value = "/products/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addProduct(@RequestBody Product product, Model model){
        System.out.println("add");
        productRepository.save(product);
        model.addAttribute("person", productRepository.findAll());
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
