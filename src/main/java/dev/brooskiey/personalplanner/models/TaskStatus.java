package dev.brooskiey.personalplanner.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "TASK_STATUS")
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    public TaskStatus(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TaskStatus() {

    }
}
