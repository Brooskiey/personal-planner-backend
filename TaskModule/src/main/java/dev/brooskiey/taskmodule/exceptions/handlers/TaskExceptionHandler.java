package dev.brooskiey.taskmodule.exceptions.handlers;

import dev.brooskiey.taskmodule.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TaskExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ FailedToCreateTask.class,
            FailedToGetTask.class,
            FailedToUpdateTask.class,
            FailedToCompleteTask.class,
            FailedToDeleteTask.class})
    public ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex,
                ex.getMessage(),
                headers,
                HttpStatus.BAD_REQUEST,
                request);
    }
}
