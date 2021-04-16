package za.co.momentum.service.products.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import za.co.momentum.service.products.model.ProductsEntity;
import za.co.momentum.service.store.model.StoreEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@EntityScan(basePackageClasses = {ProductsEntity.class, StoreEntity.class})
@EnableJpaRepositories(basePackageClasses = {ProductsRepository.class})
class ProductsRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ProductsRepository productsRepository;

	@Test
	void findByProductCode() {
		ProductsEntity productsEntity = new ProductsEntity();;
		productsEntity.setProductName("INSURANCE");
		productsEntity.setProductCode("MOMEN_0");
		productsEntity.setCost_points(10000);
		entityManager.persist(productsEntity);

		Optional<ProductsEntity> product = productsRepository.findByProductCodeIgnoreCase(productsEntity.getProductCode());
		assertTrue(product.isPresent(), "Expected product Entity to be present");
		assertNotEquals(Optional.empty(), product, "Expected Optional<ProductsEntity> not to be empty");
		assertEquals(productsEntity.getId(), product.get().getId(), "Expected productId to be" + productsEntity.getId());
		assertFalse(productsRepository.findByProductCodeIgnoreCase("MOMEN_1").isPresent(), "Product entity should not exist");
	}

	@EnableAutoConfiguration
	@Configuration
	public static class TestConfig {

	}
}