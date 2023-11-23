package es.televoip.microserv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.televoip.microserv.entity.Customer;
import es.televoip.microserv.entity.Region;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	public Optional<Customer> findByNumberID(String numberID);
	
	public Optional<Customer> findByEmail(String email);

	public List<Customer> findByLastName(String lastName);

	public List<Customer> findByRegion(Region region);

}
