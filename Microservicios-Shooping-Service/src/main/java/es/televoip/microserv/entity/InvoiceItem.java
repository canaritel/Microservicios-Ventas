package es.televoip.microserv.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_invoice_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Min(value = 0, message = "La cantidad no puede ser negativa")
	@PositiveOrZero(message = "El stok no puede ser negativo")
	private Double quantity = Double.valueOf(0);

	@PositiveOrZero(message = "El precio no puede ser negativo")
	private Double price = Double.valueOf(0);

	@NotNull(message = "El ID del producto no puede ser nulo")
	@Column(name = "product_id")
	private Long productId;

	@Transient // 'no persisteste' no será registrado en esta BD
	private BigDecimal subTotal;

	public Double getSubTotal() {
		if (this.price > 0 && this.quantity > 0) {
			return this.quantity * this.price;
		} else {
			return (double) 0;
		}
	}

}
