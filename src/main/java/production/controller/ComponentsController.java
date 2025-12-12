package production.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import production.model.Components;
import production.services.ComponentsService; // Підключаємо Сервіс

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/components")
public class ComponentsController {

    private final ComponentsService componentsService;

    public ComponentsController(ComponentsService componentsService) {
        this.componentsService = componentsService;
    }

    // Отримати всі
    @GetMapping
    public ResponseEntity<List<Components>> getAllComponents() {
        return ResponseEntity.ok(componentsService.findAll());
    }

    // Отримати по ID виробу (фільтрація)
    @GetMapping("/components_product/{productId}")
    public ResponseEntity<List<Components>> getComponentsByProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(componentsService.findByProductId(productId));
    }

    // Додати/Оновити
    @PostMapping("/components_save")
    public ResponseEntity<String> saveComponent(@RequestBody Components component) {
        componentsService.save(component);
        return ResponseEntity.ok("Component saved successfully");
    }

    // Видалити
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteComponent(@PathVariable Integer id) {
        componentsService.deleteById(id);
        return ResponseEntity.ok("Component deleted successfully");
    }
}