package es.televoip.microserv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.televoip.microserv.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
