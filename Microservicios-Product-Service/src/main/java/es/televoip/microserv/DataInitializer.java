package es.televoip.microserv;

import org.springframework.stereotype.Component;

import es.televoip.microserv.entity.Category;
import es.televoip.microserv.entity.Product;
import es.televoip.microserv.repository.CategoryRepository;
import es.televoip.microserv.repository.ProductRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
public class DataInitializer {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public DataInitializer(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    public void initializeData() {
        // Insertar categorías si no existen
        insertCategoryIfNotExists(1, "shoes");
        insertCategoryIfNotExists(2, "books");
        insertCategoryIfNotExists(3, "electronics");

        // Insertar productos
        insertProduct(1L, "Spring Boot in Action", "Craig Walls is a software developer at Pivotal and is the author of Spring in Action", 12.0, 40.06, "CREATED", LocalDateTime.parse("2018-09-05T00:00:00"), 2L);
        insertProduct(2L, "adidas Cloudfoam Ultimate", "Walk in the air in the black / black CLOUDFOAM ULTIMATE running shoe from ADIDAS", 5.0, 178.89, "CREATED", LocalDateTime.parse("2018-09-05T00:00:00"), 1L);
        insertProduct(3L, "under armour Mens Micro G Assert – 7", "under armour Mens Lightweight mesh upper delivers complete breathability . Durable leather overlays for stability", 4.0, 12.5, "CREATED", LocalDateTime.parse("2018-09-05T00:00:00"), 1L);
    }

    private void insertCategoryIfNotExists(long id, String name) {
        if (!categoryRepository.existsById(id)) {
            Category category = new Category();
            category.setId(id);
            category.setName(name);
            categoryRepository.saveAndFlush(category);
        }
    }

    private void insertProduct(long id, String name, String description, double stock, double price, String status, LocalDateTime createAt, long categoryId) {
        if (!productRepository.existsById(id)) {
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setDescription(description);
            product.setStock(stock);
            product.setPrice(price);
            product.setStatus(status);
            product.setCreateAt(createAt);
            product.setCategory(categoryRepository.findById(categoryId).get());
            productRepository.saveAndFlush(product);
        }
    }
}
