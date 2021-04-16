package za.co.momentum.service.service.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import za.co.momentum.service.store.model.StoreEntity;
import za.co.momentum.service.store.repository.StoreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@EntityScan(basePackageClasses = {StoreEntity.class})
@EnableJpaRepositories(basePackageClasses={StoreRepository.class})
class StoreServiceTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private StoreRepository storeRepository;

	@Test
	public void getAllStores() {
		createStore("MOMENTUM_0");
		createStore("MOMENTUM_1");
		createStore("MOMENTUM_2");
		createStore("MOMENTUM_3");
		createStore("MOMENTUM_4");

		List<StoreEntity> allStores = storeRepository.findAll();
		assertTrue(allStores.size() > 0);
	}

	void createStore(String storeName) {
		StoreEntity storeEntity = new StoreEntity();
		storeEntity.setStoreName(storeName);
		entityManager.persist(storeEntity);
	}

	@EnableAutoConfiguration
	@Configuration
	public static class TestConfig {

	}
}