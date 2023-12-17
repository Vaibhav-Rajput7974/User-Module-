package com.grapplermodule1.GrapplerEnhancement.dtos;

import com.grapplermodule1.GrapplerEnhancement.entities.Project;
import lombok.Data;

import java.util.List;


@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private List<TeamDTO> teams;

}
