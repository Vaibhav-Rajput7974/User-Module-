package com.grapplermodule1.GrapplerEnhancement.repository;

import java.util.List;
import java.util.Optional;

import com.grapplermodule1.GrapplerEnhancement.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long>{
    Optional<Workspace> findByIdAndCompany_Id(Long id, Long companyId);

    List<Workspace> findByCompany_Id(Long companyId);
    void deleteByCompany_Id(Long companyId);
}
