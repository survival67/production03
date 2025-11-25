package production.controller;

import org.springframework.data.repository.ListCrudRepository;
import production.model.Details;
import java.util.List;

public interface DetailsController extends ListCrudRepository<Details, Integer> {
    // Знайти всі деталі конкретного вузла
    List<Details> findByComponentId(Integer componentId);
}
