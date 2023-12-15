package com.grapplermodule1.GrapplerEnhancement.customexception;

public class ParentNotFoundException extends RuntimeException{
    public ParentNotFoundException(String message) {
        super(message);
    }

}
