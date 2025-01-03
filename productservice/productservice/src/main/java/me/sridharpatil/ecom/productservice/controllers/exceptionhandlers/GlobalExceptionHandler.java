package me.sridharpatil.ecom.productservice.controllers.exceptionhandlers;

import jakarta.validation.ConstraintViolationException;
import me.sridharpatil.ecom.productservice.controllers.exceptionhandlers.dtos.ExceptionDto;
import me.sridharpatil.ecom.productservice.exceptions.CategoryAlreadyExistsException;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleProductNotFoundException(ProductNotFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.PRODUCT_NOT_FOUND,
                ex.getMessage()
        );
        return ResponseEntity.status(404).body(exceptionDto);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.CATEGORY_NOT_FOUND,
                ex.getMessage()
        );
        return ResponseEntity.status(404).body(exceptionDto);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ExceptionDto> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.CATEGORY_ALREADY_EXISTS,
                ex.getMessage()
        );
        return ResponseEntity.status(400).body(exceptionDto);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionDto> handleNoResourceFoundException(NoResourceFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.NO_RESOURCE_FOUND,
                ex.getMessage()
        );
        return ResponseEntity.status(406).body(exceptionDto);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionDto> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.METHOD_NOT_ALLOWED,
                ex.getMessage()
        );
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

        return ResponseEntity.status(400).body(exceptionDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.UNKNOWN_ERROR,
                ex.getMessage()
        );
        return ResponseEntity.status(500).body(exceptionDto);
    }

}
