package Shop.database.service.impl;

import Shop.database.service.api.*;
import Shop.database.service.api.request.BuyProductRequest;
import Shop.database.service.api.resposnse.BuyProductResponse;
import Shop.domain.BoughtProduct;
import Shop.domain.Customer;
import Shop.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    private final ProductService productService;
    private final CustomerService  customerService;
    private final CustomerAccountService customerAccountService;
    private final BoughtProductService boughtProductService;

    public ShoppingServiceImpl(ProductService productService, CustomerService customerService, CustomerAccountService customerAccountService, BoughtProductService boughtProductService) {
        this.productService = productService;
        this.customerService = customerService;
        this.customerAccountService = customerAccountService;
        this.boughtProductService = boughtProductService;
    }

    @Override
    public BuyProductResponse buyProduct(BuyProductRequest request) {
        int productId = request.getProductId();
        int customerId = request.getCustomerId();

        Product product = productService.getProduct(productId);
        if (product == null) {
            return new BuyProductResponse(false, "Product with id: " + productId + " does not exist");
        }

        Customer customer = customerService.getCustomer(customerId);

        if (customer == null) {
            return new BuyProductResponse(false, "Customer with id: " + customerId + " does not exist");
        }

        if (product.getAvailable() < request.getQuantity()) {
            return new BuyProductResponse(false, "Not enough products in stock");

        }

        Double customerMoney = customerAccountService.getMoney(customerId);
        if (customerMoney == null) {
            return new BuyProductResponse(false, "Customer with id: "+ customerId + " does not have account");
        } else {
          double totalPriceOfRequest = product.getPrice() * request.getQuantity();
          if (customerMoney >= totalPriceOfRequest) {
              productService.updateAvailableInternal(productId, product.getAvailable() - request.getQuantity());
              customerAccountService.setMoney(customerId, customerMoney - totalPriceOfRequest);
              boughtProductService.add(new BoughtProduct(productId, customerId, request.getQuantity()));
              return new BuyProductResponse(true);

          } else {
            return new BuyProductResponse(false, "Customer with id: " + customerId + " does not have enough money");
          }
        }

    }
}
