package de.dhbwravensburg.remoso.skywatch.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * Katrin Schaake, TIA25, Mittwoch, 27.05.2026, Version: 0.1
 *
 * Globales Exception Handling für alle Controller
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 404 - Ressource nicht gefunden
	 */
	@ExceptionHandler(TrackedObjectNotFoundException.class)
	public ProblemDetail handleNotFound(TrackedObjectNotFoundException ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.NOT_FOUND,
				ex.getMessage());
		problem.setTitle("Resource not found");
		return problem;
	}

	/**
	 * 400 - Validierungsfehler bei @Valid
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, String> fieldErrors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
			fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		});
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.BAD_REQUEST,
				"Request validation failed"
		);
		problem.setTitle("Validation failed");
		problem.setProperty("fieldErrors", fieldErrors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);

	}

	/**
	 * 502 - Fehler bei externer API (z.B. NASA)
	 */
	@ExceptionHandler(ExternalApiException.class)
	public ProblemDetail handleExternalApi(ExternalApiException ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.BAD_GATEWAY,
				ex.getMessage()
		);
		problem.setTitle("Upstream service unavailable");
		return problem;
	}

	/**
	 * 500 - Fallback für alle anderen unerwarteten Fehler
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ProblemDetail> handleAnyOther(Exception ex) {

		log.error("Unexpected server error", ex);

		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.INTERNAL_SERVER_ERROR, "Unexcepted server error");
		problem.setTitle("Internal server error");
		problem.setProperty("error", ex.getClass().getSimpleName());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);

	}

}
