package dev.brooskiey.taskmodule.exceptions;

public class FailedToCompleteTask  extends RuntimeException {

    public FailedToCompleteTask(String message) {
        super(message);
    }
}
