package production.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import production.controller.DatabaseController;
import production.model.Product;
import production.services.ProductService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional 
class ProductionApplicationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private DatabaseController databaseController;

    // Перевірка, що Spring Context піднявся і контролер створився
    @Test
    void contextLoads() {
        assertNotNull(productService, "Сервіс має бути створений");
        assertNotNull(databaseController, "Контролер має бути створений");
    }

    //  Unit-тест логіки моделі
    @Test
    void testProductModelLogic() {
        UUID id = UUID.randomUUID();
        Product product = new Product(id, "Test Motor", "SN-111", "Engines");

        // Перевіряємо початковий стан
        assertTrue(product.isNew(), "Новий об'єкт має бути isNew=true");

        // Змінюємо стан
        product.setIsNew(false);

        // Перевіряємо зміну
        assertFalse(product.isNew(), "Після зміни має бути isNew=false");
    }

    // Тест Сервісу, працює з базою
    @Test
    void testRealDatabaseInsertAndRead() {
        // Створюємо продукт
        String uniqueName = "Integration Test Product " + UUID.randomUUID();
        Product p = new Product(UUID.randomUUID(), uniqueName, "TEST-SN", "Test Cat");
        
        // Зберігаємо в  БД
        productService.save(p);

        // Дістаємо всі продукти з БД
        List<Product> allProducts = productService.findAll();

        // Шукаємо наш продукт у списку
        boolean exists = allProducts.stream()
                .anyMatch(prod -> prod.getName().equals(uniqueName));

        // Перевіряємо, що він знайшовся
        assertTrue(exists, "Продукт має бути збережений у базі даних");
    }
}