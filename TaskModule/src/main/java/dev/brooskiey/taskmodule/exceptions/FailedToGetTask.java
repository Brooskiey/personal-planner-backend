package dev.brooskiey.taskmodule.exceptions;

/**
 * Exception for when an error occurs in the get task workflow
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
public class FailedToGetTask extends RuntimeException {

    public FailedToGetTask(String message) {
        super(message);
    }
}
