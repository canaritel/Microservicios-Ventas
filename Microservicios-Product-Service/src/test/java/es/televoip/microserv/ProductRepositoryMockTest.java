package es.televoip.microserv;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import es.televoip.microserv.repository.CategoryRepository;
import es.televoip.microserv.repository.ProductRepository;
import es.televoip.microserv.entity.Category;
import es.televoip.microserv.entity.Product;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // indicará al marco de pruebas que debe reinicializar el contexto de la aplicación después de cada prueba
@DataJpaTest(properties = "spring.config.location=classpath:application-test.yml")
public class ProductRepositoryMockTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@BeforeEach
	public void prepareTestData() {
	    // Insertamos categories si no existen
	    insertCategoryIfNotExists(1, "shoes");
	    insertCategoryIfNotExists(2, "books");
	    insertCategoryIfNotExists(3, "electronics");

	    // Insertamos productos
	    insertProduct(1L, "Spring Boot in Action", "Craig Walls is a software developer at Pivotal and is the author of Spring in Action", 12.0, 40.06, "CREATED", LocalDateTime.parse("2018-09-05T00:00:00"), 2L);
	    insertProduct(2L, "adidas Cloudfoam Ultimate", "Walk in the air in the black / black CLOUDFOAM ULTIMATE running shoe from ADIDAS", 5.0, 178.89, "CREATED", LocalDateTime.parse("2018-09-05T00:00:00"), 1L);
	    insertProduct(3L, "under armour Mens Micro G Assert – 7", "under armour Mens Lightweight mesh upper delivers complete breathability . Durable leather overlays for stability", 4.0, 12.5, "CREATED", LocalDateTime.parse("2018-09-05T00:00:00"), 1L);
	}

	
			
	@Test
	public void whenFindByCategory_thenReturnListProduct() {
				
		Product product01 = Product.builder()
				.id(5L) // Generate a unique ID
				.name("shoes nike")
				.category(Category.builder().id(1L).build())
				.description("description shoes nike")
				.stock(Double.parseDouble("10"))
				.price(Double.parseDouble("10.10"))
				.status("Created")
				.createAt(LocalDateTime.now())
				.build();
		
		productRepository.saveAndFlush(product01);
				
		List<Product> founds = productRepository.findByCategory(Category.builder().id(1L).build());
	    
	    System.out.println(founds.toString());

	    Assertions.assertThat(founds.size()).isEqualTo(3);
	}
		
	private void insertCategoryIfNotExists(long id, String name) {
	    if (!categoryRepository.existsById(id)) {
	        Category category = new Category();
	        category.setId(id);
	        category.setName(name);
	        categoryRepository.save(category);
	    }
	}

	private void insertProduct(long id, String name, String description, double stock, double price, String status, LocalDateTime createAt, long categoryId) {
	    Product product = new Product();
	    product.setId(id);
	    product.setName(name);
	    product.setDescription(description);
	    product.setStock(stock);
	    product.setPrice(price);
	    product.setStatus(status);
	    product.setCreateAt(createAt);
	    product.setCategory(categoryRepository.findById(categoryId).get());
	    productRepository.save(product);
	}
	
}
