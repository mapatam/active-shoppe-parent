package za.co.momentum.api.store.product.model;

import lombok.Data;
import za.co.momentum.api.store.model.Store;

import java.util.UUID;

@Data
public class Product {
	private Long id;
	private int cost_points;
	private String productName;
	private String productCode;
	private Store store;
}
