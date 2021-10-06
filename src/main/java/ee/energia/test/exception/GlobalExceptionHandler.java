package ee.energia.test.exception;

import ee.energia.test.domain.Basket;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Data
    public class ErrorDetails {
        private Date timestamp;
        private String message;
        private String details;

        public ErrorDetails(Date timestamp, String message, String details) {
            super();
            this.timestamp = timestamp;
            this.message = message;
            this.details = details;
        }
    }

    /**
     * Provides handling for exceptions throughout this service.
     */
    @ExceptionHandler({RecordNotFoundException.class, BasketNotFoundException.class, MethodArgumentNotValidException.class,
            ConstraintViolationException.class})
    public final ResponseEntity<ErrorDetails> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        log.error("Handling exception: ", ex);

        if (ex instanceof RecordNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            RecordNotFoundException rnf = (RecordNotFoundException) ex;

            return handleRecordNotFoundException(rnf, headers, status, request);
        } else if (ex instanceof BasketNotFoundException) {
            HttpStatus status = HttpStatus.FORBIDDEN;
            BasketNotFoundException rnf = (BasketNotFoundException) ex;

            return handleBasketNotFoundException(rnf, headers, status, request);
        } else if (ex instanceof MethodArgumentNotValidException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException) ex;

            return handleMethodArgumentNotValid(argumentNotValidException, headers, status, request);
        } else if (ex instanceof ConstraintViolationException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex;

            return handleConstraintViolationException(constraintViolationException, headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error("GlobalExceptionHandler: ", ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Customize the response for ContentNotAllowedException.
     */
    protected ResponseEntity<ErrorDetails> handleRecordNotFoundException(RecordNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false)), headers, status, request);
    }

    /**
     * Customize the response for ContentNotAllowedException.
     */
    protected ResponseEntity<ErrorDetails> handleBasketNotFoundException(BasketNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false)), headers, status, request);
    }

    /**
     * ALL single place to customize the response body of all Exception types.
     */
    protected ResponseEntity<ErrorDetails> handleExceptionInternal(Exception ex, ErrorDetails body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }

    protected ResponseEntity<ErrorDetails> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessages = ex.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());

        return handleExceptionInternal(ex, new ErrorDetails(new Date(), StringUtils.join(errorMessages),
                request.getDescription(false)), headers, status, request);
    }

    protected ResponseEntity<ErrorDetails> handleConstraintViolationException(ConstraintViolationException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessages = ex.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getPropertyPath().toString() + ": " + constraintViolation.getMessage())
                .collect(Collectors.toList());
        return handleExceptionInternal(ex, new ErrorDetails(new Date(), StringUtils.join(errorMessages),
                request.getDescription(false)), headers, status, request);
    }

}