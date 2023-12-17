package com.grapplermodule1.GrapplerEnhancement.service;

import com.grapplermodule1.GrapplerEnhancement.customexception.*;
import com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.TeamDTO;
import com.grapplermodule1.GrapplerEnhancement.entities.Project;
import com.grapplermodule1.GrapplerEnhancement.entities.Team;
import com.grapplermodule1.GrapplerEnhancement.entities.Workspace;
import com.grapplermodule1.GrapplerEnhancement.repository.ProjectRepository;
import com.grapplermodule1.GrapplerEnhancement.repository.TeamRepository;
import com.grapplermodule1.GrapplerEnhancement.repository.WorkspaceRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private TeamService teamService;

//    @Autowired
//    private ModelMapper modelMapper;

    @Autowired
    private TeamRepository teamRepository;
    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    /**
     * Getting the list of users
     *
     * @return List
     **/
    public List<ProjectDTO> getAllProjects() {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Getting List of all Projects");
            List<ProjectDTO> projectDTOList = projectRepository.findListOfProjects();

            if (!projectDTOList.isEmpty()) {
                log.info("Got List of all Projects that is present in db");
                projectDTOList.forEach(projectDTO -> {
                    projectDTO.setTeams(getTeamList(projectDTO.getId()));
                });
                return projectDTOList;
            } else {
                log.info("Throws exception because there no project found with UUID {}", debugUuid);
                throw new ProjectNotFoundException("Project not Found");
            }
        } catch (Exception e) {
            log.info("Exception In Fetch All Projects Exception {}", e.getMessage());
            throw e;
        }
    }

    /**
     * For Getting List Of Teams
     *
     * @return List<TeamDTO>
     */
    public List<TeamDTO> getTeamList(Long projectId) {
        try {
            log.info("Get Team By Id Called in Hierarchy Service");
            List<TeamDTO> listOfTeams = teamRepository.searchTeamById(projectId);
            listOfTeams.forEach(ls -> {
                ls.setTeamMembers(teamService.getMembersList(ls.getId()));
            });

            if (!listOfTeams.isEmpty()) {
                log.info("Get Team Member By Id returning List of TeamMemberDTO");
            }
            return listOfTeams;
        } catch (Exception e) {
            log.error("Exception In Get Members List Service in Hierarchy Service Exception {}", e.getMessage());
            throw e;
        }
    }

    /**
     * For Adding A New Project
     *
     * @return Project
     */
    public Project createProject(Long workspaceId, Project project) {
        try {
            // Check if the workspace exists
            Workspace workspace = workspaceRepository.findById(workspaceId).orElse(null);
            if (workspace == null) {
                log.error("Workspace not found with ID: " + workspaceId);
                return null;
            }
            project.setWorkspace(workspace);
            project.setCreationTime(LocalDateTime.now());

            Project createdProject = projectRepository.save(project);
            log.info("Project created successfully");
            return createdProject;
        } catch (Exception e) {
            log.error("Error while creating project", e);
            return null;
        }
    }

    /**
     * For getting A Project with id
     *
     * @return Project
     */
    public ProjectDTO getProjectById(Long projectId) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Fetching Project with id : " + projectId);
            Optional<ProjectDTO> project = projectRepository.findProjectById(projectId);
            if (project.isPresent()) {
                log.info("Project found with id : " + projectId);
                project.get().setTeams(getTeamList(project.get().getId()));
                return project.get();
            } else {
                log.error("Throws exception because there no project found with UUID {}", debugUuid);
                throw new ProjectNotFoundException("Project not Found With Id: " + projectId);
            }
        } catch (Exception e) {
            log.error("Exception In Fetching Project with id, Exception {}", e.getMessage());
            throw e;
        }
    }

    public Project getProjectByWorkspaceAndProject(Long workspaceId, Long projectId) {
        try {
            Project project = projectRepository.findByWorkspace_IdAndId(workspaceId, projectId);
            if (project != null) {
                log.info("Retrieved project successfully.");
                return project;
            } else {
                log.error("Project not found with ID: " + projectId);
                return null;
            }
        } catch (Exception e) {
            log.error("Error while getting project with ID: " + projectId, e);
            return null;
        }
    }
    /**
     * For deleting A Project with id
     *
     * @return boolean
     */
    public boolean deletedByProjectId(Long projectId) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Deleting project with id in service is called, project Id {}", projectId);
            Optional<Project> project = projectRepository.findById(projectId);
            if (project.isPresent()) {
                log.info("Deleting project with id successfully in service with UUID {}, ", debugUuid);
                projectRepository.deleteById(projectId);
                return true;
            } else {
                log.error("Throws exception because there no project found with UUID {}", debugUuid);
                throw new ProjectNotFoundException("Project not Found");
            }
        } catch (Exception e) {
            log.error("Exception In Deleting Project with id, Exception {}", e.getMessage());
            throw e;
        }

    }

    /**
     * For updating A Project with id
     *
     * @return Project
     */
    public Project updateProject(Long workspaceId, Long projectId, Project updatedProject) {
        try {
            Project existingProject = projectRepository.findByWorkspace_IdAndId(workspaceId, projectId);
            if (existingProject != null) {
                if (updatedProject.getName() != null) {
                    existingProject.setName(updatedProject.getName());
                }
                if (updatedProject.getType() != null) {
                    existingProject.setType(updatedProject.getType());
                }
                if (updatedProject.getSubType() != null) {
                    existingProject.setSubType(updatedProject.getSubType());
                }
                Project savedProject = projectRepository.save(existingProject);
                log.info("Project updated successfully");
                return savedProject;
            } else {
                log.error("Project not found with ID: " + projectId);
                return null;
            }
        } catch (Exception e) {
            log.error("Error while updating project with ID: " + projectId, e);
            return null;
        }
    }
    public Boolean deletedTeamFromProject(Long projectId, Long teamId) {
        try {
            log.info("Deleting project with id in service is called, project Id {}", projectId);

            Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found With ID : " + projectId));

            Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team Not Found With ID : " + teamId));

//            Set<Team> projectTeams = project.getTeams();
//
//            projectTeams = projectTeams.stream().filter((t) -> t.getId() != teamId).collect(Collectors.toSet());
//
//            project.setTeams(projectTeams);
            projectRepository.save(project);

            return true;
        } catch (Exception e) {
            log.error("Exception In Deleting Project with id, Exception {}", e.getMessage());
            throw e;
        }
    }

    /**
     * For Assigning Team
     *
     * @return Boolean
     */
    public List<TeamDTO> assignTeams(Long projectId, List<Long> teamIds) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Inside Assign Teams in Project service with UUID{}, ", debugUuid);
            Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found With ID : " + projectId));

            List<Team> teams = teamRepository.findAllById(teamIds);
            if (teams.isEmpty()) {
                throw new TeamNotFoundException("No Teams Found with the given IDs");
            }

            Set<Team> projectTeams = project.getTeams();

            for (Team team : teams) {
                if (projectTeams.contains(team)) {
                    throw new TeamAlreadyPresentInTheProjectException("Team " + team.getId() + " is already present in the project");
                }
                projectTeams.add(team);
            }

            project.setTeams(projectTeams);
            projectRepository.save(project);

            return teams.stream()
                    .map(team -> teamRepository.findTeamById(team.getId())
                            .orElseThrow(() -> new ResourseNotFoundException(("Team Not Found With ID : " + team.getId()))))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Exception In Assign Teams Service, Exception {}", e.getMessage());
            throw e;
        }
    }

}