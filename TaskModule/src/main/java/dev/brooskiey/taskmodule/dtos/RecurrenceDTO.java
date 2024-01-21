package dev.brooskiey.taskmodule.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Recurrence data transfer object
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Getter
@Setter
@ToString
public class RecurrenceDTO {

    private long id;
    private CategoryDTO category;
    private String recurrence;
    private LocalDate lastDate;
}
