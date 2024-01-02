package dev.brooskiey.taskmodule.exceptions;

/**
 * Exception for when an error occurs in the create workflow
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
public class FailedToCreateTask extends RuntimeException {

    public FailedToCreateTask(String message) {
        super(message);
    }
}
