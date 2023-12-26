package dev.brooskiey.personalplanner.repositories;

import dev.brooskiey.personalplanner.models.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepo extends CrudRepository<Task, Long> {

    Task findById(long id);

    List<Task> findByDateInitiated(Date date);

    List<Task> findByRecurrenceId(long id);

    List<Task> findByName(String name);

    @Query("SELECT t FROM Task t WHERE t.type.name like ?1")
    List<Task> findByType(String type);
}
