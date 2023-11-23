package es.televoip.microserv.service;

import java.util.List;
import es.televoip.microserv.entity.Customer;
import es.televoip.microserv.entity.Region;

public interface CustomerService {

	public List<Customer> findCustomerAll();

	public List<Customer> findCustomerByRegion(Region region);

	public Customer createCustomer(Customer customer);

	public Customer updateCustomer(Customer customer);

	public void deleteCustomer(Long id);

	public Customer getCustomer(Long id);

}
