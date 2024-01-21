package dev.brooskiey.taskmodule.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Task DB entity
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "TASK")
public class TaskEntity {

    /** Primary key ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    /** name of the task */
    @Column(name = "name", nullable = false)
    private String name;

    /** Type entity for the type of the task. Foreign Key TYPE_ID*/
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private TypeEntity type;

    /** Status of the task. Foreign Key STATUS_ID */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private StatusEntity status;

    /** Recurrence of the task. Foreign Key RECURR_ID */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recurr_id")
    private RecurrenceEntity recurrence;

    /** Date of the task is to be started */
    @Column(name = "date_initiated", nullable = false)
    private LocalDate dateInitiated;

    /** Date the task is completed */
    @Column(name = "date_completed")
    private LocalDate dateCompleted;

    /** Boolean value for if the task is completed */
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
}
