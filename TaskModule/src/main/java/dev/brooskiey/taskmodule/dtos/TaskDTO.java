package dev.brooskiey.taskmodule.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Task data transfer object
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Getter
@Setter
public class TaskDTO {

    private long id;
    private String name;
    private TypeDTO type;
    private StatusDTO status;
    private RecurrenceDTO recurrence;
    private LocalDate dateInitiated;
    private LocalDate dateCompleted;
    private boolean isComplete;

}
