package app.repositories;

import app.entities.ProductOrder;
import app.keys.ProductOrderKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, ProductOrderKey> { }
