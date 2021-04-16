package za.co.momentum.service.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.momentum.service.customer.model.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}
