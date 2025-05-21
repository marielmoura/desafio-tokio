package com.tokiomarine.financial_transfer_scheduler.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

	public static class FieldErrorResponse {
		private final String field;
		private final String message;

		public FieldErrorResponse(String field, String message) {
			this.field = field;
			this.message = message;
		}

		public String getField() {
			return field;
		}

		public String getMessage() {
			return message;
		}
	}

	public static class ValidationErrorResponse {
		private final String error;
		private final List<FieldErrorResponse> errors;

		public ValidationErrorResponse(String error, List<FieldErrorResponse> errors) {
			this.error = error;
			this.errors = errors;
		}

		public String getError() {
			return error;
		}

		public List<FieldErrorResponse> getErrors() {
			return errors;
		}
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> new FieldErrorResponse(err.getField(), err.getDefaultMessage()))
				.collect(Collectors.toList());

		ValidationErrorResponse response = new ValidationErrorResponse("Validation failed", errors);
		return ResponseEntity.badRequest().body(response);
	}
}
