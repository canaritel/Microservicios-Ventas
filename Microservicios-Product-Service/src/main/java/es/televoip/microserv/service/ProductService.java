package es.televoip.microserv.service;

import java.util.List;

import es.televoip.microserv.entity.Category;
import es.televoip.microserv.entity.Product;

public interface ProductService {

	public List<Product> listAllProduct();

	public Product getProduct(Long id);

	public Product createProduct(Product product);

	public Product updateProduct(Product product);

	public Product deleteProduct(Long id);

	public Product updateStock(Long id, Double quantity);
	
	public List<Product> findByCategory(Category category);

}
