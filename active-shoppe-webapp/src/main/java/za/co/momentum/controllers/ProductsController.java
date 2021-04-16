package za.co.momentum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import za.co.momentum.api.store.product.ProductApi;
import za.co.momentum.api.store.product.model.ProductsPage;
import za.co.momentum.service.service.products.ProductService;

@Controller
public class ProductsController implements ProductApi {

	@Autowired
	private ProductService productService;

	@Override
	public ResponseEntity<ProductsPage> getProducts(String storeName, int pageNo, int pageSize) {
		return ResponseEntity.ok(productService.getAllStoreProducts(storeName, pageNo, pageSize));
	}

	@Override
	public ResponseEntity<Void> purchaseProduct(String prodCode, Long custId) {
		productService.purchaseProduct(custId, prodCode);
		return ResponseEntity.ok().build();
	}
}
