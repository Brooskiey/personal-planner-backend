package dev.brooskiey.taskmodule.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@Table(name = "TASK")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private TaskType type;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private TaskStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recurr_id")
    private RecurrTask recurrence;

    @Column(name = "date_initiated", nullable = false)
    private LocalDate dateInitiated;

    @Column(name = "date_completed")
    private LocalDate dateCompleted;

    @Column(name = "is_complete", nullable = false)
    private boolean isComplete;

    public Task(long id, String name, TaskType type,
                TaskStatus status, RecurrTask recurrence,
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

    public Task() {
    }
}
