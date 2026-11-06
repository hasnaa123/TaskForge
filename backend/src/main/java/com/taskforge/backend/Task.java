package com.taskforge.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Task {
    private final UUID id;
    private String name;
    private TaskPriority priority;
    private TaskStatus status;
    private ArrayList<Comment> comments;
    private Set<User> assignedUsers;

    public Task(String name, TaskPriority priority) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("this field cannot be null or empty");
        }
        if(priority == null) {
            throw new IllegalArgumentException("this field cannot be null");
        }
        if(name.length() < 5) {
            throw new IllegalArgumentException("name must be at least 5 characters long");
        }
        this.id = UUID.randomUUID();
        this.name = name;
        this.priority = priority;
        this.status = TaskStatus.TODO; // default status;
        this.comments = new ArrayList<>();
        this.assignedUsers = new HashSet<>();
    }

      @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Task other))
            return false;

        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    // getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    // setters
    public void setName(String name) {
        if(name == null || name.isBlank() || name.length() < 5) {
            throw new IllegalArgumentException("this field cannot be null or empty");
        }
        this.name = name;
    }

    public void setPriority(TaskPriority priority) {
        if(priority == null) {
            throw new IllegalArgumentException("this field cannot be null");
        }
        this.priority = priority;
    }

    // Users management methods
    public void assignUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("this field cannot be null");
        }

        this.assignedUsers.add(user);
    }

    public void unassignUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("this field cannot be null");
        }
        this.assignedUsers.remove(user);
    }

    public Set<User> getAssignedUsers() {
         return Collections.unmodifiableSet(assignedUsers);
    }

    // Comments management methods
    public void addComment(Comment comment) {
        if(comment == null) {
            throw new IllegalArgumentException("this field cannot be null");
        }
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        if(comment == null) {
            throw new IllegalArgumentException("this field cannot be null");
        }
        this.comments.remove(comment);
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    // Status management methods

    public void markInProgress() {
        if (this.status != TaskStatus.TODO) {
            throw new IllegalStateException(
                    "Only TODO tasks can become IN_PROGRESS");
        }

        this.status = TaskStatus.IN_PROGRESS;
    }

    public void markDone() {
        if (this.status != TaskStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Only IN_PROGRESS tasks can become DONE");
        }

        this.status = TaskStatus.DONE;

    }

    public void markCancelled() {
        if (this.status == TaskStatus.DONE || this.status == TaskStatus.CANCELLED) {
            throw new IllegalStateException("this status change is not allowed");
        }
        this.status = TaskStatus.CANCELLED;

    }

    public TaskStatus getStatus() {
        return status;
    }

}