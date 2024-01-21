package dev.brooskiey.taskmodule.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Category data transfer object
 * @author Broosiey Bullet
 * @version 01.01.2024
 */

@Getter
@Setter
@ToString
public class CategoryDTO {

    private long id;
    private String name;
}
