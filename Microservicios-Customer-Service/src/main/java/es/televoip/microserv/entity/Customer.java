package es.televoip.microserv.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 9, max = 9, message = "El tamaño del DNI debe ser de 9 dígitos.")
	@NotEmpty(message = "El DNI no puede estar vacío.")
	@Column(name = "number_id", unique = true, length = 9, nullable = false)
	private String numberID;

	@NotEmpty(message = "El nombre no puede estar vacío.")
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotEmpty(message = "El apellido no puede estar vacío.")
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@NotEmpty(message = "El email no puede estar vacío.")
	@Column(unique = true, nullable = false)
	@Email(message = "No es un email correcto.")
	private String email;

	@Column(name = "photo_url")
	private String photoUrl;

	@NotNull(message = "La región no puede estar vacía.")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	private Region region;

}
