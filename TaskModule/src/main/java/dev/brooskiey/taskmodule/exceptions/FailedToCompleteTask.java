package dev.brooskiey.taskmodule.exceptions;

/**
 * Exception for when an error occurs in the complete workflow
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
public class FailedToCompleteTask  extends RuntimeException {

    public FailedToCompleteTask(String message) {
        super(message);
    }
}
