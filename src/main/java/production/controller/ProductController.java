package production.controller;

import org.springframework.data.repository.ListCrudRepository;

import production.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductController extends ListCrudRepository<Product, UUID> {

    // Пошук виробів за категорією (вузли, деталі, вироби)
    List<Product> findByCategory(String category);
}
