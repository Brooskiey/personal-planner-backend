package dev.brooskiey.taskmodule.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Type DB entity
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Entity
@Getter
@Setter
@Table(name = "TYPE")
public class TypeEntity {

    /** Primary key TYPE_ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    /** name of the type */
    @Column(name = "name", nullable = false)
    private String name;

    public TypeEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TypeEntity() {

    }
}
