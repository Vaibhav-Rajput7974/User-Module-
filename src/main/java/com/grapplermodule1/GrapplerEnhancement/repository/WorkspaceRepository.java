package com.grapplermodule1.GrapplerEnhancement.repository;

import java.util.List;
import java.util.Optional;

import com.grapplermodule1.GrapplerEnhancement.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long>{
    Optional<Workspace> findByIdAndCompanyId(Long id, Long companyId);

    List<Workspace> findByCompanyId(Long companyId);
    void deleteByCompanyId(Long companyId);
}
