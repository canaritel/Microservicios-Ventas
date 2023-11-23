package es.televoip.microserv;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import es.televoip.microserv.entity.Category;
import es.televoip.microserv.entity.Product;
import es.televoip.microserv.repository.ProductRepository;
import es.televoip.microserv.service.ProductServiceImpl;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // indicará al marco de pruebas que debe reinicializar el contexto de la aplicación después de cada prueba
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class ProductServiceMockTest {
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks // Inyecta los mocks en el servicio (no puede ser interfaz)
	private ProductServiceImpl productService;
	//private ProductService productService; //si se desea por interfaz entonces activar .openMocks(this)
	
	@BeforeEach
	public void setup() {
		//MockitoAnnotations.openMocks(this); // si se activa recomendado quitar anotación @InjectMocks
		productService = new ProductServiceImpl(productRepository);
		
		Product product01 = Product.builder()
				.id(5L)
				.name("computer")
				.category(Category.builder().id(1L).build())
				.description("description computer")
				.stock(Double.parseDouble("5"))
				.price(Double.parseDouble("11.11"))
				//.status("Created")
				//.createAt(LocalDateTime.now())
				.build();
		
		Mockito.when(productRepository.findById(5L))
			.thenReturn(Optional.of(product01));
		
		// Grabamos el objeto para poder consultarlo en los métodos @Test
		Mockito.when(productRepository.save(product01))
			.thenReturn(product01);
	}
	
	@Test
	void whenValidGetID_ThenReturnProduct() {
		Product found = productService.getProduct(5L);
		Assertions.assertThat(found.getName()).isEqualTo("computer");
	}
	
	@Test
	void whenValidUpdateStock_ThenReturnNewStock() {
		Product newStock = productService.updateStock(5L, Double.parseDouble("8"));
		Assertions.assertThat(newStock.getStock()).isEqualTo(13);
	}

}
