package za.co.momentum.service.service.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.momentum.api.store.model.Store;
import za.co.momentum.api.store.model.StoresPage;
import za.co.momentum.service.store.model.StoreEntity;
import za.co.momentum.service.store.repository.StoreRepository;

import javax.transaction.Transactional;

@Service
public class StoreService {

	@Autowired
	private StoreRepository storeRepository;


	@Transactional
	public StoresPage getAllStores(int pageNo, int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("storeName").descending());
		Page<Store> stores = storeRepository.findAll(pageRequest).map(this::convertStoreEntity);

		//Store all stores in dto
		StoresPage page = new StoresPage();
		page.setStores(stores.getContent());
		page.setTotalElements(stores.getTotalElements());
		return page;
	}

	private Store convertStoreEntity(StoreEntity storeEntity) {
		Store storeDto = new Store();
		storeDto.setId(storeEntity.getId());
		storeDto.setStoreName(storeEntity.getStoreName());
		return storeDto;
	}
}
