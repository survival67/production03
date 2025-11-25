package production.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("details")
public record Details(
    @Id Integer id, 
    String name, 
    String material, 
    Integer componentId // Зовнішній ключ на Components
) {}
