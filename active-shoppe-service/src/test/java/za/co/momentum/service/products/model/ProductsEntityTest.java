package za.co.momentum.service.products.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import za.co.momentum.service.store.model.StoreEntity;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@EntityScan(basePackageClasses = {StoreEntity.class, ProductsEntity.class})
class ProductsEntityTest {

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void save() {
		ProductsEntity productsEntity = new ProductsEntity();
		productsEntity.setCost_points(20000);
		productsEntity.setProductCode("MOMEN_01");
		productsEntity.setProductName("INSURANCE");

		ProductsEntity persist = entityManager.persist(productsEntity);
		Assertions.assertEquals("MOMEN_01", persist.getProductCode());
		Assertions.assertEquals(20000, persist.getCost_points());
	}

	@EnableAutoConfiguration
	@Configuration
	public static class TestConfig {
	}
}