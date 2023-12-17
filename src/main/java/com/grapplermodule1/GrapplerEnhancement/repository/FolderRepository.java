package com.grapplermodule1.GrapplerEnhancement.repository;
import java.util.List;

import com.grapplermodule1.GrapplerEnhancement.entities.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FolderRepository extends JpaRepository<Folder,Long> {
  List<Folder> findAllByProjectId(Long projectId);
  @Modifying
  @Transactional
  @Query("DELETE FROM Folder f WHERE f.id = :id")
  void deleteFolderById(@Param("id") Long id);
//  @Modifying
//  @Query("DELETE FROM Folder f WHERE f.parentProject.id = :projectId AND f.parentFolder.id = :parentFolderId")
//  void deleteByParentProjectIdAndParentFolderId(
//          @Param("projectId") Long projectId,
//          @Param("parentFolderId") Long parentFolderId
//  );
//  @Modifying
//  @Query("DELETE FROM Folder f WHERE f.parentFolder.id = :parentFolderId")
//  void deleteByParentFolderId(@Param("parentFolderId") Long parentFolderId);  //  void deleteByParentProject_Id(Long ProjectId);
//  //  void deleteByParentFolder_Id(Long ParentFolder_Id);
}
