package com.grapplermodule1.GrapplerEnhancement.service;
import com.grapplermodule1.GrapplerEnhancement.customexception.DuplicateDataException;
import com.grapplermodule1.GrapplerEnhancement.customexception.NotFoundException;
import com.grapplermodule1.GrapplerEnhancement.entities.Company;
import com.grapplermodule1.GrapplerEnhancement.entities.CompanyUserRole;
import com.grapplermodule1.GrapplerEnhancement.entities.Users;
import com.grapplermodule1.GrapplerEnhancement.enums.RoleEnum;
import com.grapplermodule1.GrapplerEnhancement.repository.CompanyUserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyUserRoleService {

    @Autowired
    private CompanyUserRoleRepository companyUserRoleRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(CompanyUserRoleService.class);

    public CompanyUserRole addCompanyUserRole(Long companyId, Long userId, CompanyUserRole companyUserRole){

        Users user = userService.getUserById(userId);
        if(user == null) {
            throw  new NotFoundException("User Not Found on this Id : "+userId);
        }
        RoleEnum role = findRoleByUserIdAndCompanyId(userId,companyId);
        if(role == null) {
            companyUserRole.setCompanyId(companyId);
            companyUserRole.setUser(user);
            CompanyUserRole savedCompanyUserRole = companyUserRoleRepository.save(companyUserRole);
            logger.info("Added CompanyUserRole with ID: " + savedCompanyUserRole.getId());
            return savedCompanyUserRole;
        }
        else {
        throw new DuplicateDataException("User Already assigned with a Role"+role);
        }
    }
    public CompanyUserRole updateCompanyUserRole(Long companyUserRoleId,CompanyUserRole updateCompanyUserRole){
        CompanyUserRole existingCompanyUserRole = getCompanyUserRoleById(companyUserRoleId);
        if(existingCompanyUserRole != null) {
            if(updateCompanyUserRole.getRole() != null) {
                existingCompanyUserRole.setRole(updateCompanyUserRole.getRole());
            }
            CompanyUserRole savedCompanyUserRole = companyUserRoleRepository.save(existingCompanyUserRole);
            logger.info("CompanyUserRole updated successfully with ID: " + savedCompanyUserRole.getId());
            return savedCompanyUserRole;
        } else{
            logger.warn("CompanyUserRole not found with ID: " + companyUserRoleId);
            throw new NotFoundException("CompanyUserRole not found with ID: " + companyUserRoleId);
        }
    }
    public CompanyUserRole getCompanyUserRoleById(Long companyUserRoleId) {
        CompanyUserRole companyUserRole = companyUserRoleRepository.findById(companyUserRoleId).orElse(null);
        if (companyUserRole != null) {
            logger.info("Retrieved CompanyUserRole details successfully.");
            return companyUserRole;
        } else {
            logger.warn("CompanyUserRole not found with ID: " + companyUserRoleId);
            return null;
        }
    }
    public void deleteCompanyUserRoleById(Long companyUserRoleId) {
        CompanyUserRole existingCompanyUserRole = getCompanyUserRoleById(companyUserRoleId);
        if(existingCompanyUserRole != null) {
            companyUserRoleRepository.deleteCompanyUserRoleById(companyUserRoleId);
            logger.info("Deleted CompanyUserRole with ID: " + companyUserRoleId);
        } else{
            logger.warn("CompanyUserRole not found with ID: " + companyUserRoleId);
            throw new NotFoundException("CompanyUserRole not found with ID: " + companyUserRoleId);
        }
    }
    public RoleEnum findRoleByUserIdAndCompanyId(Long userId, Long companyId) {
        return companyUserRoleRepository.findRoleByUserIdAndCompanyId(userId, companyId);
    }
}

