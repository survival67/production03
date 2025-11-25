package production.repository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import production.controller.ProductController;
import production.model.Product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRepository {

    private final ProductController productController;

    public ProductRepository(ProductController productController) {
        this.productController = productController;
    }

    // Отримати всі вироби
    @GetMapping
    ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productController.findAll());
    }

    // Отримати вироби за категорією (виріб / вузол / деталь)
   // @GetMapping("/{category}")
   // ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
    //    return ResponseEntity.ok(productController.findByCategory(category));
 //   }
}
