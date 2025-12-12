package production.repository;

import org.springframework.data.repository.ListCrudRepository;
import production.model.Product;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends ListCrudRepository<Product, UUID> {
    List<Product> findByCategory(String category);
}