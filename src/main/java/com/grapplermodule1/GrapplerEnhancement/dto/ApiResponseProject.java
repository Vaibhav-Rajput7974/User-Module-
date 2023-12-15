package com.grapplermodule1.GrapplerEnhancement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseProject<T> {
    private boolean status;
    private T data;
    private String message;
}
