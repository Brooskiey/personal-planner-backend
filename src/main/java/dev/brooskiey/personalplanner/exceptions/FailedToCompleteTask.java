package dev.brooskiey.personalplanner.exceptions;

public class FailedToCompleteTask  extends RuntimeException {

    public FailedToCompleteTask(String message) {
        super(message);
    }
}
