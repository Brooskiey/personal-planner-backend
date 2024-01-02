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

/**
 * Exception handler for all custom exceptions
 * ( FailedToCreateTask, FailedToGetTask, FailedToUpdateTask, FailedToCompleteTask, FailedToDeleteTask )
 */
@ControllerAdvice
public class TaskExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle an exception that is thrown in the controller. Set status to 400 Bad Request and place the message of the
     * exception in the outgoing entity
     * @param ex Exception thrown with message
     * @param request the that was sent in
     * @return A ResponseEntity with 400 status and the error message
     */
    @ExceptionHandler({ FailedToCreateTask.class,
            FailedToGetTask.class,
            FailedToUpdateTask.class,
            FailedToCompleteTask.class,
            FailedToDeleteTask.class })
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
