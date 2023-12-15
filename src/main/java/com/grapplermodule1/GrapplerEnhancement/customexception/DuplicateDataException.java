package com.grapplermodule1.GrapplerEnhancement.customexception;

public class DuplicateDataException extends RuntimeException{
    public DuplicateDataException(String message) {
        super(message);
    }
}
