package com.taskforge.backend;

import java.util.UUID;
import java.time.Instant;
import java.util.Objects;

final public class Comment {
    private final UUID id;
    private String content;
    private final User author;
    private final Instant createdAt;

    public Comment(String content, User author) {

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or blank");
        }
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }

        this.id = UUID.randomUUID();
        this.content = content;
        this.author = author;
        this.createdAt = Instant.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Comment other))
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

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    // setters
    public void setContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or blank");
        }
        this.content = content;
    }

}