package com.grapplermodule1.GrapplerEnhancement.controllers;

import com.grapplermodule1.GrapplerEnhancement.customexception.*;
import com.grapplermodule1.GrapplerEnhancement.dto.ApiResponseProject;
import com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.TeamDTO;
import com.grapplermodule1.GrapplerEnhancement.entities.Team;
import com.grapplermodule1.GrapplerEnhancement.entities.Project;
import com.grapplermodule1.GrapplerEnhancement.service.ProjectService;
import com.grapplermodule1.GrapplerEnhancement.validations.PostValidation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins="http://localhost:3000/")
public class ProjectsController {

    @Autowired
    private ProjectService projectService;

    private static final Logger log = LoggerFactory.getLogger(ProjectsController.class);

    @GetMapping("/hello")
    public String sayHello(){
        return "This is say hello string";
    }

    /**
     * For Getting List of Projects
     *
     * @return ResponseEntity
     **/
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<?> getAllProjects() {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Getting data of all Projects");
            List<ProjectDTO> projectList = projectService.getAllProjects();
            return new ResponseEntity<>(projectList, HttpStatus.OK);
        } catch (ProjectNotFoundException p) {
            log.error("Getting exception because there is no project in the list, UUID {}", debugUuid, p);
            return new ResponseEntity<>(new CustomResponse<>(false, p.getMessage(), null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Getting exception when we are trying to fetch the list of the projects, UUID {}", debugUuid, e);
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For Create New Project
     *
     * @return ResponseEntity
     */
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{projectId}/workspaces/{workspaceId}")
    public ResponseEntity<ApiResponseProject<Project>> createProject(@PathVariable Long workspaceId,@Valid @RequestBody Project project) {
        try {
            Project createdProject = projectService.createProject(workspaceId, project);
            log.info("Project created successfully having ID : {}", createdProject.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseProject<>(true, createdProject, "Project created successfully having ID : "+createdProject.getId()));
        } catch (Exception e) {
            log.error("Internal Server Error while creating a project", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, "Internal Server Error"));
        }
    }

    /**
     * For getting Project By I'd
     *
     * @return ResponseEntity<Project>
     */
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@Valid @PathVariable Long projectId) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Inside Get Project By Id,UUID {} ", projectId);
            Optional<ProjectDTO> project = Optional.ofNullable(projectService.getProjectById(projectId));

            log.info("Get project By Id API Called, UUID {}", debugUuid);
            return new ResponseEntity<>(project, HttpStatus.OK);

        }catch (ProjectNotFoundException p){
            log.error("UUID {}, ProjectNotFoundException in Get User BY Id API, Exception {}", debugUuid, p.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, p.getMessage(), null), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("UUID {}, Getting Exception in fetch Project BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{projectId}/workspaces/{workspaceId}")
    public ResponseEntity<ApiResponseProject<Project>> getProjectByWorkspaceAndProject(@PathVariable Long workspaceId, @PathVariable Long projectId) {
        try {
            Project project = projectService.getProjectByWorkspaceAndProject(workspaceId, projectId);
            if (project != null) {
                log.info("Project retrieved successfully having ID : {}", projectId);
                return ResponseEntity.ok(new ApiResponseProject<>(true, project, "Project retrieved successfully having ID : "+projectId));
            } else {
                log.warn("Project not found with WorkSpace ID : {} and  Project ID : {}",workspaceId, projectId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseProject<>(false, null, "Project not found with WorkSpace ID : "+workspaceId+" and  Project ID : "+projectId));
            }
        } catch (Exception e) {
            log.error("Internal Server Error while retrieving project with ID: " + projectId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseProject<>(false, null, "Internal Server Error"));
        }
    }

    /**
     * For Deleting Project By I'd
     *
     * @return ResponseEntity<Project>
     */
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deletedById(@Valid @PathVariable Long projectId) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Inside Delete Project By Id,UUID {} ", projectId);
            boolean project = projectService.deletedByProjectId(projectId);
            log.info("Delete Project By Id API called ,UUID {} ", projectId);
            return new ResponseEntity<>(new CustomResponse<>(true, "Project Deleted Successfully", project), HttpStatus.OK);
        }catch (ProjectNotFoundException p){
            log.error("UUID {}, ProjectNotFoundException in Delete Project BY Id API, Exception {}", debugUuid, p.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, p.getMessage(), false), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("UUID {}, Getting Exception in Delete Project BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), false),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For Updating Project details By I'd
     *
     * @return ResponseEntity<Project>
     */
//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/{projectId}")
//    public ResponseEntity<?> updateById(@Valid @PathVariable Long projectId, @Valid @RequestBody Project project) {
//        String debugUuid = UUID.randomUUID().toString();
//        try{
//            log.info("Inside Updating project with id in service with UUID{}, ", debugUuid);
//            Optional<Project> project1 = Optional.ofNullable(projectService.updateProjectById(projectId, project));
//            return project1.map(value -> new ResponseEntity<>(new CustomResponse<>(true, "Update Project Details Successfully", value), HttpStatus.OK)).orElse(null);
//
//        }catch (DuplicateProjectName dp){
//            log.error("UUID {} Getting exception while Updating a new project Exception {}", debugUuid, dp.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, "Project name is already in use", null),HttpStatus.CONFLICT);
//        }
//        catch (ProjectNotFoundException p){
//            log.error("UUID {}, ProjectNotFoundException in Delete Project BY Id API, Exception {}", debugUuid, p.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, p.getMessage(), false), HttpStatus.NOT_FOUND);
//        }
//        catch (DataNotPresent dnp){
//            log.error("UUID {} Getting exception while adding a new project Exception {}", debugUuid, dnp.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, "Do not Pass Empty String, Please Fill the data Properly", null),HttpStatus.NO_CONTENT);
//        }
//        catch (Exception e) {
//            log.error("UUID {}, Getting Exception in Update Project BY Id API, Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), false),HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    /**
     * For Assigning Team To A Project
     *
     * @return ResponseEntity<?>
     */
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{projectId}/teams")
    public ResponseEntity<?> assignTeamsToProject(@PathVariable Long projectId, @RequestBody List<Long> teamIds) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Assign Teams To Project API Called, UUID{}", debugUuid);
            List<TeamDTO> addedTeams = projectService.assignTeams(projectId, teamIds);
            if (!addedTeams.isEmpty()) {
                log.info("Assign Teams To Project API Returning Response Entity With OK, UUID{}", debugUuid);
                return new ResponseEntity<>(new CustomResponse<>(true, "Teams Successfully Added To Project With ID : " + projectId, addedTeams), HttpStatus.OK);
            } else {
                log.info("UUID {} No Teams Added to Project", debugUuid);
                return new ResponseEntity<>(new CustomResponseMessage(false, "No Teams Added to Project"), HttpStatus.BAD_REQUEST);
            }
        } catch (ProjectNotFoundException e) {
            log.error("UUID {} Getting ProjectNotFoundException In Assign Teams To Project, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (TeamNotFoundException e) {
            log.error("UUID {} Getting TeamNotFoundException in Assign Teams To Project API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (TeamAlreadyPresentInTheProjectException e) {
            log.error("UUID {} Getting TeamAlreadyPresentInTheProjectException in Assign Teams To Project API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.error("UUID {} Getting Exception in Assign Teams To Project, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * For Delete Team From Project
     *
     * @return ResponseEntity<?>
     */
    @DeleteMapping("/{projectId}/teams/{teamId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletedTeamFromProject(@PathVariable("projectId") Long projectId, @PathVariable("teamId") Long teamId) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Inside Delete Team From Project,UUID {} ", projectId);
            boolean isDeleted = projectService.deletedTeamFromProject(projectId, teamId);

            return new ResponseEntity<>(new CustomResponseMessage(true, "Team Removed Successfully"), HttpStatus.OK);
        }catch (ProjectNotFoundException p){
            log.error("UUID {}, ProjectNotFoundException in Delete Project BY Id API, Exception {}", debugUuid, p.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, p.getMessage(), false), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("UUID {}, Getting Exception in Delete Project BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), false),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
