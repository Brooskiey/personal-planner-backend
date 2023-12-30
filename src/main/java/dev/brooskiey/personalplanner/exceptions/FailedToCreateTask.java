package dev.brooskiey.personalplanner.exceptions;

public class FailedToCreateTask extends RuntimeException {

    public FailedToCreateTask(String message) {
        super(message);
    }
}
