package me.sridharpatil.ecom.userservice.controllers.exceptionhandlers;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorCode {
    final static String BAD_REQUEST = "BAD_REQUEST";
    final static String NO_RESOURCE_FOUND = "NO_RESOURCE_FOUND";
    final static String METHOD_NOT_ALLOWED = "METHOD_NOT_ALLOWED";
    final static String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    final static String TYPE_MISMATCH = "TYPE_MISMATCH";
    final static String USER_NOT_FOUND = "USER_NOT_FOUND";
    final static String ROLE_NOT_FOUND = "ROLE_NOT_FOUND";
    final static String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    final static String ROLE_ALREADY_EXISTS = "ROLE_ALREADY_EXISTS";
    final static String ACCESS_DENIED = "ACCESS_DENIED";
    final static String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
}
