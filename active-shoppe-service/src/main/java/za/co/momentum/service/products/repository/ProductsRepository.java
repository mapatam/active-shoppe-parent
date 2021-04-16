package za.co.momentum.service.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.momentum.service.products.model.ProductsEntity;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {

	Optional<ProductsEntity> findByProductCodeIgnoreCase(String prodCode);
}
