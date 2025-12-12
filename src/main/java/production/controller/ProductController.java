package production.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import production.model.Product;
import production.services.ProductService; 

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Отримати всі
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    // Додати або Оновити 
    @PostMapping("/save_products")
    public ResponseEntity<String> saveProduct(@RequestBody Product product) {
        productService.save(product);
        return ResponseEntity.ok("Product saved successfully");
    }

    // Видалити
    @PostMapping("/delete_products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}