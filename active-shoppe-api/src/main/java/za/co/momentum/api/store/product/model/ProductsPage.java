package za.co.momentum.api.store.product.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductsPage {
    private List<Product> products;
    private Long totalElements;
}
