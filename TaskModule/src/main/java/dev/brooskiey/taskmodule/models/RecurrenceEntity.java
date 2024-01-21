package dev.brooskiey.taskmodule.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Recurrence DB entity
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "RECURRENCE")
public class RecurrenceEntity {

    /** Primary key RECURR_ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /** Category the recurrence belongs to. Foreign key to CATEGORY_ID */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    /** Recurrence name */
    @Column(name = "recurrence")
    private String recurrence;

    /** Last date the recurrence task occurred */
    @Column(name = "last_date")
    private LocalDate lastDate;

    public RecurrenceEntity(long id, CategoryEntity category, String recurrence, LocalDate lastDate) {
        this.id = id;
        this.category = category;
        this.recurrence = recurrence;
        this.lastDate = lastDate;
    }
}
