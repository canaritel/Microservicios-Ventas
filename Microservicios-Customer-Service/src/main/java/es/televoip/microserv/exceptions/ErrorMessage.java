package es.televoip.microserv.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

	private String error;
	private LocalDateTime timestamp;
	private int status;
	private List<Map<String, String>> messages;

}
