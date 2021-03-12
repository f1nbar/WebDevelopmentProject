package app.repositories;

import app.entities.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.color.ProfileDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    default List<Product> findSuggestions(Product product, ProductRepository productRepository) {

       ArrayList<Product> suggestions = new ArrayList<Product>();;

       int size = (int) productRepository.count();
       String currentId = product.getProductId();
       String separator ="-";
        int sepPos = currentId.indexOf(separator);
        if (sepPos == -1) {
            System.out.println("");
        }
        String idSub = currentId.substring(sepPos + separator.length());
        int block = Integer.parseInt(idSub);
        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(101, (100 + size));
            String idRandom = "BASICS-" + randomNum;
            Product addProduct = productRepository.getOne(idRandom);
            suggestions.add(addProduct);
            System.out.println(addProduct.getProductName());
        }

       return suggestions;

    };
}
