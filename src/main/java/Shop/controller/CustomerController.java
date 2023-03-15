package Shop.controller;

import Shop.database.service.api.CustomerAccountService;
import Shop.database.service.api.CustomerService;
import Shop.domain.Customer;
import Shop.domain.CustomerAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerAccountService customerAccountService;



    public CustomerController(CustomerService customerService, CustomerAccountService customerAccountService) {
        this.customerService = customerService;
        this.customerAccountService = customerAccountService;
    }


    @PostMapping()
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        Integer id = customerService.addCustomer(customer);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        Customer customer = customerService.getCustomer(id);
        if (customer == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity getAll() {
       List<Customer> customerList =  customerService.getCustomers();
       return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @PostMapping("/account")
    public ResponseEntity addAccount(@RequestBody CustomerAccount customerAccount) {
        customerAccountService.addCustomerAccount(customerAccount);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

}
