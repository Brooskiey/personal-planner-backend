package dev.brooskiey.personalplanner.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO {

    private long id;
    private String name;
    private TaskTypeDTO type;
    private TaskStatusDTO status;
    private RecurrTaskDTO recurrence;
    private LocalDate dateInitiated;
    private LocalDate dateCompleted;
    private boolean isComplete;

}
