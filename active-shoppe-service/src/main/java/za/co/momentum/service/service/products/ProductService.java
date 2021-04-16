package za.co.momentum.service.service.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.momentum.api.store.model.Store;
import za.co.momentum.api.store.product.model.Product;
import za.co.momentum.api.store.product.model.ProductsPage;
import za.co.momentum.service.customer.model.CustomerEntity;
import za.co.momentum.service.customer.repository.CustomerRepository;
import za.co.momentum.service.exception.ExpectationFailedException;
import za.co.momentum.service.products.model.ProductsEntity;
import za.co.momentum.service.products.repository.ProductsRepository;
import za.co.momentum.service.store.model.StoreEntity;
import za.co.momentum.service.store.repository.StoreRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProductService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProductsRepository productsRepository;

	@Transactional
	public ProductsPage getAllStoreProducts(String storeName, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("productName").ascending());

		StoreEntity probe = new StoreEntity();
		probe.setStoreName(storeName);

		ProductsEntity product = new ProductsEntity();
		product.setStore(probe);

		Example<ProductsEntity> productEntityExample = Example.of(product, ExampleMatcher.matchingAll()
				.withIgnorePaths("productCode", "cost_points", "productName"));

		Page<Product> productsSearchResults = productsRepository.findAll(productEntityExample, pageable)
				.map(this::convertProductEntity);

		ProductsPage page = new ProductsPage();
		page.setProducts(productsSearchResults.getContent());
		page.setTotalElements(productsSearchResults.getTotalElements());
		return page;
	}

	private Product convertProductEntity(ProductsEntity product) {
		Product productDto = new Product();
		productDto.setId(product.getId());
		productDto.setProductName(product.getProductName());
		productDto.setProductCode(product.getProductCode());
		productDto.setCost_points(product.getCost_points());
		productDto.setStore(convertStoreEntity(product.getStore()));
		return productDto;
	}

	private Store convertStoreEntity(StoreEntity storeEntity) {
		Store storeDto = new Store();
		storeDto.setId(storeEntity.getId());
		storeDto.setStoreName(storeEntity.getStoreName());
		return storeDto;
	}

	@Transactional
	public void purchaseProduct(Long custId, String prodCode) {

		//Check if customer chose a product
		if (prodCode == null){
			throw new ExpectationFailedException("The customer did not provide any products to purchase.");
		}

		Optional<CustomerEntity> customer = customerRepository.findById(custId);
		Optional<ProductsEntity> product = productsRepository.findByProductCodeIgnoreCase(prodCode);

		//Check if customer exists
		if (!customer.isPresent())	{
			throw new ExpectationFailedException("The customer’s ID does not exist in the store.");
		}

		//Check if product exists
		if (!product.isPresent()){
			throw new ExpectationFailedException("The customer chose a non-existent product code.");
		}

		//Check if the customer has enough points to buy the selected product
		if (product.get().getCost_points() > customer.get().getActive_days_points()){
			throw new ExpectationFailedException("The customer does not have enough points");
		}else {
			//Customer successfully buys the product(s)
			int customerPointsRemainder = customer.get().getActive_days_points() - product.get().getCost_points();
			customer.get().setActive_days_points(customerPointsRemainder);
			customerRepository.save(customer.get());
		}
	}
}
