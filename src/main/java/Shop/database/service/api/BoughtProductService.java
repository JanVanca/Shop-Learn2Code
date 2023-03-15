package Shop.database.service.api;

import Shop.domain.BoughtProduct;

import java.util.List;

public interface BoughtProductService {

    void add(BoughtProduct boughtProduct);
    List<BoughtProduct> getAllByCustomerId(int customerId);

}
