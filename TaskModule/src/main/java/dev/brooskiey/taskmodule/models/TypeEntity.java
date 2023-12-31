package dev.brooskiey.taskmodule.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TYPE")
public class TypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    public TypeEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TypeEntity() {

    }
}
