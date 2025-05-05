package com.example.demo.exception;

public class AppBadRequestException extends RuntimeException {

    public AppBadRequestException(String s) {
        super(s);
    }
}
