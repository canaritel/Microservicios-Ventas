package es.televoip.microserv.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
