package es.televoip.microserv.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.televoip.microserv.entity.Invoice;
import es.televoip.microserv.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor // ideal para inyectar objetos por constructor
@RequestMapping("/invoices")
public class InvoiceController {

	private final InvoiceService invoiceService;

	// -------------------Retrieve All Invoices--------------------------------------------
	@GetMapping
	public ResponseEntity<List<Invoice>> listAllInvoices() {
		List<Invoice> invoices = invoiceService.findInvoiceAll();

		if (invoices.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(invoices);
	}

	// -------------------Retrieve Single Invoice------------------------------------------
	@GetMapping(value = "/{id}")
	public ResponseEntity<Invoice> getInvoice(@PathVariable("id") long id) {
		log.info("Fetching Invoice with id {}", id);
		Invoice invoice = invoiceService.getInvoice(id);

		return ResponseEntity.ok(invoice);
	}

	// -------------------Create a Invoice-------------------------------------------------
	@PostMapping
	public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice) {
		log.info("Creating Invoice : {}", invoice);
		Invoice invoiceCreate = invoiceService.createInvoice(invoice);

		return ResponseEntity.status(HttpStatus.CREATED).body(invoiceCreate);
	}

	// ------------------- Update a Invoice -----------------------------------------------
	@PutMapping(value = "/{id}")
	public ResponseEntity<Invoice> updateInvoice(@PathVariable("id") long id, @Valid @RequestBody Invoice invoice) {
		log.info("Updating Invoice with id {}", id);
		invoice.setId(id);
		Invoice currentInvoice = invoiceService.updateInvoice(invoice);

		return ResponseEntity.ok(currentInvoice);
	}

	// ------------------- Delete a Invoice------------------------------------------------
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Invoice> deleteInvoice(@PathVariable("id") Long id) {
		log.info("Fetching & Deleting Invoice with id {}", id);
		Invoice invoice = invoiceService.getInvoice(id);

		invoiceService.deleteInvoice(id);
		return ResponseEntity.ok(invoice);
	}

}
