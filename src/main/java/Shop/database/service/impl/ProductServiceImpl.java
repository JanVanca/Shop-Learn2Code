package Shop.database.service.impl;

import Shop.database.repository.ProductRepository;
import Shop.database.service.api.ProductService;
import Shop.database.service.api.request.UpdateProductRequest;
import Shop.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
       return productRepository.getProducts();
    }

    @Override
    public Product getProduct(int id) {
       return productRepository.getProduct(id);
    }

    @Override
    public Integer addProduct(Product product) {
      return productRepository.addProduct(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteProduct(id);
    }

    @Override
    public void updateProduct(int id, UpdateProductRequest request) {
        productRepository.updateProduct(id, request);
    }

    @Override
    public void updateAvailableInternal(int id, int newAvailable) {
        productRepository.updateAvailable(id, newAvailable);
    }


}
