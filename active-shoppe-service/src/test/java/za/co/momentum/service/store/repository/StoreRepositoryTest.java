package za.co.momentum.service.store.repository;

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
@EntityScan(basePackageClasses = {StoreEntity.class})
@EnableJpaRepositories(basePackageClasses = {StoreRepository.class})
class StoreRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private StoreRepository storeRepository;

	@Test
	void findByStoreId() {
		StoreEntity storeEntity = new StoreEntity();;
		storeEntity.setStoreName("MOMENTUM");
		entityManager.persist(storeEntity);

		Optional<StoreEntity> store = storeRepository.findById(storeEntity.getId());
		assertTrue(store.isPresent(), "Expected store Entity to be present");
		assertNotEquals(Optional.empty(), store, "Expected Optional<StoreEntity> not to be empty");
		assertEquals(storeEntity.getId(), store.get().getId(), "Expected storeId to be" + storeEntity.getId());
		assertFalse(storeRepository.findById(3L).isPresent(), "Store entity should not exist");
	}

	@EnableAutoConfiguration
	@Configuration
	public static class TestConfig {
	}
}