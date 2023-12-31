package dev.brooskiey.taskmodule.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RecurrTaskDTO {

    private long id;
    private String category;
    private String recurrence;
    private LocalDate lastDate;
}
