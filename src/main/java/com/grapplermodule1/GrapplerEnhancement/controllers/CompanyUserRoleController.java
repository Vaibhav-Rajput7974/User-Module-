package com.grapplermodule1.GrapplerEnhancement.controllers;
import com.grapplermodule1.GrapplerEnhancement.customexception.DuplicateDataException;
import com.grapplermodule1.GrapplerEnhancement.customexception.NotFoundException;
import com.grapplermodule1.GrapplerEnhancement.dto.ApiResponseProject;
import com.grapplermodule1.GrapplerEnhancement.entities.CompanyUserRole;
import com.grapplermodule1.GrapplerEnhancement.service.CompanyUserRoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableTransactionManagement
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/roles")
public class CompanyUserRoleController {

    @Autowired
    private CompanyUserRoleService companyUserRoleService;


    @GetMapping("/{companyUserRoleId}")
    public ResponseEntity<ApiResponseProject<?>> getCompanyUserRole(@PathVariable Long companyUserRoleId) {
        try {
            CompanyUserRole companyUserRole = companyUserRoleService.getCompanyUserRoleById(companyUserRoleId);
            if (companyUserRole != null) {
                return ResponseEntity.ok(new ApiResponseProject<>(true, companyUserRole, "CompanyUserRole retrieved successfully with ID: " + companyUserRoleId));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, "CompanyUserRole not found with ID: " + companyUserRoleId));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, "Internal Server Error"));
        }
    }

    @PostMapping("/{companyId}/{userId}")
    public ResponseEntity<ApiResponseProject<?>> createCompanyUserRole(@PathVariable Long companyId, @PathVariable Long userId, @Valid @RequestBody CompanyUserRole companyUserRole) {
        try {
            CompanyUserRole createdCompanyUserRole = companyUserRoleService.addCompanyUserRole(companyId,userId,companyUserRole);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseProject<>(true, createdCompanyUserRole, "CompanyUserRole created successfully with ID: " + createdCompanyUserRole.getId()));
        } catch (DuplicateDataException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseProject<>(false, null, ex.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, "Internal Server Error"));
        }
    }

    @PutMapping("/{companyUserRoleId}")
    public ResponseEntity<ApiResponseProject<?>> updateCompanyUserRole(@PathVariable Long companyUserRoleId, @Valid @RequestBody CompanyUserRole companyUserRole) {
        try {
            CompanyUserRole updatedCompanyUserRole = companyUserRoleService.updateCompanyUserRole(companyUserRoleId, companyUserRole);
            return ResponseEntity.ok(new ApiResponseProject<>(true, updatedCompanyUserRole, "CompanyUserRole updated successfully with ID: " + companyUserRoleId));
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, ex.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, "Internal Server Error"));
        }
    }

    @DeleteMapping("/{companyUserRoleId}")
    public ResponseEntity<ApiResponseProject<?>> deleteCompanyUserRole(@PathVariable Long companyUserRoleId) {
        try {
            companyUserRoleService.deleteCompanyUserRoleById(companyUserRoleId);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, ex.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, "Internal Server Error"));
        }
    }
}

