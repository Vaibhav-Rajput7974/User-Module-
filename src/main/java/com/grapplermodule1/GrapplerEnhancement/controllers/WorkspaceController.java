package com.grapplermodule1.GrapplerEnhancement.controllers;

import com.grapplermodule1.GrapplerEnhancement.dto.ApiResponseProject;
import com.grapplermodule1.GrapplerEnhancement.dto.WorkspaceDTO;
import com.grapplermodule1.GrapplerEnhancement.service.WorkspaceService;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies/{companyId}/workspaces")
@EnableTransactionManagement
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class WorkspaceController {
	private static final Logger logger = LoggerFactory.getLogger(WorkspaceController.class);

	@Autowired
	private WorkspaceService workspaceService;

	@GetMapping
	public ResponseEntity<ApiResponseProject<List<WorkspaceDTO>>> getAllWorkspaces(@PathVariable Long companyId) {
		try {
			List<WorkspaceDTO> workspaces = workspaceService.getAllWorkspaces(companyId);
			if (workspaces != null) {
				logger.info("All workspaces for company {} retrieved successfully", companyId);
				return ResponseEntity.ok(new ApiResponseProject<>(true, workspaces, "All workspaces retrieved successfully"));
			} else {
				logger.warn("No workspaces found for company: {}", companyId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, "No workspaces found"));
			}
		} catch (Exception e) {
			logger.error("Error occurred while retrieving all workspaces for company " + companyId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, e.getMessage()));
		}
	}

	@GetMapping("/{workspaceId}")
	public ResponseEntity<ApiResponseProject<WorkspaceDTO>> getWorkspace(@PathVariable Long companyId, @PathVariable Long workspaceId) {
		try {
			WorkspaceDTO workspace = workspaceService.getWorkspace(companyId, workspaceId);
			if (workspace != null) {
				logger.info("Workspace retrieved successfully: {}", workspace.getName());
				return ResponseEntity.ok(new ApiResponseProject<>(true, workspace, "Workspace retrieved successfully"));
			} else {
				logger.warn("Workspace not found with ID: {}", workspaceId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, "Workspace not found"));
			}
		} catch (Exception e) {
			logger.error("Internal Server Error while retrieving workspace with ID: " + workspaceId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, e.getMessage()));
		}
	}

	@PostMapping
	public ResponseEntity<ApiResponseProject<WorkspaceDTO>> createWorkspace(@PathVariable Long companyId, @Valid @RequestBody WorkspaceDTO workspaceDTO) {
		try {
			WorkspaceDTO createdWorkspace = workspaceService.createWorkspace(companyId, workspaceDTO);
			if (createdWorkspace != null) {
				logger.info("Workspace created successfully: {}", createdWorkspace.getName());
				return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseProject<>(true, createdWorkspace, "Workspace created successfully"));
			} else {
				logger.error("Workspace not created due to Company not found on ID : {}" , companyId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, "Workspace not created due to Company not found on ID : "+companyId));
			}
		} catch (Exception e) {
			logger.error("Internal Server Error while creating workspace", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, e.getMessage()));
		}
	}

	@PutMapping("/{workspaceId}")
	public ResponseEntity<ApiResponseProject<WorkspaceDTO>> updateWorkspace(@PathVariable Long companyId, @PathVariable Long workspaceId, @Valid @RequestBody WorkspaceDTO workspaceDTO) {
		try {
			WorkspaceDTO updatedWorkspace = workspaceService.updateWorkspace(companyId, workspaceId, workspaceDTO);
			if (updatedWorkspace != null) {
				logger.info("Workspace updated successfully: {}", updatedWorkspace.getName());
				return ResponseEntity.ok(new ApiResponseProject<>(true, updatedWorkspace, "Workspace updated successfully"));
			} else {
				logger.warn("Workspace not found with ID: {}", workspaceId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, "Workspace not found"));
			}
		} catch (Exception e) {
			logger.error("Internal Server Error while updating workspace with ID: " + workspaceId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, e.getMessage()));
		}
	}

	@DeleteMapping("/{workspaceId}")
	public ResponseEntity<ApiResponseProject<?>> deleteWorkspace(@PathVariable Long companyId, @PathVariable Long workspaceId) {
		try {
			boolean deleted = workspaceService.deleteWorkspace(companyId, workspaceId);
			if (deleted) {
				logger.info("Workspace deleted successfully with ID: {}", workspaceId);
				return ResponseEntity.noContent().build();
			} else {
				logger.warn("Workspace not found with ID: {}", workspaceId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, "Workspace not found"));
			}
		} catch (Exception e) {
			logger.error("Internal Server Error while deleting workspace with ID: " + workspaceId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, e.getMessage()));
		}
	}
}