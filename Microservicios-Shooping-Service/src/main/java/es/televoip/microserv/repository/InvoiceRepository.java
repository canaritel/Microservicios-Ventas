package es.televoip.microserv.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.televoip.microserv.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	public List<Invoice> findByCustomerId(Long customerId);

	public Optional<Invoice> findByNumberInvoiceIgnoreCase(String numberInvoice);

}
