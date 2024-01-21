package dev.brooskiey.taskmodule.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Status DB entity
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "STATUS")
public class StatusEntity {

    /** Primary Key STATUS_ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /** Status name */
    @Column(name = "name")
    private String name;

    public StatusEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
