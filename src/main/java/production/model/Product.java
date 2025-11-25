package production.model;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("products")
public record Product(
	@Id UUID id, 
	String name, 
	String serialNumber, 
	String category	
) {}