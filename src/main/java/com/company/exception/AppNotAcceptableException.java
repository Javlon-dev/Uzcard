package com.company.exception;

public class AppNotAcceptableException extends RuntimeException{
    public AppNotAcceptableException(String message) {
        super(message);
    }
}
