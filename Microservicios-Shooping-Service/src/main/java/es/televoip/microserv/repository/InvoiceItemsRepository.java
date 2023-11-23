package es.televoip.microserv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.televoip.microserv.entity.InvoiceItem;

@Repository
public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem, Long> {

}
