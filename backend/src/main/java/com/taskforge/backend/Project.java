package com.taskforge.backend;

import java.util.Objects;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public class Project {
    private final UUID id;
    private String name;
    private ProjectStatus status;
    private final Set<Task> tasks;

    public Project(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Project name cannot be null or blank");
        }
        if (name.length() < 5) {
            throw new IllegalArgumentException("Project name must be at least 5 characters long");
        }
        this.id = UUID.randomUUID();
        this.name = name;
        this.status = ProjectStatus.PLANNING; // default status
        this.tasks = new HashSet<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Project other))
            return false;
        return Objects.equals(this.id, other.id);
    }

    // getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // setters

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Project name cannot be null or blank");
        }
        if (name.length() < 5) {
            throw new IllegalArgumentException("Project name must be at least 5 characters long");
        }
        this.name = name;
    }

    // add a task to the project
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        this.tasks.remove(task);
    }

    public Set<Task> getTasks() {
        return Collections.unmodifiableSet(tasks);
    }

    // status
    public ProjectStatus getStatus() {
        return status;
    }

    public void active() {
        if (this.status != ProjectStatus.PLANNING) {
            throw new IllegalStateException("This status update is not allowed.");
        }
        this.status = ProjectStatus.ACTIVE;

    }

    public void complete() {
        if (this.
            status != ProjectStatus.ACTIVE) {
            throw new IllegalStateException("This status update is not allowed.");
        }
        this.status = ProjectStatus.COMPLETED;
    }

    public void cancel() {
        if (this.status == ProjectStatus.COMPLETED || this.status == ProjectStatus.CANCELLED) {
            throw new IllegalStateException("This status update is not allowed.");
        }
        this.status = ProjectStatus.CANCELLED;
    }

}
