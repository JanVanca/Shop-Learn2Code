package Shop.controller;

import Shop.database.service.api.ProductService;
import Shop.database.service.api.request.UpdateProductRequest;
import Shop.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") int id) {
        if (productService.getProduct(id) != null) {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Product with id:" + id + " does not exist");
        }
    }


    @PatchMapping("{id}")
    public ResponseEntity updateProduct(@PathVariable("id") int id, @RequestBody UpdateProductRequest request) {
        if (productService.getProduct(id) != null) {
            productService.updateProduct(id, request);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Product with id:" + id + " does not exist");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity getProduct(@PathVariable("id") int id) {
        Product product = productService.getProduct(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<Product> productList = productService.getProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addProduct(@RequestBody Product product) {
        Integer id = productService.addProduct(product);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
