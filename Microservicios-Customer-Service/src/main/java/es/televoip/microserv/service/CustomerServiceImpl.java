package es.televoip.microserv.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import es.televoip.microserv.entity.Customer;
import es.televoip.microserv.entity.Region;
import es.televoip.microserv.exceptions.AggregateException;
import es.televoip.microserv.repository.CustomerRepository;
import es.televoip.microserv.repository.RegionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // ideal para inyectar objetos por constructor
@Transactional
@Validated
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final RegionRepository regionRepository;

	@Override
	public List<Customer> findCustomerAll() {
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> findCustomerByRegion(Region region) {
		return customerRepository.findByRegion(region);
	}

	@Override
	public Customer createCustomer(Customer customer) {
		List<String> errors = new ArrayList<>();

		checkCustomerExists(customer.getNumberID(), customer.getEmail(), errors);
		checkRegionExists(customer.getRegion().getId(), errors);

		if (!errors.isEmpty()) {
			throw new AggregateException(errors);
		}

		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		Customer customerDB = this.getCustomer(customer.getId());

		List<String> errors = new ArrayList<>();

		if (!customer.getNumberID().equals(customerDB.getNumberID())) {
			checkCustomerExists(customer.getNumberID(), null, errors);
		}

		if (!customer.getEmail().equals(customerDB.getEmail())) {
			checkCustomerExists(null, customer.getEmail(), errors);
		}

		checkRegionExists(customer.getRegion().getId(), errors);

		if (!errors.isEmpty()) {
			throw new AggregateException(errors);
		}

		customerDB.setFirstName(customer.getFirstName());
		customerDB.setLastName(customer.getLastName());
		customerDB.setEmail(customer.getEmail());
		customerDB.setPhotoUrl(customer.getPhotoUrl());
		customerDB.setNumberID(customer.getNumberID());
		customerDB.setRegion(customer.getRegion());

		return customerRepository.save(customerDB);
	}

	@Override
	public void deleteCustomer(Long id) {
		Customer customerDelete = this.getCustomer(id);

		customerRepository.delete(customerDelete);
	}

	@Override
	public Customer getCustomer(Long id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Customer Id: El Id " + id + " no se encuentra."));
	}

	private void checkCustomerExists(String numberID, String email, List<String> errors) {
		if (null != numberID) {
			customerRepository.findByNumberID(numberID)
					.ifPresent(c -> errors.add("Customer numberID: El NumberID " + numberID + " ya existe."));
		}

		if (null != email) {
			customerRepository.findByEmail(email)
					.ifPresent(c -> errors.add("Customer email: El correo electrónico " + email + " ya existe."));
		}
	}

	private void checkRegionExists(Long regionId, List<String> errors) {
		if (!regionRepository.findById(regionId).isPresent()) {
			errors.add("Customer region: La región con ID " + regionId + " no existe.");
		}
	}

}
