package me.sridharpatil.ecom.productservice.exceptionhandlers;

import me.sridharpatil.ecom.productservice.exceptionhandlers.dtos.ExceptionDto;
import me.sridharpatil.ecom.productservice.exceptions.CategoryAlreadyExistsException;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.UNKNOWN_ERROR,
                ex.getMessage()
        );
        return ResponseEntity.status(500).body(exceptionDto);
    }
}
