package production.repository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Імпорти твоїх класів
import production.controller.DetailsController;
import production.model.Details;

import java.util.List;

@RestController
@RequestMapping("/api/v1/details")
public class DetailsRepository {

    private final DetailsController detailsController;

    public DetailsRepository(DetailsController detailsController) {
        this.detailsController = detailsController;
    }

    // Отримати всі деталі
    @GetMapping
    ResponseEntity<List<Details>> getAllDetails() {
        return ResponseEntity.ok(detailsController.findAll());
    }

    // Отримати деталі, що входять до конкретного вузла (по ID вузла)
    @GetMapping("/component/{componentId}")
    ResponseEntity<List<Details>> getDetailsByComponent(@PathVariable Integer componentId) {
        return ResponseEntity.ok(detailsController.findByComponentId(componentId));
    }
}