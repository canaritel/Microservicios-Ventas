package es.televoip.microserv.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import es.televoip.microserv.entity.Invoice;
import es.televoip.microserv.repository.InvoiceItemsRepository;
import es.televoip.microserv.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // ideal para inyectar objetos por constructor
@Transactional
@Validated
public class InvoiceServiceImpl implements InvoiceService {

	private final InvoiceRepository invoiceRepository;
	private final InvoiceItemsRepository invoiceItemsRepository;

	@Override
	public List<Invoice> findInvoiceAll() {
		return invoiceRepository.findAll();
	}

	@Override
	public Invoice createInvoice(Invoice invoice) {
		checkInvoiceNumberAvailability(invoice.getNumberInvoice());

		invoice.setState("CREATED");
		return invoiceRepository.save(invoice);
	}

	@Override
	public Invoice updateInvoice(Invoice invoice) {
		Invoice invoiceDB = getInvoice(invoice.getId());

		if (!invoice.getNumberInvoice().equals(invoiceDB.getNumberInvoice())) {
			checkInvoiceNumberAvailability(invoice.getNumberInvoice());
		}

		invoiceDB.setCustomerId(invoice.getCustomerId());
		invoiceDB.setDescription(invoice.getDescription());
		invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
		invoiceDB.getItems().clear();
		invoiceDB.setItems(invoice.getItems());

		return invoiceRepository.save(invoiceDB);
	}

	@Override
	public Invoice deleteInvoice(Long id) {
		Invoice invoiceDB = getInvoice(id);

		invoiceDB.setState("DELETED");
		return invoiceRepository.save(invoiceDB);
	}

	@Override
	public Invoice getInvoice(Long id) {
		return invoiceRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Invoice Id: La Factura con Id " + id + " no se encuentra."));
	}

	private void checkInvoiceNumberAvailability(String numberInvoice) {
		String strippedName = StringUtils.stripAccents(numberInvoice).trim();
		invoiceRepository.findByNumberInvoiceIgnoreCase(strippedName).ifPresent(invoice -> {
			throw new DataIntegrityViolationException(
					"Invoice numberInvoice: La Factura con numberInvoice " + strippedName + " ya está en uso.");
		});
	}

}
