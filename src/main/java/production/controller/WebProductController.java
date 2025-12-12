package production.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import production.model.Product;
import production.services.ProductService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import java.util.UUID;

@Controller
public class WebProductController {

    private final ProductService productService;

    public WebProductController(ProductService productService) {
        this.productService = productService;
    }

    // Список всіх виробів
    @GetMapping("/products")
    public String showProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products";
    }

    // Форма створення/редагування
    @GetMapping("/admin/products/form")
    @PreAuthorize("hasRole('ADMIN')")
    public String productForm(@RequestParam(required = false) UUID id, Model model) {
        if (id != null) {
            Product product = productService.findById(id).orElse(new Product(null, "", "", ""));
            product.setIsNew(false); 
            model.addAttribute("product", product);
        } else {
            // Новий виріб
            Product newProduct = new Product(null, "", "", "");
            newProduct.setIsNew(true);
            model.addAttribute("product", newProduct);
        }
        return "product-form";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    // Збереження
    @PostMapping("/admin/products/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveProduct(@ModelAttribute Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID()); 
            product.setIsNew(true);
        } else {
            product.setIsNew(false);
        }
        
        productService.save(product);
        return "redirect:/products";
    }

    // Видалення
    @PostMapping("/admin/products/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProduct(@PathVariable UUID id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}