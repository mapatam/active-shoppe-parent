package za.co.momentum.service.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.momentum.service.store.model.StoreEntity;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

}
