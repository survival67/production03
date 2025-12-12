package production.repository;

import org.springframework.data.repository.ListCrudRepository;
import production.model.Details;
import java.util.List;

public interface DetailsRepository extends ListCrudRepository<Details, Integer> {

    // Знайти всі деталі конкретного вузла
    List<Details> findByComponentId(Integer componentId);

    // Знайти деталі, яких на складі більше ніж X штук
    List<Details> findByQuantityGreaterThan(Integer quantity);

    // Знайти всі деталі за конкретним матеріалом
    List<Details> findByMaterial(String material);
}