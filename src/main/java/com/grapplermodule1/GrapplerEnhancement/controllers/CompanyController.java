package com.grapplermodule1.GrapplerEnhancement.controllers;

import com.grapplermodule1.GrapplerEnhancement.customexception.DuplicateCompanyException;
import com.grapplermodule1.GrapplerEnhancement.dto.ApiResponseProject;
import com.grapplermodule1.GrapplerEnhancement.dto.CompanyDTO;
import com.grapplermodule1.GrapplerEnhancement.entities.Company;
import com.grapplermodule1.GrapplerEnhancement.service.CompanyService;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/companies")
@EnableTransactionManagement
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	private CompanyService companyService;

	@GetMapping
	public ResponseEntity<ApiResponseProject<List<CompanyDTO>>> getAllCompanies() {
		try {
			List<CompanyDTO> companies = companyService.getAllCompanies();
			if (companies != null && !companies.isEmpty()) {
				logger.info("All companies successfully retrieved");
				return ResponseEntity.ok(new ApiResponseProject<>(true, companies, "All companies retrieved successfully"));
			}
			else {
				logger.error("No companies found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, "No companies found"));
			}
		} catch (Exception e) {
			logger.error("Error occurred while retrieving all companies", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, e.getMessage()));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseProject<CompanyDTO>> getCompany(@PathVariable Long id) {
		try {
			CompanyDTO company = companyService.getCompany(id);
			if (company != null) {
				logger.info("Company retrieved successfully: {}", company.getName());
				return ResponseEntity.ok(new ApiResponseProject<>(true, company, "Company retrieved successfully"));
			} else {
				logger.warn("Company not found with ID: {}", id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, "Company not found"));
			}
		} catch (Exception e) {
			logger.error("Internal Server Error while retrieving company with ID: " + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, e.getMessage()));
		}
	}

	@PostMapping
	public ResponseEntity<ApiResponseProject<CompanyDTO>> createCompany( @RequestBody Company company) {
		try {
			logger.info("Heeel");
			CompanyDTO createdCompany = companyService.createCompany(company);
			if (createdCompany != null) {
				logger.info("Company created successfully: {}", createdCompany.getName());
				return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseProject<>(true, createdCompany, "Company created successfully"));
			} else {
				logger.error("Internal Server Error while creating company");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, "Internal Server Error"));
			}
		} catch (DuplicateCompanyException ex) {
			logger.error("Duplicate company creation attempt: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseProject<>(false, null, ex.getMessage()));
		} catch (Exception e) {
			logger.error("Internal Server Error while creating company", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, e.getMessage()));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseProject<CompanyDTO>> updateCompany(@PathVariable Long id,@Valid @RequestBody Company company) {
		try {
			CompanyDTO updatedCompany = companyService.updateCompany(id, company);
			if (updatedCompany != null) {
				logger.info("Company updated successfully: {}", updatedCompany.getName());
				return ResponseEntity.ok(new ApiResponseProject<>(true, updatedCompany, "Company updated successfully"));
			} else {
				logger.warn("Company not found with ID: {}", id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, "Company not found"));
			}
		} catch (DuplicateCompanyException ex) {
			logger.error("Duplicate company creation attempt: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseProject<>(false, null, ex.getMessage()));
		} catch (Exception e) {
			logger.error("Internal Server Error while updating company with ID: " + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, e.getMessage()));
		}
	}

	@PutMapping("/addImage/{id}")
	public ResponseEntity<ApiResponseProject<?>> addLogo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		try {
			if (file != null && !file.isEmpty()) {
				CompanyDTO companyLogoAddedDTO = companyService.AddLogo(file, id);

				if (companyLogoAddedDTO != null) {
					return ResponseEntity.ok(new ApiResponseProject<>(true, companyLogoAddedDTO, "Company Logo updated successfully"));
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, "Error in adding Logo"));
				}
			} else {
				// Handle the case where the input file is null or empty
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseProject<>(false, null, "Invalid file provided"));
			}
		}  catch (Exception e) {
			// Handle general exceptions with a generic message
			logger.error("Internal Server Error while adding company logo", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, "Internal Server Error"));
		}
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseProject<?>> deleteCompany(@PathVariable Long id) {
		try {
			boolean deleted = companyService.deleteCompany(id);
			if (deleted) {
				logger.info("Company deleted successfully with ID: {}", id);
				return ResponseEntity.noContent().build();
			} else {
				logger.warn("Company not found with ID: {}", id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, "Company not found"));
			}
		} catch (Exception e) {
			logger.error("Internal Server Error while deleting company with ID: " + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, e.getMessage()));
		}
	}
}