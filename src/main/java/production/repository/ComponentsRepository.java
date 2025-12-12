package production.repository;

import org.springframework.data.repository.ListCrudRepository;
import production.model.Components;
import java.util.List;
import java.util.UUID;

public interface ComponentsRepository extends ListCrudRepository<Components, Integer> {
    List<Components> findByProductId(UUID productId);
}