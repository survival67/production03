package production.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

// 
@Table("components") 
public record Components(
    @Id Integer id, 
    String name, 
    String description, 
    UUID productId // Зовнішній ключ на Products
) {}