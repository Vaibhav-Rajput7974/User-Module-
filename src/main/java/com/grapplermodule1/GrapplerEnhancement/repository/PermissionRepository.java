package com.grapplermodule1.GrapplerEnhancement.repository;

import com.grapplermodule1.GrapplerEnhancement.entities.Permission;
import com.grapplermodule1.GrapplerEnhancement.enums.PermissionType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionRepository extends JpaRepository<Permission, Long> {


//    @Query("SELECT p.permission_type FROM Permission p " +
//            "WHERE member_id = :memberId AND project_id = :projectId")
    PermissionType findByMemberIdAndProjectId(
            @Param("memberId") Long memberId,
            @Param("projectId") Long projectId);


    @Transactional
    @Modifying
    @Query("UPDATE Permission p SET p.permission_type = :permissionType WHERE  memberId = :memberId AND projectId = :projectId")
    int addPermissionTypeByMemberIdAndProjectId(
            @Param("memberId") Long memberId,
            @Param("projectId") Long projectId,
            @Param("permissionType") PermissionType permissionType);
}
