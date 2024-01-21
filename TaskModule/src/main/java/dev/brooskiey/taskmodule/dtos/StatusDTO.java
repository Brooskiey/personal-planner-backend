package dev.brooskiey.taskmodule.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Status data transfer object
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Getter
@Setter
@ToString
public class StatusDTO {

    private long id;
    private String name;
}
