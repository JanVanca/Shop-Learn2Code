package Shop.database.service.api;

import Shop.database.service.api.request.BuyProductRequest;
import Shop.database.service.api.resposnse.BuyProductResponse;

public interface ShoppingService {

    BuyProductResponse buyProduct(BuyProductRequest request);
}
