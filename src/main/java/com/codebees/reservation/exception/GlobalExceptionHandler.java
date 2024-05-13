package com.codebees.reservation.exception;

import com.codebees.reservation.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler
 * Event Interceptor to handle all exceptions and form a generic response object for errors
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * 400 for duplicate registration
     * @param exception - user defined exception
     * @param webRequest - filler from super class
     * @return - Error API
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception, WebRequest webRequest) {
        log.warn("400: attempt for duplicate user registration of user {}", exception.getMessage());
        ErrorResponse errorResponseDTO = new ErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * 400 if user not found in the system
     * @param exception - user defined exception
     * @param webRequest - filler from super class
     * @return - Error API
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception, WebRequest webRequest) {
        log.warn("400: Attempt to reserve ticket for invalid user {}", exception.getMessage());
        ErrorResponse errorResponseDTO = new ErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * 400 if ticket not found in the system
     * @param exception - user defined exception
     * @param webRequest - filler from super class
     * @return - Error API
     */
    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTicketNotFoundException(TicketNotFoundException exception, WebRequest webRequest) {
        log.warn("400: Ticket not found {}", exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * 400 if seat not found in the system
     * @param exception - user defined exception
     * @param webRequest - filler from super class
     * @return - Error API
     */
    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSeatNotFoundException(SeatNotFoundException exception, WebRequest webRequest) {
        log.warn("400: Seat not found {}", exception.getMessage());
        ErrorResponse errorResponseDTO = new ErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Generic exceptions to handle invalid values in incoming requests
     * @param ex - exception
     * @return error response API
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        errors.forEach( e -> {
            String field = ((FieldError) e).getField();
            String message = e.getDefaultMessage();
            validationErrors.put(field, message);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    /**
     * 500 for all other exceptions
     * @param exception - user defined exception
     * @param webRequest - filler from super class
     * @return - Error API
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralExceptions(Exception exception, WebRequest webRequest){
        log.error("Abnormal server exception {}. Investigation required", exception.getMessage());
        ErrorResponse errorResponseDTO = new ErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
