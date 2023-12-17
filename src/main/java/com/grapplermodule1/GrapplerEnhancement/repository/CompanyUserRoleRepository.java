package com.grapplermodule1.GrapplerEnhancement.repository;
import com.grapplermodule1.GrapplerEnhancement.entities.CompanyUserRole;
import com.grapplermodule1.GrapplerEnhancement.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CompanyUserRoleRepository extends JpaRepository<CompanyUserRole, Long> {
    @Query("SELECT cu.role FROM CompanyUserRole cu WHERE cu.user.id = :userId AND cu.companyId = :companyId")
    RoleEnum findRoleByUserIdAndCompanyId(@Param("userId") Long userId, @Param("companyId") Long companyId);
    @Modifying
    @Transactional
    @Query("DELETE FROM CompanyUserRole cu WHERE cu.id = :id")
    void deleteCompanyUserRoleById(@Param("id") Long id);
}
