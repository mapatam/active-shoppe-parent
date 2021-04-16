package za.co.momentum.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import za.co.momentum.service.exception.ExpectationFailedException;
import za.co.momentum.service.exception.ResourceNotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Log4j2
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
		ApiException apiError = new ApiException(ex.getLocalizedMessage());
		return build(apiError, HttpStatus.NOT_FOUND, ex);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t + " "));

		ApiException apiError = new ApiException(ex.getLocalizedMessage() + ": " + builder.toString());
		return build(apiError, HttpStatus.METHOD_NOT_ALLOWED, ex);
	}

	@ExceptionHandler({ExpectationFailedException.class})
	public ResponseEntity<Object> handleUserDeniedException(ExpectationFailedException ex, WebRequest request) {
		ApiException apiError = new ApiException(ex.getLocalizedMessage());
		return build(apiError, HttpStatus.EXPECTATION_FAILED, ex);
	}

	@ExceptionHandler({EntityNotFoundException.class})
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		ApiException apiError = new ApiException(ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : "Entity was not found");
		return build(apiError, HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ApiException apiError = new ApiException(ex.getLocalizedMessage());
		return build(apiError, HttpStatus.NOT_FOUND, ex);
	}

	@ExceptionHandler({RuntimeException.class})
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
		ApiException apiError = new ApiException(ex.getLocalizedMessage());
		return build(apiError, HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
		ApiException apiError = new ApiException(ex.getLocalizedMessage());
		return build(apiError, HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(final DataIntegrityViolationException ex, final WebRequest request) {
		ApiException apiError = new ApiException(ex.getLocalizedMessage());

		return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({TransactionSystemException.class})
	protected ResponseEntity<Object> handlePersistenceException(final Exception ex, final WebRequest request) {
		Throwable cause = ((TransactionSystemException) ex).getRootCause();
		if (cause instanceof ConstraintViolationException) {

			ConstraintViolationException consEx= (ConstraintViolationException) cause;
			final List<String> errors = new ArrayList<String>();
			for (final ConstraintViolation<?> violation : consEx.getConstraintViolations()) {
				errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
			}

			final ApiException apiError = new ApiException(errors.toString());
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		final ApiException apiError = new ApiException("an error occurred");
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * TODO https://reflectoring.io/bean-validation-with-spring-boot/#returning-structured-error-responses
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers, HttpStatus status,
																  WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ApiException apiError = new ApiException(errors.toString());

		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * TODO see https://reflectoring.io/bean-validation-with-spring-boot/#returning-structured-error-responses
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
		final ApiException apiError = new ApiException("Validation error: " + e.getMessage());
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> build(ApiException apiError, HttpStatus httpStatus, Exception cause) {
		log.error("Error handling request", cause);
		return new ResponseEntity<>(apiError, new HttpHeaders(), httpStatus);
	}

}
