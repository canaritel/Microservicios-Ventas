package es.televoip.microserv.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.televoip.microserv.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

	Optional<Region> findByNameIgnoreCase(String name);

}
