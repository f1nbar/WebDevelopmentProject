package app.controllers;

import app.entities.Product;
import app.services.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import app.repositories.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

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

    @PostMapping(value = "products/add")
    @ResponseBody
    public String addProduct(@RequestBody Product product, Model model){
        productRepository.save(product);
        model.addAttribute("person", productRepository.findAll());
        return "added" + product.getProductName();
    }

    @PostMapping(value = "products/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addImage(@RequestParam("image")MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileUploadUtil.saveFile("/images",fileName,multipartFile);
        return "Success";
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
