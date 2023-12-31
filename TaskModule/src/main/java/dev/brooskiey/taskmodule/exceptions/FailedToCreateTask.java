package dev.brooskiey.taskmodule.exceptions;

public class FailedToCreateTask extends RuntimeException {

    public FailedToCreateTask(String message) {
        super(message);
    }
}
