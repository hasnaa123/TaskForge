package com.taskforge.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		//SpringApplication.run(BackendApplication.class, args);
		
        System.out.println("========== TASKFORGE DOMAIN MODEL TESTS ==========");

        testUser();
        testTask();
        testComment();
        testProject();
        testOrganization();

        System.out.println("\n========== ALL TESTS FINISHED ==========");
    }

    // ---------------- USER TESTS ----------------

    private static void testUser() {
        System.out.println("\n--- USER TESTS ---");

        User user = new User("testuser", "test@email.com", Role.DEVELOPER);

        check(user.getId() != null, "User ID is generated");
        check(user.getUsername().equals("testuser"), "Username is assigned");
        check(user.getEmail().equals("test@email.com"), "Email is assigned");

        expectException(IllegalArgumentException.class,
                () -> new User("", "test@email.com", Role.DEVELOPER),
                "Reject empty username");

        expectException(IllegalArgumentException.class,
                () -> new User("testuser", null, Role.DEVELOPER),
                "Reject null email");

        User anotherUser = new User("anotheruser", "another@email.com", Role.ADMIN);

        check(!user.equals(anotherUser), "Different users are not equal");
        check(user.equals(user), "User equals itself");

        check(user.hashCode() == user.hashCode(), "User hashCode is consistent");
    }

    // ---------------- TASK TESTS ----------------

    private static void testTask() {
        System.out.println("\n--- TASK TESTS ---");

        Task task = new Task("Implement authentication", TaskPriority.HIGH);

        check(task.getId() != null, "Task ID is generated");
        check(task.getStatus() == TaskStatus.TODO, "Task starts with TODO");

        task.markInProgress();
        check(task.getStatus() == TaskStatus.IN_PROGRESS, "Task starts successfully");

        task.markDone();
        check(task.getStatus() == TaskStatus.DONE, "Task completes successfully");

        expectException(IllegalStateException.class,
                () -> task.markCancelled(),
                "Cannot cancel a completed task");

        expectException(IllegalArgumentException.class,
                () -> new Task("", TaskPriority.HIGH),
                "Reject empty task name");
    }

    // ---------------- COMMENT TESTS ----------------

    private static void testComment() {
        System.out.println("\n--- COMMENT TESTS ---");

        User author = new User("commenter", "commenter@email.com", Role.DEVELOPER);

        Comment comment = new Comment("This is a test comment", author);

        check(comment.getId() != null, "Comment ID is generated");
        check(comment.getAuthor().equals(author), "Comment author is assigned");
        check(comment.getCreatedAt() != null, "Comment creation date is generated");

        expectException(IllegalArgumentException.class,
                () -> new Comment(" ", author),
                "Reject blank comment content");

        expectException(IllegalArgumentException.class,
                () -> new Comment("Valid content", null),
                "Reject null author");
    }

    // ---------------- PROJECT TESTS ----------------

    private static void testProject() {
        System.out.println("\n--- PROJECT TESTS ---");

        Project project = new Project("TaskForge Platform");

        check(project.getId() != null, "Project ID is generated");
        check(project.getStatus() == ProjectStatus.PLANNING, "Project starts in PLANNING");

        project.active();
        check(project.getStatus() == ProjectStatus.ACTIVE, "Project activates successfully");

        project.complete();
        check(project.getStatus() == ProjectStatus.COMPLETED, "Project completes successfully");

        expectException(IllegalStateException.class,
                () -> project.cancel(),
                "Cannot cancel completed project");

        Task task = new Task("Create dashboard", TaskPriority.MEDIUM);

        Project anotherProject = new Project("Another Project");
        anotherProject.addTask(task);

        check(anotherProject.getTasks().contains(task), "Project accepts a task");

        expectException(IllegalArgumentException.class,
                () -> anotherProject.addTask(null),
                "Reject null task");

        expectException(UnsupportedOperationException.class,
                () -> anotherProject.getTasks().clear(),
                "Prevent external modification of tasks");
    }

    // ---------------- ORGANIZATION TESTS ----------------

    private static void testOrganization() {
        System.out.println("\n--- ORGANIZATION TESTS ---");

        Organization organization = new Organization("OpenAI Team");

        check(organization.getId() != null, "Organization ID is generated");

        Project project = new Project("TaskForge Project");

        organization.addProject(project);

        check(organization.getProjects().contains(project),
                "Organization accepts a project");

        expectException(IllegalArgumentException.class,
                () -> organization.addProject(null),
                "Reject null project");

        expectException(UnsupportedOperationException.class,
                () -> organization.getProjects().clear(),
                "Prevent external modification of projects");

        expectException(IllegalArgumentException.class,
                () -> new Organization("AB"),
                "Reject organization name shorter than 3 characters");
    }

    // ---------------- TEST HELPERS ----------------

    private static void check(boolean condition, String description) {
        if (condition) {
            System.out.println("[PASS] " + description);
        } else {
            System.err.println("[FAIL] " + description);
            throw new AssertionError(description);
        }
    }

    private static void expectException(
            Class<? extends Throwable> expectedException,
            Runnable action,
            String description) {

        try {
            action.run();
            System.err.println("[FAIL] " + description + " - No exception thrown");
            throw new AssertionError(description);

        } catch (Throwable exception) {
            if (expectedException.isInstance(exception)) {
                System.out.println("[PASS] " + description);
            } else {
                System.err.println("[FAIL] " + description
                        + " - Unexpected exception: " + exception);
                throw new AssertionError(description, exception);
            }
        }
    }
}
