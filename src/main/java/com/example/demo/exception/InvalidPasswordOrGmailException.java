package com.example.demo.exception;

public class InvalidPasswordOrGmailException extends RuntimeException {
    public InvalidPasswordOrGmailException() {
        super("Invalid password or gmail");
    }
}
