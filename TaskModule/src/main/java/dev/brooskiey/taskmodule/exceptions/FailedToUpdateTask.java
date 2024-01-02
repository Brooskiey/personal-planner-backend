package dev.brooskiey.taskmodule.exceptions;

/**
 * Exception for when an error occurs in the update workflow
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
public class FailedToUpdateTask extends RuntimeException {

    public FailedToUpdateTask(String message) {
        super(message);
    }

}
