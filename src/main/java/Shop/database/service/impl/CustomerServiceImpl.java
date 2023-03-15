package Shop.database.service.impl;

import Shop.database.repository.CustomerRepository;
import Shop.database.service.api.CustomerService;
import Shop.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.getCustomers();
    }

    @Override
    public Customer getCustomer(int id) {
       return customerRepository.getCustomer(id);
    }

    @Override
    public Integer addCustomer(Customer customer) {
        return customerRepository.addCustomer(customer);
    }
}
