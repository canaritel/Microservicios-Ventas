package es.televoip.microserv.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import es.televoip.microserv.entity.Category;
import es.televoip.microserv.entity.Product;
import es.televoip.microserv.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // ideal para inyectar objetos por constructor
@Transactional
@Validated
public class ProductServiceImpl implements ProductService {

	// Sin usar Autowired pero por Constructor y usando RequiredArgsConstructor
	private final ProductRepository productRepository;

	@Override
	public List<Product> listAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public Product getProduct(Long id) {
		return productRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Product Id: El Producto con Id " + id + " no se encuentra."));
	}

	@Override
	public Product createProduct(Product product) {
		checkProductNameAvailability(product.getName());
		product.setStatus("CREATED");

		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		Product productDB = this.getProduct(product.getId());

		if (!product.getName().equals(productDB.getName())) {
			checkProductNameAvailability(product.getName());
		}

		productDB.setName(product.getName());
		productDB.setDescription(product.getDescription());
		productDB.setPrice(product.getPrice());
		productDB.setCategory(product.getCategory());

		return productRepository.save(productDB);
	}

	@Override
	public Product deleteProduct(Long id) {
		Product productDB = this.getProduct(id);

		productDB.setStatus("DELETED"); // no eliminamos de la DB, solo cambiamos estado
		return productRepository.save(productDB);
	}

	@Override
	public Product updateStock(Long id, Double quantity) {
		Product productDB = this.getProduct(id);
		
		Double stock = productDB.getStock() + quantity;
		productDB.setStock(stock);

		return productRepository.save(productDB);
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return productRepository.findByCategory(category);
	}

	private void checkProductNameAvailability(String nameProduct) {
		String strippedName = StringUtils.stripAccents(nameProduct).trim();
		productRepository.findByNameIgnoreCase(strippedName).ifPresent(invoice -> {
			throw new DataIntegrityViolationException(
					"Product name: El producto con nombre " + strippedName + " ya está en uso.");
		});
	}

}
