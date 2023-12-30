package dev.brooskiey.personalplanner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FailedToCompleteTask  extends Exception {

    public FailedToCompleteTask(String message) {
        super(message);
    }
}
