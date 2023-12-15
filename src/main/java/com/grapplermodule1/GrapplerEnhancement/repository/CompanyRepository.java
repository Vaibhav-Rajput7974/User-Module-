package com.grapplermodule1.GrapplerEnhancement.repository;

import java.util.Optional;

import com.grapplermodule1.GrapplerEnhancement.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends  JpaRepository<Company, Long>  {
    Optional<Company> findByEmail(String email);
    Optional<Company> findByName(String name);
//    @Query("SELECT NEW com.example.GraplerEnhancemet.dto.CompanyDto(c.id, c.name, c.email, c.logo, c.description, c.structureType, c.contactNumber, c.address, c.creationTime) " +
//            "FROM Company c WHERE c.id = :companyId")
//    Optional<CompanyDTO> findCompanyDtoById(Long companyId);
//    @Query("SELECT NEW com.example.GraplerEnhancemet.dto.CompanyDto(c.id, c.name, c.email, c.logo, c.description, c.structureType, c.contactNumber, c.address, c.creationTime) " +
//            "FROM Company c")
//    List<CompanyDTO> findAllCompanyDtos();
}