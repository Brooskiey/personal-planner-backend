package dev.brooskiey.taskmodule.exceptions;

/**
 * Exception for when an error occurs in the delete workflow
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
public class FailedToDeleteTask  extends RuntimeException {

    public FailedToDeleteTask(String message) {
        super(message);
    }
}
