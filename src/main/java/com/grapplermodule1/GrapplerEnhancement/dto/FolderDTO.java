package com.grapplermodule1.GrapplerEnhancement.dto;

import com.grapplermodule1.GrapplerEnhancement.entities.Folder;
import com.grapplermodule1.GrapplerEnhancement.entities.Project;
import com.grapplermodule1.GrapplerEnhancement.enums.FolderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderDTO {
    private Long id;
    String name;
    private FolderType folderType;
    private Project parentProject;
    private Folder parentfolder;
}
