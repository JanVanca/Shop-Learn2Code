package Shop;


import Shop.database.service.api.CustomerService;
import Shop.database.service.api.MerchantService;
import Shop.database.service.api.ProductService;
import Shop.database.service.api.request.UpdateProductRequest;
import Shop.domain.Customer;
import Shop.domain.Merchant;
import Shop.domain.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode =  DirtiesContext.ClassMode.BEFORE_CLASS)
public class DBServiceTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ProductService productService;

    private  Merchant merchant;
    @Before
    public void createMerchant() {
        if (merchant == null) {
            merchant = new Merchant("Merchant meno", "merchant.@email.sk", "Kosice");
            Integer id = merchantService.addMerchant(merchant);
            assert id != null;
            merchant.setId(id);
        }
    }

    @Test
    public void customer() {
        Customer customer = new Customer("Ferko", "Mrkvicka", "ferko.mrkvicka@email.sk", "Presov", 25, "0900 111 222");
        Integer id = customerService.addCustomer(customer);
        assert id != null;
        customer.setId(id);

        Customer customerFromDB = customerService.getCustomer(id);
        Assert.assertEquals(customer,customerFromDB);

        List<Customer> customerList = customerService.getCustomers();
        Assert.assertEquals(1, customerList.size());

        Assert.assertEquals(customer, customerList.get(0));
    }

    @Test
    public void merchant() {
        Merchant merchantFromDB = merchantService.getMerchant(merchant.getId());
        Assert.assertEquals(merchant, merchantFromDB);

        List<Merchant> merchantList = merchantService.getMerchants();
        Assert.assertEquals(1, merchantList.size());

        Assert.assertEquals(merchant, merchantList.get(0));
    }

    @Test
    public void product() {
        Product product = new Product(merchant.getId(), "Jogobela", "Jogurt", 5, 1);
        Integer id = productService.addProduct(product);
        assert id != null;
        product.setId(id);

        Product productFromDB = productService.getProduct(id);
        Assert.assertEquals(product, productFromDB);

        List<Product> productList = productService.getProducts();
        Assert.assertEquals(1, productList.size());

        Assert.assertEquals(product, productList.get(0));

        product.setAvailable(10);
        UpdateProductRequest productRequest = new UpdateProductRequest(product.getName(), product.getDescription(), product.getPrice(), product.getAvailable());

        productService.updateProduct(id, productRequest);

        Product productFromDBAfterUpdate = productService.getProduct(id);
        Assert.assertEquals(product, productFromDBAfterUpdate);
        Assert.assertNotEquals(productFromDB, productFromDBAfterUpdate);

        productService.deleteProduct(id);
        Assert.assertEquals(0, productService.getProducts().size());



    }
}
