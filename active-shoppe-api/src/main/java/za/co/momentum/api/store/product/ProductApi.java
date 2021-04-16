package za.co.momentum.api.store.product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import za.co.momentum.api.store.product.model.ProductsPage;

@Api(tags = "Products API")
@RequestMapping(path = "momentum/api",
		consumes = {MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE},
		produces = {MediaType.APPLICATION_JSON_VALUE})
public interface ProductApi {

	@ApiOperation("List all products in a store")
	@GetMapping("/store/products")
	ResponseEntity<ProductsPage> getProducts(@RequestParam("storeName") String storeName,
											  @RequestParam("page") int pageNo,
											  @RequestParam("size") int pageSize);

	@ApiOperation("Purchase a product from store")
	@PutMapping(path = "/store/purchase")
	ResponseEntity<Void> purchaseProduct(@RequestParam(value = "prodCode", required = false) String prodCode,
										 @RequestParam(value = "custId") Long custId);
}
