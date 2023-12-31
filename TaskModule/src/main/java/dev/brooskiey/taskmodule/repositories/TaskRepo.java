package dev.brooskiey.taskmodule.repositories;

import dev.brooskiey.taskmodule.models.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepo extends CrudRepository<Task, Long> {

    Task findById(long id);

    List<Task> findByDateInitiated(LocalDate date);

    List<Task> findByRecurrenceId(long id);

    List<Task> findByName(String name);

    @Query("SELECT t FROM Task t WHERE t.type.name like ?1")
    List<Task> findByType(String type);
}
