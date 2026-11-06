package com.taskforge.backend;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Organization {

    private final UUID id;
    private String name;
    private final List<Project> projects;

    public Organization(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Organization name cannot be null or blank");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Organization name must be at least 3 characters long");
        }
        this.id = UUID.randomUUID();
        this.name = name;
        this.projects = new ArrayList<>();
    }

    // getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Project> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    // setters
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Organization name cannot be null or blank");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Organization name must be at least 3 characters long");
        }
        this.name = name;
    }

    // add a project to the organization
    public void addProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        if (projects.contains(project)) {
            throw new IllegalArgumentException("Project already belongs to this organization");
        }
        this.projects.add(project);
    }

    // remove a project from the organization
    public void removeProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        this.projects.remove(project);
    }

}
