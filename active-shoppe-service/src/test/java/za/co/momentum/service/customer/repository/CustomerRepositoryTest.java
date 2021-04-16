package za.co.momentum.service.customer.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import za.co.momentum.service.customer.model.CustomerEntity;
import za.co.momentum.service.store.model.StoreEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@EntityScan(basePackageClasses = {CustomerEntity.class, StoreEntity.class})
@EnableJpaRepositories(basePackageClasses = {CustomerRepository.class})
class CustomerRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void findByProductCode() {
		CustomerEntity customerEntity = new CustomerEntity();
		;
		customerEntity.setName("LEHLOGONOLO");
		customerEntity.setActive_days_points(150000);
		entityManager.persist(customerEntity);

		Optional<CustomerEntity> customer = customerRepository.findById(customerEntity.getId());
		assertTrue(customer.isPresent(), "Expected customer Entity to be present");
		assertNotEquals(Optional.empty(), customer, "Expected Optional<CustomerEntity> not to be empty");
		assertEquals(customerEntity.getId(), customer.get().getId(), "Expected customerId to be" + customerEntity.getId());
		assertFalse(customerRepository.findById(3L).isPresent(), "Customer entity should not exist");
	}

	@EnableAutoConfiguration
	@Configuration
	public static class TestConfig {

	}
}