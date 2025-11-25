package production.controller;

import org.springframework.data.repository.ListCrudRepository;
import production.model.Components;
import java.util.List;
import java.util.UUID;

public interface ComponentsController extends ListCrudRepository<Components, Integer> {
    // Знайти всі вузли для конкретного виробу
    List<Components> findByProductId(UUID productId);
}
