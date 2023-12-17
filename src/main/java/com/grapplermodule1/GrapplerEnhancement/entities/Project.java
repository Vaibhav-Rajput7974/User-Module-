package com.grapplermodule1.GrapplerEnhancement.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name should not exceed 255 characters")
    private String name;

    @NotBlank(message = "Type is required")
    @Size(max = 255, message = "Type should not exceed 255 characters")
    private String type;

    @NotBlank(message = "SubType is required")
    @Size(max = 255, message = "SubType should not exceed 255 characters")
    private String subType;

    private LocalDateTime creationTime ;


    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH} , fetch = FetchType.EAGER)
    private Workspace workspace;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
//    private List<Task> tasks;



//    @ManyToMany
//    @JsonBackReference
//    @JoinTable(name = "project_team",
//            joinColumns = @JoinColumn(name = "project_id"),
//            inverseJoinColumns = @JoinColumn(name = "team_id"))
//    private Set<Team> teams;
}
