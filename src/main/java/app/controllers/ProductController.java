package app.controllers;

import app.entities.Product;
import app.services.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import app.repositories.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest req) {
        List<Product> products = productRepository.findAll();
        List<Product> visibleProducts = new ArrayList<>();
        for (int i = 0; i < productRepository.count(); i++) {
            if(products.get(i).isVisible()){
                visibleProducts.add(products.get(i));
            }
        }
        model.addAttribute("products", visibleProducts);
        return "index";
    }

    @PostMapping(value = "products/add")
    @ResponseBody
    public String addProduct(@RequestBody Product product, Model model){
        productRepository.save(product);
        model.addAttribute("person", productRepository.findAll());
        return "added" + product.getProductName();
    }

    @PostMapping(value = "products/image")
    public String addImage(@RequestParam("image")MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileUploadUtil.saveFile("/images",fileName,multipartFile);
        return "Success";
    }


    @GetMapping(value = "/products/view/{id}")
    public String view(Model model, HttpServletRequest request) {
        String url = (request.getRequestURI());
        String productID = url.substring(15);
        model.addAttribute("product",productRepository.getOne(productID));
        model.addAttribute("products", productRepository.findSuggestions(productRepository.getOne(productID), productRepository));
        return "view-product";
    }

    @GetMapping(value = "/products/remove/{id}")
    public String removeProduct(HttpServletRequest request){
        String url = (request.getRequestURI());
        String productID = url.substring(17);
        productRepository.deleteById(productID);
        System.out.println("deleted" + productID);
        return "redirect:/";
    }

    @PostMapping(value = "/products/edit")
    public String editProduct(@RequestBody Product product){
        System.out.println("hello");
        productRepository.save(product);
        return "redirect:/";
    }

}
