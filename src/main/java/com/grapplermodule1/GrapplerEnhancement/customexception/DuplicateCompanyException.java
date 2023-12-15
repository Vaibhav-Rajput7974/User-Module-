package com.grapplermodule1.GrapplerEnhancement.customexception;
public class DuplicateCompanyException extends RuntimeException {
    public DuplicateCompanyException(String message) {
        super(message);
    }
}