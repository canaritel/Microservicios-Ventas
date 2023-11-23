package es.televoip.microserv.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	static final String MESSAGE_KEY = "message";
	static final String FIEL_KEY = "field";

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleException(Exception e) {
		log.error("Error inesperado4: {}", e.getMessage()); // Registramos el error

		ErrorMessage errorResponse = new ErrorMessage();
		errorResponse.setError(e.getClass().getSimpleName());
		errorResponse.setTimestamp(LocalDateTime.now());

		// Set the status code based on the exception type
		if (e instanceof IllegalArgumentException) {
			errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		} else if (e instanceof DataIntegrityViolationException) {
			errorResponse.setStatus(HttpStatus.CONFLICT.value());
		} else {
			errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		// Obtén el mensaje de la excepción
		String errorMessage = e.getMessage();

		// Divide el mensaje en dos partes: [Nombre de la Entidad]: [Mensaje de Error]
		String[] parts = errorMessage.split(":", 2);

		if (parts.length == 2) {
			// Si se encontraron dos partes, la primera parte es el nombre de la entidad
			String entityName = parts[0].trim();

			// La segunda parte es el mensaje de error
			String errorDescription = parts[1].trim();

			// Crear el mensaje de error en el formato deseado
			List<Map<String, String>> messages = new ArrayList<>();
			Map<String, String> message = new HashMap<>();
			message.put(FIEL_KEY, entityName);
			message.put(MESSAGE_KEY, errorDescription);
			messages.add(message);
			errorResponse.setMessages(messages);
		} else {
			// Si no se encontraron dos partes, usa el mensaje completo como mensaje de error
			errorResponse.setMessages(Collections
					.singletonList(Map.of(FIEL_KEY, e.getClass().getSimpleName(), MESSAGE_KEY, errorMessage)));
		}

		return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
	}

	// Lo usamos para agregar varias excepciones y mostrarlas todas juntas
	@ExceptionHandler(AggregateException.class)
	public ResponseEntity<ErrorMessage> handleAggregateException(AggregateException e) {
		// Registrar todos los errores individuales
		e.getErrors().forEach(error -> log.error("Error de validación: {}", error));

		ErrorMessage errorResponse = new ErrorMessage();
		errorResponse.setError("Errores de Validación");
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

		List<Map<String, String>> messages = e.getErrors().stream().map(err -> Map.of(MESSAGE_KEY, err))
				.collect(Collectors.toList());

		errorResponse.setMessages(messages);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	// Agregamos métodos para controlar las excepciones más importantes
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
		log.error("Error inesperado3: {}", e.getMessage()); // Registramos el error

		ErrorMessage errorResponse = new ErrorMessage();
		errorResponse.setError("Error de Validación");
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

		List<Map<String, String>> errors = e.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> Map.of(FIEL_KEY, fieldError.getField(), MESSAGE_KEY, fieldError.getDefaultMessage()))
				.collect(Collectors.toList());

		errorResponse.setMessages(errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorMessage> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
		log.error("Error inesperado2: {}", e.getMessage()); // Registramos el error

		ErrorMessage errorResponse = new ErrorMessage();
		errorResponse.setError("Error de Lectura del Mensaje");
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

		errorResponse.setMessages(Collections.singletonList(Map.of(FIEL_KEY, "requestBody", MESSAGE_KEY,
				"El cuerpo de la solicitud no puede estar vacío o es inválido")));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorMessage> handleDataIntegrityViolation(DataIntegrityViolationException e) {
		log.error("Error inesperado1: {}", e.getMessage()); // Registramos el error

		// Obtener el mensaje detallado de la causa raíz
		String detailedMessage = Optional.ofNullable(e.getRootCause()).map(Throwable::getMessage).orElse(e.getMessage());

		ErrorMessage errorResponse = new ErrorMessage();
		errorResponse.setError("Error de Integridad de Datos");
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(HttpStatus.CONFLICT.value());

		// Usar el mensaje detallado
		errorResponse.setMessages(Collections.singletonList(Map.of(MESSAGE_KEY, detailedMessage)));

		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException e) {
		log.error("Error inesperado: {}", e.getMessage()); // Registramos el error

		ErrorMessage errorResponse = new ErrorMessage();
		errorResponse.setError("Entidad No Encontrada");
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());

		errorResponse.setMessages(Collections.singletonList(Map.of(MESSAGE_KEY, e.getMessage())));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException e) {
		log.error("Error de validación: {}", e.getMessage()); // Registramos el error

		ErrorMessage errorResponse = new ErrorMessage();
		errorResponse.setError("Error de Validación de Restricciones");
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

		List<Map<String, String>> errors = e
				.getConstraintViolations().stream().map(violation -> Map.of(FIEL_KEY,
						violation.getPropertyPath().toString(), MESSAGE_KEY, violation.getMessage()))
				.collect(Collectors.toList());

		errorResponse.setMessages(errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

}
