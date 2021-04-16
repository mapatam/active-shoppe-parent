package za.co.momentum.service.customer.model;

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


@DataJpaTest
@EntityScan(basePackageClasses = {StoreEntity.class, CustomerEntity.class})
class CustomerEntityTest {

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void save() {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setName("LEHLOGONOLO");
		customerEntity.setActive_days_points(20000);

		CustomerEntity persist = entityManager.persist(customerEntity);
		Assertions.assertEquals("LEHLOGONOLO", persist.getName());
		Assertions.assertEquals(20000, persist.getActive_days_points());
	}

	@EnableAutoConfiguration
	@Configuration
	public static class TestConfig {
	}
}