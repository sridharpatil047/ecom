package me.sridharpatil.ecom.paymentservice.controllers.exceptionhandlers;

import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.paymentservice.controllers.exceptionhandlers.dtos.ExceptionDto;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;


@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    // Category 1 :  Domain specific exception handlers
//    @ExceptionHandler(CartNotFoundException.class)
//    public ResponseEntity<ExceptionDto> handleCartNotFoundException(CartNotFoundException ex) {
//        ExceptionDto exceptionDto = new ExceptionDto(
//                ErrorCode.CART_NOT_FOUND,
//                ex.getMessage()
//        );
//
//        log.error("Cart not found : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());
//
//        return ResponseEntity.status(404).body(exceptionDto);
//    }
//
//    @ExceptionHandler(CartItemNotFoundException.class)
//    public ResponseEntity<ExceptionDto> handleCartItemNotFoundException(CartItemNotFoundException ex) {
//        ExceptionDto exceptionDto = new ExceptionDto(
//                ErrorCode.CART_ITEM_NOT_FOUND,
//                ex.getMessage()
//        );
//
//        log.error("Cart Item not found : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());
//
//        return ResponseEntity.status(404).body(exceptionDto);
//    }
//
//    @ExceptionHandler(ProductAlreadyExistsException.class)
//    public ResponseEntity<ExceptionDto> handleProductAlreadyExistsException(ProductAlreadyExistsException ex) {
//        ExceptionDto exceptionDto = new ExceptionDto(
//                ErrorCode.PRODUCT_ALREADY_EXISTS,
//                ex.getMessage()
//        );
//
//        log.error("Product already exists : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());
//
//        return ResponseEntity.status(400).body(exceptionDto);
//    }



    // Category 2 : Security Related exception handlers
    // TODO : Add security related exception handlers

//    @ExceptionHandler(AuthorizationDeniedException.class)
//    public ResponseEntity<ExceptionDto> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
//        ExceptionDto exceptionDto = new ExceptionDto(
//                ErrorCode.ACCESS_DENIED,
//                ex.getMessage()
//        );
//
//        log.error("Access denied : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());
//
//        return ResponseEntity.status(403).body(exceptionDto);
//    }
//
//    @ExceptionHandler(InvalidBearerTokenException.class)
//    public ResponseEntity<ExceptionDto> handleInvalidBearerTokenException(InvalidBearerTokenException ex) {
//        ExceptionDto exceptionDto = new ExceptionDto(
//                ErrorCode.INVALID_CREDENTIALS,
//                ex.getMessage()
//        );
//
//        log.error("Invalid Credentials : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());
//
//        return ResponseEntity.status(401).body(exceptionDto);
//    }
//

    // Category 3 : Generic exception handlers
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionDto> handleNoResourceFoundException(NoResourceFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.NO_RESOURCE_FOUND,
                ex.getMessage()
        );

        log.error("No resource found : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());

        return ResponseEntity.status(406).body(exceptionDto);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionDto> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.METHOD_NOT_ALLOWED,
                ex.getMessage()
        );

        log.error("Method not allowed : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());

        return ResponseEntity.status(405).body(exceptionDto);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String fieldName = ex.getName();
        String expectedType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "Unknown";
        String errorMessage = String.format("'%s' should be of type '%s'", fieldName, expectedType);

        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.TYPE_MISMATCH,
                errorMessage
        );

        log.error("Type mismatch : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());

        return ResponseEntity.status(400).body(exceptionDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> message = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            message.add(String.format("%s %s", error.getField(), error.getDefaultMessage()));
        });

        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.BAD_REQUEST,
                String.join(", ", message)
        );

        log.error("Input validation error : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());

        return ResponseEntity.status(400).body(exceptionDto);
    }

    // Category 4 : Message queue related exception handlers
    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<ExceptionDto> handleKafkaTimeoutException(TimeoutException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.TIMEOUT,
                ex.getMessage()
        );

        log.error("Timeout error : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());

        return ResponseEntity.status(504).body(exceptionDto);
    }

    // Category 5 : Internal server error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.UNKNOWN_ERROR,
                ex.getMessage()
        );

        log.error("Unknown error : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());

        return ResponseEntity.status(500).body(exceptionDto);
    }
}
