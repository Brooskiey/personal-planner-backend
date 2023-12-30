package dev.brooskiey.personalplanner.exceptions;

public class FailedToDeleteTask  extends RuntimeException {

    public FailedToDeleteTask(String message) {
        super(message);
    }
}
