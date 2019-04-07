package com.demo.springbootapis.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.demo.springbootapis.aop.LogExecutionTime;
import com.demo.springbootapis.model.projects.ProjectInfo;
import com.demo.springbootapis.model.security.UserPrincipal;
import com.demo.springbootapis.security.CurrentUser;
import com.demo.springbootapis.service.ProjectService;

@RestController
@CrossOrigin
@RequestMapping("/projects")
public class ProjectController {

  @Autowired
  ProjectService projectService;

  // Get All projects
  @GetMapping()
  // @PreAuthorize("hasRole('ROLE_GUEST')")
  @LogExecutionTime
  public List<ProjectInfo> getAllProjects(@CurrentUser UserPrincipal currentUser) {
    return projectService.getAllProjects();
  }

  // Create a new project
  @PostMapping()
  public ProjectInfo createProject(@Valid @RequestBody ProjectInfo info) {
    return projectService.createProject(info);
  }

  // Get a single project
  @GetMapping("/{id}")
  // @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ProjectInfo getProjectById(@PathVariable(value = "id") Integer id) {
    return projectService.getProjectById(id);
  }

  // Update a project
  @PutMapping("/{id}")
  public ProjectInfo updateProject(@PathVariable(value = "id") Integer id,
      @Valid @RequestBody ProjectInfo info) {
    return projectService.updateProject(info);
  }

  // Delete (set isActive to be false and leave in database)
  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteProject(@PathVariable(value = "id") Integer id) {
    projectService.deleteProject(id);
  }
}
