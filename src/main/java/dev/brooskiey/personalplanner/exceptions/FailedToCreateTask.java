package dev.brooskiey.personalplanner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FailedToCreateTask extends Exception {

    public FailedToCreateTask(String message) {
        super(message);
    }
}
