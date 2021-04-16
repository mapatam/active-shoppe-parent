package za.co.momentum.service.products.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import za.co.momentum.service.store.model.StoreEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class ProductsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	private int cost_points;

	@Column(unique = true)
	private String productName;

	@Column(unique = true, length = 5)
	private String productCode;

	@ManyToOne
	private StoreEntity store;
}
