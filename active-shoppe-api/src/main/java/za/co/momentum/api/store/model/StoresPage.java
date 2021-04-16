package za.co.momentum.api.store.model;

import lombok.Data;
import za.co.momentum.api.store.product.model.Product;

import java.util.List;

@Data
public class StoresPage {
    private List<Store> stores;
    private Long totalElements;
}
