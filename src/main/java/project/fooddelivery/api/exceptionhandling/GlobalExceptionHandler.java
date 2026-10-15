package project.fooddelivery.api.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.fooddelivery.api.restaurant.exception.RestaurantExistenceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RestaurantExistenceException.class)
    public ResponseEntity<ErrorResponse> handlerRestaurantExistenceException(RestaurantExistenceException ex){
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .status(HttpStatus.CONFLICT.toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationException(
                MethodArgumentNotValidException ex) {

            String errors = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(error ->
                            error.getField() + ": " + error.getDefaultMessage()
                    )
                    .collect(Collectors.joining(", "));

            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.name())
                    .message(errors)
                    .timestamp(LocalDateTime.now())
                    .build();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(
            Exception ex) {

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message("An unexpected error occurred")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

}
