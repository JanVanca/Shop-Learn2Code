package Shop.database.service.api;

import Shop.domain.Customer;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();
    @Nullable
    Customer getCustomer(int id);
    @Nullable
    Integer addCustomer(Customer customer);
}
