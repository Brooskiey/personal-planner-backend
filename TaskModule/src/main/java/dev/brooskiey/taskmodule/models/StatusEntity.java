package dev.brooskiey.taskmodule.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "STATUS")
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private long id;

    @Column(name = "name")
    private String name;

    public StatusEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public StatusEntity() {

    }
}
