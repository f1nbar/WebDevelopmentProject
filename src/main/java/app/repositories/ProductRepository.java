package app.repositories;

import app.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    default List<Product> findSuggestions(Product product, ProductRepository productRepository) {

        ArrayList<Product> suggestions = new ArrayList<Product>();
        ;

        int size = (int) productRepository.count();
        String currentId = product.getProductId();
        String separator = "-";
        int sepPos = currentId.indexOf(separator);
        if (sepPos == -1) {
            System.out.println("");
        }

        while (suggestions.size() < 3) {
            int randomNum = ThreadLocalRandom.current().nextInt(101, (100 + size));
            String idRandom = "BASICS-" + randomNum;
            Product addProduct = productRepository.getOne(idRandom);
            if (!suggestions.contains(addProduct) && addProduct.isVisible()) {
                suggestions.add(addProduct);
            }
            System.out.println(addProduct.getProductName());
        }

        return suggestions;

    };
}
