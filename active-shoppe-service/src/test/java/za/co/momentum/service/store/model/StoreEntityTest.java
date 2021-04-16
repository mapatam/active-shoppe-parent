package za.co.momentum.service.store.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@EntityScan(basePackageClasses = {StoreEntity.class})
class StoreEntityTest {

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void save() {
		StoreEntity storeEntity = new StoreEntity();
		storeEntity.setStoreName("MOMENTUM");

		StoreEntity persist = entityManager.persist(storeEntity);
		Assertions.assertEquals("MOMENTUM", persist.getStoreName());
		Assertions.assertEquals(1L, persist.getId());
	}

	@EnableAutoConfiguration
	@Configuration
	public static class TestConfig {
	}
}