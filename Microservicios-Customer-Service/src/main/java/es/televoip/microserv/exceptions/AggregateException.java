package es.televoip.microserv.exceptions;

import java.util.List;

public class AggregateException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final List<String> errors;

	public AggregateException(List<String> errors) {
		super("Se encontraron múltiples errores: " + String.join(", ", errors));
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}

}
