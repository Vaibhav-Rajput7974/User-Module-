package com.grapplermodule1.GrapplerEnhancement.customexception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
