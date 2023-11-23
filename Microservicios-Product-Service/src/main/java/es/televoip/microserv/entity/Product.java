package es.televoip.microserv.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "El 'nombre' no puede estar vacío.")
	@Column(unique = true)
	private String name;

	private String description;

	@PositiveOrZero(message = "El 'stock' no puede ser negativo.")
	private Double stock;

	@PositiveOrZero(message = "El precio no puede ser negativo")
	private Double price;

	private String status;

	@Column(name = "create_at")
	private LocalDateTime createAt;

	@NotNull(message = "La 'categoría' no puede estar vacía.")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@PrePersist // se realiza antes de insertarlo en la BD
	public void prePersist() {
		this.createAt = LocalDateTime.now();
	}

}
