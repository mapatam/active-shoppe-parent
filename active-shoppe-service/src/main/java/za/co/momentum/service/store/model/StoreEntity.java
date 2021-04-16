package za.co.momentum.service.store.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import za.co.momentum.service.customer.model.CustomerEntity;
import za.co.momentum.service.products.model.ProductsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class StoreEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@Column(unique = true)
	private String storeName;

}
