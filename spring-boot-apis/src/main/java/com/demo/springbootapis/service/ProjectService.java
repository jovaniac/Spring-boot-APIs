package com.demo.springbootapis.service;

import java.util.List;

import com.demo.springbootapis.model.projects.ProjectInfo;

public interface ProjectService {
	List<ProjectInfo> getAllProjects();
	ProjectInfo createProject(ProjectInfo info);
	ProjectInfo getProjectById(Integer id);
	ProjectInfo updateProject(ProjectInfo info);
	void deleteProject(Integer id);
}
