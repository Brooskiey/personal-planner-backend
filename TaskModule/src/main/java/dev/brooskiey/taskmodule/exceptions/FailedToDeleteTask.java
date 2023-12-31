package dev.brooskiey.taskmodule.exceptions;

public class FailedToDeleteTask  extends RuntimeException {

    public FailedToDeleteTask(String message) {
        super(message);
    }
}
