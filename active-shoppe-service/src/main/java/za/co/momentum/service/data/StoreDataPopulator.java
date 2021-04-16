package za.co.momentum.service.data;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.momentum.service.customer.model.CustomerEntity;
import za.co.momentum.service.customer.repository.CustomerRepository;
import za.co.momentum.service.products.model.ProductsEntity;
import za.co.momentum.service.products.repository.ProductsRepository;
import za.co.momentum.service.store.model.StoreEntity;
import za.co.momentum.service.store.repository.StoreRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Component
@Log4j2
public class StoreDataPopulator implements DataPopulator {

	public static final String STORE_NAME = "MOMENTUM_0";

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private ProductsRepository productsRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Transactional
	public void populate() {
		log.info("Populating Store data");
		TypedQuery<StoreEntity> query = entityManager.createQuery("from StoreEntity as e where storeName=:store"
				, StoreEntity.class);
		query.setParameter("store", STORE_NAME);
		if (query.getResultList().size() > 0) {
			return;
		}

		StoreEntity store = new StoreEntity();
		store.setStoreName(STORE_NAME);
		storeRepository.save(store);
		log.info("Store ID: " + store.getId());

		for (int x = 0; x < 10; x++) {

			CustomerEntity newCustomer = new CustomerEntity();
			newCustomer.setName("customer" + x);
			newCustomer.setActive_days_points(10000 * (x + 1));
			newCustomer.setStore(store);
			customerRepository.save(newCustomer);

			ProductsEntity newProduct = new ProductsEntity();
			newProduct.setProductName("product" + x);
			newProduct.setProductCode("MOM-" + x);
			newProduct.setCost_points(20000 * (x + 1));
			newProduct.setStore(store);
			productsRepository.save(newProduct);
		}
	}
}
