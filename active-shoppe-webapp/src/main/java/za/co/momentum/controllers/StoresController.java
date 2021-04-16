package za.co.momentum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import za.co.momentum.api.store.StoreApi;
import za.co.momentum.api.store.model.StoresPage;
import za.co.momentum.service.service.store.StoreService;

@Controller
public class StoresController implements StoreApi {

	@Autowired
	private StoreService storeService;

	@Override
	public ResponseEntity<StoresPage> getStores(int pageNo, int pageSize) {
		return ResponseEntity.ok(storeService.getAllStores(pageNo, pageSize));
	}
}
