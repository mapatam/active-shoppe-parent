package za.co.momentum.api.store;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import za.co.momentum.api.store.model.StoresPage;

@Api(tags = "Stores API")
@RequestMapping(path = "momentum/api",
		consumes = {MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE},
		produces = {MediaType.APPLICATION_JSON_VALUE})
public interface StoreApi {
	@ApiOperation("List all stores")
	@GetMapping("/stores")
	ResponseEntity<StoresPage> getStores(@RequestParam(value = "page") int pageNo,
										 @RequestParam(value = "size") int pageSize);
}
