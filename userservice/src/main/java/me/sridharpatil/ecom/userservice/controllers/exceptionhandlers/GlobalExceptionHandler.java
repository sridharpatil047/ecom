package me.sridharpatil.ecom.userservice.controllers.exceptionhandlers;

import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.userservice.controllers.exceptionhandlers.dtos.ExceptionDto;
import me.sridharpatil.ecom.userservice.exceptions.RoleAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
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

    // Domain specific exception handlers
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(UserNotFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.USER_NOT_FOUND,
                ex.getMessage()
        );

        log.error("User not found : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());

        return ResponseEntity.status(404).body(exceptionDto);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleRoleNotFoundException(RoleNotFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.ROLE_NOT_FOUND,
                ex.getMessage()
        );

        log.error("Role not found : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());

        return ResponseEntity.status(404).body(exceptionDto);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionDto> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.USER_ALREADY_EXISTS,
                ex.getMessage()
        );

        log.error("User already exists : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());

        return ResponseEntity.status(400).body(exceptionDto);
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ExceptionDto> handleRoleAlreadyExistsException(RoleAlreadyExistsException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.ROLE_ALREADY_EXISTS,
                ex.getMessage()
        );

        log.error("Role already exists : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());

        return ResponseEntity.status(400).body(exceptionDto);
    }



    // Generic exception handlers
//    @ExceptionHandler(NoResourceFoundException.class)
//    public ResponseEntity<ExceptionDto> handleNoResourceFoundException(NoResourceFoundException ex) {
//        ExceptionDto exceptionDto = new ExceptionDto(
//                ErrorCode.NO_RESOURCE_FOUND,
//                ex.getMessage()
//        );
//
//        log.error("No resource found : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());
//
//        return ResponseEntity.status(406).body(exceptionDto);
//    }

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

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionDto> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.ACCESS_DENIED,
                ex.getMessage()
        );

        log.error("Access denied : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());

        return ResponseEntity.status(403).body(exceptionDto);
    }

    @ExceptionHandler(InvalidBearerTokenException.class)
    public ResponseEntity<ExceptionDto> handleInvalidBearerTokenException(InvalidBearerTokenException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(
                ErrorCode.INVALID_CREDENTIALS,
                ex.getMessage()
        );

        log.error("Invalid Credentials : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());

        return ResponseEntity.status(401).body(exceptionDto);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ExceptionDto> handleException(Exception ex) {
//        ExceptionDto exceptionDto = new ExceptionDto(
//                ErrorCode.UNKNOWN_ERROR,
//                ex.getMessage()
//        );
//
//        log.error("Unknown error : {} - {}", exceptionDto.getErrorCode(), exceptionDto.getMessage());
//
//        return ResponseEntity.status(500).body(exceptionDto);
//    }
}
