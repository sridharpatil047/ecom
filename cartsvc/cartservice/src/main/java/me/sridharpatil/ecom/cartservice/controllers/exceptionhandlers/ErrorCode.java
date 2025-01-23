package me.sridharpatil.ecom.cartservice.controllers.exceptionhandlers;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorCode {
    // Category 1 : Domain specific errors
    final static String CART_NOT_FOUND = "CART_NOT_FOUND";
    final static String CART_ITEM_NOT_FOUND = "CART_ITEM_NOT_FOUND";
    final static String PRODUCT_ALREADY_EXISTS = "PRODUCT_ALREADY_EXISTS";

    // Category 2 : Security errors
    // TODO : Add security errors

    // Category 3 : Generic errors
    final static String BAD_REQUEST = "BAD_REQUEST";
    final static String NO_RESOURCE_FOUND = "NO_RESOURCE_FOUND";
    final static String METHOD_NOT_ALLOWED = "METHOD_NOT_ALLOWED";
    final static String TYPE_MISMATCH = "TYPE_MISMATCH";

    // Category 4 : Message broker errors
    final static String TIMEOUT = "TIMEOUT";

    // Category 5 : Internal server errors
    final static String UNKNOWN_ERROR = "UNKNOWN_ERROR";
}
