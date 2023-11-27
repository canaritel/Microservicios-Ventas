package es.televoip.microserv.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import es.televoip.microserv.entity.Category;
import es.televoip.microserv.entity.Product;
import es.televoip.microserv.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	/**
	 * Método para listas todos los productos
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Product>> listProduct() {
		List<Product> products = productService.listAllProduct();

		if (products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(products);
	}

	/**
	 * Método para listar filtrando por categoria los productos
	 * 
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/filter")
	public ResponseEntity<List<Product>> listProductFilter(
			@RequestParam(name = "categoryId", required = false) Long categoryId) {

		List<Product> products;

		if (null == categoryId) {
			products = productService.listAllProduct();
			if (products.isEmpty()) {
				return ResponseEntity.noContent().build(); // devuelve 'no contiene'
			}
		} else {
			products = productService.findByCategory(Category.builder().id(categoryId).build());
			if (products.isEmpty()) {
				return ResponseEntity.notFound().build(); // devuelve 'no encuentra'
			}
		}

		return ResponseEntity.ok(products);
	}

	/**
	 * Método para obtener un producto
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
		log.info("Fetching Product with id {}", id);
		Product product = productService.getProduct(id);

		return ResponseEntity.ok(product);
	}

	/**
	 * Método para crear el producto
	 * 
	 * @param product
	 * @return
	 */
	@PostMapping // Recomendado según el caso crear un mecanimo de Idempotencia!!!
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		log.info("Creating Product : {}", product);
		Product productCreate = productService.createProduct(product);

		return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
	}

	/**
	 * Método para actualizar un producto
	 * 
	 * @param id
	 * @param product
	 * @return
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody Product product) {
		log.info("Updating Product with id {}", id);
		product.setId(id);
		Product productUpdate = productService.updateProduct(product);

		return ResponseEntity.ok(productUpdate);
	}

	/**
	 * Método para borrar un producto
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
		log.info("Fetching & Deleting Product with id {}", id);
		Product productDelete = productService.deleteProduct(id);

		return ResponseEntity.ok(productDelete);
	}

	/**
	 * Método para añadir stock a un producto
	 * 
	 * @param id
	 * @param quantity
	 * @return
	 */
	@GetMapping(value = "/stock/{id}")
	public ResponseEntity<Product> stockProduct(@PathVariable("id") Long id,
			@RequestParam(name = "quantity", required = true) Double quantity) {

		Product product = productService.updateStock(id, quantity);

		return ResponseEntity.ok(product);
	}

}
