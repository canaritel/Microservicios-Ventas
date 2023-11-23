package es.televoip.microserv.service;

import java.util.List;

import es.televoip.microserv.entity.Invoice;

public interface InvoiceService {

	public List<Invoice> findInvoiceAll();

	public Invoice createInvoice(Invoice invoice);

	public Invoice updateInvoice(Invoice invoice);

	public Invoice deleteInvoice(Long id);

	public Invoice getInvoice(Long id);

}
