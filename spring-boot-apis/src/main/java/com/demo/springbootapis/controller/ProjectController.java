package com.demo.springbootapis.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springbootapis.model.projects.ProjectInfo;
import com.demo.springbootapis.model.security.UserPrincipal;
import com.demo.springbootapis.security.CurrentUser;
import com.demo.springbootapis.service.ProjectService;

@RestController
@CrossOrigin
public class ProjectController {
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	ProjectService projectService;
	
	//Get All projects
	@GetMapping("/projects")
//	@PreAuthorize("hasRole('ROLE_GUEST')")
	public List<ProjectInfo> getAllProjects(@CurrentUser UserPrincipal currentUser) {
		return projectService.getAllProjects();
	}
	
	//Create a new project
	@PostMapping("/projects")
	public ProjectInfo createProject(@Valid @RequestBody ProjectInfo info) {
		return projectService.createProject(info);
	}
	
	//Get a single project
	@GetMapping("/projects/{id}")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ProjectInfo getProjectById(@PathVariable(value = "id") Integer id) {
		return  projectService.getProjectById(id);
	}
	
	//Update a project
	@PutMapping("/projects/{id}")
	public ProjectInfo updateProject(@PathVariable(value = "id") Integer id, @Valid @RequestBody ProjectInfo info) {
		return projectService.updateProject(info);
	}
	
	//Delete (set isActive to be false and leave in database)
	@DeleteMapping("/projects/{id}")
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteProject(@PathVariable(value = "id") Integer id) {
		projectService.deleteProject(id);
	}
}
