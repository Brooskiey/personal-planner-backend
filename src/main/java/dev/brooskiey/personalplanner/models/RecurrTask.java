package dev.brooskiey.personalplanner.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "RECURR_TASK")
public class RecurrTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "category")
    private String category;

    @Column(name = "recurrence")
    private String recurrence;

    @Column(name = "last_date")
    private LocalDate lastDate;

    public RecurrTask(long id, String category, String recurrence, LocalDate lastDate) {
        this.id = id;
        this.category = category;
        this.recurrence = recurrence;
        this.lastDate = lastDate;
    }

    public RecurrTask() {

    }
}
