package es.televoip.microserv.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Entity
@Table(name = "tbl_invoices")
@Data
public class Invoice implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El número de factura no puede estar vacío")
	@Column(name = "number_invoice", unique = true)
	private String numberInvoice;

	private String description;

	@NotNull(message = "El ID del cliente no puede ser nulo")
	@Column(name = "customer_id")
	private Long customerId;

	@PastOrPresent(message = "La fecha de creación debe ser en el pasado o presente")
	@Column(name = "create_at")
	private LocalDateTime createAt;

	@Valid // validamos cada uno de los items que componen Invoice (la factura)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "invoice_id")
	private List<InvoiceItem> items;

	private String state;

	public Invoice() {
		items = new ArrayList<>();
	}

	@PrePersist // se realiza antes de insertarlo en la BD
	public void prePersist() {
		this.createAt = LocalDateTime.now();
	}

}
