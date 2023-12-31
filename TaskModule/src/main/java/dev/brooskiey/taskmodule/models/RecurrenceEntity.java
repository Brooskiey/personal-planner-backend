package dev.brooskiey.taskmodule.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "RECURRENCE")
public class RecurrenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recurr_id")
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "recurrence")
    private String recurrence;

    @Column(name = "last_date")
    private LocalDate lastDate;

    public RecurrenceEntity(long id, CategoryEntity category, String recurrence, LocalDate lastDate) {
        this.id = id;
        this.category = category;
        this.recurrence = recurrence;
        this.lastDate = lastDate;
    }

    public RecurrenceEntity() {

    }
}
