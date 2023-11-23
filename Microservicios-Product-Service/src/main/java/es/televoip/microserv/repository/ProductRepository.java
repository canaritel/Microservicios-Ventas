package es.televoip.microserv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.televoip.microserv.entity.Category;
import es.televoip.microserv.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	public List<Product> findByCategory(Category category);

	public Optional<Product> findByNameIgnoreCase(String name);

}
