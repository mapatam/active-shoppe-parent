package za.co.momentum.service.service.products;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import za.co.momentum.service.customer.model.CustomerEntity;
import za.co.momentum.service.customer.repository.CustomerRepository;
import za.co.momentum.service.products.model.ProductsEntity;
import za.co.momentum.service.products.repository.ProductsRepository;
import za.co.momentum.service.store.model.StoreEntity;
import za.co.momentum.service.store.repository.StoreRepository;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@EntityScan(basePackageClasses = {ProductsEntity.class, StoreEntity.class, CustomerEntity.class})
@EnableJpaRepositories(basePackageClasses = {ProductsRepository.class, CustomerRepository.class, StoreRepository.class})
class ProductServiceTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ProductsRepository productsRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private StoreRepository storeRepository;

	@Test
	public void getAllStoreProducts() {
		StoreEntity momentumStore = createStore("MOMENTUM");
		createProduct("INSURANCE0", momentumStore, "MOM_O");
		createProduct("INSURANCE1", momentumStore, "MOM_1");
		createProduct("INSURANCE2", momentumStore, "MOM_2");

		List<ProductsEntity> allProductsInStore = productsRepository.findAll();
		Assertions.assertTrue(allProductsInStore.size() > 0);
	}

	@Test
	public void purchaseProduct() {
		StoreEntity momentumStore = createStore("MOMENTUM");

		CustomerEntity customer0 = createCustomer(momentumStore, "LUCKY");
		CustomerEntity customer1 = createCustomer(momentumStore, "Joe");
		CustomerEntity customer2 = createCustomer(momentumStore, "Bloggs");

		ProductsEntity product0 = createProduct("INSURANCE0", momentumStore, "MOM_O");
		ProductsEntity product1 = createProduct("INSURANCE1", momentumStore, "MOM_1");
		ProductsEntity product2 = createProduct("INSURANCE2", momentumStore, "MOM_2");

		Optional<CustomerEntity> customerEntity = customerRepository.findById(customer0.getId());
		Optional<ProductsEntity> productsEntity = productsRepository.findById(product0.getId());

		Assertions.assertTrue(customerEntity.isPresent() && productsEntity.isPresent());
		Assertions.assertFalse(customerEntity.get().getActive_days_points() < productsEntity.get().getCost_points()
				, "Customer dont have enough points to purchase");

		int remainder = customerEntity.get().getActive_days_points() - productsEntity.get().getCost_points();
		Assertions.assertEquals(14500, remainder);
	}

	StoreEntity createStore(String storeName) {
		StoreEntity storeEntity = new StoreEntity();
		storeEntity.setStoreName(storeName);
		return entityManager.persist(storeEntity);
	}

	CustomerEntity createCustomer(StoreEntity store, String name) {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setName(name);
		customerEntity.setActive_days_points(15000);
		customerEntity.setStore(store);
		return entityManager.persist(customerEntity);
	}

	ProductsEntity createProduct(String productName, StoreEntity store, String code) {
		ProductsEntity productsEntity = new ProductsEntity();
		productsEntity.setProductName(productName);
		productsEntity.setProductCode(code);
		productsEntity.setCost_points(500);
		productsEntity.setStore(store);
		return entityManager.persist(productsEntity);
	}

	@EnableAutoConfiguration
	@Configuration
	public static class TestConfig {

	}

}