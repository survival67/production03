package production.repository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Імпорти твоїх класів
import production.controller.ComponentsController;
import production.model.Components;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/components")
public class ComponentsRepository {

    private final ComponentsController componentsController;

    public ComponentsRepository(ComponentsController componentsController) {
        this.componentsController = componentsController;
    }

    // Отримати всі вузли
    @GetMapping
    ResponseEntity<List<Components>> getAllComponents() {
        return ResponseEntity.ok(componentsController.findAll());
    }

    // Отримати вузли, що належать конкретному виробу (по ID виробу)
    @GetMapping("/product/{productId}")
    ResponseEntity<List<Components>> getComponentsByProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(componentsController.findByProductId(productId));
    }
}