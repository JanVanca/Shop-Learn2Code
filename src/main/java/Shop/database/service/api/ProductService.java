package Shop.database.service.api;

import Shop.database.service.api.request.UpdateProductRequest;
import Shop.domain.Product;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();
    @Nullable
    Product getProduct(int id);
    @Nullable
    Integer addProduct(Product product);
    void deleteProduct(int id);
    void updateProduct(int id, UpdateProductRequest request);

    void updateAvailableInternal(int id, int newAvailable);

}
