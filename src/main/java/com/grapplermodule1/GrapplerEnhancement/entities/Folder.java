package com.grapplermodule1.GrapplerEnhancement.entities;


/*
import java.util.List;

import com.example.GraplerEnhancemet.entity.enums.FolderType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class    Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name should not exceed 255 characters")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "FolderType type must be provided")
    private FolderType folderType;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Project parentProject;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Folder> subFolders;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Task> tasks;
}
*/

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grapplermodule1.GrapplerEnhancement.enums.FolderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name should not exceed 255 characters")
    private String name;


    @Enumerated(EnumType.STRING)
    @NotNull(message = "FolderType type must be provided")
    private FolderType folderType;

    private Long projectId;

    @JsonIgnore
//    @JsonIdentityReference(alwaysAsId = true)//self Mapping
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Folder parentFolder;

//    @JsonIgnore
@OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
private List<Folder> subFolders;
}
