package production.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import production.model.Details;
import production.services.DetailsService; // Підключаємо Сервіс

import java.util.List;

@RestController
@RequestMapping("/api/v1/details")
public class DetailsController {

    private final DetailsService detailsService;

    public DetailsController(DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    // Отримати всі
    @GetMapping
    public ResponseEntity<List<Details>> getAllDetails() {
        return ResponseEntity.ok(detailsService.findAll());
    }

    // Отримати по ID вузла
    @GetMapping("/details_component/{componentId}")
    public ResponseEntity<List<Details>> getDetailsByComponent(@PathVariable Integer componentId) {
        return ResponseEntity.ok(detailsService.findByComponentId(componentId));
    }

    // Додати/Оновити 
    @PostMapping("/details_save")
    public ResponseEntity<String> saveDetail(@RequestBody Details detail) {
        detailsService.save(detail);
        return ResponseEntity.ok("Detail saved successfully");
    }

    // Видалити
    @PostMapping("/details_delete/{id}")
    public ResponseEntity<String> deleteDetail(@PathVariable Integer id) {
        detailsService.deleteById(id);
        return ResponseEntity.ok("Detail deleted successfully");
    }
}