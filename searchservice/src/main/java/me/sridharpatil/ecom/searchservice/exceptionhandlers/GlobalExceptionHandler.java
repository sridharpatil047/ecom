package me.sridharpatil.ecom.searchservice.exceptionhandlers;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.searchservice.exceptionhandlers.dtos.ExceptionDto;
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

    @ExceptionHandler(ElasticsearchException.class)
    public ResponseEntity<ExceptionDto> handleElasticsearchException(ElasticsearchException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.BAD_REQUEST,
                ex.getMessage()
        );

        log.error(ex);

        return ResponseEntity.status(400).body(exceptionDto);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionDto> handleNoResourceFoundException(NoResourceFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.NO_RESOURCE_FOUND,
                ex.getMessage()
        );

        log.error(ex);

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.UNKNOWN_ERROR,
                ex.getMessage()
        );

        log.error("Unknown error : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());
        log.error(ex);

        return ResponseEntity.status(500).body(exceptionDto);
    }

}
