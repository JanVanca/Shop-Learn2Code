package Shop.controller;

import Shop.database.service.api.BoughtProductService;
import Shop.domain.BoughtProduct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bought-product")
public class BoughtProductController {
    private final BoughtProductService boughtProductService;

    public BoughtProductController(BoughtProductService boughtProductService) {
        this.boughtProductService = boughtProductService;
    }

    @GetMapping("{customerId}")
    public ResponseEntity getByCustomerId(@PathVariable("customerId") int customerId) {
        List<BoughtProduct> boughtProductList = boughtProductService.getAllByCustomerId(customerId);
        return new ResponseEntity<>(boughtProductList, HttpStatus.OK);
    }

}
