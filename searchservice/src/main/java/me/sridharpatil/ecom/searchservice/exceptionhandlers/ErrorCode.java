package me.sridharpatil.ecom.searchservice.exceptionhandlers;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorCode {
    final static String BAD_REQUEST = "BAD_REQUEST";
    final static String NO_RESOURCE_FOUND = "NO_RESOURCE_FOUND";
    final static String METHOD_NOT_ALLOWED = "METHOD_NOT_ALLOWED";
    final static String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    final static String TYPE_MISMATCH = "TYPE_MISMATCH";

}
