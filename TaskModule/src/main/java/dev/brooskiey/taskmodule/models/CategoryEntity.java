package dev.brooskiey.taskmodule.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "CATEGORY")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    public CategoryEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryEntity() {
    }
}
