package com.example.demo.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException() {
        super("Item Not Found!!!");
    }
}
