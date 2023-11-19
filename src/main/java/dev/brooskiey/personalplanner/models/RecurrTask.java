package dev.brooskiey.personalplanner.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

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

    @Column(name = "name")
    private String name;

    @Column(name = "last_date")
    private Date lastDate;

    public RecurrTask(long id, String name, Date lastDate) {
        this.id = id;
        this.name = name;
        this.lastDate = lastDate;
    }

    public RecurrTask() {

    }
}
