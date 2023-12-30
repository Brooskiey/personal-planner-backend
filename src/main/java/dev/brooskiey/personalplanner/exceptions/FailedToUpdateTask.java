package dev.brooskiey.personalplanner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FailedToUpdateTask extends Exception {

    public FailedToUpdateTask(String message) {
        super(message);
    }

}
