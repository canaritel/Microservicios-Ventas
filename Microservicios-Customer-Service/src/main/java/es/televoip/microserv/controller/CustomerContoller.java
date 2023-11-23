package es.televoip.microserv.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import es.televoip.microserv.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import es.televoip.microserv.entity.Customer;
import es.televoip.microserv.entity.Region;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/customers")
@RequiredArgsConstructor
public class CustomerContoller {

	private final CustomerService customerService;

	@GetMapping
	public ResponseEntity<List<Customer>> listAllCustomer(
			@RequestParam(name = "regionId", required = false) Long regionId) {

		List<Customer> customers;

		if (null == regionId) {
			customers = customerService.findCustomerAll();
			if (customers.isEmpty()) {
				return ResponseEntity.noContent().build(); // devuelve 'no contiene'
			}
		} else {
			Region region = new Region();
			region.setId(regionId);
			customers = customerService.findCustomerByRegion(region);
			if (customers.isEmpty()) {
				log.error("Customer with Region id {} not found.", regionId);
				return ResponseEntity.notFound().build(); // devuelve 'no encuentra'
			}
		}

		return ResponseEntity.ok(customers);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) {
		log.info("Fetching Customer with id {}", id);
		Customer customer = customerService.getCustomer(id);

		return ResponseEntity.ok(customer);
	}

	@PostMapping
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
		log.info("Creating Customer: {}", customer);

		// al CREAR se comprueba el numberID, !region y email, si ya existe se devuelve una excepción
		Customer customerCreate = customerService.createCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerCreate);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @Valid @RequestBody Customer customer) {
		log.info("Updating Customer with id {}", id);

		customer.setId(id);
		Customer customerUpdate = customerService.updateCustomer(customer);
		return ResponseEntity.ok(customerUpdate);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable("id") long id) {
		log.info("Fetching and Deleting Customer with id {}", id);
		customerService.deleteCustomer(id);

		return ResponseEntity.noContent().build();
	}

}
