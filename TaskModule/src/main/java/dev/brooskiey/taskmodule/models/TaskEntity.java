package dev.brooskiey.taskmodule.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@Table(name = "TASK")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private TypeEntity type;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private StatusEntity status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recurr_id")
    private RecurrenceEntity recurrence;

    @Column(name = "date_initiated", nullable = false)
    private LocalDate dateInitiated;

    @Column(name = "date_completed")
    private LocalDate dateCompleted;

    @Column(name = "is_complete", nullable = false)
    private boolean isComplete;

    public TaskEntity(long id, String name, TypeEntity type,
                      StatusEntity status, RecurrenceEntity recurrence,
                      LocalDate dateInitiated, LocalDate dateCompleted,
                      boolean isComplete) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.recurrence = recurrence;
        this.dateInitiated = dateInitiated;
        this.dateCompleted = dateCompleted;
        this.isComplete = isComplete;
    }

    public TaskEntity() {
    }
}
