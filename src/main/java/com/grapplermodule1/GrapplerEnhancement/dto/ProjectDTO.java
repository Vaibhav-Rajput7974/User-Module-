package com.grapplermodule1.GrapplerEnhancement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name should not exceed 255 characters")
    private String name;

    @NotBlank(message = "Type is required")
    @Size(max = 255, message = "Type should not exceed 255 characters")
    private String type;

    @NotBlank(message = "SubType is required")
    @Size(max = 255, message = "SubType should not exceed 255 characters")
    private String subType;

    private LocalDateTime creationTime;
}
