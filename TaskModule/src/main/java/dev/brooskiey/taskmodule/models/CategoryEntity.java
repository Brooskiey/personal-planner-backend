package dev.brooskiey.taskmodule.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Category DB entity
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Entity
@Getter
@Setter
@Table(name = "CATEGORY")
public class CategoryEntity {

    /** Primary key CATEGORY_ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /** The name of the category */
    @Column(name = "name", nullable = false)
    private String name;

    public CategoryEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryEntity() {
    }
}
