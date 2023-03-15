package Shop;

import Shop.database.service.api.request.UpdateProductRequest;
import Shop.domain.Customer;
import Shop.domain.Merchant;
import Shop.domain.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureMockMvc
public class RestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private Merchant merchant;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void createMerchant() throws Exception {
        /*
        Add merchant
         */
        if (merchant == null) {
            merchant = new Merchant("Merchant meno", "merchant.meno@test.sk", "Bratislava Stare mesto");
            String id = mockMvc.perform(post("/merchant")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(merchant)))
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();
            merchant.setId(objectMapper.readValue(id, Integer.class));
        }
    }

    @Test
    public void product() throws Exception {
        Product product = new Product(merchant.getId(), "Susienky", "Najlepsie susienky", 10.25, 11);

        /*
        Add product
         */
        String id = mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        product.setId(objectMapper.readValue(id, Integer.class));

        /*
        Get product
         */
        String returnedProduct = mockMvc.perform(get("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Product productFromRest = objectMapper.readValue(returnedProduct, Product.class);
        Assert.assertEquals(product, productFromRest);

        /*
        Get all products
         */
        String listJson = mockMvc.perform(get("/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Product> productList = objectMapper.readValue(listJson, new TypeReference<List<Product>>() {
        });

        assert productList.size() == 1;
        Assert.assertEquals(product, productList.get(0));

        /*
        Update product
         */
        double updatedPrice = product.getPrice() + 1;
        int updatedAvailable = product.getAvailable() + 5;
        UpdateProductRequest updateProductRequest = new UpdateProductRequest(product.getName(), product.getDescription(), updatedPrice, updatedAvailable);
        mockMvc.perform(patch("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProductRequest)))
                .andExpect(status().isOk());

        String returnedUpdatedProduct = mockMvc.perform(get("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Product updatedProduct = objectMapper.readValue(returnedUpdatedProduct, Product.class);
        assert updatedPrice == updatedProduct.getPrice();
        assert updatedAvailable == updatedProduct.getAvailable();

        /*
        Delete product
         */
        mockMvc.perform(delete("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        String listJson2 = mockMvc.perform(get("/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Product> productList2 = objectMapper.readValue(listJson2, new TypeReference<List<Product>>() {
        });

        assert productList2.size() == 0;


    }

    @Test
    public void customer() throws Exception {
        /*
        Add customer
         */
        Customer customer = new Customer("Ferko", "Mrkvicka", "ferko.mrkvica@test.com", "Bratislava", 21, "0900 555 666");
        String id = mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        customer.setId(objectMapper.readValue(id, Integer.class));

        /*
        Get customer
         */
        String customerJson = mockMvc.perform(get("/customer/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Customer returnedCustomer = objectMapper.readValue(customerJson, Customer.class);
        Assert.assertEquals(customer, returnedCustomer);

        /*
        Get all customers
         */
        String listJson = mockMvc.perform(get("/customer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Customer> customerList = objectMapper.readValue(listJson, new TypeReference<List<Customer>>() {
        });
        assert customerList.size() == 1;
        Assert.assertEquals(customer, customerList.get(0));
    }

    @Test
    public void merchant() throws Exception {
        /*
        Get merchant
         */
        String merchantJson = mockMvc.perform(get("/merchant/" + merchant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Merchant returnedMerchant = objectMapper.readValue(merchantJson, Merchant.class);
        Assert.assertEquals(merchant, returnedMerchant);

        /*
        Get all merchants
         */
        String listJson = mockMvc.perform(get("/merchant")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Merchant> merchantList = objectMapper.readValue(listJson, new TypeReference<List<Merchant>>() {
        });
        assert merchantList.size() == 1;
        Assert.assertEquals(merchant, merchantList.get(0));
    }
}
